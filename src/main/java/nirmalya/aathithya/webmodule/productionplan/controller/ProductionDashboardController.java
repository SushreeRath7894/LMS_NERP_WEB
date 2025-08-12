package nirmalya.aathithya.webmodule.productionplan.controller;

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
import nirmalya.aathithya.webmodule.common.utils.FileUpload;

@Controller
@RequestMapping(value = { "production/" })
public class ProductionDashboardController {
	
Logger logger = LoggerFactory.getLogger(ProductionDashboardController.class);
	
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@Autowired
	FileUpload fileUpload;
	
	@GetMapping(value = { "production-dashboard" })
	public String manageDashboard(Model model, HttpSession session) {
		logger.info("Method : manageDashboard starts");

		
		logger.info("Method : manageDashboard ends");
		return "production/production-dashboard";
	}
	
	@GetMapping("/dashboard")
	public String productionDashboard(Model model, HttpSession session) {
		String orgName = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : productionDashboard starts");	
		
		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getProduction() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> Organization = Arrays.asList(dropDownModel);
			model.addAttribute("Organization", Organization);

			logger.info("Organization" + Organization);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getProduction() + "getDivision?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDivisionList", orgDivisionList);

			logger.info("orgDivisionList" + orgDivisionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getProduction() + "getProductInAnalysis?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> getProductList = Arrays.asList(dropDownModel);
			model.addAttribute("getProductList", getProductList);

			logger.info("getProductList-----------------------" + getProductList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : productionDashboard ends");

		return "production_plan/view-dashboard";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-operation-count1")
	public @ResponseBody Object oprationalProdctionHeadCount1(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :oprationalProdctionHeadCount1 starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient
					.getForObject(
							env.getProduction() + "oprationalProdctionHeadCount1?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :oprationalProdctionHeadCount1 ends" + resp);

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-operation-count2")
	public @ResponseBody Object oprationalProdctionHeadCount2(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :oprationalProdctionHeadCount2 starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient
					.getForObject(
							env.getProduction() + "oprationalProdctionHeadCount2?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :oprationalProdctionHeadCount2 ends" + resp);

		return resp;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-operation-getAllRecord")
	public @ResponseBody Object getAllDataOprtnl(HttpSession session, @RequestParam String id,@RequestParam String fromDate, 
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org, @RequestParam String orgDiv) {
		

		logger.info("Method :getAllDataOprtnl starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient
					.getForObject(
							env.getProduction() + "dashboard-getAllData-oprtnl?id=" + id + "&fromDate=" + fromDate
							+ "&toDate=" + toDate + "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//logger.info("Method :getAllDataOprtnl ends" + resp);

		return resp;
	}
	
	
	
	

	//manage-dashboard-ooe-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-ooe-count")
	public @ResponseBody Object ooeProdctionHeadCount(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
			@RequestParam String orgDiv) {
		

		logger.info("Method :ooeProdctionHeadCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient
					.getForObject(
							env.getProduction() + "ooeProdctionHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
									+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :ooeProdctionHeadCount ends" + resp);

		return resp;
	}
	
	
	
	///dashboard-machine-specialization-details
	//manage-dashboard-getAllRecord
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-machine-specialization-details")
		public @ResponseBody Object getAllMachineSpecializationDtls(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :getAllMachineSpecializationDtls starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "getAllMachineSpecializationDtls?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(env.getAssetUrl());

			logger.info("Method :getAllMachineSpecializationDtls ends" + resp);

			return resp;
		}
		
		
		//dashboard-production-count
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-production-count")
		public @ResponseBody Object prodctionHeadCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {
			

			logger.info("Method :prodctionHeadCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "prodctionHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :prodctionHeadCount ends" + resp);

			return resp;
		}
	
		//dashboard-avg-monthly-sales-details
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-avg-monthly-sales-details")
		public @ResponseBody Object dashboardAvgMonthlySalesDetails(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardAvgMonthlySalesDetails starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardAvgMonthlySalesDetails?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(env.getAssetUrl());

			logger.info("Method :dashboardAvgMonthlySalesDetails ends" + resp);

			return resp;
		}
		
		
		//dashboard-top-five-product-revenue
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-top-five-product-revenue")
		public @ResponseBody Object dashboardTopFiveProductRevenue(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTopFiveProductRevenue starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTopFiveProductRevenue?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(env.getAssetUrl());

			logger.info("Method :dashboardTopFiveProductRevenue ends" + resp);

			return resp;
		}
		
		//dashboard-top-five-product-by-production
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-top-five-product-by-production")
		public @ResponseBody Object dashboardTopFiveProductByProduction(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTopFiveProductByProduction starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTopFiveProductByProduction?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(env.getAssetUrl());

			logger.info("Method :dashboardTopFiveProductByProduction ends" + resp);

			return resp;
		}
		
		//dashboard-machine-breakdown
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-machine-breakdown")
		public @ResponseBody Object dashboardMachineBreakdown(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardMachineBreakdown starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardMachineBreakdown?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(env.getAssetUrl());

			logger.info("Method :dashboardMachineBreakdown ends" + resp);

			return resp;
		}
		
		//dashboard-return-item-by-reason
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-return-item-by-reason")
		public @ResponseBody Object dashboardReturnByReason(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardReturnByReason starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardReturnByReason?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardReturnByReason ends" + resp);

			return resp;
		}
		
		//dashboard-return-item-by-reason-by-type
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-return-item-by-reason-by-type")
		public @ResponseBody Object dashboardReturnByReasonType(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv, @RequestParam String type) {

			logger.info("Method :dashboardReturnByReason starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardReturnByReasonType?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv+ "&type=" + type,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardReturnByReasonType ends" + resp);

			return resp;
		}
		
		//dashboard-right-first-time
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-right-first-time")
		public @ResponseBody Object dashboardRightFirstTime(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardRightFirstTime starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardRightFirstTime?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardRightFirstTime ends" + resp);

			return resp;
		}
		
		//dashboard-avg-right-first-time
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-avg-right-first-time")
		public @ResponseBody Object dashboardAvgRightFirstTime(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardAvgRightFirstTime starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardAvgRightFirstTime?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardAvgRightFirstTime ends" + resp);

			return resp;
		}
		
		
		
		//dashboard-rate-of-return
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-rate-of-return")
		public @ResponseBody Object dashboardRateOfReturn(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardRateOfReturn starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardRateOfReturn?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardRateOfReturn ends" + resp);

			return resp;
		}
		
		//dashboard-rate-of-return-by-type
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-rate-of-return-by-type")
		public @ResponseBody Object dashboardRateOfReturnByType(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv, @RequestParam String type) {

			logger.info("Method :dashboardRateOfReturnByType starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardRateOfReturnByType?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&type=" + type,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :dashboardRateOfReturnByType ends" + resp);

			return resp;
		}
		
		
				//dashboard-defect-density
				@SuppressWarnings("unchecked")
				@GetMapping("dashboard-defect-density")
				public @ResponseBody Object dashboardDefectDensity(HttpSession session, @RequestParam String fromDate,
						@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
						@RequestParam String orgDiv) {

					logger.info("Method :dashboardDefectDensity starts");
					JsonResponse<Object> resp = new JsonResponse<Object>();
				
					try {
						resp = restClient
								.getForObject(
										env.getProduction() + "dashboardDefectDensity?fromDate=" + fromDate + "&toDate=" + toDate
												+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
										JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

					//System.out.println(env.getProduction());

					logger.info("Method :dashboardDefectDensity ends" + resp);

					return resp;
				}
		
			//dashboard-most-common-defect
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-most-common-defect")
		public @ResponseBody Object dashboardMostCommonDefect(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardMostCommonDefect starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardMostCommonDefect?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardMostCommonDefect ends" + resp);

			return resp;
		}
		
		
		
		
		
		//dashboard-cost-management-count
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-cost-management-count")
		public @ResponseBody Object dashboardCostManagementCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardCostManagementCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardCostManagementCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardCostManagementCount ends" + resp);

			return resp;
		}
		
	/*	
		//dashboard-cost-management-return-on-assets
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-cost-management-return-on-assets")
		public @ResponseBody Object dashboardCostManagementReturnOnAssets(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardCostManagementReturnOnAssets starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardCostManagementReturnOnAssets?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardCostManagementReturnOnAssets ends" + resp);

			return resp;
		}
		*/
		
		//dashboard-maintenance-cost-with-target
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-maintenance-cost-with-target")
		public @ResponseBody Object dashboardCostMangmntMaintenenceWithTarget(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardCostMangmntMaintenenceWithTarget starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardCostMangmntMaintenenceWithTarget?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardCostMangmntMaintenenceWithTarget ends" + resp);

			return resp;
		}
		
		/*
		//dashboard-cost-management-asset-turnover
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-cost-management-asset-turnover")
		public @ResponseBody Object dashboardCostManagementAssetTurnOver(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardCostManagementAssetTurnOver starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardCostManagementAssetTurnOver?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardCostManagementAssetTurnOver ends" + resp);

			return resp;
		}
	*/
		
		//dashboard-cost-management-unit-cost-target
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-cost-management-unit-cost-target")
		public @ResponseBody Object dashboardCostManagementUnitCostTarget(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardCostManagementUnitCostTarget starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardCostManagementUnitCostTarget?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardCostManagementUnitCostTarget ends" + resp);

			return resp;
		}
		
		/*
		//dashboard-kpi-effectiveness
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-kpi-effectiveness")
		public @ResponseBody Object dashboardKPIEffectiveness(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardKPIEffectiveness starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardKPIEffectiveness?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardKPIEffectiveness ends" + resp);

			return resp;
		}
		
		
		//dashboard-kpi-quality-performance
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-kpi-quality-performance")
		public @ResponseBody Object dashboardKPIQualityPerformance(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardKPIQualityPerformance starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardKPIQualityPerformance?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardKPIQualityPerformance ends" + resp);

			return resp;
		}
		
		
		//dashboard-kpi-production
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-kpi-production")
		public @ResponseBody Object dashboardKPIProduction(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardKPIProduction starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardKPIProduction?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardKPIProduction ends" + resp);

			return resp;
		}
		
		
		//dashboard-kpi-cost-revenue
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-kpi-cost-revenue")
		public @ResponseBody Object dashboardKPICostRevenue(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardKPICostRevenue starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardKPICostRevenue?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardKPICostRevenue ends" + resp);

			return resp;
			}
			
				*/
		
		//dashboard-analysis-head-count 
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-analysis-head-count")
		public @ResponseBody Object dashboardAnalysisHeadCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv, @RequestParam String productSkuId,@RequestParam String currentMonth,
				@RequestParam String currentYear, @RequestParam String lastMonth,@RequestParam String lastYear) {

			logger.info("Method :dashboardAnalysisHeadCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {				
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardAnalysisHeadCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv+ "&productSkuId=" + productSkuId 
										+ "&currentMonth=" + currentMonth + "&currentYear=" + currentYear + "&lastMonth=" + lastMonth
										+ "&lastYear=" + lastYear,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardAnalysisHeadCount ends" + resp);

			return resp;
		}
		
		//dashboard-analysis-count-value-on-product-sku-id      
		
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-analysis-count-value-on-product-sku-id")
		public @ResponseBody Object dashboardAnalysisCountOnProductSKUId(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv, @RequestParam String productSkuId) {

			logger.info("Method :dashboardAnalysisCountOnProductSKUId starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardAnalysisCountOnProductSKUId?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv+ "&productSkuId=" + productSkuId,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardAnalysisCountOnProductSKUId ends" + resp);

			return resp;
		}
	
		
		//dashboard-analysis-runtime-downtime
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-analysis-runtime-downtime")
		public @ResponseBody Object dashboardAnalysisRuntimeDowntime(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardAnalysisRuntimeDowntime starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardAnalysisRuntimeDowntime?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardAnalysisRuntimeDowntime ends" + resp);

			return resp;
		}
		
		
	
		//dashboard-analysis-production-cost
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-analysis-production-cost")
		public @ResponseBody Object dashboardAnalysisProductionCost(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardAnalysisProductionCost starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardAnalysisProductionCost?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardAnalysisProductionCost ends" + resp);

			return resp;
		}
		
		
		//dashboard-analysis-availability-performance-quality-effectiveness
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-analysis-availability-performance-quality-effectiveness")
		public @ResponseBody Object dashboardAnalysisAvailablePerformQualityEffect(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardAnalysisAvailablePerformQualityEffect starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardAnalysisAvailablePerformQualityEffect?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardAnalysisAvailablePerformQualityEffect ends" + resp);

			return resp;
		}

		//==================================Control===========================================
		  
		
		
		
		//dashboard-control-cycle-yield-throughput-workforce-lead-count
				@SuppressWarnings("unchecked")
				@GetMapping("dashboard-control-cycle-yield-throughput-workforce-lead-count")
				public @ResponseBody Object dashboardCycleYieldThroughputWorkForceLeadCount(HttpSession session, @RequestParam String fromDate,
						@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
						@RequestParam String orgDiv) {

					logger.info("Method :dashboardCycleYieldThroughputWorkForceLeadCount starts");
					JsonResponse<Object> resp = new JsonResponse<Object>();
				
					try {
						resp = restClient
								.getForObject(
										env.getProduction() + "dashboardCycleYieldThroughputWorkForceLeadCount?fromDate=" + fromDate + "&toDate=" + toDate
												+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
										JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

					//dashboardControlCount

					logger.info("Method :dashboardCycleYieldThroughputWorkForceLeadCount ends" + resp);

					return resp;
				}
		
		//dashboard-qty-rework-qty-production-order-qty-count
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-qty-rework-qty-production-order-qty-count")
		public @ResponseBody Object dashboardQtyReworkProductionOrderedQtyCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv, @RequestParam String currentMonth, @RequestParam String currentYear,
				@RequestParam String lastMonth, @RequestParam String lastYear) {

			logger.info("Method :dashboardQtyReworkProductionOrderedQtyCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardQtyReworkProductionOrderedQtyCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&currentMonth=" + currentMonth 
										+ "&currentYear=" + currentYear + "&lastMonth=" + lastMonth + "&lastYear=" + lastYear,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :dashboardQtyReworkProductionOrderedQtyCount ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-ooe-capacity-firstPassYeild-scrape-count
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-ooe-capacity-firstPassYeild-scrape-count")
		public @ResponseBody Object dashboardControlOOECapacityFirstScapeCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlOOECapacityFirstScapeCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlOOECapacityFirstScapeCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlOOECapacityFirstScapeCount ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-availablity-performance-quality-effectiveness-count
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-availablity-performance-quality-effectiveness-count")
		public @ResponseBody Object dashboardControlAvailabilityPerformanceEffectivenessCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlAvailabilityPerformanceEffectivenessCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlAvailabilityPerformanceEffectivenessCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlAvailabilityPerformanceEffectivenessCount ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-right-first-time
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-right-first-time")
		public @ResponseBody Object dashboardControlRightFirstTime(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlRightFirstTime starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlRightFirstTime?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlRightFirstTime ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-throughput
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-throughput")
		public @ResponseBody Object dashboardControlThroughPut(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlThroughPut starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlThroughPut?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlThroughPut ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-cost-analysis-over-time
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-cost-analysis-over-time")
		public @ResponseBody Object dashboardControlCostAnalysisOverTime(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlCostAnalysisOverTime starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlCostAnalysisOverTime?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlCostAnalysisOverTime ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-production-variance
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-production-variance")
		public @ResponseBody Object dashboardControlProductionVariance(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlProductionVariance starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlProductionVariance?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlProductionVariance ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-top-five-machine-production
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-top-five-machine-production")
		public @ResponseBody Object dashboardControlTopFiveMachineProduction(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlTopFiveMachineProduction starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlTopFiveMachineProduction?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlTopFiveMachineProduction ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-production-efficiency-Runtime-downtime
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-production-efficiency-Runtime-downtime")
		public @ResponseBody Object dashboardControlProductionEfficiencyRuntimeDowntime(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlProductionEfficiencyRuntimeDowntime starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlProductionEfficiencyRuntimeDowntime?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlProductionEfficiencyRuntimeDowntime ends" + resp);

			return resp;
		}
		
		
		//dashboard-control-hours-to-product-completion-by-product
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-hours-to-product-completion-by-product")
		public @ResponseBody Object dashboardControlHoursToProductCompletionByProduct(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlHoursToProductCompletionByProduct starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlHoursToProductCompletionByProduct?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlHoursToProductCompletionByProduct ends" + resp);

			return resp;
		}
		
		/*
		//dashboard-control-defect-analysis-type-rate
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-control-defect-analysis-type-rate")
		public @ResponseBody Object dashboardControlDefectAnalysisTypeRate(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardControlDefectAnalysisTypeRate starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardControlDefectAnalysisTypeRate?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardControlDefectAnalysisTypeRate ends" + resp);

			return resp;
		}
		*/
		
		//=========================TEAM=====================================
		//dashboard-team-safety-calender
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-safety-calender")
		public @ResponseBody Object dashboardTeamSafetyCalender(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamSafetyCalender starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamSafetyCalender?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamSafetyCalender ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-quality-calender
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-quality-calender")
		public @ResponseBody Object dashboardTeamQualityCalender(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamQualityCalender starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamQualityCalender?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamQualityCalender ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-delivery-calender
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-delivery-calender")
		public @ResponseBody Object dashboardTeamDeliveryCalender(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamDeliveryCalender starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamDeliveryCalender?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamDeliveryCalender ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-cost-calender
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-cost-calender")
		public @ResponseBody Object dashboardTeamCostCalender(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamCostCalender starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamCostCalender?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamCostCalender ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-notes
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-notes")
		public @ResponseBody Object dashboardTeamNotes(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamNotes starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamNotes?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamNotes ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-safety-summary-mtd-count
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-safety-summary-mtd-count")
		public @ResponseBody Object dashboardTeamSafetySmryMTDCount(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamSafetySmryMTDCount starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamSafetySmryMTDCount?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamSafetySmryMTDCount ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-quality-trend
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-quality-trend")
		public @ResponseBody Object dashboardTeamQualityTrend(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamQualityTrend starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamQualityTrend?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamQualityTrend ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-weekly-scheduled-attainment
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-weekly-scheduled-attainment")
		public @ResponseBody Object dashboardTeamWeeklyScheduledAttainment(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamWeeklyScheduledAttainment starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamWeeklyScheduledAttainment?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamWeeklyScheduledAttainment ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-cost-trend
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-cost-trend")
		public @ResponseBody Object dashboardTeamCostTrend(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamCostTrend starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamCostTrend?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamCostTrend ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-top-safety-concerns
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-top-safety-concerns")
		public @ResponseBody Object dashboardTeamTopSafetyConcerns(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamTopSafetyConcerns starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamTopSafetyConcerns?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamTopSafetyConcerns ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-top-defect-categories
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-top-defect-categories")
		public @ResponseBody Object dashboardTeamTopDefectCategories(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamTopDefectCategories starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamTopDefectCategories?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamTopDefectCategories ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-daily-scheduled-attainment
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-daily-scheduled-attainment")
		public @ResponseBody Object dashboardTeamDailyScheduledAttainment(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamDailyScheduledAttainment starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamDailyScheduledAttainment?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamDailyScheduledAttainment ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-top-lines
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-top-lines")
		public @ResponseBody Object dashboardTeamTopLines(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamTopLines starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamTopLines?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamTopLines ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-safety-corrective-action
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-safety-corrective-action")
		public @ResponseBody Object dashboardTeamSafetyCorrectiveAction(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamSafetyCorrectiveAction starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamSafetyCorrectiveAction?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamSafetyCorrectiveAction ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-quality-corrective-action
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-quality-corrective-action")
		public @ResponseBody Object dashboardTeamQualityCorrectiveAction(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamQualityCorrectiveAction starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamQualityCorrectiveAction?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamQualityCorrectiveAction ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-delivery-corrective-action
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-delivery-corrective-action")
		public @ResponseBody Object dashboardTeamDeliveryAction(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamDeliveryAction starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamDeliveryAction?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamDeliveryAction ends" + resp);

			return resp;
		}
		
		
		//dashboard-team-cost-corrective-action
		@SuppressWarnings("unchecked")
		@GetMapping("dashboard-team-cost-corrective-action")
		public @ResponseBody Object dashboardTeamCostCorrectiveAction(HttpSession session, @RequestParam String fromDate,
				@RequestParam String toDate, @RequestParam String location, @RequestParam String org,
				@RequestParam String orgDiv) {

			logger.info("Method :dashboardTeamCostCorrectiveAction starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
		
			try {
				resp = restClient
						.getForObject(
								env.getProduction() + "dashboardTeamCostCorrectiveAction?fromDate=" + fromDate + "&toDate=" + toDate
										+ "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//System.out.println(env.getProduction());

			logger.info("Method :dashboardTeamCostCorrectiveAction ends" + resp);

			return resp;
		}
		

}
