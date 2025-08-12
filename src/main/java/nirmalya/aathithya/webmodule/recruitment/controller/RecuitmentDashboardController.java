package nirmalya.aathithya.webmodule.recruitment.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.recruitment.model.ActionEmployeeDetailsModel;

@Controller
@RequestMapping(value = "recruitment")
public class RecuitmentDashboardController {
	Logger logger = LoggerFactory.getLogger(RecuitmentDashboardController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	//interviwers-dashboard-recruitment-head-count
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-head-count")
	public @ResponseBody Object recruitmentDashHeadCount(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashHeadCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashHeadCount ends" + resp);
		return resp;
	}
	
	//interviwers-dashboard-recruitment-cityList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "interviwers-dashboard-recruitment-cityList" })
	public @ResponseBody JsonResponse<Object> getAllRctmntCityList(@RequestParam String id) {
		logger.info("Method : getAllRctmntCityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getRecruitment() + "getAllRctmntCityList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getAllRctmntCityList ends");
		return res;
	}


	
	//interviwers-dashboard-recruitment-avg-cost-hiring-by-seniority-level
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-avg-cost-hiring-by-seniority-level")
	public @ResponseBody Object recruitmentDashAvgCostHiringBySeniorityLevel(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashAvgCostHiringBySeniorityLevel starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashAvgCostHiringBySeniorityLevel?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashAvgCostHiringBySeniorityLevel ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-funnel
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-funnel")
	public @ResponseBody Object recruitmentDashFunnelCount(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashFunnelCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashFunnelCount?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashFunnelCount ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-turnover-rate-by-age
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-turnover-rate-by-age")
	public @ResponseBody Object recruitmentDashTurnOverRateByAge(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashTurnOverRateByAge starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashTurnOverRateByAge?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashTurnOverRateByAge ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-hired-by-source
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-hired-by-source")
	public @ResponseBody Object recruitmentDashHiredBySource(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashHiredBySource starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashHiredBySource?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashHiredBySource ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-hiring-vacancy-trend
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-hiring-vacancy-trend")
	public @ResponseBody Object recruitmentDashHiringVacancyTrend(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashHiringVacancyTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashHiringVacancyTrend?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashHiringVacancyTrend ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-hired-by-jobs-role
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-hired-by-jobs-role")
	public @ResponseBody Object recruitmentDashHiringByJobsRole(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashHiringByJobsRole starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashHiringByJobsRole?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashHiringByJobsRole ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-avg-dept-by-fullfill-in-day
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-avg-dept-by-fullfill-in-day")
	public @ResponseBody Object recruitmentDashAvgDeptByfullfillInDay(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashAvgDeptByfullfillInDay starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashAvgDeptByfullfillInDay?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashAvgDeptByfullfillInDay ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-hired-by-department
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-hired-by-department")
	public @ResponseBody Object recruitmentDashHiredByDepartment(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashHiredByDepartment starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashHiredByDepartment?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashHiredByDepartment ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-salary-distribution-by-exp-hired-emp
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-salary-distribution-by-exp-hired-emp")
	public @ResponseBody Object recruitmentDashSalaryDistByExpHiredEmp(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashSalaryDistByExpHiredEmp starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashSalaryDistByExpHiredEmp?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashSalaryDistByExpHiredEmp ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-hired-by-age-brackets
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-hired-by-age-brackets")
	public @ResponseBody Object recruitmentDashHiredByAgeBrackets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashHiredByAgeBrackets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashHiredByAgeBrackets?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashHiredByAgeBrackets ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-offer-decline-reason
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-offer-decline-reason")
	public @ResponseBody Object recruitmentDashOfferDeclineReason(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashOfferDeclineReason starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashOfferDeclineReason?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashOfferDeclineReason ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-progress-distribution
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-progress-distribution")
	public @ResponseBody Object recruitmentDashProgressDistribution(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashProgressDistribution starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashProgressDistribution?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashProgressDistribution ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-acceptance-rejection-distribution
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-acceptance-rejection-distribution")
	public @ResponseBody Object recruitmentDashAcceptRejectionDistribution(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashAcceptRejectionDistribution starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashAcceptRejectionDistribution?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashAcceptRejectionDistribution ends" + resp);
		return resp;
	}

	
	//interviwers-dashboard-recruitment-conversion-rate-by-hr
	@SuppressWarnings("unchecked")
	@GetMapping("interviwers-dashboard-recruitment-conversion-rate-by-hr")
	public @ResponseBody Object recruitmentDashConversionRateByHr(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv, @RequestParam String department, @RequestParam String jobRole,
			@RequestParam String recuiter, @RequestParam String type) {
		logger.info("Method :recruitmentDashConversionRateByHr starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();		
		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "recruitmentDashConversionRateByHr?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&department=" + department +
									"&jobRole=" + jobRole + "&recuiter=" + recuiter + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :recruitmentDashConversionRateByHr ends" + resp);
		return resp;
	}

	
	
}
