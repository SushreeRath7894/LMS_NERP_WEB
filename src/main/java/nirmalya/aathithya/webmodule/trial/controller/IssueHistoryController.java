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
import nirmalya.aathithya.webmodule.trial.model.IssueHistoryWebModel;


@Controller
@RequestMapping(value = "trial")

public class IssueHistoryController {
	
	Logger logger = LoggerFactory.getLogger(IssueHistoryController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "issue-history" })
	public String manageRoles(Model model, HttpSession session) {
		
		try {

			DropDownModel[] vehiclename = restTemplate.getForObject(env.getTrialUrl() + "get-vehicle-list",
					DropDownModel[].class);
			List<DropDownModel> vehicleList = Arrays.asList(vehiclename);

			model.addAttribute("vehicleList", vehicleList);
			System.out.println("#####" + vehicleList);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		try {

			DropDownModel[] issuename = restTemplate.getForObject(env.getTrialUrl() + "get-issue-list",
					DropDownModel[].class);
			List<DropDownModel> issuergList = Arrays.asList(issuename);

			model.addAttribute("issuergList", issuergList);
			System.out.println("$$$$" + issuergList);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		try {

			DropDownModel[] vendorname = restTemplate.getForObject(env.getTrialUrl() + "get-vendor-list",
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendorname);

			model.addAttribute("vendorList", vendorList);
			System.out.println("$$$$" + vendorList);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		
		logger.info("Method : demo starts");

		logger.info("Method : demo ends");
		
		return "trial/issuehistory";
	}
	
	// UPLOAD
	
	@PostMapping("issue-history-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		System.out.println("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("issueRegFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		System.out.println("img" + response);
		return response;
	}
		
	// add details
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "issue-history-add" })
		public @ResponseBody JsonResponse<Object> addvehicleDetails(Model model, HttpSession session,
				@RequestBody IssueHistoryWebModel vehicledata) {
			logger.info("Method : addvehicleDetails starts");

			JsonResponse<Object> res = new JsonResponse<Object>();
			
			String userId="";
			String dateFormat="";
			
			try {
				userId = (String) session.getAttribute("USER_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {

			}
					 
			vehicledata.setCreatedBy(userId);
			
			if (vehicledata.getFromDate() != null && vehicledata.getFromDate() != "") {
				vehicledata.setFromDate(DateFormatter.dateFormat(vehicledata.getFromDate(), dateFormat));
			}
			
			// upload part 
			
			MultipartFile inputFile = (MultipartFile) session.getAttribute("issueRegFile");
			byte[] bytes;
			String imageName = null;

			if (inputFile != null) {
				try {
					bytes = inputFile.getBytes();
					String[] fileType = inputFile.getContentType().split("/");
					imageName = saveAllImage(bytes, fileType[1]);

					vehicledata.setDocName(imageName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} //upload part
			
			try {
				res = restTemplate.postForObject(env.getTrialUrl() + "addvehicle-Details", vehicledata, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("Success");
			}

			logger.info("Method : addvehicleDetails ends");
			System.out.println("adddd=======" + res);
			return res;

	}
	
	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName1 = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}

			}
			Path path = Paths.get(env.getFileUploadTrialUrl() + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName1;
		
	}
	// view issue register

	@SuppressWarnings("unchecked")
	@GetMapping("issue-history-view")
	
	public @ResponseBody List<IssueHistoryWebModel> viewIssuedetails (HttpSession session) {
		
	logger.info("Method : viewIssueDetails starts");
	
	JsonResponse<List<IssueHistoryWebModel>> resp = new JsonResponse<List<IssueHistoryWebModel>>();
	List<IssueHistoryWebModel> returnList = new ArrayList<IssueHistoryWebModel>();

	try {
		resp = restTemplate.getForObject(env.getTrialUrl() + "viewvehicle-Details", JsonResponse.class);
		ObjectMapper mapper = new ObjectMapper();
		returnList = mapper.convertValue(resp.getBody(),
				new TypeReference<List<IssueHistoryWebModel>>() {
				});
//		returnList = resp.getBody();
		for(IssueHistoryWebModel m : returnList) {
			if(m.getDocName() != null && m.getDocName() != "") {
				String docUrl = env.getBaseURL() + "document/issueHistory/"+m.getDocName();
				m.setDocUrl(docUrl);
			}
		}
	} catch (RestClientException e) {
		e.printStackTrace();
	}
	logger.info("Method : viewIssueDetails ends");
	
	System.out.println("adddd=======" + returnList);
	return returnList;	
	
	
	}
	
	// edit issue register
	
	@SuppressWarnings("unchecked")
	@GetMapping("issue-history-edit")
	public @ResponseBody JsonResponse<List<Object>> editIssueDetails(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editIssueDetails starts"+ id);

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getTrialUrl() + "editIssue-Details?id=" + id,
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
	
	// delete issue register
	
	@SuppressWarnings("unchecked")
		@PostMapping("issue-history-delete")
		public @ResponseBody JsonResponse<Object> deleteIssueDetails(@RequestParam String id, Model model,
				HttpSession session) 
			{
			logger.info("Method : deleteIssue function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			try {
				res = restTemplate.getForObject(env.getTrialUrl() + "deleteIssue-Details?id=" + id,
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
