package nirmalya.aathithya.webmodule.sales.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.common.utils.NumberToWordsConverter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoiceNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoicePaymentModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;

@Controller
@RequestMapping(value = { "sales/" })

public class SalesInvoiceNewController {

	Logger logger = LoggerFactory.getLogger(SalesInvoiceNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@Autowired
	SalesInvoiceNewController salesInvoiceNewController;

	@GetMapping(value = { "/view-saleInvoice" })
	public String salesInvoiceDetails(Model model, HttpSession session) {
		logger.info("Method : salesInvoiceDetails starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getSalesUrl() + "getCollectionList",
					DropDownModel[].class);
			List<DropDownModel> collectionList = Arrays.asList(Collection);

			model.addAttribute("collectionList", collectionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Gender = restTemplate.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);
			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] payMode = restTemplate.getForObject(env.getSalesUrl() + "GetpaymentModeList",
					DropDownModel[].class);
			List<DropDownModel> paymentModeList = Arrays.asList(payMode);
			logger.info("paymentModeList@" + paymentModeList);
			model.addAttribute("paymentModeList", paymentModeList);

		} catch (Exception e) {
			e.printStackTrace();

		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-Payment-term",
					DropDownModel[].class);
			List<DropDownModel> paytermList = Arrays.asList(dropDownModel);
			model.addAttribute("paytermList", paytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] brand = restTemplate.getForObject(
					env.getMasterUrl() + "getBrandListForProduct?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);
			model.addAttribute("brandList", brandList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * try { DropDownModel[] mode = restTemplate.getForObject(env.getMasterUrl() +
		 * "getModeListForProduct", DropDownModel[].class); List<DropDownModel> modeList
		 * = Arrays.asList(mode);
		 * 
		 * model.addAttribute("modeList", modeList); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		try {
			String Type = "Sales";
			DropDownModel[] mode = restTemplate.getForObject(
					env.getMasterUrl() + "getModeListForProduct?type=" + Type + "&org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] tmode = restTemplate.getForObject(env.getSalesUrl() + "getTransportationModeList",
					DropDownModel[].class);
			List<DropDownModel> transportationModeList = Arrays.asList(tmode);
			model.addAttribute("transportationModeList", transportationModeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] years = restTemplate.getForObject(env.getSalesUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(years);
			logger.info("finicialList-->" + fiscalList);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : salesInvoiceDetails ends");
		return "sales/view-sale-invoice";
	}

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-get-product-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryList(@RequestBody String yearDtls,
			HttpSession session) {
		logger.info("Method : getProductCategoryListModal starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();

		try {
			resp = restTemplate.getForObject(env.getInventoryUrl() + "getProductCategoryDataListModal",
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method : getProductCategoryListModal starts");
		return resp;
	}

	/*
	 * Item auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-item-get-customer-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchListForItem(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
		logger.info("QuotationNewModel" + searchValue);
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getItemQuotationAutoSearchListForItem?id="
					+ searchValue + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
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
		logger.info("Method : getItemQuotationAutoSearchListForItem ends");
		return res;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-add-data")
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
			if (a.getDateOfSupply() != null && a.getDateOfSupply() != "") {
				a.setDateOfSupply(DateFormatter.inputDateFormat(a.getDateOfSupply(), dateFormat));
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

	/*
	 * view Invoice Search
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-search")
	public @ResponseBody List<SalesInvoicePaymentModel> viewsalesInvoiceSearch(HttpSession session,
			@RequestParam String searchValue) {
		logger.info("Method :viewsalesInvoiceSearch starts");

		JsonResponse<List<SalesInvoicePaymentModel>> resp = new JsonResponse<List<SalesInvoicePaymentModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate
					.getForObject(
							env.getSalesUrl() + "view-saleInvoice-search?userId=" + userId + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&searchValue=" + searchValue,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewsalesInvoiceSearch ends");
		return resp.getBody();
	}

	/*
	 * payment Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-payment-add")
	public @ResponseBody JsonResponse<Object> addinvPaymentnew(Model model, HttpSession session,

			@RequestBody SalesInvoiceNewModel salesInvoiceNewModel) {
		logger.info("Method : addinvPaymentnew starts");
		logger.info(salesInvoiceNewModel.toString());
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {
			e.printStackTrace();
		}

		salesInvoiceNewModel.setQutCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addinvPaymentnew", salesInvoiceNewModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<SalesInvoiceNewModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalesInvoiceNewModel>>() {
					});

			if (salesInvoiceNewModel.getPayDate() != null && salesInvoiceNewModel.getPayDate() != "") {
				salesInvoiceNewModel
						.setPayDate(DateFormatter.inputDateFormat(salesInvoiceNewModel.getPayDate(), dateFormat));

			}

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Sradha" + resp);

		logger.info("Method : addinvPaymentnew ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-get-salesperson-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getSalesPersonAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getSalesPersonAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesPersonListByAutoSearch?id=" + searchValue,
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
		// logger.info("RESPONSE@@" + res);
		logger.info("Method : getSalesPersonAutoSearchList ends" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-add-salesperson")
	public @ResponseBody JsonResponse<Object> addSalesPerson(@RequestBody QuotationNewModel quotationNewModel,
			HttpSession session) {
		logger.info("Method : addSalesPerson starts");
		String userId = "";
		String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {
			e.printStackTrace();
		}
		quotationNewModel.setCreatedBy(userId);

		logger.info("ADDDDATA" + quotationNewModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		quotationNewModel.setDobid(DateFormatter.inputDateFormat(quotationNewModel.getDobid(), dateFormat));

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "add-salesperson", quotationNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("ADDDDDDD" + resp);
		logger.info("Method :addSalesPerson ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-add-cust-billingaddress")
	public @ResponseBody JsonResponse<Object> addbillingaddress(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : addbillingaddress starts");
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		customerNewModel.setCreatedBy(userId);

		logger.info("ADDDDATA" + customerNewModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "add-billingaddress", customerNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("ADDDDDDD" + resp);
		logger.info("Method :addbillingaddress ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleInvoice-add-cust-shippingaddress")
	public @ResponseBody JsonResponse<Object> saveAddressDetails(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : saveAddressDetails starts");

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

		customerNewModel.setCreatedBy(userId);
		customerNewModel.setOrganization(orgName);
		customerNewModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "saveAddressDetails", customerNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAddressDetails starts");
		return resp;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-saleInvoice-stateList" })
	public @ResponseBody JsonResponse<Object> getstateCusList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getStateLists1?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getstateCusList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-get-address")
	public @ResponseBody JsonResponse<CustomerNewModel> getCustomerAddress(@RequestParam String id,
			@RequestParam String shipId, HttpSession session) {
		logger.info(id);
		logger.info("Method : getCustomerAddress starts");

		JsonResponse<CustomerNewModel> jsonResponse = new JsonResponse<CustomerNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id + "&shipId="
					+ shipId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<CustomerNewModel>() {
		});

		jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : getCustomerAddress ends");
		logger.info("getCustomerAddress--->>>>" + jsonResponse);
		return jsonResponse;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-saleInvoice-salesorderlist" })
	public @ResponseBody JsonResponse<Object> getSalesorderList(@RequestParam String id, String type) {
		logger.info("Method : getSalesorderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesorderList?id=" + id + "&type=" + type,
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
		logger.info("state" + res);
		logger.info("Method : getSalesorderList ends");
		return res;
	}

	// view-saleInvoice-getdataOnSO

	@GetMapping(value = { "view-saleInvoice-getdataOnSO" })
	public @ResponseBody List<SalesOrderNewModel> viewsalesOrderDataOnclickSo(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewsalesOrderDataOnclickSo starts");
		logger.info(id);
		List<SalesOrderNewModel> productList = new ArrayList<SalesOrderNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();

		if (id != null && id != "") {
			try {
				SalesOrderNewModel[] salesOrderNewModel = restTemplate
						.getForObject(env.getSalesUrl() + "viewsalesOrdeerEdit?id=" + id, SalesOrderNewModel[].class);

				productList = Arrays.asList(salesOrderNewModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (SalesOrderNewModel m : salesOrderNewModel) {

					count++;
					m.setSlNo(count);

					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					/*
					 * if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
					 * 
					 * }
					 */
					if (m.getOrderReceiveDate() != null && m.getOrderReceiveDate() != "") {
						m.setOrderReceiveDate(DateFormatter.dateFormat(m.getOrderReceiveDate(), dateFormat));

					}
					if (m.getExpectedShipmentDate() != null && m.getExpectedShipmentDate() != "") {
						m.setExpectedShipmentDate(DateFormatter.dateFormat(m.getExpectedShipmentDate(), dateFormat));

					}

				}

				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
												+ m.getFileName() + "></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("csv")) {
										String docPath = "<i class=\"fa-solid fa-file-csv custom-file-icon\" title=" + m.getFileName() + "></i>";
										
										m.setAction(docPath);
									}
									if (extension[1].equals("pdf")) {
										String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
												+ m.getFileName() + " ;></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("doc") || extension[1].equals("dox")
											|| extension[1].equals("docx")) {
										String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
												+ m.getFileName() + "></i> ";
										m.setAction(docPath);
									}
									if (extension[1].equals("png") || extension[1].equals("jpg")
											|| extension[1].equals("jpeg")) {
										String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
												+ m.getFileName() + "></i>  ";
										m.setAction(docPath);
									}
								} else {
									m.setAction("N/A");
								}
							} else {
								m.setAction("N/A");
							}
							m.setAction("<a href=\"/document/document/" + m.getFileName() + "\" target=\"_blank\" >"
									+ m.getAction() + "</a>");
							logger.info("m.setAction" + m);

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewsalesOrderDataOnclickSo ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("view-saleInvoice-pdf-downloads") public void
	 * getInvoicePdfDetails(HttpServletResponse response, Model model, HttpSession
	 * session,
	 * 
	 * @RequestParam("saleInvoice") String
	 * encodedParam1, @RequestParam("organization") String encodedParam3,
	 * 
	 * @RequestParam("orgDivision") String encodedParam4) {
	 * 
	 * logger.info("Method : getInvoicePdfDetails starts");
	 * 
	 * byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
	 * String invIdd = (new String(encodeByte1));
	 * 
	 * 
	 * byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
	 * String copytype = (new String(encodeByte2));
	 * 
	 * 
	 * byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
	 * String organization = (new String(encodeByte3));
	 * 
	 * byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam4.getBytes());
	 * String orgDivision = (new String(encodeByte4));
	 * 
	 * logger.info("invIdd====" + invIdd);
	 * 
	 * List<SalesInvoiceNewModel> productList = new
	 * ArrayList<SalesInvoiceNewModel>(); try { SalesInvoiceNewModel[]
	 * salesInvoiceNewModel = restTemplate .getForObject(env.getSalesUrl() +
	 * "viewsales-invoice-viewPdf?id=" + invIdd + "&organization=" + organization +
	 * "&orgDivision=" + orgDivision, SalesInvoiceNewModel[].class); productList =
	 * Arrays.asList(salesInvoiceNewModel); productList.forEach(s ->
	 * s.setSlNo(s.getSlNo())); int count = 0; for (SalesInvoiceNewModel m :
	 * salesInvoiceNewModel) { count++; m.setSlNo(count); } } catch (Exception e) {
	 * e.printStackTrace(); } String id = productList.get(0).getCustId();
	 * logger.info("id====" + id); JsonResponse<CustomerNewModel> jsonResponse = new
	 * JsonResponse<CustomerNewModel>(); try { jsonResponse =
	 * restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" +
	 * id, JsonResponse.class); } catch (RestClientException e) {
	 * e.printStackTrace(); } ObjectMapper mapper = new ObjectMapper();
	 * CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new
	 * TypeReference<CustomerNewModel>() { }); logger.info("JsonResponse====" +
	 * jsonResponse); logger.info("reimModel====" + reimModel);
	 * logger.info("productList====" + productList); Map<String, Object> data = new
	 * HashMap<String, Object>(); data.put("invoice", productList);
	 * 
	 * // String logo = ""; String logo =
	 * "classpath:static/assets/images/invoice-banner.jpg"; data.put("logo", logo);
	 * // data.put("copytype", copytype); data.put("buyer", reimModel);
	 * 
	 * response.setContentType("application/pdf");
	 * response.setHeader("Content-disposition",
	 * "inline; filename=salesInvoice.pdf"); File file; byte[] fileData = null; try
	 * { file = pdfGeneratorUtil.createPdf("sales/sales-invoice-pdf", data);
	 * InputStream in = new FileInputStream(file); fileData =
	 * IOUtils.toByteArray(in); response.setContentLength(fileData.length);
	 * response.getOutputStream().write(fileData);
	 * response.getOutputStream().flush();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } catch (Exception e1) {
	 * e1.printStackTrace(); } // logger.info("REsp" + jsonResponse);
	 * logger.info("Method : getInvoicePdfDetails ends"); }
	 */

