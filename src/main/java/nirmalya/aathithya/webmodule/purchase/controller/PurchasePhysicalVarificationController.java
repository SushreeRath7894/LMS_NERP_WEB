package nirmalya.aathithya.webmodule.purchase.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PhysicalVarificationModel;

@Controller
@RequestMapping(value = "purchase/")
public class PurchasePhysicalVarificationController {
	Logger logger = LoggerFactory.getLogger(PurchasePhysicalVarificationController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	ManageGrnReturnController manageGrnReturnController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("physical-verification")
	public String viewPhysical(Model model, HttpSession session) {

		logger.info("Method : viewPhysical starts");
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		// model.addAttribute("pageName", "Physical Verification Page Coming Soon");
		logger.info("Method : viewPhysical ends");
		return "purchase/purchasePhysicalVarification";

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "physical-verification-get-varificationId" })
	public @ResponseBody JsonResponse<Object> getvarificationId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getvarificationId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getvarificationId ends");

		return res;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("physical-verification-add")
	public @ResponseBody JsonResponse<Object> addVerification(HttpSession session,
			@RequestBody List<PhysicalVarificationModel> PhysicalVarificationModel) {
		logger.info("Method : addVerification starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<PhysicalVarificationModel> documentList = new ArrayList<PhysicalVarificationModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		for (PhysicalVarificationModel m : PhysicalVarificationModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}

		/*
		 * for (InventoryVendorDocumentModel a :
		 * PhysicalVarificationModel.get(0).getDocumentList()) {
		 * 
		 * if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
		 * a.setFileName(a.getImageNameEdit()); } else { if (a.getFileName() != null &&
		 * a.getFileName() != "") { String[] extension = a.getFileName().split("\\.");
		 * int lastindex = extension.length - 1; for (String s1 : a.getDocumentFile()) {
		 * try { byte[] bytes = Base64.getDecoder().decode(s1); logger.info("bytes" +
		 * s1); String imageName = saveAllMultiImages(bytes, extension[lastindex]);
		 * a.setFileName(imageName);
		 * 
		 * } catch (Exception e) { e.printStackTrace();
		 * 
		 * } } } } }
		 */
		
		for (InventoryVendorDocumentModel a : PhysicalVarificationModel.get(0).getDocumentList()) {

			if (a.getIsEditDoc() == null && a.getIsEditDoc() == "") {
				a.setDocView(a.getDocView());
			} else {
				if (a.getDocView() != null && a.getDocView() != "") {
					
					String extension = a.getDocType();
					
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

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addVerification", PhysicalVarificationModel,
					JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addVerification ends");

		return resp;
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

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("physical-verification-View-through-ajax")
	public @ResponseBody List<PhysicalVarificationModel> viewVerification(HttpSession session) {

		logger.info("Method :viewVerification startsssssssssssssssssssssss");
		JsonResponse<List<PhysicalVarificationModel>> resp = new JsonResponse<List<PhysicalVarificationModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-viewVerification?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<PhysicalVarificationModel> PhysicalVarificationModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<PhysicalVarificationModel>>() {
				});

		for (PhysicalVarificationModel a : PhysicalVarificationModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}
		}

		resp.setBody(PhysicalVarificationModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("resp>>>>-----" + resp);
		logger.info("Method :viewVerification ends");

		return resp.getBody();
	}
	/*
	 * edit
	 */

	@GetMapping(value = { "physical-verification-edit-new" })
	public @ResponseBody List<PhysicalVarificationModel> viewPhysicalVarifyEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewPhysicalVarifyEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		List<PhysicalVarificationModel> productList = new ArrayList<PhysicalVarificationModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				PhysicalVarificationModel[] physicalVarificationModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewPhysicalVarifyEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PhysicalVarificationModel[].class);

				productList = Arrays.asList(physicalVarificationModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PhysicalVarificationModel m : physicalVarificationModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

					}
					if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
						m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

					}
					if (m.getReferenceDate() != null && m.getReferenceDate() != "") {
						m.setReferenceDate(DateFormatter.dateFormat(m.getReferenceDate(), dateFormat));

					}
					if (m.getExptdeliveryDate() != null && m.getExptdeliveryDate() != "") {
						m.setExptdeliveryDate(DateFormatter.dateFormat(m.getExptdeliveryDate(), dateFormat));

					}

				}
				/*
				 * if (productList != null) { documentList =
				 * productList.get(0).getDocumentList(); if (documentList != null) { for
				 * (InventoryVendorDocumentModel m : documentList) { if (m.getFileName() != null
				 * && m.getFileName() != "") {
				 * 
				 * String[] extension = m.getFileName().split("\\."); if (extension.length == 2)
				 * { if (extension[1].equals("xls") || extension[1].equals("xlsx")) {
				 * 
				 * String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " +
				 * m.getFileName() + "></i> ";
				 * 
				 * m.setAction(docPath); } if (extension[1].equals("pdf")) { String docPath =
				 * " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName() +
				 * " ;></i> ";
				 * 
				 * m.setAction(docPath); } if (extension[1].equals("doc") ||
				 * extension[1].equals("dox") || extension[1].equals("docx")) { String docPath =
				 * " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title=" +
				 * m.getFileName() + "></i> "; m.setAction(docPath); } if
				 * (extension[1].equals("png") || extension[1].equals("jpg") ||
				 * extension[1].equals("jpeg")) { String docPath =
				 * " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title=" +
				 * m.getFileName() + "></i>  "; m.setAction(docPath); } } else {
				 * m.setAction("N/A"); } } else { m.setAction("N/A"); }
				 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
				 * "\" target=\"_blank\" >" + m.getAction() + "</a>"); m.
				 * setFileNameLink("<a style=\"text-decoration: none;\" href=\"/document/document/"
				 * + m.getFileName() + "\" target=\"_blank\" >" + m.getFileName() + "</a>");
				 * logger.info("m.setAction" + m);
				 * 
				 * } } }
				 */
				
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getDocView() != null && m.getDocView() != "") {
								
								String docPath = "/document/document/" + m.getDocView();

								m.setDociURL(docPath);
								
								// Set Doc Type.
								String[] extension = m.getDocView().split("\\.");
								m.setDocType(extension[1]);
								
							} else {
								m.setDociURL(null);
								m.setDocType(null);
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

		logger.info("Method : viewPhysicalVarifyEdit ends");

		return productList;
	}

	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("physical-verification-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveVarification(HttpSession session,
			@RequestParam String approveStatus, String varificationId, String invoiceId) {

		logger.info("Method : approveVarification starts");
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
			response = restTemplate.getForObject(env.getPurchaseUrl() + "approveVarification?approveStatus="
					+ approveStatus + "&varificationId=" + varificationId + "&invoiceId=" + invoiceId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		logger.info("response=====" + response);
		logger.info("Method : approveVarification ends");
		return response;
	}

	// delete

	@SuppressWarnings("unchecked")
	@PostMapping("physical-verification-delete")
	public @ResponseBody JsonResponse<Object> deleteVarificationDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteVarificationDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteVarificationDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteVarificationDetails function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}

