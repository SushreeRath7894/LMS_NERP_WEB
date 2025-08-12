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
import nirmalya.aathithya.webmodule.trial.model.StationwiseRefuellingModel;

@Controller
@RequestMapping(value = { "trial" })
public class StationwiseRefuellingController {

	Logger logger = LoggerFactory.getLogger(StationwiseRefuellingController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	StationwiseRefuellingController stationwiseRefuellingController;

	@GetMapping(value = { "stationwise-fuel-entry" })
	public String fuelingDetails(Model model, HttpSession session) {
		logger.info("Method : Details starts");
		
		try {

			DropDownModel[] vehicleddm = restTemplate.getForObject(env.getTrialUrl() + "fetch-vehiclereg-list",
					DropDownModel[].class);
			List<DropDownModel> vehicleRegList = Arrays.asList(vehicleddm);

			model.addAttribute("vehicleRegList", vehicleRegList);
			System.out.println("##################" + vehicleRegList);
		} catch (Exception e) {
			e.printStackTrace();

		}

		try {

			DropDownModel[] fuelddm = restTemplate.getForObject(env.getTrialUrl() + "fetch-fueltype-list",
					DropDownModel[].class);
			List<DropDownModel> fuelTypeList = Arrays.asList(fuelddm);

			model.addAttribute("fuelTypeList", fuelTypeList);
			System.out.println("##################" + fuelTypeList);
		} catch (Exception e) {
			e.printStackTrace();

		}

		try {

			DropDownModel[] fuelsddm = restTemplate.getForObject(env.getTrialUrl() + "fetch-fuelstation-list",
					DropDownModel[].class);
			List<DropDownModel> fuelStationList = Arrays.asList(fuelsddm);

			model.addAttribute("fuelStationList", fuelStationList);
			System.out.println("##################" + fuelStationList);
		} catch (Exception e) {
			e.printStackTrace();

		} 

		logger.info("Method : Details ends");

		return "trial/stationwise-fuel-entry";
	}

	// add

	@SuppressWarnings("unchecked")

	@PostMapping("stationwise-fuel-entry-add")
	public @ResponseBody JsonResponse<Object> addStationFuelData(Model model, HttpSession session,

			@RequestBody StationwiseRefuellingModel stationwiseRefuellingModel) {

		logger.info("Method : addStationFuelData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println(stationwiseRefuellingModel);

		try {
			logger.info("abababababababababab");
			logger.info("stationwiseRefuellingModel" + stationwiseRefuellingModel);
			resp = restTemplate.postForObject(env.getTrialUrl() + "rest-addStationFuelData",
					stationwiseRefuellingModel, JsonResponse.class);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");

		}

		logger.info("Method : addStationFuelData ends");
		System.out.println("resp" + resp);

		return resp;

	}
	
	
	
	// view
		@SuppressWarnings("unchecked")

		@GetMapping("stationwise-fuel-entry-view")
		public @ResponseBody List<StationwiseRefuellingModel> viewStationRefuelDoc(HttpSession session) {
			logger.info("Method : viewStationRefuelDoc starts");

			JsonResponse<List<StationwiseRefuellingModel>> resp = new JsonResponse<List<StationwiseRefuellingModel>>();
			List<StationwiseRefuellingModel> returnList = new ArrayList<StationwiseRefuellingModel>();

			try {
				resp = restTemplate.getForObject(env.getTrialUrl() + "view-StationRefuelDoc", JsonResponse.class);
				returnList = resp.getBody();
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("VIEWWWWWWWWWWWWWW   " + resp.getBody());
			logger.info("Method : viewStationRefuelDoc ends");
			
			System.out.println("View the data= " + returnList);
			return returnList;	

		}
		
		
		
		
		// delete
		@SuppressWarnings("unchecked")
		@PostMapping("stationwise-fuel-entry-delete")
		public @ResponseBody JsonResponse<Object> deleteStationwiseRefuelDoc(HttpSession session, Model model,
				@RequestParam String id) {

			logger.info("deleteStationwiseRefuelDoc starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			System.out.println("id is:in web" + id);
			try {
				resp = restTemplate.getForObject(env.getTrialUrl() + "deleteStationwiseRefuelDoc?id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = resp.getMessage();

			if (message != null && message != "") {

			} else {

				resp.setMessage("Success");
			}
			System.out.println("resp" + resp);
			logger.info("deleteStationwiseRefuelDoc ends");
			logger.info("Method : deleteStationwiseRefuelDoc ends");
			return resp;

		}
		
		
		
		
		// edit

		@SuppressWarnings("unchecked")
		@GetMapping("stationwise-fuel-entry-edit")
		public @ResponseBody JsonResponse<StationwiseRefuellingModel> legalStationRefuelEdit(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : legalStationRefuelEdit starts");

			JsonResponse<StationwiseRefuellingModel> jsonResponse = new JsonResponse<StationwiseRefuellingModel>();
			System.out.println(id);
			try {
				jsonResponse = restTemplate.getForObject(env.getTrialUrl() + "legalStationRefuelEdit?id=" + id, JsonResponse.class);
				System.out.println("jsonResponse==" + jsonResponse);
				ObjectMapper mapper = new ObjectMapper();
				StationwiseRefuellingModel stationwiseRefuellingModel = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<StationwiseRefuellingModel>() {
						});

				jsonResponse.setBody(stationwiseRefuellingModel);
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
			logger.info("Method : legalStationRefuelEdit ends");
			return jsonResponse;
		}

}
