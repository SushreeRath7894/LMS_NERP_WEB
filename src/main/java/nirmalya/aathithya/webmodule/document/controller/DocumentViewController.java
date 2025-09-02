/**
 * 
 */
package nirmalya.aathithya.webmodule.document.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
public class DocumentViewController {
	@Autowired
	EnvironmentVaribles env;
	Logger logger = LoggerFactory.getLogger(DocumentViewController.class);

	/**
	 * document controller to load images instantly
	 *
	 */
	/*
	 * @RequestMapping(value = "document/image/{docname}")
	 * 
	 * @ResponseBody
	 * 
	 * public ResponseEntity<byte[]> getDocument(@PathVariable(value = "docname")
	 * String docname) throws IOException {
	 * logger.info("Method : getDocument controller function starts");
	 * 
	 * File dir = ResourceUtils.getFile(env.getFileUploadDocumenttUrl()); File file
	 * = new File(dir.getAbsolutePath() + "/" + docname); byte[] bytearr =
	 * Files.readAllBytes(file.toPath());
	 * 
	 * if (docname.endsWith(".png")) { return
	 * ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr); } else if
	 * (docname.endsWith(".jpeg") || docname.endsWith(".jpg")) { // Fix: added
	 * ".jpg" return
	 * ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr); } else
	 * if (docname.endsWith(".pdf")) { return
	 * ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr); }
	 * else { return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr); }
	 * }
	 */
	
	@RequestMapping(value="document/image/{docname}")
	@ResponseBody
	public HttpEntity<byte[]> getDocumentThumbData(@PathVariable(value="docname")String docname)throws IOException{
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
		
		else if(docname.endsWith(".docx")) {
			logger.info("Method : getDocument controller function starts");
			HttpHeaders header = new HttpHeaders();
		    //header.setContentType(MediaType.ALL);
		    header.set(HttpHeaders.CONTENT_DISPOSITION,
		                   "attachment; filename=" + docname.replace(" ", "_"));
		    header.setContentLength(bytearr.length);

		    return new HttpEntity<byte[]>(bytearr, header);
			//return ResponseEntity.ok().contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document").body(bytearr);
		}
		else if(docname.endsWith(".xlsx")) {
			logger.info("Method : getDocument controller function starts");
			//return ResponseEntity.ok().body(bytearr);
			HttpHeaders header = new HttpHeaders();
		    //header.setContentType(MediaType.ALL);
		    header.set(HttpHeaders.CONTENT_DISPOSITION,
		                   "attachment; filename=" + docname.replace(" ", "_"));
		    header.setContentLength(bytearr.length);

		    return new HttpEntity<byte[]>(bytearr, header);
		}
		
		else if(docname.endsWith(".m4a")) {
			logger.info("Method : getDocument controller function starts");
			//return ResponseEntity.ok().body(bytearr);
			HttpHeaders header = new HttpHeaders();
		    //header.setContentType(MediaType.ALL);
		    header.set(HttpHeaders.CONTENT_DISPOSITION,
		                   "attachment; filename=" + docname.replace(" ", "_"));
		    header.setContentLength(bytearr.length);

		    return new HttpEntity<byte[]>(bytearr, header);
		}
		
		
		else if(docname.endsWith(".mp4")) {
			logger.info("Method : getDocument controller function starts");
			//return ResponseEntity.ok().body(bytearr);
			HttpHeaders header = new HttpHeaders();
		    //header.setContentType(MediaType.ALL);
		    header.set(HttpHeaders.CONTENT_DISPOSITION,
		                   "attachment; filename=" + docname.replace(" ", "_"));
		    header.setContentLength(bytearr.length);

		    return new HttpEntity<byte[]>(bytearr, header);
		}
		else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}


	/**
	 * document controller to load images instantly
	 *
	 */
	@RequestMapping(value = "document/excel/{docname}")
	@ResponseBody
	public HttpServletResponse getDocument1(@PathVariable(value = "docname") String docname,
			HttpServletResponse response) throws IOException {
		logger.info("Method : getDocument controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadDocumenttUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return null;
		} else if (docname.endsWith(".jpeg")) {
			logger.info("Method : getDocument controller function starts");
			return null;
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return null;
		} else {
			logger.info("Method : getDocument controller function starts");
			response.setHeader("Content-disposition", "attachment; filename=" + docname);
			File xls = new File(docname);
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			int length = 0;
			byte[] buffer = new byte[8192];
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
			return response;
		}
	}

