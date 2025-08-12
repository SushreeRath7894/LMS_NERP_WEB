package nirmalya.aathithya.webmodule.property.tenant.controller;

import java.util.ArrayList;
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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.property.tenant.model.TenantPayOSduesModel;

@Controller
@RequestMapping(value = "property")

public class TenantPayosDuesController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(TenantPayosDuesController.class);

	@GetMapping(value = { "pay-osdue" })
	public String payOSdueController(Model model, HttpSession session) {
		logger.info("Method :payOSdueController starts");
		String userId = "";
		String userRole = "";
		String userMobile="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userMobile= (String) session.getAttribute("USER_MOBILE");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
		}
		model.addAttribute("userId", userMobile);
		logger.info("Method :payOSdueController ends");
		return "new-property/payOSdue";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("pay-osdue-details-view")
	public @ResponseBody List<TenantPayOSduesModel> viewpayos(HttpSession session, @RequestParam String userid,
			@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method :viewpayos starts" + userid + fromDate + toDate);

		JsonResponse<List<TenantPayOSduesModel>> resp = new JsonResponse<List<TenantPayOSduesModel>>();
		List<TenantPayOSduesModel> returnList = new ArrayList<TenantPayOSduesModel>();
		logger.info("rssssssssssssssss:" + returnList);

		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "pay-osdue-details-view?userid="+userid+"&fromDate="+fromDate+"&toDate="+toDate, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewpayos ends");
		return returnList;
	}

}
