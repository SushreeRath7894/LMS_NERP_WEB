package nirmalya.aathithya.webmodule.lms.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping("academic")
public class ContactUsController {
    	Logger logger = LoggerFactory.getLogger(ContactUsController.class);

		@Autowired
		EnvironmentVaribles env;
		@Autowired
		RestTemplate restTemplate;

        	@GetMapping("contactus")
		public String promotionDetails(Model model, HttpSession session) {
			logger.info("Mothod:view promotionDetails page started...");

			logger.info("Mothod: view promotionDetails page ends...");
			return "lms/contact-us.html";
		}

        @SuppressWarnings("unchecked")
	@GetMapping("contactUs-data-view")
	public @ResponseBody Object getContactUs(HttpSession session) {
		logger.info("Method :getContactUs starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
 

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewContactUs?orgName=" + orgName + "&orgDivision=" + orgDivision +"&id="+userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :getContactUs ends"+resp);
		return resp;
	}
    
}
