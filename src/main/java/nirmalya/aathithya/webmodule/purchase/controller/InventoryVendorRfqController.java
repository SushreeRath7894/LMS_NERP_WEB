package nirmalya.aathithya.webmodule.purchase.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorRfqModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseQuotationModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "purchase/")
public class InventoryVendorRfqController {

	Logger logger = LoggerFactory.getLogger(InventoryVendorRfqController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@GetMapping("vendor-rfq")
	public String getRfqViewPage(Model model, HttpSession session) {

		logger.info("Method : getRfqViewPage starts");

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
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-requisition-type",
					DropDownModel[].class);
			List<DropDownModel> requisitionTypeList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionTypeList", requisitionTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-requisition-priority", DropDownModel[].class);
			List<DropDownModel> requisitionPrioList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionPrioList", requisitionPrioList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/**
		 * get DropDown value for Requisition Type
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
		
		try {

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/**
		 * for document type list
		 */
		try {
			DropDownModel[] document = restTemplate.getForObject(env.getEmployeeUrl() + "getDocumentTypeList",
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(document);
			model.addAttribute("documentTypeList", documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getRfqViewPage ends");
		return "purchase/view-inventory-vendor-rfq";

	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "vendor-rfq-activity-log" })
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
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("vendor-rfq-view-through-ajax")
	public @ResponseBody Object viewVendorRFQData(HttpSession session) {
		logger.info("Method :viewVendorRFQData starts");
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
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "viewVendorRFQData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewVendorRFQData ends");
		return resp;

	}
	
	// EDIT
		@GetMapping(value = { "vendor-rfq-vendor-edit-new" })
		public @ResponseBody List<PurchaseQuotationModel> viewRFQVendorEdit(@RequestParam String id, HttpSession session) {
			logger.info("Method : viewRFQVendorEdit starts");
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			List<PurchaseQuotationModel> productList = new ArrayList<PurchaseQuotationModel>();

			if (id != null && id != "") {
				try {
					PurchaseQuotationModel[] purchaseQuotationModel = restTemplate.getForObject(env.getPurchaseUrl()
							+ "viewRFQVendorEdit?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId ,
							PurchaseQuotationModel[].class);

					productList = Arrays.asList(purchaseQuotationModel);

					productList.forEach(s -> s.setSlNo(s.getSlNo()));

					int count = 0;

					for (PurchaseQuotationModel m : purchaseQuotationModel) {
						m.setQuantity(m.getQuantity());
						count++;
						m.setSlNo(count);
						String dateFormat = (String) session.getAttribute("DATEFORMAT");

						/*
						 * if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
						 * m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));
						 * 
						 * } if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						 * m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));
						 * 
						 * }
						 */

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("Method : viewRFQVendorEdit ends");
			return productList;
		}


	/*
	 * for edit
	 * 
	 * 
	 */
	@GetMapping(value = { "vendor-rfq-item-trough-ajax" })
	public @ResponseBody List<InventoryVendorRfqModel> viewRequsitionEdit(@RequestParam String id, HttpSession session,
			Model model) {
		logger.info("Method : viewRequsitionEdit starts");
		List<InventoryVendorRfqModel> productList = new ArrayList<InventoryVendorRfqModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		String userId = "";
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (id != null && id != "") {
			try {
				userId = (String) session.getAttribute("VENDOR_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				InventoryVendorRfqModel[] inventoryStockModel = restTemplate.getForObject(
						env.getInventoryUrl() + "get-vendor-rfq-edit?id=" + id + "&userId=" + userId +"&org="+orgName +"&orgDiv=" + orgDivision,
						InventoryVendorRfqModel[].class);
				productList = Arrays.asList(inventoryStockModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (productList != null) {
			documentList = productList.get(0).getDocumentList();
			for (InventoryVendorRfqModel a : productList) {
				if (a.getClosedDate() != null && a.getClosedDate() != "") {
					a.setClosedDate(DateFormatter.dateFormat(a.getClosedDate(), dateFormat));
				}
				if (a.getLastPoDate() != null && a.getLastPoDate() != "") {
					a.setLastPoDate(DateFormatter.dateFormat(a.getLastPoDate(), dateFormat));
				}
				if (a.getOpenDate() != null && a.getOpenDate() != "") {
					a.setOpenDate(DateFormatter.dateFormat(a.getOpenDate(), dateFormat));
				}
				if (a.getReceiveDate() != null && a.getReceiveDate() != "") {
					a.setReceiveDate(DateFormatter.dateFormat(a.getReceiveDate(), dateFormat));
				}
				if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
					a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
				}
				if (a.getSubmittedDate() != null && a.getSubmittedDate() != "") {
					a.setSubmittedDate(DateFormatter.dateFormat(a.getSubmittedDate(), dateFormat));
				}
			}
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
		productList.get(0).setDocumentList(documentList);
		logger.info("Method : viewRequsitionEdit ends"+productList);
		return productList;
	}

	/*
	 * post Mapping for add ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "vendor-rfq-save-th-ajax")
	public @ResponseBody JsonResponse<Object> saveVendorRfq(
			@RequestBody List<InventoryVendorRfqModel> inventoryItemRequisitionModel, HttpSession session,
			Model model) {
		logger.info("Method : saveVendorRfq function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		List<InventoryVendorRfqModel> vendorReqList = new ArrayList<InventoryVendorRfqModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
		logger.info("inventoryItemRequisitionModel==="+inventoryItemRequisitionModel);
		String userId = "";
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("VENDOR_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (InventoryVendorRfqModel m : inventoryItemRequisitionModel) {
			m.setCreatedBy(userId);
			m.setReceiveDate(DateFormatter.inputDateFormat(m.getReceiveDate(), dateFormat));
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDivision);

		}
		for (InventoryVendorDocumentModel a : inventoryItemRequisitionModel.get(0).getDocumentList()) {
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
		logger.info("inventoryItemRequisitionModel==="+inventoryItemRequisitionModel);
		try {
			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-add-vendor-rqf",
					inventoryItemRequisitionModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		vendorReqList = mapper.convertValue(res.getBody(), new TypeReference<List<InventoryVendorRfqModel>>() {
		});
		if (vendorReqList != null) {

			for (InventoryVendorRfqModel a : vendorReqList) {
				if (a.getClosedDate() != null && a.getClosedDate() != "") {
					a.setClosedDate(DateFormatter.dateFormat(a.getClosedDate(), dateFormat));
				}
				if (a.getLastPoDate() != null && a.getLastPoDate() != "") {
					a.setLastPoDate(DateFormatter.dateFormat(a.getLastPoDate(), dateFormat));
				}
				if (a.getOpenDate() != null && a.getOpenDate() != "") {
					a.setOpenDate(DateFormatter.dateFormat(a.getOpenDate(), dateFormat));
				}
				if (a.getReceiveDate() != null && a.getReceiveDate() != "") {
					a.setReceiveDate(DateFormatter.dateFormat(a.getReceiveDate(), dateFormat));
				}
				if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
					a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
				}
				if (a.getSubmittedDate() != null && a.getSubmittedDate() != "") {
					a.setSubmittedDate(DateFormatter.dateFormat(a.getSubmittedDate(), dateFormat));
				}
			}
			docList = vendorReqList.get(0).getDocumentList();
			for (InventoryVendorDocumentModel m : docList) {
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
						if (extension[1].equals("doc") || extension[1].equals("dox") || extension[1].equals("docx")) {
							String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
									+ m.getFileName() + "></i> ";
							m.setAction(docPath);
						}
						if (extension[1].equals("png") || extension[1].equals("jpg") || extension[1].equals("jpeg")) {
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
		String message = res.getMessage();
		vendorReqList.get(0).setDocumentList(docList);
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		res.setBody(vendorReqList);
		logger.info("Method : saveVendorRfq function Ends");
		return res;
	}

}
