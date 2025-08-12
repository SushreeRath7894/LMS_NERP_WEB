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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
//import nirmalya.aathithya.webmodule.projects.model.BudgetMasterWebModel;

@Controller
@RequestMapping(value = "projects")

public class BudgetMasterWebController {


	Logger logger = LoggerFactory.getLogger(BudgetMasterWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "manage-execution" })
	public String estimateBudget(Model model, HttpSession session) {
		logger.info("Method : manageBudget starts");
		logger.info("Method : manageBudget ends");
		return "projects/manageExecution";
	}
	@GetMapping(value = { "manage-budget" })
	public String manageBudget(Model model, HttpSession session) {
		logger.info("Method : manageBudget starts");
//		
//		try {
//			DropDownModel[] dd = restClient.getForObject(env.getRecruitment() + "getUserRoleDropDown", DropDownModel[].class);
//			List<DropDownModel> roleList = Arrays.asList(dd);
//			
//			model.addAttribute("roleList", roleList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
		logger.info("Method : manageBudget ends");
		return "projects/budgetMaster";
	}
}
