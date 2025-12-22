package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.ActivityTypeModel;
import nirmalya.aathithya.webmodule.pipeline.model.PipelineLogModel;
import nirmalya.aathithya.webmodule.pipeline.model.PipelineLostReasonModel;
import nirmalya.aathithya.webmodule.pipeline.model.PipelineMeetingModel;
import nirmalya.aathithya.webmodule.pipeline.model.PipelineModel;
import nirmalya.aathithya.webmodule.pipeline.model.PipelineSmsModel;
import nirmalya.aathithya.webmodule.pipeline.model.StagesDetailModel;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class PipelineController {
	Logger logger = LoggerFactory.getLogger(PipelineController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	/*
	 * get mapping for adding campaign name
	 */

	@GetMapping("/view-crm-pipeline")
	public String viewPipeLine(Model model, HttpSession session) {
		logger.info("Method : viewCrmPipeLine start");
		PipelineModel PipelineModel = new PipelineModel();
		PipelineModel form = (PipelineModel) session.getAttribute("sPipelineModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		session.setAttribute("message", "");
		if (form != null) {
			model.addAttribute("PipelineModel", form);
			session.setAttribute("sPipelineModel", null);

		} else {
			model.addAttribute("PipelineModel", PipelineModel);
		}

		try {
			DropDownModel[] customer = restClient.getForObject(env.getPipeline() + "/getCustomer",
					DropDownModel[].class);

			List<DropDownModel> customerList = Arrays.asList(customer);
			logger.info("customerList" + customerList);
			model.addAttribute("customerList", customerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] salesPerson = restClient.getForObject(env.getPipeline() + "/getSalesPerson",
					DropDownModel[].class);

			List<DropDownModel> salesPersonList = Arrays.asList(salesPerson);
			model.addAttribute("salesPersonList", salesPersonList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] salesTeam = restClient.getForObject(env.getPipeline() + "/getSalesTeam",
					DropDownModel[].class);

			List<DropDownModel> salesTeamList = Arrays.asList(salesTeam);
			model.addAttribute("salesTeamList", salesTeamList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] tags = restClient.getForObject(env.getPipeline() + "/getTags", DropDownModel[].class);

			List<DropDownModel> tagList = Arrays.asList(tags);
			model.addAttribute("tagList", tagList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] Company = restClient.getForObject(env.getPipeline() + "/getCompanyList",
					DropDownModel[].class);

			List<DropDownModel> companyList = Arrays.asList(Company);
			model.addAttribute("companyList", companyList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		try {
			DropDownModel[] states = restClient.getForObject(env.getPipeline() + "/getStatesList",
					DropDownModel[].class);

			List<DropDownModel> statesList = Arrays.asList(states);
			model.addAttribute("statesList", statesList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		try {
			DropDownModel[] countries = restClient.getForObject(env.getPipeline() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(countries);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] Tittle = restClient.getForObject(env.getPipeline() + "/getTittleList",
					DropDownModel[].class);

			List<DropDownModel> tittleList = Arrays.asList(Tittle);
			model.addAttribute("tittleList", tittleList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();

		}
		try {
			DropDownModel[] Campaign = restClient.getForObject(env.getPipeline() + "/getCampaignList",
					DropDownModel[].class);

			List<DropDownModel> campaignList = Arrays.asList(Campaign);
			model.addAttribute("campaignList", campaignList);
		} catch (RestClientException e)

		{ // TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] medium = restClient.getForObject(env.getPipeline() + "/getMediumList",
					DropDownModel[].class);

			List<DropDownModel> mediumList = Arrays.asList(medium);
			model.addAttribute("mediumList", mediumList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		try {
			DropDownModel[] source = restClient.getForObject(env.getPipeline() + "/getSourceList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("sourceList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();

		}
		try {
			DropDownModel[] language = restClient.getForObject(env.getPipeline() + "/getLanguageList",
					DropDownModel[].class);

			List<DropDownModel> languageList = Arrays.asList(language);
			model.addAttribute("languageList", languageList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] lostReason = restClient.getForObject(env.getPipeline() + "/getLostReasonList",
					DropDownModel[].class);

			List<DropDownModel> lostReasonList = Arrays.asList(lostReason);
			model.addAttribute("lostReasonList", lostReasonList);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewCrmPipeLine end");
		return "pipeline/view-crm-pipeLine";
	}



	
	
	/*
	 * get mapping for CRM Home
	 */

	@GetMapping("/view-crm-home")
	public String viewCrmHome(Model model, HttpSession session) {
		logger.info("Method : viewCrmHome start1111111111111111111111111111");

		logger.info("Method : viewCrmHome end2222222222222222222222222222222");
		return "pipeline/crm-dashboard";
	}
	
	
	/*
	 * post mapping for adding pipeline
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-pipeline-ajax")
	public @ResponseBody JsonResponse<Object> addPipeline(@RequestBody PipelineModel pipelineModel,
			HttpSession session) {

		logger.info("Method : addPipeline starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web add ======================" + pipelineModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			pipelineModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getPipeline() + "/addPipeline", pipelineModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addPipeline ends");

		return resp;
	}
	/*
	 * post Mapping for autocomplete contact name
	 */

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "/view-crm-pipeline-contactAutoComplete" })
	public @ResponseBody JsonResponse<PipelineModel> getAutocomplete(Model model, @RequestParam String id) {
		logger.info("Method : getAutocomplete starts");
		logger.info(id);
		JsonResponse<PipelineModel> res = new JsonResponse<PipelineModel>();
		logger.info(env.getPipeline() + "/getContactNameAutoComplete");
		try {
			res = restClient.getForObject(env.getPipeline() + "/getContactNameAutoComplete?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			logger.info("if block getmsg() not false : " + res.getMessage());
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getAutocomplete ends");
		return res;
	}

	/*
	 * post mapping for adding pipeline
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-pipeline-reason-ajax")
	public @ResponseBody JsonResponse<Object> addreasonPipeline(
			@RequestBody PipelineLostReasonModel pipelinereasonModel, HttpSession session) {

		logger.info("Method : addreasonPipeline starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web add ======================" + pipelinereasonModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			pipelinereasonModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getPipeline() + "/addreasonPipeline", pipelinereasonModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addreasonPipeline ends");

		return resp;
	}

	/*
	 * View all pipeline
	 *
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-pipeline-throughAjax")
	public @ResponseBody List<PipelineModel> viewAllPipeline() {

		logger.info("Method : viewAllAgentTicket starts");

		// logger.info("fvsjkbhkfvsjk");

		JsonResponse<List<PipelineModel>> resp = new JsonResponse<List<PipelineModel>>();

		try {
			// logger.info(env.getPipeline() + "getAllPipeLine");
			resp = restClient.getForObject(env.getPipeline() + "/getAllPipeLine", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		// logger.info("vcxfvfcvf " + resp.getBody());
		return resp.getBody();
	}

	/**
	 * get mapping for edit pipeline
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-pipeline-edit")
	public @ResponseBody JsonResponse<PipelineModel> editPipeline(@RequestParam String id, HttpSession session) {

		logger.info("Method : edit Pipeline starts");

		JsonResponse<PipelineModel> jsonResponse = new JsonResponse<PipelineModel>();

		try {
			logger.info(id + "IDIDIDIDID");
			jsonResponse = restClient.getForObject(env.getPipeline() + "/editPipeline?id=" + id, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		PipelineModel pipelineModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<PipelineModel>() {
		});
		jsonResponse.setBody(pipelineModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : edit Pipeline ends");
		logger.info("web Edit==================" + jsonResponse);
		return jsonResponse;

	}
	// for delete

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-pipeline-delete")
	public @ResponseBody JsonResponse<PipelineModel> deletePipeline(@RequestParam String deleteId) {

		logger.info("Method : deletePipeline starts");
		logger.info("@@@@@==============" + deleteId);
		JsonResponse<PipelineModel> response = new JsonResponse<PipelineModel>();

		try {
			response = restClient.getForObject(env.getPipeline() + "/deletePipeline?id=" + deleteId,
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

		logger.info("##@@@@" + response);
		logger.info("Method : deletePipeline ends");
		return response;
	}

	/**
	 * get mapping for crm pipeline
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-pipeline-view-details")
	public String viewPipelineDetails(Model model, @RequestParam("id") String encodeId) {
		logger.info("Method : viewCrmPipelineDetails starts");

		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));

		PipelineModel pipelineModel = new PipelineModel();
		JsonResponse<List<PipelineModel>> jsonResponse = new JsonResponse<List<PipelineModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "getPipelineById?id=" + id + "&Action=modelview",
					JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<StagesDetailModel> jsonResponse2 = new ArrayList<StagesDetailModel>();
		ObjectMapper mapper1 = new ObjectMapper();
		try {
			jsonResponse2 = restClient.getForObject(env.getPipeline() + "getStagesById?id=" + id + "&Action=viewStages",
					ArrayList.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<ActivityTypeModel> plannedActivityType = new ArrayList<ActivityTypeModel>();
		ObjectMapper mapper3 = new ObjectMapper();
		try {
			plannedActivityType = restClient.getForObject(
					env.getPipeline() + "getPlannedActivityList?id=" + id + "&Action=viewPlannedActivity",
					ArrayList.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<ActivityTypeModel> plannedActivityTypeList = mapper3.convertValue(plannedActivityType,
				new TypeReference<List<ActivityTypeModel>>() {
				});
		List<PipelineMeetingModel> meetingList = new ArrayList<PipelineMeetingModel>();
		ObjectMapper mapper2 = new ObjectMapper();
		try {
			meetingList = restClient.getForObject(
					env.getPipeline() + "getMeetingDetailsById?id=" + id + "&Action=viewMeetingDtls", ArrayList.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		pipelineModel = mapper.convertValue(jsonResponse.getBody(), PipelineModel.class);
		try {
			DropDownModel[] activityType = restClient.getForObject(env.getPipeline() + "getActivityTypeList",
					DropDownModel[].class);

			List<DropDownModel> activityTypeList = Arrays.asList(activityType);
			model.addAttribute("activityTypeList", activityTypeList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] meetingReminder = restClient.getForObject(env.getPipeline() + "getMeetingReminderList",
					DropDownModel[].class);

			List<DropDownModel> meetingReminderList = Arrays.asList(meetingReminder);
			model.addAttribute("meetingReminderList", meetingReminderList);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] lostReason = restClient.getForObject(env.getPipeline() + "getLostReasonList",
					DropDownModel[].class);

			List<DropDownModel> lostReasonList = Arrays.asList(lostReason);
			model.addAttribute("lostReasonList", lostReasonList);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] repetationUnit = restClient.getForObject(env.getPipeline() + "getMeetingRepetationUnitList",
					DropDownModel[].class);

			List<DropDownModel> repeatationUnitList = Arrays.asList(repetationUnit);
			model.addAttribute("repeatationUnitList", repeatationUnitList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] repetationUntill = restClient
					.getForObject(env.getPipeline() + "getMeetingRepetationUntilList", DropDownModel[].class);

			List<DropDownModel> repeatationUntilList = Arrays.asList(repetationUntill);
			model.addAttribute("repeatationUntilList", repeatationUntilList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] privacy = restClient.getForObject(env.getPipeline() + "getPrivacyList",
					DropDownModel[].class);

			List<DropDownModel> privacyList = Arrays.asList(privacy);
			model.addAttribute("privacyList", privacyList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] showAsTime = restClient.getForObject(env.getPipeline() + "getShowAsTimeList",
					DropDownModel[].class);

			List<DropDownModel> showAsTimeList = Arrays.asList(showAsTime);
			model.addAttribute("showAsTimeList", showAsTimeList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] assignTo = restClient.getForObject(env.getPipeline() + "getAssignTo",
					DropDownModel[].class);

			List<DropDownModel> assignToList = Arrays.asList(assignTo);
			model.addAttribute("assignToList", assignToList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] tag = restClient.getForObject(env.getPipeline() + "getTagList", DropDownModel[].class);

			List<DropDownModel> tagList = Arrays.asList(tag);
			model.addAttribute("tagList", tagList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : viewCrmPipelineDetails end");
		return "pipeline/viewCrmPipelineDetails";

	}
	/*
	 * for All add stages
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-pipeline-addStages")
	public @ResponseBody JsonResponse<Object> addStages(Model model, @RequestParam("id") String id1,
			@RequestParam("stage") String stage, @RequestParam("previousStage") String previousStage,
			HttpSession session) {

		logger.info("Method : assignGeneralSupports starts");

		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String createdBy = userId;
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getPipeline() + "/rest-addStages?id=" + id1 + "&stage=" + stage
					+ "&previousStage=" + previousStage + "&createdBy=" + createdBy, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			/*
			 * logger.info("if block getmsg() not false : " + resp.getMessage());
			 */
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : " + " ends");
		return resp;
	}
	/*
	 * for All add lost
	 * 
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-pipeline-add-lostreason")
	public @ResponseBody JsonResponse<Object> addLostReason(Model model, @RequestParam("id") String id,
			@RequestParam("lostReason") String lostReason, HttpSession session) {
		//

		logger.info("Method : addLostReason starts");

		logger.info("###" + lostReason);

		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String createdBy = userId;
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getPipeline() + "/addLostReason?id=" + id + "&lostReason=" + lostReason
					+ "&createdBy=" + createdBy, JsonResponse.class);//

			// logger.info(env.getPipeline() + "/addLostReason?id=" + id +
			// "&lostReason=" + lostReason + "&createdBy=" + createdBy+
			// "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBB");

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {

			// logger.info("if block getmsg() not false : " + resp.getMessage());

			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : " + " ends");
		return resp;
	}

	// Meeting

	/*
	 * post mapping for adding meeting
	 */

	@SuppressWarnings("unchecked")
	/*
	 * @SuppressWarnings({ "unchecked", "static-access" })
	 * 
	 * @Scheduled(cron = "0 5 10 * *  MON-FRI")
	 */
	@PostMapping("view-crm-pipeline-add-schedule-meeting")
	public @ResponseBody  JsonResponse<Object> scheduleMeeting(@RequestBody PipelineModel pipelineModel,
			HttpSession session) {

		logger.info("Method : meeting starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web add ======================" + pipelineModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			pipelineModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getPipeline() + "/scheduleMeeting", pipelineModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : ScheduleMeeeting ends");

		return resp;
	}
	// hgdhagdajhdgggggggggggggggggggggggggggggg

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/view-crm-pipeline-add-activity-type", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<Object> addPipelineActivityType(
			@RequestBody List<ActivityTypeModel> activityTypeModel, Model model, HttpSession session) {
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("Method : addPipelineActivityType function starts");

		try {

			for (ActivityTypeModel r : activityTypeModel) {
				String userId = null;
				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {
					e.printStackTrace();
				}
				r.setCreatedBy(userId);

			}

			res = restClient.postForObject(env.getPipeline() + "addpipelineActivityType", activityTypeModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : addPipelineActivityType function Ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/view-crm-pipeline-add-message", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<Object> addPipelineMessage(@RequestBody List<PipelineSmsModel> pipelineSmsModel,
			Model model, HttpSession session) {
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("Method : addPipelineMessage function starts");

		try {

			for (PipelineSmsModel r : pipelineSmsModel) {
				String userId = null;
				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {
					e.printStackTrace();
				}
				r.setPipelinSmsCreatedBy(userId);

			}

			res = restClient.postForObject(env.getPipeline() + "addPipelineMessage", pipelineSmsModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}

		logger.info("Method : addPipelineMessage function Ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/view-crm-pipeline-add-logs", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<Object> addPipelineLogs(@RequestBody List<PipelineLogModel> pipelineLogModel,
			Model model, HttpSession session) {
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("Method : addPipelineMessage function starts");

		try {

			for (PipelineLogModel r : pipelineLogModel) {
				String userId = null;
				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {
					e.printStackTrace();
				}
				r.setPipelineLogCreatedBy(userId);

			}

			res = restClient.postForObject(env.getPipeline() + "addCrmPipelineLog", pipelineLogModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}

		logger.info("Method : addPipelineLogs function Ends");
		return res;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/view-crm-pipeline-add-meeting-schedule", method = {
	 * RequestMethod.POST }) public @ResponseBody JsonResponse<Object>
	 * addPipelineMeetingSchedule(
	 * 
	 * @RequestBody List<PipelineMeetingModel> pipelineMeetingModel, Model model,
	 * HttpSession session) { JsonResponse<Object> res = new JsonResponse<Object>();
	 * logger.info("Method : addPipelineMeetingSchedule function starts");
	 * 
	 * String s = ""; for (String m :
	 * pipelineMeetingModel.get(0).getMeetingAttendies()) { s = s + m + ","; } for
	 * (PipelineMeetingModel staff : pipelineMeetingModel) {
	 * 
	 * if (s != "") { s = s.substring(0, s.length() - 1);
	 * staff.setMeetingAttendie(s);
	 * 
	 * } }
	 * 
	 * try {
	 * 
	 * for (PipelineMeetingModel r : pipelineMeetingModel) { String userId = null;
	 * try { userId = (String) session.getAttribute("USER_ID"); } catch (Exception
	 * e) { e.printStackTrace(); } r.setMeetingCreatedBy(userId);
	 * 
	 * }
	 * 
	 * res = restClient.postForObject(env.getPipeline() +
	 * "addCrmPipelineMeetingSchedule", pipelineMeetingModel, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * String message = res.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { res.setMessage("Success"); }
	 * logger.info("Method : addPipelineMeetingSchedule function Ends"); return res;
	 * }
	 */

	/**
	 * GetMapping for delete pipeline meeting details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-pipeline-delete-meeting-schedule")
	public @ResponseBody JsonResponse<Object> deleteCrmMeetingSchedule(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCrmMeetingSchedule starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		// byte[] encodeByte = Base64.getDecoder().decode(id.getBytes());
		// String id1 = (new String(encodeByte));
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restClient.getForObject(
					env.getPipeline() + "deleteCrmPipelineMeetingById?id=" + id + "&createdBy=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			logger.info("if block getmsg() not false : " + resp.getMessage());
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : deleteCrmMeetingSchedule ends");

		return resp;
	}

	/*
	 * for edit meeting schedule
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-pipeline-edit-schedule" })
	public @ResponseBody JsonResponse<Object> editmeetingSchedule(Model model, @RequestBody String index,
			BindingResult result) {

		logger.info("Method : editmeetingSchedule starts");

		/*
		 * byte[] encodeByte = Base64.getDecoder().decode(index.getBytes()); String id =
		 * (new String(encodeByte));
		 */
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getPipeline() + "getEditMeetingScheduleById?id=" + index,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : editmeetingSchedule ends");
		return res;
	}

	/*
	 * for edit activity type schedule
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-pipeline-edit-activity-type-schedule" })
	public @ResponseBody JsonResponse<Object> editActivityTypeSchedule(Model model, @RequestBody String index,
			BindingResult result) {

		logger.info("Method : editActivityTypeSchedule starts");

		/*
		 * byte[] encodeByte = Base64.getDecoder().decode(index.getBytes()); String id =
		 * (new String(encodeByte));
		 */
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getPipeline() + "getEditActivityTypeSchedule?id=" + index,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : editActivityTypeSchedule ends");
		return res;
	}

	/**
	 * GetMapping for change mark done status pipeline activity type
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-pipeline-activity-type-markdone")
	public @ResponseBody JsonResponse<Object> markDoneActivity(@RequestParam String id) {
		logger.info("Method : markDoneActivity starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getPipeline() + "changeMarkDoneStatus?id=" + id, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			logger.info("if block getmsg() not false : " + resp.getMessage());
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : markDoneActivity ends");

		return resp;
	}

	/**
	 * GetMapping for delete pipeline activity type
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-pipeline-activity-type-delete")
	public @ResponseBody JsonResponse<Object> deleteActivitySchedule(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteActivitySchedule starts");
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(
					env.getPipeline() + "deleteActivityTypeDetails?id=" + id + "&createdBy=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			logger.info("if block getmsg() not false : " + resp.getMessage());
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : deleteActivitySchedule ends");

		return resp;
	}
	// Activity Log

	@GetMapping(value = { "view-crm-pipeline-activity-through-ajax" })
	public @ResponseBody List<ActivitylogModel> getActivityLog(@RequestParam String id, HttpSession session) {
		logger.info("Method : getActivityLog starts");
		List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();
		try {

			ActivitylogModel[] activityLog = restClient
					.getForObject(env.getPipeline() + "/get-activity-log-pipeline?id=" + id, ActivitylogModel[].class);
			activityLogList = Arrays.asList(activityLog);

			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (ActivitylogModel m : activityLogList) {
				String date = m.getOperationOn();
				if (date != null && date != "") {
					date = DateFormatter.dateFormat(date, dateFormat);

					m.setOperationOn(date);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getActivityLog ends");
		return activityLogList;
	}
}
