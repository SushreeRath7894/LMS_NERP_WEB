package nirmalya.aathithya.webmodule.his.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;

@Controller
@RequestMapping("his")
public class HISipdTreatmentController{
@Autowired
RestTemplate restClient;
@Autowired
EnvironmentVaribles env;

Logger logger = LoggerFactory.getLogger(HISipdTreatmentController.class);

@GetMapping(value = { "/ipd-treatment" })
public String viewIpd(Model model, HttpSession session) {
	logger.info("Method : viewPatient starts");

	
		return "his/his-ipd-treatment";
}

@GetMapping(value = { "/ipd-treatment-Details" })
public String viewIpdT(Model model, HttpSession session,@RequestParam String id) {
	logger.info("Method : viewIPDTreatment starts");


	DropDownModel[] type = restClient.getForObject(env.getHisUrl() + "/typeList", DropDownModel[].class);
	List<DropDownModel> typeList = Arrays.asList(type);
	model.addAttribute("typeList", typeList);
	logger.info("Method : viewIPDTreatment ends");
		return "his/his-ipd-treatment-medicine-list";


}
}
//		logger.info("Method : viewPatient starts");

//		try {
			// Fetch gender data
//			DropDownModel[] gender = restClient.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
//			List<DropDownModel> genderList = Arrays.asList(gender);
//			model.addAttribute("genderList", genderList);

			// Fetch marital status data
//			DropDownModel[] marital = restClient.getForObject(env.getHisUrl() + "/maritalstatusList",
//					DropDownModel[].class);
//			List<DropDownModel> maritalstatusList = Arrays.asList(marital);
//			model.addAttribute("maritalstatusList", maritalstatusList);

			// Fetch nationality data
//			DropDownModel[] nationality = restClient.getForObject(env.getHisUrl() + "/nationalityList",
//					DropDownModel[].class);
//			List<DropDownModel> nationalityList = Arrays.asList(nationality);
//			model.addAttribute("nationalityList", nationalityList);
//
//			DropDownModel[] country = restClient.getForObject(env.getHisUrl() + "/countryList", DropDownModel[].class);
//			List<DropDownModel> countryList = Arrays.asList(country);
//			model.addAttribute("countryList", countryList);
//
			// Fetch department data
//			DropDownModel[] department = restClient.getForObject(env.getHisUrl() + "/departmentList",
//					DropDownModel[].class);
//			List<DropDownModel> departmentList = Arrays.asList(department);
//			model.addAttribute("departmentList", departmentList);
//
//			// Fetch bed data
			/*
			 * DropDownModel[] bed = restClient.getForObject(env.getHisUrl() + "/bedList",
			 * DropDownModel[].class); List<DropDownModel> bedList = Arrays.asList(bed);
			 * model.addAttribute("bedList", bedList);
			 */

			// Fetch ward data
//			DropDownModel[] ward = restClient.getForObject(env.getHisUrl() + "/wardList", DropDownModel[].class);
//			List<DropDownModel> wardList = Arrays.asList(ward);
//			model.addAttribute("wardList", wardList);
//
//		} catch (RestClientException e) {
//			// Handle RestClientException
//			e.printStackTrace();
//
//		}
//
//		logger.info("Method : viewIpd ends");
//		return "his/his-ipd";
//	}
//
//	// manage-patient-patientList
//	@SuppressWarnings("unchecked")
//	@PostMapping(value = { "manage-patientList" })
//	public @ResponseBody JsonResponse<HISPatientModel> getPatientDataList(Model model,
//			@RequestBody String searchValue) {
//		logger.info("Method : getPatientDataList starts");
//		JsonResponse<HISPatientModel> res = new JsonResponse<HISPatientModel>();
//
//		try {
//			res = restClient.getForObject(env.getHisUrl() + "getPatientDataList?id=" + searchValue, JsonResponse.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (res.getMessage() != null) {

//			res.setCode(res.getMessage());
//			res.setMessage("Unsuccess");
//		} else {
//			res.setMessage("success");
//		}
//
//		logger.info("Method : getPatientDataList ends" + res);
//		return res;
//	}

