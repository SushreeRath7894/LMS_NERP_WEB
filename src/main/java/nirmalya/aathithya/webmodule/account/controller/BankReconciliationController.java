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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "account")
public class BankReconciliationController {
	Logger logger = LoggerFactory.getLogger(BankReconciliationController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	//page return
	@GetMapping("/bank-reconciliation")
	public String viewPage(Model model, HttpSession session) {
		logger.info("Method : viewPage starts");
		
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] bankLedgerLis= restClient.getForObject(env.getAccountUrl() + "get-bank_ledger-list?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bankLedgerLis1 = Arrays.asList(bankLedgerLis);
			model.addAttribute("bankLedgerLis", bankLedgerLis1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewPage end");
		return "account/bank-reconciliation";
	}
	
	//bank-reconciliation-view
	@SuppressWarnings("unchecked")
	@GetMapping("/bank-reconciliation-view")
	public @ResponseBody Object bankReconciliationView(@RequestParam String fromDate,String toDate,String bank,String type ,HttpSession session) {
		logger.info("Method :bankReconciliationView starts");
		
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
			resp = restClient.getForObject(env.getAccountUrl() + "bank-reconciliation-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate+ "&toDate=" + toDate+ "&bank=" + bank+ "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :bankReconciliationView ends");
		return resp;
	}
	//bank-reconciliation-clearance-save
	@SuppressWarnings("unchecked")
	@GetMapping("/bank-reconciliation-clearance-save")
	public @ResponseBody Object clearanceSave(@RequestParam String vid,String chqClrDate,String remarks ,HttpSession session) {
		logger.info("Method :clearanceSave starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "rest-clearanceSave?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&vid=" + vid+ "&chqClrDate=" + chqClrDate+ "&remarks=" + remarks+"&userId="+userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :clearanceSave ends");
		return resp;
	}
}