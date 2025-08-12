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
@RequestMapping(value = { "warehouse/" })
public class WarehouseStockVerificationController {
	Logger logger = LoggerFactory.getLogger(WarehouseStockVerificationController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;
	/*
	 * @GetMapping("stock-verification") public String StockVerificationPage(Model
	 * model, HttpSession session) {
	 * logger.info("Method : StockVerificationPage starts");
	 * model.addAttribute("pageName", "Stock Verification Page Coming Soon");
	 * logger.info("Method : StockVerificationPage ends"); return
	 * "recruitment/view-action";
	 * 
	 * }
	 */
	
	
	@GetMapping(value = { "stock-verification" })
	public String manageWareHouse(Model model, HttpSession session) {
		logger.info("Method : manageWareHouse starts");

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
					env.getMasterUrl() + "getWarehouseLocationList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(location);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] category = restClient.getForObject(
					env.getMasterUrl() + "getWarehouseItemCategoryList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> categorylist = Arrays.asList(category);
			model.addAttribute("categorylist", categorylist);
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
					env.getMasterUrl() + "getShiftListsAllocation?org=" + org + "&orgDiv=" + orgDiv,
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
		logger.info("Method : manageWareHouse ends");
		return "warehouse/stock-verification";
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "stock-verification-getzoneList" })
	public @ResponseBody JsonResponse<Object> zoneListData(@RequestParam String id, HttpSession session) {
		logger.info("Method : zoneListData starts" + id);
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
					env.getMasterUrl() + "zoneListData?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method : zoneListData ends");
		return res;
	}
	
	@GetMapping("stock-verification-get-data")
	public @ResponseBody Object getWarehouseOpenListData(@RequestParam String warehouseId, HttpSession session) {

		logger.info("Method :getWarehouseOpenListData startsssssssssssssssssssssss");
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

			resp = restClient.getForObject(env.getMasterUrl() + "rest-getWarehouseOpenListData?warehouseId=" + warehouseId
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
		logger.info("Method :getWarehouseOpenListData ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "stock-verification-getrackList" })
	public @ResponseBody JsonResponse<Object> rackListData(@RequestParam String id, HttpSession session) {
		logger.info("Method : rackListData starts" + id);
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
					env.getMasterUrl() + "rackListData?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method : rackListData ends");
		return res;
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "stock-verification-getbinlist" })
	public @ResponseBody JsonResponse<Object> binListData(@RequestParam String id, HttpSession session) {
		logger.info("Method : binListData starts" + id);
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
					env.getMasterUrl() + "binListData?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method : binListData ends");
		return res;
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "stock-verification-getitemList" })
	public @ResponseBody JsonResponse<Object> getitemList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getitemList starts" + id);
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
					env.getMasterUrl() + "getitemList?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method : getitemList ends");
		return res;
	}

	@GetMapping("stock-verification-get-alldata")
	public @ResponseBody Object getStockVerificationData(@RequestParam String warehouseId, @RequestParam String zoneId,
			@RequestParam String categoryId, @RequestParam String itemName,@RequestParam String rackId,@RequestParam String binId, HttpSession session) {

		logger.info("Method :getStockVerificationData startsssssssssssssssssssssss");
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

			resp = restClient.getForObject(env.getMasterUrl() + "rest-getStockVerificationData?warehouseId=" + warehouseId
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
		logger.info("Method :getStockVerificationData ends");

		return resp;
	}


	@GetMapping("stock-verification-stockdetails-ajax")
	public @ResponseBody Object viewstockdetails(@RequestParam String rmId, @RequestParam String allocateid,HttpSession session) {

		logger.info("Method :viewstockdetails startsssssssssssssssssssssss");
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
					env.getMasterUrl() + "rest-viewstockdetails?rmId=" + rmId + "&allocateid=" + allocateid + "&orgName=" +orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :viewstockdetails ends");

		return resp;
	}

}
