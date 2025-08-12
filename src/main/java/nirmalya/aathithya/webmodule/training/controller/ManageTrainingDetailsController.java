package nirmalya.aathithya.webmodule.training.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.training.model.ManageTrainingDetailsModel;
import nirmalya.aathithya.webmodule.training.model.ManageTrainingDocumentModel;

@Controller
@RequestMapping(value = "training")
public class ManageTrainingDetailsController {

	Logger logger = LoggerFactory.getLogger(ManageTrainingDetailsController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/manage-training")
	public String addTrainingDetails(Model model, HttpSession session) {

		logger.info("Method : addTrainingDetails starts");

		logger.info("Method : addTrainingDetails ends");
		return "training/manage-training-details";
	}

	/*
	 * This method is used to save the parent and child category
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-training-category-save")
	public @ResponseBody JsonResponse<Object> saveTrainingCategory(@RequestBody ManageTrainingDetailsModel category,
			HttpSession session) {
		logger.info("Method : saveTrainingCategory starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		category.setCreatedBy(userId);
		category.setOrganization(organization);
		category.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getTrainingUrl() + "save-training-category", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveTrainingCategory starts");
		return resp;
	}

	/*
	 * This method is used to view category list
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-training-category-get-total-list")
	public @ResponseBody JsonResponse<List<ManageTrainingDetailsModel>> getAllTrainingCategoryList(
			HttpSession session) {
		logger.info("Method : getAllTrainingCategoryList starts");

		JsonResponse<List<ManageTrainingDetailsModel>> resp = new JsonResponse<List<ManageTrainingDetailsModel>>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(organization);
		System.out.println(orgDivision);
		System.out.println("get-training-category-dataListModal?organization="+ organization + "&orgDivision=" + orgDivision);
		try {
			
			resp = restClient.getForObject(env.getTrainingUrl() + "get-training-category-dataListModal?organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAllTrainingCategoryList ends");
		return resp;
	}

	/*
	 * This post method is used to save sub-category
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-training-category-save-subcat")
	public @ResponseBody JsonResponse<Object> saveTrainingSubCategory(@RequestBody ManageTrainingDetailsModel category,
			HttpSession session) {
		logger.info("Method : saveTrainingSubCategory starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		category.setCreatedBy(userId);
		category.setOrganization(organization);
		category.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getTrainingUrl() + "save-training-subcategory", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveTrainingSubCategory starts");
		return resp;
	}

	/*
	 * This method is used to delete categories
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-training-category-delete")
	public @ResponseBody JsonResponse<Object> deleteTrainingCategory(@RequestBody String id, HttpSession session) {
		logger.info("Method : deleteTrainingCategory starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(
					env.getTrainingUrl() + "delete-training-category?id=" + id + "&createdBy=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : deleteTrainingCategory starts");
		return resp;
	}

	/*
	 * Edit category by id
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-training-category-dtls-by-id")
	public @ResponseBody JsonResponse<ManageTrainingDetailsModel> getTrainingCategoryById(@RequestBody String id,
			HttpSession session) {
		logger.info("Method : getTrainingCategoryById starts");

		JsonResponse<ManageTrainingDetailsModel> resp = new JsonResponse<ManageTrainingDetailsModel>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getTrainingUrl() + "get-training-categoryById?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getTrainingCategoryById starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-training-save-studymaterial")
	public @ResponseBody JsonResponse<Object> saveTrainingStudyMaterials(
			@RequestBody List<ManageTrainingDetailsModel> category, HttpSession session) {
		logger.info("Method : saveTrainingStudyMaterials starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ManageTrainingDetailsModel m : category) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}

		JSONObject json = new JSONObject();
		if (category.get(0).getDocumentList().size() > 0) {
			for (ManageTrainingDocumentModel a : category.get(0).getDocumentList()) {
				String delimiters = "\\.";
				String[] x = a.getFileName().split(delimiters);
				for (String s1 : a.getDocumentFile()) {
					if (s1 != null) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							json = saveAllMediaDocuments(bytes, x[1].toString(), category.get(0).getStudyMaterialType(),
									category.get(0).getCreatedBy());

						} catch (Exception e) {
							e.printStackTrace();
						}
						a.setDocumentURL(json.getString("fileurl"));
					}
				}
			}
		}

		try {
			resp = restClient.postForObject(env.getTrainingUrl() + "save-training-studymaterial", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveTrainingStudyMaterials starts");
		return resp;
	}

	public JSONObject saveAllMediaDocuments(byte[] imageBytes, String ext, String filetype, String user_id) {
		logger.info("Method : saveAllMedicalDocuments starts");

		String imageName = null;
		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();

				if (filetype.equals("Video")) {
					ext = "mp4";
				}
				if (filetype.equals("Audio")) {
					ext = "mp3";
				}
				if (ext.contentEquals("jpeg")) {
					imageName = user_id + "_" + nowTime + ".jpg";
				} else {
					imageName = user_id + "_" + nowTime + "." + ext;
				}
			}

			Path path = Paths.get(env.getFileUploadTraining() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String url = env.getTrainingUrl() + imageName;

		JSONObject json = new JSONObject();

		try {
			json.put("filename", imageName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			json.put("fileurl", url);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : saveAllMediaDocuments ends");
		return json;
	}

}
