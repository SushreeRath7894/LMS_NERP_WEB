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
import nirmalya.aathithya.webmodule.pipeline.model.PipelineMarketingMediumModel;

@Controller
@RequestMapping(value = "crm")
public class PipelineMarketingMediumController {
	Logger logger = LoggerFactory.getLogger(PipelineMarketingMediumController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	/*
	 * get mapping for adding medium
	 */

	@GetMapping("/add-crm-marketing-medium-master")
	public String addCrmMarketMedium(Model model, HttpSession session) {
		logger.info("Method : addCrmMarketMedium start");
		PipelineMarketingMediumModel crmMarketingMediumModel = new PipelineMarketingMediumModel();
		PipelineMarketingMediumModel form = (PipelineMarketingMediumModel) session.getAttribute("scrmMarketingMediumModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		session.setAttribute("message", "");

		if (form != null) {
			model.addAttribute("crmMarketingMediumModel", form);
			session.setAttribute("crmMarketingMediumModel", null);

		} else {
			model.addAttribute("crmMarketingMediumModel", crmMarketingMediumModel);
		}
		logger.info("Method : addCrmMarketMedium end");
		return "crm/addCrmMarketMedium";
	}
	/*
	 * post mapping for adding medium
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/add-crm-marketing-medium-master")
	public String postCrmMarketingMedium(@ModelAttribute PipelineMarketingMediumModel crmMarketingMediumModel, Model model,
			HttpSession session) {
		logger.info("Method : postCrmMarketingMedium starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			crmMarketingMediumModel.setCreatedBy(userId);
			resp = restClient.postForObject(env.getPipeline() + "addMarketingMedium", crmMarketingMediumModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "") {
			session.setAttribute("message", resp.getMessage());
			session.setAttribute("scrmMarketingMediumModel", crmMarketingMediumModel);
			return "redirect:/crm/add-crm-marketing-medium-master";
		}
		session.setAttribute("scrmMarketingMediumModel", null);
		logger.info("Method : postCrmMarketingMedium end");
		return "redirect:/crm/view-crm-marketing-medium-master";
	}

	/**
	 * get mapping for crm marketing medium master
	 */

	@GetMapping("/view-crm-marketing-medium-master")
	public String viewCrmMarketingMedium(Model model) {
		logger.info("Method : viewCrmMarketingMedium starts");

		logger.info("Method : viewCrmMarketingMedium end");
		return "crm/viewCrmMarketingMedium";

	}

	/**
	 * get mapping for view Crm marketing medium ThroughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-marketing-medium-master-throughajax")
	public @ResponseBody DataTableResponse viewCrmMarketingMediumThroughAjax(Model model, HttpServletRequest request,
			@RequestParam String param1) {
		logger.info("Method : viewCrmMarketingMediumThroughAjax starts");

		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();

		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<PipelineMarketingMediumModel>> jsonResponse = new JsonResponse<List<PipelineMarketingMediumModel>>();
			jsonResponse = restClient.postForObject(env.getPipeline() + "getAllMedium", tableRequest, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<PipelineMarketingMediumModel> form = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<PipelineMarketingMediumModel>>() {
					});

			String s = "";
			for (PipelineMarketingMediumModel m : form) {
				byte[] pId = Base64.getEncoder().encode(m.getMedium().getBytes());
				s = "";
				s = "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModelData(\""
						+ new String(pId) + "\")'><i class='fa fa-search search'></i></a>";
				s = s + " &nbsp;&nbsp <a href='view-crm-marketing-medium-master-edit?id=" + new String(pId)
						+ "' ><i class=\"fa fa-edit\"></i></a> &nbsp;&nbsp; <a href= 'javascript:void(0)' "
						+ "' onclick='deleteMedium(\"" + new String(pId) + "\")' ><i class=\"fa fa-trash\"></i></a> ";
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
		logger.info("Method : viewCrmMarketingMediumThroughAjax end");
		return response;
	}

	/**
	 * get mapping for edit medium
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-marketing-medium-master-edit")
	public String editMedium(Model model, @RequestParam("id") String encodeId, HttpSession session) {
		logger.info("Method : editMedium starts");
		byte[] encodeByte = Base64.getDecoder().decode(encodeId.getBytes());
		String id = (new String(encodeByte));
		PipelineMarketingMediumModel crmMarketingMediumModel = new PipelineMarketingMediumModel();
		JsonResponse<PipelineMarketingMediumModel> jsonResponse = new JsonResponse<PipelineMarketingMediumModel>();

		try {
			jsonResponse = restClient.getForObject(
					env.getPipeline() + "getCrmMediumById?id=" + id + "&Action=editMarketing", JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		ObjectMapper mapper = new ObjectMapper();

		crmMarketingMediumModel = mapper.convertValue(jsonResponse.getBody(), PipelineMarketingMediumModel.class);
		session.setAttribute("message", "");

		model.addAttribute("crmMarketingMediumModel", crmMarketingMediumModel);

		logger.info("Method : editMedium end");
		return "crm/addCrmMarketMedium";
	}

	/**
	 * GetMapping for delete marketing medium master
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-marketing-medium-master-delete")
	public @ResponseBody JsonResponse<Object> deleteCrmMedium(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCrmMedium starts");

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

			resp = restClient.getForObject(env.getPipeline() + "deleteMediumById?id=" + id1 + "&createdBy=" + userId,
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
		logger.info("Method : deleteCrmMedium ends");
		return resp;
	}

	/**
	 * post Mapping for viewInModelData in Crm Medium
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-crm-marketing-medium-master-modeldata" })
	public @ResponseBody JsonResponse<Object> modelView(Model model, @RequestBody String index, BindingResult result) {
		logger.info("Method : modelView starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		byte[] encodeByte = Base64.getDecoder().decode(index.getBytes());
		String id = (new String(encodeByte));

		try {
			res = restClient.getForObject(
					env.getPipeline() + "getCrmMediumById?id=" + id + "&Action=" + "ModelViewMedium", JsonResponse.class);

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
