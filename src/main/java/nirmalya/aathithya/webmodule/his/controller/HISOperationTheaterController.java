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
import nirmalya.aathithya.webmodule.his.model.HISOperationTheaterModel;

@Controller
@RequestMapping("his")
public class HISOperationTheaterController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HISOperationTheaterController.class);

	@GetMapping(value = { "/operation-theater" })
	public String viewOperationTheater(Model model, HttpSession session) {
		logger.info("Method : viewOperationTheater starts");

		try {
			DropDownModel[] floor = restClient.getForObject(env.getHisUrl() + "/floorList", DropDownModel[].class);

			List<DropDownModel> floorList = Arrays.asList(floor);
			model.addAttribute("floorList", floorList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}

		logger.info("Method : viewOperationTheater starts");
		return "his/his-operation-theater";
	}

	// manage-operation-theater-add
	@SuppressWarnings("unchecked")
	@PostMapping("manage-operation-theater-add")
	public @ResponseBody JsonResponse<Object> addOperationTheater(
			@RequestBody HISOperationTheaterModel operationTheaterModel, HttpSession session) {
		logger.info("Method: addOperationTheater starts");

		JsonResponse<Object> resp = new JsonResponse<>();

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
		operationTheaterModel.setCreatedBy(userId);
		operationTheaterModel.setOrganization(orgName);
		operationTheaterModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getHisUrl() + "restAddOperationTheater", operationTheaterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setMessage("Error during REST call");
		}

		if (resp.getMessage() == null || resp.getMessage().isEmpty()) {
			resp.setMessage("Success");
		}

		logger.info("Method: addOperationTheater ends");

		return resp;
	}

	// manage-operation-theater-view

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-operation-theater-view")
	public @ResponseBody List<HISOperationTheaterModel> viewOTDetails(HttpSession session) {
		logger.info("Method : viewOTDetails starts");
		String organization = "";
		String orgDivision = "";

		JsonResponse<List<HISOperationTheaterModel>> resp = new JsonResponse<>();
		List<HISOperationTheaterModel> returnList = new ArrayList<>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(
					env.getHisUrl() + "rest-viewOTDetails?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewOTDetails ends");
		return returnList;
	}
	
	//manage-operation-theater-delete

		@SuppressWarnings("unchecked")
		@GetMapping("/manage-operation-theater-delete")
		public @ResponseBody JsonResponse<Object> deleteOTDetails(HttpSession session, @RequestParam String id) {
			logger.info("Method : deleteOTDetails starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {
				resp = restClient.getForObject(env.getHisUrl() + "deleteOTDetails?id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = resp.getMessage();

			if (message != null && message != "") {

			} else {

				resp.setMessage("Success");
			}

			logger.info("Method : deleteOTDetails ends");
			return resp;
		}


}
