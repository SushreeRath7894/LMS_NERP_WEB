package nirmalya.aathithya.webmodule.account.controller;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

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
@RequestMapping("/account")
public class ReceiptScheduleAndProcessController {
	Logger logger = LoggerFactory.getLogger(ReceiptScheduleAndProcessController.class);

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@GetMapping("payment-receivable")
	public String ReceivablePage(Model model, HttpSession session) {

		logger.info("Receivable Page Strart...");
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}


		logger.info("Receivable Page End...");
		return "account/payment-receivable";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("payment-receivable-schedule-view")
	public @ResponseBody Object paymentScheduleView(HttpSession session) {
		logger.info("Method :Receipt Sechudle starts");

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
			resp = restClient.getForObject(
					env.getAccountUrl() + "receipt-schedule-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :Receipt Schedule ends");
		return resp;
	}

	
	@SuppressWarnings("rawtypes")
	@GetMapping("payment-receivable-schedule-save")
	public @ResponseBody Object paymentScheduleSave(@RequestParam String id, String scheduleDate, String type,
			HttpSession session) {
		logger.info("Method :receivable Save starts");
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
					env.getAccountUrl() + "payment-receivable-save?invId=" + decodedId + "&scheduleDate=" + decodedDate
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId + "&type=" + type,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :receivableSave end");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("payment-receivable-process-view")
	public @ResponseBody Object receivableProcessView(HttpSession session, @RequestParam String fromdate,
			@RequestParam String todate) {
		logger.info("Method :receivableProcessView starts");

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
			resp = restClient.getForObject(env.getAccountUrl() + "receivable-process-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :receivableProcessView ends");
		return resp;
	}
}
