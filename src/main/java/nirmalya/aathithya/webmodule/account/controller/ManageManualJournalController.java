package nirmalya.aathithya.webmodule.account.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountBankModel;
import nirmalya.aathithya.webmodule.account.model.AccountBranchModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.account.model.ManualJournalModel;
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
public class ManageManualJournalController {
	Logger logger = LoggerFactory.getLogger(ManageManualJournalController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Customer in Account
	 */
	
	//view-account-journal-voucher-getAccountDebitGroup
	
	/*
	 * customer name Auto search    debit  1
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-manual-journal-getCustomerName" })
	public @ResponseBody JsonResponse<ManualJournalModel> getCustomerNameJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getCustomerNameJournalSearch starts");
		JsonResponse<ManualJournalModel> res = new JsonResponse<ManualJournalModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl()+ "/getCustomerNameSearch?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCustomerNameJournalSearch ends");
		return res;
	}
	
	//view-manual-journal-getCustomerNameCredit
	
	/*
	 * customer name Auto search      1
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-manual-journal-getCustomerNameCredit" })
	public @ResponseBody JsonResponse<ManualJournalModel> getCustomerNameJournalCreditSearch(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getCustomerNameJournalCreditSearch starts");
		JsonResponse<ManualJournalModel> res = new JsonResponse<ManualJournalModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl()+ "/getCustomerNameSearch?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCustomerNameJournalCreditSearch ends");
		return res;
	}

	
	/*
	@SuppressWarnings("unchecked")
	@PostMapping("/view-account-add")
	public @ResponseBody JsonResponse<Object> addAccount(@RequestBody AccountModel accountModel,
			HttpSession session) {

		logger.info("Method : addAccount starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web AccountModel ======================" + accountModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			accountModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addAccount", accountModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addAccount ends");

		return resp;
	}*/

	/** 
	 * View Customer Account
	 * 
	 */
	
	
	@GetMapping("/view-manual-journal")
	public String viewManageManualJournal(Model model, HttpSession session) {
		logger.info("Method : viewManageManualJournal start");	
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewManageManualJournal end");
		return "account/manage-manual-journals";
	}
	
	
	//view-manual-journal-add-journal
	
	/*
	 * post Mapping for add manual journal
	 * 
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-manual-journal-add-journal", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<DropDownModel> addManualJournalVoucher(
			@RequestBody List<ManualJournalModel> manualJournalModel, Model model, HttpSession session) {
		logger.info("Method : addManualJournalVoucher function starts");
		System.out.println(manualJournalModel + "ManualJournalModel");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		manualJournalModel.get(0).setCreatedBy(userId);
		try {

			res = restClient.postForObject(env.getAccountUrl() + "addManualJournalVoucher",
					manualJournalModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : addManualJournalVoucher function Ends");
		return res;
	}


	/**
	 * Delete manual journal Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-manual-journal-delete-id")
	public @ResponseBody JsonResponse<Object> deleteManualJournal(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteManualJournal function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteManualJournal?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteManualJournal function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	
	/**
	 * View Account Manual Journal Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-manual-journal-throughAjax")
	public @ResponseBody List<ManualJournalModel> viewManualJournal(HttpSession session) {

		logger.info("Method : viewManualJournal starts");

		JsonResponse<List<ManualJournalModel>> resp = new JsonResponse<List<ManualJournalModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewManualDlts", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<ManualJournalModel> manualJournalModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManualJournalModel>>() {
				});
		
		for (ManualJournalModel i : manualJournalModel) {
			if (i.getCreatedOn() != null && i.getCreatedOn() != "") {
				date = DateFormatter.dateFormat(i.getCreatedOn(), dateFormat);
				i.setCreatedOn(date);
				System.out.println("date for creation------------------"+date);
			}
		}
		
		resp.setBody(manualJournalModel);
		
		logger.info("Method : viewManualJournal ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Manual Journal Record
	 */
	
	//view-manual-journal-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-manual-journal-edit")
	public @ResponseBody JsonResponse<List<ManualJournalModel>> editManualJournalInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editManualJournalInfo starts" + id);
		
		JsonResponse<List<ManualJournalModel>> jsonResponse = new JsonResponse<List<ManualJournalModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editManualJournalInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<ManualJournalModel> manualJournalModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<ManualJournalModel>>() {
				});
		
		for (ManualJournalModel i : manualJournalModel) {
			if (i.getCreatedOn() != null && i.getCreatedOn() != "") {
				date = DateFormatter.dateFormat(i.getCreatedOn(), dateFormat);
				i.setCreatedOn(date);
				System.out.println("start date---------------"+date);
			}	
			
		}
		
		System.out.println("###" + manualJournalModel);
		jsonResponse.setBody(manualJournalModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editManualJournalInfo ends");
		return jsonResponse;
	}
	
	
	
}
