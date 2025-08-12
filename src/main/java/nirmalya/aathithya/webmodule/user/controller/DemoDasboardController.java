package nirmalya.aathithya.webmodule.user.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;

@Controller
@RequestMapping(value = "his")
public class DemoDasboardController {
	Logger logger = LoggerFactory.getLogger(DemoDasboardController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping("accountdasboard")
	public String account(Model model, HttpSession session) {

		logger.info("Method : account starts");

		
		logger.info("Method : account ends");

		return "dasboard/account-dashboard";

	}
	@GetMapping("qc-dashboard")
	public String qc(Model model, HttpSession session) {
		
		logger.info("Method : qc starts");
		
		
		logger.info("Method : qc ends");
		
		return "dasboard/qc-dashboard";
		
	}
	
	@GetMapping("requisition-dashboard")
	public String requisition(Model model, HttpSession session) {
		
		logger.info("Method : requisition starts");
		
		
		logger.info("Method : requisition ends");
		
		return "dasboard/requisition-dashboard";
		
	}
	
	@GetMapping("retail-dashboard")
	public String retail(Model model, HttpSession session) {
		
		logger.info("Method : retail starts");
		
		
		logger.info("Method : retail ends");
		
		return "dasboard/retail-dashboard.html";
		
	}
	
	@GetMapping("sales-dashboard")
	public String sales(Model model, HttpSession session) {
		
		logger.info("Method : sales starts");
		
		
		logger.info("Method : sales ends");
		
		return "dasboard/sales-dashboard.html";
		
	}
	
	@GetMapping("scm-dashboard")
	public String scm(Model model, HttpSession session) {
		
		logger.info("Method : scm starts");
		
		
		logger.info("Method : scm ends");
		
		return "dasboard/scm-dashboard.html";
		
	}
	
	@GetMapping("survey_dashboard")
	public String survey(Model model, HttpSession session) {
		
		logger.info("Method : survey starts");
		
		
		logger.info("Method : survey ends");
		
		return "dasboard/survey-dashboard.html";
		
	}
	
	@GetMapping("wms-dashboard")
	public String wms(Model model, HttpSession session) {
		
		logger.info("Method : wms starts");
		
		
		logger.info("Method : wms ends");
		
		return "dasboard/wms-dashboard.html";
		
	}
	
	@GetMapping("manufacturing-dashboard")
	public String manufacturing(Model model, HttpSession session) {
		
		logger.info("Method : manufacturing starts");
		
		
		logger.info("Method : manufacturing ends");
		
		return "dasboard/manufacturing-dashboard.html";
		
	}
	
	@GetMapping("pharmaceutical-dashboard")
	public String pharmaceutical(Model model, HttpSession session) {
		
		logger.info("Method : pharmaceutical starts");
		
		
		logger.info("Method : pharmaceutical ends");
		
		return "dasboard/pharmaceutical-dashboard.html";
		
	}
	
	@GetMapping("marketing-dashboard")
	public String marketing(Model model, HttpSession session) {
		
		logger.info("Method : marketing starts");
		
		
		logger.info("Method : marketing ends");
		
		return "dasboard/marketing-dashboard.html";
		
	}
	
	@GetMapping("analysis-dashboard")
	public String analysis(Model model, HttpSession session) {
		
		logger.info("Method : analysis starts");
		
		
		logger.info("Method : analysis ends");
		
		return "dasboard/analysis-dashboard.html";
		
	}
	@GetMapping("asset-dashboard")
	public String asset(Model model, HttpSession session) {
		
		logger.info("Method : asset starts");
		
		
		logger.info("Method : asset ends");
		
		return "dasboard/asset-management-dashboard.html";
		
	}
	
	@GetMapping("ceo-dashboard")
	public String ceo(Model model, HttpSession session) {
		
		logger.info("Method : ceo starts");
		
		
		logger.info("Method : ceo ends");
		
		return "dasboard/ceo-dashboard.html";
		
	}
	
	@GetMapping("cfo-dashboard")
	public String cfo(Model model, HttpSession session) {
		
		logger.info("Method : cfo starts");
		
		
		logger.info("Method : cfo ends");
		
		return "dasboard/cfo-dashboard.html";
		
	}
	
	@GetMapping("construction-dashboard")
	public String construction(Model model, HttpSession session) {
		
		logger.info("Method : construction starts");
		
		
		logger.info("Method : construction ends");
		
		return "dasboard/construction-dashboard.html";
		
	}
	@GetMapping("customer-dashboard")
	public String customer(Model model, HttpSession session) {
		
		logger.info("Method : customer starts");
		
		
		logger.info("Method : customer ends");
		
		return "dasboard/customer-insights-dashboard.html";
		
	}
	
	@GetMapping("eam-dashboard")
	public String eam(Model model, HttpSession session) {
		
		logger.info("Method : eam starts");
		
		
		logger.info("Method : eam ends");
		
		return "dasboard/eam-dashboard.html";
		
	}
	
	@GetMapping("ehs-dashboard")
	public String ehs(Model model, HttpSession session) {
		
		logger.info("Method : ehs starts");
		
		
		logger.info("Method : ehs ends");
		
		return "dasboard/ehs-dashboard.html";
		
	}
	
	@GetMapping("executive-dashboard")
	public String executive(Model model, HttpSession session) {
		
		logger.info("Method : executive starts");
		
		
		logger.info("Method : executive ends");
		
		return "dasboard/executive-dashboard.html";
		
	}
	
	@GetMapping("finance-dashboard")
	public String finance(Model model, HttpSession session) {
		
		logger.info("Method : finance starts");
		
		
		logger.info("Method : finance ends");
		
		return "dasboard/finance-dashboard.html";
		
	}
	
	@GetMapping("fleet-dashboard")
	public String fleet(Model model, HttpSession session) {
		
		logger.info("Method : fleet starts");
		
		
		logger.info("Method : fleet ends");
		
		return "dasboard/fleet-dashboard.html";
		
	}
	
	@GetMapping("healthcare-dashboard")
	public String healthcare(Model model, HttpSession session) {
		
		logger.info("Method : healthcare starts");
		
		
		logger.info("Method : healthcare ends");
		
		return "dasboard/healthcare-dashboard.html";
		
	}
	
	@GetMapping("helpdesk-dashboard")
	public String helpdesk(Model model, HttpSession session) {
		
		logger.info("Method : helpdesk starts");
		
		
		logger.info("Method : helpdesk ends");
		
		return "dasboard/helpdesk-dashboard.html";
		
	}
	
	@GetMapping("hospitality-dashboard")
	public String hospitality(Model model, HttpSession session) {
		
		logger.info("Method : hospitality starts");
		
		
		logger.info("Method : hospitality ends");
		
		return "dasboard/hospitality-dashboard.html";
		
	}
	
	@GetMapping("hrms-dashboard")
	public String hrms(Model model, HttpSession session) {
		
		logger.info("Method : hrms starts");
		
		
		logger.info("Method : hrms ends");
		
		return "dasboard/hrms-dashboard.html";
		
	}
	
	@GetMapping("logistics-dashboard")
	public String logistics(Model model, HttpSession session) {
		
		logger.info("Method : logistics starts");
		
		
		logger.info("Method : logistics ends");
		
		return "dasboard/logistics-dashboard.html";
		
	}
	
	@GetMapping("maintainance-dashboard")
	public String maintainance(Model model, HttpSession session) {
		
		logger.info("Method : maintainance starts");
		
		
		logger.info("Method : maintainance ends");
		
		return "dasboard/maintainance-dashboard.html";
		
	}
	
	@GetMapping("procurement-dashboard")
	public String procurement(Model model, HttpSession session) {
		
		logger.info("Method : procurement starts");
		
		
		logger.info("Method : procurement ends");
		
		return "dasboard/procurement-dashboard.html";
		
	}
	
	@GetMapping("production-dasboard")
	public String production(Model model, HttpSession session) {
		
		logger.info("Method : production starts");
		
		
		logger.info("Method : production ends");
		
		return "dasboard/production-dasboard.html";
		
	}
	
	@GetMapping("property-dashboard")
	public String property(Model model, HttpSession session) {
		
		logger.info("Method : property starts");
		
		
		logger.info("Method : property ends");
		
		return "dasboard/property-dashboard.html";
		
	}
	
	@GetMapping("purchase-dashboard")
	public String purchase(Model model, HttpSession session) {
		
		logger.info("Method : purchase starts");
		
		
		logger.info("Method : purchase ends");
		
		return "dasboard/purchase-dashboard.html";
		
	}

}
