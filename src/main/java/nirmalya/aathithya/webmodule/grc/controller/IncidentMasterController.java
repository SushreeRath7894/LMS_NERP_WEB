package nirmalya.aathithya.webmodule.grc.controller;

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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.grc.model.IncidentMasterModel;

@Controller
@RequestMapping(value = "grc")
public class IncidentMasterController {
	
	Logger logger = LoggerFactory.getLogger(IncidentMasterController.class);
	
	
	@Autowired RestTemplate restTemplate;
	
	@Autowired EnvironmentVaribles env;
	 
		
		@GetMapping("/incident-master")
		public String IncidentPage(Model model, HttpSession session) {
			logger.info("Method : IncidentPage method starts");
			
			logger.info("Method : IncidentPage method ends");
			return "grc/incident-master.html";
		}
		
		
		/* add  */
		

		@SuppressWarnings("unchecked")
		@PostMapping(value = { "incident-master-add" })
		public @ResponseBody JsonResponse<Object> addIncidentMaster(HttpSession session, @RequestBody IncidentMasterModel data) {
			logger.info("Method : addIncidentMaster starts");

			JsonResponse<Object> res = new JsonResponse<Object>();
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
			data.setCreatedBy(userId);
			data.setOrganization(organization);
			data.setOrgDivision(orgDivision);
			try {
				res = restTemplate.postForObject(env.getGrcUrl() + "incident-master-rest-add", data, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}

			logger.info("Method :addIncidentMaster ends");
			return res;

		}
		
		/* view */
		
		@SuppressWarnings("unchecked")

		@GetMapping("/incident-master-view")
		public @ResponseBody List<IncidentMasterModel> viewIncidentMaster(HttpSession session) {

			logger.info("Method : view Start");

			JsonResponse<List<IncidentMasterModel>> resp = new JsonResponse<List<IncidentMasterModel>>();
			
			try {
				resp = restTemplate.getForObject(env.getGrcUrl() + "incident-master-rest-view" , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<IncidentMasterModel> viewIncidentMaster = mapper.convertValue(resp.getBody(),
					new TypeReference<List<IncidentMasterModel>>() {
					});

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method : view ends");
			return viewIncidentMaster;

		}
		
		
		/* edit */
		
		@SuppressWarnings("unchecked")

		@GetMapping("/incident-master-edit")
		public @ResponseBody JsonResponse<IncidentMasterModel> editIncidentMaster(@RequestParam String Id, HttpSession session) {

			logger.info("Method : editIncidentMaster starts");
			JsonResponse<IncidentMasterModel> jsonResponse = new JsonResponse<IncidentMasterModel>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USERID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "incident-master-rest-edit?id=" + Id+
						"&orgName=" +orgName+"&orgDivision=" +orgDivision + "&uId=" + userId, JsonResponse.class);				
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();
			IncidentMasterModel Model = mapper.convertValue(jsonResponse.getBody(), new TypeReference<IncidentMasterModel>() {
			});
			
			jsonResponse.setBody(Model);
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				jsonResponse.setCode(jsonResponse.getMessage());
				jsonResponse.setMessage("Unsuccess");
			} else {
				jsonResponse.setMessage("Success");
			}
			logger.info("Method : editIncidentMaster ends");
			return jsonResponse;
		}
		
		
		/* delete */
		
		@SuppressWarnings("unchecked")

		@GetMapping("incident-master-delete")
		public @ResponseBody JsonResponse<Object> deleteIncidentMaster(@RequestParam String id, Model model,HttpSession session) {
			logger.info("Method : deleteIncidentMaster function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();
			
			
			try {
				res = restTemplate.getForObject(env.getGrcUrl() + "incident-master-rest-delete?id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteIncidentMaster function Ends");
			return res;
		}
		

}
