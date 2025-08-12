package nirmalya.aathithya.webmodule.sales.controller;

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
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesReturnModel;

@Controller
@RequestMapping(value = { "sales/" })
public class SalesReturnController {
	Logger logger = LoggerFactory.getLogger(SalesReturnController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SalesReturnController salesReturnController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/view-salesreturn" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : customerDetails starts");

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
			DropDownModel[] paymentMode = restTemplate.getForObject(env.getEmployeeUrl() + "getPaymentMode",
					DropDownModel[].class);
			List<DropDownModel> PayModeLists = Arrays.asList(paymentMode);

			model.addAttribute("PayModeLists", PayModeLists);

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
			DropDownModel[] Collection = restTemplate.getForObject(env.getSalesUrl() + "getReturnList",
					DropDownModel[].class);
			List<DropDownModel> RetunLists = Arrays.asList(Collection);

			model.addAttribute("RetunLists", RetunLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getSalesUrl() + "get-carrier-list",
					DropDownModel[].class);
			List<DropDownModel> carrierList = Arrays.asList(dropDownModel);
			model.addAttribute("carrierList", carrierList);
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
		
		logger.info("Method : customerDetails ends");
		return "sales/view-salesreturn";
	}
	
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-salesreturn-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getCustomerAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getCustomerListByAutoSearch?id=" + searchValue,
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

	@PostMapping("view-salesreturn-add-cust-billingaddress")
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

	@SuppressWarnings("unchecked")

	@PostMapping("view-salesreturn-add-cust-shippingaddress")
	public @ResponseBody JsonResponse<Object> addshippingaddress(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : addshippingaddress starts");
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

			resp = restTemplate.postForObject(env.getSalesUrl() + "add-shippingaddress", customerNewModel,
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
		logger.info("Method :addshippingaddress ends");
		return resp;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-salesreturn-stateList" })
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

	@SuppressWarnings("unchecked")
	@GetMapping("view-salesreturn-get-address")
	public @ResponseBody JsonResponse<CustomerNewModel> getCustomerAddress(@RequestParam String id,
			HttpSession session) {
		logger.info(id);
		logger.info("Method : getCustomerAddress starts");

		JsonResponse<CustomerNewModel> jsonResponse = new JsonResponse<CustomerNewModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id,
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
	@PostMapping(value = { "view-salesreturn-get-tcs-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getTCSAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getTCSAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getTCSAutoSearchList?id=" + searchValue,
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
		logger.info("Method : getTCSAutoSearchList ends" + res);
		return res;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("view-salesreturn-add-tcs")
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
	// get Product Category Data List Modal

		@SuppressWarnings("unchecked")
		@PostMapping("view-salesreturn-get-product-list")
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
		@PostMapping(value = { "view-salesreturn-get-item-list" })
		public @ResponseBody JsonResponse<DeliveryChallanModel> getItemQuotationAutoList(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getItemQuotationAutoSearchNewList starts");

			JsonResponse<DeliveryChallanModel> res = new JsonResponse<DeliveryChallanModel>();

			try {
				res = restTemplate.getForObject(
						env.getSalesUrl() + "getItemQuotationAutoSearchNewList?id=" + searchValue, JsonResponse.class);
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

		@PostMapping(value = { "view-salesreturn-item-product-by-cat" })
		public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
				@RequestBody String index,HttpSession session) {
			logger.info("Method : getProductsByCatInvoice starts");
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
			logger.info("Method : getProductsByCatInvoice ends");
			return res;
		}
		
		@SuppressWarnings("unchecked")

		@GetMapping(value = { "view-salesreturn-salesorderlist" })
		public @ResponseBody JsonResponse<Object> getSalesorderList(@RequestParam String id,String type) {
			logger.info("Method : getSalesorderList starts" + id);
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getSalesorderList1?id=" + id+"&type="+type, JsonResponse.class);
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
			logger.info("Method : getSalesorderList ends");
			return res;
		}
		
		
		//view-saleInvoice-getdataOnSO
		
		
		@GetMapping(value = { "view-salesreturn-getdataOnSO" })
		public @ResponseBody List<SalesOrderNewModel> viewsalesOrderDataOnclickSo(@RequestParam String id,
				HttpSession session) {
			logger.info("Method : viewsalesOrderDataOnclickSo starts");
			 logger.info(id);
			List<SalesOrderNewModel> productList = new ArrayList<SalesOrderNewModel>();
			List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();

			if (id != null && id != "") {
				try {
					SalesOrderNewModel[] salesOrderNewModel = restTemplate.getForObject(
							env.getSalesUrl() + "viewsalesOrdeerEdit?id=" + id, SalesOrderNewModel[].class);

					productList = Arrays.asList(salesOrderNewModel);

					productList.forEach(s -> s.setSlNo(s.getSlNo()));

					int count = 0;

					for (SalesOrderNewModel m : salesOrderNewModel) {

						count++;
						m.setSlNo(count);

						String dateFormat = (String) session.getAttribute("DATEFORMAT");

						/*
						 * if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
						 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
						 * 
						 * }
						 */
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

											String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " + m.getFileName()
													+ "></i> ";

											m.setAction(docPath);
										}
										if (extension[1].equals("pdf")) {
											String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName()
													+ " ;></i> ";

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
								logger.info("m.setAction"+m);

							}
						}
					}
					

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("Method : viewsalesOrderDataOnclickSo ends");
			logger.info("edit@@@@@@@@" + productList);
			return productList;
		}

		
		/*
		 * Add
		 */
		@SuppressWarnings("unchecked")
		@PostMapping("view-salesreturn-add")
		public @ResponseBody JsonResponse<Object> addsaleReturn(HttpSession session,@RequestBody List<SalesReturnModel> salesReturnModel) {
			logger.info("Method : addsaleReturn starts");
			logger.info(salesReturnModel.toString());
			JsonResponse<Object> resp = new JsonResponse<Object>();
			List<SalesReturnModel> documentList = new ArrayList<SalesReturnModel>();
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

			for (SalesReturnModel m : salesReturnModel) {
				m.setQutCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);

			}

			for (InventoryVendorDocumentModel a : salesReturnModel.get(0).getDocumentList()) {
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
			logger.info("salesReturnModel  "+salesReturnModel);

			try {
				//resp = restTemplate.postForObject(env.getSalesUrl() + "addsaleDeliveryChallan", deliveryChallanModel,JsonResponse.class);
				resp = restTemplate.postForObject(env.getSalesUrl() + "addsaleReturn", salesReturnModel,JsonResponse.class);
				
				/*
				 * ObjectMapper mapper = new ObjectMapper();
				 * 
				 * List<DeliveryChallanModel> quotation = mapper.convertValue(resp.getBody(),
				 * new TypeReference<List<DeliveryChallanModel>>() { });
				 */
				 
				docList = documentList.get(0).getDocumentList();
				logger.info("saddddddddddddddddd" + docList);
				for (InventoryVendorDocumentModel m : docList) {
					if (documentList != null) {

						if (m.getFileName() != null && m.getFileName() != "") {

							String[] extension = m.getFileName().split("\\.");
							if (extension.length == 2) {
								if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

									String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " + m.getFileName()
											+ "></i> ";

									m.setAction(docPath);
								}
								if (extension[1].equals("pdf")) {
									String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName()
											+ " ;></i> ";

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

					}
				}

				// resp.setBody(quotation);
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

			logger.info("Method : addsaleReturn ends");

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
		@GetMapping("view-salesreturn-through-ajax")
		public @ResponseBody List<SalesReturnModel> viewsalesreturn(HttpSession session) {

			logger.info("Method :viewsalesreturn startsssssssssssssssssssssss");
			JsonResponse<List<SalesReturnModel>> resp = new JsonResponse<List<SalesReturnModel>>();
			String dateFormat = "";
			try {
				
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				
			} catch (Exception e) {

			}
			try {

				resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesreturn",
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();

			List<SalesReturnModel> salesReturnModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalesReturnModel>>() {
					});
			for (SalesReturnModel a : salesReturnModel) {
			  if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
				  a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
				  }
			}
			resp.setBody(salesReturnModel);
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method :viewsalesreturn ends");
			logger.info("RESPONSEview" + resp);
			return resp.getBody();
		}
		
		/*
		 * edit Delivery Challan
		 */

		@GetMapping(value = { "view-salesreturn-edit-new" })
		public @ResponseBody List<SalesReturnModel> viewSalesreturnEdit(@RequestParam String id, HttpSession session) {
			logger.info("Method : viewSalesreturnEdit starts");
			logger.info(id);
			List<SalesReturnModel> productList = new ArrayList<SalesReturnModel>();
			List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
			if (id != null && id != "") {
				try {
					SalesReturnModel[] salesReturnModel = restTemplate.getForObject(
							env.getSalesUrl() + "viewSalesreturnEdit?id=" + id, SalesReturnModel[].class);

					productList = Arrays.asList(salesReturnModel);

					productList.forEach(s -> s.setSlNo(s.getSlNo()));

					int count = 0;

					for (SalesReturnModel m : salesReturnModel) {
						// m.setQuantitynew(m.getQuantity());
						count++;
						m.setSlNo(count);
						
						String dateFormat = (String) session.getAttribute("DATEFORMAT");
						if (m.getQutUpdatedOn() != null && m.getQutUpdatedOn() != "") {
							m.setQutUpdatedOn(DateFormatter.dateFormat(m.getQutUpdatedOn(), dateFormat));
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
			logger.info("Method : viewSalesreturnEdit ends");
			logger.info("edit@@@@@@@@SSSSSSSSSSSSSSSSSSSSS" + productList);
			return productList;
		}
		
		/*
		 * delete
		 */
		@GetMapping("view-salesreturn-delete")
		public @ResponseBody Object deleteSalesReturn(@RequestParam String id, HttpSession session) {

			logger.info("Method :deleteSalesReturn starts");
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
				resp = restTemplate.getForObject(env.getSalesUrl() + "rest-deleteSalesReturn?id=" + id + "&org="
						+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		}
		
		@SuppressWarnings("unchecked")

		@GetMapping(value = { "view-salesreturn-get-salesreturnid" })
		public @ResponseBody JsonResponse<Object> getSalesReturntInsertedId() {
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getSalesReturntInsertedId", JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			logger.info("Method : getSalesReturntInsertedId ends");
			logger.info("assssssssssssssssssss"+res);
			return res;
		}
		
		/*
		 * approveSalesReturn
		 */
		
		@SuppressWarnings("unchecked")

		@PostMapping("view-salesreturn-approve")
		public @ResponseBody JsonResponse<Object> approveSalesReturn(@RequestBody SalesReturnModel salesReturnModel,
				HttpSession session) {
			logger.info("Method : approveSalesReturn starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();

			String userId = "";
			String dateFormat = "";
			String organization=""; 
			String orgDivision="";

			try {
				userId = (String) session.getAttribute("USER_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {

			}
			salesReturnModel.setOrganization(organization);
			salesReturnModel.setOrgDivision(orgDivision);
			salesReturnModel.setQutCreatedBy(userId);

			
			try {

				resp = restTemplate.postForObject(env.getSalesUrl() + "approveSalesReturn", salesReturnModel,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}


			  
			  if (resp.getMessage() != "" && resp.getMessage() != null) {
			  resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
			  resp.setMessage("Success"); }
			  
			  logger.info("Method : salesReturnModel ends");
			  return resp;
		}
}
