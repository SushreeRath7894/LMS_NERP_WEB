package nirmalya.aathithya.webmodule.his.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;
import nirmalya.aathithya.webmodule.his.model.HisBookingAmbulanceModel;

@Controller
@RequestMapping("his")
public class HISIPDController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HISIPDController.class);

	@GetMapping(value = { "/manage-ipd" })
	public String viewIpd(Model model, HttpSession session) {
		logger.info("Method : viewPatient starts");
		
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// Fetch gender data
			DropDownModel[] gender = restClient.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			// Fetch marital status data
			DropDownModel[] marital = restClient.getForObject(env.getHisUrl() + "/maritalstatusList",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(marital);
			model.addAttribute("maritalstatusList", maritalstatusList);

			// Fetch nationality data
			DropDownModel[] nationality = restClient.getForObject(env.getHisUrl() + "/nationalityList",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(nationality);
			model.addAttribute("nationalityList", nationalityList);

			DropDownModel[] country = restClient.getForObject(env.getHisUrl() + "/countryList", DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);

			// Fetch department data
			DropDownModel[] department = restClient.getForObject(env.getHisUrl() + "/doctordepartmentList",
					DropDownModel[].class);
			List<DropDownModel> departmentList = Arrays.asList(department);
			model.addAttribute("departmentList", departmentList);

			// patient relation list
			DropDownModel[] relation = restClient.getForObject(env.getHisUrl() + "/getRelationList",
					DropDownModel[].class);
			List<DropDownModel> getRelationList = Arrays.asList(relation);
			model.addAttribute("getRelationList", getRelationList);

			// insurance List
			// patient relation list
			DropDownModel[] insurance = restClient.getForObject(env.getHisUrl() + "/getinsuranceList",
					DropDownModel[].class);
			List<DropDownModel> insuranceList = Arrays.asList(insurance);
			logger.info("insuranceList" + insuranceList);
			model.addAttribute("insuranceList", insuranceList);

			// Fetch ward data
			DropDownModel[] ward = restClient.getForObject(env.getHisUrl() + "/wardList", DropDownModel[].class);
			List<DropDownModel> wardList = Arrays.asList(ward);
			model.addAttribute("wardList", wardList);

		} catch (RestClientException e) {
			// Handle RestClientException
			e.printStackTrace();

		}
		
		try {
			DropDownModel[] bankAccountList = restClient.getForObject(env.getAccountUrl()
					+ "getBankAccountPaymentList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getHisUrl()+ "/getAllPatientList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> patList = Arrays.asList(costCenter);
			model.addAttribute("patList", patList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewIpd ends");
		// return "his/his-ipd";

		return "his/reception-his-ipd";
	}

	// manage-patient-patientList
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-ipd-patientList" })
	public @ResponseBody JsonResponse<HISPatientModel> getVendorNameAutoList(Model model,
			@RequestBody String searchValue) {
		logger.info("Method : getVendorNameAutoList starts");
		JsonResponse<HISPatientModel> res = new JsonResponse<HISPatientModel>();

		try {
			res = restClient.getForObject(env.getHisUrl() + "getPatientList?id=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getPatientList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("manage-ipd-add")
	public @ResponseBody JsonResponse<Object> addIpd(@RequestBody HisBookingAmbulanceModel patientModel, HttpSession session) {
		logger.info("Method: addIpd starts" + patientModel);

		JsonResponse<Object> resp = new JsonResponse<>();

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
		patientModel.setCreatedBy(userId);
		patientModel.setOrg(orgName);
		patientModel.setDiv(orgDivision);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "restipduserregsandproductbooking", patientModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setMessage("Error during REST call");
		}

		logger.info("Method: addIpd ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-ipd-view")
	public @ResponseBody Object viewIpd(HttpSession session) {
		logger.info("Method :viewIpd starts");
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
		try {
			resp = restClient.getForObject(env.getHisUrl() + "rest-viewIpd?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :viewIpd ends" + resp);
		return resp;
	}

	// manage-patient-edit
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("manage-ipd-edit") public @ResponseBody
	 * JsonResponse<HISPatientModel> editIpd(@RequestParam String id, HttpSession
	 * session) {
	 * 
	 * logger.info("Method : editIpd starts"); JsonResponse<HISPatientModel> resp =
	 * new JsonResponse<HISPatientModel>(); String organization = ""; String
	 * orgDivision = ""; try { organization = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * e.printStackTrace(); } logger.info("id====" + id);
	 * 
	 * try { resp = restClient.getForObject(env.getHisUrl() + "editIpd?id=" + id +
	 * "&organization=" + organization + "&orgDivision=" + orgDivision,
	 * JsonResponse.class); } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); HISPatientModel Model =
	 * mapper.convertValue(resp.getBody(), new TypeReference<HISPatientModel>() {
	 * });
	 * 
	 * resp.setBody(Model); if (resp.getMessage() != null && resp.getMessage() !=
	 * "") { resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
	 * resp.setMessage("Success"); } logger.info("Method : editIpd ends" + resp);
	 * return resp; }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-ipd-edit")
	public @ResponseBody Object editIpd(@RequestParam String id, HttpSession session) {
		logger.info("Method :editIpd starts");
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
		try {
			resp = restClient.getForObject(env.getHisUrl() + "rest-editIpd?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :editIpd ends" + resp);
		return resp;
	}

	// manage-patient-delete
	@SuppressWarnings("unchecked")
	@GetMapping("/manage-ipd-delete")
	public @ResponseBody JsonResponse<Object> deleteIpd(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteIpd starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getHisUrl() + "deleteIpd?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteIpd ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("manage-ipd-modalData-add")
	public @ResponseBody JsonResponse<Object> addIpdModalData(@RequestBody HISPatientModel patientModel,
			HttpSession session) {
		logger.info("Method: addIpdModalData starts");

		JsonResponse<Object> resp = new JsonResponse<>();

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
		patientModel.setCreatedByModal(userId);
		patientModel.setOrganizationModal(orgName);
		patientModel.setOrgDivisionModal(orgDivision);

		try {
			resp = restClient.postForObject(env.getHisUrl() + "restAddIpdPatient", patientModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setMessage("Error during REST call");
		}

		if (resp.getMessage() == null || resp.getMessage().isEmpty()) {
			resp.setMessage("Success");
		}

		logger.info("Method: addIpdModalData ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-patient-bedList" })
	public @ResponseBody JsonResponse<Object> getPatientBedList(@RequestParam String id) {
		logger.info("Method : getPatientBedList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getHisUrl() + "getPatientBedList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getPatientBedList ends");
		return res;
	}

	// manage-patient-feeList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-ipd-feeList" })
	public @ResponseBody JsonResponse<Object> feeList(HttpSession httpSession, @RequestParam String id,
			@RequestParam String dateOfAppoints) {
		logger.info("Method : feeList starts==================>" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getHisUrl() + "feeList?id=" + id + "&dateOfAppoints=" + dateOfAppoints,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("feeList" + res);
		logger.info("Method : feeList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-ipd-department-list" })
	public @ResponseBody JsonResponse<Object> getDepartmentList(HttpSession session, @RequestParam String id) {
		logger.info("Method : department  list starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			res = restClient.getForObject(
					env.getHisUrl() + "rest-department-list?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : department list ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-ipd-doctorList" })
	public @ResponseBody JsonResponse<Object> getDoctorList(HttpSession session, @RequestParam String from,
			@RequestParam String deptId) {
		logger.info("Method : getDoctorList starts" + from);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			res = restClient
					.getForObject(
							env.getMasterUrl() + "restgetproducttypewise?orgName=" + orgName + "&orgDivision="
									+ orgDivision + "&userId=" + userId + "&from=" + from + "&deptId=" + deptId,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getCode());
			res.setMessage(res.getMessage());
		} else {
			res.setMessage("Unsuccess");
		}
		logger.info("state" + res);
		logger.info("Method : getDoctorList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-ipd-city-list" })
	public @ResponseBody JsonResponse<Object> CityList(@RequestParam String id) {
		logger.info("Method : CityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getHisUrl() + "opd-recep-city-list?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : CityList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-ipd-bed-list" })
	public @ResponseBody JsonResponse<Object> getBedLists(HttpSession session) {
		logger.info("Method : getBedLists starts");
		
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			res = restClient.getForObject(
					env.getHisUrl() + "rest-get-bed-lists?org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getBedLists list ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-ipd-delete-child-data")
	public @ResponseBody JsonResponse<Object> deleteDepChildData(@RequestBody DropDownModel data,
			HttpSession session) {
		logger.info("Method: deleteDepChildData starts");
		
		JsonResponse<Object> resp = new JsonResponse<>();
		
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
		data.setCreatedBy(userId);
		data.setOrgName(orgName);
		data.setOrgDivision(orgDivision);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "restdeletechilddata", data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method: deleteDepChildData ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-ipd-add-dep-data")
	public @ResponseBody JsonResponse<Object> addDepChildData(@RequestBody DropDownModel data,
			HttpSession session) {
		logger.info("Method: addDepChildData starts");
		
		JsonResponse<Object> resp = new JsonResponse<>();
		
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
		data.setCreatedBy(userId);
		data.setOrgName(orgName);
		data.setOrgDivision(orgDivision);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "restaddchilddata", data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method: addDepChildData ends" + resp);
		return resp;
	}

}
