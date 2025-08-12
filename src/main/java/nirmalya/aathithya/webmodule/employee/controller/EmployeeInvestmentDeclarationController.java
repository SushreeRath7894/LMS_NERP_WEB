package nirmalya.aathithya.webmodule.employee.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.InvestmentDeclarationModel;
	@Controller
	@RequestMapping(value = "employee")
	public class EmployeeInvestmentDeclarationController {
		Logger logger = LoggerFactory.getLogger(EmployeeInvestmentDeclarationController.class);

		@Autowired
		RestTemplate restClient;

		@Autowired
		EnvironmentVaribles env;
		
		@GetMapping("/investment-declaration")
		public String employeeInvestment(Model model, HttpSession session) {

			logger.info("Method : employeeInvestment starts");

			
			logger.info("Method : employeeInvestment ends");

			return "employee/employee-investment-declaration";
		}
		/*
		 * View Header details
		 */
		@SuppressWarnings("unchecked")
		@GetMapping("investment-declaration-details")
		public @ResponseBody JsonResponse<List<InvestmentDeclarationModel>> viewInvestmentDeclare(Model model,
				HttpSession session) {

			logger.info("Method : viewInvestmentDeclare starts");
			JsonResponse<List<InvestmentDeclarationModel>> resp = new JsonResponse<List<InvestmentDeclarationModel>>();
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
			try {
				resp = restClient.getForObject(env.getEmployeeUrl() + "rest-investmentdeclare-details?id=" + userId+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method : viewInvestmentDeclare ends"+resp);
			return resp;
		}
		
		@SuppressWarnings("unchecked")

		@PostMapping("investment-declaration-details-adds")
		public @ResponseBody JsonResponse<Object> addInvestment(@RequestBody List<InvestmentDeclarationModel> investmentDeclarationModel,
				HttpSession session) {
			logger.info("Method : addInvestment starts");
			
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
			
			
			investmentDeclarationModel.get(0).setOrganization(organization);
			investmentDeclarationModel.get(0).setOrgDiv(orgDivision);
			
			for(int i=0;i<investmentDeclarationModel.get(0).getHeaderLength();i++) {
				MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile_"+i);
				byte[] bytes;
				String imageName = null;

				if (inputFile != null) {
					try {
						bytes = inputFile.getBytes();
						String[] fileType = inputFile.getContentType().split("/");
						imageName = saveAllImage(bytes, fileType[1],userId,String.valueOf(i));
						investmentDeclarationModel.get(i).setDocFile(imageName);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			logger.info("investmentDeclarationModel==="+investmentDeclarationModel);
			try {
				resp = restClient.postForObject(env.getEmployeeUrl() + "rest-addInvestment", investmentDeclarationModel,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			for(int i=0;i<investmentDeclarationModel.get(0).getHeaderLength();i++) {
				session.removeAttribute("quotationPFile_"+i);
			}

logger.info("resp==="+resp); 
			logger.info("Method : addInvestment ends");
			return resp;
		}
		@PostMapping("investment-declaration-upload-file")
		public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile, @RequestParam("id") String id,
				HttpSession session) {
			logger.info("Method : uploadFile controller function 'post-mapping' starts");
			logger.info("Method : id ="+id);
			JsonResponse<Object> response = new JsonResponse<Object>();

			try {
				response.setMessage(inputFile.getOriginalFilename());
				session.setAttribute("quotationPFile_"+id, inputFile);
			} catch (RestClientException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : uploadFile controller function 'post-mapping' ends");
			return response;
		}
		private String saveAllImage(byte[] imageBytes, String ext,String userId,String id) {
			logger.info("Method : saveAllImage starts");
			String imageName = null;
			
			long nowTime = new Date().getTime();
			if (ext.contentEquals("jpeg")) {
				imageName = userId +"_"+id+"_"+ nowTime + ".jpg";
			} else {
				imageName = userId +"_"+id+"_"+  nowTime +"." + ext;
			}
			try {
					Path path = Paths.get(env.getFileUploadtaskdocumentUrl()+ imageName);
					if (imageBytes != null) {
						Files.write(path, imageBytes);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
			logger.info("Method : saveAllImage ends");
			return imageName;
						
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("investment-declaration-alldata-details")
		public @ResponseBody JsonResponse<List<InvestmentDeclarationModel>> viewInvestmentSubheaderData(@RequestParam String id,Model model,
				HttpSession session) {
			logger.info("Method : viewInvestmentSubheaderData starts");
			JsonResponse<List<InvestmentDeclarationModel>> resp = new JsonResponse<List<InvestmentDeclarationModel>>();
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
				resp = restClient.getForObject(env.getEmployeeUrl() + "rest-investmentdeclare-subheader-details?id="+id+"&userId="+ userId+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (resp.getCode().equals("success")) {
				resp.setMessage("Success");
			} else {
				resp.setMessage("Unsuccess");
			}
			logger.info("Method : viewInvestmentSubheaderData ends");
			return resp;
		}
		
		@SuppressWarnings("unchecked")

		@GetMapping("investment-declaration-view-through-ajax")
		public @ResponseBody List<InvestmentDeclarationModel> viewDeclaretaion(HttpSession session) {

			logger.info("Method :viewDeclaretaion starts");

			JsonResponse<List<InvestmentDeclarationModel>> resp = new JsonResponse<List<InvestmentDeclarationModel>>();
			
			String empId = "";
			String organization=""; 
			String orgDivision="";
			try {
				empId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restClient.getForObject(env.getEmployeeUrl() + "rest-declarationview?empId="+empId+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method :viewDeclaretaion ends"+resp.getBody());
			return resp.getBody();
		}
		// check Edit Available
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "/investment-declaration-checkEditAvailable" })
		public @ResponseBody JsonResponse<Object> checkEditAvailable(@RequestParam String financialYear,HttpSession session) {
			logger.info("Method : checkEditAvailable starts");
			JsonResponse<Object> res = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				res = restClient.getForObject(env.getEmployeeUrl() + "rest-checkEditAvailable?userId=" + userId+"&financialYear="+financialYear
						+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getCode().equals("success")) {
				res.setCode(res.getMessage());
				res.setMessage("Success");
			} else {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			}
			logger.info("res=====" + res);
			logger.info("Method : checkEditAvailable ends");
			return res;

		}
}
