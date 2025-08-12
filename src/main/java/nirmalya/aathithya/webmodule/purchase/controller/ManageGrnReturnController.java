package nirmalya.aathithya.webmodule.purchase.controller;

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
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.AdvanceManagementModelNew;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceReturnModel;
import nirmalya.aathithya.webmodule.purchase.model.PhysicalVarificationModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller
@RequestMapping(value = { "purchase/" })
public class ManageGrnReturnController {

	Logger logger = LoggerFactory.getLogger(ManageGrnReturnController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	ManageGrnReturnController manageGrnReturnController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/manage-invoice-return" })
	public String customerDetails(Model model, HttpSession session) {
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

			DropDownModel[] department = restTemplate.getForObject(env.getPurchaseUrl()+
					"ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
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
		logger.info("Method : Details ends");
		return "purchase/manage-invoice-return";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-invoice-return-get-returnid" })
	public @ResponseBody JsonResponse<Object> geInvoiceReturnId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "geInvoiceReturnId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : geInvoiceReturnId ends");

		return res;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("manage-invoice-return-add")
	public @ResponseBody JsonResponse<Object> addInvoiceReturn(HttpSession session,
			@RequestBody List<ManageInvoiceReturnModel> manageInvoiceReturnModel) {
		logger.info("Method : addInvoiceReturn starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<ManageInvoiceReturnModel> documentList = new ArrayList<ManageInvoiceReturnModel>();
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

		for (ManageInvoiceReturnModel m : manageInvoiceReturnModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}

		/*
		 * for (InventoryVendorDocumentModel a :
		 * manageInvoiceReturnModel.get(0).getDocumentList()) {
		 * 
		 * if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
		 * a.setFileName(a.getImageNameEdit()); } else { if (a.getFileName() != null &&
		 * a.getFileName() != "") { String[] extension = a.getFileName().split("\\.");
		 * int lastindex = extension.length - 1; for (String s1 : a.getDocumentFile()) {
		 * try { byte[] bytes = Base64.getDecoder().decode(s1); logger.info("bytes" +
		 * s1); String imageName = saveAllMultiImages(bytes, extension[lastindex]);
		 * a.setFileName(imageName);
		 * 
		 * } catch (Exception e) { e.printStackTrace();
		 * 
		 * } } } } }
		 */
		
		for (InventoryVendorDocumentModel a : manageInvoiceReturnModel.get(0).getDocumentList()) {

			if (a.getIsEditDoc() == null && a.getIsEditDoc() == "") {
				a.setDocView(a.getDocView());
			} else {
				if (a.getDocView() != null && a.getDocView() != "") {
					
					String extension = a.getDocType();
					
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, extension);
							a.setDocView(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}

				}
			}
		}

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addInvoiceReturn", manageInvoiceReturnModel,
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

		logger.info("Method : addInvoiceReturn ends");

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
	@GetMapping("manage-invoice-returnView-through-ajax")
	public @ResponseBody List<ManageInvoiceReturnModel> viewInvoiceReturn(HttpSession session) {

		logger.info("Method :viewInvoiceReturn startsssssssssssssssssssssss");
		JsonResponse<List<ManageInvoiceReturnModel>> resp = new JsonResponse<List<ManageInvoiceReturnModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-viewInvoiceReturn?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<ManageInvoiceReturnModel> manageInvoiceReturnModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageInvoiceReturnModel>>() {
				});

		for (ManageInvoiceReturnModel a : manageInvoiceReturnModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}
		}

		resp.setBody(manageInvoiceReturnModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewInvoiceReturn ends");

		return resp.getBody();
	}

	/*
	 * edit
	 */

	@GetMapping(value = { "manage-invoice-return-edit-new" })
	public @ResponseBody List<ManageInvoiceReturnModel> viewInvoiteReturnEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewInvoiteReturnEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		List<ManageInvoiceReturnModel> productList = new ArrayList<ManageInvoiceReturnModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				ManageInvoiceReturnModel[] manageInvoiceReturnModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewInvoiceReturnEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						ManageInvoiceReturnModel[].class);

				productList = Arrays.asList(manageInvoiceReturnModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (ManageInvoiceReturnModel m : manageInvoiceReturnModel) {
					// m.setQuantitynew(m.getQuantity());
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
					if (m.getExptdeliveryDate() != null && m.getExptdeliveryDate() != "") {
						m.setExptdeliveryDate(DateFormatter.dateFormat(m.getExptdeliveryDate(), dateFormat));

					}

				}
				/*
				 * if (productList != null) { documentList =
				 * productList.get(0).getDocumentList(); if (documentList != null) { for
				 * (InventoryVendorDocumentModel m : documentList) { if (m.getFileName() != null
				 * && m.getFileName() != "") {
				 * 
				 * String[] extension = m.getFileName().split("\\."); if (extension.length == 2)
				 * { if (extension[1].equals("xls") || extension[1].equals("xlsx")) {
				 * 
				 * String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " +
				 * m.getFileName() + "></i> ";
				 * 
				 * m.setAction(docPath); } if (extension[1].equals("pdf")) { String docPath =
				 * " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName() +
				 * " ;></i> ";
				 * 
				 * m.setAction(docPath); } if (extension[1].equals("doc") ||
				 * extension[1].equals("dox") || extension[1].equals("docx")) { String docPath =
				 * " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title=" +
				 * m.getFileName() + "></i> "; m.setAction(docPath); } if
				 * (extension[1].equals("png") || extension[1].equals("jpg") ||
				 * extension[1].equals("jpeg")) { String docPath =
				 * " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title=" +
				 * m.getFileName() + "></i>  "; m.setAction(docPath); } } else {
				 * m.setAction("N/A"); } } else { m.setAction("N/A"); }
				 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
				 * "\" target=\"_blank\" >" + m.getAction() + "</a>"); logger.info("m.setAction"
				 * + m);
				 * 
				 * } } }
				 */
				
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getDocView() != null && m.getDocView() != "") {
								
								String docPath = "/document/document/" + m.getDocView();

								m.setDociURL(docPath);
								
								// Set Doc Type.
								String[] extension = m.getDocView().split("\\.");
								m.setDocType(extension[1]);
								
							} else {
								m.setDociURL(null);
								m.setDocType(null);
							}
							/*
							 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
							 * "\" target=\"_blank\" >" + m.getAction() + "</a>");
							 */
							logger.info("m.setDociURL" + m.getDociURL());

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Method : viewInvoiteReturnEdit ends");

		return productList;
	}

