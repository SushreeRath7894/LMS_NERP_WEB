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
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesReplacementModel;

@Controller
@RequestMapping(value = { "sales/" })
public class SalesReplacementController {
	Logger logger = LoggerFactory.getLogger(SalesReplacementController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SalesReplacementController salesReplacementController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/view-replacement" })
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
		

		logger.info("Method : customerDetails ends");
		return "sales/view-replacement";
	}
	
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-replacement-get-customer-list" })
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

	@PostMapping("view-replacement-add-cust-billingaddress")
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

	@PostMapping("view-replacement-add-cust-shippingaddress")
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

		@GetMapping(value = { "view-replacement-stateList" })
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
		@GetMapping("view-replacement-get-address")
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
		@PostMapping(value = { "view-replacement-get-tcs-list" })
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

		@PostMapping("view-replacement-add-tcs")
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
				@PostMapping("view-replacement-get-product-list")
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
				@PostMapping(value = { "view-replacement-get-item-list" })
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

				@PostMapping(value = { "view-replacement-item-product-by-cat" })
				public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
						@RequestBody String index, HttpSession session) {
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

				@GetMapping(value = { "view-replacement-salesorderlist" })
				public @ResponseBody JsonResponse<Object> getSalesorderList(@RequestParam String id,String type) {
					logger.info("Method : getSalesorderList starts" + id);
					JsonResponse<Object> res = new JsonResponse<Object>();
					try {
						res = restTemplate.getForObject(env.getSalesUrl() + "getSalesorderListR?id=" + id+"&type="+type, JsonResponse.class);
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
				
				@SuppressWarnings("unchecked")

				@GetMapping(value = { "view-replacement-get-insertedid" })
				public @ResponseBody JsonResponse<Object> getreplacementInsertedId() {
					JsonResponse<Object> res = new JsonResponse<Object>();
					try {
						res = restTemplate.getForObject(env.getSalesUrl() + "getreplacementInsertedId", JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (res.getMessage() != null) {
						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}
					logger.info("Method : getreplacementInsertedId ends");
					logger.info("assssssssssssssssssss"+res);
					return res;
				}
				
				/*
				 * Add
				 */
				@SuppressWarnings("unchecked")
				@PostMapping("view-replacement-add")
				public @ResponseBody JsonResponse<Object> addreplacementnew(HttpSession session,
						@RequestBody List<SalesReplacementModel> salesReplacementModel) {
					logger.info("Method : addreplacementnew starts");
					logger.info(salesReplacementModel.toString());
					JsonResponse<Object> resp = new JsonResponse<Object>();
					String userId = "";
					String dateFormat = "";
					
					try {
						userId = (String) session.getAttribute("USER_ID");
						// logger.info("CReatedBYYYYYY"+userId);
						dateFormat = (String) session.getAttribute("DATEFORMAT");

					} catch (Exception e) {

					}
					logger.info("aswdwsdewfregter"+salesReplacementModel);
					for (SalesReplacementModel m : salesReplacementModel) {
						m.setQutCreatedBy(userId);

						
					}
					
					try {
						resp = restTemplate.postForObject(env.getSalesUrl() + "addreplacementnew", salesReplacementModel,
								JsonResponse.class);
						ObjectMapper mapper = new ObjectMapper();

						List<SalesReplacementModel> quotation = mapper.convertValue(resp.getBody(),
								new TypeReference<List<SalesReplacementModel>>() {
								});

						/*
						 * for (SalesPackagesModel m : salesPackagesModel) { if (m.getQutValidDate() !=
						 * null && m.getQutValidDate() != "") {
						 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
						 * } }
						 */

						resp.setBody(quotation);
						logger.info(salesReplacementModel.toString());
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

					logger.info("Method : addreplacementnew ends");

					return resp;
				}
				
				
				/*
				 * view
				 */

				@SuppressWarnings("unchecked")
				@GetMapping("view-replacement-through-ajax")
				public @ResponseBody List<SalesReplacementModel> viewsalesReplacement(HttpSession session) {

					logger.info("Method :viewsalesReplacement startsssssssssssssssssssssss");
					JsonResponse<List<SalesReplacementModel>> resp = new JsonResponse<List<SalesReplacementModel>>();
					String dateFormat = "";
					try {
						
						dateFormat = (String) session.getAttribute("DATEFORMAT");
						
					} catch (Exception e) {

					}
					
					try {

						resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesReplacement", JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					ObjectMapper mapper = new ObjectMapper();

					List<SalesReplacementModel> salesReplacementModel = mapper.convertValue(resp.getBody(),
							new TypeReference<List<SalesReplacementModel>>() {
							});
					for (SalesReplacementModel a : salesReplacementModel) {
						if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
							a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
						}
					
					}

					resp.setBody(salesReplacementModel);
					if (resp.getMessage() != "" && resp.getMessage() != null) {
						resp.setCode(resp.getMessage());
						resp.setMessage("Unsuccess");
					} else {
						resp.setMessage("Success");
					}

					logger.info("Method :viewsalesReplacement ends");
					logger.info("RESPONSEview" + resp);
					return resp.getBody();
				}
				
				/*
				 * for editing using employee id
				 *
				 *
				 */
				@GetMapping(value = { "view-replacement-edit-new" })
				public @ResponseBody List<SalesReplacementModel> viewReplacementEdit(@RequestParam String id, HttpSession session) {
					logger.info("Method : viewReplacementEdit starts");
					logger.info("===========>>>>>>"+id);
					List<SalesReplacementModel> productList = new ArrayList<SalesReplacementModel>();
					String dateFormat = "";
					try {
						
						dateFormat = (String) session.getAttribute("DATEFORMAT");
						
					} catch (Exception e) {

					}
					if (id != null && id != "") {
						try {
							SalesReplacementModel[] salesReplacementModel = restTemplate
									.getForObject(env.getSalesUrl() + "viewReplacementEdit?id=" + id, SalesReplacementModel[].class);

							productList = Arrays.asList(salesReplacementModel);

							
							for (SalesReplacementModel a : salesReplacementModel) {
								if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
									a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
								}
							
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					logger.info("Method : viewReplacementEdit ends");
					logger.info("edit@@@@@@@@" + productList);
					return productList;
				}
			
				@SuppressWarnings("unchecked")
				@PostMapping("view-replacement-delete")
				public @ResponseBody JsonResponse<Object> deletReplacement(Model model, @RequestParam String id, HttpSession session) {
					logger.info("Method : deletReplacement starts");

					JsonResponse<Object> resp = new JsonResponse<Object>();
					String createdBy = "";

					try {
						createdBy = (String) session.getAttribute("USER_ID");
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					try {
						resp = restTemplate.getForObject(env.getSalesUrl() + "deletReplacement?id=" + id, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					if (resp.getMessage() != null && resp.getMessage() != "") {
						resp.setCode(resp.getMessage());
						resp.setMessage("Unsuccess");
					} else {
						resp.setMessage("success");
					}

					logger.info("Method :  deletReplacement ends");
					return resp;
				}

}
