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
import nirmalya.aathithya.webmodule.grc.model.MitigationWebModel;

@Controller
@RequestMapping(value = { "grc/" })
public class MitigationWebController {

	Logger logger = LoggerFactory.getLogger(MitigationWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	MitigationWebController mitigationWebController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/risk-mitigation-plan" })
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
		return "grc/mitigation";
	}
	
	/*
	 * view risk details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-mitigation-plan-riskdetails-view-through-ajax")
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
	@PostMapping("risk-mitigation-plan-add")
	public @ResponseBody JsonResponse<Object> addMitigationDetails(HttpSession session,
			@RequestBody List<MitigationWebModel> MitigationWebModel) {
		logger.info("Method : addMitigationDetails starts");

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
		for (MitigationWebModel m : MitigationWebModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}
		try {

			resp = restTemplate.postForObject(env.getGrcUrl() + "addMitigationDetails", MitigationWebModel,
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

		logger.info("Method : addMitigationDetails ends");

		return resp;
	}

	// get edit data

	@SuppressWarnings("unchecked")
	@GetMapping("risk-mitigation-plan-edit")
	public @ResponseBody Object editMitigationData(@RequestParam String id, HttpSession session) {
		logger.info("Method :editMitigationData starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-editMitigationData?id=" + id + "&orgName=" + orgName
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
		logger.info("Method :editMitigationData ends");
		return resp;
	}

	/*
	 * view main
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-mitigation-plan-view-all-Details")
	public @ResponseBody Object viewaAllDetailsForMitigation(HttpSession session) {
		logger.info("Method :viewaAllDetailsForMitigation starts");
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewaAllDetailsForMitigation?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewaAllDetailsForMitigation ends" + resp);
		return resp;

	}

// add 

	@SuppressWarnings("unchecked")
	@PostMapping("/risk-mitigation-plan-Riskdetails-save")
	public @ResponseBody JsonResponse<Object> saveRiskdetailsForMitigationForMitigation(@RequestBody MitigationWebModel MitigationWebModel,
			HttpSession session) {
		logger.info("Method : saveRiskdetailsForMitigation starts");

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
		MitigationWebModel.setCreatedBy(userId);
		MitigationWebModel.setOrganizationName(organization);
		MitigationWebModel.setOrganizationDivision(orgDivision);
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "saveRiskdetailsForMitigation", MitigationWebModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : saveRiskdetailsForMitigation ends");
		return resp;
	}

//add 

	@SuppressWarnings("unchecked")
	@PostMapping("/risk-mitigation-plan-impact-details-save")
	public @ResponseBody JsonResponse<Object> saveImpactDetailsForMitigationForMitigation(@RequestBody MitigationWebModel MitigationWebModel,
			HttpSession session) {
		logger.info("Method : saveImpactDetailsForMitigationForMitigation starts");

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
		MitigationWebModel.setCreatedBy(userId);
		MitigationWebModel.setOrganizationName(organization);
		MitigationWebModel.setOrganizationDivision(orgDivision);
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "saveImpactDetailsForMitigation", MitigationWebModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : saveImpactDetailsForMitigation ends");
		return resp;
	}

	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-mitigation-plan-view-Risk-Details")
	public @ResponseBody Object viewRiskDetailsForMitigation(@RequestParam String id, HttpSession session) {
		logger.info("Method :viewRiskDetailsForMitigation starts");
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewRiskDetailsForMitigation?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewRiskDetailsForMitigation ends" + resp);
		return resp;

	}

	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-mitigation-plan-view-impact-Details")
	public @ResponseBody Object viewImpactDetailsForMitigation(HttpSession session,@RequestParam String riskId ) {
		logger.info("Method :viewImpactDetailsForMitigationForMitigation starts");
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewImpactDetailsForMitigation?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&riskId=" + riskId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewImpactDetailsForMitigation ends" + resp);
		return resp;

	}
	// get edit

	@SuppressWarnings("unchecked")
	@GetMapping("risk-mitigation-plan-edit-for-impact")
	public @ResponseBody Object editImpactDataForMitigationForMitigation(@RequestParam String id, HttpSession session) {
		logger.info("Method :editImpactDataForMitigation starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-editImpactDataForMitigation?id=" + id + "&orgName=" + orgName
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
		logger.info("Method :editImpactDataForMitigation ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("risk-mitigation-plan-getData-forImpactAndRisk")
	public @ResponseBody Object getImpactAndRiskDataForMitigation(@RequestParam String id, HttpSession session) {
		logger.info("Method :getImpactAndRiskDataForMitigation starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-getImpactAndRiskDataForMitigation?id=" + id + "&orgName="
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
		logger.info("Method :getImpactAndRiskDataForMitigation ends");
		return resp;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("risk-mitigation-plan-delete")
	public @ResponseBody JsonResponse<Object> deleteMitigationsDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteMitigationsDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "deleteMitigationsDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteMitigationsDetails function Ends");

		return res;
	}
	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("risk-mitigation-plan-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveMitigationDetails(HttpSession session,
			@RequestParam String approveStatus, String mitigationId) {

		logger.info("Method : approveMitigationDetails starts");
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
		logger.info("APRRRVO" + mitigationId);
		try {
			response = restTemplate.getForObject(env.getGrcUrl() + "approveMitigationDetails?approveStatus="
					+ approveStatus + "&mitigationId=" + mitigationId + "&orgName=" + orgName + "&orgDivision="
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
		logger.info("Method : approveMitigationDetails ends");
		return response;
	}
}
