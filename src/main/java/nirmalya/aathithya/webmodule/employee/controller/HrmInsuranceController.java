package nirmalya.aathithya.webmodule.employee.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.EmployeeNomineeModel;
import nirmalya.aathithya.webmodule.his.controller.HisReceptionController;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;

@Controller
@RequestMapping(value = "employee")
public class HrmInsuranceController {
	Logger logger = LoggerFactory.getLogger(HrmInsuranceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/insurance")
	public String insurance(Model model, HttpSession session) {

		logger.info("Method : insurance starts");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
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

		logger.info("Method : insurance ends");

		return "employee/hrms-insurance.html";

	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("insurance-view")
	public @ResponseBody Object viewEmployee(HttpSession session, @RequestParam String type) {
		logger.info("Method :viewEmployee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-insurance-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&type=" + type + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewEmployee ends" + resp);
		return resp;
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("insurance-details-edit")
	public @ResponseBody Object editInsurance(@RequestParam String Id, HttpSession session) {
		logger.info("Method :editInsurance starts" + Id);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-edit-insurance?Id=" + Id + "&organization="
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
		logger.info("Method :editInsurance ends");
		return resp;
	}

	// save-nomine-details
	@SuppressWarnings("unchecked")
	@PostMapping("insurance-nominee-details")
	public @ResponseBody JsonResponse<Object> saveNomine(@RequestBody EmployeeNomineeModel employeeNomineeModel,
			HttpSession session) {
		logger.info("Method: saveNomine starts" + employeeNomineeModel);

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
		employeeNomineeModel.setCreatedBy(userId);
		employeeNomineeModel.setOrganization(orgName);
		employeeNomineeModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "rest-insurance-nominee-details",
					employeeNomineeModel, JsonResponse.class);

			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setMessage("Error during REST call");
		}

		logger.info("Method: saveNomine ends");

		return resp;
	}

	// view nomine

	@SuppressWarnings("unchecked")
	@GetMapping("/insurance-nominee-view")
	public @ResponseBody Object viewNomine(HttpSession session, @RequestParam String employeeId) {
		logger.info("Method :viewNomine starts");
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
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-view-Nomine?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&employeeId=" + employeeId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :viewNomine ends" + resp);
		return resp;
	}

	// edit nomine

	@SuppressWarnings("unchecked")
	@GetMapping("insurance-hrms-details-edit")
	public @ResponseBody Object editNomine(@RequestParam String id, HttpSession session) {
		logger.info("Method :editNomine starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-hrms-insurance-details-edit?id=" + id
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editNomine ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/insurance-name-view")
	public @ResponseBody Object viewInsurance(HttpSession session, @RequestParam String id) {
		logger.info("Method :viewInsurance starts");
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
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-view-insurance?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :viewInsurance ends" + resp);
		return resp;
	}

	// add

	@SuppressWarnings("unchecked")

	@PostMapping("insurance-details-add")
	public @ResponseBody JsonResponse<Object> addInsurance(@RequestBody EmployeeNomineeModel employeeNomineeModel,
			HttpSession session) {
		logger.info("Method: addInsurance starts" + employeeNomineeModel);

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
		employeeNomineeModel.setCreatedBy(userId);
		employeeNomineeModel.setOrganization(orgName);
		employeeNomineeModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "rest-insurance-details-add", employeeNomineeModel,
					JsonResponse.class);
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setMessage("Error during REST call");
		}

		logger.info("Method: addInsurance ends");

		return resp;
	}

	// delete
	@SuppressWarnings({ "unchecked" })
	@GetMapping("insurance-nomine-details-delete")
	public @ResponseBody JsonResponse<Object> deleteNomine(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteNomine starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-insurance-nomine-details-delete?org="
					+ organization + "&orgDiv=" + orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteNomine ends");
		return res;
	}

}
