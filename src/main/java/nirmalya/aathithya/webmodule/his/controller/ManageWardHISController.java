package nirmalya.aathithya.webmodule.his.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;

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
import nirmalya.aathithya.webmodule.his.model.HISWardModel;

@Controller
@RequestMapping("his")
public class ManageWardHISController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(ManageWardHISController.class);

	@GetMapping(value = { "/manage-ward" })
	public String viewWard(Model model, HttpSession session) {
		logger.info("Method : viewWard starts");

		try {
			DropDownModel[] floor = restClient.getForObject(env.getHisUrl() + "/floorList", DropDownModel[].class);

			List<DropDownModel> floorList = Arrays.asList(floor);
			model.addAttribute("floorList", floorList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] department = restClient.getForObject(env.getHisUrl() + "/getDepartmentList", DropDownModel[].class);

			List<DropDownModel> departmentList = Arrays.asList(department);
			
			List<String> excludedIds = Arrays.asList("DPT001", "DPT002", "DPT003", "DPT004", "DPT005", "DPT006", "DPT007", "DPT008",
					"DPT009","DPT010", "DPT011", "DPT012", "DPT013","DPT014");

	        // Filter departments where id is not in the excluded list
	        List<DropDownModel> filteredDepartments = departmentList.stream()
	                .filter(d -> !excludedIds.contains(d.getKey()))
	                .collect(Collectors.toList());
			
			model.addAttribute("departmentList", filteredDepartments);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewWard starts");
		return "his/manage-ward";
//		return "his/nurse-dashboard";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("manage-ward-add")
	public @ResponseBody JsonResponse<Object> addWard(@RequestBody HISWardModel wardModel, HttpSession session) {
		logger.info("Method: addWard starts");

		JsonResponse<Object> resp = new JsonResponse<>();

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
		wardModel.setCreatedBy(userId);
		wardModel.setOrganization(orgName);
		wardModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getHisUrl() + "rstAddWard", wardModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setMessage("Error during REST call");
		}

		if (resp.getMessage() == null || resp.getMessage().isEmpty()) {
			resp.setMessage("Success");
		}

		logger.info("Method: addWard ends");

		return resp;
	}

	// manage-ward-view

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-ward-view")
	public @ResponseBody List<HISWardModel> viewWard(HttpSession session) {
		logger.info("Method : viewWard starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HISWardModel>> resp = new JsonResponse<List<HISWardModel>>();
		List<HISWardModel> returnList = new ArrayList<HISWardModel>();

		try {
			resp = restClient.getForObject(
					env.getHisUrl() + "rest-viewWard?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		// logger.info(returnList);
		logger.info("Method : viewWard ends");
		return returnList;
	}
	
	//manage-ward-delete
	@SuppressWarnings("unchecked")
	@GetMapping("/manage-ward-delete")
	public @ResponseBody JsonResponse<Object> deleteWard(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteWard starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getHisUrl() + "deleteWard?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteWard ends");
		return resp;
	}


}