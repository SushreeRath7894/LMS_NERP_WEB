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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.account.model.SalesInvoicePaymentModel;

@Controller
@RequestMapping(value = "account")
public class AccountOutstatndingPaymentController {
	Logger logger = LoggerFactory.getLogger(AccountOutstatndingPaymentController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/outstanding-payment-ledger")
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
		
		logger.info("Method : outstandingRecievedLedger end");
		return "account/outstanding-payment-ledger";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("outstanding-payment-ledger-view")
	public @ResponseBody Object viewOutstandingPayment(HttpSession session,String fromDate,String toDate , String modOfSearch,String customerId) {
		logger.info("Method :viewOutstandingPayment starts");
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
			resp = restTemplate.getForObject(env.getAccountUrl() + "rest-viewOutstandingPayment?orgName=" + orgName + "&orgDivision=" + orgDivision + "&fromDate=" 
					+ fromDate + "&toDate="+toDate + "&modOfSearch=" + modOfSearch + "&customerId=" + customerId,
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
		logger.info("Method :viewOutstandingPayment ends");
		return resp;
	}
	
	//add payment
			@SuppressWarnings("unchecked")
			@PostMapping("outstanding-payment-ledger-payment-sent")
			public @ResponseBody JsonResponse<Object> addSendPaymentInvoice(@RequestBody SalesInvoicePaymentModel salesInvoicePaymentModel, Model model, HttpSession session) {

			logger.info("Method : addSendPaymentInvoice starts" + salesInvoicePaymentModel);

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
							
				resp = restTemplate.postForObject(env.getAccountUrl() + "addSendPaymentAccount", salesInvoicePaymentModel,JsonResponse.class);

				} catch (RestClientException e) {
					e.printStackTrace();
				}

			if (resp.getCode() == "") {
				resp.setMessage("Unsuccess");
			} else {
				resp.setCode("Success");
			}
				logger.info("Method : addSendPaymentInvoice ends" + resp);

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
			@PostMapping("outstanding-payment-ledger-upload-file")
			public @ResponseBody JsonResponse<Object> uploadFilePayment(@RequestParam("file") MultipartFile inputFile,
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
			@PostMapping("outstanding-payment-ledger-delete-file")
			public @ResponseBody JsonResponse<Object> deleteUploadFilePayment(HttpSession session) {
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
			
		//schedule date
		
		@SuppressWarnings("unchecked")
		@GetMapping("outstanding-payment-ledger-scheduleDate")
		public @ResponseBody JsonResponse<DropDownModel> paymentScheduleDate(HttpSession session,
				@RequestParam String invoiceId, String scheduledDate) {

			logger.info("Method : paymentScheduleDate starts");
			JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
			
			try { 
				response = restTemplate.getForObject(env.getAccountUrl() + "paymentScheduleDate?scheduledDate=" + scheduledDate + "&invoiceId=" + invoiceId,
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
		@GetMapping("outstanding-payment-ledger-getDebitNoteList")
		public @ResponseBody JsonResponse<SalesInvoicePaymentModel> getDebitNoteList(HttpSession session, @RequestParam String id) {

			logger.info("Method : getDebitNoteList starts");
			JsonResponse<SalesInvoicePaymentModel> resp = new JsonResponse<SalesInvoicePaymentModel>();
			
			try {
				resp = restTemplate.getForObject(env.getAccountUrl() + "getDebitNoteList?id=" + id,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			if (resp.getCode().equals("Success")) {
				resp.setMessage("Success");
			} else {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			}
			logger.info("Method : getDebitNoteList ends="+resp);
			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "outstanding-payment-ledger-voucherDetails" })
		public @ResponseBody JsonResponse<Object> getVoucherDetailOutstandPayment( HttpSession session,@RequestParam String voucherId) {
			logger.info("Method : getVoucherPaymentVoucherDetails starts" + voucherId);
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
				res = restTemplate.getForObject(env.getAccountUrl() + "getVoucherPaymentVoucherDetails?voucherId=" + voucherId
			 +"&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("Success");
			}
			logger.info("resp==>" + res);
			logger.info("Method : getVoucherPaymentVoucherDetails ends");
			return res;
		}
	
}
