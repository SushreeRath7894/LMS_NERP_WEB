package nirmalya.aathithya.webmodule.sales.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoiceNewModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;

@Controller
@RequestMapping(value = { "sales/" })
public class CustomerInvoiceNewController {
	Logger logger = LoggerFactory.getLogger(CustomerInvoiceNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	CustomerInvoiceNewController invoiceNewController;

	@GetMapping(value = { "/view-saleInvoicenew" })
	public String TaxDetails(Model model, HttpSession session) {
		logger.info("Method : invoice starts");
		

		logger.info("Method : invoice ends");
		return "sales/view-salesinvoicenew";
	}
	
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-saleInvoicenew-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
		logger.info("Method : getCustomerAutoSearchList starts");
		//logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getCustomerListByAutoSearch?id=" + searchValue
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		// logger.info("RESPONSE@@" + res);
		logger.info("Method : geProjectAutoSearchList ends");
		return res;
	}
	
	
	
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-saleInvoicenew-get-Project-list" })
	public @ResponseBody JsonResponse<SalesInvoiceNewModel> geProjectAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
		logger.info("Method : geProjectAutoSearchList starts");
		logger.info("QuotationNewModel"+searchValue);
		JsonResponse<SalesInvoiceNewModel> res = new JsonResponse<SalesInvoiceNewModel>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "geProjectListByAutoSearch?id=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		 logger.info("RESPONSE@@" + res);
		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}
	
	
	/*
	 * view Item
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-saleInvoicenew-item-through-ajax")
	public @ResponseBody List<SalesInvoiceNewModel> viewsalesInvoiceItem(@RequestParam String prId,@RequestParam String crId,HttpSession session) {

		logger.info("Method :getAllsalesInvoice starts");
		JsonResponse<List<SalesInvoiceNewModel>> resp = new JsonResponse<List<SalesInvoiceNewModel>>();
		
		try {

			resp = restTemplate.getForObject(env.getSalesUrl() + "getAllsalesInvoiceitem?prId=" + prId + "&crId=" + crId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<SalesInvoiceNewModel> quotation = mapper.convertValue(resp.getBody(),
				new TypeReference<List<SalesInvoiceNewModel>>() {
				});
		
		quotation.forEach(s -> s.setSlNo(s.getSlNo()));

		int count = 0;

		for (SalesInvoiceNewModel m : quotation) {
			count++;
			m.setSlNo(count);
		}
		

		resp.setBody(quotation);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewsalesInvoiceItem ends");
		logger.info("RESPONSEview" + resp);
		return resp.getBody();
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-saleInvoicenew-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getInvoiceInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getInvoiceInsertedId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getInvoiceInsertedId ends");
		logger.info("assssssssssssssssssss" + res);
		return res;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-saleInvoicenew-get-podata" })
	public @ResponseBody JsonResponse<Object> getInvoicepodata() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getInvoicepodata", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getInvoicepodata ends");
		logger.info("assssssssssssssssssss" + res);
		return res;
	}
	
	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoicenew-add")
	public @ResponseBody JsonResponse<Object> addsaleInvoicenew(HttpSession session,
			@RequestBody List<SalesInvoiceNewModel> salesInvoiceNewModel) {
		logger.info("Method : addsaleInvoicenew starts");
		logger.info(salesInvoiceNewModel.toString());
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		for (SalesInvoiceNewModel a : salesInvoiceNewModel) {

			if (a.getInvoiceDate() != null && a.getInvoiceDate() != "") {
				a.setInvoiceDate(DateFormatter.inputDateFormat(a.getInvoiceDate(), dateFormat));
			}
			if (a.getDueDate() != null && a.getDueDate() != "") {
				a.setDueDate(DateFormatter.inputDateFormat(a.getDueDate(), dateFormat));
			}
		}
		for (SalesInvoiceNewModel m : salesInvoiceNewModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addsaleInvoicenew", salesInvoiceNewModel,
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
		logger.info("resp+++++++++++++++++++++++" + resp);

		logger.info("Method : addsaleInvoicenew ends");

		return resp;
	}

}
