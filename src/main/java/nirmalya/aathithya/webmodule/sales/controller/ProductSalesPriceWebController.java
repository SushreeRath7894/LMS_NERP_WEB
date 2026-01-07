package nirmalya.aathithya.webmodule.sales.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = { "sales/" })
public class ProductSalesPriceWebController {
	Logger logger = LoggerFactory.getLogger(ProductSalesPriceWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "/view-sales-price" })
	public String productSalesPrice(Model model, HttpSession session, @RequestParam Optional<String> id) {
		logger.info("Method : productSalesPrice starts");
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String typeValue = "";
		if (id.isPresent()) {
			typeValue = id.get();
		}
		try {
			DropDownModel[] category = restTemplate.getForObject(env.getMasterUrl()
					+ "getWarehouseTypeWiseItemCategoryList?org=" + org + "&orgDiv=" + orgDiv + "&type=" + typeValue,
					DropDownModel[].class);
			List<DropDownModel> categorylist = Arrays.asList(category);
			model.addAttribute("categorylist", categorylist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
//		try {
//			DropDownModel[] brand = restTemplate.getForObject(
//					env.getSalesUrl() + "get-item-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
//			List<DropDownModel> productList = Arrays.asList(brand);
//
//			model.addAttribute("productList", productList);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		logger.info("Method : productSalesPrice ends");
		return "sales/product-sales-price";

	}

	// viewAllocationData
	@SuppressWarnings("unchecked")
	@GetMapping("view-sales-price-data-through-ajax")
	public @ResponseBody Object viewSalesPriceData(@RequestParam String pageno, @RequestParam String type,
			HttpSession session) {
		logger.info("Method :viewSalesPriceData starts");
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
			resp = restTemplate.getForObject(
					env.getSalesUrl() + "get-grn-data?org=" + orgName + "&orgDiv=" + orgDivision + "&pageno=" + pageno,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSalesPriceData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-sales-price-data-item-wise")
	public @ResponseBody Object viewSalesPriceDataItemWise(@RequestParam String pageno, HttpSession session) {
		logger.info("Method :viewSalesPriceDataItemWise starts");

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
			resp = restTemplate.getForObject(env.getSalesUrl() + "get-sales-price-data?org=" + orgName + "&orgDiv="
					+ orgDivision + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewSalesPriceDataItemWise ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-sales-price-product-details")
	public @ResponseBody Object getProductDetais(HttpSession session, @RequestParam String skuId) {
		logger.info("Method : getProductDetais starts");

		JsonResponse<Object> resp = new JsonResponse<>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}

		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-get-product-details?orgName=" + organization
					+ "&orgDiv=" + orgDivision + "&skuId=" + skuId, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getProductDetais: ", e);
		}

		logger.info("Method : getProductDetais ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-sales-price-get-subcategory" })
	public @ResponseBody JsonResponse<Object> subcategory(String id, HttpSession session) {
		logger.info("Method : subcategory starts");
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
		logger.info("Method : orgName starts" + orgName);
		logger.info("Method : orgDivision starts" + orgDivision);
		try {

			resp = restTemplate.getForObject(
					env.getMasterUrl() + "subcategory?org=" + orgName + "&orgDiv=" + orgDivision + "&id=" + id,
					JsonResponse.class);
			// res = restTemplate.getForObject(env.getPurchaseUrl() +
			// "getBrandList?orgName=" + orgName + "&orgDivision=" +
			// orgDivision,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : subcategory ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-sales-price-get-variation-list" })
	public @ResponseBody JsonResponse<Object> getVariationList(String id, HttpSession session) {
		logger.info("Method : getVariationList starts");
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
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "get-variation-list?org=" + orgName + "&orgDiv=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getVariationList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-sales-price-get-item-list" })
	public @ResponseBody JsonResponse<Object> getItemList(String id, HttpSession session) {
		logger.info("Method : getItemList starts");

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
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "get-item-list?org=" + orgName + "&orgDiv=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getItemList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-sales-price-save-data")
	public @ResponseBody JsonResponse<Object> saveSalesPrice(@RequestBody List<Map<String, Object>> dataset,
			HttpSession session) {
		logger.info("Method : saveSalesPrice starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String organization = (String) session.getAttribute("ORGANIZATION");
		if (organization == null)
			organization = "";

		String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		if (orgDivision == null)
			orgDivision = "";

		String createdById = (String) session.getAttribute("USER_ID");
		if (createdById == null)
			createdById = "";

		try {
			logger.info("Received dataset: " + dataset);

			String url = env.getSalesUrl() + "rest-save-sales-price?organization=" + organization + "&orgDivision="
					+ orgDivision + "&createdById=" + createdById;
			logger.info("URL for Add Sales Price : " + url);

			resp = restTemplate.postForObject(url, dataset, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in saveSalesPrice: ", e);
			resp.setMessage("Error processing request");
			resp.setCode("Error");
		}

		logger.info("Method : saveSalesPrice ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-sales-price-delete-item" })
	public @ResponseBody JsonResponse<Object> deleteSlaesItem(@RequestParam String slNo, HttpSession session) {
		logger.info("Method : deleteSlaesItem starts");
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
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-delete-sales-item?orgName=" + orgName + "&orgDivision=" 
							+ orgDivision + "&slNo=" + slNo, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteSlaesItem ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-sales-price-get-item-bySku")
	public @ResponseBody Object getItemdDtailsBySku(@RequestParam String id, HttpSession session) {
		logger.info("Method :getItemdDtailsBySku starts");
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
			System.out.println(
					env.getSalesUrl() + "get-item-bySkuId?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision);
			resp = restTemplate.getForObject(
					env.getSalesUrl() + "get-item-bySkuId?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getItemdDtailsBySku ends");
		return resp;
	}

}
