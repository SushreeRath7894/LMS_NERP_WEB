package nirmalya.aathithya.webmodule.purchase.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.fasterxml.jackson.databind.JsonNode;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.QuotationDetailsModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller
@RequestMapping(value = { "purchase/" })
public class PurchaseOrderController {
	Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PurchaseOrderController purchaseOrderController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/manage-purchase-order" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : poDetails starts");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {

			DropDownModel[] department = restTemplate.getForObject(env.getPurchaseUrl()+
					"ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
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
			DropDownModel[] brand = restTemplate.getForObject(
					env.getMasterUrl() + "getBrandListForProduct?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);
			System.err.println("DATAbrandList" + brandList);

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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getPaymentterm",
					DropDownModel[].class);
			List<DropDownModel> PaytermList = Arrays.asList(dropDownModel);
			model.addAttribute("PaytermList", PaytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getCarrierList",
					DropDownModel[].class);
			List<DropDownModel> CarrierLists = Arrays.asList(dropDownModel);
			model.addAttribute("CarrierLists", CarrierLists);
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
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorCategory",
					DropDownModel[].class);
			List<DropDownModel> CategoryList = Arrays.asList(Collection);

			model.addAttribute("CategoryList", CategoryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getSalutationLists",
					DropDownModel[].class);
			List<DropDownModel> SalutationLists = Arrays.asList(Collection);

			model.addAttribute("SalutationLists", SalutationLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getPaymentTermsLists",
					DropDownModel[].class);
			List<DropDownModel> PaymentTermList = Arrays.asList(Collection);

			model.addAttribute("PaymentTermList", PaymentTermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : poDetails ends");
		return "purchase/manage-purchase-order";
	}

	/*
	 * vendor autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-purchase-order-get-vendor-list" })
	public @ResponseBody JsonResponse<PurchaseOrderModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue,String type, BindingResult result, HttpSession session) {
		logger.info("Method : getVendorAutoSearchList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<PurchaseOrderModel> res = new JsonResponse<PurchaseOrderModel>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorAutoSearchListStore?id=" + searchValue 
					+ "&type=" + type + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getVendorAutoSearchList ends");
		return res;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "manage-purchase-order-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getPoInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getPoInsertedId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getPoInsertedId ends");

		return res;
	}

	// get Product Category Data List Modal
	@SuppressWarnings("unchecked")
	@PostMapping("manage-purchase-order-item-get-product-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryListModalPO(
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
		logger.info("Method : getProductCategoryListModalPO starts");
		return resp;
	}

	/*
	 * Item auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-purchase-order-get-item-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchNewListForPO(Model model,
			@RequestBody String searchValue,String type, HttpSession session, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchNewListForPO starts");
		logger.info("QuotationNewModel" + searchValue);
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "getItemQuotationAutoSearchNewListForPO?id=" + searchValue
					+ "&type=" + type + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
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
		logger.info("Method : getItemQuotationAutoSearchNewListForPO ends");
		return res;
	}

	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "manage-purchase-order-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
			@RequestBody String index, BindingResult result,HttpSession session) {
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
			res = restTemplate.getForObject(env.getSalesUrl() + "getProductsNByCat?id=" + indexValue + "&org=" + organization + "&orgDiv=" + orgDivision,
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

		logger.info("Method : getProductsByCatInvoice ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-purchase-order-get-tcs-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getTCSAutoSearchList1(Model model,
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

	@PostMapping("manage-purchase-order-add-tcs")
	public @ResponseBody JsonResponse<Object> addTcs(@RequestBody QuotationNewModel quotationNewModel,
			HttpSession session) {
		logger.info("Method : addTcs starts");
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		quotationNewModel.setCreatedBy(userId);

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

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("manage-purchase-order-add")
	public @ResponseBody JsonResponse<Object> addPurchase(HttpSession session,
			@RequestBody List<PurchaseOrderModel> purchaseOrderModel,Model model) {
		logger.info("Method : addPurchase starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<PurchaseOrderModel> documentList = new ArrayList<PurchaseOrderModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
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
	
		for (PurchaseOrderModel m : purchaseOrderModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}

		for (InventoryVendorDocumentModel a : purchaseOrderModel.get(0).getDocumentList()) {

			if (a.getIsEditDoc() == null && a.getIsEditDoc() == "") {
				a.setDocView(a.getDocView());
			} else {
				if (a.getDocView() != null && a.getDocView() != "") {
					// String[] extension = a.getFileName().split("\\.");
					String extension = a.getDocType();
					// int lastindex = extension.length - 1;
					/*
					 * try { byte[] bytes = Base64.getDecoder().decode(a.getIsEditDoc());
					 * logger.info("bytes" + a.getIsEditDoc()); String imageName =
					 * saveAllMultiImages(bytes, extension); a.setDocView(imageName);
					 * 
					 * } catch (Exception e) { e.printStackTrace();
					 * 
					 * }
					 */

					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, extension);
							a.setDocView(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}

				}
			}
		}
