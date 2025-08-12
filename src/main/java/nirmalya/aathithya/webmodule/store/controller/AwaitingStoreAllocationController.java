package nirmalya.aathithya.webmodule.store.controller;

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
import nirmalya.aathithya.webmodule.warehouse.controller.AwaitingWarehouseAllocationController;

@Controller

@RequestMapping(value = { "store/" })
public class AwaitingStoreAllocationController {
	Logger logger = LoggerFactory.getLogger(AwaitingWarehouseAllocationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "awaiting-store-allocation" })
	public String awaitingStockAllocationPage(Model model, HttpSession session) {
		logger.info("Method :awaitingStockAllocationPage starts");


		logger.info("Method : awaitingStockAllocationPage ends");
		return "store/awaiting-store-allocation";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("awaiting-store-allocation-view")
	public @ResponseBody Object awaitingStockAllocationView(HttpSession session) {
		logger.info("Method :awaitingStockAllocationView starts");
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
					env.getMasterUrl() + "view-awaitingstock-allocation?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :awaitingStockAllocationView ends");
		return resp;
	}
	
}
