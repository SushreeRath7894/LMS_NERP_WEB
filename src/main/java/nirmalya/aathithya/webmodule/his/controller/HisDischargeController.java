package nirmalya.aathithya.webmodule.his.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.his.model.HISPrescriptionWebModel;

@Controller
@RequestMapping(value = "his")
public class HisDischargeController {
	Logger logger = LoggerFactory.getLogger(HisDischargeController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("ipd-discharge")
	public String getHomePage(Model model, HttpSession session) {

		logger.info("Method : getHomePage starts");

		
		logger.info("Method : getHomePage ends");

		return "his/ipdDischarge";

	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/discharge-pdfData" })
	public void dischargeData(HttpServletResponse response,HttpSession session, Model model) {
		logger.info("Method :pdfData starts");

		HISPrescriptionWebModel product = new HISPrescriptionWebModel();
		JsonResponse<HISPrescriptionWebModel> jsonResponse = new JsonResponse<HISPrescriptionWebModel>();

		JsonResponse<List<HISPrescriptionWebModel>> report = new JsonResponse<List<HISPrescriptionWebModel>>();

		
			

			Map<String, Object> data = new HashMap<String, Object>();
			  String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			  System.out.println("logo"+logo);
				URL getUrl = null;
				try {
					getUrl = new URL(logo);
				} catch (MalformedURLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
				data.put("logo", "data:image/png;base64," + encodedLogoUrl);
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=prescription");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("his/discharge-pdf", data);
				InputStream in = new FileInputStream(file);
				fileData = IOUtils.toByteArray(in);
				response.setContentLength(fileData.length);
				response.getOutputStream().write(fileData);
				response.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		

	}
}
