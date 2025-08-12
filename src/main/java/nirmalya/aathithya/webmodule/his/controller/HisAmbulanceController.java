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
public class HisAmbulanceController {
	Logger logger = LoggerFactory.getLogger(HisAmbulanceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping("/his-ambulance")
	public String ambulance(Model model, HttpSession session) {

		logger.info("Method : ambulance starts");

		
		logger.info("Method : ambulance ends");

		return "his/his-ambulance.html";

	}
}
