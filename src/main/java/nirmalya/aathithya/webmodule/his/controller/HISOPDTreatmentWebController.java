package nirmalya.aathithya.webmodule.his.controller;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;
import nirmalya.aathithya.webmodule.his.model.HISTreatmentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("his")
public class HISOPDTreatmentWebController {

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(HISOPDTreatmentWebController.class);

	public static String opdId;

	@GetMapping(value = { "/opd-treatment" })
	public String viewOPDTreatment(Model model) {
			//, @RequestParam String id) {
		logger.info("Method : viewOPDTreatment starts");

		//opdId = id;
		//model.addAttribute("opdId1", id);

		DropDownModel[] type = restClient.getForObject(env.getHisUrl() + "/typeList", DropDownModel[].class);
		List<DropDownModel> typeList = Arrays.asList(type);
		model.addAttribute("typeList", typeList);

		logger.info("Method : viewOPDTreatment ends");
		return "his/his-opd-treatment";
	}
	/*
	 * medicine autoSearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "his-opd-treatment-medicine-list" })
	public @ResponseBody JsonResponse<HISTreatmentModel> getMedicineAutoList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getMedicineAutoList starts");
		JsonResponse<HISTreatmentModel> res = new JsonResponse<HISTreatmentModel>();

		try {
			res = restClient.getForObject(env.getHisUrl() + "getMedicineAutoList?id=" + searchValue,
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

		logger.info("Method : getMedicineAutoList ends");
		return res;
	}

	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "his-opd-treatment-add" })
	public @ResponseBody JsonResponse<Object> addOpdTreatment(HttpSession session,
			@RequestBody HISTreatmentModel data) {
		logger.info("Method : addOpdTreatment starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		System.out.println("data=======" + data);
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
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		System.out.println("detailsss ===" + data);
		try {
			res = restClient.postForObject(env.getHisUrl() + "rest-opd-treatment", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addOpdTreatment ends" + res);
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "his-opd-treatment-test-list" })
	public @ResponseBody JsonResponse<HISTreatmentModel> getTestAutoList(Model model, @RequestBody String searchValue,
			BindingResult result) {
		logger.info("Method : getTestAutoList starts");
		JsonResponse<HISTreatmentModel> res = new JsonResponse<HISTreatmentModel>();

		try {
			res = restClient.getForObject(env.getHisUrl() + "getTestAutoList?id=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getTestAutoList ends");
		return res;
	}

	// view-opd-treatment-lists
	@SuppressWarnings("unchecked")
	@GetMapping("view-opd-treatment-lists")
	public @ResponseBody Object viewOpdTreatmentLists(HttpSession session,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		logger.info("Method: viewOpdTreatmentLists starts here...");
		JsonResponse<Object> response = new JsonResponse<>();
		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			if (userId != null && orgName != null && orgDivision != null) {
				response = restClient.getForObject(
						env.getHisUrl() + "rest-viewOpdTreatmentLists?orgName=" + orgName + "&orgDivision="
								+ orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
						JsonResponse.class);
				logger.info("response object == \n" + response);
			} else {
				response.setCode("Missing session attributes");
				response.setMessage("Failure");
			}
		} catch (Exception e) {
			logger.error("An error occurred while processing Method : viewOpdTreatmentLists", e);
			response.setCode("Internal error");
			response.setMessage("Failure");
		}

		assert response != null;
		if ("Failure".equals(response.getMessage())) {
			logger.info("Method: viewOpdTreatmentLists ends here with failure. Response: \n" + response);
		} else {
			logger.info("Method: viewOpdTreatmentLists ends here with success. Response: \n" + response);
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "his-opd-treatment-test-add" })
	public @ResponseBody JsonResponse<Object> addOpdTest(HttpSession session, @RequestBody HISTreatmentModel data) {
		logger.info("Method : addOpdTest starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		System.out.println("data=======" + data);
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
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		System.out.println("detailsss ===" + data);
		try {
			res = restClient.postForObject(env.getHisUrl() + "rest-opd-test", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addOpdTest ends" + res);
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-opd-test-lists")
	public @ResponseBody Object viewOpdTestLists(HttpSession session, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		logger.info("Method: viewOpdTestLists starts here...");
		JsonResponse<Object> response = new JsonResponse<>();
		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			if (userId != null && orgName != null && orgDivision != null) {
				response = restClient.getForObject(
						env.getHisUrl() + "rest-viewOpdTestLists?orgName=" + orgName + "&orgDivision=" + orgDivision
								+ "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
						JsonResponse.class);
				logger.info("response object == \n" + response);
			} else {
				response.setCode("Missing session attributes");
				response.setMessage("Failure");
			}
		} catch (Exception e) {
			logger.error("An error occurred while processing Method : viewOpdTestLists", e);
			response.setCode("Internal error");
			response.setMessage("Failure");
		}

		assert response != null;
		if ("Failure".equals(response.getMessage())) {
			logger.info("Method: viewOpdTestLists ends here with failure. Response: \n" + response);
		} else {
			logger.info("Method: viewOpdTestLists ends here with success. Response: \n" + response);
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "his-opd-typeList" })
	public @ResponseBody JsonResponse<Object> getTypeList(@RequestParam String id) {
		logger.info("Method : getTypeList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getHisUrl() + "getTypeList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getTypeList ends");
		return res;
	}
}