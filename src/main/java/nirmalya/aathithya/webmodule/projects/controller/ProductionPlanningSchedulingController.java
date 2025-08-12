package nirmalya.aathithya.webmodule.projects.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.ProjectPlanningSchedulingWebModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;

@Controller
@RequestMapping(value = "projects")
public class ProductionPlanningSchedulingController {
	Logger logger = LoggerFactory.getLogger(ProductionPlanningSchedulingController.class);

	@Autowired
	RestTemplate restClient;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "production-planning-scheduling" })
	public String productionPlanning(Model model, HttpSession session) {
		logger.info("Method : productionPlanning starts");

		try {

			DropDownModel[] projectPriority = restClient.getForObject(env.getProjects() + "get-projectPriority-list",
					DropDownModel[].class);
			List<DropDownModel> projectPriorityList = Arrays.asList(projectPriority);

			model.addAttribute("projectPriorityList", projectPriorityList);
		} catch (Exception e) {
			e.printStackTrace();

		}

		try {

			DropDownModel[] projectPriority = restClient.getForObject(env.getProjects() + "get-planningStatus-list",
					DropDownModel[].class);
			List<DropDownModel> planningStatusList = Arrays.asList(projectPriority);

			model.addAttribute("planningStatusList", planningStatusList);
		} catch (Exception e) {
			e.printStackTrace();

		}

		try {
			DropDownModel[] budgetCatList = restClient.getForObject(env.getProjects() + "get-productionPlanningCategoryList",
					DropDownModel[].class);
			List<DropDownModel> budgetCategoryList = Arrays.asList(budgetCatList);
			model.addAttribute("budgetCategoryList", budgetCategoryList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getSalutationLists",
					DropDownModel[].class);
			List<DropDownModel> SalutationLists = Arrays.asList(Collection);

			model.addAttribute("SalutationLists", SalutationLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorCategory",
					DropDownModel[].class);
			List<DropDownModel> CategoryList = Arrays.asList(Collection);

			model.addAttribute("CategoryList", CategoryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getPaymentTermsLists",
					DropDownModel[].class);
			List<DropDownModel> PaymentTermList = Arrays.asList(Collection);

			model.addAttribute("PaymentTermList", PaymentTermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : productionPlanning ends");
		return "projects/productionPlanning.html";
	}

	// Category
	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-catrgoryList")
	public @ResponseBody Object getcatrgoryList(HttpSession session, @RequestParam String id) {

		logger.info("Method :getcatrgoryList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getProjects() + "get-planningCategoryList" + "?userid=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getcatrgoryList--" + resp);
		logger.info("Method :getcatrgoryList ends");

		return resp;
	}
	// view

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-view")
	public @ResponseBody Object viewProjectCreation(HttpSession session) {

		logger.info("Method :viewProjectCreation starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-view-project-creation" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :viewProjectCreation ends");

		return resp;
	}

	// get First row planning data through project

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-getfirst-row-data")
	public @ResponseBody Object getFirstRowData(@RequestParam String id, HttpSession session) {

		logger.info("Method :getFirstRowData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-getfirstProjectName-production-planning?id=" + id
					+ "&userid=" + userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getFirstRowData--" + resp);
		logger.info("Method :getFirstRowData ends");

		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("production-planning-scheduling-masterdata")
	public @ResponseBody JsonResponse<Object> saveParentData(@RequestBody ProjectPlanningSchedulingWebModel eventModel,
			HttpSession session) {

		logger.info("Method : saveParentData  starts" + eventModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		@SuppressWarnings("unused")
		String userId;
		userId = (String) session.getAttribute("USER_ID");

		try {
			resp = restClient.postForObject(env.getProjects() + "save-MasterDataProductionPlanning", eventModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveParentData Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-SubCategoryList")
	public @ResponseBody JsonResponse<Object> getsubCatList(@RequestParam String id) {
		logger.info("Method : getsubCatList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-get-budgetSubCategoryList?id=" + id,
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
		System.out.println("state" + res);
		logger.info("Method : getsubCatList ends");
		return res;
	}

	// auto search preced name
	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-autosearch-assignTo")
	public @ResponseBody JsonResponse<DropDownModel> getAssignedToAutoSearchList(Model model,
			@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getAssignedToAutoSearchList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-getAssignedToAutoSearchList?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("success");
		} else {
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getAssignedToAutoSearchList ends");
		return res;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("production-planning-scheduling-parent-add")
	public @ResponseBody JsonResponse<Object> saveparentDataSchedule(
			@RequestBody List<ProjectPlanningSchedulingWebModel> eventModel, HttpSession session) {

		logger.info("Method : saveparentDataSchedule function starts" + eventModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (ProjectPlanningSchedulingWebModel m : eventModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);

		}

		try {
			resp = restClient.postForObject(env.getProjects() + "save-parentProductionPlanningData", eventModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveparentDataSchedule function Ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-parentDatadtls")
	public @ResponseBody Object getAllProjectIdDetls(@RequestParam String id, @RequestParam String id2,
			HttpSession session) {

		logger.info("Method :getAllProjectIdDetls starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-getProductionParentDetails?id=" + id + "&id2="
					+ id2 + "&userid=" + userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getFirstRowData--" + resp);
		logger.info("Method :getAllProjectIdDetls ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-parent-edit")
	public @ResponseBody Object getAllProjectIdDetls(@RequestParam String id, HttpSession session) {

		logger.info("Method :editParaentDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-getProductionParentEditDatas?id=" + id + "&userid="
					+ userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("editParaentDetails--" + resp);
		logger.info("Method :editParaentDetails ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-delete")
	public @ResponseBody JsonResponse<Object> planningschedulingdelete(@RequestParam String id,
			@RequestParam String id2, HttpSession session) {
		logger.info("Method : planningschedulingdelete function starts" + id + "id2" + id2);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getProjects() + "rest-production-planning-delete" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision + "&id=" + id + "&id2=" + id2, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : planningschedulingdelete function Ends");

		System.out.println("Response" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-deleteTaskCategory")
	public @ResponseBody JsonResponse<Object> planningschedulingdeletetTaskCategory(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : planningschedulingdeletetTaskCategory function starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getProjects() + "rest-productionplanning-TaskCategory-delete" + "?userid="
					+ userId + "&org=" + organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : planningschedulingdeletetTaskCategory function Ends");

		System.out.println("Response" + res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-calculationdatetime")
	public @ResponseBody Object calculationdatetime(@RequestParam String startDt,@RequestParam String endDt,
			HttpSession session) {

		logger.info("Method :calculationdatetime starts"+endDt);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		System.out.println(env.getProjects() + "rest-getcalculationdatetime?startDt=" + startDt+ "&endDt="+endDt);
		
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-getcalculationdatetimeProductionPlan?startDt=" + startDt+ "&endDt="+endDt,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("calculationdatetime--" + resp);
		logger.info("Method :calculationdatetime ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-categoryData")
	public @ResponseBody JsonResponse<ProjectPlanningSchedulingWebModel> getcategoryData(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getcategoryData starts  " + id);
		JsonResponse<ProjectPlanningSchedulingWebModel> res = new JsonResponse<ProjectPlanningSchedulingWebModel>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-getcategoryDataProductionPlanning?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}
		logger.info("Method : getcategoryData ends");
		return res;
	}
	
	/*
	 * vendor autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "production-planning-scheduling-get-vendor-list" })
	public @ResponseBody JsonResponse<PurchaseOrderModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getVendorAutoSearchList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<PurchaseOrderModel> res = new JsonResponse<PurchaseOrderModel>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorAutoSearchList?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getVendorAutoSearchList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-scheduling-get-address")
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
	
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "production-planning-scheduling-stateList" })
	public @ResponseBody JsonResponse<Object> getstateVendorList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getstateVendorList ends");
		return res;
	}
	@SuppressWarnings("unchecked")

	@PostMapping("production-planning-scheduling-add-cust-billingaddress")
	public @ResponseBody JsonResponse<Object> addbillingaddress(@RequestBody VendorNewModel vendorNewModel,
			HttpSession session) {
		logger.info("Method : addbillingaddress starts");
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		vendorNewModel.setCreatedBy(userId);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addVendorBillingaddres", vendorNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :addbillingaddress ends");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("production-planning-scheduling-add-cust-shippingaddress")
	public @ResponseBody JsonResponse<Object> addshippingaddress(@RequestBody VendorNewModel vendorNewModel,
			HttpSession session) {
		logger.info("Method : addshippingaddress starts");
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		vendorNewModel.setCreatedBy(userId);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addvendorShippingaddress", vendorNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :addshippingaddress ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/production-planning-scheduling-vendor-add")
	public @ResponseBody JsonResponse<Object> addVendor(@RequestBody VendorNewModel vendorNewModel,
			HttpSession session) {

		logger.info("Method : addVendor starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
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

			vendorNewModel.setCreatedBy(userId);
			vendorNewModel.setOrgName(orgName);
			vendorNewModel.setOrgDivision(orgDivision);

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "/addVendor", vendorNewModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addVendor ends");

		return resp;
	}

}
