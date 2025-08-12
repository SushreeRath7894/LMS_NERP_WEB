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

import nirmalya.aathithya.webmodule.asset.model.AssetPropertyModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.LocationRoomModel;

@Controller
@RequestMapping(value = "asset/")

public class FloorManagementController {

	Logger logger = LoggerFactory.getLogger(FloorManagementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("floor-management")
	public String PropertyPage(Model model, HttpSession session) {
		logger.info("Method : PropertyPage floor-management starts");

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
			DropDownModel[] bedType = restClient.getForObject(env.getMasterUrl() + "getBedTypeList",
					DropDownModel[].class);
			List<DropDownModel> bedTypeList = Arrays.asList(bedType);

			model.addAttribute("bedTypeList", bedTypeList);
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

		logger.info("Method : PropertyPage floor-management ends");
		return "his_asset/floor-management";
	}
	// property floor management methodsss

	@SuppressWarnings("unchecked")
	@GetMapping("floor-management-view-building-floor")
	public @ResponseBody Object viewBuildingFloor(HttpSession session) {
		logger.info("Method :viewBuildingFloor starts");
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
					env.getAssetUrl() + "rest-viewBuildingFloor?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewBuildingFloor ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("floor-management-view-floor-rooms")
	public @ResponseBody Object viewFloorRooms(@RequestParam String id, HttpSession session) {
		logger.info("Method :viewFloorRooms starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewFloorRooms?floorId=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewFloorRooms ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("floor-management-save-room")
	public @ResponseBody JsonResponse<Object> saveRoomForAssetProperty(@RequestBody LocationRoomModel location,
			HttpSession session) {
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
	@PostMapping("floor-management-delete-room")
	public @ResponseBody JsonResponse<Object> deleteRoom(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteRoom function starts");
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
					env.getAssetUrl() + "rest-delete-room?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteRoom function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("floor-management-save-details")
	public @ResponseBody JsonResponse<Object> saveFloorDetails(@RequestBody LocationRoomModel location,
			HttpSession session) {
		logger.info("Method : saveFloorDetails starts");

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
			resp = restClient.postForObject(env.getAssetUrl() + "rest-save-floor-details", location,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveFloorDetails starts");
		return resp;
	}


	@SuppressWarnings("unchecked")
	@GetMapping("floor-management-view-floor-details")
	public @ResponseBody Object viewFloorDets(@RequestParam String id, HttpSession session) {
		logger.info("Method :viewFloorDets starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewFloorDets?floorId=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewFloorDets ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("floor-management-delete-floor-detail")
	public @ResponseBody JsonResponse<Object> deleteFloorDetails(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteFloorDetails function starts");
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
					env.getAssetUrl() + "rest-deleteFloorDetails?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteFloorDetails function Ends");

		return res;
	}
	
}
