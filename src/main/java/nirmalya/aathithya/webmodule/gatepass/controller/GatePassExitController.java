package nirmalya.aathithya.webmodule.gatepass.controller;

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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "gatepass/")
public class GatePassExitController {
	
	Logger logger = LoggerFactory.getLogger(GatePassExitController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	private static final String USER_Id = "USER_ID";
	
	@GetMapping("/gate-pass-exit")
	public String gatePassEntry(Model model, HttpSession session) {
		logger.info("Method : gatePass add starts");

		// String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			// userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(
					env.getGatepassUrl() + "get-purchseOrderId-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> pOrderIdList = Arrays.asList(dd);

			model.addAttribute("pOrderIdList", pOrderIdList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] type = restClient.getForObject(
					env.getGatepassUrl() + "get-entryType-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			System.out.println("entryTypeList>>>>>>>------" + typeList);
			model.addAttribute("entryTypeList", typeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(env.getGatepassUrl() + "get-purchseOrderIdForExit-list",
					DropDownModel[].class);
			List<DropDownModel> pOrderIdListForExit = Arrays.asList(dd);

			model.addAttribute("pOrderIdListForExit", pOrderIdListForExit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(env.getGatepassUrl() + "get-noOfwheeler-list",
					DropDownModel[].class);
			List<DropDownModel> noOfwheelerList = Arrays.asList(dd);

			model.addAttribute("noOfwheelerList", noOfwheelerList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : gatePass ends");
		return "gatepass/gatePass-exit-details";
	}

}
