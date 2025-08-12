package nirmalya.aathithya.webmodule.property.stakeholder.controller;

import java.util.ArrayList;
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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.property.stakeholder.model.StackholderRentosSupportModel;

@Controller
@RequestMapping(value = "property")
public class StackholderRentosSupportController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(StackholderRentosSupportController.class);

	/* Rent os */

	@GetMapping(value = { "rent-os" })
	public String rentOs(Model model, HttpSession session) {
		logger.info("Method :rentOs starts");
		String userId = "";
		String userRole = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
		}
		model.addAttribute("userId", userId);

		logger.info("Method : rentOs ends");
		return "new-property/rent-outstanding-report";
	}
	
	/// view
	@SuppressWarnings("unchecked")

	@GetMapping("/rent-os-view")
	public @ResponseBody List<StackholderRentosSupportModel> viewRentOS(HttpSession session,@RequestParam String userid,
			@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : viewRentOS starts");

		JsonResponse<List<StackholderRentosSupportModel>> resp = new JsonResponse<List<StackholderRentosSupportModel>>();
		List<StackholderRentosSupportModel> returnList = new ArrayList<StackholderRentosSupportModel>();

		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "rent-os-viewRentLedger?userid="+userid+"&fromDate="+fromDate+"&toDate="+toDate,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		//logger.info(returnList);
		
		logger.info("Method : viewRentOS ends");
		return returnList;
	}

}
