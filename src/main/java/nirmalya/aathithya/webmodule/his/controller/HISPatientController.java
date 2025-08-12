package nirmalya.aathithya.webmodule.his.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.his.model.HISPatientModel;

@Controller
@RequestMapping("his")
public class HISPatientController {

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	Logger logger = LoggerFactory.getLogger(HISPatientController.class);

	@GetMapping(value = { "/manage-patient" })
	public String viewPatient(Model model, HttpSession session) {
		logger.info("Method : viewPatient starts");
		
		
		try {
			// Fetch gender data
			DropDownModel[] gender = restClient.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			// Fetch marital status data
			DropDownModel[] marital = restClient.getForObject(env.getHisUrl() + "/maritalstatusList",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(marital);
			model.addAttribute("maritalstatusList", maritalstatusList);

			// Fetch nationality data
			DropDownModel[] nationality = restClient.getForObject(env.getHisUrl() + "/nationalityList",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(nationality);
			model.addAttribute("nationalityList", nationalityList);

			DropDownModel[] country = restClient.getForObject(env.getHisUrl() + "/countryList", DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);

			// Fetch department data
			DropDownModel[] department = restClient.getForObject(env.getHisUrl() + "/departmentList",
					DropDownModel[].class);
			List<DropDownModel> departmentList = Arrays.asList(department);
			model.addAttribute("departmentList", departmentList);
			
			//patient relation list	
			DropDownModel[] relation = restClient.getForObject(env.getHisUrl() + "/getRelationList", DropDownModel[].class);
			List<DropDownModel> getRelationList = Arrays.asList(relation);
			logger.info("getRelationList"+getRelationList);
			model.addAttribute("getRelationList", getRelationList);

		} catch (RestClientException e) {
			// Handle RestClientException
			e.printStackTrace();

		}

		logger.info("Method : viewPatient ends");
		return "his/manage-patient.html";
	}

	@GetMapping(value = { "/manage-patient-profile" })
	public String viewPatientprofile(Model model, HttpSession session, @RequestParam String id) {
		logger.info("Method : viewPatient starts");

		try {
			// Fetch gender data
			DropDownModel[] gender = restClient.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);

			// Fetch marital status data
			DropDownModel[] marital = restClient.getForObject(env.getHisUrl() + "/maritalstatusList",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(marital);
			model.addAttribute("maritalstatusList", maritalstatusList);

			// Fetch nationality data
			DropDownModel[] nationality = restClient.getForObject(env.getHisUrl() + "/nationalityList",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(nationality);
			model.addAttribute("nationalityList", nationalityList);

			DropDownModel[] country = restClient.getForObject(env.getHisUrl() + "/countryList", DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);

			// Fetch department data
			DropDownModel[] department = restClient.getForObject(env.getHisUrl() + "/departmentList",
					DropDownModel[].class);
			List<DropDownModel> departmentList = Arrays.asList(department);
			model.addAttribute("departmentList", departmentList);

		} catch (RestClientException e) {
			// Handle RestClientException
			e.printStackTrace();

		}

		logger.info("Method : viewPatient ends");
		return "his/manage-patient-profile.html";
	}

	// GetState List

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-patient-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getHisUrl() + "getPatientSateList?id=" + id, JsonResponse.class);
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
		logger.info("Method : getstateList ends");
		return res;
	}

	// districtList

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-patient-districtList" })
	public @ResponseBody JsonResponse<Object> districtList(@RequestParam String id) {
		logger.info("Method : districtList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getHisUrl() + "districtList?id=" + id, JsonResponse.class);
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
		logger.info("Method : districtList ends");
		return res;
	}

	// manage-patient-add

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-patient-add")
	public @ResponseBody JsonResponse<Object> addPatient(@RequestBody HISPatientModel patientModel,
			HttpSession session) {
		logger.info("Method: addPatient starts");
		logger.info("Method: addPatient dataaa====" + patientModel);

		JsonResponse<Object> resp = new JsonResponse<>();

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
		patientModel.setCreatedBy(userId);
		patientModel.setOrganization(orgName);
		patientModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getHisUrl() + "restAddPatient", patientModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		resp.setCode(resp.getCode());
		resp.setMessage(resp.getMessage());
		resp.setBody(resp.getBody());
		logger.info("Method: addPatient ends" + resp);
		return resp;
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

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 280;
				Integer width = 474;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					BufferedImage outputImage = new BufferedImage(width, height, img.getType());

					Graphics2D g2d = outputImage.createGraphics();
					g2d.drawImage(img, 0, 0, width, height, null);
					g2d.dispose();
					String outputImagePath = env.getFileUploadMaster() + "thumb/" + imageName;
					ImageIO.write(outputImage, ext, new File(outputImagePath));

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

	// manage-patient-view
	@SuppressWarnings("unchecked")
	@GetMapping("manage-patient-view")
	public @ResponseBody Object viewPatient(@RequestParam String fromDate, @RequestParam String toDate,
			HttpSession session) {
		logger.info("Method :viewPatient starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-viewPatient?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewPatient ends");
		return resp;
	}

	// manage-patient-edit
	@SuppressWarnings("unchecked")
	@GetMapping("manage-patient-edit")
	public @ResponseBody Object editPatient(@RequestParam String id, HttpSession session) {
		logger.info("Method :editPatient starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "editPatient?id=" + id + "&organization=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editPatient ends");
		return resp;
	}

	// manage-patient-delete
	@SuppressWarnings("unchecked")
	@GetMapping("/manage-patient-delete")
	public @ResponseBody JsonResponse<Object> deletePatient(HttpSession session, @RequestParam String id) {
		logger.info("Method : deletePatient starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getHisUrl() + "deletePatient?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deletePatient ends");
		return resp;
	}

	// manage-patient-patientList
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-patient-patientList" })
	public @ResponseBody JsonResponse<HISPatientModel> getVendorNameAutoList(Model model,
			@RequestBody String searchValue) {
		logger.info("Method : getVendorNameAutoList starts");
		JsonResponse<HISPatientModel> res = new JsonResponse<HISPatientModel>();

		try {
			res = restClient.getForObject(env.getHisUrl() + "getPatientList?id=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getPatientList ends");
		return res;
	}

	// manage-patient-doctorList

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-patient-doctorList" })
	public @ResponseBody JsonResponse<Object> getDoctorList(@RequestParam String id) {
		logger.info("Method : getDoctorList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getHisUrl() + "doctorList?id=" + id, JsonResponse.class);
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
		logger.info("Method : getDoctorList ends");
		return res;
	}

	// manage-patient-feeList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-patient-feeList" })
	public @ResponseBody JsonResponse<Object> feeList(HttpSession httpSession, @RequestParam String id,
			@RequestParam String dateOfAppoints) {
		logger.info("Method : feeList starts==================>" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getHisUrl() + "feeList?id=" + id + "&dateOfAppoints=" + dateOfAppoints,
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
		logger.info("feeList" + res);
		logger.info("Method : feeList ends");
		return res;
	}

	@PostMapping("/manage-patient-upload-file")
	public @ResponseBody JsonResponse<Object> uploadProductFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadProductFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("productPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadProductFile controller ' ends");
		return response;
	}

	@PostMapping("manage-patient-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile employee uploadimage controller starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.removeAttribute("employeePFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile employee uploadimage controller ends");
		return response;
	}

	// patient invoice details
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = "/download-patient-invoice")
	public void patientInvoice(@RequestParam String pId, HttpServletResponse response) {
		logger.info("Method : patientInvoice starts");

		String decodedPatientId = new String(Base64.getDecoder().decode(pId));
		logger.info("Decoded Patient ID: " + decodedPatientId);

		try {
			JsonResponse<Object> resp = restClient.getForObject(
					env.getHisUrl() + "rest-patient-invoice-details?pId=" + decodedPatientId, JsonResponse.class);

			logger.info("Patient Invoice Details: " + resp);

			if (resp != null && resp.getBody() != null) {
				ObjectMapper mapper = new ObjectMapper();

				Map<String, Object> data;
				if (resp.getBody() instanceof String) {
					data = mapper.readValue((String) resp.getBody(), Map.class);

					System.out.println("Data is coming ===============> " + data);
				} else {
					data = mapper.convertValue(resp.getBody(), Map.class);
				}

	
				List<Map<String, Object>> invoiceDetails = (List<Map<String, Object>>) data.get("invoiceDetails");

				if (invoiceDetails != null && !invoiceDetails.isEmpty()) {
					Map<String, Object> invoiceData = invoiceDetails.get(0); 


					data.put("gst", invoiceData.get("gst"));
					data.put("name", invoiceData.get("name"));
					data.put("address", invoiceData.get("address"));
					data.put("subtotal", invoiceData.get("subtotal"));
					data.put("doctorFee", invoiceData.get("doctorFee"));
					data.put("grandTotal", invoiceData.get("grandTotal"));
					data.put("ambulanceFee", invoiceData.get("ambulanceFee"));
					data.put("registrationDate", invoiceData.get("registrationDate"));
				}

				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline; filename=patient-invoice.pdf");

				File file;
				byte[] fileData;
				try {
					file = pdfGeneratorUtil.createPdf("his/patient-invoice-pdf.html", data);

					try (InputStream in = new FileInputStream(file)) {
						fileData = IOUtils.toByteArray(in);
						response.setContentLength(fileData.length);
						response.getOutputStream().write(fileData);
						response.getOutputStream().flush();
					}
				} catch (IOException e) {
					logger.error("Error generating or writing the PDF: ", e);
					throw new RuntimeException("Error generating or writing the PDF.", e);
				}
			} else {
				logger.error("No data found for the given patient ID.");
				response.sendError(HttpServletResponse.SC_NO_CONTENT, "No data found for the given patient ID.");
			}
		} catch (Exception ex) {
			logger.error("Exception in patientInvoice method: ", ex);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		logger.info("Method : patientInvoice ends");
	}

}
