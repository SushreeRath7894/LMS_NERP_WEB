package nirmalya.aathithya.webmodule.qa.controller;

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
import nirmalya.aathithya.webmodule.qa.model.QcMasterModel;


@SuppressWarnings("unused")
@Controller

@RequestMapping(value = "production/")
public class QcMasterController {
	Logger logger = LoggerFactory.getLogger(QcMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

// method for page return
	@GetMapping(value = { "qc-master" })
	public String uploadedPlan(Model model, HttpSession session) {
		logger.info("qcMaster Start");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { DropDownModel[] parameter = restTemplate.getForObject(
		 * env.getProduction() + "get-parameter-lists?org=" + organization + "&orgDiv="
		 * + orgDivision, DropDownModel[].class); List<DropDownModel> parameterList =
		 * Arrays.asList(parameter); model.addAttribute("parameterList", parameterList);
		 * } catch (RestClientException e) { e.printStackTrace(); }
		 */
		try {
			DropDownModel[] itemname = restTemplate.getForObject(
					env.getProduction() + "get-itemname-list?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> itemnameList = Arrays.asList(itemname);
			model.addAttribute("itemnameList", itemnameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("qcMaster End");
		return "qa/qc-master";
	}
	
	// Get Parameter List.
	@SuppressWarnings("unchecked")

	@GetMapping("qc-master-get-parameterList")
	public @ResponseBody Object getParameterList(Model model ,HttpSession session) {
		logger.info("Method :getParameterList starts");
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
					env.getProduction() + "rest-getParameterList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.getMessage();
		resp.getCode();

		
		//System.out.println("getParameterList===" + resp);
		logger.info("Method :getParameterList ends");
		return resp;
	}

	// addQc

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "qc-master-add" })
	public @ResponseBody JsonResponse<Object> addQc(@RequestBody List<QcMasterModel> qc, HttpSession session) {
		logger.info("Method : addQc function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (QcMasterModel m : qc) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("qc===" + qc);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-addQc", qc, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addQc function Ends");
		//System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	// viewQc

	@SuppressWarnings("unchecked")

	@GetMapping("qc-master-view")
	public @ResponseBody Object viewQc(HttpSession session) {
		logger.info("Method :viewQc starts");
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
					env.getProduction() + "rest-viewQc?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		//System.out.println("view===" + resp);
		logger.info("Method :viewQc ends");
		return resp;
	}

	// editQc
	@SuppressWarnings("unchecked")
	@GetMapping("qc-master-edit")
	public @ResponseBody Object editQc(@RequestParam String id, HttpSession session) {
		logger.info("Method :editQc starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-editQc?id=" + id + "&orgName=" + orgName
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
		//System.out.println("edit>>>-----" + resp);
		logger.info("Method :editQc ends");
		return resp;
	}

	// DeleteQc

	@SuppressWarnings("unchecked")
	@PostMapping("qc-master-delete")
	public @ResponseBody JsonResponse<Object> deleteQc(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteQc function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getProduction() + "rest-deleteQc?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteQc function Ends");

		//System.out.println("RESPPPPPPP" + res);
		return res;
	}
	
	// Add New Parameter.
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "qc-master-add-parameter"})
	public @ResponseBody JsonResponse<Object> addNewParameter(@RequestBody List<QcMasterModel> qc, HttpSession session) {
		logger.info("Method : addNewParameter function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (QcMasterModel m : qc) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		//System.out.println("qc===" + qc);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-addNewParameter", qc, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addNewParameter function Ends");
		//System.out.println("addNewParameter Save>>>------" + resp);
		return resp;
	}
	
	
	// Formula Column
	
	// get Field dropdown.
	@SuppressWarnings("unchecked")

	@GetMapping("qc-master-get-fieldList")
	public @ResponseBody Object getFieldList(Model model ,HttpSession session) {
		logger.info("Method :getFieldList starts");
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
					env.getProduction() + "rest-getFieldList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.getMessage();
		resp.getCode();

		//System.out.println("getFieldList===" + resp);
		logger.info("Method :getFieldList ends");
		return resp;
	}
	
	// Paramater list For Calculation.
	
	@SuppressWarnings("unchecked")

	@GetMapping("qc-master-get-paramFieldList")
	public @ResponseBody Object getParamFieldList(Model model ,HttpSession session) {
		logger.info("Method :getParamFieldList starts");
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
					env.getProduction() + "rest-getParamFieldList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.getMessage();
		resp.getCode();

		System.out.println("getParamFieldList===" + resp);
		logger.info("Method :getParamFieldList ends");
		return resp;
	}
	
	// Add Field
	
	@SuppressWarnings("unchecked")
	@GetMapping("qc-master-add-field")
	public @ResponseBody Object addField(@RequestParam String fvalue ,HttpSession session) {
		logger.info("Method :addField starts");
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
		//System.out.println("fvalue>>>>>-------"+fvalue);
		try {
			resp = restTemplate.getForObject(
					env.getProduction() + "rest-addField?orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId + "&fvalue=" + fvalue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.getMessage();
		resp.getCode();

		
		//System.out.println("addField===" + resp);
		logger.info("Method :addField ends");
		return resp;
	}
	
	// Add Formula.
	
	/*
	 * @SuppressWarnings({ "unchecked" })
	 * 
	 * @PostMapping(value = { "qc-master-add-formula"}) public @ResponseBody
	 * JsonResponse<Object> addNewFormula(@RequestBody List<QcMasterModel> qc,
	 * HttpSession session) { logger.info("Method : addNewFormula function starts");
	 * JsonResponse<Object> resp = new JsonResponse<Object>(); String userId = "";
	 * String organization = ""; String orgDivision = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID"); organization =
	 * (String) session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * 
	 * } for (QcMasterModel m : qc) { m.setCreatedBy(userId);
	 * m.setOrganization(organization); m.setOrgDivision(orgDivision);
	 * 
	 * } //System.out.println("qc===" + qc); try { resp =
	 * restTemplate.postForObject(env.getProduction() + "rest-addNewFormula", qc,
	 * JsonResponse.class); } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method : addNewFormula function Ends");
	 * //System.out.println("addNewFormula Save>>>------" + resp); return resp; }
	 */

}