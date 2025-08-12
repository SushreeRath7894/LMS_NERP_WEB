package nirmalya.aathithya.webmodule.employee.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.TravelClaimModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;

@Controller
@RequestMapping(value = { "employee/" })
public class TravelClaimController {
	Logger logger = LoggerFactory.getLogger(TravelRequisitinController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/travel-claim")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : reimbursement starts");

		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
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
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		try {
			DropDownModel[] Service = restClient.getForObject(env.getEmployeeUrl() + "getServiceList",
					DropDownModel[].class);
			List<DropDownModel> getServiceList = Arrays.asList(Service);
			model.addAttribute("getServiceList", getServiceList);
			logger.info("Method : getServiceList starts" + getServiceList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : reimbursement ends");
		return "employee/travelClaim";
	}

	/*
	 * view travel employee list data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("travel-claim-view-employee")
	public @ResponseBody List<TravelClaimModel> viewTravelClaim(Model model, HttpSession session,
			@RequestParam String userid, @RequestParam String roleid) {
		logger.info("Method : viewTravelClaim starts");

		JsonResponse<List<TravelClaimModel>> jsonResponse = new JsonResponse<List<TravelClaimModel>>();
		List<String> roleList = new ArrayList<String>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (roleid != null && roleid != "") {
			String[] arr = roleid.split(",");
			for (int i = 0; i < arr.length; i++) {
				roleList.add(arr[i]);
			}
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setUserRole(roleList);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);
		try {
			jsonResponse = restTemplate.postForObject(env.getEmployeeUrl() + "travel-claim-employee", empModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<TravelClaimModel> viewClaim = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<TravelClaimModel>>() {
				});
		for (TravelClaimModel m : viewClaim) {
			if (m.getStatus().equals("0")) {
				m.setStatus("Pending");
			} else if (m.getStatus().equals("4")) {
				m.setStatus("completed");
			} else {
				m.setStatus("Continue");
			}
			if (m.getAdvanceReq().equals("0")) {
				m.setAdvanceReq("NO");
			} else {
				m.setAdvanceReq("YES");
			}

		}
		jsonResponse.setBody(viewClaim);
		if (jsonResponse.getCode().equals("success")) {
			jsonResponse.setMessage("Success");
		} else {

		}
		logger.info("jsonResponse====" + jsonResponse);
		logger.info("Method ; viewTravelClaim ends");

		return jsonResponse.getBody();
	}

	/*
	 * view travel employee list data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "travel-requisition-edit-employeeClaim-trough-ajax" })
	public @ResponseBody List<TravelClaimModel> TravelClaimEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : TravelClaimEdit starts");

		JsonResponse<List<TravelClaimModel>> jsonResponse = new JsonResponse<List<TravelClaimModel>>();

		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "get-travel-claim-edit?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("travelClaimEdit: " + e.getMessage());
		}

		logger.info("Method : TravelClaimEdit ends");
		return jsonResponse.getBody();
	}
	/*
	 * save bill
	 */

	@PostMapping("travel-claim-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("uploadFile: " + e.getMessage());
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	/*
	 * Remove bill
	 */

	@GetMapping("travel-claim-remove-file")
	public @ResponseBody JsonResponse<Object> uploadFileRemove(HttpSession session) {
		logger.info("Method : uploadFileRemove controller function 'get-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			// response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", null);
			session.removeAttribute("quotationPFile");

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("uploadFileRemove: " + e.getMessage());
		}

		logger.info("Method : uploadFileRemove controller function 'get-mapping' ends");
		return response;
	}
	/*
	 * Add Travel Reimbursement Details
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("travel-claim-other-add")
	public @ResponseBody JsonResponse<Object> addTravelClaimOther(HttpSession session,
			@RequestBody TravelClaimModel travelClaimModel) {

		logger.info("Method : addTravelClaimOther" + travelClaimModel);

		String dateFormat = "";
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		travelClaimModel.setCreatedBy(userId);
		travelClaimModel.setOrganization(organization);
		travelClaimModel.setOrgDivision(orgDivision);

		/*
		 * if (travelClaimModel.getDate() != null && travelClaimModel.getDate() != "") {
		 * travelClaimModel.setDate(DateFormatter.inputDateFormat(travelClaimModel.
		 * getDate(), dateFormat)); }
		 */

		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		System.out.println("MultipartFile inputFile>>>>" + inputFile);
		byte[] bytes;
		String imageName = null;
		String docName = travelClaimModel.getDocName();
		if(!docName.isEmpty()) {
			if (inputFile != null) {
				try {
					bytes = inputFile.getBytes();
					String[] fileType = inputFile.getContentType().split("/");
					imageName = saveAllImage(bytes, fileType[1]);

					travelClaimModel.setDocName(imageName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else {
				travelClaimModel.setDocName(docName);
			}
		}else {
			travelClaimModel.setDocName(null);
		}
		/*
		 * if (inputFile != null) { try { bytes = inputFile.getBytes(); String[]
		 * fileType = inputFile.getContentType().split("/"); imageName =
		 * saveAllImage(bytes, fileType[1]);
		 * 
		 * travelClaimModel.setDocName(imageName); } catch (IOException e1) {
		 * e1.printStackTrace(); } }
		 */

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "add-travelclaim-others", travelClaimModel,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<TravelClaimModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<TravelClaimModel>>() {
					});

			resp.setBody(quotation);
		} catch (RestClientException e) {
			e.printStackTrace();
			logger.error("addTravelClaimOther: " + e.getMessage());
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			// resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : travelClaimModel ends" + travelClaimModel);
		return resp;
	}

//
	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("png") || ext.contentEquals("jpg") || ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveAllImage: " + e.getMessage());
		}
		logger.info("Method : saveAllImage ends");
		return imageName;

	}

