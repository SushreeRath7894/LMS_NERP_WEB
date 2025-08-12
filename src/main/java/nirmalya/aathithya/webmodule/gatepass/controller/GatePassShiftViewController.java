package nirmalya.aathithya.webmodule.gatepass.controller;

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
@RequestMapping(value = "gatepass/")
public class GatePassShiftViewController {

	Logger logger = LoggerFactory.getLogger(GatePassShiftViewController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("employee-shift-list")
	public String employeeShiftList(Model model, HttpSession session) {
		logger.info("Method : employeeShiftList starts");
		String userId = "";
		String org = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] shift = restTemplate.getForObject(env.getMasterUrl() + "getShiftLists?org=" + org + "&orgDiv=" 
					+ orgDiv + "&userId=" + userId,DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);
			model.addAttribute("shiftLists", shiftLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : employeeShiftList ends");
		return "gatepass/view-assigned-shiftList";
	}

	// View by shift
		@SuppressWarnings("unchecked")
		@GetMapping("employee-shift-list-view")
		public @ResponseBody Object viewShiftlistData(@RequestParam String sec, HttpSession session) {

			logger.info("Method :viewShiftlistData starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			// String Date = DateFormatter.getStringDate(date);
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewShiftListGatepass?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&sec=" + sec, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewShiftlistData ends");

			return resp;
		}
		
}
