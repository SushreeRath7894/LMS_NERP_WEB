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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.property.stakeholder.model.StakeholderTenantDetailsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "property")
public class StackholderTenantController {
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(StackholderTenantController.class);

	/* manageTenants */



		@GetMapping(value = { "manage-tenants" })
		public String property(Model model, HttpSession session) {
			logger.info("Method :tenants starts");
			String userId = "";
			String userRole = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				userRole = (String) session.getAttribute("USER_ROLES_STRING");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			String splitData[] = userRole.split("r");
			String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
					.toArray(size -> new String[size]);
			for (String part : removedNull) {
				String data = "r" + part;

				if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
					model.addAttribute("hrRole", data);
				}
			}
			model.addAttribute("userId", userId);
		logger.info("Method : manageTenants ends");
		return "new-property/manage-tenant";
	}

	/// add
	@SuppressWarnings("unchecked")
	@PostMapping("manage-tenants-add")
	public @ResponseBody JsonResponse<Object> saveSac(
			@RequestBody StakeholderTenantDetailsModel StakeholderTenantDetailsModel, HttpSession session) {
		logger.info("Method: Tenant save starts" + StakeholderTenantDetailsModel);
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("DDDDDDDDDDDD" + StakeholderTenantDetailsModel);
		
		
		MultipartFile inputFile = (MultipartFile) session.getAttribute("insuranceFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				StakeholderTenantDetailsModel.setDocName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		
		
		try {
			res = restClient.postForObject(env.getPropertyUrl() + "manage-tenant-add-tenantdetails",
					StakeholderTenantDetailsModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");

		}

		logger.info("Method:Tenant save ends");
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
	
	
	@PostMapping("manage-tenants-file")
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

	/// view
	@SuppressWarnings("unchecked")

	@GetMapping("manage-tenants-view")
	public @ResponseBody List<StakeholderTenantDetailsModel> viewTenant(HttpSession session, @RequestParam String userid,@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : View starts"+userid+fromDate+toDate);

		JsonResponse<List<StakeholderTenantDetailsModel>> resp = new JsonResponse<List<StakeholderTenantDetailsModel>>();
		List<StakeholderTenantDetailsModel> returnList = new ArrayList<StakeholderTenantDetailsModel>();
		//logger.info(userid);
		//logger.info(fromDate);
		//logger.info(toDate);

		try {
			resp = restClient.getForObject(env.getPropertyUrl() + "manage-tenant-viewTenant?userid="+userid+"&fromDate="+fromDate+"&toDate="+toDate, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		//logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaa" + returnList);

		logger.info("Method : View ends");
		return returnList;
	}

	/// edit
	@SuppressWarnings("unchecked")
	@GetMapping("manage-tenants-edit")
	public @ResponseBody JsonResponse<List<Object>> editTenant(@RequestParam String id, HttpSession session) {

		logger.info("Method : editTenant starts" + id);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		try {
			jsonResponse = restClient.getForObject(env.getPropertyUrl() + "editTenant?id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editTenant ends");
		return jsonResponse;
	}

	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("manage-tenants-delete")
	public @ResponseBody JsonResponse<Object> deleteTenant(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteTenant function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getPropertyUrl() + "tenant-delete-TenantDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteTenant function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-tenants-autosearch-property"})
	public @ResponseBody JsonResponse<DropDownModel> getPropertyAutoSearch(Model model,
			@RequestBody String id, BindingResult result) {
		logger.info("Method : getPropertyAutoSearch starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getPropertyUrl()+ "getPropertyListByAutoSearch?id=" + id,
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
		logger.info("Method : getPropertyAutoSearch ends"+res);
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-tenants-autoComplete"})
	public @ResponseBody JsonResponse<DropDownModel> getPropertyAutocomplete(Model model,
			@RequestBody String id, BindingResult result) {
		logger.info("Method : getPropertyAutocomplete starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getPropertyUrl()+ "gettenantnamemail-autocomplete?id=" + id,
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
		logger.info("Method : getPropertyAutocomplete ends"+res);
		return res;
	}

}
