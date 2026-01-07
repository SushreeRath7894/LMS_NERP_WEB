package nirmalya.aathithya.webmodule.sales.controller;

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
import org.json.JSONArray;
import org.json.JSONObject;
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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoiceNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;

@Controller
@RequestMapping(value = { "sales/" })
public class SalesOrderNewController {

	Logger logger = LoggerFactory.getLogger(SalesOrderNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SalesOrderNewController salesOrderNewController;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/view-saleorder" })
	public String salesOrderDetails(Model model, HttpSession session) {
		logger.info("Method : salesOrderDetails starts");
		String organization = "";
		String orgDivision = "";
		String org = "";
		String orgDiv = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-Payment-term",
					DropDownModel[].class);
			List<DropDownModel> paytermList = Arrays.asList(dropDownModel);
			model.addAttribute("paytermList", paytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] paymentMode = restTemplate.getForObject(env.getEmployeeUrl() + "getPaymentMode",
					DropDownModel[].class);
			List<DropDownModel> PayModeLists = Arrays.asList(paymentMode);

			model.addAttribute("PayModeLists", PayModeLists);

		} catch (RestClientException e) {
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
//		try {
//			DropDownModel[] brand = restTemplate.getForObject(
//					env.getSalesUrl() + "getBrandListForSalesProduct?org=" + org + "&orgDiv=" + orgDiv,
//					DropDownModel[].class);
//			List<DropDownModel> brandListt = Arrays.asList(brand);
//
//			model.addAttribute("brandListt", brandListt);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			DropDownModel[] mode = restTemplate.getForObject(env.getSalesUrl() + "getModeListForSalesProduct",
//					DropDownModel[].class);
//			List<DropDownModel> modeList = Arrays.asList(mode);
//
//			model.addAttribute("modeList", modeList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] department = restTemplate.getForObject(env.getSalesUrl()
					+ "ProjectListForSale?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] country = restTemplate.getForObject(env.getProjects() + "rest-project-country-list",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// for billing country drop down
		try {
			DropDownModel[] country = restTemplate.getForObject(env.getProjects() + "rest-project-billing-country-list",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// for shipping country drop down

		try {
			DropDownModel[] country = restTemplate
					.getForObject(env.getProjects() + "rest-project-shipping-country-list", DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] projectList = restTemplate.getForObject(
					env.getSalesUrl() + "getProjectAutoSearchList?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> projectListt = Arrays.asList(projectList);
			model.addAttribute("projectList", projectListt);
			System.out.println("Project List------>" + projectListt);
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
					env.getSalesUrl() + "get-sku-master-list?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> productList = Arrays.asList(brand);

			model.addAttribute("productList", productList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : salesOrderDetails ends");
		return "sales/view-salesorder";
	}

	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleorder-get-customer-list" })
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

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-saleorder-stateList" })
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

	/*
	 * Item auto search
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleorder-item-get-customer-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchListForItem(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
		logger.info("QuotationNewModel" + searchValue);
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(
					env.getSalesUrl() + "getItemQuotationAutoSearchListForItem?id=" + searchValue, JsonResponse.class);
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

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("/view-saleorder-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getSalesProductCategoryDataListModal(
			HttpSession session) {
		logger.info("Method : getSalesProductCategoryDataListModal starts");
		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "getSalesProductCategoryDataListModal",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getSalesProductCategoryDataListModal starts");
		return resp;
	}

	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "view-saleorder-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCat(Model model, @RequestBody String index,
			BindingResult result) {
		logger.info("Method : getProductsByCat starts");
		// logger.info(index);
		String indexValue = index.substring(0, index.length() - 1);

		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getProductsNByCat?id=" + indexValue,
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
		// logger.info(res);
		logger.info("Method : getProductsByCat ends");
		return res;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleorder-add")
	public @ResponseBody JsonResponse<Object> addsalenew(HttpSession session,
			@RequestBody List<SalesOrderNewModel> salesOrderNewModel) {
		logger.info("Method : addsalenew starts");
		logger.info(salesOrderNewModel.toString());
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
		for (SalesOrderNewModel a : salesOrderNewModel) {

			if (a.getExpectedShipmentDate() != null && a.getExpectedShipmentDate() != "") {
				a.setExpectedShipmentDate(DateFormatter.inputDateFormat(a.getExpectedShipmentDate(), dateFormat));
			}
			if (a.getOrderReceiveDate() != null && a.getOrderReceiveDate() != "") {
				a.setOrderReceiveDate(DateFormatter.inputDateFormat(a.getOrderReceiveDate(), dateFormat));
			}

		}
		for (SalesOrderNewModel m : salesOrderNewModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		for (InventoryVendorDocumentModel a : salesOrderNewModel.get(0).getDocumentList()) {

			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String[] extension = a.getFileName().split("\\.");
					int lastindex = extension.length - 1;
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							String imageName = saveAllMultiImages(bytes, extension[lastindex]);
							a.setFileName(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				}
			}
		}
		logger.info("salesOrderNewModel===" + salesOrderNewModel);
		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addsalenew", salesOrderNewModel, JsonResponse.class);
			logger.info(salesOrderNewModel.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if (resp.getMessage() != "" && resp.getMessage() != null) {
		 resp.setCode(resp.getMessage()); 
		 resp.setMessage("Success"); 
		 } else {
		 resp.setMessage("Unsuccess"); 
		 }
		 
		logger.info("a===" + resp);

		logger.info("Method : addsalenew ends");

		return resp;
	}

	public String saveAllMultiImages(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllMultiImages starts");
		String imageName1 = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}

	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-through-ajax")
	public @ResponseBody Object getAllsalesOrder(@RequestParam String pageno,@RequestParam String fDate,@RequestParam String tDate, HttpSession session) {

		logger.info("Method :getAllsalesOrder starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getAllsalesOrder?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno + "&userId=" + userId + "&fDate=" + fDate + "&tDate=" + tDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllsalesOrder ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-timeline")
	public @ResponseBody Object getSalesOrderTimeline(@RequestParam String id, HttpSession session) {
		logger.info("Method :getSalesOrderTimeline starts");
		
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getSalesOrderTimeline?org=" + orgName
					+ "&orgDiv=" + orgDivision + "&id=" + id , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :getSalesOrderTimeline ends");
		return resp;
	}

	/*
	 * for editing using employee id
	 *
	 *
	 */
	@GetMapping(value = { "view-saleorder-edit-new" })
	public @ResponseBody List<SalesOrderNewModel> viewsalesOrdeerEdit(@RequestParam String id,@RequestParam String invoiceId, HttpSession session) {
		logger.info("Method : viewsalesOrdeerEdit starts");
		logger.info(id);
		List<SalesOrderNewModel> productList = new ArrayList<SalesOrderNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();

		if (id != null && id != "") {
			try {
				SalesOrderNewModel[] salesOrderNewModel = restTemplate
						.getForObject(env.getSalesUrl() + "viewsalesOrdeerEdit?id=" + id + "&invoiceId=" + invoiceId, SalesOrderNewModel[].class);
				productList = Arrays.asList(salesOrderNewModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (SalesOrderNewModel m : salesOrderNewModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
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
						int i = 0;
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								System.out.println("Extension------------>" + Arrays.toString(extension));
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa-solid fa-file-excel custom-file-icon\" title= " + m.getFileName()
												+ "></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("csv")) {
										String docPath = "<i class=\"fa-solid fa-file-csv custom-file-icon\" title=" + m.getFileName() + "></i>";
										
										m.setAction(docPath);
									}
									if (extension[1].equals("pdf")) {
										String docPath = " <i class=\"fa-solid fa-file-pdf custom-file-icon\"   title=" + m.getFileName()
												+ " ;></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("doc") || extension[1].equals("dox")
											|| extension[1].equals("docx")) {
										String docPath = " <i class=\"fa-solid fa-file-word custom-file-icon\" aria-hidden=\"true\"  title="
												+ m.getFileName() + "></i> ";
										m.setAction(docPath);
									}
									if (extension[1].equals("png") || extension[1].equals("jpg")
											|| extension[1].equals("jpeg")) {
										String docPath = " <i class=\"fa-solid fa-file-image custom-file-icon\" aria-hidden=\"true\" title="
												+ m.getFileName() + "></i>";
										m.setAction(docPath);
									}
								} else {
									m.setAction("");
								}
							} else {
								m.setAction("");
							}
							
							if(m.getFileName() != null && m.getFileName() != "") {
								m.setAction(
										"<a style='margin-left: 10px' class='example-image-link' href=\"/document/document/"
												+ m.getFileName() + "\" target=\"_blank\">" + m.getAction() + "</a>"
												+ "<div id=\"imageName_"+i+"\" class=\"imageName\" style='margin-left: 2px;'>"+m.getFileName()+"</div>"
														+ "<span><i class=\"ti-close red close_sec1 deleteFileDoc\" onclick='openDeleteConfirm("+i+")'></i></span>");
							} else {
								m.setAction("");
							}
							
							
							i = i + 1;
							logger.info("m.setAction" + m);

						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewsalesOrdeerEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	// packing
/*	@GetMapping(value = { "view-saleorder-for-packing" })
	public @ResponseBody List<SalesOrderNewModel> viewsalesOrderForPacking(@RequestParam String id, String poidd,
			HttpSession session) {
		logger.info("Method : viewsalesOrderForPacking starts");
		List<SalesOrderNewModel> productList = new ArrayList<SalesOrderNewModel>();
		if (id != null && id != "") {
			try {
				SalesOrderNewModel[] salesOrderNewModel = restTemplate.getForObject(
						env.getSalesUrl() + "viewsalesOrderForPacking?id=" + id + "&poidd=" + poidd,
						SalesOrderNewModel[].class);
				productList = Arrays.asList(salesOrderNewModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (SalesOrderNewModel m : salesOrderNewModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
					if (m.getOrderReceiveDate() != null && m.getOrderReceiveDate() != "") {
						m.setOrderReceiveDate(DateFormatter.dateFormat(m.getOrderReceiveDate(), dateFormat));
					}
					if (m.getExpectedShipmentDate() != null && m.getExpectedShipmentDate() != "") {
						m.setExpectedShipmentDate(DateFormatter.dateFormat(m.getExpectedShipmentDate(), dateFormat));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewsalesOrdeerEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}*/

	/*
	 * * Delete
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-saleorder-delete")
	public @ResponseBody JsonResponse<Object> deletesalesOrder(@RequestBody SalesOrderNewModel salesOrderNewModel,
			HttpSession session) {
		logger.info("Method : deletesalesOrder starts");
		logger.info("SalesOrderNewModel" + salesOrderNewModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			salesOrderNewModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "deletesalesOrder", salesOrderNewModel,
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
		logger.info("Method : deletesalesOrder Ends");
		return resp;
	}

	// Add New Customer
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleorder-add-customer")
	public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {

		logger.info("Method : addAccountCustomer starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web AccountModel ======================" + customerNewModel);
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

			logger.info("created by id-------------------------------" + userId);

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

	@SuppressWarnings("unchecked")

	@PostMapping("view-saleorder-add-cust-billingaddress")
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

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("view-saleorder-add-cust-shippingaddress") public @ResponseBody
	 * JsonResponse<Object> addshippingaddress(
	 * 
	 * @RequestBody CustomerNewModel customerNewModel, HttpSession session) {
	 * logger.info("Method : addshippingaddress starts"); String userId = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID");
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * customerNewModel.setCreatedBy(userId);
	 * 
	 * logger.info("ADDDDATA" + customerNewModel);
	 * 
	 * 
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * 
	 * 
	 * 
	 * try {
	 * 
	 * resp = restTemplate.postForObject(env.getSalesUrl() + "add-shippingaddress",
	 * customerNewModel, JsonResponse.class); } catch (RestClientException e) {
	 * e.printStackTrace(); }
	 * 
	 * if (resp.getMessage() != "" && resp.getMessage() != null) {
	 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
	 * resp.setMessage("Success"); } logger.info("ADDDDDDD" + resp);
	 * logger.info("Method :addshippingaddress ends"); return resp; }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-get-address")
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

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleorder-get-salesperson-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getSalesPersonAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getSalesPersonAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesPersonListByAutoSearch?id=" + searchValue
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
		logger.info("Method : getSalesPersonAutoSearchList ends" + res);
		return res;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("view-saleorder-add-salesperson")
	public @ResponseBody JsonResponse<Object> addSalesPerson(@RequestBody QuotationNewModel quotationNewModel,
			HttpSession session) {
		logger.info("Method : addSalesPerson starts");
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
			e.printStackTrace();
		}
		quotationNewModel.setCreatedBy(userId);
		quotationNewModel.setOrganization(organization);
		quotationNewModel.setOrgDivision(orgDivision);

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
	@PostMapping(value = { "view-saleorder-get-tcs-list" })
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

	@SuppressWarnings("unchecked")

	@PostMapping("view-saleorder-add-tcs")
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

	@GetMapping(value = { "view-saleorder-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getSOInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSOInsertedId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getSOInsertedId ends");
		logger.info("assssssssssssssssssss" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleorder-get-customer-listt" })
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
	@GetMapping(value = { "view-saleorder-salesPoList" })
	public @ResponseBody JsonResponse<Object> getSalesPoListt(@RequestParam String id) {
		logger.info("Method : getSalesPoListt starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesPoListt?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getSalesPoListt ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-po-wise")
	public @ResponseBody List<SalesOrderNewModel> viewsalesOrderPoWise(HttpSession session, @RequestParam String id) {

		logger.info("Method :viewsalesOrderPoWise starts");
		JsonResponse<List<SalesOrderNewModel>> resp = new JsonResponse<List<SalesOrderNewModel>>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesOrderPoWise?org=" + orgName + "&orgDiv="
					+ orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<SalesOrderNewModel> soModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<SalesOrderNewModel>>() {
				});
		resp.setBody(soModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewsalesOrderPoWise ends");
		return resp.getBody();
	}

	// Block Order

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-blockeOrder-th-ajax")
	public @ResponseBody JsonResponse<List<SalesOrderNewModel>> blockSaleOrderItem(HttpSession session,
			@RequestParam String blockeOrder, String salesOrder, String sku) {

		logger.info("Method : blockSaleOrderItem starts");
		JsonResponse<List<SalesOrderNewModel>> response = new JsonResponse<List<SalesOrderNewModel>>();
		try {
			response = restTemplate.getForObject(env.getSalesUrl() + "blockSaleOrderItem?blockeOrder=" + blockeOrder
					+ "&salesOrder=" + salesOrder + "&sku=" + sku, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("response=====" + response);
		logger.info("Method : blockSaleOrderItem ends");
		return response;
	}

	// approve
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-approve-th-ajax")
	public @ResponseBody JsonResponse<List<SalesOrderNewModel>> approveSaleOrder(HttpSession session,
			@RequestParam String approveStatus, String salesOrder, String pendingQut) {
		logger.info("Method : approveSaleOrder starts");
		JsonResponse<List<SalesOrderNewModel>> response = new JsonResponse<List<SalesOrderNewModel>>();
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
			response = restTemplate.getForObject(env.getSalesUrl() + "approveSaleOrder?approveStatus=" + approveStatus
					+ "&salesOrder=" + salesOrder + "&pendingQut=" + pendingQut+"&userId="+userId+"&org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("response=====" + response);
		logger.info("Method : approveSaleOrder ends");
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleorder-add-cust-shippingaddress")
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

	@GetMapping("view-saleorder-shipping-address")
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

	@GetMapping("view-saleorder-shipping-dataedit")
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
	@PostMapping("/view-saleorder-adds-customer")
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
	@GetMapping("view-saleorder-shippingdetails")
	public @ResponseBody Object viewShippingDetails(HttpSession session, @RequestParam String customerIdd) {
		logger.info("Method :viewShippingDetails starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewShippingDetails?customerIdd=" + customerIdd
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewShippingDetails ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleorder-save-shipping-address")
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

	@GetMapping("view-saleorder-address-delete")
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
	@GetMapping("view-saleorder-edit-address")
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

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-saleorder-get-sku-by-product" })
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
	@PostMapping(value = { "view-saleorder-get-product-details" })
	public @ResponseBody JsonResponse<ProductMasterModel> getProductDetails(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getProductDetails starts");

		JsonResponse<ProductMasterModel> res = new JsonResponse<ProductMasterModel>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getProductDetailsById?id=" + tCountry,
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

		logger.info("Method : getProductDetails ends" + res);
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-saleorder-products-delete")
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
	@PostMapping(value = { "view-saleorder-get-sku-details" })
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
	@GetMapping("view-saleorder-deletesku")
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

	@PostMapping("view-saleorder-delete-fileproduct")
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

	@PostMapping("/view-saleorder-upload-fileprofuct")
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

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/view-saleorder-get-total-list") public @ResponseBody
	 * JsonResponse<List<ProductCategoryModel>>
	 * getProductCategoryDataListModal(HttpSession session) {
	 * logger.info("Method : getProductCategoryDataListModal starts");
	 * JsonResponse<List<ProductCategoryModel>> resp = new
	 * JsonResponse<List<ProductCategoryModel>>(); try { resp =
	 * restTemplate.getForObject(env.getInventoryUrl() +
	 * "getProductCategoryDataListModal", JsonResponse.class); } catch
	 * (RestClientException e) { e.printStackTrace(); }
	 * 
	 * String message = resp.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { resp.setMessage("Success"); }
	 * logger.info("Method : getProductCategoryDataListModal starts"); return resp;
	 * }
	 */

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleorder-products-save")
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

	/*
	 * @SuppressWarnings({ "unchecked" })
	 * 
	 * @PostMapping("/view-saleorder-save-sku-dtls") public @ResponseBody
	 * JsonResponse<Object> saveProductDetails(@RequestBody ProductDetailsModel
	 * product, HttpSession session) {
	 * logger.info("Method : saveProductDetails starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * 
	 * String userId = ""; String orgName = ""; String orgDivision = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * product.setCreatedBy(userId); product.setOrganizationName(orgName);
	 * product.setOrganizationDivision(orgDivision); MultipartFile inputFile =
	 * (MultipartFile) session.getAttribute("itemImageFile"); byte[] bytes; String
	 * imageName = null;
	 * 
	 * if (inputFile != null) { try { bytes = inputFile.getBytes(); String[]
	 * fileType = inputFile.getContentType().split("/"); imageName =
	 * saveAllImage(bytes, fileType[1]); product.setItemimage(imageName); } catch
	 * (IOException e1) { e1.printStackTrace(); } } try { resp =
	 * restTemplate.postForObject(env.getMasterUrl() + "saveProductDetails",
	 * product, JsonResponse.class); } catch (RestClientException e) {
	 * e.printStackTrace(); }
	 * 
	 * String message = resp.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { resp.setMessage("Success"); }
	 * 
	 * logger.info("Method : saveProductDetails starts"); return resp; }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-saleorder-get-sku-listing" })
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
	@PostMapping("view-saleorder-item-get-product-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryListModal(
			@RequestBody String yearDtls, HttpSession session) {
		logger.info("Method : getProductCategoryListModal starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		// logger.info(yearDtls);
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getProductCategoryListModalQuot",
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

	@PostMapping("view-saleorder-item-image-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFilee(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFilee controller function 'post-mapping' starts");
		logger.info("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("itemImageFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFilee controller function 'post-mapping' ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleorder-get-project-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getProjectAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getProjectAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getProjectAutoSearchList?id=" + searchValue + "&org="
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
		logger.info("Method : getProjectAutoSearchList ends" + res);
		return res;
	}

	// Search

	@SuppressWarnings("unchecked")

	@GetMapping("view-saleorder-search")
	public @ResponseBody Object soDataViewSearch(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :soDataViewSearch starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-soDataViewSearch?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&searchValue=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :soDataViewSearch ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-get-po")
	public @ResponseBody JsonResponse<Object> getPoOnSelectionCustomer(Model model, HttpSession session,
			@RequestParam String custId) {

		logger.info("Method : getPoOnSelectionCustomer starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-po?custId=" + custId + "&orgName=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getPoOnSelectionCustomer ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-get-purchase-order-details")
	public @ResponseBody JsonResponse<Object> getPoDetails(HttpSession session, @RequestParam String poId) {

		logger.info("Method : getPoDetails starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-po-details?poId=" + poId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			// Convert response body to JSONObject
			JSONObject responseBody = new JSONObject(resp.getBody().toString());
			JSONArray poData = responseBody.getJSONArray("poData");

			if (poData.length() > 0) {
				JSONObject firstPo = poData.getJSONObject(0);
				if(!firstPo.isNull("documentDetails")) {
					JSONArray documentDetails = firstPo.getJSONArray("documentDetails");

					for (int i = 0; i < documentDetails.length(); i++) {
						JSONObject document = documentDetails.getJSONObject(i);
						String fileName = document.getString("fileName");

						String[] extension = fileName.split("\\.");
						if (extension.length == 2) {
							String fileExtension = extension[1].toLowerCase();
							String docPath = "";

							if (Arrays.asList("xls", "xlsx").contains(fileExtension)) {
								docPath = "<i class=\"fa-solid fa-file-excel custom-file-icon\" title=\"" + fileName + "\"></i> ";
							} else if ("pdf".equals(fileExtension)) {
								docPath = "<i class=\"fa-solid fa-file-pdf custom-file-icon\" title=\"" + fileName + "\"></i> ";
							} else if (Arrays.asList("doc", "docx").contains(fileExtension)) {
								docPath = "<i class=\"fa-solid fa-file-word custom-file-icon\" title=\"" + fileName + "\"></i> ";
							} else if (Arrays.asList("png", "jpg", "jpeg").contains(fileExtension)) {
								docPath = "<i class=\"fa-solid fa-file-image custom-file-icon\" title=\"" + fileName + "\"></i> ";
							}

							document.put("action",
									"<a style='margin-left: 10px' class='example-image-link' href=\"/document/document/"
											+ fileName + "\" target=\"_blank\">" + docPath + "</a>"
											+ "<div id=\"imageName_"+i+"\" class=\"imageName\" style='margin-left: 2px;'>"+fileName+"</div>"
													+ "<span><i class=\"ti-close red close_sec1 deleteFileDoc\" onclick='openDeleteConfirm("+i+")'></i></span>");
						} else {
							document.put("action", "");
						}
					}
				}
				
				responseBody.put("poData", poData);
				resp.setBody(responseBody.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getPoDetails ends" + resp);
		return resp;
	}

	// Sales order PDF Download.

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-pdf-downloads")
	public void getSalesOrderPdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1,@RequestParam("poId") String encodedParam2) {
		logger.info("Method : getSalesOrderPdf starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String id = (new String(encodeByte1));
		
		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String poId = (new String(encodeByte2));
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("id got-------------" + id);
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getSalesOrderPdf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&poId=" + poId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<String, Object>();

		logger.info("id dataa  pdf -------------" + resp.getBody().toString());
		try {
			Map<String, Object> dataa = mapper.readValue(resp.getBody().toString(),
					new TypeReference<Map<String, Object>>() {
					});

			logger.info("id dataa dataa pdf -------------" + dataa);

			data.put("salesOrderNo", dataa.get("salesOrderNo"));
			data.put("ourRefNo", dataa.get("ourRefNo"));
			data.put("salesOrderDt", dataa.get("salesOrderDt"));
			data.put("customerPoNo", dataa.get("customerPoNo"));
			data.put("customerPoDt", dataa.get("customerPoDt"));
			data.put("totalAmount", dataa.get("totalAmount"));
			data.put("totalCharge", dataa.get("totalCharge"));
			data.put("totalDiscount", dataa.get("totalDiscount"));
			data.put("totalTaxableAmt", dataa.get("totalTaxableAmt"));
			data.put("cgst", dataa.get("cgst"));
			data.put("sgst", dataa.get("sgst"));
			data.put("igst", dataa.get("igst"));
			data.put("rate", dataa.get("rate"));
			data.put("igst_rate", dataa.get("igst_rate"));
			data.put("gst_rate", dataa.get("gst_rate"));
			data.put("gstType", dataa.get("gstType"));
			data.put("totalAmountMstr", dataa.get("totalAmountMstr"));
			data.put("customerName", dataa.get("customerName"));
			data.put("customerAddress", dataa.get("customerAddress"));
			data.put("customerShipAddress", dataa.get("customerShipAddress"));
			data.put("customerState", dataa.get("customerState"));
			data.put("customerGSTIN", dataa.get("customerGSTIN"));
			data.put("customerPAN", dataa.get("customerPAN"));
			data.put("deliveryMode", dataa.get("deliveryMode"));
			data.put("deliveryTerm", dataa.get("deliveryTerm"));
			data.put("list", dataa.get("list"));
			data.put("totalAmtInWords", dataa.get("totalAmtInWords"));
			data.put("totalTaxInWords", dataa.get("totalTaxInWords"));
			data.put("logo", dataa.get("logo"));
			data.put("quotationNo", dataa.get("quotationNo"));
			data.put("paymentTerm", dataa.get("paymentTerm"));
			data.put("contactPerson", dataa.get("contactPerson"));
			data.put("ac_holder", dataa.get("ac_holder"));
			data.put("ac_no", dataa.get("ac_no"));
			data.put("bank_name", dataa.get("bank_name"));
			data.put("ifsc_code", dataa.get("ifsc_code"));
			data.put("branc_name", dataa.get("branc_name"));
			data.put("custShipGSTIN", dataa.get("custShipGSTIN"));

		} catch (IOException e2) {
			e2.printStackTrace();
		}
		System.out.println("dataa.get(\"list\")>>" + data.get("list"));

		/*
		 * String logo = (String) session.getAttribute("ORGANIZATION_LOGO"); URL getUrl
		 * = null; try { getUrl = new URL(logo); } catch (MalformedURLException e2) { //
		 * TODO Auto-generated catch block e2.printStackTrace(); } String encodedLogoUrl
		 * = DownloadDocumentUrl.downloadDocumentUrl(getUrl); data.put("logo",
		 * "data:image/png;base64," + encodedLogoUrl);
		 */
		data.put("orgName", orgDivision);
		System.err.println("data exit====" + data);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=Sales-Order.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("sales/salesorder-pdf.html", data);
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
		logger.info("Method : getSalesOrderPdf ends");
	}
	
//
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-saleorder-stateListDataSO" })
	public @ResponseBody JsonResponse<Object> stateListDataSO(@RequestParam String id) {
		logger.info("Method : stateListDataSO starts" + id);
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
		logger.info("Method : stateListDataSO ends");
		return res;
	}
//
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleorder-add-shipping-address")
	public @ResponseBody JsonResponse<Object> addShippingAddressSO(@RequestBody DropDownModel data,
			HttpSession session) {
		logger.info("Method : addShippingAddressSO starts");
		
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
		data.setCreatedBy(userId);
		data.setOrgName(organization);
		data.setOrgDivision(orgDivision);
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			
			resp = restTemplate.postForObject(env.getSalesUrl() + "rest-addShippingAddressSO", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method :addShippingAddressSO ends");
		return resp; 
	}	
//
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleorder-add-invoice") 
	public @ResponseBody JsonResponse<Object> addsaleInvoice(HttpSession session,
			@RequestBody List<SalesInvoiceNewModel> salesInvoiceNewModel) {
		logger.info("Method : addsaleInvoice starts");
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
			
			if (a.getExpectedShipmentDate1() != null && a.getExpectedShipmentDate1() != "") {
				a.setExpectedShipmentDate1(DateFormatter.inputDateFormat(a.getExpectedShipmentDate1(), dateFormat));
			}
		}
		for (SalesInvoiceNewModel m : salesInvoiceNewModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addsaleInvoice", salesInvoiceNewModel,
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
		logger.info("Method : addsaleInvoice ends");
		return resp;
	}
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-saleorder-get-insertedid-data" })
	public @ResponseBody JsonResponse<Object> getDeliveryChallanInsertedIdSO(@RequestParam String type) {
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
		logger.info("Method : getDeliveryChallanInsertedIdSO ends"+res);

		return res;
	}
//
	// download 
	/*	@SuppressWarnings("unchecked")
		@GetMapping("view-saleorder-invoice-Pdf")
		public void saleorderInvoicePdf(HttpServletResponse response, Model model, HttpSession session,
				@RequestParam("id") String encodedParam1) {
			logger.info("Method : saleorderInvoicePdf starts");
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
			
			JsonResponse<Object> resp = new JsonResponse<Object>();
			ObjectMapper objectMapper = new ObjectMapper();

			Map<String, Object> data = new HashMap<String, Object>();
			JsonNode pdfHorlicksdataNode = null;
				    try {		   
				    	resp = restTemplate.getForObject(env.getSalesUrl() + "rest-saleorderInvoicePdf-data?id=" + id + "&orgName="
								+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				    	System.err.println("resp sales invoice======"+resp.getBody().toString());
				        JsonNode rootNode = objectMapper.readTree(resp.getBody().toString());
				        pdfHorlicksdataNode = rootNode.path("invoicePdfData");
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
				    logger.info("pdfHorlicksdataNode====" + pdfHorlicksdataNode);
				    
				    data.put("allData", objectMapper.convertValue(pdfHorlicksdataNode, Map.class));
							
					System.err.println("data for pdf===="+data);
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=Sales-Invoice-PDF.pdf");
			File file;
			byte[] fileData = null;
			try {
				
					file = pdfGeneratorUtil.createPdf("sales/sales-invoiceSO-pdf.html", data);
							
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
			logger.info("Method : saleorderInvoicePdf ends");
		}*/
	
	//
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-invoice-Pdf")
	public void saleorderInvoicePdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1,@RequestParam("poId") String encodedParam2) {
		logger.info("Method : saleorderInvoicePdf starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String id = (new String(encodeByte1));
		
		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String poId = (new String(encodeByte2));
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("id got-------------" + id);
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-saleorderInvoicePdf-data?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&poId=" + poId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<String, Object>();

		logger.info("id dataa  pdf -------------" + resp.getBody().toString());
		try {
			Map<String, Object> dataa = mapper.readValue(resp.getBody().toString(),
					new TypeReference<Map<String, Object>>() {
					});

			logger.info("id dataa dataa pdf -------------" + dataa);

			data.put("salesOrderNo", dataa.get("salesOrderNo"));
			data.put("salesOrderDt", dataa.get("salesOrderDt"));
			data.put("customerPoNo", dataa.get("customerPoNo"));
			data.put("customerPoDt", dataa.get("customerPoDt"));
			data.put("totalAmount", dataa.get("totalAmount"));
			data.put("totalCharge", dataa.get("totalCharge"));
			data.put("totalDiscount", dataa.get("totalDiscount"));
			data.put("totalTaxableAmt", dataa.get("totalTaxableAmt"));
			data.put("cgst", dataa.get("cgst"));
			data.put("sgst", dataa.get("sgst"));
			data.put("igst", dataa.get("igst"));
			data.put("rate", dataa.get("rate"));
			data.put("igst_rate", dataa.get("igst_rate"));
			data.put("gst_rate", dataa.get("gst_rate"));
			data.put("gstType", dataa.get("gstType"));
			data.put("totalAmountMstr", dataa.get("totalAmountMstr"));
			data.put("customerName", dataa.get("customerName"));
			data.put("customerAddress", dataa.get("customerAddress"));
			data.put("customerShipAddress", dataa.get("customerShipAddress"));
			data.put("customerState", dataa.get("customerState"));
			data.put("customerGSTIN", dataa.get("customerGSTIN"));
			data.put("customerPAN", dataa.get("customerPAN"));
			data.put("deliveryMode", dataa.get("deliveryMode"));
			data.put("deliveryTerm", dataa.get("deliveryTerm"));
			data.put("list", dataa.get("list"));
			data.put("totalAmtInWords", dataa.get("totalAmtInWords"));
			data.put("totalTaxInWords", dataa.get("totalTaxInWords"));
			data.put("logo", dataa.get("logo"));
			data.put("quotationNo", dataa.get("quotationNo"));
			data.put("paymentTerm", dataa.get("paymentTerm"));
			data.put("contactPerson", dataa.get("contactPerson"));
			data.put("ac_holder", dataa.get("ac_holder"));
			data.put("ac_no", dataa.get("ac_no"));
			data.put("bank_name", dataa.get("bank_name"));
			data.put("ifsc_code", dataa.get("ifsc_code"));
			data.put("branc_name", dataa.get("branc_name"));
			
			data.put("invoiceNo", dataa.get("invoiceNo"));
			data.put("orgPan", dataa.get("orgPan"));
			data.put("orgWebsite", dataa.get("orgWebsite"));
			data.put("orgCin", dataa.get("orgCin"));
			data.put("orgEmail", dataa.get("orgEmail"));
			data.put("orgGstIn", dataa.get("orgGstIn"));
			data.put("orgPhn", dataa.get("orgPhn"));
			data.put("invoiceDate", dataa.get("invoiceDate"));
			data.put("custShipGSTIN", dataa.get("custShipGSTIN"));
			data.put("igstTotal", dataa.get("igstTotal"));
			data.put("sgstTotal", dataa.get("sgstTotal"));
			data.put("cgstTotal", dataa.get("cgstTotal"));

		} catch (IOException e2) {
			e2.printStackTrace();
		}
		System.out.println("dataa.get(\"list\")>>" + data.get("list"));

		data.put("orgName", orgDivision);
		System.err.println("data exit====" + data);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=Sales-Order.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("sales/sales-invoiceSO-pdf.html", data);
//			file = pdfGeneratorUtil.createPdf("create-pdf/formPdf.html", data);
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
		logger.info("Method : saleorderInvoicePdf ends");
	}
	
//
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-filter-data")
	public @ResponseBody Object getAllsalesOrderFilterData(@RequestParam String pageno,@RequestParam String fDate,@RequestParam String tDate, HttpSession session) {

		logger.info("Method :getAllsalesOrderFilterData starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getAllsalesOrder?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno + "&userId=" + userId + "&fDate=" + fDate + "&tDate=" + tDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllsalesOrderFilterData ends");


		return resp;
	}
}

