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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DocumentUtils;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "his")
public class RadiologyController {

	Logger logger = LoggerFactory.getLogger(RadiologyController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	DocumentUtils docUtils = new DocumentUtils();

	@SuppressWarnings("unused")
	@GetMapping("radiology")
	public String radiology(Model model, HttpSession session) {
		logger.info("Method : radiology starts");

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
			DropDownModel[] gender = restTemplate.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			DropDownModel[] country = restTemplate.getForObject(env.getHisUrl() + "/countryList",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);

			DropDownModel[] patient = restTemplate.getForObject(
					env.getHisUrl() + "/getAllPatientList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> patList = Arrays.asList(patient);
			model.addAttribute("patList", patList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] bankAccountList = restTemplate.getForObject(env.getAccountUrl()
					+ "getBankAccountPaymentList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restTemplate.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : radiology ends");
		return "his/radiology";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("radiology-view")
	public @ResponseBody Object viewPatient(HttpSession session) {
		logger.info("Method :viewPatient starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewIPDOPDlist?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&type=RAD", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewPatient ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("radiology-test-view")
	public @ResponseBody Object viewbloodtest(HttpSession session, @RequestParam String id) {
		logger.info("Method :viewbloodtest starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-bloodtestName?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId + "&id=" + id + "&type=RAD", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewbloodtest ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("radiology-save-test-report")
	public @ResponseBody JsonResponse<Object> saveTestResult(HttpSession session, @RequestBody DropDownModel result) {
		logger.info("Method : saveTestResult starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		result.setCreatedBy(userId);
		result.setOrgName(orgName);
		result.setOrgDivision(orgDiv);

		if (result.getCode() != null && result.getCode() != "") {
			String imgUrl = null;
			try {
				byte[] bytes = docUtils.decodeBase64Image(result.getCode());
				String ext = docUtils.getImageExtension(result.getCode());
				imgUrl = docUtils.uploadImageToFolder2(bytes, ext, env.getFileUploadMaster(), result.getName());
			} catch (Exception e) {
				e.printStackTrace();
				resp.setCode("failed");
				resp.setMessage("There is some problem while uploading image");
				return resp;
			}
		}

		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-saveTestResult", result, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTestResult ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("radiology-approve-test-report")
	public @ResponseBody JsonResponse<Object> saveTestResult(HttpSession session,
			@RequestBody List<DropDownModel> result) {
		logger.info("Method : saveTestResult starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result.size() > 0) {
			for (DropDownModel m : result) {
				m.setCreatedBy(userId);
				m.setOrgName(orgName);
				m.setOrgDivision(orgDiv);
			}
		}

		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-approveTestResult", result, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTestResult ends");
		return resp;
	}

	@SuppressWarnings("unused")
	@GetMapping("pathologt-radiology")
	public String pathologtradiology(Model model, HttpSession session) {
		logger.info("Method : pathologtradiology starts");

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
			DropDownModel[] gender = restTemplate.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			DropDownModel[] country = restTemplate.getForObject(env.getHisUrl() + "/countryList",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);

			DropDownModel[] patient = restTemplate.getForObject(
					env.getHisUrl() + "/getAllPatientList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> patList = Arrays.asList(patient);
			model.addAttribute("patList", patList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] bankAccountList = restTemplate.getForObject(env.getAccountUrl()
					+ "getBankAccountPaymentList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restTemplate.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : pathologtradiology ends");
		return "his/pathologt-radiology";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("pathologt-radiology-view")
	public @ResponseBody Object viewPatientpathologtradiology(HttpSession session) {
		logger.info("Method :viewPatientpathologtradiology starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			System.out.println(env.getHisUrl() + "rest-viewIPDOPDlist?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId + "&type=RECEPTION");
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewIPDOPDlist?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&type=RECEPTION", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewPatientpathologtradiology ends" + resp);
		return resp;
	}

}
