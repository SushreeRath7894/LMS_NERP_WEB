package nirmalya.aathithya.webmodule.warehouse.controller;


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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;

@Controller
@RequestMapping(value = { "warehouse/" })
public class WarehouseDashboardController {
Logger logger = LoggerFactory.getLogger(WarehouseDashboardController.class);
	
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	WarehouseAllocationController warehouseAllocationController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/warehouse-dashboard" })
	public String manageWareHouse(Model model, HttpSession session) {
		logger.info("Method : manageWareHouse starts");

		
	
	
		logger.info("Method : manageWareHouse ends");
		return "warehouse/warehouse-dashboard";
	}

}
