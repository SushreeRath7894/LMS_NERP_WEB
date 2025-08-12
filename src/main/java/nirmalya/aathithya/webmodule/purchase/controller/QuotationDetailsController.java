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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.gatepass.model.GatePassDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRequisitionModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRfqModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRfqVendorModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseQuotationModel;
import nirmalya.aathithya.webmodule.purchase.model.QuotationDetailsModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;

@Controller
@RequestMapping(value = { "purchase/" })
public class QuotationDetailsController {
	Logger logger = LoggerFactory.getLogger(QuotationDetailsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PurchaseQuotationController purchaseQuotationController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "quotation-details" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : quotationDetails starts");

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
			DropDownModel[] mode = restTemplate.getForObject(env.getPurchaseUrl() + "getrfqdropdown",
					DropDownModel[].class);
			List<DropDownModel> rfqList = Arrays.asList(mode);

			model.addAttribute("rfqList", rfqList);
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

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getReferenceInquiry",
					DropDownModel[].class);
			List<DropDownModel> referenceList = Arrays.asList(dropDownModel);
			model.addAttribute("referenceList", referenceList);
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
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getSalutationLists",
					DropDownModel[].class);
			List<DropDownModel> SalutationLists = Arrays.asList(Collection);
			logger.info("SalutationLists" + SalutationLists);
			model.addAttribute("SalutationLists", SalutationLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			logger.info("sourceList" + sourceList);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorCategory",
					DropDownModel[].class);
			List<DropDownModel> CategoryList = Arrays.asList(Collection);
			logger.info("CATAGORY" + CategoryList);
			model.addAttribute("CategoryList", CategoryList);
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
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getPaymentModeList",
					DropDownModel[].class);
			List<DropDownModel> PaymentModeList = Arrays.asList(Collection);
			logger.info("PaymentModeList" + PaymentModeList);
			model.addAttribute("PaymentModeList", PaymentModeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : quotationDetails ends");
		return "purchase/purchaseQuotation-details";
	}

