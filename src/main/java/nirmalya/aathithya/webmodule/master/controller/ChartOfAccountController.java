package nirmalya.aathithya.webmodule.master.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ChartOfAccountModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class ChartOfAccountController {
	Logger logger = LoggerFactory.getLogger(CostCenterController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping(value = { "chart-account" })
	public String chartOfAccount(Model model, HttpSession session) {
		logger.info("Method : chartOfAccount starts");
		
		try {
			DropDownModel[] level = restClient.getForObject(env.getMasterUrl()+"getAllLevel", DropDownModel[].class);
			List<DropDownModel> levelList = Arrays.asList(level);
			
			model.addAttribute("levelList", levelList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : chartOfAccount ends");
		return "master/chartOfAccounts";
	}

	
	@SuppressWarnings("unchecked")
	@PostMapping("/chart-account-save")
	public @ResponseBody JsonResponse<Object> saveChartAccount(@RequestBody ChartOfAccountModel chartOfAccountModel, HttpSession session) {
		logger.info("Method : saveChartAccount starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		chartOfAccountModel.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveChartOfAccount", chartOfAccountModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveChartAccount starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/chart-account-get-total-list")
	public @ResponseBody JsonResponse<List<ChartOfAccountModel>> getAllChartOfAccList(HttpSession session) {
		logger.info("Method : getAllChartOfAccList starts");
		
		JsonResponse<List<ChartOfAccountModel>> resp = new JsonResponse<List<ChartOfAccountModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getAllChartAccList",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getAllChartAccList starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/chart-account-edit-by-id")
	public @ResponseBody JsonResponse<List<ChartOfAccountModel>> getChartAccById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getCostCenterById starts");
		
		JsonResponse<List<ChartOfAccountModel>> resp = new JsonResponse<List<ChartOfAccountModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getCAListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getCAListById starts");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/chart-account-delete")
	public @ResponseBody JsonResponse<Object> deleteChartAcc(@RequestBody String id, HttpSession session) {
		logger.info("Method : deleteChartAcc starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteCAById?id="+id+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : deleteChartAcc end");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/chart-account-save-subchartacc")
	public @ResponseBody JsonResponse<Object> saveSubCA(@RequestBody ChartOfAccountModel chartOfAccountModel, HttpSession session) {
		logger.info("Method : saveSubCA starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		chartOfAccountModel.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveSubCA", chartOfAccountModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveSubCA starts");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/chart-account-get-leaf-list-by-id")
	public @ResponseBody JsonResponse<List<ChartOfAccountModel>> getChartLeafListById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getChartLeafListById starts");
		
		JsonResponse<List<ChartOfAccountModel>> resp = new JsonResponse<List<ChartOfAccountModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getChartLeafListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getChartLeafListById starts");
		return resp;
	}
}
