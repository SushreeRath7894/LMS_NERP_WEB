package nirmalya.aathithya.webmodule.user.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
public class UserAllTaskController {
	Logger logger = LoggerFactory.getLogger(UserAllTaskController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
 
	//allTasksView
		@SuppressWarnings("unchecked")
		@GetMapping("/all-tasks-view")
		public @ResponseBody Object allTasksView(HttpSession session) {
			logger.info("Method :allTasksView starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getUserUrl() + "all-tasks-view?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :allTasksView ends");
			return resp;
	}
		@SuppressWarnings("unchecked")
		@GetMapping("/all-task-list-calendar-wise")
		public @ResponseBody Object getTaskForCalendars(HttpSession session, @RequestParam String startDate,
				@RequestParam String endtDate) {

			logger.info("Method : getTaskForCalendars starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";
			String userId = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {

				resp = restTemplate.getForObject(
						env.getUserUrl() + "all-task-list-calendar-wise?startDate=" + startDate + "&endtDate=" + endtDate
								+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId,
						JsonResponse.class);

			} catch (Exception e) {
				logger.error("Error in getTaskForCalendars: ", e);
				e.printStackTrace();
			}

			logger.info("Method : getTaskForCalendars ends");
			return resp;
		}
}
