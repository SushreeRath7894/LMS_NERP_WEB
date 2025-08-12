package nirmalya.aathithya.webmodule.hotel.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "user")
public class OrganisationFreeTrialRegestration {
	Logger logger = LoggerFactory.getLogger(OrganisationFreeTrialRegestration.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EnvironmentVaribles env;
    
    @SuppressWarnings("unchecked")
	@PostMapping("save-organisation-details")
	public @ResponseBody JsonResponse<Object> saveOrganisationFreeTrialReg(HttpSession session,
			@RequestBody Map<String, Object> orgData) {
		logger.info("Method : saveOrganisationFreeTrialReg starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.postForObject(env.getUserUrl() + "free-trail-org-registration",orgData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveOrganisationFreeTrialReg ends");
		return resp;
	}
    
  //State-List
  	@SuppressWarnings("unchecked")
  	@GetMapping(value = { "get-stateList" })
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
  	@GetMapping(value = { "get-city-list" })
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
  		logger.info("state" + res);
  		logger.info("Method : CityList ends");
  		System.out.println("hiii");
  		return res;
  	}
}
