package nirmalya.aathithya.webmodule.subscription.controller;

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
@RequestMapping(value = { "subscription/" })
public class CustomerOnboardingController {

	Logger logger = LoggerFactory.getLogger(CustomerOnboardingController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "customer-onboarding" })
	public String manageCustomerOnboarding(Model model, HttpSession session) {
		logger.info("Method : manageCustomerOnboarding starts");

		String userName = "";
		String org = "";
		String orgDiv = "";

		try {
			userName = (String) session.getAttribute("USER_NAME");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		logger.info("Method : manageCustomerOnboarding ends");
		return "subscription/customer-onboarding";
	}
}
