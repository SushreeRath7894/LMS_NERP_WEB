package nirmalya.aathithya.webmodule.contractlifecycle.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.html2pdf.HtmlConverter;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = { "purchase/" })
public class ContractLifecycleController {

	Logger logger = LoggerFactory.getLogger(ContractLifecycleController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "contract-creation" })
	public String contactCreation(Model model, HttpSession session) {
		logger.info("Method :contactCreation starts");

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			DropDownModel[] tenderLists = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-getTenderList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> tenderList = Arrays.asList(tenderLists);
			model.addAttribute("tenderList", tenderList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : contactCreation ends");

		return "contract-lifecycle/contract-creation";
	}

	@GetMapping(value = { "workflow-approval" })
	public String workflowApproval(Model model, HttpSession session) {
		logger.info("Method :workflowApproval starts");

		logger.info("Method : workflowApproval ends");

		return "contract-lifecycle/workflow-approval";
	}

	@GetMapping(value = { "contract-repository" })
	public String contractRepository(Model model, HttpSession session) {
		logger.info("Method :contractRepository starts");

		logger.info("Method : contractRepository ends");

		return "contract-lifecycle/contract-repository";
	}

	@GetMapping(value = { "contract-negotiation" })
	public String contractNegotiation(Model model, HttpSession session) {
		logger.info("Method :contractNegotiation starts");

		logger.info("Method : contractNegotiation ends");

		return "contract-lifecycle/contract-negotiation";
	}

	@GetMapping(value = { "compliance-risk" })
	public String complianceRisk(Model model, HttpSession session) {
		logger.info("Method :complianceRisk starts");

		logger.info("Method : complianceRisk ends");

		return "contract-lifecycle/compliance-risk";
	}

	@GetMapping(value = { "contract-lifecycle-operations" })
	public String contractClosureTermination(Model model, HttpSession session) {
		logger.info("Method :contractNegotiation starts");

		logger.info("Method : contractNegotiation ends");

		return "contract-lifecycle/contract-lifecycle-operations";
	}

	// save-contract-data

	@SuppressWarnings("unchecked")
	@PostMapping("save-contract-data")
	public @ResponseBody JsonResponse<Object> saveContract(HttpSession session, @RequestBody Map<String, Object> data) {
		logger.info("Method : saveContract starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "save-contract-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveContract ends");
		return resp;
	}

	/*
	 * Get All Contract Data
	 * 
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-contract-data")
	public @ResponseBody JsonResponse<Object> viewContract(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate) {
		logger.info("Method : viewContract starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "get-Contract-details?org=" + orgName + "&orgDiv=" + orgDivision
									+ "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewContract ends");
		return resp;
	}

	// update-contract-data

	@SuppressWarnings("unchecked")
	@PostMapping("update-contract-data")
	public @ResponseBody JsonResponse<Object> updateContractData(HttpSession session,
			@RequestBody Map<String, Object> payload) {
		logger.info("Method : updateContractData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			List<Map<String, Object>> updatedSections = (List<Map<String, Object>>) payload.get("sections");
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "update-contract-details?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, updatedSections, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : updateContractData ends");
		return resp;
	}

	@PostMapping(value = "get-contract-data/{pathValue}")
	@ResponseBody
	public ResponseEntity<byte[]> getPdf(@PathVariable("pathValue") String pathValue, @RequestParam String contractIds,
			@RequestBody Map<String, Object> pdfContent, HttpSession session, @RequestParam String vendor) {
		logger.info("Method : getPdf starts", pdfContent);
		logger.info("Vendor is coming :{}", vendor);

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String data = pdfContent.get("pdfContent").toString();

			RestTemplate restTemplate = new RestTemplate();
			String responseData = restTemplate.postForObject("https://service-node.nerp.in/api/v1/pdf/getpdf", data,
					String.class);

			JSONObject resp = new JSONObject(responseData);
			// Decode the base64 data to byte array.
			if (pathValue.equals("publishPdf")) {

				savePdfToFolder(resp.getString("data"), contractIds, userId, org, orgDiv, vendor);
				return new ResponseEntity<>(HttpStatus.CREATED);

			} else if (pathValue.equals("previewPdf")) {

				byte[] pdfBytes = Base64.getDecoder().decode(resp.getString("data"));
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDispositionFormData("attachment", "contract-pdf.pdf");
				return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
			} else if (pathValue.equals("downloadPdf")) {

				byte[] pdfBytes = Base64.getDecoder().decode(resp.getString("data"));
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDispositionFormData("attachment", "contract-pdf.pdf");
				return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info("Method : getPdf ends");
		}
	}

	private void savePdfToFolder(String base64Pdf, String contractIds, String userId, String org, String orgDiv,
			String vendor) throws IOException {
		byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);

		String uniqueId = UUID.randomUUID().toString();
		String filePath = env.getFileUploadOmcUrl() + "contract-pdf-" + uniqueId + ".pdf";

		String filePathPdf = env.getBaseURL() + "document/image/omc/" + "contract-pdf-" + uniqueId + ".pdf";

		saveContractsPdf(filePathPdf, contractIds, userId, org, orgDiv, vendor);

		Path path = Paths.get(filePath);
		Files.write(path, pdfBytes);

		logger.info("PDF saved successfully to: {}" + filePath);
	}

	/*
	 * Save Contract Publish Pdf
	 * 
	 */

	@SuppressWarnings("unchecked")
	public @ResponseBody JsonResponse<Object> saveContractsPdf(String filePathPdf, String contractIds, String userId,
			String org, String orgDiv, String vendor) {
		logger.info("Method : save-contract-pdf starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "save-contract-pdf?filePath=" + filePathPdf + "&contractIds=" + contractIds
							+ "&userId=" + userId + "&org=" + org + "&orgDiv=" + orgDiv + "&vendor=" + vendor,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : save-contract-pdf ends");
		return resp;

	}

	// get-omc-vendorList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "get-omc-vendorList" })
	public @ResponseBody JsonResponse<Object> getVendorList(@RequestParam String id) {
		logger.info("Method : getVendorList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.getMessage();
		res.getCode();
		logger.info("Method : getVendorList ends" + res);
		return res;
	}

	// manage-contract-get-contact

	@SuppressWarnings("unchecked")
	@GetMapping("get-omc-user-details")
	public @ResponseBody JsonResponse<Object> geContractDetails(HttpSession session, @RequestParam String contractId,
			@RequestParam String vendorId) {
		logger.info("Method : geContractVersionDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String userId = (String) session.getAttribute("USER_ID");
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "view-contract-details?contractId=" + contractId
					+ "&vendorId=" + vendorId + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : geContractVersionDetails ends");
		return resp;
	}

	@PostMapping("contract-creation-contentpdf")
	public ResponseEntity<byte[]> generatePdf(@RequestBody String htmlContent,
			@RequestParam("pathvalue") String pathValue, @RequestParam("tenderid") String tenderId,
			@RequestParam("vendor") String vendor, HttpSession session) {
		try {
			// Convert HTML to PDF
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			HtmlConverter.convertToPdf(htmlContent, outputStream);

			logger.info("path value-->" + pathValue);
			logger.info("tender is -- >" + tenderId);

			// Set response headers for PDF download
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", "Generated_Document.pdf");
			String userId = (String) session.getAttribute("USER_ID");
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			byte[] pdfBytes = outputStream.toByteArray();
			String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);
			logger.info("base64Pdf is -- >" + base64Pdf);

			if (pathValue.equals("publishPdf")) {
				savePdfToFolder(base64Pdf, tenderId, userId, org, orgDiv, vendor);
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(outputStream.toByteArray());
			} else if (pathValue.equals("previewPdf")) {
				return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
			} else if (pathValue.equals("downloadPdf")) {
				return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
			}

		} catch (Exception e) {

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("workflow-approval-assignemp")
	public @ResponseBody JsonResponse<Object> getAssignApproveUser(HttpSession session) {
		logger.info("Method : getAssignApproveUser starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "get-assign-approval-emp?org=" + orgName + "&orgDiv=" + orgDivision
									+ "&userId=" + userId,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAssignApproveUser ends" + resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("workflow-approval-contact-approve")
	public @ResponseBody JsonResponse<Object> contractApprove(HttpSession session,@RequestParam String id) {
		logger.info("Method : contractApprove starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "workflow-contract-approval?org=" + orgName + "&orgDiv=" + orgDivision
									+ "&userId=" + userId + "&id="+id,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : contractApprove ends" + resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("compliance-risk-contractdetails")
	public @ResponseBody JsonResponse<Object> getApprovedContract(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate) {
		logger.info("Method : viewContract starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "get-approve-Contract-details?org=" + orgName + "&orgDiv=" + orgDivision
									+ "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewContract ends");
		return resp;
	}
	
	
	
	/*
	 * @SuppressWarnings({ "unchecked" })
	 * 
	 * @PostMapping(value = { "compliance-risk-complianceadd" })
	 * public @ResponseBody JsonResponse<Object> complianceadd(
	 * 
	 * @RequestBody List<Map<String, Object>> requestData, HttpSession session) {
	 * 
	 * logger.info("Method : complianceadd function Start- Response: " +
	 * requestData);
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<>(); String userId = "",
	 * organization = "", orgDivision = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID"); organization =
	 * (String) session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * logger.error("Error retrieving session attributes", e); }
	 * 
	 * try { Map<String, Object> requestBody = new HashMap<>();
	 * requestBody.put("userId", userId); requestBody.put("organization",
	 * organization); requestBody.put("orgDivision", orgDivision);
	 * requestBody.put("complianceData", requestData);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * 
	 * HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody,
	 * headers);
	 * 
	 * resp = restTemplate.postForObject( env.getPurchaseUrl() +
	 * "rest-complianceadd", requestEntity, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { logger.error("Error calling REST API", e);
	 * resp.setMessage("Error processing request"); resp.setCode("500"); }
	 * 
	 * logger.info("Method : complianceadd function Ends - Response: " + resp);
	 * return resp; }
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "compliance-risk-complianceadd" })
	public @ResponseBody JsonResponse<Object> complianceadd(HttpSession session,
			@RequestBody List<Map<String, Object>> compliance) {
		logger.info("Method : complianceadd starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-complianceadd?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, compliance, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : complianceadd ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "compliance-risk-riskAssesmentAdd" })
	public @ResponseBody JsonResponse<Object> riskAssesmentAdd(HttpSession session,
			@RequestBody List<Map<String, Object>> compliance) {
		logger.info("Method : riskAssesmentAdd starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-riskAssesmentAdd?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, compliance, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : riskAssesmentAdd ends");
		return resp;
	}



}
