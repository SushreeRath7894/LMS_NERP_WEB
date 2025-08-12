package nirmalya.aathithya.webmodule.his.controller;

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
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "his")
public class HisPharmacyController {

	Logger logger = LoggerFactory.getLogger(HISPatholabNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("pharmacy-his")
	public String hispharmacy(Model model, HttpSession session) {

		logger.info("Method : hispharmacy starts");
		
		
		logger.info("Method : hispharmacy ends");

		return "his/his-pharmacy.html";
		
	}
	
	
	//view
			@SuppressWarnings("unchecked")
			@GetMapping("pharmacy-his-view")
			public @ResponseBody Object viewPatient(HttpSession session) {
				logger.info("Method :viewPatient starts");
				JsonResponse<Object> resp = new JsonResponse<Object>();

				try {
					String userId = (String) session.getAttribute("USER_ID");
					String orgName = (String) session.getAttribute("ORGANIZATION");
					String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

					resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewIPDOPDlist?orgName=" + orgName + "&orgDivision="
							+ orgDivision + "&userId=" + userId, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (resp.getMessage() != "" && resp.getMessage() != null) {
					resp.setCode(resp.getCode());
					resp.setMessage(resp.getMessage());
				} else {
					resp.setMessage(resp.getMessage());
				}
				logger.info("Method :viewPatient ends"+resp);
				return resp;
			}
			
			@SuppressWarnings("unchecked")
			@GetMapping("pharmacy-his-view-treatment")
			public @ResponseBody Object viewTypesDetails(HttpSession session, @RequestParam String types,
					@RequestParam String opdId) {

				logger.info("Method :viewTypesDetails starts");
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String orgName = "";
				String orgDivision = "";
				try {
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

					resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-types?orgName=" + orgName + "&orgDivision="
							+ orgDivision + "&types=" + types + "&opdId=" + opdId, JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (resp.getMessage() != "" && resp.getMessage() != null) {
					resp.setCode(resp.getCode());
					resp.setMessage(resp.getMessage());
				} else {
					resp.setCode(resp.getCode());
					resp.setMessage(resp.getMessage());
				}
				logger.info("Method :viewTypesDetails ends" + resp);
				return resp;
			}
	

}
