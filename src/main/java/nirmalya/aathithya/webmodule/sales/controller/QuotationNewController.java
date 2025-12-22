package nirmalya.aathithya.webmodule.sales.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.MailService;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.ScopeMatrixwebModel;
import nirmalya.aathithya.webmodule.util.NumberToWordsConverter;
import nirmalya.aathithya.webmodule.util.StringUtil;

@Controller
@RequestMapping(value = { "sales/" })
public class QuotationNewController {

	Logger logger = LoggerFactory.getLogger(QuotationNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	QuotationNewController quotationNewController;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	@Autowired
	MailService mailService;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    
	@GetMapping(value = { "/view-quotation" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : customerDetails starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		String org = "";
		String orgDiv = "";

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
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
			DropDownModel[] Collection = restTemplate.getForObject(env.getSalesUrl() + "getCollectionList",
					DropDownModel[].class);
			List<DropDownModel> collectionList = Arrays.asList(Collection);

			model.addAttribute("collectionList", collectionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] brand = restTemplate.getForObject(
					env.getSalesUrl() + "get-item-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> productList = Arrays.asList(brand);

			model.addAttribute("productList", productList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] bomItem = restTemplate.getForObject(
					env.getSalesUrl() + "get-bom-item-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> bomItemList = Arrays.asList(bomItem);

			model.addAttribute("bomItemList", bomItemList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] projectList = restTemplate.getForObject(
					env.getSalesUrl() + "getProjectAutoSearchList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> projectListt = Arrays.asList(projectList);
			model.addAttribute("projectList", projectListt);
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
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Quotation = restTemplate.getForObject(env.getSalesUrl() + "getQuotationTypeList",
					DropDownModel[].class);
			List<DropDownModel> quotationTypeList = Arrays.asList(Quotation);

			model.addAttribute("quotationTypeList", quotationTypeList);
		} catch (RestClientException e) {
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
			
			String userID = "";
			String userManager = "";
			try {
				userID = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userManager = (String) session.getAttribute("MANAGER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] emplist = restTemplate.getForObject(env.getPipeline() + "rest-getEmployeeList-mail?orgName="
					+ org + "&orgDivision=" + orgDiv + "&managerId=" + userManager + "&userId=" + userID,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplistsEvent", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		model.addAttribute("baseUrl", env.getBaseURL());
		logger.info("Method : customerDetails ends");
		return "sales/view-quotation";

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-quotation-get-salesperson-list" })
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
	@PostMapping(value = { "view-quotation-get-project-list" })
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

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-quotation-get-bomdetails" })
	public @ResponseBody JsonResponse<Object> getBomDetails(Model model, @RequestParam String id,
			@RequestParam String sku, HttpSession session) {
		logger.info("Method : getBomDetails starts");

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
			res = restTemplate.getForObject(env.getSalesUrl() + "getBomDetails?id=" + id + "&sku=" + sku + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getBomDetails ends");
		return res;
	}

	/*
	 * view
	 */
	@SuppressWarnings("unchecked")

	@GetMapping("view-quotation-through-ajax")
	public @ResponseBody Object getAllquotation(@RequestParam String pageno,@RequestParam String fDate,@RequestParam String tDate, HttpSession session) {

		logger.info("Method :getAllquotation starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getAllquotation?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno+"&userId="+userId + "&fDate=" + fDate + "&tDate=" + tDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllquotation ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("view-quotation-item-new-trough-ajax")
	public @ResponseBody List<QuotationNewModel> viewquotationItem(HttpSession session) {

		logger.info("Method :viewquotationItem starts");

		JsonResponse<List<QuotationNewModel>> resp = new JsonResponse<List<QuotationNewModel>>();

		try {

			resp = restTemplate.getForObject(env.getSalesUrl() + "getAllquotationItem", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<QuotationNewModel> quotationNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<QuotationNewModel>>() {
				});
		int count = 0;
		for (QuotationNewModel m : quotationNewModel) {
			count++;
			m.setSlNo(String.valueOf(count));
			// logger.info("QuotationNewModel@" +count);
		}

		resp.setBody(quotationNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewquotationItem ends");
		// logger.info("RESPONSE" + resp);
		return resp.getBody();
	}

	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-quotation-get-customer-list" })
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

	/*
	 * Item autosearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-quotation-item-get-customer-list" })
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

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-quotation-add")
	public @ResponseBody JsonResponse<Object> addquotationnew(HttpSession session,
			@RequestBody List<QuotationNewModel> quotationNewModel) {
		logger.info("Method : addquotation starts");

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
			e.printStackTrace();
		}

		for (QuotationNewModel m : quotationNewModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setQutValidDate(DateFormatter.inputDateFormat(m.getQutValidDate(), dateFormat));
		}
		for (InventoryVendorDocumentModel a : quotationNewModel.get(0).getDocumentList()) {

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
		logger.info("inventoryItemRequisitionModel===" + quotationNewModel);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addquotationnew", quotationNewModel,
					JsonResponse.class);
			logger.info("response**********************" + resp);

		} catch (Exception e) {

			e.printStackTrace();
		}
		// documentList.get(0).setDocumentList(docList);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addquotation ends");

		return resp;
	}

	// Save Quotation Draft

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-quotation-draftdata")
	public @ResponseBody JsonResponse<Object> addquotationdraft(HttpSession session,
			@RequestBody List<QuotationNewModel> quotationNewModel) {
		logger.info("Method : addquotationdraft starts");

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
			e.printStackTrace();
		}

		for (QuotationNewModel m : quotationNewModel) {
			logger.info("saddddddddddddddddd" + m);
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setQutValidDate(DateFormatter.inputDateFormat(m.getQutValidDate(), dateFormat));
		}
		for (InventoryVendorDocumentModel a : quotationNewModel.get(0).getDocumentList()) {
			logger.info("saddddddddddddddddd" + a);

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
		logger.info("inventoryItemRequisitionModel===" + quotationNewModel);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addquotationdraft", quotationNewModel,
					JsonResponse.class);
			logger.info("response**********************" + resp);

		} catch (Exception e) {

			e.printStackTrace();
		}
		// documentList.get(0).setDocumentList(docList);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addquotationdraft ends");

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

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("view-quotation-get-product-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryListModalQuot(HttpSession session) {
		logger.info("Method : getProductCategoryListModalQuot starts");

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
		logger.info("Method : getProductCategoryListModalQuot starts");
		return resp;
	}

	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "view-quotation-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCat(Model model, @RequestBody String index,
			BindingResult result) {
		logger.info("Method : getProductsByCat starts");

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
	 * 
	 * 
	 * for editing using employee id
	 *
	 *
	 */
	@GetMapping(value = { "view-quotation-edit-new" })
	public @ResponseBody List<QuotationNewModel> viewQuotationEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewQuotationEdit starts");
		logger.info(id);
		List<ScopeMatrixwebModel> smList = new ArrayList<ScopeMatrixwebModel>();
		List<QuotationNewModel> productList = new ArrayList<QuotationNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				QuotationNewModel[] quotationNewModel = restTemplate
						.getForObject(env.getSalesUrl() + "viewQuotationEdit?id=" + id, QuotationNewModel[].class);

				productList = Arrays.asList(quotationNewModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (QuotationNewModel m : quotationNewModel) {

					count++;
					m.setSlNo(String.valueOf(count));

					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
						m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

					}
				}
				smList = productList.get(0).getScopematrix();
				if (smList != null) {
					int ct = 0;
					for (ScopeMatrixwebModel n : smList) {
						ct++;
						n.setSlno(ct);
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
		// productList.get(0).setDocumentList(documentList);
		logger.info("documentList" + documentList);
		logger.info("Method : viewQuotationEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	// Edit Draft
	@GetMapping(value = { "view-quotation-draft-edit-new" })
	public @ResponseBody List<QuotationNewModel> viewQuotationDraftEdit(@RequestParam String draftId,
			HttpSession session) {
		logger.info("Method : viewQuotationDraftEdit starts");
		logger.info(draftId);
		List<ScopeMatrixwebModel> smList = new ArrayList<ScopeMatrixwebModel>();
		List<QuotationNewModel> productList = new ArrayList<QuotationNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (draftId != null && draftId != "") {
			try {
				QuotationNewModel[] quotationNewModel = restTemplate.getForObject(
						env.getSalesUrl() + "viewQuotationDraftEdit?draftId=" + draftId, QuotationNewModel[].class);

				productList = Arrays.asList(quotationNewModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (QuotationNewModel m : quotationNewModel) {

					count++;
					m.setSlNo(String.valueOf(count));

					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
						m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

					}
				}
				smList = productList.get(0).getScopematrix();
				if (smList != null) {
					int ct = 0;
					for (ScopeMatrixwebModel n : smList) {
						ct++;
						n.setSlno(ct);
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
		// productList.get(0).setDocumentList(documentList);
		logger.info("documentList" + documentList);
		logger.info("Method : viewQuotationDraftEdit ends");
		logger.info("@@@@@@@@" + productList);
		return productList;
	}

	// viewQuotationGetOrder
	@GetMapping(value = { "view-quotation-getOrder" })
	public @ResponseBody List<QuotationNewModel> viewQuotationGetOrder(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewQuotationGetOrder starts");
		logger.info(id);
		List<QuotationNewModel> productList = new ArrayList<QuotationNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				QuotationNewModel[] quotationNewModel = restTemplate
						.getForObject(env.getSalesUrl() + "viewQuotationGetOrder?id=" + id, QuotationNewModel[].class);

				productList = Arrays.asList(quotationNewModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (QuotationNewModel m : quotationNewModel) {

					count++;
					m.setSlNo(String.valueOf(count));

					String dateFormat = (String) session.getAttribute("DATEFORMAT");

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
		// productList.get(0).setDocumentList(documentList);
		logger.info("Method : viewQuotationGetOrder ends");
		return productList;
	}
	/*
	 * Delete
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = "view-quotation-delete") public @ResponseBody
	 * JsonResponse<Object> deleteItemQuotation(@RequestBody QuotationNewModel
	 * quotationNewModel, HttpSession session) {
	 * logger.info("Method : deleteItemQuotation starts");
	 * logger.info("quotationNewModel" + quotationNewModel); JsonResponse<Object>
	 * resp = new JsonResponse<Object>(); String userId = ""; try { userId =
	 * (String) session.getAttribute("USER_ID");
	 * quotationNewModel.setQutCreatedBy(userId); } catch (Exception e) {
	 * 
	 * } try {
	 * 
	 * resp = restTemplate.postForObject(env.getSalesUrl() + "deleteItemQuotation",
	 * quotationNewModel, JsonResponse.class); } catch (RestClientException e) {
	 * e.printStackTrace(); } if (resp.getMessage() != null && resp.getMessage() !=
	 * "") { resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess");
	 * 
	 * } else { resp.setMessage("Success"); } logger.info("delete@" + resp);
	 * logger.info("Method : deleteItemQuotation Ends"); return resp; }
	 */
	// Delete Draft

	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-quotation-draft-delete")
	public @ResponseBody JsonResponse<Object> deleteDraft(@RequestBody QuotationNewModel quotationNewModel,
			HttpSession session) {
		logger.info("Method : deleteDraft starts");
		logger.info("quotationNewModel" + quotationNewModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			quotationNewModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "deleteDraft", quotationNewModel, JsonResponse.class);
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
		logger.info("Method : deleteDraft Ends");
		return resp;
	}

	/*
	 * save image
	 */

	@PostMapping("view-quotation-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		logger.info("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		logger.info("img" + response);
		return response;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("view-quotation-po-add")
	public @ResponseBody JsonResponse<Object> addpoNo(Model model, HttpSession session,

			@RequestBody QuotationNewModel quotationNewModel) {
		logger.info("Method : addpoNo starts");
		logger.info("QuotationNewModel" + quotationNewModel);
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
			e.printStackTrace();
		}

		quotationNewModel.setQutCreatedBy(userId);
		quotationNewModel.setOrganization(organization);
		quotationNewModel.setOrgDivision(orgDivision);
		quotationNewModel.setPoDate(DateFormatter.inputDateFormat(quotationNewModel.getPoDate(), dateFormat));

		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				quotationNewModel.setFilePoSale(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addpoNo", quotationNewModel, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<QuotationNewModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<QuotationNewModel>>() {
					});

			if (quotationNewModel.getPoDate() != null && quotationNewModel.getPoDate() != "") {
				quotationNewModel.setPoDate(DateFormatter.inputDateFormat(quotationNewModel.getPoDate(), dateFormat));
			}

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			session.removeAttribute("quotationPFile");
			resp.setMessage("Success");
		}

		logger.info("Method : addpoNo ends");
		logger.info("resp" + resp);
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

	@SuppressWarnings("unchecked")

	@PostMapping("view-quotation-add-salesperson")
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
		if (quotationNewModel.getDobid() != "" && quotationNewModel.getDobid() != null) {
			quotationNewModel.setDobid(DateFormatter.inputDateFormat(quotationNewModel.getDobid(), dateFormat));
		}

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

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("view-quotation-get-address") public @ResponseBody
	 * JsonResponse<Object> getCustomerAddress(@RequestBody String customerId,
	 * HttpSession session) { logger.info("Method : getCustomerAddress starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * 
	 * 
	 * try { resp = restTemplate.getForObject(env.getSalesUrl() +
	 * "getCustomerAddressById?id=" + customerId , JsonResponse.class);
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * CustomerNewModel contactDetails = mapper.convertValue(resp.getBody(), new
	 * TypeReference<CustomerNewModel>() { });
	 * 
	 * resp.setBody(contactDetails); } catch (RestClientException e) {
	 * e.printStackTrace(); }
	 * 
	 * String message = resp.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { resp.setMessage("success"); }
	 * 
	 * logger.info("Method : getCustomerAddressById starts"+resp); return resp; }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-quotation-get-address")
	public @ResponseBody JsonResponse<CustomerNewModel> getCustomerAddress(@RequestParam String id,
			HttpSession session) {
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
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

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

	@PostMapping("view-quotation-add-cust-billingaddress")
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
	 * @PostMapping("view-quotation-add-cust-shippingaddress") public @ResponseBody
	 * JsonResponse<Object> addshippingaddress(@RequestBody CustomerNewModel
	 * customerNewModel, HttpSession session) {
	 * logger.info("Method : addshippingaddress starts"); String userId = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID");
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * customerNewModel.setCreatedBy(userId);
	 * 
	 * logger.info("ADDDDATA" + customerNewModel);
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
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
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-quotation-add-cust-shippingaddress")
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

	@PostMapping("view-quotation-upload-file1")
	public @ResponseBody JsonResponse<Object> uploadDocument(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		logger.info("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-quotation-get-tcs-list" })
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
	@PostMapping("view-quotation-add-tcs")
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
	@PostMapping("view-quotation-add-shipping-address")
	public @ResponseBody JsonResponse<Object> addShippingAddress(@RequestBody DropDownModel data,
			HttpSession session) {
		logger.info("Method : addShippingAddress starts");
		
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
			
			resp = restTemplate.postForObject(env.getSalesUrl() + "add-shipping-address", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method :addShippingAddress ends");
		return resp;
	}

	// approve
	@SuppressWarnings("unchecked")
	@GetMapping("view-quotation-approve-th-ajax")
	public @ResponseBody JsonResponse<List<DropDownModel>> approveItemQuatation(HttpSession session,
			@RequestParam String approveStatus, String quotationId, String reference, String url, String sub) {
		logger.info("Method : approveItemQuatation starts");
		byte[] encodeByte1 = Base64.getDecoder().decode(url.getBytes());
		String attachedUrl = (new String(encodeByte1));
		JsonResponse<List<DropDownModel>> response = new JsonResponse<List<DropDownModel>>();
		try {
			response = restTemplate.getForObject(env.getSalesUrl() + "approveItemQuatation?approveStatus="
					+ approveStatus + "&quotationId=" + quotationId + "&reference=" + reference, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");

		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : approveItemQuatation ends");
		return response;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-quotation-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getQuotationInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getQuotationInsertedId", JsonResponse.class);
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

	// NoAndaggridDetails

	@GetMapping(value = { "view-quotation-get-NoAndaggridDetails" })
	public @ResponseBody List<QuotationNewModel> getNoAndaggridDetails(HttpSession session) {
		logger.info("Method : getNoAndaggridDetails starts");
		List<QuotationNewModel> productList = new ArrayList<QuotationNewModel>();

		try {
			QuotationNewModel[] quotationNewModel = restTemplate
					.getForObject(env.getSalesUrl() + "getNoAndaggridDetails", QuotationNewModel[].class);

			productList = Arrays.asList(quotationNewModel);

			productList.forEach(s -> s.setSlNo(s.getSlNo()));

			int count = 0;

			for (QuotationNewModel m : quotationNewModel) {

				count++;
				m.setSlNo(String.valueOf(count));

				String dateFormat = (String) session.getAttribute("DATEFORMAT");

				if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

				}
				if (m.getQuotationDate() != null && m.getQuotationDate() != "") {
					m.setQuotationDate(DateFormatter.dateFormat(m.getQuotationDate(), dateFormat));

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getNoAndaggridDetails ends");
		return productList;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-quotation-get-QuotationNo" })
	public @ResponseBody JsonResponse<Object> getQuotationNo() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getQuotationNo", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getQuotationNo ends");
		return res;
	}

	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("view-quotation-delete")
	public @ResponseBody JsonResponse<Object> deletequotation(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deletequotation function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "deletequotation?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deletequotation function Ends");

		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-quotation-pdf-downloads")
	public void getQuotationPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("quotationId") String encodedParam1, @RequestParam("organization") String encodedParam2,
			@RequestParam("orgDivision") String encodedParam3, @RequestParam("userId") String encodedParam4) {

		logger.info("Method : getQuotationPdfDetails starts");

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String quotationId = (new String(encodeByte3));

		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String organization = (new String(encodeByte4));

		byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String orgDivision = (new String(encodeByte5));

		byte[] encodeByte6 = Base64.getDecoder().decode(encodedParam4.getBytes());
		String userId = (new String(encodeByte6));
		
		JsonResponse<List<QuotationNewModel>> jsonResponse = new JsonResponse<List<QuotationNewModel>>();
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			jsonResponse = restTemplate.getForObject(
					env.getSalesUrl() + "get-quotation-pdf?id=" + quotationId + "&org=" + organization + "&orgDiv=" + orgDivision,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<Map<String, Object>> resultList = null;
		try {
			resultList = mapper.readValue(jsonResponse.getBody().toString(), new TypeReference<List<Map<String, Object>>>() {});
		} catch (JsonParseException e2) {
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

        Map<String, Object> resultMap = resultList.get(0);
        
        Map<String,Object> quotDetails = (Map<String, Object>) resultMap.get("quotationDetails");
        Map<String,Object> customerDetails = (Map<String, Object>) resultMap.get("customerDetails");
        Map<String,Object> customerBillingAddress = (Map<String, Object>) resultMap.get("customerBillingAddress");
        
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        LocalDate date = LocalDate.parse(String.valueOf(quotDetails.get("quotation_date")), inputFormatter);
        String formattedDate = date.format(outputFormatter);
        
        quotDetails.put("quotation_date", formattedDate);
        
        if(!quotDetails.isEmpty() && quotDetails.get("quotation_description") != null) {
        	quotDetails.put("quotation_description", StringUtil.sanitizeXmlContent(quotDetails.get("quotation_description").toString()));
        }
        if(!quotDetails.isEmpty() && quotDetails.get("quotation_TermCondition") != null) {
        	quotDetails.put("quotation_TermCondition", StringUtil.sanitizeXmlContent(quotDetails.get("quotation_TermCondition").toString()));
        }
        
        String projectName = "--";
        if(!quotDetails.isEmpty() && quotDetails.get("quotation_project") != null && quotDetails.get("quotation_project") != "") {
        	projectName = quotDetails.get("quotation_project").toString();
        }
        
        data.put("quotDetails", quotDetails);
        data.put("customerDetails", customerDetails);
        data.put("customerBillingAddress", customerBillingAddress);
        data.put("projectName", projectName);

        String productDetailsStr = (String) resultMap.get("productDetails");
        List<Map<String, Object>> productDetails = null;
		try {
			productDetails = mapper.readValue(productDetailsStr, new TypeReference<List<Map<String, Object>>>() {});
		} catch (JsonParseException e2) {
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		System.out.println("productDetails=="+productDetails);
        resultMap.put("productDetails", productDetails);
        
        Double totalPrice = 0.0;
        String totalPriceInWords = "";
        for(Map<String,Object> m : productDetails) {
        	if(!m.containsKey("unitPrice")) {
        		m.put("unitPrice", "");
        	}
        	if(!m.containsKey("unitName")) {
        		m.put("unitName", "");
        	}
        	if(!m.containsKey("quantity")) {
        		m.put("quantity", "");
        	}
        	if(!m.containsKey("lineTotal")) {
        		m.put("lineTotal", "");
        	} else {
        		totalPrice += Double.valueOf(m.get("lineTotal").toString());
        	}
        }
        
        if(totalPrice != null) {
        	BigDecimal amount = new BigDecimal(Double.valueOf(totalPrice));
        	totalPriceInWords = NumberToWordsConverter.convertToWords(amount);
        }
        
        data.put("productDetails", productDetails);
        data.put("totalPrice", totalPrice);
        data.put("totalPriceInWords", totalPriceInWords);

        String bomDetailsStr = (String) resultMap.get("bomDetails");
        List<Map<String, Object>> bomDetails = null;
		try {
			bomDetails = mapper.readValue(bomDetailsStr, new TypeReference<List<Map<String, Object>>>() {});
		} catch (JsonParseException e2) {
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
        data.put("bomDetails", bomDetails);
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		String sign = "";
		String stamp = "";
		data.put("logo", logo);
		data.put("sign", sign);
		data.put("stamp", stamp);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=Quotation_" + quotationId + ".pdf");
		File file;
		File file2;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("sales/quotation-pdf-download", data);
			file2 = pdfGeneratorUtil.createPdf("sales/quotation-pdf-download-cover-page", data);
			
			
			File mergedPdfFile = File.createTempFile("MergedQuotation", ".pdf");

	        PdfDocument pdfDoc1 = new PdfDocument(new PdfReader(file2));
	        PdfDocument pdfDoc2 = new PdfDocument(new PdfReader(file));
	        PdfDocument mergedPdf = new PdfDocument(new PdfWriter(mergedPdfFile));

	        PdfMerger merger = new PdfMerger(mergedPdf);
	        merger.merge(pdfDoc1, 1, pdfDoc1.getNumberOfPages());
	        merger.merge(pdfDoc2, 1, pdfDoc2.getNumberOfPages());

	        pdfDoc1.close();
	        pdfDoc2.close();
	        mergedPdf.close();
			
			
			InputStream in = new FileInputStream(mergedPdfFile);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		logger.info("REsp" + jsonResponse);
		logger.info("Method : getQuotationPdfDetails ends");
		// return jsonResponse;
	}

	// getSacCode
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-quotation-getSacCode" })
	public @ResponseBody JsonResponse<Object> getSacCode() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSacCode", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getSacCode ends");
		return res;
	}

	/* GST Integration */
	@GetMapping(value = { "view-quotation-getgstno" })
	public @ResponseBody JsonResponse<Object> getGstData(Model model, @RequestParam("id") String id,
			HttpSession session) {

		logger.info("Method : getGstData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = " ";
			url = "https://commonapi.mastersindia.co/commonapis/searchgstin?gstin=" + id;

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Authorization", "Bearer 0ab31ef7392227173c6e8d34195e86d5eb0da1e9");
			requestHeaders.add("client_id", "JarZChUcsytSBbnkpt");

			HttpEntity<String> httpEntity = new HttpEntity<String>(requestHeaders);
			ResponseEntity<String> st = null;
			String err_code = null;

			try {
				st = null;
				st = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
			} catch (Exception e) {
				err_code = e.getMessage();
			}

			JSONObject obres = null;
			try {
				obres = new JSONObject(st.getBody());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.setBody(st.getBody());

		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : getGstData ends" + resp);
		return resp;
	}

	/*
	 * view draft
	 */
	@SuppressWarnings("unchecked")

	@GetMapping("view-quotation-draft-ajax")
	public @ResponseBody List<QuotationNewModel> viewquotationDraft(HttpSession session) {

		logger.info("Method :viewquotationDraft starts");

		JsonResponse<List<QuotationNewModel>> resp = new JsonResponse<List<QuotationNewModel>>();
		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userId);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "viewquotationDraft", empModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<QuotationNewModel> quotationNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<QuotationNewModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (QuotationNewModel a : quotationNewModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}

			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

			if (a.getPoDate() != null && a.getPoDate() != "") {
				a.setPoDate(DateFormatter.dateFormat(a.getPoDate(), dateFormat));
			}
			if (a.getsOrderDate() != null && a.getsOrderDate() != "") {
				a.setsOrderDate(DateFormatter.dateFormat(a.getsOrderDate(), dateFormat));
			}

			if (a.getSaleOrderDate() != null && a.getSaleOrderDate() != "") {
				a.setSaleOrderDate(DateFormatter.dateFormat(a.getSaleOrderDate(), dateFormat));
			}
			// logger.info(quotationNewModel);

		}

		resp.setBody(quotationNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewquotationDraft ends");
		logger.info("RESPONSE" + resp);
		return resp.getBody();
	}
	
	@GetMapping(value = { "view-quotation-revision-new" })
	public @ResponseBody List<QuotationNewModel> viewQuotationRevision(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewQuotationRevision starts");
		logger.info(id);
		List<ScopeMatrixwebModel> smList = new ArrayList<ScopeMatrixwebModel>();
		List<QuotationNewModel> productList = new ArrayList<QuotationNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				QuotationNewModel[] quotationNewModel = restTemplate
						.getForObject(env.getSalesUrl() + "viewQuotationRevision?id=" + id, QuotationNewModel[].class);

				productList = Arrays.asList(quotationNewModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (QuotationNewModel m : quotationNewModel) {

					count++;
					m.setSlNo(String.valueOf(count));

					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
						m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

					}
				}
				smList = productList.get(0).getScopematrix();
				if (smList != null) {
					int ct = 0;
					for (ScopeMatrixwebModel n : smList) {
						ct++;
						n.setSlno(ct);
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
		logger.info("Method : viewQuotationRevision ends");
		return productList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-quotation-get-variant-dtls" })
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

	@SuppressWarnings("unchecked")
	@PostMapping("/view-quotation-get-total-list")
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

	@PostMapping("/view-quotation-upload-fileprofuct")
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

	@GetMapping("view-quotation-get-scopematrix-details")
	public @ResponseBody List<ScopeMatrixwebModel> getScopeMatrixDetails(HttpSession session) {

		logger.info("Method :getScopeMatrixDetails startsssssssssssssssssssssss");
		JsonResponse<List<ScopeMatrixwebModel>> resp = new JsonResponse<List<ScopeMatrixwebModel>>();
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
					env.getSalesUrl() + "rest-getScopeMatrixDetails?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ScopeMatrixwebModel> scopeMatrixwebModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<ScopeMatrixwebModel>>() {
					});
			int count = 0;
			for (ScopeMatrixwebModel m : scopeMatrixwebModel) {
				logger.info(m.getScopeMatrixDesc());
				count++;
				m.setSlno(count);
			}
			resp.setBody(scopeMatrixwebModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :getScopeMatrixDetails ends");
		return resp.getBody();
	}

	@GetMapping("view-quotation-shipping-address")
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

	// view-customer-stateList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-quotation-stateList" })
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

	@GetMapping("view-quotation-shipping-dataedit")
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

	@PostMapping("view-quotation-item-image-upload-file")
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
	@GetMapping("view-quotation-email-ajax")
	public @ResponseBody JsonResponse<List<QuotationNewModel>> sendEmailQuotation(HttpSession session,
			@RequestParam String customerNames, String customerCns, String customerMails, String quotationId,
			String customerCc, String customerBcc, String customerMsg, String url) {
		logger.info("Method : sendEmailQuotation starts");
		byte[] encodeByte1 = Base64.getDecoder().decode(url.getBytes());

		String attachedUrl = (new String(encodeByte1));
		JsonResponse<List<QuotationNewModel>> response = new JsonResponse<List<QuotationNewModel>>();

		try {

			response = restTemplate.getForObject(env.getSalesUrl() + "sendEmailQuotation?quotationId=" + quotationId
					+ "&customerNames=" + customerNames + "&customerCns=" + customerCns + "&customerMails="
					+ customerMails + "&customerCc=" + customerCc + "&customerBcc=" + customerBcc + "&customerMsg="
					+ customerMsg + "&url=" + attachedUrl, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<QuotationNewModel> approvemail = mapper.convertValue(response.getBody(),
				new TypeReference<List<QuotationNewModel>>() {
				});

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
			for (QuotationNewModel m : approvemail) {
				String subject = "Regarding The Quotation Status";
				String message = m.getCustomerMsg();
				String add = m.getCustomerMails();
				List<String> toAddress = new ArrayList<String>();
				// toAddress.add(add);

				if (add != null && !add.isEmpty()) {
					// Split the add string using spaces, commas, or a combination of both
					String[] addresses = add.split("[\\s,]+");

					// Add each split email address to the toAddress list
					for (String address : addresses) {
						toAddress.add(address.trim()); // Trim to remove any leading or trailing whitespace
					}
				}

				String addCc = m.getCustomerCc();
				List<String> ccAddress = new ArrayList<String>();
				if (addCc != null && !addCc.isEmpty()) {
					// ccAddress = Arrays.asList(addCc.split(","));
					String[] addressescc = addCc.split("[\\s,]+");
					for (String addressc : addressescc) {
						ccAddress.add(addressc.trim()); // Trim to remove any leading or trailing whitespace
					}

				}

				/*
				 * String addCc = m.getVendorCc(); List<String> ccAddress = new
				 * ArrayList<String>(); ccAddress.add(addCc);
				 */

				String addBcc = m.getCustomerBcc();
				List<String> bccAddress = new ArrayList<String>();
				if (addBcc != null && !addBcc.isEmpty()) {
					// ccAddress = Arrays.asList(addCc.split(","));
					String[] addressesbcc = addBcc.split("[\\s,]+");
					for (String addressb : addressesbcc) {
						bccAddress.add(addressb.trim()); // Trim to remove any leading or trailing whitespace
					}

				}
	 
				String urlName = "Quotation_" + quotationId;

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

		logger.info("Method : sendEmailQuotation ends");
		return response;
	}

	// Search

	@SuppressWarnings("unchecked")
	@GetMapping("view-quotation-view-search")
	public @ResponseBody Object quotationDataViewSearch(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :quotationDataViewSearch starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-quotationDataViewSearch?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&searchValue=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :quotationDataViewSearch ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-quotation-get-item-bySku")
	public @ResponseBody Object getItemdDtailsBySku(@RequestParam String id, HttpSession session) {
		logger.info("Method :getItemdDtailsBySku starts");

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
					env.getSalesUrl() + "get-item-bySkuId?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getItemdDtailsBySku ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-quotation-get-sku-list")
	public @ResponseBody Object getSKUList(@RequestParam String id, HttpSession session) {
		logger.info("Method :getItemdDtailsBySku starts");

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
					env.getSalesUrl() + "get-sku-list?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getItemdDtailsBySku ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-quotation-get-preview-data")
	public @ResponseBody JsonResponse<Object> getPreviewData(@RequestParam String id, HttpSession session) {
		logger.info("Method :getPreviewData starts");

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
					env.getSalesUrl() + "get-quotation-pdf?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getPreviewData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-quotation-pdf")
	public void getViewQuotationPDF(HttpServletResponse response, Model model, HttpSession session, String id) {
		logger.info("Method : getViewQuotationPDF starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(
					env.getSalesUrl() + "get-quotation-pdf?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> request = new HashMap<>();
		request.put("content", resp.getBody());
		String responsedata = "";

		responsedata = restTemplate.postForObject("http://192.168.0.107:3001/api/v1/pdf/quatation-bom", request,
				String.class);
		// responsedata =
		// restTemplate.postForObject("https://service-node.nerp.in/api/v1/pdf/getpdfchallanpptl",
		// request, String .class);

		JSONObject resps = new JSONObject(responsedata);
		String outputPathPo = env.getFileUploadDocumenttUrl() + "quotation-pdf.pdf";
		System.out.println("outputPath" + outputPathPo);

		try {
			// Decode the base64 data.
			byte[] pdfBytes = Base64.getDecoder().decode(resps.getString("data"));

			// Create a new File object for the output path.
			File outputFile = new File(outputPathPo);

			// Write the PDF bytes to the output file.
			try (FileOutputStream fos = new FileOutputStream(outputFile)) {
				fos.write(pdfBytes);
			}
			System.out.println("PDF saved to: " + outputPathPo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Path pdfPath = Paths.get(env.getFileUploadDocumenttUrl() + "quotation-pdf.pdf");
		byte[] pdf = null;
		try {
			pdf = Files.readAllBytes(pdfPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + "quotation-pdf.pdf");
		try {
			response.getOutputStream().write(pdf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Documents merged");

		try {
			File file4 = new File(env.getFileUploadDocumenttUrl() + "quotation-pdf.pdf");
			file4.delete();
			System.out.println("Files deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getViewQuotationPDF ends");
	}
//
	@SuppressWarnings("unchecked")

	@GetMapping("view-quotation-filter-data")
	public @ResponseBody Object getAllquotationFilterData(@RequestParam String pageno,@RequestParam String fDate,@RequestParam String tDate, HttpSession session) {

		logger.info("Method :getAllquotationFilterData starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-getAllquotation?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno+"&userId="+userId + "&fDate=" + fDate + "&tDate=" + tDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllquotationFilterData ends");

		return resp;
	}
}
