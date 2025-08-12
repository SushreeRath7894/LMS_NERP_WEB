package nirmalya.aathithya.webmodule.employee.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.ReimbursementModel;
import nirmalya.aathithya.webmodule.employee.model.ReimbursementPaymentModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;

@Controller
@RequestMapping(value = { "employee/" })
public class ReimbursementController {

	Logger logger = LoggerFactory.getLogger(ReimbursementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@GetMapping("/reimbursement")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : reimbursement starts");

		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] Employee = restClient.getForObject(env.getEmployeeUrl() + "getEmployeeListData1?userId=" + userId,
					DropDownModel[].class);
			List<DropDownModel> getEmployeeList = Arrays.asList(Employee);
			model.addAttribute("getEmployeeList", getEmployeeList);
			logger.info("Method : getEmployeeList starts"+getEmployeeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

//			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
//				model.addAttribute("hrRole", data);
//			}
//			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
//				model.addAttribute("adRole", data);
//			}
			if (data.contentEquals("rol001") || data.contentEquals("rol002") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", "admin");
				//System.out.println("data>>>>----" + data);
			}
			
			 if (data.contentEquals("rol001") || data.contentEquals("rol010") ||data.contentEquals("rol002") ||data.contentEquals("rol003")) 
			 { 
				 model.addAttribute("aprRole", data); 
			 }
			 
			
			if (data.contentEquals("rol003") ) {
				model.addAttribute("managerRole", data);
			}
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : reimbursement ends");
		return "employee/reimbursementNew";
		//return "employee/reimbursement";
	}

	/*
	 * View Reimbursement
	 * 
	 */

	// view reimbursement

	@SuppressWarnings("unchecked")
	@GetMapping("reimbursement-view-through-ajax")
	public @ResponseBody List<ReimbursementModel> viewReimbursement(HttpSession session, @RequestParam String userid,
			@RequestParam String roleid,@RequestParam String selftype) {

		logger.info("Method : viewReimbursement starts");

		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<ReimbursementModel>> resp = new JsonResponse<List<ReimbursementModel>>();

		List<String> roleList = new ArrayList<String>();

		if (roleid != null && roleid != "") {
			String[] arr = roleid.split(",");
			for (int i = 0; i < arr.length; i++) {
				roleList.add(arr[i]);
			}
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setUserRole(roleList);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);
		empModel.setSelfType(selftype);

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "reimbursement-view", empModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		ObjectMapper mapper = new ObjectMapper();
		List<ReimbursementModel> viewRem = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ReimbursementModel>>() {
				});	
		for (ReimbursementModel m : viewRem) {
			if(m.getReqType().equals("0")) {
				m.setReqTypeName("Adhoc");
				m.setAdvanceReq("");
			}else {
				m.setReqTypeName("Planned");
				if (m.getAdvanceReq().equals("0")) {
					m.setAdvanceReq("NO");
				}else{
					m.setAdvanceReq("YES");
				}
			}
			if (m.getApproveStatus().equals("0")) {
				m.setApproveStatus("Pending");
			}else if (m.getApproveStatus().equals("1")) {
				m.setApproveStatus("Forwarded");
			}else if (m.getApproveStatus().equals("2")) {
				m.setApproveStatus("Rejected");
			} else if (m.getApproveStatus().equals("3")) {
				m.setApproveStatus("Approved");
			}else{
				m.setApproveStatus("completed");
			}

 
		}
		resp.setBody(viewRem);
		logger.info("Method : viewLeaveApply ends");
		logger.info("VIEWWWW"+resp.getBody());

