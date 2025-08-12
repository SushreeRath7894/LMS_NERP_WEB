package nirmalya.aathithya.webmodule.canteen.controller;


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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.canteen.model.WebMenuModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;


@Controller
@RequestMapping(value = "canteen")
public class WebMenuController {
	
Logger logger = LoggerFactory.getLogger(WebMenuController.class);

@Autowired
RestTemplate restClient;

@Autowired
EnvironmentVaribles env;

@GetMapping("/menu")
public String viewIncentive(Model model, HttpSession session) {

	logger.info("Method : Manage-incentivesDetails starts");
	

	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getIncentive",
				DropDownModel[].class);

		List<DropDownModel> incentivedetails = Arrays.asList(orderStatus);
		System.out.println("incentivedetails" + incentivedetails);
		model.addAttribute("incentivedetails", incentivedetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//for club member drop down list
	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getClubMembers",
				DropDownModel[].class);

		List<DropDownModel> clubMemberDetails = Arrays.asList(orderStatus);
		System.out.println("clubMemberDetails" + clubMemberDetails);
		model.addAttribute("clubMemberDetails", clubMemberDetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getvariants",
				DropDownModel[].class);

		List<DropDownModel> variantDetails = Arrays.asList(orderStatus);
		System.out.println("variantDetails" + variantDetails);
		model.addAttribute("variantDetails", variantDetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 
	logger.info("Method : Manage-incentivesDetails ends");
	//return "canteen/menu";
	return "canteen/canteen-menu.html";

}



   //Add
	@SuppressWarnings("unchecked")
	@PostMapping("menu-add-dtls")
	public @ResponseBody JsonResponse<Object> addIncentive(@RequestBody WebMenuModel webMenuModel, Model model,
			HttpSession session) {

		logger.info("Method : addincentivesDetails starts" + webMenuModel);

		System.out.println("resp web controller-----------------------------------" + webMenuModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
	
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		webMenuModel.setOrganization(orgName);
		webMenuModel.setOrgDivision(orgDivision);
		

		try {

			resp = restClient.postForObject(env.getcanteenUrl() + "restmenuadd?orgName=" + orgName + "&orgDivision=" + orgDivision, webMenuModel , JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addincentivesDetails ends" + resp);

		return resp;
	}
	
	
    //View
			@SuppressWarnings("unchecked")
			@GetMapping("menu-throughAjax")
			public @ResponseBody List<WebMenuModel> viewIncentive(HttpSession session) {

				logger.info("Method : viewincentivesDetails starts");

				JsonResponse<List<WebMenuModel>> resp = new JsonResponse<List<WebMenuModel>>();

				try {
					resp = restClient.getForObject(env.getcanteenUrl() + "restViewIncentiveDetails", JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				ObjectMapper mapper = new ObjectMapper();

				List<WebMenuModel> manageincentiveModel = mapper.convertValue(resp.getBody(),
						new TypeReference<List<WebMenuModel>>() {
						});

				resp.setBody(manageincentiveModel);
				System.out.println("resp.getBody()-----------" + resp.getBody());

				logger.info("Method : viewincentivesDetails ends");
				return resp.getBody();
			}
			
                // Edit  
						@SuppressWarnings("unchecked")
						@GetMapping("menu-edit")
						public @ResponseBody JsonResponse<List<WebMenuModel>> editShoukeenincentiveInfo(Model model,
								@RequestParam String id, HttpSession session) {

							logger.info("Method : editShoukeenincentiveInfo starts" + id);

							JsonResponse<List<WebMenuModel>> jsonResponse = new JsonResponse<List<WebMenuModel>>();

							try {
								jsonResponse = restClient.getForObject(env.getcanteenUrl() + "editincentivedetailInfo?id=" + id,
										JsonResponse.class);

							} catch (Exception e) {
								e.printStackTrace();
							}

							ObjectMapper mapper = new ObjectMapper();
							
							List<WebMenuModel> manageincentiveModel = mapper.convertValue(jsonResponse.getBody(),
									new TypeReference<List<WebMenuModel>>() {
									});

							
							System.out.println("###" + manageincentiveModel);
							jsonResponse.setBody(manageincentiveModel);

							if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

							} else {
								jsonResponse.setMessage("Success");
							}

							System.out.println("REsp" + jsonResponse);
							logger.info("Method :editShoukeenincentiveInfo ends");
							return jsonResponse;
						}


						
		       //Delete
						@SuppressWarnings("unchecked")
						@GetMapping("menu-delete-id")
						public @ResponseBody JsonResponse<Object> deleteIncentiveDetails(@RequestParam String id,
								 HttpSession session) {
							logger.info("Method : deleteincentiveDetails function starts"+id);

							JsonResponse<Object> res = new JsonResponse<Object>();
							
							try {
								res = restClient.getForObject(env.getcanteenUrl() + "delete-inceDetails?id=" + id  , JsonResponse.class);
							} catch (RestClientException e) {
								e.printStackTrace();
							}

							String message = res.getMessage();
							if (message != null && message != "") {

							} else {
								res.setMessage("Success");
							}
							logger.info("Method : deleteincentiveDetails function Ends");
							
							System.out.println("Response"+res);
							return res;
						}
                     }
