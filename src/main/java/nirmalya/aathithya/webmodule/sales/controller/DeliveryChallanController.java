package nirmalya.aathithya.webmodule.sales.controller;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller
@RequestMapping(value = { "sales/" })
public class DeliveryChallanController {
	Logger logger = LoggerFactory.getLogger(DeliveryChallanController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	DeliveryChallanController deliveryChallanController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/view-deliverychallan" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : customerDetails starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
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
			DropDownModel[] Gender = restTemplate.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);
			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
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
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
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
			DropDownModel[] brand = restTemplate.getForObject(
					env.getMasterUrl() + "getBrandListForProduct?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);
			model.addAttribute("brandList", brandList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] mode = restTemplate.getForObject(env.getMasterUrl() + "getModeListForProduct",
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

		logger.info("Method : customerDetails ends");
		return "sales/view-deliverychallan";
	}

	// Add New Customer
	@SuppressWarnings("unchecked")
	@PostMapping("view-deliverychallan-adds")
	public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {

		logger.info("Method : addAccountCustomer starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
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

			customerNewModel.setCreatedBy(userId);
			customerNewModel.setOrganization(organization);
			customerNewModel.setOrgDivision(orgDivision);

			resp = restTemplate.postForObject(env.getSalesUrl() + "/addAccountCustomer", customerNewModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addAccountCustomer ends");

		return resp;
	}

	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-deliverychallan-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCustomerAutoSearchList starts");

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

		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("view-deliverychallan-add-cust-billingaddress")
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

		logger.info("Method :addbillingaddress ends");
		return resp;
	}

	// Add customer shipping address
	@SuppressWarnings("unchecked")

	@PostMapping("view-deliverychallan-add-cust-shippingaddress")
	public @ResponseBody JsonResponse<Object> addshippingaddress(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : addshippingaddress starts");
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		customerNewModel.setCreatedBy(userId);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "add-shippingaddress", customerNewModel,
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

		logger.info("Method :addshippingaddress ends");
		return resp;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-deliverychallan-stateList" })
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

		logger.info("Method : getstateCusList ends");
		return res;
	}
//Get delivery challan address

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-get-address")
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
		logger.info("EDITjsonResponse" + jsonResponse);
		return jsonResponse;
	}

	// Get tcs Auto search
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-deliverychallan-get-tcs-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getTCSAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getTCSAutoSearchList starts");
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

		logger.info("Method : getTCSAutoSearchList ends" + res);
		return res;
	}

	// Add Tcs
	@SuppressWarnings("unchecked")

	@PostMapping("view-deliverychallan-add-tcs")
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

