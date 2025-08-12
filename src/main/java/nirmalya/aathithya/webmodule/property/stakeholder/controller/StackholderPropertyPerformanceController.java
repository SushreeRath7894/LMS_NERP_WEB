package nirmalya.aathithya.webmodule.property.stakeholder.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.property.stakeholder.model.StakeholderPropertyPerformanceDetailsModel;

@Controller
@RequestMapping(value = "property")
public class StackholderPropertyPerformanceController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(StackholderPropertyPerformanceController.class);

	/* property-performance */

	@GetMapping(value = { "property-performance" })
	public String propertyPerformance(Model model, HttpSession session) {
		logger.info("Method :propertyPerformance starts");
		
		try {
			DropDownModel[] Property = restClient.getForObject(env.getPropertyUrl() + "getproperty-type-List",
					DropDownModel[].class);
			List<DropDownModel> propertyList = Arrays.asList(Property);

			model.addAttribute("propertyList", propertyList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String userId = "";
		String userRole = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
		}
		model.addAttribute("userId", userId);

		logger.info("Method : propertyPerformance ends");
		return "new-property/property-performance";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "property-performance-autosearch-property"})
	public @ResponseBody JsonResponse<DropDownModel> getPropertyAutoSearch(Model model,
			@RequestBody String id, BindingResult result) {
		logger.info("Method : getPropertyAutoSearch starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getPropertyUrl()+ "getPropertyListByAutoSearch?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getPropertyAutoSearch ends"+res);
		return res;
	}
	
	/// add
	@SuppressWarnings("unchecked")
	@PostMapping("property-performance-add")
	public @ResponseBody JsonResponse<Object> saveProperty(@RequestBody StakeholderPropertyPerformanceDetailsModel StakeholderPropertyPerformanceDetailsModel,
			HttpSession session) {
		logger.info("Method:property performance save starts" + StakeholderPropertyPerformanceDetailsModel);
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("DDDDDDDDDDDD" + StakeholderPropertyPerformanceDetailsModel);
		try {
			res = restClient.postForObject(env.getPropertyUrl() + "property-performance-add-Performancedetails",
					StakeholderPropertyPerformanceDetailsModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
			
		}

		logger.info("Method:property performance save ends");
		return res;
	}
	/// view
	@SuppressWarnings("unchecked")

	@GetMapping("/property-performance-view")
	public @ResponseBody List<StakeholderPropertyPerformanceDetailsModel> viewPropertyPerformance(HttpSession session,
			 @RequestParam String userid,@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : PropertyPerformance starts"+userid+fromDate+toDate);

		JsonResponse<List<StakeholderPropertyPerformanceDetailsModel>> resp = new JsonResponse<List<StakeholderPropertyPerformanceDetailsModel>>();
		List<StakeholderPropertyPerformanceDetailsModel> returnList = new ArrayList<StakeholderPropertyPerformanceDetailsModel>();

		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "property-performance-view?userid="+userid+"&fromDate="+fromDate+"&toDate="+toDate,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		//logger.info(returnList);
		
		logger.info("Method : PropertyPerformance ends");
		return returnList;
	}
	///edit
	@SuppressWarnings("unchecked")
	@GetMapping("property-performance-edit")
	public @ResponseBody JsonResponse<List<Object>> editPropertyPerformance(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editPropertyPerformance starts"+id	);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		try {
			jsonResponse = restClient.getForObject(env.getPropertyUrl() + "editPropertyPerformance?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editPropertyPerformance ends");
		return jsonResponse;
	}
	//delete 
	@SuppressWarnings("unchecked")
	@GetMapping("property-performance-delete")
	public @ResponseBody JsonResponse<Object> deletePropertyPerformance(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deletePropertyPerformance function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		

		try {
			res = restClient.getForObject(env.getPropertyUrl() + "property-performance-delete-PropertyPerformance?id=" + id  , JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deletepropertyPerformance function Ends");
		
		logger.info("RESPPPPPPP"+res);
		return res;
	}
	
	

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "property-performance-autoComplete"})
	public @ResponseBody JsonResponse<DropDownModel> getPropertyAutocomplete(Model model,
			@RequestBody String id, BindingResult result) {
		logger.info("Method : getPropertyAutocomplete starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getPropertyUrl()+ "getProperty-type-autocomplete?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getPropertyAutocomplete ends"+res);
		return res;
	}


}
