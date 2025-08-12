package nirmalya.aathithya.webmodule.his.controller;

import java.util.ArrayList;
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
import nirmalya.aathithya.webmodule.his.model.HISIcuModel;


@Controller
@RequestMapping(value = "his")
public class HISIcuController {

	Logger logger = LoggerFactory.getLogger(HISIcuController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("icu")

	public String getHomePage(Model model, HttpSession session) {

		logger.info("Method : getHomePage starts");
		
		//floorType
		try {
			DropDownModel[] floorType = restTemplate.getForObject(env.getHisUrl()
					+ "getFloorType1",
					
					DropDownModel[].class);

			List<DropDownModel>  floorListType= Arrays.asList(floorType);
			System.out.println("fffffff"+floorListType);
			model.addAttribute("floorList", floorListType);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getHomePage ends");

		return "his/his-icu";
		
							
			}

	// icu-icu-add
	
	@SuppressWarnings("unchecked")
	@PostMapping("icu-icu-add")
	public @ResponseBody JsonResponse<Object> addIcu(@RequestBody HISIcuModel HISIcuModel,
			Model model, HttpSession session) {
		logger.info("Method :addIcu starts");
		logger.info("@@@@@@@@@@@@@@@@" + HISIcuModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
		String organization = "";
		String orgDivision = "";
		try {
			
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HISIcuModel.setIcuOrg(organization);
		HISIcuModel.setIcuDiv(orgDivision);
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-addIcu",
					HISIcuModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addIcu ends");
		return resp;
	}
	
	
	// icu-icu-view

			@SuppressWarnings("unchecked")

			@GetMapping("icu-icu-view")
			public @ResponseBody List<HISIcuModel> viewIcu(HttpSession session) {
				logger.info("Method : viewIcu starts");

				JsonResponse<List<HISIcuModel>> resp = new JsonResponse<List<HISIcuModel>>();
				List<HISIcuModel> returnList = new ArrayList<HISIcuModel>();

				try {
					resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-Icu", JsonResponse.class);
					returnList = resp.getBody();
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				logger.info("viewIcu" + returnList);
				logger.info("Method : viewIcu ends");
				return returnList;
			}
			
			
			//icu-icu-edit
			
			@SuppressWarnings("unchecked")
			@PostMapping("icu-icu-edit")
			public @ResponseBody JsonResponse<List<Object>> editIcu(@RequestBody String icu,HttpSession session) {
				logger.info("Method : editIcu starts");

				JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

				try {
					resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-Icu?id=" + icu,
							JsonResponse.class);

				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = resp.getMessage();

				if (message != null && message != "") {

				} else {
					resp.setMessage("success");
				}
				logger.info("editIcu" + resp);
				logger.info("Method : editIcu starts");
				logger.info("resp" + resp);
				return resp;
			}
			
			//icu-icu-delete
			
			@SuppressWarnings("unchecked")
			@PostMapping("icu-icu-delete")
			public @ResponseBody JsonResponse<Object> deleteIcu(@RequestParam String id,
					Model model, HttpSession session) {
				logger.info("Method : deleteIcu function starts");

				JsonResponse<Object> res = new JsonResponse<Object>();

				try {
					res = restTemplate.getForObject(env.getHisUrl() + "rest-icu-delete?id=" + id  , JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}
				logger.info("Method : deleteIcu function Ends");
				
				logger.info("RESPPPPPPP"+res);
				return res;
			}	
}