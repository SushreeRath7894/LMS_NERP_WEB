package nirmalya.aathithya.webmodule.sales.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesPackagesModel;

@Controller
@RequestMapping(value = { "sales/" })
public class SalesPackagesController {
	Logger logger = LoggerFactory.getLogger(SalesPackagesController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SalesPackagesController salesPackagesController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/view-packages" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : customerDetails starts");
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
			DropDownModel[] uom = restTemplate.getForObject(env.getSalesUrl() + "getPackagingtypeList",
					DropDownModel[].class);
			List<DropDownModel> packTypeList = Arrays.asList(uom);

			model.addAttribute("packTypeList", packTypeList);
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
					env.getSalesUrl() + "get-item-list?org=" + organization + "&orgDiv=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> productList = Arrays.asList(brand);

			model.addAttribute("productList", productList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : customerDetails ends");
		return "sales/view-packages";
	}

	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-packages-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
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
			res = restTemplate.getForObject(env.getSalesUrl() + "getCustomerListByAutoSearch?id=" + searchValue+ "&org="
					+ orgName + "&orgDiv=" + orgDivision,
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
		// logger.info("RESPONSE@@" + res);
		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("view-packages-add-cust-billingaddress")
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

	

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-packages-stateList" })
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

	// Add New Customer
	@SuppressWarnings("unchecked")
	@PostMapping("view-packages-adds")
	public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {

		logger.info("Method : addAccountCustomer starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web AccountModel ======================" + customerNewModel);
		try {
			String userId = "";
			String organization=""; 
			String orgDivision="";
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
	@GetMapping("view-packages-get-address")
	public @ResponseBody JsonResponse<CustomerNewModel> getCustomerAddress(@RequestParam String id,@RequestParam String shipId,
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
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id +"&shipId=" + shipId + "&orgName=" + orgName +"&orgDivision=" + orgDivision,
					JsonResponse.class);

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

	@GetMapping(value = { "view-packages-salesorderlist" })
	public @ResponseBody JsonResponse<Object> getSalesOrderList(@RequestParam String id, String type) {
		logger.info("Method : getSalesOrderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesOrderList1?id=" + id + "&type=" + type,
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

	/*
	 * Add
	 */ 
	@SuppressWarnings("unchecked")
	@PostMapping("view-packages-add")
	public @ResponseBody JsonResponse<Object> addpackagesnew(HttpSession session,
			@RequestBody List<SalesPackagesModel> salesPackagesModel) {
		logger.info("Method : addquotation starts");
		logger.info(salesPackagesModel.toString());
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String organization = "";
		String orgDivision = "";
		 String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			 dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		logger.info("aswdwsdewfregter" + salesPackagesModel);
		for (SalesPackagesModel m : salesPackagesModel) {
			m.setQutCreatedBy(userId);
			m.setOrgName(organization);
			m.setOrgDiv(orgDivision);

		}

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addpackagesnew", salesPackagesModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<SalesPackagesModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalesPackagesModel>>() {
					});

			/*
			 * for (SalesPackagesModel m : salesPackagesModel) { if (m.getQutValidDate() !=
			 * null && m.getQutValidDate() != "") {
			 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
			 * } }
			 */

			resp.setBody(quotation);
			logger.info(salesPackagesModel.toString());
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : addpackagesnew ends");

		return resp;
	}
	/*
	 * view
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-packages-through-ajax")
	public @ResponseBody Object viewsalesPackages(@RequestParam String pageno ,HttpSession session) {

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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesPackages?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewsalesPackages ends");
		
		//logger.info(">>>-----"+resp);

		return resp;
	}

	/*
	 * for editing using employee id
	 *
	 *
	 */
/*	@GetMapping(value = { "view-packages-edit-new" })
	public @ResponseBody List<SalesPackagesModel> viewPackageEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPackageEdit starts");
		logger.info("===========>>>>>>" + id);
		List<SalesPackagesModel> productList = new ArrayList<SalesPackagesModel>();
		String dateFormat = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {

		}
		if (id != null && id != "") {
			try {
				SalesPackagesModel[] salesPackagesModel = restTemplate
						.getForObject(env.getSalesUrl() + "viewPackageEdit?id=" + id, SalesPackagesModel[].class);

				productList = Arrays.asList(salesPackagesModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (SalesPackagesModel m : salesPackagesModel) {

					count++;
					m.setSlNo(count);

				}

				for (SalesPackagesModel a : salesPackagesModel) {
					if (a.getPackagingDate() != null && a.getPackagingDate() != "") {
						a.setPackagingDate(DateFormatter.dateFormat(a.getPackagingDate(), dateFormat));
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewPackageEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}*/

	@SuppressWarnings("unchecked")
	@PostMapping("view-packages-delete")
	public @ResponseBody JsonResponse<Object> deletPackage(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : deletPackage starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String createdBy = "";

		try {
			createdBy = (String) session.getAttribute("USER_ID");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "deletPackage?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method :  deletPackage ends");
		return resp;
	}

	/*
	 * for editing using employee id
	 *
	 *
	 */
	@GetMapping(value = { "view-packages-get-SalesItemDetails" })
	public @ResponseBody List<SalesOrderNewModel> getSalesOrderData(@RequestParam String id, HttpSession session) {
		logger.info("Method : getSalesOrderData starts");
		logger.info(id);
		List<SalesOrderNewModel> productList = new ArrayList<SalesOrderNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();

		if (id != null && id != "") {
			try {
				SalesOrderNewModel[] salesOrderNewModel = restTemplate.getForObject(
						env.getSalesUrl() + "viewsalesOrderItemDetails?id=" + id, SalesOrderNewModel[].class);
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
		logger.info("Method : ItemDetails ends");
		logger.info("edit===================" + productList);
		return productList;
	}

	/*
	 * customer autoSearch
	 */

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-packages-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getInsertedId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getinsertedid ends");
		logger.info("assssssssssssssssssss" + res);
		return res;
	}

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("view-packages-item-get-product-list")
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

	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "view-packages-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatModal(Model model,
			@RequestBody String index,HttpSession session) {
		logger.info("Method : getProductsByCat starts");
		// logger.info(index);
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
		// logger.info(res);
		logger.info("Method : getProductsByCat ends");
		return res;
	}

	/*
	 * //Main save for Advance apply
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-packages-packedQutdetails-save")
	public @ResponseBody JsonResponse<Object> savepackedQutdetails(@RequestBody SalesPackagesModel salesPackagesModel,
			HttpSession session) {
		logger.info("Method : savepackedQutdetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		 String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			 dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		salesPackagesModel.setQutCreatedBy(userId);
		salesPackagesModel.setOrgName(organization);
		salesPackagesModel.setOrgDiv(orgDivision);
		
		if (salesPackagesModel.getPackDate() != null && salesPackagesModel.getPackDate() != "") {
			salesPackagesModel.setPackDate(DateFormatter.dateFormat(salesPackagesModel.getPackDate(), dateFormat));
	}
		try {
			logger.info( "ADDDDD" +env.getSalesUrl() + "savepackedQutdetails?" + salesPackagesModel);
			resp = restTemplate.postForObject(env.getSalesUrl() + "savepackedQutdetails", salesPackagesModel,
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

		logger.info("Method : savepackedQutdetails ends");
		logger.info("savepackedQutdetails" + resp);
		return resp;
	}
	
	@GetMapping(value = { "view-packages-edit-neww" })
	public @ResponseBody List<SalesPackagesModel> getDeliveryChallanDataOnPackageId(@RequestParam String id, HttpSession session) {
		logger.info("Method : getDeliveryChallanDataOnPackageId starts");
		logger.info("===========>>>>>>" + id);
		List<SalesPackagesModel> productList = new ArrayList<SalesPackagesModel>();
		String dateFormat = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {

		}
		if (id != null && id != "") {
			try {
				SalesPackagesModel[] salesPackagesModel = restTemplate
						.getForObject(env.getSalesUrl() + "getDeliveryChallanDataOnPackageId?id=" + id, SalesPackagesModel[].class);

				productList = Arrays.asList(salesPackagesModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (SalesPackagesModel m : salesPackagesModel) {

					count++;
					m.setSlNo(count);

				}

				for (SalesPackagesModel a : salesPackagesModel) {
					if (a.getPackagingDate() != null && a.getPackagingDate() != "") {
						a.setPackagingDate(DateFormatter.dateFormat(a.getPackagingDate(), dateFormat));
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getDeliveryChallanDataOnPackageId ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-packages-add-cust-shippingaddress")
	public @ResponseBody JsonResponse<Object> saveAddressDetails(@RequestBody CustomerNewModel customerNewModel, HttpSession session) {
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
			resp = restTemplate.postForObject(env.getSalesUrl()  + "saveAddressDetails", customerNewModel,
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
	
	@GetMapping("view-packages-shipping-address")
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewShippingAddressData?customerId=" + customerId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@GetMapping("view-packages-shipping-dataedit")
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-editShippingAddressData?addressId="
					+ addressId + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("view-packages-get-sales-order")
	public @ResponseBody Object getAllsalesOrder(@RequestParam String pageno ,HttpSession session) {

		logger.info("Method :getAllsalesOrder starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-sales-order?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllsalesOrder ends");
		
		//logger.info(">>>-----"+resp);

		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleorder-for-packing")
	public @ResponseBody Object viewsalesOrderForPacking(@RequestParam String id, String poidd, HttpSession session) {
		logger.info("Method :viewsalesOrderForPacking starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "viewsalesForPacking?id=" + id + "&poidd=" + poidd + "&orgName=" + orgName
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
		logger.info("Method :viewsalesOrderForPacking ends");
		return resp;
	}
	
//
	@SuppressWarnings("unchecked")
	@GetMapping("view-packages-edit-new")
	public @ResponseBody Object viewPackageEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method :viewPackageEdit starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "viewPackageEdit?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewPackageEdit ends");
		return resp;
	}
}
