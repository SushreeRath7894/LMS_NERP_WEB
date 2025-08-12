package nirmalya.aathithya.webmodule.master.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import nirmalya.aathithya.webmodule.master.model.MasterModel;
import nirmalya.aathithya.webmodule.master.model.MasterWarehouseModel;

@Controller
@RequestMapping(value = "configuration")
public class MasterController {

	Logger logger = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/view-master-data")
	public @ResponseBody List<MasterModel> viewMasterData(HttpSession session, @RequestParam String type) {
		logger.info("Method : viewMasterData start");

		JsonResponse<List<MasterModel>> jsonResponse = new JsonResponse<List<MasterModel>>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "rest-viewmasterdata?org=" + organization
					+ "&orgDiv=" + orgDivision + "&type=" + type, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewMasterData ends");
		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/add-master-data")
	public @ResponseBody JsonResponse<Object> addMasterData(@RequestBody MasterModel masterModel, Model model,
			HttpSession session) {
		logger.info("Method : addMasterData starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			masterModel.setOrganization(organization);
			masterModel.setOrgDivision(orgDivision);
			masterModel.setCreatedBy(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "rest-addmasterdata", masterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addMasterData ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/delete-master-data")
	public @ResponseBody JsonResponse<Object> deleteMasterData(HttpSession session, @RequestParam String id,
			@RequestParam String type, @RequestParam Optional<String> imgurl) {
		logger.info("Method : deleteMasterData starts");

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

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deletemasterdata?id=" + id + "&type=" + type
					+ "&userId=" + userId + "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success") && type.equals("WHIMG")) {
			Path path = Paths.get(env.getFileUploadMaster() + imgurl.get());
			try {
				Files.deleteIfExists(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Method : deleteMasterData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/getSubCatData")
	public @ResponseBody JsonResponse<Object> getSubCatData(HttpSession session, @RequestParam String id) {
		logger.info("Method : getSubCatData starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getSubCatData?id=" + id + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getSubCatData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/getProjectSubCatData")
	public @ResponseBody JsonResponse<Object> getProjectSubCatData(HttpSession session, @RequestParam String id) {
		logger.info("Method : getProjectSubCatData starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getProjectSubCatData?id=" + id + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getProjectSubCatData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/change-password")
	public @ResponseBody JsonResponse<Object> changePassword(HttpSession session,
			@RequestBody DropDownModel data) {
		logger.info("Method : changePassword starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String userPassword = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			userPassword = (String) session.getAttribute("USER_PASSWORD");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PasswordEncoder pass = new BCryptPasswordEncoder();
		
		if(pass.matches(data.getKey(), userPassword)) {
			
			data.setCreatedBy(userId);
			data.setOrgName(orgName);
			data.setOrgDivision(orgDivision);
			data.setCode(pass.encode(data.getCode()));
			
			try {
				resp = restTemplate.postForObject(env.getMasterUrl() + "rest-changePassword", data, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			resp.setMessage("Old password is not matched");
			resp.setCode("failed");
			return resp;
		}

		logger.info("Method : changePassword ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/get-shift-details")
	public @ResponseBody JsonResponse<Object> getShiftDetails(HttpSession session, @RequestParam String dt1, @RequestParam String dt2) {
		logger.info("Method : getShiftDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getShiftDetails?dt1=" + dt1 + "&dt2=" + dt2 + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getShiftDetails ends");
		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/manage-offDays-employeeList" })
	public @ResponseBody JsonResponse<DropDownModel> EmployeeAutoSearch(Model model, @RequestBody String searchValue,
			BindingResult result, HttpSession session) {
		logger.info("Method : EmployeeAutoSearch starts");
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
			res = restTemplate.getForObject(env.getMasterUrl() + "employee-autosearch?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : EmployeeAutoSearch ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-master-offDays-Data" })
	public @ResponseBody JsonResponse<Object> viewMasterOffdaysData(Model model, HttpServletRequest request, HttpSession session) {
		logger.info("Method : viewMasterOffdaysData starts");
		
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-viewMasterOffdaysData?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewMasterOffdaysData ends");
		return res;
		 
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/add-master-OffDays-Data")
	public @ResponseBody JsonResponse<Object> saveOffDaysMaster(@RequestBody MasterWarehouseModel data, HttpSession session) {
		logger.info("Method : saveOffDaysMaster starts");
		
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
		
		data.setCreatedBy(userId);
		data.setOrg(organization);
		data.setOrgDiv(orgDivision);
		
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveOffDaysMaster", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : saveOffDaysMaster starts");
		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("/delete-master-OffDays-data")
	public @ResponseBody JsonResponse<Object> deleteMasterOffDaysData(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteMasterOffDaysData starts");

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

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteMasterOffDaysData?id=" + id 
					+ "&userId=" + userId + "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}


		logger.info("Method : deleteMasterOffDaysData ends");
		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "get-holiday-list" })
	public @ResponseBody JsonResponse<Object> viewHolidayList(Model model, HttpServletRequest request, HttpSession session) {
		logger.info("Method : viewHolidayList starts");
		
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-viewHolidayList?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewHolidayList ends");
		return res;
		 
	}
	
	//
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "get-paymentMode-list" })
	public @ResponseBody JsonResponse<Object> viewPModeList(Model model, HttpServletRequest request, HttpSession session) {
		logger.info("Method : viewPModeList starts");
		
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-viewPModeList?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewPModeList ends");
		return res;
		 
	}
	
	//
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "get-sac-list" })
	public @ResponseBody JsonResponse<Object> viewSac(Model model, HttpServletRequest request, HttpSession session) {
		logger.info("Method : viewSac starts");
		
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-viewSac?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewSac ends");
		return res;
		 
	}
}
