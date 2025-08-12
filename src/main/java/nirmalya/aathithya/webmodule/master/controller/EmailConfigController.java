package nirmalya.aathithya.webmodule.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmailConfigModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class EmailConfigController {

	Logger logger = LoggerFactory.getLogger(EmailConfigController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	EmailConfigController emailController;

	@GetMapping(value = { "email-configuration" })
	public String emailConfiguration(Model model, HttpSession session) {
		logger.info("Method : emailConfiguration starts");
		
		logger.info("Method : emailConfiguration ends");
		return "master/emailConfiguration";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/email-configuration-get-total")
	public @ResponseBody JsonResponse<List<EmailConfigModel>> getTotalMailConenction(HttpSession session) {
		logger.info("Method : getTotalMailConenction starts");
		
		JsonResponse<List<EmailConfigModel>> resp = new JsonResponse<List<EmailConfigModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getTotalMailConenction",
					JsonResponse.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : getTotalMailConenction starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/email-configuration-save")
	public @ResponseBody JsonResponse<Object> saveEmailConfiguration(@RequestBody EmailConfigModel emailDtls, HttpSession session) {
		logger.info("Method : saveEmailConfiguration starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		emailDtls.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveEmailConfiguration" ,emailDtls,
					JsonResponse.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : saveEmailConfiguration starts");
		return resp;
	}
	
	@PostMapping("/email-configuration-test-mail")
	public @ResponseBody JsonResponse<Object> testMailConnection(@RequestBody EmailConfigModel emailDtls, HttpSession session) {
		logger.info("Method : testMailConnection starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String toUsers = emailDtls.getToUsers();
		toUsers = toUsers.replaceAll("\\s",""); 
		
		String toArray[] = toUsers.split(",");
		
		List<String> toEmailIds = new ArrayList<String>();
		List<String> ccUsers = new ArrayList<String>();
		String[] attachFiles = null;
		
		for(int i = 0; i < toArray.length; i++) {
			toEmailIds.add(toArray[i]);
		}
		
		try {
			EmailAttachmentSender.sendEmailWithAttachments(emailDtls.getHostUrl(), emailDtls.getHostPort(), emailDtls.getEmailId(),
					emailDtls.getEmailPassword(), toEmailIds, ccUsers, emailDtls.getSubject(), emailDtls.getMailBody(), attachFiles);
		} catch (Exception ex) {
			resp.setMessage("Mail Not Sent....");
			ex.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : testMailConnection starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/email-configuration-edit")
	public @ResponseBody JsonResponse<EmailConfigModel> editEmailConfig(@RequestBody String emailDtls, HttpSession session) {
		logger.info("Method : editEmailConfig starts");
		
		JsonResponse<EmailConfigModel> resp = new JsonResponse<EmailConfigModel>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editEmailConfig?id="+emailDtls,
					JsonResponse.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : editEmailConfig starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/email-configuration-delete")
	public @ResponseBody JsonResponse<Object> deleteEmailConfig(@RequestBody String emailDtls, HttpSession session) {
		logger.info("Method : deleteEmailConfig starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteEmailConfig?id="+emailDtls,
					JsonResponse.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : deleteEmailConfig starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public EmailConfigModel getMailConfigurationDtls() {
		logger.info("Method : getMailConfigurationDtls starts");
		
		JsonResponse<EmailConfigModel> resp = new JsonResponse<EmailConfigModel>();
		
		EmailConfigModel emailDtls = new EmailConfigModel();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editEmailConfig",
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			emailDtls = mapper.convertValue(resp.getBody(),
					new TypeReference<EmailConfigModel>() {
					});
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getMailConfigurationDtls ends");
		return emailDtls;
	}
}
