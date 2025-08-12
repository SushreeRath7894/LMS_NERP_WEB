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
import nirmalya.aathithya.webmodule.pipeline.model.PipelineMarketingSourceModel;

@Controller
@RequestMapping(value = "pipeline")
public class PipelineSourceController {
	Logger logger = LoggerFactory.getLogger(PipelineSourceController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	/*
	 * get mapping for adding source
	 */

	@GetMapping("/add-crm-marketing-source-master")
	public String addCrmMarketingSource(Model model, HttpSession session) {
		logger.info("Method : addCrmMarketingSource start");
		PipelineMarketingSourceModel pipelinemarketingsourcemodel = new PipelineMarketingSourceModel();
		PipelineMarketingSourceModel form = (PipelineMarketingSourceModel) session.getAttribute("sPipelineMarketingSourceModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		session.setAttribute("message", "");

		if (form != null) {
			model.addAttribute("pipelinemarketingsourcemodel", form);
			session.setAttribute("pipelinemarketingsourcemodel", null);

		} else {
			model.addAttribute("pipelinemarketingsourcemodel", pipelinemarketingsourcemodel);
		}
		logger.info("Method : addCrmMarketingSource end");
		return "crm/addCrmMarketingSource";
	}
	/*
	 * post mapping for adding source
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/add-crm-marketing-source-master")
	public String postCrmMarketingSource(@ModelAttribute PipelineMarketingSourceModel pipelinemarketingsourcemodel, Model model, HttpSession session) {
		logger.info("Method : postCrmMarketingSource starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pipelinemarketingsourcemodel.setCreatedBy(userId);
			resp = restClient.postForObject(env.getPipeline() + "addMarketingSource", pipelinemarketingsourcemodel, JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "") {
			session.setAttribute("message", resp.getMessage());
			session.setAttribute("spipelinemarketingsourcemodel", pipelinemarketingsourcemodel);
			return "redirect:/crm/add-crm-marketing-source-master";
		}
		session.setAttribute("spipelinemarketingsourcemodel", null);
		logger.info("Method : postCrmMarketingSource end");
		return "redirect:/crm/view-crm-marketing-source-master";
	}

	/**
	 * get mapping for crm marketing source master
	 */

	@GetMapping("/view-crm-marketing-source-master")
	public String viewCrmMarketingSource(Model model) {
		logger.info("Method : viewCrmMarketingSource starts");

		logger.info("Method : viewCrmMarketingSource end");
		return "crm/viewCrmMarketingSource";

	}

	/**
	 * get mapping for view Crm marketing source ThroughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-marketing-source-master-throughajax")
	public @ResponseBody DataTableResponse viewCrmMarketingSourceThroughAjax(Model model, HttpServletRequest request,
			@RequestParam String param1) {
		logger.info("Method : viewCrmMarketingSourceThroughAjax starts");

		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();

		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<PipelineMarketingSourceModel>> jsonResponse = new JsonResponse<List<PipelineMarketingSourceModel>>();
			jsonResponse = restClient.postForObject(env.getPipeline() + "getAllSource", tableRequest, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<PipelineMarketingSourceModel> form = mapper.convertValue(jsonResponse.getBody(), new TypeReference<List<PipelineMarketingSourceModel>>() {
			});

			String s = "";
			for (PipelineMarketingSourceModel m : form) {
				byte[] pId = Base64.getEncoder().encode(m.getSource().getBytes());
				s = "";
				s = "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModelData(\""
						+ new String(pId) + "\")'><i class='fa fa-search search'></i></a>";
				s = s + " &nbsp;&nbsp <a href='view-crm-marketing-source-master-edit?id=" + new String(pId)
						+ "' ><i class=\"fa fa-edit\"></i></a> &nbsp;&nbsp; <a href= 'javascript:void(0)' "
						+ "' onclick='deleteSource(\"" + new String(pId)
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
		logger.info("Method : viewCrmMarketingSourceThroughAjax end");
		return response;
	}

	/**
	 * get mapping for edit source
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-marketing-source-master-edit")
	public String editSource(Model model, @RequestParam("id") String encodeId, HttpSession session) {
		logger.info("Method : editSource starts");
		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));
		PipelineMarketingSourceModel pipelinemarketingsourcemodel = new PipelineMarketingSourceModel();
		JsonResponse<PipelineMarketingSourceModel> jsonResponse = new JsonResponse<PipelineMarketingSourceModel>();

		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "getCrmSourceById?id=" + id + "&Action=editSource",
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

		pipelinemarketingsourcemodel = mapper.convertValue(jsonResponse.getBody(), PipelineMarketingSourceModel.class);
		session.setAttribute("message", "");

		model.addAttribute("pipelinemarketingsourcemodel", pipelinemarketingsourcemodel);

		logger.info("Method : editSource end");
		return "crm/addCrmMarketingSource";
	}

	/**
	 * GetMapping for delete marketing source master
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-marketing-source-master-delete")
	public @ResponseBody JsonResponse<Object> deleteCrmSource(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCrmSource starts");

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

			resp = restClient.getForObject(env.getPipeline() + "deleteSourceById?id=" + id1 + "&createdBy=" + userId,
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
		logger.info("Method : deleteCrmSource ends");
		return resp;
	}

	/**
	 * post Mapping for viewInModelData in Crm Source
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-marketing-source-master-modeldata" })
	public @ResponseBody JsonResponse<Object> modelView(Model model, @RequestBody String index, BindingResult result) {
		logger.info("Method : modelView starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(index.getBytes());
		String id = (new String(encodeByte));

		try {
			res = restClient.getForObject(env.getPipeline() + "getCrmSourceById?id=" + id + "&Action=" + "ModelViewSource",
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
