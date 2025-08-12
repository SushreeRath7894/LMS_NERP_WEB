package nirmalya.aathithya.webmodule.asset.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
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
import org.springframework.web.multipart.MultipartFile;

import nirmalya.aathithya.webmodule.asset.model.AssetViewMasterModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HISPathoLabModel;

@Controller
@RequestMapping(value = "asset/")
public class AssetManagementController {

	Logger logger = LoggerFactory.getLogger(AssetManagementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("asset-management")
	public String assetManagement(Model model, HttpSession session) {
		logger.info("Method : AssetManagement starts");

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
			DropDownModel[] emp = restTemplate.getForObject(env.getAssetUrl() + "getEmployeeListforAssetAssign?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] loc = restTemplate.getForObject(env.getAssetUrl() + "getLocationListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

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
		try {
			DropDownModel[] asset_list = restTemplate.getForObject(
					env.getTicketUrl() + "getAssetList?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(asset_list);

			model.addAttribute("assetList", assetList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] emp = restTemplate.getForObject(env.getAssetUrl() + "getEmployeeListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists1", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] loc = restTemplate.getForObject(
					env.getTicketUrl() + "get-vendor-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists1", locLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : AssetManagement ends");
		return "his_asset/asset-management";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("asset-management-view")
	public @ResponseBody Object viewCodeAsset(@RequestParam String type,HttpSession session) {
		logger.info("Method :viewCodeAsset starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getAssetUrl() + "rest-view-asset?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&type=" + type+ "&userId=" + userId,
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
		logger.info("Method :viewCodeAsset ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "asset-management-add-details" })
	public @ResponseBody JsonResponse<Object> addCodeAssetDetails(@RequestBody AssetViewMasterModel av,
			HttpSession session) {
		logger.info("Method : addCodeAssetDetails starts---"+av.getFileName());
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

		av.setCreatedBy(userId);
		av.setOrganization(organization);
		av.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-asset-management-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addCodeAssetDetails starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("asset-management-delete-details")
	public @ResponseBody JsonResponse<Object> deleteAssetDetails(@RequestParam String id,@RequestParam String type,@RequestParam String assetId, Model model,
			HttpSession session) {
		logger.info("Method : deleteAssetDetails function starts");

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
					env.getAssetUrl() + "rest-delete-asset-details?id=" + id + "&type=" + type + "&assetId=" + assetId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteAssetDetails function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("asset-management-policy-list")
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

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("asset-management-allocate-policy") public @ResponseBody
	 * JsonResponse<Object> assignCodeAssetPolicy(@RequestParam String cat, String
	 * subCat, String policyId, String assetList, String assetemp, String
	 * assigndate,String assetcat,String assetGrp,String type, Model model,
	 * HttpSession session) {
	 * logger.info("Method : assignCodeAssetPolicy function starts");
	 * 
	 * JsonResponse<Object> res = new JsonResponse<Object>(); String userId = "";
	 * String orgName = ""; String orgDivision = ""; try { userId = (String)
	 * session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * logger.error(e.getMessage()); } try { res =
	 * restTemplate.getForObject(env.getMaintenance() +
	 * "rest-asset-maintenance-allocate?cat=" + cat + "&subCat=" + subCat +
	 * "&policyId=" + policyId + "&assetList=" + assetList + "&assetemp=" + assetemp
	 * + "&assigndate=" + assigndate + "&assetcat="+assetcat+"&assetGrp="+assetGrp+
	 * "&org=" + orgName + "&orgDiv=" + orgDivision + "&userId="+ userId+ "&type="+
	 * type, JsonResponse.class); } catch (RestClientException e) {
	 * e.printStackTrace(); }
	 * 
	 * String message = res.getMessage(); if (message != null && message != "") {
	 * 
	 * } else { res.setMessage("Success"); }
	 * logger.info("Method : assignCodeAssetPolicy function Ends");
	 * 
	 * return res; }
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping("asset-management-allocate-policy")
	public @ResponseBody JsonResponse<Object> assignCodeAssetPolicy(HttpSession session,
			@RequestBody List<AssetViewMasterModel> addSample) {
		logger.info("Method : assignCodeAssetPolicy starts" + addSample);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		for (AssetViewMasterModel m : addSample) {
			m.setCreatedBy(userId);
			m.setOrganization(orgName);
			m.setOrgDivision(orgDiv);

		}

		try {
			resp = restTemplate.postForObject(env.getMaintenance() + "rest-asset-maintenance-allocate", addSample, JsonResponse.class);

			if (resp.getMessage() == "" && resp.getMessage() == null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		logger.info("Method : assignCodeAssetPolicy ends"+resp);

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("asset-management-assign")
	public @ResponseBody JsonResponse<Object> assignCodeAsset(@RequestParam String id, String assetcat, String assetemp,
				String assigndate, Model model, HttpSession session) {
			logger.info("Method : assignCodeAsset function starts");

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
				res = restTemplate.getForObject(env.getAssetUrl() + "rest-asset-master-code-assign?id=" + id + "&assetcat="
						+ assetcat + "&assetemp=" + assetemp + "&assigndate=" + assigndate + "&org=" + orgName + "&orgDiv="
						+ orgDivision, JsonResponse.class);
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

	@SuppressWarnings("unchecked")
	@PostMapping("asset-management-dissociate")
	public @ResponseBody JsonResponse<Object> dissociateAsset(@RequestParam String assignid,String assetid,String dreason, Model model, HttpSession session) {
		logger.info("Method : dissociateAsset function starts");

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
					env.getAssetUrl() + "rest-asset-assign-dissociate?assignid=" + assignid + "&assetid=" + assetid+ "&org=" + orgName + "&orgDiv=" + orgDivision+ "&dreason=" + dreason,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : dissociateAsset function Ends"+res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("asset-management-scrap")
	public @ResponseBody JsonResponse<Object> scrapCodeAsset(@RequestParam String id,String status,String assetDescsts, Model model,
			HttpSession session) {
		logger.info("Method : scrapCodeAsset function starts");

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
					env.getAssetUrl() + "rest-asset-code-scrap?id=" + id + "&status=" + status+ "&org=" + orgName + "&orgDiv=" + orgDivision + "&assetDescsts="  + assetDescsts,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : scrapCodeAsset function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("asset-management-subcategory")
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
	@GetMapping("asset-management-edit")
	public @ResponseBody Object editCodeAsset(@RequestParam String id, HttpSession session) {
		logger.info("Method :editCodeAsset starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-edit-asset?id=" + id + "&orgName=" + orgName
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
		logger.info("Method :editCodeAsset ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "asset-management-add" })
	public @ResponseBody JsonResponse<Object> addCodeAsset(@RequestBody List<AssetViewMasterModel> av,
			HttpSession session) {
		logger.info("Method : addCodeAsset starts"+av);

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
			resp = restClient.postForObject(env.getAssetUrl() + "rest-asset-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveTrainingStudyMaterials starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("asset-management-delete")
	public @ResponseBody JsonResponse<Object> deleteCodeAsset(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteCodeAsset function starts");

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
					env.getAssetUrl() + "rest-delete-asset?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteCodeAsset function Ends");

		return res;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("asset-management-approve")
	public @ResponseBody JsonResponse<Object> approveAsset(@RequestParam String id, String assetname, String purchaseno,
			String assettype, String pdate, Model model, HttpSession session) {
		logger.info("Method : approveAsset function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String Qrfile = "QR" + new Date().getTime() + ".png";

		try {
			res = restTemplate.getForObject(env.getAssetUrl() + "rest-approve-asset?id=" + id + "&assetname=" + assetname+ "&purchaseno=" + purchaseno+ "&assettype=" + assettype+ "&pdate=" + pdate
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (res.getCode().equals("success")) {
			res.setMessage("Success");

		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}

		logger.info("Method : sampleAmt function Ends");

		return res;

	}
	
	
	
}
