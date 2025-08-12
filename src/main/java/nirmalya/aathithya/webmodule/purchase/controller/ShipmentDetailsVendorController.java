package nirmalya.aathithya.webmodule.purchase.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
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
@RequestMapping(value = "purchase/")
public class ShipmentDetailsVendorController {

	Logger logger = LoggerFactory.getLogger(ShipmentDetailsVendorController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	private static final String USER_Id = "USER_ID";

	@GetMapping("/shipment-details")
	public String shipmentDetails(Model model, HttpSession session) {
		logger.info("Method : shipmentDetails add starts");

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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getSalesUrl() + "get-carrier-list",
					DropDownModel[].class);
			List<DropDownModel> carrierList = Arrays.asList(dropDownModel);
			model.addAttribute("carrierList", carrierList);
		} catch (RestClientException e) {
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
		logger.info("Method : shipmentDetails ends");
		return "purchase/shipment-details";
	}

	// view

	@SuppressWarnings("unchecked")
	@GetMapping("shipment-details-view")
	public @ResponseBody List<ShipmentDetailsVendorModel> viewShipmentDetails(HttpSession session,
			@RequestParam String pageno) {

		logger.info("Method : viewShipmentDetails starts");
		JsonResponse<List<ShipmentDetailsVendorModel>> resp = new JsonResponse<List<ShipmentDetailsVendorModel>>();

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
							env.getPurchaseUrl() + "viewShipmentDetails?userId=" + userid + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&pageno=" + pageno,
							JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<ShipmentDetailsVendorModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ShipmentDetailsVendorModel>>() {
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
			for (ShipmentDetailsVendorModel a : gatePassDetailsModel) {
				if (a.getDlDate() != null && a.getDlDate() != "") {
					a.setDlDate(DateFormatter.dateFormat(a.getDlDate(), dateFormat));
				}

			}

		logger.info("Method : viewShipmentDetails ends");
		return gatePassDetailsModel;

	}

	@GetMapping("shipment-details-get-customer-list")
	public @ResponseBody Object getCustomerAutoSearchList(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :getCustomerAutoSearchList starts");
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
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "getCustomerAutoSearchList?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

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

	// AutoSearch Vendor Name

	@GetMapping("gate-pass-get-vendor-list")
	public @ResponseBody Object getVendorAutoSearchList(@RequestParam  String type,
			HttpSession session) {

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
			resp = restTemplate.getForObject(env.getGatepassUrl() + "getVendorAutoSearchListPass?type=" + type + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getVendorAutoSearchList Ends");
		return resp;
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

	@SuppressWarnings("unchecked")
	@PostMapping("shipment-details-add")
	public @ResponseBody JsonResponse<Object> addShipmentDetails(HttpSession session,
			@RequestBody List<ShipmentDetailsVendorModel> gatePassDetailsModel) {
		logger.info("Method : addGatepassEntry starts");
		logger.info("addd gate pass entry" + gatePassDetailsModel.get(0).getCustId());
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
		for (ShipmentDetailsVendorModel m : gatePassDetailsModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDiv);
		}
		MultipartFile inputFile = (MultipartFile) session.getAttribute("gateDocFile");
		System.out.println("inputFile=====" + inputFile);
		byte[] bytes;
		String imageName = null;
		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllMultiImages(bytes, fileType[1]);
				System.out.println("imageName====" + imageName);
				gatePassDetailsModel.get(0).setImage(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("gatePassDetailsModel=====" + gatePassDetailsModel);
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addShipmentDetails", gatePassDetailsModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ShipmentDetailsVendorModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<ShipmentDetailsVendorModel>>() {
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

		logger.info("Method : addShipmentDetails ends");

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

	@PostMapping("/shipment-doc-upload-file")
	public @ResponseBody JsonResponse<Object> uploadDocFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : shipment uploadDocFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("gateDocFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : shipment uploadDocFile controller ' ends");
		return response;
	}

	@GetMapping(value = { "shipment-details-edit" })
	public @ResponseBody List<ShipmentDetailsVendorModel> editShipmentData(@RequestParam String shippingId,
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
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (shippingId != null && shippingId != "") {
			try {
				ShipmentDetailsVendorModel[] gatePassDetailsModel = restTemplate
						.getForObject(env.getPurchaseUrl() + "editShipmentData?shippingId=" + shippingId + "&organization="
								+ organization + "&orgDivision=" + orgDivision, ShipmentDetailsVendorModel[].class);

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
		logger.info("Method : editShipmentData ends");
		System.err.println("DATA" + productList);
		return productList;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("shipment-delete")
	public @ResponseBody JsonResponse<Object> deleteShipment(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteShipment function starts");

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
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteShipment?id=" + id + "&org=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		res.getMessage();
	
		logger.info("Method : deleteShipment function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}
	// approve for gate-in

		@SuppressWarnings("unchecked")
		@GetMapping("shipment-details-approve-th-ajax")
		public @ResponseBody JsonResponse<DropDownModel> approveshipment(HttpSession session,
				@RequestParam String approveStatus, String shippingId) {

			logger.info("Method : approveshipment starts");
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
						env.getPurchaseUrl() + "approveshipment?approveStatus=" + approveStatus + "&shippingId="
								+ shippingId + "&org=" + orgName + "&orgDivision=" + orgDivision,
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
			logger.info("Method : approveshipment ends");
			return response;
		}
}
