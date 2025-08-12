package nirmalya.aathithya.webmodule.account.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountCreditorLedgerModel;
import nirmalya.aathithya.webmodule.account.model.AccountCusModel;
import nirmalya.aathithya.webmodule.account.model.AccountCustomerOrderModel;
import nirmalya.aathithya.webmodule.account.model.AccountJournalVoucherModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.account.model.AccountPaymentVoucherDetailsModel;
import nirmalya.aathithya.webmodule.account.model.ContraVoucherModel;
import nirmalya.aathithya.webmodule.account.model.SalesInvoicePaymentModel;
import nirmalya.aathithya.webmodule.account.model.VendorListModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.common.utils.IndianNumberFormat;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class AccountReceiptVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountReceiptVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	/**
	 * Add Account
	 */

	@GetMapping("/receipt-voucher")
	public String receiptVoucher(Model model, HttpSession session) {
		logger.info("Method : receiptVoucher start");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			logger.info("Bank Name list Start");
			DropDownModel[] bankAccountList = restClient.getForObject(env.getAccountUrl()
					+ "/getBankAccountPaymentList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Bank Name list End");

		try {
			DropDownModel[] country = restClient.getForObject(env.getAccountUrl() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(country);
			System.out.println("countryList" + countryList);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : receiptVoucher end");
		return "account/manage-receipt-voucher";
	}

	/*
	 * post Mapping for add SPA table staff assign
	 * 
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "receipt-voucher-add-journal", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<DropDownModel> addReceiptJournalVoucher(
			@RequestBody List<AccountJournalVoucherModel> journalVoucherModel, Model model, HttpSession session) {
		logger.info("Method : addReceiptJournalVoucher function starts");
		System.out.println(journalVoucherModel + "journalVoucherModel");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// journalVoucherModel.get(0).setCreatedBy(userId);
		for (AccountJournalVoucherModel m : journalVoucherModel) {

			m.setOrganization(orgName);
			m.setCreatedBy(userId);
			m.setOrgDivision(orgDivision);
		}
		try {

			res = restClient.postForObject(env.getAccountUrl() + "addReceiptJournalVoucher", journalVoucherModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : addReceiptJournalVoucher function Ends");
		return res;
	}

	// JSON View Account Ajax
	@SuppressWarnings("unchecked")
	@GetMapping("receipt-voucher-view")
	public @ResponseBody Object viewReceiptVoucher(HttpSession session) {

		logger.info("Method :viewReceiptVoucher starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(
					env.getAccountUrl() + "/viewReceiptVoucher?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewReceiptVoucher ends" + resp);

		return resp;
	}

	/**
	 * Edit Account Record
	 */

	// view-account-edit

	// view-account-edit

	@SuppressWarnings("unchecked")

	@GetMapping("receipt-voucher-edit")
	public @ResponseBody JsonResponse<List<AccountJournalVoucherModel>> editReceiptInfo(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : editReceiptInfo starts" + id);

		JsonResponse<List<AccountJournalVoucherModel>> jsonResponse = new JsonResponse<List<AccountJournalVoucherModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editReceiptInfo?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";

		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<AccountJournalVoucherModel> accountModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountJournalVoucherModel>>() {
				});

		System.out.println("###" + accountModel);
		jsonResponse.setBody(accountModel);

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editReceiptInfo ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("receipt-voucher-vouchernumber")
	public @ResponseBody JsonResponse<List<DropDownModel>> vouchernumber(HttpSession session) {
		logger.info("Method : vouchernumber starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			resp = restClient.getForObject(
					env.getAccountUrl() + "/getReceiptvoucherNumber?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : vouchernumber ends" + resp);
		return resp;
	}

	/*
	 * debit account Auto search 1
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = { "receipt-voucher-getAccountDebitGroup" })
	 * public @ResponseBody JsonResponse<ContraVoucherModel>
	 * getDebitAccountJournalSearch(Model model,
	 * 
	 * @RequestBody String searchValue, BindingResult result) {
	 * logger.info("Method : getDebitAccountJournalSearch starts");
	 * JsonResponse<ContraVoucherModel> res = new
	 * JsonResponse<ContraVoucherModel>();
	 * 
	 * try { res = restClient.getForObject(env.getAccountUrl() +
	 * "/getDebitReceiptAccountSearch?id=" + searchValue, JsonResponse.class); }
	 * catch (Exception e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method : getDebitAccountJournalSearch ends"); return res; }
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "receipt-voucher-getAccountDebitGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getDebitAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getDebitAccountJournalSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "/getDebitReceiptAccountSearch?id=" + searchValue
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getDebitAccountJournalSearch ends");
		return res;
	}

	// view-account-journal-voucher-getAccountCreditGroup

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "receipt-voucher-getAccountCreditGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getCreditAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCreditAccountJournalSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "/getCreditReceiptAccountSearch?id=" + searchValue
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCreditAccountJournalSearch ends");
		return res;
	}

	/**
	 * Delete Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("receipt-voucher-delete-id")
	public @ResponseBody JsonResponse<Object> deleteJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteAccoutDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteReceiptDetails?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("receipt-voucher-view-filteredData")
	public @ResponseBody Object viewReceiptFilteredData(HttpSession session, @RequestParam String fromDate,
			String toDate) {

		logger.info("Method :viewReceiptFilteredData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/viewReceiptFilter?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :viewReceiptFilteredData ends" + resp);

		return resp;
	}

	/* Auto Search For Dealer */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = { "receipt-voucher-delearList" }) public @ResponseBody
	 * JsonResponse<VendorListModel> getDealerList(Model model, @RequestBody String
	 * searchValue, BindingResult result, HttpSession session) {
	 * logger.info("Method : getDealerList starts"); String orgName = ""; String
	 * orgDivision = ""; try {
	 * 
	 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { logger.error(e.getMessage()); } JsonResponse<VendorListModel> res = new
	 * JsonResponse<VendorListModel>();
	 * 
	 * try { res = restClient.getForObject(env.getAccountUrl() + "getDealerList?id="
	 * + searchValue + "&org=" + orgName + "&orgDiv=" + orgDivision,
	 * JsonResponse.class); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * if (res.getMessage() != null) {
	 * 
	 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
	 * res.setMessage("success"); }
	 * 
	 * logger.info("Method : getDealerList ends"); return res; }
	 */

	/* Auto Search For Distributor */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = { "receipt-voucher-getDistributorList" })
	 * public @ResponseBody JsonResponse<VendorListModel> getDistributorList(Model
	 * model, @RequestBody String searchValue, BindingResult result, HttpSession
	 * session) { logger.info("Method : getDistributorList starts"); String orgName
	 * = ""; String orgDivision = ""; try {
	 * 
	 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { logger.error(e.getMessage()); } JsonResponse<VendorListModel> res = new
	 * JsonResponse<VendorListModel>();
	 * 
	 * try { res = restClient.getForObject(env.getAccountUrl() +
	 * "getDistributorList?id=" + searchValue + "&org=" + orgName + "&orgDiv=" +
	 * orgDivision, JsonResponse.class); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * if (res.getMessage() != null) {
	 * 
	 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
	 * res.setMessage("success"); }
	 * 
	 * logger.info("Method : getDistributorList ends"); return res; }
	 */

	/* To Get Delear Order Account To Delaer Id */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("receipt-voucher-delearOrder") public @ResponseBody
	 * JsonResponse<List<ShoukeenCustomerOrderModel>> getDelaerOrder(HttpSession
	 * session, Model model,
	 * 
	 * @RequestParam String id) {
	 * 
	 * logger.info("Method :getDelaerOrder starts" + id);
	 * JsonResponse<List<ShoukeenCustomerOrderModel>> resp = new
	 * JsonResponse<List<ShoukeenCustomerOrderModel>>();
	 * 
	 * String orgName = ""; String orgDivision = ""; try {
	 * 
	 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { logger.error(e.getMessage()); }
	 * 
	 * try {
	 * 
	 * resp = restClient.getForObject(env.getAccountUrl() +
	 * "getDelaerOrder?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id="
	 * + id, JsonResponse.class);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method :getDelaerOrder ends"); System.out.println("RESPONSEview"
	 * + resp); return resp; }
	 */

	/* To Get Order Account To Distributor Id */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("receipt-voucher-distributor") public @ResponseBody
	 * JsonResponse<List<ShoukeenCustomerOrderModel>>
	 * getDistributorOrder(HttpSession session, Model model, @RequestParam String
	 * id) {
	 * 
	 * logger.info("Method :getDistributorOrder starts" + id);
	 * JsonResponse<List<ShoukeenCustomerOrderModel>> resp = new
	 * JsonResponse<List<ShoukeenCustomerOrderModel>>();
	 * 
	 * String orgName = ""; String orgDivision = ""; try {
	 * 
	 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { logger.error(e.getMessage()); }
	 * 
	 * try {
	 * 
	 * resp = restClient.getForObject(env.getAccountUrl() +
	 * "getDistributorOrder?orgName=" + orgName + "&orgDivision=" + orgDivision +
	 * "&id=" + id, JsonResponse.class);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method :getDistributorOrder ends");
	 * System.out.println("RESPONSEview" + resp); return resp; }
	 */

	// save upload image
	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getAccountDocUpload() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		logger.info(imageName);
		return imageName;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("receipt-voucher-payment-received")
	public @ResponseBody JsonResponse<Object> addPaymentInvoice(
			@RequestBody SalesInvoicePaymentModel salesInvoicePaymentModel, Model model, HttpSession session) {

		logger.info("Method : addPaymentInvoice starts" + salesInvoicePaymentModel);

		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (RestClientException e) {
			e.printStackTrace();

		}
		salesInvoicePaymentModel.setCreatedBy(userId);
		salesInvoicePaymentModel.setOrganization(orgName);
		salesInvoicePaymentModel.setOrgDivision(orgDivision);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		MultipartFile inputFile = (MultipartFile) session.getAttribute("receipt");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				salesInvoicePaymentModel.setDocName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {

			resp = restClient.postForObject(env.getAccountUrl() + "addPaymentInvoice", salesInvoicePaymentModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode() == "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setCode("Success");
		}
		logger.info("Method : addPaymentInvoice ends" + resp);

		return resp;
	}

	// addPaymentForUser

	@SuppressWarnings("unchecked")
	@PostMapping("receipt-voucher-addPaymentForUser")
	public @ResponseBody JsonResponse<Object> addPaymentForUser(
			@RequestBody SalesInvoicePaymentModel salesInvoicePaymentModel, Model model, HttpSession session) {

		logger.info("Method : addPaymentForUser starts");
		// JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		JsonResponse<Object> response = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		salesInvoicePaymentModel.setCreatedBy(userId);
		salesInvoicePaymentModel.setOrganization(orgName);
		salesInvoicePaymentModel.setOrgDivision(orgDivision);
		try {
			response = restClient.postForObject(env.getAccountUrl() + "addPaymentForUser", salesInvoicePaymentModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Success");
		} else {
			response.setMessage("Unsuccess");
		}

		logger.info("response=====" + response);
		logger.info("Method : addPaymentForUser ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("receipt-voucher-getCreditNoteList")
	public @ResponseBody Object getCreditNoteListPayment(HttpSession session, @RequestParam String vendorId) {

		logger.info("Method :getCreditNoteListPayment starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restGetCreditNoteListPayment?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&vendorId=" + vendorId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :getCreditNoteListPayment ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "receipt-voucher-customerList" })
	public @ResponseBody JsonResponse<VendorListModel> getCustomerList(Model model, @RequestBody String searchValue,
			BindingResult result, HttpSession session) {
		logger.info("Method : getCustomerList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<VendorListModel> res = new JsonResponse<VendorListModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "getCustomerListRest?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getCustomerList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("receipt-voucher-customerOrderList")
	public @ResponseBody JsonResponse<List<AccountCustomerOrderModel>> getCustomerOrderList(HttpSession session,
			Model model, @RequestParam String id) {

		logger.info("Method :getCustomerOrderList starts" + id);
		JsonResponse<List<AccountCustomerOrderModel>> resp = new JsonResponse<List<AccountCustomerOrderModel>>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "getCustomerOrderList?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getCustomerOrderList ends");
		return resp;
	}

	@GetMapping("/receipt-register")
	public String receiptRegister(Model model, HttpSession session) {
		logger.info("Method : receiptRegister start");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] bankAccountList = restClient
					.getForObject(env.getAccountUrl() + "/getBankAccountPaymentList", DropDownModel[].class);

			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : receiptRegister end");
		return "account/receipt-register";
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/receipt-register-Pdf" })
	public void receiptRegisterPdf(HttpServletResponse response, Model model, @RequestParam String voucherType,
			@RequestParam String ActivityType, String fromDate, String toDate, HttpSession session) {
		logger.info("Method :receiptRegisterPdf starts");

		AccountCreditorLedgerModel product = new AccountCreditorLedgerModel();
		JsonResponse<AccountCreditorLedgerModel> jsonResponse = new JsonResponse<AccountCreditorLedgerModel>();

		JsonResponse<List<AccountCreditorLedgerModel>> report = new JsonResponse<List<AccountCreditorLedgerModel>>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			report = restClient.getForObject(
					env.getAccountUrl() + "accountRegisterPdf?voucherType=" + voucherType + "&fromDate=" + fromDate
							+ "&toDate=" + toDate + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("responseReport=>>>>>>" + report);

		ObjectMapper mapper = new ObjectMapper();

		List<AccountCreditorLedgerModel> reportcard = mapper.convertValue(report.getBody(),
				new TypeReference<List<AccountCreditorLedgerModel>>() {
				});

		jsonResponse.setBody(product);

		double totalDebit = reportcard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getTransactionDebitAmt().replace(",", ""))).sum();
		String totalDebitString = IndianNumberFormat.formatIndianNumber(totalDebit);

		double totalCredit = reportcard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getTransactionCreditAmt().replace(",", ""))).sum();
		String totalCreditString = IndianNumberFormat.formatIndianNumber(totalCredit);

		String fromToDate = fromDate + " TO " + toDate;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("vendorCard", reportcard);
		data.put("fromToDate", fromToDate);
		data.put("voucherType", ActivityType);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);
		String filename = "receipt-register-pdf_" + fromDate + "_" + toDate + ".pdf";

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/account-register-pdf", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/receipt-voucher-pdf" })
	public void receiptVoucherPdf(HttpServletResponse response, Model model, @RequestParam String fromDate,
			String toDate, HttpSession session) {
		logger.info("Method :receiptVoucherPdf starts");
		AccountCreditorLedgerModel product = new AccountCreditorLedgerModel();

		JsonResponse<List<AccountCreditorLedgerModel>> receiptVoucher = new JsonResponse<List<AccountCreditorLedgerModel>>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			receiptVoucher = restClient.getForObject(env.getAccountUrl() + "receiptVoucherPdf?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&orgDivision=" + orgDivision + "&orgName=" + orgName, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AccountCreditorLedgerModel> receiptVouchercard = mapper.convertValue(receiptVoucher.getBody(),
				new TypeReference<List<AccountCreditorLedgerModel>>() {
				});

		double totalDebit = receiptVouchercard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getTransactionDebitAmt().replace(",", ""))).sum();
		String totalDebitString = IndianNumberFormat.formatIndianNumber(totalDebit);

		double totalCredit = receiptVouchercard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getTransactionCreditAmt().replace(",", ""))).sum();
		String totalCreditString = IndianNumberFormat.formatIndianNumber(totalCredit);

		String fromToDate = fromDate + " TO " + toDate;

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("receiptVoucherCard", receiptVouchercard);
		data.put("fromToDate", fromToDate);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);

		String filename = "receipt-voucher-pdf_" + fromDate + "_" + toDate + ".pdf";

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/receipt-voucher-pdf", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		logger.info("Method :receiptVoucherPdf End");
	}

	@SuppressWarnings("unchecked")
	@GetMapping("receipt-voucher-getTDSVoucherList")
	public @ResponseBody Object getTdsVoucherList(HttpSession session, @RequestParam String id) {

		logger.info("Method :getTdsVoucherList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restClient.getForObject(env.getAccountUrl() + "/getRcvTdsInvoiceList?id=" + id + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :getTdsVoucherList ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("receipt-voucher-get-invoiceListInfo")
	public @ResponseBody Object getPayInvoiceInfo(HttpSession session, @RequestParam String ledgerid,
			@RequestParam String voucherid) {

		logger.info("Method :getPayInvoiceInfo starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/getReceiveInvoiceInfo?ledgerid=" + ledgerid
					+ "&voucherid=" + voucherid + "&orgName=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :getPayInvoiceInfo ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("receipt-voucher-approve-voucher")
	public @ResponseBody Object approveVoucher(HttpSession session, @RequestBody String data) {
		logger.info("Method :approveVoucher starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		String userId = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.postForObject(env.getAccountUrl() + "rest-approveRcptVoucher?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :approveVoucher ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/receipt-voucher-details-pdf" })
	public void receiptVoucherDetailsPDF(HttpServletResponse response, Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method :receiptVoucherDetailsPDF starts");

		AccountCreditorLedgerModel product = new AccountCreditorLedgerModel();

		JsonResponse<?> paymentReport = new JsonResponse<>();
		ObjectMapper mapper = new ObjectMapper();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			paymentReport = restClient.getForObject(env.getAccountUrl() + "receiptVoucherDetailsPdf?id=" + id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :receiptVoucherDetailsPDF --->" + paymentReport);
		logger.info("Raw JSON: " + paymentReport.getBody());
		List<AccountPaymentVoucherDetailsModel> voucherList = new ArrayList<>();

		try {
			// Step 1: Convert the body string to a JsonNode
			JsonNode root = mapper.readTree((String) paymentReport.getBody());

			// Step 2: Extract viewPaymentVoucher node
			JsonNode viewPaymentVoucher = root.path("viewPaymentVoucher");

			// Step 3: Map to Java object list
			voucherList = mapper.readValue(viewPaymentVoucher.toString(),
					new TypeReference<List<AccountPaymentVoucherDetailsModel>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<>();
		data.put("voucher", voucherList.get(0)); // assuming only 1 record for PDF
		logger.info("Method :receiptVoucherDetailsPDF ---->" + voucherList);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + "demo");

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/receiptVoucher_Details_PDF.html", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		logger.info("Method :paymentVoucherDetailsPDF End");

	}
}