	/*
	 * view travel employee list data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "travel-requisition-claim-view-other" })
	public @ResponseBody List<TravelClaimModel> TravelClaimOtherView(@RequestParam String id, HttpSession session) {
		logger.info("Method : TravelClaimEdit starts");

		JsonResponse<List<TravelClaimModel>> jsonResponse = new JsonResponse<List<TravelClaimModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "get-claim-other?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TravelClaimOtherView: " + e.getMessage());
		}
		logger.info("Method : TravelClaimEdit ends" + jsonResponse.getBody());
		return jsonResponse.getBody();
	}

	/*
	 * view travel employee list data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "travel-claim-view-other-edit" })
	public @ResponseBody List<TravelClaimModel> travelClaimOtherEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : TravelClaimEdit starts");

		JsonResponse<List<TravelClaimModel>> jsonResponse = new JsonResponse<List<TravelClaimModel>>();

		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "get-claim-other-edit?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("deleteTravelClaimOther: " + e.getMessage());
		}
		logger.info("jsonResponse.getBody()==" + jsonResponse.getBody());
		logger.info("Method : TravelClaimEdit ends");
		return jsonResponse.getBody();
	}
	/*
	 *
	 * Delete Travel Claim
	 *
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("travel-claim-other-delete")
	public @ResponseBody JsonResponse<TravelClaimModel> deleteTravelClaimOther(@RequestParam String id) {
		logger.info("Method : deleteTravelClaimOther starts");

		JsonResponse<TravelClaimModel> jsonResponse = new JsonResponse<TravelClaimModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "delete-travelclaim-Other?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
			logger.error("deleteTravelClaimOther: " + e.getMessage());
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : deleteTravelClaimOther ends");
		return jsonResponse;
	}

	/*
	 * Add Travel Reimbursement Details
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("travel-claim-status-add")
	public @ResponseBody JsonResponse<Object> addTravelClaimStatus(HttpSession session,
			@RequestBody TravelClaimModel travelClaimModel) {

		logger.info("Method : addTravelClaimOther" + travelClaimModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "add-travelclaim-status", travelClaimModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
			logger.error("addTravelClaimStatus: " + e.getMessage());
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : travelClaimModel ends" + travelClaimModel);

		return resp;
	}
}
