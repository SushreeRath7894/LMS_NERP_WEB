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
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.PaymentsReceivedModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoiceNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoicePaymentModel;

@Controller
@RequestMapping(value = { "sales/" })
public class PaymentReceivedController {
	Logger logger = LoggerFactory.getLogger(PaymentReceivedController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PaymentReceivedController paymentReceivedController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/view-paymentsreceived" })
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
		
		logger.info("Method : customerDetails ends");
		return "sales/view-paymentsreceived";
	}
	
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-paymentsreceived-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getCustomerAutoSearchList starts");
		
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
		
		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}
	
	// Add New Customer
			@SuppressWarnings("unchecked")
			@PostMapping("view-paymentsreceived-adds")
			public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
					HttpSession session) {

				logger.info("Method : addAccountCustomer starts");

				JsonResponse<Object> resp = new JsonResponse<Object>();
				
				try {
					String userId = "";
					try {
						userId = (String) session.getAttribute("USER_ID");
					} catch (Exception e) {
						e.printStackTrace();
					}

				

					customerNewModel.setCreatedBy(userId);

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
     @PostMapping("view-paymentsreceived-add-cust-billingaddress")
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
		
		logger.info("Method :addbillingaddress ends");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("view-paymentsreceived-add-cust-shippingaddress")
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

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-paymentsreceived-stateList" })
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
		
		logger.info("Method : getstateCusList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-paymentsreceived-get-address")
	public @ResponseBody JsonResponse<CustomerNewModel> getCustomerAddress(@RequestParam String id,
			HttpSession session) {
		
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
		
		return jsonResponse;
	}
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-paymentsreceived-salesinvoicelist" })
	public @ResponseBody JsonResponse<Object> getSalesInvoiceList(@RequestParam String id,String type) {
		logger.info("Method : getSalesInvoiceList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesInvoiceList1?id=" + id+"&type="+type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		
		logger.info("Method : getSalesInvoiceList ends");
		return res;
	}
	

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-paymentsreceived-get-account-list" })
	public @ResponseBody JsonResponse<PaymentsReceivedModel> getAccountsByAutoSearch(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getAccountsByAutoSearch starts");
		
		JsonResponse<PaymentsReceivedModel> res = new JsonResponse<PaymentsReceivedModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl()+ "getAccountsByAutoSearch?id=" + searchValue,
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
		
		logger.info("Method : getAccountsByAutoSearch ends"+res);
		return res;
	}
	
	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-paymentsreceived-add")
	public @ResponseBody JsonResponse<Object> addpaymentsreceived(HttpSession session,
			@RequestBody List<PaymentsReceivedModel> paymentsReceivedModel) {
		logger.info("Method : addpaymentsreceived starts");
	
		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<DeliveryChallanModel> documentList = new ArrayList<DeliveryChallanModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
		String userId = "";
		String dateFormat = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {

		}
		logger.info("aswdwsdewfregter"+paymentsReceivedModel);
		for (PaymentsReceivedModel m : paymentsReceivedModel) {
			m.setQutCreatedBy(userId);

			
		}
		for (InventoryVendorDocumentModel a : paymentsReceivedModel.get(0).getDocumentList()) {
			

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
			resp = restTemplate.postForObject(env.getSalesUrl() + "addpaymentsreceived", paymentsReceivedModel,
					JsonResponse.class);
			/*
			 * ObjectMapper mapper = new ObjectMapper();
			 * 
			 * List<PaymentsReceivedModel> quotation = mapper.convertValue(resp.getBody(),
			 * new TypeReference<List<PaymentsReceivedModel>>() { });
			 */

			/*
			 * for (SalesPackagesModel m : salesPackagesModel) { if (m.getQutValidDate() !=
			 * null && m.getQutValidDate() != "") {
			 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
			 * } }
			 */
			docList = documentList.get(0).getDocumentList();
			
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
			//resp.setBody(quotation);
		
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		

		logger.info("Method : addpaymentsreceived ends");

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
	@GetMapping("view-paymentsreceived-through-ajax")
	public @ResponseBody List<SalesInvoicePaymentModel> viewsalespaymentsreceived(HttpSession session,String fromdate,String todate) {

		logger.info("Method :viewsalespaymentsreceived startsssssssssssssssssssssss");
		JsonResponse<List<SalesInvoicePaymentModel>> resp = new JsonResponse<List<SalesInvoicePaymentModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDiv = "";
		
		try {
			
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		
		try {

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalespaymentsreceived?orgName=" + orgName + "&orgDiv=" + orgDiv + "&fromdate="+fromdate+"&todate="+todate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<SalesInvoicePaymentModel> paymentsReceivedModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<SalesInvoicePaymentModel>>() {
				});
		for (SalesInvoicePaymentModel a : paymentsReceivedModel) {
			if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
				a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
			}
		
		}

		resp.setBody(paymentsReceivedModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewsalespaymentsreceived ends");
		
		return resp.getBody();
	}
	
	@GetMapping(value = { "view-paymentsreceived-edit-new" })
	public @ResponseBody List<PaymentsReceivedModel> viewPaymentReceivedEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPaymentReceivedEdit starts");
		
		List<PaymentsReceivedModel> productList = new ArrayList<PaymentsReceivedModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		String dateFormat = "";
		try {
			
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			
		} catch (Exception e) {

		}
		if (id != null && id != "") {
			try {
				PaymentsReceivedModel[] paymentsReceivedModel = restTemplate
						.getForObject(env.getSalesUrl() + "viewPaymentReceivedEdit?id=" + id, PaymentsReceivedModel[].class);

				productList = Arrays.asList(paymentsReceivedModel);

				//productList.forEach(s -> s.setSlNo(s.getSlNo()));

				// int count = 0;
				/*
				 * for (QuotationNewModel m : salesPackagesModel) {
				 * 
				 * count++; m.setSlNo(count);
				 * 
				 * 
				 * }
				 */
				for (PaymentsReceivedModel a : paymentsReceivedModel) {
					if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
						a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
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
		logger.info("Method : viewPaymentReceivedEdit ends");
		
		return productList;
	}

	
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-paymentsreceived-get-payment" })
	public @ResponseBody JsonResponse<Object> getpayment() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getpayment", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getpayment ends");
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("view-paymentsreceived-delete")
	public @ResponseBody JsonResponse<Object> deletPayment(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : deletPayment starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String createdBy = "";

		try {
			createdBy = (String) session.getAttribute("USER_ID");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "deletPayment?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method :  deletPayment ends");
		return resp;
	}
	
	//Get sales Invoice Data
	

	@GetMapping(value = { "view-paymentsreceived-get-salesinvoicedata" })
	public @ResponseBody List<SalesInvoiceNewModel> viewsalesIvoiceEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewsalesIvoiceEdit starts");
		
		List<SalesInvoiceNewModel> productList = new ArrayList<SalesInvoiceNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				SalesInvoiceNewModel[] salesInvoiceNewModel = restTemplate.getForObject(
						env.getSalesUrl() + "viewsalesIvoiceEdit?id=" + id, SalesInvoiceNewModel[].class);

				productList = Arrays.asList(salesInvoiceNewModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (SalesInvoiceNewModel m : salesInvoiceNewModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
					
					  if (m.getInvoiceDate() != null && m.getInvoiceDate() != "") {
					 m.setInvoiceDate(DateFormatter.dateFormat(m.getInvoiceDate(), dateFormat));
					 
					 }
			
					if (m.getDueDate() != null && m.getDueDate() != "") {
						m.setDueDate(DateFormatter.dateFormat(m.getDueDate(), dateFormat));

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
		logger.info("Method : viewsalesIvoiceEdit ends");
		
		return productList;
	}

	
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-paymentsreceived-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getPaymentInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getPaymentInsertedId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getPaymentInsertedId ends");
		
		return res;
	}
	
	// bulk payment
	@SuppressWarnings("unchecked")
	@GetMapping("view-paymentsreceived-savePayment")
	public @ResponseBody JsonResponse<SalesInvoicePaymentModel> saveBulkPayment(HttpSession session,
			@RequestParam String userId, String bulkAmount) {

		logger.info("Method : saveBulkPayment starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (RestClientException e) {
			e.printStackTrace();

		}
		System.out.println("orgName=="+orgName);
		System.out.println("orgDivision=="+orgDivision);
		JsonResponse<SalesInvoicePaymentModel> response = new JsonResponse<SalesInvoicePaymentModel>();
		try {
			response = restTemplate.getForObject(env.getSalesUrl() + "saveBulkPayment?userId=" + userId
					+ "&bulkAmount=" + bulkAmount + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		System.out.println("response=====" + response);
		logger.info("Method : saveBulkPayment ends");
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-paymentsreceived-details")
	public @ResponseBody Object getPaymentDetails(HttpSession session, @RequestParam String paymentId) {

		logger.info("Method :getReceiveDetailsModal starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "/getReceiveDetailsModal?paymentId=" + paymentId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :getReceiveDetailsModal ends" + resp);

		return resp;
	}

}
