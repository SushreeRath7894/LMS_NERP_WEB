package nirmalya.aathithya.webmodule.his.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.account.model.AccountJournalVoucherModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.ItemBillModel;

@Controller
@RequestMapping("his")
public class HisOpdBillingController {
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HisOpdBillingController.class);

	@GetMapping(value = { "/opd-billing" })
	public String viewOpd(Model model, HttpSession session) {
		logger.info("Method : billingOpd starts");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// Fetch gender data
			DropDownModel[] gender = restClient.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] bankAccountList = restClient.getForObject(env.getAccountUrl()
					+ "getBankAccountPaymentList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
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

		logger.info("Method : billingOpd ends");
		return "his/opd-billing";
	}

	@GetMapping("/opd-billing-get-category-list")
	public @ResponseBody Object getCategoryList(HttpSession session) {
		logger.info("Method :getCategoryList starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		try {
			DropDownModel[] category = restClient.getForObject(
					env.getHisUrl() + "/categoryList?org=" + orgName + "&orgDiv=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> categoryList = Arrays.asList(category);

			if (categoryList.size() > 0) {
				resp.setBody(categoryList);
				resp.setCode("success");
				resp.setMessage("Data found");
			} else {
				resp.setBody(null);
				resp.setCode("failed");
				resp.setMessage("Data not found");
			}

		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setBody(null);
			resp.setCode("failed");
			resp.setMessage(e.getMessage());
		}

		logger.info("Method :getCategoryList ends");
		return resp;
	}

	@GetMapping("/opd-billing-get-sku-list")
	public @ResponseBody Object getSkuList(HttpSession session, @RequestParam String cat_id) {
		logger.info("Method :getSkuList starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		try {
			ItemBillModel[] sku = restClient.getForObject(env.getHisUrl() + "/getSkuListCatWise?org=" + orgName
					+ "&orgDiv=" + orgDivision + "&cat_id=" + cat_id, ItemBillModel[].class);
			List<ItemBillModel> skuList = Arrays.asList(sku);

			if (skuList.size() > 0) {
				resp.setBody(skuList);
				resp.setCode("success");
				resp.setMessage("Data found");
			} else {
				resp.setBody(null);
				resp.setCode("failed");
				resp.setMessage("Data not found");
			}

		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setBody(null);
			resp.setCode("failed");
			resp.setMessage(e.getMessage());
		}

		logger.info("Method :getSkuList ends");
		return resp;
	}

	// manage-opd-view
	@SuppressWarnings("unchecked")
	@GetMapping("opd-billing-manage-view")
	public @ResponseBody Object viewOpdDetails(HttpSession session, @RequestParam String fromdate,
			@RequestParam String todate) {
		logger.info("Method :viewOpdDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-opd-billing-viewOpdDetails?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewOpdDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("opd-billing-details-edit")
	public @ResponseBody Object editOpd(@RequestParam String Id, HttpSession session) {
		logger.info("Method :editOpd starts" + Id);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-opd-billing-editOpd?Id=" + Id + "&organization="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editOpd ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("opd-billing-get-balance-sheet")
	public @ResponseBody Object getBalanceSheet(@RequestParam String book_id, HttpSession session) {
		logger.info("Method :getBalanceSheet starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-opd-billing-get-balancesheet?id=" + book_id
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getBalanceSheet ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("opd-billing-order-list")
	public @ResponseBody Object getOrderList(@RequestParam String id, HttpSession session) {
		logger.info("Method :getOrderList starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			resp = restClient.getForObject(env.getHisUrl() + "rest-opd-billing-get-orderlist?id=" + id
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :getOrderList ends");
		return resp;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("opd-billing-get-order-details")
	public @ResponseBody Object getOrderFullDetails(@RequestParam String id, @RequestParam String bookId, HttpSession session) {
		logger.info("Method : getOrderFullDetails starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			resp = restClient.getForObject(env.getHisUrl() + "rest-opd-billing-orderdtls?id=" + id + "&bookId=" + bookId
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getOrderFullDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/opd-billing-save")
	public @ResponseBody Object addBillInvoice(HttpSession session, @RequestBody String data) {
		logger.info("Method :addBillInvoice starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-saveBill?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :addBillInvoice ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/opd-billing-modify")
	public @ResponseBody Object modifyBillInvoice(HttpSession session, @RequestBody String data) {
		logger.info("Method :modifyBillInvoice starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
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
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-modifyBillInvoice?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :modifyBillInvoice ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "opd-billing-pay", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<DropDownModel> ipdBillingPay(
			@RequestBody String data, Model model, HttpSession session) {
		logger.info("Method : ipdBillingPay function starts");
		
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
		
		try {
			res = restClient.postForObject(env.getHisUrl() + "ipdBillingPay?userId="+userId+"&orgName="+orgName+"&orgDivision="+orgDivision, data,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : ipdBillingPay function Ends");
		return res;
	}
	
	
}
