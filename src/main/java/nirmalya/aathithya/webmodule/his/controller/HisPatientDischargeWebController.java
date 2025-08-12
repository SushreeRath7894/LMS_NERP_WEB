package nirmalya.aathithya.webmodule.his.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping("his")
public class HisPatientDischargeWebController {
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	Logger logger = LoggerFactory.getLogger(HisPatientDischargeWebController.class);

	@GetMapping(value = { "/patient-discharge" })
	public String viewDischarge(Model model, HttpSession session) {
		logger.info("Method : viewDischarge starts");
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] category = restClient.getForObject(env.getHisUrl() + "get-discharge-type-list?org=" + org
					+ "&orgDiv=" + orgDiv , DropDownModel[].class);
			List<DropDownModel> dischargeList = Arrays.asList(category);
			model.addAttribute("dischargeTypeList", dischargeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewDischarge ends");
		return "employee/patientDischarge.html";

	}

    //GET THE PATIENT DETAILS FOR DISCHARGE
	@SuppressWarnings("unchecked")
	@GetMapping("patient-discharge-get-details")
	public @ResponseBody Object getPatientDetails(@RequestParam String bookingId, HttpSession session) {

		logger.info("Method : getPatientDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restClient
					.getForObject(
							env.getHisUrl() + "rest-get-discharge-patient-details?orgName=" + organization + "&orgDiv="
									+ orgDivision + "&userId=" + userId + "&bookingId=" + bookingId,
							JsonResponse.class);
			System.out.println("Response For Patient Discharge Details------>"+resp);
			// After getting resp
			logger.info("Response For Patient Discharge Details------>" + resp);

			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> bodyList = mapper.readValue(resp.getBody().toString(), List.class);

			if (bodyList != null && !bodyList.isEmpty()) {
				Map<String, Object> dischargeDetails = (Map<String, Object>) bodyList.get(0).get("dischargeDetails");

				String dischargeDocumentsStr = (String) dischargeDetails.get("dischargeDocuments");

				List<Map<String, Object>> docList = mapper.readValue(dischargeDocumentsStr, List.class);

				int i = 0;
				for (Map<String, Object> doc : docList) {

					String fileName = (String) doc.get("fileName");

					if (fileName != null && fileName != "") {

						String extension = "";

						if (fileName.contains(".")) {
							extension = fileName.substring(fileName.lastIndexOf(".") + 1);
						}

						String docPath = "";

						if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
							docPath = "<i class='fa-solid fa-file-excel custom-file-icon' title='" + fileName + "'></i>";
						} else if (extension.equalsIgnoreCase("pdf")) {
							docPath = "<i class='fa-solid fa-file-pdf custom-file-icon' title='" + fileName + "'></i>";
						} else if (extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx")) {
							docPath = "<i class='fa-solid fa-file-word custom-file-icon' title='" + fileName + "'></i>";
						} else if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg")
								|| extension.equalsIgnoreCase("jpeg")) {
							docPath = "<i class='fa-solid fa-file-image custom-file-icon' title='" + fileName + "'></i>";
						}

						String finalAction = "<a style='margin-left: 10px' class='example-image-link' href='/document/document/"
								+ fileName + "' target='_blank'>" + docPath + "</a>"
								+ "<div id='imageName_" + i + "' class='imageName' style='margin-left: 2px;'>" + fileName
								+ "</div><span><i class='ti-close red close_sec1 deleteFileDoc' onclick='openDeleteConfirm(" + i
								+ ")'></i></span>";

						doc.put("action", finalAction);
					} else {
						doc.put("action", "");
					}
					i++;
				}

				dischargeDetails.put("dischargeDocuments", docList);
				resp.setBody(bodyList);
			}
           

		} catch (Exception e) {
			logger.error("Error in getPatientDetails: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getPatientDetails ends");

		return resp;
	}

	// ADD DISCHARGE DATA
	@SuppressWarnings("unchecked")
	@PostMapping("/patient-discharge-save-data")
	public @ResponseBody JsonResponse<Object> addPatientDischargeData(
			@RequestBody Map<String, Object> dischargeJsonData, HttpSession session) {

		logger.info("Method : addPatientDischargeData starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String organization = "";
		String orgDivision = "";
		String createdById = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdById = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes", e);
		}

		try {
			// Handling Document Manipulation
			List<Map<String, Object>> docList = (List<Map<String, Object>>) dischargeJsonData.get("docList");

			if (docList != null && !docList.isEmpty()) {
				for (Map<String, Object> doc : docList) {
					String imageNameEdit = (String) doc.get("imageNameEdit");
					String fileName = (String) doc.get("fileName");
					List<String> documentFile = (List<String>) doc.get("documentFile");

					if (imageNameEdit != null && !imageNameEdit.equals("")) {
						doc.put("fileName", imageNameEdit);
					} else {
						if (fileName != null && !fileName.equals("") && documentFile != null
								&& !documentFile.isEmpty()) {
							String[] extension = fileName.split("\\.");
							int lastIndex = extension.length - 1;

							// Save all images (Base64 to File)
							for (String s1 : documentFile) {
								try {
									byte[] bytes = Base64.getDecoder().decode(s1);
									String imageName = saveAllMultiImages(bytes, extension[lastIndex]);
									doc.put("fileName", imageName);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					// Blank the documentFile after image processing
					doc.put("documentFile", new ArrayList<String>());
				}
			}

			String url = env.getHisUrl() + "rest-add-discharge-data";
			System.out.println("URL For Discharge Data=====>" + url);

			dischargeJsonData.put("orgName", organization);
			dischargeJsonData.put("orgDiv", orgDivision);
			dischargeJsonData.put("createdById", createdById);

			logger.info("Sending Discharge Data to the service: " + dischargeJsonData);

			resp = restClient.postForObject(url, dischargeJsonData, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in addPatientDischargeData: ", e);
		}

		logger.info("Method : addPatientDischargeData ends");
		return resp;
	}

	public String saveAllMultiImages(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllMultiImages starts");
		String imageName1 = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}

	//GET THE PATIENT DETAILS FOR PDF
		@SuppressWarnings("unchecked")
		@GetMapping("patient-discharge-get-details-for-pdf")
		public @ResponseBody Object getPatientDetailsForPdf(@RequestParam String bookingId, HttpSession session,HttpServletResponse response) {

			logger.info("Method : getPatientDetailsForPdf starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";
			String userId = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userId = (String) session.getAttribute("USER_ID");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restClient
						.getForObject(
								env.getHisUrl() + "rest-get-discharge-patient-details-for-pdf?orgName=" + organization + "&orgDiv="
										+ orgDivision + "&userId=" + userId + "&bookingId=" + bookingId,
								JsonResponse.class);
				System.out.println("Response For Patient Discharge Details For Pdf------>"+resp);
				// After getting resp
				  ObjectMapper mapper = new ObjectMapper();

			        List<Map<String, Object>> bodyList = mapper.readValue(resp.getBody().toString(), List.class);
			        Map<String, Object> pdfData = new HashMap<>();

			        if (!bodyList.isEmpty()) {
			            Map<String, Object> patientData = bodyList.get(0);

			            // Patient Basic Details
			            pdfData.put("patientName", patientData.get("patientName"));
			            pdfData.put("patientId", patientData.get("patientId"));
			            pdfData.put("admitDate", patientData.get("admitDate"));
			            pdfData.put("dischargeDate", patientData.get("dischargeDate"));
			            pdfData.put("dischargeTime", patientData.get("dischargeTime"));
			            pdfData.put("patientAgeAndGender", patientData.get("patientAgeAndGender"));

			            // Doctor Name
			            pdfData.put("doctorName", patientData.get("doctorName"));

			            // Final Diagnosis
			            pdfData.put("finalDiagnosis", patientData.get("finalDiagnosis"));

			            // Follow Up Advice
			            pdfData.put("followUpAdvice", patientData.get("followUpAdvice"));

			            // Treatment Details (List of Medicines)
			            List<Map<String, Object>> treatmentDetails = (List<Map<String, Object>>) patientData.get("treatmentDetails");

			            List<Map<String, String>> medicineList = new ArrayList<>();
			            if (treatmentDetails != null) {
			                for (Map<String, Object> medicine : treatmentDetails) {
			                    Map<String, String> medicineData = new HashMap<>();
			                    medicineData.put("medicineName", (String) medicine.get("medicineName"));
			                    medicineData.put("dosage", (String) medicine.get("dosage"));
			                    medicineData.put("duration", (String) medicine.get("duration"));
			                    medicineData.put("frequency", (String) medicine.get("frequency"));
			                    medicineList.add(medicineData);
			                }
			            }
			            pdfData.put("treatmentDetails", medicineList);
			        }

			        logger.info("Extracted Data For PDF Generation : " + pdfData);
			        String filename = "patient-discharge-details.pdf";
				    response.setContentType("application/pdf");
				    response.setHeader("Content-disposition", "inline; filename=" + filename);

				    try {
				        File pdfFile = pdfGeneratorUtil.createPdf("his/patient-discharge-pdf", pdfData);
				        InputStream in = new FileInputStream(pdfFile);
				        byte[] fileData = IOUtils.toByteArray(in);
				        response.setContentLength(fileData.length);
				        response.getOutputStream().write(fileData);
				        response.getOutputStream().flush();
				    } catch (IOException e) {
				        logger.error("Error while generating or sending PDF", e);
				    }
	           

			} catch (Exception e) {
				logger.error("Error in getPatientDetailsForPdf: ", e);
				e.printStackTrace();
			}

			logger.info("Method : getPatientDetailsForPdf ends");

			return resp;
		}
		@SuppressWarnings("unchecked")
		@GetMapping("patient-discharge-delete-data")
		public @ResponseBody Object deleteDischareData (@RequestParam String bookingId,HttpSession session) {
			logger.info("Delete Discharge Data Method Starts");
			JsonResponse<Object>resp=new JsonResponse<Object>();
			String orgName="";
			String orgDiv="";
			try {
				orgName=(String)session.getAttribute("ORGANIZATION");
				orgDiv=(String)session.getAttribute("ORGANIZATION_DIVISION");
			}catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp=restClient.getForObject(env.getHisUrl()+"rest-delete-discharge-data?bookingId="+bookingId+"&orgName="+orgName+"&orgDiv="+orgDiv, JsonResponse.class);
			}catch (Exception e) {
				logger.error("Error in deleteMeetingCalendar: ", e);
				e.printStackTrace();
			}
			logger.info("Delete Discharge Data Method End");
			return resp;
			
		}
		

}
