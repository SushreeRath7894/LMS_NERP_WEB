package nirmalya.aathithya.webmodule.warehouse.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller
@RequestMapping(value = { "warehouse/" })
public class WarehouseAllocationController {
	Logger logger = LoggerFactory.getLogger(WarehouseAllocationController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	WarehouseAllocationController warehouseAllocationController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/warehouse-allocation" })
	public String manageWareHouse(Model model, HttpSession session, @RequestParam Optional<String> id) {
		logger.info("Method : manageWareHouse starts");
		String org = "";
		String orgDiv = "";

		try {
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
//		try {
//			DropDownModel[] category = restClient.getForObject(
//					env.getMasterUrl() + "getWarehouseItemCategoryList?org=" + org + "&orgDiv=" + orgDiv,
//					DropDownModel[].class);
//			List<DropDownModel> categorylist = Arrays.asList(category);
//			model.addAttribute("categorylist", categorylist);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
		try {
			DropDownModel[] category = restClient.getForObject(
					env.getMasterUrl() + "getWarehouseTypeWiseItemCategoryList?org=" + org + "&orgDiv=" + orgDiv+"&type=" + typeValue,
					DropDownModel[].class);
			List<DropDownModel> categorylist = Arrays.asList(category);
			model.addAttribute("categorylist", categorylist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] manufacturePlace = restClient.getForObject(
					env.getMasterUrl() + "getManufacturePlaceLists?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> ManufacturePlaceLists = Arrays.asList(manufacturePlace);

			model.addAttribute("ManufacturePlaceLists", ManufacturePlaceLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] batchNoLists = restClient.getForObject(
					env.getMasterUrl() + "getBatchNoLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> BatchNoLists = Arrays.asList(batchNoLists);

			model.addAttribute("BatchNoLists", BatchNoLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] shift = restClient.getForObject(
					env.getMasterUrl() + "getShiftListsAllocation?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] line = restClient.getForObject(
					env.getMasterUrl() + "getLineLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> lineLists = Arrays.asList(line);

			model.addAttribute("lineLists", lineLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] packing = restClient.getForObject(
					env.getMasterUrl() + "getPackingSiteLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> packingSiteLists = Arrays.asList(packing);

			model.addAttribute("packingSiteLists", packingSiteLists);

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
		return "warehouse/warehouse-allocation";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "warehouse-allocation-getzoneList" })
	public @ResponseBody JsonResponse<Object> getzoneList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getzoneList starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getzoneList?warehouseId=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getzoneList ends");
		return res;
	}

	// Get Rack List
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "warehouse-allocation-getrackList" })
	public @ResponseBody JsonResponse<Object> rackLists(@RequestParam String id, HttpSession session) {
		logger.info("Method : rackLists starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "rackLists?zoneId=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : rackLists ends");
		return res;
	}

	// getitemList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "warehouse-allocation-getitemList" })
	public @ResponseBody JsonResponse<Object> getitemList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getitemList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getitemList?categoryId=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getitemList ends");

		return res;
	}

//getWarehouseData
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-allocation-get-data")
	public @ResponseBody Object getWarehouseData(@RequestParam String warehouseId, HttpSession session) {

		logger.info("Method :getWarehouseData starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-getwarehouse?warehouseId=" + warehouseId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getWarehouseData ends");
		return resp;
	}

	// warehouse search Data
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-allocation-getFilterWarehouseData")
	public @ResponseBody Object warehouseGetAllData(@RequestParam String warehouseId, String zoneId, String rackId,
			String categoryId, String itemName, HttpSession session) {

		logger.info("Method :warehouseGetAllData starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "get-warehouse-search-data?warehouseId=" + warehouseId
					+ "&zoneId=" + zoneId + "&rackId=" + rackId + "&categoryId=" + categoryId + "&itemName=" + itemName
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :warehouseGetAllData ends");
		return resp;
	}

//saveAllocation
	@SuppressWarnings("unchecked")
	@PostMapping("warehouse-allocation-save-allocation")
	public @ResponseBody JsonResponse<Object> saveAllocation(HttpSession session,
			@RequestBody List<WirehouseRoomModel> wirehouseRoomModel) {

		logger.info("Method : saveAllocation starts");
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

		for (WirehouseRoomModel m : wirehouseRoomModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "warehouse-allocation-save", wirehouseRoomModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllocation ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("warehouse-allocation-get-avl-bin")
	public @ResponseBody JsonResponse<Object> getAvlBinByLocSku(HttpSession session, @RequestBody DropDownModel data) {

		logger.info("Method : saveAllocation starts");
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
			resp = restClient.postForObject(env.getMasterUrl() + "warehouse-allocation-avlbin", data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllocation ends");
		return resp;
	}

//viewAllocationData
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-allocation-through-ajax")
	public @ResponseBody Object viewAllocationData(@RequestParam String pageno, @RequestParam String type, HttpSession session) {
		logger.info("Method :viewAllocationData starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "warehouse-allocation-view-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewAllocationData ends");
		return resp;
	}

	// Clear Bin
	@SuppressWarnings("unchecked")
	@GetMapping(value = "warehouse-allocation-clearbin")
	public @ResponseBody JsonResponse<Object> clearBinData(@RequestParam String aiddatas, String bdata,
			HttpSession session) {
		logger.info("Method : clearBinData starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "clearBinData?aiddatas=" + aiddatas + "&bdata=" + bdata
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : clearBinData Ends");
		return resp;
	}
//viewBinAllocationdata

	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-allocation-get-bindata")
	public @ResponseBody Object viewBinAllocationdata(@RequestParam String rmId, HttpSession session) {

		logger.info("Method :viewBinAllocationdata starts");
		JsonResponse<Object> resp = new JsonResponse<>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewBinAllocationdata?rmId=" + rmId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// View Bin Data
	@GetMapping("warehouse-allocation-get-binconfigdata")
	public @ResponseBody Object viewbinconfigdata(@RequestParam String rmId, HttpSession session) {

		logger.info("Method :viewbinconfigdata starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewbinconfigdata?rmId=" + rmId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DXCFVGDS" + resp);
		return resp;

	}

	// deleteAllocationdata
	@SuppressWarnings("unchecked")
	@GetMapping("view-allocation-delete")
	public @ResponseBody Object deleteAllocationdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :viewBinAllocationdata starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-deleteAllocationdata?allocId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Approvalof data
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-allocation-approve")
	public @ResponseBody Object approveAllocationdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveAllocationdata starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-approveAllocationdata?allocId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Get Batch Data
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-allocation-get-batchdata")
	public @ResponseBody Object getBatchData(HttpSession session) {

		logger.info("Method :getBatchData starts");
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
					env.getMasterUrl() + "rest-getBatchData?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DXCFVGDS" + resp);
		return resp;

	}

	// Hold data
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-allocation-hold")
	public @ResponseBody Object holdAllocationdata(@RequestParam String rmId, HttpSession session) {

		logger.info("Method :holdAllocationdata starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-holdAllocationdata?rmId=" + rmId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Release data
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-allocation-release")
	public @ResponseBody Object releaseAllocationdata(@RequestParam String rmId, HttpSession session) {

		logger.info("Method :releaseAllocationdata starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-releaseAllocationdata?rmId=" + rmId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