System.out.println("addPurchase>>>>>>>>>." + purchaseOrderModel);
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addPurchase", purchaseOrderModel,
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

		logger.info("Method : addPurchase ends");

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
	@GetMapping("manage-purchase-order-through-ajax")
	public @ResponseBody List<PurchaseOrderModel> viewPurchaseOrder(HttpSession session) {

		logger.info("Method :viewPurchaseOrder startsssssssssssssssssssssss");
		JsonResponse<List<PurchaseOrderModel>> resp = new JsonResponse<List<PurchaseOrderModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-viewPurchaseOrder?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<PurchaseOrderModel> purchaseOrderModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<PurchaseOrderModel>>() {
				});

		for (PurchaseOrderModel a : purchaseOrderModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

		}

		resp.setBody(purchaseOrderModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewPurchaseOrder ends");

		return resp.getBody();
	}

	/*
	 * edit Po
	 */

	@GetMapping(value = { "manage-purchase-order-edit-new" })
	public @ResponseBody List<PurchaseOrderModel> viewPoEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPoEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				PurchaseOrderModel[] purchaseOrderModel = restTemplate.getForObject(
						env.getPurchaseUrl() + "viewPoEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseOrderModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseOrderModel m : purchaseOrderModel) {
					// m.setQuantitynew(m.getQuantity());
					System.err.println("DATE" + m.getGrnId());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

					}
					if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
						m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

					}
					if (m.getGrnDate() != null && m.getGrnDate() != "") {
						m.setGrnDate(DateFormatter.dateFormat(m.getGrnDate(), dateFormat));

					}

				}
				/*
				 * if (productList != null) { documentList =
				 * productList.get(0).getDocumentList(); if (documentList != null) { for
				 * (InventoryVendorDocumentModel m : documentList) { if (m.getFileName() != null
				 * && m.getFileName() != "") {
				 * 
				 * String[] extension = m.getFileName().split("\\."); if (extension.length == 2)
				 * { if (extension[1].equals("xls") || extension[1].equals("xlsx")) {
				 * 
				 * String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " +
				 * m.getFileName() + "></i> ";
				 * 
				 * m.setAction(docPath); } if (extension[1].equals("pdf")) { String docPath =
				 * " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName() +
				 * " ;></i> ";
				 * 
				 * m.setAction(docPath); } if (extension[1].equals("doc") ||
				 * extension[1].equals("dox") || extension[1].equals("docx")) { String docPath =
				 * " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title=" +
				 * m.getFileName() + "></i> "; m.setAction(docPath); } if
				 * (extension[1].equals("png") || extension[1].equals("jpg") ||
				 * extension[1].equals("jpeg")) { String docPath =
				 * " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title=" +
				 * m.getFileName() + "></i>  "; m.setAction(docPath); } } else {
				 * m.setAction("N/A"); } } else { m.setAction("N/A"); }
				 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
				 * "\" target=\"_blank\" >" + m.getAction() + "</a>"); logger.info("m.setAction"
				 * + m);
				 * 
				 * } } }
				 */
				
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getDocView() != null && m.getDocView() != "") {
								
								String docPath = "/document/document/" + m.getDocView();

								m.setDociURL(docPath);
								
								// Set Doc Type.
								String[] extension = m.getDocView().split("\\.");
								m.setDocType(extension[1]);
								
							} else {
								m.setDociURL(null);
								m.setDocType(null);
							}
							/*
							 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
							 * "\" target=\"_blank\" >" + m.getAction() + "</a>");
							 */
							logger.info("m.setDociURL" + m.getDociURL());

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewPoEdit ends");
		System.err.println("DATA" + productList);
		return productList;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "manage-purchase-order-delete")
	public @ResponseBody JsonResponse<Object> deletePo(@RequestBody PurchaseOrderModel purchaseOrderModel,
			HttpSession session) {
		logger.info("Method : deletePo starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			purchaseOrderModel.setCreatedBy(userId);
			purchaseOrderModel.setOrganization(orgName);
			purchaseOrderModel.setOrgDivision(orgDivision);
		} catch (Exception e) {

		}

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "deletePo", purchaseOrderModel,
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

		logger.info("Method : deletePo Ends");
		return resp;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "manage-purchase-order-stateList" })
	public @ResponseBody JsonResponse<Object> getstateVendorList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getstateVendorList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-purchase-order-get-address")
	public @ResponseBody JsonResponse<VendorNewModel> getVendorAddress(@RequestParam String id, HttpSession session) {

		logger.info("Method : getCustomerAddress starts");

		JsonResponse<VendorNewModel> jsonResponse = new JsonResponse<VendorNewModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorAddressAddressById?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		VendorNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<VendorNewModel>() {
		});

		jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : getVendorAddress ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("manage-purchase-order-add-cust-billingaddress")
	public @ResponseBody JsonResponse<Object> addbillingaddress(@RequestBody VendorNewModel vendorNewModel,
			HttpSession session) {
		logger.info("Method : addbillingaddress starts");
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		vendorNewModel.setCreatedBy(userId);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addVendorBillingaddres", vendorNewModel,
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

	@SuppressWarnings("unchecked")

	@PostMapping("manage-purchase-order-add-cust-shippingaddress")
	public @ResponseBody JsonResponse<Object> addshippingaddress(@RequestBody VendorNewModel vendorNewModel,
			HttpSession session) {
		logger.info("Method : addshippingaddress starts");
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		vendorNewModel.setCreatedBy(userId);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addvendorShippingaddress", vendorNewModel,
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

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-purchase-order-adds")
	public @ResponseBody JsonResponse<Object> addVendor(@RequestBody VendorNewModel vendorNewModel,
			HttpSession session) {

		logger.info("Method : addVendor starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
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

			vendorNewModel.setCreatedBy(userId);
			vendorNewModel.setOrgName(orgName);
			vendorNewModel.setOrgDivision(orgDivision);

			logger.info("VENDORADD" + vendorNewModel);

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addVendor", vendorNewModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addVendor ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-purchase-order-pdf-downloads")
	public void getInvoicePdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("invId") String encodedParam1) {

		logger.info("Method : getPoPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String invIdd = (new String(encodeByte3));

		List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
		try {
			PurchaseOrderModel[] purchaseOrderModel = restTemplate.getForObject(env.getPurchaseUrl()
					+ "view-po-viewPdf?id=" + invIdd + "&org=" + orgName + "&orgDiv=" + orgDivision,
					PurchaseOrderModel[].class);
			productList = Arrays.asList(purchaseOrderModel);
			productList.forEach(s -> s.setSlNo(s.getSlNo()));
			int count = 0;
			for (PurchaseOrderModel m : purchaseOrderModel) {
				count++;
				m.setSlNo(count);
				String dateFormat = (String) session.getAttribute("DATEFORMAT");
				if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
				}
				if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
					m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String id = productList.get(0).getVendorId();
		JsonResponse<VendorNewModel> jsonResponse = new JsonResponse<VendorNewModel>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorAddressAddressById?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		VendorNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<VendorNewModel>() {
		});

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("po", productList);

		// String logo = "";
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
		data.put("buyer", reimModel);
		logger.info("DATA1" + productList);
		logger.info("DATA2" + reimModel);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=salesInvoice.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("purchase/po-pdf", data);
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

		logger.info("Method : getPoPdfDetails ends");
	}

	@GetMapping(value = { "/vendor-item" })
	public String vendorReport(Model model, HttpSession session) {
		logger.info("Method : vendorReport starts");

		logger.info("Method : vendorReport ends");
		return "purchase/manage-po-report";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-item-report-through-ajax")
	public @ResponseBody List<PurchaseOrderModel> viewPoReport(HttpSession session) {

		logger.info("Method :viewPoReport startsssssssssssssssssssssss");
		JsonResponse<List<PurchaseOrderModel>> resp = new JsonResponse<List<PurchaseOrderModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-viewPOreport?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<PurchaseOrderModel> purchaseOrderModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<PurchaseOrderModel>>() {
				});

		for (PurchaseOrderModel a : purchaseOrderModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

		}

		resp.setBody(purchaseOrderModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewPoReport ends");

		return resp.getBody();
	}
	/*
	 * @GetMapping(value = { "manage-purchase-order-get-GRNdata" })
	 * public @ResponseBody List<PurchaseOrderModel> getGRNdata(@RequestParam String
	 * id,String hsnCode, HttpSession session) {
	 * logger.info("Method : getGRNdata starts"); logger.info(id);
	 * 
	 * String orgName = ""; String orgDivision = ""; try {
	 * 
	 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * {
	 * 
	 * } List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
	 * List<InventoryVendorDocumentModel> documentList = new
	 * ArrayList<InventoryVendorDocumentModel>();
	 * 
	 * if (id != null && id != "") { try { PurchaseOrderModel[] purchaseOrderModel =
	 * restTemplate.getForObject( env.getPurchaseUrl() + "getGRNdata?id=" + id
	 * +"&hsnCode="+hsnCode+"&org=" + orgName + "&orgDiv=" + orgDivision,
	 * PurchaseOrderModel[].class);
	 * 
	 * PurchaseOrderModel[] purchaseOrderModel = restTemplate.getForObject(
	 * env.getPurchaseUrl() + "viewPoEdit?id=" + id + "&org=" + orgName + "&orgDiv="
	 * + orgDivision, PurchaseOrderModel[].class);
	 * 
	 * 
	 * productList = Arrays.asList(purchaseOrderModel); productList.forEach(s ->
	 * s.setSlNo(s.getSlNo())); int count = 0; for (PurchaseOrderModel m :
	 * purchaseOrderModel) { count++; m.setSlNo(count); String dateFormat = (String)
	 * session.getAttribute("DATEFORMAT");
	 * 
	 * if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
	 * m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));
	 * 
	 * } if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
	 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
	 * 
	 * } } if (productList != null) { documentList =
	 * productList.get(0).getDocumentList(); if (documentList != null) { for
	 * (InventoryVendorDocumentModel m : documentList) { if (m.getFileName() != null
	 * && m.getFileName() != "") { String[] extension =
	 * m.getFileName().split("\\."); if (extension.length == 2) { if
	 * (extension[1].equals("xls") || extension[1].equals("xlsx")) { String docPath
	 * = "<i class=\"fa fa-file-excel-o excel\" title= " + m.getFileName()+
	 * "></i> "; m.setAction(docPath); } if (extension[1].equals("pdf")) { String
	 * docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" +
	 * m.getFileName()+ " ;></i> "; m.setAction(docPath); } if
	 * (extension[1].equals("doc") || extension[1].equals("dox") ||
	 * extension[1].equals("docx")) { String docPath =
	 * " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title=" +
	 * m.getFileName() + "></i> "; m.setAction(docPath); } if
	 * (extension[1].equals("png") || extension[1].equals("jpg") ||
	 * extension[1].equals("jpeg")) { String docPath =
	 * " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title=" +
	 * m.getFileName() + "></i>  "; m.setAction(docPath); } } else {
	 * m.setAction("N/A"); } } else { m.setAction("N/A"); }
	 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
	 * "\" target=\"_blank\" >" + m.getAction() + "</a>");
	 * logger.info("m.setAction"+m); } } } } catch (Exception e) {
	 * e.printStackTrace(); } } logger.info("Method : getGRNdata ends");
	 * logger.info("edit@@@@@@@@" + productList); return productList; }
	 */

	@GetMapping(value = { "manage-purchase-order-get-GRNdata" })
	public @ResponseBody List<PurchaseOrderModel> getGRNdata(@RequestParam String id, String sku, HttpSession session) {
		logger.info("Method : getGRNdata starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			logger.info("IDD" + id);
			try {
				PurchaseOrderModel[] purchaseOrderModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "getGRNdata?id=" + id + "&sku=" + sku + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseOrderModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseOrderModel m : purchaseOrderModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

					}
					if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
						m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

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
		logger.info("Method : getGRNdata ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-purchase-order-getReferenceList" })
	public @ResponseBody JsonResponse<Object> getReferenceList(@RequestParam String id, String poId, HttpSession session) {
		logger.info("Method : getReferenceList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();

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
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "getReferenceListPO?id=" + id + "&poId=" + poId + "&org=" + orgName + "&orgDiv=" + orgDivision,
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
		logger.info("Method : getReferenceList ends");
		return res;
	}

	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("manage-purchase-order-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approvePorder(HttpSession session,
			@RequestParam String approveStatus, String poId) {

		logger.info("Method : approvePorder starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
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
		logger.info("orgName" + orgName);
		logger.info("orgDivision" + orgDivision);
		try {
			response = restTemplate.getForObject(env.getPurchaseUrl() + "approvePorder?approveStatus=" + approveStatus
					+ "&poId=" + poId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		logger.info("response=====" + response);
		logger.info("Method : approvePorder ends");
		return response;
	}

	/*
	 * // item against reference id
	 * 
	 * @GetMapping(value = { "manage-purchase-order-getReferenceItemDetails" })
	 * public @ResponseBody List<QuotationDetailsModel>
	 * getReferenceItemDetailsForPo(@RequestParam String id, HttpSession session) {
	 * logger.info("Method : getReferenceItemDetailsForPo starts");
	 * List<QuotationDetailsModel> productList = new
	 * ArrayList<QuotationDetailsModel>(); String orgName = ""; String orgDivision =
	 * ""; try {
	 * 
	 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { logger.error(e.getMessage()); } if (id != null && id != "") {
	 * 
	 * try { QuotationDetailsModel[] QuotationDetailsModel = restTemplate
	 * .getForObject( env.getPurchaseUrl() + "getReferenceItemDetails?id=" + id +
	 * "&org=" + orgName + "&orgDivision=" + orgDivision,
	 * QuotationDetailsModel[].class); productList =
	 * Arrays.asList(QuotationDetailsModel); productList.forEach(s ->
	 * s.setSlNo(s.getSlNo())); int count = 0; for (QuotationDetailsModel m :
	 * QuotationDetailsModel) { count++; m.setSlNo(count);
	 * 
	 * }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * logger.info("Method : getReferenceItemDetailsForPo ends"); return
	 * productList; }
	 */
	
	@SuppressWarnings("unchecked")

	@GetMapping("manage-purchase-order-getReferenceItemDetails")
	public @ResponseBody Object getReferenceItemDetailsPO(@RequestParam String id, HttpSession session) {

		logger.info("Method :getReferenceItemDetailsPO starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-getReferenceItemDetailsPO?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :getReferenceItemDetailsPO ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}
	// Upload po Data

	@PostMapping("manage-purchase-order-upload-file")
	public @ResponseBody JsonResponse<Object> uploadPoData(@RequestParam("file") MultipartFile purchaseOrder,
			HttpSession session) {
		logger.info("Method : uploadPoData controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(purchaseOrder.getInputStream());
			response.setMessage(purchaseOrder.getOriginalFilename());
			session.setAttribute("purchaseOrder", workbook);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("response########" + response);
		logger.info("Method : uploadPoData controller ' ends");
		return response;
	}
	// Save Upload po Data

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-purchase-order-save-excelData")
	public @ResponseBody JsonResponse<Object> addPOUploadData(@RequestBody List<PurchaseOrderModel> poModal,
			Model model, HttpSession session) {
		logger.info("Method :addPOUploadData starts");

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

		List<PurchaseOrderModel> poList = new ArrayList<>();
		XSSFWorkbook workbook = (XSSFWorkbook) session.getAttribute("purchaseOrder");
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				PurchaseOrderModel pOrder = new PurchaseOrderModel();

				XSSFRow row = worksheet.getRow(index);
				DataFormatter formatter = new DataFormatter(); // creating formatter using the default locale

				pOrder.setPlant(formatter.formatCellValue(row.getCell(0)));

				// logger.info("DATE"+row.getCell(1).toString());
				// String date = row.getCell(1).toString();

				if (row.getCell(1).toString() != null && row.getCell(1).toString() != "") {
					String dateString = row.getCell(1).toString();
					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

					LocalDate date = LocalDate.parse(dateString, inputFormatter);
					String formattedDate = date.format(outputFormatter);
					pOrder.setDocDate(formattedDate);
				}

				// logger.info("DATE1234"+pOrder);
				pOrder.setPurchaseDoc(formatter.formatCellValue(row.getCell(2)));
				pOrder.setItem(formatter.formatCellValue(row.getCell(3)));

				// String vendor = row.getCell(4).toString();
				// vendor = vendor.substring(vendor.indexOf(" ")+1);
				// pOrder.setVendorORsupllyingPlant(vendor);

				String vendor = row.getCell(4).toString();
				String[] parts = vendor.split(" ");
				String first = parts[0];
				pOrder.setVendorORsupllyingPlant(first);

				pOrder.setMaterial(formatter.formatCellValue(row.getCell(5)));
				pOrder.setShortText(formatter.formatCellValue(row.getCell(6)));
				pOrder.setoUnit(formatter.formatCellValue(row.getCell(7)));
				String price = row.getCell(8).toString();
				Double dprice = Double.valueOf(price);// first way
				pOrder.setNetPrice(dprice);
				String qty = row.getCell(9).toString();
				Double dQty = Double.valueOf(qty);// first way

				pOrder.setOrderQuantity(dQty);
				pOrder.setToBeDel(formatter.formatCellValue(row.getCell(10)));
				pOrder.setAgreement(formatter.formatCellValue(row.getCell(11)));
				pOrder.setItemQty(formatter.formatCellValue(row.getCell(12)));
				pOrder.setPer(formatter.formatCellValue(row.getCell(13)));
				pOrder.setRel(formatter.formatCellValue(row.getCell(14)));

				pOrder.setCreatedBy(userId);
				pOrder.setOrganization(orgName);
				pOrder.setOrgDivision(orgDivision);
				poList.add(pOrder);
			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("poList add 11=====" + poList);
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-addPOUploadData", poList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addPOUploadData ends");
		return resp;
	}

	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-purchase-order-products-save")
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

	public String saveAllImage(byte[] imageBytes, String ext) {
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

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 280;
				Integer width = 474;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					BufferedImage outputImage = new BufferedImage(width, height, img.getType());

					Graphics2D g2d = outputImage.createGraphics();
					g2d.drawImage(img, 0, 0, width, height, null);
					g2d.dispose();
					String outputImagePath = env.getFileUploadMaster() + "thumb/" + imageName;
					ImageIO.write(outputImage, ext, new File(outputImagePath));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-purchase-order-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryDataListModalForPO(
			HttpSession session) {
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
		logger.info("Method : getProductCategoryDataListModalForPO starts");
		return resp;
	}

	@PostMapping("manage-purchase-order-delete-fileproduct")
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


	@PostMapping("/manage-purchase-order-upload-fileprofuct")
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
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-purchase-order-get-sku-by-product" })
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
	@GetMapping("manage-purchase-order-deletesku")
	public @ResponseBody JsonResponse<Object> deleteskuForPO(Model model, HttpSession session, @RequestParam String id) {

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
		logger.info("Method : deleteskuForPO ends");
		return resp;
	}
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-purchase-order-save-sku-dtls")
	public @ResponseBody JsonResponse<Object> saveProductDetailsforPo(@RequestBody ProductDetailsModel product,
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

		logger.info("Method : saveProductDetailsforPo starts");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-purchase-order-get-sku-details" })
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
	@PostMapping("manage-purchase-order-lines-add")
	public @ResponseBody JsonResponse<Object> addpurchagelines(HttpSession session,
			@RequestBody List<PurchaseOrderModel> model) {
		logger.info("Method : addpurchagelines starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
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

		for (PurchaseOrderModel m : model) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		logger.info("model===" + model);

		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addpurchagelines", model,
					JsonResponse.class);
			logger.info("response**********************" + resp);

		} catch (Exception e) {

			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addpurchagelines ends");

		return resp;
	}
}
