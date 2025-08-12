package nirmalya.aathithya.webmodule.master.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
import nirmalya.aathithya.webmodule.master.model.ReferenceProductModel;

@Controller
@RequestMapping(value = "master/")
public class ReferenceProductController {

	Logger logger = LoggerFactory.getLogger(ReferenceProductController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	/*
	 * @GetMapping("/insuranceName") public String displayPage(Model model,
	 * HttpSession session) { logger.info("Method : Insurance Provider starts");
	 * 
	 * // Fetch insurance data DropDownModel[] insurance =
	 * restTemplate.getForObject(env.getMasterUrl() + "/insurance",
	 * DropDownModel[].class); List<DropDownModel> insuranceList =
	 * Arrays.asList(insurance); model.addAttribute("insurance", insuranceList);
	 * 
	 * logger.info("Method : Insurance Provider ends"); return
	 * "master/insuranceName"; }
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("add-insurance-Name")
	public @ResponseBody JsonResponse<List<DropDownModel>> getinsuranceList(Model model,
			HttpSession session) {
		logger.info("Method : getinsuranceList starts");

		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getinsuranceList?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} 
		catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :  getinsuranceList ends");
		return resp;
	}
	
	

///////////// Brand Type//////////////

	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-brand-type")
	public @ResponseBody JsonResponse<Object> addBrandType(@RequestBody ReferenceProductModel brand,
			HttpSession session) {
		logger.info("Method : addBrandType starts");

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
		
		brand.setBrandCreatedBy(userId);
		brand.setOrg(organization);
		brand.setOrgDiv(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addBrandType", brand, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addBrandType starts");

		return resp;
	}

//View page for brand types

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-brand-type")
	public @ResponseBody List<ReferenceProductModel> viewBrandType(HttpSession session) {
		logger.info("Method : viewBrandType starts");

		JsonResponse<List<ReferenceProductModel>> resp = new JsonResponse<List<ReferenceProductModel>>();
		List<ReferenceProductModel> brandList = new ArrayList<ReferenceProductModel>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewBrandType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			brandList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		logger.info("Method : viewBrandType ends");
		return brandList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-brand-type-delete")
	public @ResponseBody JsonResponse<Object> deletebrandType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deletebrandType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deletebrandType?id=" + id + "&org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deletebrandType ends");
		return resp;
	}

/////////////--------------Product Type-----------------//////////////

	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-product-type")
	public @ResponseBody JsonResponse<Object> addProductType(@RequestBody ReferenceProductModel brand,
			HttpSession session) {

		logger.info("Method : addProductType starts");

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

		brand.setProductCreatedBy(userId);
		brand.setOrg(organization);
		brand.setOrgDiv(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addProductType", brand, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addProductType starts");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-product-type")
	public @ResponseBody List<ReferenceProductModel> viewProductType(HttpSession session) {
		logger.info("Method : viewProductType starts");

		JsonResponse<List<ReferenceProductModel>> resp = new JsonResponse<List<ReferenceProductModel>>();
		List<ReferenceProductModel> productList = new ArrayList<ReferenceProductModel>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewProductType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			productList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		logger.info("Method : viewProductType ends");
		return productList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-product-type-delete")
	public @ResponseBody JsonResponse<Object> deleteProductType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteProductType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteProductType?id=" + id + "&org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteProductType ends");
		return resp;
	}
	
/////////////--------------Insurance-----------------//////////////
	@SuppressWarnings("unchecked")
	@PostMapping("/add-insurance-type")
	public @ResponseBody JsonResponse<Object> addInsuranceType(@RequestBody ReferenceProductModel brand,
			HttpSession session) {

		logger.info("Method : addInsuranceType starts");

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

		brand.setProductCreatedBy(userId);
		brand.setOrg(organization);
		brand.setOrgDiv(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addInsuranceType", brand, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addInsuranceType starts");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-insurance-type")
	public @ResponseBody List<ReferenceProductModel> viewInsuranceType(HttpSession session) {
		logger.info("Method : viewInsuranceType starts");

		JsonResponse<List<ReferenceProductModel>> resp = new JsonResponse<List<ReferenceProductModel>>();
		List<ReferenceProductModel> insuranceList = new ArrayList<ReferenceProductModel>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewInsuranceType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			insuranceList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		logger.info("Method : viewInsuranceType ends");
		return insuranceList;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/insurance-type-delete")
	public @ResponseBody JsonResponse<Object> deleteInsuranceType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteInsuranceType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteInsuranceType?id=" + id + "&org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteInsuranceType ends");
		return resp;
	}
/////////////--------------Insurance-----------------//////////////

/////////////--------------Insurance Provider-----------------//////////////
@SuppressWarnings("unchecked")
@PostMapping("/add-insuranceProvider-type")
public @ResponseBody JsonResponse<Object> addInsuranceProvdrType(@RequestBody ReferenceProductModel brand,
		HttpSession session) {

	logger.info("Method : addInsuranceProvdrType starts"+ brand);

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

	brand.setProductCreatedBy(userId);
	brand.setOrg(organization);
	brand.setOrgDiv(orgDivision);

	try {
		resp = restTemplate.postForObject(env.getMasterUrl() + "addInsuranceProvdrType", brand, JsonResponse.class);
	} catch (RestClientException e) {
		e.printStackTrace();
	}

	String message = resp.getMessage();

	if (message != null && message != "") {

	} else {

		resp.setMessage("Success");
	}

	logger.info("Method : addInsuranceProvdrType starts");

return resp;
}

@SuppressWarnings("unchecked")
@GetMapping("/view-insuranceProvdr-type")
public @ResponseBody List<ReferenceProductModel> viewInsuranceProviderType(HttpSession session) {
	logger.info("Method : viewInsuranceProviderType starts");

	JsonResponse<List<ReferenceProductModel>> resp = new JsonResponse<List<ReferenceProductModel>>();
	List<ReferenceProductModel> insuranceProviderList = new ArrayList<ReferenceProductModel>();
	
	String organization = "";
	String orgDivision = "";
	
	try {
		organization = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	} catch (Exception e) {
		e.printStackTrace();
	}

	try {
		resp = restTemplate.getForObject(env.getMasterUrl() + "viewInsuranceProviderType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		insuranceProviderList = resp.getBody();
	} catch (RestClientException e) {
		e.printStackTrace();
	} 
	logger.info("Method : viewInsuranceProviderType ends");
	return insuranceProviderList;
}

@SuppressWarnings("unchecked")
@GetMapping("/insurancePrvdr-type-delete")
public @ResponseBody JsonResponse<Object> deleteInsuranceProviderType(HttpSession session, @RequestParam String id) {
	logger.info("Method : deleteInsuranceProviderType starts");

	JsonResponse<Object> resp = new JsonResponse<Object>();
	
	String organization = "";
	String orgDivision = "";
	
	try {
		organization = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	} catch (Exception e) {
		e.printStackTrace();
	}

	try {
		resp = restTemplate.getForObject(env.getMasterUrl() + "deleteInsuranceProviderType?id=" + id + "&org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
	} catch (RestClientException e) {
		e.printStackTrace();
	}

	String message = resp.getMessage();

	if (message != null && message != "") {

	} else {

		resp.setMessage("Success");
	}

	logger.info("Method : deleteInsuranceProviderType ends");
	return resp;
}
/////////////--------------Insurance Provider-----------------//////////////

/////////////--------------Variation Type-----------------//////////////

	@SuppressWarnings("unchecked")
	@PostMapping("/view-procurement-add-variation-type")
	public @ResponseBody JsonResponse<Object> addVariationType(@RequestBody ReferenceProductModel var,
			HttpSession session) {
		logger.info("Method : addVariationType starts");

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

		var.setVariationCreatedBy(userId);
		var.setOrg(organization);
		var.setOrgDiv(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addVariationType", var, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addVariationType starts");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-view-variation-type")
	public @ResponseBody List<ReferenceProductModel> viewVariationType(HttpSession session) {
		logger.info("Method : viewVariationType starts");

		JsonResponse<List<ReferenceProductModel>> resp = new JsonResponse<List<ReferenceProductModel>>();
		List<ReferenceProductModel> productList = new ArrayList<ReferenceProductModel>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewVariationType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			productList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		logger.info("Method : viewVariationType ends");
		return productList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-procurement-variation-type-delete")
	public @ResponseBody JsonResponse<Object> deleteVariationType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteVariationType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteVariationType?id=" + id + "&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteVariationType ends");
		return resp;
	}
	
/////////////--------------Variation Type-----------------//////////////
	
/////////////--------------Mode Of Transport-----------------//////////////
	@SuppressWarnings("unchecked")
	@PostMapping("/add-transport-type")
	public @ResponseBody JsonResponse<Object> addTransportType(@RequestBody ReferenceProductModel brand,
			HttpSession session) {

		logger.info("Method : addTransportType starts");

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

		brand.setProductCreatedBy(userId);
		brand.setOrg(organization);
		brand.setOrgDiv(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addTransportType", brand, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addTransportType starts");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-transport-type")
	public @ResponseBody List<ReferenceProductModel> viewtransportType(HttpSession session) {
		logger.info("Method : viewtransportType starts");

		JsonResponse<List<ReferenceProductModel>> resp = new JsonResponse<List<ReferenceProductModel>>();
		List<ReferenceProductModel> transportList = new ArrayList<ReferenceProductModel>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewtransportType?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			transportList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		logger.info("Method : viewtransportType ends");
		return transportList;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/transport-type-delete")
	public @ResponseBody JsonResponse<Object> deleteTransportType(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteTransportType starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteTransportType?id=" + id + "&org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteTransportType ends");
		return resp;
	}
/////////////--------------Mode Of Transport-----------------//////////////

	/**
	 * for upload brand type csv data
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/referenceProduct-brand-type-csv-data")
	public @ResponseBody JsonResponse<Object> addBrandTypeCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : addBrandTypeCsv uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ReferenceProductModel> referenceProductList = new ArrayList<ReferenceProductModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("ORDER", "CODE","BRAND NAME", "STATUS").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {
				referenceProductList.add(new ReferenceProductModel(csvRecord.get("ORDER"), csvRecord.get("CODE"),
						csvRecord.get("BRAND NAME"), csvRecord.get("STATUS"), userId));
			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					referenceProductList.add(new ReferenceProductModel(splitParam[0], splitParam[1], splitParam[2],
							splitParam[3], userId));
				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (referenceProductList != null) {
			referenceProductList.stream().forEach(s -> {
				if (s.getBrandStatus().contains("Active")) {
					s.setBrandStatus("1");
				} else {
					s.setBrandStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addBrandTypeCsv", referenceProductList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :addBrandTypeCsv uploadFile controller function 'post-mapping' ends");
		return response;
	}
	
	
	
	/**
	 * for upload product type csv data
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/referenceProduct-product-type-csv-data")
	public @ResponseBody JsonResponse<Object> addProductTypeCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : addProductTypeCsv uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ReferenceProductModel> referenceProductList = new ArrayList<ReferenceProductModel>();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Name", "Description", "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {
				referenceProductList.add(new ReferenceProductModel(csvRecord.get("Name"), 
						csvRecord.get("Description"), csvRecord.get("Status"), userId));
			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					referenceProductList.add(new ReferenceProductModel(splitParam[0], splitParam[1], splitParam[2], userId));
				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (referenceProductList != null) {
			referenceProductList.stream().forEach(s -> {
				if (s.getProductStatus().contains("Active")) {
					s.setProductStatus("1");
				} else {
					s.setProductStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addProductTypeCsv", referenceProductList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :addProductTypeCsv uploadFile controller function 'post-mapping' ends");
		return response;
	}
	/**
	 * for upload product type csv data
	 * @param requisitionList
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/referenceProduct-variation-type-csv-data")
	public @ResponseBody JsonResponse<Object> addVariationTypeCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : addVariationTypeCsv uploadFile controller function 'post-mapping' starts");
		
		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<ReferenceProductModel> referenceProductList = new ArrayList<ReferenceProductModel>();
		
		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Name", "Description", "Status").withIgnoreHeaderCase().withTrim());) {
			
			for (CSVRecord csvRecord : csvParser) {
				ReferenceProductModel ob = new ReferenceProductModel();
				ob.setVariationName(csvRecord.get("Name"));
				ob.setVariationDesc(csvRecord.get("Description"));
				ob.setVariationStatus(csvRecord.get("Status"));
				ob.setVariationCreatedBy(userId);
				referenceProductList.add(ob);
			}
		} catch (IOException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(reader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
				
				for (CSVRecord csvRecord : csvParser) {
					String[] splitParam = csvRecord.get(0).split("\t");
					
					ReferenceProductModel ob = new ReferenceProductModel();
					ob.setVariationName(splitParam[0]);
					ob.setVariationDesc(splitParam[1]);
					ob.setVariationStatus(splitParam[2]);
					ob.setVariationCreatedBy(userId);
					referenceProductList.add(ob);
				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}
			
		}
		
		if (referenceProductList != null) {
			referenceProductList.stream().forEach(s -> {
				if (s.getVariationStatus().contains("Active")) {
					s.setVariationStatus("1");
				} else {
					s.setVariationStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addVariationTypeCsv", referenceProductList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addVariationTypeCsv uploadFile controller function 'post-mapping' ends");
		return response;
	}
}
