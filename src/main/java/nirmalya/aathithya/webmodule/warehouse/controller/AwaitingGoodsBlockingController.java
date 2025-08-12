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
public class AwaitingGoodsBlockingController {
	Logger logger = LoggerFactory.getLogger(AwaitingGoodsBlockingController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("awaiting-blocking")
	public String AwaitingGoodsBlockingPage(Model model, HttpSession session) {

		logger.info("Method : AwaitingGoodsBlockingPage starts");
		
		logger.info("Method : AwaitingGoodsBlockingPage ends");
		return "warehouse/awaiting-blocking";

	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("awaiting-blocking-view")
	public @ResponseBody Object awaitingBlockingView(HttpSession session) {
		logger.info("Method :awaitingBlockingView starts");
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
					env.getMasterUrl() + "view-awaiting-blocking?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :awaitingBlockingView ends");
		return resp;
	}
}
