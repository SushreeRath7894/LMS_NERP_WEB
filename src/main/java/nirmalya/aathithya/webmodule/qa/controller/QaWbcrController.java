package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Base64;
import java.io.FileInputStream;
import java.io.InputStream;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.QaWbcrModel;
import nirmalya.aathithya.webmodule.qa.model.QaWcrModel;

@Controller

@RequestMapping(value = { "production/" })
public class QaWbcrController {
	
	Logger logger = LoggerFactory.getLogger(QaWbcrController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired FileUpload fileUpload;

	@Autowired PdfGeneratatorUtil pdfGeneratorUtil;
	 
	
	
	@GetMapping(value = { "wbcr" })
	public String qawbcr(Model model, HttpSession session) {
		logger.info("Method :qawbcr starts");
		
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
		
		logger.info("Method : qawbcr ends");

		return "qa/wbcr";
}
	
	
	@SuppressWarnings("unchecked")

	@GetMapping("wbcr-aggrid-view")
	public @ResponseBody Object getPolylinerView(HttpSession session) {
		logger.info("Method :getPolylinerView starts");
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
					env.getProduction() + "rest-getWbcrAgGridView?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getPolylinerView ends");
		return resp;
	}
	

	// add.

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "wbcr-add" })
	public @ResponseBody JsonResponse<Object> addWbcr(@RequestBody QaWbcrModel av,
			HttpSession session) {
		logger.info("Method : addWbcr function starts");
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
		av.setCreatedBy(userId);
		av.setOrganization(organization);
		av.setOrgDivision(orgDivision);

		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-addWbcr", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addWbcr function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	
	// View
	
	@SuppressWarnings("unchecked")

	@GetMapping("wbcr-view")
	public @ResponseBody Object getWbcrView(HttpSession session) {
		logger.info("Method :getWbcrView starts");
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
					env.getProduction() + "rest-getWbcrView?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getWbcrView ends");
		return resp;
	}
	
	// Edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("wbcr-edit")
	public @ResponseBody Object editWbcr(@RequestParam String id, HttpSession session) {
		logger.info("Method :editWbcr starts");
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

			resp = restTemplate.getForObject(env.getProduction()  + "rest-editWbcr?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :editWbcr ends");
		return resp;
	}
	
	
	// Delete.
		@SuppressWarnings("unchecked")
		@GetMapping("wbcr-delete")
		public @ResponseBody Object deleteWbcr(@RequestParam String id, HttpSession session) {
			logger.info("Method :deleteWbcr starts");
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

				resp = restTemplate.getForObject(env.getProduction()  + "rest-deleteWbcr?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :deleteWbcr ends");
			return resp;
		}
		
		// Approve.
		@SuppressWarnings("unchecked")
		@GetMapping("wbcr-approve")
		public @ResponseBody Object approveWbcr(@RequestParam String id, HttpSession session) {
			logger.info("Method :approveWbcr starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {

				resp = restTemplate.getForObject(env.getProduction()  + "rest-approveWbcr?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision + "&approvedBy=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :approveWbcr ends");
			return resp;
		}
		
		
		
		//Download WBCR Pdf
		 @SuppressWarnings("unchecked")
		 @GetMapping("view-wbcrPdf-downloads")
			public void downloadWbcrPdf(HttpServletResponse response, Model model, HttpSession session,
					@RequestParam("wbcrId") String encodedParam1) {
				logger.info("Method : downloadWbcrPdf starts");
				String orgName = "";
				String orgDivision = "";
				try {
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
				String wbcrId = (new String(encodeByte1));

				JsonResponse<Object> resp = new JsonResponse<Object>();
				try {
					resp = restTemplate.getForObject(env.getProduction() + "rest-downloadWbcrPdf?id=" + wbcrId + "&orgName="
							+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}
				List<QaWbcrModel> listModel = new ArrayList<QaWbcrModel>();
				ObjectMapper mapper = new ObjectMapper();
				try {
					listModel = mapper.readValue(resp.getBody().toString(), List.class);
				} catch (JsonParseException e2) {
					e2.printStackTrace();
				} catch (JsonMappingException e2) {
					e2.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("wcr", listModel);
		 
				String logo = "classpath:static/assets/images/invoice-banner.jpg";
				data.put("logo", logo);
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline; filename=warehouseCheckingRegister.pdf");
				File file;
				byte[] fileData = null;
				try {
					file = pdfGeneratorUtil.createPdf("qa/wbcrPdf.html", data);
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
				logger.info("Method : getWCRPdfDetails ends");
			}

}
