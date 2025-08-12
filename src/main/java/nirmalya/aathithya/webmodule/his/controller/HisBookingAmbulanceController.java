package nirmalya.aathithya.webmodule.his.controller;

import java.util.Arrays;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HisBookingAmbulanceModel;

@Controller
@RequestMapping(value = "his")
public class HisBookingAmbulanceController {
	Logger logger = LoggerFactory.getLogger(HisAmbulanceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/booking-ambulance")
	public String ambulance(Model model, HttpSession session) {
		logger.info("Method : bookingAmbulance starts");

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] gender = restTemplate.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			DropDownModel[] Country = restTemplate.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(Country);
			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] type = restTemplate.getForObject(env.getHisUrl() + "getAmbulanceTypeList",
					DropDownModel[].class);
			List<DropDownModel> getAmbulanceTypeList = Arrays.asList(type);
			model.addAttribute("ambulanceTypeList", getAmbulanceTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] requirement = restTemplate.getForObject(env.getHisUrl() + "getAmbulanceRequirement",
					DropDownModel[].class);
			List<DropDownModel> getAmbulanceRequirement = Arrays.asList(requirement);
			model.addAttribute("ambulanceRequirementList", getAmbulanceRequirement);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] bankAccountList = restTemplate.getForObject(env.getAccountUrl()
					+ "getBankAccountPaymentList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restTemplate.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] costCenter = restTemplate.getForObject(env.getHisUrl()+ "/getAllPatientList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> patList = Arrays.asList(costCenter);
			model.addAttribute("patList", patList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : bookingAmbulance ends");
		return "his/his-bookambulance.html";

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "booking-ambulance-state-list" })
	public @ResponseBody JsonResponse<Object> getStateName(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateName starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getStateListForLoc?id=" + tCountry,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateName ends" + res);
		return res;

	}

	// districtList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "booking-ambulance-districtList" })
	public @ResponseBody JsonResponse<Object> districtList(@RequestParam String id) {
		logger.info("Method : districtList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "districtList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : districtList ends");
		return res;
	}

	// cityList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "booking-ambulance-CityList" })
	public @ResponseBody JsonResponse<Object> CityList(@RequestParam String id) {
		logger.info("Method : CityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "rest-CityList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : CityList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "booking-ambulance-city-list" })
	public @ResponseBody JsonResponse<Object> getCity(Model model, @RequestBody String tCountry, BindingResult result) {
		logger.info("Method : getCity starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getCityForLocation?id=" + tCountry,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("unsuccess");
		}
		logger.info("Method : getCity ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("booking-ambulance-list-view")
	public @ResponseBody Object viewAmbulanceList(HttpSession session, @RequestParam String from) {
		logger.info("Method :viewAmbulanceList starts");

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
			resp = restTemplate
					.getForObject(
							env.getMasterUrl() + "restgetproducttypewise?orgName=" + orgName + "&orgDivision="
									+ orgDivision + "&userId=" + userId + "&from=" + from + "&deptId=",
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewAmbulanceList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("booking-ambulance-add")
	public @ResponseBody JsonResponse<Object> addBookingAmbulance(HttpSession session,
			@RequestBody HisBookingAmbulanceModel addBookingAmbulance) {
		logger.info("Method : addBookingAmbulance starts" + addBookingAmbulance);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		addBookingAmbulance.setCreatedBy(userId);
		addBookingAmbulance.setOrg(orgName);
		addBookingAmbulance.setDiv(orgDiv);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "restuserregsandproductbooking", addBookingAmbulance,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		}

		logger.info("Method : addBookingAmbulance ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("booking-ambulance-change-status")
	public @ResponseBody JsonResponse<Object> changeStatusOfBookedAmmbulance(HttpSession session,
			@RequestBody DropDownModel addBookingAmbulance) {
		logger.info("Method : changeStatusOfBookedAmmbulance starts" + addBookingAmbulance);
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String orgName = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		addBookingAmbulance.setCreatedBy(userId);
		addBookingAmbulance.setOrgName(orgName);
		addBookingAmbulance.setOrgDivision(orgDiv);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "restChangeStatusOfBookedAmmbulance", addBookingAmbulance,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : changeStatusOfBookedAmmbulance ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("booking-ambulance-view-through-ajax")
	public @ResponseBody Object viewAmbulanceBookingList(HttpSession session) {
		logger.info("Method :viewAmbulanceBookingList starts");
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
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewAmbulanceBookingList?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewAmbulanceBookingList ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/booking-ambulance-delete")
	public @ResponseBody JsonResponse<Object> deleteAmbulance(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteAmbulance starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deleteAmbulance?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteAmbulance ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("booking-ambulance-approval-status")
	public @ResponseBody JsonResponse<HisBookingAmbulanceModel> approvalStatus(HttpSession session,
			@RequestParam String approval, String bookingId) {

		logger.info("Method : approvalStatus starts");
		JsonResponse<HisBookingAmbulanceModel> response = new JsonResponse<HisBookingAmbulanceModel>();
		try {
			response = restTemplate.getForObject(
					env.getHisUrl() + "approvalStatusAmbulance?approval=" + approval + "&bookingId=" + bookingId,
					JsonResponse.class);

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
		logger.info("Method : approvalStatus ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("booking-ambulance-approve-th-ajax")
	public @ResponseBody JsonResponse<HisBookingAmbulanceModel> approveStatus(HttpSession session,
			@RequestParam String approveStatus, String bookingId) {

		logger.info("Method : approveStatus starts");
		JsonResponse<HisBookingAmbulanceModel> response = new JsonResponse<HisBookingAmbulanceModel>();
		try {
			response = restTemplate.getForObject(
					env.getHisUrl() + "approvePatientSts?approval=" + approveStatus + "&bookingId=" + bookingId,
					JsonResponse.class);

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
		logger.info("Method : approveStatus ends");
		return response;
	}

	// manage-ambulance-payment
	@SuppressWarnings("unchecked")
	@GetMapping("manage-ambulance-payment")
	public @ResponseBody Object viewPaymentDetail(HttpSession session, @RequestParam String id) {
		logger.info("Method :viewPaymentDetail starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-paymnet-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewPaymentDetail ends" + resp);
		return resp;
	}
	// booking-ambulance-getPatientDetailsById
	@SuppressWarnings("unchecked")
	@GetMapping("booking-ambulance-getPatientDetailsById")
	public @ResponseBody Object getPatientDetailsById(HttpSession session, @RequestParam String id) {
		logger.info("Method :getPatientDetailsById starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			resp = restTemplate.getForObject(env.getHisUrl() + "getPatientDetailsById?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getPatientDetailsById ends" + resp);
		return resp;
	}

}
