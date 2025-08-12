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
import nirmalya.aathithya.webmodule.productionplan.model.ManageBomModel;

@Controller

@RequestMapping("production/")
public class ManageBomController {

	Logger logger = LoggerFactory.getLogger(ManageBomController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "manage-bom" })

	public String manageBom(Model model, HttpSession session) {
		logger.info("manageBom Start");

		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-list?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			System.out.println("List Data>>>>-------" + itemList);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		try {

			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-sublist?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> itemSubList = Arrays.asList(item);
			System.out.println("List Data>>>>-------" + itemSubList);
			model.addAttribute("itemSubList", itemSubList);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		try {

			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-unitlist?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> itemUnitList = Arrays.asList(item);
			System.out.println("List unit Data>>>>-------" + itemUnitList);
			model.addAttribute("unitList", itemUnitList);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		logger.info("manageBom End");

		return "production_plan/manage-bom";
	}

	// add Bom-Details.

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "manage-bom-add-bom-details" })
	public @ResponseBody JsonResponse<Object> saveBomDetails(@RequestBody List<ManageBomModel> bomDetailsAdd,
			HttpSession session) {
		logger.info("Method : saveBomDetails function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (ManageBomModel m : bomDetailsAdd) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("web 165675===" + bomDetailsAdd);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-add-bomDetails", bomDetailsAdd,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveBomDetails function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-bom-view")
	public @ResponseBody Object viewBomData(HttpSession session) {

		logger.info("Method :viewBomData starts");
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
					env.getProduction() + "rest-viewBomData?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		System.out.println("sdefrgthg" + resp);
		logger.info("Method :viewBomData ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("manage-bom-edit")
	public @ResponseBody Object editBomData(@RequestParam String id, HttpSession session) {

		logger.info("Method :editBomData starts");
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
					env.getProduction() + "rest-editBomData?id=" + id +"&orgName=" +orgName+"&orgDivision=" +orgDivision ,
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
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :editBomData ends");

		return resp;
	}


	
	// Delete bom-Details.
		@SuppressWarnings("unchecked")
		@GetMapping(value = "manage-bom-delete-bom")
		public @ResponseBody JsonResponse<Object> deleteBom(@RequestParam String id, HttpSession session) {
			logger.info("Method : deleteBom function starts");
			JsonResponse<Object> res = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				res = restTemplate.getForObject(env.getProduction() + "rest-delete-bom?id=" +id +"&orgName=" +orgName+"&orgDivision=" +orgDivision,JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (res.getCode().equals("success")) {
				res.setMessage("Success");
			} else {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			}
			logger.info("Method : deleteBom function Ends");
			return res;
		}
		
		
		// Change Bom Status.
				@SuppressWarnings("unchecked")
				@GetMapping(value = "manage-bom-change-bom-status")
				public @ResponseBody JsonResponse<Object> changeStatus(@RequestParam String status ,String id, HttpSession session) {
					logger.info("Method : changeStatus function starts");
					JsonResponse<Object> res = new JsonResponse<Object>();
					String orgName = "";
					String orgDivision = "";
					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						res = restTemplate.getForObject(env.getProduction() + "rest-change-bom-status?status=" + status + "&id=" +id +"&orgName=" +orgName+"&orgDivision=" +orgDivision,JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}
					if (res.getCode().equals("success")) {
						res.setMessage("Success");
					} else {
						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					}
					logger.info("Method : changeStatus function Ends");
					return res;
				}

}
