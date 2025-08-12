package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.QaPcroCheckListModel;
import nirmalya.aathithya.webmodule.qa.model.RestQaMicrobiologyModel;

@Controller

@RequestMapping(value = { "qa/" })
public class QaMicrobiologyLabController {

	Logger logger = LoggerFactory.getLogger(QaMicrobiologyLabController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "micro-lab" })

	public String qaMicrobiology(Model model, HttpSession session) {
		logger.info("Method :qaMicrobiology starts");
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
			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-lists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			System.out.println("itemlist" + itemList);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] shift = restTemplate.getForObject(env.getProduction() + "getShiftListForProduction?org="
					+ org + "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : qaMicrobiology ends");

		return "qa/qa-MicroBiology-Labratory";
	}

	@SuppressWarnings("unchecked")

	@GetMapping("micro-lab-slno")
	public @ResponseBody Object getTotalSlno(HttpSession session) {
		logger.info("Method :getTotalSlno starts");
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
					env.getProduction() + "rest-micro-lab-slno?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :getTotalSlno ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("micro-lab-getID")
	public @ResponseBody JsonResponse<List<RestQaMicrobiologyModel>> getLabId(Model model,HttpSession session) {
		JsonResponse<List<RestQaMicrobiologyModel>> jsonResponse = new JsonResponse<List<RestQaMicrobiologyModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse =restTemplate.getForObject(
					env.getProduction() + "Rest-micro-lab-getID?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<RestQaMicrobiologyModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<RestQaMicrobiologyModel>>() {
				});
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method :getLabId ends----" + jsonResponse);
		return jsonResponse;
	}
	
	/* add for day1 */
	
	//Add 
	
		@SuppressWarnings({ "unchecked" })

		@PostMapping(value = { "micro-lab-add-day" })
		public @ResponseBody JsonResponse<Object> saveDataOne(@RequestBody List<RestQaMicrobiologyModel> av, HttpSession session) {
			logger.info("Method : saveDataOne function starts");
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
			for (RestQaMicrobiologyModel m : av) {
				m.setCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);

			}
			try {
				resp = restTemplate.postForObject(env.getProduction() + "rest-micro-lab-add-day-one", av, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : saveDataOne function Ends");
			return resp;
		}
		
		
	/* view */

		

		@SuppressWarnings("unchecked")

		@GetMapping("micro-lab-add-view")
		public @ResponseBody Object getTotalPcroCheckListView(HttpSession session) {
			logger.info("Method :getTotalPcroCheckListView starts");
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
						env.getProduction() + "rest-micro-lab-add-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
			logger.info("Method :getTotalPcroCheckListView ends");
			return resp;
		}
		
		
		// editLabtest
				@SuppressWarnings("unchecked")
				@GetMapping("micro-lab-edit")
				public @ResponseBody Object editLabTest(@RequestParam String id, HttpSession session) {
					logger.info("Method :editLabTest starts");
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

						resp = restTemplate.getForObject(env.getProduction() + "rest-micro-lab-edit?id=" + id + "&orgName="
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
					logger.info("Method :editLabTest ends");
					return resp;
				}
				
				
				@SuppressWarnings("unchecked")
				@PostMapping("micro-lab-delete")
				public @ResponseBody JsonResponse<Object> deleteLabtest(@RequestParam String id, Model model, HttpSession session) {
					logger.info("Method : deleteLabtest function starts");

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
						res = restTemplate.getForObject(env.getProduction() + "rest-micro-lab-delete?id=" + id + "&org=" + orgName
								+ "&orgDiv=" + orgDivision, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					String message = res.getMessage();
					if (message != null && message != "") {

					} else {
						res.setMessage("Success");
					}
					logger.info("Method : deleteLabtest function Ends");

					System.out.println("RESPPPPPPP" + res);
					return res;
				}
////PDF

				@SuppressWarnings("unchecked")
				@GetMapping("micro-lab-pdf-downloads")
				public void microLabPdf(HttpServletResponse response, Model model, HttpSession session,
				                           @RequestParam("id") String encodedParam3)
				        throws JsonParseException, JsonMappingException, JSONException, IOException {

				    logger.info("Method : microLabPdf starts");
				    String orgName = "";
				    String orgDivision = "";
				    try {
				        orgName = (String) session.getAttribute("ORGANIZATION");
				        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				    } catch (Exception e) {
				        e.printStackTrace();
				    }

				    byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
				    String id = new String(encodeByte5);

				    JsonResponse<Object> resp = new JsonResponse<Object>();
				    try {
				        resp = restTemplate.getForObject(env.getProduction() + "rest-microLabPdfDetaills?id=" + id
				        		+ "&orgName="
				                + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				    } catch (Exception e) {
				        e.printStackTrace();
				    }

				    logger.info("Response: " + resp);
				    Map<String, Object> data = new HashMap<String, Object>();

				    System.out.println("Get Data === " + resp.getBody());

				    if (resp.getBody() == null) {
				        System.out.println("JSON Obj === " + resp.getBody());

				        response.setContentType("application/pdf");
				        response.setHeader("Content-disposition", "inline; filename=microLabPdf.pdf");
				        File file;
				        byte[] fileData = null;

				        try {
				            file = pdfGeneratorUtil.createPdf("qa/microLabPdf.html", data);
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
				        return;  // exit the method as there's no data to process
				    }

				    String responseBody = resp.getBody().toString();
				    System.out.println("Response Body === " + responseBody);

				    ObjectMapper mapper = new ObjectMapper();
				    JsonNode jsonNode = mapper.readTree(responseBody);

				    if (jsonNode.isArray()) {
				        List<Map<String, Object>> dataa = mapper.readValue(responseBody,
				                new TypeReference<List<Map<String, Object>>>() {});
				        logger.info("dataa" + dataa);
				        data.put("respdata", dataa);
				    } else {
				        // Handle case where response is not an array
				        System.out.println("Response is not a JSON array: " + responseBody);
				        // Additional handling if needed
				    }

				    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
				    URL getUrl = null;
				    try {
				        getUrl = new URL(logo);
				    } catch (MalformedURLException e2) {
				        e2.printStackTrace();
				    }
				    String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
				    data.put("logo", "data:image/png;base64," + encodedLogoUrl);

				    response.setContentType("application/pdf");
				    response.setHeader("Content-disposition", "inline; filename=microLabPdf.pdf");
				    File file;
				    byte[] fileData = null;
				    try {
				        file = pdfGeneratorUtil.createPdf("qa/microLabPdf.html", data);
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

				    logger.info("Method : microLabPdf ends");
				}
				
				/*@SuppressWarnings("unchecked")
				@GetMapping("micro-lab-pdf-downloads")
				public void microLabPdf(HttpServletResponse response, Model model, HttpSession session,
				        @RequestParam("id") String encodedParam1) {

				    logger.info("Method : microLabPdf starts");
				    String orgName = "";
				    String orgDivision = "";
				    try {
				        orgName = (String) session.getAttribute("ORGANIZATION");
				        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				    } catch (Exception e) {
				        logger.error(e.getMessage());
				    }

				    byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
				    String id = new String(encodeByte3);

				    System.out.println("JSON===== === " + id);
				    JsonResponse<Object> resp = new JsonResponse<Object>();

				    try {
				        resp = restTemplate.getForObject(env.getProduction() + "rest-microLabPdfDetaills?id=" + id + "&orgName="
				                + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				        System.out.println("JSON Obj === " + resp);
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
				    logger.info("tefeg" + resp);

				    Map<String, Object> data = new HashMap<String, Object>();

				    System.out.println("Get Data === " + resp.getBody().toString());

				    // Parsing the JSON data
				    ObjectMapper objectMapper = new ObjectMapper();
				    try {
				        JsonNode rootNode = objectMapper.readTree(resp.getBody().toString());

				        // Extract sections
				        JsonNode mainSection = rootNode.path("Main");
				        JsonNode firstSection = rootNode.path("First");
				        JsonNode secondSection = rootNode.path("Second");
				        JsonNode thirdSection = rootNode.path("Third");
				        JsonNode fourSection = rootNode.path("Four");
				        JsonNode fiveSection = rootNode.path("Five");
				        if (mainSection.isArray() && mainSection.size() > 0) {
				            data.put("Main", objectMapper.convertValue(mainSection, new TypeReference<List<Map<String, Object>>>() {}));
				        }
				        
				        if (firstSection.isArray() && firstSection.size() > 0) {
				            data.put("First", objectMapper.convertValue(firstSection, new TypeReference<List<Map<String, Object>>>() {}));
				        }

				        if (secondSection.isArray() && secondSection.size() > 0) {
				            data.put("Second", objectMapper.convertValue(secondSection, new TypeReference<List<Map<String, Object>>>() {}));
				        }

				        if (thirdSection.isArray() && thirdSection.size() > 0) {
				            data.put("Third", objectMapper.convertValue(thirdSection, new TypeReference<List<Map<String, Object>>>() {}));
				        }
				        if (fourSection.isArray() && fourSection.size() > 0) {
				            data.put("Four", objectMapper.convertValue(fourSection, new TypeReference<List<Map<String, Object>>>() {}));
				        }
				        if (fiveSection.isArray() && fiveSection.size() > 0) {
				            data.put("Five", objectMapper.convertValue(fiveSection, new TypeReference<List<Map<String, Object>>>() {}));
				        }
				    } catch (Exception e) {
				        e.printStackTrace();
				    }

				    response.setContentType("application/pdf");
				    response.setHeader("Content-disposition", "inline; filename=microLabPdf.pdf");
				    File file;
				    byte[] fileData = null;
				    try {
				        file = pdfGeneratorUtil.createPdf("qa/microLabPdf.html", data);
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

				    logger.info("Method : microLabPdf ends");
				}*/
}
