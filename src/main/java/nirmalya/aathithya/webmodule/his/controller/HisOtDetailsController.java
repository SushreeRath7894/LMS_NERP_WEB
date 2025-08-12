package nirmalya.aathithya.webmodule.his.controller;

import java.util.ArrayList;
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
import nirmalya.aathithya.webmodule.user.model.UserRoleAssignModel;

@Controller
@RequestMapping(value = "his")
public class HisOtDetailsController {
	Logger logger = LoggerFactory.getLogger(HisReceptionController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/ot-details")
	public String ot(Model model, HttpSession session) {

		logger.info("Method : ot starts");
		// Fetch gender data
					DropDownModel[] gender = restTemplate.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
					List<DropDownModel> genderList = Arrays.asList(gender);
					model.addAttribute("genderList", genderList);
					
					DropDownModel[] procedure = restTemplate.getForObject(env.getHisUrl() + "/procedureList", DropDownModel[].class);
					List<DropDownModel> procedureList = Arrays.asList(procedure);
					model.addAttribute("procedureList", procedureList);

		logger.info("Method : ot ends");

		return "his/his-ot-details.html";

	}
	
	//view
	@SuppressWarnings("unchecked")
	@GetMapping("ot-details-view")
	public @ResponseBody Object viewPatient(HttpSession session) {
		logger.info("Method :viewPatient starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewOtList?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewPatient ends"+resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("ot-details-edit")
	public @ResponseBody Object editOt(@RequestParam String Id, HttpSession session) {
		logger.info("Method :editOt starts" + Id);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-ot-editOt?Id=" + Id + "&organization=" + orgName
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
		logger.info("Method :editOt ends");
		return resp;
	}
	
	//view Surgen
		@SuppressWarnings("unchecked")
		@GetMapping("ot-details-surgen-view")
		public @ResponseBody Object viewSurgen(@RequestParam String Id,HttpSession session) {
			logger.info("Method :viewSurgen starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				String userId = (String) session.getAttribute("USER_ID");
				String orgName = (String) session.getAttribute("ORGANIZATION");
				String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewSurgen?Id=" + Id + "&orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&userId=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :viewSurgen ends"+resp);
			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("ot-details-users-employee")
		public @ResponseBody Object viewDoctorMaster(HttpSession session) {

			logger.info("Method :viewDoctorMaster starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restTemplate.getForObject(env.getHisUrl() + "rest-ot-details-users-employee?orgName=" + orgName + "&orgDivision="
						+ orgDivision,JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :viewDoctorMaster ends" + resp);
			return resp;
		}
		
		
		//equipment view
		
		@SuppressWarnings("unchecked")
		@GetMapping("ot-details-equipment-view")
		public @ResponseBody Object viewEquipment(HttpSession session) {
			logger.info("Method :viewEquipment starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				String userId = (String) session.getAttribute("USER_ID");
				String orgName = (String) session.getAttribute("ORGANIZATION");
				String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
logger.info("hyyyy"+restTemplate.getForObject(env.getHisUrl() + "rest-viewEquipments?orgName=" + orgName + "&orgDivision="
		+ orgDivision + "&userId=" + userId, JsonResponse.class));


				resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewEquipments?orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&userId=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :viewEquipment ends"+resp);
			return resp;
		}

}
