package nirmalya.aathithya.webmodule.warehouse.controller;

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
import nirmalya.aathithya.webmodule.warehouse.model.WirehouseRoomModel;

@Controller

@RequestMapping(value = { "warehouse/" })
public class WarehouseBatchCreationController {
	
	Logger logger = LoggerFactory.getLogger(WarehouseBatchCreationController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "warehouse-batch-creation" })
	public String batchCreationPage(Model model, HttpSession session) {
		logger.info("Method :batchCreationPage starts");

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
			DropDownModel[] batchNoLists = restClient.getForObject(
					env.getMasterUrl() + "getBatchNoLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> BatchNoLists = Arrays.asList(batchNoLists);

			model.addAttribute("BatchNoLists", BatchNoLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] shift = restClient.getForObject(
					env.getMasterUrl() + "getShiftListsAllocation?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
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
			DropDownModel[] manufacturePlace = restClient.getForObject(
					env.getMasterUrl() + "getManufacturePlaceLists?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> ManufacturePlaceLists = Arrays.asList(manufacturePlace);

			model.addAttribute("ManufacturePlaceLists", ManufacturePlaceLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : batchCreationPage ends");
		return "warehouse/warehouse-batch-creation";
	}
	//saveBatchCreation
		@SuppressWarnings("unchecked")
		@PostMapping("warehouse-batch-creation-save")
		public @ResponseBody JsonResponse<Object> saveBatchData(HttpSession session,
				@RequestBody List<WirehouseRoomModel> wirehouseRoomModel) {
	 
			logger.info("Method : saveBatchData starts");
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
				m.setCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			try {
				resp = restClient.postForObject(env.getMasterUrl() + "saveBatchData", wirehouseRoomModel,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : saveBatchData ends");
			System.out.println("saveBatchData"+resp);
			return resp;
		}
		
		//View Batch Type
		@SuppressWarnings("unchecked")
		@GetMapping("warehouse-batch-creation-view")
		public @ResponseBody Object viewBatchData(HttpSession session) {
			logger.info("Method :viewBatchData starts");
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
						env.getMasterUrl() + "viewBatchData?orgName=" + orgName + "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewBatchData ends");
			return resp;
		}
		//Edit Batch data
		@GetMapping("warehouse-batch-creation-edit-batch")
		public @ResponseBody Object editBatchData(@RequestParam String batchId, HttpSession session) {

			logger.info("Method :editBatchData starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "rest-editBatchData?batchId=" + batchId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("editBatchData"+resp);
			return resp;
			
		}
		
		//Delete Batch data
		@SuppressWarnings("unchecked")
		@GetMapping(value = "warehouse-batch-creation-delete")
		public @ResponseBody JsonResponse<Object> deleteBatchdata(@RequestParam String batchId,
				HttpSession session) {
			logger.info("Method : deleteBatchdata starts");
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
				resp = restClient.getForObject(env.getMasterUrl() + "deleteBatchdata?batchId=" + batchId +"&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
	 
			System.out.println("delete@" + resp);
			logger.info("Method : deleteBatchdata Ends");
			return resp;
		}
		
		//Approve Batch
		@SuppressWarnings("unchecked")
		@GetMapping("warehouse-batch-creation-approve")
		public @ResponseBody JsonResponse<List<WirehouseRoomModel>> approveBatch(HttpSession session,
				@RequestParam String approveStatus, String batchId) {

			logger.info("Method : approveBatch starts");
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
				response = restClient.getForObject(env.getMasterUrl() + "approveBatch?approveStatus=" + approveStatus + "&batchId=" + batchId+"&orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : approveBatch ends");
			return response;
		}
}
