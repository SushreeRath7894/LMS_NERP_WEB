package nirmalya.aathithya.webmodule.edms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpSession;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.edms.model.DocumentControlModel;
import nirmalya.aathithya.webmodule.edms.model.DocumentManageAccessModel;
import nirmalya.aathithya.webmodule.edms.model.WorkFlowModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.util.StringUtil;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;

@Controller
@RequestMapping(value = "edms")
public class EdmsDocumentController {

	Logger logger = LoggerFactory.getLogger(EdmsDocumentController.class);

	RestTemplate restClient;
	EnvironmentVaribles env;

	@Autowired
	public EdmsDocumentController(EnvironmentVaribles EnvironmentVaribles, RestTemplate RestTemplate) {
		this.env = EnvironmentVaribles;
		this.restClient = RestTemplate;
	}

	@GetMapping(value = { "document-control" })
	public String documentControl(HttpSession session, Model model) {
		logger.info("Method : documentControl starts");

		try {
			DropDownModel[] folderType = restClient.getForObject(env.getEdms() + "rest-chooseFolderType",
					DropDownModel[].class);
			List<DropDownModel> chooseFolderType = Arrays.asList(folderType);
			model.addAttribute("chooseFolderType", chooseFolderType);
			System.out.println("chooseFolderType===>>>" + chooseFolderType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : documentControl ends");
		return "edms/documentcontrol";
	}

	// Add Documents

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-add")
	public @ResponseBody JsonResponse<Object> uploadDocus(HttpSession session,
			@RequestBody List<DocumentControlModel> documentControlModel)

	{
		logger.info("Method : uploadDocus starts" + documentControlModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		String folderPathDetails = documentControlModel.get(0).getFolderPath();

		logger.info("folderPathDetails---------" + folderPathDetails);
		for (DocumentControlModel r : documentControlModel) {
			if (r.getImageNameEdit() != null && r.getImageNameEdit() != "") {
				logger.info("hello doc getImageNameEdit---------" + r.getImageNameEdit());
				r.setFileName(r.getImageNameEdit());
				String fileUrl = env.getBaseURL() + "document/dms/" + folderPathDetails + r.getFolderName() + "/"
						+ r.getImageNameEdit();
				logger.info("Generated document URL for existing image: " + fileUrl);
				 r.setDocumentUrl(fileUrl);

			} else {

				String[] extension = r.getFileName().split("\\.");
				logger.info("extension---------" + extension);
				int lastindex = extension.length - 1;
				logger.info("lastindex---------" + lastindex);
				String imageName = "";
				String folderPath = "";
				for (String s1 : r.getDocumentFile()) {
					try {
						logger.info("r.getFolderName()---------" + r.getFolderName());
						if (StringUtil.isNull(folderPathDetails)) {
							folderPath = r.getFolderName() + "/";
						} else {
							folderPath = folderPathDetails + "/" + r.getFolderName() + "/";
						}
						byte[] bytes = Base64.getDecoder().decode(s1);
						imageName = saveAllMultiImages(bytes, extension[lastindex], folderPath);
						r.setFileName(imageName);
						logger.info("folderName---------" + folderPath);
						logger.info("imageName---------" + imageName);
					} catch (Exception e) {
						e.printStackTrace();

					}
					String fileUrl = env.getBaseURL() + "document/dms/" + folderPath + imageName;

					logger.info("fileUrl---------" + fileUrl);
					r.setDocumentUrl(fileUrl);
				}
			}
			r.setOrganization(organization);
			r.setOrgDivision(orgDivision);
			r.setCreatedBy(userId);
		}

		try {
			resp = restClient.postForObject(env.getEdms() + "add-documentcontrol", documentControlModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : uploadDocus ends" + resp);

		return resp;
	}

	/*
	 * public String saveAllMultiImages(byte[] imageBytes, String ext, String
	 * folderName) { logger.info("Method : saveAllMultiImages starts"); String
	 * imageName1 = null; logger.info("folderName---------" + folderName); try { if
	 * (imageBytes != null) { long nowTime = new Date().getTime(); if
	 * (ext.contentEquals("jpeg")) { imageName1 = nowTime + ".jpg"; } else {
	 * imageName1 = nowTime + "." + ext; } } String filepath =
	 * env.getFileUploadDms() + folderName; logger.info("filepath---------" +
	 * filepath); Path path = Paths.get(filepath + imageName1);
	 * logger.info("path---------" + path); if (imageBytes != null) {
	 * Files.write(path, imageBytes); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * logger.info("Method : saveAllMultiImages ends"); return imageName1; }
	 */
	
	public String saveAllMultiImages(byte[] imageBytes, String ext, String folderName) {
	    logger.info("Method : saveAllMultiImages starts");

	    String imageName1 = null;

	    try {
	        folderName = folderName.trim().replace("\\", "/").replaceAll("/+", "/");

	        String[] parts = folderName.split("/");
	        StringBuilder safeFolderNameBuilder = new StringBuilder();
	        for (String part : parts) {
	            if (!part.trim().isEmpty()) {
	                safeFolderNameBuilder.append(part.trim()).append(File.separator);
	            }
	        }

	        String basePath = env.getFileUploadDms(); 
	        String folderPath = basePath + safeFolderNameBuilder.toString();

	        logger.info("Cleaned folderPath: '{}'", folderPath);

	        File dir = new File(folderPath);
	        if (!dir.exists() && !dir.mkdirs()) {
	            logger.error("Failed to create directory: " + folderPath);
	            return null;
	        }

	        if (imageBytes != null) {
	            long nowTime = System.currentTimeMillis();
	            imageName1 = ext.equalsIgnoreCase("jpeg") ? nowTime + ".jpg" : nowTime + "." + ext;
	        }

	        Path path = Paths.get(folderPath, imageName1);
	        logger.info("Final file path: {}", path);

	        if (imageBytes != null) {
	            Files.write(path, imageBytes);
	        }

	    } catch (Exception e) {
	        logger.error("Exception in saveAllMultiImages: ", e);
	    }

	    logger.info("Method : saveAllMultiImages ends");
	    return imageName1;
	}

	
	

	public String saveAllFolderMultiImages(byte[] imageBytes, String ext, String folderName) {
		logger.info("Method : saveAllMultiImages starts" + folderName);
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
			String filepath = folderName + "/";
			logger.info("Folder filepath@@@@" + filepath);

			Path path = Paths.get(filepath + imageName1);
			logger.info("Folder path@@@@" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}

	public String saveAllMultiImagesFolder(byte[] imageBytes, String ext, String folderName) {
		logger.info("Method : saveAllMultiImagesFolder starts" + folderName);
		String imageName1 = null;
		System.out.print(folderName.split("-")[0]);
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}
			}
			String filepath = env.getFileUploadDms() + folderName + "/";
			Path path = Paths.get(filepath + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}

	// View Document Control Details
	@SuppressWarnings("unchecked")
	@GetMapping("document-control-view")
	public @ResponseBody Object documentControlView(HttpSession session, @RequestParam String pageno) {
		logger.info("Method :documentControlView starts");
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
			resp = restClient
					.getForObject(
							env.getEdms() + "rest-documentControlView?userId=" + userId + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&pageno=" + pageno,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :documentControlView ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("document-control-edit")
	public @ResponseBody JsonResponse<Object> editDocumentControl(@RequestParam String id, HttpSession session) {
		logger.info("Method :editDocumentControl starts");
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

			resp = restClient.getForObject(env.getEdms() + "rest-editDocumentControl?id=" + id + "&userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editDocumentControl ends" + resp);
		return resp;
	}

	// Add Documents

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-upload-folder")
	public @ResponseBody JsonResponse<Object> uploadFolder(HttpSession session,
			@RequestBody DocumentControlModel documentControlModel)

	{

		logger.info("Method : upload folder starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		String folderPathDetails = documentControlModel.getFolderPath();
		System.out.println("folderPathDetails" + folderPathDetails);
		String folderDetails = "";
		if (StringUtil.isNull(folderPathDetails)) {
			folderDetails = documentControlModel.getFolderName() + "/";
		} else {
			folderDetails = folderPathDetails + documentControlModel.getFolderName() + "/";
		}
		String filepath1 = env.getFileUploadDms() + folderDetails;
		System.out.println("filepath1filepath1: " + filepath1);
		System.out.println("filepath1filepath1: ");

		logger.info("Folder Name---------" + documentControlModel.getFolderName());
		String folderPath = env.getFileUploadDms() + folderDetails;
		logger.info("folderPath---------" + folderPath);
		// Construct the filepath
		// String[] folderParts = folderDetails;
		// logger.info("folderParts---------" + folderParts);

		// Create Path object
		Path path = Paths.get(folderPath + documentControlModel.getDocumentName().toString());

		// logger.info("Folder Path---------" + folderParts);

		try {
			// Create directories at the specified path
			if (!Files.exists(path)) {
				Files.createDirectories(path);
				logger.info("Folder Created Successfully at---------" + path);
			} else {
				logger.info("Folder already exists at---------" + path);
			}

			File fileToUpload = new File(path.toString());
			logger.info("Folder fileToUpload---------" + fileToUpload);
			Path destinationPath = path;
			logger.info("destinationPath---------" + destinationPath);
			Files.copy(fileToUpload.toPath(), destinationPath);
			logger.info("File uploaded successfully to---------" + destinationPath);

		} catch (IOException e) {
			logger.info("Failed to create directory or upload file:" + e.getMessage());
		}
		documentControlModel.setCreatedBy(userId);
		for (DocumentControlModel r : documentControlModel.getDocumentList()) {

			if (r.getImageNameEdit() != null && r.getImageNameEdit() != "") {
				// logger.info("hello doc getImageNameEdit---------"+r.getImageNameEdit());
				r.setFileName(r.getImageNameEdit());

			} else {

				String[] extension = r.getFileName().split("\\.");
				int lastindex = extension.length - 1;
				for (String s1 : r.getDocumentFile()) {
					String imageName = "";
					try {
						byte[] bytes = Base64.getDecoder().decode(s1);
						imageName = saveAllFolderMultiImages(bytes, extension[lastindex], path.toString());
						logger.info("Folder imageName---------" + imageName);
						r.setFileName(imageName);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String fileUrl = env.getBaseURL() + "document/dms/" + folderDetails
							+ documentControlModel.getDocumentName() + "/" + imageName;
					logger.info("fileUrl---------" + fileUrl);
					r.setDocumentUrl(fileUrl);
				}
			}
			r.setOrganization(organization);
			r.setOrgDivision(orgDivision);
			r.setCreatedBy(userId);
		}

		// logger.info("documentControlModel===" + documentControlModel);
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-uploadfolder", documentControlModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : upload folder ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("document-control-edit-uploadfolder")
	public @ResponseBody JsonResponse<Object> editUploadFolder(@RequestParam String id, HttpSession session) {
		logger.info("Method :editUploadFolder starts");
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

			resp = restClient.getForObject(env.getEdms() + "rest-editUploadFolder?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editUploadFolder ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-delete-folder-files")
	public @ResponseBody JsonResponse<Object> deleteFolderFiles(HttpSession session,
			@RequestBody DocumentControlModel documentControlModel)

	{
		logger.info("Method : deleteFolderFiles starts" + documentControlModel);
		// System.out.print("@@@@@@@@@@@@@@@@@@@@"+documentControlModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		documentControlModel.setCreatedBy(userId);
		if (documentControlModel.getImageNameEdit() != null || documentControlModel.getImageNameEdit() != "") {
			String filePath = env.getFileUploadDms() + documentControlModel.getFolderName() + "/"
					+ documentControlModel.getImageNameEdit() + "/";
			System.out.print("@@@@@@@@@@@@@@@@@@@@" + filePath);
			try {

				Files.deleteIfExists(Paths.get(filePath));

			} catch (NoSuchFileException nsf) {
				logger.error("Couldn't find the JSON file...");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("documentControlModel===" + documentControlModel);
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-deletefolderfiles", documentControlModel,
					JsonResponse.class);
			System.out.println("Resp ===" + documentControlModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : deleteFolderFiles ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-add-link")
	public @ResponseBody JsonResponse<Object> documentControlAddLink(HttpSession session,
			@RequestBody DocumentControlModel documentControlModel)

	{
		logger.info("Method : documentControlAddLink starts");
//			System.out.println("@@@@@@@@@@@@@@@@@@@@"+documentControlModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		documentControlModel.setOrganization(organization);
		documentControlModel.setOrgDivision(orgDivision);
		documentControlModel.setCreatedBy(userId);
		logger.info("documentControlModel===" + documentControlModel);
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-addLink", documentControlModel, JsonResponse.class);
			System.out.println("Resp ===" + documentControlModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : documentControlAddLink ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("document-control-edit-link")
	public @ResponseBody JsonResponse<Object> editLink(@RequestParam String id, HttpSession session) {
		logger.info("Method :editUploadFolder starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restClient.getForObject(env.getEdms() + "rest-editLink?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editUploadFolder ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("document-control-delete")
	public @ResponseBody JsonResponse<Object> deleteDocumentControl(@RequestParam String id, HttpSession session) {

		logger.info("Method : deleteDocumentControl starts" + id);

		JsonResponse<Object> response = new JsonResponse<Object>();
		try {
			response = restClient.getForObject(env.getEdms() + "rest-deleteControl?id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : deleteDocumentControl ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-merg-files")
	public @ResponseBody JsonResponse<Object> mergFiles(HttpSession session,
			@RequestBody List<DocumentControlModel> documentControlModel) throws IOException

	{
		logger.info("Method : mergFiles starts" + documentControlModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		List<String> filePathList = new ArrayList<>();
		for (DocumentControlModel r : documentControlModel) {
			String filepath = env.getFileUploadDms() + r.getFolderName().split("-")[0].replaceAll("\\s", "") + "/"
					+ r.getFolderName().split("-")[1].replaceAll("\\s", "") + "/" + r.getFileName();
			filePathList.add(filepath);
		}
		XWPFDocument mergedDocument = new XWPFDocument();
		for (String filePath : filePathList) {
			FileInputStream files = new FileInputStream(new File(filePath));
			XWPFDocument doc = new XWPFDocument(files);
			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				XWPFParagraph newParagraph = mergedDocument.createParagraph();
				for (XWPFRun run : paragraph.getRuns()) {
					XWPFRun newRun = newParagraph.createRun();
					newRun.setText(run.text());
					newRun.setBold(run.isBold());
					newRun.setFontSize(10);
				}
			}

			files.close();
		}
		try (FileOutputStream fos = new FileOutputStream(env.getFileUploadDms() + "/" + "MergedDocument.docx")) {
			mergedDocument.write(fos);
		}
		logger.info("documentControlModel===" + documentControlModel);

		logger.info("Method : mergFiles ends");

		return resp;
	}

	// View Document Details
	@SuppressWarnings("unchecked")
	@GetMapping("document-control-details")
	public @ResponseBody Object viewDocumentDetails(HttpSession session, @RequestParam String docId) {
		logger.info("Method :viewDocumentDetails starts");
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
			resp = restClient
					.getForObject(
							env.getEdms() + "rest-viewDocumentDetails?userId=" + userId + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&docId=" + docId,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewDocumentDetails ends");
		return resp;
	}

	// Add access
	@SuppressWarnings("unchecked")
	@PostMapping("document-control-access-add")
	public @ResponseBody JsonResponse<Object> saveAccess(HttpSession session,
			@RequestBody List<DocumentManageAccessModel> accessModel)

	{
		logger.info("Method : saveAccess starts" + accessModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		System.out.print(accessModel + "@@@@@@@@accessModel");
		for (DocumentManageAccessModel m : accessModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}

		logger.info("saveUserAccess===" + accessModel);
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-saveUserAccess", accessModel, JsonResponse.class);
			System.out.println("Resp ===" + accessModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAccess ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("document-control-accessView")
	public @ResponseBody JsonResponse<Object> documentAccessView(@RequestParam String id, HttpSession session) {
		logger.info("Method :documentAccessView starts");
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

			resp = restClient.getForObject(env.getEdms() + "rest-documentAccessView?id=" + id + "&userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :documentAccessView ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-modify")
	public @ResponseBody JsonResponse<Object> uploadDocumentModify(HttpSession session,
			@RequestBody List<DocumentControlModel> documentControlModel)

	{
		logger.info("Method : uploadDocumentModify starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		String userName = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userName = (String) session.getAttribute("USER_NAME");
		} catch (Exception e) {

		}
		String folderPathDetails = documentControlModel.get(0).getFolderPath();
		System.out.println("folderPathDetails" + folderPathDetails);
		String folderPath = "";
		for (DocumentControlModel r : documentControlModel) {
			if (StringUtil.isNull(folderPathDetails)) {
				folderPath = r.getFolderName() + "/";
			} else {
				folderPath = folderPathDetails + r.getFolderName() + "/";
			}
			System.out.println("folderPath" + folderPath);
			if (r.getImageNameEdit() != null && r.getImageNameEdit() != "") {
				logger.info("hello doc getImageNameEdit---------" + r.getImageNameEdit());
				r.setFileName(r.getImageNameEdit());

			} else {

				String[] extension = r.getFileName().split("\\.");
				int lastindex = extension.length - 1;
				String imageName = "";
				for (String s1 : r.getDocumentFile()) {
					String filepath = env.getFileUploadDms() + folderPath;

					Path path = Paths.get(filepath);
					try {
						byte[] bytes = Base64.getDecoder().decode(s1);
						imageName = saveAllMultiImages(bytes, extension[lastindex], folderPath);
						r.setFileName(imageName);

					} catch (Exception e) {
						e.printStackTrace();

					}

					String fileUrl = env.getBaseURL() + "document/dms/" + folderPath + imageName;
					System.out.println("@@@@fileUrl" + fileUrl);
					r.setDocumentUrl(fileUrl);
				}
			}

			if (r.getImageNameEditAccessImage() != null && r.getImageNameEditAccessImage() != "") {
				logger.info("hello doc getImageNameEdit---------" + r.getImageNameEditAccessImage());
				r.setImageNameEditAccessImage(r.getImageNameEditAccessImage());

			} else {
				String[] extension = r.getFileNameAccessImage().split("\\.");
				int lastindex = extension.length - 1;
				String imageName = "";
				for (String s1 : r.getDocumentFileAccessImage()) {
					String filepath = env.getFileUploadDms() + folderPath;

					Path path = Paths.get(filepath);
					try {
						byte[] bytes = Base64.getDecoder().decode(s1);
						imageName = saveAllMultiImages(bytes, extension[lastindex], folderPath);
						r.setFileNameAccessImage(imageName);

					} catch (Exception e) {
						e.printStackTrace();

					}

					String fileUrl1 = env.getBaseURL() + "document/dms/" + folderPath + imageName;
					System.out.println("@@@@fileUrl" + fileUrl1);
					r.setDocumentUrl(fileUrl1);
				}
			}
			r.setOrganization(organization);
			r.setOrgDivision(orgDivision);
			r.setCreatedBy(userId);
			r.setEmpName(userName);
		}

		try {
			resp = restClient.postForObject(env.getEdms() + "rest-uploadDocumentModify", documentControlModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : uploadDocumentModify ends");

		return resp;
	}

	@GetMapping(value = { "document-control-get-alluserEmailId" })
	public @ResponseBody List<DocumentControlModel> getAlluserEmailId(@RequestParam String userid, String docid,
			HttpSession session) {
		logger.info("Method : getAlluserEmailId starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<DocumentControlModel> productList = new ArrayList<DocumentControlModel>();
		if (userid != null && userid != "") {
			logger.info("IDD" + userid);
			try {
				DocumentControlModel[] model = restClient.getForObject(env.getEdms() + "getAlluserEmailId?userid="
						+ userid + "&docid=" + docid + "&org=" + orgName + "&orgDiv=" + orgDivision,
						DocumentControlModel[].class);

				productList = Arrays.asList(model);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getAlluserEmailId ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-upload-folder-modify")
	public @ResponseBody JsonResponse<Object> uploadFolderModify(HttpSession session,
			@RequestBody DocumentControlModel documentControlModel) {
		logger.info("Method :  uploadFolderModify starts" + documentControlModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		String filepath = env.getFileUploadDms()
				+ documentControlModel.getFolderName().split("-")[0].replaceAll("\\s", "") + "/"
				+ documentControlModel.getFolderName().split("-")[1].replaceAll("\\s", "") + "/";

		Path path = Paths.get(filepath);
		System.out.println("filepath: " + filepath);
		System.out.println("path: " + path);

		try {
			// Create directories at the specified path
			if (!Files.exists(path)) {
				Files.createDirectories(path);
				System.out.println("Folder Created Successfully at " + path);
			} else {
				System.out.println("Folder already exists at " + path);
			}
		} catch (IOException e) {
			System.out.println("Failed to create directory: " + e.getMessage());
		}
		documentControlModel.setCreatedBy(userId);
		documentControlModel.setOrganization(organization);
		documentControlModel.setOrgDivision(orgDivision);
		for (DocumentControlModel r : documentControlModel.getDocumentList()) {

			if (r.getImageNameEdit() != null && r.getImageNameEdit() != "") {
				// logger.info("hello doc getImageNameEdit---------"+r.getImageNameEdit());
				r.setFileName(r.getImageNameEdit());

			} else {
				String[] extension = r.getFileName().split("\\.");
				int lastindex = extension.length - 1;
				for (String s1 : r.getDocumentFile()) {
					String imageName = "";
					try {
						byte[] bytes = Base64.getDecoder().decode(s1);
						imageName = saveAllFolderMultiImages(bytes, extension[lastindex], path.toString());
						System.out.println("@@@@@@@@@@@@imageName" + imageName);
						r.setFileName(imageName);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String fileUrl = env.getBaseURL() + "document/dms/"
							+ documentControlModel.getFolderName().replace(" - ", "/").replaceAll("\\s+", "") + "/"
							+ imageName;
					System.out.println("@@@@fileUrl" + fileUrl);
					r.setDocumentUrl(fileUrl);
				}
			}
			r.setOrganization(organization);
			r.setOrgDivision(orgDivision);
			r.setCreatedBy(userId);
		}

		// logger.info("documentControlModel===" + documentControlModel);
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-uploadfolderModify", documentControlModel,
					JsonResponse.class);
			System.out.println("Resp ===" + documentControlModel);
			logger.info(documentControlModel.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :  uploadFolderModify ends");

		return resp;
	}

	// View Document Control History Details
	@SuppressWarnings("unchecked")
	@GetMapping("document-control-view-history")
	public @ResponseBody Object documentControlViewHistory(HttpSession session, @RequestParam String docId) {
		logger.info("Method :documentControlViewHistory starts");
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
			resp = restClient
					.getForObject(
							env.getEdms() + "rest-documentControlViewHistory?userId=" + userId + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&docId=" + docId,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :documentControlViewHistory ends");
		return resp;
	}

	// View Start Wrok Flow
	@SuppressWarnings("unchecked")
	@GetMapping("document-control-view-workFlow")
	public @ResponseBody Object documentControlViewworkFlow(HttpSession session, @RequestParam String docId) {
		logger.info("Method :documentControlViewworkFlow starts");
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
			resp = restClient
					.getForObject(
							env.getEdms() + "rest-documentControlViewworkFlow?userId=" + userId + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&docId=" + docId,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :documentControlViewworkFlow ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-workFlow-add")
	public @ResponseBody JsonResponse<Object> addWorkFlow(HttpSession session,
			@RequestBody WorkFlowModel workFlowModel) {
		logger.info("Method : addWorkFlow starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		workFlowModel.setUserId(userId);
		workFlowModel.setOrganization(organization);
		workFlowModel.setOrgDiv(orgDivision);
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-addWorkFlow", workFlowModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addWorkFlow ends");

		return resp;
	}

	// send Mail

	@SuppressWarnings("unchecked")
	@GetMapping("document-control-send-email-toall")
	public @ResponseBody JsonResponse<List<DocumentControlModel>> sendMailToAllAccessor(HttpSession session,
			@RequestParam String userid, String mailDate, String subject, String messages, String emailId, String empId,
			String empName, String docid, String fileName, String groupId, String documentUrl) throws IOException {

		logger.info("Method : sendMailToAllAccessor starts");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> documentUrls = Arrays.asList(documentUrl.split(",")); // Assuming URLs are comma-separated

		JsonResponse<List<DocumentControlModel>> response = new JsonResponse<>();

		try {
			response = restClient.getForObject(env.getEdms() + "sendMailToAllAccessor?userid=" + userid + "&mailDate="
					+ mailDate + "&subject=" + subject + "&messages=" + messages + "&emailId=" + emailId + "&empId="
					+ empId + "&empName=" + empName + "&docid=" + docid + "&fileName=" + fileName + "&groupId="
					+ groupId + "&documentUrl=" + documentUrl + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("response=====" + response);
		logger.info("Method : sendMailToAllAccessor ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("document-control-folder-modify")
	public @ResponseBody JsonResponse<Object> uploadFolderDocModify(HttpSession session,
			@RequestBody List<DocumentControlModel> documentControlModel) {
		logger.info("Method : uploadFolderDocModify starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		String folderPathDetails = documentControlModel.get(0).getFolderPath();
		System.out.println("folderPathDetails" + folderPathDetails);
		String folderDetails = "";
		if (StringUtil.isNull(folderPathDetails)) {
			folderDetails = documentControlModel.get(0).getFolderName() + "/";
		} else {
			folderDetails = folderPathDetails + documentControlModel.get(0).getFolderPathNameId() + "/";
		}
		System.out.println(documentControlModel.get(0).getFolderPathNameId());
		for (DocumentControlModel r : documentControlModel) {
			if (r.getImageNameEdit() != null && r.getImageNameEdit() != "") {
				logger.info("hello doc getImageNameEdit---------" + r.getImageNameEdit());
				r.setFileName(r.getImageNameEdit());

			} else {
				String filepath = env.getFileUploadDms() + folderDetails;
				System.out.println("filepath" + filepath);
				Path path = Paths.get(filepath);
				String[] extension = r.getFileName().split("\\.");
				int lastindex = extension.length - 1;
				String imageName = "";
				for (String s1 : r.getDocumentFile()) {
					try {
						byte[] bytes = Base64.getDecoder().decode(s1);
						imageName = saveAllMultiImagesFolder(bytes, extension[lastindex], folderDetails);
						r.setFileName(imageName);

					} catch (Exception e) {
						e.printStackTrace();

					}
					String fileUrl = env.getBaseURL() + "document/dms/" + folderDetails + imageName;

					System.out.println("@@@@fileUrl" + fileUrl);
					r.setDocumentUrl(fileUrl);
				}
			}
			r.setOrganization(organization);
			r.setOrgDivision(orgDivision);
			r.setCreatedBy(userId);
		}
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-documentFolderModify", documentControlModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : uploadFolderDocModify ends");
		return resp;
	}

	// View Document Version Control Details
	@SuppressWarnings("unchecked")
	@GetMapping("document-control-version-details")
	public @ResponseBody Object viewDocumentControlVersion(HttpSession session, @RequestParam String docId) {
		logger.info("Method :viewDocumentControlVersion starts");
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
			resp = restClient
					.getForObject(
							env.getEdms() + "rest-viewDocumentControlVersion?userId=" + userId + "&organization="
									+ organization + "&orgDivision=" + orgDivision + "&docId=" + docId,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewDocumentControlVersion ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "document-control-folderList" })
	public @ResponseBody JsonResponse<Object> folderList(Model model, @RequestBody String status, HttpSession session,
			BindingResult result) {
		logger.info("Method : folderList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
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
			res = restClient.getForObject(env.getEdms() + "rest-folderList?id=" + status + "&userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : folderList ends" + res);
		return res;

	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "document-control-lockFile" })
	public @ResponseBody JsonResponse<Object> lockFile(@RequestBody DocumentControlModel documentControlModel,
			HttpSession session) {
		logger.info("Method : lockFile function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		documentControlModel.setCreatedBy(userId);
		documentControlModel.setOrganization(organization);
		documentControlModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getEdms() + "rest-lockFile", documentControlModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : lockFile function Ends");
		return resp;
	}

	// Get Notification
	@SuppressWarnings("unchecked")
	@GetMapping(value = "document-control-notification")
	public @ResponseBody JsonResponse<Object> documentNotification(@RequestParam String userId,
			@RequestParam String type) {

		logger.info("Method: documentNotification starts" + userId);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(
					env.getEdms() + "rest-getDocumentNotification?userId=" + userId + "&type=" + type,
					JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
		}

		logger.info("Method: documentNotification starts");
		return resp;
	}

	// Notification Update

	@SuppressWarnings("unchecked")
	@GetMapping("document-control-notificationUpdate")
	public @ResponseBody JsonResponse<Object> notificationUpdate(@RequestParam String id, HttpSession session) {
		logger.info("Method :notificationUpdate starts");
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

			resp = restClient.getForObject(env.getEdms() + "rest-notificationUpdate?id=" + id + "&userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :notificationUpdate ends" + resp);
		return resp;
	}
}
