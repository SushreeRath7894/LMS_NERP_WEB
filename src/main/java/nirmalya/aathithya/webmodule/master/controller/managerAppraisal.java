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
public class managerAppraisal {
	
	Logger logger = LoggerFactory.getLogger(managerAppraisal.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/manager-appraisal-keyfactor")
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
		return "master/managerAppraisal";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manager-appraisal-keyfactor-view")
	public @ResponseBody Object viewManagerAppraisal(HttpSession session,@RequestParam String userid) {
		logger.info("Method :Get viewManagerAppraisal starts");
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
					env.getMasterUrl() + "rest-viewMAnagerAppraisalData?orgName=" + orgName + "&orgDivision=" + orgDivision + "&userid=" + userid,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		logger.info("Method :Get viewManagerAppraisal ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manager-appraisal-keyfactor-edit")
	public @ResponseBody Object editManagerAppraisalData(HttpSession session, @RequestParam String id,@RequestParam String assignid) {
		logger.info("Method :Get editManagerAppraisalData starts");
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
					env.getMasterUrl() + "rest-editManagerAppraisalData?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id + "&assignid=" +assignid,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :Get editManagerAppraisalData ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "manager-appraisal-keyfactor-add" })
	public @ResponseBody JsonResponse<Object> managerAppraisalAdd(@RequestBody List<AppraisalKeyFactorModel> av,
			HttpSession session) {
		logger.info("Method : managerAppraisalAdd function starts"+av);
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-managerAppraisalAdd", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : managerAppraisalAdd function Ends"+resp);
		return resp;
	}

}
