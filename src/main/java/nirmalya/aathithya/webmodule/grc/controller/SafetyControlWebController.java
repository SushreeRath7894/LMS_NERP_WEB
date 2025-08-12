package nirmalya.aathithya.webmodule.grc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import nirmalya.aathithya.webmodule.grc.model.SafetyControlWebModel;
import nirmalya.aathithya.webmodule.grc.model.SafetyPlanningWebModel;

@Controller
@RequestMapping(value = { "grc/" })
public class SafetyControlWebController {

	Logger logger = LoggerFactory.getLogger(SafetyControlWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SafetyPlanningWebController assesment;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "safety-control" })
	public String SafetyControl(Model model, HttpSession session) {
		logger.info("Method : SafetyControl starts");

		logger.info("Method : SafetyControl ends");
		return "grc/ehs_safety_control";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("safety-control-view-projects")
	public @ResponseBody List<SafetyPlanningWebModel> viewSafetyControlProject(HttpSession session) {
		logger.info("Method : viewSafetyControlProject starts");

		JsonResponse<List<SafetyPlanningWebModel>> resp = new JsonResponse<List<SafetyPlanningWebModel>>();
		List<SafetyPlanningWebModel> returnList = new ArrayList<SafetyPlanningWebModel>();
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
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewProject?id=" + userId + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSafetyControlProject ends" + returnList);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("safety-control-view-safety")
	public @ResponseBody List<SafetyPlanningWebModel> viewSafetyControlSafety(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewSafetyControlSafety starts" + id);

		JsonResponse<List<SafetyPlanningWebModel>> resp = new JsonResponse<List<SafetyPlanningWebModel>>();
		List<SafetyPlanningWebModel> returnList = new ArrayList<SafetyPlanningWebModel>();
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
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewSafetyAssess?id=" + id + "&userId=" + userId
					+ "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSafetyControlSafety ends" + returnList);
		return returnList;
	}

	// viewwww
	@SuppressWarnings("unchecked")
	@GetMapping("safety-control-view-action")
	public @ResponseBody List<SafetyControlWebModel> viewSafetyControl(@RequestParam String id,
			@RequestParam String pId, HttpSession session, Model model) {
		logger.info("Method : viewSafetyControl starts" + id + "@@@ " + pId);

		JsonResponse<List<SafetyControlWebModel>> resp = new JsonResponse<List<SafetyControlWebModel>>();
		List<SafetyControlWebModel> returnList = new ArrayList<SafetyControlWebModel>();
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
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewSafetyControl?id=" + id + "&pid=" + pId
					+ "&userId=" + userId + "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSafetyControl ends" + returnList);
		return returnList;
	}

	// edit
	@SuppressWarnings("unchecked")
	@PostMapping("safety-control-edit")
	public @ResponseBody JsonResponse<Object> editSafetyControl(@RequestBody SafetyControlWebModel model,
			HttpSession session) {
		logger.info("Method : editSafetyControl starts" + model);

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
			resp = restClient.getForObject(env.getGrcUrl() + "rest-editSafetyControl?id=" + model.getActionId()
					+ "&sid=" + model.getSafetyId() + "&pid=" + model.getProjectId() + "&userId=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : editSafetyControl ends" + resp);
		return resp;
	}

	// add
	@SuppressWarnings("unchecked")
	@PostMapping("safety-control-add")
	public @ResponseBody JsonResponse<Object> addSafetyControl(HttpSession session,
			@RequestBody SafetyControlWebModel model) {
		logger.info("Method : addSafetyControl starts   " + model);
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

		logger.info("Method : addSafetyControl data to add" + model);
		try {
			resp = restClient.postForObject(env.getGrcUrl() + "rest-addSafetyControl", model, JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("UnSuccess");
		}

		logger.info("Method : addSafetyControl ends" + resp);

		return resp;
	}

	// autosearch-indentNo
	@SuppressWarnings("unchecked")
	@GetMapping("safety-control-autosearch-indentNo")
	public @ResponseBody JsonResponse<DropDownModel> getIndentNoAutoSearchList(Model model,
			@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getIndentNoAutoSearchList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getGrcUrl() + "rest-getIndentNoAutoSearchList?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getIndentNoAutoSearchList ends");
		return res;
	}
}
