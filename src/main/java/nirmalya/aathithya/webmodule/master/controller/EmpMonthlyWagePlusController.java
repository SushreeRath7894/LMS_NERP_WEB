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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmpMonthlyWagePlusModel;

@Controller
@RequestMapping(value = "master/")
public class EmpMonthlyWagePlusController {
	Logger logger = LoggerFactory.getLogger(EmpMonthlyWagePlusController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	CommonUtil commonUtil;

	@GetMapping("employee-monthly-wageplus")
	public String masterTDS(Model model, HttpSession session) {
		logger.info("Method : masterTDS starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String restUrl = env.getMasterUrl() + "getBandMasterAttendance?organization=" + organization + "&orgDivision="
				+ orgDivision;
		commonUtil.getDropshownList("bandType", restUrl, model);

		restUrl = env.getMasterUrl() + "getYearList-attendance?organization=" + organization + "&orgDivision="
				+ orgDivision;
		commonUtil.getDropshownList("yearList", restUrl, model);

		restUrl = env.getMasterUrl() + "getMonthLists";
		commonUtil.getDropshownList("monthLists", restUrl, model);

		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getComponentForWage?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("componentWage", startDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : masterTDS ends");
		return "master/emp-monthly-wageplus";
	}

	// employee list
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "employee-monthly-wageplus-get-employee-list" })
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
			res = restTemplate.getForObject(env.getMasterUrl() + "employee-autosearch-forAttendance?id=" + searchValue
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
//viewWagePlus
	@SuppressWarnings("unchecked")
	@GetMapping("employee-monthly-wageplus-view-data")
	public @ResponseBody Object viewWagePlus(HttpSession session) {
		logger.info("Method :viewWagePlus starts");
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
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-employee-monthly-wageplus-view-data?orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewWagePlus ends");
		return resp;
	}
//addWagePlusdetails
	@SuppressWarnings("unchecked")
	@PostMapping("/employee-monthly-wageplus-details-save")
	public @ResponseBody JsonResponse<Object> addWagePlusdetails(Model model, HttpSession session,
			@RequestBody EmpMonthlyWagePlusModel data) {

		logger.info("Method : addWagePlusdetails starts");
		JsonResponse<Object> resp = new JsonResponse<>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setCreatedBy(userId);
		data.setOrgName(orgName);
		data.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-wagePlusAdd", data, JsonResponse.class);
		} catch (HttpClientErrorException.Conflict ex) {
			// Handle duplicate entry error
			String errorMessage = ex.getResponseBodyAsString(); // Assuming error message is in the response body

			if (errorMessage.contains("empId")) {
				resp.setMessage("Error: Employee with this ID already exists.");
			} else {
				resp.setMessage("Error: Duplicate entry. The Wage Plus record already exists.");
			}

			return resp;
		} catch (RestClientException e) {
			// Handle other rest client exceptions
			resp.setMessage("Error: Failed to save Wage Plus details. Please try again.");
			e.printStackTrace(); // Log the exception for debugging
			return resp;
		}

		if (resp.getMessage() == null || resp.getMessage().isEmpty()) {
			resp.setMessage("Success");
		}

		logger.info("Method : addWagePlusdetails ends" + resp);
		return resp;
	}

//viewWageYearWise
	@SuppressWarnings("unchecked")
	@GetMapping("employee-monthly-wageplus-view-data-yearWise")
	public @ResponseBody Object viewWageYearWise(@RequestParam String year, @RequestParam String month,
			HttpSession session) {
		logger.info("Method :viewWageYearWise starts");
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

			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-wageplus-view-data-yearWise?year=" + year
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&month=" + month, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewWageYearWise ends");
		return resp;
	}
	// approveWagePlusApply
	@SuppressWarnings("unchecked")
	@GetMapping("employee-monthly-wageplus-approve")
	public @ResponseBody JsonResponse<Object> approveWagePlusApply(HttpSession session,
			@RequestParam String approveId) {
		logger.info("Method : approveWagePlusApply starts");
		JsonResponse<Object> response = new JsonResponse<>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "approveWagePlusApply?approveId=" + approveId+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("response=====" + response);
		logger.info("Method : approveWagePlusApply ends");
		return response;
	}

	// deleteWagePlus
	@SuppressWarnings("unchecked")
	@GetMapping("employee-monthly-wageplus-delete")
	public @ResponseBody JsonResponse<Object> deleteWagePlus(HttpSession session, @RequestParam String dltId) {
		logger.info("Method : deleteWagePlus starts");
		JsonResponse<Object> response = new JsonResponse<>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "deleteWagePlus?dltId=" + dltId+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("response=====" + response);
		logger.info("Method : deleteWagePlus ends");
		return response;
	}

}
