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
import nirmalya.aathithya.webmodule.property.stakeholder.model.StakeholderRentLedgerDetailsModel;

@Controller
@RequestMapping(value = "property")
public class StackholderRentLedgerController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(StackholderRentLedgerController.class);

	/* rentLedger */

	@GetMapping(value = { "rent-ledger" })
	public String rentLedger(Model model, HttpSession session) {
		logger.info("Method :rentLedger starts");
		/* rent-ledger */
		String userId = "";
		String userRole = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			logger.info("userRole"+userRole);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] month = restClient.getForObject(env.getPropertyUrl() + "getmonth-List",
					DropDownModel[].class);
			List<DropDownModel> monthList = Arrays.asList(month);

			model.addAttribute("monthList", monthList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] year = restClient.getForObject(env.getPropertyUrl() + "getyear-List",
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;
			if (data.contentEquals("rol001") || data.contentEquals("rol034")) {
				logger.info("data===="+data);
				model.addAttribute("trRole", data);
			}
				
		}
		
		//model.addAttribute("userId", userId);
		model.addAttribute("userId", userRole);
		
		logger.info("Method : rentLedger ends");
		if (userRole.equals("rol034")) {
			return "new-property/rentledger";
		} else if (userRole.equals("rol033")) {
			return "new-property/rent-ledger";
		} else {
			return "new-property/rent-ledger";
		}
		
	}

	/// add
	@SuppressWarnings("unchecked")
	@PostMapping("rent-ledger-add")
	public @ResponseBody JsonResponse<Object> saveRentLedger(
			@RequestBody StakeholderRentLedgerDetailsModel StakeholderRentLedgerDetailsModel, HttpSession session) {
		logger.info("Method:RentLedger save starts" + StakeholderRentLedgerDetailsModel);
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("DDDDDDDDDDDD" + StakeholderRentLedgerDetailsModel);
		String userId = (String) session.getAttribute("USER_ID");
		StakeholderRentLedgerDetailsModel.setCreatedby(userId);
		try {
			res = restClient.postForObject(env.getPropertyUrl() + "rent-ledger-add-RentLedger",
					StakeholderRentLedgerDetailsModel, JsonResponse.class);
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

	@GetMapping("/rent-ledger-view")
	public @ResponseBody List<StakeholderRentLedgerDetailsModel> viewRentLedger(HttpSession session,
			@RequestParam String userid, @RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : View starts");

		JsonResponse<List<StakeholderRentLedgerDetailsModel>> resp = new JsonResponse<List<StakeholderRentLedgerDetailsModel>>();
		List<StakeholderRentLedgerDetailsModel> returnList = new ArrayList<StakeholderRentLedgerDetailsModel>();

		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "rent-ledger-viewRentLedger?userid=" + userid
					+ "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : View ends");
		return returnList;
	}

	/// edit
	@SuppressWarnings("unchecked")
	@GetMapping("rent-ledger-edit")
	public @ResponseBody JsonResponse<List<StakeholderRentLedgerDetailsModel>> editRentLedger(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editRentLedger starts" + id);

		JsonResponse<List<StakeholderRentLedgerDetailsModel>> jsonResponse = new JsonResponse<List<StakeholderRentLedgerDetailsModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPropertyUrl() + "editRentLedger?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editRentLedger ends");
		return jsonResponse;
	}
	
	/// edit2
		@SuppressWarnings("unchecked")
		@GetMapping("rent-ledger-editRent")
		public @ResponseBody JsonResponse<List<StakeholderRentLedgerDetailsModel>> editRentLedgers(@RequestParam String id,
				HttpSession session) {

			logger.info("Method : editRentLedger starts" + id);

			JsonResponse<List<StakeholderRentLedgerDetailsModel>> jsonResponse = new JsonResponse<List<StakeholderRentLedgerDetailsModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPropertyUrl() + "editRentLedgers?id=" + id,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : editRentLedger ends");
			return jsonResponse;
		}

	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("rent-ledger-delete")
	public @ResponseBody JsonResponse<Object> deleteRentLedger(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteRentLedger function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getPropertyUrl() + "rent-ledger-delete-Rent-Ledger?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteRentLedger function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "rent-ledger-autosearch-property" })
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

	@SuppressWarnings("unchecked")
	@PostMapping("rent-ledger-autoComplete")
	public @ResponseBody JsonResponse<List<StakeholderRentLedgerDetailsModel>> rentLedgerprimeData(
			@RequestParam String id, HttpSession session) {

		logger.info("Method : rentLedgerprimeData starts" + id);

		JsonResponse<List<StakeholderRentLedgerDetailsModel>> jsonResponse = new JsonResponse<List<StakeholderRentLedgerDetailsModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPropertyUrl() + "rentledger-prime-data?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : rentLedgerprimeData ends");
		return jsonResponse;
	}
}
