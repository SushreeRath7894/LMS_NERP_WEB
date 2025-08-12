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
public class DraftController {

	Logger logger = LoggerFactory.getLogger(DraftController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/draft")
	public String draftDetails(Model model, HttpSession session) {

		logger.info("Method : draftDetails starts");

		logger.info("Method : draftDetails ends");
		return "training/draft";
	}
	
	
	// viewQc

	@SuppressWarnings("unchecked")

	@GetMapping("draft-training-view")
	public @ResponseBody Object viewDraft(HttpSession session) {
		logger.info("Method :viewDraft starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getTrainingUrl() + "rest-draft-training-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("view===" + resp);
		logger.info("Method :viewDraft ends");
		return resp;
	}
	
	
	
	// deleteAssetPolicy

	@SuppressWarnings("unchecked")
	@PostMapping("draft-training-delete")
	public @ResponseBody JsonResponse<Object> deleteDraft(@RequestParam String id,String categoryName, Model model, HttpSession session) {
		logger.info("Method : deleteDraft function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTrainingUrl() + "rest-draft-training-delete?id=" + id + "&categoryName=" + categoryName+ "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDraft function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}


	// deleteAssetPolicy

	@SuppressWarnings("unchecked")
	@PostMapping("draft-training-deleteSc")
	public @ResponseBody JsonResponse<Object> deleteDraftSc(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteDraftSc function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getTrainingUrl() + "rest-draft-training-deleteSc?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDraftSc function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}



}
