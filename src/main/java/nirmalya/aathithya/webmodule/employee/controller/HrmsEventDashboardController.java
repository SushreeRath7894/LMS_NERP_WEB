package nirmalya.aathithya.webmodule.employee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.MailService;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.HrmsEventDashboardModel;

@Controller
@RequestMapping(value = { "employee/" })

public class HrmsEventDashboardController {

	Logger logger = LoggerFactory.getLogger(HrmsEventDashboardController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@Autowired
	MailService mailService;

	@GetMapping("/hrms-event-dashboard")

	public String viewDashboard(Model model, HttpSession session) {
		logger.info("Method :viewDashboard starts");
 
		logger.info("Method : viewDashboard ends");
		return "employee/hrmsEventDashboard";
	}

	// View About Organization
	@SuppressWarnings("unchecked")
	@GetMapping("hrms-event-dashboard-view-about-organization")
	public @ResponseBody JsonResponse<List<HrmsEventDashboardModel>> viewAboutOrganization(HttpSession session) {

		logger.info("Method : viewAboutOrganization starts");

		JsonResponse<List<HrmsEventDashboardModel>> response = new JsonResponse<List<HrmsEventDashboardModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			response = restTemplate.getForObject(env.getEmployeeUrl() + "rest-viewAboutOrganization?organization="+organization+"&orgDivision="+orgDivision,
					JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}
 
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : viewAboutOrganization ends" + response);
		return response;
	}

	// FOR View Birthdays

	@SuppressWarnings("unchecked")
	@GetMapping("hrms-event-dashboard-view-birthday")
	public @ResponseBody List<HrmsEventDashboardModel> getTotalBirthday(HttpSession session) {

		logger.info("Method : getTotalBirthday starts");

		JsonResponse<List<HrmsEventDashboardModel>> response = new JsonResponse<List<HrmsEventDashboardModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalBirthday?organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (
		RestClientException e) {
			e.printStackTrace();
		}
 
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}
		logger.info("Method : getTotalBirthday ends");
		logger.info("ddddd" + response);
		return response.getBody();
	}

	// FOR View Marraige Anniversary

	@SuppressWarnings("unchecked")
	@GetMapping("hrms-event-dashboard-view-mrg-anniversry")
	public @ResponseBody List<HrmsEventDashboardModel> getTotalMrgAnniversary(HttpSession session) {

		logger.info("Method : getTotalMrgAnniversary starts");

		JsonResponse<List<HrmsEventDashboardModel>> response = new JsonResponse<List<HrmsEventDashboardModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMrgAnniversary?organization="+organization+"&orgDivision="+orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}
		logger.info("Method : getTotalMrgAnniversary ends");
		logger.info("ddddd" + response);
		return response.getBody();
	}

	// FOR View Service Anniversary

	@SuppressWarnings("unchecked")
	@GetMapping("hrms-event-dashboard-view-service-anniversry")
	public @ResponseBody List<HrmsEventDashboardModel> getTotalServiceAnniversary(HttpSession session) {

		logger.info("Method : getTotalServiceAnniversary starts");
		JsonResponse<List<HrmsEventDashboardModel>> response = new JsonResponse<List<HrmsEventDashboardModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalServiceAnniversary?organization="+organization+"&orgDivision="+orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}
		logger.info("Method : getTotalServiceAnniversary ends");
		logger.info("ddddd" + response);
		return response.getBody();
	}

	// View Announcement

	@SuppressWarnings("unchecked")
	@GetMapping("hrms-event-dashboard-view-announcement")
	public @ResponseBody List<HrmsEventDashboardModel> getTotalAnnouncement(HttpSession session) {
		logger.info("Method : getTotalAnnouncement starts");
		JsonResponse<List<HrmsEventDashboardModel>> response = new JsonResponse<List<HrmsEventDashboardModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			response = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalAnnouncement?organization="+organization+"&orgDivision="+orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}
		logger.info("Method : getTotalAnnouncement ends");
		logger.info("ddddd" + response);
		return response.getBody();
	}

	 
		// View Open  Position

		@SuppressWarnings("unchecked")
		@GetMapping("hrms-event-dashboard-view-open-position")
		public @ResponseBody List<HrmsEventDashboardModel> getOpenPositionList(HttpSession session) {

			logger.info("Method : getOpenPositionList starts");
			JsonResponse<List<HrmsEventDashboardModel>> response = new JsonResponse<List<HrmsEventDashboardModel>>();
			String organization=""; 
			String orgDivision="";
			try {
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				response = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getOpenPositionList?organization="+organization+"&orgDivision="+orgDivision,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			if (response.getMessage() != null && response.getMessage() != "") {
				response.setCode(response.getMessage());
				response.setMessage("Unsuccess");

			} else {
				response.setMessage("Success");
			}
			logger.info("Method : getOpenPositionList ends");
			logger.info("gggggggggggg" + response);
			return response.getBody();
		}
		// View Leave Policy

