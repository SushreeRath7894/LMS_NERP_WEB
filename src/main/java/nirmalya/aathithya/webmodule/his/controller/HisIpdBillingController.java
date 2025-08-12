package nirmalya.aathithya.webmodule.his.controller;
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
@RequestMapping("his")
public class HisIpdBillingController {
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HisIpdBillingController.class);

	@GetMapping(value = { "/ipd-billing" })
	public String viewOpd(Model model, HttpSession session) {
		logger.info("Method : billingIpd starts");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// Fetch gender data
			DropDownModel[] gender = restClient.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

		} catch (RestClientException e) {
			// Handle RestClientException
			e.printStackTrace();
		}
		logger.info("Method : billingIpd ends");
		return "his/his-ipd-billing";
	}
	
	// manage-opd-view
		@SuppressWarnings("unchecked")
		@GetMapping("ipd-billing-manage-view")
		public @ResponseBody Object viewIpdDetails(HttpSession session, @RequestParam String fromdate,
				@RequestParam String todate) {
			logger.info("Method :viewIpdDetails starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restClient.getForObject(env.getHisUrl() + "rest-ipd-billing-viewOpdDetails?orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&fromdate=" + fromdate + "&todate=" + todate, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :viewIpdDetails ends");
			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("ipd-billing-details-edit")
		public @ResponseBody Object editIpd(@RequestParam String Id, HttpSession session) {
			logger.info("Method :editIpd starts" + Id);
			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {

				String orgName = (String) session.getAttribute("ORGANIZATION");
				String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restClient.getForObject(env.getHisUrl() + "rest-ipd-billing-editIpd?Id=" + Id + "&organization=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :editIpd ends");
			return resp;
		}

}
