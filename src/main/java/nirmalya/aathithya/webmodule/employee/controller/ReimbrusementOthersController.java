package nirmalya.aathithya.webmodule.employee.controller;

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.ReimbrusementOtherModel;
import nirmalya.aathithya.webmodule.employee.model.ReimbursementModel;

@Controller
@RequestMapping(value = { "employee/" })
public class ReimbrusementOthersController {
	Logger logger = LoggerFactory.getLogger(ReimbrusementOthersController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;
	
	@GetMapping("reimbursement-other")
	public String reimbrusementOther(Model model, HttpSession session) {

		logger.info("Method : reimbrusementOther starts");
		
		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		/*
		 * Get Policy Type List
		 * 
		 */

		try {
			DropDownModel[] reimType = restTemplate.getForObject(env.getEmployeeUrl() + "getTypeLists",
					DropDownModel[].class);
			List<DropDownModel> reimbTypeLists = Arrays.asList(reimType);
			model.addAttribute("reimbTypeLists", reimbTypeLists);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : reimbrusementOther ends");
		return "employee/reimbursement-other";
	}
	/*
	 * save image
	 */

	@PostMapping("reimbursement-other-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		logger.info("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		logger.info("img" + response);
		return response;
	}

	/*
	 * Add Travel Reimbursement Details
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("reimbursement-other-add")
	public @ResponseBody JsonResponse<Object> addReimbursementOther(HttpSession session,

			@RequestBody ReimbrusementOtherModel reimbursementModel) {

		logger.info("Method : addReimbursementTravelDetails");
		logger.info("@@@ADDDDDDweb module" + reimbursementModel);
		String dateFormat = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			logger.info(dateFormat);

		} catch (Exception e) {
			e.printStackTrace();
		}
		reimbursementModel.setCreatedBy(userId);
		if (reimbursementModel.getDate() != null && reimbursementModel.getDate() != "") {
			reimbursementModel.setDate(DateFormatter.inputDateFormat(reimbursementModel.getDate(), dateFormat));
		}
		if (reimbursementModel.getExpenseDate() != null && reimbursementModel.getExpenseDate() != "") {
			reimbursementModel.setExpenseDate(DateFormatter.inputDateFormat(reimbursementModel.getExpenseDate(), dateFormat));
		}
		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				reimbursementModel.setDocName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// logger.info(attendanceModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsop" + resp);
		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "add-reimbursement-others", reimbursementModel,
					JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			  List<ReimbursementModel> quotation = mapper.convertValue(resp.getBody(), 
					  new TypeReference<List<ReimbursementModel>>() {
			  });
			  
			  resp.setBody(quotation);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addReimbursementTravelDetails ends");
		logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrreturn" + resp);
		return resp;
	}

	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
		
	}
	
	// View Travel Reimbrusiement

		@SuppressWarnings("unchecked")

		@GetMapping("/reimbursement-other-view")
		public @ResponseBody List<ReimbrusementOtherModel> viewReimbursementOther(HttpSession session) {

			logger.info("Method : viewReimbursementOther");

			JsonResponse<List<ReimbrusementOtherModel>> resp = new JsonResponse<List<ReimbrusementOtherModel>>();
			// logger.info("studentModellllllllllllllllllllllllll" + resp);
			try {
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "viewReimbursementOther", JsonResponse.class);
		}catch(

		RestClientException e)
		{
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		// logger.info("studentmappppppppppppppppppper" + mapper);

		List<ReimbrusementOtherModel> reimbursementModel=mapper.convertValue(resp.getBody(),new TypeReference<List<ReimbrusementOtherModel>>(){});
		String dateFormat = "";
		try
		{
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		}catch(
		Exception e)
		{

		} 
			  for(ReimbrusementOtherModel a:reimbursementModel) {
			  
			  // logger.info("for rach lllllllllooooooopppppppppp" + studentModel);
				  if (a.getDate() != null && a.getDate() != "") {
					  a.setDate(DateFormatter.dateFormat(a.getDate(), dateFormat)); }		  					  
			  if (a.getExpenseDate() != null && a.getExpenseDate() != "") {
			  a.setExpenseDate(DateFormatter.dateFormat(a.getExpenseDate(), dateFormat)); }
			  }

		if(resp.getMessage()!=""&&resp.getMessage()!=null)
		{
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		}else
		{
			resp.setMessage("Success");
		}

		logger.info("Method : viewReimbursementOther ends");
		logger.info("respwwwwwwwwwwwwwwwweb view"+resp);

		logger.info("studentModelwebview"+reimbursementModel); 
		return reimbursementModel;
	
		}
		/*
		 * for editing Reimbursement
		 * 
		 */

		@SuppressWarnings("unchecked")
		@GetMapping("reimbursement-other-edit")
		public @ResponseBody JsonResponse<ReimbrusementOtherModel> editReimbursementTravel(@RequestParam String id,
				HttpSession session) {
			logger.info(id);
			logger.info("Method : editReimbursementTravel starts");

			JsonResponse<ReimbrusementOtherModel> jsonResponse = new JsonResponse<ReimbrusementOtherModel>();

			try {
				jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "edit-rest-reimbursement-other?id=" + id,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			ReimbrusementOtherModel reimModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<ReimbrusementOtherModel>() {
					});
			String dateFormat = "";
			try {
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			if (reimModel.getDate() != null && reimModel.getDate() != "") {
				reimModel.setDate(DateFormatter.dateFormat(reimModel.getDate(), dateFormat));
			}
			
			if (reimModel.getExpenseDate() != null && reimModel.getExpenseDate() != "") {
				reimModel.setExpenseDate(DateFormatter.dateFormat(reimModel.getExpenseDate(), dateFormat));
			}
			

			jsonResponse.setBody(reimModel);
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				jsonResponse.setCode(jsonResponse.getMessage());
				jsonResponse.setMessage("Unsuccess");

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("Method : editReimbursementTravel ends");
			logger.info("EDITjsonResponse"+jsonResponse);
			return jsonResponse;
		}
		
		/*
		 *
		 * Delete Reimbursement
		 *
		 */
		
		@SuppressWarnings("unchecked")
		@GetMapping("reimbursement-other-delete-details")
		public @ResponseBody JsonResponse<ReimbrusementOtherModel> deleteReimbursementOther(@RequestParam String id) {
			logger.info(id);
			logger.info("Method : deleteReimbursementOther starts");

			JsonResponse<ReimbrusementOtherModel> jsonResponse = new JsonResponse<ReimbrusementOtherModel>();

			try {
				jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "deleteReimbursementOthers?id=" + id,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				jsonResponse.setCode(jsonResponse.getMessage());
				jsonResponse.setMessage("Unsuccess");

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("Method : deleteReimbursementOther ends");
			return jsonResponse;
		}
		
		// approve leave

				@SuppressWarnings("unchecked")
				@GetMapping("reimbursement-other-approve")
				public @ResponseBody JsonResponse<ReimbrusementOtherModel> approvereimbursementOtherApply(@RequestParam String approveId,
						String name, String comment,String roleid) {

					logger.info("Method : approvereimbursementOtherApply starts");
					
					logger.info("IDDD"+approveId);
					logger.info("NAME"+name);
					logger.info("comment"+comment);
					logger.info("ROLLLId"+roleid);
					JsonResponse<ReimbrusementOtherModel> response = new JsonResponse<ReimbrusementOtherModel>();
					try {
						response = restTemplate.getForObject(env.getEmployeeUrl() + "approvereimbursementOtherApply?id=" + approveId + "&name="
								+ name + "&comment=" + comment+"&roleid="+roleid, JsonResponse.class);

					} catch (RestClientException e) {
						e.printStackTrace();
					}
					if (response.getMessage() != null && response.getMessage() != "") {
						response.setCode(response.getMessage());
						response.setMessage("Unsuccess");
					} else {
						response.setMessage("Success");
					}
					logger.info("Method : approvereimbursementOtherApply ends");
					logger.info("ADDDDD"+response);
					
					
					return response;
				}

				// reject leave

				@SuppressWarnings("unchecked")
				@GetMapping("reimbursement-other-reject")
				public @ResponseBody JsonResponse<ReimbrusementOtherModel> rejectreimbursementOtherApply(@RequestParam String rejectId,
						String name, String comment) {

					logger.info("Method : rejectreimbursementOtherApply starts");
					
					logger.info("IDDD"+rejectId);
					logger.info("NAME"+name);
					logger.info("comment"+comment);
					//logger.info("ROLLLId"+roleid);
					JsonResponse<ReimbrusementOtherModel> response = new JsonResponse<ReimbrusementOtherModel>();
					try {
						response = restTemplate.getForObject(env.getEmployeeUrl() + "rejectreimbursementOtherApply?id=" + rejectId + "&name=" + name + "&comment=" + comment,
								JsonResponse.class);

					} catch (RestClientException e) {
						e.printStackTrace();
					}
					if (response.getMessage() != null && response.getMessage() != "") {
						response.setCode(response.getMessage());
						response.setMessage("Unsuccess");
					} else {
						response.setMessage("Success");
					}
					logger.info("Method : rejectreimbursementOtherApply ends");
					logger.info("ADDDELETE"+response);
					return response;
				}
			
			
			
}
