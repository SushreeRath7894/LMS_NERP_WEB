package nirmalya.aathithya.webmodule.procurment.controller;

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
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse; 
import nirmalya.aathithya.webmodule.procurment.model.InventoryGoodsReturnModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel; 
/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "inventory/")
public class InventoryGrnReturnController {
	Logger logger = LoggerFactory.getLogger(InventoryGrnReturnController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	FileUpload fileUpload;
	
	@GetMapping("view-grn-return")
	public String getGrnViewPage(Model model, HttpSession session) {

		logger.info("Method : getGrnViewPage starts");
	 
		logger.info("Method : getGrnViewPage ends");
		return "procurement/view-goods-return-note";

	}

	/*
	 * view grn through ajax
	 * 
	 * 
	 */
	@GetMapping(value = { "view-grn-return-get-view-list" })
	public @ResponseBody List<InventoryGoodsReturnModel> getGrnReturnListThrowAjax(HttpSession session) {
		logger.info("Method : getGrnReturnListThrowAjax starts");
		List<InventoryGoodsReturnModel> inventoryPoModelList = new ArrayList<InventoryGoodsReturnModel>();
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
            logger.info("orgNameeeeeeeeeeeee"+orgName+orgDivision);
			InventoryGoodsReturnModel[] inventoryPoModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-grn-return-view-list?org=" + orgName + "&orgDiv=" + orgDivision, InventoryGoodsReturnModel[].class);
			inventoryPoModelList = Arrays.asList(inventoryPoModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (InventoryGoodsReturnModel a : inventoryPoModelList) {
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
			if (a.getPoDate() != null && a.getPoDate() != "") {
				a.setPoDate(DateFormatter.dateFormat(a.getPoDate(), dateFormat));
			}
			if (a.getOnholdDate() != null && a.getOnholdDate() != "") {
				a.setOnholdDate(DateFormatter.dateFormat(a.getOnholdDate(), dateFormat));
			}
			if (a.getDispatchDate() != null && a.getDispatchDate() != "") {
				a.setDispatchDate(DateFormatter.dateFormat(a.getDispatchDate(), dateFormat));
			}
			if (a.getEstDeliveryDate() != null && a.getEstDeliveryDate() != "") {
				a.setEstDeliveryDate(DateFormatter.dateFormat(a.getEstDeliveryDate(), dateFormat));
			}

		}
		logger.info("Method : getGrnReturnListThrowAjax ends");
		return inventoryPoModelList;
	}
	/*
	 * for copy
	 * 
	 * 
	 */
	@GetMapping(value = { "view-grn-return-edit-trough-ajax" })
	public @ResponseBody InventoryGoodsReturnModel viewGrnEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewGrnEdit starts");
		InventoryGoodsReturnModel productList = new InventoryGoodsReturnModel();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		String dateFormat = (String) session.getAttribute("DATEFORMAT");
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
				productList = restTemplate.getForObject(env.getInventoryUrl() + "get-grn-return-edit?id=" + id +"&org=" + orgName + "&orgDiv=" + orgDivision,
						InventoryGoodsReturnModel.class); 
				
				if (productList.getActiveDate() != null && productList.getActiveDate() != "") {
					productList.setActiveDate(DateFormatter.dateFormat(productList.getActiveDate(), dateFormat));
				} 
				if (productList.getCompleteDate() != null && productList.getCompleteDate() != "") {
					productList.setCompleteDate(DateFormatter.dateFormat(productList.getCompleteDate(), dateFormat));
				}
				if (productList.getCreatedOn() != null && productList.getCreatedOn() != "") {
					productList.setCreatedOn(DateFormatter.dateFormat(productList.getCreatedOn(), dateFormat));
				} 
				if (productList.getOnholdDate() != null && productList.getOnholdDate() != "") {
					productList.setOnholdDate(DateFormatter.dateFormat(productList.getOnholdDate(), dateFormat));
				}
				 
				
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
		productList.setDocumentList(documentList);

		logger.info("Method : viewGrnEdit ends");
		return productList;
	}
	
	/*
	 * post Mapping for add po
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-grn-return-save-th-ajax")
	public @ResponseBody JsonResponse<Object> savePo(@RequestBody InventoryGoodsReturnModel inventoryGRNModel,
			HttpSession session) {
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
		inventoryGRNModel.setCreatedBy(userId);
		inventoryGRNModel.setOrganizationName(orgName);
		inventoryGRNModel.setOrganizationDivision(orgDivision);
 

		for (InventoryVendorDocumentModel a : inventoryGRNModel.getDocumentList()) {
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
			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-modify-grn-return", inventoryGRNModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		InventoryGoodsReturnModel form = mapper.convertValue(res.getBody(), new TypeReference<InventoryGoodsReturnModel>() {
		});

		if (form != null) {
			if (form.getActiveDate() != null) {
				form.setActiveDate(DateFormatter.dateFormat(form.getActiveDate(), dateFormat));
			}
			if (form.getCompleteDate() != null) {
				form.setCompleteDate(DateFormatter.dateFormat(form.getCompleteDate(), dateFormat));
			}
			if (form.getCreatedOn() != null) {
				form.setCreatedOn(DateFormatter.dateFormat(form.getCreatedOn(), dateFormat));
			} 
			if (form.getInvDate() != null) {
				form.setInvDate(DateFormatter.dateFormat(form.getInvDate(), dateFormat));
			}
			if (form.getOnholdDate() != null) {
				form.setOnholdDate(DateFormatter.dateFormat(form.getOnholdDate(), dateFormat));
			} 
			if (form != null) {
				for (InventoryProductModel a : form.getProductList()) {
					if (a.getCreatedOn() != null) {
						a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
					}
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
			form.setDocumentList(documentList);
			res.setBody(form);
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : savePo function Ends");
		return res;
	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "view-grn-return-activity-log" })
	public @ResponseBody List<ActivitylogModel> getActivityLog(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewStockThroughAjax starts");
		List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			ActivitylogModel[] activityLog = restTemplate
					.getForObject(env.getInventoryUrl() + "get-activity-log?id=" + id +"&org="+orgName +"&orgDiv=" + orgDivision, ActivitylogModel[].class);
			activityLogList = Arrays.asList(activityLog);

			for (ActivitylogModel m : activityLog) {
				String dateFormat = (String) session.getAttribute("DATEFORMAT");
				if (m.getOperationOn() != null && m.getOperationOn() != "") {
					m.setOperationOn(DateFormatter.dateFormat(m.getOperationOn(), dateFormat));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewStockThroughAjax ends");
		return activityLogList;
	}
	
	/*
	 * post Mapping for approve ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-grn-return-complete-grn-return")
	public @ResponseBody JsonResponse<Object> approveItemRequisition(
			@RequestBody InventoryGoodsReturnModel inventoryGoodsReturnModel, HttpSession session) {
		logger.info("Method : approveItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryGoodsReturnModel.setCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-complete-grn-return", inventoryGoodsReturnModel,
					JsonResponse.class);
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
}
