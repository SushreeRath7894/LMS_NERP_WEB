package nirmalya.aathithya.webmodule.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import nirmalya.aathithya.webmodule.sales.controller.SalesDocumentController;

public class DocumentController {

	Logger logger = LoggerFactory.getLogger(DocumentController.class);
	/**
	 * document controller to load images instantly
	 *
	 */
	@Autowired
	EnvironmentVaribles env;
	
	@RequestMapping(value="document/purchaseOrder/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocument(@PathVariable(value="docname")String docname)throws IOException{
		logger.info("Method : getDocument controller function starts");
		
		File dir = ResourceUtils.getFile(env.getFileUploadSales());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if(docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		}
		else if(docname.endsWith(".jpeg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		}
		else if(docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		}
		else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}
	
	@RequestMapping(value="document/purchaseOrder/thumb/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentThumb(@PathVariable(value="docname")String docname)throws IOException{
		logger.info("Method : image controller function starts");
		
		File dir = ResourceUtils.getFile(env.getFileUploadSales()+"thumb");
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if(docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		}
		else if(docname.endsWith(".jpeg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		}
		else if(docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		}
		else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}
	@RequestMapping(value="document/document/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentThumbData(@PathVariable(value="docname")String docname)throws IOException{
		logger.info("Method : image controller function starts");
		
		File dir = ResourceUtils.getFile(env.getFileUploadDocumenttUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if(docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		}
		else if(docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		}
		else if(docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		}
		else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}
	@RequestMapping(value="document/imageUpload/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentImageUpload(@PathVariable(value="docname")String docname)throws IOException{
		logger.info("Method : image controller function starts");
		
		File dir = ResourceUtils.getFile(env.getFileUploadProcurement());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if(docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		}
		else if(docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		}
		else if(docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		}
		else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}
	

	@RequestMapping(value="document/taskdocument/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentTaskData(@PathVariable(value="docname")String docname)throws IOException{
		logger.info("Method : image controller function starts");
		
		File dir = ResourceUtils.getFile(env.getFileUploadtaskdocumentUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if(docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		}
		else if(docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		}
		else if(docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		}
		else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}
	@RequestMapping(value="document/taskdocument/thumb/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentTask(@PathVariable(value="docname")String docname)throws IOException{
		logger.info("Method : image controller function starts");
		
		File dir = ResourceUtils.getFile(env.getFileUploadtaskdocumentUrl()+"thumb");
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if(docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		}
		else if(docname.endsWith(".jpeg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		}
		else if(docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		}
		else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}	
	
}
