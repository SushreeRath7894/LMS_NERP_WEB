package nirmalya.aathithya.webmodule.account.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;

@Controller
@RequestMapping(value = "account")

public class AccountProductReportWebController {
	Logger logger = LoggerFactory.getLogger(AccountProductReportWebController.class);
	@Autowired
	RestTemplate restClient;
	
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/product-report")
	public String paymentVoucher(Model model, HttpSession session) {
		
		logger.info("Method : Dropdown purchaseType starts");
		
		String orgName = "";
		String orgDiv = "";
		
		try {
			orgName = (String)session.getAttribute(orgName);
			orgDiv = (String)session.getAttribute(orgDiv);
			}catch(Exception e) {
				logger.info("Something went wrong");
			}

		try {
			DropDownModel[] result = restClient.getForObject(env.getAccountUrl()+ "purchaseTypeList?orgName=" +orgName + "&orgDiv=" + orgDiv ,
					DropDownModel[].class);

			List<DropDownModel> purchaseTypeDropdown = Arrays.asList(result);
			 	model.addAttribute("purchaseType", purchaseTypeDropdown);
			 	
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		logger.info("Method : Dropdown purchaseType ends");
		 
		return "account/product-report";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("product-report-getPurchaseData")
	public @ResponseBody JsonResponse<Object> getPurchaseData(HttpSession session,@RequestParam String fromDate,@RequestParam String toDate,
			@RequestParam String purchaseTypeId ) {
		logger.info("Method : getPurchaseData starts");
		 
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "rest-manage-getPurchaseData?fromDate="+fromDate+"&toDate="+toDate+"&purchaseTypeId="+purchaseTypeId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 
		logger.info("Method : getPurchaseData ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("product-report-getSalesData")
	public @ResponseBody JsonResponse<Object> getSalesData(HttpSession session,@RequestParam String fromDate,String toDate,String salesTypeId) {
		logger.info("Method : getSalesData starts");
		
		String orgName = "";
		String orgDiv = "";
		
		try {
			
			orgName = (String)session.getAttribute("ORGANIZATION");		
			orgDiv = (String)session.getAttribute("ORGANIZATION_DIVISION");
		}
		catch(Exception e) {
			
			logger.info("Something Went Wrong..");
			
		}
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "rest-manage-getSalesData?fromDate="+fromDate+"&toDate="+toDate+"&salesTypeId="+salesTypeId + "&orgName="+orgName+"&orgDiv="+orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 
		logger.info("Method : getSalesData ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("product-report-getFilterData")
	public @ResponseBody JsonResponse<Object> getFiletrData(HttpSession session,@RequestParam String type,String fromDate,
			String todate,String purchaseTypeId,String salesTypeId) {
		logger.info("Method : getFiletrData starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "rest-manage-getFilterData?type="+ type + "&fromDate=" + fromDate + "&todate=" +
		todate + "&purchaseTypeId=" + purchaseTypeId + "&salesTypeId=" + salesTypeId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 
		logger.info("Method : getFiletrData ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-product-category-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getCategoryList(HttpSession session) {
		logger.info("Method : getCategoryList starts");
		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "getProductCategory",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getCategoryList starts");
		return resp;
	}
}


