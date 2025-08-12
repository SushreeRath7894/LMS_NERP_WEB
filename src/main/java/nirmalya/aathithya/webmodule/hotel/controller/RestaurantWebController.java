package nirmalya.aathithya.webmodule.hotel.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "hotel")
public class RestaurantWebController {
	Logger logger = LoggerFactory.getLogger(RestaurantWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/restaurant")
	public String viewBilling(Model model, HttpSession session) {
		logger.info("Method: viewBilling starts here");
		
	     logger.info("Method: viewBilling ends here");

	        return "hotel/restaurant";
	    }
	
	@GetMapping("/get-all-restaurant-item")
	public @ResponseBody Object getAllItemList(HttpSession session) {
		logger.info("Method :getAllItemList starts");

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
			DropDownModel[] item = restTemplate.getForObject(
					env.getHotelUrl() + "rest-get-all-restaurant-item?org=" + orgName + "&orgDiv=" + orgDivision , DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);

			if (itemList.size() > 0) {
				resp.setBody(itemList);
				resp.setCode("success");
				resp.setMessage("Data found");
			} else {
				resp.setBody(null);
				resp.setCode("failed");
				resp.setMessage("Data not found");
			}

		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setBody(null);
			resp.setCode("failed");
			resp.setMessage(e.getMessage());
		}

		logger.info("Method :getAllItemList ends");
		return resp;
	}
	
	/* save-all-restaurant-orders */
	
	@SuppressWarnings("unchecked")
	@PostMapping("save-all-restaurant-orders")
	public @ResponseBody Object saveAllRestOrders(HttpSession session, @RequestBody String data) {
		logger.info("Method :saveAllRestOrders starts");

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
			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-save-restro-orders?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :saveAllRestOrders ends");
		return resp;
	}
	

}
