package nirmalya.aathithya.webmodule.projects.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*import org.apache.pdfbox.multipdf.PDFMergerUtility;*/
import org.json.JSONArray;
import org.json.JSONObject;
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
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.ProjectCategoryModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;

@Controller
@RequestMapping(value = "projects")
public class ProjectNfaController {
	
	Logger logger = LoggerFactory.getLogger(TechnoCommercialOfferController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "note-for-appriasal" })
	public String technocommercialoffer(Model model, HttpSession session) {
		logger.info("Method :technocommercialoffer starts");
		String documentTypeId = "TPDT0011";

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

		logger.info("Method : technocommercialoffer ends");
		return "projects/nfa.html";
	}
	
	
	@SuppressWarnings("unchecked")

	@GetMapping("note-for-appriasal-project-view")
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
	@PostMapping("note-for-appriasal-add")
	public @ResponseBody JsonResponse<Object> nfaAdd(
			@RequestBody ProjectCreationWebModel category, HttpSession session) {
		logger.info("Method : nfaAdd starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		category.setCreatedBy(userId);
		category.setOrganizationName(orgName);
		category.setOrganizationDivision(orgDiv);

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-nfaAdd", category,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : nfaAdd starts");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")

	@GetMapping("note-for-appriasal-view")
	public @ResponseBody Object nfaview(HttpSession session,@RequestParam String id,@RequestParam String id2) {

		logger.info("Method :nfaview starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-nfaview" + "?userid=" + userId + "&id=" + id + "&id2=" + id2
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("nfaview--" + resp);
		logger.info("Method :nfaview ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("note-for-appriasal-pdf")
	public void mergePdfs(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1,@RequestParam("id2") String encodedParam2) {
		
		logger.info("Method : mergePdfs starts"+encodedParam1 + "id2"+ encodedParam2);
		
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
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String project = (new String(encodeByte3));
		
		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String type = (new String(encodeByte4));
		
		logger.info("project"+encodeByte3 + "type"+encodeByte4);		
		JsonResponse<List<ProjectCreationWebModel>> jsonResponse = new JsonResponse<List<ProjectCreationWebModel>>();

		try {
			jsonResponse = restClient.getForObject(env.getProjects() + "rest-nfaview" + "?userid=" + userId + "&id=" + project + "&id2=" + type
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		String data = "";
		
		//if(!productList.get(0).getItemDesc().toString().isEmpty()) {
		//	data += "<div class='body-content1 terms-conditions'><strong><u style='font-size:20px;'>Annexture:</u></strong><br><br /> <span>" + productList.get(0).getItemDesc().toString() + "</span></div>";
		//}
	//	if(!productList.get(0).getTerms().toString().isEmpty()) {
		//	data += "<div style='height:80px'></div><div class='body-content1 terms-conditions'><strong><u style='font-size:20px;'>Terms &#38; Conditions:</u></strong><br><br /> <span>" + productList.get(0).getTerms().toString() + "</span></div>";
	//	}
		
		
		JSONObject request = new JSONObject();
		request.put("content", data);

		System.out.println("Get Data === " + jsonResponse.getBody());

		JSONObject resp = new JSONObject(jsonResponse.getBody().toString());
		JSONArray dataList = resp.getJSONArray("data"); // Assuming "data" is the key for your list
		System.out.println(" --------------------->>>>>>>>>>>>> DATA" + dataList.toString());
			
		String outputPath = env.getFileUploadMaster() + "sample-pdf.pdf";
			
			try {
	            // Decode the base64 data.
	            byte[] pdfBytes = Base64.getDecoder().decode(resp.getString(data));
	
	            // Create a new File object for the output path.
	            File outputFile = new File(outputPath);
	
	            // Write the PDF bytes to the output file.
	            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
	                fos.write(pdfBytes);
	            }
	
	            System.out.println("PDF saved to: " + outputPath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
			/*
			 * try { mergeAllPdf(encodedParam1,encodedParam2, response); } catch
			 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */
			
		logger.info("Method : mergePdfs starts");
	}
	
	/*
	 * public void mergeAllPdf(String encodedParam1,String encodedParam2,
	 * HttpServletResponse response) throws IOException {
	 * 
	 * String id = "";
	 * 
	 * byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes()); id
	 * = (new String(encodeByte1));
	 * 
	 * 
	 * String id2 = "";
	 * 
	 * byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
	 * id2 = (new String(encodeByte2));
	 * 
	 * File file2 = new File(env.getFileUploadMaster() + "sample-pdf.pdf"); File
	 * file1 = new File(env.getFileUploadMaster() + "sample-pdf-po.pdf");
	 * 
	 * System.out.println("opening connection"); URL url = new URL(env.getBaseURL()
	 * + "projects/note-for-appriasal-pdf?id="+ encodedParam1 + "&id2=" +
	 * encodedParam2);
	 * 
	 * System.out.println("url"+url); InputStream in = url.openStream();
	 * FileOutputStream fos = new FileOutputStream(file1);
	 * 
	 * System.out.println("reading from resource and writing to file..."); int
	 * length = -1; byte[] buffer = new byte[1024];// buffer for portion of data
	 * from connection while ((length = in.read(buffer)) > -1) { fos.write(buffer,
	 * 0, length); } fos.close(); in.close(); System.out.println("File downloaded");
	 * 
	 * //Instantiating PDFMergerUtility class PDFMergerUtility PDFmerger = new
	 * PDFMergerUtility();
	 * 
	 * //Setting the destination file
	 * PDFmerger.setDestinationFileName(env.getFileUploadMaster() +"merged.pdf");
	 * 
	 * //adding the source files PDFmerger.addSource(file1);
	 * PDFmerger.addSource(file2);
	 * 
	 * //Merging the two documents PDFmerger.mergeDocuments(null);
	 * 
	 * Path pdfPath = Paths.get(env.getFileUploadMaster() +"merged.pdf"); byte[] pdf
	 * = null; try { pdf = Files.readAllBytes(pdfPath); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * response.setContentType("application/pdf");
	 * response.setHeader("Content-disposition", "inline; filename=" +
	 * "merged.pdf"); response.setContentLength(pdf.length);
	 * 
	 * try { response.getOutputStream().write(pdf) ; } catch (IOException e) {
	 * e.printStackTrace(); } try { response.getOutputStream().flush();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * System.out.println("Documents merged");
	 * 
	 * try { File file3 = new File(env.getFileUploadMaster() +"merged.pdf");
	 * 
	 * file1.delete(); file2.delete(); file3.delete();
	 * System.out.println("Files deleted successfully"); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

}