	@GetMapping("view-saleInvoice-shipping-address")
	public @ResponseBody Object viewShippingAddressData(@RequestParam String customerId, HttpSession session) {

		logger.info("Method :viewShippingAddressData starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewShippingAddressData?customerId=" + customerId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@GetMapping("view-saleInvoice-shipping-dataedit")
	public @ResponseBody Object editShippingAddressData(@RequestParam String addressId, HttpSession session) {

		logger.info("Method :editShippingAddressData starts");

		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-editShippingAddressData?addressId=" + addressId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCustomerAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
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
		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-item-get-product-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryListModal(
			@RequestBody String yearDtls, HttpSession session) {
		logger.info("Method : getProductCategoryListModal starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		// logger.info(yearDtls);
		try {
			resp = restTemplate.getForObject(env.getInventoryUrl() + "getProductCategoryDataListModal",
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		// logger.info("resp@@@@@@@"+ resp);
		logger.info("Method : getProductCategoryListModal starts");
		return resp;
	}
	// grt product by cat

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCat(Model model, @RequestBody String index,
			BindingResult result, HttpSession session) {
		logger.info("Method : getProductsByCat starts");

		String indexValue = index.substring(0, index.length() - 1);

		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getProductsNByCat?id=" + indexValue + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		// logger.info(res);
		logger.info("Method : getProductsByCat ends");
		return res;
	}

	// Add New Customer
	@SuppressWarnings("unchecked")
	@PostMapping("/view-saleInvoice-adds-customer")
	public @ResponseBody JsonResponse<Object> addCustomer(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {

		logger.info("Method : addAccountCustomer starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		customerNewModel.setOrganization(organization);
		customerNewModel.setOrgDivision(orgDivision);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web AccountModel ======================" + customerNewModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("created by id-------------------------------" + userId);

			customerNewModel.setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getSalesUrl() + "/addAccountCustomer", customerNewModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addAccountCustomer ends");

		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleInvoice-save-shipping-address")
	public @ResponseBody JsonResponse<Object> saveShippingAddressDetails(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : saveAddressDetails starts");

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

		customerNewModel.setCreatedBy(userId);
		customerNewModel.setOrganization(orgName);
		customerNewModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "saveAddressDetails", customerNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAddressDetails starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-shippingdetails")
	public @ResponseBody Object viewShippingDetails(HttpSession session, @RequestParam String customerIdd) {
		logger.info("Method :viewShippingDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+customerId);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewShippingDetails?customerIdd=" + customerIdd
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewShippingDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-edit-address")
	public @ResponseBody Object editShippingDetails(HttpSession session, @RequestParam String addressId) {
		logger.info("Method :editShippingDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-editShippingDetails?addressId=" + addressId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editShippingDetails ends");
		return resp;
	}

	@GetMapping("view-saleInvoice-address-delete")
	public @ResponseBody Object deleteaddressdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteaddressdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-deleteaddressdata?deleteId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-saleInvoice-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryDataListModal(HttpSession session) {
		logger.info("Method : getProductCategoryDataListModal starts");
		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		try {
			resp = restTemplate.getForObject(env.getInventoryUrl() + "getProductCategoryDataListModal",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getProductCategoryDataListModal starts");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleInvoice-products-save")
	public @ResponseBody JsonResponse<Object> saveProductMaster(@RequestBody ProductMasterModel product,
			HttpSession session) {
		logger.info("Method : saveProductMaster starts");

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

		product.setCreatedBy(userId);
		product.setOrganizationName(orgName);
		product.setOrganizationDivision(orgDivision);
		MultipartFile inputFile = (MultipartFile) session.getAttribute("productPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				// logger.info(imageName);

				product.setpImgName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductMaster", product, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveProductMaster starts");
		return resp;
	}

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
			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}

	@PostMapping("/view-saleInvoice-upload-fileprofuct")
	public @ResponseBody JsonResponse<Object> uploadProductFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadProductFile controller  starts");
		JsonResponse<Object> response = new JsonResponse<Object>();
		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("productPFile", inputFile);
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : uploadProductFile controller ' ends");
		return response;
	}

	@PostMapping("view-saleInvoice-delete-fileproduct")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile employee uploadimage controller starts");
		JsonResponse<Object> response = new JsonResponse<Object>();
		try {
			session.removeAttribute("productPFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteFile employee uploadimage controller ends");
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleInvoice-save-sku-dtls")
	public @ResponseBody JsonResponse<Object> saveProductDetails(@RequestBody ProductDetailsModel product,
			HttpSession session) {
		logger.info("Method : saveProductDetails starts");

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

		product.setCreatedBy(userId);
		product.setOrganizationName(orgName);
		product.setOrganizationDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductDetails", product, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveProductDetails starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-saleInvoice-get-sku-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUListingById(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getSKUListingById starts");

		JsonResponse<List<ProductDetailsModel>> res = new JsonResponse<List<ProductDetailsModel>>();

		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getSKUListingById?id=" + id + "&orgName=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ProductDetailsModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductDetailsModel>>() {
					});

			for (ProductDetailsModel m : product) {
				m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
				m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
			}
			res.setBody(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getSKUListingById ends");
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-get-sku-details" })
	public @ResponseBody JsonResponse<ProductDetailsModel> getSKUDetails(Model model,
			@RequestBody DropDownModel tCountry, BindingResult result) {
		logger.info("Method : getSKUDetails starts");

		JsonResponse<ProductDetailsModel> res = new JsonResponse<ProductDetailsModel>();

		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "getSKUDetailsById?id=" + tCountry.getKey() + "&skuid=" + tCountry.getName(),
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

		logger.info("Method : getSKUDetails ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-deletesku")
	public @ResponseBody JsonResponse<Object> deletesku(Model model, HttpSession session, @RequestParam String id) {
		logger.info("Method : deletesku starts");
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
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "deletesku?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {

		}
		logger.info("Method : deletesku ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-get-tcs-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getTCSAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getTCSAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getTCSAutoSearchList?id=" + searchValue + "&org="
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
		// logger.info("RESPONSE@@" + res);
		logger.info("Method : getTCSAutoSearchList ends" + res);
		return res;
	}

	// TCS Add
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-add-tcs")
	public @ResponseBody JsonResponse<Object> addTcs(@RequestBody QuotationNewModel quotationNewModel,
			HttpSession session) {
		logger.info("Method : addTcs starts");
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		quotationNewModel.setCreatedBy(userId);
		quotationNewModel.setOrganization(organization);
		quotationNewModel.setOrgDivision(orgDivision);
		logger.info("ADDDDATA" + quotationNewModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "add-tcs", quotationNewModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("ADDDDDDD" + resp);
		logger.info("Method :addTcs ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-saleInvoice-get-sku-listing" })
	public @ResponseBody List<ProductMasterModel> getProductSKUListing(Model model, HttpServletRequest request,
			@RequestParam String type, HttpSession session) {
		logger.info("Method : getProductSKUListing starts");

		JsonResponse<List<ProductMasterModel>> res = new JsonResponse<List<ProductMasterModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getProductSKUListing?type=" + type + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ProductMasterModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductMasterModel>>() {
					});
			for (ProductMasterModel m : product) {

				if (m.getProductStatus().contentEquals("1")) {
					m.setProductStatus("Active");
				} else {
					m.setProductStatus("Inactive");
				}
				m.setpPrice(NumberFormatter.doubleToStringWithComma(m.getPurchasePrice()));
				m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
			}

			res.setBody(product);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getProductSKUListing ends" + res.getBody());
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-get-product-details" })
	public @ResponseBody JsonResponse<ProductMasterModel> getProductDetails(Model model, @RequestBody String tCountry,
			BindingResult result, HttpSession session) {
		logger.info("Method : getProductDetails starts");

		JsonResponse<ProductMasterModel> res = new JsonResponse<ProductMasterModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getProductDetailsById?id=" + tCountry + "&orgName="
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

		logger.info("Method : getProductDetails ends" + res);
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-products-delete")
	public @ResponseBody JsonResponse<Object> deleteProductMaster(Model model, @RequestParam String id,
			@RequestParam String simpleid, HttpSession session) {
		logger.info("Method : deleteProductMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String createdBy = "";
		String orgName = "";
		String orgDivision = "";

		try {
			createdBy = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			resp = restTemplate
					.getForObject(
							env.getMasterUrl() + "deleteProductMaster?id=" + id + "&createdBy=" + createdBy
									+ "&simpleid=" + simpleid + "&org=" + orgName + "&orgDiv=" + orgDivision,
							JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method : deleteProductMaster ends");
		return resp;
	}

	@GetMapping("view-saleInvoice-reject")
	public @ResponseBody Object rejectInvoice(@RequestParam String id, @RequestParam String comment,
			HttpSession session) {

		logger.info("Method :rejectInvoice starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-rejectInvoice?invId=" + id + "&comment="
					+ comment + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Save Upload Invoice Data

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleInvoice-save-excelData")
	public @ResponseBody JsonResponse<Object> addInvoiceUploadData(@RequestBody List<SalesInvoiceNewModel> invoiceModal,
			Model model, HttpSession session) {
		logger.info("Method :addInvoiceUploadData starts");

		String dateFormat = "";
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		List<SalesInvoiceNewModel> invoiceList = new ArrayList<>();
		Workbook workbook = (Workbook) session.getAttribute("salesInvoice");
		Sheet worksheet = workbook.getSheetAt(0);
		if (orgDivision.contentEquals("Oriclean Private limited")) {
			for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
				if (index > 0) {
					SalesInvoiceNewModel invoice = new SalesInvoiceNewModel();

					Row row = worksheet.getRow(index);
					DataFormatter formatter = new DataFormatter();
					if (row.getCell(0) != null && !row.getCell(0).toString().trim().isEmpty()) {

						invoice.setCommercialInvNo(formatter.formatCellValue(row.getCell(0)));
						invoice.setGstNo(formatter.formatCellValue(row.getCell(1)));
						invoice.setPoOrSo(formatter.formatCellValue(row.getCell(2)));

						if (row.getCell(3).toString() != null && row.getCell(3).toString() != "") {
							String dateString = row.getCell(3).toString();
							DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
							DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

							LocalDate date = LocalDate.parse(dateString, inputFormatter);
							String formattedDate = date.format(outputFormatter);
							invoice.setBillingDate(formattedDate);
						}
						invoice.setSendingPlant(formatter.formatCellValue(row.getCell(4)));
						invoice.setReceivingPlant(formatter.formatCellValue(row.getCell(5)));
						invoice.setIndicator(formatter.formatCellValue(row.getCell(6)));
						invoice.setSourceState(formatter.formatCellValue(row.getCell(7)));
						invoice.setStateCode(formatter.formatCellValue(row.getCell(8)));
						invoice.setDestinationState(formatter.formatCellValue(row.getCell(9)));
						invoice.setMaterialCode(formatter.formatCellValue(row.getCell(10)));
						invoice.setMaterialDescription(formatter.formatCellValue(row.getCell(11)));
						invoice.setHsn(formatter.formatCellValue(row.getCell(12)));
						invoice.setPriceLot(formatter.formatCellValue(row.getCell(13)));
						invoice.setDeliveryDoc(formatter.formatCellValue(row.getCell(14)));

						String qty = row.getCell(15).toString();
						Double dQty = Double.valueOf(qty);// first way
						invoice.setQuantity(dQty);

						invoice.setUom(formatter.formatCellValue(row.getCell(16)));
						invoice.setCaseConfig(formatter.formatCellValue(row.getCell(17)));

						String qtny = row.getCell(18).toString();
						Double dQtny = Double.valueOf(qtny);// first way
						invoice.setQuantityInCase(dQtny);
						String wet = row.getCell(19).toString();
						Double dWet = Double.valueOf(wet);// first way
						invoice.setNetWeight(dWet);

						invoice.setWeigtUnit(formatter.formatCellValue(row.getCell(20)));

						String price = row.getCell(21).toString();
						Double dprice = Double.valueOf(price);// first way
						invoice.setBasicPrice(dprice);

						String nval = row.getCell(22).toString();
						Double dVal = Double.valueOf(nval);// first way
						invoice.setNetValue(dVal);

						String cval = row.getCell(23).toString();
						Double dCval = Double.valueOf(cval);// first way
						invoice.setCgstValue(dCval);

						String sval = row.getCell(24).toString();
						Double dSval = Double.valueOf(sval);// first way
						invoice.setSgstValue(dSval);
						String ival = row.getCell(25).toString();
						Double dIval = Double.valueOf(ival);// first way
						invoice.setIgstValue(dIval);
						String tval = row.getCell(26).toString();
						Double dTval = Double.valueOf(tval);// first way
						invoice.setTotalValue(dTval);

						invoice.setQutCreatedBy(userId);
						invoice.setOrganization(orgName);
						invoice.setOrgDivision(orgDivision);
						invoiceList.add(invoice);
					}
				}
			}

		} else if (orgDivision.contentEquals("Orifood And Beverage Private Limited")) {
			for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
				if (index > 0) {
					SalesInvoiceNewModel invoice = new SalesInvoiceNewModel();
					Row row = worksheet.getRow(index);
					DataFormatter formatter = new DataFormatter();
					if (row.getCell(0) != null && !row.getCell(0).toString().trim().isEmpty()) {

						invoice.setCommercialInvNo(formatter.formatCellValue(row.getCell(0)));
						invoice.setGstNo(formatter.formatCellValue(row.getCell(1)));
						invoice.setPoOrSo(formatter.formatCellValue(row.getCell(2)));
						invoice.setTaxInvoiceOF(formatter.formatCellValue(row.getCell(3)));
						if (row.getCell(4).toString() != null && row.getCell(4).toString() != "") {
							String dateString = row.getCell(4).toString();
							DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
							DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

							LocalDate date = LocalDate.parse(dateString, inputFormatter);
							String formattedDate = date.format(outputFormatter);
							invoice.setBillingDate(formattedDate);
						}
						invoice.setSendingPlant(formatter.formatCellValue(row.getCell(5)));
						invoice.setReceivingPlant(formatter.formatCellValue(row.getCell(6)));
						invoice.setIndicator(formatter.formatCellValue(row.getCell(7)));
						invoice.setSourceState(formatter.formatCellValue(row.getCell(8)));
						invoice.setStateCode(formatter.formatCellValue(row.getCell(9)));
						invoice.setDestinationState(formatter.formatCellValue(row.getCell(10)));
						invoice.setMaterialCode(formatter.formatCellValue(row.getCell(11)));
						invoice.setMaterialDescription(formatter.formatCellValue(row.getCell(12)));
						invoice.setHsn(formatter.formatCellValue(row.getCell(13)));
						invoice.setPriceLot(formatter.formatCellValue(row.getCell(14)));
						invoice.setDeliveryDoc(formatter.formatCellValue(row.getCell(15)));

						String qty = row.getCell(16).toString();
						Double dQty = Double.valueOf(qty);// first way
						invoice.setQuantity(dQty);

						invoice.setUom(formatter.formatCellValue(row.getCell(17)));
						invoice.setCaseConfig(formatter.formatCellValue(row.getCell(18)));

						String qtny = row.getCell(19).toString();
						Double dQtny = Double.valueOf(qtny);// first way
						invoice.setQuantityInCase(dQtny);
						String wet = row.getCell(20).toString();
						Double dWet = Double.valueOf(wet);// first way
						invoice.setNetWeight(dWet);

						invoice.setWeigtUnit(formatter.formatCellValue(row.getCell(21)));

						String price = row.getCell(22).toString();
						Double dprice = Double.valueOf(price);// first way
						invoice.setBasicPrice(dprice);

						String nval = row.getCell(23).toString();
						Double dVal = Double.valueOf(nval);// first way
						invoice.setNetValue(dVal);

						String cval = row.getCell(24).toString();
						Double dCval = Double.valueOf(cval);// first way
						invoice.setCgstValue(dCval);

						String sval = row.getCell(25).toString();
						Double dSval = Double.valueOf(sval);// first way
						invoice.setSgstValue(dSval);
						String ival = row.getCell(26).toString();
						Double dIval = Double.valueOf(ival);// first way
						invoice.setIgstValue(dIval);
						String tval = row.getCell(27).toString();
						Double dTval = Double.valueOf(tval);// first way
						invoice.setTotalValue(dTval);

						invoice.setQutCreatedBy(userId);
						invoice.setOrganization(orgName);
						invoice.setOrgDivision(orgDivision);
						invoiceList.add(invoice);
					}
				}
			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("invoiceList add 11=====" + invoiceList);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "rest-addInvoiceUploadData", invoiceList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addInvoiceUploadData ends");
		System.out.println(resp);
		return resp;
	}

	// Upload invoice Data

	@PostMapping("view-saleInvoice-upload-file")
	public @ResponseBody JsonResponse<Object> uploadInvoiceData(
			@RequestParam("file") MultipartFile salesInvoiceNewModel, HttpSession session) {
		logger.info("Method : uploadInvoiceData controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		// Log file details
		String fileName = salesInvoiceNewModel.getOriginalFilename();
		String contentType = salesInvoiceNewModel.getContentType();
		long fileSize = salesInvoiceNewModel.getSize();

		System.out.println("File name: " + fileName);
		System.out.println("File content type: " + contentType);
		System.out.println("File size: " + fileSize + " bytes");

		// System.out.println("File size: " + salesInvoiceNewModel.getInputStream());
		// Log the first few bytes of the file
		try (InputStream inputStream = salesInvoiceNewModel.getInputStream()) {
			byte[] firstBytes = new byte[8];
			inputStream.read(firstBytes);
			System.out.println("First few bytes: " + Arrays.toString(firstBytes));
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (InputStream inputStream = salesInvoiceNewModel.getInputStream();
				Workbook workbook = WorkbookFactory.create(inputStream)) {

			response.setMessage(salesInvoiceNewModel.getOriginalFilename());
			session.setAttribute("salesInvoice", workbook);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * try { XSSFWorkbook workbook = new
		 * XSSFWorkbook(salesInvoiceNewModel.getInputStream());
		 * response.setMessage(salesInvoiceNewModel.getOriginalFilename());
		 * System.out.println(salesInvoiceNewModel);
		 * session.setAttribute("salesInvoice", workbook);
		 * 
		 * } catch (RestClientException e) { e.printStackTrace(); } catch (Exception e)
		 * { e.printStackTrace(); }
		 */
		System.out.println("response########" + response);
		logger.info("Method : uploadInvoiceData controller ' ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-payment-received")
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

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "addPaymentInvoice", salesInvoicePaymentModel,
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

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-payment-received-approval")
	public @ResponseBody JsonResponse<SalesInvoicePaymentModel> paymentApproval(HttpSession session,
			@RequestParam String invoiceId, String userId) {
		logger.info("Method : paymentApproval starts");
		JsonResponse<SalesInvoicePaymentModel> response = new JsonResponse<SalesInvoicePaymentModel>();

		try {
			byte[] decodedBytes = Base64.getDecoder().decode(invoiceId);
			String decodedInvoiceId = new String(decodedBytes, StandardCharsets.UTF_8);
			logger.info("invoiceId==" + decodedInvoiceId);
			response = restTemplate.getForObject(
					env.getSalesUrl() + "paymentApproval?invoiceId=" + decodedInvoiceId + "&userId=" + userId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}

		System.out.println("response=====" + response);
		logger.info("Method : paymentApproval ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-payment-received-reject")
	public @ResponseBody JsonResponse<SalesInvoicePaymentModel> paymentReject(HttpSession session,
			@RequestParam String invoiceId, String userId) {
		logger.info("Method : paymentReject starts");
		JsonResponse<SalesInvoicePaymentModel> response = new JsonResponse<SalesInvoicePaymentModel>();

		try {
			byte[] decodedBytes = Base64.getDecoder().decode(invoiceId);
			String decodedInvoiceId = new String(decodedBytes, StandardCharsets.UTF_8);
			logger.info("invoiceId==" + decodedInvoiceId);
			response = restTemplate.getForObject(
					env.getSalesUrl() + "paymentReject?invoiceId=" + decodedInvoiceId + "&userId=" + userId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}

		System.out.println("response=====" + response);
		logger.info("Method : paymentReject ends");
		return response;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("view-saleInvoice-approve")
	public @ResponseBody Object approveInvoice(@RequestParam String id,String comment,
			HttpSession session) {

		logger.info("Method :approveInvoice starts");
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String decodedId = "";
		String decodedComment = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			decodedId = URLDecoder.decode(id, "UTF-8");
			decodedComment = URLDecoder.decode(comment, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getSalesUrl() + "rest-approveInvoice?invId=" + decodedId + "&comment=" + decodedComment
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-filterData")
	public @ResponseBody Object viewSalesFilteredData(HttpSession session, @RequestParam String fromDate,
			String toDate) {

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "saleInvoiceFilterdata?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewSalesFilteredData ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-get-po")
	public @ResponseBody JsonResponse<Object> getPoOnCustomerChange(Model model, HttpSession session,
			@RequestParam String custId) {
		logger.info("Method : getPoOnCustomerChange starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-po-challan-saleInvoice?custId=" + custId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getPoOnCustomerChange ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-get-challan-by-po")
	public @ResponseBody JsonResponse<Object> getChallanOByPoList(Model model, HttpSession session,
			@RequestParam String poId) {
		logger.info("Method : getPoOnCustomerChange starts==" + poId);
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-challan-id-by-po?poId=" + poId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getChallanOByPoList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-items-by-challan")
	public @ResponseBody JsonResponse<Object> getItemDetailsOnChallan(Model model, HttpSession session,
			@RequestParam String selectedValuesStr) {
		logger.info("Method : getItemDetailsOnChallan starts");
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
			resp = restTemplate
					.getForObject(
							env.getSalesUrl() + "rest-get-items-details-by-challan-id?selectedValuesStr="
									+ selectedValuesStr + "&orgName=" + orgName + "&orgDiv=" + orgDivision,
							JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getItemDetailsOnChallan ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-items-by-order")
	public @ResponseBody JsonResponse<Object> getItemDetailsOnOrder(Model model, HttpSession session,
			@RequestParam String selectedValuesStr) {
		logger.info("Method : getItemDetailsOnOrder starts");
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
			resp = restTemplate
					.getForObject(
							env.getSalesUrl() + "rest-get-items-details-by-order-id?selectedValuesStr="
									+ selectedValuesStr + "&orgName=" + orgName + "&orgDiv=" + orgDivision,
									JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getItemDetailsOnOrder ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-through-ajax")
	public @ResponseBody Object viewsalesdeliveryChallan(@RequestParam String type, @RequestParam String fromDate, @RequestParam String toDate, HttpSession session) {
		logger.info("Method :viewsalesdeliveryChallan starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getAllsalesInvoice?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&type=" + type + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewsalesdeliveryChallan ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-edit-new")
	public @ResponseBody Object editSalesInvoice(@RequestParam String id, HttpSession session) {
		logger.info("Method :editSalesInvoice starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-editSalesInvoice?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editSalesInvoice ends");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-saleInvoice-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getDeliveryChallanInsertedId(@RequestParam String type) {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesInvoiceInsertedId?type=" + type,
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
		logger.info("Method : getDeliveryChallanInsertedId ends");

		return res;
	}

	/*
	 * delete
	 */ 
	/* @SuppressWarnings("unchecked")
	@PostMapping(value = "view-saleInvoice-delete")
	public @ResponseBody JsonResponse<Object> deletesalesInvoice(@RequestBody SalesInvoiceNewModel salesInvoiceNewModel,
			HttpSession session) {
		logger.info("Method : deletesalesOrder starts");
		logger.info("SalesInvoiceNewModel" + salesInvoiceNewModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			salesInvoiceNewModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "deletesalesInvoice", salesInvoiceNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			resp.setMessage("Success");
		}
		logger.info("delete@" + resp);
		logger.info("Method : deletesalesInvoice Ends");
		return resp;
	}*/
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-delete")
	public @ResponseBody JsonResponse<Object> deletesalesInvoice(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : deletesalesInvoice starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String createdBy = "";
		String orgName = "";
		String orgDivision = "";

		try {
			createdBy = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "deletesalesInvoice?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method :  deletesalesInvoice ends"+resp);
		return resp;
	}

	// download
/*	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-pdf-downloads")
	public void nonroutineActivityPdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1, @RequestParam("type") String encodedParam2) {
		logger.info("Method : nonroutineActivityPdf starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String id = (new String(encodeByte3));
		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String type = (new String(encodeByte2));
		System.out.println("id=====" + id + "=====type======" + type);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> data = new HashMap<String, Object>();
		JsonNode pdfHorlicksdataNode = null;
			    try {		   
			    	resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getSalesInvoicePdf?id=" + id + "&orgName="
							+ orgName + "&orgDivision=" + orgDivision+"&type="+type, JsonResponse.class);
			    	System.err.println("resp sales invoice======"+resp.getBody().toString());
			        JsonNode rootNode = objectMapper.readTree(resp.getBody().toString());
			        pdfHorlicksdataNode = rootNode.path("pdfData");
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			    logger.info("pdfHorlicksdataNode====" + pdfHorlicksdataNode);
			    Map<String,Object> allPdfData = objectMapper.convertValue(pdfHorlicksdataNode, Map.class);
			    System.out.println("All Pdf Data-------->"+allPdfData);
		        double totalSgst = 0.0;
		        double totalCgst = 0.0;
		        double totalAmount = 0.0;

		        List<Map<String, Object>> itemList = (List<Map<String, Object>>) allPdfData.get("list");
		        for (Map<String, Object> item : itemList) {
		            Object sgstVal = item.get("sgst");
		            Object cgstVal = item.get("cgst");
		            Object totalAmntVal = item.get("taxableValue");

		            if (sgstVal != null) {
		                totalSgst += Double.parseDouble(sgstVal.toString());
		            }

		            if (cgstVal != null) {
		                totalCgst += Double.parseDouble(cgstVal.toString());
		            }
		            if (totalAmntVal != null) {
		            	totalAmount += Double.parseDouble(totalAmntVal.toString());
		            }
		        }
		        double totalTax = totalSgst + totalCgst;
		        double totalTaxableAmount = totalTax + totalAmount;
		        long roundedTax = Math.round(totalTax);
		        long roundedTaxableAmount = Math.round(totalTaxableAmount);
		        String totalTaxInWords = NumberToWordsConverter.convertWords(roundedTax);
		        String totalTaxableAmountInWords = NumberToWordsConverter.convertWords(roundedTaxableAmount);
			    data.put("allData", objectMapper.convertValue(pdfHorlicksdataNode, Map.class));
			    data.put("totalCgst",totalCgst);
			    data.put("totalSgst",totalSgst);
			    data.put("totalTaxInWords",totalTaxInWords);
			    data.put("totalAmount",totalAmount);
			    data.put("totalTaxableAmount",totalTaxableAmount);
			    data.put("totalTaxableAmountInWords",totalTaxableAmountInWords);
						
				System.err.println("data for pdf===="+data);
		response.setContentType("application/pdf");
		String fileName = "";

		if ("Perform".equals(type)) {
		    fileName = "Proforma Invoice (" + id.replaceAll("[\\\\/:*?\"<>|]", "_") + ").pdf";
		} else {
		    fileName = "Tax Invoice (" + id.replaceAll("[\\\\/:*?\"<>|]", "_") + ").pdf";
		}
		response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
		File file;
		byte[] fileData = null;
		try {
			if("Perform".equals(type)) {				
				file = pdfGeneratorUtil.createPdf("sales/performa-tax-invoice-pdf.html", data);
			}else {
				file = pdfGeneratorUtil.createPdf("sales/sales-tax-invoice-pdf.html", data);
			}			
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
		logger.info("Method : nonroutineActivityPdf ends");
	}

*/
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-pdf-downloads")
	public void nonroutineActivityPdf(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("id") String encodedParam1,
	        @RequestParam("type") String encodedParam2,
	        @RequestParam("copies") String encodedParam3) {

	    logger.info("Method : nonroutineActivityPdf starts");

	    String orgName = "";
	    String orgDivision = "";
	    try {
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error(e.getMessage());
	    }

	    // Decode request parameters
	    String id = new String(Base64.getDecoder().decode(encodedParam1.getBytes()));
	    String type = new String(Base64.getDecoder().decode(encodedParam2.getBytes()));
	    String decodedCopies = new String(Base64.getDecoder().decode(encodedParam3.getBytes()));
	    //String copies = URLDecoder.decode(decodedCopies, StandardCharsets.UTF_8.name());
	    String copies = "";
	    try {
	        copies = URLDecoder.decode(decodedCopies, StandardCharsets.UTF_8.name());
	    } catch (UnsupportedEncodingException e) {
	        logger.error("UTF-8 decoding not supported", e);
	    }


	    logger.info("Decoded id: " + id + ", type: " + type + ", copies: " + copies);

	    JsonResponse<Object> resp = new JsonResponse<>();
	    ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> data = new HashMap<>();
	    JsonNode pdfDataNode = null;

	    try {
	        resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getSalesInvoicePdf?id=" + id
	                + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&type=" + type, JsonResponse.class);
	        JsonNode rootNode = objectMapper.readTree(resp.getBody().toString());
	        pdfDataNode = rootNode.path("pdfData");
	    } catch (Exception e) {
	        logger.error("Error fetching invoice data: ", e);
	    }

	    Map<String, Object> allPdfData = objectMapper.convertValue(pdfDataNode, Map.class);

	    // Calculate totals
	    double totalSgst = 0.0;
	    double totalCgst = 0.0;
	    double totalAmount = 0.0;

	    List<Map<String, Object>> itemList = (List<Map<String, Object>>) allPdfData.get("list");
	    for (Map<String, Object> item : itemList) {
	        if (item.get("sgst") != null) {
	            totalSgst += Double.parseDouble(item.get("sgst").toString());
	        }
	        if (item.get("cgst") != null) {
	            totalCgst += Double.parseDouble(item.get("cgst").toString());
	        }
	        if (item.get("taxableValue") != null) {
	            totalAmount += Double.parseDouble(item.get("taxableValue").toString());
	        }
	    }

	    double totalTax = totalSgst + totalCgst;
	    double totalTaxableAmount = totalTax + totalAmount;
	    long roundedTax = Math.round(totalTax);
	    long roundedTaxableAmount = Math.round(totalTaxableAmount);

	    String totalTaxInWords = NumberToWordsConverter.convertWords(roundedTax);
	    String totalTaxableAmountInWords = NumberToWordsConverter.convertWords(roundedTaxableAmount);

	    // Prepare data for template
	    data.put("allData", allPdfData);
	    data.put("totalCgst", totalCgst);
	    data.put("totalSgst", totalSgst);
	    data.put("totalTaxInWords", totalTaxInWords);
	    data.put("totalAmount", totalAmount);
	    data.put("totalTaxableAmount", totalTaxableAmount);
	    data.put("totalTaxableAmountInWords", totalTaxableAmountInWords);

	    // Normalize and check which copies to include
	    List<File> filesToMerge = new ArrayList<>();
	    boolean includeOriginal = false;
	    boolean includeDuplicate = false;
	    boolean includeTriplicate = false;
	    boolean includeExtra = false;

	    if (copies == null || copies.trim().isEmpty()) {
	        includeOriginal = includeDuplicate = includeTriplicate = includeExtra = true;
	    } else {
	        String lowerCopies = copies.toLowerCase();
	        includeOriginal = lowerCopies.contains("original for consignee");
	        includeDuplicate = lowerCopies.contains("duplicate for transporter");
	        includeTriplicate = lowerCopies.contains("triplicate for consignor");
	        includeExtra = lowerCopies.contains("extra copy");
	    }

	    try {
	        if ("Perform".equalsIgnoreCase(type)) {
	            if (includeOriginal)
	                filesToMerge.add(pdfGeneratorUtil.createPdf("sales/performa-tax-invoice-pdf.html", data));
	            if (includeDuplicate)
	                filesToMerge.add(pdfGeneratorUtil.createPdf("sales/performa-tax-invoice-pdf1.html", data));
	            if (includeTriplicate)
	                filesToMerge.add(pdfGeneratorUtil.createPdf("sales/performa-tax-invoice-pdf2.html", data));
	            if (includeExtra)
	                filesToMerge.add(pdfGeneratorUtil.createPdf("sales/performa-tax-invoice-pdf3.html", data));
	        } else {
	            if (includeOriginal)
	                filesToMerge.add(pdfGeneratorUtil.createPdf("sales/sales-tax-invoice-pdf.html", data));
	            if (includeDuplicate)
	                filesToMerge.add(pdfGeneratorUtil.createPdf("sales/sales-tax-invoice-pdf1.html", data));
	            if (includeTriplicate)
	                filesToMerge.add(pdfGeneratorUtil.createPdf("sales/sales-tax-invoice-pdf2.html", data));
	            if (includeExtra)
	                filesToMerge.add(pdfGeneratorUtil.createPdf("sales/sales-tax-invoice-pdf3.html", data));
	        }

	        if (filesToMerge.isEmpty()) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No invoice copies selected.");
	            return;
	        }

	        // Merge selected PDFs
	        File mergedPdfFile = File.createTempFile("MergedInvoice", ".pdf");
	        PdfDocument mergedPdf = new PdfDocument(new PdfWriter(mergedPdfFile));
	        PdfMerger merger = new PdfMerger(mergedPdf);

	        for (File file : filesToMerge) {
	            PdfDocument sourceDoc = new PdfDocument(new PdfReader(file));
	            merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
	            sourceDoc.close();
	        }

	        mergedPdf.close();

	        // Stream merged file to response
	        InputStream in = new FileInputStream(mergedPdfFile);
	        byte[] fileData = IOUtils.toByteArray(in);

	        response.setContentType("application/pdf");
	        String fileName = ("Perform".equals(type) ? "Proforma Invoice" : "Tax Invoice")
	                + " (" + id.replaceAll("[\\\\/:*?\"<>|]", "_") + ").pdf";
	        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	        response.setContentLength(fileData.length);
	        response.getOutputStream().write(fileData);
	        response.getOutputStream().flush();
	    } catch (Exception e) {
	        logger.error("Error generating or sending PDF: ", e);
	    }

	    logger.info("Method : nonroutineActivityPdf ends");
	}



	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-get-preview-data")
	public @ResponseBody Object getPerformaInvoicePreviewData(@RequestParam String invoiceId,@RequestParam String type, HttpSession session) {
		logger.info("Method :getPerformaInvoicePreviewData starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getSalesInvoicePdf?id=" + invoiceId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision+"&type="+type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getPerformaInvoicePreviewData ends");
		return resp;
	}
}
