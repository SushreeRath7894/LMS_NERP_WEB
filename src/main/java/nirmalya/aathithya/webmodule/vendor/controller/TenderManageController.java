package nirmalya.aathithya.webmodule.vendor.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.io.File;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "vendor")
public class TenderManageController {
	Logger logger = LoggerFactory.getLogger(TenderManageController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/tender")
	public String tender() {
		logger.info("Method : tender start");
		return "vendor/tender";
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

	@PostMapping("/tender-upload-file")
	public ResponseEntity<JsonResponse<Object>> uploadFile(
			@RequestParam("technicalProposalFile") MultipartFile technicalProposalFile,
			@RequestParam("financialProposalFile") MultipartFile financialProposalFile,
			@RequestParam Map<String, MultipartFile> allFiles) { 

		logger.info("Method : logistic uploadFile starts");

		JsonResponse<Object> response = new JsonResponse<>();
		String uploadDir = env.getFileUploadOmcUrl();

		try {
			Path path = Paths.get(uploadDir);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			// Upload Technical Proposal File
			if (technicalProposalFile != null && !technicalProposalFile.isEmpty()) {
				File targetFile = new File(uploadDir + File.separator + technicalProposalFile.getOriginalFilename());
				technicalProposalFile.transferTo(targetFile);
				logger.info("Uploaded technical proposal file: " + technicalProposalFile.getOriginalFilename());
			} else {
				logger.warn("No technical proposal file selected.");
			}

			// Upload Financial Proposal File
			if (financialProposalFile != null && !financialProposalFile.isEmpty()) {
				File targetFile = new File(uploadDir + File.separator + financialProposalFile.getOriginalFilename());
				financialProposalFile.transferTo(targetFile);
				logger.info("Uploaded financial proposal file: " + financialProposalFile.getOriginalFilename());
			} else {
				logger.warn("No financial proposal file selected.");
			}

			// Upload Criteria Files by filtering the keys
			allFiles.forEach((key, file) -> {
				if (key.startsWith("criteriaFiles_") && file != null && !file.isEmpty()) {
					try {
						File targetFile = new File(uploadDir + File.separator + file.getOriginalFilename());
						file.transferTo(targetFile);
						logger.info("Uploaded criteria file: " + file.getOriginalFilename());
					} catch (IOException e) {
						logger.error("Failed to upload criteria file: " + file.getOriginalFilename(), e);
					}
				}
			});

			response.setBody(null);
			response.setMessage("Files uploaded successfully");
			response.setCode("200");

		} catch (IOException e) {
			logger.error("File upload failed due to IOException: " + e.getMessage());
			response.setMessage("Failed to upload files due to server error");
			response.setCode("500");

		} catch (Exception e) {
			logger.error("An unexpected error occurred: " + e.getMessage());
			response.setMessage("An unexpected error occurred while uploading files");
			response.setCode("500");
		}

		logger.info("Method : logistic uploadFile ends");
		return new ResponseEntity<>(response, HttpStatus.valueOf(Integer.parseInt(response.getCode())));
	}

	@SuppressWarnings("unchecked")
	@PostMapping("tender-activity-save-vendors")
	public @ResponseBody JsonResponse<Object> saveVenderActivityForTender(HttpSession session,
			@RequestBody Map<String, Object> tenderData) {
		logger.info("Method : save-Vendor-Activity-For-Tender starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");


			
	        Map<String, Object> technicalProposalData = (Map<String, Object>) tenderData.get("technicalProposalData");
	        Map<String, Object> financialProposalData = (Map<String, Object>) tenderData.get("financialProposalData");
	        List<Map<String, Object>> criteriaDataList = (List<Map<String, Object>>) tenderData.get("criteriaData");

	        String technicalProposalFileUrl = (String) technicalProposalData.get("technicalFile");
	        String financialProposalFileUrl = (String) financialProposalData.get("financialFile");

	        // Iterate over the criteriaData array to extract and update the documentName URL
	        if (criteriaDataList != null && !criteriaDataList.isEmpty()) {
	            for (Map<String, Object> criteriaData : criteriaDataList) {
	                String documentName = (String) criteriaData.get("documentName");
	                if (documentName != null && !documentName.isEmpty()) {
	                    String documentNameUrl = env.getBaseURL() + "document/image/omc/" + documentName;
	                    criteriaData.put("documentName", documentNameUrl);
	                }
	            }
	        }

	    
	        String fullTechnicalProposalFileUrl = env.getBaseURL() + "document/image/omc/" + technicalProposalFileUrl;
	        String fullFinancialProposalFileUrl = env.getBaseURL() + "document/image/omc/" + financialProposalFileUrl;

	        technicalProposalData.put("technicalFile", fullTechnicalProposalFileUrl);
	        financialProposalData.put("financialFile", fullFinancialProposalFileUrl);
	        


		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "save-vendors-tender-activity?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, tenderData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : save-Vendor-Activity-For-Tender ends");
		return resp;
	}

}
