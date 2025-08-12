package nirmalya.aathithya.webmodule.bidding.controller;

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

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "bidding/")
public class PresentationWebController {
	
	Logger logger = LoggerFactory.getLogger(PresentationWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("/presentation-mstr")
	public String closeOut(Model model, HttpSession session) {

	logger.info("Method : presentation starts");
	
	logger.info("Method : presentation ends");
	
	return "bidding/presentation-mstr";
	}
}
