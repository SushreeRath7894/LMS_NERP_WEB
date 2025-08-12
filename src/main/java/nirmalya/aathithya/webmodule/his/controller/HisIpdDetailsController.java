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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.canteen.model.WebMenuModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.gatepass.model.GatePassDetailsModel;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;

@Controller
@RequestMapping(value = "his")
public class HisIpdDetailsController {
	Logger logger = LoggerFactory.getLogger(HisIpdDetailsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/his-ipd")
	public String ambulance(Model model, HttpSession session) {
		logger.info("Method : ipdDetails starts");
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] category = restTemplate.getForObject(env.getHisUrl() + "getCategoryWiseItemList?org=" + org
					+ "&orgDiv=" + orgDiv + "&type=" + "Pharmacy", DropDownModel[].class);
			List<DropDownModel> medlist = Arrays.asList(category);
			model.addAttribute("medlist", medlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] category = restTemplate.getForObject(env.getHisUrl() + "getCategoryWiseItemList?org=" + org
					+ "&orgDiv=" + orgDiv + "&type=" + "Patholab", DropDownModel[].class);
			List<DropDownModel> testlist = Arrays.asList(category);
			model.addAttribute("testlist", testlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] procedure = restTemplate.getForObject(env.getHisUrl() + "/procedureList",
					DropDownModel[].class);
			List<DropDownModel> procedureList = Arrays.asList(procedure);
			model.addAttribute("procedureList", procedureList);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : ipdDetails ends");

		return "his/his-ipd-details.html";

	}
	// getDietMenu
	@SuppressWarnings("unchecked")
	@GetMapping("/his-ipd-getDietMenu")
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
			resp = restTemplate
					.getForObject(
							env.getHisUrl() + "rest-getDietMenu?orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getDietMenu ends" );
		return resp;
	}
	// viewAllDetailsByIPDID
	@SuppressWarnings("unchecked")
	@GetMapping("/his-ipd-viewAllDetailsByIPDID")
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
			resp = restTemplate
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
	@PostMapping("his-ipd-save-vital-details")
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
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-his-ipd-save-vital-details", patientModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method: saveVitalDetails ends");
		return resp;
	}
	// edit vital

	@SuppressWarnings("unchecked")
	@GetMapping("his-ipd-vital-edit")
	public @ResponseBody Object editVital(@RequestParam String Id, HttpSession session) {
		logger.info("Method :editVital starts" + Id);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-his-ipd-editVital?Id=" + Id + "&organization="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editVital ends");
		return resp;
	}

	 
	// save-treatment-details
	@SuppressWarnings("unchecked")
	@PostMapping("his-ipd-save-treatment-details")
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
			e.printStackTrace();
		}
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-his-ipd-save-treatment-details?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, treatmentDataList, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTreatmentDetails ends");
		return resp;
	}
 	
	// save-test-details
	@SuppressWarnings("unchecked")
	@PostMapping("his-ipd-save-test-details")
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

			resp = restTemplate.postForObject(env.getHisUrl() + "rest-his-ipd-save-test?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, testDataList, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTestDetails ends");
		return resp;
	}
	// his-ipd-save-diet-details
		@SuppressWarnings("unchecked")
		@PostMapping("his-ipd-save-diet-details")
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
				resp = restTemplate.postForObject(env.getHisUrl() + "rest-his-ipd-save-diet?userId=" + userId
						+ "&org=" + orgName + "&orgDiv=" + orgDivision, dietDataList, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : saveDietDetails ends");
			return resp;
		}
 
	// edit diet
	@SuppressWarnings("unchecked")
	@GetMapping("his-ipd-diet-edit")
	public @ResponseBody Object editDiet(@RequestParam String id, HttpSession session) {
		logger.info("Method :editDiet starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "his-ipd-edit-diet?id=" + id + "&organization=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editDiet ends" + resp);
		return resp;
	}


	// save-ot-details
	@SuppressWarnings("unchecked")
	@PostMapping("his-ipd-save-ot-details")
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
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-his-ipd-save-ot?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, otData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveOpdOt ends");
		return resp;
	}
	
	// save-opd-symp-details
			@SuppressWarnings("unchecked")
			@PostMapping("save-ipd-symp-details")
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
					resp = restTemplate.postForObject(env.getHisUrl() + "rest-save-ipd-symp-details?userId=" + userId + "&org="
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

			@SuppressWarnings("unchecked")
			@GetMapping("ipd-manage-edit-sympt")
			public @ResponseBody Object editSympt(HttpSession session, @RequestParam String symptomsId) {

				logger.info("Method :editSympt starts");
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String orgName = "";
				String orgDivision = "";
				try {
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

					resp = restTemplate.getForObject(env.getHisUrl() + "rest-ipd-manage-edit-sympt?orgName=" + orgName + "&orgDivision="
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
			
			@SuppressWarnings("unchecked")
			@GetMapping("his-ipd-manage-view-treatment")
			public @ResponseBody Object viewTreatmentHistory(HttpSession session, @RequestParam String patientId,
					@RequestParam String ipdId) {

				logger.info("Method :viewTreatmentHistory starts");
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String orgName = "";
				String orgDivision = "";
				try {
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

					resp = restTemplate.getForObject(env.getHisUrl() + "rest-ipd-view-treatment-his?orgName=" + orgName + "&orgDivision="
							+ orgDivision + "&patientId=" + patientId + "&ipdId=" + ipdId, JsonResponse.class);

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
			
			
			/* ipd-manage-view-test-his */
			@SuppressWarnings("unchecked")
			@GetMapping("his-ipd-manage-view-test-his")
			public @ResponseBody Object viewTestHistory(HttpSession session, @RequestParam String patientId,
					@RequestParam String ipdId) {

				logger.info("Method :viewTestHistory starts");
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String orgName = "";
				String orgDivision = "";
				try {
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

					resp = restTemplate.getForObject(env.getHisUrl() + "rest-ipd-view-test-his?orgName=" + orgName + "&orgDivision="
							+ orgDivision + "&patientId=" + patientId + "&ipdId=" + ipdId, JsonResponse.class);

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
}