	/*
	 * approveInvoiceReturn
	 */
	
	@SuppressWarnings("unchecked")

	@PostMapping("manage-invoice-return-approve")
	public @ResponseBody JsonResponse<Object> approveInvoiceReturn(@RequestBody ManageInvoiceReturnModel manageInvoiceReturnModel,
			HttpSession session) {
		logger.info("Method : approveInvoiceReturn starts");

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
		manageInvoiceReturnModel.setOrganization(organization);
		manageInvoiceReturnModel.setOrgDivision(orgDivision);
	    manageInvoiceReturnModel.setCreatedBy(userId);

		
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "approveInvoiceReturn", manageInvoiceReturnModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}


		  
		  if (resp.getMessage() != "" && resp.getMessage() != null) {
		  resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
		  resp.setMessage("Success"); }
		  
		  logger.info("Method : approveInvoiceReturn ends");
		  return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-invoice-return-get-grnid" })
	public @ResponseBody JsonResponse<PhysicalVarificationModel> getGrnId(Model model,
			@RequestBody String searchValue, HttpSession session,BindingResult result) {
		logger.info("Method : getGrnId starts");
		String org = "";
		String orgDiv = "";
		String type = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<PhysicalVarificationModel> res = new JsonResponse<PhysicalVarificationModel>();
		try {
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-getReturnId?id=" + searchValue
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
		logger.info("Method : getGrnId ends");
		return res;
	}
	
	
	@GetMapping(value = { "manage-invoice-return-invoiceEdit" })
	public @ResponseBody List<ManageInvoiceModel> viewInvoiteEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewInvoiteEdit starts");
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
		
		try {
			ManageInvoiceModel[] manageInvoiceModel = restTemplate.getForObject(env.getPurchaseUrl()
					+ "viewReturnEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					ManageInvoiceModel[].class);
			productList = Arrays.asList(manageInvoiceModel);
			
			if (productList != null) {
				documentList = productList.get(0).getDocumentList();
				
				if (documentList != null) {
					for (InventoryVendorDocumentModel m : documentList) {
						if (m.getDocView() != null && m.getDocView() != "") {
							
							String docPath = "/document/document/" + m.getDocView();

							m.setDociURL(docPath);
							
							// Set Doc Type.
							String[] extension = m.getDocView().split("\\.");
							m.setDocType(extension[1]);
							
						} else {
							m.setDociURL(null);
							m.setDocType(null);
						}
						/*
						 * m.setAction("<a href=\"/document/document/" + m.getFileName() +
						 * "\" target=\"_blank\" >" + m.getAction() + "</a>");
						 */
						logger.info("m.setDociURL" + m.getDociURL());

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		logger.info("Method : viewInvoiteEdit ends" + productList);

		return productList;
	}
	
	
	
}
