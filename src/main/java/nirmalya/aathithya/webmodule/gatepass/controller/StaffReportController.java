package nirmalya.aathithya.webmodule.gatepass.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "gatepass/")
public class StaffReportController {
	
	Logger logger = LoggerFactory.getLogger(StaffReportController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	
	
	@GetMapping("/staff-report")
	public String gatePassStaffRegister(Model model, HttpSession session) {
		logger.info("Method : gatePassStaffRegister add starts");
		
		//String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			//userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		logger.info("Method : gatePassStaffRegister ends");
		return "gatepass/staff-reportgate";
	}

	// viewAssignAsset

	@SuppressWarnings("unchecked")

	@GetMapping("staff-report-view")
	public @ResponseBody Object viewAssignAsset(@RequestParam String fromdate,String todate,HttpSession session) {
		logger.info("Method :viewAssignAsset starts");
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
					env.getGatepassUrl() + "rest-staff-report-view?fromdate=" + fromdate+ "&todate=" + todate+ "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		System.out.println("view===" + resp);
		logger.info("Method :viewAssignAsset ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("staff-report-list")
	public @ResponseBody Object showTotalDetails(@RequestParam String id,String fromdate,String todate,String action, HttpSession session) {
		logger.info("Method :showTotalDetails starts");
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

			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-staff-report-list?id=" + id+"&fromdate=" + fromdate+ "&todate=" + todate+ "&orgName=" + orgName
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
		logger.info("Method :showTotalDetails ends");
		return resp;
	}


}
