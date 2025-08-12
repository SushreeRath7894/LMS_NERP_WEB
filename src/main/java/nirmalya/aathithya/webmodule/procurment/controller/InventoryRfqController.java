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
import nirmalya.aathithya.webmodule.procurment.model.InventoryRequisitionModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRfqModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRfqVendorModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "inventory/")
public class InventoryRfqController {

	Logger logger = LoggerFactory.getLogger(InventoryRfqController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("view-quatation")
	public String getRfqViewPage(Model model, HttpSession session) {

		logger.info("Method : getRfqViewPage starts");

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

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-requisition-priority", DropDownModel[].class);
			List<DropDownModel> requisitionPrioList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionPrioList", requisitionPrioList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/**
		 * get DropDown value for Requisition Type
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
		logger.info("Method : getRfqViewPage ends");
		return "procurement/view-quatation";

	}

	/*
	 * for copy
	 * 
	 * 
	 */
	@GetMapping(value = { "view-quatation-item-trough-ajax" })
	public @ResponseBody List<InventoryRfqModel> viewRequsitionEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewRequsitionEdit starts");
		List<InventoryRfqModel> productList = new ArrayList<InventoryRfqModel>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		if (id != null && id != "") {
			try {
				InventoryRfqModel[] inventoryStockModel = restTemplate
						.getForObject(env.getInventoryUrl() + "get-rfq-edit?id=" + id +"&org=" + orgName + "&orgDiv=" + orgDivision, InventoryRfqModel[].class);
				productList = Arrays.asList(inventoryStockModel);
				productList.forEach(s -> s.setId(s.getSku()));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (InventoryRfqModel a : productList) {
			if (a.getActiveDate() != null && a.getActiveDate() != "") {
				a.setActiveDate(DateFormatter.dateFormat(a.getActiveDate(), dateFormat));
			}
			if (a.getCompleteDate() != null && a.getCompleteDate() != "") {
				a.setCompleteDate(DateFormatter.dateFormat(a.getCompleteDate(), dateFormat));
			}
			if (a.getOnHoldDate() != null && a.getOnHoldDate() != "") {
				a.setOnHoldDate(DateFormatter.dateFormat(a.getOnHoldDate(), dateFormat));
			}
			if (a.getReceiveDate() != null && a.getReceiveDate() != "") {
				a.setReceiveDate(DateFormatter.dateFormat(a.getReceiveDate(), dateFormat));
			}
			if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
				a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
			}
		}
		logger.info("Method : viewRequsitionEdit ends");
		return productList;
	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "view-quatation-activity-log" })
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

			ActivitylogModel[] activityLog = restTemplate
					.getForObject(env.getInventoryUrl() + "get-activity-log?id=" + id +"&org="+orgName +"&orgDiv=" + orgDivision, ActivitylogModel[].class);
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
	 * post Mapping for add ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-quatation-save-th-ajax")
	public @ResponseBody JsonResponse<Object> saveItemRequisition(
			@RequestBody List<InventoryRfqModel> inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : saveItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		List<InventoryRfqModel> productList = new ArrayList<InventoryRfqModel>();
		String userId = "";
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			//logger.info("Method : saveItemRequisition function starts"+orgName);
		} catch (Exception e) {

		}
		for (InventoryRfqModel m : inventoryItemRequisitionModel) {
			m.setReceiveDate(DateFormatter.inputDateFormat(m.getReceiveDate(), dateFormat));
			m.setCreatedBy(userId);
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDivision);
			
		}
		logger.info("Method : saveItemRequisition function starts"+inventoryItemRequisitionModel);
		
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-add-rqf", inventoryItemRequisitionModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		productList = mapper.convertValue(res.getBody(), new TypeReference<List<InventoryRfqModel>>() {
		});

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		res.setBody(productList);
		logger.info("Method : saveItemRequisition function Ends");
		return res;
	}

	/**
	 * Web Controller - Get Item List By AutoSearch
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-quatation-get-product-list" })
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
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "view-quatation-trough-ajax" })
	public @ResponseBody List<InventoryRfqModel> viewRequisitionThroughAjax(HttpSession session) {
		logger.info("Method : viewRequisitionThroughAjax starts");
		List<InventoryRfqModel> productList = new ArrayList<InventoryRfqModel>();
		String dateFormat = "";
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {

			InventoryRfqModel[] inventoryStockModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-rfq-view-list?id="+userId +"&org=" + orgName + "&orgDiv=" + orgDivision, InventoryRfqModel[].class);
			productList = Arrays.asList(inventoryStockModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (InventoryRfqModel a : productList) {
			if (a.getActiveDate() != null && a.getActiveDate() != "") {
				a.setActiveDate(DateFormatter.dateFormat(a.getActiveDate(), dateFormat));
			}
			if (a.getCompleteDate() != null && a.getCompleteDate() != "") {
				a.setCompleteDate(DateFormatter.dateFormat(a.getCompleteDate(), dateFormat));
			}
			if (a.getOnHoldDate() != null && a.getOnHoldDate() != "") {
				a.setOnHoldDate(DateFormatter.dateFormat(a.getOnHoldDate(), dateFormat));
			}
			if (a.getReceiveDate() != null && a.getReceiveDate() != "") {
				a.setReceiveDate(DateFormatter.dateFormat(a.getReceiveDate(), dateFormat));
			}
			if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
				a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
			}
		}
		logger.info("Method : viewRequisitionThroughAjax ends"+productList);
		return productList;
	}

	/*
	 * post Mapping for delete ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-quatation-delete-th-ajax")
	public @ResponseBody JsonResponse<Object> deleteItemRequisition(
			@RequestBody InventoryRfqModel inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : deleteItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryItemRequisitionModel.setCreatedBy(userId);
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			inventoryItemRequisitionModel.setOrganizationName(orgName);
			inventoryItemRequisitionModel.setOrganizationDivision(orgDivision);
		
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-delete-rfq", inventoryItemRequisitionModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteItemRequisition function Ends");
		return res;
	}

	/*
	 * post Mapping for approve ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-quatation-approve-th-ajax")
	public @ResponseBody JsonResponse<Object> approveItemRequisition(
			@RequestBody InventoryRfqModel inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : approveItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryItemRequisitionModel.setCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-approve-rfq", inventoryItemRequisitionModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approveItemRequisition function Ends");
		return res;
	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "view-quatation-vendor-trough-ajax" })
	public @ResponseBody List<InventoryRfqVendorModel> viewVendorThroughAjax(HttpSession session ) {
		logger.info("Method : viewVendorThroughAjax starts");
		List<InventoryRfqVendorModel> productList = new ArrayList<InventoryRfqVendorModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			InventoryRfqVendorModel[] inventoryStockModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-vendor-view-list?org=" + orgName + "&orgDiv=" + orgDivision, InventoryRfqVendorModel[].class);
			productList = Arrays.asList(inventoryStockModel);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewVendorThroughAjax ends");
		return productList;
	}

}
