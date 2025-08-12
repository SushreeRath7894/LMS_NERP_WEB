package nirmalya.aathithya.webmodule.qa.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.LaminateModel;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;

@Controller
@RequestMapping(value = { "qa/" })
public class LaminateController {
	Logger logger = LoggerFactory.getLogger(LaminateController.class);
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

	@GetMapping(value = { "laminate" })

	public String laminate(Model model, HttpSession session) {
		
		logger.info("Method :laminate starts");

		
		logger.info("Method : laminate ends");

		return "qa/laminate";
	}
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "laminate-details-add" })
	public @ResponseBody JsonResponse<Object> addLaminate(@RequestBody List<LaminateModel> av, HttpSession session) {
		logger.info("Method : addLaminate function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (LaminateModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setPdate(DateFormatter.inputDateFormat(m.getPdate(), dateFormat));
			m.setInvoiceDate(DateFormatter.inputDateFormat(m.getInvoiceDate(), dateFormat));

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-laminate-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addLaminate function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	

	@SuppressWarnings("unchecked")

	@GetMapping("laminate-details-view")
	public @ResponseBody Object getLaminateView(HttpSession session) {
		logger.info("Method :getLaminateView starts");
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
					env.getQa() + "rest-laminate-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		System.out.println("view===" + resp);
		logger.info("Method :getLaminateView ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("laminate-details-edit")
	public @ResponseBody Object editLaminate(@RequestParam String id, HttpSession session) {
		logger.info("Method :editLaminate starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-editLaminate?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :editLaminate ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("laminate-detls-delete")
	public @ResponseBody JsonResponse<Object> deleteLaminate(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteLaminate function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getQa() + "rest-deleteLaminate?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteLaminate function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("laminate-detls-approve")
	public @ResponseBody JsonResponse<Object> approveLaminate(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : approveLaminate function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getQa() + "rest-laminate-detls-approve?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approveLaminate function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}


}
