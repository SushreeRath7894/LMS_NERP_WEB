package nirmalya.aathithya.webmodule.productionplan.controller;

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
import nirmalya.aathithya.webmodule.productionplan.model.UploadedPlanModel;
import nirmalya.aathithya.webmodule.user.model.UserRoleAssignModel;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller
@RequestMapping("production/")
public class UploadedPlanController {
	Logger logger = LoggerFactory.getLogger(UploadedPlanController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

// method for page return
	@GetMapping(value = { "uploaded-plan" })
	public String uploadedPlan(Model model, HttpSession session) {
		logger.info("uploadedPlan Start");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-lists?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] week = restTemplate.getForObject(
					env.getProduction() + "get-week-list?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> weekList = Arrays.asList(week);
			model.addAttribute("weekList", weekList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("uploadedPlan End");
		return "production_plan/uploaded-plan";
	}

	// add plan-Details.
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "uploaded-plan-add" })
	public @ResponseBody JsonResponse<Object> addPlan(@RequestBody List<UploadedPlanModel> plan, HttpSession session) {
		logger.info("Method : addPlan function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (UploadedPlanModel m : plan) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("plan===" + plan);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-addPlan", plan, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addPlan function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

//viewPlan
	@SuppressWarnings("unchecked")
	@GetMapping("uploaded-plan-view")
	public @ResponseBody Object viewPlan(HttpSession session) {
		logger.info("Method :viewPlan starts");
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
					env.getProduction() + "rest-viewPlan?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :viewPlan ends");
		return resp;
	}

//editPlan
	@SuppressWarnings("unchecked")
	@GetMapping("uploaded-plan-edit")
	public @ResponseBody Object editPlan(@RequestParam String id, HttpSession session) {
		logger.info("Method :editPlan starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-editPlan?id=" + id + "&orgName=" + orgName+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :editPlan ends");
		return resp;
	}

	// Delete plan-Details.
	@SuppressWarnings("unchecked")
	@GetMapping(value = "uploaded-plan-delete")
	public @ResponseBody JsonResponse<Object> deletePlan(@RequestParam String id, HttpSession session) {
		logger.info("Method : deletePlan function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getProduction() + "rest-deletePlan?id=" +id +"&orgName=" +orgName+"&orgDivision=" +orgDivision,JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("Success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : deletePlan function Ends");
		return res;
	}
	
	//Approve Plan

		@SuppressWarnings("unchecked")
		@GetMapping("uploaded-plan-approve")
		public @ResponseBody JsonResponse<List<UploadedPlanModel>> approvePlan(HttpSession session,
				@RequestParam String approveStatus, String planId) {

			logger.info("Method : approvePlan starts");
			JsonResponse<List<UploadedPlanModel>> response = new JsonResponse<List<UploadedPlanModel>>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				response = restTemplate.getForObject(env.getProduction() + "approvePlan?approveStatus=" + approveStatus + "&planId=" + planId
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
	        logger.info("Method : approvePlan ends");
			return response;
		}
		
		// get boomlist
		
		@SuppressWarnings("unchecked")
		@GetMapping("uploaded-plan-boom-listing")
		public @ResponseBody Object getAllBoomList(@RequestParam String itemId,HttpSession session) {
			logger.info("Method :getAllBoomList starts");
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
				resp = restTemplate.getForObject(env.getProduction() + "getAllBoomList?itemId=" + itemId
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getAllBoomList ends");
			return resp;
		}
		
		//Add bomDetails
		
		@SuppressWarnings("unchecked")
		@PostMapping("uploaded-plan-save-bomdata")
		public @ResponseBody JsonResponse<Object> addBomData(HttpSession session,
				@RequestBody UploadedPlanModel uploadedPlanModel) {
	 
			logger.info("Method : addBomData starts");
			System.out.println(uploadedPlanModel);
			JsonResponse<Object> resp = new JsonResponse<Object>();

			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			uploadedPlanModel.setCreatedBy(userId);
			uploadedPlanModel.setOrganization(organization);
			uploadedPlanModel.setOrgDivision(orgDivision);
			try {
				resp = restTemplate.postForObject(env.getProduction() + "addBomData", uploadedPlanModel,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : addBomData ends");
			return resp;
		}
		
		//edit bom data 
		
		@SuppressWarnings("unchecked")
		@GetMapping("uploaded-plan-edit-bomdata")
		public @ResponseBody Object editBomData(@RequestParam String planId,  String itemId,
				HttpSession session) {

			logger.info("Method :editBomData starts");
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
				resp = restTemplate.getForObject(env.getProduction() + "editBomData?planId=" + planId
						+ "&itemId=" + itemId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :editBomData ends");
			System.out.println("edit>>>-----" + resp);
			return resp;
		}

}
