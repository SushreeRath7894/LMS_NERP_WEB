package nirmalya.aathithya.webmodule.account.controller;

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

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = { "account/" })
public class AccountDashboardController {
	Logger logger = LoggerFactory.getLogger(AccountDashboardController.class);

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	/**
	 * View Account Dashboard
	 * 
	 */
	
	@GetMapping("dashboard")
	public String AccDashboard(Model model, HttpSession session) {

		logger.info("Method : AccDashboard starts");
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String orgName = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getAccountUrl() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> Organization = Arrays.asList(dropDownModel);
			model.addAttribute("Organization", Organization);

			logger.info("Organization" + Organization);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getAccountUrl() + "getDivision?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDivisionList", orgDivisionList);

			logger.info("orgDivisionList" + orgDivisionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		logger.info("Method : AccDashboard end");
		return "account/manage-dashboard";
	}

	// ===========================================================================
	
	@GetMapping("dashboard-account-getAllCounts")
	public @ResponseBody Object getCount(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :getAllCounts starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restClient.getForObject(env.getAccountUrl() + "getAllCounts?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllCounts ends" + resp);

		return resp;
	}

	// ===============================================================================

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-account-getAllReport")
	public @ResponseBody Object getAllReport(@RequestParam String id, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :getAllReport starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restClient.getForObject(
					env.getAccountUrl() + "dashboard-account-getAllReport?orgName=" + org + "&orgDivision=" + orgDiv
							+ "&id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllReport ends" + resp);

		return resp;
	}

	// dashboard-current-working-capital
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-current-working-capital")
	public @ResponseBody Object currentWorkingCapital(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :currentWorkingCapital starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String url = env.getAccountUrl() + "dashboard-currentWorkingCapital?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :currentWorkingCapital ends" + resp);

