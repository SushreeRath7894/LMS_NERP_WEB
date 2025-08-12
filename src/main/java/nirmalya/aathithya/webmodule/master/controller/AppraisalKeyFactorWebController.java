package nirmalya.aathithya.webmodule.master.controller;

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
import nirmalya.aathithya.webmodule.grc.model.noiseMonitorReportModel;
import nirmalya.aathithya.webmodule.master.model.AppraisalKeyFactorModel;

@Controller
@RequestMapping(value = "master")
public class AppraisalKeyFactorWebController {
	Logger logger = LoggerFactory.getLogger(AppraisalKeyFactorWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping("/appraisal-keyfactor")
	public String viewAppraisalPage(Model model, HttpSession session) {
		logger.info("Method : Appraisal Page starts");
		String userId = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] year = restTemplate.getForObject(
					env.getMasterUrl() + "getYearLists?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userRole", userRole);
		
		logger.info("Appraisal Page End");
		return "master/AppraisalKeyFactors";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("appraisal-keyfactor-data")
	public @ResponseBody Object getAppraisalData(HttpSession session) {
		logger.info("Method :Get AppraisalData starts");
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
					env.getMasterUrl() + "rest-getAppraisalData?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :Get AppraisalData ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "appraisal-keyfactor-add" })
	public @ResponseBody JsonResponse<Object> appraisalAdd(@RequestBody List<AppraisalKeyFactorModel> av,
			HttpSession session) {
		logger.info("Method : appraisalAdd function starts"+av);
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
		for (AppraisalKeyFactorModel m : av) {
			m.setUserId(userId);
			m.setOrgName(organization);
			m.setOrgDiv(orgDivision);

		}
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-appraisalKeyfactorAdd", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : appraisalAdd function Ends"+resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("appraisal-keyfactor-view")
	public @ResponseBody Object viewAppraisalData(HttpSession session) {
		logger.info("Method :Get viewAppraisalData starts");
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
					env.getMasterUrl() + "rest-viewAppraisalData?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :Get viewAppraisalData ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("appraisal-keyfactor-edit")
	public @ResponseBody Object editAppraisalData(HttpSession session, @RequestParam String id) {
		logger.info("Method :Get editAppraisalData starts");
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
					env.getMasterUrl() + "rest-editAppraisalData?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :Get editAppraisalData ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("appraisal-keyfactor-approve")
	public @ResponseBody Object approveAppraisal(HttpSession session, @RequestParam String id) {
		logger.info("Method :Get approveAppraisal starts");
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
					env.getMasterUrl() + "rest-approveAppraisal?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :Get approveAppraisal ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("appraisal-keyfactor-assignEmployeeList")
	public @ResponseBody Object getAssignEmployeeList(HttpSession session, @RequestParam String id) {
		logger.info("Method :Get getAssignEmployeeList starts");
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
					env.getMasterUrl() + "rest-getAssignEmployeeList?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :Get getAssignEmployeeList ends");
		return resp;
	}



	@SuppressWarnings("unchecked")
	@PostMapping("appraisal-keyfactor-employeeAssign")
	public @ResponseBody JsonResponse<Object> assignEmployee(
	    @RequestBody List<Object> assignValue, HttpSession session) {
	  
	    logger.info("Method : assignEmployee starts");
	
	    JsonResponse<Object> resp = new JsonResponse<>();
	    String orgName = "";
	    String orgDivision = "";
	    String 	userId = "";
	
	    try {
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	        userId = (String) session.getAttribute("USER_ID");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    try {
	       // List<String> employeeIdList = (List<String>) assignValue.get(0);
	        String categoryId = (String) assignValue.get(1);
	        String staffId = (String) assignValue.get(0);
	
	        logger.info("Employee IDs: " + staffId);
	        logger.info("Category ID: " + categoryId);
	
	        resp = restTemplate.getForObject(
	            env.getMasterUrl() + "rest-employeeAssign?orgName=" + orgName + 
	            "&orgDivision=" + orgDivision + "&categoryId=" + categoryId +"&staffId=" + staffId + "&userId=" + userId,
	            JsonResponse.class);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    logger.info("Method : assignEmployee ends");
	    return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("appraisal-keyfactor-assignView")
	public @ResponseBody Object assignEmployeeView(HttpSession session,@RequestParam String id) {
		logger.info("Method :Get assignEmployeeView starts");
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
					env.getMasterUrl() + "rest-assignEmployeeView?orgName=" + orgName + "&orgDivision=" + orgDivision + "&categoryId=" + id,
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
		logger.info("Method :Get assignEmployeeView ends");
		return resp;
	}

}