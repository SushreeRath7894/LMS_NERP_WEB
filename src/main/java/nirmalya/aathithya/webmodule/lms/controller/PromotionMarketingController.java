
package nirmalya.aathithya.webmodule.lms.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;


	@Controller
	@RequestMapping("academic")
	public class PromotionMarketingController {
		Logger logger = LoggerFactory.getLogger(PromotionMarketingController.class);

		@Autowired
		EnvironmentVaribles env;
		@Autowired
		RestTemplate restTemplate;
		
		 @Value("${spring.mail.host}")
		    private String host;

		    @Value("${spring.mail.port}")
		    private String port;

		    @Value("${spring.mail.username}")
		    private String username;

		    @Value("${spring.mail.password}")
		    private String password;

		@GetMapping("promotion-marketing")
		public String promotionDetails(Model model, HttpSession session) {
			logger.info("Mothod:view promotionDetails page started...");

			logger.info("Mothod: view promotionDetails page ends...");
			return "lms/promotion-marketing";
		}
// View
		@SuppressWarnings("unchecked")
		@GetMapping("promotion-marketing-view")
		public @ResponseBody Object viewCoupon(HttpSession session) {
			logger.info("Method :viewCoupon starts");
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
						env.getMasterUrl() + "rest-viewCoupon?orgName=" + orgName + "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			logger.info("Method :viewCoupon ends");
			return resp;
		}
//
		@SuppressWarnings("unchecked")
		// @PostMapping("promotion-marketing-save-data")
		// public @ResponseBody JsonResponse<Object> addCoupon(@RequestBody Map<String, Object> couponJsonData, HttpSession session) {
		//     logger.info("Method : addCoupon starts");
 
		//     JsonResponse<Object> resp = new JsonResponse<Object>();
		//     String organization = "";
		//     String orgDivision = "";
		//     String createdById = "";

		//     try {
		//         organization = (String) session.getAttribute("ORGANIZATION");
		//         orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		//         createdById = (String) session.getAttribute("USER_ID");
		//     } catch (Exception e) {
		//         e.printStackTrace();
		//     }

		//     try {
		//         String url = env.getMasterUrl() + "rest-addCoupon";
		//         System.out.println("Perforation URL==========>>>>>>>"+url);

		//         String couponId = (String) couponJsonData.get("couponId");
		//         String couponNo = (String) couponJsonData.get("couponNo");
		//         String discount = (String) couponJsonData.get("discount");
		//         String price = (String) couponJsonData.get("price");
		//         String validFrom = (String) couponJsonData.get("validFrom");
		//         String validTo = (String) couponJsonData.get("validTo");
		//         String status = (String) couponJsonData.get("status");

		//         List<Map<String, Object>> rows = (List<Map<String, Object>>) couponJsonData.get("rows");

		//         Map<String, Object> requestPayload = new HashMap<>();
		//         requestPayload.put("couponId", couponId);   
		//         requestPayload.put("orgName", organization);      
		//         requestPayload.put("orgDiv", orgDivision);       
		//         requestPayload.put("createdById", createdById);   
		//         requestPayload.put("couponNo", couponNo);                
		//         requestPayload.put("discount",discount);                
		//         requestPayload.put("price",price);                
		//         requestPayload.put("validFrom", validFrom);               
		//         requestPayload.put("validTo", validTo);               
		//         requestPayload.put("status", status); 
		//         requestPayload.put("rows", rows);     

		//         logger.info("Sending filter clining record data to the service: " + requestPayload);

		//         resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
		//     } catch (Exception e) {
		//         logger.error("Error in addFCdata: ", e);
		//         e.printStackTrace();
		//     }

		//     logger.info("Method : addCoupon ends");
		//     return resp;
		// }

//

		@PostMapping("promotion-marketing-save-data")
		public @ResponseBody JsonResponse<Object> savePromotionMarketing(
		        @RequestParam String data,
		        @RequestParam(required = false) MultipartFile file,
		        HttpSession session) {
		    logger.info("Method : savePromotionMarketing starts");

		    JsonResponse<Object> resp = new JsonResponse<>();
		    String organization = "";
		    String orgDivision = "";
		    String createdById = "";

		    try {
		        organization = (String) session.getAttribute("ORGANIZATION");
		        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		        createdById = (String) session.getAttribute("USER_ID");
		    } catch (Exception e) {
		        logger.error("Error retrieving session attributes: ", e);
		        e.printStackTrace();
		    }

		    try {
		        // Parse the JSON string to Map
		        ObjectMapper mapper = new ObjectMapper();
		        Map<String, Object> requestJsonData = mapper.readValue(data, Map.class);

		        String url = env.getMasterUrl() + "rest-addCoupon"; 
		        logger.info("REST URL: {}", url);

		        String type = (String) requestJsonData.get("type");
		        Map<String, Object> requestPayload = new HashMap<>();
		        requestPayload.put("orgName", organization);
		        requestPayload.put("orgDiv", orgDivision);
		        requestPayload.put("createdById", createdById);
		        requestPayload.put("type", type);

		        String documentName = null;

		        // Handle file upload for marketing
		        if ("marketing".equals(type) && file != null && !file.isEmpty()) {
		            String originalFilename = file.getOriginalFilename();
		            String ext = getFileExtension(originalFilename);
		            byte[] imageBytes = file.getBytes();
		            documentName = saveAllMultiImagesAll(imageBytes, ext);
		            requestPayload.put("documentName", documentName);
		            logger.info("Document saved with name: {}", documentName);
		        }

		        if ("coupon".equals(type)) {
		            // Handle Coupon Data
		            String couponId = (String) requestJsonData.get("couponId");
		            String couponNo = (String) requestJsonData.get("couponNo");
		            String discount = (String) requestJsonData.get("discount");
		            String price = (String) requestJsonData.get("price");
		            String validFrom = (String) requestJsonData.get("validFrom");
		            String validTo = (String) requestJsonData.get("validTo");
		            String status = (String) requestJsonData.get("status");
		            List<Map<String, Object>> rows = (List<Map<String, Object>>) requestJsonData.get("rows");

		            requestPayload.put("couponId", couponId);
		            requestPayload.put("couponNo", couponNo);
		            requestPayload.put("discount", discount);
		            requestPayload.put("price", price);
		            requestPayload.put("validFrom", validFrom);
		            requestPayload.put("validTo", validTo);
		            requestPayload.put("status", status);
		            requestPayload.put("rows", rows);
		        } else if ("marketing".equals(type)) {
		            // Handle Marketing Data
		            String marketingId = (String) requestJsonData.get("marketingId");
		            String marketName = (String) requestJsonData.get("marketName");
		            String startDate = (String) requestJsonData.get("startDate");
		            String endDate = (String) requestJsonData.get("endDate");
		            String status = (String) requestJsonData.get("status");
                    String description = (String) requestJsonData.get("description");


		            requestPayload.put("marketingId", marketingId);
		            requestPayload.put("marketName", marketName);
		            requestPayload.put("startDate", startDate);
		            requestPayload.put("endDate", endDate);
		            requestPayload.put("status", status);
                    requestPayload.put("description", description);
		            // documentName already added above if file present
		        } else {
		            logger.error("Invalid type provided: {}", type);
		            resp.setCode("error");
		            resp.setMessage("Invalid type. Must be 'coupon' or 'marketing'.");
		            return resp;
		        }

		        logger.info("Sending promotion/marketing data to the service: {}", requestPayload);

		        resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
		    } catch (Exception e) {
		        logger.error("Error in savePromotionMarketing: ", e);
		        resp.setCode("error");
		        resp.setMessage("Failed to save data: " + e.getMessage());
		    }

		    logger.info("Method : savePromotionMarketing ends");
		    return resp;
		}

		// Helper method to get file extension
		private String getFileExtension(String filename) {
		    if (filename == null || filename.isEmpty()) {
		        return "";
		    }
		    int lastDotIndex = filename.lastIndexOf('.');
		    if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
		        return filename.substring(lastDotIndex + 1).toLowerCase();
		    }
		    return "";
		}

		// The provided saveAllMultiImagesAll method (unchanged)
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
		@GetMapping("promotion-marketing-edit")
		public @ResponseBody Object editCoupon(@RequestParam String couponId,HttpSession session) {

			logger.info("Method : editCoupon starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";
		 
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			 

			} catch (Exception e) {
				e.printStackTrace();
			}
	           
			try {

				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-editCoupon?orgName=" + organization + "&orgDiv=" + orgDivision +"&couponId="+couponId, JsonResponse.class);

			} catch (Exception e) {
				logger.error("Error in editCoupon: ", e);
				e.printStackTrace();
			}

			logger.info("Method : editCoupon ends");

			return resp;
		}
// DELETE 
		@SuppressWarnings("unchecked")
		@GetMapping("promotion-marketing-delete")
		public @ResponseBody Object deletCoupon(@RequestParam String couponId, HttpSession session) {

			logger.info("Method : deletCoupon starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";

			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deletCoupon?couponId="
						+ couponId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				logger.error("Error in deletCoupon: ", e);
				e.printStackTrace();
			}

			logger.info("Method : deletCoupon ends");

			return resp;
		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("promotion-marketing-course-list")
		public @ResponseBody Object viewCourseList(HttpSession session) {
			logger.info("Method :viewCourseList starts");
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
						env.getMasterUrl() + "rest-viewCourseList?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
			logger.info("Method :viewCourseList ends");
			return resp;
		}
//
		
}

