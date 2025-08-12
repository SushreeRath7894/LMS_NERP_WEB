package nirmalya.aathithya.webmodule.master.controller;

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
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.GoalReviewModel;
import nirmalya.aathithya.webmodule.master.model.RequestFeedBackWebModel;


@Controller

@RequestMapping(value = { "master/" })
public class RequestFeedBackController {
	Logger logger = LoggerFactory.getLogger(RequestFeedBackController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "request-feedbacks" })

	public String employeeAdvancee(Model model, HttpSession session) {
		logger.info("Method : employeeAdvancee starts");
		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
 
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : employeeAdvancee ends");
		return "master/sendRequestForFeedBacks";
	}

	// Role assign

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "request-feedbacks-roles-listing" })
	public @ResponseBody List<RequestFeedBackWebModel> getEmployeeRolesList(Model model) {
		logger.info("Method : getEmployeeRolesList starts");

		JsonResponse<List<RequestFeedBackWebModel>> res = new JsonResponse<List<RequestFeedBackWebModel>>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getEmployeeNameList", JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<RequestFeedBackWebModel> dataList = mapper.convertValue(res.getBody(),
					new TypeReference<List<RequestFeedBackWebModel>>() {
					});

			for (RequestFeedBackWebModel m : dataList) {

				if (m.getCreatedBy() == null || m.getCreatedBy() == "") {
					m.setCreatedBy("SYSTEM");
				}
			}

			res.setBody(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getEmployeeRolesList ends");

		logger.info("EMPLOYEELISTTTT" + res.getBody());
		return res.getBody();
	}

	/*
	 * view review employee list
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/request-feedbacks-employeelist")
	public @ResponseBody List<GoalReviewModel> viewEmployeeList(HttpSession session,@RequestParam String userid) {
		logger.info("Method : viewEmployeeList starts");

		JsonResponse<List<GoalReviewModel>> resp = new JsonResponse<List<GoalReviewModel>>();

		/*
		 * String userId = ""; try { userId = (String) session.getAttribute("USER_ID");
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "view-emp-list?userId=" + userid,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<GoalReviewModel> returnList = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GoalReviewModel>>() {
				});

		String dateFormat = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}

		if (returnList != null)
			for (GoalReviewModel a : returnList) {
				if (a.getDob() != null && a.getDob() != "") {
					a.setDob(DateFormatter.dateFormat(a.getDob(), dateFormat));
				}
			}
		logger.info("VIEWWWWWWList==" + returnList);
		logger.info("Method : viewEmployeeList ends");
		return returnList;
	}

	/*
	 *
	 * add
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "request-feedbacks-save-data" })
	public @ResponseBody JsonResponse<Object> addEmployeeFeedBack(Model model, HttpSession session,

			@RequestBody RequestFeedBackWebModel data) {
		logger.info("Method : addEmployeeFeedBack starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			data.setCreatedBy(userId);
			res = restTemplate.postForObject(env.getMasterUrl() + "addEmployeeFeedBack", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}

		logger.info("Method : addEmployeeFeedBack ends");

		logger.info("ADDDDD" + res);
		return res;

	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = { "request-feedbacks-save-data" }) public @ResponseBody
	 * JsonResponse<Object> addEmployeeFeedBack(
	 * 
	 * @RequestBody List<RequestFeedBackWebModel> requestFeedBackWebModel,
	 * HttpSession session) { logger.info("Method : addEmployeeFeedBack starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>(); String userId = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID");
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } for (RequestFeedBackWebModel a
	 * : requestFeedBackWebModel) {
	 * 
	 * a.setCreatedBy(userId);
	 * 
	 * } try {
	 * 
	 * resp = restTemplate.postForObject(env.getMasterUrl() + "addEmployeeFeedBack",
	 * requestFeedBackWebModel, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * if (resp.getMessage() != "" && resp.getMessage() != null) {
	 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
	 * resp.setMessage("Success"); }
	 * 
	 * logger.info("Method : addEmployeeFeedBack ends"); return resp; }
	 */
}
