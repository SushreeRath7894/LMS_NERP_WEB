package nirmalya.aathithya.webmodule.purchase.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.purchase.model.ManageSendAmountModel;

@Controller
@RequestMapping(value = { "purchase/" })
public class SendAmountController {
	Logger logger = LoggerFactory.getLogger(SendAmountController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/view-send-amount")
	public String sendAmount(Model model, HttpSession session) {

		logger.info("Method : sendAmount starts");

		logger.info("Method : sendAmount ends");
		return "purchase/view-send-amount";
	
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("view-send-amount-saveData")
	public @ResponseBody JsonResponse<Object> addPaymentGrn(@RequestBody ManageSendAmountModel manageSendAmountModel, Model model,
			HttpSession session) {

		logger.info("Method : addPaymentGrn starts" + manageSendAmountModel);

		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (RestClientException e) {
			e.printStackTrace();

		}
		manageSendAmountModel.setUserId(userId);
		manageSendAmountModel.setCreatedBy(userId);
		manageSendAmountModel.setOrganization(orgName);
		manageSendAmountModel.setOrgDivision(orgDivision);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "restAddPaymentGrn", manageSendAmountModel, JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addPaymentGrn ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-send-amount-through-ajax")
	public @ResponseBody List<ManageSendAmountModel> viewSendAmount(HttpSession session) {

		logger.info("Method : viewSendAmount starts");

		JsonResponse<List<ManageSendAmountModel>> resp = new JsonResponse<List<ManageSendAmountModel>>();

		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-viewSendAmount", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<ManageSendAmountModel> sendAmount = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageSendAmountModel>>() {
				});

		resp.setBody(sendAmount);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewSendAmount ends");
		return resp.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-send-amount-getGrnList" })
	public @ResponseBody JsonResponse<Object> getGrnList(@RequestParam String id) {
		logger.info("Method : getGrnList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getGrnList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("getGrnList===" + res);
		logger.info("Method : getGrnList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-send-amount-getDataEdit")
	public @ResponseBody JsonResponse<List<ManageSendAmountModel>> editSendAmountGrn(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : editSendAmountGrn starts" + id);

		JsonResponse<List<ManageSendAmountModel>> jsonResponse = new JsonResponse<List<ManageSendAmountModel>>();

		try {
			jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "restEditSendAmount?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();		

		List<ManageSendAmountModel> manageClubMember = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<ManageSendAmountModel>>() {
				});

		

		System.out.println("###" + manageClubMember);
		jsonResponse.setBody(manageClubMember);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		System.out.println("REsp" + jsonResponse);
		logger.info("Method :editSendAmountGrn ends");
		return jsonResponse;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-send-amount-delete")
	public @ResponseBody JsonResponse<Object> deletePaymentDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deletePaymentDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "rest-deletePaymentDetails?id=" + id  , JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deletePaymentDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-send-amount-approval")
	public @ResponseBody JsonResponse<ManageSendAmountModel> paymentApprovalAmount(HttpSession session, @RequestParam String paymentId, String userId) {
	    logger.info("Method : paymentApprovalAmount starts");
	    JsonResponse<ManageSendAmountModel> response = new JsonResponse<ManageSendAmountModel>();

	    try {
	        response = restTemplate.getForObject(env.getPurchaseUrl() + "paymentApprovalAmount?paymentId=" + paymentId + "&userId=" + userId,JsonResponse.class);
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
	    logger.info("Method : paymentApprovalAmount ends");
	    return response;
	}

}
