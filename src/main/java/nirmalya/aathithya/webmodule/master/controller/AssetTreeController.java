package nirmalya.aathithya.webmodule.master.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.AssetClassificationModel;


@Controller
@RequestMapping(value = { "master/" })
public class AssetTreeController {
	Logger logger = LoggerFactory.getLogger(AssetTreeController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "asset-classification" })
	public String assetClassification(Model model, HttpSession session) {
		logger.info("Method : assetClassification starts");

		logger.info("Method : assetClassification ends");
		return "master/asset-tree";

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/asset-classification-add")
	public @ResponseBody JsonResponse<Object> saveAssetClassification(
			@RequestBody AssetClassificationModel classification, HttpSession session) {
		logger.info("Method : saveAssetClassification starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		classification.setCreatedBy(userId);

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveAssetClassification", classification,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAssetClassification starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/asset-classification-view")
	public @ResponseBody JsonResponse<List<AssetClassificationModel>> getAllAssetList(HttpSession session) {
		logger.info("Method : getAllDemoList starts");

		JsonResponse<List<AssetClassificationModel>> resp = new JsonResponse<List<AssetClassificationModel>>();

		try {

			resp = restClient.getForObject(env.getMasterUrl() + "getAllAssetList", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAllAssetList starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/asset-classification-subcat")
	public @ResponseBody JsonResponse<Object> saveAssetSubCategory(@RequestBody AssetClassificationModel category,
			HttpSession session) {
		logger.info("Method : saveAssetSubCategory starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		category.setCreatedBy(userId);

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveAssetSubCategory", category, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveAssetSubCategory ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/asset-classification-edit-by-id")
	public @ResponseBody JsonResponse<AssetClassificationModel> getAssetById(@RequestBody String id,
			HttpSession session) {
		logger.info("Method : getDemoById starts");
		logger.info(id);
		JsonResponse<AssetClassificationModel> resp = new JsonResponse<AssetClassificationModel>();

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getAssetById?id=" + id, JsonResponse.class);
			logger.info(id);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAssetById starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/asset-classification-delete")
	public @ResponseBody JsonResponse<Object> deleteAsset(@RequestBody String id, HttpSession session) {
		logger.info("Method : deleteAsset starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteAsset?id=" + id + "&createdBy=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : deleteAsset starts");
		return resp;
	}
}