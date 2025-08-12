package nirmalya.aathithya.webmodule.account.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoicePaymentModel;

@Controller
@RequestMapping(value = "account")
public class AccountOutstatndingRecievedController {

	Logger logger = LoggerFactory.getLogger(AccountOutstatndingRecievedController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/outstanding-recieved-ledger")
	public String outstandingRecievedLedger(Model model, HttpSession session) {
		logger.info("Method : outstandingRecievedLedger start");

		String org = "";
		String orgDiv = "";
		
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] bank = restTemplate.getForObject(env.getAccountUrl()+"getBankAccountList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> bankAccountList = Arrays.asList(bank);
			
			model.addAttribute("bankAccountList", bankAccountList);
			System.out.println("bankAccountList=="+bankAccountList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] costCenter = restTemplate.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : outstandingRecievedLedger end");
		return "account/outstanding-recieved-ledger";
	}
	
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("outstanding-recieved-ledger-view") public @ResponseBody
	 * List<SalesInvoicePaymentModel> viewsalesInvoiceAccount(HttpSession session) {
	 * 
	 * logger.info("Method :viewsalesInvoiceAccount starts");
	 * JsonResponse<List<SalesInvoicePaymentModel>> resp = new
	 * JsonResponse<List<SalesInvoicePaymentModel>>(); String userId = ""; String
	 * dateFormat = ""; String organization = ""; String orgDivision = ""; try {
	 * userId = (String) session.getAttribute("USER_ID"); dateFormat = (String)
	 * session.getAttribute("DATEFORMAT"); organization = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * 
	 * }
	 * 
	 * EmpRoleModel empModel = new EmpRoleModel();
	 * 
	 * empModel.setUserId(userId); empModel.setType("WEB");
	 * empModel.setOrganization(organization); empModel.setOrgDivision(orgDivision);
	 * try {
	 * 
	 * resp = restTemplate.postForObject(env.getAccountUrl() +
	 * "getAllsalesInvoiceAccount", empModel, JsonResponse.class); } catch
	 * (Exception e) { e.printStackTrace(); } ObjectMapper mapper = new
	 * ObjectMapper();
	 * 
	 * List<SalesInvoicePaymentModel> salesInvoiceNewModel =
	 * mapper.convertValue(resp.getBody(), new
	 * TypeReference<List<SalesInvoicePaymentModel>>() { });
	 * 
	 * for (SalesInvoicePaymentModel a : salesInvoiceNewModel) {
	 * a.setQuantitynew(a.getQuantity()); if (a.getQutUpdatedOn() != null &&
	 * a.getQutUpdatedOn() != "") {
	 * a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
	 * } //logger.info(salesInvoiceNewModel.toString());
	 * 
	 * }
	 * 
	 * resp.setBody(salesInvoiceNewModel); if (resp.getMessage() != "" &&
	 * resp.getMessage() != null) { resp.setCode(resp.getMessage());
	 * resp.setMessage("Unsuccess"); } else { resp.setMessage("Success"); }
	 * logger.info("RESPONSEview" + resp);
	 * logger.info("Method :viewsalesInvoiceAccount ends");
	 * 
	 * return resp.getBody(); }
	 */
	
	//add payment
	@SuppressWarnings("unchecked")
	@PostMapping("outstanding-recieved-ledger-payment-received")
	public @ResponseBody JsonResponse<Object> addPaymentInvoice(@RequestBody SalesInvoicePaymentModel salesInvoicePaymentModel, Model model, HttpSession session) {

	logger.info("Method : addPaymentInvoice starts" + salesInvoicePaymentModel);

	String userId = "";
	String orgName = "";
	String orgDivision = "";
	try {
		orgName = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		userId = (String) session.getAttribute("USER_ID");
	} catch (RestClientException e) {
		e.printStackTrace();

	}
		salesInvoicePaymentModel.setCreatedBy(userId);
		salesInvoicePaymentModel.setOrganization(orgName);
		salesInvoicePaymentModel.setOrgDivision(orgDivision);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		MultipartFile inputFile = (MultipartFile) session.getAttribute("receipt");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				salesInvoicePaymentModel.setDocName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} // upload part

		System.out.println("document name-------------------" + imageName);
	try {
					
		resp = restTemplate.postForObject(env.getAccountUrl() + "addPaymentInvoiceAccount", salesInvoicePaymentModel,JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

	if (resp.getCode() == "") {
		resp.setMessage("Unsuccess");
	} else {
		resp.setCode("Success");
	}
		logger.info("Method : addPaymentInvoice ends" + resp);

		return resp;
	}
	
	//save upload image
	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getAccountDocUpload() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		logger.info(imageName);
		return imageName;

	}
	
	//set upload image name in session
	@PostMapping("outstanding-recieved-ledger-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("receipt", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		System.out.println("img" + response);
		return response;
	}
	
	//delete file name from session
	@PostMapping("outstanding-recieved-ledger-delete-file")
	public @ResponseBody JsonResponse<Object> deleteUploadFile(HttpSession session) {
		logger.info("Method : deleteUploadFile controller function 'post-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.removeAttribute("receipt");
			Object receiptAttribute = session.getAttribute("receipt");
			if (receiptAttribute != null) {
			    response.setMessage(receiptAttribute.toString());
			} else {
			    response.setMessage("No 'receipt' attribute found in the session");
			}

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteUploadFile controller function 'post-mapping' ends");
		System.out.println("img" + response);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("outstanding-recieved-ledger-scheduleDate")
	public @ResponseBody JsonResponse<DropDownModel> paymentScheduleDate(HttpSession session,
			@RequestParam String invoiceId, String scheduledDate) {

		logger.info("Method : paymentScheduleDate starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		
		try { 
			response = restTemplate.getForObject(env.getAccountUrl() + "recievedScheduleDate?scheduledDate=" + scheduledDate + "&invoiceId=" + invoiceId,
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

		logger.info("response=====" + response);
		logger.info("Method : paymentScheduleDate ends");
		return response;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("outstanding-recieved-ledger-view")
	public @ResponseBody Object viewOutstandingReceive(HttpSession session,@RequestParam String fromDate, String toDate,String modOfSearch,String customerId) {

		logger.info("Method :viewOutstandingReceive starts");
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
			resp = restTemplate.getForObject(env.getAccountUrl() + "getAllsalesInvoiceAccount?orgName="+orgName +"&orgDivision=" + orgDivision +
					"&fromDate=" + fromDate + "&toDate=" + toDate + "&modOfSearch=" + modOfSearch + "&customerId=" + customerId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewOutstandingReceive ends"+resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "outstanding-recieved-ledger-voucherDetails" })
	public @ResponseBody JsonResponse<Object> getVchrDetailsOfOutStandingReceive( HttpSession session,@RequestParam String voucherId) {
		logger.info("Method : getVoucherDetailsOfOutStandingReceiveLedger starts");
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
			res = restTemplate.getForObject(env.getAccountUrl() + "getVoucherDetailsOutstandingRcv?voucherId=" + voucherId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Success");
		} else {
			res.setMessage("Unsuccess");
		}
		logger.info("resp==>" + res);
		logger.info("Method : getVoucherDetailsOfOutStandingReceiveLedger ends");
		return res;
	}
}
