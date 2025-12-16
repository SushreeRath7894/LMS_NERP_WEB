package nirmalya.aathithya.webmodule.lms.controller;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping("academic")
public class AcademicCourseController {
	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(AcademicCourseController.class);

	@GetMapping(value = { "/courses" })
	public String academicCourses(Model model, HttpSession session) {
		logger.info("Mothod:view courses page started...");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] hsnCode = restClient.getForObject(
					env.getMasterUrl() + "getProductCategoryList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> productCategoryList = Arrays.asList(hsnCode);
			model.addAttribute("productCategoryList", productCategoryList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Mothod: view courses page ends...");
		return "lms/academic-courses";
	}

	// academic-course-add
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/academic-course-add") public @ResponseBody
	 * JsonResponse<Object> saveCourse( HttpSession session,
	 * 
	 * @RequestParam("courseId") String courseId,
	 * 
	 * @RequestParam("courseTittle") String courseTittle,
	 * 
	 * @RequestParam("courseDesc") String courseDesc, // JSON string
	 * 
	 * @RequestParam("duration") String duration,
	 * 
	 * @RequestParam(value = "currencySymbol", required = false) String
	 * currencySymbol,
	 * 
	 * @RequestParam("price") String price,
	 * 
	 * @RequestParam(value = "rate", required = false) String rate,
	 * 
	 * @RequestParam(value = "level", required = false) String level,
	 * 
	 * @RequestParam("startDate") String startDate,
	 * 
	 * @RequestParam("endDate") String endDate,
	 * 
	 * @RequestParam("patientStatus") String patientStatus,
	 * 
	 * @RequestParam("priceType") String priceType,
	 * 
	 * @RequestParam("parentCategory") String parentCategory,
	 * 
	 * @RequestPart(value = "document", required = false) MultipartFile document ) {
	 * logger.info("Method : saveCourse starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<>();
	 * 
	 * try { String userId = (String) session.getAttribute("USER_ID"); String
	 * orgName = (String) session.getAttribute("ORGANIZATION"); String orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION");
	 * 
	 * Map<String, Object> courseData = new HashMap<>(); courseData.put("courseId",
	 * courseId); courseData.put("courseTittle", courseTittle);
	 * courseData.put("duration", duration); courseData.put("currencySymbol",
	 * currencySymbol); courseData.put("price", price); courseData.put("rate",
	 * rate); courseData.put("level", level); courseData.put("startDate",
	 * startDate); courseData.put("endDate", endDate);
	 * courseData.put("patientStatus", patientStatus); courseData.put("priceType",
	 * priceType); courseData.put("parentCategory", parentCategory);
	 * 
	 * // ✅ Parse courseDesc JSON string into Map ObjectMapper mapper = new
	 * ObjectMapper(); Map<String, Object> courseDescMap =
	 * mapper.readValue(courseDesc, new TypeReference<Map<String, Object>>() {});
	 * courseData.put("courseDesc", courseDescMap);
	 * 
	 * // ✅ Handle file upload if provided if (document != null &&
	 * !document.isEmpty()) { String uploadDir = env.getFileUploadDocumenttUrl();
	 * File uploadPath = new File(uploadDir);
	 * 
	 * if (!uploadPath.exists()) { uploadPath.mkdirs(); }
	 * 
	 * String fileName = System.currentTimeMillis() + "_" +
	 * document.getOriginalFilename(); String filePath = uploadDir + fileName;
	 * String fileURL = env.getBaseURL() + "document/image/" + fileName;
	 * 
	 * document.transferTo(new File(filePath));
	 * 
	 * courseData.put("documentURL", fileURL); courseData.put("documentName",
	 * document.getOriginalFilename()); }
	 * 
	 * logger.info("Final course data === {}", courseData);
	 * 
	 * // ✅ Call backend REST API resp = restClient.postForObject( env.getHisUrl() +
	 * "rest-academic-course-add?userId=" + userId + "&org=" + orgName + "&orgDiv="
	 * + orgDivision, courseData, JsonResponse.class );
	 * 
	 * } catch (Exception e) { logger.error("Error saving course", e);
	 * resp.setMessage("Error saving course: " + e.getMessage());
	 * resp.setCode("Failed"); }
	 * 
	 * logger.info("Method : saveCourse ends"); return resp; }
	 */
	
	
	/*
	 * @PostMapping("academic-course-add") public @ResponseBody JsonResponse<Object>
	 * saveCourse(HttpSession session,
	 * 
	 * @RequestParam("courseId") String courseId,
	 * 
	 * @RequestParam("courseTittle") String courseTittle,
	 * 
	 * @RequestParam("parentCategory") String parentCategory,
	 * 
	 * @RequestParam("duration") String duration,
	 * 
	 * @RequestParam("price") String price,
	 * 
	 * @RequestParam("startDate") String startDate,
	 * 
	 * @RequestParam("endDate") String endDate,
	 * 
	 * @RequestParam("patientStatus") String patientStatus,
	 * 
	 * @RequestParam("courseDesc") String courseDesc,
	 * 
	 * @RequestParam("rate") String rate,
	 * 
	 * @RequestParam("currencySymbol") String currencySymbol,
	 * 
	 * @RequestParam("level") String level,
	 * 
	 * @RequestParam(value = "document", required = false) MultipartFile document,
	 * 
	 * @RequestParam(value = "uploadList", required = false) String uploadListJson )
	 * {
	 * 
	 * logger.info("Method : saveCourse starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>(); String userId = "";
	 * String orgName = ""; String orgDivision = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * logger.error("Error getting session attributes", e); }
	 * 
	 * try { Map<String, Object> courseData = new HashMap<>();
	 * courseData.put("courseId", courseId); courseData.put("courseTittle",
	 * courseTittle); courseData.put("parentCategory", parentCategory);
	 * courseData.put("duration", duration); courseData.put("price", price);
	 * courseData.put("startDate", startDate); courseData.put("endDate", endDate);
	 * courseData.put("patientStatus", patientStatus);
	 * 
	 * // Decode Base64 and handle URL-encoded content String decodedCourseDesc =
	 * new String(Base64.getDecoder().decode(courseDesc), StandardCharsets.UTF_8);
	 * // Decode URL-encoded characters if present try { decodedCourseDesc =
	 * URLDecoder.decode(decodedCourseDesc, StandardCharsets.UTF_8.name()); } catch
	 * (Exception e) { logger.warn("No URL decoding needed for courseDesc: " +
	 * e.getMessage()); } // Clean excessive backslashes and escape sequences
	 * decodedCourseDesc = decodedCourseDesc.replaceAll("\\\\{2,}",
	 * "").replaceAll("\\\\n", "").replaceAll("\\\\t", ""); // Escape JSON using
	 * ObjectMapper ObjectMapper mapper = new ObjectMapper(); String
	 * escapedCourseDesc =
	 * mapper.writeValueAsString(decodedCourseDesc).replaceFirst("^\"",
	 * "").replaceFirst("\"$", ""); courseData.put("courseDesc", escapedCourseDesc);
	 * 
	 * courseData.put("rate", rate); courseData.put("currencySymbol",
	 * currencySymbol); courseData.put("level", level);
	 * 
	 * if (document != null && !document.isEmpty()) { String uploadDir =
	 * env.getFileUploadDocumenttUrl(); File uploadPath = new File(uploadDir);
	 * 
	 * if (!uploadPath.exists()) { uploadPath.mkdirs(); }
	 * 
	 * String fileName = System.currentTimeMillis() + "_" +
	 * Paths.get(env.getFileUploadDocumenttUrl()); String filePath = uploadDir +
	 * File.separator + fileName; String fileURL = env.getBaseURL() +
	 * "document/image/" + fileName; document.transferTo(new File(filePath));
	 * 
	 * courseData.put("documentURL", fileURL); courseData.put("documentName",
	 * Paths.get(env.getFileUploadDocumenttUrl())); }
	 * 
	 * // Log JSON payload ObjectMapper mapper1 = new ObjectMapper();
	 * logger.info("JSON Payload: {}", mapper1.writeValueAsString(courseData));
	 * 
	 * resp = restClient.postForObject( env.getHisUrl() +
	 * "rest-academic-course-add?userId=" + userId + "&org=" + orgName + "&orgDiv="
	 * + orgDivision, courseData, JsonResponse.class);
	 * 
	 * } catch (Exception e) { logger.error("Error saving course", e);
	 * resp.setMessage("Error saving course: " + e.getMessage());
	 * resp.setCode("Failed"); }
	 * 
	 * logger.info("Method : saveCourse ends"); return resp; }
	 */
	
