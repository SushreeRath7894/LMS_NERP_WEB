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
public class HrmsDashboardController {
	Logger logger = LoggerFactory.getLogger(HrmsDashboardController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("hrms-dashboard")
	public String hrmsDashboard(Model model, HttpSession session) {
		logger.info("Method : hrmsDashboard starts");

		
		String orgName = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : productionDashboard starts");	
		
		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getEmployeeUrl() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> Organization = Arrays.asList(dropDownModel);
			model.addAttribute("Organization", Organization);

			logger.info("Organization" + Organization);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getEmployeeUrl() + "getDivision?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDivisionList", orgDivisionList);

			logger.info("orgDivisionList" + orgDivisionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		

		
		

		logger.info("Method : hrmsDashboard ends");
		return "employee/hrms-dashboard";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-hrms-operation-getAllRecord")
	public @ResponseBody Object getAllDataOprtnl(HttpSession session, @RequestParam String id,@RequestParam String fromDate, 
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org, @RequestParam String orgDiv) {
		

		logger.info("Method :getAllDataOprtnl starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "dashboard-hrms-getAllData-oprtnl?id=" + id + "&fromDate=" + fromDate
							+ "&toDate=" + toDate + "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//logger.info("Method :getAllDataOprtnl ends" + resp);

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-count1")
	public @ResponseBody Object hrmsDashboardCount1(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsDashboardCount1 starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsDashboardCount1?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :hrmsDashboardCount1 ends" + resp);

		return resp;
	}
	
	//hrms-dashboard-cityList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-cityList" })
	public @ResponseBody JsonResponse<Object> getAllHRMSCityList(@RequestParam String id) {
		logger.info("Method : getAllHRMSCityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "getAllHRMSCityList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getAllHRMSCityList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-count2")
	public @ResponseBody Object hrmsDashboardCount2(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsDashboardCount2 starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsDashboardCount2?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :hrmsDashboardCount2 ends" + resp);

		return resp;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-attrition-head-count")
	public @ResponseBody Object hrmsDashboardAttritionHeadCount(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsDashboardAttritionHeadCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsDashboardAttritionHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :hrmsDashboardAttritionHeadCount ends" + resp);

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-attrition-involuntary-voluntary")
	public @ResponseBody Object hrmsDashboardAttnInvoluntaryVoluntary(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsDashboardAttnInvoluntaryVoluntary starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsDashboardAttnInvoluntaryVoluntary?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :hrmsDashboardAttnInvoluntaryVoluntary ends" + resp);

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-attrition-emp-tenure")
	public @ResponseBody Object hrmsDashboardAttnEmpTenure(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :hrmsDashboardAttnEmpTenure starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "hrmsDashboardAttnEmpTenure?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :hrmsDashboardAttnEmpTenure ends" + resp);

		return resp;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-attrition-ratio")
	public @ResponseBody Object dashhrmsAttRatio(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :dashhrmsAttRatio starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "dashhrmsAttRatio?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashhrmsAttRatio ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-attrition-dept")
	public @ResponseBody Object dashhrmsAttDeptWise(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :dashhrmsAttDeptWise starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "dashhrmsAttDeptWise?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashhrmsAttDeptWise ends" + resp);

		return resp;
	}
	
	
	//dashboard-hrms-attrition-voluntary-by
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-attrition-voluntary-by")
	public @ResponseBody Object dashhrmsAttVoluntaryWise(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :dashhrmsAttVoluntaryWise starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
				
		try {
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "dashhrmsAttVoluntaryWise?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashhrmsAttVoluntaryWise ends" + resp);

		return resp;
	}
	
	
	//dashboard-hrms-reason-distribution
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-reason-distribution")
		public @ResponseBody Object dashhrmsAttReasonDistribution(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsAttReasonDistribution starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
						
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsAttReasonDistribution?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :dashhrmsAttReasonDistribution ends" + resp);

			return resp;
		}
	
		
		
		//dashboard-hrms-attrition-by-tenure-salary
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-attrition-by-tenure-salary")
		public @ResponseBody Object dashhrmsAttritionByTenureSalary(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsAttritionByTenureSalary starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsAttritionByTenureSalary?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :dashhrmsAttritionByTenureSalary ends" + resp);

			return resp;
		}
		
		//dashboard-hrms-attrition-by-job-role
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-attrition-by-job-role")
		public @ResponseBody Object dashhrmsAttritionByJobRole(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsAttritionByJobRole starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsAttritionByJobRole?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsAttritionByJobRole ends" + resp);

			return resp;
		}
		
		
		//dashboard-hrms-attrition-by-tenure
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-attrition-by-tenure")
		public @ResponseBody Object dashhrmsAttritionByTenure(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsAttritionByTenure starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsAttritionByTenure?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :dashhrmsAttritionByTenure ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-compensation-head-count
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-compensation-head-count")
		public @ResponseBody Object hrmsDashboardCompensationHeadCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsDashboardCompensationHeadCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsDashboardCompensationHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :hrmsDashboardCompensationHeadCount ends" + resp);

			return resp;
		}
		
		//hrms-dashboard-compensation-salary-distribution-by-dept
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-compensation-salary-distribution-by-dept")
		public @ResponseBody Object hrmsDashboardCompensationSalaryDistributionDept(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsDashboardCompensationSalaryDistributionDept starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsDashboardCompensationSalaryDistributionDept?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :hrmsDashboardCompensationSalaryDistributionDept ends" + resp);

			return resp;
		}
		
		//hrms-dashboard-compensation-employee-count-by-salary-range
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-compensation-employee-count-by-salary-range")
		public @ResponseBody Object hrmsDashboardCompensationEmpCountSalaryRange(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsDashboardCompensationEmpCountSalaryRange starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsDashboardCompensationEmpCountSalaryRange?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :hrmsDashboardCompensationEmpCountSalaryRange ends" + resp);

			return resp;
		}
		
		//hrms-dashboard-compensation-joining-leaving-trend
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-compensation-joining-leaving-trend")
		public @ResponseBody Object dashhrmsCompensationJoiningLeavingTrend(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsCompensationJoiningLeavingTrend starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsCompensationJoiningLeavingTrend?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsCompensationJoiningLeavingTrend ends" + resp);

			return resp;
		}
		//hrms-dashboard-compensation-salary-by-performance
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-compensation-salary-by-performance")
		public @ResponseBody Object dashhrmsCompensationSalaryByPerformance(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsCompensationSalaryByPerformance starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsCompensationSalaryByPerformance?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsCompensationSalaryByPerformance ends" + resp);

			return resp;
		}
		//hrms-dashboard-compensation-avg-salary-year-range-by
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-compensation-avg-salary-year-range-by")
		public @ResponseBody Object dashhrmsAttritionCompensationAvgSalryYearRangeBy(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsAttritionCompensationAvgSalryYearRangeBy starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsAttritionCompensationAvgSalryYearRangeBy?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsAttritionCompensationAvgSalryYearRangeBy ends" + resp);

			return resp;
		}
		
		
		///////////////////////////////////Performance Start//////////////////////////////////////////
		//hrms-dashboard-performance-head-count
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-performance-head-count")
		public @ResponseBody Object dashhrmsPerformHeadCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsPerformHeadCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsPerformHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsPerformHeadCount ends" + resp);

			return resp;
		}
		
		
			//hrms-dashboard-performance-employee-rating-distri-by-dept
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-performance-employee-rating-distri-by-dept")
		public @ResponseBody Object dashhrmsPerformanceEmpRatingDistriByDept(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsPerformanceEmpRatingDistriByDept starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsPerformanceEmpRatingDistriByDept?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsPerformanceEmpRatingDistriByDept ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-performance-employee-tenure-trend-with-last-year
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-performance-employee-tenure-trend-with-last-year")
		public @ResponseBody Object dashhrmsPerformanceEmpTenureWithLastYear(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsPerformanceEmpTenureWithLastYear starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsPerformanceEmpTenureWithLastYear?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsPerformanceEmpTenureWithLastYear ends" + resp);

			return resp;
		}
		
			
		//hrms-dashboard-performance-overtime-by-age-group
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-performance-overtime-by-age-group")
		public @ResponseBody Object dashhrmsPerformanceOvertimebyAgeGroup(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsPerformanceOvertimebyAgeGroup starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsPerformanceOvertimebyAgeGroup?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsPerformanceOvertimebyAgeGroup ends" + resp);

			return resp;
		}
		
		
			//hrms-dashboard-performance-absenteeism-rate
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-performance-absenteeism-rate")
		public @ResponseBody Object dashhrmsPerformanceAbsenteeismRate(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsPerformanceAbsenteeismRate starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsPerformanceAbsenteeismRate?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsPerformanceAbsenteeismRate ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-performance-employee-count-by-rating
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-performance-employee-count-by-rating")
		public @ResponseBody Object dashhrmsPerformanceEmpCountByRating(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsPerformanceEmpCountByRating starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsPerformanceEmpCountByRating?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsPerformanceEmpCountByRating ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-performance-employee-salary-by-tenure-range
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-performance-employee-salary-by-tenure-range")
		public @ResponseBody Object dashhrmsPerformanceAvgSalaryYearRangeBy(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :dashhrmsPerformanceAvgSalaryYearRangeBy starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "dashhrmsPerformanceAvgSalaryYearRangeBy?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :dashhrmsPerformanceAvgSalaryYearRangeBy ends" + resp);

			return resp;
		}
		
		
		
		/////////////////////////////////////Performance End///////////////////////////////////////
		
		
		/////////////////////////////////////Management Start//////////////////////////////////////
		
		//hrms-dashboard-management-head-count
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-head-count")
		public @ResponseBody Object hrmsManagementHeadCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementHeadCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementHeadCount ends" + resp);

			return resp;
		}
		
		//hrms-dashboard-management-emp-count-by-dept
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-emp-count-by-dept")
		public @ResponseBody Object hrmsManagementEmpCntByDept(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementEmpCntByDept starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementEmpCntByDept?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementEmpCntByDept ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-management-emp-count-by-age
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-emp-count-by-age")
		public @ResponseBody Object hrmsManagementEmpCntByAge(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementEmpCntByAge starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementEmpCntByAge?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementEmpCntByAge ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-management-emp-count-by-dept-experience
		
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-emp-count-by-dept-experience")
		public @ResponseBody Object hrmsManagementEmpCntByDeptExp(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementEmpCntByDeptExp starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementEmpCntByDeptExp?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementEmpCntByDeptExp ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-management-headcount-development
		
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-headcount-development")
		public @ResponseBody Object hrmsManagementHeadCountDevlpmnt(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementHeadCountDevlpmnt starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementHeadCountDevlpmnt?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementHeadCountDevlpmnt ends" + resp);

			return resp;
		}
		
		
		
		//hrms-dashboard-management-emp-pay-by-dept
		
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-emp-pay-by-dept")
		public @ResponseBody Object hrmsManagementEmpPayByDept(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementEmpPayByDept starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementEmpPayByDept?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementEmpPayByDept ends" + resp);

			return resp;
		}
		
		
		
		//hrms-dashboard-management-hire-by-emp-type
		
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-hire-by-emp-type")
		public @ResponseBody Object hrmsManagementHireByEmpType(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementHireByEmpType starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementHireByEmpType?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementHireByEmpType ends" + resp);

			return resp;
		}
		
		
		
		//hrms-dashboard-management-salary-by-age-group
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-salary-by-age-group")
		public @ResponseBody Object hrmsManagementSalaryByAgeGroup(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementSalaryByAgeGroup starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementSalaryByAgeGroup?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementSalaryByAgeGroup ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-management-emp-by-gender
		
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-emp-by-gender")
		public @ResponseBody Object hrmsManagementEmpByGender(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementEmpByGender starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementEmpByGender?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementEmpByGender ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-management-emp-count-by-city
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-emp-count-by-city")
		public @ResponseBody Object hrmsManagementEmpCountByCity(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementEmpCountByCity starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementEmpCountByCity?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementEmpCountByCity ends" + resp);

			return resp;
		}
		
		
		
		//hrms-dashboard-management-emp-by-experience
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-emp-by-experience")
		public @ResponseBody Object hrmsManagementEmpByExperience(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementEmpByExperience starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementEmpByExperience?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementEmpByExperience ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-management-cost-per-emp

		
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-management-cost-per-emp")
		public @ResponseBody Object hrmsManagementCostPerEmp(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsManagementCostPerEmp starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsManagementCostPerEmp?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsManagementCostPerEmp ends" + resp);

			return resp;
		}
		/////////////////////////////////////Management End////////////////////////////////////////
		
		/////////////////////////////////////Talent Start//////////////////////////////////////////
		//hrms-dashboard-talent-head-count
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-head-count")
		public @ResponseBody Object hrmsTalentHeadCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentHeadCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentHeadCount ends" + resp);

			return resp;
		}
		
		//hrms-dashboard-talent-turnover-rate
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-turnover-rate")
		public @ResponseBody Object hrmsTalentTurnoverRate(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentTurnoverRate starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentTurnoverRate?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentTurnoverRate ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-fired-talent-by-employment-period
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-fired-talent-by-employment-period")
		public @ResponseBody Object hrmsTalentFiredByEmploymentByPeriod(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentFiredByEmploymentByPeriod starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentFiredByEmploymentByPeriod?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentFiredByEmploymentByPeriod ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-talent-satisfaction-month-wise
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-satisfaction-month-wise")
		public @ResponseBody Object hrmsTalentSatisfactionMonthWise(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentSatisfactionMonthWise starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentSatisfactionMonthWise?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentSatisfactionMonthWise ends" + resp);

			return resp;
		}
		

		//hrms-dashboard-talent-trend-years-wise-count
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-trend-years-wise-count")
		public @ResponseBody Object hrmsTalentTrendWiseYearCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentTrendWiseYearCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentTrendWiseYearCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentTrendWiseYearCount ends" + resp);

			return resp;
		}
		
		//hrms-dashboard-talent-ratings-month-wise
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-ratings-month-wise")
		public @ResponseBody Object hrmsTalentRatingMonthWise(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentRatingMonthWise starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentRatingMonthWise?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentRatingMonthWise ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-talent-by-rating-by-percentage
		
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-by-rating-by-percentage")
		public @ResponseBody Object hrmsTalentByRatingByPercent(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentByRatingByPercent starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentByRatingByPercent?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentByRatingByPercent ends" + resp);

			return resp;
		}

		//hrms-dashboard-talent-last-six-month-category-wise
		
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-last-six-month-category-wise")
		public @ResponseBody Object hrmsTalentLastSixMonthCategoriesWise(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentLastSixMonthCategoriesWise starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentLastSixMonthCategoriesWise?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentLastSixMonthCategoriesWise ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-talent-last-one-year-category-wise
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-last-one-year-category-wise")
		public @ResponseBody Object hrmsTalentLastOneYearCategoriesWise(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentLastOneYearCategoriesWise starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentLastOneYearCategoriesWise?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentLastOneYearCategoriesWise ends" + resp);

			return resp;
		}
		
		
		
		//hrms-dashboard-talent-last-two-year-category-wise
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-last-two-year-category-wise")
		public @ResponseBody Object hrmsTalentLastTwoYearCategoriesWise(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentLastTwoYearCategoriesWise starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentLastTwoYearCategoriesWise?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentLastTwoYearCategoriesWise ends" + resp);

			return resp;
		}
		
		
		//hrms-dashboard-talent-last-three-year-category-wise
		@SuppressWarnings("unchecked")
		@GetMapping("hrms-dashboard-talent-last-three-year-category-wise")
		public @ResponseBody Object hrmsTalentLastThreeYearCategoriesWise(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :hrmsTalentLastThreeYearCategoriesWise starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			
			
			try {
				resp = restTemplate
						.getForObject(
								env.getEmployeeUrl() + "hrmsTalentLastThreeYearCategoriesWise?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method :hrmsTalentLastThreeYearCategoriesWise ends" + resp);

			return resp;
		}
		/////////////////////////////////////Talent End////////////////////////////////////////////
		
		
	/*

	// Total Monthly Attendance
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-total-monthlyattendance" })
	public @ResponseBody JsonResponse<List<Object>> getTotalMonthlyAttendance(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			logger.info("Method : getTotalMonthlyAttendance starts");
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getTotalMonthlyAttendance?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
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
		logger.info("Method : getTotalMonthlyAttendance ends" + res);

		return res;

	}
	// totalMonthlyAttendance aggrid

	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-monthlyattendance-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalMonthlyAttendanceAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("month") String month) {

		logger.info("Method : getTotalMonthlyAttendanceAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMonthlyAttendanceAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalMonthlyAttendanceAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Total Monthly Reimbursement
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-total-monthlyreimbursement" })
	public @ResponseBody JsonResponse<List<Object>> getTotalMonthlyReimbursement(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getTotalMonthlyReimbursement starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getTotalMonthlyReimbursement?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
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
		logger.info("Method : getTotalMonthlyReimbursement ends" + res);

		return res;

	}

	// Total Monthly Reimbursement Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-monthreimurse-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalMonthlyReimbursementAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("month") String month) {

		logger.info("Method : getTotalMonthlyReimbursementAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMonthlyReimbursementAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalMonthlyReimbursementAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Total leave

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/hrms-dashboard-total-leave" })
	public @ResponseBody JsonResponse<List<Object>> getTotalLeave(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getTotalLeave starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalLeave?userId=" + userId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getTotalLeave ends" + res);

		return res;

	}
	// Totalleave aggrid

	@SuppressWarnings("unchecked")

	@GetMapping("/hrms-dashboard-total-leave-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalLeaveAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear) {

		logger.info("Method : getTotalLeaveAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getTotalLeaveAggrid?userId=" + userId + "&value1=" + value1
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalLeaveAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Total Monthly event
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-total-monthlyevent" })
	public @ResponseBody JsonResponse<List<Object>> getTotalMonthlyEvent(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getTotalMonthlyEvent starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMonthlyEvent?userId=" + userId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
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
		logger.info("Method : getTotalMonthlyEvent ends" + res);
		return res;

	}

	// Monthly event Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-monthlyevent-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalMonthlyEventAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("month") String month) {

		logger.info("Method : getTotalMonthlyEventAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMonthlyEventAggrid?userId=" + userId
					+ "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalMonthlyEventAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Count details
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-countdetails" })
	public @ResponseBody JsonResponse<Object> getCountDetails(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getCountDetails starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getCountDetails?userId=" + userId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getCountDetails ends" + resp);
		return resp;
	}

	// Manager Count Details
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-managercountdetails" })
	public @ResponseBody JsonResponse<Object> getManagerCountDetails(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getManagerCountDetails starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getManagerCountDetails?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
							JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getManagerCountDetails ends" + resp);
		return resp;
	}

	// Total leave Approve

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-leave-Approve" })
	public @ResponseBody JsonResponse<List<Object>> getLeaveApprove(Model model, @RequestParam String fromdate,
			@RequestParam String todate, HttpSession session) {
		logger.info("Method : getLeaveApprove startssssssssssssssssssssssssss" + fromdate);
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getLeaveApprove?userId=" + userId + "&orgName=" + orgName
									+ "&orgDiv=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
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
		logger.info("Method : getLeaveApprove ends" + res);

		return res;

	}
	// Leave Apply aggrid

	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-leaveApprove-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getLeaveApprovedAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam String fromdate, @RequestParam String todate) {

		logger.info("Method : getLeaveApprovedAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getLeaveApprovedAggrid?userId=" + userId
					+ "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&fromdate=" + fromdate
					+ "&todate=" + todate, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getLeaveApprovedAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Total Reimbursement Approved
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-reimbursementapproved" })
	public @ResponseBody JsonResponse<List<Object>> getReimbursementApproved(Model model,
			@RequestParam String fromdate, @RequestParam String todate, HttpSession session) {
		logger.info("Method : getReimbursementApproved starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getReimbursementApproved?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&fromdate=" + fromdate+"&todate="+todate,
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
		logger.info("Method : getReimbursementApproved ends" + res);

		return res;

	}

	// Total Reimbursement Approved Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-reimurseapprove-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getReimbursementApprovedAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam String fromdate,@RequestParam String todate,
			@RequestParam("month") String month) {

		logger.info("Method : getReimbursementApprovedAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getReimbursementApprovedAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&fromdate="
					+ fromdate +"&todate="+todate+ "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getReimbursementApprovedAggrid ends" + res.getBody());
		return res.getBody();

	}

	// PayRoll Process

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-payrollprocess" })
	public @ResponseBody JsonResponse<List<Object>> getPayrollProcess(Model model, @RequestParam String fromdate,
			@RequestParam String todate, HttpSession session) {
		logger.info("Method : getPayrollProcess starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getPayrollProcess?userId=" + userId + "&orgName=" + orgName
							+ "&orgDiv=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
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
		logger.info("Method : getPayrollProcess ends" + res);

		return res;

	}

	// Payroll Process Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-payrollprocess-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getPayrollProcessAggrid(HttpSession session, Model model,
			@RequestParam("value") String value, @RequestParam String fromdate, @RequestParam String todate) {

		logger.info("Method : getPayrollProcessAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getPayrollProcessAggrid?userId=" + userId
					+ "&value=" + value + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&fromdate=" + fromdate
					+ "&todate=" + todate, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getPayrollProcessAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Rating Wise Appresal

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-ratingwise-appraisal" })
	public @ResponseBody JsonResponse<List<Object>> getRatingWise(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getRatingWise starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getRatingWise?userId=" + userId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getRatingWise ends" + res);

		return res;

	}

	// RatingWise Appresal Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-ratingwise-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getRatingWiseAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear) {

		logger.info("Method : getRatingWiseAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getRatingWiseAggrid?userId=" + userId + "&value1=" + value1
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getRatingWiseAggrid ends" + res.getBody());
		return res.getBody();

	}

	// HR Count Details
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-hrcountdetails" })
	public @ResponseBody JsonResponse<Object> getHrCountDetails(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getHrCountDetails starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getHrCountDetails?userId=" + userId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getHrCountDetails ends" + resp);
		return resp;
	}

	// Designation By Requisition
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-designationbyrequisition" })
	public @ResponseBody JsonResponse<Object> getDesignationByRequisition(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getDesignationByRequisition starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getDesignationByRequisition?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
							JsonResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getDesignationByRequisition ends" + resp);
		return resp;
	}

	// Designation By Requisition Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-designationRequisition-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getDesignationByRequisitionAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("value") String value) {

		logger.info("Method : getDesignationByRequisitionAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getDesignationByRequisitionAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&value=" + value, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getDesignationByRequisitionAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Requisition Status
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-requisitionstatus" })
	public @ResponseBody JsonResponse<Object> getRequisitionStatus(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getRequisitionStatus starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getRequisitionStatus?userId=" + userId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getRequisitionStatus ends" + resp);
		return resp;
	}

	// Requisition Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-requisitionStatus-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getRequisitionStatusAggrid(HttpSession session, Model model,
			@RequestParam("value") String value, @RequestParam("currentYear") String currentYear) {

		logger.info("Method : getRequisitionStatusAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getRequisitionStatusAggrid?userId=" + userId + "&value=" + value
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getRequisitionStatusAggrid ends" + res.getBody());
		return res.getBody();

	}

	//// Gender Wise Candidate/Employee
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-genderewisecandidate" })
	public @ResponseBody JsonResponse<List<Object>> getGendereWiseCandidate(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getGendereWiseCandidate starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getGendereWiseCandidate?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
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
		logger.info("Method : getGendereWiseCandidate ends" + res);

		return res;

	}
	// Gender Wise Candidate/Employee Aggrid

	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-genderwisecandidate-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getGendereWiseCandidateAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear) {

		logger.info("Method : getGendereWiseCandidateAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getGendereWiseCandidateAggrid?userId=" + userId + "&value1=" + value1
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getGendereWiseCandidateAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Yearly Selection
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-yearlyevent" })
	public @ResponseBody JsonResponse<List<Object>> getYearlyEvent(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getYearlyEvent starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getYearlyEvent?userId=" + userId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getYearlyEvent ends" + res);
		return res;

	}

	// Yearly Selection Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-yearlyevent-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getYearlyEventAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("month") String month) {

		logger.info("Method : getYearlyEventAggrid starts");
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getYearlyEventAggrid?userId=" + userId
					+ "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getYearlyEventAggrid ends" + res.getBody());
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-yearlyselection")
	public @ResponseBody JsonResponse<List<Object>> viewYearSelectionData(Model model, @RequestParam String currentYear,
			HttpSession session) {

		logger.info("Method : viewYearSelectionData starts" + currentYear);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(currentYear);

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "dashboard-yearlyselection?currentYear="
					+ currentYear + "&orgname=" + organization + "&orgdiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewYearSelectionData ends");
		return jsonResponse;

	}
	// Total attendance

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-attendance-report" })
	public @ResponseBody JsonResponse<List<Object>> getAttendanceReports(Model model, @RequestParam String fromdate,
			@RequestParam String todate, HttpSession session) {
		logger.info("Method : getAttendanceReports starts" + fromdate);
		logger.info("Method : getAttendanceReports starts" + todate);
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getAttendanceReports?userId=" + userId + "&orgName=" + orgName
							+ "&orgDiv=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
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
		logger.info("Method : getAttendanceReports ends" + res);

		return res;

	}

	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-employeeattendance-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalEmployeeAttendanceAggrid(HttpSession session, Model model,
			@RequestParam String value1, @RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String shift) {

		logger.info("Method : getTotalMonthlyAttendanceAggrid starts" + value1 + fromdate + todate + shift);
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

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalemployeeAttendanceAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&fromdate="
					+ fromdate + "&todate=" + todate + "&shift=" + shift, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalMonthlyAttendanceAggrid ends" + res.getBody());
		return res.getBody();

	}
	
	*/
}



