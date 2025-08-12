package nirmalya.aathithya.webmodule.productionplan.controller;

import java.util.Arrays;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.productionplan.model.ManufactureProcessModel;


@Controller

@RequestMapping("production/")
public class ManufactureProcessController {

	Logger logger = LoggerFactory.getLogger(ManufactureProcessController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "processing" })

	public String manageBom(Model model, HttpSession session) {
		logger.info("manufactureProcess Start");
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		
		try {
			DropDownModel[] shift = restTemplate.getForObject(
					env.getProduction() + "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("manufactureProcess End");

		return "production_plan/manufacture-process";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("processing-view")
	public @ResponseBody Object procrssingPlan(@RequestParam String shift, String date  ,HttpSession session) {
		logger.info("Method :procrssingPlan starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getProduction() + "rest-procrssingPlan?orgName=" + orgName + "&orgDivision=" + orgDivision  + "&shift=" + shift + "&date=" + date,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method :procrssingPlan ends");
		return resp;
	}

	// add

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "processing-add-details" })
	public @ResponseBody JsonResponse<Object> addProcessingPlan(
			@RequestBody List<ManufactureProcessModel> addProcessingPlan, HttpSession session) {
		logger.info("Method : addProcessingPlan function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ManufactureProcessModel m : addProcessingPlan) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-addProcessingPlan", addProcessingPlan,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addProcessingPlan function Ends");
	
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("processing-mfgProcessing-data-view")
	public @ResponseBody Object viewMfgProcessingData(HttpSession session) {
		logger.info("Method :viewProcrssingData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-viewMfgProcessingData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method :viewMfgProcessingData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("processing-approve-data")
	public @ResponseBody Object processingDataApprove(@RequestParam String id, HttpSession session) {
		logger.info("Method :processingDataApprove starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-processingDataApprove?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		logger.info("Method :processingDataApprove ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("processing-delete-data")
	public @ResponseBody Object processingDataDelete(@RequestParam String id, HttpSession session) {
		logger.info("Method :processingDataDelete starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-processingDataDelete?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		logger.info("Method :processingDataDelete ends");
		return resp;
	}
	
	// edit-view.
	
	@SuppressWarnings("unchecked")
	@GetMapping("processing-edit-view")
	public @ResponseBody Object procrssingPlanEdit(@RequestParam String id ,HttpSession session) {
		logger.info("Method :procrssingPlanEdit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getProduction() + "rest-procrssingPlanEdit?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method :procrssingPlanEdit ends");
		return resp;
	}

}
