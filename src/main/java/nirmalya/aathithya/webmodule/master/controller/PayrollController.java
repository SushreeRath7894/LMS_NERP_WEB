package nirmalya.aathithya.webmodule.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberToWordsConverter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeModel;
import nirmalya.aathithya.webmodule.master.model.AttendanceDateModel;
import nirmalya.aathithya.webmodule.master.model.PayrollApprovalModel;
import nirmalya.aathithya.webmodule.master.model.PayrollModel;
import nirmalya.aathithya.webmodule.master.model.PayslipModel;
import nirmalya.aathithya.webmodule.master.model.SalaryRevisionModel;
import nirmalya.aathithya.webmodule.recruitment.model.OfferletterModel;

@Controller
@RequestMapping(value = "master")
public class PayrollController {

	Logger logger = LoggerFactory.getLogger(PayrollController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	/***************** Process ********************/

	@GetMapping("/view-process")
	public String viewProcess(Model model, HttpSession session) {
		logger.info("Method : viewProcess starts");

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
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
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

		/*
		 * try { DropDownModel[] dropDownModel =
		 * restTemplate.getForObject(env.getMasterUrl() +
		 * "getBandMasterAttendance?organization=" + organization + "&orgDivision=" +
		 * orgDivision, DropDownModel[].class); List<DropDownModel> bandType =
		 * Arrays.asList(dropDownModel); model.addAttribute("bandType", bandType); }
		 * catch (RestClientException e) { e.printStackTrace(); }
		 */

		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);

			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
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
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);

