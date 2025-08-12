package nirmalya.aathithya.webmodule.his.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.his.model.HISBedMasterModel;
import nirmalya.aathithya.webmodule.his.model.HISPrescriptionWebModel;

@Controller
@RequestMapping(value = "his")
public class HISPrescriptionWebController {

	Logger logger = LoggerFactory.getLogger(HISPrescriptionWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/my-appointment" })
	public String viewPrescription() {
		logger.info("Method : viewPrescription starts");
		logger.info("Method : viewPrescription ends");
		return "his/his-opd-prescription";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-prescription")
	public @ResponseBody Object viewPrescription(HttpSession session, @RequestParam String fromdate,
			@RequestParam String todate) {
		logger.info("Method :viewPrescription starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.print(resp + "controller");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewPrescription?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewPrescription ends" + resp);
		return resp;
	}

	@GetMapping(value = { "his-opd-prescription-edit" })
	public @ResponseBody List<HISPrescriptionWebModel> viewPrescriptionEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewPrescriptionEdit starts");
		System.out.println("===========>>>>>>" + id);
		List<HISPrescriptionWebModel> productList = new ArrayList<HISPrescriptionWebModel>();
		
		if (id != null && id != "") {
			try {
				HISPrescriptionWebModel[] prescriptionModel = restTemplate.getForObject(
						env.getHisUrl() + "viewPrescriptionEdit?id=" + id, HISPrescriptionWebModel[].class);

				productList = Arrays.asList(prescriptionModel);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewPrescriptionEdit ends");
		System.out.println("edit@@@@@@@@" + productList);
		return productList;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/prescription-pdfData" })
	public void pdfData(HttpServletResponse response, Model model, @RequestParam String appointmentId) {
		logger.info("Method :pdfData starts");

		HISPrescriptionWebModel product = new HISPrescriptionWebModel();
		JsonResponse<HISPrescriptionWebModel> jsonResponse = new JsonResponse<HISPrescriptionWebModel>();

		JsonResponse<List<HISPrescriptionWebModel>> report = new JsonResponse<List<HISPrescriptionWebModel>>();

		try {
			report = restTemplate.getForObject(env.getHisUrl() + "prescription-pdfData?appointmentId=" + appointmentId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("responseReport=>>>>>>" + report);
		if (report.getBody().isEmpty()) {

			ObjectMapper mapper = new ObjectMapper();

			List<HISPrescriptionWebModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<HISPrescriptionWebModel>>() {
					});

			jsonResponse.setBody(product);

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("prescriptionCard", reportcard);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=prescription-stmt");

		} else {
			ObjectMapper mapper = new ObjectMapper();

			List<HISPrescriptionWebModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<HISPrescriptionWebModel>>() {
					});

			jsonResponse.setBody(product);
			
			System.out.println("responseReport=>>>>>>" + report);
			
			
			for(int i=0;i<reportcard.size();i++) {
				System.out.println("==="+ reportcard.get(i).getPrescriptionMedDay());
				
				if(reportcard.get(i).getPrescriptionMedDay()=="true") {
					reportcard.get(i).setPrescriptionMedDay("classpath:static/extend/img/his-imgs/tick.png");
				}else {
					reportcard.get(i).setPrescriptionMedDay("classpath:static/extend/img/his-imgs/cross.png");
				}
				
				if(reportcard.get(i).getPrescriptionMedNoon()=="true") {
					reportcard.get(i).setPrescriptionMedNoon("classpath:static/extend/img/his-imgs/tick.png");
				}else {
					reportcard.get(i).setPrescriptionMedNoon("classpath:static/extend/img/his-imgs/cross.png");
				}
				
				if(reportcard.get(i).getPrescriptionMedNight()=="true") {
					reportcard.get(i).setPrescriptionMedNight("classpath:static/extend/img/his-imgs/tick.png");
				}else {
					reportcard.get(i).setPrescriptionMedNight("classpath:static/extend/img/his-imgs/cross.png");
				}
				
				
			}
			
			System.out.println("reportcard=>>>>>>" + reportcard);
			

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("prescriptionCard", reportcard);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=prescription");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("his/prescription-pdf", data);
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
}