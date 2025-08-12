package nirmalya.aathithya.webmodule.procurment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.pagination.DataTableRequest;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.procurment.model.InventoryActionInvoiceModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRequisitionModel;

@Controller
@RequestMapping(value = "inventory/")

public class VendorEchallanController {
	Logger logger = LoggerFactory.getLogger(VendorEchallanController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("vendor-echallan")
	public String getEchallanViewPage(Model model, HttpSession session) {

		logger.info("Method : getEchallanViewPage starts");
		
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-action-vendor-list",
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(dropDownModel);
			model.addAttribute("vendorList", vendorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-payment-status",
					DropDownModel[].class);
			List<DropDownModel> paymentStatusList = Arrays.asList(dropDownModel);
			model.addAttribute("paymentStatusList", paymentStatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for Requisition Type
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
		 * get DropDown value for Requisition Type
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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-Payment-term",
					DropDownModel[].class);
			List<DropDownModel> paytermList = Arrays.asList(dropDownModel);
			model.addAttribute("paytermList", paytermList);
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
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);

		logger.info("Method : getEchallanViewPage ends");
		return "procurement/vendor-echallan";

	}
	
	@GetMapping(value = { "vendor-echallan-get-invoice-list" })
	public @ResponseBody List<InventoryActionInvoiceModel> getChallanListThrowAjax(HttpSession session) {
		logger.info("Method : getPoListThrowAjax starts");
		List<InventoryActionInvoiceModel> inventoryPoModelList = new ArrayList<InventoryActionInvoiceModel>();
		String dateFormat = (String) session.getAttribute("DATEFORMAT");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String vendorId = (String) session.getAttribute("VENDOR_ID");
			InventoryActionInvoiceModel[] inventoryPoModel = restTemplate.getForObject(
					env.getInventoryUrl() + "get-vendor-challan-view-list?vendorId=" + vendorId +"&org="+orgName +"&orgDiv=" + orgDivision,
					InventoryActionInvoiceModel[].class);
			inventoryPoModelList = Arrays.asList(inventoryPoModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (InventoryActionInvoiceModel a : inventoryPoModelList) {
			if (a.getActiveDate() != null && a.getActiveDate() != "") {
				a.setActiveDate(DateFormatter.dateFormat(a.getActiveDate(), dateFormat));
			}
			if (a.getInvDate() != null && a.getInvDate() != "") {
				a.setInvDate(DateFormatter.dateFormat(a.getInvDate(), dateFormat));
			}
			if (a.getCompleteDate() != null && a.getCompleteDate() != "") {
				a.setCompleteDate(DateFormatter.dateFormat(a.getCompleteDate(), dateFormat));
			}
			if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
				a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
			}
			if (a.getDueDate() != null && a.getDueDate() != "") {
				a.setDueDate(DateFormatter.dateFormat(a.getDueDate(), dateFormat));
			}
			if (a.getOnholdDate() != null && a.getOnholdDate() != "") {
				a.setOnholdDate(DateFormatter.dateFormat(a.getOnholdDate(), dateFormat));
			}
			if (a.getPayemtDueDate() != null && a.getPayemtDueDate() != "") {
				a.setPayemtDueDate(DateFormatter.dateFormat(a.getPayemtDueDate(), dateFormat));
			}

		}
		logger.info("Method : getChallanListThrowAjax ends"+inventoryPoModelList);
		return inventoryPoModelList;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/vendor-challan-get-product-list" })
	public @ResponseBody JsonResponse<InventoryRequisitionModel> getItemAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemAutoSearchList starts");

		JsonResponse<InventoryRequisitionModel> res = new JsonResponse<InventoryRequisitionModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getProductListByAutoSearch?id=" + searchValue,
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

		logger.info("Method : getItemAutoSearchList ends");
		return res;
	}
	
