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
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.store.model.StoreRoomModel;
import nirmalya.aathithya.webmodule.warehouse.controller.WarehouseAllocationController;
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller
@RequestMapping(value = { "store/" })
public class StoreAllocationController {
	Logger logger = LoggerFactory.getLogger(StoreAllocationController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	StoreAllocationController storeAllocationController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/store-allocation" })
	public String manageWareHouse(Model model, HttpSession session) {
		logger.info("Method : manageStore starts");

		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
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
			DropDownModel[] manufacturePlace = restClient.getForObject(
					env.getMasterUrl() + "getStoreManufacturePlaceLists?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> ManufacturePlaceLists = Arrays.asList(manufacturePlace);

			model.addAttribute("ManufacturePlaceLists", ManufacturePlaceLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] batchNoLists = restClient.getForObject(
					env.getMasterUrl() + "getStoreBatchNoLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> BatchNoLists = Arrays.asList(batchNoLists);

			model.addAttribute("BatchNoLists", BatchNoLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] shift = restClient.getForObject(
					env.getMasterUrl() + "getStoreShiftListsAllocation?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] line = restClient.getForObject(
					env.getMasterUrl() + "getStoreLineLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> lineLists = Arrays.asList(line);

			model.addAttribute("lineLists", lineLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] packing = restClient.getForObject(
					env.getMasterUrl() + "getStorePackingSiteLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
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
		logger.info("Method : manageStore ends");
		return "store/store-allocation";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "store-allocation-getzoneList" })
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
	@GetMapping(value = { "store-allocation-getrackList" })
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
		@GetMapping(value = { "store-allocation-getitemList" })
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
		
		//getWarehouseData
		@SuppressWarnings("unchecked")
		@GetMapping("store-allocation-get-data")
		public @ResponseBody Object getStoreData(@RequestParam String warehouseId, HttpSession session) {

			logger.info("Method :getStoreData starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "rest-getStoreData?warehouseId=" + warehouseId
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getStoreData ends");
			return resp;
		}
		
		//warehouse search Data
		@SuppressWarnings("unchecked")
		@GetMapping("store-allocation-getFilterWarehouseData")
		public @ResponseBody Object storeGetAllData(@RequestParam String warehouseId,  String zoneId,
				 String rackId, String categoryId,String itemName,HttpSession session) {

			logger.info("Method :storeGetAllData starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "storeGetAllData?warehouseId=" + warehouseId
						+ "&zoneId=" + zoneId + "&rackId=" + rackId + "&categoryId=" + categoryId + "&itemName=" + itemName
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :storeGetAllData ends");
			return resp;
		}
		
		//saveAllocation
		@SuppressWarnings("unchecked")
		@PostMapping("store-allocation-save-allocation")
		public @ResponseBody JsonResponse<Object> saveStoreAllocation(HttpSession session,
				@RequestBody List<StoreRoomModel> storeRoomModel) {
	 
			logger.info("Method : saveStoreAllocation starts");
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
				resp = restClient.postForObject(env.getMasterUrl() + "saveStoreAllocation", storeRoomModel,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : saveStoreAllocation ends");
			System.out.println("azsxdcfvbgnhjmhgfdsazs"+resp);
			return resp;
		}

		//viewAllocationData
		@SuppressWarnings("unchecked")
		@GetMapping("store-allocation-through-ajax")
		public @ResponseBody Object viewStoreAllocationData(HttpSession session) {
			logger.info("Method :viewStoreAllocationData starts");
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
						env.getMasterUrl() + "viewStoreAllocationData?orgName=" + orgName + "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewStoreAllocationData ends");
			return resp;
		}

		//viewBinAllocationdata
		
		@GetMapping("store-allocation-get-bindata")
		public @ResponseBody Object viewStoreBinAllocationdata(@RequestParam String rmId, HttpSession session) {

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
				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStoreBinAllocationdata?rmId=" + rmId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		}
		
		//View Bin Data
		@GetMapping("store-allocation-get-binconfigdata")
		public @ResponseBody Object viewStorebinconfigdata(@RequestParam String rmId, HttpSession session) {

			logger.info("Method :viewStorebinconfigdata starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStorebinconfigdata?rmId=" + rmId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("DXCFVGDS"+resp);
			return resp;
			
		}
		
		//deleteAllocationdata
				@GetMapping("store-allocation-delete")
				public @ResponseBody Object deleteStoreAllocationdata(@RequestParam String id, HttpSession session) {

					logger.info("Method :deleteStoreAllocationdata starts");
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
						resp = restClient.getForObject(env.getMasterUrl() + "rest-deleteStoreAllocationdata?allocId=" + id + "&org="
								+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return resp;
				}
				
				//Approvalof data
				@GetMapping("store-allocation-approve")
				public @ResponseBody Object approveStoreAllocationdata(@RequestParam String id, HttpSession session) {

					logger.info("Method :approveStoreAllocationdata starts");
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
						resp = restClient.getForObject(env.getMasterUrl() + "rest-approveStoreAllocationdata?allocId=" + id + "&org="
								+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return resp;
				}
				
				//Hold data
				@GetMapping("store-allocation-hold")
				public @ResponseBody Object holdStoreAllocationdata(@RequestParam String rmId, HttpSession session) {

					logger.info("Method :holdStoreAllocationdata starts");
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
						resp = restClient.getForObject(env.getMasterUrl() + "rest-holdStoreAllocationdata?rmId=" + rmId + "&org="
								+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return resp;
				}
				
				//Release data
				@GetMapping("store-allocation-release")
				public @ResponseBody Object releaseStoreAllocationdata(@RequestParam String rmId, HttpSession session) {

					logger.info("Method :holdStoreAllocationdata starts");
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
						resp = restClient.getForObject(env.getMasterUrl() + "rest-releaseStoreAllocationdata?rmId=" + rmId + "&org="
								+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return resp;
				}


}
