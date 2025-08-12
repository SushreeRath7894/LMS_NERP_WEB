package nirmalya.aathithya.webmodule.trial.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.trial.model.RefuelRequisitionModel;
@Controller
@RequestMapping(value = "trial/")

public class RefuelRequisitionController {
	Logger logger = LoggerFactory.getLogger(RefuelRequisitionController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "refuel-requisition" })
	public String manageRoles(Model model, HttpSession session) {
		logger.info("Method : organisation starts");
		
		
		try {
			DropDownModel[] country = restTemplate.getForObject(env.getTrialUrl() + "getVehiclenoLists-rest",
					DropDownModel[].class);
			List<DropDownModel> vendorRegNo = Arrays.asList(country);
			model.addAttribute("vendorRegNo", vendorRegNo);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		
		try {
			DropDownModel[] fuelStationId = restTemplate.getForObject(env.getTrialUrl() + "getFuelStationLists-list",
					DropDownModel[].class);
			List<DropDownModel> fuelStationList = Arrays.asList(fuelStationId);
			model.addAttribute("fuelStationList", fuelStationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] fuelTypeId = restTemplate.getForObject(env.getTrialUrl() + "getFuelTypeLists-list",
					DropDownModel[].class);
			List<DropDownModel> fuelTypeList = Arrays.asList(fuelTypeId);
			model.addAttribute("fuelTypeList", fuelTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		
		logger.info("Method : organisation starts");
		return "trial/refuelrequisition";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("refuelrequition-add")
	public @ResponseBody JsonResponse<Object> saveRefuelRequisitionList(@RequestBody RefuelRequisitionModel RefuelRequisitionModel,
			HttpSession session) {
		logger.info("Method: Education save starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
	
		System.out.println("DDDDDDDDDDDD" + RefuelRequisitionModel);
		try {
			res = restTemplate.postForObject(env.getTrialUrl() + "refuelrequition-add-master", RefuelRequisitionModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
//			
		}

		// System.out.println("ddddddddddddddddddddddddddddddddddd"+insuranceDetailsModel.getCountry());
		logger.info("Method: education save ends");
		return res;
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("refuelrequition-view")
	public @ResponseBody List<RefuelRequisitionModel> viewFuelDetails(HttpSession session) {
		logger.info("Method : viewEmployeeDemo starts");

		JsonResponse<List<RefuelRequisitionModel>> resp = new JsonResponse<List<RefuelRequisitionModel>>();
		List<RefuelRequisitionModel> returnList = new ArrayList<RefuelRequisitionModel>();
		System.out.println("rssssssssssssssss:"+returnList);

		try {
			resp = restTemplate.getForObject(env.getTrialUrl() + "refuelrequition-view-master", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		System.out.println(returnList);
		logger.info("Method : viewStudentDemo ends");
		return returnList;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("refuelrequition-edit")
	public @ResponseBody JsonResponse<RefuelRequisitionModel> editFuel(Model model, @RequestParam String refuelReqId, HttpSession session) {

		logger.info("Method : editDistrict starts");

		JsonResponse<RefuelRequisitionModel> jsonResponse = new JsonResponse<RefuelRequisitionModel>();
		System.out.println(refuelReqId);
		try {
			jsonResponse = restTemplate.getForObject(env.getTrialUrl() + "refuelrequition-edit?refuelReqId=" + refuelReqId, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			RefuelRequisitionModel RefuelRequisitionModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<RefuelRequisitionModel>() {
					});
	
		
		jsonResponse.setBody(RefuelRequisitionModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editDistrict ends");
		return jsonResponse;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("refuelrequition-delete")
	public @ResponseBody JsonResponse<RefuelRequisitionModel> deleteFuel(Model model, @RequestParam String refuelReqId,

			HttpSession session) {
		logger.info("Method : deleteEmployee starts");

		JsonResponse<RefuelRequisitionModel> resp = new JsonResponse<RefuelRequisitionModel>();
		System.out.println(refuelReqId);

		try {
			resp = restTemplate.getForObject(env.getTrialUrl() +  "refuelrequition-delete?refuelReqId=" + refuelReqId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :  deleteEmployee ends");

		System.out.println("resp" + resp);
		return resp;
	} 

	

}
