package nirmalya.aathithya.webmodule.vendor.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "vendor")
class ContractPOController {

	Logger logger = LoggerFactory.getLogger(ContractPOController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/contract-po")
	public String contractPO() {
		logger.info("Method : contractPO start");
		return "vendor/contract-po";
	}

	/*
	 * Get All Contract Data
	 * 
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("contract-po-view-contract")
	public @ResponseBody JsonResponse<Object> viewContract(HttpSession session) {
		logger.info("Method : viewContract starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "get-Contract-for-vendor?org=" + orgName
					+ "&orgDiv=" + orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewContract ends");
		return resp;
	}

	/* update notification */

	@SuppressWarnings("unchecked")
	@GetMapping("contract-po-view-update-notification")
	public @ResponseBody JsonResponse<Object> notificationUpdateForPurchase(@RequestParam String id,
			HttpSession session) {
		logger.info("Method :notificationUpdateForPurchase starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
		try {

			resp = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "rest-notificationUpdateForPurchase?id=" + id + "&userId=" + userId
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
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
		logger.info("Method :notificationUpdateForPurchase ends" + resp);
		return resp;
	}

	// contract-po-add-response

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "contract-po-add-response" })
	public @ResponseBody JsonResponse<Object> saveResponseData(@RequestBody Map<String, Object> data,
			BindingResult result, HttpSession session) {
		logger.info("Method : saveResponseData starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			List<Map<String, Object>> parsedData = (List<Map<String, Object>>) data.get("datas");

			res = restTemplate.postForObject(
					env.getPurchaseUrl() + "add-response-data?userId=" + userId + "&org=" + org + "&orgDiv=" + orgDiv,
					parsedData, JsonResponse.class);
			res.getMessage();
			res.getCode();
			res.getBody();

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveResponseData ends");
		logger.info("saveResponseData" + res);
		return res;
	}

	// contract-po-view-response
	@SuppressWarnings("unchecked")
	@GetMapping("contract-po-view-response")
	public @ResponseBody JsonResponse<Object> viewResponseData(HttpSession session, @RequestParam String contractId,
			@RequestParam String tenderId) {
		logger.info("Method : viewResponseData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String userId = (String) session.getAttribute("USER_ID");
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "view-response-data?contractId=" + contractId
					+ "&tenderId=" + tenderId + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewResponseData ends");
		return resp;
	}

	// contract-po-add-admin-response

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "contract-po-add-admin-response" })
	public @ResponseBody JsonResponse<Object> saveAdminComment(@RequestBody Map<String, Object> data,
			BindingResult result, HttpSession session) {
		logger.info("Method : saveAdminComment starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			List<Map<String, Object>> parsedData = (List<Map<String, Object>>) data.get("datas");
			System.out.println("parsed data is coming==========> " + parsedData);

			res = restTemplate.postForObject(
					env.getPurchaseUrl() + "add-admin-comment?userId=" + userId + "&org=" + org + "&orgDiv=" + orgDiv,
					parsedData, JsonResponse.class);
			res.getMessage();
			res.getCode();
			res.getBody();

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAdminComment ends");
		logger.info("saveAdminComment" + res);
		return res;
	}
	
}
