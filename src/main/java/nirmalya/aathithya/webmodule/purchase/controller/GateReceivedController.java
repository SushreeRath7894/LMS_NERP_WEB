package nirmalya.aathithya.webmodule.purchase.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;

@Controller

@RequestMapping(value = { "purchase/" })

public class GateReceivedController {

	Logger logger = LoggerFactory.getLogger(GateReceivedController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "gate-received" })

	public String awaitingQaRequest(Model model, HttpSession session) {
		logger.info("Method :awaitingQaRequest starts");

		logger.info("Method : awaitingQaRequest ends");

		return "purchase/gate-received";
	}

	// View Gate Received Data.

	@SuppressWarnings("unchecked")

	@GetMapping("gate-received-view")
	public @ResponseBody Object gateReceivedDataView(@RequestParam String id, String pageno, HttpSession session) {

		logger.info("Method :gateReceivedDataView starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-gateReceivedDataView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :gateReceivedDataView ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("gate-received-detls")
	public @ResponseBody Object gateReceivedDtls(@RequestParam String id, @RequestParam String po,
			HttpSession session) {

		logger.info("Method :gateReceivedDtls starts");
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
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-gateReceivedDtls?id=" + id + "&po=" + po
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// logger.info("Child Data>>>-----" + resp);
		// logger.info("Method :gateReceivedDtls ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "gate-received-GRNdata" })
	public @ResponseBody Object getGRNdata(@RequestParam String sku, String gatePass, String challanNo,
			String challanDt, HttpSession session) {
		logger.info("Method : getGRNdata starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "getGRNdata?sku=" + sku + "&gatePass=" + gatePass + "&org=" + orgName
							+ "&orgDiv=" + orgDivision + "&challanNo=" + challanNo + "&challanDt=" + challanDt,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getGRNdata ends");
		// logger.info("edit@@@@@@@@" + productList);
		return resp;
	}

	// Delete Entry Data.

	@SuppressWarnings("unchecked")
	@PostMapping("gate-received-delete")
	public @ResponseBody JsonResponse<Object> deleteGatepassEntry(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteGatepassEntry function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

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
			res = restTemplate.getForObject(env.getGatepassUrl() + "deleteGatepassEntry?id=" + id + "&org=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteGatepassEntry function Ends");

		// logger.info("RESPPPPPPP" + res);
		return res;
	}

	// Approve Gate In.

	@SuppressWarnings("unchecked")
	@GetMapping("approveGateEntry")
	public @ResponseBody JsonResponse<DropDownModel> approveGatepassEntry(HttpSession session,
			@RequestParam String approveStatus, String getPassEntryId) {

		logger.info("Method : approveGatepassEntry starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate.getForObject(
					env.getGatepassUrl() + "approveGatepassEntry?approveStatus=" + approveStatus + "&getPassEntryId="
							+ getPassEntryId + "&org=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		System.out.println("response=====" + response);
		logger.info("Method : approveGatepassEntry ends");
		return response;
	}

	// Search

	@SuppressWarnings("unchecked")

	@GetMapping("gate-received-view-search")
	public @ResponseBody Object gateReceivedDataViewSearch(@RequestParam String id, String searchValue,
			HttpSession session) {

		logger.info("Method :gateReceivedDataViewSearch starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-gateReceivedDataViewSearch?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&searchValue=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :gateReceivedDataViewSearch ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------------------
	 * ----------------------------------------------------ORI - FOOD GATE RECEIVED
	 * -----------------------------------------------------------------------------
	 * --------------------------------------------------------------
	 * -----------------------------------------------------------------------------
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------------------
	 */

	@GetMapping(value = { "gate-received-of" })

	public String gateReceivedForOf(Model model, HttpSession session) {
		logger.info("Method :gateReceivedForOf starts");

		logger.info("Method : gateReceivedForOf ends");

		return "purchase/gate-received-of";
	}

	// View Gate Received Data.

	@SuppressWarnings("unchecked")

	@GetMapping("gate-received-of-view")
	public @ResponseBody Object gateReceivedDataViewOf(@RequestParam String id, String pageno, HttpSession session) {

		logger.info("Method :gateReceivedDataView starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-gateReceivedDataView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :gateReceivedDataView ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("gate-received-of-detls")
	public @ResponseBody JsonResponse<Object> gateReceivedDtlsOf(@RequestParam String id, @RequestParam String po,
			HttpSession session) {

		logger.info("Method :gateReceivedDtls starts");
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
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-Of-gateReceivedDtls?id=" + id + "&po=" + po
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// logger.info("Child Data>>>-----" + resp);
		// logger.info("Method :gateReceivedDtls ends");
		System.out.println("resp>>>>>>>>>>>>" + resp);
		return resp;
	}
	
	// Ori-Food

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "gate-received-of-GRNdata" })
	public @ResponseBody Object getGRNdataOf(@RequestParam String sku, String gatePass, String challanNo,
			String challanDt, String vendorId, HttpSession session) {
		logger.info("Method : getGRNdataOf starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "getGRNdataOf?sku=" + sku + "&gatePass=" + gatePass + "&org=" + orgName
							+ "&orgDiv=" + orgDivision + "&challanNo=" + challanNo + "&challanDt=" + challanDt + "&vendorId=" + vendorId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getGRNdataOf ends");
		// logger.info("edit@@@@@@@@" + productList);
		return resp;
	}

	// Delete Entry Data.

	@SuppressWarnings("unchecked")
	@PostMapping("gate-received-of-delete")
	public @ResponseBody JsonResponse<Object> deleteGatepassEntryOf(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteGatepassEntry function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

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
			res = restTemplate.getForObject(env.getGatepassUrl() + "deleteGatepassEntry?id=" + id + "&org=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteGatepassEntry function Ends");

		// logger.info("RESPPPPPPP" + res);
		return res;
	}

	// Approve Gate In.

	@SuppressWarnings("unchecked")
	@GetMapping("approveGateEntryForOf")
	public @ResponseBody JsonResponse<DropDownModel> approveGatepassEntryOf(HttpSession session,
			@RequestParam String approveStatus, String getPassEntryId) {

		logger.info("Method : approveGatepassEntry starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate.getForObject(
					env.getGatepassUrl() + "approveGatepassEntry?approveStatus=" + approveStatus + "&getPassEntryId="
							+ getPassEntryId + "&org=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		System.out.println("response=====" + response);
		logger.info("Method : approveGatepassEntry ends");
		return response;
	}

	// Search

	@SuppressWarnings("unchecked")

	@GetMapping("gate-received-of-view-search")
	public @ResponseBody Object gateReceivedDataViewSearchOf(@RequestParam String id, String searchValue,
			HttpSession session) {

		logger.info("Method :gateReceivedDataViewSearch starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-gateReceivedDataViewSearch?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&searchValue=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :gateReceivedDataViewSearch ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

	// Qa Request From gate Received For Ori Food

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "gate-received-of-QaRequest" })
	public @ResponseBody Object qaRequestForOf(@RequestParam String sku, String gatePass, String challanNo,
			String challanDt, HttpSession session) {
		logger.info("Method : qaRequestForOf starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-qaRequestForOf?sku=" + sku + "&gatePass="
					+ gatePass + "&org=" + orgName + "&orgDiv=" + orgDivision + "&challanNo=" + challanNo
					+ "&challanDt=" + challanDt, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : qaRequestForOf ends");
		System.out.println("resp>>>>>>>>>>>" + resp);
		return resp;
	}

	// Filter data by from & to date Api
	@SuppressWarnings("unchecked")
	@GetMapping("gate-received-filtered-view")
	public @ResponseBody Object gateReceivedFilteredDataView(@RequestParam String id, String fromDate,
			String toDate, String searchData, HttpSession session) {

		logger.info("Method :gateReceived_Filtered_Data_View starts");
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

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-gateReceived-Filtered-DataView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&searchData=" + searchData, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :gateReceived_Filtered_Data_View ends");

		return resp;
	}
	
	
	

}
