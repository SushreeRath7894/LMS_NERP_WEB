package nirmalya.aathithya.webmodule.warehouse.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;
import nirmalya.aathithya.webmodule.warehouse.model.ZoneMasterModel;
import nirmalya.aathithya.webmodule.warehouse.model.ZoneRackModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "warehouse/" })
public class WarehouseController {
	Logger logger = LoggerFactory.getLogger(WarehouseController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@SuppressWarnings("unused")
	@GetMapping(value = { "manage-warehouse" })
	public String manageWareHouse(Model model, HttpSession session, @RequestParam Optional<String> id) {
		logger.info("Method : manageWareHouse starts");
		String userName = "";
		String org = "";
		String orgDiv = "";

		try {
			userName = (String) session.getAttribute("USER_NAME");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String typeValue = "";
		if (id.isPresent()) {
			typeValue = id.get();
		}
		try {
			DropDownModel[] location = restClient.getForObject(
					env.getMasterUrl() + "getWarehouseLocationList?org=" + org + "&orgDiv=" + orgDiv+ "&type=" + typeValue,
					DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(location);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] category = restClient.getForObject(
					env.getMasterUrl() + "getWarehouseItemCategoryList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> categorylist = Arrays.asList(category);
			model.addAttribute("categorylist", categorylist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] category = restClient.getForObject(
					env.getMasterUrl() + "getWarehouseTypeWiseItemCategoryList?org=" + org + "&orgDiv=" + orgDiv+"&type=" + typeValue,
					DropDownModel[].class);
			List<DropDownModel> warehouseWiseCategorylist = Arrays.asList(category);
			model.addAttribute("warehouseWiseCategorylist", warehouseWiseCategorylist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : manageWareHouse ends");
//		return "warehouse/managewarehouse";
		return "warehouse/warehouse-config";
//		return "test";
	}

	/**
	 * get Item name
	 *
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-warehouse-getItemName" })
	public @ResponseBody JsonResponse<List<DropDownModel>> getWhItemName(Model model,
			@RequestParam String id, HttpSession session) {
		logger.info("Method : getWhItemName starts");
		JsonResponse<List<DropDownModel>> res = new JsonResponse<List<DropDownModel>>();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-getWhItemName?category=" + id + "&org=" + org
					+ "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getWhItemName ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-warehouse-get-location")
	public @ResponseBody JsonResponse<Object> getlocationDetailAgainstId(@RequestParam String id,String type, HttpSession session) {
		logger.info("Method : getlocationDetailAgainstId starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-get-locationDeatils?id=" + id +"&type="+type+"&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getlocationDetailAgainstId ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-warehouse-get-item-config")
	public @ResponseBody JsonResponse<Object> getItemConfigOfBin(@RequestParam String id, HttpSession session) {
		logger.info("Method : getItemConfigOfBin starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-get-itemconfig?id=" + id + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getItemConfigOfBin ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-warehouse-get-bin")
	public @ResponseBody JsonResponse<Object> getBinDetailsByRackId(@RequestParam String id, HttpSession session) {
		logger.info("Method : getBinDetailsByRackId starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-get-bindetails?id=" + id + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getBinDetailsByRackId ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-warehouse-zone-save")
	public @ResponseBody JsonResponse<Object> saveZoneMaster(@RequestBody ZoneMasterModel zoneMasterModel,
			HttpSession session) {
		logger.info("Method : saveZoneMaster starts");
		System.out.println("add" + zoneMasterModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

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

		zoneMasterModel.setCreatedBy(userId);
		zoneMasterModel.setOrganization(organization);
		zoneMasterModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveZoneMaster", zoneMasterModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		System.out.println("adddddd" + resp);
		logger.info("Method : saveLocationMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-warehouse-save-rack")
	public @ResponseBody JsonResponse<Object> saveRackMaster(@RequestBody ZoneRackModel zoneRackModel,
			HttpSession session) {
		logger.info("Method : saveRackMaster starts");
		System.out.println("saveRackMaster" + zoneRackModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

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

		zoneRackModel.setCreatedBy(userId);
		zoneRackModel.setOrganization(organization);
		zoneRackModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveRackMaster", zoneRackModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		System.out.println("addrack" + resp);
		logger.info("Method : saveRackMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-warehouse-get-warehouse-zone-details" })
	public @ResponseBody JsonResponse<List<ZoneMasterModel>> getWarehouseZoneDetailAgainstId(Model model,
			@RequestBody String id, BindingResult result) {
		System.out.println("getWarehouseZoneDetailAgainstId" + id);
		logger.info("Method : getWarehouseZoneDetailAgainstId starts");

		JsonResponse<List<ZoneMasterModel>> res = new JsonResponse<List<ZoneMasterModel>>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getZoneDetails?id=" + id, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ZoneMasterModel> zoneDetails = mapper.convertValue(res.getBody(),
					new TypeReference<List<ZoneMasterModel>>() {
					});

			for (ZoneMasterModel m : zoneDetails) {
				try {
					ZoneRackModel[] section = restClient.getForObject(
							env.getMasterUrl() + "viewRackListByZone?id=" + m.getZoneId(), ZoneRackModel[].class);
					List<ZoneRackModel> sectionList = Arrays.asList(section);

					m.setSectionList(sectionList);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

			}

			res.setBody(zoneDetails);
			System.out.println("Rack" + zoneDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("view" + res);
		logger.info("Method : getWarehouseZoneDetailAgainstId ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-warehouse-zone-edit")
	public @ResponseBody JsonResponse<Object> editZoneMaster(@RequestBody String zone, HttpSession session) {
		logger.info("Method : editZoneMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editZoneMaster?id=" + zone, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : editZoneMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-warehouse-rack-edit")
	public @ResponseBody JsonResponse<Object> editRackMaster(@RequestBody String zone, HttpSession session) {
		logger.info("Method : editRackMaster starts");
		System.out.println("edit" + zone);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editRackMaster?id=" + zone, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		System.out.println("editweb" + resp);
		logger.info("Method : editRackMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-warehouse-zone-delete")
	public @ResponseBody JsonResponse<Object> deleteZoneMaster(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteZoneMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteZoneMaster?id=" + id + "&createdBy=" + userId
					+ "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : deleteZoneMaster starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/manage-warehouse-item-config-delete")
	public @ResponseBody JsonResponse<Object> deleteItemConfig(@RequestParam String binid, @RequestParam String skuid, HttpSession session) {
		logger.info("Method : deleteItemConfig starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
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
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteItemConfig?binid=" + binid + "&createdBy=" + userId
					+ "&org=" + organization + "&orgDiv=" + orgDivision + "&skuid=" + skuid, JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : deleteItemConfig starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/manage-warehouse-rack-delete")
	public @ResponseBody JsonResponse<Object> deleteRackMaster(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteZoneMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteRackMaster?id=" + id + "&createdBy=" + userId
					+ "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : deleteRackMaster starts");
		return resp;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@PostMapping(value = { "manage-warehouse-get-Wh-room-details" })
	public @ResponseBody JsonResponse<List<WirehouseRoomModel>> getwarehouseRoomDetails(Model model,
			@RequestBody List<String> tCountry, BindingResult result, HttpSession session) {
		logger.info("Method : getwarehouseRoomDetails starts");

		JsonResponse<List<WirehouseRoomModel>> res = new JsonResponse<List<WirehouseRoomModel>>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restClient.postForObject(env.getMasterUrl() + "getwarehouseRoomDetails", tCountry,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<WirehouseRoomModel> locDetails = mapper.convertValue(res.getBody(),
					new TypeReference<List<WirehouseRoomModel>>() {
					});

			List<DropDownModel> roomCountList = new ArrayList<DropDownModel>();

			try {
				DropDownModel[] roomCount = restClient.postForObject(env.getMasterUrl() + "countZoneWiseRoom", tCountry,
						DropDownModel[].class);
				roomCountList = Arrays.asList(roomCount);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (tCountry.size() > 0) {
				for (int i = 0; i < locDetails.size(); i++) {
					try {
						WirehouseRoomModel[] room = restClient.getForObject(
								env.getMasterUrl() + "viewRoomListByRack?id=" + locDetails.get(i).getRackId(),
								WirehouseRoomModel[].class);
						List<WirehouseRoomModel> roomList = Arrays.asList(room);

						locDetails.get(i).setRoomList(roomList);
						int c = 0;
						for (WirehouseRoomModel a : roomList) {
							c = c + 1;
						}
						locDetails.get(i).setRoomCount(c);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

				}

				for (DropDownModel m : roomCountList) {
					for (WirehouseRoomModel a : locDetails) {
						if (m.getKey().contains(a.getZoneId())) {
							a.setFloorCount(Integer.parseInt(m.getName()));
						}
					}
				}
			}

			res.setBody(locDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("room" + res);
		logger.info("Method : getwarehouseRoomDetails ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-warehouse-save-room")
	public @ResponseBody JsonResponse<Object> saveBinMaster(@RequestBody WirehouseRoomModel location,
			HttpSession session) {
		logger.info("Method : saveBinMaster starts");
		System.out.println("addddB" + location);
		JsonResponse<Object> resp = new JsonResponse<Object>();

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

		location.setCreatedBy(userId);
		location.setOrganization(organization);
		location.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveBinMaster", location, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveBinMaster starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-warehouse-save-item-config")
	public @ResponseBody JsonResponse<Object> saveItemConfig(@RequestBody DropDownModel data,
			HttpSession session) {
		logger.info("Method : saveItemConfig starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
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
		data.setOrgName(organization);
		data.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveItemConfig", data, JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : saveItemConfig starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-warehouse-room-delete")
	public @ResponseBody JsonResponse<Object> deleteBinMaster(@RequestBody String floor, HttpSession session) {
		logger.info("Method : deleteBinMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteBinMaster?id=" + floor + "&createdBy=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : deleteBinMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-warehouse-save-configuratin")
	public @ResponseBody JsonResponse<Object> saveBinConfiguration(@RequestBody WirehouseRoomModel config,
			HttpSession session) {
		logger.info("Method : saveBinConfiguration starts");
		System.out.println("addddB" + config);
		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		System.out.println("ADDDDDIVISION-======-" + orgDivision);

		config.setCreatedBy(userId);
		config.setOrganization(organization);
		config.setOrgDivision(orgDivision);
		System.out.println("ADDDDORGGGG----" + config);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveBinConfiguration", config, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		System.out.println("roomadd" + resp);
		logger.info("Method : saveBinConfiguration starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-warehouse-get-bindata" })
	public @ResponseBody JsonResponse<Object> viewBindata(@RequestParam String rmId, HttpSession session) {
		logger.info("Method : viewBindata starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			resp = restClient.getForObject(
					env.getMasterUrl() + "viewBindata?rmId=" + rmId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		System.out.println("editweb" + resp);
		logger.info("Method : viewBindata starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "manage-warehouse-delete")
	public @ResponseBody JsonResponse<Object> deleteBin(@RequestParam String binlist, HttpSession session) {
		logger.info("Method : deleteBin starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restClient.getForObject(
					env.getMasterUrl() + "deleteBin?binlist=" + binlist + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			resp.setMessage("Success");
		}
		// System.out.println("delete@" + resp);
		logger.info("Method : deleteBin Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "manage-warehouse-deleteroom")
	public @ResponseBody JsonResponse<Object> deleteRoom(@RequestParam String binlist, HttpSession session) {
		logger.info("Method : deleteRoom starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restClient.getForObject(
					env.getMasterUrl() + "deleteRoom?binlist=" + binlist + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			resp.setMessage("Success");
		}
		// System.out.println("delete@" + resp);
		logger.info("Method : deleteRoom Ends");
		return resp;
	}

}
