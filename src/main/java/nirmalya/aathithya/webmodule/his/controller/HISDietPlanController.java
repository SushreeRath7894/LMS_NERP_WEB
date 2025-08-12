package nirmalya.aathithya.webmodule.his.controller;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;

@Controller
@RequestMapping(value = "his")
public class HISDietPlanController {
	
	Logger logger = LoggerFactory.getLogger(HISDietPlanController.class);

	@Autowired 
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "/ipd-dietPlan" })

	public String getHomePage(Model model, HttpSession session) {

		logger.info("Method : Diet Plan starts");
		
		logger.info("Method : Diet Plan ends");

		return "his/ipd-dietPlan";
		
							
			}
	

}