// quotation id 

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "quotation-details-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getQuotationInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getQuotationInsertedId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getQuotationInsertedId ends");

		return res;
	}

	/*
	 * vendor autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "quotation-details-get-vendor-list" })
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
			/*
			 * res = restTemplate.getForObject(env.getPurchaseUrl() +
			 * "getVendorAutoSearchList?id=" + searchValue + "&org=" + orgName + "&orgDiv="
			 * + orgDivision, JsonResponse.class);
			 */
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
	 * Item autosearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "quotation-details-item-get-customer-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchList(Model model,
			@RequestBody String searchValue, String type, HttpSession session, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
		logger.info("QuotationNewModel" + searchValue);
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
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
		logger.info("RESPONSE@@" + res);
		logger.info("Method : getItemQuotationAutoSearchList ends");
		return res;
	}

	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "quotation-details-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
			@RequestBody String index, BindingResult result) {
		logger.info("Method : getProductsByCatInvoice starts");

		String indexValue = index.substring(0, index.length() - 1);

		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getProductsNByCat?id=" + indexValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("DATA" + res);
		logger.info("Method : getProductsByCatInvoice ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("quotation-details-get-address")
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

	@PostMapping("quotation-details-add-cust-shippingaddress")
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

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "quotation-details-stateList" })
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

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("quotation-details-add")
	public @ResponseBody JsonResponse<Object> addquotationDetails(HttpSession session,
			@RequestBody List<QuotationDetailsModel> purchaseModel) {
		logger.info("Method : addquotationDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<QuotationDetailsModel> documentList = new ArrayList<QuotationDetailsModel>();
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

		for (QuotationDetailsModel m : purchaseModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}

		for (InventoryVendorDocumentModel a : purchaseModel.get(0).getDocumentList()) {

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

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addquotationDetails", purchaseModel,
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

		logger.info("Method : addquotationDetails ends");

		return resp;
	}

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("quotation-details-through-ajax")
	public @ResponseBody List<QuotationDetailsModel> viewquotationdetails(HttpSession session) {

		logger.info("Method :viewquotationdetails startsssssssssssssssssssssss");
		JsonResponse<List<QuotationDetailsModel>> resp = new JsonResponse<List<QuotationDetailsModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-viewquotationdetails?org=" + orgName
					+ "&orgDiv=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<QuotationDetailsModel> purchaseModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<QuotationDetailsModel>>() {
				});

		/*
		 * for (QuotationDetailsModel a : purchaseModel) { if (a.getQutValidDate() !=
		 * null && a.getQutValidDate() != "") {
		 * a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
		 * } if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
		 * a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat)); }
		 * 
		 * }
		 */
		resp.setBody(purchaseModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage("Unsuccess");
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage("Success");
		}

		logger.info("Method :viewquotationdetails ends");

		return resp.getBody();
	}

	/*
	 * edit
	 */

	@GetMapping(value = { "quotation-details-edit-new" })
	public @ResponseBody List<QuotationDetailsModel> viewPurchaseQutEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPurchaseQutEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<QuotationDetailsModel> productList = new ArrayList<QuotationDetailsModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				QuotationDetailsModel[] purchaseOrderModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewPurchaseQutEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						QuotationDetailsModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (QuotationDetailsModel m : purchaseOrderModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					/*
					 * if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
					 * m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));
					 * 
					 * } if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
					 * 
					 * }
					 */

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
		logger.info("Method : viewPurchaseQutEdit ends");

		return productList;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("quotation-details-delete")
	public @ResponseBody JsonResponse<Object> deletequotationDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deletequotationDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deletequotationDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deletequotationDetails function Ends");

		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/quotation-details-vendor-adds")
	public @ResponseBody JsonResponse<Object> addVendorForQuotation(@RequestBody VendorNewModel vendorNewModel,
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
		logger.info("Method : addVendorForQuotation ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "quotation-details-getReferenceList" })
	public @ResponseBody JsonResponse<Object> getReferenceList(@RequestParam String id, String quotId, HttpSession session) {
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
					env.getPurchaseUrl() + "getReferenceList?id=" + id + "&quotId=" + quotId + "&org=" + orgName + "&orgDiv=" + orgDivision,
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
	@GetMapping("quotation-details-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveQuotation(HttpSession session,
			@RequestParam String approveStatus, String quotationId) {

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

		try {
			response = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "approveQuotation?approveStatus=" + approveStatus + "&quotationId="
									+ quotationId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);

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
		logger.info("Method : approveQuotation ends");
		return response;
	}

	// item against reference id

	@GetMapping(value = { "quotation-details-getReferenceItemDetails" })
	public @ResponseBody List<QuotationDetailsModel> getReferenceItemDetails(@RequestParam String id, String taxType1,
			HttpSession session) {
		logger.info("Method : getReferenceItemDetails starts");
		List<QuotationDetailsModel> productList = new ArrayList<QuotationDetailsModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (id != null && id != "") {

			try {
				QuotationDetailsModel[] QuotationDetailsModel = restTemplate
						.getForObject(
								env.getPurchaseUrl() + "getReferenceItemDetails?id=" + id + "&taxType1=" + taxType1
										+ "&org=" + orgName + "&orgDivision=" + orgDivision,
								QuotationDetailsModel[].class);
				productList = Arrays.asList(QuotationDetailsModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (QuotationDetailsModel m : QuotationDetailsModel) {
					count++;
					m.setSlNo(count);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getReferenceItemDetails ends");
		return productList;
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
	@PostMapping("/quotation-details-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryDataListModalForQUOT(
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

	@PostMapping("/quotation-details-upload-fileprofuct")
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
	@PostMapping(value = { "quotation-details-get-variant-dtls" })
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
	@PostMapping("/quotation-details-save-purchase-dtls")
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
	@GetMapping("quotation-details-purchase-delete")
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
	@GetMapping(value = { "quotation-details-reqid" })
	public @ResponseBody JsonResponse<DropDownModel> getRfqListByAutoSearch(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getRfqListByAutoSearch starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getRfqListByAutoSearch?id=" + id,
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
		logger.info("Method : getRfqListByAutoSearch ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("quotation-details-compare")
	public @ResponseBody List<QuotationDetailsModel> quotaitionCompare(@RequestParam String id, HttpSession session) {

		logger.info("Method :viewquotationdetails startsssssssssssssssssssssss");
		JsonResponse<List<QuotationDetailsModel>> resp = new JsonResponse<List<QuotationDetailsModel>>();
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-rfq-quotaition-compare?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<QuotationDetailsModel> purchaseModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<QuotationDetailsModel>>() {
				});

		for (QuotationDetailsModel a : purchaseModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

		}

		resp.setBody(purchaseModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewquotationdetails ends");

		return resp.getBody();
	}

	// get Product Category Data List Modal
	@SuppressWarnings("unchecked")
	@PostMapping("quotation-details-item-get-product-list")
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

}