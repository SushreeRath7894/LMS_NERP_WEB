package nirmalya.aathithya.webmodule.master.controller;

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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.AppraisalKeyFactorModel;

@Controller
@RequestMapping(value = "master")
public class SelfAppraisalKeyFactor {
	Logger logger = LoggerFactory.getLogger(SelfAppraisalKeyFactor.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping("/self-appraisal-keyfactor")
	public String viewAppraisalPage(Model model, HttpSession session) {
		logger.info("Method : Appraisal Page starts");
		String userId = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userRole", userRole);
		
		logger.info("Appraisal Page End");
		return "master/selfAppraisalKeyFactor";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("self-appraisal-keyfactor-data")
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
					env.getMasterUrl() + "rest-getSelfAppraisalData?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
	@PostMapping(value = { "self-appraisal-keyfactor-add" })
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-SelfappraisalKeyfactorAdd", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : appraisalAdd function Ends"+resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("self-appraisal-keyfactor-view")
	public @ResponseBody Object viewAppraisalData(HttpSession session,@RequestParam String userid) {
		logger.info("Method :Get viewAppraisalData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId1 = "";
		try {
			userId1 = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewSelfAppraisalData?orgName=" + orgName + "&orgDivision=" + orgDivision + "&userid=" + userId1,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		logger.info("Method :Get viewAppraisalData ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("self-appraisal-keyfactor-edit")
	public @ResponseBody Object editAppraisalData(HttpSession session, @RequestParam String id,@RequestParam String assignid) {
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
					env.getMasterUrl() + "rest-editSelfAppraisalData?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id + "&assignid=" +assignid,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :Get editAppraisalData ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("self-appraisal-keyfactor-approve")
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
					env.getMasterUrl() + "rest-approveSelfAppraisal?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :Get approveAppraisal ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("self-appraisal-keyfactor-assignEmployeeList")
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
					env.getMasterUrl() + "rest-getSelfAssignEmployeeList?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :Get getAssignEmployeeList ends");
		return resp;
	}



	@SuppressWarnings("unchecked")
	@PostMapping("self-appraisal-keyfactor-employeeAssign")
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
	        List<String> employeeIdList = (List<String>) assignValue.get(0);
	        String categoryId = (String) assignValue.get(1);
	
	        logger.info("Employee IDs: " + employeeIdList);
	        logger.info("Category ID: " + categoryId);
	
	        resp = restTemplate.getForObject(
	            env.getMasterUrl() + "rest-SelfemployeeAssign?orgName=" + orgName + 
	            "&orgDivision=" + orgDivision + "&categoryId=" + categoryId +"&empId=" + employeeIdList + "&userId=" + userId,
	            JsonResponse.class);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    logger.info("Method : assignEmployee ends");
	    return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("self-appraisal-keyfactor-assignView")
	public @ResponseBody Object assignEmployeeView(HttpSession session) {
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
					env.getMasterUrl() + "rest-SelfassignEmployeeView?orgName=" + orgName + "&orgDivision=" + orgDivision,
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