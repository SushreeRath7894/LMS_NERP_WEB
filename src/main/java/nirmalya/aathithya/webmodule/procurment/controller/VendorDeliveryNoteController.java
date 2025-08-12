package nirmalya.aathithya.webmodule.procurment.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.DeliveryNoteModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "inventory/" })
public class VendorDeliveryNoteController {

	Logger logger = LoggerFactory.getLogger(VendorDeliveryNoteController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "vendor-delivery-note" })
	public String vendorDeliveryNote(Model model, HttpSession session) {
		logger.info("Method : vendorDeliveryNote starts");

		try {
			DropDownModel[] dd = restClient.getForObject(env.getInventoryUrl() + "getDeliveryMethod",
					DropDownModel[].class);
			List<DropDownModel> deliveryMethod = Arrays.asList(dd);

			model.addAttribute("deliveryMethod", deliveryMethod);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : vendorDeliveryNote ends");
		return "procurement/vendorDeliveryNote";
	}

	@GetMapping(value = { "vendor-delivery-note-get-delnote-list" })
	public @ResponseBody List<DeliveryNoteModel> getDelNoteListThroghAjax(HttpSession session) {
		logger.info("Method : getDelNoteListThroghAjax starts");

		List<DeliveryNoteModel> delNoteList = new ArrayList<DeliveryNoteModel>();

		String dateFormat = (String) session.getAttribute("DATEFORMAT");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String vendorId = (String) session.getAttribute("VENDOR_ID");
			DeliveryNoteModel[] delNote = restClient.getForObject(
					env.getInventoryUrl() + "getDeliveryNoteList?vendorId=" + vendorId +"&org=" + orgName + "&orgDiv=" + orgDivision, DeliveryNoteModel[].class);
			delNoteList = Arrays.asList(delNote);

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (DeliveryNoteModel a : delNoteList) {
			logger.info("aaaaaaaaa"+a.getPoId());
			 if (a.getPoId().contentEquals("New"))
			   { 
			   a.setPoId(null); 
			   } 
			 
			if (a.getInvDate() != null && a.getInvDate() != "") {
				a.setInvDate(DateFormatter.dateFormat(a.getInvDate(), dateFormat));
			}
			if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
				a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
			}
			if (a.getDispatchDate() != null && a.getDispatchDate() != "") {
				a.setDispatchDate(DateFormatter.dateFormat(a.getDispatchDate(), dateFormat));
			}
			if (a.getEstDeliveryDate() != null && a.getEstDeliveryDate() != "") {
				a.setEstDeliveryDate(DateFormatter.dateFormat(a.getEstDeliveryDate(), dateFormat));
			}
			if (a.getPoDate() != null && a.getPoDate() != "") {
				a.setPoDate(DateFormatter.dateFormat(a.getPoDate(), dateFormat));
			}
			if (a.getSubmitDate() != null && a.getSubmitDate() != "") {
				a.setSubmitDate(DateFormatter.dateFormat(a.getSubmitDate(), dateFormat));
			}
			if (a.getCompleteDate() != null && a.getCompleteDate() != "") {
				a.setCompleteDate(DateFormatter.dateFormat(a.getCompleteDate(), dateFormat));
			}
			if (a.getStatus().contentEquals("0")) {
				a.setStatus("Created");
			} else if (a.getStatus().contentEquals("1")) {
				a.setStatus("Submitted");
			} else if (a.getStatus().contentEquals("2")) {
				a.setStatus("Completed");
			}
		}

		logger.info("Method : getDelNoteListThroghAjax ends");
		return delNoteList;
	}

	@GetMapping(value = { "vendor-delivery-note-get-item-list" })
	public @ResponseBody List<InventoryProductModel> getDelNoteItemListThroghAjax(HttpSession session,
			@RequestParam String id) {
		logger.info("Method : getDelNoteItemListThroghAjax starts");

		List<InventoryProductModel> delNoteList = new ArrayList<InventoryProductModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			InventoryProductModel[] delNote = restClient.getForObject(
					env.getInventoryUrl() + "getDeliveryItemNoteList?id=" + id +"&org=" + orgName + "&orgDiv=" + orgDivision, InventoryProductModel[].class);
			delNoteList = Arrays.asList(delNote);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getDelNoteItemListThroghAjax ends");
		return delNoteList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "vendor-delivery-note-edit-details" })
	public @ResponseBody JsonResponse<DeliveryNoteModel> getDelNoteDtlsById(Model model, @RequestBody String tCountry,
			BindingResult result, HttpSession session) {
		logger.info("Method : getDelNoteDtlsById starts");

		JsonResponse<DeliveryNoteModel> res = new JsonResponse<DeliveryNoteModel>();

		DeliveryNoteModel delNote = new DeliveryNoteModel();

		String dateFormat = (String) session.getAttribute("DATEFORMAT");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restClient.getForObject(env.getInventoryUrl() + "getDelNoteDtlsById?id=" + tCountry + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			delNote = mapper.convertValue(res.getBody(), new TypeReference<DeliveryNoteModel>() {
			});

			if (delNote.getPoId().contentEquals("New")) {
				delNote.setPoId(null);
			}
			if (delNote.getInvDate() != null && delNote.getInvDate() != "") {
				delNote.setInvDate(DateFormatter.dateFormat(delNote.getInvDate(), dateFormat));
			}
			if (delNote.getCreatedOn() != null && delNote.getCreatedOn() != "") {
				delNote.setCreatedOn(DateFormatter.dateFormat(delNote.getCreatedOn(), dateFormat));
			}
			if (delNote.getDispatchDate() != null && delNote.getDispatchDate() != "") {
				delNote.setDispatchDate(DateFormatter.dateFormat(delNote.getDispatchDate(), dateFormat));
			}
			if (delNote.getEstDeliveryDate() != null && delNote.getEstDeliveryDate() != "") {
				delNote.setEstDeliveryDate(DateFormatter.dateFormat(delNote.getEstDeliveryDate(), dateFormat));
			}
			if (delNote.getPoDate() != null && delNote.getPoDate() != "") {
				delNote.setPoDate(DateFormatter.dateFormat(delNote.getPoDate(), dateFormat));
			}
			if (delNote.getSubmitDate() != null && delNote.getSubmitDate() != "") {
				delNote.setSubmitDate(DateFormatter.dateFormat(delNote.getSubmitDate(), dateFormat));
			}
			if (delNote.getCompleteDate() != null && delNote.getCompleteDate() != "") {
				delNote.setCompleteDate(DateFormatter.dateFormat(delNote.getCompleteDate(), dateFormat));
			}

			if (delNote.getDocList().size() > 0) {
				for (InventoryVendorDocumentModel m : delNote.getDocList()) {
					String path = env.getBaseURL() + "document/procurment/" + m.getFileName();
					m.setCreatedBy(path);
				}
			}

			res.setBody(delNote);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getDelNoteDtlsById ends");
		return res;

	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/vendor-delivery-note-save-dtls")
	public @ResponseBody JsonResponse<Object> saveProductPurchaseDetails(@RequestBody List<DeliveryNoteModel> delNote,
			HttpSession session) {
		logger.info("Method : saveProductPurchaseDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";

		try {
			userId = (String) session.getAttribute("VENDOR_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (DeliveryNoteModel m : delNote) {
			m.setCreatedBy(userId);
			if (m.getDispatchDate() != null && m.getDispatchDate() != "") {
				m.setDispatchDate(DateFormatter.inputDateFormat(m.getDispatchDate(), dateFormat));
			}
			if (m.getEstDeliveryDate() != null && m.getEstDeliveryDate() != "") {
				m.setEstDeliveryDate(DateFormatter.inputDateFormat(m.getEstDeliveryDate(), dateFormat));
			}
		}
		List<String> editDocList = new ArrayList<String>();
		for (InventoryVendorDocumentModel a : delNote.get(0).getDocList()) {
			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
				editDocList.add(a.getImageNameEdit());
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String delimiters = "\\.";
					String[] x = a.getFileName().split(delimiters);

					if (x[1].contentEquals("png") || x[1].contentEquals("jpg") || x[1].contentEquals("jpeg")) {

						for (String s1 : a.getDocumentFile()) {
							if (s1 != null)
								try {
									byte[] bytes = Base64.getDecoder().decode(s1);
									String imageName = fileUpload.saveAllImage(bytes);
									a.setFileName(imageName);
								} catch (Exception e) {
									e.printStackTrace();
								}
						}
					} else if (x[1].contentEquals("pdf")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllPdf(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (x[1].contentEquals("docx")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllDocx(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (x[1].contentEquals("doc")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllDoc(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (x[1].contentEquals("xls")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllXls(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (x[1].contentEquals("xlsx")) {
						for (String s1 : a.getDocumentFile()) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String pdfName = fileUpload.saveAllXlsx(bytes);
								a.setFileName(pdfName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		delNote.get(0).setEditDocList(editDocList);
		try {
			resp = restClient.postForObject(env.getInventoryUrl() + "saveDeliveryNoteDetails", delNote,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveProductPurchaseDetails starts");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/vendor-delivery-note-submit-to-company")
	public @ResponseBody JsonResponse<Object> submitToCompany(@RequestBody DeliveryNoteModel delNote, HttpSession session) {
		logger.info("Method : submitToCompany starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String orgName="";
		String orgDivision="";

		try {
			userId = (String) session.getAttribute("VENDOR_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		delNote.setCreatedBy(userId);
		delNote.setOrg(orgName);
		delNote.setOrgDiv(orgDivision);
		LocalDate date = java.time.LocalDate.now();
		String submitDate = DateFormatter.dateFormat(date.toString(), dateFormat);

		try {
			resp = restClient.postForObject(env.getInventoryUrl() + "submitToCompany", delNote, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
			resp.setBody(submitDate);
		}

		logger.info("Method : submitToCompany starts");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/vendor-delivery-note-new")
	public @ResponseBody JsonResponse<Object> createNewDeliveryNote(@RequestBody DeliveryNoteModel delNote, HttpSession session) {
		logger.info("Method : createNewDeliveryNote starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("VENDOR_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

//		DropDownModel dtls = new DropDownModel();
//		dtls.setKey(delNote);
//		dtls.setName(userId);
		delNote.setCreatedBy(userId);

		try {
			resp = restClient.postForObject(env.getInventoryUrl() + "createNewDeliveryNote", delNote, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : createNewDeliveryNote starts");
		return resp;
	}

	@GetMapping(value = { "vendor-delivery-note-log" })
	public @ResponseBody List<ActivitylogModel> getActivityLog(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewStockThroughAjax starts");
		List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			ActivitylogModel[] activityLog = restClient
					.getForObject(env.getInventoryUrl() + "get-activity-log?id=" + id +"&org=" + orgName + "&orgDiv=" + orgDivision, ActivitylogModel[].class);
			activityLogList = Arrays.asList(activityLog);

			for (ActivitylogModel m : activityLog) {
				String dateFormat = (String) session.getAttribute("DATEFORMAT");
				if (m.getOperationOn() != null && m.getOperationOn() != "") {
					m.setOperationOn(DateFormatter.dateFormat(m.getOperationOn(), dateFormat));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewStockThroughAjax ends");
		return activityLogList;
	}
}
