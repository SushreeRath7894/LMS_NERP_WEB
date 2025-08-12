package nirmalya.aathithya.webmodule.employee.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.EmployeeCCRModel;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeModel;
import nirmalya.aathithya.webmodule.qa.model.VitAModel;

@Controller
@RequestMapping(value = "employee")
public class EmployeeCCRController {
	Logger logger = LoggerFactory.getLogger(EmployeeCCRController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("/view-ccr-details")
	public String employeeCCR(Model model, HttpSession session) {

		logger.info("Method : employeeCCR starts");
		
		logger.info("Method : employeeCCR ends");
		return "employee/addCCRDetails";
	}
	/************************* View Employee *********************************/
	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("view-ccr-details-view")
	public @ResponseBody Object viewEmpolyee(Model model, HttpSession session) {

			logger.info("Method :viewEmpolyee starts");
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
						env.getEmployeeUrl() + "viewEmpolyee?org=" + org + "&orgDiv=" + orgDiv,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewEmpolyee ends  ");
			return resp;
		}
	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("view-ccr-details-edit")
		public @ResponseBody Object editEmpolyee(@RequestParam String id, HttpSession session) {

			logger.info("Method :editEmpolyee starts");
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
						env.getEmployeeUrl() + "editEmpolyee?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :editEmpolyee ends  "+resp);
			return resp;
		}
	@SuppressWarnings("unchecked")
	@GetMapping("view-ccr-details-employee-list")
	public @ResponseBody Object getEmployeeList(@RequestParam String id,HttpSession session) {
		logger.info("Method :getEmployeeList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "getEmployeeListbyId?id=" +id+ "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {

		}
		logger.info("Method :getEmployeeList ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("view-ccr-details-add")
	public @ResponseBody JsonResponse<Object> addEmployeeReview(HttpSession session,
			@RequestBody EmployeeCCRModel vitamin) {
		logger.info("Method : addEmployeeReview starts"+vitamin);
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
			resp = restClient.postForObject(env.getEmployeeUrl() + "addEmployeeReview", vitamin,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * if (resp.getMessage() != "" && resp.getMessage() != null) {
		 * resp.setCode(resp.getMessage()); resp.setMessage("Success"); } else {
		 * resp.setMessage("Unsuccess"); }
		 */
		
		resp.setMessage(resp.getMessage());
		resp.setCode(resp.getCode());
		logger.info("Method : addEmployeeReview ends"+resp);
		return resp;
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("view-ccr-details-edit-review")
		public @ResponseBody Object editEmpolyeeReview(@RequestParam String id,String empId, HttpSession session) {
			logger.info("Method :editEmpolyeeReview starts");
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
						env.getEmployeeUrl() + "editEmpolyeeReview?id=" + id + "&empId=" + empId + "&org=" + org + "&orgDiv=" + orgDiv,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :editEmpolyeeReview ends  "+resp);
			return resp;
		}
	@GetMapping("view-ccr-details-delete")
	public @ResponseBody Object deleteEmpolyeeReview(@RequestParam String id,String empId, HttpSession session) {

		logger.info("Method :deleteEmpolyeeReview starts");
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
			resp = restClient.getForObject(env.getEmployeeUrl() + "deleteEmpolyeeReview?id=" + id + "&empId=" + empId + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :deleteEmpolyeeReview ends"+resp);
		return resp;
	}
	@GetMapping("view-ccr-details-reject")
	public @ResponseBody Object rejectEmpolyeeReview(@RequestParam String id,String empId, HttpSession session) {

		logger.info("Method :rejectEmpolyeeReview starts");
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
			resp = restClient.getForObject(env.getEmployeeUrl() + "rejectEmpolyeeReview?id=" + id + "&empId=" + empId + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :rejectEmpolyeeReview ends"+resp);
		return resp;
	}
	@GetMapping("view-ccr-details-approve")
	public @ResponseBody Object approveEmpolyeeReview(@RequestParam String id,String empId, HttpSession session) {

		logger.info("Method :approveEmpolyeeReview starts");
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
			resp = restClient.getForObject(env.getEmployeeUrl() + "approveEmpolyeeReview?id=" + id + "&empId=" + empId + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :approveEmpolyeeReview ends"+resp);
		return resp;
	}
}
