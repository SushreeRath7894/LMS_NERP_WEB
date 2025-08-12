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
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller
@RequestMapping(value = { "store/" })
public class StoreStackingController {
	Logger logger = LoggerFactory.getLogger(StoreStackingController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("store-stacking")
	public String goodsDispatchPage(Model model, HttpSession session) {
		logger.info("Method : stackingPage starts");

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
		logger.info("Method : stackingPage ends");
		return "store/store-stacking";
	}
	
	//View Aggrid requested dispatch goods
	@SuppressWarnings("unchecked")
	@GetMapping("store-stacking-through-ajax")
	public @ResponseBody Object viewStoreRequestedStacking(HttpSession session) {
		logger.info("Method :viewStoreRequestedStacking starts");
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
					env.getMasterUrl() + "rest-viewStoreRequestedStacking?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewStoreRequestedStacking ends");
		return resp;
	}
	

	//getStackingData
		@SuppressWarnings("unchecked")
		@GetMapping("store-stacking-getAllocationDataforStacking")
		public @ResponseBody Object getStoreAllocationDataforStacking(@RequestParam String warehouse,String allocate,HttpSession session) {
			logger.info("Method :getStoreAllocationDataforStacking starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "getStoreAllocationDataforStacking?warehouse=" + warehouse
						+ "&allocate=" + allocate + "&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getStoreAllocationDataforStacking ends");
			return resp;
		}
		
		//get Itemlist
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "store-stacking-getitemList" })
		public @ResponseBody JsonResponse<Object> getStoreitemList(@RequestParam String id, HttpSession session) {
			logger.info("Method : getStoreitemList starts" + id);
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
			logger.info("Method : getStoreitemList ends");
			return res;
		}
		
		//Save
		
		@SuppressWarnings("unchecked")
		@GetMapping("store-stacking-submit-stacking")
		public @ResponseBody JsonResponse<List<StoreRoomModel>> modifyStoreStackingData(HttpSession session,
				@RequestParam String warehouseId, String allocateId,String bdata) {

			logger.info("Method : modifyStoreStackingData starts");
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
				response = restClient.getForObject(env.getMasterUrl() + "modifyStoreStackingData?warehouseId=" + warehouseId + "&allocateId=" + allocateId+  "&bdata=" + bdata+"&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : modifyStoreStackingData ends");
			return response;
		}	
		
		//viewBinAllocationdata
		
		@GetMapping("store-stacking-viewstock")
		public @ResponseBody Object viewStoreBinAllocationdata(@RequestParam String rmId,String allocateId, HttpSession session) {

			logger.info("Method :viewStoreBinAllocationdata starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStackBinAllocationdata?rmId=" + rmId + "&allocateId=" + allocateId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		}
		
		//Get Zone List
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "store-stacking-getzoneList" })
		public @ResponseBody JsonResponse<Object> getstorezoneList(@RequestParam String id, HttpSession session) {
			logger.info("Method : getstorezoneList starts");
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
			logger.info("Method : getstorezoneList ends");
			return res;
		}
		// Get Rack List
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "store-stacking-getrackList" })
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
		
		// Get Bin List
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "store-stacking-getbinList" })
		public @ResponseBody JsonResponse<Object> binStoreLists(@RequestParam String id,String binId, HttpSession session) {
			logger.info("Method : binStoreLists starts");
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
				res = restClient.getForObject(env.getMasterUrl() + "binStoreLists?rackId=" + id + "&binId=" + binId + "&orgName=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : binStoreLists ends");
			return res;
		}
		
		@GetMapping("store-stacking-get-editdata")
		public @ResponseBody Object editStoreData(@RequestParam String rmId, HttpSession session) {

			logger.info("Method :editStoreData starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "rest-editStoreData?rmId=" + rmId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		}
		
		
		
		//Modify Bin 
		@SuppressWarnings("unchecked")
		@PostMapping("store-stacking-save-stackdata")
		public @ResponseBody JsonResponse<Object> saveStoreStackdata(HttpSession session,
				@RequestBody List<StoreRoomModel> storeRoomModel) {
	 
			logger.info("Method : saveStoreStackdata starts");
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
				m.setCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			try {
				resp = restClient.postForObject(env.getMasterUrl() + "saveStoreStackdata", storeRoomModel,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : saveStoreStackdata ends");
			System.out.println("azsxdcfvbgnhjmhgfdsazs"+resp);
			return resp;
		}

		
}
