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
import nirmalya.aathithya.webmodule.procurment.model.InventoryPoModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRequisitionModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "inventory/")
public class InventoryPoController {
	Logger logger = LoggerFactory.getLogger(InventoryPoController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("generate-po")
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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-Payment-term",
					DropDownModel[].class);
			List<DropDownModel> paytermList = Arrays.asList(dropDownModel);
			model.addAttribute("paytermList", paytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * legal term master
		 */
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-legal-term",
					DropDownModel[].class);
			List<DropDownModel> legaltermList = Arrays.asList(dropDownModel);
			model.addAttribute("legaltermList", legaltermList);
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
		return "procurement/create-po";

	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "generate-po-activity-log" })
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
	 * post Mapping for add po
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "generate-po-save-th-ajax")
	public @ResponseBody JsonResponse<Object> savePo(@RequestBody InventoryPoModel inventoryPoModel,
			HttpSession session) {
		logger.info("Method : savePo function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
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
		inventoryPoModel.setCreatedBy(userId);
		/*
		 * inventoryPoModel.setReceiveDate(DateFormatter.inputDateFormat(
		 * inventoryPoModel.getReceiveDate(), dateFormat));
		 * inventoryPoModel.setStartDate(DateFormatter.inputDateFormat(inventoryPoModel.
		 * getStartDate(), dateFormat));
		 * inventoryPoModel.setExpireDate(DateFormatter.inputDateFormat(inventoryPoModel
		 * .getExpireDate(), dateFormat));
		 */
		inventoryPoModel.setOrganizationName(orgName);
		inventoryPoModel.setOrganizationDivision(orgDivision);
		logger.info("inventoryPoModel"+inventoryPoModel);
		try {
			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-add-po", inventoryPoModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		ObjectMapper mapper = new ObjectMapper();
		InventoryPoModel inventoryPoRet = mapper.convertValue(res.getBody(), new TypeReference<InventoryPoModel>() {
		});
		if (inventoryPoRet.getApprovedDate() != null) {
			inventoryPoRet.setApprovedDate(DateFormatter.dateFormat(inventoryPoRet.getApprovedDate(), dateFormat));
		}
		if (inventoryPoRet.getCompleteDate() != null) {
			inventoryPoRet.setCompleteDate(DateFormatter.dateFormat(inventoryPoRet.getCompleteDate(), dateFormat));
		}
		if (inventoryPoRet.getCreatedOn() != null) {
			inventoryPoRet.setCreatedOn(DateFormatter.dateFormat(inventoryPoRet.getCreatedOn(), dateFormat));
		}
		if (inventoryPoRet.getExpireDate() != null) {
			inventoryPoRet.setExpireDate(DateFormatter.dateFormat(inventoryPoRet.getExpireDate(), dateFormat));
		}
		if (inventoryPoRet.getOnholdDate() != null) {
			inventoryPoRet.setOnholdDate(DateFormatter.dateFormat(inventoryPoRet.getOnholdDate(), dateFormat));
		}
		if (inventoryPoRet.getReceiveDate() != null) {
			inventoryPoRet.setReceiveDate(DateFormatter.dateFormat(inventoryPoRet.getReceiveDate(), dateFormat));
		}
		if (inventoryPoRet.getStartDate() != null) {
			inventoryPoRet.setStartDate(DateFormatter.dateFormat(inventoryPoRet.getStartDate(), dateFormat));
		}
		for (InventoryProductModel a : inventoryPoRet.getProductList()) {
			if (a.getCreatedOn() != null) {
				a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
			}
		}
		res.setBody(inventoryPoRet);
		logger.info("Method : savePo function Ends");
		return res;
	}

	/**
	 * Web Controller - Get Item List By AutoSearch
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/generate-po-get-product-list" })
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
	 * view po through ajax
	 * 
	 * 
	 */
	@GetMapping(value = { "generate-po-get-po-list" })
	public @ResponseBody List<InventoryPoModel> getPoListThrowAjax(HttpSession session) {
		logger.info("Method : getPoListThrowAjax starts");
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
		List<InventoryPoModel> inventoryPoModelList = new ArrayList<InventoryPoModel>();
		try {

			InventoryPoModel[] inventoryPoModel = restTemplate.getForObject(env.getInventoryUrl() + "get-po-view-list?org=" + orgName + "&orgDiv=" + orgDivision,
					InventoryPoModel[].class);
			inventoryPoModelList = Arrays.asList(inventoryPoModel);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		for (InventoryPoModel a : inventoryPoModelList) {
			if (a.getApprovedDate() != null) {
				a.setApprovedDate(DateFormatter.dateFormat(a.getApprovedDate(), dateFormat));
			}
			if (a.getCompleteDate() != null) {
				a.setCompleteDate(DateFormatter.dateFormat(a.getCompleteDate(), dateFormat));
			}
			if (a.getCreatedOn() != null) {
				a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
			}
			if (a.getExpireDate() != null) {
				a.setExpireDate(DateFormatter.dateFormat(a.getExpireDate(), dateFormat));
			}
			if (a.getOnholdDate() != null) {
				a.setOnholdDate(DateFormatter.dateFormat(a.getOnholdDate(), dateFormat));
			}
			if (a.getReceiveDate() != null) {
				a.setReceiveDate(DateFormatter.dateFormat(a.getReceiveDate(), dateFormat));
			}
			if (a.getStartDate() != null) {
				a.setStartDate(DateFormatter.dateFormat(a.getStartDate(), dateFormat));
			}
		}
		logger.info("Method : getPoListThrowAjax ends");
		return inventoryPoModelList;
	}

	/*
	 * for copy
	 * 
	 * 
	 */
	@GetMapping(value = { "generate-po-item-trough-ajax" })
	public @ResponseBody InventoryPoModel viewPoEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPoEdit starts");
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
		InventoryPoModel productList = new InventoryPoModel();
		if (id != null && id != "") {
			try {
				productList = restTemplate.getForObject(env.getInventoryUrl() + "get-po-edit?id=" + id +"&org="+orgName +"&orgDiv=" + orgDivision,
						InventoryPoModel.class);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		logger.info("Method : viewPoEdit ends");
		return productList;
	}

	/*
	 * post Mapping for approve ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "generate-po-approve-th-ajax")
	public @ResponseBody JsonResponse<Object> approveItemRequisition(@RequestBody InventoryPoModel poModel,
			HttpSession session) {
		logger.info("Method : approveItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			poModel.setCreatedBy(userId);
			poModel.setOrganizationName(orgName);
			poModel.setOrganizationDivision(orgDivision);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-approve-po", poModel, JsonResponse.class);
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
	 * post Mapping for delete ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "generate-po-delete-th-ajax")
	public @ResponseBody JsonResponse<Object> deleteItemRequisition(@RequestBody InventoryPoModel inventoryPoModelList,
			HttpSession session) {
		logger.info("Method : deleteItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryPoModelList.setCreatedBy(userId);
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			inventoryPoModelList.setOrganizationName(orgName);
			inventoryPoModelList.setOrganizationDivision(orgDivision);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-delete-po", inventoryPoModelList,
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
}
