package nirmalya.aathithya.webmodule.production.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping("production/")
public class ManageProductionController {
	Logger logger = LoggerFactory.getLogger(ManageProductionController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

// method for page return
	@GetMapping(value = { "sales-order" })
	public String salesOrder(Model model, HttpSession session) {
		logger.info("salesOrder Start");
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
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restTemplate.getForObject(env.getProjects() + "get-projectPriority-list?userId=" + userId + "&org="
					+ organization + "&div=" + orgDivision, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<DropDownModel> projectPriorityList = mapper.convertValue(resp.getBody(),
					new TypeReference<List<DropDownModel>>() {
					});
			model.addAttribute("projectPriorityList", projectPriorityList);

		} catch (Exception e) {
			e.printStackTrace();

		}
		try {
			DropDownModel[] bomItem = restTemplate.getForObject(
					env.getSalesUrl() + "get-bom-item-list?org=" + organization + "&orgDiv=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> bomItemList = Arrays.asList(bomItem);

			model.addAttribute("bomItemList", bomItemList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("salesOrder End");
		return "production/sales-order";
	}
	@SuppressWarnings("unchecked")
	@GetMapping("sales-order-get-data")
	public @ResponseBody JsonResponse<Object> getSalesOrderData(HttpSession session) {
		logger.info("Method : getSalesOrderData starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-getSalesOrderData?org="+organization+"&orgDiv="+orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getSalesOrderData ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "sales-order-get-bomdetails" })
	public @ResponseBody JsonResponse<Object> getBomDetails(Model model, @RequestParam String id,
			@RequestParam String sku, HttpSession session) {
		logger.info("Method : getBomDetails starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			res = restTemplate.getForObject(env.getProduction() + "getBomDetails?id=" + id + "&sku=" + sku + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getBomDetails ends");
		return res;
	}
}
