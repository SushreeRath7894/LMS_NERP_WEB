package nirmalya.aathithya.webmodule.recruitment.controller;

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
import nirmalya.aathithya.webmodule.recruitment.model.ActionEmployeeDetailsModel;
import nirmalya.aathithya.webmodule.recruitment.model.ReviewHiringProcessModel;

@Controller


@RequestMapping("recruitment")	
public class DepartmentWiseShortlistController {
	
	Logger logger = LoggerFactory.getLogger(ReviewHiringProcessController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/departmentwise-requisition")
	public String getRequisition(Model model, HttpSession session) {

		logger.info("Method : getRequisition starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		model.addAttribute("userId", userId);

		logger.info("Method : getRequisition ends");

		return "recruitment/departmentwise-requisition";
	}

	
	
	/* Function for view candidate with requisition id */
	@SuppressWarnings("unchecked")
	@GetMapping("/departmentwise-requisition-get-candidates")
	public @ResponseBody JsonResponse<Object> getCandidateListingWithRequisitions(@RequestParam String exp, Model model,
			HttpSession session) {

		logger.info("Method : getCandidateListingWithRequisitions starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		String userId = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "get-candidate-listing-department?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&exp=" + exp  + "&userId=" + userId , JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getCandidateListingWithRequisitions ends");

		return resp;
	}
	
	
	// Shortlist Candidates by Requisition id (Department wise)

		@SuppressWarnings("unchecked")
		@PostMapping("/departmentwise-requisition-candidate-shortlist")
		public @ResponseBody JsonResponse<Object> shortlistCandidates(@RequestBody ReviewHiringProcessModel data,
				HttpSession session) {
			logger.info("Method : shortlistCandidates starts");

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
			data.setCreatedBy(userId);
			data.setOrganization(organization);
			data.setOrgDivision(orgDivision);

			try {
				resp = restTemplate.postForObject(env.getRecruitment() + "rest-shortlist-candidate", data,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : shortlistCandidates starts");
			return resp;
		}
}
