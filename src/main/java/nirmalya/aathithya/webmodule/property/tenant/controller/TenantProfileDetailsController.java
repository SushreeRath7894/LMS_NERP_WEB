package nirmalya.aathithya.webmodule.property.tenant.controller;

import java.util.Arrays;


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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.property.tenant.model.TenantProfileDetailsModel;

@Controller
@RequestMapping(value = "property")
public class TenantProfileDetailsController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(TenantProfileDetailsController.class);

	@GetMapping(value = { "profile-details" })
	public String profileDetailsController(Model model, HttpSession session) {
		logger.info("Method :profileDetailsController starts");

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

		logger.info("Method :profileDetailsController ends"+userId);
		return "new-property/profileDetails";
	}


	@SuppressWarnings("unchecked")

	@GetMapping("profile-details-edit")
	public @ResponseBody JsonResponse<TenantProfileDetailsModel> editprofileDetails(Model model,
			@RequestParam String userid, HttpSession session) {

		logger.info("Method : editprofileDetails starts");

		JsonResponse<TenantProfileDetailsModel> jsonResponse = new JsonResponse<TenantProfileDetailsModel>();
		logger.info(userid);
		try {
			jsonResponse = restClient.getForObject(
					env.getPropertyUrl() + "profile-details-edit?userid="+ userid, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			TenantProfileDetailsModel TenantProfileDetailsModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<TenantProfileDetailsModel>() {
					});
	

			jsonResponse.setBody(TenantProfileDetailsModel);
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
		logger.info("Method : editprofileDetails ends");
		return jsonResponse;
	}

}
