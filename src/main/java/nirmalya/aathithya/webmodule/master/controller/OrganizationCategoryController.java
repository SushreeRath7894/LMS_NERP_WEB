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
import nirmalya.aathithya.webmodule.master.model.OrganizationalMasterModel;

@Controller
@RequestMapping(value = { "master/" })
public class OrganizationCategoryController {

	Logger logger = LoggerFactory.getLogger(OrganizationCategoryController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "organization-category" })
	public String organizationCategory(Model model, HttpSession session) {
		logger.info("Method : organizationCategory starts");
		
		try {
			DropDownModel  org = restClient.getForObject(env.getMasterUrl() + "getOrganizationName",
					DropDownModel .class);
			model.addAttribute("orgName", org.getName());
			model.addAttribute("orgId", org.getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// drop down for employee list
				try {
					DropDownModel[] emplist = restClient.getForObject(env.getMasterUrl() + "getEmployeeLists", DropDownModel[].class);
					List<DropDownModel> emplists = Arrays.asList(emplist);
					model.addAttribute("emplists", emplists);
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				
		logger.info("Method : organizationCategory ends");
		return "master/organizationCategory";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/organization-category-save")
	public @ResponseBody JsonResponse<Object> saveOrganizationCategory(@RequestBody OrganizationalMasterModel organization, HttpSession session) {
		logger.info("Method : saveOrganizationCategory starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String orgName=""; 
		String orgDivision="";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		organization.setCreatedBy(userId);
		organization.setOrgName(orgName);
		organization.setOrgDivision(orgDivision);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveOrganizationCategory", organization,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveOrganizationCategory starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/organization-category-save-subcat")
	public @ResponseBody JsonResponse<Object> saveOrganizationSubCategory(@RequestBody OrganizationalMasterModel organization, HttpSession session) {
		logger.info("Method : saveOrganizationSubCategory starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String orgName=""; 
		String orgDivision="";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		organization.setCreatedBy(userId);
		organization.setOrgName(orgName);
		organization.setOrgDivision(orgDivision);
		
		logger.info("++++++++++++++++++"+organization);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveOrganizationSubCategory", organization,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveOrganizationSubCategory starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/organization-category-get-total-list")
	public @ResponseBody JsonResponse<List<OrganizationalMasterModel>> getAllOrganizationCategoryList(HttpSession session) {
		logger.info("Method : getAllOrganizationCategoryList starts");
		
		JsonResponse<List<OrganizationalMasterModel>> resp = new JsonResponse<List<OrganizationalMasterModel>>();
		String orgName=""; 
		String orgDivision="";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getAllOrganizationCategoryList?orgName=" + orgName+"&orgDivision=" +orgDivision, 
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getAllOrganizationCategoryList starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/organization-category-get-organization-list-by-id")
	public @ResponseBody JsonResponse<List<OrganizationalMasterModel>> getOrganizationCategoryListById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getOrganizationCategoryListById starts");
		
		JsonResponse<List<OrganizationalMasterModel>> resp = new JsonResponse<List<OrganizationalMasterModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getOrganizationCategoryListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getOrganizationCategoryListById starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/organization-category-get-category-dtls-by-id")
	public @ResponseBody JsonResponse<OrganizationalMasterModel> getOrganizationCategoryById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getOrganizationCategoryById starts");
		
		JsonResponse<OrganizationalMasterModel> resp = new JsonResponse<OrganizationalMasterModel>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getOrganizationCategoryById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		logger.info("resp===="+resp);
		logger.info("Method : getOrganizationCategoryById starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/organization-category-delete")
	public @ResponseBody JsonResponse<Object> deleteOrganization(@RequestBody String id, HttpSession session) {
		logger.info("Method : deleteOrganization starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteOrganization?id="+id+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : deleteOrganization starts");
		return resp;
	}
}
