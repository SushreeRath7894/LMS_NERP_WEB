package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
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

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.MediaDecontaminationRecordModel;
import nirmalya.aathithya.webmodule.qa.model.RmPmReleaseStatusModel;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;


@Controller

@RequestMapping(value = { "qa/" })
public class MediaDecontaminationRecordController {
	Logger logger = LoggerFactory.getLogger(MediaDecontaminationRecordController.class);

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

	@GetMapping(value = { "media-decontamination-record" })

	public String mediaDecontamination(Model model, HttpSession session) {
	
		logger.info("Method :mediaDecontamination starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl()+ "getYearList-attendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : mediaDecontamination ends");

		return "qa/media-decontamination-record";
	}
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "media-decontamination-record-add" })
	public @ResponseBody JsonResponse<Object> addMedia(@RequestBody List<MediaDecontaminationRecordModel> av, HttpSession session) {
		logger.info("Method : addMedia function starts");
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
		for (MediaDecontaminationRecordModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setIssuedDate(DateFormatter.inputDateFormat(m.getIssuedDate(), dateFormat));
			

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addMedia", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addMedia function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("media-decontamination-record-view")
	public @ResponseBody Object viewMediaData(HttpSession session) {
		logger.info("Method :viewMediaData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String restUrl = env.getQa() + "viewMediaData?orgName="+ orgName + "&orgDivision=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "viewMediaData");
		return resp;
	}
	
	@GetMapping("media-decontamination-record-edit")
	public @ResponseBody Object editMedia(@RequestParam String mediaId, HttpSession session) {

		logger.info("Method :editMedia starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String restUrl = env.getQa() + "rest-editMedia?mediaId=" + mediaId + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-editMedia");
		return resp;
	}
	
	@GetMapping("media-decontamination-record-approve")
	public @ResponseBody Object approveMedia(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveMedia starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String restUrl = env.getQa() + "rest-approveMedia?mediaId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-approveMedia");
		return resp;
	}
	
	@GetMapping("media-decontamination-record-delete")
	public @ResponseBody Object deleteMedia(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteMedia starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String restUrl = env.getQa() + "rest-deleteMedia?mediaId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-deleteMedia");
		return resp;
	}
	
	/**
	 * @author silpa
	 * @param data
	 * @param session
	 * @return
	 */
	/*@PostMapping(value = { "media-decontamination-record-image-upload" })
	public @ResponseBody String saveImage(@RequestBody MediaDecontaminationRecordModel data, HttpSession session){
		
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
	}*/
	@PostMapping(value = { "media-decontamination-record-image-upload" })
	public @ResponseBody String saveImage(@RequestBody MediaDecontaminationRecordModel data, HttpSession session) {
	    
	    logger.info("Save Image File Controller");
	    data.setCreatedBy((String) session.getAttribute("USER_ID"));
	    
	    String fileUrl = "";
	    if (data.getDocumentFileBase() != null && !data.getDocumentFileBase().isEmpty()) {
	        String[] parts = data.getFileName().split("\\.");
	        String extension = parts[parts.length - 1];
	        
	        // Generate filename with today's date and a random 4-digit number
	        LocalDate today = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String dateStr = today.format(formatter);
	        
	        Random random = new Random();
	        int randomNumber = 1000 + random.nextInt(9000); // Random 4-digit number
	        
	        String uniqueFilename = dateStr + "_" + randomNumber;
	        
	        try {
	            byte[] bytes = Base64.getDecoder().decode(data.getDocumentFileBase());
	            fileUrl = saveAllMediaDocuments(bytes, extension, uniqueFilename);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        data.setDocumentURL(fileUrl);
	    }
	    
	    return fileUrl;
	}
	
	/**
	 * @author silpa
	 * @param imageBytes
	 * @param ext
	 * @param fileName
	 * @return
	 */
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
//
	//PDF

			@SuppressWarnings("unchecked")
			@GetMapping("media-decontamination-record-pdf-downloads")
			public void mediaPdf(HttpServletResponse response, Model model, HttpSession session,
			        @RequestParam("id") String encodedParam1) {

			    logger.info("Method : mediaPdf starts");
			    String orgName = "";
			    String orgDivision = "";
			    try {
			        orgName = (String) session.getAttribute("ORGANIZATION");
			        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			    } catch (Exception e) {
			        logger.error(e.getMessage());
			    }

			    byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
			    String id = (new String(encodeByte3));

			    System.out.println("JSON===== === " + id);
			    JsonResponse<Object> resp = new JsonResponse<Object>();

			    try {
			        resp = restTemplate.getForObject(env.getQa() + "rest-mediaPdf?id=" + id + "&orgName="
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
			        JsonNode mediaPdfNode = rootNode.path("mediaPdf");

			        if (mediaPdfNode.isArray() && mediaPdfNode.size() > 0) {
			            JsonNode firstElement = mediaPdfNode.get(0);
			            data.put("mediaPdf", objectMapper.convertValue(firstElement, Map.class));
			            
			            // Extracting pdfVb1arDetails
			            JsonNode mediaPdfDetailsNode = firstElement.path("mediaPdfDetails");
			            if (mediaPdfDetailsNode.isArray()) {
			                List<Map<String, Object>> mediaPdfDetailsList = objectMapper.convertValue(mediaPdfDetailsNode,new TypeReference<List<Map<String, Object>>>() {});
			                
			                data.put("mediaPdfDetails", mediaPdfDetailsList);
			            }
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
			    response.setHeader("Content-disposition", "inline; filename=mediaPdf.pdf");
			    File file;
			    byte[] fileData = null;
			    try {
			        file = pdfGeneratorUtil.createPdf("qa/mediaPdf.html", data);
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

			    logger.info("Method : mediaPdf ends");
			}




}
