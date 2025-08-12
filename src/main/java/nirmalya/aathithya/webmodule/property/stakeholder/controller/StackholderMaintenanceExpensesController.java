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
import nirmalya.aathithya.webmodule.property.stakeholder.model.StackholderMaintenanceExpensesModel;

@Controller
@RequestMapping(value = "property")
public class StackholderMaintenanceExpensesController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(StackholderMaintenanceExpensesController.class);

	/* maintenanceExp */

	@GetMapping(value = { "maintenance-expenses" })
	public String maintenanceExp(Model model, HttpSession session) {
		logger.info("Method :maintenance-exp starts");
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
		
		
		try {
			DropDownModel[] month = restClient.getForObject(env.getPropertyUrl() + "getmonth-List",
					DropDownModel[].class);
			List<DropDownModel> monthList = Arrays.asList(month);

			model.addAttribute("monthList", monthList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("userId", userId);

		logger.info("Method : prmaintenance-exp ends");
		return "new-property/maintenance-expenses";
	}
	/// add
	@SuppressWarnings("unchecked")
	@PostMapping("maintenance-expenses-description-add")
	public @ResponseBody JsonResponse<Object> saveMaintainenceDescription(@RequestBody StackholderMaintenanceExpensesModel StackholderMaintenanceExpensesModel,
			HttpSession session) {
		logger.info("Method:maintainence description save starts" + StackholderMaintenanceExpensesModel);
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("DDDDDDDDDDDD" + StackholderMaintenanceExpensesModel);
		try {
			res = restClient.postForObject(env.getPropertyUrl() + "expenses-add-Maintainence-Description",
					StackholderMaintenanceExpensesModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
			
		}

		logger.info("Method:maintainence description save ends");
		return res;
	}
	/// view
	@SuppressWarnings("unchecked")
	@GetMapping("maintenance-expenses-view")
	public @ResponseBody List<StackholderMaintenanceExpensesModel> viewMaintainenceDescription(HttpSession session,@RequestParam String userid,
			@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : MaintainenceDescription starts"+userid+fromDate+toDate);

		JsonResponse<List<StackholderMaintenanceExpensesModel>> resp = new JsonResponse<List<StackholderMaintenanceExpensesModel>>();
		List<StackholderMaintenanceExpensesModel> returnList = new ArrayList<StackholderMaintenanceExpensesModel>();

		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "expenses-viewMaintainenceDescription?userid="+userid+"&fromDate="+fromDate+"&toDate="+toDate,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		//logger.info(returnList);
		
		logger.info("Method :MaintainenceDescription ends");
		return returnList;
	}
	///edit
	@SuppressWarnings("unchecked")
	@GetMapping("maintenance-expenses-description-edit")
	public @ResponseBody JsonResponse<List<Object>> editMaintainenceDescription(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editMaintainenceDescription starts"+id	);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		try {
			jsonResponse = restClient.getForObject(env.getPropertyUrl() + "expenses-editMaintainenceDescription?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editMaintainenceDescription ends");
		return jsonResponse;
	}
	//delete 
	@SuppressWarnings("unchecked")
	@GetMapping("maintenance-expenses-description-delete")
	public @ResponseBody JsonResponse<Object> deleteMaintainenceDescription(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteMaintainenceDescription function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		

		try {
			res = restClient.getForObject(env.getPropertyUrl() + "expenses-delete-MaintainenceDescription?id=" + id  , JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteMaintainenceDescription function Ends");
		
		logger.info("RESPPPPPPP"+res);
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "maintenance-expenses-autosearch-property" })
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
