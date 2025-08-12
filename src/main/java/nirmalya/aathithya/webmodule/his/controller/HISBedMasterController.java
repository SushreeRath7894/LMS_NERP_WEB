package nirmalya.aathithya.webmodule.his.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.his.model.HISBedMasterModel;

@Controller
@RequestMapping(value = "his")
public class HISBedMasterController {

	Logger logger = LoggerFactory.getLogger(HISBedMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/manage-bed")
	public String BedMaster(Model model, HttpSession session) {
		logger.info("Method : BedMaster method starts");

		// bedcategory
		try {
			DropDownModel[] bedCategoryType = restTemplate.getForObject(env.getHisUrl() + "getbedCategoryType",
					/*
					 * "getbedCategoryType?organization=" + organization + "&orgDivision=" +
					 * orgDivision,
					 */
					DropDownModel[].class);

			List<DropDownModel> category = Arrays.asList(bedCategoryType);
			System.out.println("cccccccccccccc" + category);
			model.addAttribute("bedList", category);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// floorType
		try {
			DropDownModel[] floorType = restTemplate.getForObject(env.getHisUrl() + "getFloorType",
					DropDownModel[].class);

			List<DropDownModel> floorListType = Arrays.asList(floorType);
			model.addAttribute("floorList", floorListType);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] departmentType = restTemplate.getForObject(env.getHisUrl() + "getDepartmentList",
					DropDownModel[].class);

			List<DropDownModel> departmentListType = Arrays.asList(departmentType);
			model.addAttribute("departmentList", departmentListType);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : BedMaster method ends");
		return "his/manage-bed.html";
	}

	/* deleteBed */

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "bed-mstr-ward-list" })
	public @ResponseBody JsonResponse<Object> getwardList(@RequestParam String wid) {
		logger.info("Method : getwardList starts" + wid);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getHisUrl() + "rest-ward-list?id=" + wid, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getwardList ends" + res);
		return res;
	}

	/* add */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "bed-mstr-add" })
	public @ResponseBody JsonResponse<Object> addBedMaster(HttpSession session, @RequestBody HISBedMasterModel data) {
		logger.info("Method : addBedMaster starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		System.out.println("data=======" + data);
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
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		System.out.println("detailsss ===" + data);
		try {
			res = restTemplate.postForObject(env.getHisUrl() + "rest-bed-mstr-add", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addBedMaster ends" + res);
		return res;

	}

	/* view */

	@SuppressWarnings("unchecked")

	@GetMapping("bed-mstr-view")
	public @ResponseBody List<HISBedMasterModel> viewBedMaster(HttpSession session) {

		logger.info("Method : view Start");

		JsonResponse<List<HISBedMasterModel>> resp = new JsonResponse<List<HISBedMasterModel>>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-bed-mstr-view", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<HISBedMasterModel> viewMaster = mapper.convertValue(resp.getBody(),
				new TypeReference<List<HISBedMasterModel>>() {
				});

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : view ends" + viewMaster);
		return viewMaster;

	}

	/* edit */

	@SuppressWarnings("unchecked")

	@GetMapping("bed-mstr-edit")
	public @ResponseBody JsonResponse<HISBedMasterModel> editBedMaster(@RequestParam String id, HttpSession session) {

		logger.info("Method : editBedMaster starts");
		JsonResponse<HISBedMasterModel> jsonResponse = new JsonResponse<HISBedMasterModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USERID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("id====" + id);

		try {
			jsonResponse = restTemplate.getForObject(env.getHisUrl() + "rest-bed-mstr-edit?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		HISBedMasterModel Model = mapper.convertValue(jsonResponse.getBody(), new TypeReference<HISBedMasterModel>() {
		});

		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editBedMaster ends" + jsonResponse);
		return jsonResponse;
	}

	//

	//bed-mstr-delete
	
	@SuppressWarnings("unchecked")
	@GetMapping("/bed-mstr-delete")
	public @ResponseBody JsonResponse<Object> deleteBed(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteBed starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getHisUrl() + "deleteBed?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteBed ends");
		return resp;
	}

}
