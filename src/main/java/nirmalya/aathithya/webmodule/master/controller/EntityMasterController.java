package nirmalya.aathithya.webmodule.master.controller;

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
import nirmalya.aathithya.webmodule.master.model.EntityMasterModel;

@Controller
@RequestMapping(value = "master")
public class EntityMasterController {

	Logger logger = LoggerFactory.getLogger(EntityMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/entity-master")
	public String displayPage(Model model, HttpSession session) {
		logger.info("Method : entity starts");

		try {
			DropDownModel[] vendorCategory = restTemplate.getForObject(env.getMasterUrl() + "getVendorCategoryList",
					DropDownModel[].class);
			List<DropDownModel> vendorCategoryList = Arrays.asList(vendorCategory);

			model.addAttribute("vendorCategoryList", vendorCategoryList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getVendorCategoryList ends");
		return "master/entity-master";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "entity-master-add-cost-labour")
	public @ResponseBody JsonResponse<Object> addCostLabour(@RequestBody EntityMasterModel costlabour, Model model,
			HttpSession session) {
		logger.info("Method : addCostLabour starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		costlabour.setOrganization(organization);
		costlabour.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		costlabour.setCostLabourCreatedBy(userId);

		try {
			costlabour.setCostLabourModifiedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addCostLabour", costlabour,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addCostLabour ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-view-cost-labour")
	public @ResponseBody List<EntityMasterModel> viewCostLabour(HttpSession session) {
		logger.info("Method : viewCostLabour starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<EntityMasterModel>> resp = new JsonResponse<List<EntityMasterModel>>();
		List<EntityMasterModel> returnList = new ArrayList<EntityMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewCostLabour?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	
		logger.info("Method : viewCostLabour ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("/entity-master-labour-delete")
	public @ResponseBody JsonResponse<Object> deleteCostLabour(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteCostLabour starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteCostLabour?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteCostLabour ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "entity-master-add-cost-nature-type")
	public @ResponseBody JsonResponse<Object> addCostNature(@RequestBody EntityMasterModel costNature, Model model,
			HttpSession session) {
		logger.info("Method : addCostNature starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		costNature.setOrganization(organization);
		costNature.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		costNature.setCostnatureCreatedBy(userId);

		try {
			costNature.setCostnatureModifiedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addCostNature", costNature,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addCostNature ends");

		return jsonResponse;
	}

//        View page for Hr job types

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-view-cost-nature-type")
	public @ResponseBody List<EntityMasterModel> viewCostNature(HttpSession session) {
		logger.info("Method : viewCostNature starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<EntityMasterModel>> resp = new JsonResponse<List<EntityMasterModel>>();
		List<EntityMasterModel> costNatureList = new ArrayList<EntityMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewCostNature?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			costNatureList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewCostNature ends");
		return costNatureList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-cost-nature-type-delete")
	public @ResponseBody JsonResponse<Object> deleteCostNatureType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteCostNatureType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteCostNatureType?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteCostNatureType ends");
		return resp;
	}

	// Vendor Type Master

	@SuppressWarnings("unchecked")
	@PostMapping(value = "entity-master-add-vendor-category")
	public @ResponseBody JsonResponse<Object> addVendorType(@RequestBody EntityMasterModel vendorCategory, Model model,
			HttpSession session) {
		logger.info("Method : addVendorType starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		vendorCategory.setOrganization(organization);
		vendorCategory.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		vendorCategory.setVendorCategoryCreatedBy(userId);

		try {
			vendorCategory.setVendorCategoryModifiedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addVendorCatagory", vendorCategory,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addVendorType ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-view-vendor-category")
	public @ResponseBody List<EntityMasterModel> viewVendorType(HttpSession session) {
		logger.info("Method : viewVendorType starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<EntityMasterModel>> resp = new JsonResponse<List<EntityMasterModel>>();
		List<EntityMasterModel> returnList = new ArrayList<EntityMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewVendorCatagory?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	
		logger.info("Method : viewVendorType ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-vendor-category-delete")
	public @ResponseBody JsonResponse<Object> deleteVendorType(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteVendorType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteVendorCatagory?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteVendorType ends");
		return resp;
	}

	// Location Type Master

	@SuppressWarnings("unchecked")
	@PostMapping(value = "entity-master-add-location-type")
	public @ResponseBody JsonResponse<Object> addLocationType(@RequestBody EntityMasterModel locationCategory,
			Model model, HttpSession session) {
		logger.info("Method : addLocationType starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		locationCategory.setOrganization(organization);
		locationCategory.setOrgDivision(orgDivision);

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		locationCategory.setLocationTypeCreatedBy(userId);

		try {
			locationCategory.setLocationTypeModifiedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addLocationType", locationCategory,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addLocationType ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-view-location-type")
	public @ResponseBody List<EntityMasterModel> viewLocationType(HttpSession session) {
		logger.info("Method : viewLocationType starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewLocationType starts"+organization+orgDivision);
		JsonResponse<List<EntityMasterModel>> resp = new JsonResponse<List<EntityMasterModel>>();
		List<EntityMasterModel> returnList = new ArrayList<EntityMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewLocationType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	
		logger.info("Method : viewLocationType ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-view-location-type-delete")
	public @ResponseBody JsonResponse<Object> deleteLocationType(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteLocationType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteLocationType?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteLocationType ends");
		return resp;
	}

	// Room Type Master

	@SuppressWarnings("unchecked")
	@PostMapping(value = "entity-master-add-room-type")
	public @ResponseBody JsonResponse<Object> addRoomType(@RequestBody EntityMasterModel roomCategory, Model model,
			HttpSession session) {
		logger.info("Method : addRoomType starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		roomCategory.setOrganization(organization);
		roomCategory.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		roomCategory.setRoomTypeCreatedBy(userId);

		try {
			roomCategory.setRoomTypeModifiedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addRoomType", roomCategory,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addRoomType ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-view-room-type")
	public @ResponseBody List<EntityMasterModel> viewRoomType(HttpSession session) {
		logger.info("Method : viewRoomType starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}


		JsonResponse<List<EntityMasterModel>> resp = new JsonResponse<List<EntityMasterModel>>();
		List<EntityMasterModel> returnList = new ArrayList<EntityMasterModel>();
		logger.info("Method : viewRoomType starts"+organization+orgDivision);

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewRoomType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	
		logger.info("Method : viewRoomType ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-view-room-type-delete")
	public @ResponseBody JsonResponse<Object> deleteRoomType(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteRoomType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteRoomType?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteRoomType ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "entity-master-add-vendor-type")
	public @ResponseBody JsonResponse<Object> addVendorTypeMaster(@RequestBody EntityMasterModel vendortype,
			Model model, HttpSession session) {
		logger.info("Method : addVendorType starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		vendortype.setOrganization(organization);
		vendortype.setOrgDivision(orgDivision);


		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		vendortype.setVendorTypeCreatedBy(userId);

		try {
			vendortype.setVendorTypeModifiedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addVendorType", vendortype,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addVendorType ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-view-vendor-type")
	public @ResponseBody List<EntityMasterModel> viewVendorTypeMaster(HttpSession session) {
		logger.info("Method : viewVendorType starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<EntityMasterModel>> resp = new JsonResponse<List<EntityMasterModel>>();
		List<EntityMasterModel> returnList = new ArrayList<EntityMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewVendorType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewVendorType ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/entity-master-vendor-type-delete")
	public @ResponseBody JsonResponse<Object> deleteVendorTypeMaster(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteVendorType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteVendorType?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteVendorType ends");
		return resp;
	}

}
