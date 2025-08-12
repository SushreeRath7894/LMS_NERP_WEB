package nirmalya.aathithya.webmodule.store.controller;

import java.util.Arrays;
import java.util.List;

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
import nirmalya.aathithya.webmodule.store.model.StoreRoomModel;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller
@RequestMapping(value = { "store/" })
public class StoreGoodsDispatchController {
	Logger logger = LoggerFactory.getLogger(StoreGoodsDispatchController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("goods-store-dispatch")
	public String goodsDispatchPage(Model model, HttpSession session) {
		logger.info("Method : goodsDispatchPage starts");

		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] location = restClient.getForObject(
					env.getMasterUrl() + "getStockLocationList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(location);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] category = restClient.getForObject(
					env.getMasterUrl() + "getStockItemCategoryList?org=" + org + "&orgDiv=" + orgDiv,
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
		return "store/goods-store-dispatch";
	}
	
	//getitemList
			@SuppressWarnings("unchecked")
			@GetMapping(value = { "goods-store-dispatch-getitemList" })
			public @ResponseBody JsonResponse<Object> getstoreitemList(@RequestParam String id, HttpSession session) {
				logger.info("Method : getstoreitemList starts" + id);
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
					res = restClient.getForObject(
							env.getMasterUrl() + "getstoreitemList?categoryId=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method : getstoreitemList ends");
				
				return res;
			}
			
			//View Aggrid requested dispatch goods
			@SuppressWarnings("unchecked")
			@GetMapping("goods-store-dispatch-through-ajax")
			public @ResponseBody Object viewStoreRequestedDispatchGoods(HttpSession session) {
				logger.info("Method :viewStoreRequestedDispatchGoods starts");
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
					resp = restClient.getForObject(
							env.getMasterUrl() + "rest-viewStoreRequestedDispatchGoods?orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method :viewStoreRequestedDispatchGoods ends");
				return resp;
			}
			
			//getBlockingDataforDispatch
			@SuppressWarnings("unchecked")
			@GetMapping("goods-store-dispatch-getBlockingDataforDispatch")
			public @ResponseBody Object getStoreBlockingDataforDispatch(@RequestParam String warehouse,String block,HttpSession session) {
				logger.info("Method :getStoreBlockingDataforDispatch starts");
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
					resp = restClient.getForObject(env.getMasterUrl() + "getStoreBlockingDataforDispatch?warehouse=" + warehouse
							+ "&block=" + block + "&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method :getStoreBlockingDataforDispatch ends");
				return resp;
			}
			
			//Save
			
			@SuppressWarnings("unchecked")
			@GetMapping("goods-store-dispatch-submit-dispatchdetails")
			public @ResponseBody JsonResponse<List<StoreRoomModel>> modifyStoreDispatchData(HttpSession session,
					@RequestParam String warehouseId, String blockId,String bdata) {

				logger.info("Method : modifyStoreDispatchData starts");
				JsonResponse<List<StoreRoomModel>> response = new JsonResponse<List<StoreRoomModel>>();
				String orgName = "";
				String orgDivision = "";
				try {
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					response = restClient.getForObject(env.getMasterUrl() + "modifyStoreDispatchData?warehouseId=" + warehouseId + "&blockId=" + blockId+  "&bdata=" + bdata+"&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				logger.info("Method : modifyStoreDispatchData ends");
				return response;
			}
			
			//View Block Data
			@GetMapping("goods-store-dispatch-viewblock")
			public @ResponseBody Object viewStoreblock(@RequestParam String rmId,String blockId, HttpSession session) {

				logger.info("Method :viewStoreblock starts");
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
					resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStoreblockDispatch?rmId=" + rmId + "&blockId=" + blockId + "&org="
							+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("DXCFVGDS"+resp);
				return resp;
				
			}
			
			
		

}
