package nirmalya.aathithya.webmodule.property.tenant.controller;
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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "property")
public class TenantDashboardController {
	

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(TenantDashboardController.class);
	
	//Tenants Dashboard
	
	
	@GetMapping("tenant-dashboard")
	public String Dashboard(Model model, HttpSession session) {
		logger.info("Method : Dashboard starts");
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

		logger.info("Method : Dashboard ends");
		return "new-property/tenants_dashboard";
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("tenant-dashboard-monthRentLateFee-bar")
	public @ResponseBody JsonResponse<List<Object>> dashboardpropertycategoriseBarDetails(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboardpropertycategoriseBarDetails starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "tenant-monthlyrentLate-bar?userid=" + userid, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("RESPONSE" + jsonResponse);
		logger.info("Method : dashboardpropertycategoriseBarDetails ends");
		return jsonResponse;
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("tenant-dashboard-categorise-latefee-bar")
	public @ResponseBody JsonResponse<List<Object>> dashboardMonthlyOS(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboardMonthlyOS starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "tenant-monthlyOS-point?userid=" + userid, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("RESPONSeeeeeeeeeeeeeeE" + jsonResponse);
		logger.info("Method : dashboardpropertycategoriseBarDetails ends");
		return jsonResponse;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("tenant-dashboard-lineData")
	public @ResponseBody JsonResponse<List<Object>> dashboardLine(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboardLine starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "tenant-dashboard-line?userid=" + userid, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("RESPONSEsssssssssssssss" + jsonResponse);
		logger.info("Method : dashboardLine ends");
		return jsonResponse;
	}
	

}
