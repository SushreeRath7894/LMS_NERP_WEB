package nirmalya.aathithya.webmodule.projects.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectFileuploadModel;

@Controller
@RequestMapping(value = "projects")
public class ProjectDocumentUploadController {
	Logger logger = LoggerFactory.getLogger(ProjectDocumentUploadController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "technical-datasheet" })
	public String technical(Model model, HttpSession session) {
		logger.info("Method : technical-datasheet starts");
		
		String documentTypeId = "TPDT0001";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : technical-datasheet ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	
	@GetMapping(value = { "architect-Dwg" })
	public String architectDwg(Model model, HttpSession session) {
		logger.info("Method : architect-Dwg starts");
         String documentTypeId = "TPDT0002";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : architect-Dwg ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	
	@GetMapping(value = { "tender-drawing" })
	public String tenderdrawing(Model model, HttpSession session) {
		logger.info("Method : tenderdrawing starts");
         String documentTypeId = "TPDT0003";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : tenderdrawing ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	@GetMapping(value = { "tender-details" })
	public String tenderdetails(Model model, HttpSession session) {
		logger.info("Method : tenderdetails starts");
         String documentTypeId = "TPDT0004";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : tenderdetails ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	@GetMapping(value = { "powo-from-client" })
	public String powofromclient(Model model, HttpSession session) {
		logger.info("Method : powofromclient starts");
         String documentTypeId = "TPDT0005";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : powofromclient ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	@GetMapping(value = { "quality-inspection" })
	public String qualityinspection(Model model, HttpSession session) {
		logger.info("Method : qualityinspection starts");
         String documentTypeId = "TPDT0006";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : qualityinspection ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	
	@GetMapping(value = { "project-dbr" })
	public String dbr(Model model, HttpSession session) {
		logger.info("Method : dbr starts");
         String documentTypeId = "TPDT0007";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : dbr ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	@GetMapping(value = { "project-proposalDwg" })
	public String proposalDwg(Model model, HttpSession session) {
		logger.info("Method : proposalDwg starts");
         String documentTypeId = "TPDT0008";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : proposalDwg ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	@GetMapping(value = { "completion-certificate" })
	public String completioncertificate(Model model, HttpSession session) {
		logger.info("Method : completioncertificate starts");
		
		String documentTypeId = "TPDT0009";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : completioncertificate ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	@GetMapping(value = { "punch-points" })
	public String punchpoints(Model model, HttpSession session) {
		logger.info("Method : punchpoints starts");
		
		String documentTypeId = "TPDT0016";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : punchpoints ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	@GetMapping(value = { "close-out" })
	public String closeout(Model model, HttpSession session) {
		logger.info("Method : close-out starts");
		
		String documentTypeId = "TPDT0017";
		
		model.addAttribute("documentTypeId", documentTypeId);
		logger.info(documentTypeId);

		
		try {

			DropDownModel[] country = restClient.getForObject(
					env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId, DropDownModel[].class);

			logger.info("ddddddd" + env.getProjects() + "rest-project-document-type" + "?id=" + documentTypeId,
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(country);

			model.addAttribute("documentTypeList", documentTypeList);

			logger.info("documentTypeList" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : close-out ends");
		return "projects/projectDocumnetUpload.html";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("technical-datasheet-project-view")
	public @ResponseBody Object viewProjectCreation(HttpSession session) {

		logger.info("Method :viewProjectCreation starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-view-project-creation" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :viewProjectCreation ends");

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("technical-datasheet-add")
	public @ResponseBody JsonResponse<Object> adddocumentUpload(@RequestBody List<ProjectCreationWebModel> prjCreation,
			HttpSession session) {
		logger.info("Method : adddocumentUpload starts" + prjCreation);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<ProjectCreationWebModel> documentList = new ArrayList<ProjectCreationWebModel>();
		List<ProjectFileuploadModel> docList = new ArrayList<ProjectFileuploadModel>();

		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		for (ProjectCreationWebModel m : prjCreation) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}

		for (ProjectFileuploadModel a : prjCreation.get(0).getDocumentList()) {

			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String[] extension = a.getFileName().split("\\.");
					int lastindex = extension.length - 1;
					
					 String fileExtension = "";
					 if (extension.length > 0) {
					        fileExtension = extension[0];
					    } else {
					       
					    }
					System.out.println("file" + fileExtension);
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							System.out.println("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, /* fileExtension, */extension[lastindex]);
							a.setFileName(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				}
			}
		}


		try {
			resp = restClient.postForObject(env.getProjects() + "rest-addDocumentTpeUpload", prjCreation, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : adddocumentUpload end" + resp);
		return resp;
	}

	public String saveAllMultiImages(byte[] imageBytes, /* String name, */ String ext) {
		logger.info("Method : saveAllMultiImages starts"+ext);
		String imageName1 = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("technical-datasheet-document-view")
	public @ResponseBody Object getAllProjectIdDetls(@RequestParam String id,@RequestParam String id2,
			HttpSession session) {

		logger.info("Method :getAlldocumentView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<ProjectFileuploadModel> dataa = new ArrayList<ProjectFileuploadModel>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-getAlldocumentView?id=" + id + "&id2=" + id2 
					+ "&userid=" + userId+"&org="+organization+"&div="+orgDivision,
					JsonResponse.class);
			 

			JSONArray jsonArray = new JSONArray(resp.getBody().toString());

		
			if (jsonArray != null) {
					ObjectMapper mapper1 = new ObjectMapper();

					dataa= mapper1.readValue(jsonArray.get(0).toString(),
							new TypeReference<List<ProjectFileuploadModel>>() {
							});
					logger.info("dataa" + dataa);
					for (ProjectFileuploadModel m : dataa) {
						if (m.getFileName() != null && m.getFileName() != "") {

							String[] extension = m.getFileName().split("\\.");
							if (extension.length == 2) {
								if (extension[1].equals("xls") || extension[1].equals("xlsx") || extension[1].equals("csv")) {

									String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
											+ m.getFileName() + "></i> ";

									m.setAction(docPath);
								}
								if (extension[1].equals("pdf")) {
									String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
											+ m.getFileName() + " ;></i> ";

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
						
						m.setAction("<a href=\"/document/document/" + m.getFileName()
								+ "\" target=\"_blank\" >" +m.getAction() + "</a>");
						logger.info("m.setAction" + m.getAction());
					}
				
					
			
				
			}
			
			  
			
			resp.setBody(dataa);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getAlldocumentView--" + dataa);
		logger.info("Method :getAlldocumentView ends");

		return resp;
	}

}
