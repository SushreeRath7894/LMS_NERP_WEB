package nirmalya.aathithya.webmodule.maintenance.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "maintenance/")
public class PropertyMaintenanceController {

	Logger logger = LoggerFactory.getLogger(PropertyMaintenanceController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/property-maintenance")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : Property Maintenance starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
			}
		}

		try {
			DropDownModel[] emp = restTemplate.getForObject(env.getAssetUrl() + "getEmployeeListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(env.getMaintenance() + "getCategoryListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> catLists = Arrays.asList(cat);
			model.addAttribute("catLists", catLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] vendorListitem = restClient.getForObject(
					env.getTicketUrl() + "get-vendor-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendorListitem);

			model.addAttribute("vendorList", vendorList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 
		try {
			DropDownModel[] grp = restTemplate.getForObject(env.getMaintenance() + "getGroupListforProperty?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> groupList = Arrays.asList(grp);

			model.addAttribute("groupList", groupList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] loc = restTemplate.getForObject(env.getAssetUrl() + "getLocationListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : Property Maintenance ends");
		return "maintenance/property-maintenance";
	}
	
	
	@SuppressWarnings("unchecked")

	@GetMapping("property-maintenance-policy-list")
	public @ResponseBody Object viewPropertyMaintenancePolicy(HttpSession session) {
		logger.info("Method :viewPropertyMaintenancePolicy starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMaintenance() + "rest-property-maintenance-policy-list?orgName=" + orgName
					+ "&orgDivision=" + orgDivision+ "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewPropertyMaintenancePolicy ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("property-maintenance-view")
	public @ResponseBody Object viewPropertyMaintenance(HttpSession session) {
		logger.info("Method :viewPropertyMaintenance starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMaintenance() + "rest-property-maintenance-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewPropertyMaintenance ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("property-maintenance-deallocate")
	public @ResponseBody JsonResponse<Object> deletePolicyProperty(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deletePolicyProperty function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getMaintenance() + "rest-property-maintenance-deallocate?id=" + id
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deletePolicyProperty function Ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("property-maintenance-allocate")
	public @ResponseBody JsonResponse<Object> assignPropertyPolicy(@RequestParam
			String policyId, String assetList, String assetemp, String assigndate,String assetcat,String assetGrp,String type, Model model, HttpSession session) {
		logger.info("Method : assignPropertyPolicy function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getMaintenance() + "rest-property-maintenance-allocate?policyId=" + policyId + "&assetList=" + assetList + "&assetemp="
					+ assetemp + "&assigndate=" + assigndate + "&assetcat="+assetcat+"&assetGrp="+assetGrp+
					"&org=" + orgName + "&orgDiv=" + orgDivision + "&userid="+ userId+ "&type="+ type, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : assignPropertyPolicy function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("property-maintenance-ticket")
	public @ResponseBody Object viewTicket(@RequestParam String allotedId,HttpSession session) {
		logger.info("Method :viewTicket starts"+allotedId);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMaintenance() + "rest-viewTicket?orgName=" + orgName
					+ "&orgDivision=" + orgDivision+"&allotedId=" + allotedId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewTicket ends");
		return resp;
	}
}
