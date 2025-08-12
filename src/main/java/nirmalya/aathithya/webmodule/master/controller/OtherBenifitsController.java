package nirmalya.aathithya.webmodule.master.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.OtherBenifitsModel;

@Controller
@RequestMapping(value = "master/")
public class OtherBenifitsController {
	Logger logger = LoggerFactory.getLogger(OtherBenifitsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("view-other-benifits")
	public String leaveApply(Model model, HttpSession session) {
		logger.info("Method : otherBenifits starts");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] otherBenifit = restTemplate.getForObject(env.getMasterUrl() + "getOtherBenifit?org=" + org + "&orgDiv="+orgDiv,
					DropDownModel[].class);
			List<DropDownModel> otherBenifitList = Arrays.asList(otherBenifit);

			model.addAttribute("otherBenifitList", otherBenifitList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : otherBenifits ends");
		return "master/view-other-benifits";
	}
	
	/*
	 * Employee autosearch
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-other-benifits-get-employee-list" })
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
		logger.info("Method : EmployeeAutoSearchForAttendance ends");
		return res;
	}
	
	/*
	 * //Main save 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-other-benifits-details-save")
	public @ResponseBody JsonResponse<Object> saveOtherBanifits(@RequestBody OtherBenifitsModel otherBenifitsModel,
			HttpSession session) {
		logger.info("Method : saveOtherBanifits starts");

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
		otherBenifitsModel.setCreatedBy(userId);
		otherBenifitsModel.setOrganization(organization);
		otherBenifitsModel.setOrgDivision(orgDivision);
		try {
			//logger.info(env.getMasterUrl()+"saveOtherBanifits?"+advanceModel);
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveOtherBanifits", otherBenifitsModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		

		logger.info("Method : saveOtherBanifits ends");
		logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrreturn" + resp);
		return resp;
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("view-other-benifits-view-data")
	public @ResponseBody Object viewBenifitsData(HttpSession session) {
		logger.info("Method :viewBenifitsData starts");
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
					env.getMasterUrl() + "viewBenifitsData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewBenifitsData ends"+resp);
		return resp;
	}
	
	@GetMapping("view-other-benifits-delete")
	public @ResponseBody Object deleteBenifit(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteBenifit starts");
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
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteBenifit?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@GetMapping("view-other-benifits-approve")
	public @ResponseBody Object approveBenifitdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveBenifitdata starts");
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
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-approveBenifitdata?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-other-benifits-edit-benifits")
	public @ResponseBody Object editBenifitData(@RequestParam String benifitId, HttpSession session) {
		logger.info("Method :editBenifitData starts");
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

			resp = restTemplate.getForObject(env.getMasterUrl() + "editBenifitData?benifitId=" + benifitId + 
				"&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		
		
		
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
		logger.info("Method :editBenifitData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-other-benifits-get-frequency")
	public @ResponseBody Object getFrequency(@RequestParam String category, HttpSession session) {
		logger.info("Method :getFrequency starts");
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

			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-other-benifits-get-frequency?category=" + category + "&orgName=" + orgName
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

		logger.info("Method :getFrequency ends");
		return resp;
	}
}