	/*
	 * edit data for grn return
	 */

	@GetMapping(value = { "physical-verification-edit-grn return" })
	public @ResponseBody List<PhysicalVarificationModel> viewPhysicalVarifyEditForgrnReturn(@RequestParam String id,
			String hsnCode, HttpSession session) {

		logger.info("Method : viewPhysicalVarifyEditForgrnReturn starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		List<PhysicalVarificationModel> productList = new ArrayList<PhysicalVarificationModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {

				PhysicalVarificationModel[] physicalVarificationModel = restTemplate
						.getForObject(
								env.getPurchaseUrl() + "viewPhysicalVarifyEditForgrnReturn?id=" + id + "&hsnCode="
										+ hsnCode + "&org=" + orgName + "&orgDiv=" + orgDivision,
								PhysicalVarificationModel[].class);

				productList = Arrays.asList(physicalVarificationModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PhysicalVarificationModel m : physicalVarificationModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

					}
					if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
						m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

					}
					if (m.getReferenceDate() != null && m.getReferenceDate() != "") {
						m.setReferenceDate(DateFormatter.dateFormat(m.getReferenceDate(), dateFormat));

					}
					if (m.getExptdeliveryDate() != null && m.getExptdeliveryDate() != "") {
						m.setExptdeliveryDate(DateFormatter.dateFormat(m.getExptdeliveryDate(), dateFormat));

					}

				}
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
												+ m.getFileName() + "></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("pdf")) {
										String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
												+ m.getFileName() + " ;></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("doc") || extension[1].equals("dox")
											|| extension[1].equals("docx")) {
										String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
												+ m.getFileName() + "></i> ";
										m.setAction(docPath);
									}
									if (extension[1].equals("png") || extension[1].equals("jpg")
											|| extension[1].equals("jpeg")) {
										String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
												+ m.getFileName() + "></i>  ";
										m.setAction(docPath);
									}
								} else {
									m.setAction("N/A");
								}
							} else {
								m.setAction("N/A");
							}
							m.setAction("<a href=\"/document/document/" + m.getFileName() + "\" target=\"_blank\" >"
									+ m.getAction() + "</a>");
							logger.info("m.setAction" + m);

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Method : viewPhysicalVarifyEditForgrnReturn ends");

		return productList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "physical-verification-get-grnid" })
	public @ResponseBody JsonResponse<PhysicalVarificationModel> getGrnId(Model model, @RequestBody String searchValue,
			HttpSession session, BindingResult result) {
		logger.info("Method : getGrnId starts");
		String org = "";
		String orgDiv = "";
		String type = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<PhysicalVarificationModel> res = new JsonResponse<PhysicalVarificationModel>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "rest-getGrnId?id=" + searchValue + "&type=" + type
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
		logger.info("Method : getGrnId ends");
		return res;
	}

}
