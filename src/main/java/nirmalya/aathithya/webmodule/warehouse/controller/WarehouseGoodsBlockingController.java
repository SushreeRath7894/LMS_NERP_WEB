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
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller
@RequestMapping(value = { "warehouse/" })
public class WarehouseGoodsBlockingController {
	Logger logger = LoggerFactory.getLogger(WarehouseGoodsBlockingController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "/goods-blocking" })
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
			DropDownModel[] location = restClient.getForObject(env.getMasterUrl() + "getWarehouseLocationList?org="
					+ org + "&orgDiv=" + orgDiv + "&type=" + typeValue, DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(location);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] category = restClient.getForObject(env.getMasterUrl()
					+ "getWarehouseTypeWiseItemCategoryList?org=" + org + "&orgDiv=" + orgDiv + "&type=" + typeValue,
					DropDownModel[].class);
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
		logger.info("Method : manageWareHouse ends");
		return "warehouse/goods-blocking";
	}

	// zoneListData
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "goods-blocking-getzoneList" })
	public @ResponseBody JsonResponse<Object> zoneListData(@RequestParam String id, HttpSession session) {
		logger.info("Method : zoneListData starts");
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
		logger.info("Method : zoneListData ends");
		return res;
	}

//rackListData
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "goods-blocking-getrackList" })
	public @ResponseBody JsonResponse<Object> rackListData(@RequestParam String id, HttpSession session) {
		logger.info("Method : rackListData starts");
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
		logger.info("Method : rackListData ends");
		return res;
	}

//get itemlist
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "goods-blocking-getitemList" })
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
	// View Aggrid

	@SuppressWarnings("unchecked")
	@GetMapping("goods-blocking-through-ajax")
	public @ResponseBody Object viewBlockData(@RequestParam String pageno, @RequestParam String type,
			HttpSession session) {

		logger.info("Method :viewBlockData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "rest-getWarehouseBlockData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewBlockData ends");
		return resp;
	}

	// getWarehouseAllocateDataForBlocking
	@SuppressWarnings("unchecked")
	@GetMapping("goods-blocking-get-data")
	public @ResponseBody Object getWarehouseAllocateDataForBlocking(@RequestParam String warehouseId,
			HttpSession session) {
		logger.info("Method :getWarehouseAllocateDataForBlocking starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getWarehouseAllocateDataForBlocking?warehouseId="
					+ warehouseId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getWarehouseAllocateDataForBlocking ends");
		return resp;
	}

//Get filter data
	@SuppressWarnings("unchecked")
	@GetMapping("goods-blocking-get-filterData")
	public @ResponseBody Object getBlockStockData(@RequestParam String warehouseId, String zoneId, String rackId,
			String categoryId, String itemName, HttpSession session) {
		logger.info("Method :getBlockStockData starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "getGoodsAllocatefilterData?warehouseId=" + warehouseId
					+ "&zoneId=" + zoneId + "&rackId=" + rackId + "&categoryId=" + categoryId + "&itemName=" + itemName
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getBlockStockData ends");
		return resp;
	}

	// Save
	@SuppressWarnings("unchecked")
	@PostMapping("goods-blocking-submit-blockdetails")
	public @ResponseBody JsonResponse<Object> submitBlockDetails(HttpSession session,
			@RequestBody List<WirehouseRoomModel> wirehouseRoomModel) {
		logger.info("Method : submitBlockDetails starts");
		System.out.println(wirehouseRoomModel);
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
			m.setBlockingBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "warehouse-blocking-save", wirehouseRoomModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : submitBlockDetails ends");
		return resp;
	}

	// Assing Dispatch Request
	@SuppressWarnings("unchecked")
	@GetMapping("goods-blocking-assignForDispatch")
	public @ResponseBody JsonResponse<List<WirehouseRoomModel>> assignDispatchRequest(HttpSession session,
			@RequestParam String assignStatus, String blockId) {

		logger.info("Method : assignDispatchRequest starts");
		JsonResponse<List<WirehouseRoomModel>> response = new JsonResponse<List<WirehouseRoomModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restClient
					.getForObject(
							env.getMasterUrl() + "assignDispatchRequest?assignStatus=" + assignStatus + "&blockId="
									+ blockId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : assignDispatchRequest ends");
		return response;
	}

	// Delete block data
	@SuppressWarnings("unchecked")
	@GetMapping(value = "goods-blocking-delete")
	public @ResponseBody JsonResponse<Object> deleteBlockdata(@RequestParam String blockId, HttpSession session) {
		logger.info("Method : deleteBlockdata starts");
		String orgName = "";
		String orgDivision = "";
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteBlockdata?blockId=" + blockId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		System.out.println("delete@" + resp);
		logger.info("Method : deleteBlockdata Ends");
		return resp;
	}

	// View Block Data
	@GetMapping("goods-blocking-viewblock")
	public @ResponseBody Object viewblock(@RequestParam String rmId, HttpSession session) {

		logger.info("Method :viewblock starts");
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
			resp = restClient.getForObject(
					env.getMasterUrl() + "rest-viewblock?rmId=" + rmId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DXCFVGDS" + resp);
		return resp;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("goods-blocking-get-avl-bin")
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
		data.setId("BLOCK");

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "warehouse-allocation-avlbin", data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllocation ends");
		return resp;
	}
}
