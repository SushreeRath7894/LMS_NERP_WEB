package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.controller.MasterDataApiController;
import nirmalya.aathithya.webmodule.user.service.CustomAuthenticationSuccessHandler;

@Controller
@RequestMapping(value = "pipeline")
public class CrmExecutiveWebControllerV1 {
	Logger logger = LoggerFactory.getLogger(CrmExecutiveWebControllerV1.class);

	@Autowired
	RestTemplate restClient;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	MasterDataApiController master;
	
    @Value("${spring.mail.username}")
    private String username;
    
	List<String> role = new ArrayList<String>();

	public static String org = "";
	public static String orgDiv = "";
	public static String userId = "";

	@GetMapping(value = "crm-executive-management")
	public String viewExecutivePage(Model model, HttpSession session) {
		logger.info("Method: viewExecutivePage starts");

		List<DropDownModel> executiveList = master.getOwnerList(session);
		model.addAttribute("executive", executiveList);
		System.out.println("username==="+username);
		model.addAttribute("from_email", username);

		org = (String) session.getAttribute("ORGANIZATION");
		orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		userId = (String) session.getAttribute("USER_ID");

		logger.info("Method: viewExecutivePage ends");
		return "pipelineV2/executive-manager";
	}

	@GetMapping(value = "crm-lead-contacted")
	public String viewLeadContactPage(Model model, HttpSession session) {
		logger.info("Method: viewExecutivePage starts");

		final String userId = (String) session.getAttribute("USER_ID");
		model.addAttribute("userId", userId);

		List<DropDownModel> executiveList = master.getOwnerList(session);
		model.addAttribute("executive", executiveList);

		org = (String) session.getAttribute("ORGANIZATION");
		orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		try {
			// org = (String) session.getAttribute("ORGANIZATION");
			// orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			role = (List<String>) session.getAttribute("USER_ROLES");

			Optional<String> data = role.stream().filter(d -> d.equals("rol001")).findAny();
			if (data.isPresent())
				model.addAttribute("adminRole", data.get());

			Optional<String> userroledata = role.stream().filter(d -> d.equals("rol037")).findAny();
			if (userroledata.isPresent())
				model.addAttribute("userRole", userroledata.get());

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			List<DropDownModel> ownerList = master.getOwnerList(session);
			;
			logger.info("ownerList===============================>>>>>>" + ownerList);
			model.addAttribute("ownerListtttt", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] brand = restTemplate.getForObject(env.getMasterUrl() + "getBrandListForProduct?org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);

			model.addAttribute("brandList", brandList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String Type = "Sales";
			DropDownModel[] mode = restTemplate.getForObject(env.getMasterUrl() + "getModeListForProduct?type=" + Type
					+ "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] campaign = restTemplate.getForObject(env.getPipeline() + "/getDealCampaignList",
					DropDownModel[].class);

			List<DropDownModel> campaignList = Arrays.asList(campaign);
			model.addAttribute("campaignList", campaignList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			logger.info(env.getPipeline());
			DropDownModel[] status = restTemplate.getForObject(env.getPipeline() + "/getLeadStatusList",
					DropDownModel[].class);

			List<DropDownModel> statusList = Arrays.asList(status);
			model.addAttribute("statusList", statusList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		// leadList

		try {
			String org = "";
			String orgDiv = "";
			String userID = "";
			try {
				userID = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] lead = restTemplate.getForObject(
					env.getPipeline() + "/getLeadNameList?userId=" + userID + "&org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			String org = "";
			String orgDiv = "";
			String userID = "";
			String userManager = "";
			try {
				userID = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userManager = (String) session.getAttribute("MANAGER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] emplist = restTemplate.getForObject(env.getPipeline() + "rest-getEmployeeList-mail?orgName="
					+ org + "&orgDivision=" + orgDiv + "&managerId=" + userManager + "&userId=" + userID,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplistsEvent", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		System.out.println("username==="+username);
		model.addAttribute("from_email", username);
		logger.info("Method: viewExecutivePage ends");
		return "pipelineV2/lead-contacted";
	}

	@GetMapping(value = "crm-lead-qualified")
	public String viewLeadQualifiedPage(Model model, HttpSession session) {
		logger.info("Method: viewExecutivePage starts");

		final String userId = (String) session.getAttribute("USER_ID");
		model.addAttribute("userId", userId);

		List<DropDownModel> executiveList = master.getOwnerList(session);
		model.addAttribute("executive", executiveList);

		org = (String) session.getAttribute("ORGANIZATION");
		orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		try {
			// org = (String) session.getAttribute("ORGANIZATION");
			// orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			role = (List<String>) session.getAttribute("USER_ROLES");

			Optional<String> data = role.stream().filter(d -> d.equals("rol001")).findAny();
			if (data.isPresent())
				model.addAttribute("adminRole", data.get());

			Optional<String> userroledata = role.stream().filter(d -> d.equals("rol037")).findAny();
			if (userroledata.isPresent())
				model.addAttribute("userRole", userroledata.get());

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			List<DropDownModel> ownerList = master.getOwnerList(session);
			;
			logger.info("ownerList===============================>>>>>>" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] brand = restTemplate.getForObject(env.getMasterUrl() + "getBrandListForProduct?org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);

			model.addAttribute("brandList", brandList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String Type = "Sales";
			DropDownModel[] mode = restTemplate.getForObject(env.getMasterUrl() + "getModeListForProduct?type=" + Type
					+ "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] campaign = restTemplate.getForObject(env.getPipeline() + "/getDealCampaignList",
					DropDownModel[].class);

			List<DropDownModel> campaignList = Arrays.asList(campaign);
			model.addAttribute("campaignList", campaignList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			logger.info(env.getPipeline());
			DropDownModel[] status = restTemplate.getForObject(env.getPipeline() + "/getLeadStatusList",
					DropDownModel[].class);

			List<DropDownModel> statusList = Arrays.asList(status);
			model.addAttribute("statusList", statusList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		// leadList

		try {
			String org = "";
			String orgDiv = "";
			String userID = "";
			try {
				userID = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] lead = restTemplate.getForObject(
					env.getPipeline() + "/getLeadNameList?userId=" + userID + "&org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			String org = "";
			String orgDiv = "";
			String userID = "";
			String userManager = "";
			try {
				userID = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userManager = (String) session.getAttribute("MANAGER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] emplist = restTemplate.getForObject(env.getPipeline() + "rest-getEmployeeList-mail?orgName="
					+ org + "&orgDivision=" + orgDiv + "&managerId=" + userManager + "&userId=" + userID,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplistsEvent", emplists);
			System.out.println("username==="+username);
			model.addAttribute("from_email", username);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method: viewExecutivePage ends");
		return "pipelineV2/lead-qualified";
	}

}
