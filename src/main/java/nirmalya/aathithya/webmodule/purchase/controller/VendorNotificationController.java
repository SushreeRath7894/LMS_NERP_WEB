package nirmalya.aathithya.webmodule.purchase.controller;

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

@RequestMapping(value = { "vendor/" })
public class VendorNotificationController {
	Logger logger = LoggerFactory.getLogger(GrnReportController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "self-notification" })

	public String notificationReport(Model model, HttpSession session) {
		logger.info("Method :notificationReport starts");

		logger.info("Method : notificationReport ends");

		return "purchase/self-notification";
	}
}
