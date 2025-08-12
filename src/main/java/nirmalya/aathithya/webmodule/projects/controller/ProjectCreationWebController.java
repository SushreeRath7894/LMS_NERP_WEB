package nirmalya.aathithya.webmodule.projects.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.nio.file.Path;
import java.util.Arrays;
import java.nio.file.Paths;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectFileuploadModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectShippingModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseIndentModel;

@Controller
@RequestMapping(value = "projects")
public class ProjectCreationWebController {
	Logger logger = LoggerFactory.getLogger(ProjectCreationWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "create-project" })
	public String createProject(Model model, HttpSession session) {
		logger.info("Method : createProject starts");
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// for project country drop down

		try {
			DropDownModel[] country = restClient.getForObject(env.getProjects() + "rest-project-country-list",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// for billing country drop down
		try {
			DropDownModel[] country = restClient.getForObject(env.getProjects() + "rest-project-billing-country-list",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// for shipping country drop down

		try {
			DropDownModel[] country = restClient.getForObject(env.getProjects() + "rest-project-shipping-country-list",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		

		try {
			DropDownModel[] mode = restClient.getForObject(env.getPurchaseUrl() + "getModeListForPurchaseProduct",
					DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] hsnCode = restClient.getForObject(env.getMasterUrl() + "getHSNCodeListForProduct",
					DropDownModel[].class);
			List<DropDownModel> hsnCodeList = Arrays.asList(hsnCode);

			model.addAttribute("hsnCodeList", hsnCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restClient
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] vendor = restClient.getForObject(
					env.getMasterUrl() + "getVendorListForProductWise?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendor);

			model.addAttribute("vendorList", vendorList);
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
			DropDownModel[] crop = restClient.getForObject(env.getProjects() + "rest-project-type-list",
					DropDownModel[].class);
			List<DropDownModel> cropList = Arrays.asList(crop);

			model.addAttribute("projectTypeList", cropList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : createProject ends");
	//	return "projects/projectCreation";
		return "projects/project-Creation-New";
	}

	// FOR PROJECT CREATION ADD STARTS

	@SuppressWarnings("unchecked")
	@PostMapping("/create-project-add")
	public @ResponseBody JsonResponse<Object> addPrjCreation(@RequestBody List<ProjectCreationWebModel> prjCreation,
			HttpSession session) {
		logger.info("Method : addPrjcreation starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<ProjectCreationWebModel> documentList = new ArrayList<ProjectCreationWebModel>();
		List<ProjectFileuploadModel> docList = new ArrayList<ProjectFileuploadModel>();

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

		for (ProjectCreationWebModel m : prjCreation) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}

		for (ProjectFileuploadModel a : prjCreation.get(0).getDocumentList()) {

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
			resp = restClient.postForObject(env.getProjects() + "rest-addPrjCreation", prjCreation, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addPrjcreation end" + resp);
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

	// FOR PROJECT CREATION ADD ENDS

	// FOR PROJECT CREATION VIEW STARTS

	@SuppressWarnings("unchecked")
	@GetMapping("create-project-view")
	public @ResponseBody Object viewProjectCreation(HttpSession session) {

		logger.info("Method :viewProjectCreation starts");
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
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-view-project-creation" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :viewProjectCreation ends");

		return resp;
	}

	// FOR PROJECT CREATION EDIT STARTS
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "create-project-edit" })
		public @ResponseBody JsonResponse<ProjectCreationWebModel> projectEdit(Model model, @RequestParam String id,
				HttpSession session) {
			logger.info("Method : projectEdit starts" + id);
			List<ProjectCreationWebModel> productList = new ArrayList<ProjectCreationWebModel>();
			List<ProjectFileuploadModel> documentList = new ArrayList<ProjectFileuploadModel>();
			JsonResponse<ProjectCreationWebModel> jsonResponse = new JsonResponse<ProjectCreationWebModel>();

			// String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			// System.out.println("date" + dateFormat);
			ProjectCreationWebModel product = new ProjectCreationWebModel();
			ObjectMapper mapper = new ObjectMapper();

			try {
				jsonResponse = restClient.getForObject(env.getProjects() + "edit-projectscreation?id=" + id,
						JsonResponse.class);

				// productList = Arrays.asList(purchaseOrderModel);

				product = mapper.convertValue(jsonResponse.getBody(), new TypeReference<ProjectCreationWebModel>() {

				});

				// int count = 0;

				if (productList != null) {
					documentList = product.getDocumentList();
					if (documentList != null) {
						for (ProjectFileuploadModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx") || extension[1].equals("csv")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " + m.getFileName()
												+ "></i> ";

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

					product.setDocumentList(documentList);
					if (product.getShippingList().size() > 0) {
						int c = 0;
						for (ProjectShippingModel a : product.getShippingList()) {
							c = c + 1;
							a.setSlNo(c);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			jsonResponse.setBody(product);

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("Method : editProductInfo ends");
			return jsonResponse;
		}

	// FOR PROJECT CREATION DELETE STARTS

	@SuppressWarnings("unchecked")
	@PostMapping("create-project-delete")
	public @ResponseBody JsonResponse<Object> deleteProjectCreation(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteProjectCreation function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "deleteProjectCreation?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteProjectCreation function Ends");

		return res;
	}

	// FOR PROJECT CREATION DELETE STARTS

	/*
	 * function for project drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "create-project-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForProject(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForProject starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-project-state-list?id=" + tCountry,
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
		logger.info("Method : getStateNameForProject ends" + res);
		return res;

	}

	/*
	 * function for billing drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "create-project-billing-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForBilling(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForBilling starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-project-billing-state-list?id=" + tCountry,
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
		logger.info("Method : getStateNameForBilling ends" + res);
		return res;

	}

	/*
	 * function for shipping drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "create-project-shipping-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForShipping(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForShipping starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-project-shipping-state-list?id=" + tCountry,
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
		logger.info("Method : getStateNameForShipping ends" + res);
		return res;

	}

	// getting project state id on edit
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-state-list-project" })
	public @ResponseBody JsonResponse<Object> getProjectStateList(@RequestParam String id) {
		logger.info("Method : getProjectStateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-getProjectStateList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("state" + res);
		logger.info("Method : getProjectStateList ends");
		return res;
	}

	// getting billing state id on edit
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-state-list-billing" })
	public @ResponseBody JsonResponse<Object> getBillingStateList(@RequestParam String id) {
		logger.info("Method : getBillingStateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-getBillingStateList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("state" + res);
		logger.info("Method : getBillingStateList ends");
		return res;
	}

	// getting shipping state id on edit
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-state-list-shipping" })
	public @ResponseBody JsonResponse<Object> getShippingStateList(@RequestParam String id) {
		logger.info("Method : getShippingStateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-getShippingStateList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("state" + res);
		logger.info("Method : getShippingStateList ends");
		return res;
	}
	//call Req
	
	@SuppressWarnings("unchecked")
	@GetMapping("create-project-Requision")
	public @ResponseBody Object viewProjectRequision(HttpSession session,@RequestParam String id) {

		logger.info("Method :viewProjectRequision starts");
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
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-viewProjectRequision" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectRequision--" + resp);
		logger.info("Method :viewProjectRequision ends");

		return resp;
	}
	
	//FOR PO/WO
	
	@SuppressWarnings("unchecked")
	@GetMapping("create-project-Po")
	public @ResponseBody Object viewProjectPo(HttpSession session,@RequestParam String id) {

		logger.info("Method :viewProjectPo starts");
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
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-viewProjectPo" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectPo--" + resp);
		logger.info("Method :viewProjectPo ends");

		return resp;
	}
	
	

	//FOR MaterialIssue
	
	@SuppressWarnings("unchecked")
	@GetMapping("create-project-MaterialIssue")
	public @ResponseBody Object viewProjectMaterialIssue(HttpSession session,@RequestParam String id) {

		logger.info("Method :viewProjectMaterialIssue starts");
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
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-viewProjectMaterialIssue" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectMaterialIssue--" + resp);
		logger.info("Method :viewProjectMaterialIssue ends");

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-product-get-sku-listing" })
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
			res = restClient.getForObject(env.getProjects() + "getProjectProductSKUListing?type=" + type + "&orgName="
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
	
	// PRODUCT LISTING VIEW
	
	@GetMapping(value = { "/create-project-ProductList" })
	public String projectInvoiceDetails(Model model, HttpSession session, @RequestParam String id) {
		logger.info("Method : projectProductListing starts");

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
			DropDownModel[] mode = restClient.getForObject(env.getPurchaseUrl() + "getModeListForPurchaseProduct",
					DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] hsnCode = restClient.getForObject(env.getMasterUrl() + "getHSNCodeListForProduct",
					DropDownModel[].class);
			List<DropDownModel> hsnCodeList = Arrays.asList(hsnCode);

			model.addAttribute("hsnCodeList", hsnCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restClient
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] vendor = restClient.getForObject(
					env.getMasterUrl() + "getVendorListForProductWise?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendor);

			model.addAttribute("vendorList", vendorList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : projectProductListing ends");
		return "projects/view-product-listing.html";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "create-project-ProductList-get-product-details" })
	public @ResponseBody JsonResponse<ProductMasterModel> getProductDetails(Model model, @RequestBody String tCountry,
			BindingResult result,HttpSession session) {
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
			res = restClient.getForObject(env.getMasterUrl() + "getProductDetailsById?id=" + tCountry+"&orgName=" + orgName + "&orgDiv=" + orgDivision,
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
	@PostMapping("create-project-ProductList-add-brand")
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
			resp.setCode(resp.getCode());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :addbrandDetails ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-ProductList-get-sku-by-product" })
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
		logger.info("Method : getSKUListingById ends"+res.getBody());
		return res.getBody();

	}
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-ProductList-get-purchase-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUPurchaseListing(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getSKUPurchaseListing starts"+id);

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
		logger.info("Method : getSKUPurchaseListing ends"+res.getBody());
		return res.getBody();

	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-ProductList-get-brandList" })
	public @ResponseBody JsonResponse<Object> getBrandList(@RequestParam String id,HttpSession session) {
		logger.info("Method : getBrandList starts"+ id);
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
		logger.info("Method : orgName starts"+ orgName);
		logger.info("Method : orgDivision starts"+ orgDivision);
		try {		
		
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "getBrandList?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
			//res = restTemplate.getForObject(env.getPurchaseUrl() + "getBrandList?orgName=" + orgName + "&orgDivision=" + orgDivision,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getBrandList ends");
		return resp;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("create-project-ProductList-save")
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
			resp.setCode("");
		}

		logger.info("Method : saveProductMaster starts");
		return resp;
	}

	/*
	 * @SuppressWarnings({ "unchecked" })
	 * 
	 * @PostMapping("create-project-ProductList-save-sku-dtls") public @ResponseBody
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
	 * product.setOrganizationDivision(orgDivision);
	 * 
	 * MultipartFile inputFile = (MultipartFile)
	 * session.getAttribute("quotationPFile1"); byte[] bytes; String imageName =
	 * null;
	 * 
	 * if (inputFile != null) { try { bytes = inputFile.getBytes(); String[]
	 * fileType = inputFile.getContentType().split("/"); imageName =
	 * saveAllImage1(bytes, fileType[1]);
	 * 
	 * product.setDocName(imageName); } catch (IOException e1) {
	 * e1.printStackTrace(); } } try { resp =
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
//
	private String saveAllImage1(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage1 starts");

		String imageName = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("png") || ext.contentEquals("jpg") || ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveAllImage1: "+e.getMessage());
		}
		logger.info("Method : saveAllImage1 ends");
		return imageName;
		
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("create-project-ProductList-save-purchase-dtls")
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
	@PostMapping(value = { "create-project-ProductList-get-sku-details" })
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
	@PostMapping(value = { "create-project-ProductList-get-purchase-details-edit" })
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
	@PostMapping(value = { "create-project-ProductList-get-variant-dtls" })
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
	@GetMapping("create-project-product-edit")
	public @ResponseBody Object getprojectProductEdit(@RequestParam String id,HttpSession session) {

		logger.info("Method :viewProductSku starts");
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
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-project-product-edit?id="+id + "&userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getprojectProductEdit--" + resp);
		logger.info("Method :getprojectProductEdit ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("create-project-save-productsku")
	public @ResponseBody JsonResponse<Object> saveproductsku(@RequestBody ProjectCreationWebModel prjCreation,
			HttpSession session) {
		logger.info("Method : saveproductsku starts" + prjCreation);

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

			prjCreation.setCreatedBy(userId);
			prjCreation.setOrganizationName(organization);
			prjCreation.setOrganizationDivision(orgDivision);

		

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveproductsku", prjCreation, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveproductsku end" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("create-project-ProductSku-view")
	public @ResponseBody Object viewProductSku(HttpSession session) {

		logger.info("Method :viewProductSku starts");
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
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-viewProductSku" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProductSku--" + resp);
		logger.info("Method :viewProductSku ends");

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("create-project-deleteSku")
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
	@PostMapping("create-project-ProductList-add-color")
	public @ResponseBody JsonResponse<Object> addcolorDetails(@RequestBody ProductMasterModel productMasterModel,
			HttpSession session) {
		logger.info("Method : addcolorDetails starts");
		
		

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

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addcolorDetails", productMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :addcolorDetails ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-getValidation" })
	public @ResponseBody JsonResponse<Object> getValidation(@RequestParam String modelID,@RequestParam String color,
			@RequestParam String brand,HttpSession session) {
		logger.info("Method : getValidation starts"+ modelID + color + brand);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {		
			resp = restTemplate.getForObject(env.getMasterUrl() + "getValidation?modelID=" + modelID + "&color=" + color +
					"&brand=" + brand,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getValidation ends"+resp);
		return resp;
	}
	
	@PostMapping("create-project-ProductList-upload-file1")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile1", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("uploadFile: "+e.getMessage());
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "create-project-ProductList-get-colorList" })
	public @ResponseBody JsonResponse<Object> getColorList(@RequestParam String id,HttpSession session) {
		logger.info("Method : getColorList starts"+ id);
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
		logger.info("Method : orgName starts"+ orgName);
		logger.info("Method : orgDivision starts"+ orgDivision);
		try {		
		
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "getColorList?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
			//res = restTemplate.getForObject(env.getPurchaseUrl() + "getBrandList?orgName=" + orgName + "&orgDivision=" + orgDivision,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getColorList ends");
		return resp;
	}
}
