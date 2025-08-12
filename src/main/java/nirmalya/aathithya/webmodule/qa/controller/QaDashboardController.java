package nirmalya.aathithya.webmodule.qa.controller;

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
import nirmalya.aathithya.webmodule.common.utils.FileUpload;

@Controller
@RequestMapping(value = { "production/" })
public class QaDashboardController {
Logger logger = LoggerFactory.getLogger(QaDashboardController.class);
	
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	QaDashboardController qaDashboardController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/qa-dashboard" })
	public String manageDashboard(Model model, HttpSession session) {
		logger.info("Method : manageDashboard starts");

		
		logger.info("Method : manageDashboard ends");
		return "qa/qa-dashboard";
	}
}