	@RequestMapping(value = "document/image/thumb/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentThumb(@PathVariable(value = "docname") String docname) throws IOException {
		logger.info("Method : image controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadDocumenttUrl() + "/thumb");
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

	/**
	 * document controller to show training details
	 *
	 *//*
		 * @RequestMapping(value="document/training/{docname}")
		 * 
		 * @ResponseBody
		 * 
		 * public ResponseEntity<byte[]>
		 * getDocumentTraining(@PathVariable(value="docname")String docname)throws
		 * IOException{
		 * logger.info("Method : getDocumentTraining controller function starts");
		 * logger.info("@@"+docname); File dir =
		 * ResourceUtils.getFile(env.getTrainingUrl()); File file = new
		 * File(dir.getAbsolutePath() + "/" + docname); byte[] bytearr =
		 * Files.readAllBytes(file.toPath()); if(docname.endsWith(".png")) {
		 * logger.info("Method : getDocument controller function starts"); return
		 * ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr); } else
		 * if(docname.endsWith(".jpeg")) {
		 * logger.info("Method : getDocument controller function starts"); return
		 * ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr); } else
		 * if(docname.endsWith(".pdf")) {
		 * logger.info("Method : getDocument controller function starts"); return
		 * ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr); }
		 * else {
		 * logger.info("Method : getDocumentTraining controller function starts");
		 * return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr); } }
		 */

	/*
	 * @RequestMapping(value = "document/dms/**")
	 * 
	 * @ResponseBody public ResponseEntity<byte[]> getDocumentDms(HttpServletRequest
	 * request) throws IOException {
	 * logger.info("Method : getDocumentDms controller function starts");
	 * 
	 * // Extract the requested file path and decode it String fullPath =
	 * request.getRequestURI().substring(request.getRequestURI().indexOf(
	 * "document/dms/") + 13); fullPath = URLDecoder.decode(fullPath,
	 * StandardCharsets.UTF_8.name()); // Decode URL-encoded characters
	 * logger.info("Requested file path: " + fullPath);
	 * 
	 * String baseDirPath = env.getFileUploadDms();
	 * logger.info("Base directory path: " + baseDirPath);
	 * 
	 * File file = new File(baseDirPath, fullPath);
	 * logger.info("Resolved file path: " + file.getAbsolutePath());
	 * 
	 * if (!file.exists() || !file.isFile()) { logger.error("File not found: " +
	 * file.getAbsolutePath()); return ResponseEntity.notFound().build(); }
	 * 
	 * byte[] bytearr = Files.readAllBytes(file.toPath());
	 * 
	 * String contentType; if (fullPath.endsWith(".png")) { contentType =
	 * MediaType.IMAGE_PNG_VALUE; } else if (fullPath.endsWith(".jpeg") ||
	 * fullPath.endsWith(".jpg")) { contentType = MediaType.IMAGE_JPEG_VALUE; } else
	 * if (fullPath.endsWith(".pdf")) { contentType =
	 * MediaType.APPLICATION_PDF_VALUE; } else { contentType =
	 * MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default for unknown file types }
	 * 
	 * logger.info("Method : getDocumentDms controller function ends"); return
	 * ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(
	 * bytearr); }
	 */
	
	@RequestMapping(value = "document/dms/**")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentDms(HttpServletRequest request) throws IOException {
	    logger.info("Method : getDocumentDms controller function starts");

	    String fullPath = request.getRequestURI().substring(request.getRequestURI().indexOf("document/dms/") + 13);
	    fullPath = URLDecoder.decode(fullPath, StandardCharsets.UTF_8.name());
	    logger.info("Raw decoded path: '" + fullPath + "'");

	    String[] parts = fullPath.split("/");
	    StringBuilder normalizedPathBuilder = new StringBuilder();
	    for (String part : parts) {
	        if (!part.trim().isEmpty()) {
	            normalizedPathBuilder.append(part.trim()).append(File.separator);
	        }
	    }

	    if (normalizedPathBuilder.length() > 0 && normalizedPathBuilder.charAt(normalizedPathBuilder.length() - 1) == File.separatorChar) {
	        normalizedPathBuilder.setLength(normalizedPathBuilder.length() - 1);
	    }

	    String baseDirPath = env.getFileUploadDms();
	    String resolvedPath = baseDirPath + normalizedPathBuilder;

	    logger.info("Resolved file path: " + resolvedPath);

	    File file = new File(resolvedPath);
	    if (!file.exists() || !file.isFile()) {
	        logger.error("File not found: " + file.getAbsolutePath());
	        return ResponseEntity.notFound().build();
	    }

	    byte[] bytearr = Files.readAllBytes(file.toPath());

	    String contentType;
	    if (fullPath.toLowerCase().endsWith(".png")) {
	        contentType = MediaType.IMAGE_PNG_VALUE;
	    } else if (fullPath.toLowerCase().endsWith(".jpeg") || fullPath.toLowerCase().endsWith(".jpg")) {
	        contentType = MediaType.IMAGE_JPEG_VALUE;
	    } else if (fullPath.toLowerCase().endsWith(".pdf")) {
	        contentType = MediaType.APPLICATION_PDF_VALUE;
	    } else {
	        contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
	    }

	    logger.info("Method : getDocumentDms controller function ends");
	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(contentType))
	            .body(bytearr);
	}

	
	@RequestMapping(value = "document/image/omc/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentVms(@PathVariable(value = "docname") String docname) throws IOException {
		logger.info("Method : image controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadOmcUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

}
