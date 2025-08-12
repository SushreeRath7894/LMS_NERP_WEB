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

public class SiteManagerWebController {
	Logger logger = LoggerFactory.getLogger(SiteManagerWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("/project-site-manager")
	public String projectSiteManager(Model model, HttpSession session) {

	logger.info("Method : projectSiteManager starts");
	

	logger.info("Method : projectContractor ends");
	return "projects/site-manager";

}
}
