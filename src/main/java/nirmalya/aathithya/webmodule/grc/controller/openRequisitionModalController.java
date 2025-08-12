package nirmalya.aathithya.webmodule.grc.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseIndentModel;

@Controller
@RequestMapping(value = "grc/")
public class openRequisitionModalController {

	Logger logger = LoggerFactory.getLogger(openRequisitionModalController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("open-modal-requisition")
	public String generateInventoryStockReport(Model model, HttpSession session) {

		logger.info("Method : viewManageCus start");

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

			DropDownModel[] department = restTemplate.getForObject(env.getPurchaseUrl()+
					"ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : generateInventoryStockReport ends");
		return "grc/open-requisition-modal";

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

	@SuppressWarnings("unchecked")
	@PostMapping("/open-modal-requisition-product-category-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getAllProductCategoryList(HttpSession session) {
		logger.info("Method : getAllProductCategoryList starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getAllProductCategoryList", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAllProductCategoryList starts");
		return resp;
	}

	/*
	 * Item autosearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "open-modal-requisition-item-get-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
		logger.info("QuotationNewModel" + searchValue);
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "getItemQuotationAutoSearchListForPurchaseItem?id=" + searchValue,
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
		logger.info("Method : getItemQuotationAutoSearchList ends");
		return res;
	}
	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "open-modal-requisition-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
			@RequestBody String index, BindingResult result) {
		logger.info("Method : getProductsByCatInvoice starts");

		String indexValue = index.substring(0, index.length() - 1);

		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getProductsNByCat?id=" + indexValue,
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

		logger.info("Method : getProductsByCatInvoice ends");
		return res;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("open-modal-requisition-add")
	public @ResponseBody JsonResponse<Object> addRequisitionDetails(HttpSession session,
			@RequestBody List<PurchaseIndentModel> purchaseModel) {
		logger.info("Method : addRequisitionDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<PurchaseIndentModel> documentList = new ArrayList<PurchaseIndentModel>();
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

		for (PurchaseIndentModel m : purchaseModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}

		for (InventoryVendorDocumentModel a : purchaseModel.get(0).getDocumentList()) {

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

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addRequisitionDetails", purchaseModel,
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

		logger.info("Method : addRequisitionDetails ends");

		return resp;
	}

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("open-modal-requisition-through-ajax")
	public @ResponseBody List<PurchaseIndentModel> viewRequisitionDetails(HttpSession session) {

		logger.info("Method :viewRequisitionDetails startss");
		JsonResponse<List<PurchaseIndentModel>> resp = new JsonResponse<List<PurchaseIndentModel>>();
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
					env.getPurchaseUrl() + "rest-viewRequisitionDetails?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<PurchaseIndentModel> purchaseModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<PurchaseIndentModel>>() {
				});

		for (PurchaseIndentModel a : purchaseModel) {
			if (a.getReceiveDate() != null && a.getReceiveDate() != "") {
				a.setReceiveDate(DateFormatter.dateFormat(a.getReceiveDate(), dateFormat));
			}
			if (a.getUpdatedOn() != null && a.getUpdatedOn() != "") {
				a.setUpdatedOn(DateFormatter.dateFormat(a.getUpdatedOn(), dateFormat));
			}

		}

		resp.setBody(purchaseModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewRequisitionDetails ends"+resp.getBody());

		return resp.getBody();
	}

	/*
	 * edit
	 */

	@GetMapping(value = { "open-modal-requisition-edit-new" })
	public @ResponseBody List<PurchaseIndentModel> viewPurchaseRequisitionEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewPurchaseRequisitionEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<PurchaseIndentModel> productList = new ArrayList<PurchaseIndentModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				PurchaseIndentModel[] purchaseOrderModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewPurchaseRequisitionEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseIndentModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseIndentModel m : purchaseOrderModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

					}
					if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
						m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));

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
		logger.info("Method : viewPurchaseRequisitionEdit ends");

		return productList;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("open-modal-requisition-delete")
	public @ResponseBody JsonResponse<Object> deleteRequisitionDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteRequisitionDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteRequisitionDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteRequisitionDetails function Ends");

		return res;
	}
	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("open-modal-requisition-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveRequisitionDetails(HttpSession session,
			@RequestParam String approveStatus, String reqId) {

		logger.info("Method : approveRequisitionDetails starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
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
			response = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "approveRequisitionDetails?approveStatus=" + approveStatus
									+ "&reqId=" + reqId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		logger.info("Method : approveRequisitionDetails ends");
		return response;
	}
	
	// get Product Category Data List Modal

		@SuppressWarnings("unchecked")
		@PostMapping("open-modal-requisition-item-get-product-list")
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
		
		
		

@PostMapping("/open-modal-requisition-upload-file")
	public @ResponseBody JsonResponse<Object> uploadPartsSupplirData(@RequestParam("file") MultipartFile attendance,
			HttpSession session) {
		logger.info("Method : uploadPartsSupplirData controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(attendance.getInputStream());
			response.setMessage(attendance.getOriginalFilename());
			session.setAttribute("attendance", workbook);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : uploadPartsSupplirData controller ' ends");
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/open-modal-requisition-save-excelData")
	public @ResponseBody JsonResponse<Object> addRequisitionMaster(@RequestBody List<PurchaseIndentModel> attendanceModel,
			Model model, HttpSession session) {
		logger.info("Method :addRequisitionMaster starts");

		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {

			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (RestClientException e) {
			e.printStackTrace();

		}
		List<PurchaseIndentModel> attendanceList = new ArrayList<>();
		XSSFWorkbook workbook = (XSSFWorkbook) session.getAttribute("attendance");
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
			if (index > 0) {
				PurchaseIndentModel atten = new PurchaseIndentModel();

				XSSFRow row = worksheet.getRow(index);
				// Integer id = (int) row.getCell(0).getNumericCellValue();
				DataFormatter formatter = new DataFormatter();; // creating formatter using the default locale

				atten.setDeptId(formatter.formatCellValue(row.getCell(0)));
				atten.setIndentDate(DateFormatter.getStringDateNew(formatter.formatCellValue(row.getCell(1))));
				atten.setDesc(formatter.formatCellValue(row.getCell(2)));
				atten.setSku(formatter.formatCellValue(row.getCell(3)));
				atten.setHsnCode(formatter.formatCellValue(row.getCell(4)));
				atten.setItemName(formatter.formatCellValue(row.getCell(5)));
				atten.setModel(formatter.formatCellValue(row.getCell(6)));
				atten.setQty(formatter.formatCellValue(row.getCell(7)));
				atten.setUnit(formatter.formatCellValue(row.getCell(8)));
				atten.setCreatedBy(userId);
				atten.setOrganization(orgName);
				atten.setOrgDivision(orgDivision);
				attendanceList.add(atten);

			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-addRequisitionMasterFile", attendanceList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
			session.removeAttribute("attendance");
		}
		logger.info("Method : addRequisitionMaster ends");
		return resp;
	}



}
