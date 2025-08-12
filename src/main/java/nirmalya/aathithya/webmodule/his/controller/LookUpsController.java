package nirmalya.aathithya.webmodule.his.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "lookup")
public class LookUpsController {
	
	Logger logger = LoggerFactory.getLogger(LookUpsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@SuppressWarnings("unchecked")
	@GetMapping("lookups-getPatientDetailsById")
	public @ResponseBody Object getPatientDetailsById(HttpSession session, @RequestParam String id) {
		logger.info("Method :getPatientDetailsById starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			resp = restTemplate.getForObject(env.getHisUrl() + "getPatientDetailsById?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :getPatientDetailsById ends");
		return resp;
	}

	// GetState List

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "lookups-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "getPatientSateList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getstateList ends");
		return res;
	}

	// districtList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "lookups-districtList" })
	public @ResponseBody JsonResponse<Object> districtList(@RequestParam String id) {
		logger.info("Method : districtList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "districtList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		
		logger.info("Method : districtList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "lookups-cityList" })
	public @ResponseBody JsonResponse<Object> CityList(@RequestParam String id) {
		logger.info("Method : CityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "opd-recep-city-list?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		
		logger.info("Method : CityList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "lookups-testLists" })
	public @ResponseBody JsonResponse<Object> testList(@RequestParam String type, HttpSession session) {
		logger.info("Method : testList starts" + type);
		
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-getTestListTypeWise?type=" + type + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : testList ends");
		return res;
	}
	
}
