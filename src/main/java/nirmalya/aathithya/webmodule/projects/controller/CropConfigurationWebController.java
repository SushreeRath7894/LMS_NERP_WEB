package nirmalya.aathithya.webmodule.projects.controller;

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
import nirmalya.aathithya.webmodule.projects.model.ProjectCategoryModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCropModel;

@Controller
@RequestMapping(value = "projects")
public class CropConfigurationWebController {
	Logger logger = LoggerFactory.getLogger(ManageProjectCategoryController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "project-configuration" })
	public String cropConfiguration(Model model, HttpSession session) {
		logger.info("Method : cropConfiguration starts");
		try {
			DropDownModel[] budgetCatList = restClient.getForObject(env.getProjects() + "get-budgetCategoryList",
					DropDownModel[].class);
			List<DropDownModel> budgetCategoryList = Arrays.asList(budgetCatList);
			model.addAttribute("budgetCategoryList", budgetCategoryList);
			logger.info("budgetCategoryList" + budgetCategoryList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);
			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : cropConfiguration ends");
		//return "projects/cropConfiguration.html";
		return "projects/project-configuration";
	}

	//Crop View	
	@SuppressWarnings("unchecked")
	@GetMapping("project-configuration-view")
	public @ResponseBody Object viewCategory(HttpSession session) {

		logger.info("Method :viewCrop starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-viewCrop" + "?userid=" + userId + "&org="
					+ organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewCrop---------" + resp);
		logger.info("Method :viewCrop ends");

		return resp;
	}

	// add crop
	@SuppressWarnings("unchecked")
	@PostMapping("project-configuration-save")
	public @ResponseBody JsonResponse<Object> saveCrop(@RequestBody ProjectCropModel crop, HttpSession session) {
		logger.info("Method : saveCrop starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		crop.setCreatedBy(userId);
		crop.setOrganizationName(orgName);
		crop.setOrganizationDivision(orgDiv);

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveCrop", crop, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveCrop starts");
		return resp;
	}

	// FOR PROJECT Crop DELETE STARTS
	@SuppressWarnings("unchecked")
	@PostMapping("project-configuration-deleteCrop")
	public @ResponseBody JsonResponse<Object> deleteCrop(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteCrop function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-deleteCrop?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteCrop function Ends");

		return res;
	}

	// Crop Process View
	@SuppressWarnings("unchecked")
	@GetMapping("project-configuration-process-view")
	public @ResponseBody Object cropProcessView(HttpSession session, @RequestParam String id) {

		logger.info("Method :cropProcessView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-cropProcessView" + "?userid=" + userId + "&org="
					+ organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("cropProcessView---------" + resp);
		logger.info("Method :cropProcessView ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("project-configuration-SubCategoryList")
	public @ResponseBody JsonResponse<Object> getsubCatList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getsubCatList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restClient.getForObject(env.getProjects() + "rest-get-budgetSubCategoryList?id=" + id + "&org=" + organization + 
					"&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getsubCatList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("project-configuration-variantlist")
	public @ResponseBody JsonResponse<Object> getVariantList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getVariantList starts" + id);
		
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restClient.getForObject(env.getProjects() + "rest-get-budgetVariantList?id=" + id + "&org=" + organization + 
					"&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getVariantList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("project-configuration-save-CropProcess")
	public @ResponseBody JsonResponse<Object> saveCropProcess(@RequestBody ProjectCropModel crop, HttpSession session) {
		logger.info("Method : saveCropProcess starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		crop.setCreatedBy(userId);
		crop.setOrganizationName(orgName);
		crop.setOrganizationDivision(orgDiv);

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveCropProcess", crop, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveCropProcess starts");
		return resp;
	}

	// FOR PROJECT Crop Process DELETE STARTS

	@SuppressWarnings("unchecked")
	@PostMapping("project-configuration-deleteCropProcess")
	public @ResponseBody JsonResponse<Object> deleteCropProcess(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteCropProcess function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-deleteCropProcess?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteCropProcess function Ends");

		return res;
	}

}
