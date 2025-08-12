package nirmalya.aathithya.webmodule.purchase.controller;

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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller

@RequestMapping(value = { "purchase/" })
public class ShortageSurplusGoodsController {
	
	Logger logger = LoggerFactory.getLogger(ShortageSurplusGoodsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "shortage-surplus-goods" })

	public String shortageSurplusGoods(Model model, HttpSession session) {
		logger.info("Method :shortageSurplusGoods starts");

		logger.info("Method : shortageSurplusGoods ends");

		return "purchase/shortage-surplus-goods";
	}
	
	
	// View Shortage/Surplus Goods Data.

	@SuppressWarnings("unchecked")

	@GetMapping("shortage-surplus-goods-view")
	public @ResponseBody Object shortageSurplusGoodsDataView(@RequestParam String pageno, HttpSession session) {

		logger.info("Method :shortageSurplusGoodsDataView starts");
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
			

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-shortageSurplusGoodsDataView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :shortageSurplusGoodsDataView ends");

		return resp;
	}
	
	// Add Debit Note.

		@SuppressWarnings("unchecked")

		@GetMapping("shortage-surplus-goods-add-debitNote")
		public @ResponseBody Object shortageSurplusGoodsDataAddDebitNote(@RequestParam String id, HttpSession session) {

			logger.info("Method :shortageSurplusGoodsDataAddDebitNote starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				

				resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-shortageSurplusGoodsDataAddDebitNote?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&id=" + id + "&userId=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :shortageSurplusGoodsDataAddDebitNote ends");

			return resp;
		}

}