		return resp.getBody();
	}
	/*
	 * Add Reimbursement
	 * 
	 */
 
	@SuppressWarnings("unchecked")

	@PostMapping("/reimbursement-add-details")
	public @ResponseBody JsonResponse<Object> addReimbursement(@RequestBody ReimbursementModel vendorMasterModel,
			HttpSession session) {
		logger.info("Method : addReimbursement starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		vendorMasterModel.setCreatedBy(userId);
		//vendorMasterModel.setEmpId(userId);
		vendorMasterModel.setOrganization(organization);
		vendorMasterModel.setOrgDivision(orgDivision);
		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;
		String docName = vendorMasterModel.getDocName1();
		if(docName != null && docName != "null" && !docName.isEmpty()) {
			if (inputFile != null) {
				try {
					bytes = inputFile.getBytes();
					String[] fileType = inputFile.getContentType().split("/");
					imageName = saveAllImage(bytes, fileType[1]);
					vendorMasterModel.setDocName1(imageName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else {
				vendorMasterModel.setDocName1(docName);
			}
		}else {
			vendorMasterModel.setDocName1(null);
		}
		
		try {
			logger.info("vendorMasterModel-====="+vendorMasterModel);
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "add-reimbursement", vendorMasterModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/*
		 * if (resp.getCode().equals("success")) { resp.setMessage("Success"); } else {
		 * resp.setMessage("Unsuccess"); }
		 */
		logger.info("Method : addReimbursement ends");

		return resp;
	}


	/*
	 * for editing Reimbursement
	 * 
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("reimbursement-edit-through-ajax")
	public @ResponseBody JsonResponse<ReimbursementModel> editReimbursement(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editReimbursement starts");

		JsonResponse<ReimbursementModel> jsonResponse = new JsonResponse<ReimbursementModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "edit-rest-reimbursement?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		//jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editReimbursement ends");
		return jsonResponse;
	}

	/*
	 * Delete Reimbursement
	 */
 
	@SuppressWarnings("unchecked")
	@PostMapping("reimbursement-delete-details")
	public @ResponseBody JsonResponse<Object> deleteReimbursementTravels(@RequestParam String id,
			Model model, HttpSession session) {
		logger.info("Method : deleteReimbursementTravels function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "deleteReimbursement?id=" + id  , JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		if (res.getCode().equals("success")) {
			res.setMessage("Success");
		} else {
			res.setMessage("Unsuccess");
		}
		logger.info("Method : delete Reimbursement Ends");
		
		logger.info("RESPPPPPPP"+res);
		return res;
	}

	
	
	
	
	/*
	 * post Mapping for add reimbursement requisition details
	 * 
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = "reimbursement-req-save-th-ajax") public @ResponseBody
	 * JsonResponse<Object> saveReimbursementReq(@RequestBody
	 * List<ReimbursementModel> reimbursementModel, HttpSession session, Model
	 * model) { logger.info("Method : saveReimbursementReq function starts");
	 * logger.info(reimbursementModel); JsonResponse<Object> res = new
	 * JsonResponse<Object>();
	 * 
	 * List<ReimbursementModel> reimReqList = new ArrayList<ReimbursementModel>();
	 * 
	 * //List<ReimbursementDocumentModel> docList = new
	 * ArrayList<ReimbursementDocumentModel>();
	 * 
	 * String userId = ""; String dateFormat = ""; try { userId = (String)
	 * session.getAttribute("USER_ID"); dateFormat = (String)
	 * session.getAttribute("DATEFORMAT"); } catch (Exception e) {
	 * 
	 * } for (ReimbursementModel m : reimbursementModel) { m.setCreatedBy(userId);
	 * m.setExpenseDate(DateFormatter.inputDateFormat(m.getExpenseDate(),
	 * dateFormat));
	 * 
	 * }
	 */
	/*
	 * for (ReimbursementDocumentModel a :
	 * reimbursementModel.get(0).getDocumentList()) { if (a.getImageNameEdit() !=
	 * null && a.getImageNameEdit() != "") { a.setFileName(a.getImageNameEdit()); }
	 * else { if (a.getFileName() != null && a.getFileName() != "") { String
	 * delimiters = "\\."; String[] x = a.getFileName().split(delimiters);
	 * 
	 * if (x[1].contentEquals("png") || x[1].contentEquals("jpg") ||
	 * x[1].contentEquals("jpeg")) {
	 * 
	 * for (String s1 : a.getDocumentFile()) { if (s1 != null) try { byte[] bytes =
	 * Base64.getDecoder().decode(s1); String imageName =
	 * fileUpload.saveAllImage(bytes); a.setFileName(imageName); } catch (Exception
	 * e) { e.printStackTrace(); } } } else if (x[1].contentEquals("pdf")) { for
	 * (String s1 : a.getDocumentFile()) { try { byte[] bytes =
	 * Base64.getDecoder().decode(s1); String pdfName =
	 * fileUpload.saveAllPdf(bytes); a.setFileName(pdfName); } catch (Exception e) {
	 * e.printStackTrace(); } } } else if (x[1].contentEquals("docx")) { for (String
	 * s1 : a.getDocumentFile()) { try { byte[] bytes =
	 * Base64.getDecoder().decode(s1); String pdfName =
	 * fileUpload.saveAllDocx(bytes); a.setFileName(pdfName); } catch (Exception e)
	 * { e.printStackTrace(); } } } else if (x[1].contentEquals("doc")) { for
	 * (String s1 : a.getDocumentFile()) { try { byte[] bytes =
	 * Base64.getDecoder().decode(s1); String pdfName =
	 * fileUpload.saveAllDoc(bytes); a.setFileName(pdfName); } catch (Exception e) {
	 * e.printStackTrace(); } } } else if (x[1].contentEquals("xls")) { for (String
	 * s1 : a.getDocumentFile()) { try { byte[] bytes =
	 * Base64.getDecoder().decode(s1); String pdfName =
	 * fileUpload.saveAllXls(bytes); a.setFileName(pdfName); } catch (Exception e) {
	 * e.printStackTrace(); } } } else if (x[1].contentEquals("xlsx")) { for (String
	 * s1 : a.getDocumentFile()) { try { byte[] bytes =
	 * Base64.getDecoder().decode(s1); String pdfName =
	 * fileUpload.saveAllXlsx(bytes); a.setFileName(pdfName); } catch (Exception e)
	 * { e.printStackTrace(); } } } } } }
	 */
	/*
	 * try { res = restTemplate.postForObject(env.getEmployeeUrl() +
	 * "reimbursement-req-rest-add", reimbursementModel, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); } ObjectMapper mapper
	 * = new ObjectMapper();
	 * 
	 * reimReqList = mapper.convertValue(res.getBody(), new
	 * TypeReference<List<ReimbursementModel>>() { }); if (reimReqList != null) {
	 * 
	 * for (ReimbursementModel a : reimReqList) { if (a.getExpenseDate() != null &&
	 * a.getExpenseDate() != "") {
	 * a.setExpenseDate(DateFormatter.dateFormat(a.getExpenseDate(), dateFormat)); }
	 * }
	 */
	// docList = reimReqList.get(0).getDocumentList();
	/*
	 * for (ReimbursementDocumentModel m : docList) { if (m.getFileName() != null &&
	 * m.getFileName() != "") { String[] extension = m.getFileName().split("\\.");
	 * if (extension.length == 2) { if (extension[1].equals("xls") ||
	 * extension[1].equals("xlsx")) {
	 * 
	 * String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " +
	 * m.getFileName() + "></i> ";
	 * 
	 * m.setAction(docPath); } if (extension[1].equals("pdf")) { String docPath =
	 * " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName() +
	 * " ;></i> ";
	 * 
	 * m.setAction(docPath); } if (extension[1].equals("doc") ||
	 * extension[1].equals("dox") || extension[1].equals("docx")) { String docPath =
	 * " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title=" +
	 * m.getFileName() + "></i> "; m.setAction(docPath); } if
	 * (extension[1].equals("png") || extension[1].equals("jpg") ||
	 * extension[1].equals("jpeg")) { String docPath =
	 * " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title=" +
	 * m.getFileName() + "></i>  "; m.setAction(docPath); } } else {
	 * m.setAction("N/A"); } } else { m.setAction("N/A"); }
	 * m.setAction("<a href=\"/document/procurment/" + m.getFileName() +
	 * "\" target=\"_blank\" >" + m.getAction() + "</a>");
	 * 
	 * }
	 */
	// }
	// String message = res.getMessage();
	// reimReqList.get(0).setDocumentList(docList);
	/*
	 * if (message != null && message != "") {
	 * 
	 * } else { res.setMessage("Success"); } res.setBody(reimReqList);
	 * 
	 * logger.info("Method : saveReimbursementReq function Ends"); return res; }
	 */
	/*
	 * save image
	 */

	@PostMapping("reimbursement-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		logger.info("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}
	
	
	/*
	 * save image
	 */

	@GetMapping("reimbursement-remove-file")
	public @ResponseBody JsonResponse<Object> uploadFileRemove(HttpSession session) {
		logger.info("Method : uploadFileRemove controller function 'get-mapping' starts");
		//logger.info("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			//response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", null);
			session.removeAttribute("quotationPFile");

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFileRemove controller function 'get-mapping' ends");
		return response;
	}

	/*
	 * Add Travel Reimbursement Details
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("reimbursement-add-details-travels")
	public @ResponseBody JsonResponse<Object> addReimbursementTravelDetails(HttpSession session,

			@RequestBody ReimbursementModel reimbursementModel) {

		logger.info("Method : addReimbursementTravelDetails");
		String dateFormat = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {
			e.printStackTrace();
		}
		reimbursementModel.setCreatedBy(userId);
		if (reimbursementModel.getExpenseDate() != null && reimbursementModel.getExpenseDate() != "") {
			reimbursementModel
					.setExpenseDate(DateFormatter.inputDateFormat(reimbursementModel.getExpenseDate(), dateFormat));
		}

		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				reimbursementModel.setDocName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "add-reimbursement-travel-details",
					reimbursementModel, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ReimbursementModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<ReimbursementModel>>() {
					});

			resp.setBody(quotation);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addReimbursementTravelDetails ends");
		return resp;
	}

	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("png") || ext.contentEquals("jpg") || ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;

	}

	// View Travel Reimbrusiement

	@SuppressWarnings("unchecked")

	@GetMapping("/reimbursement-travel-view")
	public @ResponseBody List<ReimbursementModel> viewReimbursementTravels(HttpSession session) {

		logger.info("Method : viewReimbursementTravels");

		JsonResponse<List<ReimbursementModel>> resp = new JsonResponse<List<ReimbursementModel>>();
		try {
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "viewReimbursementTravels", JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<ReimbursementModel> reimbursementModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ReimbursementModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (ReimbursementModel a : reimbursementModel) {

			if (a.getExpenseDate() != null && a.getExpenseDate() != "") {
				a.setExpenseDate(DateFormatter.dateFormat(a.getExpenseDate(), dateFormat));
			}

		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : viewReimbursementTravels ends");

		return reimbursementModel;
	}
	/*
	 * //Leave Type Rule Delete
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("/reimbursement-travel-delete") public @ResponseBody
	 * JsonResponse<ReimbursementModel> deleteReimbursementTravels(@RequestParam
	 * String deleteId) { logger.info("Method : deleteReimbursementTravels starts");
	 * JsonResponse<ReimbursementModel> resp = new
	 * JsonResponse<ReimbursementModel>();
	 * 
	 * try { resp = restTemplate.getForObject(env.getEmployeeUrl() +
	 * "deleteReimbursementTravels?deleteId=" + deleteId, JsonResponse.class); }
	 * catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * if (resp.getMessage() != null && resp.getMessage() != "") {
	 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
	 * 
	 * resp.setMessage("Success");
	 * 
	 * } logger.info("deletewwwwwwwwwwwwweeeeeeeeeeeebbbbbbbbbbbbbb"+resp);
	 * logger.info("Method :  deleteReimbursementTravels ends"); return resp; }
	 */

	/*
	 *
	 * Delete Reimbursement
	 *
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("reimbursement-delete-travels")
	public @ResponseBody JsonResponse<ReimbursementModel> deleteReimbursementTravels(@RequestParam String id) {
		logger.info("Method : deleteReimbursementTravels starts");

		JsonResponse<ReimbursementModel> jsonResponse = new JsonResponse<ReimbursementModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "deleteReimbursementTravels?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : deleteReimbursementTravels ends");
		return jsonResponse;
	}

	/*
	 * for editing Reimbursement
	 * 
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("reimbursement-edit-travel")
	public @ResponseBody JsonResponse<ReimbursementModel> editReimbursementTravel(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editReimbursementTravel starts");

		JsonResponse<ReimbursementModel> jsonResponse = new JsonResponse<ReimbursementModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "edit-rest-reimbursement-travels?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		ReimbursementModel reimModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<ReimbursementModel>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (reimModel.getExpenseDate() != null && reimModel.getExpenseDate() != "") {
			reimModel.setExpenseDate(DateFormatter.dateFormat(reimModel.getExpenseDate(), dateFormat));
		}

		jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editReimbursementTravel ends");
		return jsonResponse;
	}

	/*
	 * For Modal view of reimbursement
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "reimbursement-new-modal" })
	public @ResponseBody JsonResponse<List<ReimbursementModel>> modalAssignmentRem(Model model,
			@RequestBody String index, BindingResult result) {

		logger.info("Method :modalAssignmentEdu starts");

		byte[] decodeId = Base64.getDecoder().decode(index.getBytes());

		String id = (new String(decodeId));
		JsonResponse<List<ReimbursementModel>> response = new JsonResponse<List<ReimbursementModel>>();
		try {
			response = restTemplate.getForObject(env.getEmployeeUrl() + "getReimbusementByIdsModal?reimId=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("success");
		}
		logger.info("Method : modalAssignmentEdu  ends ");
		return response;
	}
	/*
	 * 
	 * 
	 * /* for drop down branch names by bank id
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = { "reimbursement-payment-voucher-branchList" })
	 * public @ResponseBody JsonResponse<DropDownModel> getbranchLists(Model
	 * model, @RequestBody String index, BindingResult result) {
	 * logger.info("Method : getbranchList starts");
	 * 
	 * JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>(); try {
	 * res = restTemplate.getForObject(env.getEmployeeUrl() + "getbranchLists?id=" +
	 * index, JsonResponse.class);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * if (res.getMessage() != null) {
	 * 
	 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
	 * res.setMessage("success"); }
	 * 
	 * logger.info("Method : getbranchLists ends"); return res; }
	 */
	/*
	 * Add Reimbursement Payment Voucher
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("reimbursement-add-details-payment-voucher")
	public @ResponseBody JsonResponse<Object> addReimbursementPayment(
			@RequestBody ReimbursementPaymentModel reimbursementModel, HttpSession session) {
		logger.info("Method : addReimbursementPayment starts");
		String dateFormat = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		reimbursementModel.setCreatedBy(userId);
		// reimbursementModel.setEmpName(userId);

		if (reimbursementModel.getTransactionDate() != null && reimbursementModel.getTransactionDate() != "") {
			reimbursementModel
					.setTransactionDate(DateFormatter.dateFormat(reimbursementModel.getTransactionDate(), dateFormat));
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "add-reimbursement-payment", reimbursementModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addReimbursementPayment ends");
		return resp;
	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	/*
	 * @GetMapping(value = { "reimbursement-view-activity-log" })
	 * public @ResponseBody List<ActivitylogModel>
	 * getActivityLogReimbrusement(@RequestParam String id, HttpSession session) {
	 * logger.info("Method : getActivityLogReimbrusement starts");
	 * List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();
	 * try {
	 * 
	 * ActivitylogModel[] activityLog = restTemplate
	 * .getForObject(env.getEmployeeUrl() + "get-activity-log-riemb?id=" + id,
	 * ActivitylogModel[].class); activityLogList = Arrays.asList(activityLog);
	 * 
	 * for(ActivitylogModel m : activityLog) { String dateFormat = (String)
	 * session.getAttribute("DATEFORMAT"); if(m.getOperationOn()!=null &&
	 * m.getOperationOn()!="") {
	 * m.setOperationOn(DateFormatter.dateFormat(m.getOperationOn(), dateFormat)); }
	 * }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * logger.info("Method : getActivityLogReimbrusement ends"); return
	 * activityLogList; }
	 */

	@GetMapping(value = { "reimbursement-view-activity-log" })
	public @ResponseBody List<ActivitylogModel> getActivityLogReimbrusement(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getActivityLogReimbrusement starts");
		List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();
		try {

			ActivitylogModel[] activityLog = restTemplate
					.getForObject(env.getEmployeeUrl() + "get-activity-log-riemb?id=" + id, ActivitylogModel[].class);
			activityLogList = Arrays.asList(activityLog);

			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (ActivitylogModel m : activityLogList) {
				String date = m.getOperationOn();
				if (date != null && date != "") {
					date = DateFormatter.dateFormat(date, dateFormat);
					m.setOperationOn(date);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getActivityLogReimbrusement ends");
		return activityLogList;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("reimbursement-view-activity-log") public @ResponseBody
	 * List<ActivitylogModel> getActivityLogReimbrusement(Model model, HttpSession
	 * session) { logger.info("Method : getActivityLogReimbrusement starts");
	 * 
	 * JsonResponse<List<ActivitylogModel>> jsonResponse = new
	 * JsonResponse<List<ActivitylogModel>>();
	 * 
	 * try {
	 * 
	 * jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() +
	 * "get-activity-log-riemb", JsonResponse.class);
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * List<ActivitylogModel> addreq = mapper.convertValue(jsonResponse.getBody(),
	 * new TypeReference<List<ActivitylogModel>>() { });
	 * 
	 * //String dateFormat = (String) (session).getAttribute("DATEFORMAT");
	 * 
	 * 
	 * String dateFormat = (String) (session).getAttribute("DATEFORMAT"); for
	 * (ActivitylogModel m : addreq) { String date = m.getOperationOn(); if (date !=
	 * null && date != "") { date = DateFormatter.dateFormat(date, dateFormat);
	 * logger.info(date); m.setOperationOn(date); }
	 * 
	 * }
	 * 
	 * jsonResponse.setBody(addreq);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method ; getActivityLogReimbrusement ends");
	 * logger.info(jsonResponse); return jsonResponse.getBody(); }
	 */

	/*
	 * post Mapping for approve ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "reimbursement-approve-th-ajax")
	public @ResponseBody JsonResponse<Object> approveItemReimbrusement(
			@RequestBody ReimbursementModel inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : approveItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryItemRequisitionModel.setCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getEmployeeUrl() + "rest-approve-reimbrusement-rem",
					inventoryItemRequisitionModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}

		logger.info("Method : approveItemRequisition function Ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("reimbursement-master-data-through-ajax")
	public @ResponseBody List<ReimbursementModel> reimbruseemntThroughAjax(Model model, HttpServletRequest request,
			HttpSession session) {
		logger.info("Method : reimbruseemntThroughAjax starts");

		JsonResponse<List<ReimbursementModel>> jsonResponse = new JsonResponse<List<ReimbursementModel>>();

		try {

			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "get-vendor-list-reimbrusement",
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ReimbursementModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<ReimbursementModel>>() {
					});
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (ReimbursementModel m : addreq) {
				String date = m.getCreatedOn();
				if (date != null && date != "") {
					date = DateFormatter.dateFormat(date, dateFormat);
					m.setCreatedOn(date);
				}

			}

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method ; reimbruseemntThroughAjax ends");

		return jsonResponse.getBody();
	}

	// approve leave

	@SuppressWarnings("unchecked")
	@GetMapping("reimbursement-details-approve")
	public @ResponseBody JsonResponse<ReimbursementModel> approvereimbursementApply(@RequestParam String approveId,
			String name, String comment, String roleid) {

		logger.info("Method : approvereimbursementApply starts");
 
		JsonResponse<ReimbursementModel> response = new JsonResponse<ReimbursementModel>();
		try {
			response = restTemplate.getForObject(env.getEmployeeUrl() + "approvereimbursementApply?id=" + approveId
					+ "&name=" + name + "&comment=" + comment + "&roleid=" + roleid, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/*
		 * if(response.getCode().equals("success")) { response.setMessage("Success"); }
		 * else { response.setCode(response.getMessage());
		 * response.setMessage("Unsuccess"); }
		 */
		logger.info("Method : approvereimbursementApply ends");

		return response;
	}

	// reject leave

	@SuppressWarnings("unchecked")
	@GetMapping("reimbursement-details-reject")
	public @ResponseBody JsonResponse<ReimbursementModel> rejectreimbursementApply(@RequestParam String rejectId,
			String name, String comment) {

		logger.info("Method : rejectreimbursementApply starts");
		JsonResponse<ReimbursementModel> response = new JsonResponse<ReimbursementModel>();
		try {
			response = restTemplate.getForObject(env.getEmployeeUrl() + "rejectreimbursementApply?id=" + rejectId
					+ "&name=" + name + "&comment=" + comment, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		/*
		 * if(response.getCode().equals("success")) { response.setMessage("Success"); }
		 * else { response.setCode(response.getMessage());
		 * response.setMessage("Unsuccess"); }
		 */
		logger.info("Method : rejectreimbursementApply ends");
		return response;
	}
	
	//
	@SuppressWarnings("unchecked")
	@GetMapping("reimbursement-view-getEmpData")
	public @ResponseBody JsonResponse<DropDownModel> getEmpData(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : getEmpData starts");
		JsonResponse<DropDownModel> jsonResponse = new JsonResponse<DropDownModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "rest-reigetEmpData?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : getEmpData ends");
		logger.info("getEmpData=====" + jsonResponse);
		return jsonResponse;
	}

}