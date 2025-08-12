package nirmalya.aathithya.webmodule.employee.controller;

import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;
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
import nirmalya.aathithya.webmodule.employee.model.HrmsDashboardModel;

@Controller
@RequestMapping(value = "employee")
public class HrmsDashboardRecruitmentController {
	Logger logger = LoggerFactory.getLogger(HrmsDashboardRecruitmentController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	/////////////////////HRMS REQUITMENT DASHBOARD START////////////////////////
	//hrms-dashboard-recruitment-head-count
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-head-count")
	public @ResponseBody Object hrmsRecruitmentHeadCount(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentHeadCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentHeadCount ends" + resp);

		return resp;
	}
	
	
	//hrms-dashboard-recruitment-conversion-rate-by-hr
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-conversion-rate-by-hr")
	public @ResponseBody Object hrmsRecruitmentConversionRateByHr(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentConversionRateByHr starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentConversionRateByHr?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentConversionRateByHr ends" + resp);

		return resp;
	}
	
	
	//hrms-dashboard-recruitment-avg-cost-hiring-by-seniority-level
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-avg-cost-hiring-by-seniority-level")
	public @ResponseBody Object hrmsRecruitmentAvgCostHiringBySeniorityLevel(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentAvgCostHiringBySeniorityLevel starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentAvgCostHiringBySeniorityLevel?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentAvgCostHiringBySeniorityLevel ends" + resp);

		return resp;
	}
	
	
	
	
	//hrms-dashboard-recruitment-funnel
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-funnel")
	public @ResponseBody Object hrmsRecruitmentFunnel(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentFunnel starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentFunnel?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentFunnel ends" + resp);

		return resp;
	}
	
	//hrms-dashboard-recruitment-avg-dept-by-fullfill-in-day
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-avg-dept-by-fullfill-in-day")
	public @ResponseBody Object hrmsRecruitmentAvgDeptByFullfillInDays(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {

		logger.info("Method :hrmsRecruitmentAvgDeptByFullfillInDays starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();				
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentAvgDeptByFullfillInDays?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :hrmsRecruitmentAvgDeptByFullfillInDays ends" + resp);
		return resp;
	}
	
	
	//hrms-dashboard-recruitment-hired-by-source
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-hired-by-source")
	public @ResponseBody Object hrmsRecruitmentHiredBySource(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentHiredBySource starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentHiredBySource?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentHiredBySource ends" + resp);

		return resp;
	}
	//hrms-dashboard-recruitment-progress-distribution
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-progress-distribution")
	public @ResponseBody Object hrmsRecruitmentProgressDistribution(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentProgressDistribution starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentProgressDistribution?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentProgressDistribution ends" + resp);

		return resp;
	}
	
	//hrms-dashboard-recruitment-acceptance-rejection-distribution
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-acceptance-rejection-distribution")
	public @ResponseBody Object hrmsRecruitmentAcceptanceRejectionDistribution(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentAcceptanceRejectionDistribution starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentAcceptanceRejectionDistribution?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentAcceptanceRejectionDistribution ends" + resp);

		return resp;
	}
	
	
	//hrms-dashboard-recruitment-offer-decline-reason
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-offer-decline-reason")
	public @ResponseBody Object hrmsRecruitmentOfferDeclineReason(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentOfferDeclineReason starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentOfferDeclineReason?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentOfferDeclineReason ends" + resp);

		return resp;
	}
	
	
	//hrms-dashboard-recruitment-hiring-vacancy-trend
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-hiring-vacancy-trend")
	public @ResponseBody Object hrmsRecruitmentHiringVacancyTrend(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentHiringVacancyTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentHiringVacancyTrend?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentHiringVacancyTrend ends" + resp);

		return resp;
	}
	
	
	//hrms-dashboard-recruitment-hired-by-jobs-role
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-hired-by-jobs-role")
	public @ResponseBody Object hrmsRecruitmentHiredByJobsRole(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentHiredByJobsRole starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentHiredByJobsRole?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentHiredByJobsRole ends" + resp);

		return resp;
	}
	
	
	//hrms-dashboard-recruitment-hired-by-project
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-hired-by-department")
	public @ResponseBody Object hrmsRecruitmentHiredByDepartment(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentHiredByDepartment starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentHiredByDepartment?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentHiredByDepartment ends" + resp);

		return resp;
	}
	
	
	//hrms-dashboard-recruitment-salary-distribution-by-exp-hired-emp
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-salary-distribution-by-exp-hired-emp")
	public @ResponseBody Object hrmsRecruitmentSalaryDistByExpHiresEmp(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentSalaryDistByExpHiresEmp starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentSalaryDistByExpHiresEmp?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentSalaryDistByExpHiresEmp ends" + resp);

		return resp;
	}
	
	
	//hrms-dashboard-recruitment-hired-by-age-brackets
	
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-recruitment-hired-by-age-brackets")
	public @ResponseBody Object hrmsRecruitmentHiredByAgeBrackets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsRecruitmentHiredByAgeBrackets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsRecruitmentHiredByAgeBrackets?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method :hrmsRecruitmentHiredByAgeBrackets ends" + resp);

		return resp;
	}
	/////////////////////HRMS REQUITMENT DASHBOARD END//////////////////////////
	
	

	
}
