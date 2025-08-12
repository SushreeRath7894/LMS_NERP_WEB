package nirmalya.aathithya.webmodule.employee.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.InsuranceAndNomineeWebModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;

@Controller
@RequestMapping(value = { "employee/" })
public class InsuranceAndNomineeWebController {
	Logger logger = LoggerFactory.getLogger(InsuranceAndNomineeWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/insurance-nominee")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : InsuranceAndNominee starts");

		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] relationship = restClient.getForObject(env.getEmployeeUrl() + "relationshipList",
					DropDownModel[].class);
			List<DropDownModel> relationshipList = Arrays.asList(relationship);

			model.addAttribute("relationshipList", relationshipList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] name = restClient.getForObject(env.getEmployeeUrl() + "nameList?userId=" + userId,
					DropDownModel[].class);
			List<DropDownModel> nameList = Arrays.asList(name);
			logger.info("NAMEEEE" + nameList);
			model.addAttribute("nameList", nameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : InsuranceAndNominee ends");
		return "employee/insuranceAndNominee";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("insurance-nominee-details")
	public @ResponseBody List<InsuranceAndNomineeWebModel> viewInsuranceAndNominee(HttpSession session,
			@RequestParam String userid) {

		logger.info("Method : viewInsuranceAndNominee starts");
		JsonResponse<List<InsuranceAndNomineeWebModel>> resp = new JsonResponse<List<InsuranceAndNomineeWebModel>>();

		// List<String> roleList = new ArrayList<String>();
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "viewInsuranceAndNominee", empModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewInsuranceAndNominee ends");
		return resp.getBody();
	}

	@SuppressWarnings("unchecked")

	@GetMapping("insurance-nominee-view-empDetails")
	public @ResponseBody JsonResponse<List<InsuranceAndNomineeWebModel>> viewEmpDetails(Model model,
			HttpSession session, @RequestParam String userid) {

		logger.info("Method : viewEmpDetails ");

		JsonResponse<List<InsuranceAndNomineeWebModel>> resp = new JsonResponse<List<InsuranceAndNomineeWebModel>>();

		try {
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "viewEmpDetails?userid=" + userid,
					JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("unsuccess");
		}

		logger.info("Method : viewEmpDetails  ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp);

		logger.info("VIEWWWW" + resp);

		return resp;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("insurance-nominee-employeeDeclared-save")
	public @ResponseBody JsonResponse<Object> saveEmployeeDtls(
			@RequestBody InsuranceAndNomineeWebModel insuranceAndNomineeWebModel, HttpSession session) {
		logger.info("Method : saveEmployeeDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		insuranceAndNomineeWebModel.setEmpId(userId);
		insuranceAndNomineeWebModel.setOrganization(organization);
		insuranceAndNomineeWebModel.setOrgDivision(orgDivision);

		if (insuranceAndNomineeWebModel.getEmpDob() != null && insuranceAndNomineeWebModel.getEmpDob() != "") {
			insuranceAndNomineeWebModel
					.setEmpDob(DateFormatter.inputDateFormat(insuranceAndNomineeWebModel.getEmpDob(), dateFormat));
		}
		try {

			resp = restClient.postForObject(env.getEmployeeUrl() + "saveEmployeeDtls", insuranceAndNomineeWebModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
			session.removeAttribute("employeePFile");
		} else {

		}
		logger.info("Method : saveEmployeeDtls End");
		logger.info("ADDDDD" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("insurance-nominee-declarationDetails")
	public @ResponseBody List<InsuranceAndNomineeWebModel> viewInsuranceDeclaration(HttpSession session,
			@RequestParam String userid) {

		logger.info("Method : viewInsuranceDeclaration starts");
		JsonResponse<List<InsuranceAndNomineeWebModel>> resp = new JsonResponse<List<InsuranceAndNomineeWebModel>>();

		// List<String> roleList = new ArrayList<String>();
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "viewInsuranceDeclaration", empModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewInsuranceDeclaration ends");
		return resp.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "insurance-nominee-viewDobRelation" })
	public @ResponseBody JsonResponse<Object> viewDobRelation(HttpSession session, @RequestParam String name) {
		logger.info("Method : viewDobRelation starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "get-viewDobRelation?id=" + name,
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

		logger.info("Method : viewDobRelation ends");

		logger.info("LISTTTT" + res);
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("insurance-nominee-NomineeDetails-save")
	public @ResponseBody JsonResponse<Object> saveNomineeDetails(
			@RequestBody InsuranceAndNomineeWebModel insuranceAndNomineeWebModel, HttpSession session) {
		logger.info("Method : saveNomineeDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		insuranceAndNomineeWebModel.setEmpId(userId);
		insuranceAndNomineeWebModel.setOrganization(organization);
		insuranceAndNomineeWebModel.setOrgDivision(orgDivision);

		if (insuranceAndNomineeWebModel.getEmpDob() != null && insuranceAndNomineeWebModel.getEmpDob() != "") {
			insuranceAndNomineeWebModel
					.setEmpDob(DateFormatter.inputDateFormat(insuranceAndNomineeWebModel.getEmpDob(), dateFormat));
		}
		try {

			resp = restClient.postForObject(env.getEmployeeUrl() + "saveNomineeDetails", insuranceAndNomineeWebModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
			session.removeAttribute("employeePFile");
		} else {

		}
		logger.info("Method : saveNomineeDetails End");
		logger.info("ADDDDD" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("insurance-nominee-NomineeDetails")
	public @ResponseBody List<InsuranceAndNomineeWebModel> viewInsuranceNomineeDetails(HttpSession session,
			@RequestParam String insuid) {

		logger.info("Method : viewInsuranceNomineeDetails starts");
		JsonResponse<List<InsuranceAndNomineeWebModel>> resp = new JsonResponse<List<InsuranceAndNomineeWebModel>>();

		// List<String> roleList = new ArrayList<String>();
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(insuid);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "viewInsuranceNomineeDetails", empModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewInsuranceNomineeDetails ends");
		return resp.getBody();
	}

	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("insurance-nominee-deleteDeclaration")
	public @ResponseBody JsonResponse<Object> deleteDeclaration(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteDeclaration function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "deleteDeclaration?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDeclaration function Ends");

		logger.info("deleteDeclaration" + res);
		return res;
	}

	// delete

	@SuppressWarnings("unchecked")
	@GetMapping("insurance-nominee-deleteNominee")
	public @ResponseBody JsonResponse<Object> deleteNominee(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteNominee function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "deleteNominee?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteNominee function Ends");

		logger.info("deleteNominee" + res);
		return res;
	}

	// Edit Details
	@SuppressWarnings("unchecked")
	@GetMapping("insurance-nominee-editDeclaration")
	public @ResponseBody JsonResponse<InsuranceAndNomineeWebModel> editDeclaration(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editDeclaration starts");
		JsonResponse<InsuranceAndNomineeWebModel> jsonResponse = new JsonResponse<InsuranceAndNomineeWebModel>();
		logger.info("id====" + id);
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "rest-editDeclaration?id=" + id

					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editDeclaration ends");
		logger.info("editDeclaration=====" + jsonResponse);
		return jsonResponse;
	}

	// Edit Details

	@SuppressWarnings("unchecked")
	@GetMapping("insurance-nominee-editNominee")
	public @ResponseBody JsonResponse<InsuranceAndNomineeWebModel> editNominee(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editNominee starts");
		JsonResponse<InsuranceAndNomineeWebModel> jsonResponse = new JsonResponse<InsuranceAndNomineeWebModel>();
		logger.info("id====" + id);
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "rest-editNominee?id=" + id

					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editNominee ends");
		logger.info("editNominee=====" + jsonResponse);
		return jsonResponse;
	}

	// check new Available
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/insurance-nominee-checkNewAvailable" })
	public @ResponseBody JsonResponse<Object> checkNewAvailable(@RequestParam String financialYear,
			HttpSession session) {
		logger.info("Method : checkNewAvailable starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-checkNewAvailable?userId=" + userId + "&financialYear="
									+ financialYear + "&organization=" + organization + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setCode(res.getMessage());
			res.setMessage("Success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : checkNewAvailable ends");
		logger.info("CHECKKKKK" + res);
		return res;
	}

	// getDeclareName
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "insurance-nominee-getDeclareName" })
	public @ResponseBody JsonResponse<Object> getDeclareName(HttpSession session, @RequestParam String insuId) {
		logger.info("Method : getDeclareName starts");
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
			res = restTemplate.getForObject(env.getEmployeeUrl() + "getDeclareName?insuId=" + insuId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getDeclareName ends");
		logger.info("LISTTTT" + res);
		return res;
	}

}