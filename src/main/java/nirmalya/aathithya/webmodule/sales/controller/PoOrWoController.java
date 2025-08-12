package nirmalya.aathithya.webmodule.sales.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.PoOrWoModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller
@RequestMapping(value = { "sales/" })
public class PoOrWoController {
	Logger logger = LoggerFactory.getLogger(PoOrWoController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PoOrWoController poOrWoController;

	@GetMapping(value = { "/view-po-or-wo" })
	public String salesOrderDetails(Model model, HttpSession session) {
		logger.info("Method : poDetails starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
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
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
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
			DropDownModel[] brand = restTemplate.getForObject(
					env.getSalesUrl() + "get-sku-master-list?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> productList = Arrays.asList(brand);

			model.addAttribute("productList", productList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : poDetails ends");
		return "sales/view-po-or-wo";
	}

	// Sales Person AutoSearch
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-po-or-wo-get-salesperson-list" })
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

	// Sales Person Add
	@SuppressWarnings("unchecked")

	@PostMapping("view-po-or-wo-add-salesperson")
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
	// TCS Auto search

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-po-or-wo-get-tcs-list" })
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

//TCS Add
	@SuppressWarnings("unchecked")

	@PostMapping("view-po-or-wo-add-tcs")
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

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("view-po-or-wo-get-address") public @ResponseBody
	 * JsonResponse<CustomerNewModel> getCustomerAddress(@RequestParam String id,
	 * HttpSession session) { logger.info(id);
	 * logger.info("Method : getCustomerAddress starts");
	 * 
	 * JsonResponse<CustomerNewModel> jsonResponse = new
	 * JsonResponse<CustomerNewModel>();
	 * 
	 * try { jsonResponse = restTemplate.getForObject(env.getSalesUrl() +
	 * "getCustomerAddressById?id=" + id, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new
	 * TypeReference<CustomerNewModel>() { });
	 * 
	 * 
	 * jsonResponse.setBody(reimModel); if (jsonResponse.getMessage() != null &&
	 * jsonResponse.getMessage() != "") {
	 * jsonResponse.setCode(jsonResponse.getMessage());
	 * jsonResponse.setMessage("Unsuccess");
	 * 
	 * } else { jsonResponse.setMessage("Success"); }
	 * 
	 * logger.info("Method : getCustomerAddress ends");
	 * logger.info("EDITjsonResponse"+jsonResponse); return jsonResponse; }
	 */
	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-po-or-wo-add")
	public @ResponseBody JsonResponse<Object> addSalesPo(HttpSession session,
			@RequestBody List<PoOrWoModel> poOrWoModel) {
		logger.info("Method : addSalesPo starts");
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
		for (PoOrWoModel m : poOrWoModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		for (InventoryVendorDocumentModel a : poOrWoModel.get(0).getDocumentList()) {
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
							System.out.println("Image Name------------------>" + imageName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addSalesPo", poOrWoModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("a===" + resp);

		logger.info("Method : addSalesPo ends");

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

	@GetMapping("view-po-or-wo-through-ajax")
	public @ResponseBody Object viewsalesPo(@RequestParam String pageno,@RequestParam String fDate,@RequestParam String tDate, HttpSession session) {

		logger.info("Method :viewsalesPo starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesPo?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&pageno=" + pageno+"&userId="+userId + "&fDate=" + fDate + "&tDate=" + tDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewsalesPo ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	/*
	 * for editing using employee id
	 *
	 *
	 */
	@GetMapping(value = { "view-po-or-wo-edit-new" })
	public @ResponseBody List<PoOrWoModel> viewsalesPoEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewsalesPoEdit starts");
		logger.info(id);

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		List<PoOrWoModel> productList = new ArrayList<PoOrWoModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();

		if (id != null && id != "") {
			try {
				PoOrWoModel[] poOrWoModel = restTemplate.getForObject(
						env.getSalesUrl() + "viewsalesPoEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PoOrWoModel[].class);
				productList = Arrays.asList(poOrWoModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (PoOrWoModel m : poOrWoModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
					if (m.getPoDate() != null && m.getPoDate() != "") {
						m.setPoDate(DateFormatter.dateFormat(m.getPoDate(), dateFormat));
					}
					if (m.getExpectedShipmentDate() != null && m.getExpectedShipmentDate() != "") {
						m.setExpectedShipmentDate(DateFormatter.dateFormat(m.getExpectedShipmentDate(), dateFormat));
					}
					if (m.getSalesOrderDate() != null && m.getSalesOrderDate() != "") {
						m.setSalesOrderDate(DateFormatter.dateFormat(m.getSalesOrderDate(), dateFormat));
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
		logger.info("Method : viewsalesPoEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-po-or-wo-delete")
	public @ResponseBody JsonResponse<Object> deleteSalesPo(@RequestBody PoOrWoModel poOrWoModel, HttpSession session) {
		logger.info("Method : deleteSalesPo starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			poOrWoModel.setQutCreatedBy(userId);
			poOrWoModel.setOrganization(orgName);
			poOrWoModel.setOrgDivision(orgDivision);
		} catch (Exception e) {

		}

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "deleteSalesPo", poOrWoModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : deleteSalesPo Ends");
		return resp;
	}

	@GetMapping(value = { "view-po-or-wo-get-sodata" })
	public @ResponseBody List<PoOrWoModel> getSoData(@RequestParam String id, String skuid, HttpSession session) {
		logger.info("Method : getSoData starts");
		logger.info(id);

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		List<PoOrWoModel> productList = new ArrayList<PoOrWoModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();

		if (id != null && id != "") {
			try {
				PoOrWoModel[] poOrWoModel = restTemplate.getForObject(env.getSalesUrl() + "getSoData?id=" + id
						+ "&skuid=" + skuid + "&org=" + orgName + "&orgDiv=" + orgDivision, PoOrWoModel[].class);
				productList = Arrays.asList(poOrWoModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (PoOrWoModel m : poOrWoModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
					if (m.getPoDate() != null && m.getPoDate() != "") {
						m.setPoDate(DateFormatter.dateFormat(m.getPoDate(), dateFormat));
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
		logger.info("Method : getSoData ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	// Block Order

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("view-po-or-wo-blockeOrder-th-ajax") public @ResponseBody
	 * JsonResponse<List<PoOrWoModel>> blockSaleOrder(HttpSession session,
	 * 
	 * @RequestParam String blockeOrder, String referenceId,String sku) {
	 * 
	 * logger.info("Method : blockSaleOrder starts");
	 * JsonResponse<List<PoOrWoModel>> response = new
	 * JsonResponse<List<PoOrWoModel>>(); try { response =
	 * restTemplate.getForObject(env.getSalesUrl() + "blockSaleOrder?blockeOrder=" +
	 * blockeOrder + "&referenceId=" + referenceId + "&sku=" + sku,
	 * JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * if(response.getCode().equals("success")) { response.setMessage("Success"); }
	 * else { response.setCode(response.getMessage());
	 * response.setMessage("Unsuccess"); } logger.info("response=====" + response);
	 * logger.info("Method : blockSaleOrder ends"); return response; }
	 */

	@GetMapping(value = { "view-po-or-wo-createversion-new" })
	public @ResponseBody List<PoOrWoModel> viewsalesCreateversionEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewsalesCreateversionEdit starts");
		logger.info(id);

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		List<PoOrWoModel> productList = new ArrayList<PoOrWoModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();

		if (id != null && id != "") {
			try {
				PoOrWoModel[] poOrWoModel = restTemplate.getForObject(env.getSalesUrl()
						+ "viewsalesCreateversionEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PoOrWoModel[].class);
				productList = Arrays.asList(poOrWoModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (PoOrWoModel m : poOrWoModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
					if (m.getPoDate() != null && m.getPoDate() != "") {
						m.setPoDate(DateFormatter.dateFormat(m.getPoDate(), dateFormat));
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
		logger.info("Method : viewsalesCreateversionEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-po-or-wo-get-customer-list" })
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

	@SuppressWarnings("unchecked")
	@GetMapping("view-po-or-wo-get-address")
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

	@GetMapping("view-po-or-wo-shipping-address")
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

	@GetMapping("view-po-or-wo-shipping-dataedit")
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

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-po-or-wo-stateList" })
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

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-po-or-wo-add-cust-shippingaddress")
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

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("view-po-or-wo-item-get-product-list")
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

	@PostMapping(value = { "view-po-or-wo-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCat(Model model, @RequestBody String index,
			HttpSession session) {
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

	/*
	 * Item auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-po-or-wo-item-get-customer-list" })
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

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-po-or-wo-get-sku-by-product" })
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
	@PostMapping("/view-po-or-wo-get-total-list")
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

	@PostMapping("/view-po-or-wo-upload-fileprofuct")
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

	@PostMapping("view-po-or-wo-delete-fileproduct")
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
	@PostMapping("/view-po-or-wo-adds-customer")
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
	@GetMapping("view-po-or-wo-shippingdetails")
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
	@GetMapping(value = { "view-po-or-wo-get-sku-listing" })
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
	@PostMapping(value = { "view-po-or-wo-get-product-details" })
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
	@PostMapping("view-po-or-wo-products-delete")
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
	@GetMapping("view-po-or-wo-deletesku")
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
	@PostMapping(value = { "view-po-or-wo-get-sku-details" })
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
	@PostMapping("/view-po-or-wo-products-save")
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

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-po-or-wo-save-sku-dtls")
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
	@GetMapping("view-po-or-wo-get-quotation")
	public @ResponseBody JsonResponse<Object> getQuotationOnselectedCustomer(Model model, HttpSession session,
			@RequestParam String custId) {

		logger.info("Method : getQuotationOnselectedCustomer starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-quotation?custId=" + custId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			if ("success".equals(resp.getCode())) {
				List<String> quotationList = (List<String>) resp.getBody();
				model.addAttribute("quotationList", quotationList);
				System.out.println("Response For Quotation------------->" + quotationList);
			} else {
				model.addAttribute("quotationList", new ArrayList<>());
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getQuotationOnselectedCustomer ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-po-or-wo-get-quotation-details")
	public @ResponseBody JsonResponse<Object> getQuotationDetails(HttpSession session,
			@RequestParam String quotationId) {

		logger.info("Method : getQuotationDetails starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-quotation-details?quotationId=" + quotationId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			// Convert response body to JSONObject
			JSONObject responseBody = new JSONObject(resp.getBody().toString());
			JSONArray quotationData = responseBody.getJSONArray("quotationData");

			if (quotationData.length() > 0) {
				JSONObject firstQuotation = quotationData.getJSONObject(0);
//				JSONArray documentDetails = firstQuotation.has("documentDetails") ? firstQuotation.getJSONArray("documentDetails") : null;
				JSONArray documentDetails = null;
				if (firstQuotation.has("documentDetails")) {
				    Object obj = firstQuotation.get("documentDetails");
				    if (obj instanceof JSONArray) {
				        documentDetails = (JSONArray) obj;
				        
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
				    } else {
				        System.out.println("documentDetails is not a JSONArray, it is: " + obj.getClass().getSimpleName());
				    }
				}


				
				responseBody.put("quotationData", quotationData);
				resp.setBody(responseBody.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getQuotationDetails ends" + resp);
		return resp;
	}

	@GetMapping("view-po-or-wo-approve")
	public @ResponseBody Object approvePoWodata(@RequestParam String id,String poRef, HttpSession session) {

		logger.info("Method :approvePoWodata starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-approvePoWodata?poId=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision+"&poRef="+poRef, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-po-or-wo-stateListData" })
	public @ResponseBody JsonResponse<Object> getstateListData(@RequestParam String id) {
		logger.info("Method : getstateCusListData starts" + id);
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
		logger.info("Method : getstateCusListData ends");
		return res;
	}
//
	@SuppressWarnings("unchecked")
	@PostMapping("view-po-or-wo-add-shipping-address")
	public @ResponseBody JsonResponse<Object> addShippingAddressPO(@RequestBody DropDownModel data,
			HttpSession session) {
		logger.info("Method : addShippingAddressPO starts");
		
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
			
			resp = restTemplate.postForObject(env.getSalesUrl() + "add-shipping-addressPO", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method :addShippingAddressPO ends");
		return resp; 
	}
//
	@SuppressWarnings("unchecked")

	@GetMapping("view-po-or-wo-filter-data")
	public @ResponseBody Object viewsalesPoFilterData(@RequestParam String pageno,@RequestParam String fDate,@RequestParam String tDate, HttpSession session) {

		logger.info("Method :viewsalesPoFilterData starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesPo?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&pageno=" + pageno+"&userId="+userId + "&fDate=" + fDate + "&tDate=" + tDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewsalesPoFilterData ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}
}
