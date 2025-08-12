package nirmalya.aathithya.webmodule.ticket.controller;

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

@Controller
@RequestMapping(value = "ticket")
public class ManageDashboardController {

	Logger logger = LoggerFactory.getLogger(TicketManagementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/manage-dashboard")
	public String ticketManagement(Model model, HttpSession session) {
		logger.info("Method : manage-dashboard starts");

		String orgName = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getTicketUrl() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> Organization = Arrays.asList(dropDownModel);
			model.addAttribute("Organization", Organization);

			logger.info("Organization" + Organization);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getTicketUrl() + "getDivision?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDivisionList", orgDivisionList);

			logger.info("orgDivisionList" + orgDivisionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : manage-dashboard ends");
		return "ticket/manage-dashboard";
	}

	// manage-dashboard-ticketHeadData

	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-operational-tab-data")
	public @ResponseBody Object ticketHeadData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String location, @RequestParam String organization,@RequestParam String division) {

		logger.info("Method :ticketHeadData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
		try {

			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-operational-tab-data?fromDate=" + fromDate + "&toDate=" + toDate +"&location=" + location + "&organization=" + organization + "&division=" + division,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :ticketHeadData ends" + resp);

		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-operational-head-count-data")
	public @ResponseBody Object operationalHeadData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String location, @RequestParam String organization,@RequestParam String division) {

		logger.info("Method :operationalHeadData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-operational-head-count-data?fromDate=" + fromDate + "&toDate=" + toDate + "&location=" + location +"&organization=" + organization 
					+ "&division=" + division,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :operationalHeadData  ends" + resp);

		return resp;
	}

	// manage-dashboard-ticketListData

	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-operational-aggrid-data")
	public @ResponseBody Object operationalAgGridData(HttpSession session, @RequestParam String id,@RequestParam String fromDate,@RequestParam String location ,@RequestParam String toDate ,
			@RequestParam String organization,@RequestParam String division ) {

		logger.info("Method :operationalAgGridData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
		try {

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-operational-aggrid-data?organization=" + organization + "&division="
					+ division + "&id=" + id +"&fromDate=" + fromDate +"&location=" + location +"&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :operationalAgGridData ends" + resp);

		return resp;
	}

	// manage-dashboard-ticketByType

	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-ticketByType")
	public @ResponseBody Object ticketByType(HttpSession session) {

		logger.info("Method :ticketByType starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restTemplate.getForObject(
					env.getTicketUrl() + "ticketByType?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :ticketByType ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-ticketByCategory")
	public @ResponseBody Object ticketByCategory(HttpSession session) {

		logger.info("Method :ticketByCategory starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restTemplate.getForObject(
					env.getTicketUrl() + "ticketByCategory?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :ticketByCategory ends" + resp);

		return resp;
	}

}
