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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.OFFManageModel;

@Controller
@RequestMapping(value = "master/")
public class ManageOffDaysController {
	Logger logger = LoggerFactory.getLogger(ManageOffDaysController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("manage-off-days")
	public String manageOffDays(Model model, HttpSession session) {
		logger.info("Method : manageOffDays starts");

		String userId = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID"); 
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : manageOffDays ends");
		return "master/manage-off-days";
	}
	//employee list
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "manage-off-days-employee-list" })
		public @ResponseBody JsonResponse<DropDownModel> EmployeeAutoSearch(Model model, @RequestBody String searchValue,
				BindingResult result, HttpSession session) {
			logger.info("Method : EmployeeAutoSearch starts");
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
			String org = "";
			String orgDiv = "";
			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			try {
				res = restClient.getForObject(env.getMasterUrl() + "employee-autosearch-forAttendance?id=" + searchValue
						+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			logger.info("Method : EmployeeAutoSearch ends");
			return res;
		}
		@SuppressWarnings("unchecked")
		@PostMapping("manage-off-days-add")
		public @ResponseBody JsonResponse<Object> addOffDays(HttpSession session,
				@RequestBody OFFManageModel offDay) {
			logger.info("Method : addOffDays starts"+offDay);
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String dateFormat = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
				offDay.setCreatedBy(userId);
				offDay.setOrganization(organization);
				offDay.setOrgDivision(orgDivision);
			try {
				resp = restClient.postForObject(env.getMasterUrl() + "addOffDays", offDay,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Success");
			} else {
				resp.setMessage("Unsuccess");
			}
			logger.info("Method : addOffDays ends"+resp);
			return resp;
		}
		
	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("manage-off-days-view")
	public @ResponseBody Object viewOffDays(Model model, HttpSession session) {

			logger.info("Method :viewOffDays starts");
			@SuppressWarnings("rawtypes")
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restClient.getForObject(
					env.getMasterUrl() + "viewOffDays?org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewOffDays ends  ");
		return resp;
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("manage-off-days-edit")
	public @ResponseBody Object editOffDays(@RequestParam String id, HttpSession session) {
		
		logger.info("Method :editOffDays starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getMasterUrl() + "editOffDays?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editOffDays ends  ");
		return resp;
	}
	@GetMapping("manage-off-days-approve")
	public @ResponseBody Object approveOffDays(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveOffDays starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "approveOffDays?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv + "&userId=" + userId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :approveOffDays ends"+resp);
		return resp;
	}
	@GetMapping("manage-off-days-delete")
	public @ResponseBody Object deleteOffDays(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteOffDays starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteOffDays?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :deleteOffDays ends"+resp);
		return resp;
	}
}
