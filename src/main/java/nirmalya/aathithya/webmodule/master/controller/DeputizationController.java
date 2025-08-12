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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.DeputizationWebModel;

@Controller
@RequestMapping(value = "/master")
public class DeputizationController {
	Logger logger = LoggerFactory.getLogger(DeputizationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/view-deputization")
	public String salaryRevision(Model model, HttpSession session) {
		logger.info("Method : salary starts");

		try {
			DropDownModel[] financialYrType = restTemplate
					.getForObject(env.getMasterUrl() + "getFinancialYrForDeputization", DropDownModel[].class);

			List<DropDownModel> financialYr = Arrays.asList(financialYrType);
			model.addAttribute("financialYr", financialYr);

		} catch (RestClientException e) {
			
			e.printStackTrace();
		}

		try {

			DropDownModel[] requisition = restTemplate.getForObject(env.getMasterUrl() + "get-employee-listDeputization",
					DropDownModel[].class);
			List<DropDownModel> EmployeeList = Arrays.asList(requisition);

			model.addAttribute("EmployeeList", EmployeeList);

			logger.info("EMPLOYEEE LIST" + EmployeeList);
		} catch (Exception e) {
			e.printStackTrace();

		}
 
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID"); 
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		model.addAttribute("userId", userId);
		
		logger.info("Method : salary ends");
		return "master/view-deputization";
	}
	/*
	 *
	 * add 
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-deputization-save" })
	public @ResponseBody JsonResponse<Object> adddeputization(HttpSession session,
			@RequestBody DeputizationWebModel data) {
		logger.info("Method : adddeputization starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		logger.info("data=======" + data);
		String dateFormat = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			/*
			 * if (data.getEffectiveDate() != null && data.getEffectiveDate() != "") {
			 * data.setEffectiveDate(DateFormatter.dateFormat(data.getEffectiveDate(),
			 * dateFormat)); }
			 */
			data.setFromDate(DateFormatter.inputDateFormat(data.getFromDate(), dateFormat));
			data.setToDate(DateFormatter.inputDateFormat(data.getToDate(), dateFormat));
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setCreatedBy(userId);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-deputization", data,
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

		logger.info("Method : adddeputization ends");
		logger.info("adddd=======" + res);
		return res;

	}

	/*
	 * get date list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-deputization-date-ajax" })
	public @ResponseBody JsonResponse<Object> getDateListDeputization(@RequestParam String name) {
		logger.info("Method : getDateListDeputization starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-dateListDeputization?id=" + name, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getDateListDeputization ends");
		logger.info("LISTTTT" + res);
		return res;

	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("view-deputization-DetailsView")
	public @ResponseBody List<DeputizationWebModel> viewdeputization(HttpSession session) {
		logger.info("Method : viewdeputization starts");

		JsonResponse<List<DeputizationWebModel>> resp = new JsonResponse<List<DeputizationWebModel>>();
		List<DeputizationWebModel> List = new ArrayList<DeputizationWebModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewdeputization", JsonResponse.class);
			List = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<DeputizationWebModel> Model = mapper.convertValue(resp.getBody(),
				new TypeReference<List<DeputizationWebModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		
		for (DeputizationWebModel a : Model) {
			if (a.getFromDate() != null && a.getFromDate() != "") {
				a.setFromDate(DateFormatter.dateFormat(a.getFromDate(), dateFormat));
			}
			if (a.getToDate() != null && a.getToDate() != "") {
				a.setToDate(DateFormatter.dateFormat(a.getToDate(), dateFormat));
			}
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : viewdeputization  ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp);
		logger.info("Viewwww " + Model);
		return Model;

	}

	 // edit 

	@SuppressWarnings("unchecked")
	@GetMapping("view-deputization-edit")
	public @ResponseBody JsonResponse<DeputizationWebModel> editDeputization(@RequestParam String Id,
			HttpSession session) {

		logger.info("Method : editDeputization starts");
		JsonResponse<DeputizationWebModel> jsonResponse = new JsonResponse<DeputizationWebModel>();
		logger.info("id===="+Id);

		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "rest-editDeputization-edit?id=" + Id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
		DeputizationWebModel Model = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<DeputizationWebModel>() {
				});
		/*for (SalaryRevisionModel a : jsonResponse) {
			if (a.getEffectiveDate() != null && a.getEffectiveDate() != "") {
				a.setEffectiveDate(DateFormatter.dateFormat(a.getEffectiveDate(), dateFormat));
			}
		}*/
		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editDeputization ends");
		logger.info("editDeputization====="+jsonResponse);
		return jsonResponse;
	}
	/*
	 * get name and designation list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-deputization-nameDesignation-ajax" })
	public @ResponseBody JsonResponse<Object> getnameAndDesignationListDeputization(@RequestParam String name) {
		logger.info("Method : getnameAndDesignationListDeputization starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-nameandDesignationListDeputization?id=" + name, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getnameAndDesignationListDeputization ends");
		
		logger.info("LISTTTT" + res);
		return res;

	}

			
	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("view-deputization-delete")
	public @ResponseBody JsonResponse<Object> deleteDeputization(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteDeputization function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-Deputization-delete?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDeputization function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}

}
