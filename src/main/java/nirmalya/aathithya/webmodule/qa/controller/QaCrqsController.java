package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.QaCrqsModel;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;

@Controller

@RequestMapping(value = { "production/" })
public class QaCrqsController {

	Logger logger = LoggerFactory.getLogger(QaCrqsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "crqs" })

	public String qaRequest(Model model, HttpSession session) {
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] shift = restTemplate.getForObject(
					env.getProduction() + "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] packing = restTemplate.getForObject(
					env.getMasterUrl() + "getPackingSiteLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> packingSiteLists = Arrays.asList(packing);

			model.addAttribute("packingSiteLists", packingSiteLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-lists?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			System.out.println("itemlist"+itemList);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :qaRequest starts");

		logger.info("Method : qaRequest ends");

		return "qa/crqs";
	}
	
	// getaggridData

	@SuppressWarnings("unchecked")

	@GetMapping("crqs-aggrid-show")
	public @ResponseBody Object getaggridData(HttpSession session) {
		logger.info("Method :getaggridData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getProduction() + "rest-crqs-aggrid-show?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("view===" + resp);
		logger.info("Method :getaggridData ends");
		return resp;
	}
	
	// addPCRO

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "crqs-details-add" })
	public @ResponseBody JsonResponse<Object> addCRQS(@RequestBody List<QaCrqsModel> av, HttpSession session) {
		logger.info("Method : addCRQS function starts");
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
		for (QaCrqsModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-crqs-details-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addCRQS function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	// getTotalCRQSView

	@SuppressWarnings("unchecked")

	@GetMapping("crqs-details-view")
	public @ResponseBody Object getTotalCRQSView(HttpSession session) {
		logger.info("Method :getTotalCRQSView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getProduction() + "rest-crqs-details-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("view===" + resp);
		logger.info("Method :getTotalCRQSView ends");
		return resp;
	}
	
	// editCodeCrqs
		@SuppressWarnings("unchecked")
		@GetMapping("crqs-reqst-edit")
		public @ResponseBody Object editCodeCrqs(@RequestParam String id, HttpSession session) {
			logger.info("Method :editCodeCrqs starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {

				resp = restTemplate.getForObject(env.getProduction() + "rest-crqs-reqst-edit?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			System.out.println("edit>>>-----" + resp);
			logger.info("Method :editCodeCrqs ends");
			return resp;
		}

		// deleteCrqs

		@SuppressWarnings("unchecked")
		@PostMapping("crqs-detls-delete")
		public @ResponseBody JsonResponse<Object> deleteCrqs(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : deleteCrqs function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

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
				res = restTemplate.getForObject(env.getProduction() + "rest-crqs-detls-delete?id=" + id + "&org=" + orgName
						+ "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteCrqs function Ends");

			System.out.println("RESPPPPPPP" + res);
			return res;
		}
		
		
		// approveCRQS

		@SuppressWarnings("unchecked")
		@PostMapping("crqs-detls-approve")
		public @ResponseBody JsonResponse<Object> approveCRQS(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : approveCRQS function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

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
				res = restTemplate.getForObject(env.getProduction() + "rest-crqs-detls-approve?id=" + id + "&org=" + orgName
						+ "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : approveCRQS function Ends");

			System.out.println("RESPPPPPPP" + res);
			return res;
		}

		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crqs-pdf-downloads")
		public void getDcPdfDetails(HttpServletResponse response, Model model, HttpSession session,
				@RequestParam("dcId") String encodedParam1) {

			logger.info("Method : getDcPdfDetails starts");
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
			String dcId = (new String(encodeByte1));

			JsonResponse<Object> resp = new JsonResponse<Object>();
			JsonResponse<Object> resp1 = new JsonResponse<Object>();
			try {
				resp = restTemplate.getForObject(env.getProduction() + "rest-crqs-reqst-download?id=" + dcId + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				resp1 = restTemplate.getForObject(env.getProduction() + "rest-crqs-inspect-download?id=" + dcId + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("JsonResponse====" +resp.getBody());
			List<QaCrqsModel> listModel = new ArrayList<QaCrqsModel>();
			List<QaCrqsModel> listModel1 = new ArrayList<QaCrqsModel>();
			ObjectMapper mapper = new ObjectMapper();


			System.out.println("JsonResponse====" +resp.getBody()+resp1.getBody());
			try {
				listModel = mapper.readValue(resp.getBody().toString(), List.class);
				if(resp1.getBody()==null) {
					listModel1 = null;
				}else {
					listModel1 = mapper.readValue(resp1.getBody().toString(), List.class);
				}
				//listModel1 = mapper.readValue(resp1.getBody().toString(), List.class);
			} catch (JsonParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (JsonMappingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block spring security
				e2.printStackTrace();
			}
	 
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("crqs", listModel);
			data.put("crqss", listModel1);
	 
			System.out.println("data====" + data);
			String logo = "classpath:static/assets/images/invoice-banner.jpg";
			data.put("logo", logo);
	 
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=crqsInvoice.pdf");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("qa/CrqsReciept.html", data);
				InputStream in = new FileInputStream(file);
				fileData = IOUtils.toByteArray(in);
				response.setContentLength(fileData.length);
				response.getOutputStream().write(fileData);
				response.getOutputStream().flush();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			logger.info("Method : getDcPdfDetails ends");
		}

		// InspectCrqs
				@SuppressWarnings("unchecked")
				@GetMapping("crqs-inspect-view")
				public @ResponseBody Object crqsInspectView(@RequestParam String id, HttpSession session) {
					logger.info("Method :crqsInspectView starts");
					JsonResponse<Object> resp = new JsonResponse<Object>();
					String orgName = "";
					String orgDivision = "";
					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {

						resp = restTemplate.getForObject(env.getProduction() + "rest-crqs-inspect-view?id=" + id + "&orgName="
								+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (resp.getMessage() != "" && resp.getMessage() != null) {
						resp.setCode(resp.getMessage());
						resp.setMessage("Unsuccess");
					} else {
						resp.setMessage("Success");
					}
					System.out.println("edit>>>-----" + resp);
					logger.info("Method :crqsInspectView ends");
					return resp;
				}

				// addPCRO

				@SuppressWarnings({ "unchecked" })

				@PostMapping(value = { "crqs-inspect-add" })
				public @ResponseBody JsonResponse<Object> addCrqsInspect(@RequestBody List<QaCrqsModel> av, HttpSession session) {
					logger.info("Method : addCrqsInspect function starts");
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
					for (QaCrqsModel m : av) {
						m.setCreatedBy(userId);
						m.setOrganization(organization);
						m.setOrgDivision(orgDivision);

					}
					try {
						resp = restTemplate.postForObject(env.getProduction() + "rest-crqs-inspect-add", av, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					logger.info("Method : addCrqsInspect function Ends");
					return resp;
				}
				@SuppressWarnings("unchecked")
				@GetMapping("view-crqs-inspect-pdf-downloads")
				public void getInspectPdfDetails(HttpServletResponse response, Model model, HttpSession session,
						@RequestParam("crqsId") String encodedParam1) {

					logger.info("Method : getInspectPdfDetails starts");
					String orgName = "";
					String orgDivision = "";
					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
					String crqsId = (new String(encodeByte1));

					JsonResponse<Object> resp = new JsonResponse<Object>();
					try {
						resp = restTemplate.getForObject(env.getProduction() + "rest-crqs-inspect-download?id=" + crqsId + "&orgName="
								+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("JsonResponse====" +resp.getBody());

					List<QaCrqsModel> listModel = new ArrayList<QaCrqsModel>();
					ObjectMapper mapper = new ObjectMapper();
					
					try {
						listModel = mapper.readValue(resp.getBody().toString(), List.class);
					} catch (JsonParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (JsonMappingException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block spring security
						e2.printStackTrace();
					}
			 
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("crqs", listModel);
			 
					System.out.println("data====" + data);
					String logo = "classpath:static/assets/images/invoice-banner.jpg";
					data.put("logo", logo);
			 
					response.setContentType("application/pdf");
					response.setHeader("Content-disposition", "inline; filename=crqsInspectReport.pdf");
					File file;
					byte[] fileData = null;
					try {
						file = pdfGeneratorUtil.createPdf("qa/crqsInspect.html", data);
						InputStream in = new FileInputStream(file);
						fileData = IOUtils.toByteArray(in);
						response.setContentLength(fileData.length);
						response.getOutputStream().write(fileData);
						response.getOutputStream().flush();

					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					logger.info("Method : getInspectPdfDetails ends");
				}


}
