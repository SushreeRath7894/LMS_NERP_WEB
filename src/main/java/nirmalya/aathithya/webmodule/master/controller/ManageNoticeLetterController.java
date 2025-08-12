package nirmalya.aathithya.webmodule.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;


@Controller
@RequestMapping(value = { "master/" })
public class ManageNoticeLetterController {

	Logger logger = LoggerFactory.getLogger(ManageNoticeLetterController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping(value = { "view-notice-letter" })
	public String viewNoticeLetter(Model model, HttpSession session) {
		logger.info("Method : viewNoticeLetter starts");

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
		} /*
			 * 
			 * try { DropDownModel[] noticeType = restClient.getForObject(env.getMasterUrl()
			 * + "get-all-notice-type?organization=" + organization + "&orgDivision=" +
			 * orgDivision, DropDownModel[].class); List<DropDownModel> noticeTypeList =
			 * Arrays.asList(noticeType);
			 * 
			 * model.addAttribute("noticeType", noticeTypeList);
			 * 
			 * } catch (RestClientException e) { e.printStackTrace(); }
			 */

		model.addAttribute("userId", userId);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		model.addAttribute("logo", logo);
		model.addAttribute("baseURL", baseURL);

		logger.info("Method : viewNoticeLetter ends");
		return "master/view-notice-letter.html";
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-notice-letter-alldata")
	public @ResponseBody JsonResponse<Object> viewAllNoticeLetter(HttpSession session) {
		logger.info("Method : viewAllNoticeLetter starts");

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
			res = restClient.getForObject(env.getMasterUrl() + "rest-viewAllNoticeLetter?org=" + organization + "&orgDiv="
					+ orgDivision + "&userId=" + userId , JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewAllNoticeLetter ends");
		return res;

	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-notice-letter-pdf-notice")
	public void getNoticePdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("noticeId") String encodedParam1, @RequestParam("organization") String encodedParam2,
			@RequestParam("orgDivision") String encodedParam3,  @RequestParam("logo") String encodedParam5,  @RequestParam("userId") String encodedParam4) {

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
		
		System.out.println("noticeId>>"+noticeId);
		try {
			jsonResponse = restClient
					.getForObject(
							env.getMasterUrl() + "get-pdf-details-notice?id=" + noticeId + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&userId=" + userId,
							JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		System.out.println("jsonResponse>>"+jsonResponse);

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
		URL getUrl = null;
		try {
			getUrl = new URL(logo);
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}

		String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		data.put("noticeId", hashMapObject.get("noticeId"));
		data.put("publishedBy", hashMapObject.get("publishedBy"));
		data.put("publishedByName", hashMapObject.get("publishedByName"));
		data.put("noticeSubject", hashMapObject.get("noticeSubject"));
		data.put("publishedTo", hashMapObject.get("publishedTo"));
		data.put("publishDate", hashMapObject.get("publishDate"));
		data.put("noticeContent", hashMapObject.get("noticeContent"));
		data.put("publishedOn", hashMapObject.get("publishedOn"));
		data.put("orgPhone", hashMapObject.get("orgPhone"));
		data.put("orgEmail", hashMapObject.get("orgEmail"));
		data.put("orgAddress", hashMapObject.get("orgAddress"));
		data.put("organization", organization);
		data.put("division", orgDivision);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=Notice_" + noticeId + ".pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("notice/notice-pdf-download", data);
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
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-notice-letter-pdf-letter")
	public void getLetterPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("noticeId") String encodedParam1, @RequestParam("organization") String encodedParam2,
			@RequestParam("orgDivision") String encodedParam3,  @RequestParam("logo") String encodedParam5,  @RequestParam("userId") String encodedParam4) {

		logger.info("Method: getLetterPdfDetails starts");

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
			jsonResponse = restClient
					.getForObject(
							env.getMasterUrl() + "get-notice-pdfDetails?id=" + noticeId + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&userId=" + userId,
							JsonResponse.class);
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
		URL getUrl = null;
		try {
			getUrl = new URL(logo);
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}
		
		String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		data.put("logo", "data:image/png;base64," + encodedLogoUrl);
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

		logger.info("Method: getLetterPdfDetails ends");
		// return "master/notice-pdf-download";
	}

	
}
