package nirmalya.aathithya.webmodule.master.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.BudgetPlanMasterModel;
import nirmalya.aathithya.webmodule.master.model.CCAccountMapModel;
import nirmalya.aathithya.webmodule.master.model.FiscalYearModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class BudgetPlanMasterController {

	Logger logger = LoggerFactory.getLogger(BudgetPlanMasterController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "budget-plan" })
	public String budgetPlan(Model model, HttpSession session) {
		logger.info("Method : budgetPlan starts");
		
		try {
			DropDownModel[] year = restClient.getForObject(env.getMasterUrl()+"getYearList", DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			
			List<Integer> yearData = new ArrayList<Integer>();
			
			int startYear = Integer.parseInt(yearList.get(0).getKey());  
			int endYear = Integer.parseInt(yearList.get(0).getName());  
			
			for(int i = startYear; i <= endYear; i++) {
				yearData.add(i);
			}
			
			model.addAttribute("yearList", yearData);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : budgetPlan ends");
		return "master/budgetPlan";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/budget-plan-save-year")
	public @ResponseBody JsonResponse<Object> saveBudgetPlanYear(@RequestBody FiscalYearModel yearDtls, HttpSession session) {
		logger.info("Method : saveBudgetPlanYear starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		yearDtls.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveBudgetPlanYear", yearDtls,
					JsonResponse.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : saveBudgetPlanYear starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/budget-plan-get-year-list")
	public @ResponseBody JsonResponse<List<FiscalYearModel>> getYearDataList(@RequestBody String yearDtls, HttpSession session) {
		logger.info("Method : getYearDataList starts");
		
		JsonResponse<List<FiscalYearModel>> resp = new JsonResponse<List<FiscalYearModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getYearDataList",
					JsonResponse.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : getYearDataList starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/budget-plan-get-cc-list")
	public @ResponseBody JsonResponse<List<CCAccountMapModel>> getCCAccountDataList(@RequestBody BudgetPlanMasterModel yearDtls, HttpSession session) {
		logger.info("Method : getCCAccountDataList starts");
		
		JsonResponse<List<CCAccountMapModel>> resp = new JsonResponse<List<CCAccountMapModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getCCAccountDataList?id="+yearDtls.getYear()+"&mnth="+yearDtls.getId(),
					JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			List<CCAccountMapModel> ccDtls = mapper.convertValue(resp.getBody(),
					new TypeReference<List<CCAccountMapModel>>() {
					});
			
			for(CCAccountMapModel m : ccDtls) {
				CCAccountMapModel[] chart = restClient.getForObject(env.getMasterUrl() + "getChartAccountDetails?id="+m.getId()+"&year="+yearDtls.getYear()+"&mnth="+yearDtls.getId(), CCAccountMapModel[].class);
				List<CCAccountMapModel> chartAccList = Arrays.asList(chart);
				
				m.setChartAccList(chartAccList);
			}
			
			resp.setBody(ccDtls);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : getCCAccountDataList starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/budget-plan-save")
	public @ResponseBody JsonResponse<Object> saveBudgetPlanDtls(@RequestBody List<BudgetPlanMasterModel> budgetPlanDtls, HttpSession session) {
		logger.info("Method : saveBudgetPlanDtls starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(BudgetPlanMasterModel m : budgetPlanDtls) {
			m.setCreatedBy(userId);
		}
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveBudgetPlanDtls" ,budgetPlanDtls,
					JsonResponse.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : saveBudgetPlanDtls starts");
		return resp;
	}
}
