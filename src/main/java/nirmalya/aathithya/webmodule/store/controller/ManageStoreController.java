package nirmalya.aathithya.webmodule.store.controller;

import java.util.ArrayList;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.store.model.StoreRoomModel;
import nirmalya.aathithya.webmodule.store.model.StoreZoneMasterModel;
import nirmalya.aathithya.webmodule.store.model.StoreZoneRackModel;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;
import nirmalya.aathithya.webmodule.warehouse.model.ZoneMasterModel;
import nirmalya.aathithya.webmodule.warehouse.model.ZoneRackModel;

@Controller
@RequestMapping(value = { "store/" })
public class ManageStoreController {
	Logger logger = LoggerFactory.getLogger(ManageStoreController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@SuppressWarnings("unused")
	@GetMapping(value = { "manage-store" })
	public String manageStock(Model model, HttpSession session) {
		logger.info("Method : manageStore starts");
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
		try {
			DropDownModel[] location = restClient.getForObject(env.getMasterUrl() + "getStockLocationList?org="+org+"&orgDiv="+orgDiv, DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(location);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] category = restClient.getForObject(env.getMasterUrl() + "getStockItemCategoryList?org="+org+"&orgDiv="+orgDiv, DropDownModel[].class);
			List<DropDownModel> categorylist = Arrays.asList(category);
			model.addAttribute("categorylist", categorylist);
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
		logger.info("Method : manageStore ends");
		return "store/manage-store";
	}
	
	/**
	 * get Item name
	 *
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-store-getItemName" })
	public @ResponseBody JsonResponse<List<DropDownModel>> getstockItemName(Model model,
			@RequestParam("category") String category, HttpSession session) {
		logger.info("Method : getstockItemName starts");
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
			res = restClient.getForObject(env.getMasterUrl() + "rest-getstockItemName?category=" + category+"&org="+org+"&orgDiv="+orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getstockItemName ends");
		return res;
	}
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-store-get-location" })
	public @ResponseBody JsonResponse<Object> getStocklocationDeatils(Model model, @RequestBody String warehouseId,
			BindingResult result) {
		logger.info("Method : getStocklocationDeatils starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getStocklocationDeatils?id=" + warehouseId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getStocklocationDeatils ends");
		return res;

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-store-zone-save")
	public @ResponseBody JsonResponse<Object> saveStockZoneMaster(@RequestBody StoreZoneMasterModel stockZoneMasterModel, HttpSession session) {
		logger.info("Method : saveStockZoneMaster starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		

		String userId = "";
		String organization=""; 
		String orgDivision="";
	
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		stockZoneMasterModel.setCreatedBy(userId);
		stockZoneMasterModel.setOrganization(organization);
		stockZoneMasterModel.setOrgDivision(orgDivision);
		
		
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveStockZoneMaster", stockZoneMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveStockZoneMaster starts");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-store-save-rack")
	public @ResponseBody JsonResponse<Object> saveStockRackMaster(@RequestBody StoreZoneRackModel stockZoneRackModel, HttpSession session) {
		logger.info("Method : saveStockRackMaster starts");
		System.out.println("saveStockRackMaster"+stockZoneRackModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String organization=""; 
		String orgDivision="";
	
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		stockZoneRackModel.setCreatedBy(userId);
		stockZoneRackModel.setOrganization(organization);
		stockZoneRackModel.setOrgDivision(orgDivision);
		
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveStockRackMaster", stockZoneRackModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		System.out.println("addrack"+resp);
		logger.info("Method : saveStockRackMaster starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-store-zone-details" })
	public @ResponseBody JsonResponse<List<StoreZoneMasterModel>> getStockZoneDetailAgainstId(Model model, @RequestBody String id,
			BindingResult result) {
		System.out.println("getStockZoneDetailAgainstId"+id); 
		logger.info("Method : getStockZoneDetailAgainstId starts");
		
		
		JsonResponse<List<StoreZoneMasterModel>> res = new JsonResponse<List<StoreZoneMasterModel>>();
		
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getZoneDetailss?id=" + id,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			
			List<StoreZoneMasterModel> zoneDetails = mapper.convertValue(res.getBody(),
					new TypeReference<List<StoreZoneMasterModel>>() {
			});
			
			for(StoreZoneMasterModel m : zoneDetails) {
				try {
					StoreZoneRackModel[] section = restClient.getForObject(env.getMasterUrl() + "viewRackListByZonee?id="+m.getZoneId(), StoreZoneRackModel[].class);
					List<StoreZoneRackModel> sectionList = Arrays.asList(section);
					
					m.setSectionList(sectionList);
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				
			}
			
			res.setBody(zoneDetails);
			System.out.println("Rack"+zoneDetails);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("view"+res);
		logger.info("Method : getStockZoneDetailAgainstId ends");
		return res;

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-store-zone-edit")
	public @ResponseBody JsonResponse<Object> editStockZoneMaster(@RequestBody String zone, HttpSession session) {
		logger.info("Method : editStockZoneMaster starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editStockZoneMaster?id="+zone,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : editStockZoneMaster starts");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-store-rack-edit")
	public @ResponseBody JsonResponse<Object> editStockRackMaster(@RequestBody String zone, HttpSession session) {
		logger.info("Method : editStockRackMaster starts");
		System.out.println("edit"+zone);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editStockRackMaster?id="+zone,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		System.out.println("editweb"+resp);
		logger.info("Method : editStockRackMaster starts");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-store-zone-delete")
	public @ResponseBody JsonResponse<Object> deleteStockZoneMaster(@RequestBody String zone, HttpSession session) {
		logger.info("Method : deleteStockZoneMaster starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteStockZoneMaster?id="+zone+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : deleteStockZoneMaster starts");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-store-rack-delete")
	public @ResponseBody JsonResponse<Object> deleteStockRackMaster(@RequestBody String zone, HttpSession session) {
		logger.info("Method : deleteStockRackMaster starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteStockRackMaster?id="+zone+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : deleteStockRackMaster starts");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@PostMapping(value = { "manage-store-get-Wh-room-details" })
	public @ResponseBody JsonResponse<List<StoreRoomModel>> getStockRoomDetails(Model model, @RequestBody List<String> tCountry,
			BindingResult result,HttpSession session) {
		logger.info("Method : getStockRoomDetails starts");
		
		JsonResponse<List<StoreRoomModel>> res = new JsonResponse<List<StoreRoomModel>>();
		
		String organization=""; 
		String orgDivision="";
	
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restClient.postForObject(env.getMasterUrl() + "getStockRoomDetails", tCountry,
					JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			List<StoreRoomModel> locDetails = mapper.convertValue(res.getBody(),
					new TypeReference<List<StoreRoomModel>>() {
			});
			
			List<DropDownModel> roomCountList = new ArrayList<DropDownModel>();
			
			try {
				DropDownModel[] roomCount = restClient.postForObject(env.getMasterUrl() + "countStockZoneWiseRoom",tCountry, DropDownModel[].class);
				roomCountList = Arrays.asList(roomCount);
						} catch(Exception e) {
				e.printStackTrace();
			}

			if(tCountry.size() > 0) {
				for(int i = 0; i < locDetails.size(); i++) {
					try {
						StoreRoomModel[] room = restClient.getForObject(env.getMasterUrl() + "viewStockRoomListByRack?id="+locDetails.get(i).getRackId(), StoreRoomModel[].class);
						List<StoreRoomModel> roomList = Arrays.asList(room);
						
						locDetails.get(i).setRoomList(roomList);
						int c  = 0;
						for(StoreRoomModel a : roomList) {
							c = c + 1;
						}
						locDetails.get(i).setRoomCount(c);
					} catch (RestClientException e) {
						e.printStackTrace();
					}
					
				}
				
				for(DropDownModel m : roomCountList) {
					for(StoreRoomModel a : locDetails) {
						if(m.getKey().contains(a.getZoneId())) {
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
		System.out.println("room"+res);
		logger.info("Method : getStockRoomDetails ends");
		return res;
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-store-save-room")
	public @ResponseBody JsonResponse<Object> saveBinMaster(@RequestBody StoreRoomModel location, HttpSession session) {
		logger.info("Method : saveBinMaster starts");
		System.out.println("addddB"+location);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String organization=""; 
		String orgDivision="";
	
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ADDDDDIVISION-======-"+orgDivision);

		location.setCreatedBy(userId);
		location.setOrganization(organization);
		location.setOrgDivision(orgDivision);
		System.out.println("ADDDDORGGGG----"+location);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveStockBinMaster", location,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		System.out.println("roomadd"+resp);
		logger.info("Method : saveBinMaster starts");
		return resp;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/manage-warehouse-room-delete") public @ResponseBody
	 * JsonResponse<Object> deleteBinMaster(@RequestBody String floor, HttpSession
	 * session) { logger.info("Method : deleteBinMaster starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * 
	 * String userId = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID"); } catch (Exception
	 * e) { e.printStackTrace(); }
	 * 
	 * try { resp = restClient.getForObject(env.getMasterUrl() +
	 * "deleteBinMaster?id="+floor+"&createdBy="+userId, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * String message = resp.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { resp.setMessage("success"); }
	 * 
	 * logger.info("Method : deleteBinMaster starts"); return resp; }
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-store-save-configuratin")
	public @ResponseBody JsonResponse<Object> saveStockBinConfiguration(@RequestBody StoreRoomModel config, HttpSession session) {
		logger.info("Method : saveStockBinConfiguration starts");
		System.out.println("addddB"+config);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String organization=""; 
		String orgDivision="";
	
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ADDDDDIVISION-======-"+orgDivision);

		config.setCreatedBy(userId);
		config.setOrganization(organization);
		config.setOrgDivision(orgDivision);
		System.out.println("ADDDDORGGGG----"+config);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveStockBinConfiguration", config,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		System.out.println("roomadd"+resp);
		logger.info("Method : saveStockBinConfiguration starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-store-get-bindata" })
	public @ResponseBody JsonResponse<Object> viewStockBindata(@RequestParam String rmId, HttpSession session) {
		logger.info("Method : viewStockBindata starts");
		System.out.println("edit"+rmId);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "viewStockBindata?rmId="+rmId+ "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		System.out.println("editweb"+resp);
		logger.info("Method : viewStockBindata starts");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "manage-store-delete")
	public @ResponseBody JsonResponse<Object> deleteStockBin(@RequestParam String binlist,
			HttpSession session) {
		logger.info("Method : deleteStockBin starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {

			resp = restClient.getForObject(env.getMasterUrl() + "deleteStockBin?binlist="+binlist+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			resp.setMessage("Success");
		}
		//System.out.println("delete@" + resp);
		logger.info("Method : deleteStockBin Ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "manage-store-deleteroom")
	public @ResponseBody JsonResponse<Object> deleteStockRoom(@RequestParam String binlist,
			HttpSession session) {
		logger.info("Method : deleteStockRoom starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {

			resp = restClient.getForObject(env.getMasterUrl() + "deleteStockRoom?binlist="+binlist+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			resp.setMessage("Success");
		}
		//System.out.println("delete@" + resp);
		logger.info("Method : deleteStockRoom Ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-store-item-list")
	public @ResponseBody Object viewBinItemList(@RequestParam String bindatas,HttpSession session) {
		logger.info("Method :viewBinItemList starts");
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
			resp = restClient.getForObject(
					env.getMasterUrl() + "viewBinItemList?bindatas=" + bindatas + "&orgName=" + orgName+ "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewBinItemList ends");
		return resp;
	}
	
	@GetMapping("manage-store-delete-item")
	public @ResponseBody Object deleteConfigItemdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteConfigItemdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "rest-deleteConfigItemdata?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}


