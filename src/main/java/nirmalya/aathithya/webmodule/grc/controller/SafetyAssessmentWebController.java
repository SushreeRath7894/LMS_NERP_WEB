package nirmalya.aathithya.webmodule.grc.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.grc.model.SafetyAssesmentWebModel;

@Controller
@RequestMapping(value = { "grc/" })

public class SafetyAssessmentWebController {

	Logger logger = LoggerFactory.getLogger(SafetyAssessmentWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SafetyAssessmentWebController assesment;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "safety-assesment" })
	public String SafetyAssesment(Model model, HttpSession session) {
		logger.info("Method : SafetyAssesment starts");

		logger.info("Method : SafetyAssesment ends");
		return "grc/ehs_safety_assesment";
	}
	// view-project
	
			@SuppressWarnings("unchecked")
			@GetMapping("safety-assesment-view-projects")
			public @ResponseBody List<SafetyAssesmentWebModel> viewProjectCreation(HttpSession session) {
				logger.info("Method : viewProjectCreation starts");

				JsonResponse<List<SafetyAssesmentWebModel>> resp = new JsonResponse<List<SafetyAssesmentWebModel>>();
				List<SafetyAssesmentWebModel> returnList = new ArrayList<SafetyAssesmentWebModel>();
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
					resp = restClient.getForObject(env.getGrcUrl() + "rest-viewProject?id=" + userId +"&org="+organization + "&orgDiv="+ orgDivision,
							JsonResponse.class);
					returnList = resp.getBody();
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method :viewProjectCreation ends"+returnList);
				return returnList;
			}
			@SuppressWarnings("unchecked")
			@GetMapping("safety-assesment-view")
			public @ResponseBody List<SafetyAssesmentWebModel> viewSafetyAssess(@RequestParam String id,HttpSession session) {
				logger.info("Method : viewSafetyAssess starts"+id);

				JsonResponse<List<SafetyAssesmentWebModel>> resp = new JsonResponse<List<SafetyAssesmentWebModel>>();
				List<SafetyAssesmentWebModel> returnList = new ArrayList<SafetyAssesmentWebModel>();
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
					resp = restClient.getForObject(env.getGrcUrl() + "rest-viewSafetyAssess?id="+ id + "&userId=" + userId+"&org="+organization+"&orgDiv="+orgDivision,
							JsonResponse.class);
					returnList = resp.getBody();
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method :viewSafetyAssess ends"+returnList);
				return returnList;
			}
			@SuppressWarnings("unchecked")
			@PostMapping("safety-assesment-edit")
			public @ResponseBody JsonResponse<Object> editSafetyAssess(@RequestBody SafetyAssesmentWebModel model,HttpSession session) {
				logger.info("Method : editSafetyAssess starts"+ model);
				
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
					resp = restClient.getForObject(env.getGrcUrl() + "rest-editSafetyAssess?id="+ model.getSafetyId()+ "&userId=" + userId+"&org="+organization+"&orgDiv="+orgDivision,
							JsonResponse.class);
					
						
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				
				String message = resp.getMessage();
				if (message != null && message != "") {
					
				} else {
					resp.setMessage("Success");
				}
				
				logger.info("Method : editSafetyAssess ends"+resp);
				return resp;
			}
			// add
			@SuppressWarnings("unchecked")
			@PostMapping("safety-assesment-add-data")
			public @ResponseBody JsonResponse<Object> addSafetyAssess(HttpSession session,
					@RequestBody SafetyAssesmentWebModel model) {
				logger.info("Method : addSafetyAssess starts   " + model);
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
				model.setCreatedBy(userId);
				model.setOrganizationName(organization);
				model.setOrganizationDivision(orgDivision);
			
				logger.info("Method : addSafetyAssess data to add"+model);
				try {
						resp = restClient.postForObject(env.getGrcUrl() + "rest-addSafetyAssess", model,
							JsonResponse.class);

				} catch (Exception e) {

					e.printStackTrace();
				}

				if (resp.getMessage() != "" && resp.getMessage() != null) {
					resp.setCode(resp.getMessage());
					resp.setMessage("Success");
				} else {
					resp.setMessage("UnSuccess");
				}

				logger.info("Method : addSafetyAssess ends"+resp);

				return resp;
			}
}
