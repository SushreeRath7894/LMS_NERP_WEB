package nirmalya.aathithya.webmodule.property.tenant.controller;

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

@Controller
@RequestMapping(value = "property")
public class TenantNoticeController {
	
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(TenantNoticeController.class);
	
	@GetMapping(value = { "notice-reminder" })
	public String noticeController(Model model, HttpSession session) {
		logger.info("Method :noticeController starts");

		logger.info("Method :noticeController ends");
		return "new-property/tenant-notice";
	}

}
