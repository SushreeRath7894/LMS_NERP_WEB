package nirmalya.aathithya.webmodule.lms.controller;

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

@Controller
@RequestMapping("academic")
public class ApplicationTemplateController {
	Logger logger = LoggerFactory.getLogger(ApplicationTemplateController.class);

	@Autowired
	EnvironmentVaribles env;
	@Autowired
	RestTemplate restTemplate;

	/*
	 * view all stores in the page
	 */
	@GetMapping("application-template")
	public String studentDashboard(Model model, HttpSession session) {
		logger.info("Mothod:view application-template page started...");
		try {

			DropDownModel[] getUserCountry = restTemplate.getForObject(env.getMasterUrl() + "getUserCountry",
					DropDownModel[].class);
			List<DropDownModel> conList = Arrays.asList(getUserCountry);
			model.addAttribute("countryList", conList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Mothod: view application-template page ends...");
		return "lms/application-template";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("save-student-details")
	public @ResponseBody JsonResponse<Object> saveStudent(HttpSession session,
			@RequestBody Map<String, Object> studentData) {
		logger.info("Method : saveStudentDetails starts");
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-save-student-details", studentData,
					JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveStudentDetails ends");
		return resp;
	}

	// view-Student-Details
	@SuppressWarnings("unchecked")

	@GetMapping("studentdetails-view")
	public @ResponseBody Object viewStudent(HttpSession session) {
		logger.info("Method :viewStudent starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			/*
			 * System.out.println("URL:::" + env.getMasterUrl() +
			 * "rest-studentdetails-view?orgName=" + orgName + "&orgDivision=" +
			 * orgDivision);
			 */

			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-studentdetails-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewStudent ends" + resp);
		return resp;
	}

	// Delete --->>>>>>
	@SuppressWarnings("unchecked")

	@GetMapping("studentdetails-delete")
	public @ResponseBody JsonResponse<Object> deleteStudent(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteStudent function starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-studentdetails-delete?id=" + id,
					JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteStudent function Ends");

		return resp;
	}

	// Application-Data Save
	@SuppressWarnings("unchecked")
	@PostMapping("save-application-details")
	public @ResponseBody JsonResponse<Object> saveApplication(HttpSession session,
			@RequestBody Map<String, Object> applicationData) {
		logger.info("Method : saveApplication starts");
		logger.info("Received Application data: {}", applicationData);
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-save-application-details", applicationData,
					JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveApplication ends");
		return resp;
	}

	// view-Application-Details
	@SuppressWarnings("unchecked")

	@GetMapping("application-view")
	public @ResponseBody Object viewApplication(HttpSession session) {
		logger.info("Method :viewApplication starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-application-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewApplication ends" + resp);
		return resp;
	}

	// Edit Application ::
	@SuppressWarnings("unchecked")

	@GetMapping("application-edit")
	public @ResponseBody JsonResponse<Object> editApplication(@RequestParam String id, HttpSession session) {
		logger.info("Method : editApplication function starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-application-edit?id=" + id, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : editApplication function Ends");

		return resp;
	}

	// Delete Application Data --->>>>>>
	@SuppressWarnings("unchecked")

	@GetMapping("application-delete")
	public @ResponseBody JsonResponse<Object> deleteApplication(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteApplication function starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-application-delete?id=" + id,
					JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteApplication function Ends");

		return resp;
	}

	// GetState List

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "application-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateList starts" + " " + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getApplicationStateList?id=" + id,
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
		logger.info("state" + res);
		logger.info("Method : getstateList ends");
		return res;
	}

	// districtList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "application-districtList" })
	public @ResponseBody JsonResponse<Object> districtList(@RequestParam String id) {
		logger.info("Method : districtList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "districtList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : districtList ends");
		return res;
	}

	// application-city-list
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "application-city-list" })
	public @ResponseBody JsonResponse<Object> CityList(@RequestParam String id) {
		logger.info("Method : CityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "studentCity-list?id=" + id, JsonResponse.class);
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

}
