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

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.procurment.model.InventoryActionRfqModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "inventory/")
public class InventoryActionRfqController {
	Logger logger = LoggerFactory.getLogger(InventoryActionRfqController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("action-rfq")
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
			logger.error(e.getMessage());
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-requisition-priority", DropDownModel[].class);
			List<DropDownModel> requisitionPrioList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionPrioList", requisitionPrioList);
		} catch (RestClientException e) {
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		}
		logger.info("Method : getRfqViewPage ends");
		return "procurement/view-inventory-action-rfq";

	}

	@GetMapping(value = { "action-rfq-activity-log" })
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
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "action-rfq-view-trough-ajax" })
	public @ResponseBody List<InventoryActionRfqModel> viewRequisitionThroughAjax(HttpSession session) {
		logger.info("Method : viewRequisitionThroughAjax starts");
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
		List<InventoryActionRfqModel> productList = new ArrayList<InventoryActionRfqModel>();
		try {

			InventoryActionRfqModel[] inventoryStockModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-action-rfq-view-list?org="+orgName +"&orgDiv=" + orgDivision, InventoryActionRfqModel[].class);
			productList = Arrays.asList(inventoryStockModel);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		for (InventoryActionRfqModel a : productList) {
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
			if (a.getLastPoDate() != null && a.getLastPoDate() != "") {
				a.setLastPoDate(DateFormatter.dateFormat(a.getLastPoDate(), dateFormat));
			}
		}
		logger.info("Method : viewRequisitionThroughAjax ends");
		return productList;
	}

	/*
	 * for edit
	 */
	@GetMapping(value = { "action-rfq-item-trough-ajax" })
	public @ResponseBody List<InventoryActionRfqModel> viewRequsitionEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewRequsitionEdit starts");
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
		List<InventoryActionRfqModel> productList = new ArrayList<InventoryActionRfqModel>();
		if (id != null && id != "") {
			try {
				InventoryActionRfqModel[] inventoryStockModel = restTemplate.getForObject(
						env.getInventoryUrl() + "get-action-rfq-edit?id=" + id +"&org="+orgName +"&orgDiv=" + orgDivision, InventoryActionRfqModel[].class);
				productList = Arrays.asList(inventoryStockModel);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		for (InventoryActionRfqModel a : productList) {
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
			if (a.getLastPoDate() != null && a.getLastPoDate() != "") {
				a.setLastPoDate(DateFormatter.dateFormat(a.getLastPoDate(), dateFormat));
			}
		}
		logger.info("Method : viewRequsitionEdit ends");
		return productList;
	}
}
