package nirmalya.aathithya.webmodule.canteen.controller;

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
public class CanteenComboController {
	
	Logger logger = LoggerFactory.getLogger(CanteenComboController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/canteen-combo")
	public String viewIncentive(Model model, HttpSession session) {



	logger.info("Method : Manage-incentivesDetails starts");
	

	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getComboCatagory",
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
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getComboSubCatagiry",
				DropDownModel[].class);

		List<DropDownModel> clubMemberDetails = Arrays.asList(orderStatus);
		System.out.println("clubMemberDetails" + clubMemberDetails);
		model.addAttribute("clubMemberDetails", clubMemberDetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getCombovariants",
				DropDownModel[].class);

		List<DropDownModel> variantDetails = Arrays.asList(orderStatus);
		System.out.println("variantDetails" + variantDetails);
		model.addAttribute("variantDetails", variantDetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 
	logger.info("Method : Manage-incentivesDetails ends");
	//return "canteen/canteen-combo";
	return "canteen/canteen-newcombo.html";

}
	
	@SuppressWarnings("unchecked")
	@PostMapping("canteen-combo-add-dtls")
	public @ResponseBody JsonResponse<Object> addIncentive(@RequestBody WebMenuModel webMenuModel, Model model,
	        HttpSession session) {

	    logger.info("Method : addincentivesDetails starts");
	    System.out.println("Received WebMenuModel data:" + webMenuModel);
	    
	    

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
		
		System.out.println("orgName"+orgName+"orgDivision"+orgDivision);
	    
	    try {
	        
	        resp = restClient.postForObject(env.getcanteenUrl() + "restcomboadd", webMenuModel, JsonResponse.class);
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
		@GetMapping("canteen-combo/canteen-item-list")
		public @ResponseBody List<WebMenuModel> viewItemList(HttpSession session , String catId ,String subCatId ,String variant ) {

			logger.info("Method : viewItemList starts"+variant);

			JsonResponse<List<WebMenuModel>> resp = new JsonResponse<List<WebMenuModel>>();

			try {
				resp = restClient.getForObject(env.getcanteenUrl() + "rest-canteen-item-list?CatId="+catId+"&SubCatId="+subCatId+"&variant="+variant, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<WebMenuModel> manageincentiveModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<WebMenuModel>>() {
					});

			resp.setBody(manageincentiveModel);
			System.out.println("resp.getBody()-----------" + resp.getBody());
			
			if(resp.getMessage() ==null) {
				resp.setMessage("Success");
			}

			logger.info("Method : viewItemList ends"+resp);
			return resp.getBody();
		}
		
		
		//View
		@SuppressWarnings("unchecked")
		@GetMapping("canteen-combo-all-throughAjax")
		public @ResponseBody List<WebMenuModel> viewIncentive(HttpSession session) {

			logger.info("Method : viewcomboallDetails starts");

			JsonResponse<List<WebMenuModel>> resp = new JsonResponse<List<WebMenuModel>>();

			try {
				resp = restClient.getForObject(env.getcanteenUrl() + "restViewAllDetails", JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<WebMenuModel> manageincentiveModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<WebMenuModel>>() {
					});

			resp.setBody(manageincentiveModel);
			System.out.println("resp.getBody()-----------" + resp.getBody());

			logger.info("Method : viewcomboallDetails ends");
			return resp.getBody();
		}
		
		

		 /* //View
		@SuppressWarnings("unchecked")
		@GetMapping("canteen-combo-rowData")
		public @ResponseBody List<WebMenuModel> viewRowdata(HttpSession session , String itemId ) {

			logger.info("Method : viewRowdata starts"+itemId);

			JsonResponse<List<WebMenuModel>> resp = new JsonResponse<List<WebMenuModel>>();

			try {
				resp = restClient.getForObject(env.getcanteenUrl() + "viewRowdata?itemId="+itemId, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<WebMenuModel> manageincentiveModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<WebMenuModel>>() {
					});

			resp.setBody(manageincentiveModel);
			System.out.println("resp.getBody()-----------" + resp.getBody());
			
			if(resp.getMessage() ==null) {
				resp.setMessage("Success");
			}

			logger.info("Method : viewRowdata ends"+resp);
			return resp.getBody();
		}*/
		
	//searchin
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "canteen-combo-menu-list" })
		public @ResponseBody JsonResponse<WebMenuModel> getProductAutoSearchList(Model model,
				@RequestBody String searchValue, BindingResult result, HttpSession session) {
			logger.info("Method : getProductAutoSearchList starts");
			 System.out.println("QuotationNewModel"+searchValue);
			JsonResponse<WebMenuModel> res = new JsonResponse<WebMenuModel>();
			String userId = (String) session.getAttribute("USER_ID");
			String userType = (String) session.getAttribute("USER_ROLETYPE");
			System.out.println("==userid====" + userId);
			System.out.println("==user_roles====" + userType);
			try {

				res = restClient.getForObject(env.getcanteenUrl() + "getProductSList?id=" + searchValue,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (res.getCode() != null) {
				res.setCode("success");
			} else {
				res.setCode("Unsuccess");
			}
			System.out.println("RESPONSE@@" + res);
			logger.info("Method : getProductAutoSearchList ends");
			return res;
		}
		
		
		// Edit  
		@SuppressWarnings("unchecked")
		@GetMapping("canteen-combo-edit")
		public @ResponseBody JsonResponse<List<WebMenuModel>> editcanteencombo(Model model,
				@RequestParam String id, HttpSession session) {

			logger.info("Method : editcanteencombo starts" + id);

			JsonResponse<List<WebMenuModel>> jsonResponse = new JsonResponse<List<WebMenuModel>>();

			try {
				jsonResponse = restClient.getForObject(env.getcanteenUrl() + "editcanteencombo?id=" + id,
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
			logger.info("Method :editcanteencombo ends");
			return jsonResponse;
		}
		
		
		
		
		   //Delete
						@SuppressWarnings("unchecked")
						@GetMapping("canteen-combo-delete-id")
						public @ResponseBody JsonResponse<Object> deleteComboDetails(@RequestParam String id,
								 HttpSession session) {
							logger.info("Method : deleteComboDetails function starts"+id);

							JsonResponse<Object> res = new JsonResponse<Object>();

							

							try {
								res = restClient.getForObject(env.getcanteenUrl() + "deleteComboDetails?id=" + id  , JsonResponse.class);
							} catch (RestClientException e) {
								e.printStackTrace();
							}

							String message = res.getMessage();
							if (message != null && message != "") {

							} else {
								res.setMessage("Success");
							}
							logger.info("Method : deleteComboDetails function Ends");
							
							System.out.println("Response"+res);
							return res;
				
}


}
