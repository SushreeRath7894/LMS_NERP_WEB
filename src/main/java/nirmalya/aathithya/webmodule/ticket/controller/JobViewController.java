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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.maintenance.model.AllotedMaintenanceModel;
import nirmalya.aathithya.webmodule.ticket.model.TicketManagementModel;

@Controller
@RequestMapping(value = "ticket")
public class JobViewController {

	Logger logger = LoggerFactory.getLogger(TicketManagementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/jobview")
	public String jobView(Model model, HttpSession session) {
		logger.info("Method : jobView starts");

		String org = "";
		String orgDiv = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] result_List = restClient.getForObject(
					env.getTicketUrl() + "get-result-status?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);

			List<DropDownModel> resultList = Arrays.asList(result_List);

			model.addAttribute("resultList", resultList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] spare_list = restTemplate.getForObject(
					env.getTicketUrl() + "get-sparepart-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> spareList = Arrays.asList(spare_list);

			model.addAttribute("sparePartList", spareList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(env.getAssetUrl() + "getCategoryListforSparePart?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> catLists = Arrays.asList(cat);
			model.addAttribute("catLists", catLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] policyListitem = restClient.getForObject(
					env.getTicketUrl() + "get-policy-list?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> policyList = Arrays.asList(policyListitem);

			model.addAttribute("policyList", policyList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] shift = restTemplate.getForObject(
					env.getProduction() + "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
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

		logger.info("Method : jobView ends");
		return "ticket/job-view";
	}

	// Job view

	@SuppressWarnings({ "unchecked" })
	@GetMapping("/jobview-all-data")
	public @ResponseBody Object getJobView(@RequestParam String pageno,String option, HttpSession session) {
		logger.info("Method : getJobView starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTicketUrl() + "rest-jobview-all-data?userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&pageno=" + pageno+ "&option=" + option, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getJobView ends");
		return resp;

	}
	
	// Job view search
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/jobview-all-data-search")
	public @ResponseBody Object getJobViewSearch(HttpSession session, @RequestParam String search ,@RequestParam String option,@RequestParam String date) {
		logger.info("Method : getJobViewSearch starts"+option);
		
		String organization = "";
		String orgDivision = "";
		String userId = "";
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTicketUrl() + "rest-jobview-all-data-search?userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision+ "&search=" + search + "&option=" + option+ "&date=" + date, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getJobViewSearch ends"+resp);
		return resp;
		
	}

	// Add Ticket.

	@SuppressWarnings("unchecked")
	@PostMapping("/jobview-save-result")
	public @ResponseBody JsonResponse<Object> saveJobResult(@RequestBody TicketManagementModel category,
			HttpSession session) {
		logger.info("Method : saveJobResult starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		category.setCreatedBy(userId);
		category.setOrganization(organization);
		category.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getTicketUrl() + "rest-add-job-result", category, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : saveJobResult starts"+resp);
		return resp;
	}

	/*
	 * public JSONObject saveAllMediaDocuments(byte[] imageBytes, String ext, String
	 * user_id) { logger.info("Method : saveAllMedicalDocuments starts");
	 * 
	 * String imageName = null; try { if (imageBytes != null) { long nowTime = new
	 * Date().getTime();
	 * 
	 * 
	 * if (filetype.equals("Video")) { ext = "mp4"; }
	 * 
	 * if (ext.contentEquals("flv") || ext.contentEquals("avi") ||
	 * ext.contentEquals("3gp") || ext.contentEquals("mov") ||
	 * ext.contentEquals("cda") || ext.contentEquals("wav") ||
	 * ext.contentEquals("mkv") || ext.contentEquals("wma") ||
	 * ext.contentEquals("wpl") ) { ext = "mp4"; } if (ext.contentEquals("jpeg")) {
	 * imageName = user_id + "_" + nowTime + ".jpg"; } else { imageName = user_id +
	 * "_" + nowTime + "." + ext; } }
	 * 
	 * Path path = Paths.get(env.getTicketresult() + imageName); if (imageBytes !=
	 * null) { Files.write(path, imageBytes); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * String url = env.getTrainingUrl() + imageName;
	 * 
	 * JSONObject json = new JSONObject();
	 * 
	 * try { json.put("filename", imageName); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } try { json.put("fileurl",
	 * url); } catch (JSONException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * logger.info("Method : saveAllMediaDocuments ends"); return json; }
	 */
	// DeleteAsset

	@SuppressWarnings("unchecked")
	@PostMapping("jobview-save-accept")
	public @ResponseBody JsonResponse<Object> acceptOperation(@RequestParam String id,String operation,String expectedDate,String expectedTime,String expectedCost, Model model,
			HttpSession session) {
		logger.info("Method : acceptOperation function starts");

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
					env.getTicketUrl() + "rest-jobview-save-accept?id=" + id + "&operation=" + operation+"&expectedDate="+expectedDate+"&expectedTime="+expectedTime+"&expectedCost="+expectedCost+ "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : acceptOperation function Ends");

		return res;
	}

	//getResultView

	@SuppressWarnings({ "unchecked" })
	@GetMapping("/jobview-result-view")
	public @ResponseBody Object getResultView(@RequestParam String id, HttpSession session) {
		logger.info("Method : getResultView starts");

		String organization = "";
		String orgDivision = "";
		//String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			//userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTicketUrl() + "rest-jobview-result-view?&id=" + id + "&org="
					+ organization + "&orgDiv=" + orgDivision , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getResultView ends");
		return resp;

	}
	

	@SuppressWarnings("unchecked")

	@GetMapping("jobview-maintenance-policylist")
	public @ResponseBody Object getEmergencyList(@RequestParam String aid, String pid,
			HttpSession session) {
		logger.info("Method :getEmergencyList starts");
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

			resp = restTemplate
					.getForObject(
							env.getMaintenance() + "rest-asset-maintenance-policylist?aid=" + aid + "&pid=" + pid
									+ "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :getEmergencyList ends"+resp);
		return resp;
	}


	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "jobview-maintenance-progress" })
	public @ResponseBody JsonResponse<Object> addPolicyProgress(@RequestBody List<AllotedMaintenanceModel> av, HttpSession session) {
		logger.info("Method : addPolicyProgress function starts"+av);
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
		for (AllotedMaintenanceModel m : av) {
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("INPUT DATA::::::"+av);
		try {
			resp = restTemplate.postForObject(env.getMaintenance() + "rest-asset-maintenance-progress", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addPolicyProgress function Ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("jobview-maintenance-subcategory")
	public @ResponseBody Object getSubCategory(@RequestParam String id, HttpSession session) {
		logger.info("Method :getSubCategory starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-spare-part-subcategory?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getSubCategory ends");
		return resp;
	}

	
	@SuppressWarnings("unchecked")

	@GetMapping("jobview-maintenance-policylist-upload")
	public @ResponseBody Object getDetailsForUpload(@RequestParam String aid, String pid,String shift,
			HttpSession session) {
		logger.info("Method :getDetailsForUpload starts");
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

			resp = restTemplate
					.getForObject(
							env.getMaintenance() + "rest-asset-maintenance-policylist-upload?aid=" + aid + "&pid=" + pid
									+ "&orgName=" + orgName + "&orgDivision=" + orgDivision+ "&shift=" + shift,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getDetailsForUpload ends"+resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("jobview-all-type-btn")
	public @ResponseBody Object JobTypeList(HttpSession session) {
		logger.info("Method :JobTypeList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getTicketUrl() + "rest-jobview-all-type-btn?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :JobTypeList ends"+resp);
		return resp;
	}
//
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/jobview-all-data-preventive")
	public @ResponseBody Object getJobView1(@RequestParam String pageno,String option, HttpSession session) {
		logger.info("Method : getJobView1 starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTicketUrl() + "rest-jobview-all-data?userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&pageno=" + pageno+ "&option=" + option, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getJobView1 ends");
		return resp;

	}
//
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/jobview-all-data-installation")
	public @ResponseBody Object getJobView2(@RequestParam String pageno,String option, HttpSession session) {
		logger.info("Method : getJobView starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTicketUrl() + "rest-jobview-all-data?userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&pageno=" + pageno+ "&option=" + option, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getJobView2 ends");
		return resp;

	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("jobview-maintenance-policylist-datewise")
	public @ResponseBody Object getEmergencyListDate(@RequestParam String aid, String pid,String shift, HttpSession session) {
		logger.info("Method :getEmergencyList starts");
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

			resp = restClient.getForObject(env.getTicketUrl() + "rest-departmentview-maintenance-policylist?aid=" + aid
					+ "&pid=" + pid + "&orgName=" + orgName + "&orgDivision=" + orgDivision+ "&shift=" + shift, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getEmergencyList ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("jobview-all-policywise-view")
	public @ResponseBody Object getJobsPolicyWise(@RequestParam String id,String date, HttpSession session) {
		logger.info("Method : getJobsPolicyWise starts"+id);

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getTicketUrl() + "rest-jobview-all-policywise-view?&userid=" + userId + "&org=" + organization
							+ "&orgDiv=" + orgDivision + "&id=" + id+ "&date=" + date,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getJobsPolicyWise ends"+resp);
		return resp;

	}
}
