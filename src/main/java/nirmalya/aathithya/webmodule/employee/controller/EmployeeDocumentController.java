package nirmalya.aathithya.webmodule.employee.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
public class EmployeeDocumentController {
	Logger logger = LoggerFactory.getLogger(EmployeeDocumentController.class);

	@Autowired
	EnvironmentVaribles env;

	/**
	 * document controller to load images instantly
	 *
	 */
	@RequestMapping(value = "document/employee/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocument(@PathVariable(value = "docname") String docname) throws IOException {
		logger.info("Method : getDocument controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadEmployee());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png") || docname.endsWith(".PNG")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg") || docname.endsWith(".JPEG")
				|| docname.endsWith(".JPG")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf") || docname.endsWith(".PDF")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

	@RequestMapping(value = "document/employee/thumb/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentThumb(@PathVariable(value = "docname") String docname) throws IOException {
		logger.info("Method : image controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadEmployee());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png") || docname.endsWith(".PNG")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg") || docname.endsWith(".JPEG")
				|| docname.endsWith(".JPG")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf") || docname.endsWith(".PDF")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

	@RequestMapping(value = "document/property/{docname}")

	@ResponseBody
	public ResponseEntity<byte[]> getDocumentPropertyFunRecord(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentProperty controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadProperty());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png") || docname.endsWith(".PNG")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg") || docname.endsWith(".JPEG")
				|| docname.endsWith(".JPG")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf") || docname.endsWith(".PDF")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (docname.endsWith(".mp4")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(bytearr);
		} else {
			logger.info("Method : getDocumentProperty controller function end");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

	@RequestMapping(value = "document/crm/{docname}")

	@ResponseBody
	public ResponseEntity<byte[]> getDocumentCrmFunRecord(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentCrmFunRecord controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadCrmUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/userQrCode/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentUserQrCode(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentUserQrCode controller function starts");

		File dir = ResourceUtils.getFile(env.getUserQrCode());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/training/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentTraining(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentTraining controller function starts");
		logger.info("Doccc" + docname);
		File dir = ResourceUtils.getFile(env.getFileUploadTraining());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/staffQrCode/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentStaffQrCode(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentStaffQrCode controller function starts");

		File dir = ResourceUtils.getFile(env.getStaffQrCode());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/communication/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentCommunication(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : image controller function starts");

		File dir = ResourceUtils.getFile(env.getCommunicationDocUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		System.out.println("file===" + file);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/grcDocUrl/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getgrcDocUrlData(@PathVariable(value = "docname") String docname) throws IOException {
		logger.info("Method : getgrcDocUrlData controller function starts");

		File dir = ResourceUtils.getFile(env.getGrcDocUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/assetDocUrl/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentAssetDocUrl(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentAssetDocUrl controller function starts");

		File dir = ResourceUtils.getFile(env.getAssetDocUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/ticketDocs/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentTicket(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentTicket controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadticketUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/notice/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getNoticeDocumentUrl(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getNoticeDocumentUrl controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadnoticeUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);

		byte[] bytearr = Files.readAllBytes(file.toPath());

		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

	@RequestMapping(value = "document/resign/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getResignationDocumentUrl(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getResignationDocumentUrl controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadResignationUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		String lowerName = docname.toLowerCase();
		if (lowerName.endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (lowerName.endsWith(".jpeg") || lowerName.endsWith(".jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (lowerName.endsWith(".pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (lowerName.endsWith(".doc")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/msword")).body(bytearr);
		} else if (lowerName.endsWith(".docx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(bytearr);
		} else if (lowerName.endsWith(".xls")) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytearr);
		} else if (lowerName.endsWith(".xlsx")) {
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bytearr);
		} else {
			// Fallback for unknown types, force download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docname + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytearr);
		}
	}

}