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
public class GoodsDispatchController {
	Logger logger = LoggerFactory.getLogger(GoodsDispatchController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("goods-dispatch")
	public String goodsDispatchPage(Model model, HttpSession session, @RequestParam Optional<String> id) {
		logger.info("Method : goodsDispatchPage starts");

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
		logger.info("Method : goodsDispatchPage ends");
		return "warehouse/goods-dispatch";
	}
	
	// get Itemlist
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "goods-dispatch-getitemList" })
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

	// View Aggrid requested dispatch goods
	@SuppressWarnings("unchecked")
	@GetMapping("goods-dispatch-through-ajax")
	public @ResponseBody Object viewRequestedDispatchGoods(@RequestParam String pageno, @RequestParam String type, HttpSession session) {
		logger.info("Method :viewRequestedDispatchGoods starts");
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
			resp = restClient.getForObject(env.getMasterUrl() + "rest-viewRequestedDispatchGoods?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewRequestedDispatchGoods ends");
		return resp;
	}

//getBlockingDataforDispatch
	@SuppressWarnings("unchecked")
	@GetMapping("goods-dispatch-getBlockingDataforDispatch")
	public @ResponseBody Object getBlockStockData(@RequestParam String warehouse, String block, HttpSession session) {
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
			resp = restClient.getForObject(env.getMasterUrl() + "getBlockingDataforDispatch?warehouse=" + warehouse
					+ "&block=" + block + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getBlockStockData ends");
		return resp;
	}

	// Save

	@SuppressWarnings("unchecked")
	@PostMapping("goods-dispatch-submit-dispatchdetails")
	public @ResponseBody JsonResponse<List<WirehouseRoomModel>> modifyDispatchData(HttpSession session,
			@RequestBody List<DropDownModel> data) {

		logger.info("Method : modifyDispatchData starts");
		
		JsonResponse<List<WirehouseRoomModel>> response = new JsonResponse<List<WirehouseRoomModel>>();
		
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
			response = restClient.postForObject(env.getMasterUrl() + "modifyDispatchData", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : modifyDispatchData ends");
		return response;
	}

	// View Block Data
	@GetMapping("goods-dispatch-viewblock")
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
}
