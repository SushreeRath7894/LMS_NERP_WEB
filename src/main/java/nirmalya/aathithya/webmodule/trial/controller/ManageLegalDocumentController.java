package nirmalya.aathithya.webmodule.trial.controller;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.nio.file.Paths;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.trial.model.ManageLegalDocumentModel;

@Controller
@RequestMapping(value = { "/trial" })
public class ManageLegalDocumentController {

	Logger logger = LoggerFactory.getLogger(ManageLegalDocumentController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	ManageLegalDocumentController manageLegalDocumentController;

	@GetMapping(value = { "manage-legal-document" })
	
	public String legalDetails(Model model, HttpSession session) {
		logger.info("Method : Details starts");
		
		
		try {

			DropDownModel[] tempdocument = restTemplate.getForObject(env.getTrialUrl() + "dd-document-list",
					DropDownModel[].class);
			List<DropDownModel> documentList = Arrays.asList(tempdocument);

			model.addAttribute("documentList", documentList);
			System.out.println("##################" + documentList);
		} catch (Exception e) {
			e.printStackTrace();

		}

		try {

			DropDownModel[] vendortemp = restTemplate.getForObject(env.getTrialUrl() + "dd-vendor-list",
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendortemp);

			model.addAttribute("vendorList", vendorList);
			System.out.println("##################" + vendorList);
		} catch (Exception e) {
			e.printStackTrace();

		}

		try {

			DropDownModel[] vhtemp = restTemplate.getForObject(env.getTrialUrl() + "dd-vehicle-list",
					DropDownModel[].class);
			List<DropDownModel> vehicleList = Arrays.asList(vhtemp);

			model.addAttribute("vehicleList", vehicleList);
			System.out.println("##################" + vehicleList);
		} catch (Exception e) {
			e.printStackTrace();

		}

		logger.info("Method : Details ends");

		return "trial/manage-legal-document";
	}
	
	// upload

	@PostMapping("manage-legal-document-upload")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		System.out.println("MultipartFile" + inputFile);
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
		System.out.println("img" + response);
		return response;
	}

	// add

	@SuppressWarnings("unchecked")

	@PostMapping("manage-legal-document-add")
	public @ResponseBody JsonResponse<Object> addLeagalData(Model model, HttpSession session,

			@RequestBody ManageLegalDocumentModel legaldocs) {
		logger.info("Method : addLeagalData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}

		legaldocs.setCreatedBy(userId);

		// upload part

		MultipartFile inputFile = (MultipartFile) session.getAttribute("insuranceFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				logger.info("imageName" + imageName);
				legaldocs.setFileUpload(imageName);
			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} // upload part

		System.out.println("manageLegalDocumentModel" + legaldocs);

		try {
			logger.info("abababababababababab");
			logger.info("manageLegalDocumentModel" + legaldocs);
			resp = restTemplate.postForObject(env.getTrialUrl() + "rest-addLeagalData", legaldocs,
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

		logger.info("Method : adddistrict ends");
		System.out.println("resp" + resp);
		return resp;
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

			Path path = Paths.get(env.getFileUploadTrialUrl() + imageName);
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



	// view
	@SuppressWarnings("unchecked")

	@GetMapping("manage-legal-document-view")
	public @ResponseBody List<ManageLegalDocumentModel> viewLegalDocument(HttpSession session) {
		logger.info("Method : viewLegalDocument starts");

		JsonResponse<List<ManageLegalDocumentModel>> resp = new JsonResponse<List<ManageLegalDocumentModel>>();
		List<ManageLegalDocumentModel> returnList = new ArrayList<ManageLegalDocumentModel>();

		try {
			resp = restTemplate.getForObject(env.getTrialUrl() + "view-LegalDocus", JsonResponse.class);
			// returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("VIEWWWWWWWWWWWWWW   " + resp.getBody());
		logger.info("Method : viewLegalDocument ends");
		return resp.getBody();
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("manage-legal-document-delete")
	public @ResponseBody JsonResponse<Object> deleteLegalDoc(HttpSession session, Model model,
			@RequestParam String id) {

		logger.info("deletelegalDoc starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("id is:in web" + id);
		try {
			resp = restTemplate.getForObject(env.getTrialUrl() + "delete-LegalDoc?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		System.out.println("resp" + resp);
		logger.info("deletelegalDoc ends");
		logger.info("Method : deleteDistType ends");
		return resp;

	}

	// edit

	@SuppressWarnings("unchecked")
	@GetMapping("manage-legal-document-edit")
	public @ResponseBody JsonResponse<List<Object>> editLegalDoc(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editLegalDoc starts"+ id);

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getTrialUrl() + "edit-LegalDoc?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : editLegalDoc starts"+ resp);
		return resp;
	}
}
