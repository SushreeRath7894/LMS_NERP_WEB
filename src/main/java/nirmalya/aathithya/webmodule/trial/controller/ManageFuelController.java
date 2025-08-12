package nirmalya.aathithya.webmodule.trial.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.trial.model.ManageFuelWebModel;
@Controller
@RequestMapping(value = "trial")

public class ManageFuelController {
	
	Logger logger = LoggerFactory.getLogger(ManageFuelController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "manage-fuel" })
	
	public String manageRoles(Model model, HttpSession session) {
		
		try {

			DropDownModel[] regname = restTemplate.getForObject(env.getTrialUrl() + "get-vehiclereg-list",
					DropDownModel[].class);
			List<DropDownModel> vehicleregList = Arrays.asList(regname);

			model.addAttribute("vehicleregList", vehicleregList);
			System.out.println("#####" + vehicleregList);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		try {

			DropDownModel[] fuelname = restTemplate.getForObject(env.getTrialUrl() + "get-fuel-list",
					DropDownModel[].class);
			List<DropDownModel> fuelList = Arrays.asList(fuelname);

			model.addAttribute("fuelList", fuelList);
			System.out.println("$$$$" + fuelList);
		} catch (Exception e) {
			e.printStackTrace();

		}
		logger.info("Method : demo starts");

		logger.info("Method : demo ends");
		
		return "trial/managefuel";
	}
	
	// add details
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-fuel-add" })
		public @ResponseBody JsonResponse<Object> addfuelDetails(Model model, HttpSession session,
				@RequestBody ManageFuelWebModel fueldata) {
			logger.info("Method : addfuelDetails starts");

			JsonResponse<Object> res = new JsonResponse<Object>();
			
			String userId="";
			String dateFormat="";
			
			try {
				userId = (String) session.getAttribute("USER_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {

			}
					 
			fueldata.setCreatedBy(userId);
			
			if (fueldata.getFromDate() != null && fueldata.getFromDate() != "") {
				fueldata.setFromDate(DateFormatter.dateFormat(fueldata.getFromDate(), dateFormat));
			}
			
			if (fueldata.getToDate() != null && fueldata.getToDate() != "") {
				fueldata.setToDate(DateFormatter.dateFormat(fueldata.getToDate(), dateFormat));
			}
			
			try {
				res = restTemplate.postForObject(env.getTrialUrl() + "addfuel-Details", fueldata, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("Success");
			}

			logger.info("Method : addfuelDetails ends");
			System.out.println("WEB CONTROLLER ADD===" + res);
			return res;

	}
	
	// view details
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-fuel-view")
	
	public @ResponseBody List<ManageFuelWebModel> viewIssuedetails (HttpSession session) {
		
	logger.info("Method : viewIssueDetails starts");
	
	JsonResponse<List<ManageFuelWebModel>> resp = new JsonResponse<List<ManageFuelWebModel>>();
	List<ManageFuelWebModel> returnList = new ArrayList<ManageFuelWebModel>();

	try {
		resp = restTemplate.getForObject(env.getTrialUrl() + "viewfuel-Details", JsonResponse.class);
		returnList = resp.getBody();
	} catch (RestClientException e) {
		e.printStackTrace();
	}
	logger.info("Method : viewIssueDetails ends");
	
	System.out.println("adddd=======" + returnList);
	return returnList;	
	
	
	}
	
	// edit details

	
		@SuppressWarnings("unchecked")
		@GetMapping("manage-fuel-edit")
		public @ResponseBody JsonResponse<List<Object>> editIssueDetails(@RequestParam String id,
				HttpSession session) {
			logger.info("Method : editIssueDetails starts"+ id);

			JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

			try {
				resp = restTemplate.getForObject(env.getTrialUrl() + "editfuel-Details?id=" + id,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = resp.getMessage();

			if (message != null && message != "") {

			} else {
				resp.setMessage("success");
			}
			
			logger.info("Method : editIssueDetails starts"+ resp);
			return resp;
		}
		
		// delete details
		
		@SuppressWarnings("unchecked")
			@PostMapping("manage-fuel-delete")
			public @ResponseBody JsonResponse<Object> deleteIssueDetails(@RequestParam String id, Model model,
					HttpSession session) 
				{
				logger.info("Method : deleteIssue function starts");

				JsonResponse<Object> res = new JsonResponse<Object>();

				try {
					res = restTemplate.getForObject(env.getTrialUrl() + "deletefuel-Details?id=" + id,
							JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}
				logger.info("Method : deleteIssue function Ends");

				System.out.println("DELETE1" + res);
				return res;
			} 
	
	

}
