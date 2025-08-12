package nirmalya.aathithya.webmodule.asset.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import nirmalya.aathithya.webmodule.asset.model.AssetPropertyModel;
import nirmalya.aathithya.webmodule.asset.model.AssetPropertyOwnerModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "asset/")
public class PropertyManagementController {

	Logger logger = LoggerFactory.getLogger(AssetViewMasterController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("property")
	public String PropertyPage(Model model, HttpSession session) {
		logger.info("Method : PropertyPage property-management starts");

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
			DropDownModel[] locationType = restClient.getForObject(env.getMasterUrl() + "getLocationTypeList",
					DropDownModel[].class);
			List<DropDownModel> locationTypeList = Arrays.asList(locationType);

			model.addAttribute("locationTypeList", locationTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] roomType = restClient.getForObject(env.getMasterUrl() + "getRoomTypeList",
					DropDownModel[].class);
			List<DropDownModel> roomTypeList = Arrays.asList(roomType);

			model.addAttribute("roomTypeList", roomTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] country = restClient.getForObject(env.getMasterUrl() + "getCountryListForLocation",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			AssetPropertyModel[] location = restClient.getForObject(env.getMasterUrl() + "getLocationList",
					AssetPropertyModel[].class);
			List<AssetPropertyModel> locationList = Arrays.asList(location);

			int count = 0;
			for (AssetPropertyModel m : locationList) {
				count = count + 1;
				if (m.getLocVirtual().equals("0")) {
					m.setLocVirtual("No");
				}
				if (m.getLocVirtual().equals("1")) {
					m.setLocVirtual("Yes");
				}
				if (m.getLocStatus().equals("0")) {
					m.setLocStatus("Inactive");
				}
				if (m.getLocStatus().equals("1")) {
					m.setLocStatus("Active");
				}
			}
			model.addAttribute("count", count);

			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : PropertyPage property-management ends");
		return "his_asset/property-management";
	}

//for state list drop down
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "building-management-get-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForBilling(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForLocation starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getStateListForLoc?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateNameForLocation ends" + res);
		return res;
	}

//for city list drop down
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "building-management-get-city-list" })
	public @ResponseBody JsonResponse<Object> getCityForLocation(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getCityForLocation starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getCityForLocation?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getCityForLocation ends" + res);
		return res;
	}
// image methods starts

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}

	@PostMapping("building-management-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("propertyFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	@PostMapping("building-management-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile controller function for session clearing 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.setAttribute("propertyFile", null);
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile controller function 'post-mapping' ends");
		return response;
	}

// image methods ends

// building details methods starts

	@SuppressWarnings("unchecked")
	@GetMapping("building-management-details")
	public @ResponseBody Object editAssetProperty(@RequestParam String id, HttpSession session) {
		logger.info("Method :editAssetProperty starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-get-details?id=" + id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editAssetProperty ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("building-management-save")
	public @ResponseBody JsonResponse<Object> saveLocationMaster(@RequestBody AssetPropertyModel location,
			HttpSession session) {
		logger.info("Method : saveLocationMaster starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		location.setCreatedBy(userId);
		location.setOrganization(org);
		location.setOrgDivision(orgDiv);
		MultipartFile inputFile = (MultipartFile) session.getAttribute("propertyFile");
		byte[] bytes;
		String imageName = null;
		if(location.getFileLocation() == null || location.getFileLocation() == "") {
			if (inputFile != null) {
				try {
					bytes = inputFile.getBytes();
					String[] fileType = inputFile.getContentType().split("/");
					imageName = saveAllImage(bytes, fileType[1]);
					location.setFileLocation(imageName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				location.setFileLocation(null);
			}
		}
		
		logger.info("RESP DATA:::::" + location);
		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-save", location,
					JsonResponse.class);
			session.setAttribute("propertyFile", null);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveLocationMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("building-management-view")
	public @ResponseBody Object viewAssetProperty(HttpSession session) {
		logger.info("Method :viewAssetProperty starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String type = "main";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewAssetProperty ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("building-management-delete")
	public @ResponseBody JsonResponse<Object> deleteLocationForAsset(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteLocationForAsset function starts");

		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		logger.info("DELETE IDDDDDDDDDD = " + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAssetUrl() + "rest-manage-property-delete?id=" + id + "&userId="
					+ userId + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteLocationForAsset function Ends");

		return res;
	}
// building details methods ends

// owner/lease-rent details methods starts

	@SuppressWarnings("unchecked")
	@GetMapping("building-management-report-list")
	public @ResponseBody Object showOwnerDetails(@RequestParam String type, String id, HttpSession session) {
		logger.info("Method :showOwnerDetails starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-report-list?type=" + type
					+ "&id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :showOwnerDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "building-management-add-owner" })
	public @ResponseBody JsonResponse<Object> addOwnerOfProperty(@RequestBody List<AssetPropertyOwnerModel> av,
			HttpSession session) {
		logger.info("Method : addOwnerOfProperty starts");

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

		for (AssetPropertyOwnerModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}

		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-add-owner", av,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addOwnerOfProperty starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("building-management-delete-owner")
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
			res = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-delete-owner?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteCodeAsset function Ends");

		return res;
	}

// owner/lease-rent details methods ends

// property documnentation methods starts

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "building-management-add-documents" })
	public @ResponseBody JsonResponse<Object> addDocsOfProperty(@RequestBody List<AssetPropertyOwnerModel> av,
			HttpSession session) {
		logger.info("Method : addDocsOfProperty starts" + av);

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

		for (AssetPropertyOwnerModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}

		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-add-documents", av,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addDocsOfProperty starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("building-management-view-document")
	public @ResponseBody Object getDocsOfProperty(@RequestParam String id, HttpSession session) {
		logger.info("Method :getDocsOfProperty starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-getDocsOfProperty?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getDocsOfProperty ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("building-management-delete-documents")
	public @ResponseBody JsonResponse<Object> deleteDocuments(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteDocuments function starts");
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
			res = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-deleteDocuments?id=" + id
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteDocuments function Ends");

		return res;
	}
// property documnentation methods ends

// property floor methods starts

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "building-management-floor-details" })
	public @ResponseBody JsonResponse<List<AssetPropertyModel>> getFloorDetails(Model model,
			@RequestBody String tCountry, BindingResult result) {
		logger.info("Method : getFloorDetails starts");

		JsonResponse<List<AssetPropertyModel>> res = new JsonResponse<List<AssetPropertyModel>>();

		try {
			res = restClient.getForObject(
					env.getAssetUrl() + "rest-manage-property-get-loc-floor-details?id=" + tCountry,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getFloorDetails ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("building-management-save-floor")

	public @ResponseBody JsonResponse<Object> saveFloorMaster(@RequestBody AssetPropertyModel location,
			HttpSession session) {
		logger.info("Method : savePropertyFloor starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		location.setCreatedBy(userId);
		location.setOrganization(org);
		location.setOrgDivision(orgDiv);

		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-save-floor", location,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : savePropertyFloor starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("building-management-delete-floor")
	public @ResponseBody JsonResponse<Object> deleteFloors(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteFloors function starts");
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
			res = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-deleteFloors?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteFloors function Ends");

		return res;
	}
// property floor methods ends

}
