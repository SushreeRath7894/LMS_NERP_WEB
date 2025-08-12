package nirmalya.aathithya.webmodule.vendor.controller;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping(value = "vendor")
public class VendorSelfServiceController {
	Logger logger = LoggerFactory.getLogger(VendorSelfServiceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/self-dashboard")
	public String dashboardSelfService() {
		logger.info("Start of Method : dashboardSelfService");
		/**/
		logger.info("End of Method : dashboardSelfService");
		return "vendor/self-dashboard";
	}

}