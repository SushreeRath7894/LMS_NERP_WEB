package nirmalya.aathithya.webmodule.budget.controller;

	import java.util.List;

	import javax.servlet.http.HttpSession;
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	import com.fasterxml.jackson.core.type.TypeReference;
	import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.budget.model.ManageDepartmentWebModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
	import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
	

	import org.slf4j.LoggerFactory;
	import org.slf4j.Logger;

	@Controller
	@RequestMapping(value ="budget")
	public class ManageDepartmentWebController {

		Logger logger = LoggerFactory.getLogger(ManageDepartmentWebController.class);

		@Autowired
		RestTemplate restClient;
		
		@Autowired
		RestTemplate restTemplate;

		@Autowired
		EnvironmentVaribles env;
		
		@Autowired
		PasswordEncoder passwordEncoder;

		@GetMapping(value = { "manage-department" })
		public String manageDepartment(Model model, HttpSession session) {
			logger.info("Method : manageDepartment starts");
		
			logger.info("Method : manageDepartment ends");
			return "budget/view-department";
		}
		
		//Add
				@SuppressWarnings("unchecked")
				@PostMapping("manage-department-add-dtls")
				public @ResponseBody JsonResponse<Object> addDepartmentInfo(@RequestBody ManageDepartmentWebModel 
						manageDepartmentWebModel, Model model,HttpSession session) {

					logger.info("Method : addDepartmentInfo starts" + manageDepartmentWebModel);

					System.out.println("resp web controller-----------------------------------" + manageDepartmentWebModel);
					
					String orgName = "";
					String orgDivision = "";
					String createdBy = "";
					try {
						
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
						createdBy = (String) session.getAttribute("USER_ID");
						
						manageDepartmentWebModel.setCreatedBy(createdBy);
						manageDepartmentWebModel.setOrgName(orgName);
						manageDepartmentWebModel.setOrgDivision(orgDivision);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String pass = manageDepartmentWebModel.getPassword();
					if (pass != null && pass != "") {
						pass = passwordEncoder.encode(pass);
						manageDepartmentWebModel.setPassword(pass);
					}
					
					System.out.println("password==>>>" + pass);


					JsonResponse<Object> resp = new JsonResponse<Object>();

					try {

						resp = restClient.postForObject(env.getBudgetUrl() + "restAddDepartmentInfo", manageDepartmentWebModel, JsonResponse.class);

					} catch (RestClientException e) {

						e.printStackTrace();
					}

					if (resp.getMessage() == "") {
						resp.setMessage("Success");
					}
					logger.info("Method : addDepartmentInfo ends" + resp);

					return resp;
				}
				
				//manage-department-through-json

				@SuppressWarnings("unchecked")

				@GetMapping("manage-department-through-json")
				public @ResponseBody Object viewDepartment(HttpSession session) {
					logger.info("Method :viewDepartment starts");
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
						resp = restTemplate.getForObject(
								env.getBudgetUrl() + "rest-viewDept?orgName=" + orgName + "&orgDivision=" + orgDivision,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

					
					System.out.println("view===" + resp);
					logger.info("Method :viewDepartment ends");
					return resp;
				}
				
				//view
			/*	
				@SuppressWarnings("unchecked")
				@GetMapping("manage-department-throughAjax")
				public @ResponseBody List<ManageDepartmentWebModel> viewDepartment(HttpSession session) {

					logger.info("Method : viewDepartment starts");

					JsonResponse<List<ManageDepartmentWebModel>> resp = new JsonResponse<List<ManageDepartmentWebModel>>();

					try {
						resp = restClient.getForObject(env.getShoukeenUrl() + "restViewShoukeenDepartment", JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					ObjectMapper mapper = new ObjectMapper();

					List<ManageDepartmentWebModel> ManageDepartmentWebModel = mapper.convertValue(resp.getBody(),
							new TypeReference<List<ManageDepartmentWebModel>>() {
							});

					resp.setBody(ManageDepartmentWebModel);
					System.out.println("resp.getBody()-----------" + resp.getBody());

					logger.info("Method : viewDepartment ends");
					return resp.getBody();
				}
*/
				
				//Edit
				
				
				@SuppressWarnings("unchecked")
				@GetMapping("manage-department-edit")
				public @ResponseBody JsonResponse<List<ManageDepartmentWebModel>> editDepartmentInfo(Model model,@RequestParam String departmentId, HttpSession session) {

					logger.info("Method : editDepartmentInfo starts" + departmentId);

					JsonResponse<List<ManageDepartmentWebModel>> jsonResponse = new JsonResponse<List<ManageDepartmentWebModel>>();

					try {
						jsonResponse = restClient.getForObject(env.getBudgetUrl() + "manage-department-edit?departmentId=" + departmentId,
								JsonResponse.class);

					} catch (Exception e) {
						e.printStackTrace();
					}

					ObjectMapper mapper = new ObjectMapper();
					
					 String date = ""; String drProfDoc = null; String dateFormat = (String)
					 (session).getAttribute("DATEFORMAT");
					

					List<ManageDepartmentWebModel> ManageDepartmentWebModel = mapper.convertValue(jsonResponse.getBody(),
							new TypeReference<List<ManageDepartmentWebModel>>() {
							});

					

					System.out.println("###" + ManageDepartmentWebModel);
					jsonResponse.setBody(ManageDepartmentWebModel);

					if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

					} else {
						jsonResponse.setMessage("Success");
					}

					System.out.println("REsp" + jsonResponse);
					logger.info("Method :editDepartmentInfo ends");
					return jsonResponse;
				}
				
				
				
	            //Delete
				
				@SuppressWarnings("unchecked")
				@GetMapping("manage-department-delete")
				public @ResponseBody JsonResponse<Object>deleteDepartmentInfo(@RequestParam String departmentId,
						 HttpSession session) {
					logger.info("Method : deleteDepartmentInfo function starts-------------"+departmentId);

					JsonResponse<Object> res = new JsonResponse<Object>();

					

					try {
						res = restClient.getForObject(env.getBudgetUrl() + "manage-department-delete?departmentId=" + departmentId  , JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					String message = res.getMessage();
					if (message != null && message != "") {

					} else {
						res.setMessage("Success");
					}
					logger.info("Method : deleteDepartmentInfo function Ends");
					
					System.out.println("Response"+res);
					return res;
				


			}



}
