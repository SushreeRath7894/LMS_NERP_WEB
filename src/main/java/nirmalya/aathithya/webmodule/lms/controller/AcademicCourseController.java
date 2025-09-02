package nirmalya.aathithya.webmodule.lms.controller;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
	@SuppressWarnings("unchecked")
	@PostMapping("academic-course-add")
	public @ResponseBody JsonResponse<Object> saveCourse(HttpSession session,
	        @RequestParam("courseId") String courseId,
	        @RequestParam("courseTittle") String courseTittle,
	        @RequestParam("parentCategory") String parentCategory,
	        @RequestParam("duration") String duration,
	        @RequestParam("price") String price,
	        @RequestParam("startDate") String startDate,
	        @RequestParam("endDate") String endDate,
	        @RequestParam("patientStatus") String patientStatus,
	        @RequestParam("courseDesc") String courseDesc,
	        @RequestParam("rate") String rate,
	        @RequestParam("currencySymbol") String currencySymbol,
	        @RequestParam("level") String level,
	        @RequestParam(value = "document", required = false) MultipartFile document,
	        @RequestParam(value = "uploadList", required = false) String uploadList) {

	    logger.info("Method : saveCourse starts");

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

	        // --- Single document upload ---
	        if (document != null && !document.isEmpty()) {
	            String ext = FilenameUtils.getExtension(document.getOriginalFilename());
	            byte[] bytes = document.getBytes();
	            String fileName = saveAllMultiImages(bytes, ext);
	            if (fileName != null) {
	                String fileURL = env.getBaseURL() + "document/image/" + fileName;
	                courseData.put("documentURL", fileURL);
	                courseData.put("documentName", fileName);
	            }
	        }

	        // --- Handle uploadList (old + new files) ---
	        List<Map<String, Object>> uploadListData = new ArrayList<>();

	        if (uploadList != null && !uploadList.isEmpty()) {
	            ObjectMapper mapper1 = new ObjectMapper();
	            List<Map<String, Object>> parsedUploadList = 
	                    mapper1.readValue(uploadList, new TypeReference<List<Map<String, Object>>>() {});

	            for (Map<String, Object> item : parsedUploadList) {
	                List<String> documentFiles = new ArrayList<>();

	                // Check if this item has new uploads
	                if (item.containsKey("documentFile") && item.get("documentFile") != null) {
	                    Object documentFileObj = item.get("documentFile");

	                    if (documentFileObj instanceof ArrayList) {
	                        @SuppressWarnings("unchecked")
	                        ArrayList<String> fileList = (ArrayList<String>) documentFileObj;
	                        documentFiles.addAll(fileList);
	                    } else if (documentFileObj instanceof String) {
	                        documentFiles.add((String) documentFileObj);
	                    } else {
	                        logger.warn("Unexpected documentFile type: " + documentFileObj.getClass().getName());
	                    }
	                }

	                // Process new uploads
	                for (String base64Data : documentFiles) {
	                    if (base64Data == null || base64Data.isEmpty()) continue;

	                    // Remove Base64 prefix (generic for all file types)
	                    base64Data = base64Data.replaceFirst("^data:[^;]+;base64,", "");
	                    byte[] bytes = Base64.getDecoder().decode(base64Data);

	                    // Determine extension
	                    String ext = (String) item.get("docType");
	                    if (ext == null || ext.isEmpty()) {
	                        ext = "bin";
	                    }
	                    
	                    

	                    String fileName = saveAllMultiImages(bytes, ext);
	                    if (fileName != null) {
	                        String fileURL = env.getBaseURL() + "document/image/" + fileName;

	                        Map<String, Object> docItem = new HashMap<>();
	                        docItem.put("fileName", fileName);
	                        docItem.put("docUrl", fileURL);
	                        docItem.put("docName", item.getOrDefault("docName", fileName));

	                      //  uploadListData.add(docItem);
	                    }
	                    
	                    // Preserve existing uploaded files (already saved previously)
		                if (documentFiles.isEmpty()) {
		                	logger.info("hhhh"+documentFiles);
		                    Map<String, Object> docItem = new HashMap<>();
		                    docItem.put("fileName", item.get("dociURL"));
		                    docItem.put("docUrl", item.get("dociURL"));
		                    docItem.put("docName", item.get("docView"));
		                    uploadListData.add(docItem);
		                }
	                }

	               
	            }
	        }

	        courseData.put("uploadList", uploadListData);

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

	    logger.info("Method : saveCourse ends");
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

}
