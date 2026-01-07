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
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesRefundModel;

@Controller
@RequestMapping(value = { "sales/" })
public class SalesRefundController {
	Logger logger = LoggerFactory.getLogger(SalesRefundController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SalesRefundController salesRefundController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/view-refund" })
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
			DropDownModel[] Collection = restTemplate.getForObject(env.getSalesUrl() + "getPaymentModeLists",
					DropDownModel[].class);
			List<DropDownModel> PaymentModeLists = Arrays.asList(Collection);

			model.addAttribute("PaymentModeLists", PaymentModeLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : customerDetails ends");
		return "sales/view-refund";
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-refund-salesinvoicelist" })
	public @ResponseBody JsonResponse<Object> getSalesorderList(@RequestParam String id,String type) {
		logger.info("Method : getSalesorderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesorderListt?id=" + id+"&type="+type, JsonResponse.class);
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
	
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-refund-get-customer-list" })
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

	@PostMapping("view-refund-add-cust-billingaddress")
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

	@PostMapping("view-refund-add-cust-shippingaddress")
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

	@GetMapping(value = { "view-refund-stateList" })
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
	@GetMapping("view-refund-get-address")
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
	@PostMapping(value = { "view-refund-get-tcs-list" })
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

	@PostMapping("view-refund-add-tcs")
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
		@PostMapping("view-refund-get-product-list")
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
		@PostMapping(value = { "view-refund-get-item-list" })
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

		@PostMapping(value = { "view-refund-item-product-by-cat" })
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
		
		@GetMapping(value = { "view-refund-getdataOnSO" })
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
		
		
		@SuppressWarnings("unchecked")

		@GetMapping(value = { "view-refund-get-insertedid" })
		public @ResponseBody JsonResponse<Object> getRefundInsertedId() {
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getRefundInsertedId", JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			logger.info("Method : getRefundInsertedId ends");
			logger.info("assssssssssssssssssss"+res);
			return res;
		}

		
		
		/*
		 * Add
		 */
		@SuppressWarnings("unchecked")
		@PostMapping("view-refund-add")
		public @ResponseBody JsonResponse<Object> addrefundnew(HttpSession session,
				@RequestBody List<SalesRefundModel> salesRefundModel) {
			logger.info("Method : addrefundnew starts");
			logger.info(salesRefundModel.toString());
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String dateFormat = "";
			
			try {
				userId = (String) session.getAttribute("USER_ID");
				// logger.info("CReatedBYYYYYY"+userId);
				dateFormat = (String) session.getAttribute("DATEFORMAT");

			} catch (Exception e) {

			}
			logger.info("aswdwsdewfregter"+salesRefundModel);
			for (SalesRefundModel m : salesRefundModel) {
				m.setQutCreatedBy(userId);

				
			}
			
			try {
				resp = restTemplate.postForObject(env.getSalesUrl() + "addrefundnew", salesRefundModel,
						JsonResponse.class);
				ObjectMapper mapper = new ObjectMapper();

				List<SalesRefundModel> quotation = mapper.convertValue(resp.getBody(),
						new TypeReference<List<SalesRefundModel>>() {
						});

				/*
				 * for (SalesPackagesModel m : salesPackagesModel) { if (m.getQutValidDate() !=
				 * null && m.getQutValidDate() != "") {
				 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
				 * } }
				 */

				resp.setBody(quotation);
				logger.info(salesRefundModel.toString());
			} catch (Exception e) {

				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Sradhaaaaaaaaaaaaaaaaaaaaaaaaaa" + resp);

			logger.info("Method : addrefundnew ends");

			return resp;
		}
		
		/*
		 * view
		 */

		@SuppressWarnings("unchecked")
		@GetMapping("view-refund-through-ajax")
		public @ResponseBody List<SalesRefundModel> viewsalesRefund(HttpSession session) {

			logger.info("Method :viewsalesRefund startsssssssssssssssssssssss");
			JsonResponse<List<SalesRefundModel>> resp = new JsonResponse<List<SalesRefundModel>>();
			String dateFormat = "";
			try {
				
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				
			} catch (Exception e) {

			}
			
			try {

				resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesRefund", JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();

			List<SalesRefundModel> salesRefundModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalesRefundModel>>() {
					});
			for (SalesRefundModel a : salesRefundModel) {
				if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
					a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
				}
			
			}

			resp.setBody(salesRefundModel);
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method :viewsalesRefund ends");
			logger.info("RESPONSEview" + resp);
			return resp.getBody();
		}

		
		/*
		 * for editing using employee id
		 *
		 *
		 */
		@GetMapping(value = { "view-refund-edit-new" })
		public @ResponseBody List<SalesRefundModel> viewRefundEdit(@RequestParam String id, HttpSession session) {
			logger.info("Method : viewRefundEdit starts");
			logger.info("===========>>>>>>"+id);
			List<SalesRefundModel> productList = new ArrayList<SalesRefundModel>();
			String dateFormat = "";
			try {
				
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				
			} catch (Exception e) {

			}
			if (id != null && id != "") {
				try {
					SalesRefundModel[] salesRefundModel = restTemplate
							.getForObject(env.getSalesUrl() + "viewRefundEdit?id=" + id, SalesRefundModel[].class);

					productList = Arrays.asList(salesRefundModel);

					
					for (SalesRefundModel a : salesRefundModel) {
						if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
							a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
						}
					
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("Method : viewRefundEdit ends");
			logger.info("edit@@@@@@@@" + productList);
			return productList;
		}
	
		
		@SuppressWarnings("unchecked")
		@PostMapping("view-refund-delete")
		public @ResponseBody JsonResponse<Object> deletRefund(Model model, @RequestParam String id, HttpSession session) {
			logger.info("Method : deletRefund starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String createdBy = "";

			try {
				createdBy = (String) session.getAttribute("USER_ID");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				resp = restTemplate.getForObject(env.getSalesUrl() + "deletRefund?id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != null && resp.getMessage() != "") {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("success");
			}

			logger.info("Method :  deletRefund ends");
			return resp;
		}

}
