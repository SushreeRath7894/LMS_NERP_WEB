package nirmalya.aathithya.webmodule.purchase.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.gatepass.model.GatePassDetailsModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.purchase.model.QaRequestModel;

@Controller

@RequestMapping(value = { "purchase/" })

public class AwaitingQaRequestController {

	Logger logger = LoggerFactory.getLogger(AwaitingQaRequestController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "awaiting-qarequest" })

	public String awaitingQaRequest(Model model, HttpSession session) {
		logger.info("Method :awaitingQaRequest starts");

		logger.info("Method : awaitingQaRequest ends");

		return "purchase/awaiting-qarequest";
	}

	// View Awaiting-QA-Data

	@SuppressWarnings("unchecked")

	@GetMapping("awaiting-qarequest-view")
	public @ResponseBody Object awaitingQaRequestData(HttpSession session) {

		logger.info("Method :awaitingQaRequestData starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-awaitingQaRequestData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :awaitingQaRequestData ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("awaiting-qarequest-edit")
	public @ResponseBody List<GatePassDetailsModel> awaitingQAReqDetails(HttpSession session,
			@RequestParam String id) {

		logger.info("Method : awaitingQAReqDetails starts");
		JsonResponse<List<GatePassDetailsModel>> resp = new JsonResponse<List<GatePassDetailsModel>>();

		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(id);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getGatepassUrl() + "editGatePassEntry", empModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<GatePassDetailsModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GatePassDetailsModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		int count = 0;
		for (GatePassDetailsModel m : gatePassDetailsModel) {
			count++;
			m.setSlNo(count);
			if (m.getEntrydate() != null && m.getEntrydate() != "") {
				m.setEntrydate(DateFormatter.dateFormat(m.getEntrydate(), dateFormat));
			}
		}
logger.info("gatePassDetailsModel=="+gatePassDetailsModel);
		logger.info("Method : awaitingQAReqDetails ends");
		return gatePassDetailsModel;
	}
	
	
@SuppressWarnings("unchecked")
	
	@PostMapping("awaiting-qarequest-add-request")
	public @ResponseBody JsonResponse<Object> qaRequestAdd(@RequestBody List<QaRequestModel> qaRequestAdd,
			HttpSession session) {
		logger.info("Method : qaRequestAdd Start");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (QaRequestModel m : qaRequestAdd) {
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		logger.info("qaRequestAdd===" + qaRequestAdd);

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-qaRequestSave", qaRequestAdd,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : qaRequestAdd ends");
		logger.info("Web Adddd>>>>>---" + resp);
		return resp;
	}


}
