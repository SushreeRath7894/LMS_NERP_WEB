package nirmalya.aathithya.webmodule.hotel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "hotel")
public class HotelConfigurationController {
	Logger logger = LoggerFactory.getLogger(HotelConfigurationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/rate-configuration")
	public String rateConfig(Model model, HttpSession session) {
		logger.info("Method: rate-configuration starts here");

		try {

			// getRooms
			DropDownModel[] getUserRooms = restTemplate.getForObject(env.getHotelUrl() + "getUserRooms",
					DropDownModel[].class);
			List<DropDownModel> roomList = Arrays.asList(getUserRooms);
			model.addAttribute("roomTypeList", roomList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method: rate-configuration ends here");

		return "hotel/rate-configuration";
	}

	@GetMapping("/service-configuration")
	public String serviceConfig(Model model, HttpSession session) {
		logger.info("Method: service-configuration starts here");

		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			// getRooms
			DropDownModel[] getUserRooms = restTemplate.getForObject(env.getHotelUrl() + "getUserRooms",
					DropDownModel[].class);
			List<DropDownModel> roomList = Arrays.asList(getUserRooms);
			model.addAttribute("roomTypeList", roomList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] hsnCode = restTemplate.getForObject(
					env.getHotelUrl() + "getProductCategoryList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> productCategoryList = Arrays.asList(hsnCode);
			model.addAttribute("productCategoryList", productCategoryList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] hsnCode = restTemplate.getForObject(
					env.getHotelUrl() + "getProductCategoryList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> productCategoryList = Arrays.asList(hsnCode);
			model.addAttribute("productCategoryList", productCategoryList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method: service-configuration ends here");

		return "hotel/service-configuration";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("hotel-management-view")
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
	@PostMapping("save-ratetype-details")
	public @ResponseBody JsonResponse<Object> saveRatetypesData(HttpSession session,
			@RequestBody Map<String, Object> hotelData) {

		logger.info("Method : saveRatetypesDataDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			hotelData.put("userId", userId);
			hotelData.put("org", orgName);
			hotelData.put("orgDiv", orgDivision);
		} catch (Exception e) {

		}

		try {
			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-save-ratetype-details", hotelData,
					JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveRatetypesDataDetails ends");
		return resp;
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("ratetype-edit")
	public @ResponseBody Object editRatetype(@RequestParam String id, HttpSession session) {
		logger.info("Method :editRatetype starts");
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
			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-ratetype-edit?propertyId=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editRatetype ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("get-roomtype-data")
	public @ResponseBody Object getRoomTypeData(@RequestParam String id, HttpSession session) {
		logger.info("Method :getRoomTypeData starts");
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
			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-get-room-type-data?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getRoomTypeData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("get-bed-data")
	public @ResponseBody Object getBedData(@RequestParam String id, String propertyId, HttpSession session) {
		logger.info("Method :getBedData starts");
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
			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-get-bed-data?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&propertyId=" + propertyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getBedData ends");
		return resp;
	}

	// view

	@SuppressWarnings("unchecked")
	@GetMapping("ratetypes-view")
	public @ResponseBody Object viewRatetype(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewRatetype starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-ratetypes-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && !resp.getMessage().isEmpty()) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage("No Data Found");
		}
		logger.info("Method : viewRatetype ends");
		return resp;
	}

	// Service Config

	@SuppressWarnings("unchecked")

	@PostMapping("save-hotel-details")
	public @ResponseBody JsonResponse<Object> saveService(HttpSession session,
			@RequestBody Map<String, Object> studentData) {
		logger.info("Method : saveService starts");
		logger.info("Received saveService data: {}", studentData);
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

			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-save-hotel-details", studentData,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveService ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("servicetype-details-edit")
	public @ResponseBody JsonResponse<?> editServiceType(@RequestParam String id, HttpSession session) {
		logger.info("Method :editServiceType starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			if (orgName == null || orgDivision == null) {
				logger.warn("Session attributes ORGANIZATION or ORGANIZATION_DIVISION are missing");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String url = env.getHotelUrl() + "rest-servicetype-details-edit?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision;

			logger.info("Calling URL: " + url);

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :editServiceType ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("servicetype-view")
	public @ResponseBody Object viewService(HttpSession session, @RequestParam String id) {
		logger.info("Method :viewService starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-servicetype-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewService ends" + resp);
		return resp;

	}
	// ==============================Taxtype
	// details==================================

	@GetMapping("/tax-types")
	public String adminDashboard(Model model, HttpSession session) {
		logger.info("Method: Hotel starts here");

		logger.info("Method: Hotel ends here");
		return "hotel/taxtype.html";
	}

	// SAVE
	@SuppressWarnings("unchecked")

	@PostMapping("save-taxtype-details")
	public @ResponseBody JsonResponse<Object> saveTaxDetails(HttpSession session,
			@RequestBody Map<String, Object> studentData) {
		logger.info("Method : saveTaxDetails starts");
		logger.info("Received saveTaxDetails data: {}", studentData);
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

			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-save-taxtype-details", studentData,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTaxDetails ends");
		return resp;
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("taxtype-details-edit")
	public @ResponseBody Object editTaxType(HttpSession session, @RequestParam String taxtypeId) {
		logger.info("Method :editTaxType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-taxtype-details-edit?taxtypeId=" + taxtypeId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :editTaxType ends" + resp);
		return resp;
	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("property-taxtype-view")
	public @ResponseBody Object viewTaxtype(HttpSession session) {
		logger.info("Method :viewTaxtype starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(
					env.getHotelUrl() + "rest-property-taxtype-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewTaxtype ends" + resp);
		return resp;
	}

	// get Subcategory --------------->>>>>>>>>>>>>>>>>>>
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-products-get-subcategory" })
	public @ResponseBody JsonResponse<Object> subcategory(String id, HttpSession session) {
		logger.info("Method : subcategory starts");
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
		logger.info("Method : orgName starts" + orgName);
		logger.info("Method : orgDivision starts" + orgDivision);
		try {

			resp = restTemplate.getForObject(
					env.getMasterUrl() + "subcategory?org=" + orgName + "&orgDiv=" + orgDivision + "&id=" + id,
					JsonResponse.class);
			// res = restTemplate.getForObject(env.getPurchaseUrl() +
			// "getBrandList?orgName=" + orgName + "&orgDivision=" +
			// orgDivision,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : subcategory ends");
		return resp;
	}

	// ================Restaurant Food Details=============
	@GetMapping("/restaurant-configuration")
	public String restaurantFood(Model model, HttpSession session) {
		logger.info("Method: Hotel starts here");

		logger.info("Method: Hotel ends here");

		return "hotel/restaurant-config";
	}

	// SAVEFood detail-----------
	@SuppressWarnings("unchecked")

	@PostMapping("save-restaurant-food-details")
	public @ResponseBody JsonResponse<Object> saveRestaurantFoodDetails(HttpSession session,
			@RequestBody Map<String, Object> resturantFoodLists) {
		logger.info("Method : saveRestaurantFoodDetails starts");
		logger.info("Received saveRestaurantFoodDetails data: {}", resturantFoodLists);
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

			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-save-restaurant-food-details",
					resturantFoodLists, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveRestaurantFoodDetails ends");
		return resp;
	}

	// editFood detail-----------
	@SuppressWarnings("unchecked")
	@GetMapping("hotel-restaurantfood-edit")
	public @ResponseBody Object editRestaurantFoodDetails(HttpSession session, @RequestParam String item_Code) {
		logger.info("Method :editRestaurantFoodDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(
					env.getHotelUrl() + "rest-hotel-restaurantfood-edit?item_Code=" + item_Code, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :editRestaurantFoodDetails ends" + resp);
		return resp;
	}

	// viewFood detail-----------
	@SuppressWarnings("unchecked")
	@GetMapping("hotel-restaurant-food-view")
	public @ResponseBody Object viewRestaurantFoodDetails(HttpSession session) {
		logger.info("Method :viewRestaurantFoodDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-hotel-restaurant-food-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewRestaurantFoodDetails ends" + resp);
		return resp;
	}

	// ===============================================================
	// SAVETime detail-----------
	@SuppressWarnings("unchecked")

	@PostMapping("save-restaurant-food-time-details")
	public @ResponseBody JsonResponse<Object> saveRestaurantFoodTime(HttpSession session,
			@RequestBody Map<String, Object> resturantFoodTimeLists) {
		logger.info("Method : saveRestaurantFoodTime starts");
		logger.info("Received saveRestaurantFoodTime data: {}", resturantFoodTimeLists);
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

			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-save-restaurant-food-time-details",
					resturantFoodTimeLists, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveRestaurantFoodTime ends");
		return resp;
	}

	// viewTime detail-----------
	@SuppressWarnings("unchecked")
	@GetMapping("hotel-restaurant-food-time-view")
	public @ResponseBody Object viewRestaurantFoodTime(HttpSession session) {
		logger.info("Method :viewRestaurantFoodTime starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-hotel-restaurant-food-time-view?orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewRestaurantFoodTime ends" + resp);
		return resp;
	}

	// editTime detail-----------
	@SuppressWarnings("unchecked")
	@GetMapping("hotel-restauranttime-edit")
	public @ResponseBody Object editRestaurantTimeDetails(HttpSession session, @RequestParam String tableId) {
		logger.info("Method :editRestaurantTimeDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-hotel-restauranttime-edit?tableId=" + tableId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :editRestaurantTimeDetails ends" + resp);
		return resp;
	}
}