		@SuppressWarnings("unchecked")
		@GetMapping("hrms-event-dashboard-view-leave-policy")
		public @ResponseBody List<HrmsEventDashboardModel> getLeavePolicyList(HttpSession session) {

			logger.info("Method : getLeavePolicyList starts");
			JsonResponse<List<HrmsEventDashboardModel>> response = new JsonResponse<List<HrmsEventDashboardModel>>();
			String organization=""; 
			String orgDivision="";
			try {
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				response = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getLeavePolicyList?organization="+organization+"&orgDivision="+orgDivision,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (response.getMessage() != null && response.getMessage() != "") {
				response.setCode(response.getMessage());
				response.setMessage("Unsuccess");

			} else {
				response.setMessage("Success");
			}
			logger.info("Method : getLeavePolicyList ends");
			logger.info("gggggggggggg" + response);
			return response.getBody();
		}
	@SuppressWarnings("unchecked")
	@PostMapping("hrms-event-dashboard-send-birthday-wish")
	public @ResponseBody JsonResponse<Object> sendBirthdayWishMail(Model model, HttpSession session,
			@RequestBody DropDownModel DropDownModel) {

		logger.info("Method : sendBirthdayWishMail starts");
		logger.info("DropDownModel===" + DropDownModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userEmail = "";
		String userName = "";
		String orgDivision = "";
		String orgLogo = "";
		String subjects = "Birthday Wish";
		try {
			userEmail = (String) session.getAttribute("USER_EMAIL");
			userName = (String) session.getAttribute("USER_NAME");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			orgLogo = (String) session.getAttribute("ORGANIZATION_LOGO");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String nameEn = Base64.getEncoder().encodeToString((DropDownModel.getName()).getBytes());
		String orgDivisionEn = Base64.getEncoder().encodeToString(orgDivision.getBytes());
		String orgLogoEn = Base64.getEncoder().encodeToString(orgLogo.getBytes());

		String Url = env.getBaseURL() + "/employee/birthday-wish?name=" + nameEn + "&orgDivision=" + orgDivisionEn
				+ "&orgLogo=" + orgLogoEn;

		String tolist = DropDownModel.getKey();
		String str[] = tolist.split(",");

		logger.info("str " + Arrays.toString(str));

		ArrayList<String> mylist = new ArrayList<String>();
		mylist.addAll(Arrays.asList(str));

		List<String> toAddress = new ArrayList<String>();

		String subject = "From :-" + userName + System.lineSeparator() + "Subject:-" + subjects;
		String message1 = DropDownModel.getCode() + System.lineSeparator() + Url;

		String sub = "";
		String to = "";
		String msg = "";

		for (int i = 0; i < mylist.size(); i++) {
			try {
				resp = restTemplate.getForObject(env.getEmployeeUrl() + "get_maildetails?mylist=" + mylist.get(i),
						JsonResponse.class);
				logger.info("emails:- " + (String) resp.getBody());
				toAddress.add((String) resp.getBody());
			} catch (RestClientException e) {
				e.printStackTrace();
			}
		}
		logger.info("userEmail===" + userEmail);
		logger.info("to===" + to);
		logger.info("sub===" + sub);
		logger.info("msg===" + msg);
		mailService.sendEmail(to, sub, msg);
		/*
		 * logger.info("message1 " + message1);
		 * EmailAttachmentSender.sendEmailWithAttachments("smtp.gmail.com", "587",
		 * "amarendramaity101@gmail.com ", "Nirmalya@123", toAddress, null, subject,
		 * message1, null);
		 */

		logger.info("Method : sendBirthdayWishMail ends");
		logger.info("sendBirthdayWishMail===" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("hrms-event-dashboard-send-marriageAnni-wish")
	public @ResponseBody JsonResponse<Object> sendmarriageAnniMail(Model model, HttpSession session,
			@RequestBody DropDownModel DropDownModel) {

		logger.info("Method : sendmarriageAnniMail starts");
		logger.info("DropDownModel===" + DropDownModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userEmail = "";
		String userName = "";

		String subjects = "Marriage Anniversary Wish";
		try {
			userEmail = (String) session.getAttribute("USER_EMAIL");
			userName = (String) session.getAttribute("USER_NAME");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String tolist = DropDownModel.getKey();
		String str[] = tolist.split(",");

		logger.info("str " + Arrays.toString(str));

		ArrayList<String> mylist = new ArrayList<String>();
		mylist.addAll(Arrays.asList(str));

		List<String> toAddress = new ArrayList<String>();

		String subject = "From :-" + userName + System.lineSeparator() + "Subject:-" + subjects;
		String message1 = DropDownModel.getCode();

		for (int i = 0; i < mylist.size(); i++) {
			try {
				resp = restTemplate.getForObject(env.getEmployeeUrl() + "get_maildetails?mylist=" + mylist.get(i),
						JsonResponse.class);
				logger.info("emails:- " + (String) resp.getBody());
				toAddress.add((String) resp.getBody());
			} catch (RestClientException e) {
				e.printStackTrace();
			}
		}
		logger.info("userEmail===" + userEmail);
		try {
			logger.info("message1 " + message1);
			EmailAttachmentSender.sendEmailWithAttachments("smtp.gmail.com", "587", "amarendramaity101@gmail.com",
					"Nirmalya@123", toAddress, null, subject, message1, null);

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { resp = restTemplate.postForObject(env.getEmployeeUrl()+
		 * "rest-sendMail", DropDownModel, JsonResponse.class); } catch
		 * (RestClientException e) { e.printStackTrace(); }
		 * 
		 * if (resp.getMessage() != "" && resp.getMessage() != null) {
		 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
		 * resp.setMessage("Success"); }
		 */

		logger.info("Method : sendmarriageAnniMail ends");
		logger.info("sendmarriageAnniMail===" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("hrms-event-dashboard-send-serviceAnni-wish")
	public @ResponseBody JsonResponse<Object> sendMail(Model model, HttpSession session,
			@RequestBody DropDownModel DropDownModel) {

		logger.info("Method : sendMail starts");
		logger.info("DropDownModel===" + DropDownModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userEmail = "";
		String userName = "";

		String subjects = "Service Anniversary Wish";
		try {
			userEmail = (String) session.getAttribute("USER_EMAIL");
			userName = (String) session.getAttribute("USER_NAME");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String tolist = DropDownModel.getKey();
		String str[] = tolist.split(",");

		logger.info("str " + Arrays.toString(str));

		ArrayList<String> mylist = new ArrayList<String>();
		mylist.addAll(Arrays.asList(str));

		List<String> toAddress = new ArrayList<String>();

		String subject = "From :-" + userName + System.lineSeparator() + "Subject:-" + subjects;
		String message1 = DropDownModel.getCode();

		for (int i = 0; i < mylist.size(); i++) {
			try {
				resp = restTemplate.getForObject(env.getEmployeeUrl() + "get_maildetails?mylist=" + mylist.get(i),
						JsonResponse.class);
				logger.info("emails:- " + (String) resp.getBody());
				toAddress.add((String) resp.getBody());
			} catch (RestClientException e) {
				e.printStackTrace();
			}
		}
		logger.info("userEmail===" + userEmail);
		try {
			logger.info("message1 " + message1);
			EmailAttachmentSender.sendEmailWithAttachments("smtp.gmail.com", "587", "amarendramaity101@gmail.com",
					"Nirmalya@123", toAddress, null, subject, message1, null);

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { resp = restTemplate.postForObject(env.getEmployeeUrl()+
		 * "rest-sendMail", DropDownModel, JsonResponse.class); } catch
		 * (RestClientException e) { e.printStackTrace(); }
		 * 
		 * if (resp.getMessage() != "" && resp.getMessage() != null) {
		 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
		 * resp.setMessage("Success"); }
		 */

		logger.info("Method : sendMail ends");
		logger.info("sxdcdx" + resp);
		return resp;
	}

	/***************** Birthday pdf download ********************/

	@GetMapping(value = { "/birthday-wish" })
	public void birthDay(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("name") String encodedParam1, @RequestParam("orgDivision") String encodedParam2,
			@RequestParam("orgLogo") String encodedParam3) {
		logger.info("Method : birthDay starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String name = (new String(encodeByte1));
		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String orgDivision = (new String(encodeByte2));

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String orgLogo = (new String(encodeByte3));

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", name);
		data.put("orgDivision", orgDivision);
		data.put("orgLogo", orgLogo);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=birthdaywish.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("employee/birtdayWishTemplate", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : birthDay ends");
	}

	// View Public Holiday

	@SuppressWarnings("unchecked")
	@GetMapping("hrms-event-dashboard-view-public-holiday")
	public @ResponseBody List<HrmsEventDashboardModel> getTotalPublicHoliday(HttpSession session) {

		logger.info("Method : getTotalPublicHoliday starts");
		JsonResponse<List<HrmsEventDashboardModel>> response = new JsonResponse<List<HrmsEventDashboardModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalPublicHoliday?organization="+organization+"&orgDivision="+orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}
		logger.info("Method : getTotalPublicHoliday ends");
		logger.info("gggggggggggg" + response);
		return response.getBody();
	}

}
