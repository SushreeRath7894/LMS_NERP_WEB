package nirmalya.aathithya.webmodule.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.ManageNoticeModel;

@Controller
@RequestMapping(value = "master/")
public class LetterManagementController {
	Logger logger = LoggerFactory.getLogger(LetterManagementController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    
	@GetMapping(value = { "letter-management" })
	public String letterManagement(Model model, HttpSession session) {
		logger.info("Method : letterManagement starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";
		String baseURL = "";
		String logo = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			baseURL = env.getBaseURL();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] noticeType = restClient.getForObject(env.getMasterUrl() + "get-all-notice-type?organization=" + organization 
					+ "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> noticeTypeList = Arrays.asList(noticeType);
			model.addAttribute("noticeType", noticeTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		model.addAttribute("logo", logo);
		model.addAttribute("baseURL", baseURL);
		logger.info("Method : letterManagement ends");
		return "his_master/letter-management";
	}
// view Letters
	@SuppressWarnings({ "unchecked" })
	@GetMapping("letter-management-view-employee")
	public @ResponseBody JsonResponse<List<Object>> getLetters(HttpSession session, @RequestParam String type) {
		logger.info("Method : getLetters starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";
		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-get-elg-employee?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getLetters ends");
		return res;
	}
// emp autosearch	
	@SuppressWarnings("unchecked")
	@PostMapping("letter-management-autoserach-employee")
	public @ResponseBody Object EmployeeAutoSearch(Model model, @RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method :EmployeeAutoSearch starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "get-all-employee-list?id=" + searchValue + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getCode().contentEquals("success")) {
			resp.setMessage("success");
		} else {
			resp.setMessage(" ");
		}
		logger.info("Method :EmployeeAutoSearch ends");
		return resp;
	}
// letter content data
	@SuppressWarnings({ "unchecked" })
	@GetMapping("letter-management-get-content")
	public @ResponseBody JsonResponse<Object> getLetterContent(HttpSession session, @RequestParam String id) {
		logger.info("Method : getLetterContent starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-get-notice-content?org=" + organization
					+ "&orgDiv=" + orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getLetterContent ends");
		return res;

	}
// edit letter
	@SuppressWarnings({ "unchecked" })
	@GetMapping("letter-management-edit")
	public @ResponseBody JsonResponse<Object> getLetterEdit(HttpSession session, @RequestParam String id) {
		logger.info("Method : getLetterEdit starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-notice-edit-fetch?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getLetterEdit ends");
		return res;
	}
// download pdf
	@SuppressWarnings("unchecked")
	@GetMapping("letter-management-pdf-download")
	public void getNoticePdfDetails(HttpServletResponse response, Model model, HttpSession session, @RequestParam("noticeId") String encodedParam1, 
			@RequestParam("organization") String encodedParam2, @RequestParam("orgDivision") String encodedParam3,  @RequestParam("logo") String encodedParam5,
			@RequestParam("userId") String encodedParam4) {

		logger.info("Method: getNoticePdfDetails starts");
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String noticeId = new String(encodeByte3);
		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String organization = new String(encodeByte4);
		byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String orgDivision = new String(encodeByte5);
		byte[] encodeByte6 = Base64.getDecoder().decode(encodedParam4.getBytes());
		String userId = new String(encodeByte6);		
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam5.getBytes());
		String logo = new String(encodeByte1);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		try {
			jsonResponse = restClient .getForObject( env.getMasterUrl() + "get-notice-pdfDetails?id=" + noticeId + "&organization="
				+ organization + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String noticeDescString = jsonResponse.getBody().toString();
		Map<String, Object> hashMapObject = null;
		try {
			List<Map<String, Object>> list = objectMapper.readValue(noticeDescString,
					new TypeReference<List<Map<String, Object>>>() {
					});

			hashMapObject = list.get(0);

			System.out.println("hashMapObject>>"+hashMapObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> data = new HashMap<>();
		/*
		 * URL getUrl = null; try { getUrl = new URL(logo); } catch
		 * (MalformedURLException e2) { e2.printStackTrace(); }
		 * 
		 * String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		 * data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		 */
		System.out.println("logo==="+logo);
		data.put("logo","");
		data.put("reason", hashMapObject.get("reason"));
		data.put("noticeId", hashMapObject.get("noticeId"));
		data.put("noticeType", hashMapObject.get("noticeType"));
		data.put("noticeDate", hashMapObject.get("currDate"));
		data.put("empId", hashMapObject.get("empId"));
		data.put("empMobile", hashMapObject.get("empMobile"));
		data.put("employeeName", hashMapObject.get("empName"));
		data.put("manager", hashMapObject.get("manager"));
		data.put("noticeDescription", hashMapObject.get("noticeDesc"));
		data.put("companyName", hashMapObject.get("companyName"));
		data.put("noticeSender", hashMapObject.get("noticeSender"));
		data.put("empDepartment", hashMapObject.get("empDepartment"));
		data.put("empDesignation", hashMapObject.get("empDesignation"));
		data.put("senderDesignation", hashMapObject.get("senderDesignation"));
		data.put("senderMobile", hashMapObject.get("senderMobile"));
		data.put("createdBy", hashMapObject.get("createdBy"));
		data.put("orgPhone", hashMapObject.get("orgPhone"));
		data.put("orgEmail", hashMapObject.get("orgEmail"));
		data.put("orgAddress", hashMapObject.get("orgAddress"));
		data.put("workedAt", hashMapObject.get("workedAt"));
		data.put("gender", hashMapObject.get("gender"));
		data.put("noticeSubject", hashMapObject.get("noticeSubject"));
		data.put("organization", organization);
		data.put("division", orgDivision);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=Letter_" + noticeId + ".pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("notice/letter-pdf-download", data);
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
		logger.info("Method: getNoticePdfDetails ends");
	}
// Add Notice Details
	@SuppressWarnings("unchecked")
	@PostMapping("letter-management-master-save")
	public @ResponseBody JsonResponse<Object> saveLetterDets(@RequestBody ManageNoticeModel data, HttpSession session) {
		logger.info("Method : saveLetterDets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "rest-add-notice-dtls", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : saveLetterDets starts");
		return resp;
	}
// send mail
	@SuppressWarnings("unchecked")
	@GetMapping("letter-management-send-mail")
	public @ResponseBody JsonResponse<Object> sendEmailNotice(HttpServletResponse response, Model model, HttpSession session, 
			@RequestParam String noticeId, String emailTo, String emailCc, String emailBody, String url) {
		logger.info("Method : sendEmailNotice starts");
		byte[] encodeByte1 = Base64.getDecoder().decode(url.getBytes());
		 String attachedUrl = (new String(encodeByte1)); 
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
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		try {
			jsonResponse = restClient.getForObject(env.getMasterUrl() + "send-email-notice?noticeId=" + noticeId
					+ "&emailTo=" + emailTo + "&emailCc=" + emailCc + "&emailBody=" + emailBody + "&organization="
					+ organization + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		System.out.println("jsonResponse===++"+jsonResponse);
		ObjectMapper objectMapper = new ObjectMapper();
		String mailDetailsString = jsonResponse.getBody().toString();
		Map<String, Object> hashMapObject = null;
		try {
			List<Map<String, Object>> list = objectMapper.readValue(mailDetailsString,new TypeReference<List<Map<String, Object>>>() {});
			hashMapObject = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!hashMapObject.isEmpty()) {
			jsonResponse.setMessage("Success");
			String subject = hashMapObject.get("noticeSubject").toString();
			String message = hashMapObject.get("mailBody").toString();
			String add = hashMapObject.get("mailTo").toString();
			List<String> toAddress = new ArrayList<String>();
			if (add != null && !add.isEmpty()) {
				// Split the add string using spaces, commas, or a combination of both
				String[] addresses = add.split("[\\s,]+");
				// Add each split email address to the toAddress list
				for (String address : addresses) {
					toAddress.add(address.trim()); // Trim to remove any leading or trailing whitespace
				}
			}
			String addCc = hashMapObject.get("mailCC").toString();
			List<String> ccAddress = new ArrayList<String>();
			if (addCc != null && !addCc.isEmpty()) {
				// ccAddress = Arrays.asList(addCc.split(","));
				String[] addressescc = addCc.split("[\\s,]+");
				for (String addressc : addressescc) {
					ccAddress.add(addressc.trim()); // Trim to remove any leading or trailing whitespace
				}
			}
			List<String> bccAddress = new ArrayList<String>();
 
			String urlName = "Notice_" + noticeId;
 
			System.out.println("a===="+host+" , "+port+" , "+ username+" , "+ password+" , "+ toAddress+" , "+ ccAddress+" , "+
					subject+" , "+ message+" , "+ attachedUrl+" , "+ urlName+" , "+ bccAddress);
			try {
				EmailAttachmentSender.sendEmailWithAttachmentsURL(host, port, username, password, toAddress, ccAddress,
						subject, message, attachedUrl, urlName, bccAddress);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonResponse.setCode(jsonResponse.getMessage());
				jsonResponse.setMessage("Unsuccess");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonResponse.setCode(jsonResponse.getMessage());
				jsonResponse.setMessage("Unsuccess");
			}
		}
		logger.info("Method : sendEmailNotice ends");
		return jsonResponse;
	}
// delete letter
	@SuppressWarnings({ "unchecked" })
	@GetMapping("letter-management-delete-letter")
	public @ResponseBody JsonResponse<Object> deleteLetter(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteLetter starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-delete-letter?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteLetter ends");
		return res;
	}
}
