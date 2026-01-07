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
import nirmalya.aathithya.webmodule.pipeline.model.ActivityTypeModel;


@Controller
@RequestMapping(value = "crm")
public class ActivityTypeController {
	Logger logger = LoggerFactory.getLogger(ActivityTypeController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * get mapping for adding activity type
	 */
	@GetMapping("/add-crm-activity-type")
	public String addCrmActivityType(Model model, HttpSession session) {
		logger.info("Method : addCrmActivityType start");
		ActivityTypeModel crmActivityTypeModel = new ActivityTypeModel();
		ActivityTypeModel form = (ActivityTypeModel) session.getAttribute("scrmActivityTypeModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		session.setAttribute("message", "");
		if (form != null) {
			model.addAttribute("crmActivityTypeModel", form);
			session.setAttribute("scrmActivityTypeModel", null);

		} else {
			model.addAttribute("crmActivityTypeModel", crmActivityTypeModel);
		}
		try {
			DropDownModel[] user = restClient.getForObject(env.getPipeline() + "getCrmDefaultUser",
					DropDownModel[].class);

			List<DropDownModel> userList = Arrays.asList(user);
			model.addAttribute("userList", userList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] unit = restClient.getForObject(env.getPipeline() + "getCrmUnitList", DropDownModel[].class);

			List<DropDownModel> unitList = Arrays.asList(unit);
			model.addAttribute("unitList", unitList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] sceduledType = restClient.getForObject(env.getPipeline() + "getCrmSceduledType",
					DropDownModel[].class);

			List<DropDownModel> sceduledTypeList = Arrays.asList(sceduledType);
			model.addAttribute("sceduledTypeList", sceduledTypeList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] nextActivity = restClient.getForObject(env.getPipeline() + "getNextActivityList",
					DropDownModel[].class);

			List<DropDownModel> nextActivityList = Arrays.asList(nextActivity);
			model.addAttribute("nextActivityList", nextActivityList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] recomandedActivity = restClient.getForObject(env.getPipeline() + "getRecomandedActivityList",
					DropDownModel[].class);

			List<DropDownModel> recomandedActivitList = Arrays.asList(recomandedActivity);
			model.addAttribute("recomandedActivitList", recomandedActivitList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] emailTemplates = restClient.getForObject(env.getPipeline() + "getEmailTemplateList",
					DropDownModel[].class);

			List<DropDownModel> emailTemplatesList = Arrays.asList(emailTemplates);
			model.addAttribute("emailTemplatesList", emailTemplatesList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addCrmActivityType end");
		return "crm/addCrmActivityType";
	}
	/*
	 * post mapping for adding sales team
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/add-crm-activity-type")
	public String postCrmActivityType(@ModelAttribute ActivityTypeModel crmActivityTypeModel, Model model,
			HttpSession session) {
		logger.info("Method : postCrmActivityType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			crmActivityTypeModel.setCreatedBy(userId);
			resp = restClient.postForObject(env.getPipeline() + "addActivityType", crmActivityTypeModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "") {
			session.setAttribute("message", resp.getMessage());
			session.setAttribute("scrmActivityTypeModel", crmActivityTypeModel);
			return "redirect:/crm/add-crm-activity-type";
		}

		session.setAttribute("scrmActivityTypeModel", null);
		logger.info("Method : postCrmActivityType end");
		return "redirect:/crm/view-crm-activity-type";
	}

	/**
	 * get mapping for crm activity type
	 */

	@GetMapping("/view-crm-activity-type")
	public String viewCrmActivityType(Model model) {
		logger.info("Method : viewCrmActivityType starts");

		logger.info("Method : viewCrmActivityType end");
		return "crm/viewCrmActivityType";

	}

	/**
	 * get mapping for view Crm sales team ThroughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-activity-type-throughajax")
	public @ResponseBody DataTableResponse viewCrmActivityTypeThroughAjax(Model model, HttpServletRequest request,
			@RequestParam String param1) {
		logger.info("Method : viewCrmActivityTypeThroughAjax starts");

		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();

		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<ActivityTypeModel>> jsonResponse = new JsonResponse<List<ActivityTypeModel>>();
			jsonResponse = restClient.postForObject(env.getPipeline() + "getAllCrmActivityType", tableRequest,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ActivityTypeModel> form = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<ActivityTypeModel>>() {
					});
			
			String s = "";
			for (ActivityTypeModel m : form) {
				byte[] pId = Base64.getEncoder().encode(m.getActivityType().getBytes());
				
				s = "";
				s = "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModelData(\""
						+ new String(pId) + "\")'><i class='fa fa-search search'></i></a>";
				s = s + " &nbsp;&nbsp <a href='view-crm-activity-type-edit?id=" + new String(pId)
						+ "' ><i class=\"fa fa-edit\"></i></a> &nbsp;&nbsp; <a href= 'javascript:void(0)' "
						+ "' onclick='deleteActivityType(\"" + new String(pId)
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
		logger.info("Method : viewCrmActivityTypeThroughAjax end");
		return response;
	}

	/**
	 * get mapping for edit lost reason
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-activity-type-edit")
	public String editCrmActivityType(Model model, @RequestParam("id") String encodeId, HttpSession session) {
		logger.info("Method : editCrmActivityType starts");
		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));
		ActivityTypeModel crmActivityTypeModel = new ActivityTypeModel();
		JsonResponse<ActivityTypeModel> jsonResponse = new JsonResponse<ActivityTypeModel>();
		try {
			jsonResponse = restClient.getForObject(
					env.getPipeline() + "getCrmActivityTypeById?id=" + id + "&Action=editActivityType",
					JsonResponse.class);
			logger.info("jsonResponse" + jsonResponse);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		ObjectMapper mapper = new ObjectMapper();

		crmActivityTypeModel = mapper.convertValue(jsonResponse.getBody(), ActivityTypeModel.class);
		session.setAttribute("message", "");

		model.addAttribute("crmActivityTypeModel", crmActivityTypeModel);
		try {
			DropDownModel[] user = restClient.getForObject(env.getPipeline() + "getCrmDefaultUser",
					DropDownModel[].class);

			List<DropDownModel> userList = Arrays.asList(user);
			model.addAttribute("userList", userList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] unit = restClient.getForObject(env.getPipeline() + "getCrmUnitList", DropDownModel[].class);

			List<DropDownModel> unitList = Arrays.asList(unit);
			model.addAttribute("unitList", unitList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] sceduledType = restClient.getForObject(env.getPipeline() + "getCrmSceduledType",
					DropDownModel[].class);

			List<DropDownModel> sceduledTypeList = Arrays.asList(sceduledType);
			model.addAttribute("sceduledTypeList", sceduledTypeList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] nextActivity = restClient.getForObject(env.getPipeline() + "getNextActivityList",
					DropDownModel[].class);

			List<DropDownModel> nextActivityList = Arrays.asList(nextActivity);
			model.addAttribute("nextActivityList", nextActivityList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] recomandedActivity = restClient.getForObject(env.getPipeline() + "getRecomandedActivityList",
					DropDownModel[].class);

			List<DropDownModel> recomandedActivitList = Arrays.asList(recomandedActivity);
			model.addAttribute("recomandedActivitList", recomandedActivitList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : editCrmActivityType end");
		return "crm/addCrmActivityType";
	}

	/**
	 * post Mapping for viewInModelData in activity types
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-activity-type-modeldata" })
	public @ResponseBody JsonResponse<Object> modelView(Model model, @RequestBody String index, BindingResult result) {
		logger.info("Method : modelView starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(index.getBytes());
		String id = (new String(encodeByte));

		try {
			res = restClient.getForObject(
					env.getPipeline() + "getCrmActivityTypeById?id=" + id + "&Action=" + "ModelView",
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
	 * GetMapping for delete activity types
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-activity-type-delete")
	public @ResponseBody JsonResponse<Object> deleteActivityType(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteActivityType starts");

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

			resp = restClient.getForObject(env.getPipeline() + "deleteCrmActivityType?id=" + id1 + "&createdBy=" + userId,
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
		logger.info("Method : deleteActivityType ends");
		return resp;
	}

}
