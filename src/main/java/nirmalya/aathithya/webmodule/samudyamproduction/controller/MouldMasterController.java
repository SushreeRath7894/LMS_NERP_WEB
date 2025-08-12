package nirmalya.aathithya.webmodule.samudyamproduction.controller;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.samudyamproduction.model.CheckingDetailsModel;
import nirmalya.aathithya.webmodule.samudyamproduction.model.MouldMasterModel;
@Controller
@RequestMapping(value = "production")
public class MouldMasterController {
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(MouldMasterController.class);
	
	@Autowired
	FileUpload fileUpload;
	
	//manageMouldMaster
	@GetMapping(value = { "/view-mouldmaster" })
	public String pageMouldMaster(Model model, HttpSession session) {
		logger.info("Method :manageMouldMaster starts");
		String org=""; 
		String orgDiv="";
		try {
			org = (String) session.getAttribute("ORGANIZATION"); 
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] patern = restClient.getForObject(env.getProduction()+ "get-pattern-type-list?org="+org+"&orgDiv="+orgDiv, DropDownModel[].class);
			List<DropDownModel> paternList = Arrays.asList(patern);
			model.addAttribute("paternList", paternList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] moldType = restClient.getForObject(env.getProduction() + "get-mold-type-list?org="+org+"&orgDiv="+orgDiv, DropDownModel[].class);
			List<DropDownModel> moldTypeList = Arrays.asList(moldType);
			model.addAttribute("moldTypeList", moldTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : manageMouldMaster ends");
		return "samudyam-production/view-mouldmaster";
	}
	
	//viewCheckingType
	@SuppressWarnings("unchecked")
	@GetMapping("view-mouldmaster-view-checkingtype")
	public @ResponseBody List<CheckingDetailsModel> viewCheckingType(HttpSession session) {
		logger.info("Method : viewCheckingType starts");
		JsonResponse<List<CheckingDetailsModel>> resp = new JsonResponse<List<CheckingDetailsModel>>();
		List<CheckingDetailsModel> returnList = new ArrayList<CheckingDetailsModel>();
		String org=""; 
		String orgDiv="";
		try {
			org = (String) session.getAttribute("ORGANIZATION"); 
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProduction() + "checking-detailstype-list?org="+org+"&orgDiv="+orgDiv,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :ViewMoldMasterView ends"+returnList);
		return returnList;
		}
	//saveModlmaster
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-mouldmaster-add-modlmaster")
	public @ResponseBody JsonResponse<Object> saveModlmaster(@RequestBody MouldMasterModel moldModel,
			HttpSession session) {
		logger.info("Method : saveModlmaster function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		for (InventoryVendorDocumentModel a : moldModel.getDocumentList()) {
			logger.info("a.getFileName()===="+a.getFileName());
			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				logger.info("a.getFileName()111===="+a.getFileName());
				if (a.getFileName() != null && a.getFileName() != "") {
					String delimiters = "\\.";
					String[] x = a.getFileName().split(delimiters);

					if (x[1].contentEquals("png") || x[1].contentEquals("jpg") || x[1].contentEquals("jpeg")) {
						logger.info("x[1]===="+x[1]);
						for (String s1 : a.getDocumentFile()) {
							logger.info("s1===="+s1.toString());
							if (s1 != null)
								try {
									byte[] bytes = Base64.getDecoder().decode(s1);
									String imageName = fileUpload.saveAllImage(bytes);
									a.setFileName(imageName);
									logger.info("imageName===="+imageName);
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
		String organization=""; 
		String orgDivision="";
		String userId="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		moldModel.setOrganization(organization);
		moldModel.setOrgDivision(orgDivision);
		moldModel.setCreatedBy(userId);
		logger.info("moldModel===="+moldModel);
		
		try {
			resp = restClient.postForObject(env.getProduction() + "rest-add-molddetails", moldModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveModlmaster function Ends");
		return resp;
	}	
//ViewMoldMasterView		
	@SuppressWarnings("unchecked")
	@GetMapping("view-mouldmaster-view-mold-master")
	public @ResponseBody List<MouldMasterModel> ViewMoldMasterView(HttpSession session) {
		logger.info("Method : ViewMoldMasterView starts");

		JsonResponse<List<MouldMasterModel>> resp = new JsonResponse<List<MouldMasterModel>>();
		List<MouldMasterModel> returnList = new ArrayList<MouldMasterModel>();
		String org=""; 
		String orgDiv="";
		try {
			org = (String) session.getAttribute("ORGANIZATION"); 
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProduction() + "mold-master-data-list?org="+org+"&orgDiv="+orgDiv,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :ViewMoldMasterView ends"+returnList);
		return returnList;
	}
	
	@GetMapping("view-mouldmaster-edit")
	public @ResponseBody MouldMasterModel editMoldMaster(@RequestParam String id, HttpSession session) {

		logger.info("Method : editMoldMaster starts" + id);
		JsonResponse<MouldMasterModel> response = new JsonResponse<MouldMasterModel>();
		MouldMasterModel productList = new MouldMasterModel();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		try {
			productList = restClient.getForObject(env.getProduction() + "rest-edit-moldmaster?id=" + id, MouldMasterModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("edittsssssssssssss"+productList);
		if (productList != null) {
			documentList = productList.getDocumentList();
			logger.info("documentlist"+documentList);
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
		logger.info("REsp" + response);
		logger.info("Method : editMoldMaster ends");
		return productList;
	}
	//delete Mold Master
	@SuppressWarnings("unchecked")
	@GetMapping("view-mouldmaster-delete")
	public @ResponseBody JsonResponse<Object> deleteMoldMaster(HttpSession session,@RequestParam String mlid) {
		logger.info("Method : deleteMoldMaster starts");
		JsonResponse<Object> response = new JsonResponse<Object>();
		String org=""; 
		String orgDiv="";
		try {
			org = (String) session.getAttribute("ORGANIZATION"); 
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restClient.getForObject(env.getProduction() + "delete-moldMaster?mlid=" + mlid + "&org=" + org+ "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if(response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : deleteMoldMaster ends");
		return response;
	}
}
