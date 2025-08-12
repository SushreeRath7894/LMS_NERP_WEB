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
import nirmalya.aathithya.webmodule.grc.model.IncidentCasualAnalysisModel;

@Controller
@RequestMapping(value = "/grc")
public class IncidentCasualAnalysisController {

	Logger logger = LoggerFactory.getLogger(IncidentCasualAnalysisController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/incident-casual-analysis")
	public String AnalysisPage(Model model, HttpSession session) {
		logger.info("Method : Analysis Page method starts");

		logger.info("Method : Analysis Page method ends");
		return "grc/incident-casual-analysis.html";
	}

	/* view */

	@SuppressWarnings("unchecked")

	@GetMapping("incident-casual-analysis-view")
	public @ResponseBody List<IncidentCasualAnalysisModel> viewCasualAnalysis(HttpSession session) {

		logger.info("Method : view Start");

		JsonResponse<List<IncidentCasualAnalysisModel>> resp = new JsonResponse<List<IncidentCasualAnalysisModel>>();

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
			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-view-casual-analysis?uId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<IncidentCasualAnalysisModel> viewCasualAnalysis = mapper.convertValue(resp.getBody(),
				new TypeReference<List<IncidentCasualAnalysisModel>>() {
				});

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : view ends");
		return viewCasualAnalysis;

	}

	/* edit */

	@SuppressWarnings("unchecked")

	@GetMapping("/incident-casual-analysis-edit")
	public @ResponseBody JsonResponse<IncidentCasualAnalysisModel> editIncidentCasualAnalysis(@RequestParam String Id,
			HttpSession session) {

		logger.info("Method : editIncidentCasualAnalysis starts");
		JsonResponse<IncidentCasualAnalysisModel> jsonResponse = new JsonResponse<IncidentCasualAnalysisModel>();
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
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "rest-edit-casual-analysis?id=" + Id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		IncidentCasualAnalysisModel Model = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<IncidentCasualAnalysisModel>() {
				});

		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editIncidentCasualAnalysis ends");
		return jsonResponse;
	}

	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "incident-casual-analysis-add" })
	public @ResponseBody JsonResponse<Object> addIncidentCasualAnalysis(HttpSession session,
			@RequestBody IncidentCasualAnalysisModel data) {
		logger.info("Method : addIncidentCasualAnalysis starts");

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
			res = restTemplate.postForObject(env.getGrcUrl() + "rest-add-casual-analysis", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addIncidentCasualAnalysis ends");
		return res;

	}

}
