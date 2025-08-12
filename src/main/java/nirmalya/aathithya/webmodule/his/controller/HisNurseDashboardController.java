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
public class HisNurseDashboardController {
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HisNurseDashboardController.class);

	@GetMapping(value = { "/his-nurse" })
	public String viewOpd(Model model, HttpSession session) {
		logger.info("Method : viewNurse starts");
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] category = restClient.getForObject(env.getHisUrl() + "getCategoryWiseItemList?org=" + org
					+ "&orgDiv=" + orgDiv + "&type=" + "Pharmacy", DropDownModel[].class);
			List<DropDownModel> medlist = Arrays.asList(category);
			model.addAttribute("medlist", medlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] category = restClient.getForObject(env.getHisUrl() + "getCategoryWiseItemList?org=" + org
					+ "&orgDiv=" + orgDiv + "&type=" + "Patholab", DropDownModel[].class);
			List<DropDownModel> testlist = Arrays.asList(category);
			model.addAttribute("testlist", testlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewNurse ends");
		return "his/nurse-dashboard.html";
	}
	
	//patient view
	@SuppressWarnings("unchecked")
	@GetMapping("/his-nurse-ipd-view")
	public @ResponseBody Object viewNurseIpd(HttpSession session) {
		logger.info("Method :viewNurseIpd starts");
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
			resp = restClient.getForObject(env.getHisUrl() + "his-nurse-ipd-viewIpd?orgName=" + orgName + "&orgDivision="
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
		logger.info("Method :viewNurseIpd ends" + resp);
		return resp;
	}
	
	
	// viewAllDetailsByIPDID
		@SuppressWarnings("unchecked")
		@GetMapping("/his-nurse-ipd-viewAllDetailsByIPDID")
		public @ResponseBody Object viewAllDetailsByIPDID(HttpSession session, @RequestParam String patientId, String ipdId) {
			logger.info("Method :viewAllDetailsByIPDID starts");
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
				resp = restClient
						.getForObject(
								env.getHisUrl() + "rest-ipd-viewAllDetailsByIPDID?orgName=" + orgName + "&orgDivision=" + orgDivision
								+ "&userId=" + userId + "&patientId=" + patientId + "&ipdId=" + ipdId,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewAllDetailsByIPDID ends" );
			return resp;
		}
		
		
		// save vital
		@SuppressWarnings("unchecked")
		@PostMapping("his-nurse-save-vital-details")
		public @ResponseBody JsonResponse<Object> saveVitalDetails(@RequestBody HISPatientModel patientModel,
				HttpSession session) {
			logger.info("Method: saveVitalDetails starts" + patientModel);

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
			patientModel.setOrganization(orgName);
			patientModel.setOrgDivision(orgDivision);

			try {
				resp = restClient.postForObject(env.getHisUrl() + "rest-his-ipd-save-vital-details", patientModel,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method: saveVitalDetails ends");
			return resp;
		}
		
		
		// save-treatment-details
		@SuppressWarnings("unchecked")
		@PostMapping("his-nurse-save-treatment-details")
		public @ResponseBody JsonResponse<Object> saveTreatmentDetails(HttpSession session,
				@RequestBody List<Map<String, Object>> treatmentDataList) {
			logger.info("Method : saveTreatmentDetails starts"+treatmentDataList);

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
				resp = restClient.postForObject(env.getHisUrl() + "rest-his-ipd-save-treatment-details?userId=" + userId
						+ "&org=" + orgName + "&orgDiv=" + orgDivision, treatmentDataList, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : saveTreatmentDetails ends" );
			return resp;
		}
		
		
		// save-test-details
		@SuppressWarnings("unchecked")
		@PostMapping("his-nurse-save-test-details")
		public @ResponseBody JsonResponse<Object> saveTestDetails(HttpSession session,
				@RequestBody List<Map<String, Object>> testDataList) {
			logger.info("Method : saveTestDetails starts");

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

				resp = restClient.postForObject(env.getHisUrl() + "rest-his-ipd-save-test?userId=" + userId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, testDataList, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : saveTestDetails ends");
			return resp;
		}
		
		
		// getDietMenu
		@SuppressWarnings("unchecked")
		@GetMapping("/his-nurse-getDietMenu")
		public @ResponseBody Object getDietMenu(HttpSession session) {
			logger.info("Method :getDietMenu starts");
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
				resp = restClient
						.getForObject(
								env.getHisUrl() + "rest-getDietMenu?orgName=" + orgName + "&orgDivision=" + orgDivision,
								JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getDietMenu ends" );
			return resp;
		}
		
		// his-ipd-save-diet-details
				@SuppressWarnings("unchecked")
				@PostMapping("his-nurse-save-diet-details")
				public @ResponseBody JsonResponse<Object> saveDietDetails(HttpSession session,
						@RequestBody List<Map<String, Object>> dietDataList) {
					logger.info("Method : saveDietDetails starts");

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
						resp = restClient.postForObject(env.getHisUrl() + "rest-his-ipd-save-diet?userId=" + userId
								+ "&org=" + orgName + "&orgDiv=" + orgDivision, dietDataList, JsonResponse.class);

					} catch (Exception e) {
						e.printStackTrace();
					}

					logger.info("Method : saveDietDetails ends");
					return resp;
				}
}