	/*
	 * @GetMapping(value = { "vendor-echallan-edit-trough-ajax" })
	 * public @ResponseBody InventoryActionInvoiceModel viewPoEdit(@RequestParam
	 * String id, HttpSession session) { logger.info("Method : viewPoEdit starts");
	 * InventoryActionInvoiceModel productList = new InventoryActionInvoiceModel();
	 * List<InventoryVendorDocumentModel> documentList = new
	 * ArrayList<InventoryVendorDocumentModel>(); String dateFormat = (String)
	 * session.getAttribute("DATEFORMAT"); String orgName = ""; String orgDivision =
	 * ""; try {
	 * 
	 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { e.printStackTrace(); } if (id != null && id != "") { try { productList =
	 * restTemplate.getForObject(env.getInventoryUrl() + "get-challan-edit?id=" + id
	 * +"&org="+orgName +"&orgDiv=" + orgDivision,
	 * InventoryActionInvoiceModel.class);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } if
	 * (productList.getActiveDate() != null && productList.getActiveDate() != "") {
	 * productList.setActiveDate(DateFormatter.dateFormat(productList.getActiveDate(
	 * ), dateFormat)); } if (productList.getInvDate() != null &&
	 * productList.getInvDate() != "") {
	 * productList.setInvDate(DateFormatter.dateFormat(productList.getInvDate(),
	 * dateFormat)); } if (productList.getCompleteDate() != null &&
	 * productList.getCompleteDate() != "") {
	 * productList.setCompleteDate(DateFormatter.dateFormat(productList.
	 * getCompleteDate(), dateFormat)); } if (productList.getCreatedOn() != null &&
	 * productList.getCreatedOn() != "") {
	 * productList.setCreatedOn(DateFormatter.dateFormat(productList.getCreatedOn(),
	 * dateFormat)); } if (productList.getDueDate() != null &&
	 * productList.getDueDate() != "") {
	 * productList.setDueDate(DateFormatter.dateFormat(productList.getDueDate(),
	 * dateFormat)); } if (productList.getOnholdDate() != null &&
	 * productList.getOnholdDate() != "") {
	 * productList.setOnholdDate(DateFormatter.dateFormat(productList.getOnholdDate(
	 * ), dateFormat)); } if (productList.getPayemtDueDate() != null &&
	 * productList.getPayemtDueDate() != "") {
	 * productList.setPayemtDueDate(DateFormatter.dateFormat(productList.
	 * getPayemtDueDate(), dateFormat)); }
	 * 
	 * 
	 * logger.info("Method : viewPoEdit ends"); return productList; }
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("vendor-echallan-edit-trough-ajax")
	public @ResponseBody InventoryActionInvoiceModel viewPoEdit(@RequestParam String id, HttpSession session) {

		logger.info("Method : viewPoEdit starts" + id);
		JsonResponse<List<Object>> productList = new JsonResponse<List<Object>>();

		InventoryActionInvoiceModel jsonResponse = new InventoryActionInvoiceModel();
		
		
		try {
			jsonResponse = restTemplate.getForObject(env.getInventoryUrl() + "get-challan-edit?id=" + id, InventoryActionInvoiceModel.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		

//		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
//
//		} else {
//			jsonResponse.setMessage("Success");
//		}
		
		/* if (productList.getActiveDate() != null && productList.getActiveDate() != "")
		 * {
		 * productList.setActiveDate(DateFormatter.dateFormat(productList.getActiveDate(
		 * ), dateFormat)); } if (productList.getInvDate() != null &&
		 * productList.getInvDate() != "") {
		 * productList.setInvDate(DateFormatter.dateFormat(productList.getInvDate(),
		 * dateFormat)); } if (productList.getCompleteDate() != null &&
		 * productList.getCompleteDate() != "") {
		 * productList.setCompleteDate(DateFormatter.dateFormat(productList.
		 * getCompleteDate(), dateFormat)); } if (productList.getCreatedOn() != null &&
		 * productList.getCreatedOn() != "") {
		 * productList.setCreatedOn(DateFormatter.dateFormat(productList.getCreatedOn(),
		 * dateFormat)); } if (productList.getDueDate() != null &&
		 * productList.getDueDate() != "") {
		 * productList.setDueDate(DateFormatter.dateFormat(productList.getDueDate(),
		 * dateFormat)); } if (productList.getOnholdDate() != null &&
		 * productList.getOnholdDate() != "") {
		 * productList.setOnholdDate(DateFormatter.dateFormat(productList.getOnholdDate(
		 * ), dateFormat)); } if (productList.getPayemtDueDate() != null &&
		 * productList.getPayemtDueDate() != "") {
		 * productList.setPayemtDueDate(DateFormatter.dateFormat(productList.
		 * getPayemtDueDate(), dateFormat)); }
		 */

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewPoEdit ends");
		return jsonResponse;
	}
	

	@SuppressWarnings("unchecked")
	 @GetMapping("vendor-echallan-pdf-downloads")
	public void getChallanPdfDetails(HttpServletResponse response, Model model,HttpSession session,
			@RequestParam("invId") String encodedParam1, @RequestParam("organization") String encodedParam2,
			@RequestParam("orgDivision") String encodedParam3) {
		
		logger.info("Method : getChallanPdfDetails starts");

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String invIdd = (new String(encodeByte3));

		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String organizationd = (new String(encodeByte4));

		byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String orgDivisiond = (new String(encodeByte5));

		JsonResponse<List<InventoryActionInvoiceModel>> jsonResponse = new JsonResponse<List<InventoryActionInvoiceModel>>();
		DataTableRequest tableRequest = new DataTableRequest();

		//tableRequest.setParam1(param1);

		try { 
			jsonResponse = restTemplate.getForObject(env.getInventoryUrl() + "get-challan-pdfDetails?id=" + invIdd + "&organization="
					+ organizationd + "&orgDivision=" + orgDivisiond,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			
			List<InventoryActionInvoiceModel> payslip = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<InventoryActionInvoiceModel>>() {
					});

			
			jsonResponse.setBody(payslip);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<InventoryActionInvoiceModel> payslip = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<InventoryActionInvoiceModel>>() {
				});
		
		logger.info("payslip==" + payslip);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("payslip", payslip);
		logger.info("DATA----"+payslip);
		
		List<InventoryProductModel> productList = payslip.get(0).getProductList();
	
		logger.info("jjjjjj"+payslip.get(0).getProductList());
		
		logger.info("productList"+productList);
		
		// String logo = "";
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		String sign = "";
		String stamp = "";
		data.put("logo", logo);
		data.put("sign", sign);
		data.put("stamp", stamp);
		data.put("productList", productList);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=challanPdfDownload.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("procurement/challanPdfDownload", data);
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
		logger.info("REsp" + jsonResponse);
		logger.info("Method : getChallanPdfDetails ends");
		//return jsonResponse;
	}
}
