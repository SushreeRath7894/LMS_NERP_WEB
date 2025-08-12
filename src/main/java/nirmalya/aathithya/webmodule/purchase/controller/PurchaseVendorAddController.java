package nirmalya.aathithya.webmodule.purchase.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import groovyjarjarpicocli.CommandLine.Model;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;

@Controller
@RequestMapping(value = { "purchase/" })
public class PurchaseVendorAddController {
	Logger logger = LoggerFactory.getLogger(PurchaseVendorAddController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;


	@GetMapping("/vendor-page")
	public String openPage(Model model, HttpSession session) {
		logger.info("Method : openPage starts");
		
		logger.info("Method : openPage ends");
		return "purchase/open-vendor-modal";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-page-modal-adds")
	public @ResponseBody JsonResponse<Object> addVendor(@RequestBody VendorNewModel vendorNewModel,
			HttpSession session) {

		logger.info("Method : addVendorModal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
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

			vendorNewModel.setCreatedBy(userId);
			vendorNewModel.setOrgName(orgName);
			vendorNewModel.setOrgDivision(orgDivision);

			 System.out.println("VENDORADD" + vendorNewModel);

			//resp = restTemplate.postForObject(env.getPurchaseUrl() + "addVendor", vendorNewModel, JsonResponse.class);
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "/addVendor", vendorNewModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addVendor ends");

		return resp;
	}
}
