package nirmalya.aathithya.webmodule.sales.controller;

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
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;


@Controller
@RequestMapping(value = { "sales/" })
public class SalesCustomerAddController {
	
	Logger logger = LoggerFactory.getLogger(SalesCustomerAddController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;


	@GetMapping("/customer-page")
	public String openPage(Model model, HttpSession session) {
		logger.info("Method : openPage starts");
		
		logger.info("Method : openPage ends");
		return "sales/open-customer-modal";
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/customer-page-modal-adds")
	public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {

		logger.info("Method : addAccountCustomer starts");
		String organization=""; 
		String orgDivision="";
		try {
			
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		customerNewModel.setOrganization(organization);
		customerNewModel.setOrgDivision(orgDivision);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web AccountModel ======================" + customerNewModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("created by id-------------------------------" + userId);

			customerNewModel.setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getSalesUrl() + "/addAccountCustomer", customerNewModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
        System.out.println("message>>>>>>-------"+message);
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
			resp.setCode("Success");
		}
		logger.info("Method : addAccountCustomer ends");
		System.out.println("resp>>>>>>-------"+resp);
		return resp;
	}

}
