package nirmalya.aathithya.webmodule.grc.controller;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.grc.model.GRCReportModel;
import nirmalya.aathithya.webmodule.maintenance.model.AllotedMaintenanceModel;

@Controller
@RequestMapping(value = "grc/")
public class SBOHIWebController {

	Logger logger = LoggerFactory.getLogger(SBOHIWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/sbo-hi-report")
	public String report(Model model, HttpSession session) {
		logger.info("Method : report starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(
					env.getMasterUrl() + "getYearLists?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getMasterUrl() + "getStaffType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> staffType = Arrays.asList(dropDownModel);
			model.addAttribute("staffType", staffType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);
			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			DropDownModel[] manager = restTemplate.getForObject(
					env.getEmployeeUrl() + "EmployeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> managerList = Arrays.asList(manager);
			model.addAttribute("EmployeeList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : report ends");
		return "grc/sbo-hi-report";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("sbo-hi-report-monthly-emplist")
	public @ResponseBody Object getMonthlyEmpList(HttpSession session) {
		logger.info("Method :getMonthlyEmpList starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-sbo-hi-report-monthly-emplist?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getMonthlyEmpList ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "sbo-hi-report-monthly-add" })
	public @ResponseBody JsonResponse<Object> addMonthlyEmpDetails(@RequestBody List<GRCReportModel> av, HttpSession session) {
		logger.info("Method : addMonthlyEmpDetails function starts"+av);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (GRCReportModel m : av) {
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setCreatedBy(userId);
		}
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "rest-sbo-hi-report-monthly-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addMonthlyEmpDetails function Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("sbo-hi-report-monthly-view")
	public @ResponseBody Object getMonthlyView(HttpSession session) {
		logger.info("Method :getMonthlyView starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-sbo-hi-report-monthly-view?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getMonthlyView ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("sbo-hi-report-monthly-edit")
	public @ResponseBody Object getMonthlyEdit(@RequestParam String id,HttpSession session) {
		logger.info("Method :getMonthlyView starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-sbo-hi-report-monthly-edit?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getMonthlyView ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("sbo-hi-report-yearly-view")
	public @ResponseBody Object getYearlyView(@RequestParam String financialYr,HttpSession session) {
		logger.info("Method :getYearlyView starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "sbo-hi-report-yearly-view?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&financialYr=" + financialYr, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getYearlyView ends");
		return resp;
	}
	
	@GetMapping("/master-checklist")
	public String MasterChecklist(Model model, HttpSession session) {
		logger.info("Method : report starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(
					env.getMasterUrl() + "getYearLists?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getMasterUrl() + "getStaffType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> staffType = Arrays.asList(dropDownModel);
			model.addAttribute("staffType", staffType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);
			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			DropDownModel[] manager = restTemplate.getForObject(
					env.getEmployeeUrl() + "EmployeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> managerList = Arrays.asList(manager);
			model.addAttribute("EmployeeList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : report ends");
		return "grc/master-checklist";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("master-checklist-monthly-emplist")
	public @ResponseBody Object getMonthlyMaster(@RequestParam String month,String year,HttpSession session) {
		logger.info("Method :getMonthlyMaster starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-master-checklist-monthly-emplist?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&month=" + month+ "&year=" + year, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getMonthlyMaster ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "master-checklist-monthly-add" })
	public @ResponseBody JsonResponse<Object> addMasterChecklistDetails(@RequestBody List<GRCReportModel> av, HttpSession session) {
		logger.info("Method : addMasterChecklistDetails function starts"+av);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (GRCReportModel m : av) {
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setCreatedBy(userId);
		}
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "rest-master-checklist-monthly-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addMasterChecklistDetails function Ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("master-checklist-view")
	public @ResponseBody Object getMasterCheckilistView(HttpSession session) {
		logger.info("Method :getMasterCheckilistView starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-master-checklist-view?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getMasterCheckilistView ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("master-checklist-edit")
	public @ResponseBody Object getMasterChecklistEdit(@RequestParam String id,HttpSession session) {
		logger.info("Method :getMasterChecklistEdit starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-master-checklist-edit?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getMasterChecklistEdit ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("master-checklist-report")
	public @ResponseBody Object getReportView(@RequestParam String financialYr,HttpSession session) {
		logger.info("Method :getReportView starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-master-checklist-report?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&financialYr=" + financialYr, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getReportView ends");
		return resp;
	}
}
