package nirmalya.aathithya.webmodule.account.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import nirmalya.aathithya.webmodule.account.model.ManageLeadgerModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller
@RequestMapping(value = "account")
public class ManageLeadgerController {

	Logger logger = LoggerFactory.getLogger(ManageLeadgerController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/manage-ledger")
	public String viewColor(Model model, HttpSession session) {

		logger.info("Method : Manage-Ledger starts");

		try {
			DropDownModel[] country = restClient.getForObject(env.getAccountUrl() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] ledgerType = restClient.getForObject(
					env.getAccountUrl() + "/getLedgerTypeList?org=" + orgName + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ledgerTypeList = Arrays.asList(ledgerType);
			
			model.addAttribute("ledgerTypeList", ledgerTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : Manage-Ledger ends");
		return "account/manage-ledger";

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-ledger-stateList" })
	public @ResponseBody JsonResponse<Object> getStateListInShoukeen(@RequestParam String id) {
		logger.info("Method : getStateListaccount starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "ledgerGetStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getStateListacount ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("manage-ledger-add")
	public @ResponseBody JsonResponse<Object> addledger(@RequestBody ManageLeadgerModel manageleadgermodel, Model model,
			HttpSession session) {

		logger.info("Method : addledger starts" + manageleadgermodel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		manageleadgermodel.setOrganization(orgName);
		manageleadgermodel.setOrgDivision(orgDivision);
		try {

			resp = restClient.postForObject(env.getAccountUrl() + "resaddledger", manageleadgermodel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addledger ends" + resp);

		return resp;

	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("manage-ledger-view")
	public @ResponseBody List<ManageLeadgerModel> viewLeadger(HttpSession session) {

		logger.info("Method : viewManageLeadger starts");

		JsonResponse<List<ManageLeadgerModel>> resp = new JsonResponse<List<ManageLeadgerModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {
			resp = restClient.getForObject(
					env.getAccountUrl() + "restViewleadger?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<ManageLeadgerModel> manageleadgermodel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageLeadgerModel>>() {
				});

		resp.setBody(manageleadgermodel);

		logger.info("Method : viewManageLeadger ends");
		return resp.getBody();
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("manage-ledger-edit")
	public @ResponseBody JsonResponse<List<ManageLeadgerModel>> editmanageleadgerInfo(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : editManageLeadger starts" + id);

		JsonResponse<List<ManageLeadgerModel>> jsonResponse = new JsonResponse<List<ManageLeadgerModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "editmanageledger?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		String date = "";
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<ManageLeadgerModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<ManageLeadgerModel>>() {
				});

		jsonResponse.setBody(manageleadger);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method :editManageLeadger ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-ledger-delete")
	public @ResponseBody JsonResponse<Object> deleteleadgerDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteManageLeadger function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {
			res = restClient.getForObject(env.getAccountUrl() + "deletemanageLedger?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteManageLeadger function Ends");

		System.out.println("Response" + res);
		return res;
	}

	// search
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-ledger-group-list" })
	public @ResponseBody JsonResponse<ManageLeadgerModel> getProductAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getgroupNameSearchList starts");
		// System.out.println("QuotationNewModel"+searchValue);
		JsonResponse<ManageLeadgerModel> res = new JsonResponse<ManageLeadgerModel>();

		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			res = restClient.getForObject(env.getAccountUrl() + "getundergrouplist?id=" + searchValue + "&orgName="
					+ orgName + "&orgDiv=" + orgDiv, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getCode() != null) {
			res.setCode("success");
		} else {
			res.setCode("Unsuccess");
		}
		logger.info("RESPONSE===>" + res);
		logger.info("Method : getgroupNameSearchList ends");
		return res;
	}
}
