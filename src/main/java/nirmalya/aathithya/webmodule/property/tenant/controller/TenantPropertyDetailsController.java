package nirmalya.aathithya.webmodule.property.tenant.controller;

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
import nirmalya.aathithya.webmodule.property.tenant.model.TenantPropertyDetailsModel;

@Controller
@RequestMapping(value = "property/")
public class TenantPropertyDetailsController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(TenantPropertyDetailsController.class);

	@GetMapping(value = { "/property-details" })
	public String propertyDetailsController(Model model, HttpSession session) {
		logger.info("Method :propertyDetailsController starts");
		String userId = "";
		String userRole = "";
		String userMobile="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userMobile= (String) session.getAttribute("USER_MOBILE");
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
		model.addAttribute("userId", userMobile);

		logger.info("Method :propertyDetailsController ends");
		return "new-property/propertyDetails";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("property-details-view")
	public @ResponseBody List<TenantPropertyDetailsModel> viewPropertyDetails(HttpSession session,
			@RequestParam String userid) {
		logger.info("Method :viewPropertyDetails starts" + userid);

		JsonResponse<List<TenantPropertyDetailsModel>> resp = new JsonResponse<List<TenantPropertyDetailsModel>>();
		List<TenantPropertyDetailsModel> returnList = new ArrayList<TenantPropertyDetailsModel>();
		logger.info(env.getPropertyUrl());
		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "property-view-throughAjax?userid=" + userid,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewPropertyDetails ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("property-details-edit")
	public @ResponseBody JsonResponse<TenantPropertyDetailsModel> editpropertyDetails(Model model,
			@RequestParam String propertyno, HttpSession session) {

		logger.info("Method : editpropertyDetails starts");

		JsonResponse<TenantPropertyDetailsModel> jsonResponse = new JsonResponse<TenantPropertyDetailsModel>();
		logger.info(propertyno);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "property-details-edit?propertyno=" + propertyno, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			TenantPropertyDetailsModel TenantPropertyDetailsModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<TenantPropertyDetailsModel>() {
					});

			jsonResponse.setBody(TenantPropertyDetailsModel);
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
		logger.info("Method : editpropertyDetails ends");
		return jsonResponse;
	}

}
