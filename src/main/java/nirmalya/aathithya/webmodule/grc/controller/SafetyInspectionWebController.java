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
public class SafetyInspectionWebController {

	Logger logger = LoggerFactory.getLogger(SafetyInspectionWebController.class);

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

	@GetMapping(value = { "safety-inspection" })
	public String SafetyInspection(Model model, HttpSession session) {
		logger.info("Method : SafetyInspection starts");

		logger.info("Method : SafetyInspection ends");
		return "grc/ehs_safety_inspection";
	}
	// view-project
	
				@SuppressWarnings("unchecked")
				@GetMapping("safety-inspection-view-projects")
				public @ResponseBody List<SafetyAssesmentWebModel> viewProjectInspection(HttpSession session) {
					logger.info("Method : viewProjectInspection starts");

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
					logger.info("Method :viewProjectInspection ends"+returnList);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@GetMapping("safety-inspection-view-data")
				public @ResponseBody List<SafetyAssesmentWebModel> viewDataInspection(@RequestParam String id,HttpSession session) {
					logger.info("Method : viewDataInspection starts"+id);

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
					logger.info("Method :viewDataInspection ends"+returnList);
					return returnList;
				}
}
