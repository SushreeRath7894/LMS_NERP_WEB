package nirmalya.aathithya.webmodule.grc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.grc.model.InspectionGenerateWebModel;
import nirmalya.aathithya.webmodule.grc.model.InspectionGenerateWebSubModel;
import nirmalya.aathithya.webmodule.grc.model.SafetyAssesmentWebModel;

@Controller
@RequestMapping(value = { "grc/" })
public class InspectionGenerateWebController {
	Logger logger = LoggerFactory.getLogger(InspectionGenerateWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SafetyAssessmentWebController assesment;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "generate-inspection" })
	public String InspectionGenerate(Model model, HttpSession session) {
		logger.info("Method : InspectionGenerate starts");

		logger.info("Method : InspectionGenerate ends");
		return "grc/generate_inspection";
	}

	// view-project
	@SuppressWarnings("unchecked")
	@GetMapping("generate-inspection-view-projects")
	public @ResponseBody List<SafetyAssesmentWebModel> viewProjectCreation(HttpSession session) {
		logger.info("Method : viewProjectCreation starts");
		JsonResponse<List<SafetyAssesmentWebModel>> resp = new JsonResponse<List<SafetyAssesmentWebModel>>();
		List<SafetyAssesmentWebModel> returnList = new ArrayList<SafetyAssesmentWebModel>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewProject?id=" + userId + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewProjectCreation ends" + returnList);
		return returnList;
	}
	// view-checklist

	@SuppressWarnings("unchecked")
	@GetMapping("generate-inspection-view-checklist")
	public @ResponseBody List<InspectionGenerateWebSubModel> viewChecklist(HttpSession session) {
		logger.info("Method : viewChecklist starts");

		JsonResponse<List<InspectionGenerateWebSubModel>> resp = new JsonResponse<List<InspectionGenerateWebSubModel>>();
		List<InspectionGenerateWebSubModel> returnList = new ArrayList<InspectionGenerateWebSubModel>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewChecklist?id=" + userId + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewChecklist ends" + returnList);
		return returnList;
	}

	// add
	@SuppressWarnings("unchecked")
	@PostMapping("generate-inspection-add-data")
	public @ResponseBody JsonResponse<Object> addInspectionGenerate(HttpSession session,
			@RequestBody InspectionGenerateWebModel model) {
		logger.info("Method : addInspectionGenerate starts   " + model);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setCreatedBy(userId);
		model.setOrganizationName(organization);
		model.setOrganizationDivision(orgDivision);
		for (InspectionGenerateWebSubModel m : model.getSubModel()) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}
		try {
			resp = restClient.postForObject(env.getGrcUrl() + "rest-addInspectionGenerate", model, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addInspectionGenerate ends");
		return resp;
	}
	// view-checklist

	@SuppressWarnings("unchecked")
	@GetMapping("generate-inspection-view-inspection")
	public @ResponseBody List<InspectionGenerateWebModel> viewInspection(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewInspection starts");

		JsonResponse<List<InspectionGenerateWebModel>> resp = new JsonResponse<List<InspectionGenerateWebModel>>();
		List<InspectionGenerateWebModel> returnList = new ArrayList<InspectionGenerateWebModel>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewInspection?id=" + id + "&uId=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewInspection ends" + returnList);
		return returnList;
	}

	// editInspection

	@SuppressWarnings("unchecked")
	@GetMapping("generate-inspection-edit")
	public @ResponseBody JsonResponse<List<InspectionGenerateWebModel>> editInspection(Model model,
			@RequestParam String id, String type, HttpSession session) {

		logger.info("Method : editInspection starts" + id);
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<InspectionGenerateWebModel>> jsonResponse = new JsonResponse<List<InspectionGenerateWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getGrcUrl() + "rest-editInspection?id=" + id + "&type=" + type
					+ "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<InspectionGenerateWebModel> model1 = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<InspectionGenerateWebModel>>() {
				});

		jsonResponse.setBody(model1);

		logger.info("Method : editInspection ends");
		if (jsonResponse.getMessage() != "" && jsonResponse.getMessage() != null) {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : addInspectionGenerate ends");
		return jsonResponse;
	}

	// autosearch-employee
	@SuppressWarnings("unchecked")
	@GetMapping("generate-inspection-autosearch-employee")
	public @ResponseBody JsonResponse<DropDownModel> getEmployeeAutoSearchList(Model model,
			@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getEmployeeAutoSearchList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getGrcUrl() + "rest-getOwnerAutoSearchList?id=" + searchValue,
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
		logger.info("Method : getEmployeeAutoSearchList ends");
		return res;
	}

	// autosearch-employee
	@SuppressWarnings("unchecked")
	@GetMapping("generate-inspection-autosearch-vendor")
	public @ResponseBody JsonResponse<DropDownModel> getVendorAutoSearchList(Model model,
			@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getVendorAutoSearchList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getGrcUrl() + "rest-getVendorAutoSearchList?id=" + searchValue,
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
		logger.info("Method : getVendorAutoSearchList ends");
		return res;
	}

	// add
	@SuppressWarnings("unchecked")
	@PostMapping("generate-inspection-add-assignedTo")
	public @ResponseBody JsonResponse<Object> addAssignedTo(HttpSession session,
			@RequestBody List<InspectionGenerateWebSubModel> model) {
		logger.info("Method : addAssignedTo starts   " + model);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (InspectionGenerateWebSubModel m : model) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}
		try {
			resp = restClient.postForObject(env.getGrcUrl() + "rest-addAssignedTo", model, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" && resp.getMessage() == null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addAssignedTo ends");
		return resp;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("generate-inspection-delete")
	public @ResponseBody JsonResponse<Object> deleteinspectionDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteinspectionDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getGrcUrl() + "deleteinspectionDetails?id=" + id + "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		logger.info("Message"+message);
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteinspectionDetails function Ends"+message);

		return res;
	}

	// delete checklist
	
	@SuppressWarnings("unchecked")
	@PostMapping("generate-inspection-checkList-delete")
	public @ResponseBody JsonResponse<Object> deletecheckListDetails(@RequestParam String id,String checkId,  Model model,
			HttpSession session) {
		logger.info("Method : deletecheckListDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getGrcUrl() + "deletecheckListDetails?id=" + id + "&checkId=" + checkId + "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deletecheckListDetails function Ends");

		return res;
	}
}