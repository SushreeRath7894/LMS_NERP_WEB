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
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceReturnModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller
@RequestMapping(value = { "purchase/" })
public class CreditNoteWebController {

	Logger logger = LoggerFactory.getLogger(CreditNoteWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	CreditNoteWebController creditNoteWebController;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/view-credit-note" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : poDetails starts");

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getPurchaseUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : Details ends");
		return "purchase/view-creditNote";
	}
	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-credit-note-through-ajax")
	public @ResponseBody Object viewcreditNoteData(HttpSession session) {
		logger.info("Method :viewcreditNoteData starts");
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
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "viewcreditNoteData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewcreditNoteData ends");
		return resp;

	}
	
	/*
	 * view Dtls
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-credit-note-edit-dtls")
	public @ResponseBody Object viewcreditNoteEditData(@RequestParam String id,HttpSession session) {
		logger.info("Method :viewcreditNoteEditData starts");
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
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "viewcreditNoteEditData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewcreditNoteEditData ends");
		return resp;

	}

}
