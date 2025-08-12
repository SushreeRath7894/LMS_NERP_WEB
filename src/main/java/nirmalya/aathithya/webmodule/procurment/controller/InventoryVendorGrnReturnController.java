package nirmalya.aathithya.webmodule.procurment.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.procurment.model.InventoryGoodsReturnModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "inventory/")
public class InventoryVendorGrnReturnController {
	Logger logger = LoggerFactory.getLogger(InventoryVendorGrnReturnController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@GetMapping("vendor-grn")
	public String getGrnViewPage(Model model, HttpSession session) {

		logger.info("Method : getGrnViewPage starts");

		logger.info("Method : getGrnViewPage ends");
		return "procurement/view-vendor-grn-return";

	}

	/*
	 * view GRN return through
	 * 
	 * 
	 */
	@GetMapping(value = { "vendor-grn-get-view-list" })
	public @ResponseBody List<InventoryGoodsReturnModel> getGrnReturnListThrowAjax(HttpSession session) {
		logger.info("Method : getGrnReturnListThrowAjax starts");
		List<InventoryGoodsReturnModel> inventoryPoModelList = new ArrayList<InventoryGoodsReturnModel>();
		String dateFormat = "";
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {

			InventoryGoodsReturnModel[] inventoryPoModel = restTemplate.getForObject(
					env.getInventoryUrl() + "get-vendor-grn-return-view-list?userId=" + userId +"&org=" + orgName + "&orgDiv=" + orgDivision,
					InventoryGoodsReturnModel[].class);
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
	@GetMapping(value = { "vendor-grn-return-edit-trough-ajax" })
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
	
}
