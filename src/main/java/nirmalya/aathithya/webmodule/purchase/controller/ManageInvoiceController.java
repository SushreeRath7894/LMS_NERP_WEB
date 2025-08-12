package nirmalya.aathithya.webmodule.purchase.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
import nirmalya.aathithya.webmodule.purchase.model.QaRequestModel;
import nirmalya.aathithya.webmodule.purchase.model.GrnReturnExcelModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseIndentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller
@RequestMapping(value = { "purchase/" })
public class ManageInvoiceController {

	Logger logger = LoggerFactory.getLogger(ManageInvoiceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	ManageInvoiceController manageInvoiceController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/manage-invoice" })
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

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
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

		try {
			DropDownModel[] paymentMode = restTemplate.getForObject(env.getEmployeeUrl() + "getPaymentMode",
					DropDownModel[].class);
			List<DropDownModel> PayModeLists = Arrays.asList(paymentMode);

			model.addAttribute("PayModeLists", PayModeLists);

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
			DropDownModel[] mode = restTemplate.getForObject(env.getPurchaseUrl() + "getModeListForPurchaseProduct",
					DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
			logger.info("MODELISTTT" + modeList);
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
			String type="Inventory";
			DropDownModel[] vendor = restTemplate.getForObject(
					env.getMasterUrl() + "getVendorListForProductWise?org=" + org + "&orgDiv=" + orgDiv + "&type=" + type,
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendor);

			model.addAttribute("vendorList", vendorList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("org", org);
		model.addAttribute("orgDiv", orgDiv);
		logger.info("Method : poDetails ends");
		return "purchase/manage-invoice";
	}

	/*
	 * vendor autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-invoice-get-vendor-list" })
	public @ResponseBody JsonResponse<PurchaseOrderModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, String type, BindingResult result, HttpSession session) {
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
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorAutoSearchListInventory?id=" + searchValue
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

	@GetMapping(value = { "manage-invoice-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getPoInsertedId(HttpSession session) {
		JsonResponse<Object> res = new JsonResponse<Object>();
		String requestType = "inventory";
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getInvoiceInsertedId?requestType=" + requestType
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
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
	@PostMapping("manage-invoice-item-get-product-list")
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
	@PostMapping(value = { "manage-invoice-order-get-item-list" })
	public @ResponseBody JsonResponse<DeliveryChallanModel> getItemQuotationAutoList(Model model,
			@RequestBody String searchValue, String type, HttpSession session, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchNewList starts");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<DeliveryChallanModel> res = new JsonResponse<DeliveryChallanModel>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getItemQuotationAutoSearchNewListForPO?id="
					+ searchValue + "&type=" + type + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getItemQuotationAutoSearchNewList ends");
		return res;
	}

	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "manage-invoice-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
			@RequestBody String index, BindingResult result, HttpSession session) {
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

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-invoice-get-tcs-list" })
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

	@PostMapping("manage-invoice-add-tcs")
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
	@PostMapping("manage-invoice-add")
	public @ResponseBody JsonResponse<Object> addInvoice(HttpSession session,
			@RequestBody List<ManageInvoiceModel> manageInvoiceModel) {
		logger.info("Method : addInvoice starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<ManageInvoiceModel> documentList = new ArrayList<ManageInvoiceModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		String requestType = "inventory";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		for (ManageInvoiceModel m : manageInvoiceModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setRequestType(requestType);

		}

		/*
		 * for (InventoryVendorDocumentModel a :
		 * manageInvoiceModel.get(0).getDocumentList()) {
		 * 
		 * if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
		 * a.setFileName(a.getImageNameEdit()); } else { if (a.getFileName() != null &&
		 * a.getFileName() != "") { String[] extension = a.getFileName().split("\\.");
		 * int lastindex = extension.length - 1; for (String s1 : a.getDocumentFile()) {
		 * try { byte[] bytes = Base64.getDecoder().decode(s1); logger.info("bytes" +
		 * s1); String imageName = saveAllMultiImages(bytes, extension[lastindex]);
		 * a.setFileName(imageName);
		 * 
		 * } catch (Exception e) { e.printStackTrace();
		 * 
		 * } } } } }
		 */
		
		for (InventoryVendorDocumentModel a : manageInvoiceModel.get(0).getDocumentList()) {

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

		// logger.info("ADDDDINVC" + manageInvoiceModel);
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addInvoice", manageInvoiceModel,
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

		logger.info("Method : addInvoice ends");

		return resp;
	}

	public String saveAllMultiImages(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllMultiImages starts");
		String imageName1 = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg") || ext.contentEquals("JPEG")) {
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
	@GetMapping("manage-invoice-through-ajax")
	public @ResponseBody List<ManageInvoiceModel> viewInvoice(HttpSession session) {

		logger.info("Method :viewInvoice startsssssssssssssssssssssss");
		JsonResponse<List<ManageInvoiceModel>> resp = new JsonResponse<List<ManageInvoiceModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		String requestType = "inventory";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-viewInvoice?org=" + orgName + "&orgDiv="
					+ orgDivision + "&requestType=" + requestType, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<ManageInvoiceModel> manageInvoiceModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageInvoiceModel>>() {
				});

		for (ManageInvoiceModel a : manageInvoiceModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

		}

		resp.setBody(manageInvoiceModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewInvoice ends");

		return resp.getBody();
	}

	/*
	 * edit
	 */

	@GetMapping(value = { "manage-invoice-edit-new" })
	public @ResponseBody List<ManageInvoiceModel> viewInvoiteEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewInvoiteEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		List<ManageInvoiceModel> productList = new ArrayList<ManageInvoiceModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				ManageInvoiceModel[] manageInvoiceModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewInvoiteEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						ManageInvoiceModel[].class);

				productList = Arrays.asList(manageInvoiceModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (ManageInvoiceModel m : manageInvoiceModel) {
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
					if (m.getReferenceDate() != null && m.getReferenceDate() != "") {
						m.setReferenceDate(DateFormatter.dateFormat(m.getReferenceDate(), dateFormat));

					}
					if (m.getExptdeliveryDate() != null && m.getExptdeliveryDate() != "") {
						m.setExptdeliveryDate(DateFormatter.dateFormat(m.getExptdeliveryDate(), dateFormat));

					}
					if (m.getChallanDate() != null && m.getChallanDate() != "") {
						m.setChallanDate(DateFormatter.dateFormat(m.getChallanDate(), dateFormat));

					}

				}
				System.out.println("productList.isEmpty()>>>----" + !productList.isEmpty());
				System.out.println("productList>>>----" + productList);
				/*
				 * if (productList != null || !productList.isEmpty()) { documentList =
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
				 * extension[1].equals("jpeg") || extension[1].equals("JPEG")) { String docPath
				 * = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title=" +
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

		logger.info("Method : viewInvoiteEdit ends" + productList);

		return productList;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "manage-invoice-polist" })
	public @ResponseBody JsonResponse<Object> getPoList(@RequestParam String id, String grnId, HttpSession session) {
		logger.info("Method : getPoList starts" + id);
		logger.info("Method : getPoList starts" + grnId);

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("Method : getPoList starts" + orgName);
		logger.info("Method : getPoList starts" + orgDivision);
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getPoList?id=" + id + "&grnId=" + grnId + "&org="
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

		logger.info("Method : getPoList ends");
		return res;
	}

	@GetMapping(value = { "manage-invoice-getdataOnPO" })
	public @ResponseBody List<PurchaseOrderModel> viewPoEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPoEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		logger.info("Method : viewPoEdit ends");

		return productList;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "manage-invoice-delete")
	public @ResponseBody JsonResponse<Object> deleteInvoice(@RequestBody ManageInvoiceModel manageInvoiceModel,
			HttpSession session) {
		logger.info("Method : deleteInvoice starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			manageInvoiceModel.setCreatedBy(userId);
			manageInvoiceModel.setOrganization(orgName);
			manageInvoiceModel.setOrgDivision(orgDivision);
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "deleteInvoice", manageInvoiceModel,
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

		logger.info("Method : deleteInvoice Ends");
		return resp;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "manage-invoice-stateList" })
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
	@GetMapping("manage-invoice-get-address")
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

	@PostMapping("manage-invoice-add-cust-billingaddress")
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

	@PostMapping("manage-invoice-add-cust-shippingaddress")
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
	@PostMapping("/manage-invoice-adds")
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

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "/addVendor", vendorNewModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addVendor ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "manage-invoice-generate-return-ajax")
	public @ResponseBody JsonResponse<Object> createReturnNote(@RequestBody ManageInvoiceModel manageInvoiceModel,
			HttpSession session) {
		logger.info("Method : createReturnNote function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		manageInvoiceModel.setCreatedBy(userId);
		manageInvoiceModel.setOrganization(orgName);
		manageInvoiceModel.setOrgDivision(orgDivision);

		try {
			res = restTemplate.postForObject(env.getPurchaseUrl() + "rest-add-grn-return", manageInvoiceModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : createReturnNote function Ends");
		return res;
	}

	/*
	 * edit data for grn-return
	 */

	@GetMapping(value = { "manage-invoice-edit-forGrnReturn" })
	public @ResponseBody List<ManageInvoiceModel> viewInvoiceEditForReturn(@RequestParam String id, String hsnCode,
			HttpSession session) {
		logger.info("Method : viewInvoiceEditForReturn starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		List<ManageInvoiceModel> productList = new ArrayList<ManageInvoiceModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				ManageInvoiceModel[] manageInvoiceModel = restTemplate
						.getForObject(env.getPurchaseUrl() + "viewInvoiceEditForReturn?id=" + id + "&hsnCode=" + hsnCode
								+ "&org=" + orgName + "&orgDiv=" + orgDivision, ManageInvoiceModel[].class);

				productList = Arrays.asList(manageInvoiceModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (ManageInvoiceModel m : manageInvoiceModel) {
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
					if (m.getReferenceDate() != null && m.getReferenceDate() != "") {
						m.setReferenceDate(DateFormatter.dateFormat(m.getReferenceDate(), dateFormat));

					}
					if (m.getExptdeliveryDate() != null && m.getExptdeliveryDate() != "") {
						m.setExptdeliveryDate(DateFormatter.dateFormat(m.getExptdeliveryDate(), dateFormat));

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

		logger.info("Method : viewInvoiceEditForReturn ends");

		return productList;
	}
	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("manage-invoice-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveinvoice(HttpSession session,
			@RequestParam String approveStatus, String invoiceId) {

		logger.info("Method : approveinvoice starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		String invType = "inventory";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate.getForObject(env.getPurchaseUrl() + "approveinvoice?approveStatus=" + approveStatus
					+ "&invoiceId=" + invoiceId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&invType="
					+ invType + "&approvedBy=" + userId, JsonResponse.class);

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
		logger.info("Method : approveinvoice ends");
		return response;
	}

	// Add Quality-Assurance Request.

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("manage-invoice-add-qarequest") public @ResponseBody
	 * JsonResponse<Object> qaRequestAdd(@RequestBody List<QaRequestModel>
	 * qaRequestAdd, HttpSession session) {
	 * logger.info("Method : qaRequestAdd Start");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * 
	 * String organization = ""; String orgDivision = "";
	 * 
	 * try {
	 * 
	 * organization = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { e.printStackTrace(); } for (QaRequestModel m : qaRequestAdd) {
	 * m.setOrganization(organization); m.setOrgDivision(orgDivision); }
	 * logger.info("qaRequestAdd===" + qaRequestAdd);
	 * 
	 * try {
	 * 
	 * resp = restTemplate.postForObject(env.getPurchaseUrl() +
	 * "rest-qaRequestAddData", qaRequestAdd, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method : qaRequestAdd ends"); logger.info("Web Adddd>>>>>---" +
	 * resp); return resp; }
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-invoice-add-qarequest")
	public @ResponseBody Object qaRequestAdd(@RequestParam String id, HttpSession session) {
		logger.info("Method :qaRequestAdd starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		String decodedId= "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			decodedId = URLDecoder.decode(id, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restTemplate.getForObject(env.getPurchaseUrl()  + "rest-qaRequestAddData?id=" + decodedId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :qaRequestAdd ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-invoice-get-total-list")
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

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-invoice-get-sku-by-product" })
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

	@PostMapping("manage-invoice-delete-fileproduct")
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

	@PostMapping("/manage-invoice--upload-fileprofuct")
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

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-invoice-save-sku-dtls")
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
	@GetMapping("manage-invoice-deletesku")
	public @ResponseBody JsonResponse<Object> deleteskuForPO(Model model, HttpSession session,
			@RequestParam String id) {

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
	@PostMapping("/manage-invoice-products-save")
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
	@PostMapping(value = { "manage-invoice-get-sku-details" })
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

// product

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-invoice-get-sku-listing" })
	public @ResponseBody List<ProductMasterModel> getProductSKUListingForInvoice(Model model,
			HttpServletRequest request, @RequestParam String type, HttpSession session) {
		logger.info("Method : getProductSKUListingForInvoice starts");

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

		logger.info("Method : getProductSKUListingForInvoice ends" + res.getBody());
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-invoice-get-product-details" })
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
			res = restTemplate.getForObject(env.getMasterUrl() + "getProductDetailsById?id=" + tCountry+"&orgName=" + orgName + "&orgDiv=" + orgDivision,
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
	@GetMapping(value = { "manage-invoice-get-purchase-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUPurchaseListing(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getSKUPurchaseListing starts");

		JsonResponse<List<ProductDetailsModel>> res = new JsonResponse<List<ProductDetailsModel>>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getSKUPurchaseListingById?id=" + id + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ProductDetailsModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductDetailsModel>>() {
					});

			String dateFormat = "";

			try {
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (ProductDetailsModel m : product) {
				m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
				m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
				m.setsMoq(NumberFormatter.doubleToStringWithComma(m.getMoq()));
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
		logger.info("Method : getSKUPurchaseListing ends");
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@PostMapping("manage-invoice-add-brand")
	public @ResponseBody JsonResponse<Object> addbrandDetails(@RequestBody ProductMasterModel productMasterModel,
			HttpSession session) {
		logger.info("Method : addbrandDetails starts");

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
		productMasterModel.setCreatedBy(userId);
		productMasterModel.setOrganizationName(orgName);
		productMasterModel.setOrganizationDivision(orgDivision);
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addbrandDetails", productMasterModel,
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

		logger.info("Method :addbrandDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-invoice-get-purchase-details-edit" })
	public @ResponseBody JsonResponse<ProductDetailsModel> getSKUPurchaseDetails(Model model,
			@RequestBody ProductDetailsModel tCountry, BindingResult result, HttpSession session) {
		logger.info("Method : getSKUPurchaseDetails starts");

		JsonResponse<ProductDetailsModel> res = new JsonResponse<ProductDetailsModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getSKUPurchaseDetails?id=" + tCountry.getProductId()
					+ "&skuid=" + tCountry.getSku() + "&vendorId=" + tCountry.getVendorId() + "&orgName=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getSKUPurchaseDetails ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-invoice-get-variant-dtls" })
	public @ResponseBody JsonResponse<DropDownModel> getVariantDetails(Model model, @RequestBody DropDownModel tCountry,
			BindingResult result) {
		logger.info("Method : getVariantDetails starts");

		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "getVariantDetails?id=" + tCountry.getKey() + "&skuid=" + tCountry.getName(),
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

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-invoice-save-purchase-dtls")
	public @ResponseBody JsonResponse<Object> saveProductPurchaseDetails(@RequestBody ProductDetailsModel product,
			HttpSession session) {
		logger.info("Method : saveProductPurchaseDetails starts");

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
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductPurchaseDetails", product,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveProductPurchaseDetails starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-invoice-purchase-delete")
	public @ResponseBody JsonResponse<Object> deletepurchase(Model model, HttpSession session,
			@RequestParam String id) {

		logger.info("Method : deletesku starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deletepurchase?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {

		}
		logger.info("Method : deletepurchase ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-invoice-get-VendorList" })
	public @ResponseBody JsonResponse<Object> getVendorList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getVendorList starts" + id);
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
		logger.info("Method : orgName starts" + orgName);
		logger.info("Method : orgDivision starts" + orgDivision);
		try {

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorList?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getVendorList ends" + resp);
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-invoice-save-excelData")
	public @ResponseBody JsonResponse<Object> addGrnRecieveData(@RequestBody List<GrnReturnExcelModel> attendanceModel,
			Model model, HttpSession session) {
		logger.info("Method :addGrnRecieveData starts");

		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {

			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (RestClientException e) {
			e.printStackTrace();

		}
		List<GrnReturnExcelModel> attendanceList = new ArrayList<>();
		XSSFWorkbook workbook = (XSSFWorkbook) session.getAttribute("grnReturnExcel");
		XSSFSheet worksheet = workbook.getSheetAt(0);
		System.out.println(
				"worksheet.getPhysicalNumberOfRows()>>>>>>>>>>>>>>>>>>>>>>>>" + worksheet.getPhysicalNumberOfRows());
		for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				GrnReturnExcelModel atten = new GrnReturnExcelModel();

				XSSFRow row = worksheet.getRow(index);
				// Integer id = (int) row.getCell(0).getNumericCellValue();
				DataFormatter formatter = new DataFormatter(); // creating formatter using the default locale

				// UPDATED EXCEL FORMAT
				atten.setGrNo(formatter.formatCellValue(row.getCell(0)));
				if (row.getCell(1).toString() != null && row.getCell(1).toString() != "") {
					String dateString = row.getCell(1).toString();
					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

					LocalDate date = LocalDate.parse(dateString, inputFormatter);
					String formattedDate = date.format(outputFormatter);
					atten.setGrDate(formattedDate);
				}
				atten.setPoNo(formatter.formatCellValue(row.getCell(2)));
				atten.setPoItem(formatter.formatCellValue(row.getCell(3)));
				atten.setVendorId(formatter.formatCellValue(row.getCell(4)));
				atten.setVendorName(formatter.formatCellValue(row.getCell(5)));
				atten.setTransporterId(formatter.formatCellValue(row.getCell(6)));
				atten.setTransporterName(formatter.formatCellValue(row.getCell(7)));
				atten.setDocType(formatter.formatCellValue(row.getCell(8)));
				atten.setInvoiceNo(formatter.formatCellValue(row.getCell(9)));
				atten.setPlant(formatter.formatCellValue(row.getCell(10)));
				atten.setMaterialCode(formatter.formatCellValue(row.getCell(11)));
				atten.setMaterialDesc(formatter.formatCellValue(row.getCell(12)));
				atten.setMvtType(formatter.formatCellValue(row.getCell(13)));
				atten.setBatch(formatter.formatCellValue(row.getCell(14)));
				atten.setMaterialQty(formatter.formatCellValue(row.getCell(15)).replace(",", ""));
				atten.setMaterialUom(formatter.formatCellValue(row.getCell(16)));
				atten.setBasicValue(formatter.formatCellValue(row.getCell(17)).replace(",", ""));
				atten.setTotalCost(formatter.formatCellValue(row.getCell(18)).replace(",", ""));
				atten.setTruckNo(formatter.formatCellValue(row.getCell(19)));
				atten.setMaterialType(formatter.formatCellValue(row.getCell(20)));
				System.out.println("row.getCell(21).toString()??>>>>" + row.getCell(21).toString());
				if (row.getCell(21).toString() != null && row.getCell(21).toString() != "") {
					String dateString = row.getCell(21).toString();
					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

					LocalDate date = LocalDate.parse(dateString, inputFormatter);
					String formattedDate = date.format(outputFormatter);
					atten.setPoDocDate(formattedDate);
				}
				atten.setFrtBefTax(formatter.formatCellValue(row.getCell(22)).replace(",", ""));
				atten.setFreight(formatter.formatCellValue(row.getCell(23)).replace(",", ""));
				// atten.setarriva(formatter.formatCellValue(row.getCell(24)));
				System.out.println("row.getCell(24).toString()??>>>>" + row.getCell(24).toString());
				atten.setIgstPercent(formatter.formatCellValue(row.getCell(24)));
				atten.setIgstAmount(formatter.formatCellValue(row.getCell(25)).replace(",", ""));
				atten.setCgstPercent(formatter.formatCellValue(row.getCell(26)));
				atten.setCgstAmount(formatter.formatCellValue(row.getCell(27)).replace(",", ""));
				atten.setSgstPercent(formatter.formatCellValue(row.getCell(28)));
				atten.setSgstAmount(formatter.formatCellValue(row.getCell(29)).replace(",", ""));
				atten.setVendorGst(formatter.formatCellValue(row.getCell(30)));
				atten.setVendorRegionName(formatter.formatCellValue(row.getCell(31)));
				atten.setSupplierStateId(formatter.formatCellValue(row.getCell(32)));
				atten.setRecPlantStateCode(formatter.formatCellValue(row.getCell(33)));
				atten.setHsnCode(formatter.formatCellValue(row.getCell(34)));
				atten.setGoodsServiceToggle(formatter.formatCellValue(row.getCell(35)));

				/*
				 * if (row.getCell(36).toString() != null && row.getCell(36).toString() != "") {
				 * atten.setGoodsServiceToggle(formatter.formatCellValue(row.getCell(36))); }
				 */

				// System.out.println("row.getCell(39).toString()>>>>>>>>"+row.getCell(39).toString());
				if (row.getCell(38).toString() != null && row.getCell(38).toString() != "") {
					String dateString = row.getCell(38).toString();
					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

					LocalDate date = LocalDate.parse(dateString, inputFormatter);
					String formattedDate = date.format(outputFormatter);
					atten.setInvoiceDate(formattedDate);
				}
				// System.out.println(row.getCell(2).toString());
//				atten.setInvoiceNo(formatter.formatCellValue(row.getCell(0)));
//				atten.setPoNo(formatter.formatCellValue(row.getCell(1)));
//				if (row.getCell(2).toString() != null && row.getCell(2).toString() != "") {
//					String dateString = row.getCell(2).toString();
//					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//					DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//					LocalDate date = LocalDate.parse(dateString, inputFormatter);
//					String formattedDate = date.format(outputFormatter);
//					atten.setGrDate(formattedDate);
//				}
//				atten.setVendorName(formatter.formatCellValue(row.getCell(3)));
//				atten.setMaterialCode(formatter.formatCellValue(row.getCell(4)));
//				atten.setMaterialDesc(formatter.formatCellValue(row.getCell(5)));
//				atten.setMaterialQty(formatter.formatCellValue(row.getCell(6)).replace(",",""));
//				atten.setMaterialUom(formatter.formatCellValue(row.getCell(7)));
//				atten.setGrNo(formatter.formatCellValue(row.getCell(8)));
//				atten.setTransporterName(formatter.formatCellValue(row.getCell(9)));
//				atten.setPlant(formatter.formatCellValue(row.getCell(10)));
//				atten.setTotalCost(formatter.formatCellValue(row.getCell(11)).replace(",",""));
//				atten.setPoItem(formatter.formatCellValue(row.getCell(12)));
//				atten.setVendorId(formatter.formatCellValue(row.getCell(13)));
//				atten.setLocalIsToggle(formatter.formatCellValue(row.getCell(19)));
//				atten.setTransporterId(formatter.formatCellValue(row.getCell(20)));
//				atten.setDocType(formatter.formatCellValue(row.getCell(21)));
//				atten.setFlDoc(formatter.formatCellValue(row.getCell(25)));
//				atten.setMvtType(formatter.formatCellValue(row.getCell(28)));
//				atten.setBatch(formatter.formatCellValue(row.getCell(29)));
//				atten.setBasicValue(formatter.formatCellValue(row.getCell(30)).replace(",",""));
//				atten.setTruckNo(formatter.formatCellValue(row.getCell(39)));
//				atten.setMaterialType(formatter.formatCellValue(row.getCell(40)));
//				if (row.getCell(41).toString() != null && row.getCell(41).toString() != "") {
//					String dateString = row.getCell(41).toString();
//					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//					DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//					LocalDate date = LocalDate.parse(dateString, inputFormatter);
//					String formattedDate = date.format(outputFormatter);
//					atten.setPoDocDate(formattedDate);
//				}
//				atten.setFrtBefTax(formatter.formatCellValue(row.getCell(43)).replace(",",""));
//				atten.setIgstPercent(formatter.formatCellValue(row.getCell(46)));
//				atten.setIgstAmount(formatter.formatCellValue(row.getCell(47)).replace(",",""));
//				atten.setCgstPercent(formatter.formatCellValue(row.getCell(48)));
//				atten.setCgstAmount(formatter.formatCellValue(row.getCell(49)).replace(",",""));
//				atten.setSgstPercent(formatter.formatCellValue(row.getCell(50)));
//				atten.setSgstAmount(formatter.formatCellValue(row.getCell(51)).replace(",",""));
//				if (row.getCell(52).toString() != null && row.getCell(52).toString() != "") {
//					String dateString = row.getCell(52).toString();
//					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//					DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//					LocalDate date = LocalDate.parse(dateString, inputFormatter);
//					String formattedDate = date.format(outputFormatter);
//					atten.setInvoiceDate(formattedDate);
//				}
//				//atten.setInvoiceDate(formatter.formatCellValue(row.getCell(52)));
//				atten.setVendorGst(formatter.formatCellValue(row.getCell(53)));
//				atten.setVendorRegionId(formatter.formatCellValue(row.getCell(54)));
//				atten.setVendorRegionName(formatter.formatCellValue(row.getCell(55)));
//				atten.setBpCode(formatter.formatCellValue(row.getCell(56)));
//				atten.setBpName(formatter.formatCellValue(row.getCell(57)));
//				atten.setSupplierStateId(formatter.formatCellValue(row.getCell(58)));
//				atten.setRecPlantStateCode(formatter.formatCellValue(row.getCell(59)));
//				atten.setHsnCode(formatter.formatCellValue(row.getCell(60)));
//				atten.setGoodsServiceToggle(formatter.formatCellValue(row.getCell(61)));
				atten.setCreatedBy(userId);
				atten.setOrganization(orgName);
				atten.setOrgDivision(orgDivision);
				attendanceList.add(atten);

			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("addGrnRecieveData====" + attendanceList);
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-addGrnRecieveExcel", attendanceList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
			session.removeAttribute("attendance");
		}
		logger.info("Method : addRequisitionMaster ends");
		System.out.println(resp);
		return resp;
	}

	// Upload invoice Data

	@PostMapping("manage-invoice-upload-file")
	public @ResponseBody JsonResponse<Object> uploadGrnRecieveData(@RequestParam("file") MultipartFile grnReturnExcel,
			HttpSession session) {
		logger.info("Method : uploadGrnRecieveData controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(grnReturnExcel.getInputStream());
			response.setMessage(grnReturnExcel.getOriginalFilename());
			System.out.println(grnReturnExcel);
			session.setAttribute("grnReturnExcel", workbook);
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("uploadGrnRecieveData==" + response);
		logger.info("Method : uploadGrnRecieveData controller ' ends");
		return response;
	}

	// Search

	@SuppressWarnings("unchecked")
	@GetMapping("manage-invoice-through-ajax-search")
	public @ResponseBody List<ManageInvoiceModel> viewInvoiceSearch(HttpSession session,
			@RequestParam String searchValue) {

		logger.info("Method :viewInvoiceSearch startsssssssssssssssssssssss");
		JsonResponse<List<ManageInvoiceModel>> resp = new JsonResponse<List<ManageInvoiceModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		String requestType = "inventory";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-viewInvoiceSearch?org=" + orgName + "&orgDiv="
					+ orgDivision + "&requestType=" + requestType + "&searchValue=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<ManageInvoiceModel> manageInvoiceModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageInvoiceModel>>() {
				});

		for (ManageInvoiceModel a : manageInvoiceModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

		}

		resp.setBody(manageInvoiceModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewInvoiceSearch ends");

		return resp.getBody();
	}

	// Vendor State.

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "manage-invoice-get-vendor_state_data" })
	public @ResponseBody JsonResponse<Object> getVendorState(@RequestParam String id, String type,
			HttpSession session) {
		logger.info("Method : getVendorState starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorState?id=" + id + "&type=" + type
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * if (res.getMessage() != null) { res.setCode(res.getMessage());
		 * res.setMessage("Unsuccess"); } else { res.setMessage("success"); }
		 */
		System.out.println("res>>>>State>>>>" + res);
		logger.info("Method : getVendorState ends");
		return res;
	}
	
	// Filter data by from & todate 
	@SuppressWarnings("unchecked")
	@GetMapping("manage-invoice-filtered-data")
	public @ResponseBody List<ManageInvoiceModel> viewFilteredInvoice(HttpSession session, @RequestParam String fromDate,
			 @RequestParam String toDate, @RequestParam String searchData) {

		logger.info("Method :viewFilteredInvoice starts");
		JsonResponse<List<ManageInvoiceModel>> resp = new JsonResponse<List<ManageInvoiceModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		String requestType = "inventory";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-viewFilterInvoice?org=" + orgName + "&orgDiv="
					+ orgDivision + "&requestType=" + requestType + "&fromDate=" + fromDate + "&toDate=" + toDate
					 + "&searchData=" + searchData ,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<ManageInvoiceModel> manageInvoiceModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageInvoiceModel>>() {
				});

		for (ManageInvoiceModel a : manageInvoiceModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

		}

		resp.setBody(manageInvoiceModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewFilteredInvoice ends");

		return resp.getBody();
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("manage-invoice-Grn-ItemDetails")
	public @ResponseBody Object getGrnDtlsPO(@RequestParam String id, HttpSession session) {

		logger.info("Method :getGrnDtlsPO starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-getGrnDtlsPO?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getGrnDtlsPO ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

}
