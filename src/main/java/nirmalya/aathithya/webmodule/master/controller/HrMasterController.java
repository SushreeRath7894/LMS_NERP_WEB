package nirmalya.aathithya.webmodule.master.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.HrMasterModel;
import com.fasterxml.jackson.core.type.TypeReference;

@Controller
@RequestMapping(value = "configuration")
public class HrMasterController {

	Logger logger = LoggerFactory.getLogger(HrMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/referenceHr")
	public String displayHrPage(Model model, HttpSession session) {
		logger.info("Method : hr starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] departmentType = restTemplate.getForObject(env.getMasterUrl()
					+ "getDepartmentTypeForShiftType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> department = Arrays.asList(departmentType);
			model.addAttribute("department", department);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : hr ends");
		return "master/referenceHr";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/referenceHr-add-job-type")
	public @ResponseBody JsonResponse<Object> addJobMaster(@RequestBody HrMasterModel job, HttpSession session) {
		logger.info("Method : addJobMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		job.setOrganization(organization);
		job.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		job.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-job-type", job, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addJobMaster starts");
		// logger.info(resp);

		return resp;
	}

//        View page for Hr job types

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-view-job-type")
	public @ResponseBody List<HrMasterModel> viewJobType(HttpSession session) {
		logger.info("Method : viewJobType starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> resp = new JsonResponse<List<HrMasterModel>>();
		List<HrMasterModel> returnList = new ArrayList<HrMasterModel>();

		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "viewJobType?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		// logger.info(returnList);
		logger.info("Method : viewJobType ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-job-type-delete")
	public @ResponseBody JsonResponse<Object> deleteJobType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteJobType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteJobType?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteJobType ends");
		return resp;
	}

	/*
	 * --------------------------Reference HR WORK HOURS
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-work-hour")
	public @ResponseBody JsonResponse<Object> addWorkHour(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method : add work hour starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setWorkCreatedBy(userId);

		try {
			hrWorkHoursModel.setWorkUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-work-hour", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : add work hour ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-work-hour")
	public @ResponseBody List<HrMasterModel> viewWorkHour(HttpSession session) {

		logger.info("Method : view work hour starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "rest-view-work-hour?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<HrMasterModel> addreq = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<HrMasterModel>>() {
				});
		String name = "";
		for (HrMasterModel m : addreq) {
			name = m.getWorkFromTime() + "-" + m.getWorkToTime() + " (" + m.getWorkFromDate() + "-" + m.getWorkToDate()
					+ ")";
			m.setWorkhourName(name);
			// logger.info(m.getWorkhourName());
		}

		jsonResponse.setBody(addreq);

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : view work hour ends");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-work-hour-delete")
	public @ResponseBody JsonResponse<Object> deleteWorkHour(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteWorkHour starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteWorkHour?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteWorkHour ends");
		return resp;
	}

	/*
	 * --------------------------Reference Education
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-education-level")
	public @ResponseBody JsonResponse<Object> addEducation(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addEducation starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setEducationCreatedBy(userId);
		try {
			hrWorkHoursModel.setEducationupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addEducation", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addEducation ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-education-level")
	public @ResponseBody List<HrMasterModel> viewEducation(HttpSession session) {

		logger.info("Method : viewEducation");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewEducation?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewEducation");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-edu-delete")
	public @ResponseBody JsonResponse<Object> deleteEducation(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteEducation starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteEducation?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteEducation ends");
		return resp;
	}

	/*
	 * --------------------------Reference Job Band
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-job-band-level")
	public @ResponseBody JsonResponse<Object> addJobBand(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addJobBand starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setJobBandCreatedBy(userId);
		try {
			hrWorkHoursModel.setJobBandupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addJobBand", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addJobBand ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-job-band-level")
	public @ResponseBody List<HrMasterModel> viewJobBand(HttpSession session) {

		logger.info("Method : viewEducation");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewJobBand?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewJobBand");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-job-band-delete")
	public @ResponseBody JsonResponse<Object> deleteJobBand(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteJobBand starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteJobBand?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteJobBand ends");
		return resp;
	}

	/*
	 * --------------------------Reference Benefits
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-benefits")
	public @ResponseBody JsonResponse<Object> addBenefits(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addBenefits starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setBenifitCreatedBy(userId);
		try {
			hrWorkHoursModel.setBenifitupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addBenefits", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addBenefits ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-benefits")
	public @ResponseBody List<HrMasterModel> viewBenefits(HttpSession session) {

		logger.info("Method : viewBenefits");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewBenefits?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewBenefits");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-benefit-delete")
	public @ResponseBody JsonResponse<Object> deleteBenefits(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteBenefits starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteBenefits?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteBenefits ends");
		return resp;
	}

	/*
	 * --------------------------Address Type
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-address")
	public @ResponseBody JsonResponse<Object> addAddress(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addAddress starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setAddressCreatedBy(userId);
		try {
			hrWorkHoursModel.setAddressupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addAddress", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addAddress ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-address")
	public @ResponseBody List<HrMasterModel> viewAddress(HttpSession session) {

		logger.info("Method : viewAddress");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewAddress?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewAddress");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-address-delete")
	public @ResponseBody JsonResponse<Object> deleteAddress(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteAddress starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteAddress?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteAddress ends");
		return resp;
	}

	/*
	 * --------------------------Blood Group Type
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-bloodGroup")
	public @ResponseBody JsonResponse<Object> addBloodgroup(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addBloodgroup starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setBloodGroupCreatedBy(userId);
		try {
			hrWorkHoursModel.setBloodGroupsupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addBloodgroup", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addBloodgroup ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-bloodGroup")
	public @ResponseBody List<HrMasterModel> viewBloodGroup(HttpSession session) {

		logger.info("Method : viewBloodGroup");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewBloodGroup?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewBloodGroup");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-bloodGroup-delete")
	public @ResponseBody JsonResponse<Object> deleteBloodGroup(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteBloodGroup starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteBloodGroup?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteBloodGroup ends");
		return resp;
	}

	/*
	 * --------------------------Shift Type
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-shift")
	public @ResponseBody JsonResponse<Object> addShift(@RequestBody HrMasterModel shiftModel, Model model,
			HttpSession session) {
		logger.info("Method : addShift starts");
		// logger.info("aaaa" + shiftModel);

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		shiftModel.setOrganization(organization);
		shiftModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		shiftModel.setShiftCreatedBy(userId);

		try {
			shiftModel.setShiftUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-shift", shiftModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}
		// logger.info(jsonResponse);
		logger.info("Method : addShift ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-shift")
	public @ResponseBody List<HrMasterModel> viewShift(HttpSession session) {

		logger.info("Method : viewShift Starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewShift?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewShift Ends");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-shift-delete")
	public @ResponseBody JsonResponse<Object> deleteShift(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteShift starts");

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
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteshift?id=" + id + "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteShift ends");
		return resp;
	}

	/*
	 * --------------------------Marital Type
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-marital")
	public @ResponseBody JsonResponse<Object> addMarital(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addMarital starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setMaritalCreatedBy(userId);
		try {
			hrWorkHoursModel.setMaritalupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addMarital", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addMarital ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-marital")
	public @ResponseBody List<HrMasterModel> viewMarital(HttpSession session) {

		logger.info("Method : viewMarital");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewMarital?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewMarital");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-marital-delete")
	public @ResponseBody JsonResponse<Object> deleteMarital(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteMarital starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteMarital?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteMarital ends");
		return resp;
	}

	/*
	 * --------------------------Document Type
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-document")
	public @ResponseBody JsonResponse<Object> addDocument(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addDocument starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setDocumentCreatedBy(userId);
		try {
			hrWorkHoursModel.setDocumentupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addDocument", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addDocument ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-document")
	public @ResponseBody List<HrMasterModel> viewDocument(HttpSession session) {

		logger.info("Method : viewDocument");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewDocument?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewDocument");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-document-delete")
	public @ResponseBody JsonResponse<Object> deleteDocument(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteDocument starts");

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
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteDocument?id=" + id + "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteDocument ends");
		return resp;
	}

	/*
	 * --------------------------Time Sheet Type
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-timeSheet-type")
	public @ResponseBody JsonResponse<Object> addTimeSheet(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addTimeSheet starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setTimeSheetCreatedBy(userId);
		try {
			hrWorkHoursModel.setTimeSheetupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addTimeSheet", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addTimeSheet ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-timeSheet")
	public @ResponseBody List<HrMasterModel> viewTimeSheet(HttpSession session) {

		logger.info("Method : viewTimeSheet");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewTimeSheet?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewTimeSheet");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-timeSheet-delete")
	public @ResponseBody JsonResponse<Object> deleteTimeSheet(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteTimeSheet starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteTimeSheet?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteTimeSheet ends");
		return resp;
	}

	/*
	 * --------------------------Employment Type
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-empstatus-type")
	public @ResponseBody JsonResponse<Object> addEmpStatus(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addEmpStatus starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setEmploymentstatusCreatedBy(userId);
		try {
			hrWorkHoursModel.setEmploymentstatusupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addEmpStatus", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addEmpStatus ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-empstatus")
	public @ResponseBody List<HrMasterModel> viewEmpStatus(HttpSession session) {

		logger.info("Method : viewEmpStatus");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewEmpStatus?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewEmpStatus");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-emp-status-delete")
	public @ResponseBody JsonResponse<Object> deleteEmpStatus(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteEmpStatus starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteEmpStatus?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteEmpStatus ends");
		return resp;
	}

	/*
	 * --------------------------Project Type
	 * -----------------------------------------------------------------------
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-projectType")
	public @ResponseBody JsonResponse<Object> addprojectType(@RequestBody HrMasterModel hrWorkHoursModel, Model model,
			HttpSession session) {
		logger.info("Method :addprojectType starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setOrganization(organization);
		hrWorkHoursModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrWorkHoursModel.setProjectTypeCreatedBy(userId);
		try {
			hrWorkHoursModel.setProjectTypeupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addprojectType", hrWorkHoursModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addprojectType ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-projectType")
	public @ResponseBody List<HrMasterModel> viewprojectType(HttpSession session) {

		logger.info("Method : viewprojectType");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewprojectType?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewprojectType");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-projectType-delete")
	public @ResponseBody JsonResponse<Object> deleteprojectType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteprojectType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteprojectType?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteprojectType ends");
		return resp;
	}

	/*
	 * --------------------------Priority master
	 * -----------------------------------------------------------------------
	 *
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-priority-master")
	public @ResponseBody JsonResponse<Object> addPriority(@RequestBody HrMasterModel hrMasterModel, Model model,
			HttpSession session) {
		logger.info("Method :addPriority starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setOrganization(organization);
		hrMasterModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setPriorityCreatedBy(userId);
		try {
			hrMasterModel.setPriorityUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addPriority", hrMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addPriority ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-priority-master")
	public @ResponseBody List<HrMasterModel> viewPriority(HttpSession session) {

		logger.info("Method : viewPriority start");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewPriority?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewPriority ends");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-priority-master-delete")
	public @ResponseBody JsonResponse<Object> deletePriority(HttpSession session, @RequestParam String id) {
		logger.info("Method : deletePriority starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deletePriority?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deletePriority ends");
		return resp;
	}

	/*
	 * --------------------------Priority master
	 * -----------------------------------------------------------------------
	 *
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-gender-master")
	public @ResponseBody JsonResponse<Object> addGender(@RequestBody HrMasterModel hrMasterModel, Model model,
			HttpSession session) {
		logger.info("Method :addGender starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setOrganization(organization);
		hrMasterModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setGenderCreatedBy(userId);
		try {
			hrMasterModel.setGenderupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addGender", hrMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addGender ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-gender-master")
	public @ResponseBody List<HrMasterModel> viewGender(HttpSession session) {

		logger.info("Method : viewGender start");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewGender?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewGender ends");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-gender-master-delete")
	public @ResponseBody JsonResponse<Object> deleteGender(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteGender starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteGender?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteGender ends");
		return resp;
	}

	/*
	 * --------------------------Dependent Relationship
	 * -----------------------------------------------------------------------
	 *
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-dependent-relationship")
	public @ResponseBody JsonResponse<Object> addDepRelationship(@RequestBody HrMasterModel hrMasterModel, Model model,
			HttpSession session) {
		logger.info("Method :addDepRelationship starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setOrganization(organization);
		hrMasterModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setDepRelationCreatedBy(userId);
		try {
			hrMasterModel.setDepRelationupdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addDepRelationship", hrMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addDepRelationship ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-emp-dependent-relationship")
	public @ResponseBody List<HrMasterModel> viewDepRelationship(HttpSession session) {

		logger.info("Method : viewDepRelationship start");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewDepRelationship?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewDepRelationship ends");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-dependent-relationship-delete")
	public @ResponseBody JsonResponse<Object> deleteDepRelationship(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteDepRelationship starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteDepRelationship?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteDepRelationship ends");
		return resp;
	}

	/*
	 * --------------------------Employee Dependent
	 * type---------------------------------------------------
	 *
	 *
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-employee-dependent-type")
	public @ResponseBody JsonResponse<Object> addDependentType(@RequestBody HrMasterModel hrMasterModel, Model model,
			HttpSession session) {
		logger.info("Method : addDependentType starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setOrganization(organization);
		hrMasterModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setDependentCreatedBy(userId);
		try {
			hrMasterModel.setDependentUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addDependentType", hrMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addDependentType ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-employee-dependent-type")
	public @ResponseBody List<HrMasterModel> viewDependentType(HttpSession session) {

		logger.info("Method : viewDependentType start");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewDependentType?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewDependentType ends");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-dependent-type-delete")
	public @ResponseBody JsonResponse<Object> deleteDependentType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteDependentType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteDependentType?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteDependentType ends");
		return resp;
	}

	/*
	 * --------------------------Employee Insurance
	 * Company---------------------------------------------------
	 *
	 *
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-employee-insurance-company")
	public @ResponseBody JsonResponse<Object> addInsuranceCompany(@RequestBody HrMasterModel hrMasterModel, Model model,
			HttpSession session) {
		logger.info("Method : addInsuranceCompany starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setOrganization(organization);
		hrMasterModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setInsuranceCreatedBy(userId);
		try {
			hrMasterModel.setInsuranceUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addInsuranceCompany", hrMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addInsuranceCompany ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-employee-insurance-company")
	public @ResponseBody List<HrMasterModel> viewInsuranceCompany(HttpSession session) {

		logger.info("Method : viewInsuranceCompany start");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "viewInsuranceCompany?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewInsuranceCompany ends");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-insurance-company-delete")
	public @ResponseBody JsonResponse<Object> deleteInsuranceCompany(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteInsuranceCompany starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteInsuranceCompany?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteInsuranceCompany ends");
		return resp;
	}

	/*
	 * --------------------------Designation Add
	 * ---------------------------------------------------
	 *
	 *
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/referenceHr-add-designationmaster")
	public @ResponseBody JsonResponse<Object> addDesignationMaster(@RequestBody HrMasterModel hrMasterModel,
			Model model, HttpSession session) {
		logger.info("Method : addDesignationMaster starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setOrganization(organization);
		hrMasterModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
			// logger.info("==========" + hrMasterModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		hrMasterModel.setDesignationCreatedBy(userId);
		// logger.info("hrMasterModel====" + hrMasterModel);
		try {
			hrMasterModel.setDesignationUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "rest-addDesignationMaster", hrMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		// logger.info("hrMasterModel====" + hrMasterModel);
		logger.info("Method : addDesignationMaster ends");

		return jsonResponse;
	}
	/// view designation

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/referenceHr-view-designation")
	public @ResponseBody List<HrMasterModel> viewDesignationDetails(HttpSession session) {

		logger.info("Method : viewDesignationDetails start");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewDesignationDetails?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewDesignationDetails ends");
		// logger.info(jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/referenceHr-designation-delete")
	public @ResponseBody JsonResponse<Object> deleteDesignation(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteDesignation starts");

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
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteDesignationMaster?id=" + id + "&orgName=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		// logger.info("##################" + resp);
		logger.info("Method : deleteDesignation ends");
		return resp;
	}

	// *************************Department Add***********************************

	@SuppressWarnings("unchecked")
	@PostMapping(value = "referenceHr-add-department-master")
	public @ResponseBody JsonResponse<Object> addDepartmentMaster(@RequestBody HrMasterModel hrMasterModel, Model model,
			HttpSession session) {
		logger.info("Method : addDepartmentMaster starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
	

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setOrganizationName(orgName);
		hrMasterModel.setOrganizationDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		hrMasterModel.setDepartmentCreatedBy(userId);
		logger.info(" hrMasterModel==========="+ hrMasterModel);
		try {

			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "rest-addDepartmentMaster", hrMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		
		logger.info("Method : addDepartmentMaster ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "referenceHr-view-department")
	public @ResponseBody List<HrMasterModel> viewDepartmentDetails(HttpSession session) {

		logger.info("Method : viewDepartmentDetails start");
		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewDepartmentDetails?orgName=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewDepartmentDetails ends");
	

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("referenceHr-department-delete")
	public @ResponseBody JsonResponse<Object> deleteDepartment(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteDepartment starts");

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
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteDepartment?id=" + id + "&orgName=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		
		logger.info("Method : deleteDepartment ends");
		return resp;
	}

	// *******************************Sub-Department*********************************

	@SuppressWarnings("unchecked")
	@PostMapping(value = "referenceHr-add-subdepartment-master")
	public @ResponseBody JsonResponse<Object> addSubDepartmentMaster(@RequestBody HrMasterModel hrMasterModel,
			Model model, HttpSession session) {
		logger.info("Method : addSubDepartmentMaster starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		hrMasterModel.setOrganizationNames(orgName);
		hrMasterModel.setOrganizationDivisions(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		hrMasterModel.setSubDepartmentCreatedBy(userId);
	
		try {

			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "rest-addSubDepartmentMaster", hrMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
	
		logger.info("Method : addSubDepartmentMaster ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = "referenceHr-view-subdepartment")
	public @ResponseBody List<HrMasterModel> viewSubDepartmentDetails(HttpSession session) {

		logger.info("Method : viewSubDepartmentDetails start");
		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrMasterModel>> jsonResponse = new JsonResponse<List<HrMasterModel>>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewSubDepartmentDetails?orgName=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewSubDepartmentDetails ends" + jsonResponse.getBody());

		
		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")

	@GetMapping("referenceHr-subdepartment-delete")
	public @ResponseBody JsonResponse<Object> deleteSubDepartment(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteSubDepartment starts");

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
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteSubDepartment?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("Method : deleteSubDepartment ends");
		return resp;
	}
	/*
	 * get shiftSubDepartment
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "referenceHr-get-shiftSubDepartment" })
	public @ResponseBody JsonResponse<Object> getshiftSubDepartment(HttpSession session,@RequestParam String shiftDeptId) {
		logger.info("Method : getshiftSubDepartment starts");
		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-shiftSubDepartment?shiftDeptId=" + shiftDeptId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getshiftSubDepartment ends");
		
		logger.info("LISTTTT" + res);
		return res;

	}
	
	// getSubDeptTypeList
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "referenceHr-SubDeptType-List" })
	public @ResponseBody JsonResponse<Object> getSubDeptTypeList(HttpSession session ) {
		logger.info("Method : getSubDeptTypeList starts");
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-getSubDeptTypeList?orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setMessage("Unsuccess");
		}

		logger.info("Method : getSubDeptTypeList ends");

		logger.info("LISTTTT" + res);
		return res;
	}
	
	// getShiftDeptTypeList
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "referenceHr-ShiftDeptType-List" })
	public @ResponseBody JsonResponse<Object> getShiftDeptTypeList(HttpSession session ) {
		logger.info("Method : getShiftDeptTypeList starts");
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-getSubDeptTypeList?orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setMessage("Unsuccess");
		}

		logger.info("Method : getShiftDeptTypeList ends");

		logger.info("LISTTTT" + res);
		return res;
	}
}
