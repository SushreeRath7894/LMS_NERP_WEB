package nirmalya.aathithya.webmodule.grc.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.grc.model.IncidentCorrectiveActionModel;

@Controller
@RequestMapping(value = "/grc")
public class IncidentCorrectiveActionController {

	Logger logger = LoggerFactory.getLogger(IncidentCorrectiveActionController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/incident-corrective-action")
	public String ActionPage(Model model, HttpSession session) {
		logger.info("Method : Action Page method starts");

		logger.info("Method : Action Page method ends");
		return "grc/incident-corrective-action.html";
	}

	/* view */

	@SuppressWarnings("unchecked")

	@GetMapping("incident-corrective-action-view")
	public @ResponseBody List<IncidentCorrectiveActionModel> viewCorrectiveAction(HttpSession session) {

		logger.info("Method : view Start");

		JsonResponse<List<IncidentCorrectiveActionModel>> resp = new JsonResponse<List<IncidentCorrectiveActionModel>>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-view-corrective-action?uId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<IncidentCorrectiveActionModel> viewCorrectiveAction = mapper.convertValue(resp.getBody(),
				new TypeReference<List<IncidentCorrectiveActionModel>>() {
				});

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : view ends");
		return viewCorrectiveAction;

	}

	/* edit */

	@SuppressWarnings("unchecked")

	@GetMapping("/incident-corrective-action-edit")
	public @ResponseBody JsonResponse<IncidentCorrectiveActionModel> editIncidentCorrectiveAction(
			@RequestParam String Id, HttpSession session) {

		logger.info("Method : editIncidentCorrectiveAction starts");
		JsonResponse<IncidentCorrectiveActionModel> jsonResponse = new JsonResponse<IncidentCorrectiveActionModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "rest-edit-corrective-action?id=" + Id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		IncidentCorrectiveActionModel Model = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<IncidentCorrectiveActionModel>() {
				});

		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editIncidentCorrectiveAction ends");
		return jsonResponse;
	}

	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "incident-corrective-action-add" })
	public @ResponseBody JsonResponse<Object> addIncidentCorrectiveAction(HttpSession session,
			@RequestBody IncidentCorrectiveActionModel data) {
		logger.info("Method : addIncidentCorrectiveAction starts");

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
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);
		try {
			res = restTemplate.postForObject(env.getGrcUrl() + "rest-add-corrective-action", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addIncidentCorrectiveAction ends");
		return res;

	}

}
