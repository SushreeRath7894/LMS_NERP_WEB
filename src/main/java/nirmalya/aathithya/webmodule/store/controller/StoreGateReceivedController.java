package nirmalya.aathithya.webmodule.store.controller;

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
import nirmalya.aathithya.webmodule.purchase.controller.GateReceivedController;

@Controller

@RequestMapping(value = { "store/" })
public class StoreGateReceivedController {
	
	Logger logger = LoggerFactory.getLogger(GateReceivedController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "store-gatereceived" })
	
	public String storeGateReceived(Model model, HttpSession session) {
		logger.info("Method :storeGateReceived starts");

		logger.info("Method : storeGateReceived ends");

		return "store/store-gatereceived";
	}
	
	// View Gate Received.
	
	@SuppressWarnings("unchecked")

	@GetMapping("store-gatereceived-view")
	public @ResponseBody Object gateReceivedDataView(@RequestParam String id, String pageno ,HttpSession session) {

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
		
		//logger.info(">>>-----"+resp);

		return resp;
	}
	
	// Received Details
	
	@SuppressWarnings("unchecked")
	@GetMapping("store-gatereceived-detls")
	public @ResponseBody Object gateReceivedDtls(@RequestParam String id,@RequestParam String po, HttpSession session) {
		
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
			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-gateReceivedDtls?id=" + id +"&po=" + po +"&orgName=" +orgName+"&orgDivision=" +orgDivision ,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//logger.info("Child Data>>>-----" + resp);
		//logger.info("Method :gateReceivedDtls ends");

		return resp;
	}
	
	// Grn Data.
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "store-gatereceived-GRNdata" })
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
	
	
	// Search

		@SuppressWarnings("unchecked")

		@GetMapping("store-gatereceived-view-search")
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
		
		
		// Approve Gate In.

		@SuppressWarnings("unchecked")
		@GetMapping("store-gatereceived-approveGateEntry")
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

}
