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
import nirmalya.aathithya.webmodule.pipeline.model.PipelineLostReasonModel;

@Controller
@RequestMapping(value = "pipeline")
public class PipelineLostReasonController {
	Logger logger = LoggerFactory.getLogger(PipelineLostReasonController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	/*
	 * get mapping for adding lost reason
	 */

	@GetMapping("/add-crm-lost-reason")
	public String addCrmLostReason(Model model, HttpSession session) {
		logger.info("Method : addCrmLostReason start");
		PipelineLostReasonModel crmLostReason = new PipelineLostReasonModel();
		PipelineLostReasonModel form = (PipelineLostReasonModel) session.getAttribute("scrmlostreason");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		session.setAttribute("message", "");

		if (form != null) {
			model.addAttribute("crmLostReason", form);
			session.setAttribute("scrmlostreason", null);

		} else {
			model.addAttribute("crmLostReason", crmLostReason);
		}
		logger.info("Method : addCrmLostReason end");
		return "crm/addCrmLostReason";
	}
	/*
	 * post mapping for adding lost reason
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/add-crm-lost-reason")
	public String postCrmLostReason(@ModelAttribute PipelineLostReasonModel crmLostReason, Model model,
			HttpSession session) {
		logger.info("Method : postCrmLostReason starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			crmLostReason.setCreatedBy(userId);
			resp = restClient.postForObject(env.getPipeline() + "addLostReason", crmLostReason, JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "") {
			session.setAttribute("message", resp.getMessage());
			session.setAttribute("crmLostReason", crmLostReason);
			return "redirect:/crm/add-crm-lost-reason";
		}
		session.setAttribute("scrmlostreason", null);
		logger.info("Method : postCrmLostReason end");
		return "redirect:/crm/view-crm-lost-reason";
	}

	/**
	 * get mapping for crm log reason
	 */

	@GetMapping("/view-crm-lost-reason")
	public String viewCrmLostReason(Model model) {
		logger.info("Method : viewCrmLostReason starts");

		logger.info("Method : viewCrmLostReason end");
		return "crm/viewCrmLostReason";

	}

	/**
	 * get mapping for view Crm Losr Reason ThroughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-lost-reason-throughajax")
	public @ResponseBody DataTableResponse viewCrmLostReasonThroughAjax(Model model, HttpServletRequest request,
			@RequestParam String param1) {
		logger.info("Method : viewCrmLostReasonThroughAjax starts");

		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();

		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<PipelineLostReasonModel>> jsonResponse = new JsonResponse<List<PipelineLostReasonModel>>();
			jsonResponse = restClient.postForObject(env.getPipeline() + "getAllCrmLostReason", tableRequest,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<PipelineLostReasonModel> form = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<PipelineLostReasonModel>>() {
					});

			String s = "";
			for (PipelineLostReasonModel m : form) {
				byte[] pId = Base64.getEncoder().encode(m.getLostReason().getBytes());
				s = "";
				s = "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModelData(\""
						+ new String(pId) + "\")'><i class='fa fa-search search'></i></a>";
				s = s + " &nbsp;&nbsp <a href='view-crm-lost-reason-edit?id=" + new String(pId)
						+ "' ><i class=\"fa fa-edit\"></i></a> &nbsp;&nbsp; <a href= 'javascript:void(0)' "
						+ "' onclick='deleteLostReason(\"" + new String(pId)
						+ "\")' ><i class=\"fa fa-trash\"></i></a> ";
				m.setAction(s);
				s = "";

				if (m.getLostReasonStatus()) {
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
		logger.info("Method : viewCrmLostReasonThroughAjax end");
		return response;
	}

	/**
	 * get mapping for edit lost reason
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-lost-reason-edit")
	public String editLostReason(Model model, @RequestParam("id") String encodeId, HttpSession session) {
		logger.info("Method : editLostReason starts");
		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));
		PipelineLostReasonModel crmLostReason = new PipelineLostReasonModel();
		JsonResponse<PipelineLostReasonModel> jsonResponse = new JsonResponse<PipelineLostReasonModel>();

		try {
			jsonResponse = restClient.getForObject(
					env.getPipeline() + "getCrmLostReasonById?id=" + id + "&Action=editLostReason", JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		ObjectMapper mapper = new ObjectMapper();

		crmLostReason = mapper.convertValue(jsonResponse.getBody(), PipelineLostReasonModel.class);
		session.setAttribute("message", "");

		model.addAttribute("crmLostReason", crmLostReason);

		logger.info("Method : editLostReason end");
		return "crm/addCrmLostReason";
	}

	/**
	 * GetMapping for delete lost reasons
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-lost-reason-delete")
	public @ResponseBody JsonResponse<Object> deleteCrmLostReasons(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCrmLostReasons starts");

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

			resp = restClient.getForObject(env.getPipeline() + "deleteLostReasonById?id=" + id1 + "&createdBy=" + userId,
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
		logger.info("Method : deleteCrmLostReasons ends");
		return resp;
	}

	/**
	 * post Mapping for viewInModelData in lost reason
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-lost-reason-modeldata" })
	public @ResponseBody JsonResponse<Object> modelView(Model model, @RequestBody String index, BindingResult result) {
		logger.info("Method : modelView starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(index.getBytes());
		String id = (new String(encodeByte));

		try {
			res = restClient.getForObject(
					env.getPipeline() + "getCrmLostReasonById?id=" + id + "&Action=" + "ModelViewLostReason",
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
