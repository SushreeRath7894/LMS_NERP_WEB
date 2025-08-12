package nirmalya.aathithya.webmodule.master.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
import org.springframework.web.multipart.MultipartFile;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProcurementMasterModel;

@Controller
@RequestMapping(value = "configuration")
public class ProcurementMasterController {

	Logger logger = LoggerFactory.getLogger(ProcurementMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/view-procurement")
	public String displayPage(Model model, HttpSession session) {
		logger.info("Method : procurement starts");

		logger.info("Method : procurement ends");
		return "master/view-procurement";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-measurement-type")
	public @ResponseBody JsonResponse<Object> addMeasureType(@RequestBody ProcurementMasterModel brand,
			HttpSession session) {
		logger.info("Method : addMeasureType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		brand.setMeasurementCreatedBy(userId);
		brand.setOrg(org);
		brand.setOrgDiv(orgDiv);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addMeasureType", brand, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addMeasureType starts");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-measurement-type")
	public @ResponseBody List<ProcurementMasterModel> viewMeasureType(HttpSession session) {
		logger.info("Method : viewMeasureType starts");

		JsonResponse<List<ProcurementMasterModel>> resp = new JsonResponse<List<ProcurementMasterModel>>();
		List<ProcurementMasterModel> MeasureList = new ArrayList<ProcurementMasterModel>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewMeasureType?org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
			MeasureList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewMeasureType ends");
		return MeasureList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-measurement-type-delete")
	public @ResponseBody JsonResponse<Object> deleteMeasureType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteMeasureType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteMeasureType?id=" + id +  "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteMeasureType ends");
		return resp;
	}

	

	/**
	 * for requisition type
	 * 
	 * @param requisition
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-reqi-type")
	public @ResponseBody JsonResponse<Object> addReqiType(@RequestBody ProcurementMasterModel requisition,
			HttpSession session) {
		logger.info("Method : addReqiType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		requisition.setRequisitionCreatedBy(userId);
		requisition.setOrg(org);
		requisition.setOrgDiv(orgDiv);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addReqiType", requisition, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addReqiType starts");

		return resp;
	}

	

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-reqi-type")
	public @ResponseBody List<ProcurementMasterModel> viewRequisitionType(HttpSession session) {
		logger.info("Method : viewRequisitionType starts");

		JsonResponse<List<ProcurementMasterModel>> resp = new JsonResponse<List<ProcurementMasterModel>>();
		List<ProcurementMasterModel> RequisitonList = new ArrayList<ProcurementMasterModel>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewRequisitionType?org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
			RequisitonList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewRequisitionType ends");
		return RequisitonList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-reqi-type-delete")
	public @ResponseBody JsonResponse<Object> deleteRequiType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteRequiType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteRequiType?id=" + id + "&org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteRequiType ends");
		return resp;
	}

	/**
	 * for requisition type master
	 * 
	 * @param ProcurementMasterModel
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-reqi-priority-type")
	public @ResponseBody JsonResponse<Object> addReqiPriorityType(@RequestBody ProcurementMasterModel reqiprio,
			HttpSession session) {
		logger.info("Method : addReqiPriorityType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		reqiprio.setRequiPriorityCreatedBy(userId);
		reqiprio.setOrg(org);
		reqiprio.setOrgDiv(orgDiv);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addReqiPriorityType", reqiprio, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addReqiPriorityType starts");

		return resp;
	}

	

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-reqi-priority-type")
	public @ResponseBody List<ProcurementMasterModel> viewReqiPriorityType(HttpSession session) {
		logger.info("Method : viewReqiPriorityType starts");

		JsonResponse<List<ProcurementMasterModel>> resp = new JsonResponse<List<ProcurementMasterModel>>();
		List<ProcurementMasterModel> RequisitonPriorityList = new ArrayList<ProcurementMasterModel>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewReqiPriorityType?org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
			RequisitonPriorityList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewReqiPriorityType ends");
		return RequisitonPriorityList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-reqi-priority-type-delete")
	public @ResponseBody JsonResponse<Object> deleteRequiPriorityType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteRequiPriorityType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteRequiPriorityType?id=" + id + "&org="+org+"&orgDiv="+orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteRequiPriorityType ends");
		return resp;
	}

////////Payment Term Type///////////////////////
	/**
	 * payment term master
	 * 
	 * @param procure
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/view-procurement-add-payment-term-type")
	public @ResponseBody JsonResponse<Object> addPaymentTerm(@RequestBody ProcurementMasterModel procure, Model model,
			HttpSession session) {
		logger.info("Method : add PayTerm starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		procure.setPaymentTermCreatedBy(userId);
		procure.setOrg(org);
		procure.setOrgDiv(orgDiv);

		try {
			procure.setPaymentTermUpdatedBy(userId);

			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addPayTerm", procure, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : add PayTerm ends");

		return jsonResponse;
	}

	

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-payment-term-type")
	public @ResponseBody List<ProcurementMasterModel> viewPaymentTerm(HttpSession session) {
		logger.info("Method : viewPaymentTerm starts");

		JsonResponse<List<ProcurementMasterModel>> resp = new JsonResponse<List<ProcurementMasterModel>>();
		List<ProcurementMasterModel> PaymentTermList = new ArrayList<ProcurementMasterModel>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewPaymentTerm?org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
			PaymentTermList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		logger.info("Method : viewPaymentTerm ends");
		return PaymentTermList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-payment-term-type-delete")
	public @ResponseBody JsonResponse<Object> deletePaymentTerm(HttpSession session, @RequestParam String id) {
		logger.info("Method : deletePaymentTerm starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deletePaymentTerm?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deletePaymentTerm ends");
		return resp;
	}

	/**
	 * legal term master
	 * 
	 * @param procure
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/view-procurement-add-legal-term-type")
	public @ResponseBody JsonResponse<Object> addLegalTerm(@RequestBody ProcurementMasterModel procure,
			HttpSession session) {
		logger.info("Method : add LegalTerm starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		procure.setLegalTermCreatedBy(userId);
		procure.setOrg(org);
		procure.setOrgDiv(orgDiv);

		try {
			procure.setLegalTermUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addLegalTerm", procure, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : add LegalTerm ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-legal-term-type")
	public @ResponseBody List<ProcurementMasterModel> viewLegalTerm(HttpSession session) {
		logger.info("Method : viewLegalTerm starts");

		JsonResponse<List<ProcurementMasterModel>> resp = new JsonResponse<List<ProcurementMasterModel>>();
		List<ProcurementMasterModel> LegalTermList = new ArrayList<ProcurementMasterModel>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewLegalTerm?org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
			LegalTermList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewLegalTerm ends");
		return LegalTermList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-legal-term-type-delete")
	public @ResponseBody JsonResponse<Object> deleteLegalTerm(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteLegalTerm starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteLegalTerm?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteLegalTerm ends");
		return resp;
	}

	/**
	 * master for payment type
	 * 
	 * @param procure
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/view-procurement-add-payment-status-type")
	public @ResponseBody JsonResponse<Object> addPaymentStatus(@RequestBody ProcurementMasterModel procure, Model model,
			HttpSession session) {
		logger.info("Method : addPaymentStatus starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		procure.setPaymentStatusCreatedBy(userId);
		procure.setOrg(org);
		procure.setOrgDiv(orgDiv);

		try {
			procure.setPaymentStatusUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addPaymentStatus", procure,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addPaymentStatus ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-payment-status-type")
	public @ResponseBody List<ProcurementMasterModel> viewPaymentStatus(HttpSession session) {
		logger.info("Method : viewPaymentStatus starts");

		JsonResponse<List<ProcurementMasterModel>> resp = new JsonResponse<List<ProcurementMasterModel>>();
		List<ProcurementMasterModel> paymentStatusList = new ArrayList<ProcurementMasterModel>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewPaymentStatus?org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
			paymentStatusList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewPaymentStatus ends");
		return paymentStatusList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-payment-status-type-delete")
	public @ResponseBody JsonResponse<Object> deletePaymentStatus(HttpSession session, @RequestParam String id) {
		logger.info("Method : deletePaymentStatus starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deletePaymentStatus?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deletePaymentStatus ends");
		return resp;
	}

////////Delivery Method Type///////////////////////
	/**
	 * delivery method
	 * 
	 * @param procure
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/view-procurement-add-delivery-method-type")
	public @ResponseBody JsonResponse<Object> addDeliveryMethod(@RequestBody ProcurementMasterModel procure,
			Model model, HttpSession session) {
		logger.info("Method : addDeliveryMethod starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		procure.setDeliveryMethodCreatedBy(userId);
		procure.setOrg(org);
		procure.setOrgDiv(orgDiv);

		try {
			procure.setDeliveryMethodUpdatedBy(userId);
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "addDeliveryMethod", procure,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {

			jsonResponse.setMessage("Success");
		}

		logger.info("Method : addDeliveryMethod ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-delivery-method-type")
	public @ResponseBody List<ProcurementMasterModel> viewDeliveryMethod(HttpSession session) {
		logger.info("Method : viewPaymentStatus starts");

		JsonResponse<List<ProcurementMasterModel>> resp = new JsonResponse<List<ProcurementMasterModel>>();
		List<ProcurementMasterModel> DeliveryMethodList = new ArrayList<ProcurementMasterModel>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewDeliveryMethod?org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
			DeliveryMethodList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewDeliveryMethod ends");
		return DeliveryMethodList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-delivery-method-delete")
	public @ResponseBody JsonResponse<Object> deleteDeliveryMethod(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteDeliveryMethod starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteDeliveryMethod?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteDeliveryMethod ends");
		return resp;
	}

	
	/**
	 * 
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-measure-type-read-csv-data")
	public @ResponseBody JsonResponse<Object> addMeasureTypeFromCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : addMeasureTypeFromCsv uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ProcurementMasterModel> reqTypeList = new ArrayList<ProcurementMasterModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Order", "Code", "Measuement Name", "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {

				reqTypeList.add(new ProcurementMasterModel(csvRecord.get("Order"), csvRecord.get("Code"),
						csvRecord.get("Measuement Name"), csvRecord.get("Status"), userId));

			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					reqTypeList.add(new ProcurementMasterModel(splitParam[0], splitParam[1], splitParam[2],
							splitParam[3], userId));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (reqTypeList != null) {
			reqTypeList.stream().forEach(s -> {
				if (s.getMeasurementStatus().contains("Active")) {
					s.setMeasurementStatus("1");
				} else {
					s.setMeasurementStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addMeasureTypeFromCsv", reqTypeList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :addMeasureTypeFromCsv uploadFile controller function 'post-mapping' ends");
		return response;
	}
	
	/**
	 * 
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-reqi-type-read-csv-data")
	public @ResponseBody JsonResponse<Object> uploadDistCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ProcurementMasterModel> reqTypeList = new ArrayList<ProcurementMasterModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Name", "Description", "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {

				reqTypeList.add(new ProcurementMasterModel(csvRecord.get("Name"), csvRecord.get("Description"),
						csvRecord.get("Status"), userId));

			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					reqTypeList.add(new ProcurementMasterModel(splitParam[0], splitParam[1], splitParam[2], userId));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (reqTypeList != null) {
			reqTypeList.stream().forEach(s -> {
				if (s.getRequisitionStatus().contains("Active")) {
					s.setRequisitionStatus("1");
				} else {
					s.setRequisitionStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addReqiTypeFromCsv", reqTypeList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}
	/**
	 * 
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-reqi-prio-type-read-csv-data")
	public @ResponseBody JsonResponse<Object> addReqiPriorityTypeFromCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ProcurementMasterModel> reqTypeList = new ArrayList<ProcurementMasterModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Name", "Description", "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {
				ProcurementMasterModel obj = new ProcurementMasterModel();
				obj.setRequiPriorityName(csvRecord.get("Name"));
				obj.setRequiPriorityDesc(csvRecord.get("Description"));
				obj.setRequiPriorityStatus(csvRecord.get("Status"));
				obj.setRequiPriorityCreatedBy(csvRecord.get("Status"));
				reqTypeList.add(obj);  
			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t"); 
					ProcurementMasterModel obj = new ProcurementMasterModel();
					obj.setRequiPriorityName(splitParam[0]);
					obj.setRequiPriorityDesc(splitParam[1]);
					obj.setRequiPriorityStatus(splitParam[2]);
					obj.setRequiPriorityCreatedBy(userId);
					reqTypeList.add(obj); 
				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (reqTypeList != null) {
			reqTypeList.stream().forEach(s -> {
				if (s.getRequiPriorityStatus().contains("Active")) {
					s.setRequiPriorityStatus("1");
				} else {
					s.setRequiPriorityStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addReqiPrioTypeFromCsv", reqTypeList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}
	
	/**
	 * 
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-payment-term-read-csv-data")
	public @ResponseBody JsonResponse<Object> addPaymentTermFromCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : addPaymentTermFromCsv uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ProcurementMasterModel> reqTypeList = new ArrayList<ProcurementMasterModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Name", "Description", "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {
				ProcurementMasterModel obj = new ProcurementMasterModel();
				obj.setPaymentTermName(csvRecord.get("Name"));
				obj.setPaymentTermDesc(csvRecord.get("Description"));
				obj.setPaymentTermStatus(csvRecord.get("Status"));
				obj.setPaymentTermCreatedBy(userId);
				reqTypeList.add(obj);
				/*
				 * reqTypeList.add(new ProcurementMasterModel(csvRecord.get("Name"),
				 * csvRecord.get("Description"), csvRecord.get("Status"), userId));
				 */

			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					ProcurementMasterModel obj = new ProcurementMasterModel();
					obj.setPaymentTermName(splitParam[0]);
					obj.setPaymentTermDesc(splitParam[1]);
					obj.setPaymentTermStatus(splitParam[2]);
					obj.setPaymentTermCreatedBy(userId);
					reqTypeList.add(obj);
					
					//reqTypeList.add(new ProcurementMasterModel(splitParam[0], splitParam[1], splitParam[2], userId));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (reqTypeList != null) {
			reqTypeList.stream().forEach(s -> {
				if (s.getPaymentTermStatus().contains("Active")) {
					s.setPaymentTermStatus("1");
				} else {
					s.setPaymentTermStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addPaymentTermFromCsv", reqTypeList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :addPaymentTermFromCsv uploadFile controller function 'post-mapping' ends");
		return response;
	}

	/**
	 * 
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-legal-term-read-csv-data")
	public @ResponseBody JsonResponse<Object> addLegalTermFromCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : addLegalTermFromCsv uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ProcurementMasterModel> reqTypeList = new ArrayList<ProcurementMasterModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Name", "Description", "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {
				ProcurementMasterModel ob = new ProcurementMasterModel();
				ob.setLegalTermName(csvRecord.get("Name"));
				ob.setLegalTermStatus(csvRecord.get("Status"));
				ob.setLegalTermDesc( csvRecord.get("Description"));
				ob.setLegalTermCreatedBy(userId);
				reqTypeList.add(ob);
				/*
				 * reqTypeList.add(new ProcurementMasterModel(csvRecord.get("Name"),
				 * csvRecord.get("Description"), csvRecord.get("Status"), userId));
				 */

			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					ProcurementMasterModel ob = new ProcurementMasterModel();
					ob.setLegalTermName(splitParam[0]);
					ob.setLegalTermStatus(splitParam[2]);
					ob.setLegalTermDesc(splitParam[1]);
					ob.setLegalTermCreatedBy(userId);
					reqTypeList.add(ob);
					//reqTypeList.add(new ProcurementMasterModel(splitParam[0], splitParam[1], splitParam[2], userId));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (reqTypeList != null) {
			reqTypeList.stream().forEach(s -> {
				if (s.getLegalTermStatus().contains("Active")) {
					s.setLegalTermStatus("1");
				} else {
					s.setLegalTermStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addLegalTermFromCsv", reqTypeList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :addLegalTermFromCsv uploadFile controller function 'post-mapping' ends");
		return response;
	}
	
	/**
	 * 
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-pay-status-read-csv-data")
	public @ResponseBody JsonResponse<Object> addPayStatusFromCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : addPayStatusFromCsv uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ProcurementMasterModel> reqTypeList = new ArrayList<ProcurementMasterModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Name", "Description", "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {
				ProcurementMasterModel ob = new ProcurementMasterModel(); 
				ob.setPaymentStatusName(csvRecord.get("Name"));
				ob.setPaymentStatusDesc(csvRecord.get("Description"));
				ob.setPaymentStatusStatus(csvRecord.get("Status"));  
				ob.setPaymentStatusCreatedBy(userId);
				reqTypeList.add(ob);
				/*
				 * reqTypeList.add(new ProcurementMasterModel(csvRecord.get("Name"),
				 * csvRecord.get("Description"), csvRecord.get("Status"), userId));
				 */

			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					ProcurementMasterModel ob = new ProcurementMasterModel();  
					ob.setPaymentStatusName(splitParam[0]);
					ob.setPaymentStatusDesc(splitParam[1]);
					ob.setPaymentStatusStatus(splitParam[2]);  
					ob.setPaymentStatusCreatedBy(userId);
					reqTypeList.add(ob);
					//reqTypeList.add(new ProcurementMasterModel(splitParam[0], splitParam[1], splitParam[2], userId));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (reqTypeList != null) {
			reqTypeList.stream().forEach(s -> {
				if (s.getPaymentStatusStatus().contains("Active")) {
					s.setPaymentStatusStatus("1");
				} else {
					s.setPaymentStatusStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addPayStatusFromCsv", reqTypeList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :addPayStatusFromCsv uploadFile controller function 'post-mapping' ends");
		return response;
	}
	
	/**
	 * 
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-dlv-method-read-csv-data")
	public @ResponseBody JsonResponse<Object> addDlvMethodFromCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : addDlvMethodFromCsv uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ProcurementMasterModel> reqTypeList = new ArrayList<ProcurementMasterModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Name", "Description", "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {
				ProcurementMasterModel ob = new ProcurementMasterModel();  
				ob.setDeliveryMethodName(csvRecord.get("Name"));
				ob.setDeliveryMethodStatus(csvRecord.get("Status"));
				ob.setDeliveryMethodDesc(csvRecord.get("Description"));   
				ob.setDeliveryMethodCreatedBy(userId); 
				reqTypeList.add(ob);
				/*
				 * reqTypeList.add(new ProcurementMasterModel(csvRecord.get("Name"),
				 * csvRecord.get("Description"), csvRecord.get("Status"), userId));
				 */

			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					ProcurementMasterModel ob = new ProcurementMasterModel();  
					
					ob.setDeliveryMethodName(splitParam[0]);
					ob.setDeliveryMethodStatus(splitParam[2]);
					ob.setDeliveryMethodDesc(splitParam[1]);   
					ob.setDeliveryMethodCreatedBy(userId);  
					reqTypeList.add(ob);
					//reqTypeList.add(new ProcurementMasterModel(splitParam[0], splitParam[1], splitParam[2], userId));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (reqTypeList != null) {
			reqTypeList.stream().forEach(s -> {
				if (s.getDeliveryMethodStatus().contains("Active")) {
					s.setDeliveryMethodStatus("1");
				} else {
					s.setDeliveryMethodStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addDlvMethodFromCsv", reqTypeList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :addDlvMethodFromCsv uploadFile controller function 'post-mapping' ends");
		return response;
	}
}
