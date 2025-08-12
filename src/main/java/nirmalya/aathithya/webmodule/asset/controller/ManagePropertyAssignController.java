package nirmalya.aathithya.webmodule.asset.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.asset.model.AssetPropertyModel;
import nirmalya.aathithya.webmodule.asset.model.AssetPropertyOwnerModel;
import nirmalya.aathithya.webmodule.asset.model.AssetViewMasterModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.LocationRoomModel;

@Controller
@RequestMapping(value = "asset/")
public class ManagePropertyAssignController {

	Logger logger = LoggerFactory.getLogger(ManagePropertyAssignController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/manage-property-assign")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : Manage Property starts");

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
			DropDownModel[] locationType = restClient.getForObject(env.getMasterUrl() + "getLocationTypeList", DropDownModel[].class);
			List<DropDownModel> locationTypeList = Arrays.asList(locationType);
			
			model.addAttribute("locationTypeList", locationTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] roomType = restClient.getForObject(env.getMasterUrl() + "getRoomTypeList", DropDownModel[].class);
			List<DropDownModel> roomTypeList = Arrays.asList(roomType);
			
			model.addAttribute("roomTypeList", roomTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] country = restClient.getForObject(env.getMasterUrl() + "getCountryListForLocation", DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			AssetPropertyModel[] location = restClient.getForObject(env.getMasterUrl() + "getLocationList", AssetPropertyModel[].class);
			List<AssetPropertyModel> locationList = Arrays.asList(location);
			
			int count = 0;
			
			for(AssetPropertyModel m : locationList) {
				count = count + 1;
				if(m.getLocVirtual().equals("0")) {
					m.setLocVirtual("No");
				}
				if(m.getLocVirtual().equals("1")) {
					m.setLocVirtual("Yes");
				}
				if(m.getLocStatus().equals("0")) {
					m.setLocStatus("Inactive");
				}
				if(m.getLocStatus().equals("1")) {
					m.setLocStatus("Active");
				}
			}
			
			model.addAttribute("count", count);
			
			model.addAttribute("locationList", locationList);
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

		logger.info("Method : Manage Property ends");
		return "asset/manage-property-assign";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-property-assign-get-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForBilling(Model model, @RequestBody String tCountry,
	        BindingResult result) {
	    logger.info("Method : getStateNameForLocation starts");

	    JsonResponse<Object> res = new JsonResponse<Object>();

	    try {
	        res = restClient.getForObject(env.getMasterUrl() + "getStateListForLoc?id=" + tCountry,
	                JsonResponse.class);
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
	@PostMapping(value = { "manage-property-assign-get-city-list" })
	public @ResponseBody JsonResponse<Object> getCityForLocation(Model model, @RequestBody String tCountry,
	        BindingResult result) {
	    logger.info("Method : getCityForLocation starts");

	    JsonResponse<Object> res = new JsonResponse<Object>();

	    try {
	        res = restClient.getForObject(env.getMasterUrl() + "getCityForLocation?id=" + tCountry,
	                JsonResponse.class);
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

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-property-assign-save")
	public @ResponseBody JsonResponse<Object> saveLocationMaster(@RequestBody AssetPropertyModel location, HttpSession session) {
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
		
		if(inputFile!=null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes,fileType[1]);
				
				location.setFileLocation(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		logger.info("RESP DATA:::::"+location);
		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-save", location,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			session.removeAttribute("propertyFile");
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveLocationMaster starts");
		return resp;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");
		
		String imageName = null;
		
		try {
			
			if(imageBytes!=null) {
				long nowTime = new Date().getTime();
				if(ext.contentEquals("jpeg")) {
					imageName = nowTime+".jpg";
				} else {
					imageName = nowTime+"."+ext;
				}
				
			}

			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
			if(imageBytes !=null) {
				Files.write(path, imageBytes);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}
	
	// viewQc

	@SuppressWarnings("unchecked")

	@GetMapping("manage-property-assign-view")
	public @ResponseBody Object viewAssetProperty(HttpSession session) {
		logger.info("Method :viewAssetProperty starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String type="assign";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getAssetUrl() + "rest-manage-property-view?orgName=" + orgName + "&orgDivision=" + orgDivision + "&type=" + type,
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
		logger.info("Method :viewAssetProperty ends");
		return resp;
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-assign-get-location-details")
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-get-details?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editAssetProperty ends");
		return resp;
	}

	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-property-assign-save-floor")
	public @ResponseBody JsonResponse<Object> saveFloorMaster(@RequestBody AssetPropertyModel location, HttpSession session) {
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

	
	@SuppressWarnings({ "unchecked", "unused" })
	@PostMapping(value = { "manage-property-assign-get-loc-room-details" })
	public @ResponseBody JsonResponse<Object> getPropertyRoomDetails(Model model, @RequestBody List<String> floorId,
			BindingResult result) {
		logger.info("Method : getPropertyRoomDetails starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-property-get-loc-room-details", floorId,
					JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		logger.info("Method : getPropertyRoomDetails ends");
		return resp;
		
	}


	@SuppressWarnings("unchecked")
	@PostMapping("/manage-property-assign-save-room")
	public @ResponseBody JsonResponse<Object> saveRoomForAssetProperty(@RequestBody LocationRoomModel location, HttpSession session) {
		logger.info("Method : saveRoomForAssetProperty starts");
		
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
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-save-room", location,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : saveRoomForAssetProperty starts");
		return resp;
	}

	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-property-assign-get-loc-floor-details" })
	public @ResponseBody JsonResponse<List<AssetPropertyModel>> getLocationFloorDetails(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getLocationFloorDetails starts");
		
		JsonResponse<List<AssetPropertyModel>> res = new JsonResponse<List<AssetPropertyModel>>();
		
		try {
			res = restClient.getForObject(env.getAssetUrl() + "rest-manage-property-get-loc-floor-details?id=" + tCountry,
					JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getLocationDetails ends");
		return res;
		
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-property-assign-floor-delete")
	public @ResponseBody JsonResponse<Object> deleteFloorForAsset(@RequestBody String floor, HttpSession session) {
		logger.info("Method : deleteFloorForAsset starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "rest-manage-property-assign-delete?id="+floor+"&createdBy="+userId,
					JsonResponse.class);	
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteFloorForAsset starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-property-assign-room-delete")
	public @ResponseBody JsonResponse<Object> deleteRoomForAsset(@RequestBody String floor, HttpSession session) {
		logger.info("Method : deleteRoomForAsset starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "rest-manage-property-room-delete?id="+floor+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteRoomForAsset starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-property-assign-location-delete")
	public @ResponseBody JsonResponse<Object> deleteLocationForAsset(@RequestBody List<DropDownModel> locationList, HttpSession session) {
		logger.info("Method : deleteLocationForAsset starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(DropDownModel m : locationList) {
			m.setName(userId);
		}
		try {
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-location-delete",locationList,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteLocationForAsset starts");
		return resp;
	}

	@PostMapping("/manage-property-assign-upload-file")
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

	@SuppressWarnings("unchecked")
	@PostMapping("manage-property-assign-deleteP")
	public @ResponseBody JsonResponse<Object> deleteLocationForAsset(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteLocationForAsset function starts");
		logger.info("DELETE IDDDDDDDDDD = " + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAssetUrl() + "rest-manage-property-delete?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteLocationForAsset function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-property-assign-add-file-delete")
	public @ResponseBody JsonResponse<Object> deleteLocationFile(@RequestBody String floor, HttpSession session) {
		logger.info("Method : deleteLocationFile starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "rest-manage-property-file-delete?id="+floor+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteLocationFile starts");
		return resp;
	}
	
	@PostMapping("/manage-property-assign-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile controller function 'post-mapping' starts");
		
		JsonResponse<Object> response = new JsonResponse<Object>();
		
		try {
			session.removeAttribute("propertyFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : deleteFile controller function 'post-mapping' ends");
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-assign-asset-list")
	public @ResponseBody Object showTotalAsset(@RequestParam String type,String assetcat,String assetscat, HttpSession session) {
		logger.info("Method :showTotalAsset starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-asset-list?type=" + type + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision+ "&cat=" + assetcat+ "&scat=" + assetscat+ "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :showTotalAsset ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = "manage-property-assign-asset-assign")
	public @ResponseBody JsonResponse<Object> assignAsset(@RequestParam String id, String locid, String loctype,
			String date, Model model, HttpSession session) {
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
			res = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-asset-assign?id=" + id + "&locid=" + locid+ "&loctype=" + loctype+ "&date=" + date
					+ "&org=" + orgName + "&orgDiv=" + orgDivision+ "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : assignAsset function Ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-assign-report-list")
	public @ResponseBody Object showTotalAsset(@RequestParam String type,String id, HttpSession session) {
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-manage-property-report-list-assign?type=" + type + "&id=" + id+ "&orgName=" + orgName
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
		logger.info("Method :showTotalAsset ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-property-assign-add-owner" })
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
			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-property-add-owner", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addOwnerOfProperty starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("manage-property-assign-delete-owner")
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
					env.getAssetUrl() + "rest-manage-property-delete-owner?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteCodeAsset function Ends");

		return res;
	}

	// getSubCategory
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-assign-subcategory")
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
}
