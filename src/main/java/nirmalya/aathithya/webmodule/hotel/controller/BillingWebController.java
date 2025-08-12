package nirmalya.aathithya.webmodule.hotel.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "hotel")
public class BillingWebController {
	Logger logger = LoggerFactory.getLogger(BillingWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/billing")
	public String viewBilling(Model model, HttpSession session) {
		logger.info("Method: viewBilling starts here");
		
		
		try {
		String orgName = (String) session.getAttribute("ORGANIZATION");
		String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		
		DropDownModel[] bankAccountList = restTemplate.getForObject(env.getAccountUrl() + "/getBankAccountPaymentList?orgName=" + orgName + "&orgDivision="+ orgDivision,
				DropDownModel[].class);

		List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
		model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		
		DropDownModel[] costCenter = restTemplate.getForObject(env.getAccountUrl() + "/getCostCenterList",
				DropDownModel[].class);

		List<DropDownModel> ccList = Arrays.asList(costCenter);
		model.addAttribute("ccList", ccList);
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
        logger.info("Method: viewBilling ends here");

        return "hotel/billing";
    }
    
    @SuppressWarnings({ "unchecked" })
	@GetMapping("get-all-cust-details")
	public @ResponseBody Object getAllCustDetails(HttpSession session, @RequestParam String id) {
		logger.info("Method : getAllCustDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String organization = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-get-all-cust-details?&userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getAllCustDetails ends");
		return resp;

	}
    
    @SuppressWarnings("unchecked")
	@GetMapping("get-all-hotel-balace-sheet")
	public @ResponseBody Object getAllBalanceSheet(@RequestParam String book_id, HttpSession session) {
		logger.info("Method :getAllBalanceSheet starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-hotel-billing-get-balancesheet?id=" + book_id
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllBalanceSheet ends");
		return resp;
	}
    @GetMapping("/his-billing-pharmacy-get-paymode-list")
	public @ResponseBody Object getPayModeList(HttpSession session) {
		logger.info("Method :getPayModeList starts");

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
			DropDownModel[] item = restTemplate.getForObject(
					env.getHisUrl() + "/getPayModeList?org=" + orgName + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);

			if (itemList.size() > 0) {
				resp.setBody(itemList);
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

		logger.info("Method :getPayModeList ends");
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
			res = restTemplate.postForObject(env.getHisUrl() + "ipdBillingPay?userId="+userId+"&orgName="+orgName+"&orgDivision="+orgDivision, data,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : ipdBillingPay function Ends");
		return res;
	}
    @SuppressWarnings("unchecked")
	@GetMapping("opd-billing-order-list")
	public @ResponseBody Object getOrderList(@RequestParam String id, HttpSession session) {
		logger.info("Method :getOrderList starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-opd-billing-get-orderlist?id=" + id
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
			
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-opd-billing-orderdtls?id=" + id + "&bookId=" + bookId
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getOrderFullDetails ends");
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
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-modifyBillInvoice?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :modifyBillInvoice ends");
		return resp;
	}



}