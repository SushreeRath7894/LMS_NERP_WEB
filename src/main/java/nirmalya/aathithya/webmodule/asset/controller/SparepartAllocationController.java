package nirmalya.aathithya.webmodule.asset.controller;

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
@RequestMapping(value = "asset/")
public class SparepartAllocationController {

	Logger logger = LoggerFactory.getLogger(SparepartAllocationController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/asset-sparepart-allocation")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : reimbursement starts");
		
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
			DropDownModel[] emp = restTemplate.getForObject(
					env.getAssetUrl() + "getEmployeeListforAsset?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] loc = restTemplate.getForObject(
					env.getAssetUrl() + "getLocationListforAsset?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}try {
			DropDownModel[] asset_list = restTemplate.getForObject(
					env.getTicketUrl() + "getAssetList?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(asset_list);

			model.addAttribute("assetList", assetList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		 
		logger.info("Method : reimbursement ends");
		return "asset/asset-sparepart-allocation";
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("asset-sparepart-allocation-view")
	public @ResponseBody Object viewAssignAsset(@RequestParam String id,HttpSession session) {
		logger.info("Method :viewAssignAsset starts");
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
			resp = restTemplate.getForObject(
					env.getAssetUrl() + "rest-sparepart-assign-view?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&id=" + id+ "&userId=" + userId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewAssignAsset ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("asset-sparepart-allocation-dissociate")
	public @ResponseBody JsonResponse<Object> dissociateSpare(@RequestParam String assignid,String assetid,String dreason,String spareAQty, Model model, HttpSession session) {
		logger.info("Method : dissociateSpare function starts");

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
			res = restTemplate.getForObject(
					env.getAssetUrl() + "rest-asset-sparepart-dissociate?assignid=" + assignid + "&assetid=" + assetid+ "&org=" + orgName + "&orgDiv=" + orgDivision+ "&dreason=" + dreason+ "&spareAQty=" + spareAQty,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : dissociateSpare function Ends");
		return res;
	}
}
