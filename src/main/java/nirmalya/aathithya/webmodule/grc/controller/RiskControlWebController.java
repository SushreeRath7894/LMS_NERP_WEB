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
import nirmalya.aathithya.webmodule.grc.model.RiskControlWebModel;

@Controller
@RequestMapping(value = { "grc/" })
public class RiskControlWebController {

	Logger logger = LoggerFactory.getLogger(RiskControlWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RiskControlWebController riskControlWebController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/risk-control" })
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
		
		try {

			DropDownModel[] department = restTemplate.getForObject(env.getRecruitment() + "DepartmentList",
					DropDownModel[].class);
			List<DropDownModel> DepartmentList = Arrays.asList(department);
			model.addAttribute("DepartmentList", DepartmentList);
			logger.info("DepartmentList"+DepartmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
			logger.info("unitList"+unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : vendorDetails ends");
		return "grc/risk-control";
	}

	/*
	 * view risk details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("risk-control-view-throtugh-ajax")
	public @ResponseBody Object viewRiskdetailsForControl(HttpSession session) {
		logger.info("Method :viewRiskdetailsForMiigationPlan starts");
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "viewRiskdetailsForControl?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewRiskdetailsForControl ends" + resp);
		return resp;

	}

	// add

	@SuppressWarnings("unchecked")
	@PostMapping("/risk-control-details-save")
	public @ResponseBody JsonResponse<Object> saveControlDetails(
			@RequestBody RiskControlWebModel RiskControlWebModel, HttpSession session) {
		logger.info("Method : saveControlDetails starts");

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
		RiskControlWebModel.setCreatedBy(userId);
		RiskControlWebModel.setOrganizationName(organization);
		RiskControlWebModel.setOrganizationDivision(orgDivision);
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "saveControlDetails", RiskControlWebModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : saveControlDetails ends");
		return resp;
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("risk-control-edit-for-control")
	public @ResponseBody Object editControlData(@RequestParam String id,String impactMitigationId,String riskIdentificationId, HttpSession session) {
		logger.info("Method :editControlData starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-editControlData?id=" + id + "&impactMitigationId=" + impactMitigationId + "&riskIdentificationId=" + riskIdentificationId + "&orgName=" + orgName
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
		logger.info("Method :editControlData ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "risk-control-get-RequisitionList" })
	public @ResponseBody JsonResponse<Object> RequisitionList(@RequestParam String id,HttpSession session) {
		logger.info("Method : RequisitionList starts"+ id);
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
		logger.info("Method : orgName starts"+ orgName);
		logger.info("Method : orgDivision starts"+ orgDivision);
		try {		
		
			resp = restTemplate.getForObject(env.getGrcUrl() + "RequisitionList?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : RequisitionList ends");
		return resp;
	}
}
