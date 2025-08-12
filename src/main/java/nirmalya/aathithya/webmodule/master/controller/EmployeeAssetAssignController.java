package nirmalya.aathithya.webmodule.master.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.core.type.TypeReference;
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
import nirmalya.aathithya.webmodule.master.model.EmployeeAssetAssignModel;

@Controller
@RequestMapping(value = "master/")
public class EmployeeAssetAssignController {

	Logger logger = LoggerFactory.getLogger(EmployeeAssetAssignController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("employee-asset-assign")
	public String leaveApply(Model model, HttpSession session) {
		logger.info("Method : emp asset assign starts");
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

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl() + "rest-getassetlist",
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String userId = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
				logger.info("data ==" + data);
			} else if (data.contentEquals("rol003")) {
				model.addAttribute("mrRole", data);
				logger.info("data 1==" + data);
			} else {
				model.addAttribute("empRole", data);
				logger.info("data 2==" + data);
			}
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userRole", userRole);

		logger.info("Method :emp asset assign  ends");
		return "master/employeeassetassign";
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "employee-asset-assign-getSubDepartmentByDept" })
	public @ResponseBody JsonResponse<Object> getSubDepartmentByDept(HttpSession session,
			@RequestParam String shiftDeptId) {
		logger.info("Method : getSubDepartmentByDept starts");
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
			res = restTemplate.getForObject(env.getMasterUrl() + "get-shiftSubDepartment?shiftDeptId=" + shiftDeptId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getSubDepartmentByDept ends");

		logger.info("LISTTTT" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("employee-asset-assign-get-employeeid")
	public @ResponseBody JsonResponse<List<DropDownModel>> getEmpId(Model model, @RequestParam String subDeptId,
			HttpSession session) {
		logger.info("Method : getEmpId starts");

		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getMasterUrl() + "get-EmployeeId?subDeptId=" + subDeptId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method :  getEmpId ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("employee-asset-assign-getEmployeeList")
	public @ResponseBody JsonResponse<List<DropDownModel>> getEmployeeLists(Model model,HttpSession session) {
		logger.info("Method : getEmployeeLists starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "get-employee-list?orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method :  getEmployeeLists ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "employee-asset-assign-nameDesignation" })
	public @ResponseBody JsonResponse<Object> getnameAndDesignationList(HttpSession session,@RequestParam String empid) {
		logger.info("Method : getnameAndDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-nameandDesignationList?id=" + empid+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getnameAndDesignationList ends");

		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("employee-asset-assign-savedata")
	public @ResponseBody JsonResponse<Object> saveInsuranceDetailsList(
			@RequestBody EmployeeAssetAssignModel EmployeeAssetAssignModel, HttpSession session) {
		logger.info("Method: Education save starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		EmployeeAssetAssignModel.setCreatedby(userId);
		EmployeeAssetAssignModel.setOrganization(organization);
		EmployeeAssetAssignModel.setOrgDivision(orgDivision);

		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "employee-asset-assign-savedata-master",
					EmployeeAssetAssignModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method: education save ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("employee-asset-assign-view")
	public @ResponseBody List<EmployeeAssetAssignModel> viewAssetAssignDetails(HttpSession session,
			@RequestParam String userid, @RequestParam String roleid) {
		logger.info("Method : view Employee asset assign starts");

		JsonResponse<List<EmployeeAssetAssignModel>> resp = new JsonResponse<List<EmployeeAssetAssignModel>>();
		List<EmployeeAssetAssignModel> returnList = new ArrayList<EmployeeAssetAssignModel>();
		logger.info("rssssssssssssssss:" + returnList);
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate
					.getForObject(
							env.getMasterUrl() + "employee-asset-assign-view?userid=" + userid + "&roleid=" + roleid
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
							JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewEmployee asset assign ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("employee-asset-assign-edit")
	public @ResponseBody JsonResponse<EmployeeAssetAssignModel> editAssetAssign(Model model,
			@RequestParam String empAssetId, HttpSession session) {

		logger.info("Method : editDistrict starts");

		JsonResponse<EmployeeAssetAssignModel> jsonResponse = new JsonResponse<EmployeeAssetAssignModel>();
		logger.info(empAssetId);
		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "employee-asset-assign-edit?empAssetId=" + empAssetId, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			EmployeeAssetAssignModel EmployeeAssetAssignModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<EmployeeAssetAssignModel>() {
					});

			jsonResponse.setBody(EmployeeAssetAssignModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editDistrict ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("employee-asset-assign-delete")
	public @ResponseBody JsonResponse<EmployeeAssetAssignModel> deleteFuel(Model model, @RequestParam String deleteId,

			HttpSession session) {
		logger.info("Method : deleteEmployee starts");
		logger.info("@@@@@" + deleteId);
		JsonResponse<EmployeeAssetAssignModel> resp = new JsonResponse<EmployeeAssetAssignModel>();
		logger.info(deleteId);

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "employee-asset-assign-delete?deleteId=" + deleteId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :  deleteEmployee ends");

		logger.info("resp" + resp);
		return resp;
	}

}
