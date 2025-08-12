package nirmalya.aathithya.webmodule.projects.controller;


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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.ProjectCategoryModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectSubContractorWebModel;

@Controller
@RequestMapping(value = "projects")
public class ManageProjectCategoryController {
	Logger logger = LoggerFactory.getLogger(ManageProjectCategoryController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "manage-category" })
	public String manageCategory(Model model, HttpSession session) {
		logger.info("Method : manageCategory starts");
		
		
		logger.info("Method : manageCategory ends");
		//return "projects/manage-projectCategory.html";
		return "projects/manage-categoryNew.html";
	}
	
	//add category
	
	@SuppressWarnings("unchecked")
	@PostMapping("manage-category-save-category")
	public @ResponseBody JsonResponse<Object> saveCategory(
			@RequestBody ProjectCategoryModel category, HttpSession session) {
		logger.info("Method : saveCategory starts");

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

		category.setCreatedBy(userId);
		category.setOrganizationName(orgName);
		category.setOrganizationDivision(orgDiv);

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveCategory", category,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveCategory starts");
		return resp;
	}
	
	
	//  CAtegory view
		@SuppressWarnings("unchecked")
		@GetMapping("manage-category-view")
		public @ResponseBody Object viewCategory(HttpSession session) {

			logger.info("Method :viewCategory starts");
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
				resp = restClient.getForObject(env.getProjects() + "rest-viewCategory"
						+ "?userid=" + userId+"&org="+organization+"&div="+orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("viewCategory---------" + resp);
			logger.info("Method :viewCategory ends");

			return resp;
		}
	
		//FOR PROJECT CATEGORY DELETE STARTS
		
		@SuppressWarnings("unchecked")
		@PostMapping("manage-category-deleteCategory")
		public @ResponseBody JsonResponse<Object> deleteCategory(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : deleteCategory function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			try {
				res = restClient.getForObject(env.getProjects() + "rest-deleteCategory?id=" + id,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteCategory function Ends");

			return res;
		}
		
		
		//add SUbcategory
		
		@SuppressWarnings("unchecked")
		@PostMapping("manage-category-save-subcategory")
		public @ResponseBody JsonResponse<Object> savesubcategory(
				@RequestBody ProjectCategoryModel category, HttpSession session) {
			logger.info("Method : savesubcategory starts");

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

			category.setCreatedBy(userId);
			category.setOrganizationName(orgName);
			category.setOrganizationDivision(orgDiv);

			try {
				resp = restClient.postForObject(env.getProjects() + "rest-savesubcategory", category,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != null && resp.getMessage() != "") {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method : saveCategory starts");
			return resp;
		}
		
	//  Sub CAtegory view
			@SuppressWarnings("unchecked")
			@GetMapping("manage-category-view-subcategory")
			public @ResponseBody Object viewsubcategory(HttpSession session,@RequestParam String cdId) {

				logger.info("Method :viewsubcategory starts");
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
					resp = restClient.getForObject(env.getProjects() + "rest-viewsubcategory"
							+ "?userid=" + userId+"&org="+organization+"&div="+orgDivision+"&cdId="+cdId,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("viewsubcategory---------" + resp);
				logger.info("Method :viewsubcategory ends");

				return resp;
			}
			
			//FOR PROJECT SUB CATEGORY DELETE STARTS
			
			@SuppressWarnings("unchecked")
			@PostMapping("manage-category-deleteSubCategory")
			public @ResponseBody JsonResponse<Object> deleteSubCategory(@RequestParam String id, Model model,
					HttpSession session) {
				logger.info("Method : deleteSubCategory function starts");

				JsonResponse<Object> res = new JsonResponse<Object>();

				try {
					res = restClient.getForObject(env.getProjects() + "rest-deleteSubCategory?id=" + id,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}
				logger.info("Method : deleteSubCategory function Ends");

				return res;
			}
					
		
}
