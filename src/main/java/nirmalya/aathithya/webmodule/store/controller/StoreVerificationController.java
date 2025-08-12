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
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = { "store/" })
public class StoreVerificationController {
	Logger logger = LoggerFactory.getLogger(StoreVerificationController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;
	
	
	@GetMapping(value = { "store-verification" })
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
					env.getMasterUrl() + "getStoreShiftListsAllocation?org=" + org + "&orgDiv=" + orgDiv,
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
		logger.info("Method : manageStore ends");
		return "store/store-verification";
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "store-verification-getzoneList" })
	public @ResponseBody JsonResponse<Object> storeZoneListData(@RequestParam String id, HttpSession session) {
		logger.info("Method : storeZoneListData starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(
					env.getMasterUrl() + "storeZoneListData?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : storeZoneListData ends");
		return res;
	}
	
	@GetMapping("store-verification-get-data")
	public @ResponseBody Object getStoreOpenListData(@RequestParam String warehouseId, HttpSession session) {

		logger.info("Method :getStoreOpenListData startsssssssssssssssssssssss");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(env.getMasterUrl() + "rest-getStoreOpenListData?warehouseId=" + warehouseId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		// System.out.println("sdefrgthg"+resp);
		logger.info("Method :getStoreOpenListData ends");

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "store-verification-getrackList" })
	public @ResponseBody JsonResponse<Object> storerackListData(@RequestParam String id, HttpSession session) {
		logger.info("Method : storerackListData starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(
					env.getMasterUrl() + "storerackListData?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : storerackListData ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "store-verification-getbinlist" })
	public @ResponseBody JsonResponse<Object> storebinListData(@RequestParam String id, HttpSession session) {
		logger.info("Method : storebinListData starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(
					env.getMasterUrl() + "storebinListData?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : storebinListData ends");
		return res;
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "store-verification-getitemList" })
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
	
	@GetMapping("store-verification-get-alldata")
	public @ResponseBody Object getStoreStockVerificationData(@RequestParam String warehouseId, @RequestParam String zoneId,
			@RequestParam String categoryId, @RequestParam String itemName,@RequestParam String rackId,@RequestParam String binId, HttpSession session) {

		logger.info("Method :getStoreStockVerificationData startsssssssssssssssssssssss");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(env.getMasterUrl() + "rest-getStoreStockVerificationData?warehouseId=" + warehouseId
					+ "&zoneId=" + zoneId + "&categoryId=" + categoryId + "&itemName=" + itemName + "&rackId="
							+ rackId + "&binId=" + binId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		// System.out.println("sdefrgthg"+resp);
		logger.info("Method :getStoreStockVerificationData ends");

		return resp;
	}

	@GetMapping("store-verification-stockdetails-ajax")
	public @ResponseBody Object viewStorestockdetails(@RequestParam String rmId, @RequestParam String allocateid,HttpSession session) {

		logger.info("Method :viewStorestockdetails startsssssssssssssssssssssss");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(
					env.getMasterUrl() + "rest-viewStorestockdetails?rmId=" + rmId + "&allocateid=" + allocateid + "&orgName=" +orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("sdefrgthg"+resp);
		logger.info("Method :viewStorestockdetails ends");

		return resp;
	}
}
