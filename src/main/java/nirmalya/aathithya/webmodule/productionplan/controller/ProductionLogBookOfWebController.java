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
import nirmalya.aathithya.webmodule.productionplan.model.ProductionLogBookOfWebModel;
import nirmalya.aathithya.webmodule.qa.model.EvaluationOfLaminatesModel;

@Controller
@RequestMapping(value = "production/")
public class ProductionLogBookOfWebController {

	Logger logger = LoggerFactory.getLogger(ProductionLogBookOfWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("production-logbook-of")
	public String productionLogBookOf(Model model, HttpSession session) {
		logger.info("Method : productionLogBookOf starts");

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
			DropDownModel[] shift = restTemplate.getForObject(env.getProduction() + "getShiftListForProduction?org="
					+ org + "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);
			System.out.println("shiftLists>>>>>>>>>>>>>>>>>>>" + shiftLists);
			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/*
		 * try { DropDownModel[] item = restTemplate.getForObject( env.getProduction() +
		 * "get-Item-lists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
		 * List<DropDownModel> itemList = Arrays.asList(item);
		 * model.addAttribute("itemList", itemList); } catch (RestClientException e) {
		 * e.printStackTrace(); }
		 */
		/*
		 * try { DropDownModel[] line = restClient.getForObject( env.getProduction() +
		 * "getLineLists-Lmr?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
		 * List<DropDownModel> lineLists = Arrays.asList(line);
		 * 
		 * model.addAttribute("lineLists", lineLists);
		 * 
		 * } catch (RestClientException e) { e.printStackTrace(); }
		 */
		logger.info("Method : productionLogBookOf ends");
		return "production_plan/production-logbook-of";
	}

	// getaggridData

	@SuppressWarnings("unchecked")

	@GetMapping("production-logbook-of-aggrid-show")
	public @ResponseBody Object getProductionLogOfAggridDatas(HttpSession session) {
		logger.info("Method :getProductionLogOfAggridDatas starts");
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
			resp = restTemplate.getForObject(env.getProduction() + "rest-getProductionLogOfAggridDatas?orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("view===" + resp);
		logger.info("Method :getProductionLogOfAggridDatas ends");
		return resp;
	}

	// add.

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "production-logbook-of-add" })
	public @ResponseBody JsonResponse<Object> addProductionLogBookOf(@RequestBody ProductionLogBookOfWebModel av,
			HttpSession session) {
		logger.info("Method : addProductionLogBookOf function starts");
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

		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-addProductionLogBookOf", av,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addProductionLogBookOf function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	// View.

	@SuppressWarnings("unchecked")
	@GetMapping("production-logbook-of-view")
	public @ResponseBody Object getProductionLogOfView(@RequestParam String pageno, HttpSession session) {
		logger.info("Method :getProductionLogOfView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		/// String pageno = "1";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-getProductionLogOfView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getProductionLogOfView ends");
		return resp;
	}

	// Edit.

	@SuppressWarnings("unchecked")
	@GetMapping("production-logbook-of-edit")
	public @ResponseBody Object editProductionLogOf(@RequestParam String id, HttpSession session) {
		logger.info("Method :editProductionLogOf starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-editProductionLogOf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :editProductionLogOf ends");
		return resp;
	}

	// Delete.
	@SuppressWarnings("unchecked")
	@GetMapping("production-logbook-of-delete")
	public @ResponseBody Object deleteProductionLogOf(@RequestParam String id, HttpSession session) {
		logger.info("Method :deleteProductionLogOf starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-deleteProductionLogOf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :deleteProductionLogOf ends");
		return resp;
	}

	// Approve.
	@SuppressWarnings("unchecked")
	@GetMapping("production-logbook-of-approve")
	public @ResponseBody Object approveProductionLogOf(@RequestParam String id, HttpSession session) {
		logger.info("Method :approveProductionLogOf starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restTemplate.getForObject(env.getProduction() + "rest-approveProductionLogOf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :approveProductionLogOf ends");
		return resp;
	}

	// Pdf.

	@SuppressWarnings("unchecked")
		@GetMapping("production-logbook-of-download")
		public void getProductionLogOfPdfDtls(HttpServletResponse response, Model model, HttpSession session,
				@RequestParam("id") String encodedParam1, @RequestParam("org") String encodedParam2 ,@RequestParam("orgDiv") String encodedParam3) {

			logger.info("Method : getProductionLogOfPdfDtls starts");
			/*
			 * String orgName = ""; String orgDivision = "";
			 */
			/*
			 * try { orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
			 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
			 * { logger.error(e.getMessage()); }
			 */
			byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
			String dcId = (new String(encodeByte1));
			byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
			String orgName = (new String(encodeByte2));
			byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
			String orgDivision = (new String(encodeByte3));
			

			JsonResponse<Object> resp = new JsonResponse<Object>();
			try {
				resp = restTemplate.getForObject(env.getProduction() + "rest-getProductionLogOfPdfDtls?id=" + dcId  + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("JsonResponse====" + resp.getBody());

			List<ProductionLogBookOfWebModel> listModel = new ArrayList<ProductionLogBookOfWebModel>();
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
	 System.out.println("listModel>>>>>>>>>>>>>>>>>>>>>>>>" + listModel);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("pdfDtls", listModel);
	 
			System.out.println("data====" + data);
			//String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			//System.out.println("logo>>>>>"+logo);
			/*
			 * String logo = "classpath:static/assets/images/invoice-banner.jpg";
			 * data.put("logo", logo);
			 */
			
			/*
			 * String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			 * System.out.println("logo>>>>>"+logo); URL getUrl= null; try { getUrl = new
			 * URL(logo); } catch (MalformedURLException e2) { // TODO Auto-generated catch
			 * block e2.printStackTrace(); } String encodedLogoUrl =
			 * DownloadDocumentUrl.downloadDocumentUrl(getUrl); data.put("logo",
			 * "data:image/png;base64," + encodedLogoUrl);
			 */
			 
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=ProductionLogBookPdf.pdf");
			File file = null;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("production_plan/production-logbook-of-pdf.html", data);
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

			logger.info("Method : getProductionLogOfPdfDtls ends");
		}

	// search
	@SuppressWarnings("unchecked")
	@GetMapping("production-logbook-of-view-search")
	public @ResponseBody Object searchProductionLogOfView(@RequestParam String value, HttpSession session) {
		logger.info("Method :searchProductionLogOfView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		/// String pageno = "1";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-searchProductionLogOfView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&value=" + value, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :searchProductionLogOfView ends");
		return resp;
	}

}
