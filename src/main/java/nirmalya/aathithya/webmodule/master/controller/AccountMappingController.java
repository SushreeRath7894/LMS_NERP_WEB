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
import nirmalya.aathithya.webmodule.master.model.AccountMappingModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class AccountMappingController {
	Logger logger = LoggerFactory.getLogger(AccountMappingController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping(value = { "account-mapping" })
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
		return "master/accountMapping";
	}
	@SuppressWarnings("unchecked")
	@PostMapping(value = "account-mapping-save" )
	public @ResponseBody JsonResponse<Object> accountMapping(@RequestBody List<AccountMappingModel> accountMappingModel,
			Model model, HttpSession session) {
		logger.info("Method : accountMapping function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			for (int i = 0; i < accountMappingModel.size(); i++) {
				accountMappingModel.get(i).setCreatedBy(userId);
			}
			res = restClient.postForObject(env.getMasterUrl() + "accountMappingSave", accountMappingModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : accountMapping function Ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/account-mapping-list")
	public @ResponseBody JsonResponse<List<AccountMappingModel>> getAllAccMappingAccList(HttpSession session) {
		logger.info("Method : getAllAccMappingAccList starts");
		
		JsonResponse<List<AccountMappingModel>> resp = new JsonResponse<List<AccountMappingModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getAllAccMappingList",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getAllAccMappingAccList starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/account-mapping-list-by-id")
	public @ResponseBody JsonResponse<List<AccountMappingModel>> geAccMapByListById(@RequestBody String id,HttpSession session) {
		logger.info("Method : geAccMapByListById starts");
		
		JsonResponse<List<AccountMappingModel>> resp = new JsonResponse<List<AccountMappingModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getAccMapListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : geAccMapByListById starts");
		return resp;
	}
}
