package nirmalya.aathithya.webmodule.grc.controller;

import java.util.ArrayList;
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
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.grc.model.SafetyIdentificationWebModel;

@Controller
@RequestMapping(value = { "grc/" })
public class SafetyIdentificationWebController {

	Logger logger = LoggerFactory.getLogger(SafetyIdentificationWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SafetyIdentificationWebController identification;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "safety-identification" })
	public String SafetyIdentification(Model model, HttpSession session) {
		logger.info("Method : SafetyIdentification starts");

		logger.info("Method : SafetyIdentification ends");
		return "grc/ehs_safety_identification";
	}
	// view-project
	
		@SuppressWarnings("unchecked")
		@GetMapping("safety-identification-view-projects")
		public @ResponseBody List<SafetyIdentificationWebModel> viewProjectCreation(HttpSession session) {
			logger.info("Method : viewProjectCreation starts");

			JsonResponse<List<SafetyIdentificationWebModel>> resp = new JsonResponse<List<SafetyIdentificationWebModel>>();
			List<SafetyIdentificationWebModel> returnList = new ArrayList<SafetyIdentificationWebModel>();
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
// view-category

	@SuppressWarnings("unchecked")
	@GetMapping("safety-identification-view-category")
	public @ResponseBody List<SafetyIdentificationWebModel> viewCategory(HttpSession session) {
		logger.info("Method : viewCategory starts");

		JsonResponse<List<SafetyIdentificationWebModel>> resp = new JsonResponse<List<SafetyIdentificationWebModel>>();
		List<SafetyIdentificationWebModel> returnList = new ArrayList<SafetyIdentificationWebModel>();
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
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewCategoryss?id=" + userId+"&org="+organization+"&orgDiv="+orgDivision,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewCategory ends"+returnList);
		return returnList;
	}
	// add
	@SuppressWarnings("unchecked")
	@PostMapping("safety-identification-add-data")
	public @ResponseBody JsonResponse<Object> addSafetyIdentification(HttpSession session,
			@RequestBody List<SafetyIdentificationWebModel> model) {
		logger.info("Method : addSafetyIdentification starts   " + model);
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
		for(SafetyIdentificationWebModel m : model) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}
		logger.info("Method : addSafetyIdentification data to add"+model);
		try {
				resp = restClient.postForObject(env.getGrcUrl() + "rest-addSafetyIdentification", model,
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

		logger.info("Method : addSafetyIdentification ends");

		return resp;
	}
	// view

		@SuppressWarnings("unchecked")
		@GetMapping("safety-identification-view")
		public @ResponseBody List<SafetyIdentificationWebModel> viewSafety(@RequestParam String id,HttpSession session) {
			logger.info("Method : viewSafety starts"+id);

			JsonResponse<List<SafetyIdentificationWebModel>> resp = new JsonResponse<List<SafetyIdentificationWebModel>>();
			List<SafetyIdentificationWebModel> returnList = new ArrayList<SafetyIdentificationWebModel>();
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
				resp = restClient.getForObject(env.getGrcUrl() + "rest-viewSafety?id="+ id + "&userId=" + userId+"&org="+organization+"&orgDiv="+orgDivision,
						JsonResponse.class);
				returnList = resp.getBody();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewSafety ends"+returnList);
			return returnList;
		}
		// delete 
			@SuppressWarnings("unchecked")
			@GetMapping("safety-identification-delete")
			public @ResponseBody JsonResponse<Object> deleteSafety(@RequestParam String id, HttpSession session) {
				logger.info("Method : deleteSafety function starts" + id);
				JsonResponse<Object> res = new JsonResponse<Object>();
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
					res = restClient.getForObject(env.getGrcUrl() + "rest-deleteSafety?id=" + id +"&userId=" + userId+
							"&org=" +organization+"&orgDiv="+orgDivision,
							JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				logger.info("Method : deleteSafety function Ends");
				return res;
		}
	
}