//	@SuppressWarnings("unchecked")
//	@PostMapping("manage-ipd-add")
//	public @ResponseBody JsonResponse<Object> addIpd(@RequestBody HISPatientModel patientModel, HttpSession session) {
//		logger.info("Method: addIpd starts");
//
//		JsonResponse<Object> resp = new JsonResponse<>();
//
//		String userId = "";
//		String orgName = "";
//		String orgDivision = "";
//
//		try {
//			userId = (String) session.getAttribute("USER_ID");
//			orgName = (String) session.getAttribute("ORGANIZATION");
//			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		patientModel.setCreatedBy(userId);
//		patientModel.setOrganization(orgName);
//		patientModel.setOrgDivision(orgDivision);
//
//		try {
//			resp = restClient.postForObject(env.getHisUrl() + "restAddIpd", patientModel, JsonResponse.class);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//			resp.setMessage("Error during REST call");
//		}
//
//		if (resp.getMessage() == null || resp.getMessage().isEmpty()) {
//			resp.setMessage("Success");
//		}
//
//		logger.info("Method: addIpd ends");
//
//		return resp;
//	}
//
//	// manage-patient-view
//
//	@SuppressWarnings("unchecked")
//	@GetMapping("/manage-ipd-view")
//	public @ResponseBody List<HISPatientModel> viewIpd(HttpSession session) {
//		logger.info("Method : viewIpd starts");
//		String organization = "";
//		String orgDivision = "";
//
//		try {
//
//			organization = (String) session.getAttribute("ORGANIZATION");
//			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		JsonResponse<List<HISPatientModel>> resp = new JsonResponse<List<HISPatientModel>>();
//		List<HISPatientModel> returnList = new ArrayList<HISPatientModel>();
//
//		try {
//			resp = restClient.getForObject(
//					env.getHisUrl() + "rest-viewIpd?org=" + organization + "&orgDiv=" + orgDivision,
//					JsonResponse.class);
//			returnList = resp.getBody();
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//		logger.info("Method : viewIpd ends");
//		return returnList;
//	}
//
//	// manage-patient-edit
//	@SuppressWarnings("unchecked")
//	@GetMapping("manage-ipd-edit")
//	public @ResponseBody JsonResponse<HISPatientModel> editIpd(@RequestParam String id, HttpSession session) {
//
//		logger.info("Method : editIpd starts");
//		JsonResponse<HISPatientModel> resp = new JsonResponse<HISPatientModel>();
//		String organization = "";
//		String orgDivision = "";
//		try {
//			organization = (String) session.getAttribute("ORGANIZATION");
//			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("id====" + id);
//
//		try {
//			resp = restClient.getForObject(env.getHisUrl() + "editIpd?id=" + id + "&organization=" + organization
//					+ "&orgDivision=" + orgDivision, JsonResponse.class);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//
//		ObjectMapper mapper = new ObjectMapper();
//		HISPatientModel Model = mapper.convertValue(resp.getBody(), new TypeReference<HISPatientModel>() {
//		});
//
//		resp.setBody(Model);
//		if (resp.getMessage() != null && resp.getMessage() != "") {
//			resp.setCode(resp.getMessage());
//			resp.setMessage("Unsuccess");
//		} else {
//			resp.setMessage("Success");
//		}
//		logger.info("Method : editIpd ends" + resp);
//		return resp;
//	}
//	
	// manage-patient-delete
//		@SuppressWarnings("unchecked")
//		@GetMapping("/manage-ipd-delete")
//		public @ResponseBody JsonResponse<Object> deleteIpd(HttpSession session, @RequestParam String id) {
//			logger.info("Method : deleteIpd starts");
//
//			JsonResponse<Object> resp = new JsonResponse<Object>();
//
//			try {
//				resp = restClient.getForObject(env.getHisUrl() + "deleteIpd?id=" + id, JsonResponse.class);
//			} catch (RestClientException e) {
//				e.printStackTrace();
//			}
//
//			String message = resp.getMessage();
//
//			if (message != null && message != "") {
//
//			} else {
//
//				resp.setMessage("Success");
//			}
//
//			logger.info("Method : deleteIpd ends");
//			return resp;
//		}
//		
//		@SuppressWarnings("unchecked")
//		@PostMapping("manage-ipd-modalData-add")
//		public @ResponseBody JsonResponse<Object> addIpdModalData(@RequestBody HISPatientModel patientModel, HttpSession session) {
//			logger.info("Method: addIpdModalData starts");
//
//			JsonResponse<Object> resp = new JsonResponse<>();
//
//			String userId = "";
//			String orgName = "";
//			String orgDivision = "";
//
//			try {
//				userId = (String) session.getAttribute("USER_ID");
//				orgName = (String) session.getAttribute("ORGANIZATION");
//				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			patientModel.setCreatedByModal(userId);
//			patientModel.setOrganizationModal(orgName);
//			patientModel.setOrgDivisionModal(orgDivision);
//
//			try {
//				resp = restClient.postForObject(env.getHisUrl() + "restAddIpdPatient", patientModel, JsonResponse.class);
//			} catch (RestClientException e) {
//				e.printStackTrace();
//				resp.setMessage("Error during REST call");
//			}
//
//			if (resp.getMessage() == null || resp.getMessage().isEmpty()) {
//				resp.setMessage("Success");
//			}
//
//			logger.info("Method: addIpdModalData ends");
//
//			return resp;
//		}
//		
//		@SuppressWarnings("unchecked")
//		@GetMapping(value = { "manage-patient-bedList" })
//		public @ResponseBody JsonResponse<Object> getPatientBedList(@RequestParam String id) {
//			logger.info("Method : getPatientBedList starts" + id);
//			JsonResponse<Object> res = new JsonResponse<Object>();
//			try {
//				res = restClient.getForObject(env.getHisUrl() + "getPatientBedList?id=" + id, JsonResponse.class);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if (res.getMessage() != null) {
//				res.setCode(res.getMessage());
//				res.setMessage("Unsuccess");
//			} else {
//				res.setMessage("success");
//			}
//			logger.info("state" + res);
//			logger.info("Method : getPatientBedList ends");
//			return res;
//		}
//
