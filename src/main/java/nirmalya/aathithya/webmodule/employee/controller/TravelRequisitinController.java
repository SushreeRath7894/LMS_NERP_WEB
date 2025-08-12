package nirmalya.aathithya.webmodule.employee.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.TravelClaimModel;
import nirmalya.aathithya.webmodule.employee.model.TravelRequisitionModel;
import nirmalya.aathithya.webmodule.employee.model.TravelServiceWebModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import java.util.stream.Collectors;
@Controller
@RequestMapping(value = { "employee/" })
public class TravelRequisitinController {

	Logger logger = LoggerFactory.getLogger(TravelRequisitinController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/travel-requisition")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : reimbursement starts");
		
		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		System.out.println("Dataaaaaaaa"+userRole);
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol002") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", "admin");
				//System.out.println("data>>>>----" + data);
			}
			
			 if (data.contentEquals("rol001") || data.contentEquals("rol010") ||data.contentEquals("rol002") ||data.contentEquals("rol003")) 
			 { 
				 model.addAttribute("aprRole", data); 
			 }
			 
			
			if (data.contentEquals("rol003") ) {
				model.addAttribute("managerRole", data);
			}
			
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		
		try {
			DropDownModel[] Purpose = restClient.getForObject(env.getEmployeeUrl() + "getPurposeList",
					DropDownModel[].class);
			List<DropDownModel> getPurposeList = Arrays.asList(Purpose);
			model.addAttribute("getPurposeList", getPurposeList);
			logger.info("Method : getPurposeList starts"+getPurposeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] Service = restClient.getForObject(env.getEmployeeUrl() + "getServiceList",
					DropDownModel[].class);
			List<DropDownModel> getServiceList = Arrays.asList(Service);
			model.addAttribute("getServiceList", getServiceList);
			logger.info("Method : getServiceList starts"+getServiceList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Employee = restClient.getForObject(env.getEmployeeUrl() + "getEmployeeListData?userId=" + userId,
					DropDownModel[].class);
			List<DropDownModel> getEmployeeList = Arrays.asList(Employee);
			model.addAttribute("getEmployeeList", getEmployeeList);
			logger.info("Method : getEmployeeList starts"+getEmployeeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		logger.info("Method : reimbursement ends");
		//return "employee/travelRequisition";
		return "employee/travelRequisitionNew";
	}
	

	//
	
	@SuppressWarnings("unchecked")
	@GetMapping("travel-requisition-view-employee")
	public @ResponseBody List<TravelRequisitionModel> viewTravelReq(HttpSession session,@RequestParam String userid,
			@RequestParam String roleid,@RequestParam String selftype) {

		logger.info("Method : viewTravelReq starts");
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<TravelRequisitionModel>> resp = new JsonResponse<List<TravelRequisitionModel>>();
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
				resp = restTemplate.postForObject(env.getEmployeeUrl() + "travel-requisition-employee" , empModel, 
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			List<TravelRequisitionModel> viewRem = mapper.convertValue(resp.getBody(),
					new TypeReference<List<TravelRequisitionModel>>() {
					});	
			for (TravelRequisitionModel a : viewRem) {
				if (a.getAdvanceReq().equals("0")) {
					a.setAdvanceReq("NO");
				}else{
					a.setAdvanceReq("YES");
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
				
				if (a.getClaimStatus().equals("0")) {
					a.setClaimStatus("Pending");
				}else if (a.getClaimStatus().equals("4")) {
					a.setClaimStatus("completed");
				}else{
					a.setClaimStatus("Continue");
				}
				

	 
			}
			
			
			resp.setBody(viewRem);
			System.out.println("Data==========================="+resp.getBody());
			logger.info("Method : viewTravelReq ends"+viewRem);
			return resp.getBody();
		}

	
	/*
	 * post Mapping for adding travel
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "travel-requisition-add-travel-details")
	public @ResponseBody JsonResponse<Object> saveTravel(@RequestBody TravelRequisitionModel travelModel,
			HttpSession session) {
		logger.info("Method : saveTravel function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";
		logger.info("web 1===" + travelModel);
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		travelModel.setCreatedBy(userId);
		travelModel.setOrganization(organization);
		travelModel.setOrgDivision(orgDivision);
		
		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "rest-add-travel", travelModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/*
		 * if(resp.getCode().equals("success")) { resp.setMessage("Success"); } else {
		 * 
		 * }
		 */
		logger.info("Method : saveTravel function Ends");
		return resp;
	}
	
//	@SuppressWarnings("unchecked")
//	@GetMapping(value = { "travel-requisition-edit-employee-trough-ajax" })
//	public @ResponseBody List<TravelRequisitionModel> TravelReqEdit(@RequestParam String id,
//			HttpSession session) {
//		logger.info("Method : TravelReqEdit starts");
//
//		List<TravelRequisitionModel> travelModel = new ArrayList<TravelRequisitionModel>();
//		JsonResponse<List<TravelRequisitionModel>> jsonResponse = new JsonResponse<List<TravelRequisitionModel>>();
//
//		if (id != null && id != "") {
//			try {
//				
//				jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "get-travel-edit?id=" + id,
//						JsonResponse.class);
//
//				ObjectMapper mapper = new ObjectMapper();
//
//				List<TravelRequisitionModel> addreq = mapper.convertValue(jsonResponse.getBody(),
//						new TypeReference<List<TravelRequisitionModel>>() {
//						});
//logger.info("addreq==="+addreq);
//
//				addreq.forEach(s -> s.setId(s.getServiceId()));
//
//				int count = 0;
//				
//				for (TravelRequisitionModel m : addreq) {
//					
//					count ++;
//					m.setSlnoId(count);
//					
//				}
//				jsonResponse.setBody(addreq);
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	
//		}
//		logger.info("Method : TravelReqEdit ends");
//		return jsonResponse.getBody();
//	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("travel-requisition-edit-employee-trough-ajax")
	public @ResponseBody JsonResponse<TravelRequisitionModel> TravelReqEdit(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : TravelReqEdit starts" + id);
		List<TravelRequisitionModel> productList = new ArrayList<TravelRequisitionModel>();

		JsonResponse<TravelRequisitionModel> jsonResponse = new JsonResponse<TravelRequisitionModel>();
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		System.out.println("date" + dateFormat);

		TravelRequisitionModel product = new TravelRequisitionModel();
		ObjectMapper mapper = new ObjectMapper();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {

			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "get-travel-edit?id=" + id, JsonResponse.class);

			product = mapper.convertValue(jsonResponse.getBody(), new TypeReference<TravelRequisitionModel>() {
			});
			System.out.println("enter mapper controller" + product);

			if (product.getServicedtls().size() > 0) {
				int c = 0;
				for (TravelServiceWebModel a : product.getServicedtls()) {
					c = c + 1;
					a.setSlnoId(c);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonResponse.setBody(product);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		System.out.println("REsp" + jsonResponse);

		logger.info("Method : TravelReqEdit ends");
		return jsonResponse;
	}

	//delete travel
	@SuppressWarnings("unchecked")
	@PostMapping(value = "travel-requisition-delete-th-ajax")
	public @ResponseBody JsonResponse<Object> deleteTravel(
			@RequestBody TravelRequisitionModel travelModel, HttpSession session) {
		logger.info("Method : deleteTravel function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		logger.info("masggggggggg"+travelModel.getTravelingReqId());
		try {
			userId = (String) session.getAttribute("USER_ID");
			travelModel.setCreatedBy(userId);
			
		} catch (Exception e) {

		}
		try {  

			res = restTemplate.postForObject(env.getEmployeeUrl() + "rest-delete-travel",
					travelModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}


		if (res.getCode().equals("success")) {
			res.setMessage("Success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : deleteTravel function Ends"+travelModel);
		return res;
	}
	
	
	
	
	
	
	// approve requisition

	@SuppressWarnings("unchecked")
	@GetMapping("travel-requisition-approve")
	public @ResponseBody JsonResponse<TravelRequisitionModel> approveRequisition(@RequestParam String approveId,String name,String comment,String roleid) {

		logger.info("Method : approveRequisition starts");
		JsonResponse<TravelRequisitionModel> response = new JsonResponse<TravelRequisitionModel>();

		try {
			response = restClient.getForObject(env.getEmployeeUrl() + "approveRequisition?id=" + approveId + "&name=" + name+"&comment="+comment+"&roleid="+roleid,
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
		logger.info("Method : approveRequisition ends");
		return response;
	}
	
	//reject requisition
	
	@SuppressWarnings("unchecked")
	@GetMapping("travel-requisition-reject")
	public @ResponseBody JsonResponse<TravelRequisitionModel> rejectRequisition(@RequestParam String rejectId,String name,String comment,String roleid) {

		logger.info("Method : rejectRequisition starts");
		JsonResponse<TravelRequisitionModel> response = new JsonResponse<TravelRequisitionModel>();

		try {
			response = restClient.getForObject(env.getEmployeeUrl() + "rejectRequisition?id=" + rejectId + "&name=" + name+"&comment="+comment+"&roleid="+roleid,
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
		logger.info("Method : rejectRequisition ends");
		return response;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("travel-requisition-getEmpData")
	public @ResponseBody JsonResponse<DropDownModel> getEmpData(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : getEmpData starts");
		JsonResponse<DropDownModel> jsonResponse = new JsonResponse<DropDownModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getEmpData?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : getEmpData ends");
		logger.info("getEmpData=====" + jsonResponse);
		return jsonResponse;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "travel-requisition-view-other" })
	public @ResponseBody List<TravelRequisitionModel> TravelReqServiceOtherView(@RequestParam String id,HttpSession session) {
		logger.info("Method : TravelReqServiceOtherView starts");

		JsonResponse<List<TravelRequisitionModel>> jsonResponse = new JsonResponse<List<TravelRequisitionModel>>();
			try {
				jsonResponse=restClient.getForObject(env.getEmployeeUrl() + "get-req-service-other?id=" + id, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("TravelClaimOtherView: "+e.getMessage());
			}		
		logger.info("Method : TravelReqServiceOtherView endss");
		return jsonResponse.getBody();
	}
//
	

	/*
	 * Employee autosearch
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "travel-requisition-get-employee-list" })
	public @ResponseBody JsonResponse<DropDownModel> EmployeeAutoSearchForTravel(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : EmployeeAutoSearchForTravel starts");
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
			System.out.println("employee-autosearch-forTravel?id=" + searchValue+"&org="+org+"&orgDiv="+orgDiv);
			res = restTemplate.getForObject(env.getEmployeeUrl() + "employee-autosearch-forTravel?id=" + searchValue+"&org="+org+"&orgDiv="+orgDiv,
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
		logger.info("Method : EmployeeAutoSearchForTravel ends"+res);
		return res;
	}
	
}
