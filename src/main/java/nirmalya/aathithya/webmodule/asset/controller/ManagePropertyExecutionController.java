package nirmalya.aathithya.webmodule.asset.controller;


import java.util.Arrays;
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

import nirmalya.aathithya.webmodule.asset.model.AssetPlanningModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "asset/")
public class ManagePropertyExecutionController {

	Logger logger = LoggerFactory.getLogger(ManagePropertyExecutionController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/manage-property-execution")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : Manage Property Approve starts");
		
		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
			}
		}
		try {
			DropDownModel[] emp = restTemplate.getForObject(
					env.getAssetUrl() + "getPropertyListForAugment?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> propertyLists = Arrays.asList(emp);

			model.addAttribute("propertyLists", propertyLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] loc = restTemplate.getForObject(
					env.getAssetUrl() + "getLocationListforAsset?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}try {
			DropDownModel[] asset_list = restTemplate.getForObject(
					env.getTicketUrl() + "getAssetList?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(asset_list);

			model.addAttribute("assetList", assetList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(env.getAssetUrl() + "getCategoryListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> catLists = Arrays.asList(cat);
			model.addAttribute("catLists", catLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		 
		logger.info("Method : Manage Property Approve ends");
		return "asset/manage-property-execution";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-execution-asset-list")
	public @ResponseBody Object showTotalAsset(@RequestParam String type,String assetcat,String assetscat, HttpSession session) {
		logger.info("Method :showTotalAsset starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-execution-asset-list?type=" + type + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision+ "&cat=" + assetcat+ "&scat=" + assetscat, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :showTotalAsset ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = "manage-property-execution-asset-assign")
	public @ResponseBody JsonResponse<Object> assignAsset(@RequestParam String id, String locid, String loctype,
			String date,String planId, Model model, HttpSession session) {
		logger.info("Method : assignAsset function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-execution-asset-assign?id=" + id + "&locid=" + locid+ "&loctype=" + loctype+ "&date=" + date+ "&planId=" + planId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision+ "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : assignAsset function Ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-execution-view")
	public @ResponseBody Object viewPropertyAugment(@RequestParam String property,String floor,String space,HttpSession session) {
		logger.info("Method :viewPropertyAugment starts");
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
			resp = restTemplate.getForObject(
					env.getAssetUrl() + "rest-manage-property-execution-view?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&property=" + property+ "&floor=" + floor+ "&space=" + space,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewPropertyAugment ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-execution-edit")
	public @ResponseBody Object editPropertyPlan(@RequestParam String id, HttpSession session) {
		logger.info("Method :editPropertyPlan starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-execution-edit?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editPropertyPlan ends");
		return resp;
	}
}
