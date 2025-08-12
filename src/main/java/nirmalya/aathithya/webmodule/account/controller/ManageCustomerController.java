package nirmalya.aathithya.webmodule.account.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountBankModel;
import nirmalya.aathithya.webmodule.account.model.AccountBranchModel;
import nirmalya.aathithya.webmodule.account.model.AccountCusModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCustomerModel;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class ManageCustomerController {
	Logger logger = LoggerFactory.getLogger(ManageCustomerController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Customer in Account
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-customer-add")
	public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody AccountCusModel accountCusModel,
	        HttpSession session) {

	    logger.info("Method : addAccountCustomer starts");

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    System.out.println("web AccountModel ======================" + accountCusModel);

	    try {
	        String userId = "";
	        try {
	            userId = (String) session.getAttribute("USER_ID");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        System.out.println("created by id-------------------------------" + userId);
	        accountCusModel.setCreatedBy(userId);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<AccountCusModel> requestEntity = new HttpEntity<>(accountCusModel, headers);

	        ResponseEntity<JsonResponse> response = restClient.exchange(
	                env.getAccountUrl() + "/addAccountCustomer",
	                HttpMethod.POST,
	                requestEntity,
	                JsonResponse.class
	        );

	        resp = response.getBody();
	        System.out.println("Status Code: " + response.getStatusCode());
	        System.out.println("Response Message: " + resp.getMessage());

	    } catch (HttpClientErrorException ex) {
	        // This block captures 409 and similar error responses
	        System.out.println("HTTP Error Status Code: " + ex.getStatusCode());

	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            resp = mapper.readValue(ex.getResponseBodyAsString(), JsonResponse.class);
	            System.out.println("Error Response Code: " + resp.getCode());
	            System.out.println("Error Message: " + resp.getMessage());
	        } catch (IOException ioEx) {
	            ioEx.printStackTrace();
	            resp.setCode("ERROR");
	            resp.setMessage("Could not parse error response.");
	        }

	    } catch (RestClientException e) {
	        e.printStackTrace();
	        resp.setCode("ERROR");
	        resp.setMessage("Server error while adding customer.");
	    }

	    logger.info("Method : addAccountCustomer ends");

	    return resp;
	}



	/** 
	 * View Customer Account
	 * 
	 */
	
	
	@GetMapping("/view-customer")
	public String viewManageCus(Model model, HttpSession session) {
		logger.info("Method : viewManageCus start");	
		try {
			DropDownModel[] bank = restClient.getForObject(env.getAccountUrl() + "/getBankList",
					DropDownModel[].class);

			List<DropDownModel> bankList = Arrays.asList(bank);
			model.addAttribute("bankList", bankList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restClient.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Met    hod : viewManageCus end");
		return "account/manage-customer";
	}
	

	/**
	 * Delete Customer Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-customer-delete-id")
	public @ResponseBody JsonResponse<Object> deleteCustomerDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteCustomerDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteCusAccDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteCustomerDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	
	
	/**
	 * View Customer Account Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-customer-throughAjax")
	public @ResponseBody List<AccountCusModel> viewCustomerAccount(HttpSession session) {

		logger.info("Method : viewCustomerAccount starts");

		JsonResponse<List<AccountCusModel>> resp = new JsonResponse<List<AccountCusModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewCustDtls", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountCusModel> accountCusModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountCusModel>>() {
				});
		
		for (AccountCusModel i : accountCusModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}
		
		resp.setBody(accountCusModel);
		
		logger.info("Method : viewCustomerAccount ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Customer Account Record
	 */
	
	//view-customer-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-customer-edit")
	public @ResponseBody JsonResponse<List<AccountCusModel>> editCustomerInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editCustomerInfo starts" + id);
		
		JsonResponse<List<AccountCusModel>> jsonResponse = new JsonResponse<List<AccountCusModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editAccCusInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountCusModel> accountCusModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountCusModel>>() {
				});
		
		for (AccountCusModel i : accountCusModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("start date---------------"+date);
			}	
			
		}
		
		System.out.println("###" + accountCusModel);
		jsonResponse.setBody(accountCusModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editCustomerInfo ends");
		return jsonResponse;
	}
	
	//view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-customer-stateList" })
	public @ResponseBody JsonResponse<Object> getstateCusList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "getStateLists1?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("state" + res);
		logger.info("Method : getstateCusList ends");
		return res;
	}
	
	
}
