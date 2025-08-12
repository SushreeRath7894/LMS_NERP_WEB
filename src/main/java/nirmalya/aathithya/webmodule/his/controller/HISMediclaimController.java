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
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
@Controller
@RequestMapping(value = "his")
public class HISMediclaimController {
	Logger logger = LoggerFactory.getLogger(HISMediclaimController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping("/his-mediclaim")
	public String mediclaim(Model model, HttpSession session) {

		logger.info("Method : mediclaim starts");

		
		logger.info("Method : mediclaim ends");

		return "his/mediclaim.html";

	}
}
