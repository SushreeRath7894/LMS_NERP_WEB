package nirmalya.aathithya.webmodule.master.controller;

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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ManagePassModel;

@Controller
@RequestMapping(value = { "master/" })
public class EmployeePassController {

	Logger logger = LoggerFactory.getLogger(EmployeePassController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "employee-pass" })
	public String employeePass(Model model, HttpSession session) {
		logger.info("Method : employeePass starts");

		logger.info("Method : employeePass ends");
		return "master/manage-employee-pass";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("employee-pass-autoserach-employee")
	public @ResponseBody Object employeeAutoSearch(Model model, @RequestBody String searchValue, BindingResult result,
			HttpSession session) {

		logger.info("Method :employeeAutoSearch starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			resp = restClient.getForObject(env.getMasterUrl() + "get-all-employee-autosearch?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getCode().contentEquals("success")) {
			resp.setMessage("success");
		} else {
			resp.setMessage(" ");
		}

		logger.info("Method :employeeAutoSearch ends");

		return resp;
	}

	// Add Gate Pass Details

	@SuppressWarnings("unchecked")
	@PostMapping("/emploee-pass-save-pass")
	public @ResponseBody JsonResponse<Object> savePass(@RequestBody ManagePassModel data, HttpSession session) {
		logger.info("Method : savePass starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";
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

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "rest-add-pass", data, JsonResponse.class);
			
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : savePass starts");
		return resp;
	}

	/*
	 * Get All Entries
	 */
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/employee-pass-all-entries")
	public @ResponseBody JsonResponse<Object> getAllEntries(HttpSession session, @RequestParam String module,String type) {
		logger.info("Method : getNoticeEdit starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-get-entries?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&module=" + module+ "&type=" + type, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getAllEntries ends");
		return res;

	}

	/*
	 * Get Entry Details by ID
	 */
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/employee-pass-get-pass")
	public @ResponseBody JsonResponse<Object> getEntry(HttpSession session, @RequestParam String id) {
		logger.info("Method : getEntry starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-get-entry-byId?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getEntry ends");
		return res;

	}

	/*
	 * Update pass status
	 */
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/employee-pass-approve")
	public @ResponseBody JsonResponse<Object> updatePass(HttpSession session, @RequestParam String id) {
		logger.info("Method : getEntry starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-update-pass?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : updatePass ends");
		return res;

	}

	/*
	 * Allow gate pass
	 */
	@SuppressWarnings({ "unchecked" })
	@GetMapping("employee-pass-allow-Gate")
	public @ResponseBody JsonResponse<Object> allowPass(HttpSession session, @RequestParam String id) {
		logger.info("Method : allowPass starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-allow-pass?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : allowPass ends");
		return res;

	}

	/*
	 * Delete pass
	 */
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/employee-pass-delete")
	public @ResponseBody JsonResponse<Object> deletePass(HttpSession session, @RequestParam String id) {
		logger.info("Method : deletePass starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-delete-pass?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : deletePass ends");
		return res;

	}
	
	/*
	 * Allow gate pass
	 */
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/employee-pass-log")
	public @ResponseBody JsonResponse<Object> entrypassLog(HttpSession session, @RequestParam String id) {
		logger.info("Method : allowPass starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-allow-pass?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : allowPass ends");
		return res;

	}
}
