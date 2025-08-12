package nirmalya.aathithya.webmodule.property.stakeholder.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.property.stakeholder.model.StakeholderPropertyDetailsModel;

@Controller
@RequestMapping(value = "property")
public class StackholderPropertyDetailsController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(StakeholderDashboardController.class);

	/* Manage Property */

	@GetMapping(value = { "manage-property" })
	public String property(Model model, HttpSession session) {
		logger.info("Method :property starts");

		try {
			DropDownModel[] Country = restClient.getForObject(env.getPropertyUrl() + "getstackCityList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(Country);

			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Property = restClient.getForObject(env.getPropertyUrl() + "getproperty-type-List",
					DropDownModel[].class);
			List<DropDownModel> propertyList = Arrays.asList(Property);

			model.addAttribute("propertyList", propertyList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : property ends");
		return "new-property/manage-property";
	}

	@PostMapping("manage-property-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("insuranceFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		logger.info("img" + response);
		return response;
	}

	/// add
	@SuppressWarnings("unchecked")
	@PostMapping("manage-property-add")
	public @ResponseBody JsonResponse<Object> saveSac(
			@RequestBody StakeholderPropertyDetailsModel StakeholderPropertyDetailsModel, HttpSession session) {
		logger.info("Method: Property save starts" + StakeholderPropertyDetailsModel);
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("DDDDDDDDDDDD" + StakeholderPropertyDetailsModel);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("insuranceFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				StakeholderPropertyDetailsModel.setDocName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} // upload part
		try {
			res = restClient.postForObject(env.getPropertyUrl() + "manage-property-add-propertiesdetails",
					StakeholderPropertyDetailsModel, JsonResponse.class);
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

		logger.info("Method:Property save ends");
		return res;
	}

	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadProperty() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		logger.info(imageName);
		return imageName;

	}

	/// view
	@SuppressWarnings("unchecked")

	@GetMapping("/manage-property-view")
	public @ResponseBody List<StakeholderPropertyDetailsModel> viewSac(HttpSession session,@RequestParam String userid,@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : View starts"+userid+fromDate+toDate);

		JsonResponse<List<StakeholderPropertyDetailsModel>> resp = new JsonResponse<List<StakeholderPropertyDetailsModel>>();
		List<StakeholderPropertyDetailsModel> returnList = new ArrayList<StakeholderPropertyDetailsModel>();
		//logger.info(userid);
		//logger.info(fromDate);
		//logger.info(toDate);
		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "manage-property-viewProperty?userid="+userid+"&fromDate="+fromDate+"&toDate="+toDate, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		//logger.info("view" + returnList);
		logger.info("Method : View ends");
		return returnList;
	}

	/// edit
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-edit")
	public @ResponseBody JsonResponse<List<Object>> editSac(@RequestParam String id, HttpSession session) {

		logger.info("Method : editProperty starts" + id);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		try {
			jsonResponse = restClient.getForObject(env.getPropertyUrl() + "editProperty?id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ObjectMapper mapper = new ObjectMapper();

		/*
		 * ReminderMasterModel reimModel = mapper.convertValue(jsonResponse.getBody(),
		 * new TypeReference<ReminderMasterModel>() { });
		 * 
		 * jsonResponse.setBody(reimModel);
		 */

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editProperty ends");
		return jsonResponse;
	}

	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("manage-property-delete")
	public @ResponseBody JsonResponse<Object> deleteSac(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteProperty function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getPropertyUrl() + "manage-property-delete-PropertyDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteProperty function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}

}
