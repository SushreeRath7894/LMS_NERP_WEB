package nirmalya.aathithya.webmodule.asset.controller;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
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
import nirmalya.aathithya.webmodule.asset.model.AssetViewMasterModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "asset/")
public class ManagePropertyPlanningController {

	Logger logger = LoggerFactory.getLogger(ManagePropertyPlanningController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/manage-property-planning")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : reimbursement starts");
		
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
		 
		logger.info("Method : reimbursement ends");
		return "asset/manage-property-planning";
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-planning-getfloor")
	public @ResponseBody Object getFloorFromProperty(@RequestParam String property,HttpSession session) {
		logger.info("Method :getFloorFromProperty starts");
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
					env.getAssetUrl() + "rest-manage-property-augment-getfloor?property=" + property +"&orgName="+orgName+"&orgDivision="+orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getFloorFromProperty ends");
		return resp;
	}

	// getSubCategory
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-planning-subcategory")
	public @ResponseBody Object getSubCategory(@RequestParam String id, HttpSession session) {
		logger.info("Method :getSubCategory starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-asset-policy-subcategory?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getSubCategory ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-property-planning-add" })
	public @ResponseBody JsonResponse<Object> addPropertyPlan(@RequestBody List<AssetPlanningModel> av,
			HttpSession session) {
		logger.info("Method : addPropertyPlan starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (AssetPlanningModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}

		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-planning-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addPropertyPlan starts");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("manage-property-planning-view")
	public @ResponseBody Object viewAssignAsset(HttpSession session) {
		logger.info("Method :viewAssignAsset starts");
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
					env.getAssetUrl() + "rest-manage-property-planning-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewAssignAsset ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("manage-property-planning-delete")
	public @ResponseBody JsonResponse<Object> deletePlanning(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deletePlanning function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getAssetUrl() + "rest-manage-property-planning-delete?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deletePlanning function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-planning-edit")
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-planning-edit?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editPropertyPlan ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("manage-property-planning-filter")
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
					env.getAssetUrl() + "rest-manage-property-augment-view?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&property=" + property+ "&floor=" + floor+ "&space=" + space,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewPropertyAugment ends");
		return resp;
	}
}
