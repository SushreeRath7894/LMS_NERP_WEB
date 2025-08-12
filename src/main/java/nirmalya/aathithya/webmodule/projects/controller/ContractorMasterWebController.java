package nirmalya.aathithya.webmodule.projects.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;


@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "projects/")

public class ContractorMasterWebController {
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("/project-contractor")
	public String projectContractorMstr(Model model, HttpSession session) {
	Logger logger = LoggerFactory.getLogger(ContractorMasterWebController.class);


	logger.info("Method : projectContractor starts");
	

	logger.info("Method : projectContractor ends");
	return "projects/contractor-master";
}
}
