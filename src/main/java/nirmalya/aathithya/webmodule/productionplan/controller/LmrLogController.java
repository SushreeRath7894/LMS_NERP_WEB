package nirmalya.aathithya.webmodule.productionplan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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


import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.productionplan.model.LmrLogPdfModel;
import nirmalya.aathithya.webmodule.productionplan.model.LmrLogWebModel;



@Controller
@RequestMapping(value = "production/")
public class LmrLogController {
	
	Logger logger = LoggerFactory.getLogger(LmrLogController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("lmr-log")
	public String lmrLog(Model model, HttpSession session) {
		logger.info("Method : lmrLog starts");
		
		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
			}
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("orgName", org);
		model.addAttribute("orgDivision", orgDiv);
		
		try {
			DropDownModel[] shift = restTemplate.getForObject(
					env.getProduction() + "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);
			System.out.println("shiftLists>>>>>>>>>>>>>>>>>>>"  + shiftLists);
			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 
		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-lists?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/*
		 * try { DropDownModel[] line = restClient.getForObject( env.getProduction() +
		 * "getLineLists-Lmr?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
		 * List<DropDownModel> lineLists = Arrays.asList(line);
		 * 
		 * model.addAttribute("lineLists", lineLists);
		 * 
		 * } catch (RestClientException e) { e.printStackTrace(); }
		 */
		logger.info("Method : lmrLog ends");
		return "production_plan/lmr-log";
	}
	
	// MAchine List.

	@SuppressWarnings("unchecked")
	@GetMapping("lmr-log-machineList")
	public @ResponseBody Object machineList(@RequestParam String id, HttpSession session) {
		logger.info("Method :machineList starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-lmrLog-machineList?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		logger.info("Method :machineList ends");
		return resp;
	}
	
	// add
	
	@SuppressWarnings("unchecked")
	@PostMapping("lmr-log-add")
	public @ResponseBody JsonResponse<Object> addLmrLog(HttpSession session,
			@RequestBody LmrLogWebModel offDay) {
		logger.info("Method : addLmrLog starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "addLmrLog", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addLmrLog ends"+resp);
		return resp;
	}
	
	// view
	
	@SuppressWarnings("rawtypes")
	@GetMapping("lmr-log-view")
	public @ResponseBody Object viewLmrLog(Model model, HttpSession session, @RequestParam String pageno) {

			logger.info("Method :viewLmrLog starts");
			
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";
			//String pageno = "1";

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restClient.getForObject(
					env.getProduction() + "viewLmrLog?org=" + org + "&orgDiv=" + orgDiv + "&pageno=" + pageno,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewLmrLog ends  ");
		return resp;
	}
	
	
	// view edit
	
	@SuppressWarnings("rawtypes")
	@GetMapping("lmr-log-view-edit")
	public @ResponseBody Object editLmrLogView(@RequestParam String id, String itemFlag, HttpSession session) {

			logger.info("Method :editLmrLogView starts");
			
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restClient.getForObject(
					env.getProduction() + "rest-editLmrLogView?id=" + id +"&itemFlag=" + itemFlag +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :editLmrLogView ends  ");
		return resp;
	}
	
	
	// add part b
	
	@SuppressWarnings("unchecked")
	@PostMapping("lmr-log-add-partbingd")
	public @ResponseBody JsonResponse<Object> addPartBIngd(HttpSession session,
			@RequestBody LmrLogWebModel offDay) {
		logger.info("Method : addPartBIngd starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "rest-addPartBIngd", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addPartBIngd ends"+resp);
		return resp;
	}
	
	
	// view part b
	
	@SuppressWarnings("rawtypes")
	@GetMapping("lmr-log-view-partbingredient")
	public @ResponseBody Object partBIngredientView(@RequestParam String id, HttpSession session) {

			logger.info("Method :partBIngredientView starts");
			
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restClient.getForObject(
					env.getProduction() + "rest-partBIngredientView?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :partBIngredientView ends  ");
		return resp;
	}
	
	
	
	// add femto blending
	
	@SuppressWarnings("unchecked")
	@PostMapping("lmr-log-add-femtoblending")
	public @ResponseBody JsonResponse<Object> addFemtoBlending(HttpSession session,
			@RequestBody LmrLogWebModel offDay) {
		logger.info("Method : addFemtoBlending starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "rest-addFemtoBlending", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addFemtoBlending ends"+resp);
		return resp;
	}
	
	// add weighing scale
	
	@SuppressWarnings("unchecked")
	@PostMapping("lmr-log-add-weighingscale")
	public @ResponseBody JsonResponse<Object> addWeighingScale(HttpSession session,
			@RequestBody LmrLogWebModel offDay) {
		logger.info("Method : addWeighingScale starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "rest-addWeighingScale", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addWeighingScale ends"+resp);
		return resp;
	}
	
	
	
	// add area line clearance
	
	@SuppressWarnings("unchecked")
	@PostMapping("lmr-log-add-arealineclearance")
	public @ResponseBody JsonResponse<Object> addAreaLineClearance(HttpSession session,
			@RequestBody LmrLogWebModel offDay) {
		logger.info("Method : addAreaLineClearance starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "rest-addAreaLineClearance", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addAreaLineClearance ends"+resp);
		return resp;
	}
	
	
	// Delete
	
		@SuppressWarnings("rawtypes")
		@GetMapping("lmr-log-delete")
		public @ResponseBody Object deleteLmrLog(@RequestParam String id, HttpSession session) {

				logger.info("Method :deleteLmrLog starts");
				
				JsonResponse resp = new JsonResponse();
				String org = "";
				String orgDiv = "";

				try {
					org = (String) session.getAttribute("ORGANIZATION");
					orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					resp = restClient.getForObject(
						env.getProduction() + "rest-deleteLmrLog?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
						JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method :deleteLmrLog ends  ");
			return resp;
		}
		
		

		//  Pdf.

		@SuppressWarnings("unchecked")
		@GetMapping("lmr-logbook-download")
		public void getLmrLogPdfDtls(HttpServletResponse response, Model model, HttpSession session,
				@RequestParam("packId") String encodedParam1, @RequestParam("type") String encodedParam2,
				@RequestParam("org") String encodedParam3 ,@RequestParam("orgDiv") String encodedParam4) {

			logger.info("Method : getLmrLogPdfDtls starts");
			/*
			 * String orgName = ""; String orgDivision = ""; try { orgName = (String)
			 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
			 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
			 * logger.error(e.getMessage()); }
			 */
			byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
			String dcId = (new String(encodeByte1));
			byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
			String type = (new String(encodeByte2));
			byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
			String orgName = (new String(encodeByte3));
			byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam4.getBytes());
			String orgDivision = (new String(encodeByte4));

			JsonResponse<Object> resp = new JsonResponse<Object>();
			try {
				resp = restTemplate.getForObject(env.getProduction() + "rest-lmr-pdf-dtls?id=" + dcId + "&type=" + type + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("JsonResponse====" + resp.getBody());

			List<LmrLogPdfModel> listModel = new ArrayList<LmrLogPdfModel>();
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
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	 
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("pdfDtls", listModel);
	 
			System.out.println("data====" + data);
			/*
			 * String logo = "classpath:static/assets/images/invoice-banner.jpg";
			 * data.put("logo", logo);
			 */
			String logo = "classpath:static/assets/images/oriFoodWatermark.png";
			  data.put("logo", logo);
			/*
			 * String logo = (String) session.getAttribute("ORGANIZATION_LOGO"); URL getUrl
			 * = null; try { getUrl = new URL(logo); } catch (MalformedURLException e2) { //
			 * TODO Auto-generated catch block e2.printStackTrace(); } String encodedLogoUrl
			 * = DownloadDocumentUrl.downloadDocumentUrl(getUrl); data.put("logo",
			 * "data:image/png;base64," + encodedLogoUrl);
			 */

			response.setContentType("application/pdf");
			if(type.equals("partB")) {
				response.setHeader("Content-disposition", "inline; filename=partBIngredientPdf.pdf");
			}else if(type.equals("wScale")) {
				response.setHeader("Content-disposition", "inline; filename=weighingScaleAndPolyBagLeakageChallangeTest.pdf");
			}else if(type.equals("blending")) {
				response.setHeader("Content-disposition", "inline; filename=dispensingRecord.pdf");
			}else if(type.equals("alc")) {
				response.setHeader("Content-disposition", "inline; filename=areaLineClearanceReport.pdf");
			}
			File file = null;
			byte[] fileData = null;
			try {
				if(type.equals("partB")) {
					file = pdfGeneratorUtil.createPdf("production_plan/partBIngredientPdf.html", data);
				}else if(type.equals("wScale")) {
					file = pdfGeneratorUtil.createPdf("production_plan/weighingScalePdf.html", data);
				}else if(type.equals("blending")) {
					file = pdfGeneratorUtil.createPdf("production_plan/fentoBlendingDispensingRecordPdf.html", data);
				}else if(type.equals("alc")) {
					file = pdfGeneratorUtil.createPdf("production_plan/areaLineClearancePdfLMR2.html", data);
				}
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

			logger.info("Method : getLmrLogPdfDtls ends");
		}
		

		// Search
		@SuppressWarnings("unchecked")
		@GetMapping("lmr-log-view-search")
		public @ResponseBody Object lmrLogDataViewSearch(@RequestParam String searchValue, HttpSession session) {

		    logger.info("Method :lmrLogDataViewSearch starts");
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
		        resp = restTemplate.getForObject(env.getProduction() +
		                "rest-lmrLogDataViewSearch?orgName=" + orgName + "&orgDivision=" +
		                orgDivision + "&searchValue=" + searchValue, JsonResponse.class);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    logger.info("Method :lmrLogDataViewSearch ends");

		    logger.info(">>>-----"+resp);

		    return resp;
		}

}
