package nirmalya.aathithya.webmodule.budget.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.budget.model.FinancialYearWebModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "budget")
public class FinancialYearWebController {

	Logger logger = LoggerFactory.getLogger(FinancialYearWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "manage-financial-year" })
	public String manageFY(Model model, HttpSession session) {
		logger.info("Method : manageFY starts");

		logger.info("Method : manageFY ends");
		return "budget/financial-year";
	}
	
	//add========

	@SuppressWarnings("unchecked")
	@PostMapping("manage-financialYear-add")
	public @ResponseBody JsonResponse<Object> addFinancialYear(@RequestBody FinancialYearWebModel financialYearWebModel,
			Model model, HttpSession session) {

		logger.info("Method : addFinancialYear starts" + financialYearWebModel);

		System.out.println("resp web controller-----------------------------------" + financialYearWebModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		String createdBy = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdBy = (String) session.getAttribute("USER_ID");
			
			financialYearWebModel.setCreatedBy(createdBy);
			financialYearWebModel.setOrgName(orgName);
			financialYearWebModel.setOrgDivision(orgDivision);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restClient.postForObject(env.getBudgetUrl() + "restAddFinancialYear", financialYearWebModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addFinancialYear ends" + resp);

		return resp;
	}
	// view ==============

//	@SuppressWarnings("unchecked")
//	@GetMapping("manage-financialYear-view")
//	public @ResponseBody List<FinancialYearWebModel> viewFinancialYear(HttpSession session) {
//
//		logger.info("Method : viewFinancialYear starts");
//
//		JsonResponse<List<FinancialYearWebModel>> resp = new JsonResponse<List<FinancialYearWebModel>>();
//
//		try {
//			resp = restClient.getForObject(env.getBudgetUrl() + "restViewFinancialYear", JsonResponse.class);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		List<FinancialYearWebModel> departmentWebModel = mapper.convertValue(resp.getBody(),
//				new TypeReference<List<FinancialYearWebModel>>() {
//				});
//
//		resp.setBody(departmentWebModel);
//		System.out.println("resp.getBody()-----------" + resp.getBody());
//
//		logger.info("Method : viewFinancialYear ends");
//		return resp.getBody();
//	}
	//manage-finacialYear-view-through-json
	
	@SuppressWarnings("unchecked")

	@GetMapping("manage-finacialYear-view-through-json")
	public @ResponseBody Object viewQc(HttpSession session) {
		logger.info("Method :viewBudget starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
	
		String id="";
		String orgName = "";
		String orgDivision = "";
		try {
			id = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("checkId -------------------------------------------===" + id);
		try {
			resp = restClient.getForObject(
					env.getBudgetUrl() + "viewFinancialYear?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		System.out.println("view===" + resp);
		logger.info("Method :viewBudget ends");
		return resp;
	}
//	// Edit

	@SuppressWarnings("unchecked")
	@GetMapping("manage-financialyear-edit")
	public @ResponseBody JsonResponse<List<FinancialYearWebModel>> editFinancialYear(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editFinancialYear starts" + id);

		JsonResponse<List<FinancialYearWebModel>> jsonResponse = new JsonResponse<List<FinancialYearWebModel>>();

		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "restfinancialyearedit?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		
		List<FinancialYearWebModel> financialyear = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<FinancialYearWebModel>>() {
				});
		
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Success");

		} else {
			jsonResponse.setMessage("Unsuccess");
		}
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :editDepartmentInfo ends");
		return jsonResponse;
	}

//	// Delete

	@SuppressWarnings("unchecked")
	@GetMapping("manage-financialYear-delete")
	public @ResponseBody JsonResponse<Object> deleteFinancialYearInfo(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteFinancialYearInfo function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getBudgetUrl() + "financialyear-delete?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteFinancialYearInfo function Ends");

		System.out.println("Response" + res);
		return res;
	}

}
