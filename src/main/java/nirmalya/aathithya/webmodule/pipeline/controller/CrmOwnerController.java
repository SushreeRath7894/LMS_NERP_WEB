package nirmalya.aathithya.webmodule.pipeline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmOwnerModel;

@RequestMapping( value = "pipeline")
@Controller
public class CrmOwnerController {

	Logger logger = LoggerFactory.getLogger(CrmOwnerController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	/* 
	 * get mapping for view-crm-contracts
	 * 
	 */
	
	@GetMapping("/view-crm-owner")
	public String viewCrmOwner(Model model, HttpSession session) {
		logger.info("Method : viewCrmOwner start");
		
		try {
			DropDownModel[] industry = restClient.getForObject(env.getPipeline() + "/getindustrylist",
					DropDownModel[].class);

			List<DropDownModel> industryList = Arrays.asList(industry);
			model.addAttribute("industryList", industryList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] source = restClient.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : viewCrmOwner end");
		return "pipeline/crm-owner";
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-crm-owner-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getPipeline() + "getStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getstateListAJAX ends");
		return res;
	}
	
	/*
	 * Post Mapping for adding new assignSkill
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-owner-add-owner-details")
	public @ResponseBody JsonResponse<Object> addCRMOwnerDetails(@RequestBody CrmOwnerModel crmModel, Model model,
			HttpSession session) {

		logger.info("Method : addCRMOwnerDetails starts" + crmModel);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("employeePFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				// logger.info(imageName);

				crmModel.setImageName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {

			resp = restClient.postForObject(env.getPipeline() + "rest-add-owner-details", crmModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addCRMOwnerDetails ends" + resp);

		return resp;
	}
	
	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;
		Path imagePath = null;
		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}
			
			Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
				// crm.setImagePath(path);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends" + imagePath);
		return imageName;
	}
	
	/*
	 * upload image
	 */

	@PostMapping("/view-owner-leads-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : employee uploadimage controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("employeePFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : employee uploadimage controller ' ends" + response);
		return response;
	}

	@PostMapping("/view-crm-owner-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile employee uploadimage controller starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.removeAttribute("employeePFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile employee uploadimage controller ends");
		return response;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-owner-view-Data")
	public @ResponseBody List<CrmOwnerModel> viewItemCategory(HttpSession session) {
		logger.info("Method : View starts");

		JsonResponse<List<CrmOwnerModel>> resp = new JsonResponse<List<CrmOwnerModel>>();
		List<CrmOwnerModel> returnList = new ArrayList<CrmOwnerModel>();


		try {
			resp = restClient.getForObject(env.getPipeline() + "rest-view-owner-details", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();

		List<CrmOwnerModel> quotationNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmOwnerModel>>() {
				});

		for (CrmOwnerModel i : quotationNewModel) {
			if (i.getImageName() != null && i.getImageName() != "") {
				 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
				
				logger.info("Image------------------------" + fileEmployeeimg);
				i.setImageName(fileEmployeeimg); 
			}
		}

		

		/*
		 * ObjectMapper mapper = new ObjectMapper();
		 * 
		 * CrmLeadsModel empDetails = mapper.convertValue(resp.getBody(), new
		 * TypeReference<CrmLeadsModel>() { }); if (empDetails.getImageName() != null &&
		 * empDetails.getImageName() != "" && !empDetails.getImageName().equals("null"))
		 * { String fileEmployeeimg = env.getBaseURL() + "document/document/" +
		 * empDetails.getImageName();
		 * 
		 * empDetails.setImageName(fileEmployeeimg); }
		 */

		if (resp.getMessage() == null) {
			resp.setMessage("Success");
		}

		logger.info("views" + resp);
		logger.info("Method : View ends");
		return quotationNewModel;
	}
	
	
	
	/// edit
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-owner-editDetails")
		public @ResponseBody JsonResponse<List<CrmOwnerModel>> editOwnerInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : editOwnerInfo starts" + id);

			JsonResponse<List<CrmOwnerModel>> jsonResponse = new JsonResponse<List<CrmOwnerModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPipeline() + "edit-rest-OwnerInfo?id=" + id,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			

			ObjectMapper mapper = new ObjectMapper();

			List<CrmOwnerModel> quotationNewModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<CrmOwnerModel>>() {
					});
			String drProfDoc = null;
			for (CrmOwnerModel i : quotationNewModel) {
				String date = "";
				String dateFormat = (String) (session).getAttribute("DATEFORMAT");
				if (i.getOwnerDob() != null && i.getOwnerDob() != "") {
					date = DateFormatter.dateFormat(i.getOwnerDob(), dateFormat);
					i.setOwnerDob(date);
					logger.info("state value---------------"+i.getStates());
				}
				if (i.getImageName() != null && i.getImageName() != "") {
					// logger.info("$$$$$"+env.getuploadImageCrm());
					//&& !quotationNewModel.get(0).getImageName().equals("null")
					// drProfDoc = env.getBaseURL() + "fileupload/document/"+i.getImageName();
					//drProfDoc = env.getuploadImageCrm() + i.getImageName();
					// logger.info("%%%"+drProfDoc); i.setImageName(drProfDoc);
					 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
					
					logger.info("Image" + fileEmployeeimg);
					i.setImageName(fileEmployeeimg); 
				}
			}

			logger.info("###" + quotationNewModel);
			jsonResponse.setBody(quotationNewModel);

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : editOwnerInfo ends");
			return jsonResponse;
		}
		
		
		
		/*
//		 * delete details
//		 */
	//
		@SuppressWarnings("unchecked")
		@PostMapping(value = "view-crm-owner-delete")
		public @ResponseBody JsonResponse<Object> deleteSchedule(
				@RequestBody CrmOwnerModel scheduleModel, HttpSession session) {
			logger.info("Method : deleteSchedule function starts"+scheduleModel.getOwnerId());
			JsonResponse<Object> res = new JsonResponse<Object>();
			
			
		
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				scheduleModel.setCreatedBy(userId);
			} catch (Exception e) {

			}
			try {

				res = restClient.postForObject(env.getPipeline() + "rest-delete-OwnerInfo", scheduleModel,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();

			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteSchedule function Ends"+res);
			return res;
		}
	
	
	
	
}
