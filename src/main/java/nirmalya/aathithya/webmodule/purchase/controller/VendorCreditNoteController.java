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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = { "purchase/" })
public class VendorCreditNoteController {

	Logger logger = LoggerFactory.getLogger(VendorCreditNoteController.class);

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

	@GetMapping(value = { "/vendor-creditnote" })
	public String vendorDetails(Model model, HttpSession session) {
		logger.info("Method : vendorDetails starts");

	/*	try {
			DropDownModel[] uom = restTemplate.getForObject(env.getPurchaseUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		logger.info("Method : vendorDetails ends");
		return "purchase/vendor-credit-note";
	}
	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("vendor-creditnote-through-ajax")
	public @ResponseBody Object viewcreditNoteDataForVendor(HttpSession session) {
		logger.info("Method :viewcreditNoteDataForVendor starts");
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
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "viewcreditNoteDataForVendor?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewcreditNoteDataForVendor ends");
		return resp;

	}

}
