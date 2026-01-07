package nirmalya.aathithya.webmodule.pipeline.controller;


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
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.PipelineTagModel;

@Controller
@RequestMapping(value = "crm")
public class PipelineTagController {
	Logger logger = LoggerFactory.getLogger(PipelineTagController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	/*
	 * get mapping for adding tag
	 */

	@GetMapping("/add-crm-tag-master")
	public String addCrmTagMaster(Model model, HttpSession session) {
		logger.info("Method : addCrmTagMaster start");
		PipelineTagModel crmtag = new PipelineTagModel();
		PipelineTagModel form = (PipelineTagModel) session.getAttribute("scrmtag");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		session.setAttribute("message", "");

		if (form != null) {
			model.addAttribute("crmtag", form);
			session.setAttribute("scrmtag", null);

		} else {
			model.addAttribute("crmtag", crmtag);
		}
		logger.info("Method : addCrmTagMaster end");
		return "crm/addCrmTag";
	}
	/*
	 * post mapping for adding tag
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/add-crm-tag-master")
	public String postCrmTag(@ModelAttribute PipelineTagModel crmtag, Model model, HttpSession session) {
		logger.info("Method : postCrmTag starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			crmtag.setTagCreatedBy(userId);
			resp = restClient.postForObject(env.getPipeline() + "addTag", crmtag, JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "") {
			session.setAttribute("message", resp.getMessage());
			session.setAttribute("scrmtag", crmtag);
			return "redirect:/crm/add-crm-tag-master";
		}
		session.setAttribute("scrmtag", null);
		logger.info("Method : postCrmTag end");
		return "redirect:/crm/view-crm-tag-master";
	}

	/**
	 * get mapping for crm tag master
	 */

	@GetMapping("/view-crm-tag-master")
	public String viewCrmTag(Model model) {
		logger.info("Method : viewCrmTag starts");

		logger.info("Method : viewCrmTag end");
		return "crm/viewCrmTag";

	}

	/**
	 * get mapping for view CrmTag ThroughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-tag-master-throughajax")
	public @ResponseBody DataTableResponse viewCrmTagThroughAjax(Model model, HttpServletRequest request,
			@RequestParam String param1) {
		logger.info("Method : viewCrmTagThroughAjax starts");

		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();

		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<PipelineTagModel>> jsonResponse = new JsonResponse<List<PipelineTagModel>>();
			jsonResponse = restClient.postForObject(env.getPipeline() + "getAllCrmTag", tableRequest, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<PipelineTagModel> form = mapper.convertValue(jsonResponse.getBody(), new TypeReference<List<PipelineTagModel>>() {
			});

			String s = "";
			for (PipelineTagModel m : form) {
				byte[] pId = Base64.getEncoder().encode(m.getTagId().getBytes());
				s = "";
				s = "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModelData(\""
						+ new String(pId) + "\")'><i class='fa fa-search search'></i></a>";
				s = s + " &nbsp;&nbsp <a href='view-crm-tag-master-edit?id=" + new String(pId)
						+ "' ><i class=\"fa fa-edit\"></i></a> &nbsp;&nbsp; <a href= 'javascript:void(0)' "
						+ "' onclick='deleteTag(\"" + new String(pId)
						+ "\")' ><i class=\"fa fa-trash\"></i></a> ";
				m.setAction(s);
				s = "";

				if (m.getTagActive()) {
					m.setStatus("Active");
				} else {
					m.setStatus("Inactive");
				}
			}

			response.setRecordsTotal(jsonResponse.getTotal());
			response.setRecordsFiltered(jsonResponse.getTotal());
			response.setDraw(Integer.parseInt(draw));
			response.setData(form);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : viewCrmTagThroughAjax end");
		return response;
	}

	/**
	 * get mapping for edit tag
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-tag-master-edit")
	public String editTag(Model model, @RequestParam("id") String encodeId, HttpSession session) {
		logger.info("Method : editTag starts");
		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));
		PipelineTagModel crmtag = new PipelineTagModel();
		JsonResponse<PipelineTagModel> jsonResponse = new JsonResponse<PipelineTagModel>();

		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "getCrmTagById?id=" + id + "&Action=editTag",
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		ObjectMapper mapper = new ObjectMapper();

		crmtag = mapper.convertValue(jsonResponse.getBody(), PipelineTagModel.class);
		session.setAttribute("message", "");

		model.addAttribute("crmtag", crmtag);

		logger.info("Method : editTag end");
		return "crm/addCrmTag";
	}

	/**
	 * GetMapping for delete account group master
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-tag-master-delete")
	public @ResponseBody JsonResponse<Object> deleteCrmTag(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCrmTag starts");

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

			resp = restClient.getForObject(env.getPipeline() + "deleteTagById?id=" + id1 + "&createdBy=" + userId,
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
		logger.info("Method : deleteCrmTag ends");
		return resp;
	}

	/**
	 * post Mapping for viewInModelData in account group
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-tag-master-modeldata" })
	public @ResponseBody JsonResponse<Object> modelView(Model model, @RequestBody String index, BindingResult result) {
		logger.info("Method : modelView starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(index.getBytes());
		String id = (new String(encodeByte));

		try {
			res = restClient.getForObject(env.getPipeline() + "getCrmTagById?id=" + id + "&Action=" + "ModelViewTag",
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
}
