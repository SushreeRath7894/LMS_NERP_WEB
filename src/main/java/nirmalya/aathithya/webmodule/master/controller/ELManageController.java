package nirmalya.aathithya.webmodule.master.controller;

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
import nirmalya.aathithya.webmodule.master.model.ELManageModel;

@Controller
@RequestMapping(value = "master/")
public class ELManageController {

	Logger logger = LoggerFactory.getLogger(ELManageController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("manage-earned-leave")
	public String leaveApply(Model model, HttpSession session) {
		logger.info("Method : salary starts");

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
		
		/*
		 * try {
		 * 
		 * DropDownModel[] manager = restTemplate.getForObject( env.getEmployeeUrl() +
		 * "EmployeeList?organization=" + organization + "&orgDivision=" + orgDivision,
		 * DropDownModel[].class); List<DropDownModel> managerList =
		 * Arrays.asList(manager); model.addAttribute("EmployeeList", managerList); }
		 * catch (RestClientException e) { e.printStackTrace(); }
		 */
		
		logger.info("Method : salary ends");
		return "master/earned-leave-manager";
	}
	//employee list
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "manage-earned-leave-get-employee-list" })
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
		@PostMapping("manage-earned-leave-add")
		public @ResponseBody JsonResponse<Object> addEarnedLeave(HttpSession session,
				@RequestBody ELManageModel vitamin) {
			logger.info("Method : addEarnedLeave starts"+vitamin);
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
				vitamin.setCreatedBy(userId);
				vitamin.setOrganization(organization);
				vitamin.setOrgDivision(orgDivision);
			try {
				resp = restClient.postForObject(env.getMasterUrl() + "addEarnedLeave", vitamin,
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
			logger.info("Method : addEarnedLeave ends"+resp);
			return resp;
		}
		
		@SuppressWarnings({ "unchecked", "deprecation" })
		@GetMapping("manage-earned-leave-view")
		public @ResponseBody Object viewEarnedLeave(Model model, HttpSession session) {

				logger.info("Method :viewEarnedLeave starts");
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
							env.getMasterUrl() + "viewEarnedLeave?org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method :viewEarnedLeave ends  ");
				return resp;
			}
		@SuppressWarnings({ "unchecked", "deprecation" })
		@GetMapping("manage-earned-leave-edit")
		public @ResponseBody Object editEarnedLeave(@RequestParam String id, HttpSession session) {

				logger.info("Method :editEarnedLeave starts");
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
							env.getMasterUrl() + "editEarnedLeave?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method :editEarnedLeave ends  ");
				return resp;
			}
		@GetMapping("manage-earned-leave-approve")
		public @ResponseBody Object approveEarnedLeave(@RequestParam String id,String empId, HttpSession session) {

			logger.info("Method :approveEarnedLeave starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "approveEarnedLeave?id=" + id + "&empId=" + empId + "&org=" + org + "&orgDiv=" + orgDiv + "&userId=" + userId,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :approveEarnedLeave ends"+resp);
			return resp;
		}
		@GetMapping("manage-earned-leave-delete")
		public @ResponseBody Object deleteEarnedLeave(@RequestParam String id,String empId, HttpSession session) {

			logger.info("Method :deleteEarnedLeave starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "deleteEarnedLeave?id=" + id + "&empId=" + empId + "&org=" + org + "&orgDiv=" + orgDiv,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :deleteEarnedLeave ends"+resp);
			return resp;
		}
}

