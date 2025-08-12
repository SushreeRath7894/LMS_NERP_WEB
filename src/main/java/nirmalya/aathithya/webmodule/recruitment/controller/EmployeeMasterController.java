package nirmalya.aathithya.webmodule.recruitment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "recruitment")
public class EmployeeMasterController {

	Logger logger = LoggerFactory.getLogger(AddRecruitmentController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	
	// Summary
	@GetMapping("/add-employee-new")
	public String requisition(Model model, HttpSession session) {

		logger.info("Method : requisition starts");
		
		logger.info("Method : requisition ends");

		return "recruitment/add-employee-new";
	}
}