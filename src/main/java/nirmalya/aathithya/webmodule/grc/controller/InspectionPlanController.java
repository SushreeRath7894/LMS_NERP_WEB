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
public class InspectionPlanController {

	Logger logger = LoggerFactory.getLogger(InspectionPlanController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restClient;

	@GetMapping(value = { "inspection-plan" })
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

			DropDownModel[] inspectType = restTemplate.getForObject(
					env.getGrcUrl() + "getInspectionTypeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> InspectionType = Arrays.asList(inspectType);
			model.addAttribute("inspectType", InspectionType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] auditTypeWiseAuditCategory = restTemplate.getForObject(env.getGrcUrl()
					+ "getInspectionCategoryList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditTypeWiseAuditCategory = Arrays.asList(auditTypeWiseAuditCategory);
			model.addAttribute("auditTypeWiseAuditCategory", AuditTypeWiseAuditCategory);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {

			DropDownModel[] auditTypeWiseAuditCategoryEx = restTemplate.getForObject(env.getGrcUrl()
					+ "getInspectionCategoryListEx?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditTypeWiseAuditCategoryX = Arrays.asList(auditTypeWiseAuditCategoryEx);
			model.addAttribute("auditTypeWiseAuditCategoryEx", AuditTypeWiseAuditCategoryX);
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
		return "grc/inspection-plan";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("inspection-plan-view")
	public @ResponseBody Object viewAuditPlan(HttpSession session) {
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
					env.getGrcUrl() + "rest-inspection-plan-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewAuditPlan ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "inspection-plan-add" })
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
			resp = restClient.postForObject(env.getGrcUrl() + "rest-inspection-plan-add", ap, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addAuditPlan function Ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("inspection-plan-edit")
	public @ResponseBody Object inspectionPlanEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method :inspectionPlanEdit starts");
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

			resp = restClient.getForObject(env.getGrcUrl() + "rest-inspection-plan-edit?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :inspectionPlanEdit ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("inspection-plan-delete")
	public @ResponseBody JsonResponse<Object> deleteInspectionPlan(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteInspectionPlan function starts");

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
			res = restClient.getForObject(env.getGrcUrl() + "rest-inspection-plan-delete?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteInspectionPlan function Ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("inspection-plan-approve")
	public @ResponseBody JsonResponse<Object> approveInspectionPlan(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approveInspectionPlan function starts");

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
			res = restClient.getForObject(env.getGrcUrl() + "rest-inspection-plan-approve?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : approveInspectionPlan function Ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("inspection-plan-newCategory")
	public @ResponseBody JsonResponse<Object> addNewCategory(@RequestParam String name,
			String type, HttpSession session) {
		logger.info("Method : addNewCategory starts");
		JsonResponse<Object> jsonResponse = new JsonResponse<>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (RestClientException e) {
			e.printStackTrace();

		}
		try {
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "rest-addNewCategory?name="
					+ name + "&type=" + type+ "&userId=" + userId + "&orgName="+orgName +"&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : addNewCategory ends");
		return jsonResponse;
	}
	
	
	@SuppressWarnings({"unchecked" })
	@GetMapping("inspection-plan-getCategory")
	public @ResponseBody JsonResponse <List<DropDownModel>> getCategory(String type,HttpSession session){
		
		logger.info("Get Category Start");
		
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		
		
		  String orgName = ""; 
		  String orgDiv = ""; 
		  try {
		 
		  orgName = (String) session.getAttribute("ORGANIZATION"); orgDiv = (String)
		  session.getAttribute("ORGANIZATION_DIVISION");
		  
		  } catch(Exception e) {
		  
		  }
		 
		
		try {
			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-getCategory?orgName=" +orgName  + "&orgDiv=" + orgDiv +"&type=" + type, JsonResponse.class);
		}catch(RestClientException e) {
			
			e.printStackTrace();
		}
		
		logger.info("Get Category Start End");
		return resp;
	}
}
