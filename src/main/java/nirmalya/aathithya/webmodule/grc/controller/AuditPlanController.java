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
import nirmalya.aathithya.webmodule.grc.model.AuditPlanModel;

@Controller
@RequestMapping(value = { "grc/" })
public class AuditPlanController {
	Logger logger = LoggerFactory.getLogger(AuditPlanController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	RestTemplate restClient;
	
	@GetMapping(value = { "audit-plan" })
	public String auditMaster(Model model, HttpSession session) {
		logger.info("Method : auditPlan starts");
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {

			DropDownModel[] auditType = restTemplate.getForObject(env.getGrcUrl()
					+ "getAuditType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditType = Arrays.asList(auditType);
			model.addAttribute("auditType", AuditType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {

			DropDownModel[] projectList = restTemplate.getForObject(
					env.getGrcUrl() + "getAuditProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditProjectList = Arrays.asList(projectList);
			model.addAttribute("ProjectList", AuditProjectList);
			 
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		try {

			DropDownModel[] auditTypeWiseAuditCategory = restClient.getForObject(
					env.getGrcUrl() + "getAuditTypeWiseAuditCategory?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditTypeWiseAuditCategory = Arrays.asList(auditTypeWiseAuditCategory);
			model.addAttribute("auditTypeWiseAuditCategory", AuditTypeWiseAuditCategory);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {

			DropDownModel[] auditorList = restTemplate.getForObject(env.getGrcUrl()
					+ "getInternalAuditorList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditorList = Arrays.asList(auditorList);
			model.addAttribute("internalauditorList", AuditorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {

			DropDownModel[] extAuditorList = restTemplate.getForObject(env.getGrcUrl()
					+ "getExternalAuditorList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ExtAuditorList = Arrays.asList(extAuditorList);
			model.addAttribute("externalauditorList", ExtAuditorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(env.getGrcUrl() + "getPriorityListforAsset?organization=" + organization
					+ "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> prioLists = Arrays.asList(cat);
			model.addAttribute("prioLists", prioLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restClient.getForObject(env.getGrcUrl() + "getUOMListforAsset?organization=" + organization
					+ "&orgDivision=" + orgDivision , DropDownModel[].class);
			List<DropDownModel> unitLists = Arrays.asList(cat);
			model.addAttribute("unitOfMeasurement", unitLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : auditPlan ends");
		return "grc/audit-plan";
	}
	
 
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "audit-plan/get_auditTypeWise_AuditCategory" })
	public @ResponseBody JsonResponse<Object> get_auditTypeWise_AuditCategory(@RequestParam String id) {
		logger.info("Method : get_auditTypeWise_AuditCategory starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "rest-getAuditCategoryLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("AuditCategory" + res);
		logger.info("Method : get_auditTypeWise_AuditCategory ends");
		return res;
	}
	
	// add audit-plan-savedata
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "audit-plan-add" })
	public @ResponseBody JsonResponse<Object> addAuditPlan(@RequestBody List<AuditPlanModel> ap,
			HttpSession session) {
		logger.info("Method : addAuditPlan function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (AuditPlanModel m : ap) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		 
		try {
			resp = restClient.postForObject(env.getGrcUrl() + "rest-audit-plan-add", ap, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addAuditPlan function Ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("audit-plan-view")
	public @ResponseBody Object viewAuditPlan(@RequestParam String type,HttpSession session) {
		logger.info("Method :viewAuditPlan starts");
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
					env.getGrcUrl() + "rest-audit-plan-view?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&type=" + type,
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
		logger.info("Method :viewAuditPlan ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("audit-plan-edit")
	public @ResponseBody Object auditPlanEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method :auditPlanEdit starts");
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

			resp = restClient.getForObject(env.getGrcUrl() + "rest-audit-plan-edit?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :auditPlanEdit ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("audit-plan-delete")
	public @ResponseBody JsonResponse<Object> deleteAuditPlan(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteAuditPlan function starts");

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
			res = restClient.getForObject(env.getGrcUrl() + "rest-audit-plan-delete?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteAuditPlan function Ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("audit-plan-approve")
	public @ResponseBody JsonResponse<Object> approveAuditPlan(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approveAuditPlan function starts");

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
			res = restClient.getForObject(env.getGrcUrl() + "rest-audit-plan-approve?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {

		}
		logger.info("Method : approveAuditPlan function Ends");
		return res;
	}

}
	 
