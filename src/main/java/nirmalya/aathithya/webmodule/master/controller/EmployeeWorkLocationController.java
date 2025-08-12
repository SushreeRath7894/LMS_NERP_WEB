package nirmalya.aathithya.webmodule.master.controller;

import java.util.ArrayList;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmployeeWorkLocation;

@Controller

@RequestMapping(value = { "master/" })
public class EmployeeWorkLocationController {
	
		Logger logger = LoggerFactory.getLogger(EmployeeWorkLocationController.class);

		@Autowired
		RestTemplate restTemplate;

		@Autowired
		EnvironmentVaribles env;

		@GetMapping(value = { "work-location" })

		public String employeeLocation(Model model, HttpSession session) {
			logger.info("Method :EmployeeWorkLocation starts");
			
			try {

				DropDownModel[] list = restTemplate.getForObject(env.getMasterUrl() + "get-employee-List",
						DropDownModel[].class);
				List<DropDownModel> employeeId = Arrays.asList(list);

				model.addAttribute("employeeId", employeeId);
			} catch (Exception e) {
				e.printStackTrace();

			}

			logger.info("Method : EmployeeWorkLocation ends");
			return "master/employeeWorkLocation";
		}
		
		//get employee lists
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "work-location-employee-list" })
		public @ResponseBody JsonResponse<Object> getEmppLists(HttpSession session,@RequestParam String type) {
			logger.info("Method : getEmppLists starts");
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getMasterUrl() + "get-getEmployeeList?type=" + type
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getCode().equals("success")) {
				res.setMessage("success");
			} else {
				res.setMessage("Unsuccess");
			}
			logger.info("Method : getEmppLists ends");
			logger.info("LISTTTT" + res);
			return res;
		}
		/*
		 * get name list
		 */
		@SuppressWarnings("unchecked")

		@GetMapping(value = { "work-location-name-ajax" })
		public @ResponseBody JsonResponse<Object> getnameList(@RequestParam String name) {
			logger.info("Method : getnameList starts");

			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getMasterUrl() + "get-nameList?id=" + name, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}

			logger.info("Method : getnameList ends");
			
			logger.info("LISTTTT" + res);
			return res;

		}

		/*
		 *
		 * add
		 * 
		 */

		@SuppressWarnings("unchecked")
		@PostMapping(value = { "work-location-add" })
		public @ResponseBody JsonResponse<Object> addWorkLocation(Model model, HttpSession session,
				@RequestBody EmployeeWorkLocation data) {
			logger.info("Method : addWorkLocation starts");
			JsonResponse<Object> res = new JsonResponse<Object>();
			String userId = "";
			String organization=""; 
			String orgDivision="";
			try {
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			data.setOrganization(organization);
			data.setOrgDivision(orgDivision);
			logger.info("Method : addWorkLocation starts"+ data);
			try {
				userId = (String) session.getAttribute("USER_ID");
				data.setCreatedBy(userId);
				res = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-worklocation", data, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			logger.info("Method : addWorkLocation ends");
			logger.info("adddd=======" + res);
			return res;
		}
		
		// view

		@SuppressWarnings("unchecked")
		@GetMapping("work-location-view")
		public @ResponseBody List<EmployeeWorkLocation> viewWorkLocation(HttpSession session) {
			logger.info("Method : viewWorkLocation starts");
			String organization=""; 
			String orgDivision="";

			try {
			
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			JsonResponse<List<EmployeeWorkLocation>> resp = new JsonResponse<List<EmployeeWorkLocation>>();
			List<EmployeeWorkLocation> returnList = new ArrayList<EmployeeWorkLocation>();

			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "EmployeeWorkLocation?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
				returnList = resp.getBody();
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("VIEWWWWWWWWWWWWWW" + returnList);
			logger.info("Method : viewWorkLocation ends");
			return returnList;
		}
		
		// edit 

		@SuppressWarnings("unchecked")
		@GetMapping("work-location-edit")
		public @ResponseBody JsonResponse<EmployeeWorkLocation> editEmployeeWorkLocation(@RequestParam String Id,
				HttpSession session) {

			logger.info("Method : editEmployeeWorkLocation starts");
			JsonResponse<EmployeeWorkLocation> jsonResponse = new JsonResponse<EmployeeWorkLocation>();
			logger.info("id===="+Id);

			try {
				jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "rest-EmployeeWorkLocation-edit?id=" + Id,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			EmployeeWorkLocation Model = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<EmployeeWorkLocation>() {
					});
			jsonResponse.setBody(Model);
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				jsonResponse.setCode(jsonResponse.getMessage());
				jsonResponse.setMessage("Unsuccess");
			} else {
				jsonResponse.setMessage("Success");
			}
			logger.info("Method : editEmployeeWorkLocation ends");
			logger.info("editEmployeeWorkLocation====="+jsonResponse);
			return jsonResponse;
		}
		
		
		// delete
		@SuppressWarnings("unchecked")
		@PostMapping("work-location-delete")
		public @ResponseBody JsonResponse<Object> deleteEmployeeWorkLocation(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : deleteEmployeeWorkLocation function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				res = restTemplate.getForObject(env.getMasterUrl() + "rest-EmployeeWorkLocation-delete?id=" + id,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteEmployeeWorkLocation function Ends");

			logger.info("RESPPPPPPP" + res);
			return res;
		}

}
