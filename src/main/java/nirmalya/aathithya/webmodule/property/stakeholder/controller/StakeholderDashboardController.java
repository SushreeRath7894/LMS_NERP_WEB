package nirmalya.aathithya.webmodule.property.stakeholder.controller;

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
public class StakeholderDashboardController {

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(StakeholderDashboardController.class);

	/*Property Dashboard */

	@GetMapping("dashboard")
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
		if (userRole.equals("rol034")) {
			return "new-property/tenants_dashboard";
		} else if (userRole.equals("rol035")) {
			return "new-property/sprovider_dashboard";
		} else {
			return "new-property/stakeholder_dashboard";
		}

	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-monthRentLateFee-bar")
	public @ResponseBody JsonResponse<List<Object>> tenantDashboardpropertycategoriseBarDetails(Model model,
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
	@GetMapping("dashboard-tenant-categorise-latefee-bar")
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
	@GetMapping("dashboard-tenant-lineData")
	public @ResponseBody JsonResponse<List<Object>> tenantDashboardLine(Model model,
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
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-service-district-property-categorise-bar")

	public @ResponseBody JsonResponse<List<Object>> serviceDashboardpropertycategoriseBarDetails(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboardpropertycategoriseBarDetails starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "service-dashboard-property-categorise-bar?userid=" + userid, JsonResponse.class);

		
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
	@GetMapping("dashboard-service-categorise-latefee-bar")

	public @ResponseBody JsonResponse<List<Object>> serviceDashboardmonthlyOS(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboardmonthlyOS starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "service-dashboard-monthly-os?userid=" + userid, JsonResponse.class);

		
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
		logger.info("Method : dashboardmonthlyOS ends");
		return jsonResponse;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-service-lineData")
	public @ResponseBody JsonResponse<List<Object>> serviceDashboardLine(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboardLine starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "service-dashboard-line?userid=" + userid, JsonResponse.class);

		
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
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-property-categorise-bar")
	public @ResponseBody JsonResponse<List<Object>> dashboardpropertycategoriseBarDetails(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboardpropertycategoriseBarDetails starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "dashboard-property-categorise-bar?userid=" + userid, JsonResponse.class);

		
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
	
	//district property type
	@SuppressWarnings("unchecked")

	@GetMapping("dashboard-district-property-categorise-bar")
	public @ResponseBody JsonResponse<List<Object>> dashboarddistrictcategoriseBarDetails(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboarddistrictcategoriseBarDetails starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "dashboard-district-property-categorise-bar?userid=" + userid, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : dashboarddistrictcategoriseBarDetails ends");
		return jsonResponse;
		
	}
	
	// property category wise return Pie chart
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-propertycategorise-pie")
	
	public @ResponseBody JsonResponse<List<Object>> propertyCategoriseReturnPie(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : propertyCategoriseReturnPie starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "categorise-property-pie?userid=" + userid, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : propertyCategoriseReturnPie ends");
		return jsonResponse;
	}
	///Category Wise Return OS
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-categorywisereturnos-bar")
	
	public @ResponseBody JsonResponse<List<Object>> categoryWiseReturnOS(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : categoryWiseReturnOS starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "returnos-bar?userid=" + userid, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : categoryWiseReturnOS ends"+jsonResponse);
		return jsonResponse;
	}
	
	//Category wise maintainance Bar
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-categorywisemaintainance-bar")
	
	public @ResponseBody JsonResponse<List<Object>> categoryWiseMaintainanceBar(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : categoryWiseMaintainanceBar starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "maintainance-bar?userid=" + userid, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : categoryWiseMaintainanceBar ends"+jsonResponse);
		return jsonResponse;
	}
	
	
	// Category wise Late Fee Bar
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-categorise-latefee-bar")
	
	public @ResponseBody JsonResponse<List<Object>> categoryWiseLateFeeBar(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : categoryWiseLateFeeBar starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "latefee-bar?userid=" + userid, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : categoryWiseLateFeeBar ends"+jsonResponse);
		return jsonResponse;
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-lineData")
	public @ResponseBody JsonResponse<List<Object>> dashboardLine(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : dashboardLine starts"+userid);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "dashboard-line-data?userid=" + userid, JsonResponse.class);

		
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
