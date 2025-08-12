package nirmalya.aathithya.webmodule.grc.controller;

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
import nirmalya.aathithya.webmodule.grc.model.AuditScheduleModel;

@Controller
@RequestMapping(value = { "grc/" })
public class InspectionScheduleController {

	Logger logger = LoggerFactory.getLogger(InspectionScheduleController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restClient;

	@GetMapping(value = { "inspection-schedule" })
	public String inspectionSchedule(Model model, HttpSession session) {
		logger.info("Method : inspectionSchedule starts");
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			DropDownModel[] auditPlanList = restTemplate.getForObject(env.getGrcUrl()
					+ "getInceptionPlanIds?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditPlanList = Arrays.asList(auditPlanList);

			logger.info("Method : inceptionPlanList Called");
			model.addAttribute("incptionPlanList", AuditPlanList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {

			DropDownModel[] auditorList = restTemplate.getForObject(env.getGrcUrl()
					+ "getAuditorList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditorList = Arrays.asList(auditorList);
			model.addAttribute("auditorList", AuditorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] departmentList = restTemplate.getForObject(
					env.getGrcUrl() + "getDepartmentList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> DepartmentList = Arrays.asList(departmentList);

			logger.info("Method : DepartmentList Called");
			model.addAttribute("DeptList", DepartmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] auditeeList = restTemplate.getForObject(
					env.getGrcUrl() + "getAuditeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditeeList = Arrays.asList(auditeeList);
			model.addAttribute("AuditeeList", AuditeeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] scheduledAuditorList = restTemplate.getForObject(env.getGrcUrl()
					+ "getSheduledInceptionList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ScheduledAuditorList = Arrays.asList(scheduledAuditorList);
			model.addAttribute("ScheduledAuditorList", ScheduledAuditorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : inspectionSchedule ends");
		return "grc/inspection-schedule";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("inspection-schedule-view")
	public @ResponseBody Object viewSchedulePlan(HttpSession session) {
		logger.info("Method :viewSchedulePlan starts");
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
			resp = restTemplate.getForObject(
					env.getGrcUrl() + "rest-inspection-schedule-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSchedulePlan ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("inspection-schedule-add")
	public @ResponseBody JsonResponse<Object> auditPlanSchedule(HttpSession session,
			@RequestBody AuditScheduleModel model) {
		logger.info("Method : inspectionPlanSchedule function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		 
		   model.setCreatedBy(userId);
		    model.setOrganization(organization);
		    model.setOrgDivision(orgDivision);
		    
		try {
			res = restClient.postForObject(env.getGrcUrl() + "rest-inspection-schedule-add",model, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : inspectionPlanSchedule function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("inspection-schedule-unschedule")
	public @ResponseBody JsonResponse<Object> unScheduledAuditPlan(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : unScheduledAuditPlan function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restClient.getForObject(env.getGrcUrl() + "rest-inspection-schedule-unschedule?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : unScheduledAuditPlan function Ends");
		return res;
	}
}
