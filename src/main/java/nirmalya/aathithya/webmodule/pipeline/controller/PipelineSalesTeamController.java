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
import nirmalya.aathithya.webmodule.pipeline.model.PipelineSalesTeamModel;

@Controller
@RequestMapping(value = "pipeline")
public class PipelineSalesTeamController {
	Logger logger = LoggerFactory.getLogger(PipelineSalesTeamController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/*
	 * get mapping for adding sales team
	 */

	@GetMapping("/add-crm-sales-team")
	public String addCrmSalesTeam(Model model, HttpSession session) {
		logger.info("Method : addCrmSalesTeam start");
		PipelineSalesTeamModel PipelineSalesTeamModel = new PipelineSalesTeamModel();
		PipelineSalesTeamModel form = (PipelineSalesTeamModel) session.getAttribute("sPipelineSalesTeamModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		session.setAttribute("message", "");
		if (form != null) {
			model.addAttribute("PipelineSalesTeamModel", form);
			session.setAttribute("sPipelineSalesTeamModel", null);

		} else {
			model.addAttribute("PipelineSalesTeamModel", PipelineSalesTeamModel);
		}
		try {
			DropDownModel[] teamLeader = restClient.getForObject(env.getPipeline() + "getCrmTeamLeader",
					DropDownModel[].class);

			List<DropDownModel> teamLeaderList = Arrays.asList(teamLeader);
			model.addAttribute("teamLeaderList", teamLeaderList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] assignTo = restClient.getForObject(env.getPipeline() + "getCrmAssignTo",
					DropDownModel[].class);

			List<DropDownModel> assignToList = Arrays.asList(assignTo);
			model.addAttribute("assignToList", assignToList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addCrmSalesTeam end");
		return "crm/addCrmSalesTeam";
	}
	/*
	 * post mapping for adding sales team
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/add-crm-sales-team")
	public String postSalesTeam(@ModelAttribute PipelineSalesTeamModel PipelineSalesTeamModel, Model model, HttpSession session) {
		logger.info("Method : postSalesTeam starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PipelineSalesTeamModel.setCreatedBy(userId);
			resp = restClient.postForObject(env.getPipeline() + "addSalesTeam", PipelineSalesTeamModel, JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "") {
			session.setAttribute("message", resp.getMessage());
			session.setAttribute("sPipelineSalesTeamModel", PipelineSalesTeamModel);
			return "redirect:/crm/add-crm-sales-team";
		}
		
		session.setAttribute("sPipelineSalesTeamModel", null);
		logger.info("Method : postSalesTeam end");
		return "redirect:/crm/view-crm-sales-team";
	}

	/**
	 * get mapping for crm sales team
	 */

	@GetMapping("/view-crm-sales-team")
	public String viewCrmSalesTeam(Model model) {
		logger.info("Method : viewCrmSalesTeam starts");

		logger.info("Method : viewCrmSalesTeam end");
		return "crm/viewCrmSalesTeam";

	}

	/**
	 * get mapping for view Crm sales team ThroughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-sales-team-throughajax")
	public @ResponseBody DataTableResponse viewCrmSalesTeamThroughAjax(Model model, HttpServletRequest request,
			@RequestParam String param1) {
		logger.info("Method : viewCrmSalesTeamThroughAjax starts");

		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();

		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<PipelineSalesTeamModel>> jsonResponse = new JsonResponse<List<PipelineSalesTeamModel>>();
			jsonResponse = restClient.postForObject(env.getPipeline() + "getAllCrmSalesTeam", tableRequest,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<PipelineSalesTeamModel> form = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<PipelineSalesTeamModel>>() {
					});

			String s = "";
			for (PipelineSalesTeamModel m : form) {
				byte[] pId = Base64.getEncoder().encode(m.getTeamId().getBytes());
				s = "";
				s = "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModelData(\""
						+ new String(pId) + "\")'><i class='fa fa-search search'></i></a>";
				s = s + " &nbsp;&nbsp <a href='view-crm-sales-team-edit?id=" + new String(pId)
						+ "' ><i class=\"fa fa-edit\"></i></a> &nbsp;&nbsp; <a href= 'javascript:void(0)' "
						+ "' onclick='deleteSalesTeam(\"" + new String(pId)
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
		logger.info("Method : viewCrmSalesTeamThroughAjax end");
		return response;
	}

	/**
	 * get mapping for edit lost reason
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-sales-team-edit")
	public String editSalesTeam(Model model, @RequestParam("id") String encodeId, HttpSession session) {
		logger.info("Method : editSalesTeam starts");
		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));
		PipelineSalesTeamModel PipelineSalesTeamModel = new PipelineSalesTeamModel();
		JsonResponse<PipelineSalesTeamModel> jsonResponse = new JsonResponse<PipelineSalesTeamModel>();

		try {
			jsonResponse = restClient.getForObject(
					env.getPipeline() + "getCrmSaleTeamById?id=" + id + "&Action=editSaleTeam", JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] teamLeader = restClient.getForObject(env.getPipeline() + "getCrmTeamLeader",
					DropDownModel[].class);

			List<DropDownModel> teamLeaderList = Arrays.asList(teamLeader);
			model.addAttribute("teamLeaderList", teamLeaderList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] assignTo = restClient.getForObject(env.getPipeline() + "getCrmAssignTo",
					DropDownModel[].class);

			List<DropDownModel> assignToList = Arrays.asList(assignTo);
			model.addAttribute("assignToList", assignToList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		ObjectMapper mapper = new ObjectMapper();

		PipelineSalesTeamModel = mapper.convertValue(jsonResponse.getBody(), PipelineSalesTeamModel.class);
		session.setAttribute("message", "");

		model.addAttribute("PipelineSalesTeamModel", PipelineSalesTeamModel);

		logger.info("Method : editSalesTeam end");
		return "crm/addCrmSalesTeam";
	}

	/**
	 * post Mapping for viewInModelData in lost reason
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-sales-team-modeldata" })
	public @ResponseBody JsonResponse<Object> modelView(Model model, @RequestBody String index, BindingResult result) {
		logger.info("Method : modelView starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(index.getBytes());
		String id = (new String(encodeByte));

		try {
			res = restClient.getForObject(
					env.getPipeline() + "getCrmSaleTeamById?id=" + id + "&Action=" + "ModelViewSalesTeam",
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
	@GetMapping("/view-crm-sales-team-delete")
	public @ResponseBody JsonResponse<Object> deleteCrmSalesTeam(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCrmSalesTeam starts");

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

			resp = restClient.getForObject(env.getPipeline() + "deleteSalesTeamById?id=" + id1 + "&createdBy=" + userId,
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
		logger.info("Method : deleteCrmSalesTeam ends");
		return resp;
	}
}
