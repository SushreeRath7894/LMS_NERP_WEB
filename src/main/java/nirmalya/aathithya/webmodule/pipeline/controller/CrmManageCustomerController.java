package nirmalya.aathithya.webmodule.pipeline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.DirecterManagerCrmReportModel;
import nirmalya.aathithya.webmodule.pipeline.model.RegistrationCrmReportModel;

@Controller
@RequestMapping(value = "pipeline")
public class CrmManageCustomerController {
	Logger logger = LoggerFactory.getLogger(CrmManageCustomerController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	/*************************** Customer Switch **********************************/

	// Summary
	@GetMapping("/crm-customer-switch")
	public String crmCustomerSwitchPage(Model model, HttpSession session) {

		logger.info("Method : crmCustomerSwitchPage starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		// Sales Team List

		try {
			DropDownModel[] salesTeamList = restTemplate
					.getForObject(env.getApiUrl() + "getSalesTeamLists?organization=" + organization + "&orgDivision="
							+ orgDivision + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> salesTeam = Arrays.asList(salesTeamList);
			model.addAttribute("salesTeam", salesTeam);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			logger.info(env.getPipeline());
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getSourceList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("customerList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			logger.info(env.getPipeline());
			DropDownModel[] status = restTemplate.getForObject(env.getPipeline() + "/getLeadStatusList",
					DropDownModel[].class);

			List<DropDownModel> statusList = Arrays.asList(status);
			model.addAttribute("statusList", statusList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getLeadList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("leadList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getindustrylist",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("industryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : crmCustomerSwitchPage ends");
		return "pipeline/crmCustomerSwitch.html";
	}

	// view Customer details for switch

	@SuppressWarnings("unchecked")
	@GetMapping("/crm-customer-switch-customerDetails")
	public @ResponseBody List<RegistrationCrmReportModel> viewCustomerDetails(HttpSession session) {
		logger.info("Method : viewCustomerDetails");

		JsonResponse<List<RegistrationCrmReportModel>> resp = new JsonResponse<List<RegistrationCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "getClientDetailsReport?createdby=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewCustomerDetails ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}

	// customer Switch Apply
	@SuppressWarnings("unchecked")
	@GetMapping("crm-customer-switch-apply")
	public @ResponseBody JsonResponse<Object> switchCustomerApply(@RequestParam String customer, String executive,
			Model model, HttpSession session) {
		logger.info("Method : switchCustomerApply function starts");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getApiUrl() + "rest-switchCustomerApply?customer=" + customer
					+ "&executive=" + executive + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = res.getMessage();
		if (message != null && message != "") {
		} else {
			res.setMessage("Success");
		}
		logger.info("Method : clearIMEI function Ends");

		logger.info("switchCustomerApply" + res);
		return res;
	}

	// edit

	@SuppressWarnings("unchecked")
	@GetMapping("crm-customer-switch-edit")
	public @ResponseBody JsonResponse<RegistrationCrmReportModel> customerSwitchEdit(HttpSession session,
			@RequestParam String userid) {

		logger.info("Method : customerSwitchEdit starts");

		JsonResponse<RegistrationCrmReportModel> response = new JsonResponse<RegistrationCrmReportModel>();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			response = restTemplate.getForObject(env.getApiUrl() + "getClientDetailsEdit-web?id=" + userid
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("customerSwitchEdit EDITTTT" + response);
		logger.info("Method : customerSwitchEdit ends");
		return response;
	}

	/*
	 * delete Customer
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("crm-customer-switch-deleteCustomer")
	public @ResponseBody JsonResponse<RegistrationCrmReportModel> deleteCustomer(Model model,
			@RequestParam String deleteId, HttpSession session) {
		logger.info("Method : deleteCustomer starts");
		logger.info("@@@@@" + deleteId);
		JsonResponse<RegistrationCrmReportModel> resp = new JsonResponse<RegistrationCrmReportModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(
					env.getApiUrl() + "deleteCustomer?deleteId=" + deleteId + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :  deleteCustomer ends");
		logger.info("resp" + resp);
		return resp;
	}
	/*
	 * add customer
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "crm-customer-switch-addCustomerDetails" })
	public @ResponseBody JsonResponse<Object> addCustomerDetails(Model model, HttpSession session,
			@RequestBody RegistrationCrmReportModel data) {
		logger.info("Method : addCustomerDetails starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		MultipartFile inputFile = (MultipartFile) session.getAttribute("employeePFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				logger.info(imageName);

				data.setProfileImg(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		data.setCreatedBy(userId);
		data.setOrgName(organization);
		data.setOrgDiv(orgDivision);
		logger.info("Method : addCustomerDetails starts" + data);
		try {
			res = restTemplate.postForObject(env.getApiUrl() + "addCustomerDetails", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode() == "Success") {
			session.removeAttribute("employeePFile");
		}
		logger.info("Method : addCustomerDetails ends");
		logger.info("adddd=======" + res);
		return res;
	}

	/******************* Decision Maker ****************************/
	// edit

	@SuppressWarnings("unchecked")
	@GetMapping("crm-customer-switch-editDecisionMaker")
	public @ResponseBody JsonResponse<DirecterManagerCrmReportModel> editDecisionMaker(@RequestParam String dmId,
			HttpSession session) {

		logger.info("Method : editDecisionMaker starts");
		JsonResponse<DirecterManagerCrmReportModel> jsonResponse = new JsonResponse<DirecterManagerCrmReportModel>();
		logger.info("id====" + dmId);
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			jsonResponse = restTemplate.getForObject(
					env.getApiUrl() + "rest-editDecisionMaker?id=" + dmId + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : editDecisionMaker ends");
		logger.info("editDecisionMaker=====" + jsonResponse);
		return jsonResponse;
	}
	/*
	 * add
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "crm-customer-switch-addDecisionMaker" })
	public @ResponseBody JsonResponse<Object> addDecisionMaker(Model model, HttpSession session,
			@RequestBody DirecterManagerCrmReportModel data) {
		logger.info("Method : addDecisionMaker starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		data.setOrg(organization);
		data.setOrgDiv(orgDivision);
		logger.info("Method : addDecisionMaker starts" + data);
		try {
			res = restTemplate.postForObject(env.getApiUrl() + "addDecisionMaker", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : addDecisionMaker ends");
		logger.info("adddd=======" + res);
		return res;
	}

	/*
	 * delete Decision Maker
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("crm-customer-switch-deleteDecisionMaker")
	public @ResponseBody JsonResponse<DirecterManagerCrmReportModel> deleteDecisionMaker(Model model,
			@RequestParam String deleteId, HttpSession session) {
		logger.info("Method : deleteDecisionMaker starts");
		logger.info("@@@@@" + deleteId);
		JsonResponse<DirecterManagerCrmReportModel> resp = new JsonResponse<DirecterManagerCrmReportModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(
					env.getApiUrl() + "deleteDecisionMaker?deleteId=" + deleteId + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :  deleteDecisionMaker ends");
		logger.info("resp" + resp);
		return resp;
	}

	@PostMapping("/crm-customer-switch-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : CRM uploadimage controller starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("employeePFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : CRM uploadimage controller ends");
		return response;
	}

	@PostMapping("/crm-customer-switch-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile employee uploadimage controller starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.removeAttribute("employeePFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile employee uploadimage controller ends");
		return response;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
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

			Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}

}
