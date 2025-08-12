package nirmalya.aathithya.webmodule.productionplan.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.productionplan.model.PlanningProductionModel;
import nirmalya.aathithya.webmodule.productionplan.model.PlanningProductionParentModel;
import nirmalya.aathithya.webmodule.productionplan.model.ProductionPlanningProductList;
import nirmalya.aathithya.webmodule.qa.model.QaCrqsModel;

@Controller
@RequestMapping(value = "production")
public class DailyRosterController {
	Logger logger = LoggerFactory.getLogger(DailyRosterController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/dailyroster")
	public String planningProductionPageReturn(Model model, HttpSession session) {

		logger.info("Method : planningProductionPageReturn starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {
			DropDownModel[] grade = restClient.getForObject(env.getProduction() + "rest-getunit-list",
					DropDownModel[].class);

			List<DropDownModel> mainPartCodeList = Arrays.asList(grade);
			model.addAttribute("uomlist", mainPartCodeList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] PlanList = restClient.getForObject(
					env.getProduction() + "rest-getplan-list?org=" + orgName + "&orgDiv=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> planList = Arrays.asList(PlanList);
			model.addAttribute("planList", planList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] plant = restClient.getForObject(
					env.getProduction() + "rest-getplant-list?org=" + orgName + "&orgDiv=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> plantList = Arrays.asList(plant);
			model.addAttribute("plantList", plantList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : planningProductionPageReturn ends");

		return "production_plan/dailyroster";
	}

	@SuppressWarnings("unchecked")

	@GetMapping("production-dailyroster-view")
	public @ResponseBody Object viewDailyRoster(HttpSession session) {
		logger.info("Method :viewDailyRoster starts");
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
			resp = restClient.getForObject(env.getProduction() + "rest-production-dailyroster-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
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
		logger.info("Method :viewDailyRoster ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-daily-planning-view")
	public @ResponseBody List<PlanningProductionParentModel> viewDropPlanningProduction(@RequestParam String planid,
			HttpSession session) {
		logger.info("Method : view Daily viewDropPlanningProduction starts");

		JsonResponse<List<PlanningProductionParentModel>> resp = new JsonResponse<List<PlanningProductionParentModel>>();
		List<PlanningProductionParentModel> returnList = new ArrayList<PlanningProductionParentModel>();

		String userid = (String) session.getAttribute("USER_ID");

		try {
			resp = restClient.getForObject(env.getProduction() + "rest-production-daily-planning-view?planid=" + planid,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		System.out.println("view" + returnList);
		logger.info("Method :view Daily viewDropPlanningProduction ends");
		return returnList;
	}

	

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("dailyroster-planning-view") public @ResponseBody
	 * List<PlanningProductionParentModel> viewPlanningProduction(HttpSession
	 * session) { logger.info("Method : viewPlanningProduction starts");
	 * 
	 * JsonResponse<List<PlanningProductionParentModel>> resp = new
	 * JsonResponse<List<PlanningProductionParentModel>>();
	 * List<PlanningProductionParentModel> returnList = new
	 * ArrayList<PlanningProductionParentModel>();
	 * 
	 * String userid = (String) session.getAttribute("USER_ID");
	 * 
	 * try { resp = restClient.getForObject(env.getProduction() +
	 * "rest-daily-viewproductionplanning?userid=" + userid, JsonResponse.class);
	 * returnList = resp.getBody(); } catch (RestClientException e) {
	 * e.printStackTrace(); } System.out.println("view" + returnList);
	 * logger.info("Method :viewPlanningProduction ends"); return returnList; }
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("production-daily-planning-get-machine-list")
	public @ResponseBody List<PlanningProductionModel> getProductMachineList(
			@RequestBody PlanningProductionModel PlanningProductionModel, HttpSession session) {

		logger.info("Method : getProductMachineList starts" + PlanningProductionModel);

		JsonResponse<List<PlanningProductionModel>> resp = new JsonResponse<List<PlanningProductionModel>>();
		List<PlanningProductionModel> returnList = new ArrayList<PlanningProductionModel>();
		try {
			resp = restClient.postForObject(env.getProduction() + "rest-getmachine-list", PlanningProductionModel,
					JsonResponse.class);
			returnList = resp.getBody();
			;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {

		} else {
			resp.setMessage("Success");
		}

		System.out.println("REsp" + returnList);
		logger.info("Method : getProductMachineList ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-daily-planning-get-manpower")
	public @ResponseBody JsonResponse<PlanningProductionModel> getManPowerList(
			@RequestBody PlanningProductionModel PlanningProductionModel, HttpSession session) {

		logger.info("Method : getManPowerList starts" + PlanningProductionModel);

		JsonResponse<PlanningProductionModel> response = new JsonResponse<PlanningProductionModel>();
		try {
			response = restClient.postForObject(env.getProduction() + "rest-manpower-list", PlanningProductionModel,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : getManPowerList ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-daily-planning-get-shiftallocation-list")
	public @ResponseBody JsonResponse<List<DropDownModel>> getShiftAllocationList(Model model, HttpSession session) {

		logger.info("Method : getShiftAllocationList starts");
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {

		}

		JsonResponse<List<DropDownModel>> response = new JsonResponse<List<DropDownModel>>();
		try {
			response = restClient.getForObject(env.getProduction() + "getShiftListForProduction?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : getShiftAllocationList ends" + response);
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-daily-planning-variantdrop")
	public @ResponseBody JsonResponse<List<PlanningProductionModel>> getProductVariantList(Model model,
			@RequestParam String id, @RequestParam String planid, HttpSession session) {

		logger.info("Method : getProductVariantList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		JsonResponse<List<PlanningProductionModel>> response = new JsonResponse<List<PlanningProductionModel>>();
		try {
			response = restClient.getForObject(env.getProduction() + "rest-getvariant-list?id=" + id + "&planid="
					+ planid + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : getProductVariantList ends" + response);
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-daily-planning-brand-drop")
	public @ResponseBody JsonResponse<List<PlanningProductionModel>> getProductBrandList(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : getProductBrandList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		JsonResponse<List<PlanningProductionModel>> response = new JsonResponse<List<PlanningProductionModel>>();
		try {
			response = restClient.getForObject(env.getProduction() + "rest-getbrandname-list?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : getProductBrandList ends" + response);
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-daily-planning-get-rawmaterial")
	public @ResponseBody JsonResponse<List<PlanningProductionModel>> getRawMaterialList(
			@RequestBody PlanningProductionModel PlanningProductionModel, HttpSession session) {

		logger.info("Method : getRawMaterialList starts" + PlanningProductionModel);

		JsonResponse<List<PlanningProductionModel>> response = new JsonResponse<List<PlanningProductionModel>>();
		try {
			response = restClient.postForObject(env.getProduction() + "rest-rawmaterial-list", PlanningProductionModel,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : getRawMaterialList ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-daily-planning-unitDropdown")
	public @ResponseBody JsonResponse<List<PlanningProductionModel>> getVarUnitDropdownList(Model model,
			HttpSession session) {

		logger.info("Method : getVarUnitDropdownList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		JsonResponse<List<PlanningProductionModel>> response = new JsonResponse<List<PlanningProductionModel>>();
		try {
			response = restClient.getForObject(
					env.getProduction() + "rest-unitDropdown-list?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : getVarUnitDropdownList ends" + response);
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("production-daily-planning-add-planning")
	public @ResponseBody JsonResponse<Object> savePlanningDetails(
			@RequestBody PlanningProductionParentModel planningmodel, HttpSession session) {

		logger.info("Method : savePlanningDetails function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId;
		userId = (String) session.getAttribute("USER_ID");

		System.out.println("userid===>>" + userId);
		planningmodel.setCreatedBy(userId);
		System.out.println("===>>>>" + planningmodel);

		try {
			resp = restClient.postForObject(env.getProduction() + "saveplanning-Details", planningmodel,
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
		System.out.println("resp==>>" + resp);
		logger.info("Method : savePlanningDetails function Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-daily-planning-delete")
	public @ResponseBody JsonResponse<Object> deletePlanProduction(
			@RequestBody PlanningProductionParentModel PlanningProductionParentModel, HttpSession session) {

		logger.info("Method : getProduction starts" + PlanningProductionParentModel);

		JsonResponse<Object> response = new JsonResponse<Object>();
		try {
			response = restClient.postForObject(env.getProduction() + "rest-delete-production-planning",
					PlanningProductionParentModel, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : getProduction ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-daily-planning-edit")
	public @ResponseBody JsonResponse<PlanningProductionParentModel> editPlanningProduction(
			@RequestParam String planningid, HttpSession session) {

		logger.info("Method : editPlanningProduction starts" + planningid);

		JsonResponse<PlanningProductionParentModel> response = new JsonResponse<PlanningProductionParentModel>();
		try {
			response = restClient.getForObject(env.getProduction() + "rest-edit-production-planning?id=" + planningid,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : editPlanningProduction ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-daily-planning-approve")
	public @ResponseBody JsonResponse<Object> approvePlanProduction(
			@RequestBody PlanningProductionParentModel PlanningProductionParentModel, HttpSession session) {

		logger.info("Method : approvePlanProduction starts" + PlanningProductionParentModel);

		JsonResponse<Object> response = new JsonResponse<Object>();
		try {
			response = restClient.postForObject(env.getProduction() + "rest-approve-production-planning",
					PlanningProductionParentModel, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : approvePlanProduction ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-daily-planning-schedule")
	public @ResponseBody JsonResponse<PlanningProductionParentModel> schedulePlanningProduction(
			@RequestParam String planningid, HttpSession session) {

		logger.info("Method : schedulePlanningProduction starts" + planningid);

		JsonResponse<PlanningProductionParentModel> response = new JsonResponse<PlanningProductionParentModel>();
		try {
			response = restClient.getForObject(
					env.getProduction() + "rest-schedule-production-planning?id=" + planningid, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : schedulePlanningProduction ends");
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("production-daily-planning-set-schedule")
	public @ResponseBody JsonResponse<Object> setPlanSchedule(@RequestBody PlanningProductionParentModel planningmodel,
			HttpSession session) {

		logger.info("Method : setPlanSchedule function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId;
		userId = (String) session.getAttribute("USER_ID");

		System.out.println("userid===>>" + userId);
		planningmodel.setCreatedBy(userId);
		System.out.println("===>>>>" + planningmodel);

		try {
			resp = restClient.postForObject(env.getProduction() + "setschedule-Details", planningmodel,
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
		System.out.println("resp==>>" + resp);
		logger.info("Method : setPlanSchedule function Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-daily-planning-schdule-edit")
	public @ResponseBody JsonResponse<PlanningProductionParentModel> editScheduleData(@RequestParam String planningid,
			HttpSession session) {

		logger.info("Method : editScheduleData starts" + planningid);

		JsonResponse<PlanningProductionParentModel> response = new JsonResponse<PlanningProductionParentModel>();
		try {
			response = restClient.getForObject(
					env.getProduction() + "rest-schedule-edit-production-planning?id=" + planningid,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : editScheduleData ends");
		return response;
	}
	
	// Daily Roster View.
	
	@SuppressWarnings("unchecked")
	@GetMapping("dailyroster-planning-view")
	public @ResponseBody Object dailyrosterView(HttpSession session) {
		logger.info("Method :dailyrosterView starts");
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
			resp = restClient.getForObject(
					env.getProduction() + "rest-dailyrosterView?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		System.out.println("view===" + resp);
		logger.info("Method :dailyrosterView ends");
		return resp;
	}
	
	// Daily Roster Shift View.

	@SuppressWarnings("unchecked")
	@GetMapping("dailyroster-shift-view")
	public @ResponseBody Object dailyrosterShiftView(@RequestParam String date, String id, HttpSession session) {
		logger.info("Method :dailyrosterShiftView starts");
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
			resp = restClient.getForObject(env.getProduction() + "rest-dailyrosterShiftView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&date=" + date + "&id=" + id, JsonResponse.class);
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
		logger.info("Method :dailyrosterShiftView ends");
		return resp;
	}

	// Add Daily Roster.

	@SuppressWarnings({ "unchecked" })
	@PostMapping("dailyroster-add")
	public @ResponseBody JsonResponse<Object> addDailyRoster(
			@RequestBody List<ProductionPlanningProductList> planningmodel, HttpSession session) {

		logger.info("Method : addDailyRoster function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId;
		userId = (String) session.getAttribute("USER_ID");

		System.out.println("userid===>>" + userId);

		System.out.println("===>>>>" + planningmodel);

		try {
			resp = restClient.postForObject(env.getProduction() + "rest-addDailyRoster", planningmodel,
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
		System.out.println("resp==>>" + resp);
		logger.info("Method : addDailyRoster function Ends");
		return resp;
	}

}
