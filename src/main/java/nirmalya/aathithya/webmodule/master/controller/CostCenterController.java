package nirmalya.aathithya.webmodule.master.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.CostCenterModel;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class CostCenterController {
	Logger logger = LoggerFactory.getLogger(CostCenterController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "cost-center" })
	public String costCenter(Model model, HttpSession session) {
		logger.info("Method : costCenter starts");
		try {
			DropDownModel[] costNature = restClient.getForObject(env.getMasterUrl()+"getCostNature", DropDownModel[].class);
			List<DropDownModel> costNatureList = Arrays.asList(costNature);
			
			model.addAttribute("costNatureList", costNatureList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] costLabourType = restClient.getForObject(env.getMasterUrl()+"getLabourType", DropDownModel[].class);
			List<DropDownModel> costLabourList = Arrays.asList(costLabourType);
			
			model.addAttribute("costLabourList", costLabourList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : costCenter ends");
		return "master/costCenter";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/cost-center-save")
	public @ResponseBody JsonResponse<Object> saveCostCenter(@RequestBody CostCenterModel costCenterModel, HttpSession session) {
		logger.info("Method : saveCostCenter starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		costCenterModel.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveCostCenter", costCenterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveCostCenter starts");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/cost-center-get-total-list")
	public @ResponseBody JsonResponse<List<CostCenterModel>> getAllCostCenterList(HttpSession session) {
		logger.info("Method : getAllCostCenterList starts");
		
		JsonResponse<List<CostCenterModel>> resp = new JsonResponse<List<CostCenterModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getAllCostCenterList",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getAllCostCenterList starts");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/cost-center-edit-by-id")
	public @ResponseBody JsonResponse<List<CostCenterModel>> getCostCenterById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getCostCenterById starts");
		
		JsonResponse<List<CostCenterModel>> resp = new JsonResponse<List<CostCenterModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getCostCenterListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getCostCenterById starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/cost-center-save-subcostcenter")
	public @ResponseBody JsonResponse<Object> saveSubCostCenter(@RequestBody CostCenterModel costCenterModel, HttpSession session) {
		logger.info("Method : saveSubCostCenter starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		costCenterModel.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveSubCostCenter", costCenterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveSubCostCenter starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/cost-center-get-leaf-list-by-id")
	public @ResponseBody JsonResponse<List<CostCenterModel>> getLeafListById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getLeafListById starts");
		
		JsonResponse<List<CostCenterModel>> resp = new JsonResponse<List<CostCenterModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getLeafListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getLeafListById starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/cost-center-delete")
	public @ResponseBody JsonResponse<Object> deleteCostCenter(@RequestBody String id, HttpSession session) {
		logger.info("Method : deleteCostCenter starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteCostCenter?id="+id+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : deleteCostCenter end");
		return resp;
	}
}
