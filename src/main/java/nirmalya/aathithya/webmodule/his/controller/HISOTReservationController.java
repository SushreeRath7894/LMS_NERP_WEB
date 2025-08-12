package nirmalya.aathithya.webmodule.his.controller;

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
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HISBedMasterModel;
import nirmalya.aathithya.webmodule.his.model.HISOTReservationModel;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;
import nirmalya.aathithya.webmodule.master.model.EmailConfigModel;
import nirmalya.aathithya.webmodule.user.model.RolesAccessModel;
import nirmalya.aathithya.webmodule.user.model.UserAccessModel;

@Controller
@RequestMapping(value = "his")
public class HISOTReservationController {

	Logger logger = LoggerFactory.getLogger(HISOTReservationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/ot-reservation")
	public String otReserve(Model model, HttpSession session) {
		logger.info("Method : otReserve method starts");

		DropDownModel[] department = restTemplate.getForObject(env.getHisUrl() + "/departmentList",
				DropDownModel[].class);
		List<DropDownModel> departmentList = Arrays.asList(department);
		model.addAttribute("departmentList", departmentList);

		DropDownModel[] group = restTemplate.getForObject(env.getHisUrl() + "/groupList", DropDownModel[].class);
		List<DropDownModel> groupList = Arrays.asList(group);
		model.addAttribute("groupList", groupList);

		DropDownModel[] surgery = restTemplate.getForObject(env.getHisUrl() + "/surgeryList", DropDownModel[].class);
		List<DropDownModel> surgeryList = Arrays.asList(surgery);
		model.addAttribute("surgeryList", surgeryList);

		DropDownModel[] subGroup = restTemplate.getForObject(env.getHisUrl() + "/subGroupList", DropDownModel[].class);
		List<DropDownModel> subGroupList = Arrays.asList(subGroup);
		model.addAttribute("subGroupList", subGroupList);

		DropDownModel[] emp = restTemplate.getForObject(env.getHisUrl() + "/empList", DropDownModel[].class);
		List<DropDownModel> empList = Arrays.asList(emp);
		model.addAttribute("empList", empList);

		logger.info("Method : otReserve method ends");
		return "his/ot-reservation-backup.html";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "ot-reserve-patientList" })
	public @ResponseBody JsonResponse<HISPatientModel> getOTPatientList(Model model, @RequestBody String searchValue) {
		logger.info("Method : getOTPatientList starts");
		JsonResponse<HISPatientModel> res = new JsonResponse<HISPatientModel>();

		try {
			res = restTemplate.getForObject(env.getHisUrl() + "getOTPatientList?id=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getOTPatientList ends");
		return res;
	}

	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "ot-reservation-add" })
	public @ResponseBody JsonResponse<Object> addOtReserve(HttpSession session,
			@RequestBody HISOTReservationModel data) {
		logger.info("Method : addOtReserve starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		System.out.println("data=======" + data);
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
		data.setCreatedBy(userId);
		data.setOrgName(organization);
		data.setOrgDiv(orgDivision);

		System.out.println("detailsss ===" + data);
		try {
			res = restTemplate.postForObject(env.getHisUrl() + "addOtReserve", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addOtReserve ends" + res);
		return res;

	}

	@SuppressWarnings("unchecked")

	@GetMapping("ot-reservation-view")
	public @ResponseBody List<HISOTReservationModel> viewReservation(HttpSession session) {

		logger.info("Method : viewReservation Start");

		JsonResponse<List<HISBedMasterModel>> resp = new JsonResponse<List<HISBedMasterModel>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-ot-reservation-view", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<HISOTReservationModel> viewMaster = mapper.convertValue(resp.getBody(),
				new TypeReference<List<HISOTReservationModel>>() {
				});

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : viewReservation ends" + viewMaster);
		return viewMaster;

	}

	@SuppressWarnings("unchecked")

	@GetMapping("ot-reservation-edit")
	public @ResponseBody JsonResponse<HISOTReservationModel> editReservation(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editReservation starts");
		JsonResponse<HISOTReservationModel> jsonResponse = new JsonResponse<HISOTReservationModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USERID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("id====" + id);

		try {
			jsonResponse = restTemplate.getForObject(env.getHisUrl() + "editReservation?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		HISOTReservationModel Model = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<HISOTReservationModel>() {
				});

		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editReservation ends" + jsonResponse);
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-patient-subGroupList" })
	public @ResponseBody JsonResponse<Object> subGroupList(@RequestParam String id) {
		logger.info("Method : subGroupList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "subGroupListData?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : subGroupList ends");
		return res;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "ot-reservation-get-employee-listing" })
	public @ResponseBody List<RolesAccessModel> getResEmployeeList(Model model, HttpSession session) {
		logger.info("Method : getResEmployeeList starts");

		JsonResponse<List<RolesAccessModel>> res = new JsonResponse<List<RolesAccessModel>>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getHisUrl() + "getResEmployeeList?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<RolesAccessModel> dataList = mapper.convertValue(res.getBody(),
					new TypeReference<List<RolesAccessModel>>() {
					});

			for (RolesAccessModel m : dataList) {
				if (m.getRoleStatus()) {
					m.setStatus("Active");
				} else {
					m.setStatus("Inactive");
				}

				if (m.getCreatedBy() == null || m.getCreatedBy() == "") {
					m.setCreatedBy("SYSTEM");
				}
			}

			res.setBody(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getResEmployeeList ends");
		return res.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/ot-reserve-save-data")
	public @ResponseBody JsonResponse<Object> saveOtReserve(@RequestBody HISOTReservationModel data, HttpSession session) {
		logger.info("Method : saveOtReserve starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "saveOtReserve", data, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveOtReserve ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "ot-reserve-view-data" })
	public @ResponseBody List<HISOTReservationModel> getReserveList(Model model, HttpSession session) {
		logger.info("Method : getReserveList starts");
		
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HISOTReservationModel>> res = new JsonResponse<List<HISOTReservationModel>>();

		try {
			res = restTemplate.getForObject(env.getHisUrl() + "getReserveList", JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<HISOTReservationModel> dataList = mapper.convertValue(res.getBody(),
					new TypeReference<List<HISOTReservationModel>>() {
					});

			if(dataList.size() > 0) {
			for (HISOTReservationModel m : dataList) {

				if (m.getCreatedBy() == null || m.getCreatedBy() == "") {
					m.setCreatedBy("SYSTEM");
				}
			}}

			res.setBody(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getReserveList ends");
		return res.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/ot-reserve-edit-data")
	public @ResponseBody JsonResponse<HISOTReservationModel> editReserveList(Model model, @RequestBody String data,
			HttpSession session, BindingResult result) {
		logger.info("Method : editReserveList starts");

		JsonResponse<HISOTReservationModel> resp = new JsonResponse<HISOTReservationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "editReserveList?id=" + data, JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			HISOTReservationModel userData = mapper.convertValue(resp.getBody(),
					new TypeReference<HISOTReservationModel>() {
					});

		
			if(userData.getRoleName()!=null && userData.getRoleName()!="") {
				String[] arr = userData.getRoleName().split(",");
				
				List<String> roleNameList = new ArrayList<String>();
				
				for(int i = 0; i < arr.length; i++) {
					roleNameList.add(arr[i]);
				}
				
				userData.setRoleNameList(roleNameList);
			}
			
			resp.setBody(userData);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : editReserveList starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/ot-reserved-delete")
	public @ResponseBody JsonResponse<Object> deleteReserved(@RequestBody List<DropDownModel> roleList, HttpSession session) {
		logger.info("Method : deleteReserved starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(DropDownModel m : roleList) {
			m.setName(userId);
		}
		
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "deleteReserved",roleList,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : deleteReserved starts");
		return resp;
	}
}
