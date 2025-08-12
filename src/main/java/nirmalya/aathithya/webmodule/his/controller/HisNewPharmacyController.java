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
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller
@RequestMapping(value = "his")
public class HisNewPharmacyController {

	Logger logger = LoggerFactory.getLogger(HisNewPharmacyController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/pharmacy-dashboard")
	public String pharmacyDashboard() {
		logger.info("Start of method : pharmacyDashboard");
		logger.info("End of method : pharmacyDashboard");
		return "his/pharmacy/dashboard";
	}

	@GetMapping("/billing-pharmacy")
	public String reception(Model model, HttpSession session) {

		logger.info("Method : billing starts");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getHisUrl() + "/getItemList?org=" + orgName + "&orgDiv=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restTemplate.getForObject(
					env.getHisUrl() + "/getAllPatientList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> patList = Arrays.asList(costCenter);
			model.addAttribute("patList", patList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
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
			// Fetch gender data
			DropDownModel[] gender = restTemplate.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			DropDownModel[] country = restTemplate.getForObject(env.getHisUrl() + "/countryList",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : billing ends");
		return "his/his-newpharmacy.html";

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "billing-pharmacy-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCustomerAutoSearchList starts");

		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "getPatientListByAutoSearch?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/his-billing-pharmacy-view")
	public @ResponseBody Object viewPharmacy(HttpSession session) {
		logger.info("Method :viewPharmacy starts");
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
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-his-viewPharmacy?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewPharmacy ends" + resp);
		return resp;
	}

	@GetMapping("/his-billing-pharmacy-get-medicine-list")
	public @ResponseBody Object getMedicineList(HttpSession session) {
		logger.info("Method :getMedicineList starts");

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
			DropDownModel[] item = restTemplate.getForObject(
					env.getHisUrl() + "/getItemList?org=" + orgName + "&orgDiv=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);

			if (itemList.size() > 0) {
				resp.setBody(itemList);
				resp.setCode("success");
				resp.setMessage("Data found");
			} else {
				resp.setBody(null);
				resp.setCode("failed");
				resp.setMessage("Data not found");
			}

		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setBody(null);
			resp.setCode("failed");
			resp.setMessage(e.getMessage());
		}

		logger.info("Method :getMedicineList ends");
		return resp;
	}

	@GetMapping("/his-billing-pharmacy-get-paymode-list")
	public @ResponseBody Object getPayModeList(HttpSession session) {
		logger.info("Method :getPayModeList starts");

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
			DropDownModel[] item = restTemplate.getForObject(
					env.getHisUrl() + "/getPayModeList?org=" + orgName + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);

			if (itemList.size() > 0) {
				resp.setBody(itemList);
				resp.setCode("success");
				resp.setMessage("Data found");
			} else {
				resp.setBody(null);
				resp.setCode("failed");
				resp.setMessage("Data not found");
			}

		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setBody(null);
			resp.setCode("failed");
			resp.setMessage(e.getMessage());
		}

		logger.info("Method :getPayModeList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/his-billing-pharmacy-get-batch")
	public @ResponseBody Object getBatchDetails(HttpSession session, @RequestParam String id) {
		logger.info("Method :getBatchDetails starts");

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
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-getBatchDetails?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getBatchDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/his-billing-pharmacy-dispatch")
	public @ResponseBody Object dispatchMedicine(HttpSession session, @RequestBody String data) {
		logger.info("Method :dispatchMedicine starts");

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
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-dispatchMedicine?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dispatchMedicine ends");
		return resp;
	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("his-billing-pharmacy-dashboard-expiried-view")
	public @ResponseBody Object viewmedicine(HttpSession session) {
		logger.info("Method :viewmedicine starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-pharmacy-dashboard-expiried-view?orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewmedicine ends" + resp);
		return resp;
	}

	// payment view
	@SuppressWarnings("unchecked")
	@GetMapping("his-billing-pharmacy-dashboard-payment-view")
	public @ResponseBody Object viewPayment(HttpSession session) {
		logger.info("Method :viewPayment starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-pharmacy-dashboard-payment-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewPayment ends" + resp);
		return resp;
	}
}