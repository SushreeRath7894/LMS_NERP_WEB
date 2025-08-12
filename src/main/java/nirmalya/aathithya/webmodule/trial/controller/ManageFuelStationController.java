package nirmalya.aathithya.webmodule.trial.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.trial.model.ManageFuelStationModel;
import nirmalya.aathithya.webmodule.master.model.AssetClassificationModel;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;

import nirmalya.aathithya.webmodule.procurment.model.InventoryRequisitionModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;

@Controller
@RequestMapping(value = "trial/")

public class ManageFuelStationController {
	Logger logger = LoggerFactory.getLogger(ManageFuelStationController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "manage-fuel-station" })
	public String manageRolesm(Model model, HttpSession session) {
		logger.info("Method : organisation starts");

		try {
			DropDownModel[] getvendordrop = restTemplate.getForObject(env.getTrialUrl() + "fetch-vendor-list-dropdown",
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(getvendordrop);
			model.addAttribute("vendorList", vendorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : organisation starts");
		return "trial/managefuelstation";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("manage-fuel-station-add")
	public @ResponseBody JsonResponse<Object> saveInsuranceDetailsList(
			@RequestBody ManageFuelStationModel ManageFuelStationModel, HttpSession session) {
		logger.info("Method: Education save starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		System.out.println("DDDDDDDDDDDD" + ManageFuelStationModel);
		try {
			res = restTemplate.postForObject(env.getTrialUrl() + "managefuelstation-add-master", ManageFuelStationModel,
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
	@GetMapping("manage-fuel-station-view")
	public @ResponseBody List<ManageFuelStationModel> viewFuelDetails(HttpSession session) {
		logger.info("Method : viewEmployeeDemo starts");

		JsonResponse<List<ManageFuelStationModel>> resp = new JsonResponse<List<ManageFuelStationModel>>();
		List<ManageFuelStationModel> mfsreturnlist = new ArrayList<ManageFuelStationModel>();
		System.out.println("rssssssssssssssss:" + mfsreturnlist);

		try {
			resp = restTemplate.getForObject(env.getTrialUrl() + "managefuelstation-view-master", JsonResponse.class);
			mfsreturnlist = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		System.out.println(mfsreturnlist);
		logger.info("Method : viewStudentDemo ends");
		return mfsreturnlist;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("manage-fuel-station-edit")
	public @ResponseBody JsonResponse<ManageFuelStationModel> editFuel(Model model, @RequestParam String stationId,
			HttpSession session) {

		logger.info("Method : editDistrict starts");

		JsonResponse<ManageFuelStationModel> jsonResponse = new JsonResponse<ManageFuelStationModel>();
		System.out.println(stationId);
		try {
			jsonResponse = restTemplate.getForObject(
					env.getTrialUrl() + "managefuelstation-edit?stationId=" + stationId, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			ManageFuelStationModel ManageFuelStationModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<ManageFuelStationModel>() {
					});

			jsonResponse.setBody(ManageFuelStationModel);
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

	@PostMapping("manage-fuel-station-delete")
	public @ResponseBody JsonResponse<ManageFuelStationModel> deleteMFuelStation(Model model,
			@RequestParam String stationId, HttpSession session) {
		logger.info("Method : deleteMFuelStation starts");

		JsonResponse<ManageFuelStationModel> resp = new JsonResponse<ManageFuelStationModel>();
		System.out.println(stationId);

		try {
			resp = restTemplate.getForObject(env.getTrialUrl() + "managefuelstation-delete?stationId=" + stationId,
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

		logger.info("Method :  deleteMFuelStation ends");

		System.out.println("resp" + resp);
		return resp;
	}

}
