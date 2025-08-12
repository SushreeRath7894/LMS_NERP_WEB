package nirmalya.aathithya.webmodule.his.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;
import nirmalya.aathithya.webmodule.his.model.HISTreatmentModel;

@Controller
@RequestMapping("his")
public class HISEmergencyController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HISEmergencyController.class);

	@GetMapping(value = { "/emergency" })
	public String viewEmergency() {
		logger.info("Method : viewEmergency starts");

		logger.info("Method : viewEmergency ends");
		return "his/his-emergency";
	}

	// manage-emergency-view
	@SuppressWarnings("unchecked")
	@GetMapping("manage-emergency-view")
	public @ResponseBody Object viewEmergencyDetails(HttpSession session, @RequestParam String fromdate,
			@RequestParam String todate) {
		logger.info("Method :viewEmergencyDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-emergencyDetails?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
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
		logger.info("Method :viewEmergencyDetails ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("his-emergency-approval-status")
	public @ResponseBody JsonResponse<HISPatientModel> emergencyStatus(HttpSession session,
			@RequestParam String approval, String emerId, String approvedBy) {

		logger.info("Method : emergencyStatus starts");
		JsonResponse<HISPatientModel> response = new JsonResponse<HISPatientModel>();
		try {
			response = restClient.getForObject(env.getHisUrl() + "emergencyStatus?approval=" + approval + "&emerId="
					+ emerId + "&approvedBy=" + approvedBy, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		System.out.println("response=====" + response);
		logger.info("Method : emergencyStatus ends");
		return response;
	}

	// view-opd-treatment-lists
	@SuppressWarnings("unchecked")
	@GetMapping("view-emergency-treatment-lists")
	public @ResponseBody Object viewEmergencyTreatmentLists(HttpSession session,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		logger.info("Method: viewEmergencyTreatmentLists starts here...");
		JsonResponse<Object> response = new JsonResponse<>();
		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			if (userId != null && orgName != null && orgDivision != null) {
				response = restClient.getForObject(
						env.getHisUrl() + "viewEmergencyTreatmentLists?orgName=" + orgName + "&orgDivision="
								+ orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
						JsonResponse.class);
				logger.info("response object == \n" + response);
			} else {
				response.setCode("Missing session attributes");
				response.setMessage("Failure");
			}
		} catch (Exception e) {
			logger.error("An error occurred while processing Method : viewOpdTreatmentLists", e);
			response.setCode("Internal error");
			response.setMessage("Failure");
		}

		assert response != null;
		if ("Failure".equals(response.getMessage())) {
			logger.info("Method: viewEmergencyTreatmentLists ends here with failure. Response: \n" + response);
		} else {
			logger.info("Method: viewEmergencyTreatmentLists ends here with success. Response: \n" + response);
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "his-emergency-treatment-test-add" })
	public @ResponseBody JsonResponse<Object> addemergencyTest(HttpSession session, @RequestBody HISTreatmentModel data) {
		logger.info("Method : addemergencyTest starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		System.out.println("data=======" + data);
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

		System.out.println("detailsss ===" + data);
		try {
			res = restClient.postForObject(env.getHisUrl() + "rest-emergency-test", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addemergencyTest ends" + res);
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-emergency-test-lists")
	public @ResponseBody Object viewEmergencyTestLists(HttpSession session, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		logger.info("Method: viewEmergencyTestLists starts here...");
		JsonResponse<Object> response = new JsonResponse<>();
		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			if (userId != null && orgName != null && orgDivision != null) {
				response = restClient.getForObject(
						env.getHisUrl() + "viewEmergencyTestLists?orgName=" + orgName + "&orgDivision=" + orgDivision
								+ "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
						JsonResponse.class);
				logger.info("response object == \n" + response);
			} else {
				response.setCode("Missing session attributes");
				response.setMessage("Failure");
			}
		} catch (Exception e) {
			logger.error("An error occurred while processing Method : viewOpdTestLists", e);
			response.setCode("Internal error");
			response.setMessage("Failure");
		}

		assert response != null;
		if ("Failure".equals(response.getMessage())) {
			logger.info("Method: viewEmergencyTestLists ends here with failure. Response: \n" + response);
		} else {
			logger.info("Method: viewEmergencyTestLists ends here with success. Response: \n" + response);
		}

		return response;
	}

}
