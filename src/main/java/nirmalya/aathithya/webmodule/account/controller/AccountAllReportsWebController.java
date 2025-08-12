package nirmalya.aathithya.webmodule.account.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = { "account" })
public class AccountAllReportsWebController {

	Logger logger = LoggerFactory.getLogger(AccountAllReportsWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "/reports" })
	public String RenderReportsPage(Model model, HttpSession session) {

		logger.info("Method : RenderReportsPage starts");
		try {
			DropDownModel[] costCenter = restTemplate.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			String orgName = "";
			String orgDivision = "";

			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] receivableLedgerList = restTemplate.getForObject(env.getAccountUrl()
					+ "/rest-getTdsReceivableLedgerList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> tdsReceivableLedgerList = Arrays.asList(receivableLedgerList);
			model.addAttribute("tdsReceivableLedgerList", tdsReceivableLedgerList);

			DropDownModel[] payableLedgerList = restTemplate.getForObject(env.getAccountUrl()
					+ "/rest-getTdsPayableLedgerList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> tdsPayableLedgerList = Arrays.asList(payableLedgerList);
			model.addAttribute("tdsPayableLedgerList", tdsPayableLedgerList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : RenderReportsPage ends");
		return "account/report-list";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("reports-get-details")
	public @ResponseBody Object AccountReportList(HttpSession session) {
		logger.info("Method :AccountReportList starts");

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
			resp = restTemplate.getForObject(
					env.getAccountUrl() + "get-AccountReportList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :AccountReportList ends");
		return resp;
	}

	@GetMapping("reports-load-report-fragment/{fragmentName}/{htmlFile}")
	public String loadReportFragment(@PathVariable String fragmentName, @PathVariable String htmlFile) {
		return "account/" + htmlFile + " :: " + fragmentName;
	}

}