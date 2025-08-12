package nirmalya.aathithya.webmodule.his.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;

@Controller
@RequestMapping("his")
public class HISOPDController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HISOPDController.class);

	@GetMapping(value = { "/opd" })
	public String viewOpd(Model model, HttpSession session) {
		logger.info("Method : viewOpd starts");
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] category = restClient.getForObject(
					env.getHisUrl() + "getCategoryWiseItemList?org=" + org + "&orgDiv=" + orgDiv+"&type=" + "Pharmacy",
					DropDownModel[].class);
			List<DropDownModel> medlist = Arrays.asList(category);
			model.addAttribute("medlist", medlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] category = restClient.getForObject(
					env.getHisUrl() + "getCategoryWiseItemList?org=" + org + "&orgDiv=" + orgDiv+"&type=" + "Patholab",
					DropDownModel[].class);
			List<DropDownModel> testlist = Arrays.asList(category);
			model.addAttribute("testlist", testlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewOpd ends");
		return "his/his-opd";
	}

	// manage-opd-view
	@SuppressWarnings("unchecked")
	@GetMapping("opd-manage-view")
	public @ResponseBody Object viewOpdDetails(HttpSession session, @RequestParam String fromdate,
			@RequestParam String todate) {
		logger.info("Method :viewOpdDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-viewOpdDetails?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&fromdate=" + fromdate + "&todate=" + todate, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewOpdDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("his-opd-approval-status")
	public @ResponseBody JsonResponse<HISPatientModel> approvalStatus(HttpSession session,
			@RequestParam String approval, String opdId, String approvedBy) {

		logger.info("Method : approvalStatus starts");
		JsonResponse<HISPatientModel> response = new JsonResponse<HISPatientModel>();
		try {
			response = restClient.getForObject(env.getHisUrl() + "approvalStatus?approval=" + approval + "&opdId="
					+ opdId + "&approvedBy=" + approvedBy, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : approvalStatus ends");
		return response;
	}

	// save vital details
	@SuppressWarnings("unchecked")
	@PostMapping("save-vital-details")
	public @ResponseBody JsonResponse<Object> saveVitalDetails(HttpSession session,
			@RequestBody Map<String, Object> vitalData) {
		logger.info("Method : saveVitalDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-his-opd-vital-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, vitalData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveVitalDetails ends");
		return resp;
	}

	// opd-manage-edit-vital
	@SuppressWarnings("unchecked")
	@GetMapping("opd-manage-edit-vital")
	public @ResponseBody Object editVital(HttpSession session, @RequestParam String vitalId,String vitalIdSlNo) {

		logger.info("Method :editVital starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-edit-vital?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&vitalId=" + vitalId+"&vitalIdSlNo="+vitalIdSlNo, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editVital ends" + resp);
		return resp;
	}

	// manage-opd-view-vital
	@SuppressWarnings("unchecked")
	@GetMapping("opd-manage-view-types")
	public @ResponseBody Object viewTypesDetails(HttpSession session, @RequestParam String types,
			@RequestParam String opdId) {

		logger.info("Method :viewTypesDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-view-types?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&types=" + types + "&opdId=" + opdId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewTypesDetails ends" + resp);
		return resp;
	}

	// save-treatment-details
	@SuppressWarnings("unchecked")
	@PostMapping("save-treatment-details")
	public @ResponseBody JsonResponse<Object> saveTreatmentDetails(HttpSession session,
			@RequestBody List<Map<String, Object>> treatmentDataList) {
		logger.info("Method : saveTreatmentDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-treatment-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, treatmentDataList, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTreatmentDetails ends");
		return resp;
	}
  
	// save-opd-test-details 
	@SuppressWarnings("unchecked")
	@PostMapping("save-opd-test-details")
	public @ResponseBody JsonResponse<Object> saveTestDetails(HttpSession session,
			@RequestBody List<Map<String, Object>> testDataList) {
		logger.info("Method : saveTestDetails starts" + testDataList);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {

			resp = restClient.postForObject(env.getHisUrl() + "rest-test-details?userId=" + userId + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, testDataList, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTestDetails ends");
		return resp;
	}


	// save-diet-details
	@SuppressWarnings("unchecked")
	@PostMapping("save-diet-details")
	public @ResponseBody JsonResponse<Object> saveOpdDiet(HttpSession session,
			@RequestBody Map<String, Object> dietData) {
		logger.info("Method : saveOpdDiet starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-opd-diet-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, dietData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTsaveOpdDietestDetails ends");
		return resp;
	}

	// opd-manage-edit-diet
	@SuppressWarnings("unchecked")
	@GetMapping("opd-manage-edit-diet")
	public @ResponseBody Object editOpdDiet(HttpSession session, @RequestParam String dietId,String dietSlNo) {

		logger.info("Method :editOpdDiet starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-edit-opd-diet?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&dietId=" + dietId+"&dietSlNo="+dietSlNo, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editOpdDiet ends" + resp);
		return resp;
	}

	// opd-manage-delete-diet
	@SuppressWarnings("unchecked")
	@GetMapping("opd-manage-delete-diet")
	public @ResponseBody Object deleteOpdDiet(HttpSession session, @RequestParam String dietId) {

		logger.info("Method :deleteOpdDiet starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-delelte-opd-diet?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&dietId=" + dietId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :deleteOpdDiet ends" + resp);
		return resp;
	}

	// save-opd-notes-details
	@SuppressWarnings("unchecked")
	@PostMapping("save-opd-notes-details")
	public @ResponseBody JsonResponse<Object> saveNotesDetail(HttpSession session,
			@RequestBody Map<String, Object> notesData) {
		logger.info("Method : saveNotesDetail starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-opd-notes-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, notesData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveNotesDetail ends");
		return resp;
	}
	
	//opd-manage-edit-notes
	@SuppressWarnings("unchecked")
	@GetMapping("opd-manage-edit-notes")
	public @ResponseBody Object editOpdNotes(HttpSession session, @RequestParam String noteId,String notesIdSlNo) {

		logger.info("Method :editOpdNotes starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-edit-opd-notes?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&noteId=" + noteId+"&notesIdSlNo="+notesIdSlNo, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editOpdNotes ends" + resp);
		return resp;
	}
	
	//opd-manage-delete-note
	
	@SuppressWarnings("unchecked")
	@GetMapping("opd-manage-delete-note")
	public @ResponseBody Object deleteOpdNote(HttpSession session, @RequestParam String noteId) {

		logger.info("Method :deleteOpdNote starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-delelte-opd-note?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&noteId=" + noteId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :deleteOpdNote ends" + resp);
		return resp;
	}
	
	
	// save-ot-details
	@SuppressWarnings("unchecked")
	@PostMapping("save-ot-details")
	public @ResponseBody JsonResponse<Object> saveOpdOt(HttpSession session,
			@RequestBody Map<String, Object> otData) {
		logger.info("Method : saveOpdOt starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restClient.postForObject(env.getHisUrl() + "rest-opd-ot-details?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, otData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveOpdOt ends"+resp);
		return resp;
	}
	
	
	// opd-manage-edit-ot
		@SuppressWarnings("unchecked")
		@GetMapping("opd-manage-edit-ot")
		public @ResponseBody Object editOpdOt(HttpSession session, @RequestParam String opd,String pat) {

			logger.info("Method :editOpdOt starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restClient.getForObject(env.getHisUrl() + "rest-edit-opd-ot?orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&opd=" + opd+"&pat="+pat, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :editOpdOt ends" + resp);
			return resp;
		}
		
		/* opd-manage-view-treatment-his */
		@SuppressWarnings("unchecked")
		@GetMapping("opd-manage-view-treatment-his")
		public @ResponseBody Object viewTreatmentHistory(HttpSession session, @RequestParam String types,
				@RequestParam String patId,@RequestParam String bookingId) {

			logger.info("Method :viewTreatmentHistory starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restClient.getForObject(env.getHisUrl() + "rest-view-treatment-his?orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&types=" + types + "&patId=" + patId + "&bookingId=" + bookingId, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :viewTreatmentHistory ends" + resp);
			return resp;
		}
		
		/* opd-manage-view-test-his */
		@SuppressWarnings("unchecked")
		@GetMapping("opd-manage-view-test-his")
		public @ResponseBody Object viewTestHistory(HttpSession session, @RequestParam String types,
				@RequestParam String patId) {

			logger.info("Method :viewTestHistory starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restClient.getForObject(env.getHisUrl() + "rest-view-test-his?orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&types=" + types + "&patId=" + patId, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :viewTestHistory ends" + resp);
			return resp;
		}
		
		/* opd-manage-view-diet-his */
		@SuppressWarnings("unchecked")
		@GetMapping("opd-manage-view-diet-his")
		public @ResponseBody Object viewDietHistory(HttpSession session, @RequestParam String types,
				@RequestParam String patId) {

			logger.info("Method :viewDietHistory starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restClient.getForObject(env.getHisUrl() + "rest-view-diet-his?orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&types=" + types + "&patId=" + patId, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :viewDietHistory ends" + resp);
			return resp;
		}
		
		
		// save-opd-symp-details
		@SuppressWarnings("unchecked")
		@PostMapping("save-opd-symp-details")
		public @ResponseBody JsonResponse<Object> saveSympsDetail(HttpSession session,
				@RequestBody Map<String, Object> sympData) {
			logger.info("Method : saveSympsDetail starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
			try {
				resp = restClient.postForObject(env.getHisUrl() + "rest-save-opd-symp-details?userId=" + userId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, sympData, JsonResponse.class);

				resp.setBody(resp.getBody());
				resp.setMessage(resp.getMessage());
				resp.setCode(resp.getCode());

			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : saveSympsDetail ends");
			return resp;
		}
		
		//opd-manage-edit-sympt
		@SuppressWarnings("unchecked")
		@GetMapping("opd-manage-edit-sympt")
		public @ResponseBody Object editSympt(HttpSession session, @RequestParam String symptomsId) {

			logger.info("Method :editSympt starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restClient.getForObject(env.getHisUrl() + "rest-opd-manage-edit-sympt?orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&symptomsId=" + symptomsId, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :editSympt ends" + resp);
			return resp;
		}
}