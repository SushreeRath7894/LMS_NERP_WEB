package nirmalya.aathithya.webmodule.master.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.GoalReviewDocumentModel;
import nirmalya.aathithya.webmodule.master.model.GoalReviewModel;

@Controller
@RequestMapping(value = "master")
public class GoalReviewController {
	Logger logger = LoggerFactory.getLogger(GoalReviewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/view-goal-review")
	public String reviewPage(Model model, HttpSession session) {
		logger.info("Method : reviewPage starts");
		
		String userId = "";
		String userRole = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
        String[] removedNull = Arrays.stream(splitData)
                .filter(value ->value != "" && value.length() > 0)
                .toArray(size -> new String[size]);
		for(String part:removedNull ) {
			String data= "r"+part;
			
			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			} 
		}
	
		model.addAttribute("userId", userId);
		model.addAttribute("userRole", userRole);
		
		// drop down for employee list
				try {
					DropDownModel[] employee = restTemplate.getForObject(env.getMasterUrl() + "getempList?id="+userId, DropDownModel[].class);
					List<DropDownModel> emplists = Arrays.asList(employee);
					model.addAttribute("emplists", emplists);
					logger.info("emplists==="+emplists);
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				
		logger.info("Method : reviewPage ends");
		return "master/review";
	}
	
	/*
	 * view review employee list
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-review-employeelist")
	public @ResponseBody List<GoalReviewModel> viewEmployeeList(HttpSession session) {
		logger.info("Method : viewExploreData starts");
	
		JsonResponse<List<GoalReviewModel>> resp = new JsonResponse<List<GoalReviewModel>>();
		
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "view-emp-list?userId=" + userId,JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<GoalReviewModel> returnList = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GoalReviewModel>>() {
				});

		String dateFormat = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}

		if(returnList!= null)
		// leaveapply.setEmpID(userId);
		for (GoalReviewModel a : returnList) {
			if (a.getDob() != null && a.getDob() != "") {
				a.setDob(DateFormatter.dateFormat(a.getDob(), dateFormat));
			}
		}
		logger.info("Method : viewExploreData ends");
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-review-empband")
	public @ResponseBody JsonResponse<List<GoalReviewModel>> viewEmployeeBandList(HttpSession session,String id) {
		logger.info("Method : viewExploreData starts");
	
		JsonResponse<List<GoalReviewModel>> resp = new JsonResponse<List<GoalReviewModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "view-empband?id="+id, JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : viewExploreData ends");
		return resp;
	}
	
	/*
	 * view review goal list
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-review-goallist")
	public @ResponseBody List<GoalReviewModel> viewGoalList(HttpSession session,String id) {
		logger.info("Method : viewGoalList starts");
	
		JsonResponse<List<GoalReviewModel>> resp = new JsonResponse<List<GoalReviewModel>>();
		List<GoalReviewModel> returnList = new ArrayList<GoalReviewModel>();
	
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "view-goal-list?id="+id, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewGoalList ends");
		return returnList;
	}
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-goal-review-save")
	public @ResponseBody JsonResponse<Object> saveGoalReview(@RequestBody GoalReviewModel goalReviewModel,
			HttpSession session) {
		logger.info("Method : saveLeaveApply function starts");

		//logger.info(leaveModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
	
		logger.info("goalReviewModel====="+goalReviewModel);
		try {

			resp = restTemplate.postForObject(env.getMasterUrl() + "save-goal-review", goalReviewModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
		} else {
			resp.setMessage("Success");
		}
		//logger.info("WEBBB" + resp);
		logger.info("Method : saveLeaveApply function Ends");

		return resp;
	}
	/*
	 * view review goal list
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-review-reviewlist")
	public @ResponseBody List<GoalReviewModel> viewReviewList(HttpSession session,String id) {
		logger.info("Method : viewReviewList starts");
	
		JsonResponse<List<GoalReviewModel>> resp = new JsonResponse<List<GoalReviewModel>>();
		List<GoalReviewModel> returnList = new ArrayList<GoalReviewModel>();
	
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "view-review-list?id="+id, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewReviewList ends");
		return returnList;
	}
	// delete review

		@SuppressWarnings("unchecked")
		@GetMapping("view-goal-review-delete")
		public @ResponseBody JsonResponse<GoalReviewModel> deleteReview(@RequestParam String deleteId) {

			logger.info("Method : deleteReview starts");
			logger.info(deleteId);
			JsonResponse<GoalReviewModel> response = new JsonResponse<GoalReviewModel>();

			try {
				response = restTemplate.getForObject(env.getMasterUrl() + "delete-review?id=" + deleteId,
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

			logger.info("Method : deleteReview ends");
			return response;
		}
		/*
		 * Add Meeting Schedule
		 *
		 */
		@SuppressWarnings("unchecked")
		@PostMapping(value = "view-goal-review-save-meetingDetails")
		public @ResponseBody JsonResponse<Object> addMeetingScedule(
				@RequestBody List<GoalReviewModel> goalreviewModel, HttpSession session, Model model) {
			logger.info("Method : addMeetingScedule function starts");
			logger.info("goalReviewModel==="+goalreviewModel);
			JsonResponse<Object> res = new JsonResponse<Object>();
			List<GoalReviewModel> noticeList = new ArrayList<GoalReviewModel>();
			List<GoalReviewDocumentModel> docList = new ArrayList<GoalReviewDocumentModel>();
			String userId = "";
			String dateFormat = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (GoalReviewModel m : goalreviewModel) {
				m.setCreatedBy(userId);
				m.setMeetingDate(DateFormatter.inputDateFormat(m.getMeetingDate(), dateFormat));
			}
			

				for (GoalReviewDocumentModel a : goalreviewModel.get(0).getDocumentList()) {

					if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
						a.setFileName(a.getImageNameEdit());
					} else {
						if (a.getFileName() != null && a.getFileName() != "") {
							String[] extension = a.getFileName().split("\\.");
							int lastindex = extension.length - 1;
								for (String s1 : a.getDocumentFile()) {
										try {
											byte[] bytes = Base64.getDecoder().decode(s1);
											String imageName = saveAllGoalReviewMeetingImage(bytes, extension[lastindex]);
											a.setFileName(imageName);
										} catch (Exception e) {
											e.printStackTrace();
										}
								}
						}
					}
				}
			try {

				res = restTemplate.postForObject(env.getMasterUrl() + "rest-save-meetingdetails", goalreviewModel,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			String message = res.getMessage();
			goalreviewModel.get(0).setDocumentList(docList);
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			res.setBody(noticeList);
			logger.info("Method : addMeetingScedule function Ends");
			return res;
		}
		public String saveAllGoalReviewMeetingImage(byte[] imageBytes, String ext) {
			logger.info("Method : saveAllDoctorProfileImage starts");
			
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
				logger.info("image==1 "+imageName1);
				Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
				if (imageBytes != null) {
					Files.write(path, imageBytes);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : saveAllDoctorProfileImage ends");
			return imageName1;
		}
		/*
		 * view review employee list
		 */
		@SuppressWarnings("unchecked")
		@GetMapping("/view-goal-review-meetingdetails")
		public @ResponseBody List<GoalReviewModel> viewMeetingDetails(HttpSession session) {
			logger.info("Method : viewMeetingDetails starts");
		
			JsonResponse<List<GoalReviewModel>> resp = new JsonResponse<List<GoalReviewModel>>();
			//List<GoalReviewModel> returnList = new ArrayList<GoalReviewModel>();
			
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "view-meeting-list",JsonResponse.class);
			
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();

			List<GoalReviewModel> returnList = mapper.convertValue(resp.getBody(),
					new TypeReference<List<GoalReviewModel>>() {
					});

			String dateFormat = "";
			try {

				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {

			}

			if(returnList!= null)
			// leaveapply.setEmpID(userId);
			for (GoalReviewModel a : returnList) {
				if (a.getMeetingDate() != null && a.getMeetingDate() != "") {
					a.setMeetingDate(DateFormatter.dateFormat(a.getMeetingDate(), dateFormat));
				}
			}
			logger.info("Method : viewMeetingDetails ends");
			return returnList;
		}
}
