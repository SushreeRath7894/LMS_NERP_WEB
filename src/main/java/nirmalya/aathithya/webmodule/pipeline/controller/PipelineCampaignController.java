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
import nirmalya.aathithya.webmodule.pipeline.model.PipelineCampaignModel;

@Controller
@RequestMapping(value = "pipeline")
public class PipelineCampaignController {
	Logger logger = LoggerFactory.getLogger(PipelineCampaignController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	/*
	 * get mapping for adding campaign name
	 */

	@GetMapping("/add-crm-campaign")
	public String addCrmCampaign(Model model, HttpSession session) {
		logger.info("Method : addCrmCampaign start");
		PipelineCampaignModel pipelineCampaignModel = new PipelineCampaignModel();
		PipelineCampaignModel form = (PipelineCampaignModel) session.getAttribute("sPipelineCampaignModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		session.setAttribute("message", "");

		if (form != null) {
			model.addAttribute("PipelineCampaignModel", form);
			session.setAttribute("sPipelineCampaignModel", null);

		} else {
			model.addAttribute("PipelineCampaignModel", pipelineCampaignModel);
		}
		try {
			DropDownModel[] user = restClient.getForObject(env.getPipeline() + "getCrmResponsible",
					DropDownModel[].class);

			List<DropDownModel> userList = Arrays.asList(user);
			model.addAttribute("userList", userList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] tag = restClient.getForObject(env.getPipeline() + "getCrmTagList", DropDownModel[].class);

			List<DropDownModel> tagList = Arrays.asList(tag);
			model.addAttribute("tagList", tagList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addCrmCampaign end");
		return "crm/addCrmCampaign";
	}
	/*
	 * post mapping for adding campaign name
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/add-crm-campaign")
	public String postCrmCampaign(@ModelAttribute PipelineCampaignModel pipelineCampaignModel, Model model, HttpSession session) {
		logger.info("Method : postCrmCampaign starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pipelineCampaignModel.setCreatedBy(userId);
			resp = restClient.postForObject(env.getPipeline() + "addCrmCampaign", pipelineCampaignModel, JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "") {
			session.setAttribute("message", resp.getMessage());
			session.setAttribute("PipelineCampaignModel", pipelineCampaignModel);
			return "redirect:/crm/add-crm-campaign";
		}
		session.setAttribute("sPipelineCampaignModel", null);
		logger.info("Method : postCrmCampaign end");
		return "redirect:/crm/view-crm-campaign";
	}

	/**
	 * get mapping for crm campaign
	 */

	@GetMapping("/view-crm-campaign")
	public String viewCrmCampaign(Model model) {
		logger.info("Method : viewCrmCampaign starts");

		logger.info("Method : viewCrmCampaign end");
		return "crm/viewCrmCampaign";

	}

	/**
	 * get mapping for view Crm campaign ThroughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-campaign-throughajax")
	public @ResponseBody DataTableResponse viewCrmCampaignThroughAjax(Model model, HttpServletRequest request,
			@RequestParam String param1) {
		logger.info("Method : viewCrmCampaignThroughAjax starts");

		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();

		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<PipelineCampaignModel>> jsonResponse = new JsonResponse<List<PipelineCampaignModel>>();
			jsonResponse = restClient.postForObject(env.getPipeline() + "getAllCampaign", tableRequest,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<PipelineCampaignModel> form = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<PipelineCampaignModel>>() {
					});

			String s = "";
			for (PipelineCampaignModel m : form) {
				byte[] pId = Base64.getEncoder().encode(m.getCampaign().getBytes());
				s = "";
				s = "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModelData(\""
						+ new String(pId) + "\")'><i class='fa fa-search search'></i></a>";
				s = s + " &nbsp;&nbsp <a href='view-crm-campaign-edit?id=" + new String(pId)
						+ "' ><i class=\"fa fa-edit\"></i></a> &nbsp;&nbsp; <a href= 'javascript:void(0)' "
						+ "' onclick='deleteCampaign(\"" + new String(pId) + "\")' ><i class=\"fa fa-trash\"></i></a> ";
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
		logger.info("Method : viewCrmCampaignThroughAjax end");
		return response;
	}

	/**
	 * get mapping for edit lost reason
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-campaign-edit")
	public String editCrmCampaign(Model model, @RequestParam("id") String encodeId, HttpSession session) {
		logger.info("Method : editCrmCampaign starts");
		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));
		PipelineCampaignModel PipelineCampaignModel = new PipelineCampaignModel();
		JsonResponse<PipelineCampaignModel> jsonResponse = new JsonResponse<PipelineCampaignModel>();
		try {
			jsonResponse = restClient.getForObject(
					env.getPipeline() + "getCrmCampaignById?id=" + id + "&Action=editCampaign", JsonResponse.class);
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

		PipelineCampaignModel = mapper.convertValue(jsonResponse.getBody(), PipelineCampaignModel.class);
		session.setAttribute("message", "");

		model.addAttribute("PipelineCampaignModel", PipelineCampaignModel);
		try {
			DropDownModel[] user = restClient.getForObject(env.getPipeline() + "getCrmResponsible",
					DropDownModel[].class);

			List<DropDownModel> userList = Arrays.asList(user);
			model.addAttribute("userList", userList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] tag = restClient.getForObject(env.getPipeline() + "getCrmTagList", DropDownModel[].class);

			List<DropDownModel> tagList = Arrays.asList(tag);
			model.addAttribute("tagList", tagList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : editCrmCampaign end");
		return "crm/addCrmCampaign";
	}

	/**
	 * post Mapping for viewInModelData in lost reason
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-campaign-modeldata" })
	public @ResponseBody JsonResponse<Object> modelView(Model model, @RequestBody String index, BindingResult result) {
		logger.info("Method : modelView starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(index.getBytes());
		String id = (new String(encodeByte));

		try {
			res = restClient.getForObject(env.getPipeline() + "getCrmCampaignById?id=" + id + "&Action=" + "ModelView",
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
	@GetMapping("/view-crm-campaign-delete")
	public @ResponseBody JsonResponse<Object> deleteCampaign(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCampaign starts");

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

			resp = restClient.getForObject(env.getPipeline() + "deleteCampaign?id=" + id1 + "&createdBy=" + userId,
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
		logger.info("Method : deleteCampaign ends");
		return resp;
	}
}
