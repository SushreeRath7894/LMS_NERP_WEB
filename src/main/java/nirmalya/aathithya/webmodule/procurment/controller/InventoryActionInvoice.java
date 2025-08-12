package nirmalya.aathithya.webmodule.procurment.controller;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryActionInvoiceModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRequisitionModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "inventory/")
public class InventoryActionInvoice {
	Logger logger = LoggerFactory.getLogger(InventoryActionInvoice.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@GetMapping("action-invoice")
	public String getInvoiceViewPage(Model model, HttpSession session) {

		logger.info("Method : getInvoiceViewPage starts");

		/**
		 * get DropDown value for Requisition Type
		 *
		 */
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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-action-vendor-list",
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(dropDownModel);
			model.addAttribute("vendorList", vendorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-payment-status",
					DropDownModel[].class);
			List<DropDownModel> paymentStatusList = Arrays.asList(dropDownModel);
			model.addAttribute("paymentStatusList", paymentStatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for Requisition Type
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-location",
					DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(dropDownModel);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for Requisition Type
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-uom",
					DropDownModel[].class);
			List<DropDownModel> uomList = Arrays.asList(dropDownModel);
			model.addAttribute("uomList", uomList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/**
		 * payment term master
		 */
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-Payment-term",
					DropDownModel[].class);
			List<DropDownModel> paytermList = Arrays.asList(dropDownModel);
			model.addAttribute("paytermList", paytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/**
		 * get DropDown value for cost center
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-cost-center",
					DropDownModel[].class);
			List<DropDownModel> costCenterList = Arrays.asList(dropDownModel);
			model.addAttribute("costCenterList", costCenterList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);

		logger.info("Method : getInvoiceViewPage ends");
		return "procurement/action-invoice";

	}

	/*
	 * drop down for get vendor location
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/action-invoice-vendor-loc-change" })
	public @ResponseBody JsonResponse<DropDownModel> getVendorLocChange(Model model, @RequestBody String index,
			BindingResult result) {
		logger.info("Method : getVendorLocChange List starts");

		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getVendorLocChange?vendorId=" + index,
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

		logger.info("Method :getVendorLocChange List starts  ends");
		return res;
	}

	/*
	 * drop down for get vendor contact
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/action-invoice-vendor-contact-change" })
	public @ResponseBody JsonResponse<DropDownModel> getVendorContactChange(Model model, @RequestBody String index,
			BindingResult result) {
		logger.info("Method : getVendorContactChange List starts");

		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getVendorContactChange?vendorId=" + index,
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

		logger.info("Method :getVendorContactChange List starts  ends");
		return res;
	}

	/*
	 * drop down for get vendor contact
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/action-invoice-costCenterId-change" })
	public @ResponseBody JsonResponse<DropDownModel> getCostCenterChange(Model model, @RequestBody String index,
			BindingResult result) {
		logger.info("Method : getCostCenterChange List starts");

		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getCostCenterChange?costCenterId=" + index,
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

		logger.info("Method :getCostCenterChange List starts  ends");
		return res;
	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "action-invoice-activity-log" })
	public @ResponseBody List<ActivitylogModel> getActivityLog(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewStockThroughAjax starts");
		List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {

			ActivitylogModel[] activityLog = restTemplate
					.getForObject(env.getInventoryUrl() + "get-activity-log?id=" + id +"&org="+orgName +"&orgDiv=" + orgDivision, ActivitylogModel[].class);
			activityLogList = Arrays.asList(activityLog);

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ActivitylogModel m : activityLogList) {

			if (m.getOperationOn() != null && m.getOperationOn() != "") {
				m.setOperationOn(DateFormatter.dateFormat(m.getOperationOn(), dateFormat));
			}
		}
		logger.info("Method : viewStockThroughAjax ends");
		return activityLogList;
	}

	/*
	 * post Mapping for add po
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "action-invoice-save-th-ajax")
	public @ResponseBody JsonResponse<Object> savePo(
			@RequestBody InventoryActionInvoiceModel inventoryActionInvoiceModel, HttpSession session) {
		logger.info("Method : savePo function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		String userId = "";
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		inventoryActionInvoiceModel.setCreatedBy(userId);
        inventoryActionInvoiceModel
				.setDueDate(DateFormatter.inputDateFormat(inventoryActionInvoiceModel.getDueDate(), dateFormat));
		inventoryActionInvoiceModel
				.setInvDate(DateFormatter.inputDateFormat(inventoryActionInvoiceModel.getInvDate(), dateFormat));
		inventoryActionInvoiceModel.setOrganizationName(orgName);
		inventoryActionInvoiceModel.setOrganizationDivision(orgDivision);
		for (InventoryVendorDocumentModel a : inventoryActionInvoiceModel.getDocumentList()) {
			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String delimiters = "\\.";
					String[] x = a.getFileName().split(delimiters);

					if (x[1].contentEquals("png") || x[1].contentEquals("jpg") || x[1].contentEquals("jpeg")) {

						for (String s1 : a.getDocumentFile()) {
							if (s1 != null)
								try {
									byte[] bytes = Base64.getDecoder().decode(s1);
									String imageName = fileUpload.saveAllImage(bytes);
									a.setFileName(imageName);
								} catch (Exception e) {
									e.printStackTrace();
								}
						}
					} else if (x[1].contentEquals("pdf")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllPdf(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (x[1].contentEquals("docx")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllDocx(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (x[1].contentEquals("doc")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllDoc(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (x[1].contentEquals("xls")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllXls(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (x[1].contentEquals("xlsx")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllXlsx(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		try {
			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-add-action-invoice",
					inventoryActionInvoiceModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		InventoryActionInvoiceModel form = mapper.convertValue(res.getBody(),
				new TypeReference<InventoryActionInvoiceModel>() {
				});

		if (form.getActiveDate() != null) {
			form.setActiveDate(DateFormatter.dateFormat(form.getActiveDate(), dateFormat));
		}
		if (form.getCompleteDate() != null) {
			form.setCompleteDate(DateFormatter.dateFormat(form.getCompleteDate(), dateFormat));
		}
		if (form.getCreatedOn() != null) {
			form.setCreatedOn(DateFormatter.dateFormat(form.getCreatedOn(), dateFormat));
		}
		if (form.getDueDate() != null) {
			form.setDueDate(DateFormatter.dateFormat(form.getDueDate(), dateFormat));
		}
		if (form.getInvDate() != null) {
			form.setInvDate(DateFormatter.dateFormat(form.getInvDate(), dateFormat));
		}
		if (form.getOnholdDate() != null) {
			form.setOnholdDate(DateFormatter.dateFormat(form.getOnholdDate(), dateFormat));
		}
		if (form.getPayemtDueDate() != null) {
			form.setPayemtDueDate(DateFormatter.dateFormat(form.getPayemtDueDate(), dateFormat));
		}
		if (form != null) {
			for (InventoryProductModel a : form.getProductList()) {
				if (a.getCreatedOn() != null) {
					a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
				}
			}
			documentList = form.getDocumentList();
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
					m.setAction("<a href=\"/document/procurment/" + m.getFileName() + "\" target=\"_blank\" >"
							+ m.getAction() + "</a>");

				}
			}
		}
		res.setBody(form);
		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : savePo function Ends");
		return res;
	}

	/**
	 * Web Controller - Get Item List By AutoSearch
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/action-invoice-get-product-list" })
	public @ResponseBody JsonResponse<InventoryRequisitionModel> getItemAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemAutoSearchList starts");

		JsonResponse<InventoryRequisitionModel> res = new JsonResponse<InventoryRequisitionModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getProductListByAutoSearch?id=" + searchValue,
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

		logger.info("Method : getItemAutoSearchList ends");
		return res;
	}

	/*
	 * view po through ajax
	 * 
	 * 
	 */
	@GetMapping(value = { "action-invoice-get-invoice-list" })
	public @ResponseBody List<InventoryActionInvoiceModel> getPoListThrowAjax(HttpSession session) {
		logger.info("Method : getPoListThrowAjax starts");
		List<InventoryActionInvoiceModel> inventoryPoModelList = new ArrayList<InventoryActionInvoiceModel>();
		String dateFormat = (String) session.getAttribute("DATEFORMAT");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			InventoryActionInvoiceModel[] inventoryPoModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-invoice-view-list?org=" + orgName + "&orgDiv=" + orgDivision, InventoryActionInvoiceModel[].class);
			inventoryPoModelList = Arrays.asList(inventoryPoModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (InventoryActionInvoiceModel a : inventoryPoModelList) {
			if (a.getActiveDate() != null && a.getActiveDate() != "") {
				a.setActiveDate(DateFormatter.dateFormat(a.getActiveDate(), dateFormat));
			}
			if (a.getInvDate() != null && a.getInvDate() != "") {
				a.setInvDate(DateFormatter.dateFormat(a.getInvDate(), dateFormat));
			}
			if (a.getCompleteDate() != null && a.getCompleteDate() != "") {
				a.setCompleteDate(DateFormatter.dateFormat(a.getCompleteDate(), dateFormat));
			}
			if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
				a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
			}
			if (a.getDueDate() != null && a.getDueDate() != "") {
				a.setDueDate(DateFormatter.dateFormat(a.getDueDate(), dateFormat));
			}
			if (a.getOnholdDate() != null && a.getOnholdDate() != "") {
				a.setOnholdDate(DateFormatter.dateFormat(a.getOnholdDate(), dateFormat));
			}
			if (a.getPayemtDueDate() != null && a.getPayemtDueDate() != "") {
				a.setPayemtDueDate(DateFormatter.dateFormat(a.getPayemtDueDate(), dateFormat));
			}

		}
		logger.info("Method : getPoListThrowAjax ends");
		return inventoryPoModelList;
	}

	/*
	 * for copy
	 * 
	 * 
	 */
	@GetMapping(value = { "action-invoice-edit-trough-ajax" })
	public @ResponseBody InventoryActionInvoiceModel viewPoEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPoEdit starts");
		InventoryActionInvoiceModel productList = new InventoryActionInvoiceModel();
		String dateFormat = (String) session.getAttribute("DATEFORMAT");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				productList = restTemplate.getForObject(env.getInventoryUrl() + "get-invoice-edit?id=" + id +"&org="+orgName +"&orgDiv=" + orgDivision,
						InventoryActionInvoiceModel.class);
 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (productList != null) {
			for (InventoryProductModel a : productList.getProductList()) {
				if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
					a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
				}
			}

			documentList = productList.getDocumentList();
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
					m.setAction("<a href=\"/document/procurment/" + m.getFileName() + "\" target=\"_blank\" >"
							+ m.getAction() + "</a>");

				}
			}
			productList.setDocumentList(documentList);
		}
		logger.info("Method : viewPoEdit ends");
		return productList;
	}

	/*
	 * post Mapping for delete ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "action-invoice-delete-th-ajax")
	public @ResponseBody JsonResponse<Object> deleteItemRequisition(
			@RequestBody InventoryActionInvoiceModel inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : deleteItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryItemRequisitionModel.setCreatedBy(userId);
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			inventoryItemRequisitionModel.setOrganizationName(orgName);
			inventoryItemRequisitionModel.setOrganizationDivision(orgDivision);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-delete-invoice",
					inventoryItemRequisitionModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteItemRequisition function Ends");
		return res;
	}

	/*
	 * post Mapping for approve ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "action-invoice-approve-th-ajax")
	public @ResponseBody JsonResponse<Object> approveItemRequisition(
			@RequestBody InventoryActionInvoiceModel inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : approveItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryItemRequisitionModel.setCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-approve-invoice",
					inventoryItemRequisitionModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approveItemRequisition function Ends");
		return res;
	}

	/*
	 * drop down for get vendor contact
	 */
	@GetMapping(value = { "action-invoice-vendor-add-days-to-inddate" })
	public @ResponseBody JsonResponse<DropDownModel> calculateInvDate(@RequestParam String invDate,
			@RequestParam String days, HttpSession session) {
		logger.info("Method : calculateInvDate List starts");
		String dateFormat = (String) session.getAttribute("DATEFORMAT");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String format = DateFormatter.inputDateFormat(invDate, dateFormat);
		String[] splitDate = format.split("-");
		DropDownModel dropDownModel = new DropDownModel();

		LocalDate date4 = LocalDate
				.of(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]))
				.plusDays(Integer.parseInt(days));

		String indDueDate = DateFormatter.dateFormat(date4.toString(), dateFormat);

		String pattern = "yyyy-MM-dd";
		String dateInString = new SimpleDateFormat(pattern).format(new Date());

		LocalDate d2 = LocalDate.parse(dateInString, DateTimeFormatter.ISO_LOCAL_DATE);
		Duration diff = Duration.between(d2.atStartOfDay(), date4.atStartOfDay());
		Long diffDays = diff.toDays();
		dropDownModel.setKey(indDueDate);
		dropDownModel.setName(diffDays + " Days");
		res.setBody(dropDownModel);
		logger.info("Method :calculateInvDate List starts  ends");
		return res;
	}

}
