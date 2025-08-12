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
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.master.model.LeaveApplyWebModel;

@Controller
@RequestMapping(value = "master/")
public class LeaveApplyWebController {
	Logger logger = LoggerFactory.getLogger(LeaveApplyWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("leave-apply")
	public String leaveApply(Model model, HttpSession session) {
		logger.info("Method : leaveApply starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);

		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol002") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", "admin");
				//System.out.println("data>>>>----" + data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010") || data.contentEquals("rol002")) {
				model.addAttribute("hrRole", data);
			}

		}

		// leave type list
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			DropDownModel[] leaveType = restClient.getForObject(env.getMasterUrl() + "getleavelists?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> leavelist = Arrays.asList(leaveType);
			model.addAttribute("leavelist", leavelist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		// Employee list
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			DropDownModel[] empList = restClient.getForObject(env.getMasterUrl() + "getEmplists?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employeelist = Arrays.asList(empList);
			model.addAttribute("empList", employeelist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		
		// logger.info("splitData=="+data);
		/*
		 * String roleid = ""; for(int i = 6; i <= userRole.length(); i=i+6){ if
		 * ((userRole.slice(i-6, i)).contentEquals("rol001") || (userRole.slice(i-6,
		 * i)).contentEquals("rol003")) { model.addAttribute("hrRole", userRole);
		 * 
		 * } }
		 */
		/*
		 * for(int i=0;i<array.length;i++) {
		 * logger.info("a+array[i]==="+userRole+array[i]); if ((userRole.slice(i-6,
		 * i)).contentEquals("rol001") || (userRole+array[i]).contentEquals("rol003")) {
		 * model.addAttribute("hrRole", userRole);
		 * 
		 * } }
		 */

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : leaveApply ends");
		return "master/leave-apply";
//		return "master/hrms-attendance";
	}

	// view leave apply

	@SuppressWarnings("unchecked")
	@GetMapping("leave-apply-view")
	public @ResponseBody List<LeaveApplyWebModel> viewLeaveApply(HttpSession session, @RequestParam String userid,
			@RequestParam String roleid,@RequestParam String selftype) {

		logger.info("Method : viewLeaveApply starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<LeaveApplyWebModel>> resp = new JsonResponse<List<LeaveApplyWebModel>>();

		List<String> roleList = new ArrayList<String>();

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
			resp = restClient.postForObject(env.getMasterUrl() + "viewleaveapply", empModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewLeaveApply ends");
		return resp.getBody();
	}

	/*
	 * Main save for leave apply
	 * 
	 */

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "leave-apply-save")
	public @ResponseBody JsonResponse<Object> saveLeaveApply(@RequestBody List<LeaveApplyWebModel> leaveModel,
			HttpSession session) {
		logger.info("Method : saveLeaveApply function starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (LeaveApplyWebModel m : leaveModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveleaveapply", leaveModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : saveLeaveApply function Ends");

		return resp;
	}

	// edit leave apply

	@SuppressWarnings("unchecked")
	@GetMapping("leave-apply-edit")
	public @ResponseBody List<LeaveApplyWebModel> editLeaveApply(@RequestParam String id, HttpSession session) {

		logger.info("Method : editLeaveApply starts");

		JsonResponse<List<LeaveApplyWebModel>> resp = new JsonResponse<List<LeaveApplyWebModel>>();
		try {

			resp = restClient.getForObject(env.getMasterUrl() + "editleaveapply?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<LeaveApplyWebModel> leaveapply = mapper.convertValue(resp.getBody(),
				new TypeReference<List<LeaveApplyWebModel>>() {
				});

		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "dd-MM-yyyy";
			}

		} catch (Exception e) {
		}
		int i = 0;
		for (LeaveApplyWebModel a : leaveapply) {
			i = i + 1;
			a.setRowId(i);

		}
		resp.setBody(leaveapply);

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : editLeaveApply ends");

		return leaveapply;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("leave-apply-view-details")
	public @ResponseBody JsonResponse<List<LeaveApplyWebModel>> viewLeaveApplynew(Model model, HttpSession session,
			@RequestParam String userid) {
		logger.info("Method : viewLeaveApplynew ");

		JsonResponse<List<LeaveApplyWebModel>> resp = new JsonResponse<List<LeaveApplyWebModel>>();
		
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "viewLeaveApplynew?userid=" + userid + "&org=" + organization + "&orgDiv=" + orgDivision,
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

		logger.info("Method : viewLeaveApplynew  ends");

		return resp;

	}
	@SuppressWarnings("unchecked")

	@GetMapping("leave-apply-edit-details")
	public @ResponseBody JsonResponse<List<LeaveApplyWebModel>> editLeaveApplynew(Model model, HttpSession session,
			@RequestParam String userid, @RequestParam String leaveId) {

		logger.info("Method : editLeaveApplynew ");

		JsonResponse<List<LeaveApplyWebModel>> resp = new JsonResponse<List<LeaveApplyWebModel>>();
System.err.println("editLeaveApplynew?userid=" + userid + "&leaveId=" + leaveId);
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editLeaveApplynew?userid=" + userid + "&leaveId=" + leaveId,
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

		logger.info("Method : editLeaveApplynew  ends");

		return resp;

	}
	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("leave-apply-delete")
	public @ResponseBody JsonResponse<Object> deleteleaveapply(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteleaveapply function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "deleteleaveapply?leaveId=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (res.getCode().equals("success")) {
			res.setMessage("Success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : deleteleaveapply function Ends");
		return res;
	}

	// approve leave

	@SuppressWarnings("unchecked")
	@GetMapping("leave-apply-approve")
	public @ResponseBody JsonResponse<LeaveApplyWebModel> approveLeaveApply(HttpSession session,
			@RequestParam String approveId, String name, String comment, String roleid, String dateid) {

		logger.info("Method : approveLeaveApply starts");
		JsonResponse<LeaveApplyWebModel> response = new JsonResponse<LeaveApplyWebModel>();
		try {
			response = restClient.getForObject(env.getMasterUrl() + "approveleaveapply?id=" + approveId + "&name="
					+ name + "&comment=" + comment + "&userRole=" + roleid + "&dateid=" + dateid, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : approveLeaveApply ends");
		return response;
	}

	// reject leave

	@SuppressWarnings("unchecked")
	@GetMapping("leave-apply-reject")
	public @ResponseBody JsonResponse<LeaveApplyWebModel> rejectLeaveApply(@RequestParam String rejectId, String name,
			String comment) {

		logger.info("Method : rejectLeaveApply starts");
		JsonResponse<LeaveApplyWebModel> response = new JsonResponse<LeaveApplyWebModel>();

		try {
			response = restClient.getForObject(
					env.getMasterUrl() + "rejectleaveapply?id=" + rejectId + "&name=" + name + "&comment=" + comment,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : rejectLeaveApply ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("leave-apply-approve-view")
	public @ResponseBody Object getLeaveApproveDates(@RequestParam String id, HttpSession session) {
		logger.info("Method :getLeaveApproveDates starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-leave-apply-approve-view?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getLeaveApproveDates ends");
		return resp;
	}

}