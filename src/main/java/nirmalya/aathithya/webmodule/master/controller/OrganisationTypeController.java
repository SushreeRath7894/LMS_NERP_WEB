package nirmalya.aathithya.webmodule.master.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.OrganisationTypeModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;

@Controller
@RequestMapping(value = "master")

public class OrganisationTypeController {

	Logger logger = LoggerFactory.getLogger(OrganisationTypeController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "organisation-type" })
	public String manageRoles(Model model, HttpSession session) {
		logger.info("Method : organisation starts");

		logger.info("Method : organisation starts");
		return "master/organisationType";
	}

	// organizer add

	@SuppressWarnings("unchecked")
	@PostMapping("organisation-type-master-add")
	public @ResponseBody JsonResponse<Object> addOrgType(@RequestBody OrganisationTypeModel organisationTypeModel,
			Model model, HttpSession session) {
		logger.info("Method :addOrgType starts");
		logger.info("@@@@@@@@@@@@@@@@" + organisationTypeModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		organisationTypeModel.setCreatedBy(userId);
		organisationTypeModel.setOrganization(organization);
		organisationTypeModel.setOrgDivision(orgDivision);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addOrganisationTypeMaster",
					organisationTypeModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addOrgType ends");
		return resp;
	}

	// View org

	@SuppressWarnings("unchecked")

	@GetMapping("organisation-type-master-view")
	public @ResponseBody List<OrganisationTypeModel> vieworgType(HttpSession session) {
		logger.info("Method : vieworgType starts");

		JsonResponse<List<OrganisationTypeModel>> resp = new JsonResponse<List<OrganisationTypeModel>>();
		List<OrganisationTypeModel> returnList = new ArrayList<OrganisationTypeModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-organisertypedetails", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("vieworgType" + returnList);
		logger.info("Method : vieworgType ends");
		return returnList;
	}

	// edit responsible
	@SuppressWarnings("unchecked")
	@PostMapping("organisation-type-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editOrganizerTypeMaster(@RequestBody String orgId,
			HttpSession session) {
		logger.info("Method : editOrganizerTypeMaster starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-editOrganizerMasterDetails?id=" + orgId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editOrganizerTypeMaster" + resp);
		logger.info("Method : editOrganizerTypeMaster starts");
		logger.info("resp" + resp);
		return resp;
	}
 
	@SuppressWarnings("unchecked")
	@GetMapping("organisation-type-master-delete")
	public @ResponseBody JsonResponse<Object> deleteOrganzierType(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteOrganzierType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteOrganzierTypeDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteOrganzierType ends");
		return resp;
	}

	// organizer add holiday

	@SuppressWarnings("unchecked")
	@PostMapping("/organisation-type-holiday-master-add")
	public @ResponseBody JsonResponse<Object> addOrgTypeHoliday(
			@RequestBody OrganisationTypeModel organisationTypeModel, Model model, HttpSession session) {
		logger.info("Method :addOrgTypeHoliday starts");
		logger.info("@@@@@@@@@@@@@@@@" + organisationTypeModel);
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
			e.printStackTrace();
		}
		organisationTypeModel.setCreatedBy(userId);
		organisationTypeModel.setOrganization(organization);
		organisationTypeModel.setOrgDivision(orgDivision);
		if (organisationTypeModel.getFromDate() != null && organisationTypeModel.getFromDate() != "") {
			organisationTypeModel
					.setFromDate(DateFormatter.inputDateFormat(organisationTypeModel.getFromDate(), dateFormat));
		}
		if (organisationTypeModel.getToDate() != null && organisationTypeModel.getToDate() != "") {
			organisationTypeModel
					.setToDate(DateFormatter.inputDateFormat(organisationTypeModel.getToDate(), dateFormat));
		}
		try {

			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addOrganisationTypeHolidayMaster",
					organisationTypeModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addOrgTypeHoliday ends");
		return resp;
	}

	// View org holiday

	@SuppressWarnings("unchecked")

	@GetMapping("/organisation-type-holiday-master-view")
	public @ResponseBody List<OrganisationTypeModel> vieworgTypeHoliday(HttpSession session) {

		logger.info("Method : vieworgTypeHoliday starts");

		JsonResponse<List<OrganisationTypeModel>> resp = new JsonResponse<List<OrganisationTypeModel>>();
		List<OrganisationTypeModel> organisationTypeModel = new ArrayList<OrganisationTypeModel>();
 
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "organisation-type-holiday-master-view",
					JsonResponse.class);
			organisationTypeModel = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("organisationTypeModel" + organisationTypeModel);
		logger.info("Method : vieworgTypeHoliday ends");
		return organisationTypeModel;
	}

	// delete org holiday
	@SuppressWarnings("unchecked")
	@GetMapping("/organisation-type-master-holiday-delete")
	public @ResponseBody JsonResponse<Object> deleteOrganzierTypeHoliday(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteOrganzierTypeHoliday starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteOrganzierTypeholidayDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteOrganzierTypeHoliday ends");
		return resp;
	}

	// bank add

	@SuppressWarnings("unchecked")
	@PostMapping("/organisation-type-bank-master-add")
	public @ResponseBody JsonResponse<Object> addBank(@RequestBody OrganisationTypeModel organisationTypeModel,
			Model model, HttpSession session) {
		logger.info("Method :addBank starts");
		logger.info("@@@@@@@@@@@@@@@@" + organisationTypeModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("organization==="+organization);
		organisationTypeModel.setCreatedBy(userId);
		organisationTypeModel.setOrganization(organization);
		organisationTypeModel.setOrgDivision(orgDivision);
		logger.info("@@@@@@@@@@@@@@@@" + organisationTypeModel);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addbankMaster", organisationTypeModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addBank ends");
		return resp;
	}

	// View bank

	@SuppressWarnings("unchecked")

	@GetMapping("/organisation-type-bank-master-view")
	public @ResponseBody List<OrganisationTypeModel> viewBank(HttpSession session) {

		logger.info("Method : viewBank starts");

		JsonResponse<List<OrganisationTypeModel>> resp = new JsonResponse<List<OrganisationTypeModel>>();
		List<OrganisationTypeModel> returnList = new ArrayList<OrganisationTypeModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-viewBankDetails", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewBank" + returnList);
		logger.info("Method : viewBank ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/organisation-type-bank-master-delete")
	public @ResponseBody JsonResponse<Object> deleteBankType(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteBankType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteBankTypeDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteBankType ends");
		return resp;
	}

	// Announcement Details Add

	@SuppressWarnings("unchecked")
	@PostMapping("/organisation-type-announcement-details-add")
	public @ResponseBody JsonResponse<Object> addannouncement(@RequestBody OrganisationTypeModel organisationTypeModel,
			Model model, HttpSession session) {
		logger.info("Method :addannouncement starts");
		logger.info("@@@@@@@@@@@@@@@@" + organisationTypeModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (RestClientException e) {
			e.printStackTrace();

		}

		organisationTypeModel.setCreatedByAnnouncement(userId);
		organisationTypeModel.setOrganization(organization);
		organisationTypeModel.setOrgDivision(orgDivision);
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addannouncementMaster", organisationTypeModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addannouncement ends");
		return resp;
	}

	// View Announcement

	@SuppressWarnings("unchecked")

	@GetMapping("/organisation-type-announcement-master-view")
	public @ResponseBody List<OrganisationTypeModel> viewannouncement(HttpSession session) {

		logger.info("Method : viewannouncement starts");

		JsonResponse<List<OrganisationTypeModel>> resp = new JsonResponse<List<OrganisationTypeModel>>();
		List<OrganisationTypeModel> returnList = new ArrayList<OrganisationTypeModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-announcementmaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewannouncement" + returnList);
		logger.info("Method : viewannouncement ends");
		return returnList;
	}

	// Delete Announcement

	@SuppressWarnings("unchecked")
	@GetMapping("/organisation-type-announcement-details-delete")
	public @ResponseBody JsonResponse<Object> deleteannouncement(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteannouncement starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-delete-announcementmaster?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteannouncement ends");
		return resp;
	}

	// Leave Policy Add

	@SuppressWarnings("unchecked")
	@PostMapping("/organisation-type-leave-policy-add")
	public @ResponseBody JsonResponse<Object> addleavepolicy(@RequestBody OrganisationTypeModel organisationTypeModel,
			Model model, HttpSession session) {
		logger.info("Method :addleavepolicy starts");
		
		logger.info("@@@@@@@@@@@@@@@@" + organisationTypeModel);
		
		MultipartFile inputFile = (MultipartFile) session.getAttribute("policy");
		logger.info("inputFile====="+inputFile);
		byte[] bytes;
		String imageName = null;
		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				logger.info("imageName===="+imageName);

				organisationTypeModel.setLeavePolicyDtls(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String dateFormat = "";
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (RestClientException e) {
			e.printStackTrace();

		}
		organisationTypeModel.setLeavePolicyCreatedBy(userId);
		organisationTypeModel.setLeavePolicyOrgName(orgName);
		organisationTypeModel.setLeavePolicyorgDivision(orgDivision);
		
		if (organisationTypeModel.getLeaveFromDate() != null && organisationTypeModel.getLeaveFromDate() != "") {
			organisationTypeModel
					.setLeaveFromDate(DateFormatter.inputDateFormat(organisationTypeModel.getLeaveFromDate(), dateFormat));
		}
		if (organisationTypeModel.getLeaveToDate() != null && organisationTypeModel.getLeaveToDate() != "") {
			organisationTypeModel
					.setLeaveToDate(DateFormatter.inputDateFormat(organisationTypeModel.getLeaveToDate(), dateFormat));
		}
		logger.info("organisationTypeModel add 11====="+organisationTypeModel);
		try {

			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addleavepolicyMaster", organisationTypeModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addleavepolicy ends");
		return resp;
	}
	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg") || ext.contentEquals("jpg")) {
					imageName = nowTime + ".jpg";
				}else if (ext.contentEquals("png")) {
					imageName = nowTime + ".png";
				}else if (ext.contentEquals("pdf")) {
					imageName = nowTime + ".pdf";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadOrganisationUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;

	}
	
	
	

	// View Leave Policy

	@SuppressWarnings("unchecked")

	@GetMapping("/organisation-type-leave-master-view")
	public @ResponseBody List<OrganisationTypeModel> viewleavepolicy(HttpSession session) {

		logger.info("Method : viewleavepolicy starts");

		JsonResponse<List<OrganisationTypeModel>> resp = new JsonResponse<List<OrganisationTypeModel>>();
		List<OrganisationTypeModel> returnList = new ArrayList<OrganisationTypeModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-leavepolicymaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewleavepolicy" + returnList);
		logger.info("Method : viewleavepolicy ends");
		return returnList;
	}

	//Edit Leave Policy
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("organisation-type-leave-policy-edit")
	public @ResponseBody JsonResponse<OrganisationTypeModel> editleavepolicy(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editleavepolicy starts");

		JsonResponse<OrganisationTypeModel> jsonResponse = new JsonResponse<OrganisationTypeModel>();
		logger.info(id);
		try {
			jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "editleavepolicy?id=" + id, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			OrganisationTypeModel OrganisationTypeModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<CustomerNewModel>() {
					});
		
		jsonResponse.setBody(OrganisationTypeModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editleavepolicy ends");
		return jsonResponse;
	}
	
	// Delete Leave Policy

		@SuppressWarnings("unchecked")
		@GetMapping("/organisation-type-leave-policy-delete")
		public @ResponseBody JsonResponse<Object> deleteleavepolicy(HttpSession session, @RequestParam String id) {

			logger.info("Method : deleteleavepolicy starts"+id);

			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-delete-leavepolicymaster?id=" + id,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = resp.getMessage();

			if (message != null && message != "") {

			} else {

				resp.setMessage("Success");
			}
			logger.info("resp" + resp);
			logger.info("Method : deleteleavepolicy ends");
			return resp;
		}
	
		@PostMapping("/organisation-type-leave-policy-upload-file")
		public @ResponseBody JsonResponse<Object> uploadFileSignature(@RequestParam("file") MultipartFile policy,
				HttpSession session) {
			logger.info("Method : organisation uploadimage controller  starts");

			JsonResponse<Object> response = new JsonResponse<Object>();

			try {
				response.setMessage(policy.getOriginalFilename());
				session.setAttribute("policy", policy);

			} catch (RestClientException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : organisation uploadimage controller ' ends");
			return response;
		}
	

}