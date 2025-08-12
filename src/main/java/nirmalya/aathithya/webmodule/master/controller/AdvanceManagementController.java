package nirmalya.aathithya.webmodule.master.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.AdvanceManagementModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;

@Controller

@RequestMapping(value = { "master/" })
public class AdvanceManagementController {
	Logger logger = LoggerFactory.getLogger(AdvanceManagementController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "advance-disburshment" })

	public String employeeAdvancee(Model model, HttpSession session) {
		logger.info("Method : employeeAdvancee starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		logger.info("userRole==="+userRole);
		try {
			DropDownModel[] requisition = restTemplate.getForObject(
					env.getMasterUrl() + "get-advance-policy-list?userId=" + userId + "&org=" + organization + "&orgDiv=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> policyList = Arrays.asList(requisition);
			model.addAttribute("policyList", policyList);
			System.out.println("policyList>>>>>>>>>>>>>>>>>>>>>"+policyList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Employee list
		try {
			DropDownModel[] empList = restTemplate.getForObject(env.getMasterUrl() + "getEmplistsForAdvanceManagement?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employeelist = Arrays.asList(empList);
			model.addAttribute("empList", employeelist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		// Payment mode list
		try {
			DropDownModel[] paymentMode = restTemplate.getForObject(env.getEmployeeUrl() + "getPaymentMode",
					DropDownModel[].class);
			List<DropDownModel> PayModeLists = Arrays.asList(paymentMode);

			model.addAttribute("PayModeLists", PayModeLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		//Year DropDown
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl()+ "getYearList-attendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get bank Name Details
		try {
			DropDownModel[] bankNames = restTemplate.getForObject(env.getEmployeeUrl() + "getBankNamesPay",
					DropDownModel[].class);
			List<DropDownModel> bankNameLists = Arrays.asList(bankNames);
			model.addAttribute("bankNameLists", bankNameLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol010")|| data.contentEquals("rol002")) {
				model.addAttribute("adRole", "admin");
			}
		}
 
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : employeeAdvancee ends");
		return "master/advanceManagement";
	}

	// for policy details onchange of policy type
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "/advance-disburshment-getPolicyDetails" })
		public @ResponseBody JsonResponse<Object> getStateLists(@RequestParam String reqPolicyId) {
			logger.info("Method : getPolicyDetails starts");
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getMasterUrl() + "rest-getPolicyDetails?reqPolicyId=" + reqPolicyId,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("success");
			} else {
				res.setMessage("unsuccess");
			}
			logger.info("res=====" + res);
			logger.info("Method : getPolicyDetails ends");
			return res;

		}
 
	/*
	 * //Main save for Advance apply
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/advance-disburshment-details-save")
	public @ResponseBody JsonResponse<Object> saveAdvanceApply(@RequestBody AdvanceManagementModel advanceModel,
			HttpSession session) {
		logger.info("Method : saveAdvanceApply starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";
		// String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			// dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		advanceModel.setCreatedBy(userId);
		advanceModel.setOrganization(organization);
		advanceModel.setOrgDivision(orgDivision);
		try {
			logger.info(env.getMasterUrl()+"saveAdvanceDetailsApply?"+advanceModel);
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveAdvanceDetailsApply", advanceModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAdvanceApply ends");
		logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrreturn" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("advance-disburshment-details-view")
	public @ResponseBody List<AdvanceManagementModel> viewAdvanceApply(HttpSession session,@RequestParam String userid,
			@RequestParam String roleid,@RequestParam String selftype) {

		logger.info("Method : viewAdvanceApply starts");
		JsonResponse<List<AdvanceManagementModel>> resp = new JsonResponse<List<AdvanceManagementModel>>();
			List<String> roleList = new ArrayList<String>();
			String organization=""; 
			String orgDivision="";
			try {
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (roleid != null && roleid != "") {
				String[] arr = roleid.split(",");
				for (int i = 0; i < arr.length; i++) {
					roleList.add(arr[i]);
				}
			}

			EmpRoleModel empModel = new EmpRoleModel();

			empModel.setUserId(userid);
			empModel.setUserRole(roleList);
			empModel.setType("WEB");
			empModel.setOrganization(organization);
			empModel.setOrgDivision(orgDivision);
			empModel.setSelfType(selftype);
			
			try {
				resp = restTemplate.postForObject(env.getMasterUrl() + "viewAdvanceApply" , empModel, 
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();
			List<AdvanceManagementModel> advanceapply = mapper.convertValue(resp.getBody(),
					new TypeReference<List<AdvanceManagementModel>>() {
					});
			String dateFormat = "";
			
			try {
				logger.info("#####"+(String) session.getAttribute("DATEFORMAT"));
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				if(dateFormat == null) {
					dateFormat = "yyyy-MM-dd";
				}
				
			} catch (Exception e) {
			}
			if (advanceapply != null)
				for (AdvanceManagementModel a : advanceapply) {
					if (a.getAdvanceApplyDate() != null && a.getAdvanceApplyDate() != "") {
						a.setAdvanceApplyDate(DateFormatter.dateFormat(a.getAdvanceApplyDate(), dateFormat));
					}
					if (a.getApprovedDate() != null && a.getApprovedDate() != "") {
						a.setApprovedDate(DateFormatter.dateFormat(a.getApprovedDate(), dateFormat));
					}
					if (a.getStatus().equals("0")) {
						a.setStatus("Pending");
					}else if (a.getStatus().equals("1")) {
						a.setStatus("Forwarded");
					}else if (a.getStatus().equals("2")) {
						a.setStatus("Rejected");
					} else if (a.getStatus().equals("3")) {
						a.setStatus("Approved");
					}else{
						a.setStatus("completed");
					}
				}
			logger.info("ADVANCEEEE VIEWWW" + advanceapply);
			logger.info("Method : viewAdvanceApply ends");
			return advanceapply;
		}
	// edit advance apply

	@SuppressWarnings("unchecked")
	@GetMapping("advance-disburshment-details-edit")
	public @ResponseBody JsonResponse<AdvanceManagementModel> editAdvanceApply(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editAdvanceApply starts");
		JsonResponse<AdvanceManagementModel> jsonResponse = new JsonResponse<AdvanceManagementModel>();
		logger.info("id====" + id);
		try {
			jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "editAdvanceApply?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		AdvanceManagementModel reimModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<AdvanceManagementModel>() {
				});
		jsonResponse.setBody(reimModel);
		/*
		 * if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		 * jsonResponse.setCode(jsonResponse.getMessage());
		 * jsonResponse.setMessage("Unsuccess"); } else {
		 * jsonResponse.setMessage("Success"); }
		 */
		logger.info("Method : editAdvanceApply ends");
		logger.info("editAdvanceApply=====" + jsonResponse);
		return jsonResponse;
	}

	// delete advance details table
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("advance-disburshment-details-delete") public @ResponseBody
	 * JsonResponse<AdvanceManagementModel> deleteAdvanceApply(@RequestParam String
	 * id) {
	 * 
	 * logger.info("Method : deleteAdvanceApply starts");
	 * JsonResponse<AdvanceManagementModel> response = new
	 * JsonResponse<AdvanceManagementModel>(); try { response =
	 * restTemplate.getForObject(env.getMasterUrl() + "deleteAdvanceApply?id=" + id,
	 * JsonResponse.class); } catch (RestClientException e) { e.printStackTrace(); }
	 * if (response.getMessage() != null && response.getMessage() != "") {
	 * response.setCode(response.getMessage()); response.setMessage("Unsuccess"); }
	 * else { response.setMessage("Success"); }
	 * logger.info("Method : deleteAdvanceApply ends"); return response; }
	 */
	
	
	
	@SuppressWarnings("unchecked")

	@GetMapping("advance-disburshment-details-delete")
	public @ResponseBody JsonResponse<AdvanceManagementModel> deleteAdvanceApply(@RequestParam String id) {

		logger.info("EEE" + id);

		JsonResponse<AdvanceManagementModel> jsonResponse = new JsonResponse<AdvanceManagementModel>();
		List<String> roleList = new ArrayList<String>();
		if (id != null && id != "") {
			String[] arr = id.split(",");
			for (int i = 0; i < arr.length; i++) {
				roleList.add(arr[i]);
			}
		}

		EmpRoleModel empModel = new EmpRoleModel();
		empModel.setUserRole(roleList);
		empModel.setType("WEB");

		try {

			logger.info("hiiii");
			// jsonResponse =restTemplate.getForObject(env.getEmployeeUrl() +
			// "deleteReimbursement?id=" + id, JsonResponse.class);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "deleteAdvanceApply", empModel,
					JsonResponse.class);
			logger.info("hiiii");

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AdvanceManagementModel> advanceManagementModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AdvanceManagementModel>>() {
				});

		if (jsonResponse.getCode().equals("success")) {
			jsonResponse.setMessage("Success");
		} else {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		}
		logger.info("Method : deleteAdvanceApply ends" + advanceManagementModel);
		return jsonResponse;

	}

	// approve leave

	@SuppressWarnings("unchecked")
	@GetMapping("advance-disburshment-details-approve")
	public @ResponseBody JsonResponse<AdvanceManagementModel> approveAdvanceApply(@RequestParam String approveId,
			String name, String comment,String roleid) {

		logger.info("Method : approveAdvanceApply starts");
		JsonResponse<AdvanceManagementModel> response = new JsonResponse<AdvanceManagementModel>();
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "approveAdvanceApply?id=" + approveId + "&name="
					+ name + "&comment=" + comment+"&roleid="+roleid, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if(response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : approveAdvanceApply ends");
		return response;
	}

	// reject leave

	@SuppressWarnings("unchecked")
	@GetMapping("advance-disburshment-details-reject")
	public @ResponseBody JsonResponse<AdvanceManagementModel> rejectAdvanceApply(@RequestParam String rejectId,
			String name, String comment) {

		logger.info("Method : rejectAdvanceApply starts");
		JsonResponse<AdvanceManagementModel> response = new JsonResponse<AdvanceManagementModel>();
		try {
			response = restTemplate.getForObject(
					env.getMasterUrl() + "rejectAdvanceApply?id=" + rejectId + "&name=" + name + "&comment=" + comment,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if(response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : rejectAdvanceApply ends");
		return response;
	}
	// view Process Deatils

	@SuppressWarnings("unchecked")
	@GetMapping("advance-disburshment-process-view")
	public @ResponseBody List<AdvanceManagementModel> viewAdvanceProcess(HttpSession session,
			@RequestParam String advanceId) {

		logger.info("Method : viewAdvanceProcess starts");
		JsonResponse<List<AdvanceManagementModel>> resp = new JsonResponse<List<AdvanceManagementModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewAdvanceProcess?advanceId=" + advanceId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AdvanceManagementModel> advanceprocess = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AdvanceManagementModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
		}
		if (advanceprocess != null)
			for (AdvanceManagementModel a : advanceprocess) {
				if (a.getAdvanceApplyDate() != null && a.getAdvanceApplyDate() != "") {
					a.setAdvanceApplyDate(DateFormatter.dateFormat(a.getAdvanceApplyDate(), dateFormat));
				}
				if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
					a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
				}
			}
		logger.info("viewAdvanceProcess===" + advanceprocess);
		logger.info("Method : viewAdvanceProcess ends");
		return advanceprocess;
	}

	/*
	 * //Process save for final approve
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/advance-disburshment-process-approve")
	public @ResponseBody JsonResponse<Object> saveAdvanceProcessApprove(
			@RequestBody AdvanceManagementModel advanceModel, HttpSession session) {
		logger.info("Method : saveAdvanceProcessApprove starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		advanceModel.setCreatedBy(userId);
		logger.info("advanceModel===" + advanceModel);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveAdvanceProcessApprove", advanceModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAdvanceProcessApprove ends");
		logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrreturn" + resp);
		return resp;
	}
	// view Emi Deatils

	@SuppressWarnings("unchecked")
	@GetMapping("advance-disburshment-view-emiDetails")
	public @ResponseBody List<AdvanceManagementModel> viewEmiDetails(HttpSession session, @RequestParam String advanceId,
			@RequestParam String empId) {

		logger.info("Method : viewEmiDetails starts");
		JsonResponse<List<AdvanceManagementModel>> resp = new JsonResponse<List<AdvanceManagementModel>>();

		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "viewEmiDetailss?advanceId=" + advanceId + "&empId=" + empId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AdvanceManagementModel> emi = mapper.convertValue(resp.getBody(), new TypeReference<List<AdvanceManagementModel>>() {
		});

		logger.info("viewEmiDetails===" + emi);
		logger.info("Method : viewEmiDetails ends");
		return emi;
	}
	//Edir Emi 
	@SuppressWarnings("unchecked")
	@GetMapping("advance-disburshment-edit-emi")
	public @ResponseBody Object editEmiData(@RequestParam String id,String date, HttpSession session) {
		logger.info("Method :editEmiData starts");
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

			resp = restTemplate.getForObject(env.getMasterUrl() + "editEmiData?id=" + id + "&date="
					+ date + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("edit>>>-----" + resp);
		logger.info("Method :editEmiData ends");
		return resp;
	}
	
	@GetMapping("advance-disburshment-modifyemi")
	public @ResponseBody Object modifyemi(@RequestParam String advanceId,String dueDate,String dueDateNew,String empId, HttpSession session) {

		logger.info("Method :modifyemi starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-modifyemi?advanceId=" + advanceId + "&dueDate="
					+ dueDate + "&dueDateNew=" + dueDateNew  + "&empId=" + empId + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	
	/*
	 * Employee autosearch
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "advance-disburshment-get-employee-list" })
	public @ResponseBody JsonResponse<DropDownModel> EmployeeAutoSearchForAttendance(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : EmployeeAutoSearchForAttendance starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			System.out.println("employee-autosearch-forAttendance?id=" + searchValue+"&org="+org+"&orgDiv="+orgDiv);
			res = restTemplate.getForObject(env.getMasterUrl() + "employee-autosearch-forAttendance?id=" + searchValue+"&org="+org+"&orgDiv="+orgDiv,
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
		logger.info("Method : EmployeeAutoSearchForAttendance ends"+res);
		return res;
	}

}
