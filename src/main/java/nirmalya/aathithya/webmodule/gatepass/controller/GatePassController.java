package nirmalya.aathithya.webmodule.gatepass.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.gatepass.model.GatePassDetailsModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryGatePassEntryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.ShipmentDetailsVendorModel;

@Controller
@RequestMapping(value = "gatepass/")
public class GatePassController {

	Logger logger = LoggerFactory.getLogger(GatePassController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	private static final String USER_Id = "USER_ID";

	@GetMapping("/gate-pass")
	public String gatePassEntry(Model model, HttpSession session) {
		logger.info("Method : gatePass add starts");

		// String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			// userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(
					env.getGatepassUrl() + "get-purchseOrderId-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> pOrderIdList = Arrays.asList(dd);

			model.addAttribute("pOrderIdList", pOrderIdList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] type = restClient.getForObject(
					env.getGatepassUrl() + "get-entryType-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			System.out.println("entryTypeList>>>>>>>------" + typeList);
			model.addAttribute("entryTypeList", typeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(env.getGatepassUrl() + "get-purchseOrderIdForExit-list",
					DropDownModel[].class);
			List<DropDownModel> pOrderIdListForExit = Arrays.asList(dd);

			model.addAttribute("pOrderIdListForExit", pOrderIdListForExit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(env.getGatepassUrl() + "get-noOfwheeler-list",
					DropDownModel[].class);
			List<DropDownModel> noOfwheelerList = Arrays.asList(dd);

			model.addAttribute("noOfwheelerList", noOfwheelerList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : gatePass ends");
		return "gatepass/gatePassDetails";
	}

	/*
	 * save image
	 */
	@GetMapping("/gate-pass-get-purchaseId")
	public @ResponseBody List<DropDownModel> getPurchaseOrderIds(HttpSession session) {
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] dd = restClient.getForObject(
					env.getGatepassUrl() + "get-purchseOrderId-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			return Arrays.asList(dd);
		} catch (RestClientException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@PostMapping("gate-pass-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		System.out.println("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	// gate-pass add

	@SuppressWarnings("unchecked")
	@PostMapping("gate-pass-entry-add")
	public @ResponseBody JsonResponse<Object> addGatepassEntry(HttpSession session,
			@RequestBody List<GatePassDetailsModel> gatePassDetailsModel) {
		logger.info("Method : addGatepassEntry starts");
		logger.info("addd gate pass entry" + gatePassDetailsModel.get(0).getInspectList());
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (GatePassDetailsModel m : gatePassDetailsModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDiv);
		}

		for (GatePassDetailsModel a : gatePassDetailsModel.get(0).getDocDetails()) {

			if (a.getIsEditDoc() == null && a.getIsEditDoc() == "") {
				a.setDocView(a.getDocView());
			} else {
				if (a.getDocView() != null && a.getDocView() != "") {
					// String[] extension = a.getFileName().split("\\.");
					String extension = a.getDocType();
					// int lastindex = extension.length - 1;
					/*
					 * try { byte[] bytes = Base64.getDecoder().decode(a.getIsEditDoc());
					 * logger.info("bytes" + a.getIsEditDoc()); String imageName =
					 * saveAllMultiImages(bytes, extension); a.setDocView(imageName);
					 * 
					 * } catch (Exception e) { e.printStackTrace();
					 * 
					 * }
					 */

					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, extension);
							a.setDocView(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}

				}
			}
		}

		/*
		 * MultipartFile inputFile = (MultipartFile)
		 * session.getAttribute("gateDocFile"); System.out.println("inputFile=====" +
		 * inputFile); byte[] bytes; String imageName = null; if (inputFile != null) {
		 * try { bytes = inputFile.getBytes(); String[] fileType =
		 * inputFile.getContentType().split("/"); imageName = saveAllMultiImages(bytes,
		 * fileType[1]); System.out.println("imageName====" + imageName);
		 * gatePassDetailsModel.get(0).setImage(imageName); } catch (IOException e1) {
		 * e1.printStackTrace(); } }
		 */
		System.out.println("gatePassDetailsModel=====" + gatePassDetailsModel);
		try {
			resp = restTemplate.postForObject(env.getGatepassUrl() + "rest-add-gatepass-entry", gatePassDetailsModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<GatePassDetailsModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<GatePassDetailsModel>>() {
					});

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
			session.removeAttribute("gateDocFile");
		}

		logger.info("Method : addGatepassEntry ends");

		return resp;
	}

	@PostMapping("/gate-pass-doc-upload-file")
	public @ResponseBody JsonResponse<Object> uploadDocFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : gate uploadDocFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("gateDocFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : gate uploadDocFile controller ' ends");
		return response;
	}

	@PostMapping("/gate-pass-doc-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile controller function for session clearing 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.setAttribute("gateDocFile", null);
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile controller function 'post-mapping' ends");
		return response;
	}

	public String saveAllMultiImages(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllMultiImages starts");
		String imageName1 = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}

	// View
	@SuppressWarnings("unchecked")

	@GetMapping("gate-pass-get-gatepassentry-list")
	public @ResponseBody List<InventoryGatePassEntryModel> viewgatepassEntry(HttpSession session) {

		logger.info("Method :viewgatepassEntry starts");
		JsonResponse<List<InventoryGatePassEntryModel>> resp = new JsonResponse<List<InventoryGatePassEntryModel>>();

		try {

			resp = restTemplate.getForObject(env.getGatepassUrl() + "getAllgatepassEntry", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<InventoryGatePassEntryModel> inventoryGatePassEntryModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<InventoryGatePassEntryModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (InventoryGatePassEntryModel a : inventoryGatePassEntryModel) {
			// a.setQuantitynew(a.getQuantity());
			if (a.getEntrydate() != null && a.getEntrydate() != "") {
				a.setEntrydate(DateFormatter.dateFormat(a.getEntrydate(), dateFormat));
			}

		}

		resp.setBody(inventoryGatePassEntryModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewgatepassEntry ends");
		return resp.getBody();
	}
	// view

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-entry-view")
	public @ResponseBody List<GatePassDetailsModel> viewGatePassIn(HttpSession session, @RequestParam String pageno) {

		logger.info("Method : viewGatePassIn starts");
		JsonResponse<List<GatePassDetailsModel>> resp = new JsonResponse<List<GatePassDetailsModel>>();

		String userid = "";
		String organization = "";
		String orgDivision = "";
		try {
			userid = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate
					.getForObject(
							env.getGatepassUrl() + "viewGatePassEntry?userId=" + userid + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&pageno=" + pageno,
							JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<GatePassDetailsModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GatePassDetailsModel>>() {
				});
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
		}

		if (gatePassDetailsModel != null)
			for (GatePassDetailsModel a : gatePassDetailsModel) {
				if (a.getEntrydate() != null && a.getEntrydate() != "") {
					a.setEntrydate(DateFormatter.dateFormat(a.getEntrydate(), dateFormat));
				}

			}

		logger.info("Method : viewGatePassIn ends");
		return gatePassDetailsModel;

	}

	// edit

	@GetMapping(value = { "gate-pass-entry-edit" })
	public @ResponseBody List<GatePassDetailsModel> editGatePassEntryData(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editGatePassEntryData starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<GatePassDetailsModel> productList = new ArrayList<GatePassDetailsModel>();
		List<GatePassDetailsModel> documentList = new ArrayList<GatePassDetailsModel>();
		if (id != null && id != "") {
			try {
				GatePassDetailsModel[] gatePassDetailsModel = restTemplate
						.getForObject(env.getGatepassUrl() + "editGatePassEntryData?id=" + id + "&organization="
								+ organization + "&orgDivision=" + orgDivision, GatePassDetailsModel[].class);

				productList = Arrays.asList(gatePassDetailsModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (GatePassDetailsModel m : gatePassDetailsModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getEntrydate() != null && m.getEntrydate() != "") {
						m.setEntrydate(DateFormatter.dateFormat(m.getEntrydate(), dateFormat));
					}

				}

				if (productList != null) {
					documentList = productList.get(0).getDocList();

					if (documentList != null) {
						for (GatePassDetailsModel m : documentList) {
							if (m.getDocView() != null && m.getDocView() != "") {

								String docPath = "/document/document/" + m.getDocView();

								m.setDociURL(docPath);
								// String[] extension = m.getFileName().split("\\.");

							} else {
								m.setDociURL(null);
							}
							/*
							 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
							 * "\" target=\"_blank\" >" + m.getAction() + "</a>");
							 */
							logger.info("m.setDociURL" + m.getDociURL());

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : editGatePassEntryData ends");
		System.err.println("DATA" + productList);
		return productList;
	}
	// gate out add

	@SuppressWarnings("unchecked")
	@PostMapping("gate-pass-exit-add")
	public @ResponseBody JsonResponse<Object> addGatepassExit(HttpSession session,
			@RequestBody List<GatePassDetailsModel> gatePassDetailsModel) {
		logger.info("Method : addGatepassExit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDiv = "";
		System.out.println("gatePassDetailsModel435=====" + gatePassDetailsModel);
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (GatePassDetailsModel m : gatePassDetailsModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDiv);
		}

		for (GatePassDetailsModel a : gatePassDetailsModel.get(0).getDocDetails()) {

			if (a.getIsEditDoc() == null && a.getIsEditDoc() == "") {
				a.setDocView(a.getDocView());
			} else {
				if (a.getDocView() != null && a.getDocView() != "") {
					// String[] extension = a.getFileName().split("\\.");
					String extension = a.getDocType();
					// int lastindex = extension.length - 1;
					/*
					 * try { byte[] bytes = Base64.getDecoder().decode(a.getIsEditDoc());
					 * logger.info("bytes" + a.getIsEditDoc()); String imageName =
					 * saveAllMultiImages(bytes, extension); a.setDocView(imageName);
					 * 
					 * } catch (Exception e) { e.printStackTrace();
					 * 
					 * }
					 */

					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, extension);
							a.setDocView(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}

				}
			}
		}
		/*
		 * MultipartFile inputFile = (MultipartFile)
		 * session.getAttribute("gateDocFile"); System.out.println("inputFile=====" +
		 * inputFile); byte[] bytes; String imageName = null; if (inputFile != null) {
		 * try { bytes = inputFile.getBytes(); String[] fileType =
		 * inputFile.getContentType().split("/"); imageName = saveAllMultiImages(bytes,
		 * fileType[1]); System.out.println("imageName====" + imageName);
		 * gatePassDetailsModel.get(0).setImage(imageName); } catch (IOException e1) {
		 * e1.printStackTrace(); } }
		 */
		System.out.println("gatePassDetailsModel=====" + gatePassDetailsModel);
		try {
			resp = restTemplate.postForObject(env.getGatepassUrl() + "rest-add-gatepass-exit", gatePassDetailsModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<GatePassDetailsModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<GatePassDetailsModel>>() {
					});

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
			session.removeAttribute("gateDocFile");
		}

		logger.info("Method : addGatepassExit ends");

		return resp;
	}

	// view for gate out

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-exit-view")
	public @ResponseBody List<GatePassDetailsModel> viewGatePassExit(HttpSession session, @RequestParam String pageno) {

		logger.info("Method : viewGatePassExit starts");
		JsonResponse<List<GatePassDetailsModel>> resp = new JsonResponse<List<GatePassDetailsModel>>();

		String userid = "";
		String organization = "";
		String orgDivision = "";
		try {
			userid = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "viewGatePassExit?userId=" + userid
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&pageno=" + pageno,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<GatePassDetailsModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GatePassDetailsModel>>() {
				});
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
		}

		if (gatePassDetailsModel != null)
			for (GatePassDetailsModel a : gatePassDetailsModel) {
				if (a.getExitDate() != null && a.getExitDate() != "") {
					a.setExitDate(DateFormatter.dateFormat(a.getExitDate(), dateFormat));
				}

			}

		logger.info("Method : viewGatePassExit ends");
		return gatePassDetailsModel;

	}

	// edit gate out

	@GetMapping(value = { "gate-pass-exit-edit" })
	public @ResponseBody List<GatePassDetailsModel> editGatePassExit(@RequestParam String id, HttpSession session) {
		logger.info("Method : editGatePassExit starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<GatePassDetailsModel> productList = new ArrayList<GatePassDetailsModel>();
		List<GatePassDetailsModel> documentList = new ArrayList<GatePassDetailsModel>();
		if (id != null && id != "") {
			try {
				GatePassDetailsModel[] gatePassDetailsModel = restTemplate.getForObject(env.getGatepassUrl()
						+ "editGatePassExit?id=" + id + "&organization=" + organization + "&orgDivision=" + orgDivision,
						GatePassDetailsModel[].class);

				productList = Arrays.asList(gatePassDetailsModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (GatePassDetailsModel m : gatePassDetailsModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getExitDate() != null && m.getExitDate() != "") {
						m.setExitDate(DateFormatter.dateFormat(m.getExitDate(), dateFormat));
					}

				}
				if (productList != null) {
					documentList = productList.get(0).getDocList();

					if (documentList != null) {
						for (GatePassDetailsModel m : documentList) {
							if (m.getDocView() != null && m.getDocView() != "") {

								String docPath = "/document/document/" + m.getDocView();

								m.setDociURL(docPath);
								// String[] extension = m.getFileName().split("\\.");

							} else {
								m.setDociURL(null);
							}
							/*
							 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
							 * "\" target=\"_blank\" >" + m.getAction() + "</a>");
							 */
							logger.info("m.setDociURL" + m.getDociURL());

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : editGatePassExit ends");
		System.err.println("DATA" + productList);
		return productList;
	}

	// item against po id

	@GetMapping(value = { "gate-pass-getVendorandItemDetails" })
	public @ResponseBody List<GatePassDetailsModel> getVendorandItemDetails(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getVendorandItemDetails starts");
		List<GatePassDetailsModel> productList = new ArrayList<GatePassDetailsModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		System.out.println("FF" + orgName);
		System.out.println("FF1" + orgDivision);
		if (id != null && id != "") {

			try {
				GatePassDetailsModel[] GatePassDetailsModel = restTemplate.getForObject(env.getGatepassUrl()
						+ "getVendorandItemDetails?id=" + id + "&org=" + orgName + "&orgDivision=" + orgDivision,
						GatePassDetailsModel[].class);
				productList = Arrays.asList(GatePassDetailsModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (GatePassDetailsModel m : GatePassDetailsModel) {
					count++;
					m.setSlNo(count);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getVendorandItemDetails ends");
		return productList;
	}
	// get item details for exit

	@GetMapping(value = { "gate-pass-getItemDetailsForExit" })
	public @ResponseBody List<GatePassDetailsModel> getItemDetailsForExit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getItemDetailsForExit starts");
		List<GatePassDetailsModel> productList = new ArrayList<GatePassDetailsModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		System.out.println("FF" + orgName);
		System.out.println("FF1" + orgDivision);
		if (id != null && id != "") {

			try {
				GatePassDetailsModel[] GatePassDetailsModel = restTemplate.getForObject(env.getGatepassUrl()
						+ "getItemDetailsForExit?id=" + id + "&org=" + orgName + "&orgDivision=" + orgDivision,
						GatePassDetailsModel[].class);
				productList = Arrays.asList(GatePassDetailsModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (GatePassDetailsModel m : GatePassDetailsModel) {
					count++;
					m.setSlNo(count);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getItemDetailsForExit ends");
		return productList;
	}

	// delete for gate-in
	@SuppressWarnings("unchecked")
	@PostMapping("gate-pass-entry-delete")
	public @ResponseBody JsonResponse<Object> deleteGatepassEntry(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteGatepassEntry function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getGatepassUrl() + "deleteGatepassEntry?id=" + id + "&org=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		res.getMessage();
		/*
		 * if (message != null && message != "") {
		 * 
		 * } else { res.setMessage("Success"); }
		 */
		logger.info("Method : deleteGatepassEntry function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	// delete for gate-out

	@SuppressWarnings("unchecked")
	@PostMapping("gate-pass-exit-delete")
	public @ResponseBody JsonResponse<Object> deleteGatepassExit(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteGatepassExit function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getGatepassUrl() + "deleteGatepassExit?id=" + id + "&org=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteGatepassExit function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	// approve for gate-in

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-entry-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveGatepassEntry(HttpSession session,
			@RequestParam String approveStatus, String getPassEntryId) {

		logger.info("Method : approveGatepassEntry starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate.getForObject(
					env.getGatepassUrl() + "approveGatepassEntry?approveStatus=" + approveStatus + "&getPassEntryId="
							+ getPassEntryId + "&org=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		System.out.println("response=====" + response);
		logger.info("Method : approveGatepassEntry ends");
		return response;
	}

	// approve for gate-out

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-exit-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveGatepassExit(HttpSession session,
			@RequestParam String approveStatus, String getPassExitId) {

		logger.info("Method : approveGatepassExit starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate.getForObject(
					env.getGatepassUrl() + "approveGatepassExit?approveStatus=" + approveStatus + "&getPassExitId="
							+ getPassExitId + "&org=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		System.out.println("response=====" + response);
		logger.info("Method : approveGatepassExit ends");
		return response;
	}
	/*
	 * Item auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "gate-pass-get-item-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemAutoSearchNewList(Model model,
			@RequestBody String searchValue, String type, HttpSession session) {
		logger.info("Method : getItemAutoSearchNewList starts");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(env.getGatepassUrl() + "getItemAutoSearchListforgate?id=" + searchValue
					+ "&type=" + type + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("RESPONSE@@" + res);
		logger.info("Method : getItemAutoSearchNewList ends");
		return res;
	}

	/*
	 * Item auto search for Fg
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "gate-pass-get-item-listForFg" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemAutoSearchNewListForFg(Model model,
			@RequestBody String searchValue, HttpSession session) {
		logger.info("Method : getItemAutoSearchNewListForFg starts");
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getGatepassUrl() + "getItemAutoSearchNewListForFg?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("RESPONSE@@" + res);
		logger.info("Method : getItemAutoSearchNewListForFg ends");
		return res;
	}

	// Autosearch
	@GetMapping("gate-pass-get-transport-list")
	public @ResponseBody Object gettransportAutoSearchList(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :gettransportAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "gettransportAutoSearchList?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

//  Autosearch Driver list
	@GetMapping("gate-pass-get-driver-list")
	public @ResponseBody Object getdriverAutoSearchList(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :getdriverAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "getdriverAutoSearchList?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Autosearch
	@GetMapping("gate-pass-get-vehicle-list")
	public @ResponseBody Object getvehicleAutoSearchList(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :getvehicleAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "getvehicleAutoSearchList?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Autosearch
	@GetMapping("gate-pass-get-depo-list")
	public @ResponseBody Object getdepoAutoSearchList(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :getdepoAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "getdepoAutoSearchList?id=" + searchValue + "&org="
					+ org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Autosearch
	@GetMapping("gate-pass-get-visit-list")
	public @ResponseBody Object getvisitList(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :getvisitList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "getvisitList?id=" + searchValue + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// AutoSearch Vendor Name

	@GetMapping("gate-pass-get-vendor-list")
	public @ResponseBody Object getVendorAutoSearchList(@RequestParam String type, HttpSession session) {

		logger.info("Method :getVendorAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "getVendorAutoSearchListPass?type=" + type + "&org="
					+ org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getVendorAutoSearchList Ends");
		return resp;
	}

	// Search Gate In.

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-entry-view-search")
	public @ResponseBody List<GatePassDetailsModel> viewGatePassInSearch(HttpSession session,
			@RequestParam String searchValue) {

		logger.info("Method : viewGatePassInSearch starts");
		JsonResponse<List<GatePassDetailsModel>> resp = new JsonResponse<List<GatePassDetailsModel>>();

		String userid = "";
		String organization = "";
		String orgDivision = "";
		try {
			userid = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "rest-viewGatePassEntrySearch?userId=" + userid + "&organization="
							+ organization + "&orgDivision=" + orgDivision + "&searchValue=" + searchValue,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<GatePassDetailsModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GatePassDetailsModel>>() {
				});
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
		}

		if (gatePassDetailsModel != null)
			for (GatePassDetailsModel a : gatePassDetailsModel) {
				if (a.getEntrydate() != null && a.getEntrydate() != "") {
					a.setEntrydate(DateFormatter.dateFormat(a.getEntrydate(), dateFormat));
				}

			}

		logger.info("Method : viewGatePassInSearch ends");
		return gatePassDetailsModel;

	}

	// Gate Out Search.

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-exit-view-search")
	public @ResponseBody List<GatePassDetailsModel> viewGatePassExitSearch(HttpSession session,
			@RequestParam String searchValue) {

		logger.info("Method : viewGatePassExitSearch starts");
		JsonResponse<List<GatePassDetailsModel>> resp = new JsonResponse<List<GatePassDetailsModel>>();

		String userid = "";
		String organization = "";
		String orgDivision = "";
		try {
			userid = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "rest-viewGatePassExitSearch?userId=" + userid + "&organization="
							+ organization + "&orgDivision=" + orgDivision + "&searchValue=" + searchValue,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<GatePassDetailsModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GatePassDetailsModel>>() {
				});
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
		}

		if (gatePassDetailsModel != null)
			for (GatePassDetailsModel a : gatePassDetailsModel) {
				if (a.getExitDate() != null && a.getExitDate() != "") {
					a.setExitDate(DateFormatter.dateFormat(a.getExitDate(), dateFormat));
				}

			}

		logger.info("Method : viewGatePassExitSearch ends");
		return gatePassDetailsModel;

	}

// get inspection checklist
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "gate-pass-getInspectionChecklist" })
	public @ResponseBody Object getInspectionChecklist(@RequestParam String type, HttpSession session) {
		logger.info("Method :getInspectionChecklist starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-getInspectionChecklist?type=" + type
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("view===" + resp);
		logger.info("Method :getInspectionChecklist ends");
		return resp;
	}

	// checklist pdf view
	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-entry-checklist-pdf-downloads")
	public void getChecklistEntry(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1) {

		logger.info("Method : getChecklistEntry starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String id = (new String(encodeByte1));
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-entry-checklist-pdf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// parseJsonString(resp.getBody().toString());
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> dataa = mapper.readValue(resp.getBody().toString(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			for (Map<String, Object> datad : dataa) {
				data.put("material", datad.get("material"));
				data.put("quantity", datad.get("quantity"));
				data.put("challanNo", datad.get("challanNo"));
				data.put("vehicleNo", datad.get("vehicleNo"));
				data.put("challanDate", datad.get("challanDate"));
				data.put("receivedDate", datad.get("receivedDate"));
				data.put("driverName", datad.get("driverName"));
				data.put("createdBy", datad.get("createdBy"));
				// Extract inspectList field
				Map<String, Object> inspectList = mapper.readValue(datad.get("inspectList").toString(),
						new TypeReference<Map<String, Object>>() {
						});
				List<Map<String, Object>> checklist = (List<Map<String, Object>>) inspectList.get("checklist");
				data.put("checklist", checklist);
			}

			System.out.println("data>>>>>>>" + data.get("material"));
			String materials = (String) data.get("material");
			// model.addAttribute("material", materials);

			int itemCount = materials.split(",").length;
			int baseHeight = 80;
			int additionalHeight = (itemCount / 2) * 25;
			int totalHeight = baseHeight + additionalHeight;

			data.put("tdHeight", totalHeight);

		} catch (IOException e2) {
			e2.printStackTrace();
		}
		String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
		URL getUrl = null;
		try {
			getUrl = new URL(logo);
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		data.put("orgName", orgDivision);
		System.err.println("data getChecklistEntry====" + data);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=ShiftRegister.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("gatepass/recieptChecklistPdf.html", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		logger.info("Method : getChecklistEntry ends");
	}

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-visit-checklist-pdf-downloads")
	public void getChecklistVisit(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1) {
		logger.info("Method : getChecklistVisit starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String id = (new String(encodeByte1));
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-visit-checklist-pdf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> dataa = mapper.readValue(resp.getBody().toString(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			for (Map<String, Object> datad : dataa) {
				data.put("visitorName", datad.get("visitorName"));
				data.put("date", datad.get("date"));
				data.put("createdBy", datad.get("createdBy"));
				// Extract inspectList field
				Map<String, Object> inspectList = mapper.readValue(datad.get("inspectList").toString(),
						new TypeReference<Map<String, Object>>() {
						});
				List<Map<String, Object>> checklist = (List<Map<String, Object>>) inspectList.get("checklist");
				data.put("checklist", checklist);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
		URL getUrl = null;
		try {
			getUrl = new URL(logo);
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		data.put("orgName", orgDivision);
		System.err.println("data visit====" + data);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=ShiftRegister.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("gatepass/visitChecklistPdf.html", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		logger.info("Method : getChecklistVisit ends");
	}

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-exit-checklist-pdf-downloads")
	public void getChecklistExit(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1) {
		logger.info("Method : getChecklistExit starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String id = (new String(encodeByte1));
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-exit-checklist-pdf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> dataa = mapper.readValue(resp.getBody().toString(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			for (Map<String, Object> datad : dataa) {
				data.put("destination", datad.get("destination"));
				data.put("dispatchDate", datad.get("dispatchDate"));
				data.put("vehicleNo", datad.get("vehicleNo"));
				data.put("driverName", datad.get("driverName"));
				data.put("createdBy", datad.get("createdBy"));
				// Extract inspectList field
				Map<String, Object> inspectList = mapper.readValue(datad.get("inspectList").toString(),
						new TypeReference<Map<String, Object>>() {
						});
				List<Map<String, Object>> checklist = (List<Map<String, Object>>) inspectList.get("checklist");
				data.put("checklist", checklist);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
		URL getUrl = null;
		try {
			getUrl = new URL(logo);
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		data.put("orgName", orgDivision);
		System.err.println("data exit====" + data);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=ShiftRegister.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("gatepass/dispatchChecklistPdf.html", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		logger.info("Method : getChecklistExit ends");
	}

	/*
	 * view for shipment
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-shipment-through-ajax")
	public @ResponseBody Object viewshipmentData(HttpSession session) {
		logger.info("Method :viewshipmentData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "viewshipmentData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewshipmentData ends");
		return resp;

	}

	@GetMapping(value = { "gate-pass-shipment-received-data" })
	public @ResponseBody List<ShipmentDetailsVendorModel> getShippmentReceiveData(@RequestParam String shippingId,
			HttpSession session) {
		logger.info("Method : editShipmentData starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<ShipmentDetailsVendorModel> productList = new ArrayList<ShipmentDetailsVendorModel>();
		if (shippingId != null && shippingId != "") {
			try {
				ShipmentDetailsVendorModel[] gatePassDetailsModel = restTemplate
						.getForObject(
								env.getPurchaseUrl() + "editShipmentData?shippingId=" + shippingId + "&organization="
										+ organization + "&orgDivision=" + orgDivision,
								ShipmentDetailsVendorModel[].class);

				productList = Arrays.asList(gatePassDetailsModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (ShipmentDetailsVendorModel m : gatePassDetailsModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getDlDate() != null && m.getDlDate() != "") {
						m.setDlDate(DateFormatter.dateFormat(m.getDlDate(), dateFormat));
					}

					if (m.getCreatedOn() != null && m.getCreatedOn() != "") {
						m.setCreatedOn(DateFormatter.dateFormat(m.getCreatedOn(), dateFormat));
					}

					if (m.getPoDate() != null && m.getPoDate() != "") {
						m.setPoDate(DateFormatter.dateFormat(m.getPoDate(), dateFormat));
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getShippmentReceiveData ends");
		System.err.println("DATA" + productList);
		return productList;
	}

	/*
	 * Get Document Name
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-get-docName")
	public @ResponseBody Object getDocNameData(HttpSession session) {
		logger.info("Method :getDocNameData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "rest-getDocNameData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getDocNameData ends");
		return resp;

	}

}
