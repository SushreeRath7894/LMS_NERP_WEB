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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.PayrollMasterModel;

@Controller
@RequestMapping(value = "/master")
public class PayrollMasterController {
	Logger logger = LoggerFactory.getLogger(PayrollMasterController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/view-payroll")
	public String eventmanagementdetails(Model model, HttpSession session) {
		logger.info("Method : banddetails starts");
		logger.info("Method : banddetails ends");

		try {
			DropDownModel[] bandType = restTemplate.getForObject(env.getMasterUrl() + "getBandTypeList",
					DropDownModel[].class);
			List<DropDownModel> bandTypeList = Arrays.asList(bandType);

			model.addAttribute("bandTypeList", bandTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] componentType = restTemplate.getForObject(env.getMasterUrl() + "getComponentTypeList",
					DropDownModel[].class);
			List<DropDownModel> componentTypeList = Arrays.asList(componentType);

			model.addAttribute("componentTypeList", componentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : banddetails ends");
		return "master/payrollMaster";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-payroll-add-details")
	public @ResponseBody JsonResponse<Object> addPayrollMaster(@RequestBody PayrollMasterModel job,
			HttpSession session) {
		logger.info("Method : addPayrollMaster starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		job.setOrganization(organization);
		job.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// sal.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-payroll", job, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addPayrollMaster starts");
		return resp;
	}

//      View page for Hr job types

	@SuppressWarnings("unchecked")
	@GetMapping("view-payroll-Master")
	public @ResponseBody List<PayrollMasterModel> viewSalType(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewSalType starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<PayrollMasterModel>> resp = new JsonResponse<List<PayrollMasterModel>>();
		List<PayrollMasterModel> returnList = new ArrayList<PayrollMasterModel>();

		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "viewSalType?id=" + id + "&org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewSalType ends");
		return returnList;
	}

}
