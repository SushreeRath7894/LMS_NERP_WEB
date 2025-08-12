package nirmalya.aathithya.webmodule.trial.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.sales.controller.SalesDocumentController;

public class IssueDocumentController {
	
	Logger logger = LoggerFactory.getLogger(IssueDocumentController.class);
	
	@Autowired
	EnvironmentVaribles env;
	
	@RequestMapping(value="document/issueHistory/{docname}")
	@ResponseBody
	
	public ResponseEntity<byte[]> getDocument(@PathVariable(value="docname")String docname)throws IOException{
		logger.info("Method : getDocument controller function starts");
		
		File dir = ResourceUtils.getFile(env.getFileUploadTrialUrl());
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
	
	/*@RequestMapping(value="document/refuelModel/{docname}")
	@ResponseBody
	
	public ResponseEntity<byte[]> getRefuel(@PathVariable(value="docname")String docname)throws IOException{
		logger.info("Method : getDocument controller function starts");
		
		File dir = ResourceUtils.getFile(env.getFileUploadTrialUrl());
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
	}*/

}
