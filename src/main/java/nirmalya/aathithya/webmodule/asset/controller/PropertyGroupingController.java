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

import nirmalya.aathithya.webmodule.asset.model.AssetPolicyModel;
import nirmalya.aathithya.webmodule.asset.model.AssetViewMasterModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "asset/")
public class PropertyGroupingController {

	Logger logger = LoggerFactory.getLogger(PropertyGroupingController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/property-grouping")
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
			DropDownModel[] cat = restTemplate.getForObject(env.getAssetUrl() + "getPropertyList?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> propertyLists = Arrays.asList(cat);
			model.addAttribute("propertyLists", propertyLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(env.getAssetUrl() + "getPriorityListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> prioLists = Arrays.asList(cat);
			model.addAttribute("prioLists", prioLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(env.getAssetUrl() + "getUOMListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> unitLists = Arrays.asList(cat);
			model.addAttribute("unitOfMeasurement", unitLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] asset_list = restTemplate.getForObject(
					env.getAssetUrl() + "getAssetList?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(asset_list);

			model.addAttribute("assetList", assetList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : reimbursement ends");
		return "asset/property-grouping";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("property-grouping-room-list")
	public @ResponseBody Object viewPropertyInGrouping(@RequestParam String pid,String fid,HttpSession session) {
		logger.info("Method :viewPropertyInGrouping starts");
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
					env.getAssetUrl() + "rest-property-grouping-room-list?orgName=" + orgName + "&orgDivision=" + orgDivision +
					"&pid=" + pid + "&fid=" + fid,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewPropertyInGrouping ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "property-grouping-add" })
	public @ResponseBody JsonResponse<Object> addPropertyGroup(@RequestBody List<AssetPolicyModel> av,
			HttpSession session) {
		logger.info("Method : addPropertyGroup function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
		}
		for (AssetPolicyModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getAssetUrl() + "rest-property-grouping-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addPropertyGroup function Ends"+resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")

	@GetMapping("property-grouping-view")
	public @ResponseBody Object viewPropertyGroup(HttpSession session) {
		logger.info("Method :viewPropertyGroup starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getAssetUrl() + "rest-property-grouping-view?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewPropertyGroup ends");
		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("property-grouping-edit")
	public @ResponseBody Object editPropertyGrouping(@RequestParam String id, HttpSession session) {
		logger.info("Method :editPropertyGrouping starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-edit-Property-grouping?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editPropertyGrouping ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("property-grouping-approve")
	public @ResponseBody JsonResponse<Object> approvePropertyGroup(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approvePropertyGroup function starts");

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
			res = restTemplate.getForObject(env.getAssetUrl() + "rest-Property-group-approve?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {

		}
		logger.info("Method : approvePropertyGroup function Ends");
		return res;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("property-grouping-delete")
	public @ResponseBody JsonResponse<Object> deletePropertyGroup(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deletePropertyGroup function starts");

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
			res = restTemplate.getForObject(env.getAssetUrl() + "rest-Property-group-delete?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deletePropertyGroup function Ends");
		return res;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("asset-grouping-floor")
	public @ResponseBody Object getFloor(@RequestParam String id, HttpSession session) {
		logger.info("Method :getFloor starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-property-floor?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getFloor ends");
		return resp;
	}
}
