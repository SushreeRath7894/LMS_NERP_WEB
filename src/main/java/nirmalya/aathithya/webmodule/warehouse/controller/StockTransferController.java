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
import nirmalya.aathithya.webmodule.warehouse.model.StockTransferModel;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller

@RequestMapping(value = { "warehouse/" })
public class StockTransferController {

	Logger logger = LoggerFactory.getLogger(StockTransferController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "stock-transfer" })
	public String stockTransferPage(Model model, HttpSession session, @RequestParam Optional<String> id) {
		logger.info("Method :awaitingWarehouseAllocationPage starts");

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
			DropDownModel[] location = restTemplate.getForObject(env.getMasterUrl() + "getWarehouseLocationList?org="
					+ org + "&orgDiv=" + orgDiv + "&type=" + typeValue, DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(location);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] category = restTemplate.getForObject(env.getMasterUrl()
					+ "getWarehouseTypeWiseItemCategoryList?org=" + org + "&orgDiv=" + orgDiv + "&type=" + typeValue,
					DropDownModel[].class);
			List<DropDownModel> categorylist = Arrays.asList(category);
			model.addAttribute("categorylist", categorylist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : awaitingWarehouseAllocationPage ends");
		return "warehouse/stock-transfer";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("stock-transfer-get-stack-data")
	public @ResponseBody Object getStockTransferStackData(@RequestParam String warehouseId, HttpSession session) {

		logger.info("Method :getStockTransferStackData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("<<<<<<<<<<<<<<<<<<<<< OK >>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getStockTransferStackData?warehouseId="
					+ warehouseId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getStockTransferStackData ends");
		return resp;
	}

	// add.

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "stock-transfer-add" })
	public @ResponseBody JsonResponse<Object> addStockTransfer(@RequestBody StockTransferModel av,
			HttpSession session) {
		logger.info("Method : addDispCartons function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		av.setCreatedBy(userId);
		av.setOrganization(organization);
		av.setOrgDivision(orgDivision);

		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addStockTransfer", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addDispCartons function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("stock-transfer-get-stack-data-filter")
	public @ResponseBody Object getStockTransferStackDataFilter(@RequestBody DropDownModel data, HttpSession session) {
		logger.info("Method :getStockTransferStackDataFilter starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		data.setOrgName(orgName);
		data.setOrgDivision(orgDivision);
		
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-getStockTransferStackDataFilter", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getStockTransferStackDataFilter ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "stock-transfer-getzoneList" })
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
			res = restTemplate.getForObject(env.getMasterUrl() + "getzoneList?warehouseId=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getzoneList ends");
		return res;
	}

	// Get Rack List
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "stock-transfer-getrackList" })
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
			res = restTemplate.getForObject(env.getMasterUrl() + "rackLists?zoneId=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : rackLists ends");
		return res;
	}

	// getitemList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "stock-transfer-getitemList" })
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
			res = restTemplate.getForObject(env.getMasterUrl() + "getitemList?categoryId=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getitemList ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("stock-transfer-save-allocation")
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
			resp = restTemplate.postForObject(env.getMasterUrl() + "stock-transfer-save", wirehouseRoomModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : saveAllocation ends");
		return resp;
	}

}
