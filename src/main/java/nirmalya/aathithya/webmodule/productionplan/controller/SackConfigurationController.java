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
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.productionplan.model.LmrLogWebModel;
import nirmalya.aathithya.webmodule.productionplan.model.SackConfigurationWebModel;

@Controller
@RequestMapping(value = "production/")
public class SackConfigurationController {

	Logger logger = LoggerFactory.getLogger(SackConfigurationController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("sack-configuration")
	public String sackConfiguration(Model model, HttpSession session) {
		logger.info("Method : sackConfiguration starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
			}
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		/*
		 * try { DropDownModel[] shift = restTemplate.getForObject(env.getProduction() +
		 * "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv + "&userId=" +
		 * userId, DropDownModel[].class); List<DropDownModel> shiftLists =
		 * Arrays.asList(shift); System.out.println("shiftLists>>>>>>>>>>>>>>>>>>>" +
		 * shiftLists); model.addAttribute("shiftLists", shiftLists);
		 * 
		 * } catch (RestClientException e) { e.printStackTrace(); }
		 */

		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-lists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/*
		 * try { DropDownModel[] line = restClient.getForObject( env.getProduction() +
		 * "getLineLists-Lmr?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
		 * List<DropDownModel> lineLists = Arrays.asList(line);
		 * 
		 * model.addAttribute("lineLists", lineLists);
		 * 
		 * } catch (RestClientException e) { e.printStackTrace(); }
		 */
		logger.info("Method : sackConfiguration ends");
		return "production_plan/sack-configuration";
	}
	
	
	
	// view
	
	@SuppressWarnings("rawtypes")
	@GetMapping("sack-configuration-view")
	public @ResponseBody Object viewSackConfiguration(Model model, HttpSession session) {

			logger.info("Method :viewSackConfiguration starts");
			
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";
			

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restClient.getForObject(
					env.getProduction() + "rest-viewSackConfiguration?org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewSackConfiguration ends  ");
		return resp;
	}
	
	
	// add
	
		@SuppressWarnings("unchecked")
		@PostMapping("sack-configuration-add")
		public @ResponseBody JsonResponse<Object> addSackConfiguration(HttpSession session,
				@RequestBody SackConfigurationWebModel offDay) {
			logger.info("Method : addSackConfiguration starts"+offDay);
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String dateFormat = "";
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
				offDay.setCreatedBy(userId);
				offDay.setOrganization(organization);
				offDay.setOrgDivision(orgDivision);
			try {
				resp = restClient.postForObject(env.getProduction() + "rest-addSackConfiguration", offDay,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Success");
			} else {
				resp.setMessage("Unsuccess");
			}
			logger.info("Method : addSackConfiguration ends"+resp);
			return resp;
		}
		

		
		// Delete
		
			@SuppressWarnings("rawtypes")
			@GetMapping("sack-configurationg-delete")
			public @ResponseBody Object deleteSackConfig(@RequestParam String id, HttpSession session) {

					logger.info("Method :deleteSackConfig starts");
					
					JsonResponse resp = new JsonResponse();
					String org = "";
					String orgDiv = "";

					try {
						org = (String) session.getAttribute("ORGANIZATION");
						orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						resp = restClient.getForObject(
							env.getProduction() + "rest-deleteSackConfig?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("Method :deleteSackConfig ends  ");
				return resp;
			}

}
