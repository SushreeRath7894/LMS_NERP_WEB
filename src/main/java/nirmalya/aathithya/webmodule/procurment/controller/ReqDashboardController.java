package nirmalya.aathithya.webmodule.procurment.controller;

import java.math.BigInteger;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProcurementStockDetailsModel;
import nirmalya.aathithya.webmodule.procurment.model.ProcurementDashboadModel;
import nirmalya.aathithya.webmodule.procurment.model.ProcurementPoRecptModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "inventory/")
public class ReqDashboardController {

	Logger logger = LoggerFactory.getLogger(ReqDashboardController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("procurement-dashboard")
	public String dahboardIndex(Model model, HttpSession session) {
		logger.info("Method : dahboardIndex starts");

		List<ProcurementDashboadModel> resp = new ArrayList<ProcurementDashboadModel>();

		ProcurementDashboadModel[] inventoryPoModel = restTemplate
				.getForObject(env.getInventoryUrl() + "purchase-analysis", ProcurementDashboadModel[].class);
		resp = Arrays.asList(inventoryPoModel);
		model.addAttribute("purchaseAnalys", resp);

		List<ProcurementDashboadModel> response = new ArrayList<ProcurementDashboadModel>();

		ProcurementDashboadModel[] stock = restTemplate.getForObject(env.getInventoryUrl() + "stock-analysis",
				ProcurementDashboadModel[].class);
		response = Arrays.asList(stock);
		model.addAttribute("stocks", response);

		List<ProcurementDashboadModel> porecptHist = new ArrayList<ProcurementDashboadModel>();

		ProcurementDashboadModel[] poRecptHist = restTemplate
				.getForObject(env.getInventoryUrl() + "poRecptHist-analysis", ProcurementDashboadModel[].class);
		porecptHist = Arrays.asList(poRecptHist);
		model.addAttribute("poRecptHist", porecptHist);
		
		
		
		List<ProcurementDashboadModel> pendingInvoice = new ArrayList<ProcurementDashboadModel>();

		ProcurementDashboadModel[] pendInvoice = restTemplate
				.getForObject(env.getInventoryUrl() + "pendingInvoice-analysis", ProcurementDashboadModel[].class);
		pendingInvoice = Arrays.asList(pendInvoice);
		model.addAttribute("pendInvoice", pendingInvoice);

		

		logger.info("Method : dahboardIndex ends");
		return "procurement/procurement-dashboard";

	}

	@GetMapping("/procurement-dashboard-InitData")
	public @ResponseBody JsonResponse<Object> getMonthlyInitData(HttpSession session) {

		logger.info("Method : getMonthlyInitData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementDashboadModel[] MonthlyInitData = restTemplate.getForObject(
					env.getInventoryUrl() + "getMonthlyInitData?totalVendor", ProcurementDashboadModel[].class);
			// List<DropDownModel> MonthlyInitDataList = Arrays.asList(MonthlyInitData);
			resp.setBody(MonthlyInitData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getMonthlyInitData ends");
		
		return resp;
	}

	@GetMapping("/procurement-dashboard-StockData")
	public @ResponseBody JsonResponse<Object> getReqiInitData(HttpSession session) {

		logger.info("Method : getReqiInitData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementDashboadModel[] ReqiInitData = restTemplate.getForObject(
					env.getInventoryUrl() + "getReqiInitData?totalStocks", ProcurementDashboadModel[].class);
			// List<DropDownModel> MonthlyInitDataList = Arrays.asList(MonthlyInitData);
			resp.setBody(ReqiInitData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getReqiInitData ends");
		return resp;
	}

	@GetMapping("/procurement-dashboard-POData")
	public @ResponseBody JsonResponse<Object> getPoInitData(HttpSession session) {

		logger.info("Method : getPoInitData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementDashboadModel[] PoInitData = restTemplate
					.getForObject(env.getInventoryUrl() + "getPoInitData?totalPo", ProcurementDashboadModel[].class);

			resp.setBody(PoInitData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getPoInitData ends");
		return resp;
	}

	@GetMapping("/procurement-dashboard-RequiData")
	public @ResponseBody JsonResponse<Object> getRequiData(HttpSession session) {

		logger.info("Method : getRequiData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementDashboadModel[] RequiData = restTemplate
					.getForObject(env.getInventoryUrl() + "getRequiData?totalRequi", ProcurementDashboadModel[].class);

			resp.setBody(RequiData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getRequiData ends");
		return resp;
	}

	@GetMapping("/procurement-dashboard-purchaseTranData")
	public @ResponseBody JsonResponse<Object> getAuditTypes(HttpSession session) {

		logger.info("Method : getpurchaseTranData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			DropDownModel[] auditTypes = restTemplate.getForObject(env.getInventoryUrl() + "getpurchaseTranData",
					DropDownModel[].class);
			resp.setBody(auditTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getpurchaseTranData ends");

		return resp;
	}

	@GetMapping("/procurement-dashboard-receivedData")
	public @ResponseBody JsonResponse<Object> getreceivedData(HttpSession session) {

		logger.info("Method : getreceivedData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			DropDownModel[] auditTypes = restTemplate.getForObject(env.getInventoryUrl() + "getreceivedData",
					DropDownModel[].class);
			resp.setBody(auditTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getreceivedData ends");

		return resp;
	}

	@GetMapping("/procurement-dashboard-returnData")
	public @ResponseBody JsonResponse<Object> getreturnData(HttpSession session) {

		logger.info("Method : getreturnData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			DropDownModel[] auditTypes = restTemplate.getForObject(env.getInventoryUrl() + "getreturnData",
					DropDownModel[].class);
			resp.setBody(auditTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getreturnData ends");

		return resp;
	}

	@GetMapping("/procurement-dashboard-inventoryData")
	public @ResponseBody JsonResponse<Object> getinventoryData(HttpSession session) {

		logger.info("Method : getinventoryData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementDashboadModel[] RequiData = restTemplate.getForObject(
					env.getInventoryUrl() + "getinventoryData?totalInventory", ProcurementDashboadModel[].class);

			resp.setBody(RequiData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getinventoryData ends");
		return resp;
	}

	@GetMapping("/procurement-dashboard-StocksInTransit")
	public @ResponseBody JsonResponse<Object> getstocksInTransit(HttpSession session) {

		logger.info("Method : getstocksInTransit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementDashboadModel[] stocksInTransit = restTemplate.getForObject(
					env.getInventoryUrl() + "getstocksInTransit?totalStocksInTransit",
					ProcurementDashboadModel[].class);

			resp.setBody(stocksInTransit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getstocksInTransit ends");
		
		return resp;
	}

	@GetMapping("/procurement-dashboard-requisitionData")
	public @ResponseBody JsonResponse<Object> getrequisitionData(HttpSession session) {

		logger.info("Method : getrequisitionData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementStockDetailsModel[] auditTypes = restTemplate
					.getForObject(env.getInventoryUrl() + "getrequisitionData", ProcurementStockDetailsModel[].class);
			List<ProcurementStockDetailsModel> dataList = Arrays.asList(auditTypes);
			List<Double> reqList = new ArrayList<Double>();
			List<Double> stockTList = new ArrayList<Double>();
			List<Double> stockHList = new ArrayList<Double>();

			List<String> monthList = new ArrayList<String>();

			for (ProcurementStockDetailsModel m : auditTypes) {

				reqList.add(m.getRequisition());
				stockTList.add(m.getStockTransit());
				stockHList.add(m.getStockHand());
				monthList.add(m.getMonth());

			}

			dataList.get(0).setMonthList(monthList);

			dataList.get(0).setReqList(reqList);
			dataList.get(0).setStockTList(stockTList);
			dataList.get(0).setStockHList(stockHList);

			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getrequisitionData ends");

		return resp;
	}

	@GetMapping("/procurement-dashboard-poRecptHistoryData")
	public @ResponseBody JsonResponse<Object> getpoRecptHistoryData(HttpSession session) {

		logger.info("Method : getpoRecptHistoryData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementDashboadModel[] poRecpt = restTemplate.getForObject(
					env.getInventoryUrl() + "getpoRecptHistoryData?totalImmediate", ProcurementDashboadModel[].class);

			resp.setBody(poRecpt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		logger.info("Method : getpoRecptHistoryData ends");
		return resp;
	}

	@GetMapping("/procurement-dashboard-poRecptData")
	public @ResponseBody JsonResponse<Object> getpoRecptData(HttpSession session) {

		logger.info("Method : getpoRecptData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			ProcurementPoRecptModel[] auditTypes = restTemplate
					.getForObject(env.getInventoryUrl() + "getpoRecptData", ProcurementPoRecptModel[].class);
			List<ProcurementPoRecptModel> dataList = Arrays.asList(auditTypes);
			List<BigInteger> imdList = new ArrayList<BigInteger>();
			List<BigInteger> generalList = new ArrayList<BigInteger>();
			List<BigInteger> highList = new ArrayList<BigInteger>();

			List<String> monthList = new ArrayList<String>();

			for (ProcurementPoRecptModel m : auditTypes) {

				imdList.add(m.getImmediate());
				generalList.add(m.getGeneral());
				highList.add(m.getHigh());
				monthList.add(m.getMonth());

			}

			dataList.get(0).setMonthList(monthList);

			dataList.get(0).setImdList(imdList);
			dataList.get(0).setGeneralList(generalList);
			dataList.get(0).setHighList(highList);

			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getpoRecptData ends");

		return resp;
	}

}
