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
import nirmalya.aathithya.webmodule.grc.model.SafetyPlanningWebModel;

@Controller
@RequestMapping(value = { "grc/" })
public class SafetyPlanningWebController {

	Logger logger = LoggerFactory.getLogger(SafetyPlanningWebController.class);

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

	@GetMapping(value = { "safety-planning" })
	public String SafetyPlanning(Model model, HttpSession session) {
		logger.info("Method : SafetyPlanning starts");

		logger.info("Method : SafetyPlanning ends");
		return "grc/ehs_safety_planning";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("safety-planning-view-projects")
	public @ResponseBody List<SafetyPlanningWebModel> viewProjectCreation(HttpSession session) {
		logger.info("Method : viewProjectCreation starts");

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
		logger.info("Method :viewProjectCreation ends" + returnList);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("safety-planning-view")
	public @ResponseBody List<SafetyPlanningWebModel> viewSafetyAssess(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewSafetyAssess starts" + id);

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
		logger.info("Method :viewSafetyAssess ends" + returnList);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("safety-planning-getData")
	public @ResponseBody JsonResponse<Object> getSafetyPlanning(@RequestBody SafetyPlanningWebModel model,
			HttpSession session) {
		logger.info("Method : getSafetyPlanning starts" + model);

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
			resp = restClient.getForObject(env.getGrcUrl() + "rest-getSafetyPlanning?id=" + model.getSafetyId()
					+ "&userId=" + userId + "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getSafetyPlanning ends" + resp);
		return resp;
	}

	// add
	@SuppressWarnings("unchecked")
	@PostMapping("safety-planning-add")
	public @ResponseBody JsonResponse<Object> addSafetyPlanning(HttpSession session,
			@RequestBody SafetyPlanningWebModel model) {
		logger.info("Method : addSafetyPlanning starts   " + model);
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

		logger.info("Method : addSafetyAssess data to add" + model);
		try {
			resp = restClient.postForObject(env.getGrcUrl() + "rest-addSafetyPlanning", model, JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("UnSuccess");
		}

		logger.info("Method : addSafetyPlanning ends" + resp);

		return resp;
	}

	// viewwww
	@SuppressWarnings("unchecked")
	@GetMapping("safety-planning-view-action")
	public @ResponseBody List<SafetyPlanningWebModel> viewSafetyPlanning(@RequestParam String id,
			@RequestParam String pId, HttpSession session, Model model) {
		logger.info("Method : viewSafetyPlanning starts" + id + "@@@ " + pId);

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
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewSafetyPlanning?id=" + id + "&pid=" + pId
					+ "&userId=" + userId + "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSafetyPlanning ends" + returnList);
		return returnList;
	}

	// edit
	@SuppressWarnings("unchecked")
	@PostMapping("safety-planning-edit")
	public @ResponseBody JsonResponse<Object> editSafetyPlanning(@RequestBody SafetyPlanningWebModel model,
			HttpSession session) {
		logger.info("Method : editSafetyPlanning starts" + model);

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
			resp = restClient.getForObject(env.getGrcUrl() + "rest-editSafetyPlanning?id=" + model.getActionId()
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

		logger.info("Method : editSafetyPlanning ends" + resp);
		return resp;
	}

	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("safety-planning-delete")
	public @ResponseBody JsonResponse<Object> deleteSafetyPlanning(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteSafetyPlanning function starts" + id);
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
			res = restClient.getForObject(env.getGrcUrl() + "rest-deleteSafetyPlanning?id=" + id + "&userId=" + userId
					+ "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteSafetyPlanning function Ends");
		return res;
	}

	// autosearch-owner
	@SuppressWarnings("unchecked")
	@GetMapping("safety-planning-autosearch-owner")
	public @ResponseBody JsonResponse<DropDownModel> getOwnerAutoSearchList(Model model,
			@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getOwnerAutoSearchList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getGrcUrl() + "rest-getOwnerAutoSearchList?id=" + searchValue,
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
		logger.info("Method : getOwnerAutoSearchList ends");
		return res;
	}
}
