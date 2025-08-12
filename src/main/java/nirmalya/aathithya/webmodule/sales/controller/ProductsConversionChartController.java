package nirmalya.aathithya.webmodule.sales.controller;

import java.util.Arrays;
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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.sales.model.ProductsConversionChartModel;

@Controller
@RequestMapping(value = { "sales/" })
public class ProductsConversionChartController {
	Logger logger = LoggerFactory.getLogger(ProductsConversionChartController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("products-conversion-chart")
	public String viewProductsConversionRender(Model model, HttpSession session) {
		logger.info("Method : viewProductsConversionRender starts");
		
		try {
			String orgName = "";
			String orgDiv = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
					
			} catch (Exception e) {

			}
			ProductsConversionChartModel[] bank = restTemplate.getForObject(env.getSalesUrl() + "/getProductConversionList?orgName=" + orgName + "&orgDiv=" + orgDiv,
					ProductsConversionChartModel[].class);
			List<ProductsConversionChartModel> productList = Arrays.asList(bank);
			logger.info("productList==>"+productList);
			model.addAttribute("productList", productList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : viewProductsConversionRender end");
		return "sales/products-conversion-chart";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/products-conversion-chart-add")
	public @ResponseBody JsonResponse<Object> addProductConversion(@RequestBody ProductsConversionChartModel productsConversionChartModel,
			HttpSession session) {

		logger.info("Method : addProductConversion starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web productsConversionChartModel ===>" + productsConversionChartModel);
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

			productsConversionChartModel.setCreatedBy(userId);
			productsConversionChartModel.setOrgName(orgName);
			productsConversionChartModel.setOrgDiv(orgDivision);

			resp = restTemplate.postForObject(env.getSalesUrl() + "/restAddProductConversion", productsConversionChartModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("resp==>"+resp);
		logger.info("Method : addProductConversion ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("products-conversion-chart-view")
	public @ResponseBody Object viewProductConversionChart(HttpSession session) {

		logger.info("Method :viewProductConversionChart starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "/restViewProductConversionChart?orgName=" + orgName + "&orgDivision=" + orgDivision ,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method :viewProductConversionChart ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("products-conversion-chart-edit")
	public @ResponseBody Object editProductConversionChart(HttpSession session, @RequestParam String id) {

		logger.info("Method :editProductConversionChart starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "/restEditProductConversionChart?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method :editProductConversionChart ends" + resp);
		return resp;
	}
}
