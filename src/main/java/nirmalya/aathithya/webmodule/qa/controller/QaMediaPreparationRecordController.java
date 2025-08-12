package nirmalya.aathithya.webmodule.qa.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

import org.apache.poi.util.IOUtils;
import org.json.JSONException;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.AdvanceManagementModel;
import nirmalya.aathithya.webmodule.qa.model.AshAnalysisRawDataModel;
import nirmalya.aathithya.webmodule.qa.model.FatSolAnalysisModel;
import nirmalya.aathithya.webmodule.qa.model.MediaDecontaminationRecordModel;
import nirmalya.aathithya.webmodule.qa.model.PACAnalysisRecordModel;
import nirmalya.aathithya.webmodule.qa.model.QaCrqsModel;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;
import nirmalya.aathithya.webmodule.qa.model.QaEvaluationBoppTapeModel;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;
import nirmalya.aathithya.webmodule.qa.model.QaRCFTMModel;

@Controller

@RequestMapping(value = { "qa/" })
public class QaMediaPreparationRecordController {

	Logger logger = LoggerFactory.getLogger(QaMediaPreparationRecordController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "media-preparation-record" })

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

		return "qa/media-preparation-record";
	}

	// getaggridData

	@SuppressWarnings("unchecked")

	@GetMapping("media-preparation-record-aggrid-show")
	public @ResponseBody Object getaggridDatas(HttpSession session) {
		logger.info("Method :getaggridData starts");
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
			resp = restTemplate.getForObject(env.getQa() + "rest-media-preparation-record-aggrid-show?orgName="
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
		System.out.println("view===" + resp);
		logger.info("Method :getaggridDatas ends");
		return resp;
	}

	/*
	 * //Main save
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "media-preparation-record-details-save" })
	public @ResponseBody JsonResponse<Object> addMediaPreparation(@RequestBody List<QaEvaluationBoppTapeModel> av,
			HttpSession session) {
		logger.info("Method : addMediaPreparation function starts");
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
		
		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
	    byte[] bytes;
	    String imageName = null;

	    if (inputFile != null) {
	        try {
	            bytes = inputFile.getBytes();
	            String[] fileType = inputFile.getContentType().split("/");
	            imageName = saveAllImage(bytes, fileType[1]);
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	    }
	    for (QaEvaluationBoppTapeModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			if (imageName != null) {
	            m.setDocName(imageName);
	        }
		}
	
		System.out.println("imageName ==" + imageName);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addMediaPreparation", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addMediaPreparation function Ends");
		return resp;
	}
	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("png") || ext.contentEquals("jpg") || ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveAllImage: "+e.getMessage());
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
		
	}
	// getView

	@SuppressWarnings("unchecked")

	@GetMapping("media-preparation-record-details-view")
	public @ResponseBody Object getTotalMediaPreparationView(HttpSession session) {
		logger.info("Method :getTotalMediaPreparationView starts");
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
					env.getQa() + "getTotalMediaPreparationView?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :getTotalMediaPreparationView ends");
		return resp;
	}

	// editCodeCrqs
	@SuppressWarnings("unchecked")
	@GetMapping("media-preparation-record-edit")
	public @ResponseBody Object editMediaPreparationViewRecord(@RequestParam String id, HttpSession session) {
		logger.info("Method :editMediaPreparationViewRecord starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-editMediaPreparationViewRecord?id=" + id + "&orgName="
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
		logger.info("Method :editMediaPreparationViewRecord ends");
		return resp;
	}

	// deleteCrqs

	@SuppressWarnings("unchecked")
	@PostMapping("media-preparation-record-delete")
	public @ResponseBody JsonResponse<Object> deleteMediaPreparationRecord(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteMediaPreparationRecord function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		try {

			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getQa() + "rest-deleteMediaPreparationRecord?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteMediaPreparationRecord function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	// approveCRQS

	@SuppressWarnings("unchecked")
	@PostMapping("media-preparation-record-approve-data")
	public @ResponseBody JsonResponse<Object> approveMediaPreparationRecord(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approveMediaPreparationRecord function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		try {

			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getQa() + "rest-approveMediaPreparationRecord?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approveMediaPreparationRecord function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	

	@PostMapping("media-preparation-record-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("uploadFile: "+e.getMessage());
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}
	@PostMapping(value = { "media-preparation-record-image-upload" })
	public @ResponseBody String saveImage(@RequestBody MediaDecontaminationRecordModel data, HttpSession session) {

		logger.info("Save Image File COntroller");
		data.setCreatedBy((String) session.getAttribute("USER_ID"));

		String fileUrl = "";
		if (data.getDocumentFileBase() != null && data.getDocumentFileBase() != "") {
			String[] x = data.getFileName().split("\\.");
			String extension = x[x.length - 1];
			try {
				byte[] bytes = Base64.getDecoder().decode(data.getDocumentFileBase());
				fileUrl = saveAllMediaDocuments(bytes, extension, x[0]);

			} catch (Exception e) {
				e.printStackTrace();
			}
			data.setDocumentURL(fileUrl);
		}

		return fileUrl;
	}

	public String saveAllMediaDocuments(byte[] imageBytes, String ext, String fileName) {
		logger.info("Method : saveAllMedicalDocuments starts");

		String imageName = null;
		try {

			if (imageBytes != null) {

				if (ext.contentEquals("jpeg")) {
					imageName = fileName + ".jpg";
				} else {
					imageName = fileName + "." + ext;
				}
			}

			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String url = env.getBaseURL() + "document/document/" + imageName;

		logger.info("Method : saveAllMediaDocuments ends");
		return url;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("media-preparation-record-pdf-downloads")
	public void mediaPrepPdfDetails(HttpServletResponse response, Model model, HttpSession session,
	                               @RequestParam("id") String encodedParam3)
	        throws JsonParseException, JsonMappingException, JSONException, IOException {

	    logger.info("Method : mediaPrepPdfDetails starts");
	    String orgName = "";
	    String orgDivision = "";
	    try {
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
	    String id = new String(encodeByte5);

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    
	    try {
	        resp = restTemplate.getForObject(env.getQa() + "rest-mediaPrepPdfDetails?id=" + id
	                + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    logger.info("Response: " + resp);
	    Map<String, Object> data = new HashMap<String, Object>();

	    if (resp.getBody() == null) {
	        response.setContentType("application/pdf");
	        response.setHeader("Content-disposition", "inline; filename=mediaPrepPdfDetails.pdf");
	        File file;
	        byte[] fileData = null;

	        try {
	            file = pdfGeneratorUtil.createPdf("qa/mediaPrepPdfDetails.html", data);
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
	        return;
	    }

	    String responseBody = resp.getBody().toString();
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode jsonNode = mapper.readTree(responseBody);

	    if (jsonNode.isArray()) {
	        List<Map<String, Object>> dataa = mapper.readValue(responseBody,
	                new TypeReference<List<Map<String, Object>>>() {});
	        
	        data.put("respdata", dataa);

	        // Ensure the dataa list is not empty
	        if (!dataa.isEmpty()) {
	            // Retrieve the first item's docName
	            String docName = (String) dataa.get(0).get("docName");

	            // Log the filename for debugging
	            System.out.println("Filename: " + docName);

	            // Construct the image URL
	            String imagePath = env.getBaseURL() + "document/document/" + docName;
	            System.out.println("Image URL: " + imagePath);

	            // Update the data map with the image URL
	            data.put("docName", imagePath);
	        } else {
	            System.out.println("The dataa list is empty.");
	        }
	    } else {
	        System.out.println("Response is not a JSON array: " + responseBody);
	    }


	    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
	    URL getUrl = null;
	    try {
	        getUrl = new URL(logo);
	    } catch (MalformedURLException e2) {
	        e2.printStackTrace();
	    }
	    String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
	    data.put("logo", "data:image/png;base64," + encodedLogoUrl);

	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "inline; filename=mediaPrepPdfDetails.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("qa/mediaPrepPdfDetails.html", data);
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

	    logger.info("Method : mediaPrepPdfDetails ends");
	}
	
}
