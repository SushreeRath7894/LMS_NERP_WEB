package nirmalya.aathithya.webmodule.purchase.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryPoModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "purchase/")
public class InventoryVendorPoController {
	Logger logger = LoggerFactory.getLogger(InventoryVendorPoController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("vendor-po")
	public String generatePo(Model model, HttpSession session) {

		logger.info("Method : generatePo starts");

		/**
		 * get DropDown value for Requisition Type
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-requisition-type",
					DropDownModel[].class);
			List<DropDownModel> requisitionTypeList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionTypeList", requisitionTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/* for requisition priority */
		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-requisition-priority", DropDownModel[].class);
			List<DropDownModel> requisitionPrioList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionPrioList", requisitionPrioList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for cost center
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-cost-center",
					DropDownModel[].class);
			List<DropDownModel> costCenterList = Arrays.asList(dropDownModel);
			model.addAttribute("costCenterList", costCenterList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for location
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-location",
					DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(dropDownModel);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for unit on measurement
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-uom",
					DropDownModel[].class);
			List<DropDownModel> uomList = Arrays.asList(dropDownModel);
			model.addAttribute("uomList", uomList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/**
		 * payment term master
		 */
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getPaymentterm",
					DropDownModel[].class);
			List<DropDownModel> PaytermList = Arrays.asList(dropDownModel);
			model.addAttribute("PaytermList", PaytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * legal term master
		 */
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getCarrierList",
					DropDownModel[].class);
			List<DropDownModel> CarrierLists = Arrays.asList(dropDownModel);
			model.addAttribute("CarrierLists", CarrierLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
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
		/**
		 * vendor master
		 */
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-vendor-master",
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(dropDownModel);
			model.addAttribute("vendorList", vendorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : generatePo ends");
		return "purchase/vendor-po";

	}

	/*
	 * view po through ajax
	 * 
	 * 
	 */
	@GetMapping(value = { "vendor-po-get-po-list" })
	public @ResponseBody List<InventoryPoModel> getPoListThrowAjax(HttpSession session) {
		logger.info("Method : getPoListThrowAjax starts");
		List<InventoryPoModel> inventoryPoModelList = new ArrayList<InventoryPoModel>();
		String userId = "";
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("VENDOR_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {

			InventoryPoModel[] inventoryPoModel = restTemplate.getForObject(env.getInventoryUrl()
					+ "get-vendor-po-view-list?userId=" + userId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					InventoryPoModel[].class);
			inventoryPoModelList = Arrays.asList(inventoryPoModel);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getPoListThrowAjax ends");
		return inventoryPoModelList;
	}

	/*
	 * for copy
	 * 
	 * 
	 */
	@GetMapping(value = { "vendor-po-item-trough-ajax" })
	public @ResponseBody InventoryPoModel viewPoEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPoEdit starts");
		InventoryPoModel productList = new InventoryPoModel();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		if (id != null && id != "") {
			try {
				productList = restTemplate.getForObject(
						env.getInventoryUrl() + "get-po-edit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						InventoryPoModel.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Method : viewPoEdit ends");
		return productList;
	}

	@GetMapping(value = { "vendor-po-activity-log" })
	public @ResponseBody List<ActivitylogModel> getActivityLog(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewStockThroughAjax starts");
		List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {

			ActivitylogModel[] activityLog = restTemplate.getForObject(
					env.getInventoryUrl() + "get-activity-log?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					ActivitylogModel[].class);
			activityLogList = Arrays.asList(activityLog);

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ActivitylogModel m : activityLogList) {

			if (m.getOperationOn() != null && m.getOperationOn() != "") {
				m.setOperationOn(DateFormatter.dateFormat(m.getOperationOn(), dateFormat));
			}
		}
		logger.info("Method : viewStockThroughAjax ends");
		return activityLogList;
	}

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-po-through-ajax")
	public @ResponseBody List<PurchaseOrderModel> viewPurchaseOrderForVendor(HttpSession session) {

		logger.info("Method :viewPurchaseOrderForVendor starts");
		JsonResponse<List<PurchaseOrderModel>> resp = new JsonResponse<List<PurchaseOrderModel>>();
		String userId = "";
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-viewPurchaseOrderForVendor?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<PurchaseOrderModel> purchaseOrderModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<PurchaseOrderModel>>() {
				});

		resp.setBody(purchaseOrderModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewPurchaseOrderForVendor ends");

		return resp.getBody();
	}
	/*
	 * edit Po
	 */

	@GetMapping(value = { "vendor-po-edit-new" })
	public @ResponseBody List<PurchaseOrderModel> viewPoEditForVendor(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPoEditForVendor starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				PurchaseOrderModel[] purchaseOrderModel = restTemplate.getForObject(
						env.getPurchaseUrl() + "viewPoEditForVendor?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseOrderModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseOrderModel m : purchaseOrderModel) {
					// m.setQuantitynew(m.getQuantity());
					System.err.println("DATE" + m.getGrnId());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					/*
					 * if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
					 * m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));
					 * 
					 * } if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
					 * 
					 * }
					 */
					if (m.getGrnDate() != null && m.getGrnDate() != "") {
						m.setGrnDate(DateFormatter.dateFormat(m.getGrnDate(), dateFormat));

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
		logger.info("Method : viewPoEditForVendor ends");
		return productList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-po-get-address")
	public @ResponseBody JsonResponse<VendorNewModel> getVendorAddress(@RequestParam String id, HttpSession session) {

		logger.info("Method : getCustomerAddress starts");

		JsonResponse<VendorNewModel> jsonResponse = new JsonResponse<VendorNewModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorAddressAddressById?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		VendorNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<VendorNewModel>() {
		});

		jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : getVendorAddress ends");

		return jsonResponse;
	}

	/*
	 * Item auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "vendor-po-order-get-item-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchNewListForPO(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchNewListForPO starts");
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "getItemQuotationAutoSearchNewListForPO?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getItemQuotationAutoSearchNewListForPO ends");
		return res;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "vendor-po-order-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getPoInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getPoInsertedId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getPoInsertedId ends");

		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "vendor-po-getReferenceList" })
	public @ResponseBody JsonResponse<Object> getReferenceList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getReferenceList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "getReferenceList?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getReferenceList ends");
		return res;
	}

	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-po-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approvePorderForVendor(HttpSession session,
			@RequestParam String approveStatus, String poId) {

		logger.info("Method : approvePorderForVendor starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(
					env.getPurchaseUrl() + "approvePorderForVendor?approveStatus=" + approveStatus + "&poId=" + poId
							+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId,
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

		logger.info("Method : approvePorderForVendor ends");
		return response;
	}

	// reject

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-po-reject-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> rejectPorderForVendor(HttpSession session,
			@RequestParam String rejectStatus, String poId) {

		logger.info("Method : rejectPorderForVendor starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(
					env.getPurchaseUrl() + "rejectPorderForVendor?rejectStatus=" + rejectStatus + "&poId=" + poId
							+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId,
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

		logger.info("Method : rejectPorderForVendor ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-po-get-cust-address")
	public @ResponseBody JsonResponse<VendorNewModel> getcustomerAddressDtls(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : getVendorAddress starts" + id);

		JsonResponse<VendorNewModel> jsonResponse = new JsonResponse<VendorNewModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "getcustomerAddressDtlsById?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		VendorNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<VendorNewModel>() {
		});

		jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : getcustomerAddressDtls ends");

		return jsonResponse;
	}
	@GetMapping(value = { "vendor-po-get-deliverychallan-details" })
	public @ResponseBody List<PurchaseOrderModel> getdeliverychallandata(@RequestParam String id, String sku, HttpSession session) {
		logger.info("Method : getdeliverychallandata starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			logger.info("IDD" + id);
			try {
				PurchaseOrderModel[] purchaseOrderModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "getdeliverychallandata?id=" + id + "&sku=" + sku + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseOrderModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;
				
				for (PurchaseOrderModel m : purchaseOrderModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

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
		logger.info("Method : getdeliverychallandata ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}	
	
}
