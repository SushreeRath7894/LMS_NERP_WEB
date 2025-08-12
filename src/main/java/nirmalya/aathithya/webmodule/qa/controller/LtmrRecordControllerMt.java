package nirmalya.aathithya.webmodule.qa.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller

@RequestMapping(value = { "qa/" })
public class LtmrRecordControllerMt {
	
	Logger logger = LoggerFactory.getLogger(LtmrRecordController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@Autowired
	CommonUtil commonUtil;

	@GetMapping(value = { "ltmr-record-mt" })

	public String ltmrReport(Model model, HttpSession session) {
		logger.info("Method :ltmrReport starts");

		
		logger.info("Method : ltmrReport ends");

		return "qa/ltmr-record-mt";
	}

}