		logger.info("Method : viewProcess ends");
		return "master/viewProcess";

	}

	// for componets list
	@SuppressWarnings("unchecked")
	@GetMapping("view-process-getComponetList")
	public @ResponseBody JsonResponse<List<DropDownModel>> getComponetList(Model model, HttpSession session) {
		logger.info("Method : getComponetList starts");

		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getComponetList?organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("getComponetList==" + resp);
		logger.info("Method :  getComponetList ends");
		return resp;
	}

	/*
	 * Process view details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-process-view-details")
	public @ResponseBody List<PayrollModel> viewProcess(@RequestParam String fromDate, String toDate, String stafftype,
			String employedBy,String id, HttpSession session, Model model) {
		logger.info("Method : viewProcess starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewProcess?fromDate=" + fromDate + "&toDate="
					+ toDate + "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision
					+ "&employedBy=" + employedBy + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("resp.getBody()=====" + resp.getBody());
		logger.info("Method : viewProcess ends");
		return resp.getBody();
	}

	// for get Approver Status
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/view-process-getApproverStatus" })
	public @ResponseBody JsonResponse<Object> getApproverStatus(HttpSession session, @RequestParam String fromDate,
			@RequestParam String userId) {
		logger.info("Method : getApproverStatus starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String process = "upm004";
		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "rest-getApproverStatus?fromDate=" + fromDate + "&process=" + process
							+ "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision,
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
		logger.info("res=====" + res);
		logger.info("Method : getApproverStatus ends");
		return res;

	}

	/*
	 * Approve Process details
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-process-approve-details" })
	public @ResponseBody JsonResponse<Object> approveProcessDetails(Model model, HttpSession session,
			@RequestBody List<PayrollApprovalModel> data, BindingResult result) {
		logger.info("Method : approveProcessDetails starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

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
		for (PayrollApprovalModel m : data) {
			m.setApprovedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		logger.info("data=======" + data);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "approveProcessDetails", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : approveProcessDetails ends");
		return res;

	}

	/***************** Approve ********************/
	@GetMapping("/view-approve")
	public String viewApprove(Model model, HttpSession session) {
		logger.info("Method : viewProcess starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
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
		/*
		 * try { DropDownModel[] dropDownModel =
		 * restTemplate.getForObject(env.getMasterUrl() +
		 * "getBandMasterAttendance?organization=" + organization + "&orgDivision=" +
		 * orgDivision, DropDownModel[].class); List<DropDownModel> bandType =
		 * Arrays.asList(dropDownModel); model.addAttribute("bandType", bandType); }
		 * catch (RestClientException e) { e.printStackTrace(); }
		 */

		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);

			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
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

		logger.info("Method : viewProcess ends");
		return "master/viewApprove";
	}

	/*
	 * Approve
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-approve-view-details")
	public @ResponseBody List<PayrollModel> viewApprove(@RequestParam String fromDate, String toDate, String employedBy,
			String stafftype, HttpSession session, Model model) {

		logger.info("Method : viewApprove starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewApprove?fromDate=" + fromDate + "&toDate="
					+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewApprove ends");
		return resp.getBody();
	}

	/************************************************
	 * Salary Advice
	 **********************************************/

	@GetMapping("/view-salary-advice")
	public String viewSalaryAdvice(Model model, HttpSession session) {
		logger.info("Method : viewSalaryAdvice starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
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
		/*
		 * try { DropDownModel[] dropDownModel =
		 * restTemplate.getForObject(env.getMasterUrl() +
		 * "getBandMasterAttendance?organization=" + organization + "&orgDivision=" +
		 * orgDivision, DropDownModel[].class); List<DropDownModel> bandType =
		 * Arrays.asList(dropDownModel); model.addAttribute("bandType", bandType); }
		 * catch (RestClientException e) { e.printStackTrace(); }
		 */

		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);

			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
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

		logger.info("Method : viewSalaryAdvice ends");
		return "master/salaryAdvice";
	}

	/*
	 * view Salary Advice
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-advice-view-details")
	public @ResponseBody List<PayrollModel> viewSalaryAdvice(@RequestParam String fromDate, String toDate,
			String employedBy, String stafftype, HttpSession session, Model model) {

		logger.info("Method : viewSalaryAdvice starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewSalaryAdvice?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision + "&stafftype=" + stafftype, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewSalaryAdvice ends");
		return resp.getBody();
	}

	/***************** EPF ********************/

	@GetMapping("/view-epf")
	public String viewEpf(Model model, HttpSession session) {
		logger.info("Method : viewEpf starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
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
		/*
		 * try { DropDownModel[] dropDownModel =
		 * restTemplate.getForObject(env.getMasterUrl() +
		 * "getBandMasterAttendance?organization=" + organization + "&orgDivision=" +
		 * orgDivision, DropDownModel[].class); List<DropDownModel> bandType =
		 * Arrays.asList(dropDownModel); model.addAttribute("bandType", bandType); }
		 * catch (RestClientException e) { e.printStackTrace(); }
		 */

		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);

			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
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
		logger.info("Method : viewEpf ends");
		return "master/viewEpf";
	}

	/*
	 * View EPF
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-epf-view-details")
	public @ResponseBody List<PayrollModel> viewEpf(@RequestParam String fromDate, String toDate, String employedBy,
			String stafftype, HttpSession session, Model model) {

		logger.info("Method : viewEpf starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEpf?fromDate=" + fromDate + "&toDate="
					+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewEpf ends");
		return resp.getBody();
	}

	/***************** ESI ********************/

	@GetMapping("/view-esi")
	public String viewEsi(Model model, HttpSession session) {
		logger.info("Method : viewEsi starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
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
//		try {
//			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl()
//					+ "getBandMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
//			model.addAttribute("bandType", bandType);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);

			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
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
		logger.info("Method : viewEsi ends");
		return "master/viewEsi";
	}

	/*
	 * View ESI
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-esi-view-details")
	public @ResponseBody List<PayrollModel> viewEsi(@RequestParam String fromDate, String toDate, String employedBy,
			String stafftype, HttpSession session, Model model) {

		logger.info("Method : viewEsi starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEsi?fromDate=" + fromDate + "&toDate="
					+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewEsi ends");
		return resp.getBody();
	}

	/***************** TAX ********************/
	@GetMapping("/view-tax")
	public String viewTax(Model model, HttpSession session) {
		logger.info("Method : viewTax starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
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
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
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
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewTax ends");
		return "master/viewTax";
	}

	/*
	 * View TAX
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-tax-view-details")
	public @ResponseBody List<PayrollModel> viewTax(@RequestParam String fromDate, String toDate, String employedBy,
			String stafftype, HttpSession session, Model model) {

		logger.info("Method : viewTax starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewTax?fromDate=" + fromDate + "&toDate="
					+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewTax ends");
		return resp.getBody();
	}

	/**************** Pay Slip *******************/
	@GetMapping("/view-payslip")
	public String viewPayslip(Model model, HttpSession session) {
		logger.info("Method : viewAttendance starts");

		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		String isHr = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		/*
		 * String[] splitData = userRole.split(","); for (String a : splitData) { if
		 * (a.contentEquals("rol001") || a.contentEquals("rol002")) isHr = isHr + "HR";
		 * }
		 */
		/*
		 * String splitData[] = userRole.split("r");
		 * 
		 * String[] removedNull = Arrays.stream(splitData).filter(value -> value != ""
		 * && value.length() > 0) .toArray(size -> new String[size]); for (String part :
		 * removedNull) { String data = "r" + part;
		 * 
		 * if (data.contentEquals("rol001") || data.contentEquals("rol003")) { isHr =
		 * isHr + "HR"; }
		 * 
		 * }
		 */
		// drop down for employee list
		try {
			DropDownModel[] emp = restTemplate
					.getForObject(
							env.getMasterUrl() + "getEmployeeListsSlip?userId=" + userId + "&isHr=" + isHr
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
							DropDownModel[].class);
			List<DropDownModel> employeeLists = Arrays.asList(emp);
			model.addAttribute("employeeLists", employeeLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] emp = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employeeLists = Arrays.asList(emp);
			model.addAttribute("yearList", employeeLists);

		} catch (RestClientException e) {
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
		try {

			DropDownModel[] manager = restTemplate.getForObject(
					env.getEmployeeUrl() + "EmployeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> managerList = Arrays.asList(manager);
			model.addAttribute("EmployeeList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : viewAttendance ends");
		return "master/viewPayslip";
	}

	/*
	 * view-payslip-list
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-payslip-list")
	public @ResponseBody List<PayslipModel> viewPayslipList(@RequestParam String empId, @RequestParam String fromDate,
			@RequestParam String toDate, HttpSession session, Model model) {

		logger.info("Method : viewPayslipList starts");

		JsonResponse<List<PayslipModel>> resp = new JsonResponse<List<PayslipModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "view-employe-paySlip-api?userId=" + empId + "&fromDate=" + fromDate
							+ "&toDate=" + toDate + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewPayslipList ends");
		return resp.getBody();
	}
	/*
	 * view PaySlip personal details
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-payslip-personal-details")
	public @ResponseBody JsonResponse<PayslipModel> viewpaySlipPersonal(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String empId, @RequestParam String organization,
			@RequestParam String orgDivision, HttpSession session, Model model) {

		logger.info("Method : viewpaySlipPersonal starts");

		JsonResponse<PayslipModel> jsonResponse = new JsonResponse<PayslipModel>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewpaySlipPersonal?fromDate=" + fromDate + "&toDate=" + toDate
							+ "&empId=" + empId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/*
		 * if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		 * jsonResponse.setCode(jsonResponse.getMessage());
		 * jsonResponse.setMessage("Unsuccess");
		 * 
		 * } else { jsonResponse.setMessage("Success"); }
		 */

		logger.info("Method : viewpaySlipPersonal ends");
		return jsonResponse;
	}

	/***************** check payslip eligible ********************/
	// check payslip eligible
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/view-payslip-checkPayslipEligibility" })
	public @ResponseBody JsonResponse<Object> checkPayslipEligibility(HttpSession session) {
		logger.info("Method : checkPayslipEligibility starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
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
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "check-payslip-eligible?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setCode(res.getMessage());
			res.setMessage("Success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("res=====" + res);
		logger.info("Method : checkPayslipEligibility ends");
		return res;

	}

	/**************** Pay slip pdf download *******************/

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/payslip-pdf-download" }) 
	public void payslipPdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("fromDate") String encodedParam1, @RequestParam("toDate") String encodedParam2,
			@RequestParam("empId") String encodedParam3, @RequestParam("organization") String encodedParam4,
			@RequestParam("orgDivision") String encodedParam5) {
		logger.info("Method : payslipPdf starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String fromDate = (new String(encodeByte1));

		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String toDate = (new String(encodeByte2));

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String empId = (new String(encodeByte3));

		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam4.getBytes());
		String organization = (new String(encodeByte4));

		byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam5.getBytes());
		String orgDivision = (new String(encodeByte5));
		JsonResponse<OfferletterModel> jsonResponse = new JsonResponse<OfferletterModel>();
		logger.error("empId-----" + empId);
		List<PayslipModel> productList = new ArrayList<PayslipModel>();
		try {
			PayslipModel[] data = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewpaySlipPersonal?fromDate=" + fromDate + "&toDate=" + toDate
							+ "&empId=" + empId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					PayslipModel[].class);
			productList = Arrays.asList(data);
			logger.info("data payslip)/>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + productList);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		/*
		 * List<PayslipModel> payslip = mapper.convertValue(jsonResponse.getBody(), new
		 * TypeReference<List<PayslipModel>>() { }); logger.info("payslip==" + payslip);
		 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("allData", productList);

		String logo = "";
		// String logo = "classpath:static/assets/css/extend/NEWS_7.png";
		String sign = "";
		String stamp = "";

		for (PayslipModel a : productList) {
			if (a.getOrgLogo() != null && a.getOrgLogo() != "" && a.getOrgLogo() != " ") {
				logo = env.getBaseURL() + "document/document/" + a.getOrgLogo();
			}

			if (a.getOrgSign() != null && a.getOrgSign() != "" && a.getOrgSign() != " ") {
				sign = env.getBaseURL() + "document/document/" + a.getOrgSign();
			}
			if (a.getOrgStamp() != null && a.getOrgStamp() != "" && a.getOrgStamp() != " ") {
				stamp = env.getBaseURL() + "document/document/" + a.getOrgStamp();
			}
		}
		data.put("logo", logo);
		data.put("sign", sign);
		data.put("stamp", stamp);
		
		String variable = env.getBaseURL();
		String header = "oriFoodPDFHeader.png";
		String footer = "oriFoodPDFFooter.png";
		String watermark = "oriFoodWatermark-1.png";// "oriFoodWatermark.png";
		// assets/images/login_img/logo.png
		data.put("header", variable + "assets/images/" + header + "");
		data.put("footer", variable + "assets/images/" + footer + "");
		data.put("watermark", variable + "assets/images/" + watermark + "");
		/*
		 * String apilLogo = (String) session.getAttribute("ORGANIZATION_LOGO"); URL
		 * getUrl = null; try { getUrl = new URL(apilLogo); } catch
		 * (MalformedURLException e2) { // TODO Auto-generated catch block
		 * e2.printStackTrace(); } String encodedLogoUrl =
		 * DownloadDocumentUrl.downloadDocumentUrl(getUrl); data.put("apilLogo",
		 * "data:image/png;base64," + encodedLogoUrl);
		 */
		
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=payslipPdfDownload.pdf");
		File file;
		byte[] fileData = null;

		try {
			if ("Orifood And Beverage Private Limited".equals(orgDivision)) {
				file = pdfGeneratorUtil.createPdf("master/payslipPdfDownload-orifood", data);
			} else if("Akanksha Power and Infrastructure Ltd.".equals(orgDivision)){
				file = pdfGeneratorUtil.createPdf("master/payslipPdfDownload-apil", data);
//				file = pdfGeneratorUtil.createPdf("create-pdf/offerLetter", data);
			}else {
				file = pdfGeneratorUtil.createPdf("master/payslipPdfDownload", data);
			}
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : payslipPdf ends");
	}

	/***************** proffesional tax ********************/
	@GetMapping("/view-proffesional-tax")
	public String proffesionalTax(Model model, HttpSession session) {
		logger.info("Method : proffesionalTax starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
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
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
		} catch (Exception e) {
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
		logger.info("Method : proffesionalTax ends");
		return "master/view_profTax";
	}

	/*
	 * View TAX
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-proffesional-tax-view-details")
	public @ResponseBody List<PayrollModel> viewProffesionalTax(@RequestParam String fromDate, String toDate,
			String employedBy, String stafftype, HttpSession session, Model model) {

		logger.info("Method : viewTax starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-proffesional-tax?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision + "&stafftype=" + stafftype, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewTax ends");
		return resp.getBody();
	}

	/*
	 * Approve Process details
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-process-saveAsDraft-details" })
	public @ResponseBody JsonResponse<Object> saveAsDraftProcessDetails(Model model, HttpSession session,
			@RequestBody List<PayrollApprovalModel> data, BindingResult result) {
		logger.info("Method : saveAsDraftProcessDetails starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

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
		for (PayrollApprovalModel m : data) {
			m.setApprovedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		logger.info("data=======" + data);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "saveAsDraftProcessDetails", data,
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

		logger.info("Method : saveAsDraftProcessDetails ends");
		return res;

	}

	/*
	 * Approve
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-approve-view-details-excel")
	public @ResponseBody ModelAndView viewApproveExcel(HttpServletResponse response, @RequestParam String fromDate,
			String toDate, String employedBy, String stafftype, String monthYear, String days, HttpSession session,
			Model model) {

		logger.info("Method : viewApproveExcel starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		Map<String, Object> data = new HashMap<String, Object>();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewApprove?fromDate=" + fromDate + "&toDate="
					+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype, JsonResponse.class);
			System.out.println("resp.getBody()-----" + resp.getBody());
			ObjectMapper mapper = new ObjectMapper();
			List<PayrollModel> attendance = mapper.convertValue(resp.getBody(),
					new TypeReference<List<PayrollModel>>() {
					});
			attendance.get(0).setDays(days);
			logger.info("attendance==" + attendance);

			data.put("attendance", attendance);

			response.setContentType("application/ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + stafftype + " "
					+ "Employee Appove Details-" + monthYear + " " + new Date().getTime() + ".xls");

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewApproveExcel ends==" + data);
		return new ModelAndView(new EmployeePayrollApproveExcelModel(), data);
		/*
		 * logger.info("Method : viewApproveExcel ends"); return resp.getBody();
		 */
	}

	/***************** TAX ********************/
	@GetMapping("/view-lic")
	public String viewLic(Model model, HttpSession session) {
		logger.info("Method : viewLic starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
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
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
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
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewLic ends");
		return "master/viewLic";
	}

	@GetMapping("view-lic-view-details")
	public @ResponseBody Object viewLic(@RequestParam String fromDate, String toDate, String employedBy,
			String stafftype, HttpSession session, Model model) {

		logger.info("Method :viewLic starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewLic?fromDate=" + fromDate + "&toDate="
					+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewLic ends  " + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/professional-tax-Pdf" })
	public void generatePdfForDemo(HttpServletResponse response, HttpSession session, @RequestParam String stafftype,
			@RequestParam String employedBy, @RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : generatePdfForDemo starts");

		String userId = (String) session.getAttribute("USER_ID");
		String orgName = (String) session.getAttribute("ORGANIZATION");
		String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		JsonResponse<Object> resp = new JsonResponse<>();

		try {
			String url = env.getMasterUrl() + "rest-tax-Pdf?orgName=" + orgName + "&orgDivision=" + orgDivision
					+ "&stafftype=" + stafftype + "&employedBy=" + employedBy + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&userId=" + userId;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error fetching data for PDF generation", e);
		}

		logger.info("Fetched response data: ");

		// Assuming resp.getBody() returns a JSON string
		String responseBody = (String) resp.getBody();

		Map<String, Object> data = new HashMap<>();

		// Convert JSON string to Map
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> responseData = mapper.readValue(responseBody, Map.class);
			List<Map<String, Object>> employeeData = (List<Map<String, Object>>) responseData.get("data");
			Map<String, Object> totals = (Map<String, Object>) responseData.get("totals");

			data.put("employeeData", employeeData);
			
			System.out.println("employeeData====================>"+employeeData);
			
			//data.put("totals", totals);

			String sumNetPay = (String) totals.get("sumNetPay");
			String sumProfTax = (String) totals.get("sumProfTax");
			String date = (String) totals.get("date");
			
	        data.put("date" ,date);
			System.out.println("date ========================>"+ date);
			
			
			
            data.put("sumNetPay" ,sumNetPay);
            System.out.println("sumNetPay ========================>"+ sumNetPay);
            
            data.put( "sumProfTax",sumProfTax);
            System.out.println("sumProfTax ========================>"+ sumProfTax);
            
            String sumNetPayInWords = NumberToWordsConverter.convert(sumNetPay);
            System.out.println("sumNetPayInWords ========================>"+ sumNetPayInWords);
            data.put( "sumNetPayInWords",sumNetPayInWords);
            
            
            
            String sumProfTaxinWord =NumberToWordsConverter.convert(sumProfTax);
            String UpperCaseSumProfTaxinWord=sumProfTaxinWord.toUpperCase();
            System.out.println("UpperCaseSumProfTaxinWord ========================>"+ sumProfTaxinWord);
            data.put( "sumProfTaxinWord",UpperCaseSumProfTaxinWord);
           
            data.put("orgDivision", orgDivision);
            System.out.println("orgDivision ========================>"+ orgDivision);
         

		} catch (IOException e) {
			logger.error("Error parsing JSON response", e);
		}

		String filename = "professional-tax.pdf";
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("master/professional-tax_pdf.html", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		logger.info("Method : generatePdfForDemo ends");
	}

}