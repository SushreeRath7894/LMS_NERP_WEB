package nirmalya.aathithya.webmodule.warehouse.controller;

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

@Controller
@RequestMapping(value = { "warehouse/" })
public class WarehouseReportController {

	Logger logger = LoggerFactory.getLogger(WarehouseAllocationController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	WarehouseAllocationController warehouseAllocationController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/warehouse-report" })

	public String manageWarehouseReport(Model model, HttpSession session) {
		logger.info("Method : manageWareHouse starts");

		logger.info("Method : manageWareHouse ends");
		return "warehouse/warehouse-report";
	}

	// Warehouse_Report_View.

	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-report-view")
	public @ResponseBody Object viewWarehouseReportData(@RequestParam String state, HttpSession session) {

		logger.info("Method :viewWarehouseReportData starts");
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

			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewWarehouseReportData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&state=" + state, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Warehouse-Report-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewWarehouseReportData ends");

		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("warehouse-report-filter-view")
	public @ResponseBody Object viewWarehouseReportFilterData(@RequestParam String slno , HttpSession session) {

		logger.info("Method :viewWarehouseReportFilterData starts");
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

			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewWarehouseReportFilterData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&slno=" + slno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Warehouse-Report-Filter-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewWarehouseReportFilterData ends");

		return resp;
	}
	

	// Warehouse_Report_View For Item Wise Stock.

	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-report-view-forItemWiseStock")
	public @ResponseBody Object viewWarehouseReportForItemWiseStock(HttpSession session) {

		logger.info("Method :viewWarehouseReportForItemWiseStock starts");
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

			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewWarehouseReportForItemWiseStock?orgName=" + orgName
					+ "&orgDivision=" + orgDivision , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Warehouse-Report-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewWarehouseReportForItemWiseStock ends");

		return resp;
	}
	
	

	// Warehouse_Report_View For Stock Register.

	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-report-view-stockRegister")
	public @ResponseBody Object viewWarehouseReportForStockRegister(HttpSession session) {

		logger.info("Method :viewWarehouseReportForStockRegister starts");
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

			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewWarehouseReportForStockRegister?orgName=" + orgName
					+ "&orgDivision=" + orgDivision , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Warehouse-Report-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewWarehouseReportForStockRegister ends");

		return resp;
	}

}
