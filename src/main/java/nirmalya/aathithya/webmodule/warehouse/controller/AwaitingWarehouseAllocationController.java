package nirmalya.aathithya.webmodule.warehouse.controller;



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

@Controller

@RequestMapping(value = { "warehouse/" })
public class AwaitingWarehouseAllocationController {
	
	Logger logger = LoggerFactory.getLogger(AwaitingWarehouseAllocationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "awaiting-allocation" })
	public String awaitingWarehouseAllocationPage(Model model, HttpSession session) {
		logger.info("Method :awaitingWarehouseAllocationPage starts");


		logger.info("Method : awaitingWarehouseAllocationPage ends");
		return "warehouse/awaiting-allocation";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("awaiting-allocation-view")
	public @ResponseBody Object awaitingAllocationView(HttpSession session) {
		logger.info("Method :awaitingAllocationView starts");
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
					env.getMasterUrl() + "view-awaiting-allocation?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :awaitingAllocationView ends");
		return resp;
	}

}
