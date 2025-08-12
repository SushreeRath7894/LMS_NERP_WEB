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
import nirmalya.aathithya.webmodule.store.model.StoreRoomModel;
import nirmalya.aathithya.webmodule.warehouse.controller.WarehouseGoodsBlockingController;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller
@RequestMapping(value = { "store/" })
public class StoreGoodsBlockingController {
	Logger logger = LoggerFactory.getLogger(WarehouseGoodsBlockingController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "/goods-store-blocking" })
	public String manageStore(Model model, HttpSession session) {
		logger.info("Method : manageStore starts");

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
		logger.info("Method : manageStore ends");
		return "store/goods-store-blocking";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "goods-store-blocking-getzoneList" })
	public @ResponseBody JsonResponse<Object> getStorezoneList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getStorezoneList starts");
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
					env.getMasterUrl() + "getStorezoneList?warehouseId=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getStorezoneList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "goods-store-blocking-getrackList" })
	public @ResponseBody JsonResponse<Object> rackStoreLists(@RequestParam String id, HttpSession session) {
		logger.info("Method : rackStoreLists starts");
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
			res = restClient.getForObject(env.getMasterUrl() + "rackStoreLists?zoneId=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : rackStoreLists ends");
		return res;
	}
	
	//getitemList
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "goods-store-blocking-getitemList" })
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
		
		//View Aggrid
		
		@SuppressWarnings("unchecked")
		@GetMapping("goods-store-blocking-through-ajax")
		public @ResponseBody Object viewStoreBlockData(HttpSession session) {

			logger.info("Method :viewStoreBlockData starts");
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
				resp = restClient.getForObject(
						env.getMasterUrl() + "rest-viewStoreBlockData?orgName=" + orgName + "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sdefrgthg"+resp);
			logger.info("Method :viewStoreBlockData ends");
			return resp;
		}
		
		//getWarehouseAllocateDataForBlocking
		@SuppressWarnings("unchecked")
		@GetMapping("goods-store-blocking-get-data")
		public @ResponseBody Object getStoreAllocateDataForBlocking(@RequestParam String warehouseId, HttpSession session) {
			logger.info("Method :getStoreAllocateDataForBlocking starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "getStoreAllocateDataForBlocking?warehouseId=" + warehouseId
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getStoreAllocateDataForBlocking ends");
			return resp;
		}
		//Get filter data
		@SuppressWarnings("unchecked")
		@GetMapping("goods-store-blocking-get-filterData")
		public @ResponseBody Object getStoreBlockStockData(@RequestParam String warehouseId,String zoneId,String rackId,
				 String categoryId,String itemName,HttpSession session) {
			logger.info("Method :getStoreBlockStockData starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "getStoreBlockStockData?warehouseId=" + warehouseId
						+ "&zoneId=" + zoneId + "&rackId="+ rackId+ "&categoryId=" + categoryId + "&itemName=" + itemName 
						+"&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getStoreBlockStockData ends");
			return resp;
		}	
		//Save
		@SuppressWarnings("unchecked")
		@PostMapping("goods-store-blocking-submit-blockdetails")
		public @ResponseBody JsonResponse<Object> submitStoreBlockDetails(HttpSession session,
				@RequestBody List<StoreRoomModel> storeRoomModel) {
			logger.info("Method : submitStoreBlockDetails starts");
			System.out.println(storeRoomModel);
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

			for (StoreRoomModel m : storeRoomModel) {
				m.setBlockingBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			try {
				resp = restClient.postForObject(env.getMasterUrl() + "submitStoreBlockDetails", storeRoomModel,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : submitStoreBlockDetails ends");
			return resp;
		}
		
		//Assing Dispatch Request
		@SuppressWarnings("unchecked")
		@GetMapping("goods-store-blocking-assignForDispatch")
		public @ResponseBody JsonResponse<List<StoreRoomModel>> assignStoreDispatchRequest(HttpSession session,
				@RequestParam String assignStatus, String blockId) {

			logger.info("Method : assignStoreDispatchRequest starts");
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
				response = restClient.getForObject(env.getMasterUrl() + "assignStoreDispatchRequest?assignStatus=" + assignStatus + "&blockId=" + blockId+"&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : assignStoreDispatchRequest ends");
			return response;
		}
		
		//Delete block data
		@SuppressWarnings("unchecked")
		@GetMapping(value = "goods-store-blocking-delete")
		public @ResponseBody JsonResponse<Object> deleteStoreBlockdata(@RequestParam String blockId,
				HttpSession session) {
			logger.info("Method : deleteStoreBlockdata starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "deleteStoreBlockdata?blockId=" + blockId +"&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
	 
			System.out.println("delete@" + resp);
			logger.info("Method : deleteStoreBlockdata Ends");
			return resp;
		}
		
		//View Block Data
			@GetMapping("goods-store-blocking-viewblock")
			public @ResponseBody Object viewStoreblock(@RequestParam String rmId, HttpSession session) {

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
					resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStoreblock?rmId=" + rmId + "&org="
							+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("DXCFVGDS"+resp);
				return resp;
				
			}
			
			@GetMapping("goods-store-blocking-get-bindata")
			public @ResponseBody Object viewStoreBinBlockdata(@RequestParam String rmId, HttpSession session) {

				logger.info("Method :viewStoreBinBlockdata starts");
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
					resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStoreBinBlockdata?rmId=" + rmId + "&org="
							+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return resp;
			}
			
}
