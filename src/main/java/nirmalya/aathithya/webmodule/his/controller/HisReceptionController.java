package nirmalya.aathithya.webmodule.his.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
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
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;
import nirmalya.aathithya.webmodule.his.model.HisBookingAmbulanceModel;

@Controller
@RequestMapping(value = "his")
public class HisReceptionController {
	
	Logger logger = LoggerFactory.getLogger(HisReceptionController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/reception")
	public String reception(Model model, HttpSession session) {

		logger.info("Method : reception starts");

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
			DropDownModel[] gender = restTemplate.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			// Fetch marital status data
			DropDownModel[] marital = restTemplate.getForObject(env.getHisUrl() + "/maritalstatusList",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(marital);
			model.addAttribute("maritalstatusList", maritalstatusList);

			// Fetch nationality data
			DropDownModel[] nationality = restTemplate.getForObject(env.getHisUrl() + "/nationalityList",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(nationality);
			model.addAttribute("nationalityList", nationalityList);

			DropDownModel[] country = restTemplate.getForObject(env.getHisUrl() + "/countryList",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);

			String actId = (String) session.getAttribute("OPD");
			System.out.println("activity id is coming ======================> " + actId);

			// Fetch department data
			DropDownModel[] department = restTemplate.getForObject(env.getHisUrl() + "/departmentList",
					DropDownModel[].class);
			List<DropDownModel> departmentList = Arrays.asList(department);
			model.addAttribute("departmentList", departmentList);

			// patient relation list
			DropDownModel[] relation = restTemplate.getForObject(env.getHisUrl() + "/getRelationList",
					DropDownModel[].class);
			List<DropDownModel> getRelationList = Arrays.asList(relation);
			logger.info("getRelationList" + getRelationList);
			model.addAttribute("getRelationList", getRelationList);

		} catch (RestClientException e) {
			// Handle RestClientException
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
		
		try {
			DropDownModel[] costCenter = restTemplate.getForObject(env.getHisUrl()+ "/getAllPatientList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> patList = Arrays.asList(costCenter);
			model.addAttribute("patList", patList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : reception ends");

		return "his/his-reception.html";

	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("reception-getPatientDetailsById")
	public @ResponseBody Object getPatientDetailsById(HttpSession session, @RequestParam String id) {
		logger.info("Method :getPatientDetailsById starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			resp = restTemplate.getForObject(env.getHisUrl() + "getPatientDetailsById?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getPatientDetailsById ends" + resp);
		return resp;
	}

	// GetState List

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "reception-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "getPatientSateList?id=" + id, JsonResponse.class);
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
		logger.info("Method : getstateList ends");
		return res;
	}

	// districtList

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "reception-districtList" })
	public @ResponseBody JsonResponse<Object> districtList(@RequestParam String id) {
		logger.info("Method : districtList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "districtList?id=" + id, JsonResponse.class);
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
		logger.info("Method : districtList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "reception-doctorList" })
	public @ResponseBody JsonResponse<Object> getDoctorList(HttpSession session, @RequestParam String from,
			@RequestParam String deptId) {
		logger.info("Method : getDoctorList starts" + from);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			res = restTemplate
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

	// manage-patient-feeList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "reception-feeList" })
	public @ResponseBody JsonResponse<Object> feeList(HttpSession httpSession, @RequestParam String id,
			@RequestParam String dateOfAppoints) {
		logger.info("Method : feeList starts==================>" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "feeList?id=" + id + "&dateOfAppoints=" + dateOfAppoints,
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

	// add
	@SuppressWarnings("unchecked")
	@PostMapping("/reception-add")
	public @ResponseBody JsonResponse<Object> addPatient(@RequestBody HisBookingAmbulanceModel patientModel,
			HttpSession session) {
		logger.info("Method: addPatient starts");
		logger.info("Method: addPatient dataaa====" + patientModel);

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
			resp = restTemplate.postForObject(env.getMasterUrl() + "restuserregsandproductbooking", patientModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setCode(resp.getCode());
		resp.setMessage(resp.getMessage());
		resp.setBody(resp.getBody());
		logger.info("Method: addPatient ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/reception-delete-child-data")
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "restdeletechilddata", data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method: deleteDepChildData ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/reception-add-dep-data")
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "restaddchilddata", data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method: addDepChildData ends" + resp);
		return resp;
	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("reception-view")
	public @ResponseBody Object viewPatient(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String from, HttpSession session) {
		logger.info("Method :viewPatient starts" + fromDate + toDate);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate
					.getForObject(
							env.getHisUrl() + "rest-viewPatient?orgName=" + orgName + "&orgDivision=" + orgDivision
									+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&from=" + from,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewPatient ends" + resp);
		return resp;
	}

	// edit

	@SuppressWarnings("unchecked")
	@GetMapping("reception-edit")
	public @ResponseBody Object editPatient(@RequestParam String id, HttpSession session) {
		logger.info("Method :editPatient starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "editPatient?id=" + id + "&organization=" + orgName
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
		logger.info("Method :editPatient ends");
		return resp;
	}

	// reception-opd-city-list
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "reception-opd-city-list" })
	public @ResponseBody JsonResponse<Object> CityList(@RequestParam String id) {
		logger.info("Method : CityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "opd-recep-city-list?id=" + id, JsonResponse.class);
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
	@GetMapping(value = { "reception-department-list" })
	public @ResponseBody JsonResponse<Object> getDepartmentList(HttpSession session, @RequestParam String id) {
		logger.info("Method : department  list starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			res = restTemplate.getForObject(
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
}
