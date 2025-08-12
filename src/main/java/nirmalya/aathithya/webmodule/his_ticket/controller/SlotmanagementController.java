package nirmalya.aathithya.webmodule.his_ticket.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.gst.controller.GstSalesReturnController;



@Controller
@RequestMapping(value = "opd/")
public class SlotmanagementController {
	Logger logger = LoggerFactory.getLogger(GstSalesReturnController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("slotmanagement")
	public String slotmanagement(Model model, HttpSession session) {

		logger.info("Method : slotmanagement starts");

		logger.info("Method : slotmanagement ends");

		return "his/opd-management";
	}


}
