package nirmalya.aathithya.webmodule.master.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class WarehouseItemConfigurationController {
	Logger logger = LoggerFactory.getLogger(WarehouseItemConfigurationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	// warehouseItemConfiguration
	@GetMapping(value = { "warehouse-item-configuration" })
	public String warehouseItemConfiguration(Model model, HttpSession session, @RequestParam Optional<String> id) {
		logger.info("Method : warehouseItemConfiguration starts");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String typeValue = "";
		if (id.isPresent()) {
			typeValue = id.get();
		}

		try {
			DropDownModel[] item = restTemplate
					.getForObject(env.getMasterUrl() + "getItemListForWarehouseAllocationConfig?org=" + orgName
							+ "&orgDiv=" + orgDivision + "&type=" + typeValue, DropDownModel[].class);
			List<DropDownModel> itemlist = Arrays.asList(item);
			model.addAttribute("itemlist", itemlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : warehouseItemConfiguration ends");
		return "master/warehouse-item-configuration";
	}

	// viewWarehouseDetails
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-item-configuration-view-warehouse")
	public @ResponseBody Object viewWarehouseDetails(HttpSession session, @RequestParam String type) {
		logger.info("Method :viewWarehouseDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "warehouse-item-configuration-view-warehouse?orgName=" + orgName
							+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&type=" + type,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewWarehouseDetails ends");
		return resp;
	}

	// viewAllocateItems
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-item-configuration-view-allocate-item")
	public @ResponseBody Object viewAllocateItems(@RequestParam String whid, HttpSession session,
			@RequestParam String type) {
		logger.info("Method :viewAllocateItems starts");
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
			resp = restTemplate
					.getForObject(
							env.getMasterUrl() + "warehouse-item-configuration-view-allocate-item?orgName=" + orgName
									+ "&orgDivision=" + orgDivision + "&whid=" + whid + "&type=" + type,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewAllocateItems ends");
		return resp;
	}

	// saveAllocateItems
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-item-configuration-save-allocate-item")
	public @ResponseBody Object saveAllocateItems(@RequestParam String sku, String whid, String minQty, String edit,
			HttpSession session) {
		logger.info("Method :saveAllocateItems starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
		try {
			resp = restTemplate
					.getForObject(env.getMasterUrl() + "warehouse-item-configuration-save-allocate-item?orgName="
							+ orgName + "&orgDivision=" + orgDivision + "&userId=" + userId + "&sku=" + sku + "&whid="
							+ whid + "&minQty=" + minQty + "&edit=" + edit, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :saveAllocateItems ends");
		return resp;
	}

	// deleteAllocateItems
	@SuppressWarnings("unchecked")
	@GetMapping("warehouse-item-configuration-delete-allocate-item")
	public @ResponseBody Object deleteAllocateItems(@RequestParam String sku, String whid, HttpSession session) {
		logger.info("Method :deleteAllocateItems starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "warehouse-item-configuration-delete-allocate-item?orgName=" + orgName
							+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&sku=" + sku + "&whid=" + whid,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :deleteAllocateItems ends");
		return resp;
	}
}
