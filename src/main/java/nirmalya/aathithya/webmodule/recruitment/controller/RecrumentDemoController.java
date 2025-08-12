package nirmalya.aathithya.webmodule.recruitment.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.recruitment.model.ActionEmployeeDetailsModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "recruitment/")
public class RecrumentDemoController {
	Logger logger = LoggerFactory.getLogger(RecrumentDemoController.class);

	@Autowired
	RestTemplate restClient;
	
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("review")
	public String generateInventoryStockReport(Model model, HttpSession session) {

		logger.info("Method : generateInventoryStockReport starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			ActionEmployeeDetailsModel[] manager = restClient.getForObject(env.getRecruitment() + "EmployeeFullList?org="+organization+"&orgDiv="+orgDivision,
			ActionEmployeeDetailsModel[].class);
			List<ActionEmployeeDetailsModel> managerList = Arrays.asList(manager);
			model.addAttribute("employeeFullList", managerList);
			} catch (RestClientException e) {
			e.printStackTrace();
			}
		
		try {	
			DropDownModel[] location = restClient.getForObject(env.getRecruitment() + "jobLocationList",
					DropDownModel[].class);
			List<DropDownModel> jobLocationList = Arrays.asList(location);
			model.addAttribute("jobLocationList", jobLocationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {	
			DropDownModel[] ratingCat = restClient.getForObject(env.getRecruitment() + "ratingCategoryList",
					DropDownModel[].class);
			List<DropDownModel> ratingCatList = Arrays.asList(ratingCat);
			model.addAttribute("ratingCatList", ratingCatList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String userId = "";
		String userName = "";
		String userJobId = "JOB002"; 
		String userJob = "SDEII";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userJobId", userJobId);
		model.addAttribute("userJob", userJob);
		logger.info("Method : generateInventoryStockReport ends");
		return "recruitment/review";

	}
	/**
	 * get Rating type
	 *
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "review-getRatingType" })
	public @ResponseBody JsonResponse<List<DropDownModel>> getRatingType(Model model,
			@RequestParam("category") String category, HttpSession session) {
		logger.info("Method : getRatingType starts");
		JsonResponse<List<DropDownModel>> res = new JsonResponse<List<DropDownModel>>();


		try {
			res = restClient.getForObject(env.getRecruitment() + "rest-getRatingType?category=" + category,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getRatingType ends");
		return res;
	}
	
	@GetMapping("time-sheet")
	public String recurementTimeSheet(Model model, HttpSession session) {

		logger.info("Method : recurementTimeSheet starts");
		model.addAttribute("pageName", "Time Sheet Page Coming Soon");
		logger.info("Method : recurementTimeSheet ends");
		return "recruitment/view-action";

	}

	@GetMapping("exit")
	public String recurementExit(Model model, HttpSession session) {

		logger.info("Method : recurementExit starts");
		model.addAttribute("pageName", "Exit Page Coming Soon");
		logger.info("Method : recurementExit ends");
		return "recruitment/view-action";

	}

	@GetMapping("view-plan")
	public String recurementPlan(Model model, HttpSession session) {

		logger.info("Method : recurementPlan starts");
		model.addAttribute("pageName", "Plan Page Coming Soon");
		logger.info("Method : recurementPlan ends");
		return "recruitment/view-action";

	}

	@GetMapping("set-goal")
	public String recurementSetGoal(Model model, HttpSession session) {

		logger.info("Method : recurementSetGoal starts");
		model.addAttribute("pageName", "Set Goal Coming Soon");
		logger.info("Method : recurementSetGoal ends");
		return "recruitment/view-action";

	}

	@GetMapping("view-outcome")
	public String recurementViewOutCome(Model model, HttpSession session) {

		logger.info("Method : recurementViewOutCome starts");
		model.addAttribute("pageName", "OutCome Coming Soon");
		logger.info("Method : recurementViewOutCome ends");
		return "recruitment/view-action";

	}

	@GetMapping("view-payrole")
	public String viewPayRole(Model model, HttpSession session) {

		logger.info("Method : viewPayRole starts");
		model.addAttribute("pageName", "Payrole Coming Soon");
		logger.info("Method : viewPayRole ends");
		return "recruitment/view-action";

	}

	@GetMapping("view-advance")
	public String viewAdvance(Model model, HttpSession session) {

		logger.info("Method : viewAdvance starts");
		model.addAttribute("pageName", "Advance Coming Soon");
		logger.info("Method : viewAdvance ends");
		return "recruitment/view-action";

	}

	@GetMapping("view-expense")
	public String viewExpense(Model model, HttpSession session) {

		logger.info("Method : viewExpense starts");
		model.addAttribute("pageName", "Expense Coming Soon");
		logger.info("Method : viewExpense ends");
		return "recruitment/view-action";

	}
}
