package nirmalya.aathithya.webmodule.hotel.controller;

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
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;

	
	@Controller
	@RequestMapping(value = "hotel")
	public class ProcurementWebController {
		Logger logger = LoggerFactory.getLogger(BillingWebController.class);

		@Autowired
		RestTemplate restTemplate;

		@Autowired
		EnvironmentVaribles env;

		@GetMapping("/procurement")
		public String viewProcurement(Model model, HttpSession session) {
			logger.info("Method: viewProcurement starts here");
			
			String org = "";
			String orgDiv = "";
			String userRole = "";
			String userId = "";
			String userName = "";
			try {
				 userId = (String) session.getAttribute("USER_ID");
				 userName = (String) session.getAttribute("USER_NAME");
				userRole = (String) session.getAttribute("USER_ROLES_STRING");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			String splitData[] = userRole.split("r");
			String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
					.toArray(size -> new String[size]);

			for (String part : removedNull) {
				String data = "r" + part;

				if (data.contentEquals("rol001") || data.contentEquals("rol006")) {
					model.addAttribute("adRole", "admin");
					//System.out.println("data>>>>----" + data);
				}

			}
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
				String type="Inventory";
				DropDownModel[] vendor = restTemplate.getForObject(
						env.getMasterUrl() + "getVendorListForProductWise?org=" + org + "&orgDiv=" + orgDiv + "&type=" + type,
						DropDownModel[].class);
				List<DropDownModel> vendorList = Arrays.asList(vendor);

				model.addAttribute("vendorList", vendorList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				DropDownModel[] years = restTemplate.getForObject(env.getPurchaseUrl() + "/getFiscalYearList",
						DropDownModel[].class);

				List<DropDownModel> fiscalList = Arrays.asList(years);
				logger.info("finicialList-->"+ fiscalList);
				model.addAttribute("fiscalList", fiscalList);
			} catch (RestClientException e) { 
				e.printStackTrace();
			}
			

			logger.info("Method: viewProcurement ends here");
			return "hotel/hotel-procurement";
		}
		
		@GetMapping("invoice-details-view")
		@SuppressWarnings("unchecked")
		public @ResponseBody Object viewInvoiceDetails(HttpSession session,@RequestParam String type) {
			logger.info("Method :viewInvoiceDetails starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			String userId = "";
			String invType = "inventory";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(
						env.getPurchaseUrl() + "rest-viewInvoiceDetails?orgName=" + orgName + "&orgDivision=" + orgDivision + "&invType=" + invType+"&type="+type+"&userId="+userId,
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
		@GetMapping(value = { "invoice-details-edit-Invoice" })
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
								if (m.getDocView() != null && m.getDocView() != "") {
									
									String docPath = "/document/document/" + m.getDocView();

									m.setDociURL(docPath);
									String[] extension = m.getDocView().split("\\.");
									m.setDocType(extension[1]);
									
								} else {
									m.setDociURL(null);
									m.setDocType(null);
								}
								
								logger.info("m.setDociURL" + m.getDociURL());

							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("productList>>><<<<<<------"+productList);
			logger.info("Method : purchaseInvoiceEdit ends");

			return productList;
		}
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "invoice-details-get-vendor-list" })
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
				res = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorAutoSearchListInventory?id=" + searchValue
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
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "invoice-details-get-insertedid" })
		public @ResponseBody JsonResponse<Object> getPoInsertedId() {
			JsonResponse<Object> res = new JsonResponse<Object>();
			String requestType = "inventory";
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
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "manage-invoice-polist" })
		public @ResponseBody JsonResponse<Object> getPoList(@RequestParam String id, String grnId, HttpSession session) {
			logger.info("Method : getPoList starts" + id);
			logger.info("Method : getPoList starts" + grnId);

			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			JsonResponse<Object> res = new JsonResponse<Object>();
			logger.info("Method : getPoList starts" + orgName);
			logger.info("Method : getPoList starts" + orgDivision);
			try {
				res = restTemplate.getForObject(env.getPurchaseUrl() + "getPoList?id=" + id + "&grnId=" + grnId + "&org="
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

			logger.info("Method : getPoList ends");
			return res;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "manage-invoice-get-vendor_state_data" })
		public @ResponseBody JsonResponse<Object> getVendorState(@RequestParam String id, String type,
				HttpSession session) {
			logger.info("Method : getVendorState starts" + id);
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
				res = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorState?id=" + id + "&type=" + type
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("res>>>>State>>>>" + res);
			logger.info("Method : getVendorState ends");
			return res;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("manage-invoice-get-address")
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
		@PostMapping(value = { "invoice-details-get-item-list" })
		public @ResponseBody JsonResponse<DeliveryChallanModel> getItemQuotationAutoList(Model model,
				@RequestBody String searchValue,String type, HttpSession session,BindingResult result) {
			logger.info("Method : getItemQuotationAutoSearchNewList starts");
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
				res = restTemplate.getForObject(
						env.getPurchaseUrl() + "getItemQuotationAutoSearchNewListForPO?id=" + searchValue
						+ "&type=" + type + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
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
}
