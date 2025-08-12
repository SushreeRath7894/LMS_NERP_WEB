package nirmalya.aathithya.webmodule.property.serviceprovider.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.property.serviceprovider.model.ServiceProviderWorkdetailsModel;
@Controller
@RequestMapping(value = "property")
public class ServiceProviderWorkdetailsController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(ServiceProviderWorkdetailsController.class);

	@GetMapping(value = { "work-details" })
	public String workDetailsController(Model model, HttpSession session) {
		logger.info("Method :workDetailsController starts");
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

		logger.info("Method :workDetailsController ends");
		return "serviceprovider/workdetails";
	}
	@SuppressWarnings("unchecked")
	@GetMapping("work-details-view")
	public @ResponseBody List<ServiceProviderWorkdetailsModel> viewWorkDetails(HttpSession session,
			@RequestParam String userid) {
		logger.info("Method :viewWorkDetails starts" + userid);

		JsonResponse<List<ServiceProviderWorkdetailsModel>> resp = new JsonResponse<List<ServiceProviderWorkdetailsModel>>();
		List<ServiceProviderWorkdetailsModel> returnList = new ArrayList<ServiceProviderWorkdetailsModel>();
		logger.info("rssssssssssssssss:" + returnList);

		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "work-details-view?userid=" + userid,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewWorkDetails ends");
		return returnList;
	}
	@SuppressWarnings("unchecked")

	@GetMapping("work-details-edit")
	public @ResponseBody JsonResponse<ServiceProviderWorkdetailsModel> editworkDetails(Model model,
			@RequestParam String workid, HttpSession session) {

		logger.info("Method : editworkDetails starts");

		JsonResponse<ServiceProviderWorkdetailsModel> jsonResponse = new JsonResponse<ServiceProviderWorkdetailsModel>();
		logger.info(workid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "work-details-edit?workid=" + workid, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			ServiceProviderWorkdetailsModel ServiceProviderWorkdetailsModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<ServiceProviderWorkdetailsModel>() {
					});

			jsonResponse.setBody(ServiceProviderWorkdetailsModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editworkDetails ends");
		return jsonResponse;
	}


}
