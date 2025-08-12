package nirmalya.aathithya.webmodule.hotel.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.ticket.model.TicketManagementModel;

	@Controller
	@RequestMapping(value = "hotel")
	public class MaintainanceWebController {
	
		Logger logger = LoggerFactory.getLogger(BillingWebController.class);
	
		@Autowired
		RestTemplate restTemplate;
	
		@Autowired
		EnvironmentVaribles env;
	
		@GetMapping("/maintenance")
		public String viewBilling(Model model, HttpSession session) {
			logger.info("Method: viewBilling starts here");
	
			try {
				String userId = (String) session.getAttribute("USER_ID");
				String org = (String) session.getAttribute("ORGANIZATION");
				String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
	
				DropDownModel[] result_List = restTemplate.getForObject(
						env.getTicketUrl() + "get-result-status?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
	
				List<DropDownModel> resultList = Arrays.asList(result_List);
	
				model.addAttribute("resultList", resultList);
	
			} catch (RestClientException e) {
				e.printStackTrace();
			}
	
			logger.info("Method: viewBilling ends here");
			return "hotel/maintainance";
		}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/view-job-manage-all-data")
	public @ResponseBody Object getjobview(@RequestParam String pageno, @RequestParam String type , HttpSession session) {
		logger.info("Method : getview-job-manage starts");

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
			resp = restTemplate.getForObject(env.getTicketSystemUrl() + "jobview-all-data?userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&pageno=" + pageno + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getview-job-manage ends");
		return resp;

	}
	@SuppressWarnings("unchecked")
	@PostMapping("view-job-manage-save-accept")
	public @ResponseBody JsonResponse<Object> acceptOperation(@RequestParam String id,String operation, Model model,
			HttpSession session) {
		logger.info("Method : acceptOperation function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTicketSystemUrl() + "jobview-save-accept?id=" + id + "&operation=" + operation+ "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : acceptOperation function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-job-manage-save-result")
	public @ResponseBody JsonResponse<Object> saveJobResult(@RequestBody TicketManagementModel category,
			HttpSession session) {
		logger.info("Method : saveJobResult starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		category.setCreatedBy(userId);
		category.setOrganization(organization);
		category.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getTicketSystemUrl() + "add-job-result", category, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : saveJobResult starts");
		return resp;
	}
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/view-job-manage-result-view")
	public @ResponseBody Object getResultView(@RequestParam String id, HttpSession session) {
		logger.info("Method : getResultView starts");

		String organization = "";
		String orgDivision = "";
		//String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			//userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getTicketSystemUrl() + "jobview-result-view?&id=" + id + "&org="
					+ organization + "&orgDiv=" + orgDivision , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getResultView ends");
		return resp;

	}
	
}
