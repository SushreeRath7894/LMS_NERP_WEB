package nirmalya.aathithya.webmodule.vendor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "vendor")
public class GRNReceiptController {
	Logger logger = LoggerFactory.getLogger(GRNReceiptController.class);

	@GetMapping("/grn-reciept")
	public String grnReceipt() {
		logger.info("Method : tender start");
		return "vendor/grn-reciept";
	}
}
