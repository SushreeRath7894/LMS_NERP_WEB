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

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.TdsWebModel;

@Controller
@RequestMapping(value = "master/")
public class EmployeeTDSWebController {
	Logger logger = LoggerFactory.getLogger(EmployeeTDSWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	CommonUtil commonUtil;

	@GetMapping("employee-tds")
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
		logger.info("Method : masterTDS ends");
		return "master/employeeTDS";
	}

//employee list
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "employee-tds-get-employee-list" })
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

//employeeTdsView
	@SuppressWarnings("unchecked")
	@GetMapping("employee-tds-view-data")
	public @ResponseBody Object employeeTdsView(HttpSession session, @RequestParam String year, String month) {
		logger.info("Method : employeeTdsView");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "employee-tds-view-data?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&year=" + year + "&month=" + month, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : employeeTdsView ends");
		return resp;
	}

//employeeTdsSave
	@SuppressWarnings("unchecked")
	@PostMapping("/employee-tds-details-save")
	public @ResponseBody JsonResponse<Object> employeeTdsSave(Model model, HttpSession session,
			@RequestBody TdsWebModel data) {
		logger.info("Method : employeeTdsSave starts");
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "employee-tds-details-save", data,
					JsonResponse.class);
		} catch (RestClientException e) {
			// Handle other rest client exceptions
			e.printStackTrace(); // Log the exception for debugging
			return resp;
		}
		logger.info("Method : employeeTdsSave ends {}", resp);
		return resp;
	}

//employeeTdsEdit
	@SuppressWarnings("unchecked")
	@GetMapping("employee-tds-edit")
	public @ResponseBody JsonResponse<Object> employeeTdsEdit(@RequestParam String Id, HttpSession session) {
		logger.info("Method : employeeTdsEdit starts");
		JsonResponse<Object> response = new JsonResponse<>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "employee-tds-edit?id=" + Id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : employeeTdsEdit ends {} " + response);
		return response;
	}

//employeeTdsApprove
	@SuppressWarnings("unchecked")
	@GetMapping("employee-tds-approve")
	public @ResponseBody JsonResponse<Object> employeeTdsApprove(HttpSession session, @RequestParam String approveId) {
		logger.info("Method : employeeTdsApprove starts");
		JsonResponse<Object> response = new JsonResponse<>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "employee-tds-approve?approveId=" + approveId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : employeeTdsApprove ends {} " + response);
		return response;
	}

//employeeTdsDelete
	@SuppressWarnings("unchecked")
	@GetMapping("employee-tds-delete")
	public @ResponseBody JsonResponse<Object> employeeTdsDelete(HttpSession session, @RequestParam String dltId) {
		logger.info("Method : employeeTdsDelete starts");
		JsonResponse<Object> response = new JsonResponse<>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "employee-tds-delete?dltId=" + dltId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : employeeTdsDelete ends {}" + response);
		return response;
	}
}
