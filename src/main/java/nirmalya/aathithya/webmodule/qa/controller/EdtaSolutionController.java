package nirmalya.aathithya.webmodule.qa.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.EdtaSolutionModel;
import nirmalya.aathithya.webmodule.qa.model.QaCrqsModel;
import nirmalya.aathithya.webmodule.qa.model.SulphuricAcidModel;

@Controller

@RequestMapping(value = { "qa/" })
public class EdtaSolutionController {

	Logger logger = LoggerFactory.getLogger(EdtaSolutionController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "edta-calcium" })

	public String edtaCalcium(Model model, HttpSession session) {
		logger.info("Method :edtaCalcium starts");

		logger.info("Method : edtaCalcium ends");

		return "qa/edtasolution";
	}
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "edta-calcium-save" })
	public @ResponseBody JsonResponse<Object> addCalcium(@RequestBody List<EdtaSolutionModel> av, HttpSession session) {
		logger.info("Method : addCalcium function starts");
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
		for (EdtaSolutionModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setDateOfPreparation(DateFormatter.inputDateFormat(m.getDateOfPreparation(), dateFormat));
			m.setDateOfIssue(DateFormatter.inputDateFormat(m.getDateOfIssue(), dateFormat));

		}
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addCalcium", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addCalcium function Ends"+av);
		return resp;
	}
	
	 @SuppressWarnings("unchecked")

	@GetMapping("edta-calcium-view")
	public @ResponseBody Object viewCalcium(HttpSession session) {
		logger.info("Method :viewCalcium starts");
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
					env.getQa() + "rest-viewCalcium?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :viewCalcium ends");
		return resp;
	}
	 
	 @SuppressWarnings("unchecked")
		@GetMapping("edta-calcium-edit")
		public @ResponseBody Object editCalcium(@RequestParam String edtacalciumId, HttpSession session) {
			logger.info("Method :editCalcium starts");
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

				resp = restTemplate.getForObject(env.getQa() + "rest-editCalcium?edtacalciumId=" + edtacalciumId + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :editCalcium ends");
			return resp;
		}
	 
	 
	 @SuppressWarnings("unchecked")
		@PostMapping("edta-calcium-delete")
		public @ResponseBody JsonResponse<Object> deleteCalcium(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : deleteCalcium function starts");

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
				res = restTemplate.getForObject(env.getQa() + "rest-deleteCalcium?id=" + id + "&org=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

		
			logger.info("Method : deleteCalcium function Ends");

			System.out.println("RESPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP" + res);
			return res;
		}
//	 
	 @SuppressWarnings("unchecked")
		@PostMapping("edta-calcium-approve")
		public @ResponseBody JsonResponse<Object> approveCalcium(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : approveCalcium function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			String orgName = "";
			String orgDivision = "";
			String userId = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userId = (String) session.getAttribute("USER_ID");
				System.out.println("USERIDDDDDDDDDDDDDDDDDDDD"+userId);			
				} catch (Exception e) {
				logger.error(e.getMessage());
			}
		
			try {
				res = restTemplate.getForObject(env.getQa() + "rest-approveCalcium?id=" + id + "&org=" + orgName
						+ "&orgDiv=" + orgDivision +"&userId=" + userId ,JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			
			logger.info("Method : approveCalcium function Ends"+userId);

			System.out.println("RESPPPPPPP" + res);
			return res;
		}
//
	 @SuppressWarnings("unchecked")
		@GetMapping("edta-calcium-pdf-downloads")
		public void edtaCalPdf(HttpServletResponse response, Model model, HttpSession session,
		        @RequestParam("id") String encodedParam1) {

		    logger.info("Method : edtaCalPdf starts");
		    String orgName = "";
		    String orgDivision = "";
		    try {
		        orgName = (String) session.getAttribute("ORGANIZATION");
		        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		    } catch (Exception e) {
		        logger.error(e.getMessage());
		    }

		    byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		    String id = new String(encodeByte3);

		    System.out.println("JSON===== === " + id);
		    JsonResponse<Object> resp = new JsonResponse<Object>();

		    try {
		        resp = restTemplate.getForObject(env.getQa() + "rest-edtaCalPdf?id=" + id + "&orgName="
		                + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		        System.out.println("JSON Obj === " + resp);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    logger.info("tefeg" + resp);

		    Map<String, Object> data = new HashMap<String, Object>();

		    System.out.println("Get Data === " + resp.getBody().toString());

		    // Parsing the JSON data
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
		        JsonNode rootNode = objectMapper.readTree(resp.getBody().toString());

		        // Extract sections
		        JsonNode mainSection = rootNode.path("Main");
		        JsonNode firstSection = rootNode.path("First");
		        JsonNode secondSection = rootNode.path("Second");
		        
		        if (mainSection.isArray() && mainSection.size() > 0) {
		            data.put("Main", objectMapper.convertValue(mainSection, new TypeReference<List<Map<String, Object>>>() {}));
		        }
		        
		        if (firstSection.isArray() && firstSection.size() > 0) {
		            data.put("First", objectMapper.convertValue(firstSection, new TypeReference<List<Map<String, Object>>>() {}));
		        }

		        if (secondSection.isArray() && secondSection.size() > 0) {
		            data.put("Second", objectMapper.convertValue(secondSection, new TypeReference<List<Map<String, Object>>>() {}));
		        }

		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
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
		    response.setHeader("Content-disposition", "inline; filename=edtaCalPdf.pdf");
		    File file;
		    byte[] fileData = null;
		    try {
		        file = pdfGeneratorUtil.createPdf("qa/edtaCalPdf.html", data);
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

		    logger.info("Method : edtaCalPdf ends");
		}
}
