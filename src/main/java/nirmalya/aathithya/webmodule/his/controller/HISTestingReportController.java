package nirmalya.aathithya.webmodule.his.controller;

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
	public class HISTestingReportController {
		Logger logger = LoggerFactory.getLogger(HISTestingReportController.class);

		@Autowired 
		RestTemplate restTemplate;

		@Autowired
		EnvironmentVaribles env;

		@GetMapping(value = { "/testing-report" })

		public String getHomePage(Model model, HttpSession session) {

			logger.info("Method : Testing Reports starts");
			
			logger.info("Method : Testing Reports ends");

			return "his/testing-report";
			
								       
				}
}
