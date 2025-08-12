package nirmalya.aathithya.webmodule.communication.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
import org.springframework.validation.BindingResult;
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
import nirmalya.aathithya.webmodule.communication.model.ManageCommunicationModel;

@Controller
@RequestMapping(value = "communicate/")
public class ManageCommunicationController {

	Logger logger = LoggerFactory.getLogger(ManageCommunicationController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/manage-communication")
	public String communication(Model model, HttpSession session) {
		logger.info("Method : communication starts");

		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] country = restClient.getForObject(env.getCommunication() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dept = restTemplate.getForObject(
					env.getCommunication() + "getDepartmentList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);

			List<DropDownModel> deptList = Arrays.asList(dept);
			model.addAttribute("deptList", deptList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : communication ends");
		return "communication/manage-communication";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-communication-get-statelist" })
	public @ResponseBody JsonResponse<Object> getStateList(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getCommunication() + "getStateList?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}

		logger.info("Method : getStateList ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-communication-get-citylist" })
	public @ResponseBody JsonResponse<Object> getCityList(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getCityList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getCommunication() + "getCityList?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCityList ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-communication-add-dispatch" })
	public @ResponseBody JsonResponse<Object> addDispatchDetails(@RequestBody ManageCommunicationModel data,
			HttpSession session) {
		logger.info("Method : addDispatchDetails starts");

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

		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getCommunication() + "add-dispatch-details", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addDispatchDetails starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-communication-add-receive" })
	public @ResponseBody JsonResponse<Object> addReceiveDetails(@RequestBody ManageCommunicationModel data,
			HttpSession session) {
		logger.info("Method : addReceiveDetails starts");

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

		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getCommunication() + "add-receive-details", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addReceiveDetails starts");
		return resp;
	}

	public JSONObject saveAllMediaDocuments(byte[] imageBytes, String ext, String user_id) {
		logger.info("Method : saveAllMedicalDocuments starts");

		String imageName = null;
		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();

				if (ext.contentEquals("jpeg")) {
					imageName = user_id + "_" + nowTime + ".jpg";
				} else {
					imageName = user_id + "_" + nowTime + "." + ext;
				}
			}

			Path path = Paths.get(env.getCommunicationDocUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String url = env.getAssetUrl() + imageName;

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

	@SuppressWarnings("unchecked")
	@GetMapping("manage-communication-view-all")
	public @ResponseBody Object getAllData(@RequestParam String pageno, String type, HttpSession session) {
		logger.info("Method :getAllData starts");

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
			resp = restClient.getForObject(env.getCommunication() + "rest-get-all-data?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllData ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-communication-edit")
	public @ResponseBody Object getEditData(@RequestParam String regNo, HttpSession session) {
		logger.info("Method :getEditData starts");

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
			resp = restClient.getForObject(env.getCommunication() + "rest-get-edit-data?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&regNo=" +regNo, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getEditData ends");

		return resp;
	}

	
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-communication-edit-dispatch")
	public @ResponseBody Object getEditDataDispatch(@RequestParam String regNo, HttpSession session) {
		logger.info("Method :getEditDataDispatch starts");
		
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
			resp = restClient.getForObject(env.getCommunication() + "rest-get-edit-dispatch?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&regNo=" +regNo, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :getEditDataDispatch ends");
		
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("manage-communication-delete")
	public @ResponseBody JsonResponse<Object> deleteData(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteData function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		String userId = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getCommunication() + "delete-communication-data?id=" + id + "&org="
					+ orgName + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		res.getMessage();
		logger.info("Method : deleteData function Ends");

		return res;
	}

	
	// Attachemnt View.

		@SuppressWarnings("unchecked")
		@GetMapping("manage-communication-attachment")
		public @ResponseBody Object attachmentTicket(@RequestParam String id, HttpSession session) {
			logger.info("Method :attachmentTicket starts");
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

				resp = restTemplate.getForObject(env.getCommunication() + "rest-view-attachment?id=" + id
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getCode().contentEquals("success")) {
				resp.setMessage("Success");
			} else {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			}
			logger.info("Method :attachmentTicket ends");
			return resp;
		}
}
