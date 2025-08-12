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

@Controller
@RequestMapping(value = "production")
public class PlanningProductionController {
	Logger logger = LoggerFactory.getLogger(PlanningProductionController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/production-planning")
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
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : planningProductionPageReturn ends");

		return "production_plan/planningproduction";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-planning-get-machine-list")
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
		logger.info("Method : getProductMachineList ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-planning-get-manpower")
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
		logger.info("Method : getManPowerList ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-get-shiftallocation-list")
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
			response = restClient.getForObject(env.getProduction() + "getShiftListForProductionPlan?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : getShiftAllocationList ends" + response);
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-variantdrop")
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

		logger.info("Method : getProductVariantList ends" + response);
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-brand-drop")
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

		logger.info("Method : getProductBrandList ends" + response);
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-planning-get-rawmaterial")
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

		logger.info("Method : getRawMaterialList ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-unitDropdown")
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

		logger.info("Method : getVarUnitDropdownList ends" + response);
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("production-planning-add-planning")
	public @ResponseBody JsonResponse<Object> savePlanningDetails(
			@RequestBody PlanningProductionParentModel planningmodel, HttpSession session) {

		logger.info("Method : savePlanningDetails function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		planningmodel.setCreatedBy(userId);
		planningmodel.setOrg(orgName);
		planningmodel.setOrgDiv(orgDivision);
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
		logger.info("Method : savePlanningDetails function Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-view")
	public @ResponseBody List<PlanningProductionParentModel> viewPlanningProduction(HttpSession session) {
		logger.info("Method : viewPlanningProduction starts");

		JsonResponse<List<PlanningProductionParentModel>> resp = new JsonResponse<List<PlanningProductionParentModel>>();
		List<PlanningProductionParentModel> returnList = new ArrayList<PlanningProductionParentModel>();

		String userid = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userid = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {
			resp = restClient.getForObject(env.getProduction() + "rest-viewproductionplanning?userid=" + userid
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :viewPlanningProduction ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-planning-delete")
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

		logger.info("Method : getProduction ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-edit")
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

		logger.info("Method : editPlanningProduction ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("production-planning-approve")
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

		logger.info("Method : approvePlanProduction ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-schedule")
	public @ResponseBody JsonResponse<PlanningProductionParentModel> schedulePlanningProduction(
			@RequestParam String planningid, HttpSession session) {

		logger.info("Method : schedulePlanningProduction starts" + planningid);

		String org = "";
		String orgDiv = "";
		try {

			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		
		JsonResponse<PlanningProductionParentModel> response = new JsonResponse<PlanningProductionParentModel>();
		try {
			response = restClient.getForObject(
					env.getProduction() + "rest-schedule-production-planning?id=" + planningid + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : schedulePlanningProduction ends");
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("production-planning-set-schedule")
	public @ResponseBody JsonResponse<Object> setPlanSchedule(@RequestBody PlanningProductionParentModel planningmodel,
			HttpSession session) {
		logger.info("Method : setPlanSchedule function starts");
		logger.info("adddddd dataaa=="+planningmodel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
		}
		planningmodel.setCreatedBy(userId);
		planningmodel.setOrg(orgName);
		planningmodel.setOrgDiv(orgDivision);
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
		logger.info("Method : setPlanSchedule function Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-schdule-edit")
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

		logger.info("Method : editScheduleData ends");
		return response;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("production-planning-get-resouceOnItem-list")
	public @ResponseBody List<PlanningProductionModel> getResouceOnItem(
			@RequestParam String item, String type, HttpSession session) {

		logger.info("Method : getResouceOnItem starts" + item);
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		JsonResponse<List<PlanningProductionModel>> resp = new JsonResponse<List<PlanningProductionModel>>();
		List<PlanningProductionModel> returnList = new ArrayList<PlanningProductionModel>();
		try {
			resp = restClient.getForObject(
					env.getProduction() + "rest-getResouceOnItem-list?item=" + item +"&type=" + type + "&org=" + orgName + "&orgDiv=" + orgDivision ,
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

		System.err.println("returnList====="+returnList);
		logger.info("Method : getResouceOnItem ends");
		return returnList;
	}
}
