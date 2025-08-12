package nirmalya.aathithya.webmodule.account.controller;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import nirmalya.aathithya.webmodule.account.model.AccountMonthlyprovisionModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;



@Controller
@RequestMapping(value = "account/")
public class AccountMonthlyProvisionController {
	Logger logger = LoggerFactory.getLogger(AccountMonthlyProvisionController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	/*********************** monthly provision **********************/
	@GetMapping("manage-monthlyprovision")
	public String masterGoal(Model model, HttpSession session) {
		logger.info("Method : monthlyprovision starts");
		/*
		 * try { DropDownModel[] getList = restTemplate.getForObject(env.getAccountUrl()
		 * + "rest-getYearList", DropDownModel[].class); List<DropDownModel> getYearList
		 * = Arrays.asList(getList); model.addAttribute("getYearList", getYearList); }
		 * catch (RestClientException e) { e.printStackTrace(); }
		 */
		logger.info("Method : monthlyprovision ends");
		return "account/monthlyprovision";
	}
	
	
	
	/*
	 * category autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-monthlyprovision-get-category-list" })
	public @ResponseBody JsonResponse<Object> getCategoryAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCategoryAutoSearchList starts");
		System.out.println("getCategoryAutoSearchList#####"+model);
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getAccountUrl() + "getCategoryAutoSearchList?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(res+"AAAAA");
		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("success");
		} else {
			res.setMessage("Failed");
		}

		logger.info("Method : getCategoryAutoSearchList ends");
		return res;
	}
	
	
	/*
	 *
	 * add
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-monthlyprovision-master-add" })
	public @ResponseBody JsonResponse<Object> addmonthlyprovisionMaster(HttpSession session, @RequestBody AccountMonthlyprovisionModel data) {
		logger.info("Method : addmonthlyprovisionMaster starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		logger.info("data=======" + data);
		//String dateFormat = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		//data.setCreatedBy(userId);
		try {
			res = restTemplate.postForObject(env.getAccountUrl() + "rest-addmonthlyprovisionMasterdetails", data, JsonResponse.class);
			logger.info("responseddata=======" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method :addmonthlyprovisionMaster ends");
		logger.info("adddd=======" + res);
		return res;

	}
	
	
	    //view
		
		@SuppressWarnings("unchecked")

		@GetMapping("manage-monthlyprovision-view")
		public @ResponseBody List<AccountMonthlyprovisionModel> viewMonthlyprovision(HttpSession session,@RequestParam String year,@RequestParam String month) {

			logger.info("Method : viewMonthlyprovision");

			JsonResponse<List<AccountMonthlyprovisionModel>> resp = new JsonResponse<List<AccountMonthlyprovisionModel>>();
			try {
				resp = restTemplate.getForObject(env.getAccountUrl() + "rest-viewMonthlyprovision?year=" + year + "&month=" + month, JsonResponse.class);
			} catch (

			RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<AccountMonthlyprovisionModel> viewMonthlyprovision = mapper.convertValue(resp.getBody(),
					new TypeReference<List<AccountMonthlyprovisionModel>>() {
					});

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method : viewMonthlyprovision ends");
			logger.info("VIEWWW"+viewMonthlyprovision);
			return viewMonthlyprovision;

		}
		
		            // edit
					@SuppressWarnings("unchecked")

					@GetMapping("manage-monthlyprovision-edit")
					public @ResponseBody JsonResponse<AccountMonthlyprovisionModel> editmonthlyprovision(@RequestParam String Id, HttpSession session) {

						logger.info("Method : editmonthlyprovision starts");
						JsonResponse<AccountMonthlyprovisionModel> jsonResponse = new JsonResponse<AccountMonthlyprovisionModel>();
						logger.info("id====" + Id);
						try {
							jsonResponse = restTemplate.getForObject(env.getAccountUrl() + "editmonthlyprovision?id=" + Id,
									JsonResponse.class);
						} catch (RestClientException e) {
							e.printStackTrace();
						}

						ObjectMapper mapper = new ObjectMapper();
						AccountMonthlyprovisionModel Model = mapper.convertValue(jsonResponse.getBody(), new TypeReference<AccountMonthlyprovisionModel>() {
						});
						
						jsonResponse.setBody(Model);
						if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
							jsonResponse.setCode(jsonResponse.getMessage());
							jsonResponse.setMessage("Unsuccess");
						} else {
							jsonResponse.setMessage("Success");
						}
						logger.info("Method : editmonthlyprovision ends");
						logger.info("edit=====" + jsonResponse);
						return jsonResponse;
					}
					
					
					
					
					// delete

					@SuppressWarnings("unchecked")

					@PostMapping("manage-monthlyprovision-delete")
					public @ResponseBody JsonResponse<Object> deletemonthlyprovision(@RequestParam String id, Model model,
							HttpSession session) {
						logger.info("Method : deletemonthlyprovision function starts");

						JsonResponse<Object> res = new JsonResponse<Object>();
						try {
							res = restTemplate.getForObject(env.getAccountUrl() + "rest-monthlyprovision-delete?id=" + id, JsonResponse.class);
						} catch (RestClientException e) {
							e.printStackTrace();
						}

						String message = res.getMessage();
						if (message != null && message != "") {

						} else {
							res.setMessage("Success");
						}
						logger.info("Method : deletemonthlyprovision function Ends");

						logger.info("RESPPPPPPP" + res);
						return res;
					}
					
				
}
