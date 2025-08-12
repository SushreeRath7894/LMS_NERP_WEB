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
public class WarehouseStackingController {
	Logger logger = LoggerFactory.getLogger(WarehouseStackingController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("warehouse-stacking")
	public String goodsDispatchPage(Model model, HttpSession session, @RequestParam Optional<String> id) {
		logger.info("Method : stackingPage starts");

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
		logger.info("Method : stackingPage ends");
		return "warehouse/warehouse-stacking";
	}

	// View Aggrid requested dispatch goods
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-stacking-through-ajax")
	public @ResponseBody Object viewRequestedStacking(@RequestParam String pageno, @RequestParam String type,
			HttpSession session) {
		logger.info("Method :viewRequestedStacking starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewRequestedStackingGoods?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewRequestedStacking ends");
		return resp;
	}

	// getStackingData
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-stacking-getAllocationDataforStacking")
	public @ResponseBody Object getAllocationDataforStacking(@RequestParam String warehouse, String allocate,
			HttpSession session) {
		logger.info("Method :getAllocationDataforStacking starts");
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
			resp = restClient
					.getForObject(
							env.getMasterUrl() + "getAllocationDataforStacking?warehouse=" + warehouse + "&allocate="
									+ allocate + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getAllocationDataforStacking ends");
		return resp;
	}

	// get Itemlist

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "warehouse-stacking-getitemList" })
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

	// Save

	@SuppressWarnings("unchecked")
	@PostMapping("warehouse-stacking-submit-stacking")
	public @ResponseBody JsonResponse<Object> modifyStackingData(@RequestBody List<DropDownModel> data,
			HttpSession session) {
		logger.info("Method : modifyStackingData starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (DropDownModel m : data) {
			m.setCreatedBy(userId);
			m.setOrgName(orgName);
			m.setOrgDivision(orgDivision);
		}

		try {
			response = restClient.postForObject(env.getMasterUrl() + "modifyStackingData", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : modifyStackingData ends");
		return response;
	}
	// viewBinAllocationdata

	@GetMapping("warehouse-stacking-viewstock")
	public @ResponseBody Object viewBinAllocationdata(@RequestParam String rmId, HttpSession session) {

		logger.info("Method :viewBinAllocationdata starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewBinAllocationdata?rmId=" + rmId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Get Zone List
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "warehouse-stacking-getzoneList" })
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
	@GetMapping(value = { "warehouse-stacking-getrackList" })
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

	// Get Bin List
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "warehouse-stacking-getbinList" })
	public @ResponseBody JsonResponse<Object> binLists(@RequestParam String id, String binId, HttpSession session) {
		logger.info("Method : binLists starts");
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
			res = restClient.getForObject(env.getMasterUrl() + "binLists?rackId=" + id + "&binId=" + binId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : binLists ends");
		return res;
	}

	@GetMapping("warehouse-stacking-get-editdata")
	public @ResponseBody Object editData(@RequestParam String rmId, HttpSession session) {

		logger.info("Method :editData starts");
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
					env.getMasterUrl() + "rest-editData?rmId=" + rmId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// Modify Bin
	@SuppressWarnings("unchecked")
	@PostMapping("warehouse-stacking-save-stackdata")
	public @ResponseBody JsonResponse<Object> saveStackdata(HttpSession session,
			@RequestBody WirehouseRoomModel wirehouseRoomModel) {
		logger.info("Method : saveStackdata starts");

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

		wirehouseRoomModel.setCreatedBy(userId);
		wirehouseRoomModel.setOrganization(organization);
		wirehouseRoomModel.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "warehouse-saveStackdata", wirehouseRoomModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveStackdata ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("warehouse-stacking-get-avl-bin")
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

}
