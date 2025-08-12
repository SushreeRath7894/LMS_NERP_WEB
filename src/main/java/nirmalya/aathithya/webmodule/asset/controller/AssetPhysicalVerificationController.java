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

import nirmalya.aathithya.webmodule.asset.model.AssetPolicyModel;
import nirmalya.aathithya.webmodule.asset.model.AssetViewMasterModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "asset/")
public class AssetPhysicalVerificationController {

	Logger logger = LoggerFactory.getLogger(AssetPhysicalVerificationController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/asset-physical-verfication")
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
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : reimbursement ends");
		return "asset/asset-physical-verification";
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("asset-physical-verfication-view")
	public @ResponseBody Object allotedVerificationView(HttpSession session) {
		logger.info("Method :allotedVerificationView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String userRole = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getAssetUrl() + "rest-asset-physical-verfication-view?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId+ "&userRole=" + userRole,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :allotedVerificationView ends");
		return resp;
	}
	
	

	@SuppressWarnings("unchecked")
	@GetMapping("asset-physical-verfication-verify-details")
	public @ResponseBody Object showAssetDetailsonAlloc(@RequestParam String id, HttpSession session) {
		logger.info("Method :showAssetDetailsonAlloc starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String userRole = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-asset-physical-verfication-verify-details?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" +orgDivision+ "&userId=" + userId+ "&userRole=" + userRole, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :showAssetDetailsonAlloc ends");
		return resp;
	}
	

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "asset-physical-verfication-submit" })
	public @ResponseBody JsonResponse<Object> assetVerifySubmit(@RequestBody List<AssetViewMasterModel> av,
			HttpSession session) {
		logger.info("Method : assetVerifySubmit starts");

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
		JSONObject json = new JSONObject();
		for (AssetViewMasterModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-asset-physical-verfication-submit", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : assetVerifySubmit starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("asset-physical-verfication-active-details")
	public @ResponseBody Object showVerifyDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method :showVerifyDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String userRole = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-asset-physical-verfication-active-details?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" +orgDivision+ "&userId=" + userId+ "&userRole=" + userRole, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :showVerifyDetails ends");
		return resp;
	}
}
