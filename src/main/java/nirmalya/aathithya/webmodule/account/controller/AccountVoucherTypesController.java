package nirmalya.aathithya.webmodule.account.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.JsonNode;

import nirmalya.aathithya.webmodule.account.model.ContraVoucherModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "account")
public class AccountVoucherTypesController {
	Logger logger = LoggerFactory.getLogger(AccountVoucherTypesController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("manage-voucher-types")
	public String manageVoucherTypes(Model model, HttpSession session) {
		logger.info("Method : manageVoucherTypes viewBank starts");
		
		
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		
		try {
			DropDownModel[] voucherTypeLis= restClient.getForObject(env.getAccountUrl() + "/rest-voucherTypeList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> listOfTypes = Arrays.asList(voucherTypeLis);
			System.out.println("voucherTypeList" + voucherTypeLis);
			model.addAttribute("voucherTypeList", listOfTypes);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : manageVoucherTypes viewBank end");
		return "account/manage-voucher-types";
	}
	
	//Auto search ledger
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-voucher-types-getLedgerList" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getLedgerListSearch(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getLedgerListSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "/rest-getLedgerListSearch?id=" + searchValue
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("response===>"+res);
		logger.info("Method : getLedgerListSearch ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "manage-voucher-types-add-voucherTypes")
	public @ResponseBody JsonResponse<Object> VoucherTypeAdd(@RequestBody JsonNode obj, HttpSession session,@RequestParam String voucherId) {
	    logger.info("Method : VoucherTypeAdd starts" + obj);
	    JsonResponse<Object> res = new JsonResponse<Object>();
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
	        String url = env.getAccountUrl() + "voucherTypesAdd?orgName=" + orgName + "&orgDivision=" + orgDivision+"&userId="+userId + "&voucherId=" + voucherId;
	        res = restClient.postForObject(url, obj, JsonResponse.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessage("Unsuccess");
	    }

	    if (res.getMessage() == null) {
	        res.setMessage("Success");
	    }

	    logger.info("Response: " + res);
	    logger.info("Method : VoucherTypeAdd ends");
	    return res;
	}

	
		@SuppressWarnings("unchecked")
		@GetMapping("manage-voucher-types-viewVoucherTypes")
		public @ResponseBody Object viewVoucherTypes(HttpSession session) {

			logger.info("Method :viewVoucherTypes starts");
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
				resp = restClient.getForObject(env.getAccountUrl() + "/restViewVoucherTypes?orgName=" + orgName + "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Response===>"+resp);
			logger.info("Method :viewVoucherTypes ends");
			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("manage-voucher-types-edit")
		public @ResponseBody Object editVoucherTypeFilteredData(HttpSession session,@RequestParam String id) {

			logger.info("Method :viewSalesFilteredData starts");
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
				resp = restClient.getForObject(env.getAccountUrl() + "rest-editVoucherType?orgName="+orgName +"&orgDivision=" + orgDivision +
						"&id=" + id, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewSalesFilteredData ends"+resp);

			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("manage-voucher-types-delete-id")
		public @ResponseBody Object deleteVoucherTypeId(HttpSession session,@RequestParam String id) {

			logger.info("Method :DeleteVoucherTypeId starts");
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
				resp = restClient.getForObject(env.getAccountUrl() + "/restDeleteVoucherId?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Response===>"+resp);
			logger.info("Method :DeleteVoucherTypeId ends");
			return resp;
		}
}
