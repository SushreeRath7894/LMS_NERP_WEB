package nirmalya.aathithya.webmodule.hotel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import java.util.Base64;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;

@Controller
@RequestMapping(value = "hotel")
public class FrontdeskWebController {

	Logger logger = LoggerFactory.getLogger(FrontdeskWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/front-desk")
	public String colleges(Model model, HttpSession session) {
		logger.info("Method : hotel-frontdesk starts");

		try {

			// getCountry
			DropDownModel[] getUserCountry = restTemplate.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> conList = Arrays.asList(getUserCountry);
			model.addAttribute("countryList", conList);

		} catch (RestClientException e) {
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
		

		logger.info("Method : hotel-frontdesk ends");
		return "hotel/frontdesk";
	}

	// State-List
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "corporate-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateList starts" + " " + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getStateListForLoc?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("state" + res);
		logger.info("Method : getstateList ends");
		return res;
	}

	// city-list
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "corporate-city-list" })
	public @ResponseBody JsonResponse<Object> CityList(@RequestParam String id) {
		logger.info("Method : CityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getCityForLocation?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("unsuccess");
		}
		logger.info("Method : CityList ends");
		return res;
	}

	// Save CorporateDetails

	@SuppressWarnings("unchecked")
	@PostMapping("save-corporate-details")
	public @ResponseBody JsonResponse<Object> saveCorporate(HttpSession session,
			@RequestBody Map<String, Object> corporateData) {
		logger.info("Method : saveCorporate starts");
		logger.info("Received saveCorporate data: {}", corporateData);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			corporateData.put("orgName", orgName);
			corporateData.put("userId", userId);
			corporateData.put("orgDivision", orgDivision);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String custType = (String) corporateData.get("custType");
            if (!"Corporates".equals(custType) &&  corporateData.containsKey("photo") && corporateData.containsKey("photoName")) {
                String base64Image = (String) corporateData.get("photo");
                String photoName = (String) corporateData.get("photoName");
                

                if (base64Image != null && photoName != null && base64Image.startsWith("data:image")) {
                    String base64Data = base64Image.replaceFirst("^data:image/[^;]+;base64,", "");

                    byte[] imageBytes = Base64.getDecoder().decode(base64Data);

                    String uploadDir = env.getFileUploadDocumenttUrl();
                    
                    String filePath = uploadDir + photoName;
                    try (FileOutputStream fos = new FileOutputStream(filePath)) {
                        fos.write(imageBytes);
                        logger.info("Image saved successfully to: {}", filePath);
                    } catch (IOException e) {
                        logger.error("Error saving image: {}", e.getMessage());
                        resp.setMessage("Failed to save image");
                        return resp;
                    }

                    // Remove the photo key from corporateData
                    corporateData.remove("photo");
                } else {
                    logger.warn("Invalid or missing photo or photoName in corporateData");
                    resp.setMessage("Invalid or missing photo or photoName");
                    return resp;
                }
            } 

			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-save-corporate-details", corporateData,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveCorporate ends");
		return resp;
	}

	// view-Corporate-Details
	@SuppressWarnings("unchecked")

	@GetMapping("corporatedetails-view")
	public @ResponseBody Object viewStudent(HttpSession session, @RequestParam String type) {
		logger.info("Method :corporatedetails-view starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-corporatedetails-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :corporatedetails-view ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("corporateDetails-edit")
	public @ResponseBody JsonResponse<Object> editApplication(@RequestParam String id, HttpSession session) {
	    logger.info("Method : corporateDetails-edit function starts");

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    ObjectMapper mapper = new ObjectMapper();

	    try {
	        resp = restTemplate.getForObject(env.getHotelUrl() + "rest-corporateDetails-edit?id=" + id,
	                JsonResponse.class);

	        if (resp.getBody() != null && resp.getBody() instanceof String) {
	            // Step 1: Convert JSON string to Map
	            String jsonBody = (String) resp.getBody();
	            Map<String, Object> bodyMap = mapper.readValue(jsonBody, new TypeReference<Map<String, Object>>() {});

	            if (bodyMap.containsKey("corporateDetails")) {
	                List<Map<String, Object>> corpList = (List<Map<String, Object>>) bodyMap.get("corporateDetails");
                    System.out.println(corpList);
	                for (Map<String, Object> corp : corpList) {
	                    Object idNameObj = corp.get("idName");
	                    if (idNameObj != null && !"null".equals(idNameObj.toString())) {
	                        String idName = idNameObj.toString();
	                        String fullUrl = env.getBaseURL() + "document/image/" + idName;
	                        corp.put("idName", fullUrl);
	                    }
	                }
	            }

	            // Step 2: Convert modified Map back to JSON string and set it as body
	            resp.setBody(mapper.writeValueAsString(bodyMap));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    logger.info("Method : corporateDetails-edit function Ends");

	    return resp;
	}



	// Reservation Tab Data Handle ---Here --->>>

	@SuppressWarnings("unchecked")
	@PostMapping("savehotel-reservation-details")
	public @ResponseBody JsonResponse<Object> saveReservation(HttpSession session,
			@RequestBody Map<String, Object> reservationdata) {
		logger.info("Method : saveReservation starts");
		logger.info("Received Reservation data: {}", reservationdata);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-savehotel-reservation-details",
					reservationdata, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveReservation ends");
		return resp;
	}

	// view-Application-Details

	@SuppressWarnings("unchecked")

	@GetMapping("get-hotel-reservation-view")
	public @ResponseBody Object viewReservation(HttpSession session) {
		logger.info("Method :viewReservation starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-get-hotel-reservation-view?orgName=" + orgName
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
		logger.info("Method :viewReservation ends" + resp);
		return resp;
	}

	// Edit Application ::

	@SuppressWarnings("unchecked")

	@GetMapping("hotel-reservation-edit")
	public @ResponseBody JsonResponse<Object> editReservation(@RequestParam String bookingId, HttpSession session) {

		logger.info("Method : editReservation function starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-hotel-reservation-edit?bookingId=" + bookingId,
					JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : editReservation function Ends");

		return resp;
	}

	// Check-in Tab Handles Here ---->>>>
	// Save Check-in
	@SuppressWarnings("unchecked")
	@PostMapping("save-hotel-checkin-details")
	public @ResponseBody JsonResponse<Object> saveCheckinHotel(HttpSession session,
			@RequestBody Map<String, Object> checkinData) {
		logger.info("Method : saveCheckinHotel starts");
		logger.info("Received CheckinHotel data: {}", checkinData);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-savehotel-checkin-details", checkinData,
					JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveCheckinHotel ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("get-hotel-room-type-list")
	public @ResponseBody Object getRoomTypeList(HttpSession session) {
		logger.info("Method :getRoomTypeList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			// getRooms
			DropDownModel[] getUserRooms = restTemplate.getForObject(env.getHotelUrl() + "getUserRooms",
					DropDownModel[].class);
			List<DropDownModel> roomList = Arrays.asList(getUserRooms);
			resp.setBody(roomList);
			System.out.println(roomList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :getRoomTypeList ends" + resp);
		return resp;
	}

}
