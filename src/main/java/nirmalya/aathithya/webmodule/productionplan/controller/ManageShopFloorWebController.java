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
import nirmalya.aathithya.webmodule.productionplan.model.ManageShopFloorModel;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;

@Controller
@RequestMapping(value = "production")
public class ManageShopFloorWebController {
	
	Logger logger = LoggerFactory.getLogger(ManageShopFloorWebController.class);
	
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	private String userId;
    private String uType;
    private String orgName;
    private String orgDiv;

	
	@GetMapping(value = { "manage-shop-floor" })
	public String usageCapacity(Model model, HttpSession session) {
		logger.info("Method : usageCapacity starts");
		
		
		    userId = (String) session.getAttribute("USER_ID");
	        uType = (String) session.getAttribute("USER_ROLETYPE");
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		
		
		
			
			try {
				DropDownModel[] shift = restClient.getForObject(
						env.getProduction() + "getShiftListForProduction?org=" + orgName + "&orgDiv=" + orgDiv +"&userId="+userId,
						DropDownModel[].class);
				List<DropDownModel> shiftLists = Arrays.asList(shift);

				model.addAttribute("shiftLists", shiftLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
		
		
		logger.info("Method : usageCapacity ends{}:");
		return "production_plan/manage-shop-floor";
	}
	

	
	@SuppressWarnings("unchecked")
	@GetMapping("/manage-shop-floor-dtls")
	public @ResponseBody JsonResponse<Object> getShiftWiseMcDetls(HttpSession session,@RequestParam String date,String shift) {
		logger.info("Method : getShiftWiseMcDetls starts"+orgName);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			
			resp = restClient.getForObject(env.getProduction() + "rest-getShiftWiseMcDetls?date=" + date +"&shift="+shift+"&orgName="+orgName+"&orgDiv="+orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getShiftWiseMcDetls ends" + resp);
		return resp;
	}
	
	
	// add.
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "manage-shop-floor-add" })
	public @ResponseBody JsonResponse<Object> addFloor(@RequestBody ManageShopFloorModel av, HttpSession session) {
		logger.info("Method : addFloor function starts");
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
		av.setCreatedBy(userId);
		av.setOrganization(organization);
		av.setOrgDivision(orgDivision);
		
		try {
			resp = restClient.postForObject(env.getProduction() + "rest-addShopFloor", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addFloor function Ends");
		return resp;
	}
	

	// View.
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-shop-floor-view")
	public @ResponseBody Object getShoopFloorView(HttpSession session) {
		logger.info("Method :getShoopFloorView starts");
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
			resp = restClient.getForObject(
					env.getProduction() + "rest-getShoopFloorView?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getShoopFloorView ends");
		return resp;
	}
	
	// Edit.
	
		@SuppressWarnings("unchecked")
		@GetMapping("manage-shop-floor-edit")
		public @ResponseBody Object editFloorData(@RequestParam String id, HttpSession session) {
			logger.info("Method :editFloorData starts");
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

				resp = restClient.getForObject(env.getProduction()  + "rest-editFloorData?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :editFloorData ends"+resp);
			return resp;
		}
		
		

		// add BreakDown.
		
		@SuppressWarnings({ "unchecked" })

		@PostMapping(value = { "manage-shop-floor-add-breakdown" })
		public @ResponseBody Object addBreakDown(@RequestBody ManageShopFloorModel av, HttpSession session) {
			logger.info("Method : addBreakDown function starts");
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
			av.setCreatedBy(userId);
			av.setOrganization(organization);
			av.setOrgDivision(orgDivision);
			
			try {
				resp = restClient.postForObject(env.getProduction() + "rest-addBreakDown", av, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : addBreakDown function Ends");
			return resp;
		}
		
		
		// Approve.
		
		@SuppressWarnings("unchecked")
		@GetMapping("manage-shop-floor-approve")
		public @ResponseBody Object approveShopFloor(@RequestParam String id, HttpSession session) {
			logger.info("Method :approveShopFloor starts");
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
			try {

				resp = restClient.getForObject(env.getProduction()  + "rest-approveShopFloor?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision + "&approvedBy=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :approveShopFloor ends");
			return resp;
		}
		
		
	// Delete.
		
		@SuppressWarnings("unchecked")
		@GetMapping("manage-shop-floor-delete")
		public @ResponseBody Object deleteShopFloor(@RequestParam String id, HttpSession session) {
			logger.info("Method :deleteShopFloor starts");
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
			try {

				resp = restClient.getForObject(env.getProduction()  + "rest-deleteShopFloor?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :deleteShopFloor ends");
			return resp;
		}
		
		// Pdf
		
		@SuppressWarnings("unchecked")
		@GetMapping("manage-shop-floor-pdf-downloads")
		public void getBreakDownPdf(HttpServletResponse response, Model model, HttpSession session,
				@RequestParam("id") String encodedParam1,@RequestParam("mcNo") String encodedParam2,
				@RequestParam("sku") String encodedParam3) {

			logger.info("Method : getBreakDownPdf starts");
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
			String id = (new String(encodeByte1));
			byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
			String mcNo = (new String(encodeByte2));
			byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
			String sku = (new String(encodeByte3));

			System.err.println(" id="+id+" mcNo="+mcNo+" sku="+sku);
			JsonResponse<Object> resp = new JsonResponse<Object>();
			try {
				resp = restClient.getForObject(env.getProduction() + "rest-breakDownPdf?id=" + id + "&mcNo="
						+ mcNo + "&sku=" + sku+ "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			List<ManageShopFloorModel> listModel = new ArrayList<ManageShopFloorModel>();
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
			data.put("dtls", listModel);
	 
			String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
 
			data.put("logo",logo);
			data.put("orgDivision",orgDivision);
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=breakDownDetails.pdf");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("production_plan/breakDownDetails.html", data);
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

			logger.info("Method : breakDownPdf ends");
		}	

}

