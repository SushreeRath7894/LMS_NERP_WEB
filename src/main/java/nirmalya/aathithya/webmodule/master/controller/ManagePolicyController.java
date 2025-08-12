package nirmalya.aathithya.webmodule.master.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ManagePolicyModel;

@Controller
@RequestMapping(value = { "master/" })
public class ManagePolicyController {

	Logger logger = LoggerFactory.getLogger(ManagePolicyController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "manage-policy" })
	public String getDeptList(Model model, HttpSession session) {
		logger.info("Method : getDeptList starts");

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] department = restClient.getForObject(env.getMasterUrl()
					+ "get-all-departmentList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> departmentList = Arrays.asList(department);
			model.addAttribute("departmentList", departmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getDeptList ends");
		return "master/manage-policy";
	}

	/*
	 * Main save for Notice Policy
	 * 
	 */

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "manage-policy-master-save")
	public @ResponseBody JsonResponse<Object> saveNoticePolicy(@RequestBody ManagePolicyModel policy,
			HttpSession session) {
		logger.info("Method : saveNoticePolicy function starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		policy.setCreatedBy(userId);
		policy.setOrganization(organization);
		policy.setOrgDivision(orgDivision);

		try {
			System.out.println("THE MODEL DATA:::" + policy);
			resp = restClient.postForObject(env.getMasterUrl() + "save-notice-policy", policy, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("resp=====" + resp);
		logger.info("Method : saveNoticePolicy function Ends");

		return resp;
	}
}
