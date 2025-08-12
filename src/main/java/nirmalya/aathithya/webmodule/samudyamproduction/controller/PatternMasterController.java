package nirmalya.aathithya.webmodule.samudyamproduction.controller;

import java.util.ArrayList;
import java.util.Base64;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.samudyamproduction.model.PatternMasterModel;

@Controller
@RequestMapping(value = "/production")
public class PatternMasterController {
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(PatternMasterController.class);

	@Autowired
	FileUpload fileUpload;

	/*
	 * @GetMapping("manage-pattern") public String managePatternMaster(Model model,
	 * HttpSession session) {
	 * 
	 * logger.info("Method : managePatternMaster starts");
	 * model.addAttribute("pageName", "Pattern Master Page Coming Soon");
	 * logger.info("Method : managePatternMaster ends"); return
	 * "recruitment/view-action"; }
	 */

	// managePatternMaster

	@GetMapping(value = { "/manage-pattern" })
	public String managePatternMaster(Model model, HttpSession session) {
		logger.info("Method :managePatternMaster starts");

		logger.info("Method : managePatternMaster ends");
		return "samudyam-production/patternMaster.html";
	}

	// savePatternmaster
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "manage-pattern-add-details")
	public @ResponseBody JsonResponse<Object> savePatternmaster(@RequestBody PatternMasterModel patternModel,
			HttpSession session) {
		logger.info("Method : savePatternmaster function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		for (InventoryVendorDocumentModel a : patternModel.getDocumentList()) {
			logger.info("a.getFileName()====" + a.getFileName());
			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				logger.info("a.getFileName()111====" + a.getFileName());
				if (a.getFileName() != null && a.getFileName() != "") {
					String delimiters = "\\.";
					String[] x = a.getFileName().split(delimiters);

					if (x[1].contentEquals("png") || x[1].contentEquals("jpg") || x[1].contentEquals("jpeg")) {
						logger.info("x[1]====" + x[1]);
						for (String s1 : a.getDocumentFile()) {
							logger.info("s1====" + s1.toString());
							if (s1 != null)
								try {
									byte[] bytes = Base64.getDecoder().decode(s1);
									String imageName = fileUpload.saveAllImage(bytes);
									a.setFileName(imageName);
									logger.info("imageName====" + imageName);
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
		String organization = "";
		String orgDivision = "";
		String userId = "";
		logger.info("web 1===" + patternModel);
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		patternModel.setOrganization(organization);
		patternModel.setOrgDivision(orgDivision);
		patternModel.setCreatedBy(userId);
		logger.info("patternModel====" + patternModel);

		try {
			resp = restClient.postForObject(env.getProduction() + "rest-addPatternDetails", patternModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode("Unsuccess");
		} else {
			resp.setCode("Success");
		}
		logger.info("Method : savePatternmaster function Ends");
		return resp;
	}

	// ViewPatternMasterView
	@SuppressWarnings("unchecked")
	@GetMapping("manage-pattern-view-details")
	public @ResponseBody List<PatternMasterModel> ViewMoldMasterView(HttpSession session) {
		logger.info("Method : ViewMoldMasterView starts");

		JsonResponse<List<PatternMasterModel>> resp = new JsonResponse<List<PatternMasterModel>>();
		List<PatternMasterModel> returnList = new ArrayList<PatternMasterModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getProduction() + "view-pattern-details?org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :ViewMoldMasterView ends" + returnList);
		return returnList;
	}
//editPatternMaster
	@SuppressWarnings("unchecked")
	@GetMapping("manage-pattern-edit-details")
	public @ResponseBody JsonResponse<PatternMasterModel> editPatternMaster(@RequestParam String id, HttpSession session) {

		logger.info("Method : editPatternMaster starts" + id);
		JsonResponse<PatternMasterModel> response = new JsonResponse<PatternMasterModel>();
		PatternMasterModel productList = new PatternMasterModel();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restClient.getForObject(env.getProduction() + "edit-pattern-details?id=" + id+ "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		productList = mapper.convertValue(response.getBody(), new TypeReference<PatternMasterModel>() {
		});
		logger.info("edittsssssssssssss" + productList);
		if (response != null) {
			documentList = productList.getDocumentList();
			logger.info("documentlist" + documentList);
			if (documentList != null) {
				for (InventoryVendorDocumentModel m : documentList) {
					if (m.getFileName() != null && m.getFileName() != "") {

						String[] extension = m.getFileName().split("\\.");
						if (extension.length == 2) {

							logger.info("satyyyyyyyyyyyyyyyyyyyyyyyyyaaaa");
							if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

								String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " + m.getFileName()
										+ "></i> ";

								m.setAction(docPath);
							}
							if (extension[1].equals("pdf")) {
								String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName()
										+ " ;></i> ";

								m.setAction(docPath);
							}
							if (extension[1].equals("doc") || extension[1].equals("dox")
									|| extension[1].equals("docx")) {
								String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
										+ m.getFileName() + "></i> ";
								m.setAction(docPath);
							}
							if (extension[1].equals("png") || extension[1].equals("jpg")
									|| extension[1].equals("jpeg")) {
								String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
										+ m.getFileName() + "></i>  ";
								m.setAction(docPath);
							}
						} else {
							m.setAction("N/A");
						}
					} else {
						m.setAction("N/A");
					}
					m.setAction("<a href=\"/document/procurment/" + m.getFileName() + "\" target=\"_blank\" >"
							+ m.getAction() + "</a>");
				}
			}
			productList.setDocumentList(documentList);
		}
		if (response.getMessage() != null && response.getMessage() != "") {
		} else {
			response.setMessage("Success");
		}
		response.setBody(productList);
		logger.info("REsp" + response);
		logger.info("Method : editPatternMaster ends");
		return response;
	}

	// deletePatternMaster
	@SuppressWarnings("unchecked")
	@GetMapping("manage-pattern-delete-details")
	public @ResponseBody JsonResponse<Object> deletePatternMaster(HttpSession session, @RequestParam String patid) {
		logger.info("Method : deletePatternMaster starts");
		JsonResponse<Object> response = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restClient.getForObject(
					env.getProduction() + "delete-pattern-details?patid=" + patid + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : deletePatternMaster ends");
		return response;
	}
	
	// approvePatternMaster
	@SuppressWarnings("unchecked")
	@GetMapping("manage-pattern-approve-details")
	public @ResponseBody JsonResponse<Object> approvePatternMaster(HttpSession session, @RequestParam String patid) {
		logger.info("Method : approvePatternMaster starts");
		JsonResponse<Object> response = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restClient.getForObject(
					env.getProduction() + "approve-pattern-details?patid=" + patid + "&org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : approvePatternMaster ends");
		return response;
	}
}
