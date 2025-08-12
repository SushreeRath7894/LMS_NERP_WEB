package nirmalya.aathithya.webmodule.store.controller;

import java.net.URLDecoder;
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
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.controller.PurchaseInvoiceDetailsWebController;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;

@Controller
@RequestMapping(value = { "store/" })
public class StoreInvoiceController {
	Logger logger = LoggerFactory.getLogger(PurchaseInvoiceDetailsWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PurchaseInvoiceDetailsWebController purchaseInvoiceDetailsWebController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/store-invoice" })
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
		
		
		// Capex Header
		
		try {

			DropDownModel[] header = restTemplate.getForObject(env.getPurchaseUrl()+
					"rest-capexHeader?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> capexHeader = Arrays.asList(header);
			model.addAttribute("capexHeader", capexHeader);
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
			
			String type="Store";
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
		return "store/store-invoice";
	}

	/*
	 * vendor autoSearch
	 */


	@SuppressWarnings("unchecked")
	@PostMapping(value = { "store-invoice-get-vendor-list" })
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
	/*
	 * Item auto search
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "store-invoice-get-item-list" })
	public @ResponseBody JsonResponse<DeliveryChallanModel> getItemQuotationAutoSearchNewListForMI(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
		logger.info("Method : getItemQuotationAutoSearchNewListForMI starts");
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
			res = restClient.getForObject(
					env.getMasterUrl() + "getItemQuotationAutoSearchNewListForMI?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getItemQuotationAutoSearchNewListForMI ends");
		return res;
	}
	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("store-invoice-get-product-list")
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

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "store-invoice-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getPoInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		String requestType = "store";
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getPurchaseInvoiceInsertedId?requestType=" + requestType, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getPurchaseInvoiceInsertedId ends");

		return res;
	}
	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "store-invoice-item-product-by-cat" })
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

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("store-invoice-add")
	public @ResponseBody JsonResponse<Object> addInvoice(HttpSession session,
			@RequestBody List<ManageInvoiceModel> manageInvoiceModel) {
		logger.info("Method : addPurchaseInvoice starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<ManageInvoiceModel> documentList = new ArrayList<ManageInvoiceModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		String requestType = "store";

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

		for (InventoryVendorDocumentModel a : manageInvoiceModel.get(0).getDocumentList()) {

			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String[] extension = a.getFileName().split("\\.");
					int lastindex = extension.length - 1;
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, extension[lastindex]);
							a.setFileName(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				}
			}
		}

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addPurchaseInvoice", manageInvoiceModel,
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

		logger.info("Method : addPurchaseInvoice ends");

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

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("store-invoice-view")
	public @ResponseBody Object viewInvoiceDetails(HttpSession session, @RequestParam String pageno) {
		logger.info("Method :viewInvoiceDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String invType = "store";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-viewInvoiceDetails?orgName=" + orgName + "&orgDivision=" + orgDivision + "&invType=" + invType + "&pageno=" + pageno,
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
		logger.info("Method :viewInvoiceDetails ends");
		return resp;
	}

	// edit

	@SuppressWarnings("unchecked")
	@GetMapping("store-invoice-edit")
	public @ResponseBody Object editInvoiceDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method :editInvoiceDetails starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-editInvoiceDetails?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editInvoiceDetails ends");
		return resp;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "store-invoice-delete")
	public @ResponseBody JsonResponse<Object> deleteInvoiceDetils(@RequestBody ManageInvoiceModel manageInvoiceModel,
			HttpSession session) {
		logger.info("Method : deleteInvoiceDetils starts");

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

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "deleteInvoiceDetils", manageInvoiceModel,
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

		logger.info("Method : deleteInvoiceDetils Ends");
		return resp;
	}

	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("store-invoice-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveInvoiceDetails(HttpSession session,
			@RequestParam String approveStatus, String invoiceId) {

		logger.info("Method : approveInvoiceDetails starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String orgName = "";
		String orgDivision = "";
		String decodedId= "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			 decodedId = URLDecoder.decode(invoiceId, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "approveInvoiceDetails?approveStatus=" + approveStatus + "&invoiceId="
									+ decodedId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method : approveInvoiceDetails ends");
		return response;
	}
	/*
	 * edit
	 */

	@GetMapping(value = { "store-invoice-edit-Invoice" })
	public @ResponseBody List<ManageInvoiceModel> purchaseInvoiceEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : purchaseInvoiceEdit starts");
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
						+ "purchaseInvoiceEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
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
					if (m.getInvoiceDate() != null && m.getInvoiceDate() != "") {
						m.setInvoiceDate(DateFormatter.dateFormat(m.getInvoiceDate(), dateFormat));

					}
					if (m.getChallanDate() != null && m.getChallanDate() != "") {
						m.setChallanDate(DateFormatter.dateFormat(m.getChallanDate(), dateFormat));

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

		logger.info("Method : purchaseInvoiceEdit ends");

		return productList;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("store-invoice-get-address")
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

	@GetMapping(value = { "store-invoice-stateList" })
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
	
	
	// Search
	
		@SuppressWarnings("unchecked")

		@GetMapping("store-invoice-view-search")
		public @ResponseBody Object viewInvoiceDetailsSearch(HttpSession session, @RequestParam String search) {
			logger.info("Method :viewInvoiceDetailsSearch starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			String invType = "store";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(
						env.getPurchaseUrl() + "rest-viewInvoiceDetailsSearch?orgName=" + orgName + "&orgDivision=" + orgDivision + "&invType=" + invType + "&search=" + search,
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
			logger.info("Method :viewInvoiceDetailsSearch ends");
			return resp;
		}
}
