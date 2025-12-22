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
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesShipmentsModel;

@Controller
@RequestMapping(value = { "sales/" })
public class SalesShipmentsController {
	
	Logger logger = LoggerFactory.getLogger(SalesShipmentsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SalesShipmentsController salesShipmentsController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/view-shipments" })
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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getSalesUrl() + "get-carrier-list",
					DropDownModel[].class);
			List<DropDownModel> carrierList = Arrays.asList(dropDownModel);
			model.addAttribute("carrierList", carrierList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : customerDetails ends");
		return "sales/view-shipments";
	}
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-shipments-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCustomerAutoSearchList starts");
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
			res = restTemplate.getForObject(env.getSalesUrl() + "getCustomerListByAutoSearch?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision,JsonResponse.class);
					
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
	
	// Add New Customer
			@SuppressWarnings("unchecked")
			@PostMapping("view-shipments-adds")
			public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
					HttpSession session) {

				logger.info("Method : addAccountCustomer starts");

				JsonResponse<Object> resp = new JsonResponse<Object>();
				logger.info("web AccountModel ======================" + customerNewModel);
				try {
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
     @PostMapping("view-shipments-add-cust-billingaddress")
	public @ResponseBody JsonResponse<Object> addbillingaddress(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : addbillingaddress starts");
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
		customerNewModel.setCreatedBy(userId);
		customerNewModel.setOrganization(organization);
		customerNewModel.setOrgDivision(orgDivision);

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

	@GetMapping(value = { "view-shipments-stateList" })
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
	@GetMapping("view-shipments-get-address")
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

	@GetMapping(value = { "view-shipments-salesorderlist" })
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
	@PostMapping("view-shipments-add")
	public @ResponseBody JsonResponse<Object> addShipments(HttpSession session,
			@RequestBody List<SalesShipmentsModel> salesShipmentsModel) {
		logger.info("Method : addShipments starts");
		logger.info(salesShipmentsModel.toString());
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		logger.info("aswdwsdewfregter"+salesShipmentsModel);
		for (SalesShipmentsModel m : salesShipmentsModel) {
			m.setQutCreatedBy(userId);
			m.setOrgName(organization);
			m.setOrgDiv(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addShipments", salesShipmentsModel,
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
		logger.info("Sradhaaaaaaaaaaaaaaaaaaaaaaaaaa" + resp);

		logger.info("Method : addShipments ends");

		return resp;
	}
	
	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-shipments-through-ajax")
	public @ResponseBody Object viewsalesShipments(@RequestParam String pageno ,HttpSession session) {

		logger.info("Method :viewsalesShipments starts");
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

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesShipments?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewsalesShipments ends");
		
		//logger.info(">>>-----"+resp);

		return resp;
	}

	
	/*
	 * for editing using employee id
	 *
	 *
	 */
	@GetMapping(value = { "view-shipments-edit-new" })
	public @ResponseBody List<SalesShipmentsModel> viewShipmentEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewShipmentEdit starts");
		logger.info(id);
		List<SalesShipmentsModel> productList = new ArrayList<SalesShipmentsModel>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		if (id != null && id != "") {
			try {
				SalesShipmentsModel[] salesShipmentsModel = restTemplate
						.getForObject(env.getSalesUrl() + "viewShipmentEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision, SalesShipmentsModel[].class);

				productList = Arrays.asList(salesShipmentsModel);

				// productList.forEach(s -> s.setSlNo(s.getSlNo()));

				// int count = 0;
				/*
				 * for (QuotationNewModel m : salesPackagesModel) {
				 * 
				 * count++; m.setSlNo(count);
				 * 
				 * 
				 * }
				 */
				for (SalesShipmentsModel a : salesShipmentsModel) {
					if (a.getShipmentDate() != null && a.getShipmentDate() != "") {
						a.setShipmentDate(DateFormatter.dateFormat(a.getShipmentDate(), dateFormat));
					}
				
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewShipmentEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("view-shipments-delete")
	public @ResponseBody JsonResponse<Object> deletShipments(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : deletShipments starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "deletShipments?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method :  deletShipments ends"+resp);
		return resp;
	}
	
	//Get PackagrId
	

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-shipments-get-PackageId" })
	public @ResponseBody JsonResponse<Object> getPackageId(@RequestParam String id,HttpSession session) {
		logger.info("Method : getPackageId starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
		} catch (Exception e) {

		}
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getPackageId?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
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
		logger.info("Method : getPackageId ends");
		return res;
	}
	
	//insertedid
		@SuppressWarnings("unchecked")

		@GetMapping(value = { "view-shipments-get-insertedid" })
		public @ResponseBody JsonResponse<Object> getInsertedId() {
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getShipmentInsertedId", JsonResponse.class);
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
			logger.info("assssssssssssssssssss"+res);
			return res;
		}
		
		// approve
		@SuppressWarnings("unchecked")
		@GetMapping("view-shipments-delivery-th-ajax")
		public @ResponseBody JsonResponse<List<SalesShipmentsModel>> deliveryStatus(HttpSession session,
				@RequestParam String shipmentStatus, String salesShipmentId) {

			logger.info("Method : deliveryStatus starts");
			JsonResponse<List<SalesShipmentsModel>> response = new JsonResponse<List<SalesShipmentsModel>>();
			String orgName = "";
			String orgDivision = "";
			try {
				
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					
			} catch (Exception e) {

			}
			try {
				response = restTemplate.getForObject(env.getSalesUrl() + "deliveryStatus?shipmentStatus="
						+ shipmentStatus + "&salesShipmentId=" + salesShipmentId + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			if(response.getCode().equals("success")) {
				response.setMessage("Success");
			} else {
				response.setCode(response.getMessage());
				response.setMessage("Unsuccess");
			}
			logger.info("response=====" + response);
			logger.info("Method : deliveryStatus ends");
			return response;
		}

		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-shipments-get-customer-listt" })
		public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchListt(Model model,
				@RequestBody String searchValue, BindingResult result, HttpSession session) {
			logger.info("Method : getCustomerAutoSearchList starts");
			// logger.info("QuotationNewModel"+searchValue);
			
			JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();

			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getCustomerListByAutoSearch?id=" + searchValue ,JsonResponse.class);
						
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
		@GetMapping("view-shipments-po-wise")
		public @ResponseBody List<SalesShipmentsModel> viewsalesPOWiseShipment(HttpSession session,@RequestParam String id) {

			logger.info("Method :viewsalesPOWiseShipment starts");
			JsonResponse<List<SalesShipmentsModel>> resp = new JsonResponse<List<SalesShipmentsModel>>();
			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {

			}
			try {

				resp = restTemplate.getForObject(
						env.getSalesUrl() + "rest-viewsalesPOWiseShipment?org=" + orgName + "&orgDiv=" + orgDivision+"&id="+id,
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

			logger.info("Method :viewsalesPOWiseShipment ends");

			return resp.getBody();
		}
		
		//get challan data
	
		@GetMapping(value = { "view-shipments-edit-salesreturn" })
		public @ResponseBody List<DeliveryChallanModel> viewsalesChallanData(@RequestParam String id, HttpSession session) {
			logger.info("Method : viewsalesChallanData starts");
			logger.info(id);
			List<DeliveryChallanModel> productList = new ArrayList<DeliveryChallanModel>();
			String dateFormat = "";
			String orgName = "";
			String orgDivision = "";
			try {
				
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}
			if (id != null && id != "") {
				try {
					DeliveryChallanModel[] deliveryChallanModel = restTemplate
							.getForObject(env.getSalesUrl() + "viewsalesChallanData?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision, DeliveryChallanModel[].class);

					productList = Arrays.asList(deliveryChallanModel);

					productList.forEach(s -> s.setSlNo(s.getSlNo()));

					int count = 0;
					
					  for (DeliveryChallanModel m : deliveryChallanModel) {
					  
					  count++; m.setSlNo(count);
					  
					  
					  }
					 
					for (DeliveryChallanModel a : deliveryChallanModel) {
						/*
						 * if (a.getShipmentDate() != null && a.getShipmentDate() != "") {
						 * a.setShipmentDate(DateFormatter.dateFormat(a.getShipmentDate(), dateFormat));
						 * }
						 */
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("Method : viewsalesChallanData ends");
			logger.info("edit@@@@@@@@" + productList);
			return productList;
		}
		
		@SuppressWarnings("unchecked")

		@PostMapping("view-shipments-add-cust-shippingaddress")
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

			logger.info("Method :addshippingaddress ends");
			return resp;
		}
		
		@GetMapping("view-shipments-shipping-address")
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
		
		@GetMapping("view-shipments-shipping-dataedit")
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
		@GetMapping("view-shipments-get-challan")
		public @ResponseBody JsonResponse<Object> getChallanOnPo(Model model, HttpSession session, @RequestParam String poId) {

			logger.info("Method : getChallanOnPo starts");

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
						env.getSalesUrl() + "rest-get-challan?poId=" + poId + "&orgName=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
				
			} catch (RestClientException e) {
				e.printStackTrace();
			}
           
			logger.info("Method : getChallanOnPo ends");
			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-shipments-get-po")
		public @ResponseBody JsonResponse<Object> getPoOnCustomerChange(Model model, HttpSession session, @RequestParam String custId) {

			logger.info("Method : getPoOnCustomerChange starts");

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
						env.getSalesUrl() + "rest-get-po-shipment?custId=" + custId + "&orgName=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
				
			} catch (RestClientException e) {
				e.printStackTrace();
			}
           
			logger.info("Method : getPoOnCustomerChange ends");
			return resp;
		}

}
