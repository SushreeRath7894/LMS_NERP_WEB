package nirmalya.aathithya.webmodule.purchase.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = { "purchase/" })
public class DebitNoteWebController {
	
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

	@GetMapping(value = { "/view-debit-note" })
	public String debitNoteDetails(Model model, HttpSession session) {
		logger.info("Method : debitNoteDetails starts");

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getPurchaseUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : debitNoteDetails ends");
		return "purchase/view-debit-note";
	}
	
	
	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-debit-note-through-ajax")
	public @ResponseBody Object viewDebitNoteData(HttpSession session) {
		logger.info("Method :viewDebitNoteData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String type = "debit";
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "viewDebitNoteData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision +  "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewDebitNoteData ends");
		return resp;

	}
	
	
	/*
	 * view Dtls.
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-debit-note-edit-dtls")
	public @ResponseBody Object viewDebitNoteEditData(@RequestParam String id, HttpSession session) {
		logger.info("Method :viewDebitNoteEditData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String type = "debit";
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "viewDebitNoteEditData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision +  "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewDebitNoteEditData ends");
		return resp;

	}

}
