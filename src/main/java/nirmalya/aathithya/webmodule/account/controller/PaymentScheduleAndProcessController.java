package nirmalya.aathithya.webmodule.account.controller;

import java.util.Arrays;
import java.util.List;
import java.net.URLDecoder;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "account/")
public class PaymentScheduleAndProcessController {
	Logger logger = LoggerFactory.getLogger(PaymentScheduleAndProcessController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/*********************************
	 * Payment Schedule
	 *************************************/
	// page return
	@GetMapping("payment-payable")
	public String paymentPayablePage(Model model, HttpSession session) {
		logger.info("Method : paymentPayablePage starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : paymentPayablePage end");
		return "account/payment-payable.html";
	}

	// payment-schedule-view
	@SuppressWarnings("unchecked")
	@GetMapping("payment-payable-schedule-view")
	public @ResponseBody Object paymentScheduleView(HttpSession session, @RequestParam String type) {
		logger.info("Method :paymentScheduleView starts");

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
			resp = restClient.getForObject(env.getAccountUrl() + "payment-schedule-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :paymentScheduleView ends");
		return resp;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("payment-payable-schedule-save")
	public @ResponseBody Object paymentScheduleSave(@RequestParam String id, String scheduleDate, String type,
			HttpSession session) {
		logger.info("Method :paymentScheduleSave starts");
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String decodedId = "";
		String decodedDate = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			decodedId = URLDecoder.decode(id, "UTF-8");
			decodedDate = URLDecoder.decode(scheduleDate, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getAccountUrl() + "payment-schedule-save?invId=" + decodedId + "&scheduleDate=" + decodedDate
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId + "&type=" + type,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :paymentScheduleSave end");
		return resp;
	}

	/*********************************
	 * Payment Process
	 *************************************/

	// payment-process-view
	@SuppressWarnings("unchecked")
	@GetMapping("payment-payable-process-view")
	public @ResponseBody Object paymentProcessView(HttpSession session, @RequestParam String fromdate,
			@RequestParam String todate, @RequestParam String type) {
		logger.info("Method :paymentProcessView starts");

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
			resp = restClient.getForObject(env.getAccountUrl() + "payment-process-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate + "&type=" + type,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :paymentProcessView ends");
		return resp;
	}
}
