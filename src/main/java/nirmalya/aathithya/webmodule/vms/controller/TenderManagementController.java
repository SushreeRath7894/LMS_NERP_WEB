package nirmalya.aathithya.webmodule.vms.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import nirmalya.aathithya.webmodule.account.model.ContraVoucherModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.user.model.UserRoleAssignModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "/purchase")
public class TenderManagementController {

	Logger logger = LoggerFactory.getLogger(TenderManagementController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("tender-management")
	public String tenderMangement(Model model, HttpSession session) {
		logger.info("Method : Tendor Mangement start");

		logger.info("Method : Tendor Mangement ends");
		return "vms-templates/tender-management";
	}
	
	@GetMapping("self-tender")
	public String selfTenderMangement(Model model, HttpSession session) {
		logger.info("Method : Tendor Mangement start");

		logger.info("Method : Tendor Mangement ends");
		return "vendor/tender";
	}
	
	@GetMapping("self-contract")
	public String selfContractMangement(Model model, HttpSession session) {
		logger.info("Method : Contract Mangement start");

		logger.info("Method : Contract Mangement ends");
		return "vms-templates/vendors-contracts";
	}

	@GetMapping("contract-management")
	public String contractMangement(Model model, HttpSession session) {
		logger.info("Method : Tendor Mangement start");

		logger.info("Method : Tendor Mangement ends");
		return "vms-templates/contract-management";
	}

	@GetMapping("vendor-evaluation")
	public String vendorEvaluation(Model model, HttpSession session) {
		logger.info("Method : Vendor Evaluation start");

		logger.info("Method : Vendor Evaluation ends");
		return "vms-templates/vendor-evaluation";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("get-users-details")
	public @ResponseBody List<UserRoleAssignModel> getEmployeeMasterDetails(HttpSession session) {
		logger.info("Method : getEmployeeMasterDetails starts");

		JsonResponse<List<UserRoleAssignModel>> resp = new JsonResponse<List<UserRoleAssignModel>>();
		List<UserRoleAssignModel> returnList = new ArrayList<UserRoleAssignModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restClient.getForObject(env.getUserUrl() + "viewEmployeeMasterView?org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getEmployeeMasterDetails ends");
		return returnList;
	}

	@PostMapping(value = "get-pdf-data/{pathValue}")
	@ResponseBody
	public ResponseEntity<byte[]> getPdf(@PathVariable("pathValue") String pathValue, @RequestParam String tenderId,
			@RequestBody Map<String, Object> pdfContent, HttpSession session) {
		logger.info("Method : getPdf starts", pdfContent);

		try {
			String userId = (String) session.getAttribute("USER_ID");

			String data = pdfContent.get("pdfContent").toString();

			RestTemplate restTemplate = new RestTemplate();
			String responseData = restTemplate.postForObject("https://service-node.nerp.in/api/v1/pdf/getpdf", data,
					String.class);

			JSONObject resp = new JSONObject(responseData);
			// Decode the base64 data to byte array.
			if (pathValue.equals("publishPdf")) {
				savePdfToFolder(resp.getString("data"), tenderId, userId);
				return new ResponseEntity<>(HttpStatus.CREATED);

			} else if (pathValue.equals("previewPdf")) {
				byte[] pdfBytes = Base64.getDecoder().decode(resp.getString("data"));
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDispositionFormData("attachment", "sample-pdf.pdf");
				return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
			} else if (pathValue.equals("downloadPdf")) {
				byte[] pdfBytes = Base64.getDecoder().decode(resp.getString("data"));
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDispositionFormData("attachment", "sample-pdf.pdf");
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

	private void savePdfToFolder(String base64Pdf, String tenderId, String userId) throws IOException {
		byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);

		String uniqueId = UUID.randomUUID().toString();
		String filePath = env.getFileUploadOmcUrl() + "tender-pdf-" + uniqueId + ".pdf";

		String filePathPdf = env.getBaseURL() + "document/image/omc/" + "tender-pdf-" + uniqueId + ".pdf";
		saveTendersPdf(filePathPdf, tenderId, userId);

		Path path = Paths.get(filePath);
		Files.write(path, pdfBytes);

		logger.info("PDF saved successfully to: {}" + filePath);
	}

	/*
	 * Save Tender Publish Pdf
	 * 
	 */

	@SuppressWarnings("unchecked")
	public @ResponseBody JsonResponse<Object> saveTendersPdf(String filePathPdf, String tenderId, String userId) {
		logger.info("Method : save-tender-pdf starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "save-tender-pdf?filePath=" + filePathPdf
					+ "&tenderId=" + tenderId + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : save-tender-pdf ends");
		return resp;

	}

	/*
	 * Add Tenders Details
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("save-tender-data")
	public @ResponseBody JsonResponse<Object> saveTender(HttpSession session, @RequestBody Map<String, Object> data) {
		logger.info("Method : saveTender starts");
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
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "save-tender-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTender ends");
		return resp;
	}

	/*
	 * Get All Tenders Data
	 * 
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("tender-data")
	public @ResponseBody JsonResponse<Object> getTendersData(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate) {
		logger.info("Method : getTendersData starts");
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
							env.getPurchaseUrl() + "get-tender-details?org=" + orgName + "&orgDiv=" + orgDivision
									+ "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getTendersData ends");
		return resp;
	}

	/*
	 * Update Tenders Data
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("update-tender-data")
	public @ResponseBody JsonResponse<Object> updateTenderData(HttpSession session,
			@RequestBody Map<String, Object> payload) {
		logger.info("Method : updateTenderData starts");
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
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "update-tender-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, updatedSections, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : updateTenderData ends");
		return resp;
	}

	/*
	 * Update Criteria Tenders Data
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("update-criteria-tender-data")
	public @ResponseBody JsonResponse<Object> updateCriteriaTenderData(HttpSession session,
			@RequestBody Map<String, Object> payload) {
		logger.info("Method : updateCriteriaTenderData starts");
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
			List<Map<String, Object>> criteriaSectionData = (List<Map<String, Object>>) payload.get("criteriaData");
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "update-criteria-tender-details?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, criteriaSectionData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : updateCriteriaTenderData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("tender-all-data/{status}")
	public @ResponseBody JsonResponse<Object> getAllTendersData(@PathVariable("status") String status,
			HttpSession session) {
		logger.info("Method : getAllTendersData For Vendor starts");
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
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "get-AlltenderData?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&status=" + status, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllTendersData For Vendor ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("tender-applied-vendor-data")
	public @ResponseBody JsonResponse<Object> getAllAppliedVendorData(@RequestParam String tenderId,
			@RequestParam String status, HttpSession session) {
		logger.info("Method : get-All-Applied-VendorData For Vendor starts");
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
							env.getPurchaseUrl() + "get-All-AppliedVendorData?org=" + orgName + "&orgDiv=" + orgDivision
									+ "&userId=" + userId + "&tenderId=" + tenderId + "&status=" + status,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : get-All-Applied-VendorData For Vendor ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("assign-vendor")
	public @ResponseBody JsonResponse<Object> assignVendorData(HttpSession session,
			@RequestBody Map<String, Object> payload) {
		logger.info("Method : assignVendorData starts");
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
			List<Map<String, Object>> selectedData = (List<Map<String, Object>>) payload.get("selectedData");
			String vendorRemarks = (String) payload.get("vendorRemarks");

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "assign-vendor-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision + "&vendorRemarks=" + vendorRemarks + "&cancelledVendors="
					+ "No Unselected Vendors", selectedData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : assignVendorData ends");
		return resp;
	}

	@PostMapping("tender-management-contentpdf")
	public ResponseEntity<byte[]> generatePdf(@RequestBody String htmlContent,
			@RequestParam("pathvalue") String pathValue, @RequestParam("tenderid") String tenderId,
			HttpSession session) {
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
			byte[] pdfBytes = outputStream.toByteArray();
			String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);
			logger.info("base64Pdf is -- >" + base64Pdf);

			if (pathValue.equals("publishPdf")) {
				savePdfToFolder(base64Pdf, tenderId, userId);
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
//Search Box Api For Assign User
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "tender-management-assignuserlist" })
	public @ResponseBody JsonResponse<Object> getAssignUserList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getAssignUserList starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getPurchaseUrl() + "/getAssignUserList?id=" + searchValue
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAssignUserList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "tender-management-evalutionuseradd" })
	public @ResponseBody JsonResponse<Object> evalutionUserAdd(HttpSession session,
			@RequestBody List<Map<String, Object>> compliance,@RequestParam String id) {
		logger.info("Method : evalutionUserAdd starts");

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
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-evalutionUserAdd?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision + "&id=" + id, compliance, JsonResponse.class);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : evalutionUserAdd ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "vendor-evaluation-details-add" })
	public @ResponseBody JsonResponse<Object> assignUserEvalutionDetailsAdd(HttpSession session,
			@RequestBody List<Map<String, Object>> evalutionDetails,@RequestParam String id) {
		logger.info("Method : assignUserEvalutionDetailsAdd starts");

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
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-assignUserEvalutionDetailsAdd?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision + "&id=" + id, evalutionDetails, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : assignUserEvalutionDetailsAdd ends");
		return resp;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("vendor-evaluation-fetchvendorlist")
	public @ResponseBody JsonResponse<Object> fetchAppliedTenderVendors(@RequestParam String tenderId,HttpSession session) {
		logger.info("Method : fetchAppliedTenderVendors For Vendor starts");
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
							env.getPurchaseUrl() + "fetchAppliedTenderVendors?org=" + orgName + "&orgDiv=" + orgDivision
									+ "&userId=" + userId + "&tenderId=" + tenderId,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : fetchAppliedTenderVendors For Vendor ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("vendor-evaluation-vendorevalutiondetails")
	public @ResponseBody JsonResponse<Object> vendorevalutiondetails(@RequestParam String tenderId,@RequestParam String vendorId,HttpSession session) {
		logger.info("Method : vendorevalutiondetails For Vendor starts");
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
							env.getPurchaseUrl() + "vendorevalutiondetails?org=" + orgName + "&orgDiv=" + orgDivision
									+ "&userId=" + userId + "&tenderId=" + tenderId + "&vendorId="+vendorId,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : vendorevalutiondetails For Vendor ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "vendor-evaluation-vendortrackingstatus" })
	public @ResponseBody JsonResponse<Object> vendortrackingstatus(HttpSession session,
			@RequestBody  String compliance) {
		logger.info("Method : vendortrackingstatus starts" + compliance);

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
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-vendortrackingstatus?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, compliance, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : vendortrackingstatus ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "vendor-evaluation-vendorAllocationDetails" })
	public @ResponseBody JsonResponse<Object> vendorAllocationDetails(HttpSession session,
			@RequestBody  String data) {
		logger.info("Method : vendorAllocationDetails starts");

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
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-vendorAllocationDetails?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : vendorAllocationDetails ends");
		return resp;
	}
	

}
