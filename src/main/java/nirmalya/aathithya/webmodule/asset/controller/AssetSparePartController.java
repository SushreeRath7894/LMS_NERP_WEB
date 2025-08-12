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
@Controller
@RequestMapping(value = "asset/")
public class AssetSparePartController {
	

	Logger logger = LoggerFactory.getLogger(AssetReportController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	
	@GetMapping("/spare-part")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : spare part controller starts");
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
			DropDownModel[] loc = restTemplate.getForObject(env.getAssetUrl() + "getLocationListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(env.getAssetUrl() + "getCategoryListforSparePart?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> catLists = Arrays.asList(cat);
			model.addAttribute("catLists", catLists);

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
		logger.info("Method : spare part controlle ends");
		return "asset/spare-part";
	}

	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/spare-part-sparepartslist-view")
	public @ResponseBody Object getAllSpareParts(HttpSession session) {
		logger.info("Method : getAllSpareParts Details starts");
		
		String org = "";
		String orgDiv = "";
		String userId = "";
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "rest-viewSparePartList?org=" + org +"&orgDiv="+orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getAllSpareParts Details ends");
		return resp;
		
	}
	
	// getSubCategory
		@SuppressWarnings("unchecked")
		@GetMapping("spare-part-maintenance-subcategory")
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
		@PostMapping(value = { "spare-part-add" })
		public @ResponseBody JsonResponse<Object> addCodeAsset(@RequestBody List<AssetViewMasterModel> av,
				HttpSession session) {
			logger.info("Method : addCodeAsset starts");

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

		public JSONObject saveAllMediaDocuments(byte[] imageBytes, String ext, String user_id) {
			logger.info("Method : saveAllMedicalDocuments starts");

			String imageName = null;
			try {

				if (imageBytes != null) {
					long nowTime = new Date().getTime();

					if (ext.contentEquals("jpeg")) {
						imageName = user_id + "_" + nowTime + ".jpg";
					} else {
						imageName = user_id + "_" + nowTime + "." + ext;
					}
				}

				Path path = Paths.get(env.getAssetDocUrl() + imageName);
				if (imageBytes != null) {
					Files.write(path, imageBytes);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			String url = env.getAssetUrl() + imageName;

			JSONObject json = new JSONObject();

			try {
				json.put("filename", imageName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				json.put("fileurl", url);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			logger.info("Method : saveAllMediaDocuments ends");
			return json;
		}

		// viewQc

		@SuppressWarnings("unchecked")

		@GetMapping("spare-part-view")
		public @ResponseBody Object viewCodeAsset(@RequestParam String type,HttpSession session) {
			logger.info("Method :viewCodeAsset starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
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
				resp = restTemplate.getForObject(
						env.getAssetUrl() + "rest-view-asset?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&type=" + type+"&userId="+userId,
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

		// editQc
		@SuppressWarnings("unchecked")
		@GetMapping("spare-part-edit")
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

		// DeleteAsset

		@SuppressWarnings("unchecked")
		@PostMapping("spare-part-delete")
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

		// ScrapAsset

		@SuppressWarnings("unchecked")
		@PostMapping("spare-part-scrap")
		public @ResponseBody JsonResponse<Object> scrapCodeAsset(@RequestParam String id,String status, Model model,
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
						env.getAssetUrl() + "rest-asset-code-scrap?id=" + id + "&status=" + status+ "&org=" + orgName + "&orgDiv=" + orgDivision,
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

		// ApproveQc

		@SuppressWarnings("unchecked")
		@PostMapping("spare-part-approvew")
		public @ResponseBody JsonResponse<Object> approveCodeAsset(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : approveCodeAsset function starts");

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
						env.getAssetUrl() + "rest-approve-asset?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : approveCodeAssett function Ends");
			return res;
		}

		// Add Amount.

		@SuppressWarnings({ "unchecked", "deprecation" })
		@GetMapping(value = "spare-part-approve")
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

		// assignCodeAsset

		@SuppressWarnings("unchecked")
		@PostMapping("spare-part-assign")
		public @ResponseBody JsonResponse<Object> assignCodeAsset(@RequestParam String id, String assetcat, String assetemp,String qty,
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
				res = restTemplate.getForObject(env.getAssetUrl() + "rest-sparepart-master-code-assign?id=" + id + "&assetcat="
						+ assetcat + "&assetemp=" + assetemp + "&assigndate=" + assigndate+ "&qty=" + qty + "&org=" + orgName + "&orgDiv="
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

		// History log
		@SuppressWarnings("unchecked")
		@GetMapping("spare-part-history")
		public @ResponseBody Object historyAsset(@RequestParam String id, HttpSession session) {
			logger.info("Method :historyAsset starts");
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

				resp = restTemplate.getForObject(env.getAssetUrl() + "rest-asset-master-history?id=" + id + "&orgName="
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
			logger.info("Method :historyAsset ends");
			return resp;
		}

		// upload docs
		@PostMapping("spare-part-upload-file")
		public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
				HttpSession session) {

			logger.info("Method : uploadFile controller function 'post-mapping' starts");
			JsonResponse<Object> response = new JsonResponse<Object>();

			try {
				response.setMessage(inputFile.getOriginalFilename());
				session.setAttribute("quotationPFile", inputFile);

			} catch (RestClientException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("uploadFile: " + e.getMessage());
			}
			logger.info("Method : uploadFile controller function 'post-mapping' ends");
			return response;
		}

		// getSubCategory
		@SuppressWarnings("unchecked")
		@GetMapping("spare-part-subcategory")
		public @ResponseBody Object getSubCategoryForSpare(@RequestParam String id, HttpSession session) {
			logger.info("Method :getSubCategoryForSpare starts");
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

				resp = restTemplate.getForObject(env.getAssetUrl() + "rest-spare-part-subcategory?id=" + id + "&orgName="
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
			logger.info("Method :getSubCategoryForSpare ends");
			return resp;
		}
}
