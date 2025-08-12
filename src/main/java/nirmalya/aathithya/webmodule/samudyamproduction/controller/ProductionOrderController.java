package nirmalya.aathithya.webmodule.samudyamproduction.controller;

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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.samudyamproduction.model.ProductionOrderItemModel;
import nirmalya.aathithya.webmodule.samudyamproduction.model.ProductionOrderModel;
@Controller
@RequestMapping(value = "production")
public class ProductionOrderController {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(ProductionOrderController.class);
	
	@Autowired
	FileUpload fileUpload;
	
	//manage production
	@GetMapping(value = { "/production-order" })
	public String pageManageOrder(Model model, HttpSession session) {
		logger.info("Method :pageManageOrder starts");

		logger.info("Method : pageManageOrder ends");
		return "samudyam-production/production-order";
	}
	/*
	 * view Production Order
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("production-order-view")
	public @ResponseBody List<ProductionOrderModel> viewProductionOrder(HttpSession session) {

		logger.info("Method :viewProductionOrder starts");
		JsonResponse<List<ProductionOrderModel>> resp = new JsonResponse<List<ProductionOrderModel>>();
		String org=""; 
		String orgDiv="";
		try {
			org = (String) session.getAttribute("ORGANIZATION"); 
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getProduction() + "getProductionOrder?org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewProductionOrder ends");
		logger.info("RESPONSEview" + resp);
		return resp.getBody();
	}
	/*
	 * view Production Order
	 */
	@GetMapping("production-order-view-items")
	public @ResponseBody List<ProductionOrderModel> viewOrderItemDetails(HttpSession session, @RequestParam String orderId) {
		
		logger.info("Method :viewOrderItemDetails starts");
		List<ProductionOrderModel> orderList = new ArrayList<ProductionOrderModel>();
		String org=""; 
		String orgDiv="";
		try {
			org = (String) session.getAttribute("ORGANIZATION"); 
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ProductionOrderModel[] productionOrderModel = restTemplate.getForObject(
					env.getProduction() + "getOrderItemDetails?orderId="+orderId+"&org="+org+"&orgDiv="+orgDiv, ProductionOrderModel[].class);
			orderList = Arrays.asList(productionOrderModel);
			  for (ProductionOrderModel m : productionOrderModel) {
				  int count = 0; 
				  for (ProductionOrderItemModel a:m.getItemList()) {
					  count++;
					  a.setSlNo(count);
				  }
			  }
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		logger.info("Method :viewOrderItemDetails ends");
		logger.info("orderList" + orderList);
		return orderList;
	}
	
}
