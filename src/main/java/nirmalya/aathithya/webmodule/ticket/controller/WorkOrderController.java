package nirmalya.aathithya.webmodule.ticket.controller;


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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "ticket")
public class WorkOrderController {

	Logger logger = LoggerFactory.getLogger(WorkOrderController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/work-order")
	public String workOrder(Model model, HttpSession session) {
		logger.info("Method : workOrder starts");

		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}


		logger.info("Method : jobView ends");
		return "ticket/work-order";
	}
	
	
	
	// Job view

	@SuppressWarnings({ "unchecked" })
	@GetMapping("/work-order-get-all")
	public @ResponseBody Object getWorkOrderAll(@RequestParam String pageno,@RequestParam String activity,@RequestParam String fromDate,
			@RequestParam String toDate,HttpSession session) {
		logger.info("Method : getWorkOrderAll starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTicketUrl() + "rest-get-workorder?userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&activity=" + activity +"&pageno=" + pageno
					+ "&fromDate=" + fromDate+ "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getWorkOrderAll ends");
		return resp;

	}
	
}
