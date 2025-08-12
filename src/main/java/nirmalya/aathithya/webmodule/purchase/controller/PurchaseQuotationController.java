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
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
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
import nirmalya.aathithya.webmodule.purchase.model.PurchaseQuotationModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;

@Controller
@RequestMapping(value = { "purchase/" })
public class PurchaseQuotationController {
	Logger logger = LoggerFactory.getLogger(PurchaseQuotationController.class);

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

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    
	@GetMapping(value = { "view-purchase-quotation" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : poDetails starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-requisition-type",
					DropDownModel[].class);
			List<DropDownModel> requisitionTypeList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionTypeList", requisitionTypeList);
		} catch (RestClientException e) {
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

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-requisition-priority", DropDownModel[].class);
			List<DropDownModel> requisitionPrioList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionPrioList", requisitionPrioList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-location",
					DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(dropDownModel);
			model.addAttribute("locationList", locationList);
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
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getPurchaseUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { DropDownModel[] reqList =
		 * restTemplate.getForObject(env.getPurchaseUrl() + "getRequisitionList?org=" +
		 * organization + "&orgDiv=" + orgDivision, DropDownModel[].class);
		 * List<DropDownModel> requisitionList = Arrays.asList(reqList);
		 * model.addAttribute("reqList", requisitionList); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : poDetails ends");
		return "purchase/view-purchase-quotation";
	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "view-purchase-quotation-vendor-trough-ajax" })
	public @ResponseBody List<VendorNewModel> viewVendorThroughAjax(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewVendorThroughAjax starts");
		List<VendorNewModel> productList = new ArrayList<VendorNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			VendorNewModel[] inventoryStockModel = restTemplate.getForObject(env.getPurchaseUrl()
					+ "get-purchasevendor-view-list?id=" + id + "&orgName=" + orgName + "&orgDiv=" + orgDivision,
					VendorNewModel[].class);
			productList = Arrays.asList(inventoryStockModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("productList>>>>-----" + productList);
		logger.info("Method : viewVendorThroughAjax ends");
		return productList;
	}

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("view-purchase-quotation-item-get-product-list")
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
	 * Item autosearch
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = { "view-purchase-quotation-item-get-list" })
	 * public @ResponseBody JsonResponse<InventorySkuProductModel>
	 * getItemQuotationAutoSearchList(Model model,
	 * 
	 * @RequestBody String searchValue, BindingResult result,HttpSession session) {
	 * logger.info("Method : getItemQuotationAutoSearchListForItem starts");
	 * logger.info("QuotationNewModel" + searchValue);
	 * JsonResponse<InventorySkuProductModel> res = new
	 * JsonResponse<InventorySkuProductModel>(); String organization = ""; String
	 * orgDivision = "";
	 * 
	 * try { organization = (String) session.getAttribute("ORGANIZATION");
	 * orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch
	 * (Exception ex) { logger.error(ex.getMessage()); }
	 * 
	 * try { res = restTemplate.getForObject( env.getSalesUrl() +
	 * "getItemQuotationAutoSearchListForItem?id=" + searchValue + "&org=" +
	 * organization + "&orgDiv=" + orgDivision, JsonResponse.class); } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * if (res.getMessage() != null) {
	 * 
	 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
	 * res.setMessage("success"); } logger.info("RESPONSE@@" + res);
	 * logger.info("Method : getItemQuotationAutoSearchList ends"); return res; }
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-purchase-quotation-item-get-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchList(Model model,
			@RequestBody String searchValue, String type, HttpSession session, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
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
		logger.info("Method : getItemQuotationAutoSearchList ends");
		return res;
	}
	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "view-purchase-quotation-item-product-by-cat" })
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
	@PostMapping("view-purchase-quotation-add")
	public @ResponseBody JsonResponse<Object> addPurchaseQuotation(HttpSession session,
			@RequestBody List<PurchaseQuotationModel> purchaseQuotationModel) {
		logger.info("Method : addPurchaseQuotation starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";
		String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {

		}
		for (PurchaseQuotationModel m : purchaseQuotationModel) {
			m.setCreatedBy(userId);
			m.setOrganization(orgName);
			m.setOrgDivision(orgDiv);

		}

		System.out.println("purchaseQuotationModel>>>>>>>" + purchaseQuotationModel);

		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addPurchaseQuotation", purchaseQuotationModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<PurchaseQuotationModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<PurchaseQuotationModel>>() {
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

		logger.info("Method : addPurchaseQuotation ends");

		return resp;
	}

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-purchase-quotation-through-ajax")
	public @ResponseBody List<PurchaseQuotationModel> viewQuotation(HttpSession session) {

		logger.info("Method :viewQuotation startsssssssssssssssssssssss");
		JsonResponse<List<PurchaseQuotationModel>> resp = new JsonResponse<List<PurchaseQuotationModel>>();
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
					env.getPurchaseUrl() + "rest-viewQuotation?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<PurchaseQuotationModel> purchaseQuotationModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<PurchaseQuotationModel>>() {
				});

		for (PurchaseQuotationModel a : purchaseQuotationModel) {

			if (a.getReceiveDate() != null && a.getReceiveDate() != "") {
				a.setReceiveDate(DateFormatter.dateFormat(a.getReceiveDate(), dateFormat));
			}

			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

		}

		resp.setBody(purchaseQuotationModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewQuotation ends");

		return resp.getBody();
	}

	// EDIT
	@GetMapping(value = { "view-purchase-quotation-edit-new" })
	public @ResponseBody List<PurchaseQuotationModel> viewInquiryEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewInquiryEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		List<PurchaseQuotationModel> productList = new ArrayList<PurchaseQuotationModel>();

		if (id != null && id != "") {
			try {
				PurchaseQuotationModel[] purchaseQuotationModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewInquiryEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseQuotationModel[].class);

				productList = Arrays.asList(purchaseQuotationModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseQuotationModel m : purchaseQuotationModel) {
					m.setQuantity(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
						m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));

					}
					if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewInquiryEdit ends" + productList);
		return productList;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-purchase-quotation-delete")
	public @ResponseBody JsonResponse<Object> deleteInquiry(@RequestBody PurchaseQuotationModel purchaseQuotationModel,
			HttpSession session) {
		logger.info("Method : deleteInquiry starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			purchaseQuotationModel.setCreatedBy(userId);
			purchaseQuotationModel.setOrganization(orgName);
			purchaseQuotationModel.setOrgDivision(orgDivision);
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "deleteInquiry", purchaseQuotationModel,
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

		logger.info("Method : deleteInquiry Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-purchase-quotation-pdf-downloads")
	public void getInvoicePdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("itemDetailsId") String encodedParam1,@RequestParam("organization") String encodedParam2,
			@RequestParam("orgDivision") String encodedParam3) {

		logger.info("Method : getItemPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		/*
		 * try {
		 * 
		 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
		 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
		 * { logger.error(e.getMessage()); }
		 */
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String itemDetailsIdd = (new String(encodeByte3));
		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam2.getBytes());
		orgName = (new String(encodeByte4));

		byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
		orgDivision = (new String(encodeByte5));
System.out.println("itemDetailsIdd>>>>> " + itemDetailsIdd);
		List<PurchaseQuotationModel> productList = new ArrayList<PurchaseQuotationModel>();
		try {
			PurchaseQuotationModel[] purchaseQuotationModel = restTemplate.getForObject(env.getPurchaseUrl()
					+ "view-itemdetails-viewPdf?id=" + itemDetailsIdd + "&org=" + orgName + "&orgDiv=" + orgDivision,
					PurchaseQuotationModel[].class);
			productList = Arrays.asList(purchaseQuotationModel);
			logger.info("productList>>>>>>>" + productList);
			productList.forEach(s -> s.setSlNo(s.getSlNo()));
			int count = 0;
			for (PurchaseQuotationModel m : purchaseQuotationModel) {
				count++;
				m.setSlNo(count);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		JsonResponse<VendorNewModel> jsonResponse = new JsonResponse<VendorNewModel>();

		ObjectMapper mapper = new ObjectMapper();
		VendorNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<VendorNewModel>() {
		});
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("itemdetails", productList);
		logger.info("Data>>>>>>>>>----" + data);
		//logger.info("imageName>>>>>>>>>----" + productList.get(0).getOrgImage().isEmpty());
		String imageName = null;
		
		if(!productList.get(0).getOrgImage().isEmpty()) {
			imageName = productList.get(0).getOrgImage();
			//imageName = "oriclean_org_logo.png";
		}
		String logo = null;
		if(!imageName.isEmpty()) {
			 logo = "classpath:static/assets/images/" + imageName;
		}
		//logger.info("logo>>>>>>>>>----" + logo);
		data.put("logo", logo);
		data.put("buyer", reimModel);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=itemdetails.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("purchase/item-details-pdf", data);
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
		logger.info("Method : getItemPdfDetails ends");
	}

	@GetMapping(value = { "view-purchase-quotation-get-Vendordata" })
	public @ResponseBody List<VendorNewModel> getVendordata(@RequestParam String sku, @RequestParam String rowCount,
			HttpSession session) {
		logger.info("Method : getVendordata starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<VendorNewModel> productList = new ArrayList<VendorNewModel>();
		if (sku != null && sku != "") {
			logger.info("IDD" + sku);
			try {
				VendorNewModel[] vendorNewModel = restTemplate
						.getForObject(
								env.getPurchaseUrl() + "getVendordata?sku=" + sku + "&orgName=" + orgName
										+ "&orgDivision=" + orgDivision + "&rowCount=" + rowCount,
								VendorNewModel[].class);

				productList = Arrays.asList(vendorNewModel);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getVendordata ends");
		logger.info("DATAAAA" + productList);
		return productList;
	}

	@PostMapping("view-purchase-quotation-delete-fileproduct")
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

	@SuppressWarnings("unchecked")
	@PostMapping("view-purchase-quotation-add-brand")
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
	@PostMapping("/view-purchase-quotation-get-total-list")
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

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-purchase-quotation-products-save")
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
	@PostMapping(value = { "view-purchase-quotation-get-product-details" })
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
	@GetMapping(value = { "view-purchase-quotation-get-sku-by-product" })
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
	@GetMapping(value = { "view-purchase-quotation-get-purchase-by-product" })
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
	@PostMapping(value = { "view-purchase-quotation-get-purchase-details-edit" })
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
	@PostMapping(value = { "view-purchase-quotation-get-sku-details" })
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

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-purchase-quotation-save-sku-dtls")
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
	@PostMapping(value = { "view-purchase-quotation-get-variant-dtls" })
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
	@PostMapping("/view-purchase-quotation-save-purchase-dtls")
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
	@GetMapping("view-purchase-quotation-purchase-delete")
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

	// Autosearch
	@GetMapping("view-purchase-quotation-get-requisition-list")
	public @ResponseBody Object getReqNoAutoSearchList(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :getReqNoAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "getReqNoAutoSearchList?id=" + searchValue + "&org="
					+ org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getReqNoAutoSearchList Ends");
		return resp;
	}

	@GetMapping("view-purchase-quotation-get-requisition-dtls")
	public @ResponseBody Object getReqNoAutoSearchListDtls(@RequestParam String id, HttpSession session) {

		logger.info("Method :getReqNoAutoSearchListDtls starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "getReqNoAutoSearchListDtls?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getReqNoAutoSearchListDtls Ends");
		return resp;
	}

	// All vendor

	@GetMapping("allVendor-purchase-quotation-through-ajax")
	public @ResponseBody Object getAllVendorListDtls(HttpSession session) {

		logger.info("Method :getAllVendorListDtls starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";
		String type = "Inventory";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-getAllVendorListDtls?type=" + type + "&org="
					+ org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllVendorListDtls Ends");
		return resp;
	}

	// Rfq Drop Down

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "rfqDropDown-purchase-quotation-through-ajax" })
	public @ResponseBody JsonResponse<Object> rfqDropDownList(@RequestParam String id, HttpSession session) {
		logger.info("Method : rfqDropDownList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "getRequisitionList?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : rfqDropDownList ends");
		return res;

	}

	// Email Data

	@SuppressWarnings("unchecked")

	@GetMapping("view-purchase-quotation-getEmailData")
	public @ResponseBody Object getEmailData(@RequestParam String id, String mailStatus, HttpSession session) {
		logger.info("Method :getEmailData starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-getEmailData?id=" + id + "&mailStatus=" + mailStatus + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getEmailData ends");
		return resp;
	}

	// Email

	@SuppressWarnings("unchecked")
	@GetMapping("view-purchase-quotation-email-ajax")
	public @ResponseBody JsonResponse<List<PurchaseQuotationModel>> approveEmailRfq(HttpSession session,
			@RequestParam String vendorNames, String vendorMails, String rfqId, String vendorCc, String vendorBcc,
			String vendorMsg, String url) throws IOException {
		logger.info("Method : approveEmailRfq starts" + url);
		byte[] encodeByte1 = Base64.getDecoder().decode(url.getBytes());
		logger.info("encode" + encodeByte1);
		String attachedUrl = new String(encodeByte1);

		List<String> bccAddress = null;
		List<String> ccAddress = null;

		System.out.println("attachedUrl===" + attachedUrl);
		JsonResponse<List<PurchaseQuotationModel>> response = new JsonResponse<List<PurchaseQuotationModel>>();

		try {
			logger.info("Method : fghj starts" + vendorCc);

			response = restTemplate.getForObject(env.getPurchaseUrl() + "sendEmailRfq?rfqId=" + rfqId + "&vendorNames="
					+ vendorNames + "&vendorMails=" + vendorMails + "&vendorCc=" + vendorCc + "&vendorBcc=" + vendorBcc
					+ "&vendorMsg=" + vendorMsg + "&url=" + attachedUrl, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<PurchaseQuotationModel> approvemail = mapper.convertValue(response.getBody(),
				new TypeReference<List<PurchaseQuotationModel>>() {
				});
		System.out.println("approvemail>>>>>" + approvemail);
		//List<String> imageAttachedUrls = new ArrayList<>();
		if (response.getCode().equals("success")) {
			response.setMessage("Success");
			for (PurchaseQuotationModel m : approvemail) {

				// String subject = vendorSubject;
				String subject = "Regarding RFQ Status";
				String message = m.getVendorMsg();
				String add = m.getVendorMails();
				List<String> toAddress = new ArrayList<>();

				/*
				 * try { logger.info("Document List: " + m.getDocumentList()); for
				 * (InventoryVendorDocumentModel n : m.getDocumentList()) { String imagePath =
				 * env.getBaseURL() + "document/document/" + n.getFileName();
				 * imageAttachedUrls.add(imagePath); logger.info("Added Image URL: " +
				 * imagePath); } } catch (Exception e) { e.printStackTrace(); }
				 */

				// System.out.println("imageAttachedUrls: " + imageAttachedUrls);

				if (add != null && !add.isEmpty()) {
					String[] addresses = add.split("[\\s,]+");
					//String[] addresses = add.split(",");
					for (String address : addresses) {
						toAddress.add(address.trim());
					}
				}
				System.out.println("toAddress: " + toAddress);
				String addCc = m.getVendorCc();

				if (addCc != null && !addCc.isEmpty()) {
					ccAddress = Arrays.asList(addCc.split(","));
				}

				String addBcc = m.getVendorBcc();

				if (addBcc != null && !addBcc.isEmpty()) {
					bccAddress = Arrays.asList(addBcc.split(","));
				}
 
				/*
				 * String urlName = "Quotation_" + rfqId; String rfqurlName = "Attachment_" +
				 * rfqId;
				 */
				String urlName = "RFQ_" + rfqId;
				// url = "https://www.africau.edu/images/default/sample.pdf";
				String attachedUrlafter = m.getUrl();

				/*
				 * if (imageAttachedUrls.isEmpty()) { System.out.println("Img Empty"); try {
				 * EmailAttachmentSender.sendEmailWithAttachmentsPdf(host, port, addresses,
				 * password, toAddress, ccAddress, subject, message, attachedUrl, urlName,
				 * bccAddress, rfqurlName); } catch (AddressException e) { e.printStackTrace();
				 * } catch (MessagingException e) { e.printStackTrace(); } } else {
				 * System.out.println("Img Available"); try {
				 * EmailAttachmentSender.sendEmailWithAttachmentsURL(host, port, addresses,
				 * password, toAddress, ccAddress, subject, message, attachedUrl, urlName,
				 * bccAddress, imageAttachedUrls, rfqurlName); } catch (AddressException e) {
				 * e.printStackTrace(); } catch (MessagingException e) { e.printStackTrace(); }
				 * }
				 */
				/*
				 * System.out.println("?Email Content Start"); System.out.println("host>>> " +
				 * host); System.out.println("port>>> " + port);
				 * System.out.println("addresses>>> " + addresses);
				 * System.out.println("password>>> " + password);
				 * System.out.println("toAddress>>> " + toAddress);
				 * System.out.println("ccAddress>>> " + ccAddress);
				 * System.out.println("subject>>> " + subject); System.out.println("message>>> "
				 * + message); System.out.println("attachedUrl>>> " + attachedUrl);
				 * System.out.println("urlName>>> " + urlName);
				 * System.out.println("bccAddress>>> " + bccAddress);
				 * System.out.println("?Email Content End");
				 */
				
				try {
					EmailAttachmentSender.sendEmailWithAttachmentsURL(host, port, username, password, toAddress,
							ccAddress, subject, message, attachedUrl, urlName, bccAddress);

				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.setCode(response.getMessage());
					response.setMessage("Unsuccess");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.setCode(response.getMessage());
					response.setMessage("Unsuccess");
				}
				 

			}
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}

		logger.info("response=====" + response);
		logger.info("Method : approveEmailRfq ends");
		return response;
	}

}
