package nirmalya.aathithya.webmodule.account.controller;

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

import nirmalya.aathithya.webmodule.account.model.ManageCurrencyModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;



@Controller
@RequestMapping(value = "account")
public class ManageCurrencyController {
	

	Logger logger = LoggerFactory.getLogger(ManageCurrencyController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	
	@GetMapping("/manage-currency")
	
	public String viewCurrency(Model model, HttpSession session) {

		logger.info("Method : manage-currency starts");

		logger.info("Method : manage-currency ends");
		return "account/manage-currency";
	
   }
	

	@SuppressWarnings("unchecked")
	@PostMapping("manage-currency-add")
	public @ResponseBody JsonResponse<Object> addCurrency(@RequestBody ManageCurrencyModel manageCurrency, Model model,
			HttpSession session) {

		logger.info("Method : addCurrency starts" + manageCurrency);

		System.out.println("resp web controller-----------------------------------" + manageCurrency);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restClient.postForObject(env.getAccountUrl() + "restaddcurrency", manageCurrency, JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addCurrency ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-currency-view")
	public @ResponseBody List<ManageCurrencyModel> viewCurrency(HttpSession session) {

		logger.info("Method : viewCurrency starts");

		JsonResponse<List<ManageCurrencyModel>> resp = new JsonResponse<List<ManageCurrencyModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "restViewCurrency", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<ManageCurrencyModel> manageCurrency = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageCurrencyModel>>() {
				});

		resp.setBody(manageCurrency);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewCurrency ends");
		return resp.getBody();
	}
	
	
	
	// edit
			@SuppressWarnings("unchecked")
			@GetMapping("manage-currency-edit")
			public @ResponseBody JsonResponse<List<ManageCurrencyModel>> editcurrencymanageInfo(Model model,
					@RequestParam String id, HttpSession session) {

				logger.info("Method : editcurrencymanageInfo starts" + id);

				JsonResponse<List<ManageCurrencyModel>> jsonResponse = new JsonResponse<List<ManageCurrencyModel>>();

				try {
					jsonResponse = restClient.getForObject(env.getAccountUrl() + "edit-Currency-Info?id=" + id,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				ObjectMapper mapper = new ObjectMapper();
				
				 String date = ""; String drProfDoc = null; String dateFormat = (String)
				 (session).getAttribute("DATEFORMAT");
				

				List<ManageCurrencyModel> manageCurrenct = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<ManageCurrencyModel>>() {
						});

				

				System.out.println("###" + manageCurrenct);
				jsonResponse.setBody(manageCurrenct);

				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

				} else {
					jsonResponse.setMessage("Success");
				}

				System.out.println("REsp" + jsonResponse);
				logger.info("Method :editeditcurrencymanageInfo ends");
				return jsonResponse;
			}

//delete
			@SuppressWarnings("unchecked")
			@GetMapping("manage-currency-delete")
			public @ResponseBody JsonResponse<Object> deletecurrencyDetails(@RequestParam String id,
					 HttpSession session) {
				logger.info("Method : deletecurrencyDetails function starts"+id);

				JsonResponse<Object> res = new JsonResponse<Object>();

				

				try {
					res = restClient.getForObject(env.getAccountUrl() + "delete-currency-Details?id=" + id  , JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}
				logger.info("Method : deletecurrencyDetails function Ends");
				
				System.out.println("Response"+res);
				return res;
			}

		
			
}
