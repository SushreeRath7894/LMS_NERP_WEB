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

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("his")
public class HISOPDPatientListingController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HISOPDPatientListingController.class);

	@GetMapping(value = { "/opd-patient-listing" })
	public String viewPatientListing() {
		logger.info("Method : viewPatientListing starts");

		logger.info("Method : viewPatientListing ends");
		return "his/OPD-Listing";
	}

	@GetMapping(value = { "/emergency-patient-listing" })
	public String viewEmergencyPatient() {
		logger.info("Method : viewEmergencyPatient starts");

		logger.info("Method : viewEmergencyPatient ends");
		return "his/emergency-patient-listing";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("opd-listing-getPatientList")
	public @ResponseBody JsonResponse<HISPatientModel> getPatientList(HttpSession session,
			@RequestParam String userId) {

		logger.info("Method : getPatientList starts");

		JsonResponse<HISPatientModel> resp = new JsonResponse<HISPatientModel>();

		try {
			resp = restClient.getForObject(env.getHisUrl() + "getListingPatientList?userId=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("Success")) {
			resp.setMessage("Success");
		} else {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : getPatientList ends");
		System.out.println("VIEWWWW" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("emergency-listing-getPatientList")
	public @ResponseBody JsonResponse<HISPatientModel> getEmergencyPatientList(HttpSession session,
			@RequestParam String userId) {

		logger.info("Method : getEmergencyPatientList starts");

		JsonResponse<HISPatientModel> resp = new JsonResponse<HISPatientModel>();

		try {
			resp = restClient.getForObject(env.getHisUrl() + "getEmergencyPatient?userId=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("Success")) {
			resp.setMessage("Success");
		} else {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : getEmergencyPatientList ends");
		System.out.println("VIEWWWW" + resp);

		return resp;
	}

	// opd-time-slot
	@GetMapping(value = { "/opd-time-slot" })
	public String viewOPDTimeSlot() {
		logger.info("Method : viewOPDTimeSlot starts here...");

		logger.info("Method : viewOPDTimeSlot ends here...");
		return "his/opd-time-slot";
//		return "his/opd-management";
	}

	// view-opd-time-lists
	@SuppressWarnings("unchecked")
	@GetMapping("view-opd-time-lists")
	public @ResponseBody Object viewOpdTimeLists(HttpSession session, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		logger.info("Method: viewOpdTimeLists starts here...");
		JsonResponse<Object> response = new JsonResponse<>();
		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			if (userId != null && orgName != null && orgDivision != null) {
				response = restClient.getForObject(
						env.getHisUrl() + "rest-viewOpdTimeSlots?orgName=" + orgName + "&orgDivision=" + orgDivision
								+ "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
						JsonResponse.class);
				logger.info("response object == \n" + response);
			} else {
				response.setCode("Missing session attributes");
				response.setMessage("Failure");
			}
		} catch (Exception e) {
			logger.error("An error occurred while processing Method : viewOpdTimeLists", e);
			response.setCode("Internal error");
			response.setMessage("Failure");
		}

		assert response != null;
		if ("Failure".equals(response.getMessage())) {
			logger.info("Method: viewOpdTimeLists ends here with failure. Response: \n" + response);
		} else {
			logger.info("Method: viewOpdTimeLists ends here with success. Response: \n" + response);
		}

		return response;
	}
	
	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "opd-time-slot-add" })
	public @ResponseBody JsonResponse<Object> addOpdTimeSlot(HttpSession session, @RequestBody HISPatientModel data) {
		logger.info("Method : addOpdTimeSlot starts");

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
		data.setDoctor(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		System.out.println("detailsss ===" + data);
		try {
			res = restClient.postForObject(env.getHisUrl() + "rest-opd-time-add", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addOpdTimeSlot ends" + res);
		return res;

	}

}