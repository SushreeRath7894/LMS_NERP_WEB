package nirmalya.aathithya.webmodule.maintenance.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "maintenance/")
public class AssetMaintainanceController {

	Logger logger = LoggerFactory.getLogger(AssetMaintainanceController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/asset-maintenance")
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
			DropDownModel[] emp = restTemplate.getForObject(env.getAssetUrl() + "getEmployeeListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(env.getMaintenance() + "getCategoryListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> catLists = Arrays.asList(cat);
			model.addAttribute("catLists", catLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] loc = restTemplate.getForObject(
					env.getTicketUrl() + "get-vendor-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

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
		try {
			DropDownModel[] grp = restTemplate.getForObject(env.getMaintenance() + "getGroupListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> groupList = Arrays.asList(grp);

			model.addAttribute("groupList", groupList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : reimbursement ends");
		return "maintenance/asset-maintenance";
	}

	// viewQc

	/*@SuppressWarnings("unchecked")

	@GetMapping("asset-maintenance-view")
	public @ResponseBody Object viewAssetMaintenance(HttpSession session) {
		logger.info("Method :viewAssetMaintenance starts");
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
			resp = restTemplate.getForObject(env.getMaintenance() + "rest-asset-maintenance-view?orgName=" + orgName
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
		logger.info("Method :viewAssetMaintenance ends");
		return resp;
	}*/
	@SuppressWarnings("unchecked")

	@GetMapping("asset-maintenance-view")
	public @ResponseBody Object viewAssetMaintenance(HttpSession session) {
		logger.info("Method :viewAssetMaintenance starts");
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
			resp = restTemplate.getForObject(env.getMaintenance() + "rest-alloted-maintenance-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision+ "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewAssetMaintenance ends");
		return resp;
	}

	// assignCodeAsset

	@SuppressWarnings("unchecked")
	@PostMapping("asset-maintenance-allocate")
	public @ResponseBody JsonResponse<Object> assignCodeAsset(@RequestParam String cat, String subCat,
			String policyId, String assetList, String assetemp, String assigndate,String assetcat,String assetGrp,String type, Model model, HttpSession session) {
		logger.info("Method : assignCodeAsset function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getMaintenance() + "rest-asset-maintenance-allocate?cat=" + cat
					+ "&subCat=" + subCat + "&policyId=" + policyId + "&assetList=" + assetList + "&assetemp="
					+ assetemp + "&assigndate=" + assigndate + "&assetcat="+assetcat+"&assetGrp="+assetGrp+
					"&org=" + orgName + "&orgDiv=" + orgDivision + "&userId="+ userId+ "&type="+ type, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : assignCodeAsset function Ends");

		return res;
	}

	// getSubCategory
	@SuppressWarnings("unchecked")
	@GetMapping("asset-maintenance-subcat")
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
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getSubCategory ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("asset-maintenance-emergencylist")
	public @ResponseBody Object getEmergencyList(@RequestParam String type, String cat, String subcat,
			HttpSession session) {
		logger.info("Method :getEmergencyList starts");
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

			resp = restTemplate
					.getForObject(
							env.getMaintenance() + "rest-maintenance-emergencylist?type=" + type + "&cat=" + cat
									+ "&subcat=" + subcat + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getEmergencyList ends");
		return resp;
	}

	// DeleteAsset

	@SuppressWarnings("unchecked")
	@PostMapping("asset-maintenance-deallocate")
	public @ResponseBody JsonResponse<Object> deletePolicyAsset(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deletePolicyAsset function starts");

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
			res = restTemplate.getForObject(env.getMaintenance() + "rest-asset-maintenance-deallocate?id=" + id
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deletePolicyAsset function Ends");

		return res;
	}
	@SuppressWarnings("unchecked")

	@GetMapping("asset-maintenance-policy-list")
	public @ResponseBody Object viewAssetMaintenancePolicy(@RequestParam String cat,String subcat,HttpSession session) {
		logger.info("Method :viewAssetMaintenancePolicy starts 	&cat="+cat + "&subcat="+subcat);
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
			resp = restTemplate.getForObject(env.getMaintenance() + "rest-asset-maintenance-viewPolicy?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&cat="+cat + "&subcat="+subcat, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewAssetMaintenancePolicy ends");
		return resp;
	}
	@SuppressWarnings("unchecked")

	@GetMapping("asset-maintenance-tickt")
	public @ResponseBody Object viewTicket(@RequestParam String allotedId,HttpSession session) {
		logger.info("Method :viewTicket starts"+allotedId);
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
			resp = restTemplate.getForObject(env.getMaintenance() + "rest-viewTicket?orgName=" + orgName
					+ "&orgDivision=" + orgDivision+"&allotedId=" + allotedId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewTicket ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("asset-maintenance-assetList")
	public @ResponseBody Object showTotalAsset(@RequestParam String cat,String scat, HttpSession session) {
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

			resp = restTemplate.getForObject(env.getMaintenance() + "rest-manage-asset-list?orgName=" + orgName
					+ "&orgDivision=" + orgDivision+ "&cat=" + cat+ "&scat=" + scat, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :showTotalAsset ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("asset-maintenance-close-ticket")
	public @ResponseBody JsonResponse<Object> closeTicketAction(@RequestParam String id, String operation, Model model, HttpSession session) {
		logger.info("Method : closeTicketAction function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getMaintenance() + "rest-asset-maintenance-close-ticket?id=" + id
					+ "&operation=" + operation + "&org=" + orgName + "&orgDiv=" + orgDivision + "&userid="
					+ userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : closeTicketAction function Ends");
		return res;
	}
}
