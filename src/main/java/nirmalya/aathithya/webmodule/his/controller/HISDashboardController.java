package nirmalya.aathithya.webmodule.his.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("his")
public class HISDashboardController {
	
	Logger logger = LoggerFactory.getLogger(HISDashboardController.class);
	

	@GetMapping(value = { "/view-dashboard" })
	public String viewDashboard() {
		logger.info("Method : viewDashboard starts");

		logger.info("Method : viewDashboard starts");
		return "account/manage-dashboard.html";
	}
}
