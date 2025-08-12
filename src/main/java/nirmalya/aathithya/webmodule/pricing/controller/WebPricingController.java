package nirmalya.aathithya.webmodule.pricing.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;


@Controller
@RequestMapping(value = "master")
public class WebPricingController {

	Logger logger = LoggerFactory.getLogger(WebPricingController.class);
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/package-configuration")
	public String pricing(Model model, HttpSession session) {
		logger.info("Method : pricing-web-module starts");
		
		String org = "";
		String orgDiv = "";
		
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] hsnCode = restTemplate.getForObject(
					env.getMasterUrl() + "getActivityList?org=" + org +"&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> activityListData = Arrays.asList(hsnCode);
			model.addAttribute("activityList", activityListData);
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		
		logger.info("Method : pricing-web-module ends");
		return "package-pricing/pricing";
		
	}
	
	
	//Save PackageConfiguration Data  --------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	@SuppressWarnings("unchecked")
	@PostMapping("save-packageconfig-details")
	public @ResponseBody JsonResponse<Object> savePackageConfig(HttpSession session,
			@RequestBody Map<String, Object> packageData) {
		logger.info("Method : savePackageConfig starts");
		logger.info("Received savePackageConfig data: {}", packageData);
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-save-packageconfig-details?org=" + orgName +"&orgDiv=" + orgDivision,
					packageData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : savePackageConfig ends");
		return resp;
	}
	
	//View Packaging Data ------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	@SuppressWarnings("unchecked")

	@GetMapping("packageData-view")
	public @ResponseBody Object viewPackageData(HttpSession session) {
		logger.info("Method :viewPackageData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-packageData-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewPackageData ends" + resp);
		return resp;
	}
	
	//Delete Package Data --->>>>>>>>
	@SuppressWarnings("unchecked")
	@GetMapping("packageData-delete")
	public @ResponseBody JsonResponse<Object> deletePackageData(@RequestParam String id, HttpSession session) {
		logger.info("Method : deletePackageData function starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-packageData-delete?id=" + id,
					JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deletePackageData function Ends");

		return resp;
	}
}
