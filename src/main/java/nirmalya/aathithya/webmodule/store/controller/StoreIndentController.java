package nirmalya.aathithya.webmodule.store.controller;

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
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.controller.PurchaseIndentController;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseIndentModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "store/")
public class StoreIndentController {

	Logger logger = LoggerFactory.getLogger(StoreIndentController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/store-indent")
	public String viewManageCus(Model model, HttpSession session) {
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
		try {

			DropDownModel[] department = restTemplate.getForObject(env.getRecruitment() + "DepartmentList",
					DropDownModel[].class);
			List<DropDownModel> DepartmentList = Arrays.asList(department);
			model.addAttribute("DepartmentList", DepartmentList);
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
		logger.info("Method : viewManageCus end");
		return "store/manage-indent";
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
	@PostMapping("/store-indent-product-category-get-total-list")
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
	@PostMapping(value = { "store-indent-item-get-list" })
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
		logger.info("RESPONSE@@" + res);
		logger.info("Method : getItemQuotationAutoSearchList ends");
		return res;
	}
	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "store-indentr-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
			@RequestBody String index, HttpSession session) {
		logger.info("Method : getProductsByCatInvoice starts");

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

		logger.info("Method : getProductsByCatInvoice ends");
		return res;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("store-indent-add")
	public @ResponseBody JsonResponse<Object> addIndentDetails(HttpSession session,
			@RequestBody List<PurchaseIndentModel> purchaseModel) {
		logger.info("Method : addIndentDetails starts");

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

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addIndentDetails", purchaseModel,
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

		logger.info("Method : addIndentDetails ends");

		return resp;
	}

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("store-indent-through-ajax")
	public @ResponseBody List<PurchaseIndentModel> viewIndentDetails(HttpSession session) {

		logger.info("Method :viewIndentDetails startss");
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
					env.getPurchaseUrl() + "rest-viewIndentDetails?org=" + orgName + "&orgDiv=" + orgDivision,
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

		logger.info("Method :viewIndentDetails ends");

		return resp.getBody();
	}

	/*
	 * edit
	 */

	@GetMapping(value = { "store-indent-edit-new" })
	public @ResponseBody List<PurchaseIndentModel> viewPurchaseIndentEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewPurchaseIndentEdit starts");
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
						+ "viewPurchaseIndentEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseIndentModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseIndentModel m : purchaseOrderModel) {
					// m.setQuantitynew(m.getQuantity());
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
		logger.info("Method : viewPurchaseIndentEdit ends");

		return productList;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("store-indent-delete")
	public @ResponseBody JsonResponse<Object> deleteIndentDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteIndentDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteIndentDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteIndentDetails function Ends");

		return res;
	}
	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("store-indent-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveIndentDetails(HttpSession session,
			@RequestParam String approveStatus, String indentId) {

		logger.info("Method : approveIndentDetails starts");
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
		logger.info("APRRRVO" + indentId);
		try {
			response = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "approveIndentDetails?approveStatus=" + approveStatus + "&indentId="
									+ indentId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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

		logger.info("response=====" + response);
		logger.info("Method : approveIndentDetails ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("store-indent-add-feedback")
	public @ResponseBody JsonResponse<Object> addFeedBackDetails(@RequestBody PurchaseIndentModel purchaseIndentModel,
			HttpSession session) {
		logger.info("Method : addFeedBackDetails starts");

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

		purchaseIndentModel.setCreatedBy(userId);
		purchaseIndentModel.setOrganization(orgName);
		purchaseIndentModel.setOrgDivision(orgDivision);
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addFeedBackDetails", purchaseIndentModel,
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

		logger.info("Method :addFeedBackDetails ends");
		return resp;
	}
}
