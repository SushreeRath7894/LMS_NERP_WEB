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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;

@Controller
@RequestMapping(value = "projects")
public class ProjectExecutionWebControllerV1 {
	Logger logger = LoggerFactory.getLogger(ProjectExecutionWebControllerV1.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "/project-execution-v1" })
	public String projectExecution(Model model, HttpSession session) {
		logger.info("Method : projectExecution starts");

		try {
			DropDownModel[] priority = restClient.getForObject(env.getProjects() + "get-projectPriority-list-v1",
					DropDownModel[].class);
			List<DropDownModel> priorityList = Arrays.asList(priority);
			model.addAttribute("projectPriorityList", priorityList);

			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);
			model.addAttribute("unitList", unitList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : projectExecution ends");
		return "projects/project-execution";
	}
}
