package nirmalya.aathithya.webmodule.procurment.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryActionInvoiceModel;

@Controller
@RequestMapping(value = "inventory/")
public class InventoryGenerateEchallan {
	Logger logger = LoggerFactory.getLogger(InventoryGenerateEchallan.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@GetMapping("generate-echallan")
	public String getEchallanViewPage(Model model, HttpSession session) {

		logger.info("Method : getEchallanViewPage starts");
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
	
		logger.info("Method : getEchallanViewPage ends");
		return "procurement/generate-echallan";

	}
	@GetMapping(value = { "generate-echallan-get-invoice-list" })
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
			String vendorId = (String) session.getAttribute("USER_ID");
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
	@GetMapping("generate-echallan-edit-trough-ajax")
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

}
