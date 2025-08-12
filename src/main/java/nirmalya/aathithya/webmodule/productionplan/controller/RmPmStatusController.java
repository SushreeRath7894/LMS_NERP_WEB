package nirmalya.aathithya.webmodule.productionplan.controller;

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
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller

@RequestMapping("production/")
public class RmPmStatusController {
	
	Logger logger = LoggerFactory.getLogger(RmPmStatusController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "rmpm-status" })

	public String rmPmStatus(Model model, HttpSession session) {
		logger.info("rmPmStatus Start");
		
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		
		try {
			DropDownModel[] shift = restTemplate.getForObject(
					env.getProduction() + "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("rmPmStatus End");

		return "production_plan/rmpm-status";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("rmpm-status-view")
	public @ResponseBody Object statusView(@RequestParam String state,String shift,String date,HttpSession session) {
		logger.info("Method :statusView starts");
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
					env.getProduction() + "rest-statusView?orgName=" + orgName + "&orgDivision=" + orgDivision + "&state=" + state + "&shift=" + shift + "&date=" + date,
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
		
		logger.info("Method :statusView ends");
		return resp;
	}


}
