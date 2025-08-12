package nirmalya.aathithya.webmodule.his.controller;

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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HISTreatmentModel;

@Controller
@RequestMapping("/his")
public class HISEmergencyTreatmentController {

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HISEmergencyTreatmentController.class);
	
	public static String emerId;

	@GetMapping(value = { "emergency-treatment" })
	public String viewEmergencyTreatment(Model model, @RequestParam String id) {
		logger.info("Method : viewEmergencyTreatment starts");

		emerId = id;
		model.addAttribute("emerId1", id);

		DropDownModel[] type = restClient.getForObject(env.getHisUrl() + "/typeList", DropDownModel[].class);
		List<DropDownModel> typeList = Arrays.asList(type);
		model.addAttribute("typeList", typeList);

		logger.info("Method : viewEmergencyTreatment ends");
		return "his/his-emergency-treatment";
	}
	

	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "his-emergency-treatment-add" })
	public @ResponseBody JsonResponse<Object> addEmergencyTreatment(HttpSession session,
			@RequestBody HISTreatmentModel data) {
		logger.info("Method : addEmergencyTreatment starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		System.out.println("data=======" + data);
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		System.out.println("detailsss ===" + data);
		try {
			res = restClient.postForObject(env.getHisUrl() + "rest-emergency-treatment-add", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addEmergencyTreatment ends" + res);
		return res;

	}
}
