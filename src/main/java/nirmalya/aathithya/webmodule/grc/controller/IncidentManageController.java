package nirmalya.aathithya.webmodule.grc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import nirmalya.aathithya.webmodule.grc.model.IncidentManageModel;
import nirmalya.aathithya.webmodule.grc.model.IncidentReportingModel;

@Controller
@RequestMapping(value = "/grc")
public class IncidentManageController {

	Logger logger = LoggerFactory.getLogger(IncidentManageController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/incident-manage")
	public String ManagePage(Model model, HttpSession session) {
		logger.info("Method : Manage Page method starts");

		logger.info("Method : Manage Page method ends");
		return "grc/incident-manage.html";
	}

	/* master-view */

	@SuppressWarnings("unchecked")

	@GetMapping("/incident-manage-view-master")
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

	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "incident-manage-add" })
	public @ResponseBody JsonResponse<Object> addIncidentManage(HttpSession session,
			@RequestBody IncidentManageModel data) {
		logger.info("Method : addIncidentManage starts");

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
			res = restTemplate.postForObject(env.getGrcUrl() + "incident-manage-rest-add", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addIncidentManage ends");
		return res;

	}

	/*
	 * AssignTo autoSearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "incident-manage-get-assign-list" })
	public @ResponseBody JsonResponse<IncidentManageModel> getAssignToAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getAssignToAutoSearchList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<IncidentManageModel> res = new JsonResponse<IncidentManageModel>();

		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "rest-getAssignToAutoSearchList?id=" + searchValue
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getAssignToAutoSearchList ends");
		return res;
	}

	/* view */

	@SuppressWarnings("unchecked")

	@GetMapping("/incident-manage-view")
	public @ResponseBody List<IncidentManageModel> viewIncidentManage(@RequestParam String id, HttpSession session) {

		logger.info("Method : view Start");

		JsonResponse<List<IncidentManageModel>> resp = new JsonResponse<List<IncidentManageModel>>();

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
			resp = restTemplate.getForObject(env.getGrcUrl() + "incident-manage-rest-view?uId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<IncidentManageModel> viewIncidentManage = mapper.convertValue(resp.getBody(),
				new TypeReference<List<IncidentManageModel>>() {
				});

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : view ends");
		return viewIncidentManage;

	}

	/* edit */

	@SuppressWarnings("unchecked")

	@GetMapping("/incident-manage-edit")
	public @ResponseBody JsonResponse<IncidentManageModel> editIncidentManage(@RequestParam String Id,
			HttpSession session) {

		logger.info("Method : editIncidentManage starts");
		JsonResponse<IncidentManageModel> jsonResponse = new JsonResponse<IncidentManageModel>();
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
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "incident-manage-rest-edit?id=" + Id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		IncidentManageModel Model = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<IncidentManageModel>() {
				});

		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editIncidentManage ends");
		return jsonResponse;
	}

	/* delete */

	@SuppressWarnings("unchecked")

	@GetMapping("incident-manage-delete")
	public @ResponseBody JsonResponse<Object> deleteIncidentManage(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteIncidentManage function starts");

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
			res = restTemplate.getForObject(env.getGrcUrl() + "incident-manage-rest-delete?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteIncidentManage function Ends");

		return res;
	}

}
