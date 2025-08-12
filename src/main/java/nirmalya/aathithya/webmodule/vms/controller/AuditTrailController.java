package nirmalya.aathithya.webmodule.vms.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "/purchase")
public class AuditTrailController {

	Logger logger = LoggerFactory.getLogger(AuditTrailController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "manage-audit trail" })
	public String viewAuditTrail(Model model, HttpSession session) {
		logger.info("Method :viewAuditTrail starts");

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			DropDownModel[] auditLists = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-audit-type-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> auditTypeList = Arrays.asList(auditLists);
			model.addAttribute("auditTypeList", auditTypeList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewAuditTrail ends");

		return "vms-templates/audit-trail";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-audit-log-data")
	public @ResponseBody JsonResponse<Object> viewAuditTrailData(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String type) {
		logger.info("Method : viewAuditTrailData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "audit-log-data?fromDate=" + fromDate + "&toDate="
					+ toDate + "&type=" + type, JsonResponse.class);

			System.out.println(env.getPurchaseUrl());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewAuditTrailData ends");
		return resp;
	}

}
