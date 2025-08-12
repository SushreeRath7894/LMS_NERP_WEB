package nirmalya.aathithya.webmodule.ticket.controller;

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

import nirmalya.aathithya.webmodule.asset.model.AssetPolicyModel;
import nirmalya.aathithya.webmodule.asset.model.AssetViewMasterModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.ticket.model.DigitalLogBookModel;

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "ticket/")
public class DigitalLogBookController {

	Logger logger = LoggerFactory.getLogger(DigitalLogBookController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("logbook")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : reimbursement starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] shift = restTemplate.getForObject(env.getProduction() + "getShiftListForProduction?org="
					+ org + "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : reimbursement ends");
		return "ticket/logbook";
	}

	// add

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "logbook-add" })
	public @ResponseBody JsonResponse<Object> addLogBook(@RequestBody List<AssetPolicyModel> av, HttpSession session) {
		logger.info("Method : addLogBook function starts" + av);
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
		for (AssetPolicyModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		try {
			resp = restTemplate.postForObject(env.getTicketUrl() + "rest-logbook-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addLogBook function Ends" + resp);
		return resp;
	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("logbook-view")
	public @ResponseBody Object viewLogbook(HttpSession session) {
		logger.info("Method :viewLogbook starts");
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
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-logbook-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewLogbook ends");
		return resp;
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("logbook-edit")
	public @ResponseBody Object editLogbook(@RequestParam String id, HttpSession session) {
		logger.info("Method :editLogbook starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-logbook-edit?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editLogbook ends");
		return resp;
	}

	// delete

	@SuppressWarnings("unchecked")
	@PostMapping("logbook-delete")
	public @ResponseBody JsonResponse<Object> deleteLogbook(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteLogbook function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTicketUrl() + "rest-logbook-delete?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteLogbook function Ends");
		return res;
	}

	// approve

	@SuppressWarnings("unchecked")
	@PostMapping("logbook-approve")
	public @ResponseBody JsonResponse<Object> approveLogbook(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approveLogbook function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTicketUrl() + "rest-logbook-approve?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {

		}
		logger.info("Method : approveLogbook function Ends");
		return res;
	}
//	ELCB LOGBOOK //

	@GetMapping("elcb")
	public String elcbDATA(Model model, HttpSession session) {
		logger.info("Method : elcbDATA starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] elcbType1 = restTemplate.getForObject(env.getTicketUrl() + "rest-elcbTypeList?org=" + org
					+ "&orgDiv=" + orgDiv , DropDownModel[].class);
			List<DropDownModel> elcbType = Arrays.asList(elcbType1);

			model.addAttribute("elcbType", elcbType);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		

		logger.info("Method : elcbDATA ends");
		return "ticket/elcb-logbook";
	}
	// add

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "elcb-add" })
	public @ResponseBody JsonResponse<Object> addelcbLogBook(@RequestBody List<DigitalLogBookModel> av,
			HttpSession session) {
		logger.info("Method : addLogBook function starts" + av);
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
		for (DigitalLogBookModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		try {
			resp = restTemplate.postForObject(env.getTicketUrl() + "rest-elcblogBook-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addelcbLogBook function Ends" + resp);
		return resp;
	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("elcb-view")
	public @ResponseBody Object viewelcbLogbook(HttpSession session) {
		logger.info("Method :viewelcbLogbook starts");
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
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-elcb-logbook-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewelcbLogbook ends");
		return resp;
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("elcb-edit")
	public @ResponseBody Object editelcbLogbook(@RequestParam String id, HttpSession session) {
		logger.info("Method :editelcbLogbook starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-elcblogbook-edit?id=" + id + "&orgName="
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
		logger.info("Method :editelcbLogbook ends");
		return resp;
	}

	//
	@SuppressWarnings("unchecked")
	@PostMapping("elcb-delete")
	public @ResponseBody JsonResponse<Object> deleteElcb(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteElcb function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTicketUrl() + "rest-elcb-delete?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteElcb function Ends");
		return res;
	}

	// approve

	@SuppressWarnings("unchecked")
	@PostMapping("elcb-approve")
	public @ResponseBody JsonResponse<Object> approveElcb(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : approveElcb function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTicketUrl() + "rest-elcb-approve?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {

		}
		logger.info("Method : approveElcb function Ends");
		return res;
	}
// WASTE OIL TRACKING RECORD //

	@GetMapping("oilTracking")
	public String oilTrackingDATA(Model model, HttpSession session) {
		logger.info("Method : elcbDATA starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);

		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol051") || data.contentEquals("rol052")
					|| data.contentEquals("rol053")) {
				model.addAttribute("aprvRole", "aprvRole");

			}

		}

		try {
			DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : elcbDATA ends");
		return "ticket/wasteoil-trackingRecord";
	}
	// add

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "oilTracking-add" })
	public @ResponseBody JsonResponse<Object> addOilTracking(@RequestBody List<DigitalLogBookModel> av,
			HttpSession session) {
		logger.info("Method : addOilTracking function starts" + av);
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
		for (DigitalLogBookModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		try {
			resp = restTemplate.postForObject(env.getTicketUrl() + "rest-oilTracking-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addOilTracking function Ends" + resp);
		return resp;
	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("oilTracking-view")
	public @ResponseBody Object viewOilTracking(HttpSession session) {
		logger.info("Method :viewOilTracking starts");
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
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-oilTracking-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewOilTracking ends");
		return resp;
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("oilTracking-edit")
	public @ResponseBody Object editOilTracking(@RequestParam String id, HttpSession session) {
		logger.info("Method :editOilTracking starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-oilTracking-edit?id=" + id + "&orgName="
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
		logger.info("Method :editOilTracking ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("oilTracking-delete")
	public @ResponseBody JsonResponse<Object> deleteOilTracking(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteOilTracking function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getTicketUrl() + "rest-oilTracking-delete?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteOilTracking function Ends");
		return res;
	}

	// approve

	@SuppressWarnings("unchecked")
	@PostMapping("oilTracking-approve")
	public @ResponseBody JsonResponse<Object> approveOilTracking(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approveOilTracking function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getTicketUrl() + "rest-oilTracking-approve?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {

		}
		logger.info("Method : approveOilTracking function Ends");
		return res;
	}
// INITIATION/COMPLETION OF NON-ROUTINE ACTIVITY

	@GetMapping("nonroutine-activity")
	public String nonroutineRecord(Model model, HttpSession session) {
		logger.info("Method : elcbDATA starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);

		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol051") || data.contentEquals("rol052")
					|| data.contentEquals("rol053")) {
				model.addAttribute("aprvRole", "aprvRole");

			}

		}
		try {
			DropDownModel[] departmentList = restClient.getForObject(
					env.getTicketUrl() + "get-department-list?org=" + org + "&orgDiv=" + orgDiv + "&userId=" + userId,
					DropDownModel[].class);
			List<DropDownModel> deptList = Arrays.asList(departmentList);

			model.addAttribute("deptList", deptList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : elcbDATA ends");
		return "ticket/completion-nonroutineRecord";
	}

//
	@SuppressWarnings("unchecked")
	@GetMapping("nonroutine-activity-child1-view")
	public @ResponseBody Object viewActivity1(HttpSession session) {
		logger.info("Method :viewActivity1 starts");
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
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-viewActivity1?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewActivity1 ends");
		return resp;
	}

//
	@SuppressWarnings("unchecked")
	@GetMapping("nonroutine-activity-child2-view")
	public @ResponseBody Object viewActivity2(HttpSession session) {
		logger.info("Method :viewActivity2 starts");
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
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-viewActivity2?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewActivity2 ends");
		return resp;
	}

//
	@SuppressWarnings("unchecked")
	@GetMapping("nonroutine-activity-view")
	public @ResponseBody Object viewcomplitionRoutine(HttpSession session) {
		logger.info("Method :viewcomplitionRoutine starts");
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
			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-viewcomplitionRoutine?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewcomplitionRoutine ends");
		return resp;
	}

//
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "nonroutine-activity-add" })
	public @ResponseBody JsonResponse<Object> activityAdd(@RequestBody List<DigitalLogBookModel> av,
			HttpSession session) {
		logger.info("Method : activityAdd function starts" + av);
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
		for (DigitalLogBookModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		try {
			resp = restTemplate.postForObject(env.getTicketUrl() + "rest-activityAdd", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : activityAdd function Ends" + resp);
		return resp;
	}

//
	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("nonroutine-activity-edit")
	public @ResponseBody Object editActivity(@RequestParam String id, HttpSession session) {
		logger.info("Method :editActivity starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-activity-edit?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editActivity ends");
		return resp;
	}

	// delete

	@SuppressWarnings("unchecked")
	@PostMapping("nonroutine-activity-delete")
	public @ResponseBody JsonResponse<Object> deleteActivity(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteActivity function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTicketUrl() + "rest-activity-delete?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteActivity function Ends");
		return res;
	}

	// approve

	@SuppressWarnings("unchecked")
	@PostMapping("nonroutine-activity-approve")
	public @ResponseBody JsonResponse<Object> approveActivity(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approveActivity function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getTicketUrl() + "rest-activity-approve?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {

		}
		logger.info("Method : approveActivity function Ends");
		return res;
	}

//RW and BR Logbook

	@GetMapping("RWandBR")
	public String RWandBR(Model model, HttpSession session) {
		logger.info("Method : RWandBR starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] shift = restTemplate.getForObject(env.getProduction() + "getShiftListForProduction?org="
					+ org + "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : RWandBR ends");
		return "ticket/RWandBR";
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "RWandBR-details-add" })
	public @ResponseBody JsonResponse<Object> addRWandBRLogBook(@RequestBody List<AssetPolicyModel> av,
			HttpSession session) {
		logger.info("Method : addRWandBRLogBook function starts" + av);
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
		for (AssetPolicyModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getTicketUrl() + "rest-RWandBR-details-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addRWandBRLogBook function Ends" + resp);
		return resp;
	}
	
	// view

	@SuppressWarnings("unchecked")

	@GetMapping("RWandBR-details-view")
	public @ResponseBody Object viewRWandBRLogbook(HttpSession session) {
		logger.info("Method :viewRWandBRLogbook starts");
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
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-RWandBR-details-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewRWandBRLogbook ends");
		return resp;
	}


	// delete

	@SuppressWarnings("unchecked")
	@PostMapping("RWandBR-details-delete")
	public @ResponseBody JsonResponse<Object> deleteRWandBRLogbook(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteRWandBRLogbook function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTicketUrl() + "rest-RWandBR-details-delete?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteRWandBRLogbook function Ends");
		return res;
	}

	// approve

	@SuppressWarnings("unchecked")
	@PostMapping("RWandBR-details-approve")
	public @ResponseBody JsonResponse<Object> approveRWandBRLogbook(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approveRWandBRLogbook function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTicketUrl() + "rest-RWandBR-details-approve?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {

		}
		logger.info("Method : approveRWandBRLogbook function Ends");
		return res;
	}
	
	///////////////////////////////////////////RW and BR Logbook////////////////////////////////////////////////////////////

		@GetMapping("WaterReadingRecord")
		public String WaterReadingRecord(Model model, HttpSession session) {
			logger.info("Method : RWandBR starts");

			String userId = "";
			String userName = "";
			String userRole = "";
			String org = "";
			String orgDiv = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				userName = (String) session.getAttribute("USER_NAME");
				userRole = (String) session.getAttribute("USER_ROLES_STRING");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			try {
				DropDownModel[] shift = restTemplate.getForObject(env.getProduction() + "getShiftListForProduction?org="
						+ org + "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
				List<DropDownModel> shiftLists = Arrays.asList(shift);

				model.addAttribute("shiftLists", shiftLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			try {
				DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
						+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
				List<DropDownModel> empLists = Arrays.asList(emp);

				model.addAttribute("empLists", empLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : WaterReadingRecord ends");
			return "ticket/WaterReadingRecord";
		}

		@SuppressWarnings({ "unchecked" })
		@PostMapping(value = { "WaterReadingRecord-details-add" })
		public @ResponseBody JsonResponse<Object> addWRRLogBook(@RequestBody List<AssetPolicyModel> av,
				HttpSession session) {
			logger.info("Method : addWRRLogBook function starts" + av);
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
			for (AssetPolicyModel m : av) {
				m.setCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			try {
				resp = restTemplate.postForObject(env.getTicketUrl() + "rest-WRR-details-add", av, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : addWRRLogBook function Ends" + resp);
			return resp;
		}
		
		// view

		@SuppressWarnings("unchecked")

		@GetMapping("WaterReadingRecord-details-view")
		public @ResponseBody Object viewWRRLogbook(HttpSession session) {
			logger.info("Method :viewWRRLogbook starts");
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
				resp = restTemplate.getForObject(
						env.getTicketUrl() + "rest-WRR-details-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewWRRLogbook ends");
			return resp;
		}


		// delete

		@SuppressWarnings("unchecked")
		@PostMapping("WaterReadingRecord-details-delete")
		public @ResponseBody JsonResponse<Object> deleteWRRLogbook(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : deleteWRRLogbook function starts");
			JsonResponse<Object> res = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(
						env.getTicketUrl() + "rest-WRR-details-delete?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : deleteWRRLogbook function Ends");
			return res;
		}

		// approve

		@SuppressWarnings("unchecked")
		@PostMapping("WaterReadingRecord-details-approve")
		public @ResponseBody JsonResponse<Object> approveWRRLogbook(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : approveWRRLogbook function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			String orgName = "";
			String orgDivision = "";

			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(
						env.getTicketUrl() + "rest-WRR-details-approve?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {
				res.setMessage("Success");
			} else {

			}
			logger.info("Method : approveWRRLogbook function Ends");
			return res;

		}
		
		///////////////////////////////////////////EB Logbook////////////////////////////////////////////////////////////

		@GetMapping("EBReading")
		public String EBReading(Model model, HttpSession session) {
			logger.info("Method : EBReading starts");

			String userId = "";
			String userName = "";
			String userRole = "";
			String org = "";
			String orgDiv = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				userName = (String) session.getAttribute("USER_NAME");
				userRole = (String) session.getAttribute("USER_ROLES_STRING");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			try {
				DropDownModel[] shift = restTemplate.getForObject(env.getProduction() + "getShiftListForProduction?org="
						+ org + "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
				List<DropDownModel> shiftLists = Arrays.asList(shift);

				model.addAttribute("shiftLists", shiftLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			try {
				DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
						+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
				List<DropDownModel> empLists = Arrays.asList(emp);

				model.addAttribute("empLists", empLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : EBReading ends");
			return "ticket/EBReading";
		}

		@SuppressWarnings({ "unchecked" })
		@PostMapping(value = { "EBReading-details-add" })
		public @ResponseBody JsonResponse<Object> addEBReadingLogBook(@RequestBody List<AssetPolicyModel> av,
				HttpSession session) {
			logger.info("Method : addEBReadingLogBook function starts" + av);
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
			for (AssetPolicyModel m : av) {
				m.setCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			try {
				resp = restTemplate.postForObject(env.getTicketUrl() + "rest-EBReading-details-add", av, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : addEBReadingLogBook function Ends" + resp);
			return resp;
		}
		
		// view

		@SuppressWarnings("unchecked")

		@GetMapping("EBReading-details-view")
		public @ResponseBody Object viewEBReadingLogbook(HttpSession session) {
			logger.info("Method :viewEBReadingLogbook starts");
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
				resp = restTemplate.getForObject(
						env.getTicketUrl() + "rest-EBReading-details-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewEBReadingLogbook ends");
			return resp;
		}


		// delete

		@SuppressWarnings("unchecked")
		@PostMapping("EBReading-details-delete")
		public @ResponseBody JsonResponse<Object> deleteEBReadingLogbook(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : deleteEBReadingLogbook function starts");
			JsonResponse<Object> res = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(
						env.getTicketUrl() + "rest-EBReading-details-delete?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : deleteEBReadingLogbook function Ends");
			return res;
		}

		// approve

		@SuppressWarnings("unchecked")
		@PostMapping("EBReading-details-approve")
		public @ResponseBody JsonResponse<Object> approveEBReadingLogbook(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : approveEBReadingLogbook function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			String orgName = "";
			String orgDivision = "";

			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(
						env.getTicketUrl() + "rest-EBReading-details-approve?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {
				res.setMessage("Success");
			} else {

			}
			logger.info("Method : approveEBReadingLogbook function Ends");
			return res;

		}
// Illumination Record
		@GetMapping("illumination")
		public String illumination(Model model, HttpSession session) {
			logger.info("Method : elcbDATA starts");

			String userId = "";
			String userName = "";
			String userRole = "";
			String org = "";
			String orgDiv = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				userName = (String) session.getAttribute("USER_NAME");
				userRole = (String) session.getAttribute("USER_ROLES_STRING");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			/*String splitData[] = userRole.split("r");
			String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
					.toArray(size -> new String[size]);

			for (String part : removedNull) {
				String data = "r" + part;

				if (data.contentEquals("rol001") || data.contentEquals("rol051") || data.contentEquals("rol052")
						|| data.contentEquals("rol053")) {
					model.addAttribute("aprvRole", "aprvRole");

				}

			}*/
			try {
				DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
						+ org + "&orgDivision=" + orgDiv, DropDownModel[].class);
				List<DropDownModel> yearList = Arrays.asList(year);

				model.addAttribute("yearList1", yearList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
						+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
				List<DropDownModel> empLists = Arrays.asList(emp);

				model.addAttribute("empLists", empLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : illumination ends");
			return "ticket/illumination-record";
		}
		@SuppressWarnings("unchecked")
		@GetMapping("illumination-child1-view")
		public @ResponseBody Object viewIlluminationData(HttpSession session) {
			logger.info("Method :viewIlluminationData starts");
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
				resp = restTemplate.getForObject(
						env.getTicketUrl() + "rest-viewIlluminationData?orgName=" + orgName + "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :viewIlluminationData ends");
			return resp;
		}
		
//
		@SuppressWarnings({ "unchecked" })
		@PostMapping(value = { "illumination-add" })
		public @ResponseBody JsonResponse<Object> illuminationAdd(@RequestBody List<DigitalLogBookModel> av,
				HttpSession session) {
			logger.info("Method : illuminationAdd function starts" + av);
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
			for (DigitalLogBookModel m : av) {
				m.setCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);

			}
			try {
				resp = restTemplate.postForObject(env.getTicketUrl() + "rest-illuminationAdd", av, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : illuminationAdd function Ends" + resp);
			return resp;
		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("illumination-view")
		public @ResponseBody Object viewIllumination(HttpSession session) {
			logger.info("Method :viewIllumination starts");
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
				resp = restTemplate.getForObject(env.getTicketUrl() + "rest-viewIllumination?orgName=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :viewIllumination ends");
			return resp;
		}
//
		// edit
		@SuppressWarnings("unchecked")
		@GetMapping("illumination-edit")
		public @ResponseBody Object editIllumination(@RequestParam String id, HttpSession session) {
			logger.info("Method :editIllumination starts");
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

				resp = restTemplate.getForObject(env.getTicketUrl() + "rest-editIllumination?id=" + id + "&orgName=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :editIllumination ends");
			return resp;
		}
		@SuppressWarnings("unchecked")
		@PostMapping("illumination-delete")
		public @ResponseBody JsonResponse<Object> deleteIllumination(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : deleteIllumination function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(
						env.getTicketUrl() + "rest-deleteIllumination?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteIllumination function Ends");
			return res;
		}

		// approve

		@SuppressWarnings("unchecked")
		@PostMapping("illumination-approve")
		public @ResponseBody JsonResponse<Object> approveIllumination(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : approveIllumination function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			String orgName = "";
			String orgDivision = "";

			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(env.getTicketUrl() + "rest-approveIllumination?id=" + id + "&org=" + orgName
						+ "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {
				res.setMessage("Success");
			} else {

			}
			logger.info("Method : approveIllumination function Ends");
			return res;
		}
}
