package nirmalya.aathithya.webmodule.samudyamproduction.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;
import nirmalya.aathithya.webmodule.samudyamproduction.model.ProductionOrderModel;

@Controller
@RequestMapping(value = "production")
public class OrderSchedulingController {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(MouldMasterController.class);
	
	@Autowired
	FileUpload fileUpload;
	
	//manage production
	@GetMapping(value = { "/order-scheduling" })
	public String pageOrderScheduling(Model model, HttpSession session) {
		logger.info("Method :pageOrderScheduling starts");
 
		logger.info("Method : pageOrderScheduling ends");
		return "samudyam-production/order-scheduling";
	}
	/*
	 * view
	 */
	@SuppressWarnings("unchecked")

	@GetMapping("order-scheduling-view")
	public @ResponseBody List<SalesOrderNewModel> viewOrderScheduling(HttpSession session) {

		logger.info("Method :viewOrderScheduling starts");
		JsonResponse<List<SalesOrderNewModel>> resp = new JsonResponse<List<SalesOrderNewModel>>();
		String userId = "";
		String dateFormat = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		
		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userId);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);
		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "getAllsalesOrder",empModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<SalesOrderNewModel> salesOrderNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<SalesOrderNewModel>>() {
				});
	
		for (SalesOrderNewModel a : salesOrderNewModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}
			if (a.getOrderReceiveDate() != null && a.getOrderReceiveDate() != "") {
				a.setOrderReceiveDate(DateFormatter.dateFormat(a.getOrderReceiveDate(), dateFormat));
			}
			  if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
			  a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
			  }
		}

		resp.setBody(salesOrderNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewOrderScheduling ends");
		logger.info("RESPONSEview" + resp);
		return resp.getBody();
	}
	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/order-scheduling-add")
	public @ResponseBody JsonResponse<Object> addOrderSceduling(HttpSession session,
			@RequestBody List<ProductionOrderModel> productionOrderModel ) {
		logger.info("Method : addOrderSceduling starts");
		 
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (ProductionOrderModel m : productionOrderModel) {
			m.setOrderCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			
		}
 
logger.info("productionOrderModel==="+productionOrderModel);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "addOrderSceduling", productionOrderModel,
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
		logger.info("a===" + resp);

		logger.info("Method : addOrderSceduling ends");

		return resp;
	}
}
