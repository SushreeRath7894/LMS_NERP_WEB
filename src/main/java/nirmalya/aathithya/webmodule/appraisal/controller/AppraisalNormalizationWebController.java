package nirmalya.aathithya.webmodule.appraisal.controller;

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
@RequestMapping(value = "master")
public class AppraisalNormalizationWebController {
    Logger logger = LoggerFactory.getLogger(AppraisalNormalizationWebController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EnvironmentVaribles env;
    
    @GetMapping("/normalization")
    public String normalizationV2(Model model, HttpSession session) {
        logger.info("Method: normalizationV2 starts here");


        logger.info("Method: normalizationV2 ends here");

        return "appraisal-v2/normalization-v2";
    }
    
    @SuppressWarnings("unchecked")
	@GetMapping("get-all-reviewed-employee")
	public @ResponseBody Object getAllReviewedEmployee(HttpSession session) {
		logger.info("Method :getAllReviewedEmployee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(
					env.getAppraisalUrl() + "rest-get-all-reviewed-employee?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		}
		logger.info("Method :getAllReviewedEmployee ends" + resp);
		return resp;
	}

}