	// Assuming this is in a utility or service class, but for completeness, including it here.
	// If it's in a separate class, autowire it accordingly.
	// Note: This method is as provided, but ensure 'env' is autowired or accessible (e.g., @Autowired Environment env;)
	/*
	 * public String saveAllMultiImages(byte[] imageBytes, String ext) {
	 * logger.info("Method : saveAllMultiImages starts"); String imageName1 = null;
	 * try { if (imageBytes != null) { long nowTime = new Date().getTime(); if
	 * (ext.contentEquals("jpeg")) { imageName1 = nowTime + ".jpg"; } else {
	 * imageName1 = nowTime + "." + ext; } } Path path =
	 * Paths.get(env.getFileUploadDocumenttUrl() + imageName1); if (imageBytes !=
	 * null) { Files.write(path, imageBytes); } } catch (Exception e) {
	 * e.printStackTrace(); } logger.info("Method : saveAllMultiImages ends");
	 * return imageName1; }
	 */
	
	public String saveAllMultiImagesAll(byte[] imageBytes, String ext) {
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
	
	@SuppressWarnings("unchecked")
	@PostMapping("academic-course-add")
	public @ResponseBody JsonResponse<Object> saveCourse(HttpSession session,
	        @RequestParam("courseId") String courseId,
	        @RequestParam("courseTittle") String courseTittle,
	        @RequestParam("parentCategory") String parentCategory,
	        @RequestParam("subcategory") String subcategory ,
	        @RequestParam("duration") String duration,
	        @RequestParam("price") String price,
	        @RequestParam("startDate") String startDate,
	        @RequestParam("endDate") String endDate,
	        @RequestParam("patientStatus") String patientStatus,
	        @RequestParam("courseDesc") String courseDesc,
	        @RequestParam("rate") String rate,
	        @RequestParam("currencySymbol") String currencySymbol,
	        @RequestParam("level") String level,
	        @RequestParam(value = "documents", required = false) MultipartFile documents
	      /*  @RequestParam("oldImage") String oldImage*/
	       /* @RequestParam(value = "uploadList", required = false) String uploadList*/) {
 
	    logger.info("Method : saveCourse starts"+documents);
 
	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    String userId = "";
	    String orgName = "";
	    String orgDivision = "";
 
	    try {
	        userId = (String) session.getAttribute("USER_ID");
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error("Error getting session attributes", e);
	    }
 
	    try {
	        Map<String, Object> courseData = new HashMap<>();
	        courseData.put("courseId", courseId);
	        courseData.put("courseTittle", courseTittle);
	        courseData.put("parentCategory", parentCategory);
	        courseData.put("subcategory", subcategory);
	        courseData.put("duration", duration);
	        courseData.put("price", price);
	        courseData.put("startDate", startDate);
	        courseData.put("endDate", endDate);
	        courseData.put("patientStatus", patientStatus);
 
	        // --- Decode courseDesc (Base64 + URL decode safe) ---
	        String decodedCourseDesc = new String(Base64.getDecoder().decode(courseDesc), StandardCharsets.UTF_8);
	        try {
	            decodedCourseDesc = URLDecoder.decode(decodedCourseDesc, StandardCharsets.UTF_8.name());
	        } catch (Exception e) {
	            logger.warn("No URL decoding needed for courseDesc: {}", e.getMessage());
	        }
	        decodedCourseDesc = decodedCourseDesc.replaceAll("\\\\{2,}", "")
	                                             .replaceAll("\\\\n", "")
	                                             .replaceAll("\\\\t", "");
 
	        ObjectMapper mapper = new ObjectMapper();
	        String escapedCourseDesc = mapper.writeValueAsString(decodedCourseDesc)
	                                         .replaceFirst("^\"", "")
	                                         .replaceFirst("\"$", "");
	        courseData.put("courseDesc", escapedCourseDesc);
 
	        courseData.put("rate", rate);
	        courseData.put("currencySymbol", currencySymbol);
	        courseData.put("level", level);
	       // courseData.put("categoryData", categoryData);
 
	        // --- Single document upload ---
	        if (documents != null && !documents.isEmpty()) {
	            String ext = FilenameUtils.getExtension(documents.getOriginalFilename());
	            byte[] bytes = documents.getBytes();
	            String fileName = saveAllMultiImagesAll(bytes, ext);
	            if (fileName != null) {
	                String fileURL = env.getBaseURL() + "document/image/" + fileName;
	                courseData.put("documentURL", fileURL);
	                courseData.put("documentName", fileName);
	            }
	        }
	        	
 
	        // --- Handle uploadList (old + new files) ---
	        
			/*
			 * List<Map<String, Object>> uploadListData = new ArrayList<>();
			 * 
			 * if (uploadList != null && !uploadList.isEmpty()) { ObjectMapper mapper1 = new
			 * ObjectMapper(); List<Map<String, Object>> parsedUploadList =
			 * mapper1.readValue(uploadList, new TypeReference<List<Map<String, Object>>>()
			 * {});
			 * 
			 * for (Map<String, Object> item : parsedUploadList) { if
			 * (item.containsKey("documentFile")) { Object documentFileObj =
			 * item.get("documentFile"); String base64Data = "";
			 * 
			 * // Handle both ArrayList and single String if (documentFileObj instanceof
			 * ArrayList) {
			 * 
			 * @SuppressWarnings("unchecked") ArrayList<String> documentFileList =
			 * (ArrayList<String>) documentFileObj; if (!documentFileList.isEmpty()) {
			 * base64Data = documentFileList.get(0); } } else if (documentFileObj instanceof
			 * String) { base64Data = (String) documentFileObj; } else {
			 * logger.warn("Unexpected documentFile type: " +
			 * documentFileObj.getClass().getName()); continue; }
			 * 
			 * // Remove common data URL prefixes for any type base64Data =
			 * base64Data.replaceFirst("^data:[^;]+;base64,", "");
			 * 
			 * // Decode Base64 byte[] bytes = Base64.getDecoder().decode(base64Data);
			 * 
			 * // Determine file extension String ext = (String) item.get("docType"); if
			 * (ext == null || ext.isEmpty()) { ext = "bin"; // fallback for unknown types }
			 * 
			 * // Save the file String fileName = saveAllMultiImages(bytes, ext); if
			 * (fileName != null) { String fileURL = env.getBaseURL() + "document/image/" +
			 * fileName;
			 * 
			 * Map<String, Object> docItem = new HashMap<>(); docItem.put("fileName",
			 * fileName); docItem.put("docUrl", fileURL); docItem.put("docName",
			 * item.getOrDefault("docName", fileName));
			 * 
			 * uploadListData.add(docItem); } } } }
			 * 
			 * 
			 * courseData.put("uploadList", uploadListData);
			 */
 
	        // --- Log JSON Payload ---
	        ObjectMapper mapper3 = new ObjectMapper();
	        logger.info("JSON Payload: {}", mapper3.writeValueAsString(courseData));
 
	        // --- Call REST API ---
	        resp = restClient.postForObject(
	                env.getHisUrl() + "rest-academic-course-add?userId=" + userId
	                        + "&org=" + orgName + "&orgDiv=" + orgDivision,
	                courseData, JsonResponse.class);
 
	    } catch (Exception e) {
	        logger.error("Error saving course", e);
	        resp.setMessage("Error saving course: " + e.getMessage());
	        resp.setCode("Failed");
	    }
 
	    logger.info("Method : saveCourse ends"+resp);
	    return resp;
	}


	@SuppressWarnings("unchecked")
	@GetMapping("academic-course-view")
	public @ResponseBody Object viewCourse(HttpSession session) {
		logger.info("Method :viewCourse starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(
					env.getHisUrl() + "rest-viewCourse?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :viewCourse ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("academic-course-quiz-view")
	public @ResponseBody Object coursequiz(HttpSession session) {
		logger.info("Method :coursequiz starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(
					env.getHisUrl() + "rest-coursequiz?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :coursequiz ends"+resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("academic-training-view")
	public @ResponseBody Object viewtraining(@RequestParam String id,HttpSession session) {
		logger.info("Method :viewtraining starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(
					env.getHisUrl() + "rest-viewtraining?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id,
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
		logger.info("Method :viewtraining ends");
		return resp;
	}
	

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("academic-course-edit")
	public @ResponseBody Object editCourse(@RequestParam String Id, HttpSession session) {
		logger.info("Method :editCourse starts" + Id);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-editCourse?Id=" + Id + "&organization=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editCourse ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("academic-course-employee-view")
	public @ResponseBody Object viewEmpolyee(HttpSession session) {
		logger.info("Method :viewEmpolyee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-academic-course-employee-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewEmpolyee ends");
		return resp;
	}

	/// addd/////
	@SuppressWarnings("unchecked")
	@PostMapping("academic-course-employee-add")
	public @ResponseBody JsonResponse<Object> saveAssign(HttpSession session, @RequestBody Map<String, Object> data) {
		logger.info("Method : saveAssign starts");

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
			resp = restClient.postForObject(env.getHisUrl() + "rest-academic-course-employee-add?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAssign ends");
		return resp;
	}
	
	
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("academic-course-training-save") public @ResponseBody
	 * JsonResponse<Object> saveTraining(HttpSession session, @RequestBody
	 * Map<String, Object> payload) {
	 * logger.info("Method : saveTraining starts"+payload);
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>(); String userId = "";
	 * String orgName = ""; String orgDivision = ""; try { userId = (String)
	 * session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * 
	 * } try { resp = restClient.postForObject(env.getHisUrl() +
	 * "rest-academic-saveTraining?userId=" + userId + "&org=" + orgName +
	 * "&orgDiv=" + orgDivision, payload, JsonResponse.class);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method : saveTraining ends"); return resp; }
	 */
	
	
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("academic-course-training-save") public @ResponseBody
	 * JsonResponse<Object> saveTraining(HttpSession session, @RequestBody
	 * Map<String, Object> payload) {
	 * 
	 * logger.info("Method: saveTraining starts - Payload: {}", payload);
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>(); String userId = "";
	 * String orgName = ""; String orgDivision = "";
	 * 
	 * // Session values try { userId = (String) session.getAttribute("USER_ID");
	 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { logger.error("Error retrieving session attributes", e); }
	 * 
	 * // ------------------------- PROCESS DOCUMENT FILES -------------------------
	 * try {
	 * 
	 * String courseId = (String) payload.get("courseId"); String categoryDataText =
	 * (String) payload.get("categoryData");
	 * 
	 * if (courseId != null && categoryDataText != null &&
	 * !categoryDataText.trim().isEmpty()) {
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * // Parse categoryData List<Map<String, Object>> trainingData =
	 * mapper.readValue(categoryDataText, new TypeReference<List<Map<String,
	 * Object>>>() {});
	 * 
	 * for (Map<String, Object> item : trainingData) {
	 * 
	 * List<Map<String, Object>> documents = (List<Map<String, Object>>)
	 * item.get("documents");
	 * 
	 * if (documents != null && !documents.isEmpty()) {
	 * 
	 * for (Map<String, Object> doc : documents) {
	 * 
	 * Object documentFileObj = doc.get("documentFile");
	 * 
	 * // ------------------------- CASE 1: NEW FILE PRESENT
	 * ------------------------- if (documentFileObj != null) {
	 * 
	 * String base64Data = "";
	 * 
	 * if (documentFileObj instanceof ArrayList) { List<String> list =
	 * (List<String>) documentFileObj; if (!list.isEmpty()) base64Data =
	 * list.get(0); } else if (documentFileObj instanceof String) { base64Data =
	 * (String) documentFileObj; }
	 * 
	 * // New file given if (base64Data != null && !base64Data.trim().isEmpty()) {
	 * 
	 * base64Data = base64Data.replaceFirst("^data:[^;]+;base64,", "");
	 * 
	 * try { byte[] bytes = Base64.getDecoder().decode(base64Data);
	 * 
	 * String ext = (String) doc.get("docType"); if (ext == null || ext.isEmpty())
	 * ext = "bin";
	 * 
	 * // Save file locally String fileName = saveAllMultiImages(bytes, ext);
	 * 
	 * if (fileName != null) { String fileURL = env.getBaseURL() + "document/image/"
	 * + fileName;
	 * 
	 * doc.put("fileName", fileName); doc.put("docUrl", fileURL); }
	 * 
	 * } catch (Exception ex) { logger.error("Error saving document", ex); } }
	 * 
	 * // Remove documentFile from JSON doc.remove("documentFile"); }
	 * 
	 * // ------------------------- CASE 2: OLD FILE (NO NEW UPLOAD)
	 * ------------------------- else {
	 * logger.info("Existing document retained: {}", doc.get("docView"));
	 * 
	 * // Ensure old keys remain doc.put("fileName", doc.get("docView"));
	 * doc.put("docUrl", doc.get("dociURL"));
	 * 
	 * // Clear 'documentFile' if present doc.remove("documentFile"); } } // END
	 * LOOP documents } } // END LOOP trainingData
	 * 
	 * // Replace cleaned data back into payload payload.put("categoryData",
	 * mapper.writeValueAsString(trainingData)); } } catch (Exception e) {
	 * logger.error("Error processing training documents", e); }
	 * 
	 * // ------------------------- FORWARD TO EXTERNAL HIS URL
	 * ------------------------- try { String hisUrl = env.getHisUrl() +
	 * "rest-academic-saveTraining?userId=" + userId + "&org=" + orgName +
	 * "&orgDiv=" + orgDivision;
	 * 
	 * resp = restClient.postForObject(hisUrl, payload, JsonResponse.class);
	 * 
	 * } catch (Exception e) { logger.error("Error calling HIS service", e);
	 * resp.setMessage("Error saving training data"); }
	 * 
	 * logger.info("Method: saveTraining ends"); return resp; }
	 */
	private String sanitizeFolderName(String name) {
	    if (name == null) return "";
	    return name
	        .toLowerCase()
	        .replaceAll("[^a-z0-9]+", "_")  // replace non-safe characters
	        .replaceAll("_+", "_")          // collapse multiple _
	        .replaceAll("^_|_$", "");       // trim leading/trailing _
	}

	private String extractBase64(Object fileObj) {
	    try {
	        String base64 = "";

	        // Case 1: List
	        if (fileObj instanceof List) {
	            List list = (List) fileObj;
	            if (!list.isEmpty()) {
	                base64 = String.valueOf(list.get(0));
	            }
	        }

	        // Case 2: String
	        else if (fileObj instanceof String) {
	            base64 = (String) fileObj;
	        }

	        // No valid data
	        if (base64 == null || base64.trim().isEmpty()) {
	            return null;
	        }

	        // Remove metadata: "data:image/png;base64,xxxxxx"
	        if (base64.contains(",")) {
	            return base64.substring(base64.indexOf(",") + 1);
	        }

	        return base64;

	    } catch (Exception e) {
	        logger.error("extractBase64() failed", e);
	        return null;
	    }
	}
	private String getSessionValue(HttpSession session, String key) {
	    try {
	        Object val = session.getAttribute(key);
	        return val != null ? val.toString() : "";
	    } catch (Exception e) {
	        logger.error("Error getting session attribute: {}", key, e);
	        return "";
	    }
	}

	private String saveFile(byte[] bytes, String ext) {
	    try {
	        return saveAllMultiImages(bytes, ext); // Handles both ZIP and images
	    } catch (Exception e) {
	        logger.error("Error saving file with extension: " + ext, e);
	        return null;
	    }
	}
	private String extractZipAndGetLaunchUrl(String zipFileName, String courseId) {
       logger.info("extractZipAndGetLaunchUrl Start");
	    String baseExtractPath = env.getScormExtractPath(); 
	    System.out.println("SCORM Base Path: " + baseExtractPath);

 	    if (!baseExtractPath.endsWith("/") && !baseExtractPath.endsWith("\\")) {
	        baseExtractPath = baseExtractPath + "/";
	    }

	    String extractFolder = baseExtractPath + courseId + "/" + System.currentTimeMillis() + "/";
	    System.out.println("Extract Folder Path: " + extractFolder);

	    File destDir = new File(extractFolder);
	    destDir.mkdirs();

	    try (ZipInputStream zipIn = new ZipInputStream(
	            new FileInputStream(env.getFileUploadDocumenttUrl() + zipFileName))) {

	        ZipEntry entry;

	        while ((entry = zipIn.getNextEntry()) != null) {
	            String entryName = entry.getName();

 	            String[] parts = entryName.split("/", 2);

	            if (parts.length == 2) {
	                String sanitizedRoot = sanitizeFolderName(parts[0]);
	                entryName = sanitizedRoot + "/" + parts[1];
	            } else {
	                entryName = sanitizeFolderName(entryName);
	            }

	            File filePath = new File(destDir, entryName);

	            if (entry.isDirectory()) {
	                filePath.mkdirs();
	            } else {
	                filePath.getParentFile().mkdirs();
	                Files.copy(zipIn, filePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            }

	            zipIn.closeEntry();
	        }

 	        File launchFile = findScormLaunchFile(destDir);
	        if (launchFile != null) {

 	            String fullPath = launchFile.getAbsolutePath().replace("\\", "/");
	            String normalizedBase = baseExtractPath.replace("\\", "/");

 	            if (!normalizedBase.endsWith("/")) {
	                normalizedBase = normalizedBase + "/";
	            }

	            // Build relative path
	            String relativePath = fullPath.replace(normalizedBase, "");

	            String finalUrl = env.getBaseURL() + "scorm/" + relativePath;

	            System.out.println("FINAL SCORM URL: " + finalUrl);

	            return finalUrl;
	        }
	        

	    } catch (Exception e) {
	        logger.error("SCORM Extraction Failed", e);
	    }
	    logger.info("extractZipAndGetLaunchUrl Ends");
	    return null;
	}

	private File findScormLaunchFile(File folder) {

	    // 1. List of the MOST common SCORM launch files (ALL vendors)
	    String[] commonLaunchFiles = {
	        "index.html", "index.htm",
	        "index_lms.html", "index_lms.htm",
	        "index_scorm.html", "index_scorm.htm",
	        "launch.html", "launch.htm",
	        "presentation.html", "presentation.htm",
	        "player.html", "player.htm",
	        "story.html", "story.htm",
	        "story_html5.html", "story_html5.htm",
	        "start.html", "start.htm",
	        "course.html", "course.htm",
	        "default.html", "default.htm",
	        "lms.html", "lms.htm"
	    };

 	    for (String name : commonLaunchFiles) {
	        File f = new File(folder, name);
	        if (f.exists()) return f;
	    }

 	    File[] files = folder.listFiles();
	    if (files != null) {
	        for (File f : files) {
	            if (f.isDirectory()) {
	                File result = findScormLaunchFile(f);
	                if (result != null) return result;
	            }
	        }
	    }

 	    if (files != null) {
	        for (File f : files) {
	            if (f.isFile() && f.getName().toLowerCase().endsWith(".html")) {
	                return f;
	            }
	        }
	    }

	    return null;
	}



	private Map<String, Object> processDocumentFile(Map<String, Object> doc, String courseId) {

	    String docTypeSelect = (String) doc.get("docTypeSelect"); // NORMAL or SCORM
	    String ext = (String) doc.get("docType");
	    Object fileObj = doc.get("documentFile");

	    // -----------------------------------------
	    // CASE 1: NEW FILE UPLOADED
	    // -----------------------------------------
	    if (fileObj != null) {

	        String base64 = extractBase64(fileObj);
	        if (base64 == null) return doc; // nothing to process

	        byte[] bytes = Base64.getDecoder().decode(base64);

	        // ZIP / SCORM handling
	        if ("SCORM".equalsIgnoreCase(docTypeSelect) && "zip".equalsIgnoreCase(ext)) {

	            String zipFileName = saveFile(bytes, "zip");
	            doc.put("fileName", zipFileName);

	            // Extract ZIP and get SCORM LAUNCH URL
	            String launchUrl = extractZipAndGetLaunchUrl(zipFileName, courseId);
	            doc.put("launchUrl", launchUrl);  // <-- FINAL URL FOR UI

	            // For compatibility, remove old zip URL
	            doc.put("docUrl", launchUrl);
               System.out.println("Document Url of final------->"+launchUrl);
	        } 

	        // Normal image / other files
	        else {
	            String savedName = saveFile(bytes, ext);
	            doc.put("fileName", savedName);
	            doc.put("docUrl", env.getBaseURL() + "document/image/" + savedName);
	        }

	        doc.remove("documentFile");
	        return doc;
	    }

	    // -----------------------------------------
	    // CASE 2: EXISTING FILE (NO NEW UPLOAD)
	    // -----------------------------------------
	    doc.put("fileName", doc.get("docView"));
	    doc.put("docUrl", doc.get("dociURL"));
        System.out.println("Get the extracted path------>"+(String) doc.get("extractedPath"));
	    if ("SCORM".equalsIgnoreCase(docTypeSelect)) {
	        String preview = generateExtractionPreviewVideo((String) doc.get("extractedPath"));
	        doc.put("previewVideoUrl", preview);
	    }

	    return doc;
	}

	
	@SuppressWarnings("unchecked")
	@PostMapping("academic-course-training-save")
	public @ResponseBody JsonResponse<Object> saveTraining(
	        HttpSession session,
	        @RequestBody Map<String, Object> payload) {

	    logger.info("Method: saveTraining starts - Payload: {}", payload);

	    JsonResponse<Object> resp = new JsonResponse<>();

	    // ------------------------- SESSION DATA -------------------------
	    String userId = getSessionValue(session, "USER_ID");
	    String orgName = getSessionValue(session, "ORGANIZATION");
	    String orgDivision = getSessionValue(session, "ORGANIZATION_DIVISION");

	    try {
	        String courseId = (String) payload.get("courseId");
	        String categoryDataText = (String) payload.get("categoryData");

	        if (courseId != null && categoryDataText != null && !categoryDataText.trim().isEmpty()) {

	            ObjectMapper mapper = new ObjectMapper();

	            // FIX for Java 8: must specify full type
	            List<Map<String, Object>> trainingData =
	                    mapper.readValue(categoryDataText, new TypeReference<List<Map<String, Object>>>() {});

	            for (Map<String, Object> item : trainingData) {

	                List<Map<String, Object>> documents =
	                        (List<Map<String, Object>>) item.get("documents");

	                if (documents == null) continue;

	                for (int i = 0; i < documents.size(); i++) {
	                    Map<String, Object> updated = processDocumentFile(documents.get(i), courseId);
	                    documents.set(i, updated);
	                }
	            }

	            payload.put("categoryData", mapper.writeValueAsString(trainingData));
	        }


	    } catch (Exception e) {
	        logger.error("Error processing training documents", e);
	    }

	    // ------------------------- CALL HIS SERVICE -------------------------
	    try {
	        String hisUrlFull = env.getHisUrl()
	                + "rest-academic-saveTraining?userId=" + userId
	                + "&org=" + orgName
	                + "&orgDiv=" + orgDivision;

	        resp = restClient.postForObject(hisUrlFull, payload, JsonResponse.class);

	    } catch (Exception e) {
	        logger.error("Error calling HIS service", e);
	        resp.setMessage("Error saving training data");
	    }

	    logger.info("Method: saveTraining ends");
	    return resp;
	}


    // Helper: Save ZIP file
    private String saveZipFile(byte[] bytes, String ext) {
        try {
            long now = new Date().getTime();
            String fileName = now + "." + ext;
            Path path = Paths.get(env.getFileUploadDocumenttUrl() + fileName);
            Files.write(path, bytes);
            return fileName;
        } catch (Exception e) {
            logger.error("Error saving ZIP", e);
            return null;
        }
    }

    // Helper: Extract ZIP to local folder and prepare for LMS SCRUM data
    private String extractZipAndPreparePreview(String zipFileName, String courseId, String docType) {
        try {
            Path zipPath = Paths.get(env.getFileUploadDocumenttUrl() + zipFileName);
            String extractDirName = courseId + "_" + docType + "_" + System.currentTimeMillis();
            Path extractPath = Paths.get(env.getFileUploadDocumenttUrl() + "extracted/" + extractDirName);

            // Create extracted directory if not exists
            Files.createDirectories(extractPath);

            // Extract ZIP
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath.toFile()))) {
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null) {
                    Path filePath = extractPath.resolve(zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        Files.createDirectories(filePath);
                    } else {
                        Files.createDirectories(filePath.getParent());
                        Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
            }

            // For LMS SCRUM: Optionally process extracted files (e.g., validate SCORM structure)
            // e.g., check for imsmanifest.xml for SCORM

            return "extracted/" + extractDirName; // Relative path for preview
        } catch (Exception e) {
            logger.error("Error extracting ZIP", e);
            return null;
        }
    }

    // Helper: Generate preview video (simple: if ZIP has MP4, return URL; else, placeholder)
    // Note: For real "extraction video", you could use FFmpeg to record screen of extraction, but that's external.
    // Here, assume pick first video from extracted.
    private String generateExtractionPreviewVideo(String extractPath) {
    	System.out.println("Extracted Path as parameter----->"+extractPath);
        try {
            Path fullExtractPath = Paths.get(env.getFileUploadDocumenttUrl() + extractPath);
            System.out.println("Full Extract Path------>"+fullExtractPath);
            // Find first .mp4 or .avi in extracted folder
            Optional<Path> videoFile = Files.walk(fullExtractPath)
                    .filter(p -> p.toString().toLowerCase().endsWith(".mp4") || p.toString().toLowerCase().endsWith(".avi"))
                    .findFirst();

            if (videoFile.isPresent()) {
                String videoName = videoFile.get().getFileName().toString();
                return env.getBaseURL()  + "document/image/" + videoName; // Serve via separate endpoint
            } else {
                // Placeholder: Generate a simple HTML video or return null
                return null;
            }
        } catch (Exception e) {
            logger.error("Error generating preview video", e);
            return null;
        }
    }

    // New endpoint: Serve extracted preview video
    @GetMapping("document/image/{filename}")
    public ResponseEntity<Resource> servePreviewVideo(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(env.getFileUploadDocumenttUrl() + "extracted/" + filename); // Adjust path
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("video/mp4"))
                        .body(resource);
            }
        } catch (Exception e) {
            logger.error("Error serving preview video", e);
        }
        return ResponseEntity.notFound().build();
    }

    // Existing saveAllMultiImages (unchanged)
    public String saveAllMultiImages(byte[] imageBytes, String ext) {
        logger.info("Method : saveAllMultiImages starts");

        String imageName = null;

        try {
            if (imageBytes != null) {

                long now = new Date().getTime();

                if ("jpeg".equalsIgnoreCase(ext)) ext = "jpg";

                imageName = now + "." + ext;

                Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);

                Files.write(path, imageBytes);
            }

        } catch (Exception e) {
            logger.error("Error saving file", e);
        }

        logger.info("Method : saveAllMultiImages ends");
        return imageName;
    }


	// ------------------------- SAVE FILE FUNCTION -------------------------
	/*
	 * public String saveAllMultiImages(byte[] imageBytes, String ext) {
	 * 
	 * logger.info("Method : saveAllMultiImages starts");
	 * 
	 * String imageName = null;
	 * 
	 * try { if (imageBytes != null) {
	 * 
	 * long now = new Date().getTime();
	 * 
	 * if ("jpeg".equalsIgnoreCase(ext)) ext = "jpg";
	 * 
	 * imageName = now + "." + ext;
	 * 
	 * Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
	 * 
	 * Files.write(path, imageBytes); }
	 * 
	 * } catch (Exception e) { logger.error("Error saving file", e); }
	 * 
	 * logger.info("Method : saveAllMultiImages ends"); return imageName; }
	 */

	// view instructor
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-instructor-list")
	public @ResponseBody Object viewList(HttpSession session) {
		logger.info("Method :viewList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(
					env.getHisUrl() + "rest-get-all-instructor-list?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :viewList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("course-details-view")
	public @ResponseBody Object viewCourses(@RequestParam String courseId, HttpSession session) {
		logger.info("Method :viewEnrollCourses starts");
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
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewCourses?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&courseId=" + courseId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewEnrollCourses ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "get-all-course-list" })
	public @ResponseBody JsonResponse<DropDownModel> getCourseAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCourseAutoSearchList starts");
		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restTemplate.getForObject(env.getHisUrl() + "getCourseAutoSearchList?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getCourseAutoSearchList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("get-catlog-courses")
	public @ResponseBody Object viewCatlog(@RequestParam String catId, String level, HttpSession session) {
		logger.info("Method :viewCatlog starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			String url = env.getHisUrl() + "rest-get-catlog-courses?orgName=" + orgName + "&orgDivision=" + orgDivision
					+ "&catId=" + catId + "&level=" + level;

			resp = restClient.getForObject(url, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && !resp.getMessage().equals("")) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewCatlog ends");
		return resp;
	}

	@GetMapping(value = { "course-builder" })
	public String courseBuilder(Model model, HttpSession session) {
		logger.info("Mothod:view courseBuilder page started...");

		logger.info("Mothod: view courseBuilder page ends...");
		return "lms/course-builder";
	}
	
	@GetMapping(value = { "progress-dashboard" })
	public String studentProgressDashboard(Model model, HttpSession session) {
		logger.info("Mothod:view studentProgressDashboard page started...");
		DropDownModel[] course = restTemplate.getForObject(env.getMasterUrl() + "/courseList",
				DropDownModel[].class);
		List<DropDownModel> courseList = Arrays.asList(course);
		model.addAttribute("courseList", courseList);
		logger.info("Mothod: view studentProgressDashboard page ends...");
		return "lms/student-progress-dashboard";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("save-course-content")
	public @ResponseBody JsonResponse<Object> saveCorseContent(HttpSession session, @RequestParam String courseId,
			@RequestBody Map<String, Object> data) {
		logger.info("Method : saveCorseContent starts");

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
			resp = restClient.postForObject(env.getHisUrl() + "rest-academic-course-content-add?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision + "&courseId=" + courseId, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveCorseContent ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("get-course-details")
	public @ResponseBody Object getCourseDetails(@RequestParam String courseId, HttpSession session) {
		logger.info("Method :getCourseDetails starts");
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
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-get-course-details?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&courseId=" + courseId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getCourseDetails ends" + resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "academic-course-get-subcategory" })
	public @ResponseBody JsonResponse<Object> subcategory(String id, HttpSession session) {
		logger.info("Method : subcategory starts");
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
		logger.info("Method : orgName starts" + orgName);
		logger.info("Method : orgDivision starts" + orgDivision);
		try {

			resp = restClient.getForObject(
					env.getMasterUrl() + "subcategory?org=" + orgName + "&orgDiv=" + orgDivision + "&id=" + id,
					JsonResponse.class);
			// res = restTemplate.getForObject(env.getPurchaseUrl() +
			// "getBrandList?orgName=" + orgName + "&orgDivision=" +
			// orgDivision,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : subcategory ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping(value = {"courses-delete-training"})
	public @ResponseBody JsonResponse<Object> deleteTraining(String trainingId, HttpSession session) {
		logger.info("Method : deleteTraining starts");
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
		logger.info("Method : orgName starts" + orgName);
		logger.info("Method : orgDivision starts" + orgDivision);
		try {

			resp = restClient.getForObject(
					env.getHisUrl() + "rest-delete-training?org=" + orgName + "&orgDiv=" + orgDivision + "&trainingId=" + trainingId,
					JsonResponse.class);
			// res = restTemplate.getForObject(env.getPurchaseUrl() +
			// "getBrandList?orgName=" + orgName + "&orgDivision=" +
			// orgDivision,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteTraining ends");
		return resp;
	}
	
	// Fixed Web Controller Method (in AcademicCourseWebController or similar)
	@SuppressWarnings("unchecked")
	@PostMapping("academic-course-quiz-save")
	public @ResponseBody JsonResponse<Object> saveQuizMappings(HttpSession session,
	        @RequestBody String quizData) {

	    logger.info("Method : saveQuizMappings starts");

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    String userId = "";
	    String orgName = "";
	    String orgDivision = "";

	    try {
	        userId = (String) session.getAttribute("USER_ID");
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error("Error getting session attributes", e);
	    }

	    if (userId == null || userId.isEmpty()) {
	        resp.setMessage("User session not found.");
	        resp.setCode("Failed");
	        return resp;
	    }

	    try {
	        // Parse the incoming JSON payload
	        ObjectMapper mapper = new ObjectMapper();
	        ObjectNode jsonNode = (ObjectNode) mapper.readTree(quizData);
	        
	        // Extract and validate courseId
	        String courseId = "";
	        if (jsonNode.has("courseId") && !jsonNode.get("courseId").isNull()) {
	            courseId = jsonNode.get("courseId").asText();
	            logger.info("Extracted courseId from JSON: " + courseId);
	        } else {
	            logger.info("No courseId found in JSON or courseId is null");
	            resp.setMessage("Course ID is required.");
	            resp.setCode("Failed");
	            return resp;
	        }

	        // Clean and validate quizMappings if present
	        if (jsonNode.has("quizMappings")) {
	            ArrayNode mappingsArray = (ArrayNode) jsonNode.get("quizMappings");
				/*
				 * if (mappingsArray.isEmpty()) {
				 * logger.info("Empty quizMappings array provided; this will unmap all quizzes."
				 * ); }
				 */
	            for (JsonNode mapping : mappingsArray) {
	                // Validate required fields in each mapping
	                if (!mapping.has("courseId") || !mapping.has("quizCode") || !mapping.has("status")) {
	                    logger.warn("Invalid mapping entry: missing required fields");
	                    resp.setMessage("Invalid quiz mapping data.");
	                    resp.setCode("Failed");
	                    return resp;
	                }
	                // Optional: Clean any text fields if added in future (e.g., quizDesc)
	                // if (mapping.has("quizDesc")) { ... similar to courseDesc cleaning }
	            }
	            jsonNode.set("quizMappings", mappingsArray);
	        } else {
	            logger.info("No quizMappings found in payload; unmapping all quizzes for course.");
	        }

	        // Convert cleaned jsonNode to Map for consistent serialization (mirrors saveCourse approach)
	        Map<String, Object> quizPayload = mapper.convertValue(jsonNode, new TypeReference<Map<String, Object>>() {});
	        logger.info("Prepared quizPayload: " + mapper.writeValueAsString(quizPayload));

	        // --- Log JSON Payload ---
	        ObjectMapper mapper3 = new ObjectMapper();
	        logger.info("JSON Payload: {}", mapper3.writeValueAsString(quizPayload));

	        // --- Call REST API ---
	        // Now sending Map directly, serializes to {"courseId":..., "quizMappings":[...]} JSON
	        resp = restClient.postForObject(
	                env.getHisUrl() + "rest-academic-course-quiz-save?userId=" + userId
	                        + "&org=" + orgName + "&orgDiv=" + orgDivision,
	                quizPayload, JsonResponse.class);

	    } catch (Exception e) {
	        logger.error("Error saving quiz mappings", e);
	        resp.setMessage("Error saving quiz mappings: " + e.getMessage());
	        resp.setCode("Failed");
	    }

	    logger.info("Method : saveQuizMappings ends");
	    return resp;
	}


	// Serve SCORM content (HTML, JS, CSS, images...) from extracted ZIP
@GetMapping("scorm-content/{zipFileName:.+}/**")
public ResponseEntity<Resource> serveScormPackage(HttpServletRequest request,
                                                 @PathVariable String zipFileName) {
    logger.info("[SCORM] serveScormPackage called with zipFileName={}", zipFileName);

    try {
        String requestUri = request.getRequestURI();
 
        String prefix = "/academic/scorm-content/" + zipFileName + "/";
        int idx = requestUri.indexOf(prefix);
        String relativePath = "";
        if (idx != -1) {
            relativePath = requestUri.substring(idx + prefix.length());
        }
        if (relativePath.isEmpty()) {
            relativePath = "index.html";
        }

 
        Path uploadBase = Paths.get(env.getFileUploadDocumenttUrl());
        logger.info("[SCORM] uploadBase = {}", uploadBase.toAbsolutePath());

        // Folder: <uploadDir>/scorm/<zipBaseName>/
        String baseName = zipFileName;
        int dot = baseName.lastIndexOf('.');
        if (dot != -1) {
            baseName = baseName.substring(0, dot);
        }

        Path scormRoot = uploadBase.resolve("scorm").resolve(baseName);
        logger.info("[SCORM] scormRoot directory = {}", scormRoot.toAbsolutePath());

        // First request → unzip
        if (!Files.exists(scormRoot)) {
            Path zipPath = uploadBase.resolve(zipFileName);
            logger.info("[SCORM] scormRoot does not exist, will unzip. zipPath = {}", zipPath.toAbsolutePath());

            if (!Files.exists(zipPath)) {
                logger.error("[SCORM] Zip file not found at {}", zipPath.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }
            unzipScorm(zipPath, scormRoot);
        }

        Path target = scormRoot.resolve(relativePath);
        logger.info("[SCORM] Target SCORM asset path = {}", target.toAbsolutePath());

        if (!Files.exists(target) || Files.isDirectory(target)) {
            logger.error("[SCORM] Target asset not found or is a directory: {}", target.toAbsolutePath());
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(target.toUri());
        MediaType mediaType = resolveMediaType(target);

        logger.info("[SCORM] Serving asset with mediaType={} from {}", mediaType, target.toAbsolutePath());
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);

    } catch (Exception e) {
        logger.error("[SCORM] Error serving SCORM content", e);
        return ResponseEntity.notFound().build();
    }
}

private void unzipScorm(Path zipPath, Path destinationDir) throws Exception {
    logger.info("[SCORM] unzipScorm started. zipPath={}, destinationDir={}",
            zipPath.toAbsolutePath(), destinationDir.toAbsolutePath());

    Files.createDirectories(destinationDir);

    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath.toFile()))) {
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            Path newPath = destinationDir.resolve(entry.getName());
            logger.info("[SCORM] Extracting entry: {} => {}", entry.getName(), newPath.toAbsolutePath());

            if (entry.isDirectory()) {
                Files.createDirectories(newPath);
            } else {
                if (newPath.getParent() != null) {
                    Files.createDirectories(newPath.getParent());
                }
                Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    logger.info("[SCORM] unzipScorm completed for {}", zipPath.toAbsolutePath());
}

private MediaType resolveMediaType(Path file) {
    String fileName = file.getFileName().toString().toLowerCase();
    logger.info("[SCORM] resolveMediaType for file {}", fileName);

    if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
        return MediaType.TEXT_HTML;
    } else if (fileName.endsWith(".js")) {
        return new MediaType("application", "javascript");
    } else if (fileName.endsWith(".css")) {
        return new MediaType("text", "css");
    } else if (fileName.endsWith(".png")) {
        return MediaType.IMAGE_PNG;
    } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
        return MediaType.IMAGE_JPEG;  
    } else if (fileName.endsWith(".gif")) {
        return MediaType.IMAGE_GIF;
    } else if (fileName.endsWith(".json")) {
        return MediaType.APPLICATION_JSON;
    } else if (fileName.endsWith(".xml")) {
        return MediaType.APPLICATION_XML;
    }

    logger.info("[SCORM] Defaulting mediaType to OCTET_STREAM for file {}", fileName);
    return MediaType.APPLICATION_OCTET_STREAM;
}


@SuppressWarnings("unchecked")
@PostMapping("academic-course-delete")
public @ResponseBody JsonResponse<Object> coursedelete(
        HttpSession session,
        @RequestBody Map<String, Object> payload) {

    logger.info("Method : coursedelete starts");
    JsonResponse<Object> response = new JsonResponse<>();

    try {
        String orgName = (String) session.getAttribute("ORGANIZATION");
        String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
        String userId = (String) session.getAttribute("USER_ID");

        // Add session data
        payload.put("orgName", orgName);
        payload.put("orgDivision", orgDivision);
        payload.put("loginUserId", userId);

        logger.info("📦 Final payload to send: {}", payload);

        String url = env.getHisUrl() + "rest-coursedelete";
        response = restTemplate.postForObject(url, payload, JsonResponse.class);

    } catch (Exception e) {
        logger.error("Error calling REST service:", e);
    }

    logger.info("Method : coursedelete ends");
    return response;
}

}
