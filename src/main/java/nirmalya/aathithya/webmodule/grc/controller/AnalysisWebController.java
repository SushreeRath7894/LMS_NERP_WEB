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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.grc.model.AnalysisWebModel;
import nirmalya.aathithya.webmodule.grc.model.RiskIdentificationWebModel;

@Controller
@RequestMapping(value = { "grc/" })
public class AnalysisWebController {

	Logger logger = LoggerFactory.getLogger(AnalysisWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	AnalysisWebController analysisWebController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/risk-analysis" })
	public String vendorDetails(Model model, HttpSession session) {
		logger.info("Method : vendorDetails starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : vendorDetails ends");
		return "grc/analysis";
	}

	/*
	 * view risk details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-analysis-riskdetails-view-through-ajax")
	public @ResponseBody Object viewanalysisriskdetails(HttpSession session) {
		logger.info("Method :viewanalysisriskdetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewIdentification?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewanalysisriskdetails ends" + resp);
		return resp;

	}

	/*
	 * Add main
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("risk-analysis-add")
	public @ResponseBody JsonResponse<Object> addAnalysisDetails(HttpSession session,
			@RequestBody List<AnalysisWebModel> analysisWebModel) {
		logger.info("Method : addAnalysisDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		for (AnalysisWebModel m : analysisWebModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}
		try {

			resp = restTemplate.postForObject(env.getGrcUrl() + "addAnalysisDetails", analysisWebModel,
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

		logger.info("Method : addAnalysisDetails ends");

		return resp;
	}

	// get edit data

	@SuppressWarnings("unchecked")
	@GetMapping("risk-analysis-edit")
	public @ResponseBody Object editAnalysisData(@RequestParam String id, HttpSession session) {
		logger.info("Method :editAnalysisData starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-editAnalysisData?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("edit>>>-----" + resp);
		logger.info("Method :editAnalysisData ends");
		return resp;
	}

	/*
	 * view main
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-analysis-view-all-Details")
	public @ResponseBody Object viewaAllDetails(HttpSession session) {
		logger.info("Method :viewaAllDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewaAllDetails?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewaAllDetails ends" + resp);
		return resp;

	}

// add 

	@SuppressWarnings("unchecked")
	@PostMapping("/risk-analysis-Riskdetails-save")
	public @ResponseBody JsonResponse<Object> saveRiskdetails(@RequestBody AnalysisWebModel analysisWebModel,
			HttpSession session) {
		logger.info("Method : saveRiskdetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		// String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			// dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		analysisWebModel.setCreatedBy(userId);
		analysisWebModel.setOrganizationName(organization);
		analysisWebModel.setOrganizationDivision(orgDivision);
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "saveRiskdetails", analysisWebModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : saveRiskdetails ends");
		return resp;
	}

//add 

	@SuppressWarnings("unchecked")
	@PostMapping("/risk-analysis-impact-details-save")
	public @ResponseBody JsonResponse<Object> saveImpactDetails(@RequestBody AnalysisWebModel analysisWebModel,
			HttpSession session) {
		logger.info("Method : saveImpactDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		// String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			// dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		analysisWebModel.setCreatedBy(userId);
		analysisWebModel.setOrganizationName(organization);
		analysisWebModel.setOrganizationDivision(orgDivision);
		logger.info("Method"+analysisWebModel.getAnalysisId());
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "saveImpactDetails", analysisWebModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : saveImpactDetails ends");
		return resp;
	}

	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-analysis-view-Risk-Details")
	public @ResponseBody Object viewRiskDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method :viewRiskDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewRiskDetails?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewRiskDetails ends" + resp);
		return resp;

	}

	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-analysis-view-impact-Details")
	public @ResponseBody Object viewImpactDetails(HttpSession session,@RequestParam String riskId) {
		logger.info("Method :viewImpactDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewImpactDetails?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&riskId=" + riskId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewImpactDetails ends" + resp);
		return resp;

	}
	// get edit

	@SuppressWarnings("unchecked")
	@GetMapping("risk-analysis-edit-for-impact")
	public @ResponseBody Object editImpactData(@RequestParam String id, HttpSession session) {
		logger.info("Method :editImpactData starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-editImpactData?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("edit>>>-----" + resp);
		logger.info("Method :editImpactData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("risk-analysis-getData-forImpactAndRisk")
	public @ResponseBody Object getImpactAndRiskData(@RequestParam String id, HttpSession session) {
		logger.info("Method :getImpactAndRiskData starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-getImpactAndRiskData?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("edit>>>-----" + resp);
		logger.info("Method :getImpactAndRiskData ends");
		return resp;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("risk-analysis-delete")
	public @ResponseBody JsonResponse<Object> deleteAnalysisDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteAnalysisDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "deleteAnalysisDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteAnalysisDetails function Ends");

		return res;
	}
	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("risk-analysis-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveAnalysisDetails(HttpSession session,
			@RequestParam String approveStatus, String analysisId) {

		logger.info("Method : approveAnalysisDetails starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
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
		logger.info("APRRRVO" + analysisId);
		try {
			response = restTemplate.getForObject(env.getGrcUrl() + "approveAnalysisDetails?approveStatus="
					+ approveStatus + "&analysisId=" + analysisId + "&orgName=" + orgName + "&orgDivision="
					+ orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		logger.info("response=====" + response);
		logger.info("Method : approveAnalysisDetails ends");
		return response;
	}
}
