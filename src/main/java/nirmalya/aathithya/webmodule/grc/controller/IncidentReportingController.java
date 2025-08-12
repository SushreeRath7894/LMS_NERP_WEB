package nirmalya.aathithya.webmodule.grc.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.grc.model.IncidentReportingModel;

@Controller
@RequestMapping(value = "/grc")
public class IncidentReportingController {

	Logger logger = LoggerFactory.getLogger(IncidentReportingController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/incident-reporting")
	public String IncidentReportingPage(Model model, HttpSession session) {
		logger.info("Method : IncidentReporting Page method starts");

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getGrcUrl() + "rest-getType", DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("typeList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : IncidentReporting Page method ends");
		return "grc/incident-reporting.html";
	}

	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "incident-reporting-add" })
	public @ResponseBody JsonResponse<Object> addIncidentReportingMaster(HttpSession session,
			@RequestBody IncidentReportingModel data) {
		logger.info("Method : addIncidentReportingMaster starts");

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
			res = restTemplate.postForObject(env.getGrcUrl() + "incident-reporting-rest-add", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addIncidentReportingMaster ends");
		return res;

	}

	/* view */

	@SuppressWarnings("unchecked")

	@GetMapping("/incident-reporting-view")
	public @ResponseBody List<IncidentReportingModel> viewReportingMaster(HttpSession session) {

		logger.info("Method : view Start");

		JsonResponse<List<IncidentReportingModel>> resp = new JsonResponse<List<IncidentReportingModel>>();

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
			resp = restTemplate.getForObject(env.getGrcUrl() + "incident-reporting-rest-view?uId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<IncidentReportingModel> viewReportingMaster = mapper.convertValue(resp.getBody(),
				new TypeReference<List<IncidentReportingModel>>() {
				});

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : view ends");
		return viewReportingMaster;

	}

	/* edit */

	@SuppressWarnings("unchecked")

	@GetMapping("/incident-reporting-edit")
	public @ResponseBody JsonResponse<IncidentReportingModel> editIncidentReporting(@RequestParam String Id,
			HttpSession session) {

		logger.info("Method : editIncidentReporting starts");
		JsonResponse<IncidentReportingModel> jsonResponse = new JsonResponse<IncidentReportingModel>();
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
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "incident-reporting-rest-edit?id=" + Id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		IncidentReportingModel Model = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<IncidentReportingModel>() {
				});

		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editIncidentReporting ends");
		return jsonResponse;
	}

	/* delete */

	@SuppressWarnings("unchecked")

	@GetMapping("incident-reporting-delete")
	public @ResponseBody JsonResponse<Object> deleteIncidentMaster(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteIncidentMaster function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

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
			res = restTemplate.getForObject(env.getGrcUrl() + "incident-reporting-rest-delete?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteIncidentMaster function Ends");
		return res;
	}

}
