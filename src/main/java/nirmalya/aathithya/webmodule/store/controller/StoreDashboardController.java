package nirmalya.aathithya.webmodule.store.controller;

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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;

@Controller
@RequestMapping(value = { "store/" })
public class StoreDashboardController {
Logger logger = LoggerFactory.getLogger(StoreDashboardController.class);
	
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	StoreDashboardController storeDashboardController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/view-dashboard" })
	public String manageWareHouse(Model model, HttpSession session) {
		logger.info("Method : manageStore starts");

			logger.info("Method : manageStore ends");
		return "store/view-dashboard";
	}
}
