package nirmalya.aathithya.webmodule.property.stakeholder.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.property.stakeholder.model.StackholderServiceProviderModel;

@Controller
@RequestMapping(value = "property")
public class StackholderServiceProviderController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(StackholderServiceProviderController.class);

	/* service Provider */

	@GetMapping(value = { "service-provider" })
	public String serviceProvider(Model model, HttpSession session) {
		logger.info("Method :serviceProvider starts");
		/* rent-ledger */
		String userId = "";
		String userRole = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
		}
		model.addAttribute("userId", userId);
		logger.info("Method : serviceProvider ends");
		return "new-property/service-provider";
	}
	/// add
			@SuppressWarnings("unchecked")
			@PostMapping("service-provider-add")
			public @ResponseBody JsonResponse<Object> saveRentLedger(@RequestBody StackholderServiceProviderModel StackholderServiceProviderModel,
					HttpSession session) {
				logger.info("Method:RentLedger save starts" + StackholderServiceProviderModel);
				JsonResponse<Object> res = new JsonResponse<Object>();
				logger.info("DDDDDDDDDDDD" + StackholderServiceProviderModel);
				String userId = (String) session.getAttribute("USER_ID");
				StackholderServiceProviderModel.setCreatedby(userId);
				try {
					res = restClient.postForObject(env.getPropertyUrl() + "service-provider-add-RentLedger",
							StackholderServiceProviderModel, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (res.getMessage() != null) {
					res.setCode(res.getMessage());
					res.setMessage("Unsuccess");
				} else {
					res.setMessage("Success");
					
				}

				logger.info("Method:RentLedger save ends");
				return res;
			}
			
			/// view
			@SuppressWarnings("unchecked")
			@GetMapping("/service-provider-view")
			public @ResponseBody List<StackholderServiceProviderModel> viewserviceprovider(HttpSession session,@RequestParam String userid,
					@RequestParam String fromDate, @RequestParam String toDate) {
				logger.info("Method : viewserviceprovider starts"+userid+fromDate+toDate);

				JsonResponse<List<StackholderServiceProviderModel>> resp = new JsonResponse<List<StackholderServiceProviderModel>>();
				List<StackholderServiceProviderModel> returnList = new ArrayList<StackholderServiceProviderModel>();

				try {
					resp = restClient.getForObject(env.getPropertyUrl() + "viewServiceProviders?userid="+userid+"&fromDate="+fromDate+"&toDate="+toDate,
							JsonResponse.class);
					returnList = resp.getBody();
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				//logger.info(returnList);
				
				logger.info("Method : viewserviceprovider ends");
				return returnList;
			}
			///edit
			@SuppressWarnings("unchecked")
			@GetMapping("service-provider-edit")
			public @ResponseBody JsonResponse<List<StackholderServiceProviderModel>> editserviceprovider(@RequestParam String id,
					HttpSession session) {

				logger.info("Method : editserviceprovider starts"+id	);

				JsonResponse<List<StackholderServiceProviderModel>> jsonResponse = new JsonResponse<List<StackholderServiceProviderModel>>();
				try {
					jsonResponse = restClient.getForObject(env.getPropertyUrl() + "service-editRentLedger?id=" + id,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}
				

				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

				} else {
					jsonResponse.setMessage("Success");
				}

				logger.info("REsp" + jsonResponse);
				logger.info("Method : editserviceprovider ends");
				return jsonResponse;
			}
			
			@SuppressWarnings("unchecked")
			@PostMapping(value = { "service-provider-autosearch-property" })
			public @ResponseBody JsonResponse<DropDownModel> getPropertyAutoSearch(Model model, @RequestBody String id,
					BindingResult result) {
				logger.info("Method : getPropertyAutoSearch starts");
				JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

				try {
					res = restClient.getForObject(env.getPropertyUrl() + "getPropertyListByAutoSearch?id=" + id,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (res.getMessage() != null) {

					res.setCode(res.getMessage());
					res.setMessage("Unsuccess");
				} else {
					res.setMessage("success");
				}
				logger.info("Method : getPropertyAutoSearch ends" + res);
				return res;
			}

}
