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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.RegsPatientModel;

@Controller
@RequestMapping("his")
public class HisMasterController {

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HisMasterController.class);

	@SuppressWarnings("unchecked")
	@PostMapping("patient-registration-with-test-list")
	public @ResponseBody JsonResponse<Object> patientRegistrationWithTestList(HttpSession session,
			@RequestBody RegsPatientModel data) {
		logger.info("Method : patientRegistrationWithTestList starts");

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
		
		data.setUserId(userId);
		data.setOrg(orgName);
		data.setOrgDiv(orgDivision);
		
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-patientRegistrationWithTestList", data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : patientRegistrationWithTestList ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("patient-details-with-test-list")
	public @ResponseBody JsonResponse<Object> patientDetailsWithTestList(HttpSession session, @RequestParam String id, @RequestParam String type) {
		logger.info("Method : patientRegistrationWithTestList starts");
		
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
			resp = restClient.getForObject(env.getHisUrl() + "rest-patientDetailsWithTestList?id="+id+"&type="+type+"&org="+orgName+"&orgDiv="+orgDivision, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : patientRegistrationWithTestList ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("payment-proceed-by-test-id")
	public @ResponseBody JsonResponse<Object> paymentProceed(HttpSession session, @RequestBody DropDownModel data) {
		logger.info("Method : paymentProceed starts");

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
		
		data.setCreatedBy(userId);
		data.setOrgName(orgName);
		data.setOrgDivision(orgDivision);
		
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-paymentProceed", data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : paymentProceed ends");
		return resp;
	}
}
