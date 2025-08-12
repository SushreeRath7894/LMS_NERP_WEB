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
import nirmalya.aathithya.webmodule.his.model.HISConfigurationModel;

@Controller
@RequestMapping(value = "his")
public class HISConfigurationController {
	Logger logger = LoggerFactory.getLogger(HISConfigurationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("configuration")
	public String getHomePage(Model model, HttpSession session) {

		logger.info("Method : getHomePage starts");

		try {
			DropDownModel[] group = restTemplate.getForObject(env.getHisUrl() + "getGroupId",

					DropDownModel[].class);

			List<DropDownModel> groupList = Arrays.asList(group);
			System.out.println("fffffff" + groupList);
			model.addAttribute("group", groupList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getHomePage ends");

		return "his/his-configuration";

	}

	// configuration-bed-master-add

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-bed-master-add")
	public @ResponseBody JsonResponse<Object> addBedcat(@RequestBody HISConfigurationModel HISConfigurationModel,
			Model model, HttpSession session) {
		logger.info("Method :addBedcat starts");
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		HISConfigurationModel.setOrg4(organization);
		HISConfigurationModel.setDiv4(orgDivision);
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-addBedCategoryMaster", HISConfigurationModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addBedcat ends");
		return resp;
	}

	// configuration-bed-master-view

	@SuppressWarnings("unchecked")

	@GetMapping("configuration-bed-master-view")
	public @ResponseBody List<HISConfigurationModel> viewBedcat(HttpSession session) {
		logger.info("Method : viewBedcat starts");

		JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
		List<HISConfigurationModel> returnList = new ArrayList<HISConfigurationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-BedCategoryMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewBedcat" + returnList);
		logger.info("Method : viewBedcat ends");
		return returnList;
	}

	// configuration-bed-master-edit

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-bed-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editBedCat(@RequestBody String bedcat, HttpSession session) {
		logger.info("Method : editBedCat starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-BedCategoryMaster?id=" + bedcat,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editBedCat" + resp);
		logger.info("Method : editBedCat starts");
		logger.info("resp" + resp);
		return resp;
	}

	// configuration-bed-master-delete

	@SuppressWarnings("unchecked")
	@GetMapping("configuration-bed-master-delete")
	public @ResponseBody JsonResponse<Object> deleteBedCategory(HttpSession session, @RequestParam String bedcat) {

		logger.info("Method : deleteBedCategory starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deleteBedCategoryMaster?id=" + bedcat,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteBedCategory ends");
		return resp;
	}
	
	// configuration-discharge-master-add

			@SuppressWarnings("unchecked")
			@PostMapping("configuration-discharge-master-add")
			public @ResponseBody JsonResponse<Object> adddischarge(@RequestBody HISConfigurationModel HISConfigurationModel,
					Model model, HttpSession session) {
				logger.info("Method :adddischarge starts");
				logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
				JsonResponse<Object> resp = new JsonResponse<Object>();
				
				String organization = "";
				String orgDivision = "";
				try {
					
					organization = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("organization==="+organization);
				
				HISConfigurationModel.setOrg2(organization);
				HISConfigurationModel.setDiv2(orgDivision);
				logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
				try {
					resp = restTemplate.postForObject(env.getHisUrl() + "rest-addDischargeMaster", HISConfigurationModel,
							JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				String message = resp.getMessage();
				if (message != null && message != "") {

				} else {
					resp.setMessage("Success");
				}
				logger.info("Method : adddischarge ends");
				return resp;
			}
			
			//configuration-discharge-master-view

			@SuppressWarnings("unchecked")

			@GetMapping("configuration-discharge-master-view")
			public @ResponseBody List<HISConfigurationModel> viewDisType(HttpSession session) {
				logger.info("Method : viewDisType starts");

				JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
				List<HISConfigurationModel> disList = new ArrayList<HISConfigurationModel>();

				try {
					resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-DisTypeMaster", JsonResponse.class);
					disList = resp.getBody();
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				logger.info("viewDisType" + disList);
				logger.info("Method : viewDisType ends");
				return disList;
			}
			


	// configuration-discharge-master-edit

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-discharge-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editDisType(@RequestBody String distype, HttpSession session) {
		logger.info("Method : editDisType starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-DisTypeMaster?distype=" + distype,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editDisType" + resp);
		logger.info("Method : editDisType starts");
		logger.info("resp" + resp);
		return resp;
	}

	// configuration-discharge-master-delete

	@SuppressWarnings("unchecked")
	@GetMapping("configuration-discharge-master-delete")
	public @ResponseBody JsonResponse<Object> deleteDischargeType(HttpSession session, @RequestParam String distype) {

		logger.info("Method : deleteDischargeType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deleteDischargeType?id=" + distype,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteDischargeType ends");
		return resp;
	}

	// configuration-destination-master-add

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-destination-master-add")
	public @ResponseBody JsonResponse<Object> adddestination(@RequestBody HISConfigurationModel HISConfigurationModel,
			Model model, HttpSession session) {
		logger.info("Method :adddestination starts");
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("organization===" + organization);

		HISConfigurationModel.setOrg3(organization);
		HISConfigurationModel.setDiv3(orgDivision);
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-addDestinationMaster", HISConfigurationModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : adddestination ends");
		return resp;
	}

	// configuration-destination-master-view

	@SuppressWarnings("unchecked")

	@GetMapping("configuration-destination-master-view")
	public @ResponseBody List<HISConfigurationModel> viewDestination(HttpSession session) {
		logger.info("Method : viewDestination starts");

		JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
		List<HISConfigurationModel> returnList = new ArrayList<HISConfigurationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-DestinMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewDestination" + returnList);
		logger.info("Method : viewDestination ends");
		return returnList;
	}

	// configuration-destination-master-edit

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-destination-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editDestination(@RequestBody String disDes, HttpSession session) {
		logger.info("Method : editDestination starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-DestinMaster?disDes=" + disDes,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editDestination" + resp);
		logger.info("Method : editDestination starts");
		logger.info("resp" + resp);
		return resp;
	}

	// configuration-destination-master-delete

	@SuppressWarnings("unchecked")
	@GetMapping("configuration-destination-delete")
	public @ResponseBody JsonResponse<Object> deleteDestination(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteDestination starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deleteDestination?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteDestination ends");
		return resp;
	}

	// configuration-concession-master-add

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-concession-master-add")
	public @ResponseBody JsonResponse<Object> addconcession(@RequestBody HISConfigurationModel HISConfigurationModel,
			Model model, HttpSession session) {
		logger.info("Method :addconcession starts");
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("organization===" + organization);

		HISConfigurationModel.setOrg1(organization);
		HISConfigurationModel.setDiv1(orgDivision);
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-addConcessionMaster", HISConfigurationModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addconcession ends");
		return resp;
	}

	// configuration-concession-master-view

	@SuppressWarnings("unchecked")

	@GetMapping("configuration-concession-master-view")
	public @ResponseBody List<HISConfigurationModel> viewConcession(HttpSession session) {
		logger.info("Method : viewConcession starts");

		JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
		List<HISConfigurationModel> returnList = new ArrayList<HISConfigurationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-ConcessionMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewConcession" + returnList);
		logger.info("Method : viewConcession ends");
		return returnList;
	}

	// configuration-concession-master-edit

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-concession-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editConcession(@RequestBody String conCat, HttpSession session) {
		logger.info("Method : editConcession starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-ConcessionMaster?id=" + conCat,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editConcession" + resp);
		logger.info("Method : editConcession starts");
		logger.info("resp" + resp);
		return resp;
	}

	// configuration-concession-delete

	@SuppressWarnings("unchecked")
	@GetMapping("configuration-concession-delete")
	public @ResponseBody JsonResponse<Object> deleteConcession(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteConcession starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deleteConcession?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteConcession ends");
		return resp;
	}

	// configuration-pathology-master-add

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-pathology-master-add")
	public @ResponseBody JsonResponse<Object> addpathology(@RequestBody HISConfigurationModel HISConfigurationModel,
			Model model, HttpSession session) {
		logger.info("Method :addpathology starts");
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("organization===" + organization);

		HISConfigurationModel.setOrg5(organization);
		HISConfigurationModel.setDiv5(orgDivision);
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-addpathology", HISConfigurationModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addpathology ends");
		return resp;
	}

	// configuration-pathology-master-view

	@SuppressWarnings("unchecked")

	@GetMapping("configuration-pathology-master-view")
	public @ResponseBody List<HISConfigurationModel> viewPathology(HttpSession session) {
		logger.info("Method : viewPathology starts");

		JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
		List<HISConfigurationModel> returnList = new ArrayList<HISConfigurationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-PathologyMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewConcession" + returnList);
		logger.info("Method : viewPathology ends");
		return returnList;
	}

	// configuration-pathology-master-edit

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-pathology-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editPathology(@RequestBody String testType, HttpSession session) {
		logger.info("Method : editPathology starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-PathologyMaster?id=" + testType,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editPathology" + resp);
		logger.info("Method : editPathology starts");
		logger.info("resp" + resp);
		return resp;
	}

	// configuration-pathology-delete

	@SuppressWarnings("unchecked")
	@GetMapping("configuration-pathology-delete")
	public @ResponseBody JsonResponse<Object> deletePathology(HttpSession session, @RequestParam String id) {

		logger.info("Method : deletePathology starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deletePathology?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deletePathology ends");
		return resp;
	}

	// configuration-group-master-add

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-group-master-add")
	public @ResponseBody JsonResponse<Object> addgroup(@RequestBody HISConfigurationModel HISConfigurationModel,
			Model model, HttpSession session) {
		logger.info("Method :addgroup starts");
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("organization===" + organization);

		HISConfigurationModel.setOrg6(organization);
		HISConfigurationModel.setDiv6(orgDivision);
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-addgroup", HISConfigurationModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addgroup ends");
		return resp;
	}

	// configuration-group-master-view

	@SuppressWarnings("unchecked")

	@GetMapping("configuration-group-master-view")
	public @ResponseBody List<HISConfigurationModel> viewGroup(HttpSession session) {
		logger.info("Method : viewGroup starts");

		JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
		List<HISConfigurationModel> returnList = new ArrayList<HISConfigurationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-GroupMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewGroup" + returnList);
		logger.info("Method : viewGroup ends");
		return returnList;
	}

	// configuration-group-master-edit

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-group-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editGroup(@RequestBody String group, HttpSession session) {
		logger.info("Method : editGroup starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-GroupMaster?id=" + group, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editGroup" + resp);
		logger.info("Method : editGroup starts");
		logger.info("resp" + resp);
		return resp;
	}

	// configuration-group-delete

	@SuppressWarnings("unchecked")
	@GetMapping("configuration-group-delete")
	public @ResponseBody JsonResponse<Object> deleteGroup(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteGroup starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deleteGroup?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteGroup ends");
		return resp;
	}

	// configuration-group-master-add

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-subgroup-master-add")
	public @ResponseBody JsonResponse<Object> addsubgroup(@RequestBody HISConfigurationModel HISConfigurationModel,
			Model model, HttpSession session) {
		logger.info("Method :addsubgroup starts");
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("organization===" + organization);

		HISConfigurationModel.setOrg7(organization);
		HISConfigurationModel.setDiv7(orgDivision);
		logger.info("@@@@@@@@@@@@@@@@" + HISConfigurationModel);
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-addsubgroup", HISConfigurationModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addsubgroup ends");
		return resp;
	}

	// configuration-sub-master-view

	@SuppressWarnings("unchecked")

	@GetMapping("configuration-sub-master-view")
	public @ResponseBody List<HISConfigurationModel> viewSubGroup(HttpSession session) {
		logger.info("Method : viewSubGroup starts");

		JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
		List<HISConfigurationModel> returnList = new ArrayList<HISConfigurationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-SubGroupMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewSubGroup" + returnList);
		logger.info("Method : viewSubGroup ends");
		return returnList;
	}

	// configuration-subgroup-master-edit

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-subgroup-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editSubGroup(@RequestBody String subGroup, HttpSession session) {
		logger.info("Method : editSubGroup starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-SubGroupMaster?id=" + subGroup,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editSubGroup" + resp);
		logger.info("Method : editSubGroup starts");
		logger.info("resp" + resp);
		return resp;
	}

// configuration-subgroup-delete

	@SuppressWarnings("unchecked")
	@GetMapping("configuration-subgroup-delete")
	public @ResponseBody JsonResponse<Object> deleteSubGroup(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteSubGroup starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deleteSubGroup?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : deleteSubGroup ends");
		return resp;
	}

	// configuration-surgery-master-add

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-surgery-master-add")
	public @ResponseBody JsonResponse<Object> addSurgery(@RequestBody HISConfigurationModel HISConfigurationModel,
			Model model, HttpSession session) {
		logger.info("Method :addSurgery starts");
		logger.info("@@@@@Surgery@@@@@@@" + HISConfigurationModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("organization===" + organization);

		HISConfigurationModel.setOrg8(organization);
		HISConfigurationModel.setDiv8(orgDivision);
		logger.info("@@@@Surgery@@@@@@" + HISConfigurationModel);
		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-addSurgery", HISConfigurationModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("unSuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addSurgery ends");
		return resp;
	}

	// configuration-surgery-master-view

	@SuppressWarnings("unchecked")

	@GetMapping("configuration-surgery-master-view")
	public @ResponseBody List<HISConfigurationModel> viewSurgery(HttpSession session) {
		logger.info("Method : viewSurgery starts");

		JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
		List<HISConfigurationModel> returnList = new ArrayList<HISConfigurationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-view-SurgeryMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("viewSurgery" + returnList);
		logger.info("Method : viewSurgery ends");
		return returnList;
	}

	// configuration-surgery-master-edit

	@SuppressWarnings("unchecked")
	@PostMapping("configuration-surgery-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editSurgery(@RequestBody String testType, HttpSession session) {
		logger.info("Method : editSurgery starts");

		JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-edit-surgeryMaster?id=" + testType,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("editSurgery" + resp);
		logger.info("Method : editSurgery ends");
		logger.info("resp" + resp);
		return resp;
	}

	// configuration-surgery-delete

	@SuppressWarnings("unchecked")
	@GetMapping("configuration-surgery-delete")
	public @ResponseBody JsonResponse<Object> deleteSurgery(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteSurgery starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-deleteSurgery?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("deleteSurgery" + resp);
		logger.info("Method : deleteSurgery ends");
		return resp;
	}

	// configuration-subgroup-master-type-view-onclick
	@SuppressWarnings("unchecked")
	@GetMapping("configuration-subgroup-master-type-view-onclick")
	public @ResponseBody List<HISConfigurationModel> viewOnclickSubGroup(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewOnclickSubGroup starts");

		JsonResponse<List<HISConfigurationModel>> resp = new JsonResponse<List<HISConfigurationModel>>();
		List<HISConfigurationModel> returnList = new ArrayList<HISConfigurationModel>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewOnclickSubGroup?id=" + id, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewOnclickSubGroup ends");
		return returnList;
	}

}