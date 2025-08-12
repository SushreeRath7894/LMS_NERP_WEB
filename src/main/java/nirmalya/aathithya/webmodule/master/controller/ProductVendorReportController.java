package nirmalya.aathithya.webmodule.master.controller;

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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProductVendorReportModel;

@Controller
@RequestMapping(value = { "master/" })
public class ProductVendorReportController {
	Logger logger = LoggerFactory.getLogger(ProductVendorReportController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "product-vendor-report" })
	public String productVendorReport(Model model, HttpSession session) {
		logger.info("Method : productVendorReport starts");
 

		logger.info("Method : productVendorReport ends");
		return "master/product-vendor-report";
	}
	@SuppressWarnings("unchecked")
	@GetMapping("product-vendor-report-view")
	public @ResponseBody List<ProductVendorReportModel> viewProductVendorReport(HttpSession session) {

		logger.info("Method :viewProductVendorReport starts");
		JsonResponse<List<ProductVendorReportModel>> resp = new JsonResponse<List<ProductVendorReportModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "viewProductVendorReport?org=" + orgName + "&orgDiv=" + orgDivision,
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

		logger.info("Method :viewProductVendorReport ends");
		return resp.getBody();
	}
}
