package nirmalya.aathithya.webmodule.master.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.MasterOrganisationalModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;

@Controller
@RequestMapping(value = "master")

public class MasterOrganisationController {

	Logger logger = LoggerFactory.getLogger(MasterOrganisationController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/organisationl-master")
	public String organisationMasterdetails(Model model, HttpSession session) {
		logger.info("Method : organisationMasterdetails starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		//org type
		DropDownModel[] organisationType = restTemplate.getForObject(env.getMasterUrl() + "getOrganisationTypeList",
				DropDownModel[].class);
		List<DropDownModel> OrganisationTypeList = Arrays.asList(organisationType);
		model.addAttribute("organisationTypeList", OrganisationTypeList);
		
		//org location
		DropDownModel[] loc = restTemplate.getForObject(env.getMasterUrl() + "getLocationsLists",
				DropDownModel[].class);
		List<DropDownModel> location = Arrays.asList(loc);
		model.addAttribute("location", location);
		
		//financial location
		DropDownModel[] fYear = restTemplate.getForObject(env.getMasterUrl() + "getFinancialYearLists",
				DropDownModel[].class);
		List<DropDownModel> financialYear = Arrays.asList(fYear);
		model.addAttribute("financialYearList", financialYear);

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : organisationMasterdetails ends");
		return "master/organisationMaster";
	}

	// organizer add

	@SuppressWarnings("unchecked")
	@PostMapping("/organisationl-master-add")
	public @ResponseBody JsonResponse<Object> addOrganisation(
			@RequestBody MasterOrganisationalModel masterOrganisationalModel, Model model, HttpSession session) {
		logger.info("Method :addOrganiser starts");
		logger.info("@@@@@@@@@@@@@@@@" + masterOrganisationalModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		MultipartFile inputFile = (MultipartFile) session.getAttribute("organizationLogo");
		logger.info("inputFile====="+inputFile);
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				logger.info("imageName===="+imageName);

				masterOrganisationalModel.setLogo(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		MultipartFile inputFile1 = (MultipartFile) session.getAttribute("organizationSignature");
		logger.info("inputFile1====="+inputFile1);
		byte[] bytes1;
		String imageName1 = null;

		if (inputFile1 != null) {
			try {
				bytes1 = inputFile1.getBytes();
				String[] fileType = inputFile1.getContentType().split("/");
				imageName1 = saveAllImage(bytes1, fileType[1]);
				logger.info("imageName1===="+imageName1);

				masterOrganisationalModel.setSignature(imageName1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		MultipartFile inputFile2 = (MultipartFile) session.getAttribute("organizationStamp");
		logger.info("inputFile2====="+inputFile2);
		byte[] bytes2;
		String imageName2 = null;

		if (inputFile2 != null) {
			try {
				bytes2 = inputFile2.getBytes();
				String[] fileType = inputFile2.getContentType().split("/");
				imageName2 = saveAllImage(bytes2, fileType[1]);
				logger.info("imageName2===="+imageName2);

				masterOrganisationalModel.setStamp(imageName2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-add-organiserdetails",
					masterOrganisationalModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addOrganiser ends");

		return resp;
	}

	// View org
	@SuppressWarnings("unchecked")
	@GetMapping("/organisationl-master-view")
	public @ResponseBody List<MasterOrganisationalModel> viewJobType(HttpSession session) {
		logger.info("Method : vieworg starts");

		JsonResponse<List<MasterOrganisationalModel>> resp = new JsonResponse<List<MasterOrganisationalModel>>();
		List<MasterOrganisationalModel> returnList = new ArrayList<MasterOrganisationalModel>();


		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("O___"+organization);
		logger.info("D___"+orgDivision);
		try {
			//resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-organiserdetails"+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-organiserdetails?organization=" + organization+"&orgDivision="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : vieworg ends");
		return returnList;
	}

	// edit responsible
	@SuppressWarnings("unchecked")
	@PostMapping("/organisationl-master-edit")
	public @ResponseBody JsonResponse<List<Object>> editOrganizerMaster(@RequestBody String orgId,
			HttpSession session) {
		logger.info("Method : editOrganizerMaster starts");
		
		JsonResponse<List<MasterOrganisationalModel>> orgdtls = new JsonResponse<List<MasterOrganisationalModel>>();
		List<MasterOrganisationalModel> documentList = new ArrayList<MasterOrganisationalModel>();
		logger.info("####################################" +orgId);
		/*
		 * if (documentList != null) { for (MasterOrganisationalModel m : documentList)
		 * {
		 * 
		 * if (m.getFileName() != null && m.getFileName() != "") { String[] extension =
		 * m.getFileName().split("\\."); if (extension.length == 2) { if
		 * (extension[1].equals("xls") || extension[1].equals("xlsx")) {
		 * 
		 * String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " +
		 * m.getFileName() + "></i> ";
		 * 
		 * m.setDescription(docPath); } if (extension[1].equals("pdf")) { String docPath
		 * = " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName() +
		 * " ;></i> ";
		 * 
		 * m.setDescription(docPath); } if (extension[1].equals("doc") ||
		 * extension[1].equals("dox") || extension[1].equals("docx")) { String docPath =
		 * " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title=" +
		 * m.getFileName() + "></i> "; m.setDescription(docPath); } if
		 * (extension[1].equals("png") || extension[1].equals("jpg") ||
		 * extension[1].equals("jpeg")) { String docPath =
		 * " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title=" +
		 * m.getFileName() + "></i>  "; m.setDescription(docPath); } } else {
		 * m.setDescription("N/A"); } } else { m.setDescription("N/A"); }
		 * m.setDescription("<a href=\"/document/organisation/" + m.getFileName() +
		 * "\" target=\"_blank\" >" + m.getDescription() + "</a>");
		 * 
		 * } } orgdtls.setBody(documentList);
		 */

		JsonResponse<List<Object>> response = new JsonResponse<List<Object>>();

		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "editOrganizerMasterDetails?id=" + orgId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/*
		 * ObjectMapper mapper = new ObjectMapper(); orgdtls =
		 * mapper.convertValue(response.getBody(), new
		 * TypeReference<List<MasterOrganisationalModel>>() { });
		 */

	logger.info("orgdtls===="+orgdtls);
		String message = response.getMessage();

		if (message != null && message != "") {

		} else {
			response.setMessage("success");
		}
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@editOrganizerMaster" + response);
		logger.info("Method : editOrganizerMaster Ends");
		return response;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("organisationl-master-delete")
	public @ResponseBody JsonResponse<Object> deleteOrganzier(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteOrganzier function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String createdBy = "";

		try {
			createdBy = (String) session.getAttribute("USER_ID");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "deleteOrganzierDetails?id=" + id + "&createdBy=" + createdBy,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteOrganzier function Ends");
		return res;
	}

	@PostMapping("/organisationl-master-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile logo,
			HttpSession session) {
		logger.info("Method : organisation uploadimage controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(logo.getOriginalFilename());
			session.setAttribute("organizationLogo", logo);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("response===" + response);
		logger.info("Method : organisation uploadimage controller ' ends");
		return response;
	}
	
	
	@PostMapping("/organisationl-master-signature-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFileSignature(@RequestParam("file") MultipartFile signature,
			HttpSession session) {
		logger.info("Method : organisation uploadimage controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(signature.getOriginalFilename());
			session.setAttribute("organizationSignature", signature);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("response########" + response);
		logger.info("Method : organisation uploadimage controller ' ends");
		return response;
	}
	
	
	@PostMapping("/organisationl-master-stamp-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFileStamp(@RequestParam("file") MultipartFile stamp,
			HttpSession session) {
		logger.info("Method : organisation uploadimage controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(stamp.getOriginalFilename());
			
			session.setAttribute("organizationStamp", stamp);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("response$$$$$$$$$$$$$$$" + response);
		logger.info("Method : organisation uploadimage controller ' ends");
		return response;
	}
	




	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadOrganisationUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;

	}
	
	

	public String saveAllImage1(byte[] imageBytes1) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {
			if (imageBytes1 != null) {
				long nowTime = new Date().getTime();
				imageName = nowTime + ".png";
			}

			Path path = Paths.get(env.getFileUploadOrganisationUrl() + imageName);
			if (imageBytes1 != null) {
				Files.write(path, imageBytes1);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes1);
				Integer height = 50;
				Integer width = 50;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

					BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

					ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					ImageIO.write(imageBuff, "png", buffer);

					byte[] thumb = buffer.toByteArray();

					Path pathThumb = Paths.get(env.getFileUploadOrganisationUrl() + "thumb/" + imageName);
					Files.write(pathThumb, thumb);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllImage ends");
		return imageName;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("organisationl-master-autosearch-orgName")
	public @ResponseBody JsonResponse<DropDownModel> getOrgNameAutoSearchList(Model model,
			@RequestParam String searchValue, HttpServletRequest request,
			HttpSession session) {
		logger.info("Method : getOrgNameAutoSearchList starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		
		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "getOrgNameAutoSearchList?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getOrgNameAutoSearchList ends");
		logger.info("AUTOSEARCHHH" + res);
		return res;
	}
	// organizer workingDay add
	@SuppressWarnings("unchecked")
	@PostMapping("/organisationl-master-add-workingDay-details")
	public @ResponseBody JsonResponse<Object> addOrganisationWorkingDay(
			@RequestBody MasterOrganisationalModel masterOrganisationalModel, Model model, HttpSession session) {
		logger.info("Method :addOrganisationWorkingDay starts");
		
		logger.info("@@@@@@@@@@@@@@@@" + masterOrganisationalModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
 
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addOrgWorkingDay",
					masterOrganisationalModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addOrganisationWorkingDay ends");

		return resp;
	}
	// View workingDay

	@SuppressWarnings("unchecked")
	@GetMapping("/organisationl-master-view-workingDay-details")
	public @ResponseBody List<MasterOrganisationalModel> viewworkingDay(HttpSession session,@RequestParam String orgName,String orgDiv) {
		logger.info("Method : viewworkingDay starts");

		JsonResponse<List<MasterOrganisationalModel>> resp = new JsonResponse<List<MasterOrganisationalModel>>();
		List<MasterOrganisationalModel> returnList = new ArrayList<MasterOrganisationalModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewworkingDay?orgName="+orgName+"&orgDiv="+orgDiv, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewworkingDay ends");
		return returnList;
	}
	// organizer Tax Declaration add
	@SuppressWarnings("unchecked")
	@PostMapping("/organisationl-master-add-taxDeclarationModifyDeclare")
	public @ResponseBody JsonResponse<Object> addTaxDeclarationModifyDeclare(
			@RequestBody MasterOrganisationalModel masterOrganisationalModel, Model model, HttpSession session) {
		logger.info("Method :addTaxDeclarationModifyDeclare starts");
		
		logger.info("@@@@@@@@@@@@@@@@" + masterOrganisationalModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addTaxDeclarationModifyDeclare",
					masterOrganisationalModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addTaxDeclarationModifyDeclare ends");

		return resp;
	}
	// View workingDay
	
	@SuppressWarnings("unchecked")
	@GetMapping("/organisationl-master-view-taxDeclarationModifyDeclare")
	public @ResponseBody List<MasterOrganisationalModel> viewTaxDeclarationModifyDeclare(HttpSession session,@RequestParam String orgName,String orgDiv) {
		logger.info("Method : viewTaxDeclarationModifyDeclare starts");
		
		JsonResponse<List<MasterOrganisationalModel>> resp = new JsonResponse<List<MasterOrganisationalModel>>();
		List<MasterOrganisationalModel> returnList = new ArrayList<MasterOrganisationalModel>();
		
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewTaxDeclarationModifyDeclare?orgName="+orgName+"&orgDiv="+orgDiv, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewTaxDeclarationModifyDeclare ends");
		return returnList;
	}
	
	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "organisationl-master-stateList" })
	public @ResponseBody JsonResponse<Object> getstateCusList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getStateLists1?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getstateCusList ends");
		return res;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/organisationl-master-save-shipping-address")
	public @ResponseBody JsonResponse<Object> saveOrgAddressDetails(@RequestBody MasterOrganisationalModel masterOrganisationalModel, HttpSession session) {
		logger.info("Method : saveOrgAddressDetails starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		masterOrganisationalModel.setCreatedBy(userId);
		masterOrganisationalModel.setOrganization(orgName);
		masterOrganisationalModel.setOrgDivision(orgDivision);
		
		try {
			resp = restTemplate.postForObject(env.getMasterUrl()  + "saveOrgAddressDetails", masterOrganisationalModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveOrgAddressDetails starts");
		System.out.println("dfvdvdfv"+resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("organisationl-master-shippingdetails")
	public @ResponseBody Object viewShippingAddressDetails(HttpSession session,@RequestParam String organisationalIdd) {
		logger.info("Method :viewShippingAddressDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+customerId);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewShippingAddressDetails?organisationalIdd=" + organisationalIdd + "&orgName=" + orgName +"&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewShippingAddressDetails ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("organisationl-master-edit-address")
	public @ResponseBody Object editShippingAddressDetails(HttpSession session,@RequestParam String addressId) {
		logger.info("Method :editShippingAddressDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-editShippingAddressDetails?addressId=" + addressId +"&orgName=" + orgName +"&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editShippingAddressDetails ends");
		return resp;
	}
	
	@GetMapping("organisationl-master-address-delete")
	public @ResponseBody Object deleteShippingaddressdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteShippingaddressdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteShippingaddressdata?deleteId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}


}		 
