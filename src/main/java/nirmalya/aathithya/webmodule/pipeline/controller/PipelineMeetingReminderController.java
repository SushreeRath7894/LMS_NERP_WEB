package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nirmalya.aathithya.webmodule.common.pagination.DataTableRequest;
import nirmalya.aathithya.webmodule.common.pagination.DataTableResponse;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.PipelineMeetingReminderModel;

@Controller
@RequestMapping(value = "crm")
public class PipelineMeetingReminderController {
	Logger logger = LoggerFactory.getLogger(PipelineMeetingReminderController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	/*
	 * get mapping for adding reminder type master
	 */

	@GetMapping("/add-crm-meeting-reminder")
	public String addCrmMeetingReminder(Model model, HttpSession session) {
		logger.info("Method : addCrmMeetingReminder start");
		PipelineMeetingReminderModel PipelineMeetingReminderModel = new PipelineMeetingReminderModel();
		PipelineMeetingReminderModel form = (PipelineMeetingReminderModel) session.getAttribute("sPipelineMeetingReminderModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		session.setAttribute("message", "");

		try {
			DropDownModel[] reminderType = restClient.getForObject(env.getPipeline() + "getCrmReminderType",
					DropDownModel[].class);

			List<DropDownModel> reminderTypeList = Arrays.asList(reminderType);
			model.addAttribute("reminderTypeList", reminderTypeList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] reminderUnit = restClient.getForObject(env.getPipeline() + "getCrmReminderUnit",
					DropDownModel[].class);

			List<DropDownModel> reminderUnitList = Arrays.asList(reminderUnit);
			model.addAttribute("reminderUnitList", reminderUnitList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (form != null) {
			model.addAttribute("PipelineMeetingReminderModel", form);
			session.setAttribute("PipelineMeetingReminderModel", null);

		} else {
			model.addAttribute("PipelineMeetingReminderModel", PipelineMeetingReminderModel);
		}
		logger.info("Method : addCrmMeetingReminder end");
		return "crm/addCrmMeetingReminder";
	}
	/*
	 * post mapping for adding pipeline
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/add-crm-meeting-reminder")
	public String postCrmMeetingReminder(@ModelAttribute PipelineMeetingReminderModel PipelineMeetingReminderModel, Model model,
			HttpSession session) {
		logger.info("Method : postCrmMeetingReminder starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PipelineMeetingReminderModel.setCreatedBy(userId);
			resp = restClient.postForObject(env.getPipeline() + "addMeetingReminder", PipelineMeetingReminderModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "") {
			session.setAttribute("message", resp.getMessage());
			session.setAttribute("sPipelineMeetingReminderModel", PipelineMeetingReminderModel);
			return "redirect:/crm/add-crm-meeting-reminder";
		}

		session.setAttribute("sPipelineMeetingReminderModel", null);
		logger.info("Method : postCrmMeetingReminder end");
		return "redirect:/crm/view-crm-meeting-reminder";
	}

	/**
	 * get mapping for crm meeting reminder
	 */

	@GetMapping("/view-crm-meeting-reminder")
	public String viewCrmMeetingReminder(Model model) {
		logger.info("Method : viewCrmMeetingReminder starts");
		try {
			DropDownModel[] reminderType = restClient.getForObject(env.getPipeline() + "getCrmReminderType",
					DropDownModel[].class);

			List<DropDownModel> reminderTypeList = Arrays.asList(reminderType);
			model.addAttribute("reminderTypeList", reminderTypeList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : viewCrmMeetingReminder end");
		return "crm/viewCrmMeetingReminder";

	}

	/**
	 * get mapping for view Crm meeting reminderThroughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-meeting-reminder-throughajax")
	public @ResponseBody DataTableResponse viewCrmMeetingReminderAjax(Model model, HttpServletRequest request,
			@RequestParam String param1) {
		logger.info("Method : viewCrmMeetingReminderAjax starts");

		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();

		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<PipelineMeetingReminderModel>> jsonResponse = new JsonResponse<List<PipelineMeetingReminderModel>>();
			jsonResponse = restClient.postForObject(env.getPipeline() + "getAllCrmMeetingReminder", tableRequest,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<PipelineMeetingReminderModel> form = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<PipelineMeetingReminderModel>>() {
					});

			String s = "";
			for (PipelineMeetingReminderModel m : form) {
				byte[] pId = Base64.getEncoder().encode(m.getReminder().getBytes());
				s = "";
				s = "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModelData(\""
						+ new String(pId) + "\")'><i class='fa fa-search search'></i></a>";
				s = s + " &nbsp;&nbsp <a href='view-crm-meeting-reminder-edit?id=" + new String(pId)
						+ "' ><i class=\"fa fa-edit\"></i></a> &nbsp;&nbsp; <a href= 'javascript:void(0)' "
						+ "' onclick='deleteMeetingReminder(\"" + new String(pId)
						+ "\")' ><i class=\"fa fa-trash\"></i></a> ";

				m.setAction(s);
				s = "";
			}

			response.setRecordsTotal(jsonResponse.getTotal());
			response.setRecordsFiltered(jsonResponse.getTotal());
			response.setDraw(Integer.parseInt(draw));
			response.setData(form);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : viewCrmMeetingReminderAjax end");
		return response;
	}

	/**
	 * get mapping for edit lost reason
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-meeting-reminder-edit")
	public String editMeetingReminder(Model model, @RequestParam("id") String encodeId, HttpSession session) {
		logger.info("Method : editMeetingReminder starts");
		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));
		PipelineMeetingReminderModel PipelineMeetingReminderModel = new PipelineMeetingReminderModel();
		JsonResponse<PipelineMeetingReminderModel> jsonResponse = new JsonResponse<PipelineMeetingReminderModel>();

		try {
			jsonResponse = restClient.getForObject(
					env.getPipeline() + "getCrmMeetingReminderById?id=" + id + "&Action=editMeetingReminder",
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] reminderType = restClient.getForObject(env.getPipeline() + "getCrmReminderType",
					DropDownModel[].class);

			List<DropDownModel> reminderTypeList = Arrays.asList(reminderType);
			model.addAttribute("reminderTypeList", reminderTypeList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] reminderUnit = restClient.getForObject(env.getPipeline() + "getCrmReminderUnit",
					DropDownModel[].class);

			List<DropDownModel> reminderUnitList = Arrays.asList(reminderUnit);
			model.addAttribute("reminderUnitList", reminderUnitList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		ObjectMapper mapper = new ObjectMapper();

		PipelineMeetingReminderModel = mapper.convertValue(jsonResponse.getBody(), PipelineMeetingReminderModel.class);
		session.setAttribute("message", "");

		model.addAttribute("PipelineMeetingReminderModel", PipelineMeetingReminderModel);

		logger.info("Method : editMeetingReminder end");
		return "crm/addCrmMeetingReminder";
	}

	/**
	 * post Mapping for viewInModelData in meeting reminder
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-meeting-reminder-modeldata" })
	public @ResponseBody JsonResponse<Object> modelView(Model model, @RequestBody String index, BindingResult result) {
		logger.info("Method : modelView starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(index.getBytes());
		String id = (new String(encodeByte));

		try {
			res = restClient.getForObject(
					env.getPipeline() + "getCrmMeetingReminderById?id=" + id + "&Action=" + "ModelViewReminder",
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

		logger.info("Method : modelView end");
		return res;
	}

	/**
	 * GetMapping for delete sales team
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-meeting-reminder-delete")
	public @ResponseBody JsonResponse<Object> deleteCrmMeetingReminder(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCrmMeetingReminder starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(id.getBytes());
		String id1 = (new String(encodeByte));
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restClient.getForObject(
					env.getPipeline() + "deleteMeetingReminderById?id=" + id1 + "&createdBy=" + userId,
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
		logger.info("Method : deleteCrmMeetingReminder ends");
		return resp;
	}

}
