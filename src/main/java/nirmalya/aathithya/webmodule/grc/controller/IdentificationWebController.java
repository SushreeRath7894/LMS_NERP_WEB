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
import nirmalya.aathithya.webmodule.grc.model.RiskIdentificationWebModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseIndentModel;

@Controller
@RequestMapping(value = { "grc/" })
public class IdentificationWebController {

	Logger logger = LoggerFactory.getLogger(IdentificationWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	IdentificationWebController identificationWebController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/risk-identification" })
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
		return "grc/risk-identification";
	}

	/*
	 * view identification
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-identification-riskdetails-view-through-ajax")
	public @ResponseBody Object viewIdentification(HttpSession session) {
		logger.info("Method :viewIdentification starts");
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewIdentification?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewIdentification ends" + resp);
		return resp;

	}

	// get edit data

	@SuppressWarnings("unchecked")
	@GetMapping("risk-identification-edit")
	public @ResponseBody Object editIdentificationData(@RequestParam String id, HttpSession session) {
		logger.info("Method :editIdentificationData starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-editIdentificationData?id=" + id + "&orgName="
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
		logger.info("Method :editIdentificationData ends");
		return resp;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("risk-identification-add")
	public @ResponseBody JsonResponse<Object> addIdentificationDetails(HttpSession session,
			@RequestBody List<RiskIdentificationWebModel> riskIdentificationWebModel) {
		logger.info("Method : addIdentificationDetails starts");

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
		for (RiskIdentificationWebModel m : riskIdentificationWebModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}
		try {

			resp = restTemplate.postForObject(env.getGrcUrl() + "addIdentificationDetails",
					riskIdentificationWebModel, JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addIdentificationDetails ends");

		return resp;
	}

	/*
	 * view project details with identification
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-identification-view-project-Details")
	public @ResponseBody Object viewIdentificationProjectDetails(HttpSession session) {
		logger.info("Method :viewIdentificationProjectDetails starts");
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewIdentificationProjectDetails?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewIdentificationProjectDetails ends" + resp);
		return resp;

	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("risk-identification-delete")
	public @ResponseBody JsonResponse<Object> deleteIdentificationDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteIdentificationDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "deleteIdentificationDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteIdentificationDetails function Ends");

		return res;
	}
	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("risk-identification-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveIdentificationDetails(HttpSession session,
			@RequestParam String approveStatus, String identificationId) {

		logger.info("Method : approveIdentificationDetails starts");
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
		logger.info("APRRRVO" + identificationId);
		try {
			response = restTemplate
					.getForObject(
							env.getGrcUrl() + "approveIdentificationDetails?approveStatus=" + approveStatus + "&identificationId="
									+ identificationId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);

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
		logger.info("Method : approveIdentificationDetails ends");
		return response;
	}
}
