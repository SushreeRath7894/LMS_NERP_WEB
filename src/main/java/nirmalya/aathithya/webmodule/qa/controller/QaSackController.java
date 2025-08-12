package nirmalya.aathithya.webmodule.qa.controller;

import javax.servlet.http.HttpSession;
import java.util.List;
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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;
import nirmalya.aathithya.webmodule.qa.model.QaSackModel;


@Controller

@RequestMapping(value = { "production/" })

public class QaSackController {
	
	Logger logger = LoggerFactory.getLogger(QaPcroController.class);

		@Autowired
		RestTemplate restTemplate;

		@Autowired
		EnvironmentVaribles env;

		@Autowired
		FileUpload fileUpload;

		@Autowired
		PdfGeneratatorUtil pdfGeneratorUtil;

		@GetMapping(value = { "sack" })

		public String qaRequest(Model model, HttpSession session) {
			String userId = "";
			String org = "";
			String orgDiv = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			
			logger.info("Method :qaRequest starts");

			logger.info("Method : qaRequest ends");

			return "qa/sack";
		}
		
		// getTotalSlno

		@SuppressWarnings("unchecked")

		@GetMapping("sack-shift-slno")
		public @ResponseBody Object getTotalSlno(HttpSession session) {
			logger.info("Method :getTotalSlno starts");
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
						env.getProduction() + "rest-sack-shift-slno?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
			logger.info("Method :getTotalSlno ends");
			return resp;
		}
		
	// add 	
		@SuppressWarnings({ "unchecked" })

		@PostMapping(value = { "sack-details-add" })
		public @ResponseBody JsonResponse<Object> addSack(@RequestBody List<QaSackModel> av, HttpSession session) {
			logger.info("Method : addSack function starts"+av);
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
			
			for (QaSackModel m : av) {
				m.setCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
				
			}
			System.out.println("qc= ==" + av);
			try {
				resp = restTemplate.postForObject(env.getProduction() + "rest-sack-details-add", av, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			
			logger.info("Method : addSack function Ends");
			System.out.println("Final Save>>>------" + resp);
			return resp;
		}
		
		@SuppressWarnings("unchecked")

		@GetMapping("sack-details-view")
		public @ResponseBody Object viewSack(HttpSession session) {
			logger.info("Method :viewSack starts");
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
						env.getProduction() + "rest-sack -view?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
			logger.info("Method :viewSack ends");
			return resp;
		}
		
		//edit
		
		@SuppressWarnings("unchecked")
		@GetMapping("sack-reqst-edit")
		public @ResponseBody Object editSack(@RequestParam String id, HttpSession session) {
			logger.info("Method :editSack starts");
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

				resp = restTemplate.getForObject(env.getProduction() + "rest-sack-reqst-editview?id=" + id + "&orgName="
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
			logger.info("Method :editSack ends");
			return resp;
		}
		
		// Delete

		@SuppressWarnings("unchecked")
		@PostMapping("sack-detls-delete")
		public @ResponseBody JsonResponse<Object> deleteSack(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : deleteSack function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(env.getProduction() + "rest-sack-detls-delete?id=" + id + "&org=" + orgName
						+ "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteSack function Ends");

			System.out.println("RESPPPPPPP" + res);
			return res;
		}
		
		
		
		@SuppressWarnings("unchecked")
		@PostMapping("sack-detls-approve")
		public @ResponseBody JsonResponse<Object> approveSack(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : approveSack function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(env.getProduction() + "rest-sack-detls-approve?id=" + id + "&org=" + orgName
						+ "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : approveSack function Ends");

			System.out.println("RESPPPPPPP" + res);
			return res;
		}


}
