package nirmalya.aathithya.webmodule.master.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.HrmsAttendanceModel;

@Controller
@RequestMapping(value = { "master/" })
public class HrmsAttendanceController {
	Logger logger = LoggerFactory.getLogger(HrmsAttendanceController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	HrmsAttendanceController hrmsAttendanceController;

	@GetMapping(value = { "master-attendance" })
	public String emailConfiguration(Model model, HttpSession session) {
		logger.info("Method : attendance starts");

		DropDownModel[] emp = restClient.getForObject(env.getMasterUrl() + "/emplist", DropDownModel[].class);
		List<DropDownModel> emplist = Arrays.asList(emp);
		model.addAttribute("emplist", emplist);

		logger.info("Method : attendance ends");
		return "master/hrms-attendance.html";
	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("master-attendance-view")
	public @ResponseBody Object viewAttendance(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String empid, String shift) {
		logger.info("Method :viewAttendance starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getMasterUrl() + "rest-master-attendance-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate + "&empid=" + empid
					+ "&shift=" + shift, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewAttendance ends" + resp);
		return resp;
	}

	// add

	@SuppressWarnings("unchecked")
	@PostMapping("master-attendance-add")
	public @ResponseBody JsonResponse<Object> addTravelClaimOther(HttpSession session,
			@RequestBody HrmsAttendanceModel attendanceModel) {
		logger.info("Method : addTravelClaimOther");

		String dateFormat = "";
		String userId = "";
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
		attendanceModel.setCreatedBy(userId);
		attendanceModel.setOrganization(organization);
		attendanceModel.setOrgDivision(orgDivision);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "rest-add-attendance", attendanceModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : attendanceModel ends");
		return resp;
	}
}