		logger.info("Method :addTcs ends");
		return resp;
	}

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("view-deliverychallan-get-product-list")
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
	@PostMapping(value = { "view-deliverychallan-item-get-customer-list" })
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
	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "view-deliverychallan-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
			@RequestBody String index, HttpSession session) {
		logger.info("Method : getProductsByCatInvoice starts");

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

		logger.info("Method : getProductsByCatInvoice ends");
		return res;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-deliverychallan-add")
	public @ResponseBody JsonResponse<Object> addsaleDeliveryChallan(HttpSession session,
			@RequestBody List<DeliveryChallanModel> deliveryChallanModel) {
		logger.info("Method : addsaleDeliveryChallan starts");

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

		for (DeliveryChallanModel a : deliveryChallanModel) {
			if (a.getEbillDate() != null && a.getEbillDate() != "") {
				a.setEbillDate(DateFormatter.inputDateFormat(a.getEbillDate(), dateFormat));
			}
		}

		for (DeliveryChallanModel m : deliveryChallanModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "addsaleDeliveryChallan", deliveryChallanModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<DeliveryChallanModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<DeliveryChallanModel>>() {
					});

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

		logger.info("Method : addsaleDeliveryChallan ends");

		return resp;
	}

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-deliverychallan-through-ajax")
	public @ResponseBody Object viewsalesdeliveryChallan(@RequestParam String pageno, @RequestParam String fromDate, @RequestParam String toDate, HttpSession session) {

		logger.info("Method :viewsalesdeliveryChallan starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesdeliveryChallan?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewsalesdeliveryChallan ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	/*
	 * edit Delivery Challan
	 */

	@GetMapping(value = { "view-deliverychallan-edit-new" })
	public @ResponseBody List<DeliveryChallanModel> viewsalesChallanEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewsalesChallanEdit starts");

		List<DeliveryChallanModel> productList = new ArrayList<DeliveryChallanModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				DeliveryChallanModel[] deliveryChallanModel = restTemplate.getForObject(
						env.getSalesUrl() + "viewsalesChallanEdit?id=" + id, DeliveryChallanModel[].class);

				productList = Arrays.asList(deliveryChallanModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (DeliveryChallanModel m : deliveryChallanModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
					if (m.getEbillDate() != null && m.getEbillDate() != "") {
						m.setEbillDate(DateFormatter.dateFormat(m.getEbillDate(), dateFormat));
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
										String docPath = "<i class=\"fa-solid fa-file-csv custom-file-icon\" title="
												+ m.getFileName() + "></i>";

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
		logger.info("Method : viewsalesChallanEdit ends");

		return productList;
	}

	/*
	 * edit Delivery Challan
	 */

	@GetMapping(value = { "view-deliverychallan-getInvoice" })
	public @ResponseBody List<DeliveryChallanModel> viewsalesChallanGetInvoice(@RequestParam String id, String poid,
			String noOfChallan, HttpSession session) {
		logger.info("Method : viewsalesChallanGetInvoice starts");

		List<DeliveryChallanModel> productList = new ArrayList<DeliveryChallanModel>();
		if (id != null && id != "") {
			try {
				DeliveryChallanModel[] deliveryChallanModel = restTemplate.getForObject(env.getSalesUrl()
						+ "viewsalesChallanGetInvoice?id=" + id + "&poid=" + poid + "&noOfChallan=" + noOfChallan,
						DeliveryChallanModel[].class);

				productList = Arrays.asList(deliveryChallanModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (DeliveryChallanModel m : deliveryChallanModel) {
					count++;
					m.setSlNo(count);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewsalesChallanGetInvoice ends");

		return productList;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-deliverychallan-delete")
	public @ResponseBody JsonResponse<Object> deletesalesDeliveryChallan(
			@RequestBody DeliveryChallanModel deliveryChallanModel, HttpSession session) {
		logger.info("Method : deletesalesDeliveryChallan starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			deliveryChallanModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "deletesalesDeliveryChallan", deliveryChallanModel,
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

		logger.info("Method : deletesalesDeliveryChallan Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-deliverychallan-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getDeliveryChallanInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getDeliveryChallanInsertedId", JsonResponse.class);
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

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-deliverychallan-salespolist" })
	public @ResponseBody JsonResponse<Object> getSalesPoListForDc(@RequestParam String id) {
		logger.info("Method : getSalesPoListForDc starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesPoListForDc?id=" + id, JsonResponse.class);
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
		logger.info("Method : getSalesPoListForDc ends");
		return res;
	}

	// Get PackagrId

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-deliverychallan-get-PackageId" })
	public @ResponseBody JsonResponse<Object> getPackageId(@RequestParam String id) {
		logger.info("Method : getPackageId starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getPackageIdDc?id=" + id, JsonResponse.class);
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
		logger.info("Method : getPackageId ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-pdf-downloads")
	public void getDcPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1) {

		logger.info("Method : getDcPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String dcId = (new String(encodeByte1));

		List<DeliveryChallanModel> productList = new ArrayList<DeliveryChallanModel>();
		try {
			DeliveryChallanModel[] deliveryChallanModel = restTemplate.getForObject(
					env.getSalesUrl() + "view-dc-viewPdf?id=" + dcId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					DeliveryChallanModel[].class);
			productList = Arrays.asList(deliveryChallanModel);
			productList.forEach(s -> s.setSlNo(s.getSlNo()));
			int count = 0;
			for (DeliveryChallanModel m : deliveryChallanModel) {
				count++;
				m.setSlNo(count);
				String dateFormat = (String) session.getAttribute("DATEFORMAT");
				if (m.getInvoiceDate() != null && m.getInvoiceDate() != "") {
					m.setInvoiceDate(DateFormatter.dateFormat(m.getInvoiceDate(), dateFormat));
				}
				if (m.getSoDate() != null && m.getSoDate() != "") {
					m.setSoDate(DateFormatter.dateFormat(m.getSoDate(), dateFormat));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String id = productList.get(0).getCustId();
		String shipId = productList.get(0).getShippingHiddenId();

		JsonResponse<CustomerNewModel> jsonResponse = new JsonResponse<CustomerNewModel>();
		try {
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id + "&shipId="
					+ shipId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<CustomerNewModel>() {
		});
		logger.info("JsonResponse====" + jsonResponse);
		logger.info("reimModel====" + reimModel);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("deliverychallan", productList);

		// String logo = "";
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
		data.put("buyer", reimModel);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=salesInvoice.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("sales/deliverychallan-pdf", data);
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

		logger.info("Method : getDcPdfDetails ends");
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-deliverychallan-get-customer-listt" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchListt(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCustomerAutoSearchList starts");

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

		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-deliverychallan-salesorderlistt" })
	public @ResponseBody JsonResponse<Object> getSalesOrderListt(@RequestParam String id, String type) {
		logger.info("Method : getSalesOrderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesOrderListDc?id=" + id + "&type=" + type,
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
		logger.info("Method : getSalesOrderList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-po-wise")
	public @ResponseBody List<DeliveryChallanModel> viewsalesPOWiseDeliveryChallan(HttpSession session,
			@RequestParam String id) {

		logger.info("Method :viewsalesPOWiseDeliveryChallan starts");
		JsonResponse<List<DeliveryChallanModel>> resp = new JsonResponse<List<DeliveryChallanModel>>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesPOWiseDeliveryChallan?org=" + orgName
					+ "&orgDiv=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewsalesPOWiseDeliveryChallan ends");

		return resp.getBody();
	}

	@GetMapping("view-deliverychallan-shipping-address")
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

	@GetMapping("view-deliverychallan-shipping-dataedit")
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
	// Add New Customer

	@SuppressWarnings("unchecked")
	@PostMapping("/view-deliverychallan-adds-customer")
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

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-shippingdetails")
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

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-deliverychallan-save-shipping-address")
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
	@GetMapping("view-deliverychallan-edit-address")
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

	@GetMapping("view-deliverychallan-address-delete")
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
	@GetMapping(value = { "view-deliverychallan-get-sku-by-product" })
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
	@GetMapping(value = { "view-deliverychallan-get-sku-listing" })
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
	@PostMapping(value = { "view-deliverychallan-get-product-details" })
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
	@PostMapping("view-deliverychallan-products-delete")
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

	@SuppressWarnings("unchecked")
	@PostMapping("/view-deliverychallan-get-total-list")
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
	@PostMapping("/view-deliverychallan-products-save")
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

	@PostMapping("/view-deliverychallan-upload-fileprofuct")
	public @ResponseBody JsonResponse<Object> uploadProductFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadProductFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("productPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadProductFile controller ' ends");
		return response;
	}

	@PostMapping("view-deliverychallan-delete-fileproduct")
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
	@PostMapping("/view-deliverychallan-save-sku-dtls")
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
	@PostMapping(value = { "view-deliverychallan-get-sku-details" })
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
	@GetMapping("view-deliverychallan-deletesku")
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

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("view-deliverychallan-item-get-product-list")
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

	@SuppressWarnings("unchecked")
	@GetMapping("view-packages-for-challan")
	public @ResponseBody Object viewsalesPackages(@RequestParam String pageno, HttpSession session) {

		logger.info("Method :viewsalesPackages starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-sales-packages-for-challan?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewsalesPackages ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-get-po")
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-po-challan?custId=" + custId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getPoOnCustomerChange ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-get-edit-po")
	public @ResponseBody JsonResponse<Object> getPoOnCustomerChangeEdit(Model model, HttpSession session,
			@RequestParam String custId, @RequestParam String chId) {
		logger.info("Method : getPoOnCustomerChangeEdit starts");
		
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-po-challan-edit?custId=" + custId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision + "&chId=" + chId, JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getPoOnCustomerChangeEdit ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-get-package")
	public @ResponseBody JsonResponse<Object> getPackageOnPo(Model model, HttpSession session,
			@RequestParam String poId) {

		logger.info("Method : getPackageOnPo starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-package?poId=" + poId + "&orgName=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getPackageOnPo ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-get-item-details")
	public @ResponseBody JsonResponse<Object> getItemDetailsOnPackage(Model model, HttpSession session,
			@RequestParam String selectedValuesStr) {

		logger.info("Method : getItemDetailsOnPackage starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-item-details?selectedValuesStr="
					+ selectedValuesStr + "&orgName=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getItemDetailsOnPackage ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-get-preview-data")
	public @ResponseBody JsonResponse<Object> getPreviewData(Model model, HttpSession session,
			@RequestParam String id) {
		logger.info("Method : getPreviewData starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "get-challan-pdf?challanId=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in restTemplate call: ", e);
		}

		logger.info("Method : getPreviewData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-get-transporter-details")
	public @ResponseBody JsonResponse<Object> getTransporterDetails(Model model, HttpSession session,
			@RequestParam String searchValue) {

		logger.info("Method : getTransporterDetails starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "get-transporter-details?id="
					+ searchValue + "&orgName=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTransporterDetails ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-challan-pdf-downloads" })
	public void getChallanDataForPdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam String challanId,@RequestParam String copies) {

		logger.info("Method : getChallanDataForPdf starts");
		JsonResponse<Object> resp = new JsonResponse<>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "get-challan-pdf?challanId=" + challanId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in restTemplate call: ", e);
			return;
		}

		logger.info("Fetched response data: " + resp);

		// Parse the JSON response
		String responseBody = (String) resp.getBody();
		Map<String, Object> pdfData = new HashMap<>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> responseMap = mapper.readValue(responseBody, Map.class);
			System.out.println("PDF DATA FOR DELIVERY CHALLAN--------->" + responseMap);

			// Extract challanPdfData (it's a list, but we expect only one entry)
			List<Map<String, Object>> challanPdfDataList = (List<Map<String, Object>>) responseMap
					.get("challanPdfData");
			if (challanPdfDataList != null && !challanPdfDataList.isEmpty()) {
				Map<String, Object> challanData = challanPdfDataList.get(0);

				// Map the fields to pdfData
				pdfData.put("challanId", challanData.get("challanId"));
				pdfData.put("soNumber", challanData.get("soNumber"));
				pdfData.put("challanDate", challanData.get("challanDate"));
				pdfData.put("dcDesc", challanData.get("dcDesc"));
				pdfData.put("poNumber", challanData.get("cpoNumber") != null ? challanData.get("cpoNumber") : "N/A");
				pdfData.put("soDate", challanData.get("soDate") != null ? challanData.get("soDate") : "N/A");
				pdfData.put("poDate", challanData.get("poDate") != null ? challanData.get("poDate") : "N/A");
				pdfData.put("vechicleNumber", challanData.get("vechicleNumber"));
				pdfData.put("transporterName", challanData.get("transporterName"));
				pdfData.put("soNumber", challanData.get("soNumber"));
				pdfData.put("dcComment", challanData.get("dcComment"));
				pdfData.put("dcRemark", challanData.get("dcRemark"));
				pdfData.put("supplyFrom", challanData.get("supplyFrom"));

				// Extract customer details
				List<Map<String, Object>> customerDetailsList = (List<Map<String, Object>>) challanData
						.get("customerDetails");
				if (customerDetailsList != null && !customerDetailsList.isEmpty()) {
					Map<String, Object> customerData = customerDetailsList.get(0);
					pdfData.put("customerName", customerData.get("customerName"));
					pdfData.put("customerAddress", customerData.get("customerAddress"));
					pdfData.put("customerGst", customerData.get("CcustomerGst"));
					pdfData.put("stateCode", customerData.get("stateCode"));
				}

				List<Map<String, Object>> itemDetailsList = (List<Map<String, Object>>) challanData.get("itemDetails");
				if (itemDetailsList != null && !itemDetailsList.isEmpty()) {
					double totalCGST = 0.0;
					double totalSGST = 0.0;
					double totalIGST = 0.0;
					double grandTotal = 0.0;

					for (int i = 0; i < itemDetailsList.size(); i++) {
						Map<String, Object> item = itemDetailsList.get(i);
						item.put("serialNumber", i + 1);

						double totalAmount = (double) item.get("totalAmount");
						double cGst = (double) item.get("cGst");
						double sGst = (double) item.get("sGst");
						double iGst = (double) item.get("iGst");
//	                    Object cgstRateObj = item.get("cgstRate");
//	                    Object sgstRateObj = item.get("sgstRate");
//
//	                    double cgstRate = cgstRateObj != null ? Double.parseDouble(cgstRateObj.toString()) : 0.0;
//	                    double sgstRate = sgstRateObj != null ? Double.parseDouble(sgstRateObj.toString()) : 0.0;
//
//	                    double cGst = totalAmount * (cgstRate / 100);
//	                    double sGst = totalAmount * (sgstRate / 100);
//	                    double sGst = totalAmount * (sgstRate / 100);

						item.put("cGst", cGst);
						item.put("sGst", sGst);

						totalCGST += cGst;
						totalSGST += sGst;
						totalIGST += iGst;
						grandTotal += totalAmount + cGst + sGst + iGst;
					}

					// Add item details and totals to pdfData
					pdfData.put("itemDetails", itemDetailsList);
					pdfData.put("totalCGST", totalCGST);
					pdfData.put("totalSGST", totalSGST);
					pdfData.put("totalIGST", totalIGST);
					pdfData.put("grandTotal", grandTotal);
					System.out.println("After Serialize The Pdf Data----->" + pdfData);
				}
			}
		} catch (IOException e) {
			logger.error("Error parsing JSON response: ", e);
		}

		String filename = "delivery-challan-" + challanId + ".pdf";
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		System.out.println("copies Data================================"+copies);
	/*  File file1;
		File file2;
		File file3;
		File file4;
		try {
			file1 = pdfGeneratorUtil.createPdf("sales/delivery-challan-pdf", pdfData);
			file2 = pdfGeneratorUtil.createPdf("sales/delivery-challan-pdf1", pdfData);
			file3 = pdfGeneratorUtil.createPdf("sales/delivery-challan-pdf2", pdfData);
			file4 = pdfGeneratorUtil.createPdf("sales/delivery-challan-pdf3", pdfData);
			
			File mergedPdfFile = File.createTempFile("MergedChallan", ".pdf");

	        PdfDocument pdfDoc1 = new PdfDocument(new PdfReader(file1));
	        PdfDocument pdfDoc2 = new PdfDocument(new PdfReader(file2));
	        PdfDocument pdfDoc3 = new PdfDocument(new PdfReader(file3));
	        PdfDocument pdfDoc4 = new PdfDocument(new PdfReader(file4));
	        PdfDocument mergedPdf = new PdfDocument(new PdfWriter(mergedPdfFile));

	        PdfMerger merger = new PdfMerger(mergedPdf);
	        merger.merge(pdfDoc1, 1, pdfDoc1.getNumberOfPages());
	        merger.merge(pdfDoc2, 1, pdfDoc2.getNumberOfPages());
	        merger.merge(pdfDoc3, 1, pdfDoc3.getNumberOfPages());
	        merger.merge(pdfDoc4, 1, pdfDoc4.getNumberOfPages());

	        pdfDoc1.close();
	        pdfDoc2.close();
	        mergedPdf.close();
			
			InputStream in = new FileInputStream(mergedPdfFile);
			byte[] fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			logger.error("Error while generating or sending PDF", e);
		} catch (Exception e1) {
			logger.error("Unexpected error occurred", e1);
		}*/
		
		List<File> generatedFiles = new ArrayList<>();

		try {
		    List<String> selectedCopies = new ArrayList<>();

		    if (copies != null && !copies.trim().isEmpty()) {
		        selectedCopies = Arrays.stream(copies.split(","))
		                               .map(String::trim)
		                               .collect(Collectors.toList());
		    }

		    // If nothing selected, generate all
		    if (selectedCopies.isEmpty() || selectedCopies.size() == 0) {
		        selectedCopies = Arrays.asList(
		            "Original for Consignee",
		            "Duplicate for Transporter",
		            "Triplicate for Consignor",
		            "Extra Copy"
		        );
		    }

		    if (selectedCopies.contains("Original for Consignee")) {
		        File file = pdfGeneratorUtil.createPdf("sales/delivery-challan-pdf", pdfData);
		        generatedFiles.add(file);
		    }

		    if (selectedCopies.contains("Duplicate for Transporter")) {
		        File file = pdfGeneratorUtil.createPdf("sales/delivery-challan-pdf1", pdfData);
		        generatedFiles.add(file);
		    }

		    if (selectedCopies.contains("Triplicate for Consignor")) {
		        File file = pdfGeneratorUtil.createPdf("sales/delivery-challan-pdf2", pdfData);
		        generatedFiles.add(file);
		    }

		    if (selectedCopies.contains("Extra Copy")) {
		        File file = pdfGeneratorUtil.createPdf("sales/delivery-challan-pdf3", pdfData);
		        generatedFiles.add(file);
		    }

		    // Merge all selected PDFs
		    File mergedPdfFile = File.createTempFile("MergedChallan", ".pdf");
		    PdfDocument mergedPdf = new PdfDocument(new PdfWriter(mergedPdfFile));
		    PdfMerger merger = new PdfMerger(mergedPdf);

		    for (File file : generatedFiles) {
		        PdfDocument doc = new PdfDocument(new PdfReader(file));
		        merger.merge(doc, 1, doc.getNumberOfPages());
		        doc.close();
		    }

		    mergedPdf.close();

		    // Return merged file
		    InputStream in = new FileInputStream(mergedPdfFile);
		    byte[] fileData = IOUtils.toByteArray(in);
		    response.setContentLength(fileData.length);
		    response.getOutputStream().write(fileData);
		    response.getOutputStream().flush();

		} catch (IOException e) {
		    logger.error("Error while generating or sending PDF", e);
		} catch (Exception e1) {
		    logger.error("Unexpected error occurred", e1);
		}

		logger.info("Method : getChallanDataForPdf ends");
	}

}
