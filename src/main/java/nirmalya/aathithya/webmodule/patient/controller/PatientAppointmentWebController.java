package nirmalya.aathithya.webmodule.patient.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.RegsPatientModel;

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

@Controller
@RequestMapping(value = "patient")
public class PatientAppointmentWebController {
    Logger logger = LoggerFactory.getLogger(PatientAppointmentWebController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EnvironmentVaribles env;
    
    @GetMapping("/appointment")
    public String appointMent(HttpSession session,Model model) {
        logger.info("Start of method : Appointment Page");
        
        try {

			DropDownModel[] type = restTemplate.getForObject(env.getHisUrl() + "getAmbulanceTypeList",
					DropDownModel[].class);
			List<DropDownModel> getAmbulanceTypeList = Arrays.asList(type);
			model.addAttribute("ambulanceTypeList", getAmbulanceTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] requirement = restTemplate.getForObject(env.getHisUrl() + "getAmbulanceRequirement",
					DropDownModel[].class);
			List<DropDownModel> getAmbulanceRequirement = Arrays.asList(requirement);
			model.addAttribute("ambulanceRequirementList", getAmbulanceRequirement);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] gender = restTemplate.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			DropDownModel[] Country = restTemplate.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(Country);
			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
        logger.info("End of method : Appointment Page");
        return "patient/patientSelfService/patient-appointment";
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = { "appointment-department-list" })
    public @ResponseBody JsonResponse<Object> getDepartmentList(HttpSession session, @RequestParam String id) {
        logger.info("Method : department  list starts" + id);
        JsonResponse<Object> res = new JsonResponse<Object>();
        try {
            String org = (String) session.getAttribute("ORGANIZATION");
            String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

            res = restTemplate.getForObject(
                    env.getHisUrl() + "rest-department-list?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
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
        logger.info("state" + res);
        logger.info("Method : department list ends");
        return res;
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = { "appointment-doctorList" })
    public @ResponseBody JsonResponse<Object> getDoctorList(HttpSession session, @RequestParam String from,
                                                            @RequestParam String deptId) {
        logger.info("Method : getDoctorList starts" + from);
        JsonResponse<Object> res = new JsonResponse<Object>();
        try {

            String orgName = (String) session.getAttribute("ORGANIZATION");
            String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
            String userId = (String) session.getAttribute("USER_ID");

            res = restTemplate
                    .getForObject(
                            env.getMasterUrl() + "restgetproducttypewise?orgName=" + orgName + "&orgDivision="
                                    + orgDivision + "&userId=" + userId + "&from=" + from + "&deptId=" + deptId,
                            JsonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res.getMessage() != null) {
            res.setCode(res.getCode());
            res.setMessage(res.getMessage());
        } else {
            res.setMessage("Unsuccess");
        }
        logger.info("state" + res);
        logger.info("Method : getDoctorList ends");
        return res;
    }

    @SuppressWarnings("unchecked")
    @GetMapping("appointment-ambulance-list-view")
    public @ResponseBody Object viewAmbulanceList(HttpSession session, @RequestParam String from) {
        logger.info("Method :viewAmbulanceList starts");

        JsonResponse<Object> resp = new JsonResponse<Object>();

        String userId = "";
        String orgName = "";
        String orgDivision = "";

        try {
            userId = (String) session.getAttribute("USER_ID");
            orgName = (String) session.getAttribute("ORGANIZATION");
            orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            resp = restTemplate
                    .getForObject(
                            env.getMasterUrl() + "restgetproducttypewise?orgName=" + orgName + "&orgDivision="
                                    + orgDivision + "&userId=" + userId + "&from=" + from + "&deptId=",
                            JsonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Method :viewAmbulanceList ends");
        return resp;
    }
    
 // ADD APPOINTMENT DATA
 	@SuppressWarnings("unchecked")
 	@PostMapping("/appointment-patient-save-data")
 	public @ResponseBody JsonResponse<Object> addAppointmentData(
 			@RequestBody Map<String, Object> jsonAppointmentData, HttpSession session) {

 		logger.info("Method : addAppointmentData starts");

 		JsonResponse<Object> resp = new JsonResponse<>();
 		String organization = "";
 		String orgDivision = "";
 		String createdById = "";

 		try {
 			organization = (String) session.getAttribute("ORGANIZATION");
 			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
 			createdById = (String) session.getAttribute("USER_ID");
 		} catch (Exception e) {
 			logger.error("Error retrieving session attributes", e);
 		}

 		try {
 			 

 			String url = env.getPatientUrl() + "rest-add-appointment-data";
 			System.out.println("URL For Discharge Data=====>" + url);

 			jsonAppointmentData.put("orgName", organization);
 			jsonAppointmentData.put("orgDiv", orgDivision);
 			jsonAppointmentData.put("createdById", createdById);

 			logger.info("Sending Discharge Data to the service: " + jsonAppointmentData);

 			resp = restTemplate.postForObject(url, jsonAppointmentData, JsonResponse.class);

 		} catch (Exception e) {
 			logger.error("Error in addAppointmentData: ", e);
 		}

 		logger.info("Method : addAppointmentData ends");
 		return resp;
 	}
// VIEW APPOINTMENT DATA	
 	@SuppressWarnings("unchecked")
	@GetMapping("appointment-get-all-bookings")
	public @ResponseBody Object getAllAppointmentData(HttpSession session) {

		logger.info("Method : getAllAppointmentData starts");
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
			resp = restTemplate.getForObject(env.getPatientUrl() + "rest-get-all-appointment-data?orgName=" + organization + "&orgDiv="
							+ orgDivision + "&userId=" + userId,JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getAllAppointmentData: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getAllAppointmentData ends");

		return resp;
	}
 	
 	@SuppressWarnings("unchecked")
	@GetMapping("appointment-edit-booking-data")
	public @ResponseBody Object getTheAppointmentData(HttpSession session,@RequestParam String bookingId,@RequestParam String bookingType) {

		logger.info("Method : getTheAppointmentData starts");
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
			resp = restTemplate.getForObject(env.getPatientUrl() + "rest-get-appointment-data?orgName=" + organization + "&orgDiv="
							+ orgDivision + "&userId=" + userId +"&bookingId="+bookingId+"&bookingType="+bookingType,JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getTheAppointmentData: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getTheAppointmentData ends");

		return resp;
	}
 	
 	@SuppressWarnings("unchecked")
	@GetMapping("appointment-delete-data")
	public @ResponseBody Object deleteAppointment(@RequestParam String appointmentId,@RequestParam String appointmentType, HttpSession session) {

		logger.info("Method : deleteAppointment starts");
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

			resp = restTemplate.getForObject(env.getPatientUrl() + "rest-delete-appointment?appointmentId=" + appointmentId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId + "&appointmentType=" +appointmentType, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in deleteAppointment: ", e);
			e.printStackTrace();
		}

		logger.info("Method : deleteAppointment ends");

		return resp;
	}
 	@SuppressWarnings("unchecked")
	@PostMapping("appointment-registration-with-test-list")
	public @ResponseBody JsonResponse<Object> patientRegistrationWithTestList(HttpSession session,
			@RequestBody RegsPatientModel data) {
		logger.info("Method : patientRegistrationWithTestList starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		data.setUserId(userId);
		data.setOrg(orgName);
		data.setOrgDiv(orgDivision);
		
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-patientRegistrationWithTestList", data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : patientRegistrationWithTestList ends");
		return resp;
	}
}
