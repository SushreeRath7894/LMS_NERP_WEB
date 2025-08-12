package nirmalya.aathithya.webmodule.appraisal.controller;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "master")
public class AppraisalControllerV2 {
    Logger logger = LoggerFactory.getLogger(AppraisalControllerV2.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EnvironmentVaribles env;


    @GetMapping("/appraisal")
    public String configurationV2(Model model, HttpSession session) {
        logger.info("Method: configurationV2 starts here");

        // Logic will be placed here

        logger.info("Method: configurationV2 ends here");

        return "appraisal-v2/configuration-v2";
    }

    @GetMapping("/360-feedback")
    public String feedback360V2(Model model, HttpSession session) {
        logger.info("Method: feedback360V2 starts here");

    	DropDownModel[] employeeLists = restTemplate.getForObject(env.getAppraisalUrl() + "get-all-employee-list",
				DropDownModel[].class);
    	
		List<DropDownModel> employeeList = Arrays.asList(employeeLists);
		System.out.println("=====================================> "+employeeList);
		model.addAttribute("employeeList", employeeList);

        logger.info("Method: feedback360V2 ends here");

        return "appraisal-v2/feedback360-v2";
    }

	/*
	 * @GetMapping("/self-appraisal") public String selfAppraisalV2(Model model,
	 * HttpSession session) { logger.info("Method: selfAppraisalV2 starts here");
	 * 
	 * // Logic will be placed here
	 * 
	 * logger.info("Method: selfAppraisalV2 ends here");
	 * 
	 * return "appraisal-v2/self-appraisal-v2"; }
	 */

	/*
	 * @GetMapping("/review") public String reviewV2(Model model, HttpSession
	 * session) { logger.info("Method: reviewV2 starts here");
	 * 
	 * // Logic will be placed here
	 * 
	 * logger.info("Method: reviewV2 ends here");
	 * 
	 * return "appraisal-v2/review-v2"; }
	 */

	/*
	 * @GetMapping("/normalization") public String normalizationV2(Model model,
	 * HttpSession session) { logger.info("Method: normalizationV2 starts here");
	 * 
	 * // Logic will be placed here
	 * 
	 * logger.info("Method: normalizationV2 ends here");
	 * 
	 * return "appraisal-v2/normalization-v2"; }
	 */
    

}