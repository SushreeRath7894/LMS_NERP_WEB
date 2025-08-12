package nirmalya.aathithya.webmodule.gst.controller;

import org.apache.commons.io.IOUtils;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.gst.model.GstReportWebModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/gstreturn")
public class GstSalesReturnController {

	Logger logger = LoggerFactory.getLogger(GstSalesReturnController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("prepare-gst-list")
	public String viewDemoCandidate(Model model, HttpSession session) {

		logger.info("Method : viewDemoCandidate starts");

		logger.info("Method : viewDemoCandidate ends");

		return "gstreturn/prepare-gst-list";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("prepare-gst-list-sales")
	public @ResponseBody JsonResponse<Object> viewGstSalesList(@RequestParam String month, @RequestParam String year,
			HttpSession session) {
		logger.info("Method : viewGstSalesList starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String org = (String) session.getAttribute("ORGANIZATION");
		String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		try {
			resp = restTemplate.getForObject(env.getGstUrl() + "getGstSalesInvoiceData?month=" + month + "&year=" + year
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewGstSalesList ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("prepare-gst-list-purchase")
	public @ResponseBody JsonResponse<Object> viewGstPurchaseList(@RequestParam String month, @RequestParam String year,
			HttpSession session) {
		logger.info("Method : viewGstPurchaseList starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String org = (String) session.getAttribute("ORGANIZATION");
		String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		try {
			resp = restTemplate.getForObject(env.getGstUrl() + "getPurchasesasperRecord?month=" + month + "&year="
					+ year + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewGstPurchaseList ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("prepare-gst-list-sales-hsn")
	public @ResponseBody JsonResponse<Object> viewHsnSales(@RequestParam String month, @RequestParam String year,
			HttpSession session) {
		logger.info("Method : viewHsnSales starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String org = (String) session.getAttribute("ORGANIZATION");
		String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		try {
			resp = restTemplate.getForObject(env.getGstUrl() + "sales-hsn?month=" + month + "&year=" + year + "&org="
					+ org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewHsnSales ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("prepare-gst-list-purchase-hsn")
	public @ResponseBody JsonResponse<Object> viewHsnPurchase(@RequestParam String month, @RequestParam String year,
			HttpSession session) {
		logger.info("Method : viewHsnPurchase starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String org = (String) session.getAttribute("ORGANIZATION");
		String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		try {
			resp = restTemplate.getForObject(env.getGstUrl() + "purchase-hsn?month=" + month + "&year=" + year + "&org="
					+ org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewHsnPurchase ends");

		return resp;
	}

	// for 3B

	@SuppressWarnings("unchecked")
	@GetMapping("prepare-gst-list-3B")
	public @ResponseBody JsonResponse<Object> viewGst3BList(@RequestParam String month, @RequestParam String year,
			HttpSession session) {
		logger.info("Method : viewGst3BList starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String org = (String) session.getAttribute("ORGANIZATION");
		String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		try {
			resp = restTemplate.getForObject(env.getGstUrl() + "viewGst3BList?month=" + month + "&year=" + year
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewGst3BList ends" + resp);

		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/gst-3B-view-pdf-report" })
	public void gst3BPdf(HttpServletResponse response, Model model, @RequestParam String years, String months) {
		logger.info("Method :gst3BPdf starts");

		// System.out.println("fromDate===>>" + fromDate);
		// System.out.println("toDate===>>" + toDate);

		GstReportWebModel gst = new GstReportWebModel();
		JsonResponse<GstReportWebModel> jsonResponse = new JsonResponse<GstReportWebModel>();

		JsonResponse<List<GstReportWebModel>> report = new JsonResponse<List<GstReportWebModel>>();

		try {
			report = restTemplate.getForObject(env.getGstUrl() + "gst3BPdfReport?years=" + years + "&months=" + months,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("responseReport=>>>>>>" + report);
		if (report.getBody().isEmpty()) {

			ObjectMapper mapper = new ObjectMapper();

			List<GstReportWebModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<GstReportWebModel>>() {
					});

			jsonResponse.setBody(gst);

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("gstCard", reportcard);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=gst-stmt");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("gstreturn/gst-3B-view-Pdf-noData", data);
				InputStream in = new FileInputStream(file);
				fileData = IOUtils.toByteArray(in);
				response.setContentLength(fileData.length);
				response.getOutputStream().write(fileData);
				response.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else {
			ObjectMapper mapper = new ObjectMapper();

			List<GstReportWebModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<GstReportWebModel>>() {
					});

			jsonResponse.setBody(gst);

			String fromToDate = years + " TO " + months;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("gstCard", reportcard);
			data.put("fromToDate", fromToDate);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=gst-3B-stmt");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("gstreturn/gst-3B-view-Pdf", data);
				InputStream in = new FileInputStream(file);
				fileData = IOUtils.toByteArray(in);
				response.setContentLength(fileData.length);
				response.getOutputStream().write(fileData);
				response.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("prepare-gst-list-3B-hsn")
	public @ResponseBody JsonResponse<Object> viewHsn3B(@RequestParam String month, @RequestParam String year,
			HttpSession session) {
		logger.info("Method : viewHsn3B starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String org = (String) session.getAttribute("ORGANIZATION");
		String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		try {
			resp = restTemplate.getForObject(env.getGstUrl() + "3B-hsn?month=" + month + "&year=" + year + "&org="
					+ org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewHsn3B ends");

		return resp;
	}

}
