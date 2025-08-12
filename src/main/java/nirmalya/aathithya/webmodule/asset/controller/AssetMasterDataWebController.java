package nirmalya.aathithya.webmodule.asset.controller;

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

import nirmalya.aathithya.webmodule.asset.model.AssetMasterDataWebModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = { "asset/" })

public class AssetMasterDataWebController {
	Logger logger = LoggerFactory.getLogger(AssetMasterDataWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "/dropdown-master" })
	public String assetCategory(Model model, HttpSession session) {
		logger.info("Method : Dropdown assetcategory starts");

		try {
			DropDownModel[] assetCategoryList = restClient.getForObject(env.getAssetUrl()+ "assetCategoryList",
					DropDownModel[].class);

			List<DropDownModel> assetCategory = Arrays.asList(assetCategoryList);
			 	model.addAttribute("assetCategoryList", assetCategory);
			 	
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		try {
			DropDownModel[] assetSpCategoryList = restClient.getForObject(env.getAssetUrl()+ "assetSpCategoryList",
					DropDownModel[].class);

			List<DropDownModel> assetSppCategory = Arrays.asList(assetSpCategoryList);
			 	model.addAttribute("assetSpCategoryList", assetSpCategoryList);
			 	
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : Dropdown assetcategory ends");
		return "asset/asset-masterdata";

	}

	@SuppressWarnings("unchecked")
	@PostMapping("dropdown-master-add")
	public @ResponseBody JsonResponse<Object> addAsset(@RequestBody AssetMasterDataWebModel assetMaster, Model model,
			HttpSession session) {

		logger.info("Method :  addAsset starts");

		 

		String orgName = "";
		String orgDiv = "";
		String userId="";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			assetMaster.setOrgName(orgName);
			assetMaster.setOrgDiv(orgDiv);
			assetMaster.setCreatedBy(userId);

		} catch (Exception e) {

		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		

		try {

			resp = restClient.postForObject(env.getAssetUrl() + "rest-manage-addAsset", assetMaster,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		 
		logger.info("Method : addAsset ends");

		return resp;
		
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dropdown-master-getAsset")
	public @ResponseBody JsonResponse<Object> getAssetMaster(HttpSession session, @RequestParam String type) {
		logger.info("Method : getAssetMaster starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "rest-manage-getAsset?type=" + type, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 
		  
		logger.info("Method : getAssetMaster ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dropdown-master-delete")
	public @ResponseBody JsonResponse<Object> deleteAsset(@RequestParam String type, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : deleteAsset starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getAssetUrl() + "manage-asset-data-delete?type=" + type + "&id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		 
		logger.info("Method : deleteAsset  Ends"+res);

		 
		return res;
	}

}
