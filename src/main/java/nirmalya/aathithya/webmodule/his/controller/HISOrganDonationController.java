package nirmalya.aathithya.webmodule.his.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "his")
public class HISOrganDonationController {
	Logger logger = LoggerFactory.getLogger(HISOrganDonationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("organ-donation")
	public String getorgandonation(Model model, HttpSession session) {

		logger.info("Method : getorgandonation starts");

		
		logger.info("Method : getorgandonation ends");

		return "his/organdonation.html";

	}	
	
	
	@GetMapping("patient-organ-donation-card")
	public String organCard(HttpSession session, Model model, @RequestParam String id) {

		logger.info("Method : organCard starts");
		String userId = "";
		byte[] encodeByte1 = Base64.getDecoder().decode(id.getBytes());
		userId = (new String(encodeByte1));
		/*
		 * try { List<String> staticOrganList = Arrays.asList("Heart", "Liver",
		 * "Kidney", "Lungs"); model.addAttribute("totalorganList", staticOrganList); }
		 * catch (Exception e) { e.printStackTrace(); }
		 */
		
		
		try {
		    // Mock data for tissueList with both key and name
		    List<DropDownModel> organList = Arrays.asList(
		    		new DropDownModel("1", "Heart"),
			        new DropDownModel("2", "Liver"),
			        new DropDownModel("3", "Kidney")
		    );
		    
		    model.addAttribute("organList", organList);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		
		try {
		    // Mock data for tissueList with both key and name
		    List<DropDownModel> tissueList = Arrays.asList(
		    		 new DropDownModel("1", "BLOOD VESSELS"),
				        new DropDownModel("2", "BONES"),
				        new DropDownModel("3", "SKIN")
		    );
		    
		    model.addAttribute("tissueList", tissueList);
		} catch (Exception e) {
		    e.printStackTrace();
		}

		logger.info("Method : organCard ends");

		return "his/organ-donation-card.html";

	}	

}