		return resp;
	}

	// dashboard-financial-kpi-liquidity-ratios
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-financial-kpi-liquidity-ratios")
	public @ResponseBody Object liquidityRatioDataCount(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :liquidityRatioDataCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "liquidityRatioDataCount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :liquidityRatioDataCount ends" + resp);

		return resp;
	}

	// dashboard-working-ratios-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-working-ratios-count")
	public @ResponseBody Object dashboardWorkingRatiosCount(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardWorkingRatiosCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardWorkingRatiosCount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardWorkingRatiosCount ends" + resp);

		return resp;
	}

	// dashboard-risk-ratios-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-risk-ratios-count")
	public @ResponseBody Object dashboardRiskRatiosCount(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardRiskRatiosCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardRiskRatiosCount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardRiskRatiosCount ends" + resp);

		return resp;
	}

	// dashboard-profitability-ratio-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-profitability-ratio-count")
	public @ResponseBody Object dashboardProfitabilityRatioCount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardProfitabilityRatioCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardProfitabilityRatioCount?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardProfitabilityRatioCount ends" + resp);

		return resp;
	}

	// dashboard-balance-sheet-days-or-others-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balance-sheet-days-or-others-count")
	public @ResponseBody Object dashboardBalaceSheetDaysOrOthersCount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalaceSheetDaysOrOthersCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalaceSheetDaysOrOthersCount?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalaceSheetDaysOrOthersCount ends" + resp);

		return resp;
	}

	// dashboard-balance-sheet-payable-tab-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balance-sheet-payable-tab-count")
	public @ResponseBody Object dashboardBalaceSheetPayableTabCount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalaceSheetPayableTabCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalaceSheetPayableTabCount?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalaceSheetPayableTabCount ends" + resp);

		return resp;
	}

	// dashboard-balance-sheet-receivable-tab-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balance-sheet-receivable-tab-count")
	public @ResponseBody Object dashboardBalaceSheetReceivableTabCount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalaceSheetReceivableTabCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalaceSheetReceivableTabCount?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalaceSheetReceivableTabCount ends" + resp);

		return resp;
	}

	// dashboard-balance-sheet-fixed-assets-tab-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balance-sheet-fixed-assets-tab-count")
	public @ResponseBody Object dashboardBalaceSheetFixedAssetsTabCount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalaceSheetFixedAssetsTabCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalaceSheetFixedAssetsTabCount?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalaceSheetFixedAssetsTabCount ends" + resp);

		return resp;
	}

	// dashboard-balance-sheet-working-capital-tab-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balance-sheet-working-capital-tab-count")
	public @ResponseBody Object dashboardBalaceWorkingCapitalTabCount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalaceWorkingCapitalTabCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalaceWorkingCapitalTabCount?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalaceWorkingCapitalTabCount ends" + resp);

		return resp;
	}

	// dashboard-subscription-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-subscription-count")
	public @ResponseBody Object dashboardSubscriptionCount(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardSubscriptionCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardSubscriptionCount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardSubscriptionCount ends" + resp);

		return resp;
	}

	// dashboard-pl-outstanding-revenue-expenses-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-outstanding-revenue-expenses-count")
	public @ResponseBody Object dashboardOutstandingRevenueExpenseCount(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String thisYear,
			@RequestParam String lastYear, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardOutstandingRevenueExpenseCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restClient.getForObject(env.getAccountUrl() + "dashboardOutstandingRevenueExpenseCount?orgName="
					+ org + "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&thisYear="
					+ thisYear + "&lastYear=" + lastYear + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardOutstandingRevenueExpenseCount ends" + resp);

		return resp;
	}

	// dashboard-per-main-dso-dio-dpo-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-main-dso-dio-dpo-count")
	public @ResponseBody Object dashboardMainDsoDioDpoCount(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardMainDsoDioDpoCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardMainDsoDioDpoCount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardMainDsoDioDpoCount ends" + resp);

		return resp;
	}

	// dashboard-ratio-comparison-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-ratio-comparison-count")
	public @ResponseBody Object dashboardRatioComparisionCount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardRatioComparisionCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardRatioComparisionCount?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardRatioComparisionCount ends" + resp);

		return resp;
	}

	// dashboard-current-budget-varience
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-current-budget-varience")
	public @ResponseBody Object currentBudgetVarience(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :currentBudgetVarience starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboard-currentBudgetVarience?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :currentBudgetVarience ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-vendor-payment-errorrate")
	public @ResponseBody Object vendorpaymenterrorrate(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :vendorpaymenterrorrate starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboard-vendorpaymenterrorrate?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :vendorpaymenterrorrate ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cashmanagementWorkingCap")
	public @ResponseBody Object cashmanagementWorkingCap(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :cashmanagementWorkingCap starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboard-cashmanagementWorkingCap?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cashmanagementWorkingCap ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cashbalance")
	public @ResponseBody Object cashbalance(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :vendorpaymenterrorrate starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboard-cashbalance?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cashbalance ends" + resp);

		return resp;
	}

	// dashboard-cash-ratio-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cash-ratio-trend")
	public @ResponseBody Object cashRatioTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :cashRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "cashRatioTrend?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cashRatioTrend ends" + resp);

		return resp;
	}

	// dashboard-quick-ratio-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-quick-ratio-trend")
	public @ResponseBody Object quickRatioTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :quickRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "quickRatioTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :quickRatioTrend ends" + resp);

		return resp;
	}

	// dashboard-current-ratio-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-current-ratio-trend")
	public @ResponseBody Object currentRatioTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :currentRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "currentRatioTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :currentRatioTrend ends" + resp);

		return resp;
	}

	// dashboard-absolute-liquid-ratio-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-absolute-liquid-ratio-trend")
	public @ResponseBody Object absoluteLiquidRatioTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :absoluteLiquidRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "absoluteLiquidRatioTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :absoluteLiquidRatioTrend ends" + resp);

		return resp;
	}

	// dashboard-working-receivable-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-working-receivable-trend")
	public @ResponseBody Object workingReceivableRatioTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :workingReceivableRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "workingReceivableRatioTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :workingReceivableRatioTrend ends" + resp);

		return resp;
	}

	// dashboard-working-cash-conversion-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-working-cash-conversion-trend")
	public @ResponseBody Object workingCashConversionRatioTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :workingCashConversionRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "workingCashConversionRatioTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :workingCashConversionRatioTrend ends" + resp);

		return resp;
	}

	// dashboard-working-debators-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-working-debators-trend")
	public @ResponseBody Object dashboardWorkingDebatorsRatioTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardWorkingDebatorsRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardWorkingDebatorsRatioTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardWorkingDebatorsRatioTrend ends" + resp);

		return resp;
	}

	// dashboard-working-creditors-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-working-creditors-trend")
	public @ResponseBody Object dashboardCreditorsRatioTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardCreditorsRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardCreditorsRatioTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardCreditorsRatioTrend ends" + resp);

		return resp;
	}

	///////////////////////////////////////////////////////////
	// dashboard-operating-leverage-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-operating-leverage-trend")
	public @ResponseBody Object dashboardOperatingLeverageTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardOperatingLeverageTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardOperatingLeverageTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardOperatingLeverageTrend ends" + resp);

		return resp;
	}

	// dashboard-financial-leverage-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-financial-leverage-trend")
	public @ResponseBody Object dashboardFinancialLeverageTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardFinancialLeverageTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardFinancialLeverageTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :dashboardFinancialLeverageTrend ends" + resp);

		return resp;
	}

	// dashboard-dept-to-equity-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-dept-to-equity-trend")
	public @ResponseBody Object dashboardDebtToEquityTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardDebtToEquityTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardDebtToEquityTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardDebtToEquityTrend ends" + resp);

		return resp;
	}

	// dashboard-interest-coverage-ratio-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-interest-coverage-ratio-trend")
	public @ResponseBody Object dashboardInterestCoverageTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardInterestCoverageTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardInterestCoverageTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardInterestCoverageTrend ends" + resp);

		return resp;
	}

	//////////////////////////////////////////////////////
	// dashboard-earning-margin-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-earning-margin-trend")
	public @ResponseBody Object dashboardEarningMarginTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardEarningMarginTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardEarningMarginTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardEarningMarginTrend ends" + resp);

		return resp;
	}

	// dashboard-return-on-equity-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-return-on-equity-trend")
	public @ResponseBody Object dashboardReturnOnEquityTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardReturnOnEquityTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardReturnOnEquityTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardReturnOnEquityTrend ends" + resp);

		return resp;
	}

	///////////////////////////////////////////////////////
	// dashboard-return-on-investment-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-return-on-investment-trend")
	public @ResponseBody Object dashboardReturnOnInvestmentTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardInterestCoverageTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardReturnOnInvestmentTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :dashboardReturnOnInvestmentTrend ends" + resp);

		return resp;
	}

	///////////////////////////////////////////////////////
	// dashboard-earning-per-share-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-earning-per-share-trend")
	public @ResponseBody Object dashboardEarnignPerShareTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardEarnignPerShareTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardEarnignPerShareTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :dashboardEarnignPerShareTrend ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-over-all-cash-by-year
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-over-all-cash-by-year")
	public @ResponseBody Object dashboardBalanceSheetOverAllCashByYear(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetOverAllCashByYear starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetOverAllCashByYear?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetOverAllCashByYear ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-debt-ratios
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-debt-ratios")
	public @ResponseBody Object dashboardBalanceSheetDebtRatios(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetDebtRatios starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetDebtRatios?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetDebtRatios ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-receivable-payable-turnover
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-receivable-payable-turnover")
	public @ResponseBody Object dashboardBalanceSheetReceivablePayableTurnOver(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetReceivablePayableTurnOver starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetReceivablePayableTurnOver?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetReceivablePayableTurnOver ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-over-all-financial-statment
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-over-all-financial-statment")
	public @ResponseBody Object dashboardBalanceSheetOverAllFinancialStatment(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetOverAllFinancialStatment starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetOverAllFinancialStatment?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetOverAllFinancialStatment ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-data-table
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-data-table")
	public @ResponseBody Object dashboardBalanceSheetDataTable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetDataTable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetDataTable?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetDataTable ends" + resp);

		return resp;
	}

	// dashboard-overdue-invoice-details-table
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-overdue-invoice-details-table")
	public @ResponseBody Object dashboardBalanceSheetOverDueInvoiceDetailsTable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetOverDueInvoiceDetailsTable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetOverDueInvoiceDetailsTable?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetOverDueInvoiceDetailsTable ends" + resp);

		return resp;
	}

	// dashboard-upcoming-payment-details-table
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-upcoming-payment-details-table")
	public @ResponseBody Object dashboardBalanceSheetUpcomingPaymentDtlsTable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetUpcomingPaymentDtlsTable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetUpcomingPaymentDtlsTable?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetUpcomingPaymentDtlsTable ends" + resp);

		return resp;
	}

	// dashboard-overdue-invoice-details-receivable-table
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-overdue-invoice-details-receivable-table")
	public @ResponseBody Object dashboardBalanceSheetOverDueDtlsReceivableTable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetOverDueDtlsReceivableTable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetOverDueDtlsReceivableTable?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetOverDueDtlsReceivableTable ends" + resp);

		return resp;
	}

	// dashboard-upcoming-payment-dtls-receivable-table
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-upcoming-payment-dtls-receivable-table")
	public @ResponseBody Object dashboardBalanceSheetUpcomingPaymentDtlsReceivableTable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetUpcomingPaymentDtlsReceivableTable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetUpcomingPaymentDtlsReceivableTable?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetUpcomingPaymentDtlsReceivableTable ends" + resp);

		return resp;
	}

	// dashboard-assets-turnover-ratio-trend-table
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-assets-turnover-ratio-trend-table")
	public @ResponseBody Object dashboardBalanceSheetAssetsTurnOverRatioTrendTable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetAssetsTurnOverRatioTrendTable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetAssetsTurnOverRatioTrendTable?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetAssetsTurnOverRatioTrendTable ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-count-volume-vs-age
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-count-volume-vs-age")
	public @ResponseBody Object dashboardBalanceSheetCountVolumeVsAge(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetCountVolumeVsAge starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetCountVolumeVsAge?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetCountVolumeVsAge ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-payable-par-vs-discount
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-payable-par-vs-discount")
	public @ResponseBody Object dashboardBalanceSheetPayableParVsDiscount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetPayableParVsDiscount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetPayableParVsDiscount?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetPayableParVsDiscount ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-department-wise-outstanding
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-department-wise-outstanding")
	public @ResponseBody Object dashboardBalanceSheetDepartmentWiseOutstanding(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetDepartmentWiseOutstanding starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetDepartmentWiseOutstanding?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetDepartmentWiseOutstanding ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-top-vendor-by-due
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-top-vendor-by-due")
	public @ResponseBody Object dashboardBalanceSheetTopVendorWiseDue(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetTopVendorWiseDue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetTopVendorWiseDue?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetTopVendorWiseDue ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-upcoming-payment
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-upcoming-payment")
	public @ResponseBody Object dashboardBalanceSheetUpcomingPayment(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetUpcomingPayment starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetUpcomingPayment?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetUpcomingPayment ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-purchase-payable-vs-payable
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-purchase-payable-vs-payable")
	public @ResponseBody Object dashboardBalanceSheetPurchasePayableVsPayable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetPurchasePayableVsPayable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetPurchasePayableVsPayable?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetPurchasePayableVsPayable ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-expense-type-wise-outstanding
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-expense-type-wise-outstanding")
	public @ResponseBody Object dashboardBalanceSheetExpenseTypeWiseOutstanding(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetExpenseTypeWiseOutstanding starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetExpenseTypeWiseOutstanding?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetExpenseTypeWiseOutstanding ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-ytd-amount-paid
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-ytd-amount-paid")
	public @ResponseBody Object dashboardBalanceSheetYtdAmountPaid(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetYtdAmountPaid starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetYtdAmountPaid?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetYtdAmountPaid ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-avg-payment-age
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-avg-payment-age")
	public @ResponseBody Object dashboardBalanceSheetAvgPaymentAge(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetAvgPaymentAge starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetAvgPaymentAge?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetAvgPaymentAge ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-receivable-count-vs-age
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-receivable-count-vs-age")
	public @ResponseBody Object dashboardBalanceSheeRcvblePaymentAgeCount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheeRcvblePaymentAgeCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheeRcvblePaymentAgeCount?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheeRcvblePaymentAgeCount ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-invoice-category-breakdown
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-invoice-category-breakdown")
	public @ResponseBody Object dashboardBalanceSheetInvoiceCategoryBreakdown(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetInvoiceCategoryBreakdown starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetInvoiceCategoryBreakdown?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetInvoiceCategoryBreakdown ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-top-customer-by-amount-due
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-top-customer-by-amount-due")
	public @ResponseBody Object dashboardBalanceSheetTopCustomerByDue(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetTopCustomerByDue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetTopCustomerByDue?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetTopCustomerByDue ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-avg-age-payment-rcvble
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-avg-age-payment-rcvble")
	public @ResponseBody Object dashboardBalanceSheetAvgRecvblePaymentAge(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetAvgRecvblePaymentAge starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetAvgRecvblePaymentAge?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetAvgRecvblePaymentAge ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-upcoming-receivable
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-upcoming-receivable")
	public @ResponseBody Object dashboardBalanceSheetUpcomingReceivable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetUpcomingReceivable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetUpcomingReceivable?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetUpcomingReceivable ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-sales-account-receivable-vs-time
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-sales-account-receivable-vs-time")
	public @ResponseBody Object dashboardBalanceSheetAccountReceivableVstime(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetAccountReceivableVstime starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetAccountReceivableVstime?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetAccountReceivableVstime ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-fixed-assets-balance-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-fixed-assets-balance-trend")
	public @ResponseBody Object dashboardBalanceSheetFixedAssetsTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetFixedAssetsTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetFixedAssetsTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetFixedAssetsTrend ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-assets-turn-over-ratio-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-assets-turn-over-ratio-trend")
	public @ResponseBody Object dashboardBalanceSheetAssetsTurnOverRatioTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetAssetsTurnOverRatioTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetAssetsTurnOverRatioTrend?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetAssetsTurnOverRatioTrend ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-fixed-assets-movement-by-month
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-fixed-assets-movement-by-month")
	public @ResponseBody Object dashboardBalanceSheetFixedAssetsMovementByMonth(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetFixedAssetsMovementByMonth starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetFixedAssetsMovementByMonth?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :dashboardBalanceSheetFixedAssetsMovementByMonth ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-current-ratio-mom-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-current-ratio-mom-trend")
	public @ResponseBody Object dashboardBalanceSheetCurrRatioMomTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetCurrRatioMomTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetCurrRatioMomTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetCurrRatioMomTrend ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-net-working-capital-by-month
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-net-working-capital-by-month")
	public @ResponseBody Object dashboardBalanceSheetNetWorkingCapByMonth(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetNetWorkingCapByMonth starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetNetWorkingCapByMonth?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetNetWorkingCapByMonth ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-quick-ratio-mom-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-quick-ratio-mom-trend")
	public @ResponseBody Object dashboardBalanceSheetQuickRatioMomTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetQuickRatioMomTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetAssetsVsLiabilities?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetQuickRatioMomTrend ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-asset-vs-liabilities
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-asset-vs-liabilities")
	public @ResponseBody Object dashboardBalanceSheetAssetsVsLiabilities(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetAssetsVsLiabilities starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetAssetsVsLiabilities?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetAssetsVsLiabilities ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-inventory-turn-over-ratio-mom-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-inventory-turn-over-ratio-mom-trend")
	public @ResponseBody Object dashboardBalanceSheetInvntTurnOvrRatioMomTrnd(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetInvntTurnOvrRatioMomTrnd starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetInvntTurnOvrRatioMomTrnd?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetInvntTurnOvrRatioMomTrnd ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-base-line-vs-comparision-table
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-base-line-vs-comparision-table")
	public @ResponseBody Object dashboardBalanceSheetBaseLineVsComparisionTbl(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetBaseLineVsComparisionTbl starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetBaseLineVsComparisionTbl?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetBaseLineVsComparisionTbl ends" + resp);

		return resp;
	}

	// dashboard-balancesheet-base-line-vs-comparision-graph
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-balancesheet-base-line-vs-comparision-graph")
	public @ResponseBody Object dashboardBalanceSheetBaseLineVsComparisionGraph(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardBalanceSheetBaseLineVsComparisionGraph starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardBalanceSheetBaseLineVsComparisionGraph?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardBalanceSheetBaseLineVsComparisionGraph ends" + resp);

		return resp;
	}

//dashboard-monthly-recurring-revenue
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-monthly-recurring-revenue")
	public @ResponseBody Object dashboardMonthlyRecurringRevenue(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardMonthlyRecurringRevenue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardMonthlyRecurringRevenue?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardMonthlyRecurringRevenue ends" + resp);

		return resp;
	}

//dashboard-top-cust-by-monthly-recur-revenue
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-top-cust-by-monthly-recur-revenue")
	public @ResponseBody Object dashboardTopCustByMonthlyRecurRevenue(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardTopCustByMonthlyRecurRevenue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardTopCustByMonthlyRecurRevenue?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardTopCustByMonthlyRecurRevenue ends" + resp);

		return resp;
	}

	// dashboard-top-cust-by-revenue
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-top-cust-by-revenue")
	public @ResponseBody Object dashboardTopCustCustByRevenue(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardTopCustCustByRevenue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardTopCustCustByRevenue?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardTopCustCustByRevenue ends" + resp);

		return resp;
	}

//dashboard-top-compny-monthly-recur-revenue
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-top-compny-monthly-recur-revenue")
	public @ResponseBody Object dashboardTopCompanyMonthlyRecurRevenue(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardTopCompanyMonthlyRecurRevenue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardTopCompanyMonthlyRecurRevenue?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardTopCompanyMonthlyRecurRevenue ends" + resp);

		return resp;
	}

//dashboard-cust-account-status-breakdown
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cust-account-status-breakdown")
	public @ResponseBody Object dashboardCustAccountStatusBreakdown(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardCustAccountStatusBreakdown starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardCustAccountStatusBreakdown?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardCustAccountStatusBreakdown ends" + resp);

		return resp;
	}

	// dashboard-payment-month-breakdown
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-payment-month-breakdown")
	public @ResponseBody Object dashboardPaymentMonthBreakdown(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPaymentMonthBreakdown starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPaymentMonthBreakdown?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPaymentMonthBreakdown ends" + resp);

		return resp;
	}

//dashboard-cust-subscription-by-status
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cust-subscription-by-status")
	public @ResponseBody Object dashboardCustSubscriptionByStatus(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardCustSubscriptionByStatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardCustSubscriptionByStatus?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardCustSubscriptionByStatus ends" + resp);

		return resp;
	}

//dashboard-subscriptn-by-plan-status
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-subscriptn-by-plan-status")
	public @ResponseBody Object dashboardSubscriptionByPlanStatus(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardSubscriptionByPlanStatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardSubscriptionByPlanStatus?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardSubscriptionByPlanStatus ends" + resp);

		return resp;
	}

//dashboard-account-receivble-by-pay-target
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-account-receivble-by-pay-target")
	public @ResponseBody Object dashboardAccountReceivableByPayTarget(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardAccountReceivableByPayTarget starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardAccountReceivableByPayTarget?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardAccountReceivableByPayTarget ends" + resp);

		return resp;
	}

//dashboard-payments-by-month
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-payments-by-month")
	public @ResponseBody Object dashboardPaymentByMonth(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPaymentByMonth starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPaymentByMonth?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPaymentByMonth ends" + resp);

		return resp;
	}

//dashboard-transaction-count-by-month-type
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-transaction-count-by-month-type")
	public @ResponseBody Object dashboardTransactionCountByMonthType(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardTransactionCountByMonthType starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardTransactionCountByMonthType?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardTransactionCountByMonthType ends" + resp);

		return resp;
	}

//dashboard-monthly-revenue-trend-analysis
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-monthly-revenue-trend-analysis")
	public @ResponseBody Object dashboardMonthlyRevenueTrendAnalysis(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardMonthlyRevenueTrendAnalysis starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardMonthlyRevenueTrendAnalysis?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardMonthlyRevenueTrendAnalysis ends" + resp);

		return resp;
	}

//dashboard-annual-run-rate-trend-analysis
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-annual-run-rate-trend-analysis")
	public @ResponseBody Object dashboardAnnualRunRateTrendAnalysis(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardAnnualRunRateTrendAnalysis starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardAnnualRunRateTrendAnalysis?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardAnnualRunRateTrendAnalysis ends" + resp);

		return resp;
	}

//dashboard-monthly-recurring-revenue-churn
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-monthly-recurring-revenue-churn")
	public @ResponseBody Object dashboardMonthlyRecurringRevenueChurn(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardMonthlyRecurringRevenueChurn starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardMonthlyRecurringRevenueChurn?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardMonthlyRecurringRevenueChurn ends" + resp);

		return resp;
	}

//dashboard-customer-churn-rate-trend-analysis
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-customer-churn-rate-trend-analysis")
	public @ResponseBody Object dashboardCustomerChurnRateTrendAnalysis(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardCustomerChurnRateTrendAnalysis starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardCustomerChurnRateTrendAnalysis?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardCustomerChurnRateTrendAnalysis ends" + resp);

		return resp;
	}

//dashboard-avg-revenue-per-account
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-avg-revenue-per-account")
	public @ResponseBody Object dashboardAvgRevenuePerAccount(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardAvgRevenuePerAccount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardAvgRevenuePerAccount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardAvgRevenuePerAccount ends" + resp);

		return resp;
	}

	// dashboard-invoice-summary-dtls
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-invoice-summary-dtls")
	public @ResponseBody Object dashboardInvoiceSummaryDetails(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardInvoiceSummaryDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardInvoiceSummaryDetails?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardInvoiceSummaryDetails ends" + resp);

		return resp;
	}

//dashboard-invoice-summary-dtls-tbl
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-invoice-summary-dtls-tbl")
	public @ResponseBody Object dashboardInvoiceSummaryDetailsTbl(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardInvoiceSummaryDetailsTbl starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardInvoiceSummaryDetailsTbl?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardInvoiceSummaryDetailsTbl ends" + resp);

		return resp;
	}

	// dashboard-pl-opex-mom-ytd
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-opex-mom-ytd")
	public @ResponseBody Object dashboardPlOpexMomYtd(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPlOpexMomYtd starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPlOpexMomYtd?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlOpexMomYtd ends" + resp);

		return resp;
	}

//dashboard-pl-payment-status
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-payment-status")
	public @ResponseBody Object dashboardPlPaymentStatus(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPlPaymentStatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPlPaymentStatus?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlPaymentStatus ends" + resp);

		return resp;
	}

//dashboard-pl-gross-profit-margin
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-gross-profit-margin")
	public @ResponseBody Object dashboardPlGrossProfitMargin(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPlGrossProfitMargin starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPlGrossProfitMargin?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlGrossProfitMargin ends" + resp);

		return resp;
	}

//dashboard-pl-net-profit-margin
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-net-profit-margin")
	public @ResponseBody Object dashboardPlNetProfitMargin(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate) {

		logger.info("Method :dashboardPlNetProfitMargin starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "dashboardPlNetProfitMargin?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlNetProfitMargin ends" + resp);

		return resp;
	}

//dashboard-pl-top-expenses-by-cat-prcnt
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-top-expenses-by-cat-prcnt")
	public @ResponseBody Object dashboardPlTopExpbyCatPrcnt(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPlTopExpbyCatPrcnt starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPlTopExpbyCatPrcnt?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlTopExpbyCatPrcnt ends" + resp);

		return resp;
	}

	// dashboard-pl-revenue-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-revenue-trend")
	public @ResponseBody Object dashboardPlRevTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPlRevTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPlRevTrend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlRevTrend ends" + resp);

		return resp;
	}

	// dashboard-pl-customer-growth
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-customer-growth")
	public @ResponseBody Object dashboardPlCustGrowth(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPlCustGrowth starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPlCustGrowth?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlCustGrowth ends" + resp);

		return resp;
	}

	// dashboard-pl-top-revenue-generating-countries
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-top-revenue-generating-countries")
	public @ResponseBody Object dashboardPlToRevnGenerateCont(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPlToRevnGenerateCont starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPlToRevnGenerateCont?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlToRevnGenerateCont ends" + resp);

		return resp;
	}

//dashboard-pl-income-and-expenses-by-cat-prcnt
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-income-and-expenses-by-cat-prcnt")
	public @ResponseBody Object dashboardPlIncmExpnByCatPrcnt(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPlIncmExpnByCatPrcnt starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardPlIncmExpnByCatPrcnt?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlIncmExpnByCatPrcnt ends" + resp);

		return resp;
	}

//dashboard-pl-profit-and-loss-report
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-profit-and-loss-report")
	public @ResponseBody Object dashboardPlProfitAndLossReport(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPlProfitAndLossReport starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPlProfitAndLossReport?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPlProfitAndLossReport ends" + resp);

		return resp;
	}

//dashboard-per-return-on-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-return-on-assets")
	public @ResponseBody Object dashboardPerReturnOnAssets(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPerReturnOnAssets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerReturnOnAssets?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerReturnOnAssets ends" + resp);

		return resp;
	}

//dashboard-per-working-capital-ratio
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-working-capital-ratio")
	public @ResponseBody Object dashboardPerWorkingCaptlRatio(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerWorkingCaptlRatio starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerWorkingCaptlRatio?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerWorkingCaptlRatio ends" + resp);

		return resp;
	}

	// dashboard-per-return-on-equity
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-return-on-equity")
	public @ResponseBody Object dashboardPerReturnOnEquity(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPerReturnOnEquity starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerReturnOnEquity?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerReturnOnEquity ends" + resp);

		return resp;
	}

//dashboard-per-debt-equity-ratio
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-debt-equity-ratio")
	public @ResponseBody Object dashboardPerDebtEquityRatio(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPerDebtEquityRatio starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerDebtEquityRatio?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerDebtEquityRatio ends" + resp);

		return resp;
	}

//dashboard-per-balancesheet-count-list
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-balancesheet-count-list")
	public @ResponseBody Object dashboardPerBalanceSheetCountList(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerBalanceSheetCountList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerBalanceSheetCountList?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerBalanceSheetCountList ends" + resp);

		return resp;
	}

//dashboard-per-profit-loss-summry
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-profit-loss-summry")
	public @ResponseBody Object dashboardPerProfitLossSmry(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPerProfitLossSmry starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerProfitLossSmry?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerProfitLossSmry ends" + resp);

		return resp;
	}

//dashboard-per-net-gross-working-capital
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-net-gross-working-capital")
	public @ResponseBody Object dashboardPerNetGrossWorkingCapital(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerNetGrossWorkingCapital starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerNetGrossWorkingCapital?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerNetGrossWorkingCapital ends" + resp);

		return resp;
	}

//dashboard-per-acc-rec-acc-pay-turnover
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-acc-rec-acc-pay-turnover")
	public @ResponseBody Object dashboardPerAccReceivablePayableTurnOver(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerAccReceivablePayableTurnOver starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerAccReceivablePayableTurnOver?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerAccReceivablePayableTurnOver ends" + resp);

		return resp;
	}

//dashboard-per-inventory-dtls
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-inventory-dtls")
	public @ResponseBody Object dashboardPerInvoiceDetails(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :dashboardPerInvoiceDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerInvoiceDetails?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerInvoiceDetails ends" + resp);

		return resp;
	}

//dashboard-per-acc-paybl-by-pay-target
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-acc-paybl-by-pay-target")
	public @ResponseBody Object dashboardPerAccPayableByPayTarget(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerAccPayableByPayTarget starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardPerAccPayableByPayTarget?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerAccPayableByPayTarget ends" + resp);

		return resp;
	}

//dashboard-per-expenses-breakdown-list-count
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-expenses-breakdown-list-count")
	public @ResponseBody Object dashboardPerExpensesListCountList(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerExpensesListCountList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerExpensesListCountList?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerExpensesListCountList ends" + resp);

		return resp;
	}

//dashboard-per-invoice-due-dtls-by-cust-tbl
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-invoice-due-dtls-by-cust-tbl")
	public @ResponseBody Object dashboardPerInvoiceDueDtlsByCusTable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerInvoiceDueDtlsByCusTable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardPerInvoiceDueDtlsByCusTable?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerInvoiceDueDtlsByCusTable ends" + resp);

		return resp;
	}

//dashboard-per-profit-loss-summary-tbl
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-profit-loss-summary-tbl")
	public @ResponseBody Object dashboardPerProfitLossSummryTable(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerProfitLossSummryTable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "dashboardPerProfitLossSummryTable?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerProfitLossSummryTable ends" + resp);

		return resp;
	}

//dashboard-per-executive-summary-tbl
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-per-executive-summary-tbl")
	public @ResponseBody Object dashboardPerExecutiveSummryTbl(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardPerExecutiveSummryTbl starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardPerExecutiveSummryTbl?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardPerExecutiveSummryTbl ends" + resp);

		return resp;
	}

	////////////////////////////////////////////////////////////
	// dashboard-ratio-return-on-equity-trend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-ratio-return-on-equity-trend")
	public @ResponseBody Object dashboardRatioRetunOnEquityTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardRatioRetunOnEquityTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardRatioRetunOnEquityTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardRatioRetunOnEquityTrend ends" + resp);

		return resp;
	}

	///////////////////////////////////////////////////////////
	// dashboard-ratio-earning-margin-trend

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-ratio-earning-margin-trend")
	public @ResponseBody Object dashboardRatioEarnignMarginTrend(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :dashboardRatioEarnignMarginTrend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboardRatioEarnignMarginTrend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dashboardRatioEarnignMarginTrend ends" + resp);

		return resp;
	}

	/////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cminventory")
	public @ResponseBody Object cminventory(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :cminventory starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboard-cminventory?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cminventory ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actualForcastIndicators")
	public @ResponseBody Object debtEquityRatio(HttpSession session) {

		logger.info("Method :actualForcastIndicators starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-actualForcastIndicators?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : actualForcastIndicators ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actualForcastHighCharts")
	public @ResponseBody Object actualForcastHighCharts(HttpSession session) {

		logger.info("Method :actualForcastHighCharts starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-actualForcastHighCharts?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : actualForcastHighCharts ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-profit-loss-cost-statement")
	public @ResponseBody Object profitLossSheet(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :profitLossCostStatement starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "profitLossCostStatement?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :profitLossCostStatement ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-revenuecogsRate")
	public @ResponseBody Object revenuecogsRate(HttpSession session) {

		logger.info("Method :revenuecogsRate starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-revenuecogsRate?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :revenuecogsRate ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-opexSheet")
	public @ResponseBody Object opexSheet(HttpSession session) {

		logger.info("Method :opexSheet starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "dashboard-opexSheetSheet?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :opexSheet ends" + resp);

		return resp;
	}

	// dashboard-earningBeforeTaxes

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-earning-before-interest-and-taxes-list")
	public @ResponseBody Object earningBeforeInterestTaxes(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :earningBeforeInterestTaxes starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "earningBeforeInterestTaxes?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :earningBeforeInterestTaxes ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-opexYearWise")
	public @ResponseBody Object opexYearWise(HttpSession session) {

		logger.info("Method :opexYearWise starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "opexYearWise?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :opexYearWise ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-gross-profit-margin-prcnt")
	public @ResponseBody Object grossProfitLossMarginPrcnt(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :grossProfitLossMarginPrcnt starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "grossProfitLossMarginPrcnt?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :grossProfitLossMarginPrcnt ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-opex-ratio-prcnt")
	public @ResponseBody Object grossOpexRatioPrcnt(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :grossOpexRatioPrcnt starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "grossOpexRatioPrcnt?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :grossOpexRatioPrcnt ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-operating-profit-prcnt")
	public @ResponseBody Object operatingProfitPrcnt(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :operatingProfitPrcnt starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "operatingProfitPrcnt?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :operatingProfitPrcnt ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-opex-year-to-date")
	public @ResponseBody Object opexYearToDate(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		logger.info("Method :opexYearToDate starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "opexYearToDate?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :opexYearToDate ends" + resp);
		return resp;
	}

	// dashboard-pl-revenue-and-cogs-list
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-revenue-and-cogs-list")
	public @ResponseBody Object revenuecogslist(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		logger.info("Method :revenuecogslist starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "revenuecogslist?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :revenuecogslist ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-pl-net-profit-margin-prcnt")
	public @ResponseBody Object netProfitMarginPrcnt(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :netProfit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "netProfitMarginPrcnt?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :netProfitMarginPrcnt ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cash-management-payable-vs-receivable")
	public @ResponseBody Object cmPayableVsReceivable(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :cmPayableVsReceivable starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboard-cm-payable-vs-receivable?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cmPayableCount ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cash-management-payable-count")
	public @ResponseBody Object cmPayableCount(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :cmPayableCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "dashboard-cm-payable-count?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cmPayableCount ends" + resp);

		return resp;
	}

///////////////////////////////////////////////////////////////////start
	// dashboard-actual-forecast-revenue
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-revenue")
	public @ResponseBody Object actualforecastrevenue(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastrevenue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastrevenue?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastrevenue ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-cogs
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-cogs")
	public @ResponseBody Object actualforecastcogs(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastcogs starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastcogs?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :actualforecastcogs ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-costs
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-costs")
	public @ResponseBody Object actualforecastcosts(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastcosts starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastcosts?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastcosts ends" + resp);

		return resp;
	}

	// dashboard-actual-forecast-taxes
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-taxes")
	public @ResponseBody Object actualforecasttaxes(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecasttaxes starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecasttaxes?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecasttaxes ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-breakdown-costs
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-breakdown-costs")
	public @ResponseBody Object actualforecastbreakdownOfcosts(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :actualforecastbreakdownOfcosts starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastbreakdownOfcosts?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastbreakdownOfcosts ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-breakdown-Of-incomebudget
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-incomebudget")
	public @ResponseBody Object actualforecastincomebudget(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastincomebudget starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastincomebudget?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastincomebudget ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-breakdown-Of-expensesbudget
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-expensesbudget")
	public @ResponseBody Object actualforecastexpensesbudget(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastexpensesbudget starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String url = env.getAccountUrl() + "actualforecastexpensesbudget?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :actualforecastexpensesbudget ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-actualincome
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-actualincome")
	public @ResponseBody Object actualforecastactualincome(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastactualincome starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastactualincome?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastactualincome ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-actualexpenses
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-actualexpenses")
	public @ResponseBody Object actualforecastactualexpenses(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastactualexpenses starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastactualexpenses?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :actualforecastactualexpenses ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-budget-actual-table-income
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-budget-actual-table-income")
	public @ResponseBody Object actualforecastbudgetactualincome(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :actualforecastbudgetactualincome starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastbudgetactualincome?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastbudgetactualincome ends" + resp);

		return resp;
	}

	// dashboard-actual-forecast-budget-actual-table-expense
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-budget-actual-table-expense")
	public @ResponseBody Object actualforecastbudgetactualtableexpense(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :actualforecastbudgetactualtableexpense starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastbudgetactualexpense?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastbudgetactualtableexpense ends" + resp);

		return resp;
	}

	// dashboard-actual-forecast-revenues
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-revenues")
	public @ResponseBody Object actualforecastrevenues(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastrevenues starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastrevenues?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastrevenues ends" + resp);

		return resp;
	}

//dashboard-actual-forecast-finance-costs
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-finance-costs")
	public @ResponseBody Object actualforecastfinancecosts(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastfinancecosts starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastfinancecosts?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastfinancecosts ends" + resp);

		return resp;
	}

	// dashboard-actual-forecast-netprofit
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actual-forecast-netprofit")
	public @ResponseBody Object actualforecastnetprofit(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :actualforecastnetprofit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getAccountUrl() + "actualforecastnetprofit?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :actualforecastnetprofit ends" + resp);

		return resp;
	}

/*	@SuppressWarnings("unchecked")

	@GetMapping("dashboard-account-getAllCounts")
	public @ResponseBody Object getCount(HttpSession session) {

		logger.info("Method :getAllCounts starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "getAllCounts?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllCounts ends" + resp);

		return resp;
	}

	// ===============================================================================

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-account-getAllReport")
	public @ResponseBody Object getAllReport(HttpSession session, @RequestParam String id) {

		logger.info("Method :getAllReport starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-account-getAllReport?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllReport ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-currentWorkingCapital")
	public @ResponseBody Object currentWorkingCapital(HttpSession session) {

		logger.info("Method :currentWorkingCapital starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-currentWorkingCapital?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :currentWorkingCapital ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-currentBudgetVarience")
	public @ResponseBody Object currentBudgetVarience(HttpSession session) {

		logger.info("Method :currentBudgetVarience starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-currentBudgetVarience?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :currentBudgetVarience ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-vendorpaymenterrorrate")
	public @ResponseBody Object vendorpaymenterrorrate(HttpSession session) {

		logger.info("Method :vendorpaymenterrorrate starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-vendorpaymenterrorrate?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :vendorpaymenterrorrate ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cashmanagementWorkingCap")
	public @ResponseBody Object cashmanagementWorkingCap(HttpSession session) {

		logger.info("Method :cashmanagementWorkingCap starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-cashmanagementWorkingCap?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cashmanagementWorkingCap ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cashbalance")
	public @ResponseBody Object cashbalance(HttpSession session) {

		logger.info("Method :vendorpaymenterrorrate starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "dashboard-cashbalance?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cashbalance ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-cminventory")
	public @ResponseBody Object cminventory(HttpSession session) {

		logger.info("Method :cminventory starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "dashboard-cminventory?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cminventory ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-performanceBalanceSheet")
	public @ResponseBody Object performanceBalanceSheet(HttpSession session) {

		logger.info("Method :performanceBalanceSheet starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-performanceBalanceSheet?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :performanceBalanceSheet ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-returnonequity")
	public @ResponseBody Object returnonequity(HttpSession session) {

		logger.info("Method :returnonequity starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "dashboard-returnonequity?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : returnonequity ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actualForcastIndicators")
	public @ResponseBody Object debtEquityRatio(HttpSession session) {

		logger.info("Method :actualForcastIndicators starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-actualForcastIndicators?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : actualForcastIndicators ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-actualForcastHighCharts")
	public @ResponseBody Object actualForcastHighCharts(HttpSession session) {

		logger.info("Method :actualForcastHighCharts starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-actualForcastHighCharts?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : actualForcastHighCharts ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-profitLossSheet")
	public @ResponseBody Object profitLossSheet(HttpSession session) {

		logger.info("Method :profitLossSheet starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-profitLossSheet?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :profitLossSheet ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-revenuecogsRate")
	public @ResponseBody Object revenuecogsRate(HttpSession session) {

		logger.info("Method :revenuecogsRate starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "dashboard-revenuecogsRate?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :revenuecogsRate ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-opexSheet")
	public @ResponseBody Object opexSheet(HttpSession session) {

		logger.info("Method :opexSheet starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "dashboard-opexSheetSheet?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :opexSheet ends" + resp);

		return resp;
	}

	// dashboard-earningBeforeTaxes

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-earningBeforeTaxes")
	public @ResponseBody Object earningBeforeTaxes(HttpSession session) {

		logger.info("Method :earningBeforeTaxes starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "earningBeforeTaxes?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :earningBeforeTaxes ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-opexYearWise")
	public @ResponseBody Object opexYearWise(HttpSession session) {

		logger.info("Method :opexYearWise starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "opexYearWise?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :opexYearWise ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-grossProfitLoss")
	public @ResponseBody Object grossProfitLoss(HttpSession session) {

		logger.info("Method :grossProfitLoss starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "grossProfitLoss?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :grossProfitLoss ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-grossOpexRatio")
	public @ResponseBody Object grossOpexRatio(HttpSession session) {

		logger.info("Method :grossOpexRatio starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "grossOpexRatio?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :grossOpexRatio ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-operatingProfit")
	public @ResponseBody Object operatingProfit(HttpSession session) {

		logger.info("Method :operatingProfit starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "operatingProfit?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :operatingProfit ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-netProfit")
	public @ResponseBody Object netProfit(HttpSession session) {

		logger.info("Method :netProfit starts");
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

			resp = restClient.getForObject(
					env.getAccountUrl() + "netProfit?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :netProfit ends" + resp);

		return resp;
	}
	
	*/
}
