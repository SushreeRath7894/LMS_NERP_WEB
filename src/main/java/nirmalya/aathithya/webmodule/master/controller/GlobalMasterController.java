package nirmalya.aathithya.webmodule.master.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.DateFormatModel;
import nirmalya.aathithya.webmodule.master.model.FiscalYearModel;
import nirmalya.aathithya.webmodule.master.model.GlobalMasterModel;
import nirmalya.aathithya.webmodule.master.model.YearMasterModel;

@Controller
@RequestMapping(value = "master")
public class GlobalMasterController {

	Logger logger = LoggerFactory.getLogger(GlobalMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	// Summary
	@GetMapping("/geography-master")
	public String manageLocation(Model model, HttpSession session) {
		logger.info("Method : manageGlobal starts");

		try {

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList", DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			List<String> yearData = new ArrayList<String>();

			for (DropDownModel m : yearList) {
				yearData.add(m.getKey());
			}

			model.addAttribute("yearList", yearData);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dateFormat = restTemplate.getForObject(env.getMasterUrl() + "getDateFormatTypeList",
					DropDownModel[].class);
			List<DropDownModel> dateFormatTypeList = Arrays.asList(dateFormat);

			model.addAttribute("dateFormatTypeList", dateFormatTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : manageGlobal ends");
		return "master/geography-master";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-country-type")
	public @ResponseBody JsonResponse<Object> addGlobalMaster(@RequestBody GlobalMasterModel global,
			HttpSession session) {
		logger.info("Method : addGlobalMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		global.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addGlobalMaster", global, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addGlobalMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-save-fiscal-year")
	public @ResponseBody JsonResponse<Object> addFiscalyear(@RequestBody FiscalYearModel fiscalyr,
			HttpSession session) {
		logger.info("Method : addFiscalyear starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		fiscalyr.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addFiscalyear", fiscalyr, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : addFiscalyear starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-save-calendar-year")
	public @ResponseBody JsonResponse<Object> addCalenedarYear(@RequestBody YearMasterModel calendarYr,
			HttpSession session) {
		logger.info("Method : addCalenedarYear starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		calendarYr.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addCalenedarYear", calendarYr, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : addCalenedarYear starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-delete-fiscal-year")
	public @ResponseBody JsonResponse<Object> deleteFiscalyear(@RequestBody String fiscalyr, HttpSession session) {
		logger.info("Method : deleteFiscalyear starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteFiscalyear?id=" + fiscalyr,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : deleteFiscalyear starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-change-dateformat")
	public @ResponseBody JsonResponse<DateFormatModel> changeDateFormat(@RequestBody String id, HttpSession session) {
		logger.info("Method : changeDateFormat starts");

		JsonResponse<DateFormatModel> resp = new JsonResponse<DateFormatModel>();
		DateFormatModel data = new DateFormatModel();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "changeDateFormat?id=" + id + "&userId=" + userId,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			data = mapper.convertValue(resp.getBody(), new TypeReference<DateFormatModel>() {
			});

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
			session.setAttribute("DATEFORMAT", data.getDfType());
			session.setAttribute("DATEFORMAT_JS", data.getDfJS());
			session.setAttribute("DATEFORMAT_ID", id);
		}

		logger.info("Method : changeDateFormat starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-country-type-view")
	public @ResponseBody List<GlobalMasterModel> viewGlobalMaster(HttpSession session) {
		logger.info("Method : viewGlobalMaster starts");

		JsonResponse<List<GlobalMasterModel>> resp = new JsonResponse<List<GlobalMasterModel>>();
		List<GlobalMasterModel> returnList = new ArrayList<GlobalMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewGlobalMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewGlobalMaster ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-country-type-delete")
	public @ResponseBody JsonResponse<Object> deleteGlobalMaster(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteGlobalMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteGlobalMaster?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteGlobalMaster ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-get-fiscal-year-list")
	public @ResponseBody JsonResponse<List<FiscalYearModel>> getFiscalYearList(HttpSession session,
			@RequestBody String id) {
		logger.info("Method : getFiscalYearList starts");

		JsonResponse<List<FiscalYearModel>> resp = new JsonResponse<List<FiscalYearModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getFiscalYearList", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("success");
		}

		logger.info("Method : getFiscalYearList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-state-type-view-onclick")
	public @ResponseBody List<GlobalMasterModel> viewOnclickState(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewOnclickState starts");

		JsonResponse<List<GlobalMasterModel>> resp = new JsonResponse<List<GlobalMasterModel>>();
		List<GlobalMasterModel> returnList = new ArrayList<GlobalMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewOnclickState?id=" + id, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewOnclickState ends");
		return returnList;
	}

	/* State Start */

	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-state-type")
	public @ResponseBody JsonResponse<Object> addStateMaster(@RequestBody GlobalMasterModel state,
			HttpSession session) {
		logger.info("Method : addStateMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		state.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addStateMaster", state, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addStateMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-state-type-view")
	public @ResponseBody List<GlobalMasterModel> viewStateMaster(HttpSession session) {
		logger.info("Method : viewStateMaster starts");

		JsonResponse<List<GlobalMasterModel>> resp = new JsonResponse<List<GlobalMasterModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewStateMaster", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : viewStateMaster ends");
		return resp.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-state-type-delete")
	public @ResponseBody JsonResponse<Object> deleteStateMaster(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteStateMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteStateMaster?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteStateMaster ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-district-type-view-onclick")
	public @ResponseBody List<GlobalMasterModel> viewOnclickDistrict(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewOnclickDistrict starts");

		JsonResponse<List<GlobalMasterModel>> resp = new JsonResponse<List<GlobalMasterModel>>();
		List<GlobalMasterModel> returnList = new ArrayList<GlobalMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewOnclickDistrict?id=" + id, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewOnclickDistrict ends");
		return returnList;
	}

	/* DISTRICT STARTS */
	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-district-type")
	public @ResponseBody JsonResponse<Object> addDistrictMaster(@RequestBody GlobalMasterModel dis,
			HttpSession session) {
		logger.info("Method : addDistrictMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		dis.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addDistrictMaster", dis, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addDistrictMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-district-type-view")
	public @ResponseBody List<GlobalMasterModel> viewDistrictMaster(HttpSession session) {
		logger.info("Method : viewDistrictMaster starts");

		JsonResponse<List<GlobalMasterModel>> resp = new JsonResponse<List<GlobalMasterModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewDistrictMaster", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : viewDistrictMaster ends");
		return resp.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-district-type-delete")
	public @ResponseBody JsonResponse<Object> deleteDistrictMaster(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteDistrictMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteDistrictMaster?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteDistrictMaster ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-city-type-view-onclick")
	public @ResponseBody List<GlobalMasterModel> viewOnclickCity(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewOnclickCity starts");

		JsonResponse<List<GlobalMasterModel>> resp = new JsonResponse<List<GlobalMasterModel>>();
		List<GlobalMasterModel> returnList = new ArrayList<GlobalMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewOnclickCity?id=" + id, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewOnclickCity ends");
		return returnList;
	}

	/* CITY STARTS */
	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-city-type")
	public @ResponseBody JsonResponse<Object> addCityMaster(@RequestBody GlobalMasterModel city, HttpSession session) {
		logger.info("Method : addCityMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		city.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addCityMaster", city, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : addCityMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-city-type-view")
	public @ResponseBody List<GlobalMasterModel> viewCityMaster(HttpSession session) {
		logger.info("Method : viewCityMaster starts");

		JsonResponse<List<GlobalMasterModel>> resp = new JsonResponse<List<GlobalMasterModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewCityMaster", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : viewCityMaster ends");
		return resp.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-city-type-delete")
	public @ResponseBody JsonResponse<Object> deleteCityMaster(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteCityMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteCityMaster?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteCityMaster ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-get-year-list")
	public @ResponseBody List<YearMasterModel> viewYearListMaster(HttpSession session) {
		logger.info("Method : viewYearListMaster starts");

		JsonResponse<List<YearMasterModel>> resp = new JsonResponse<List<YearMasterModel>>();
		List<YearMasterModel> returnList = new ArrayList<YearMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewYearListMaster", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewYearListMaster ends");
		return returnList;
	}

	/*
	 * upload city csv
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-city-type-upload-data")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<GlobalMasterModel> tutorials = new ArrayList<GlobalMasterModel>();

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				tutorials.add(
						new GlobalMasterModel(csvRecord.get("country"), csvRecord.get("state"), csvRecord.get("dist"),
								csvRecord.get("Name"), csvRecord.get("order_Id"), csvRecord.get("status"), userId));

			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addCityMasterList", tutorials,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	/*
	 * upload country csv
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-upload-global-data")
	public @ResponseBody JsonResponse<Object> uploadGlobalCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		List<GlobalMasterModel> countryList = new ArrayList<GlobalMasterModel>();
		String countyName = "Country Name";
		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withHeader("Order", "Code", countyName, "Status").withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {

				countryList.add(new GlobalMasterModel(csvRecord.get("Order"), csvRecord.get("Code"),
						csvRecord.get(countyName), csvRecord.get("Status")));

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
					countryList.add(new GlobalMasterModel(splitParam[0], splitParam[1], splitParam[2], splitParam[3]));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (countryList != null) {
			countryList.forEach(s -> s.setCreatedBy((String) session.getAttribute("USER_ID")));
			countryList.stream().forEach(s -> {
				if (s.getCountryStatus().contains("Active")) {
					s.setCountryStatus("1");
				} else {
					s.setCountryStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addCountryMasterList", countryList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	/*
	 * upload state csv
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-upload-state-data")
	public @ResponseBody JsonResponse<Object> uploadStateCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<GlobalMasterModel> countryList = new ArrayList<GlobalMasterModel>();
		String stateName = "State Name";
		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withHeader("Country", "Order", "Code", stateName, "Status")
								.withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {

				countryList.add(new GlobalMasterModel(csvRecord.get("Country"), csvRecord.get(stateName),
						csvRecord.get("Code"), csvRecord.get("Order"), csvRecord.get("Status"), userId));

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
					countryList.add(new GlobalMasterModel(splitParam[0], splitParam[1], splitParam[2], splitParam[3],
							splitParam[4], userId));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (countryList != null) {
			countryList.stream().forEach(s -> {
				if (s.getStateStatus().contains("Active")) {
					s.setCountryStatus("1");
				} else {
					s.setCountryStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addStateMasterList", countryList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	/*
	 * upload dist csv
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/geography-master-upload-dist-data")
	public @ResponseBody JsonResponse<Object> uploadDistCsv(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");

		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = (String) session.getAttribute("USER_ID");
		List<GlobalMasterModel> countryList = new ArrayList<GlobalMasterModel>();
		String stateName = "State Name";
		try (Reader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withHeader("Country", "Order", "Code", stateName, "Status")
								.withIgnoreHeaderCase().withTrim());) {

			for (CSVRecord csvRecord : csvParser) {

				countryList.add(new GlobalMasterModel(csvRecord.get("Country"), csvRecord.get(stateName),
						csvRecord.get("Code"), csvRecord.get("Order"), csvRecord.get("Status"), userId));

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
					countryList.add(new GlobalMasterModel(splitParam[0], splitParam[1], splitParam[2], splitParam[3],
							splitParam[4], userId));

				}
			} catch (IOException ex) {
				response.setMessage(ex.getMessage());
				logger.error(ex.getMessage());
			}

		}

		if (countryList != null) {
			countryList.stream().forEach(s -> {
				if (s.getCountryStatus().contains("Active")) {
					s.setCountryStatus("1");
				} else {
					s.setCountryStatus("0");
				}
			});
		}
		try {
			response = restTemplate.postForObject(env.getMasterUrl() + "addDistMasterList", countryList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	/*
	 * for view all global data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/geography-master-total-view")
	public @ResponseBody List<GlobalMasterModel> viewTotalGlobal(HttpSession session) {
		logger.info("Method : viewTotalGlobal starts");

		JsonResponse<List<GlobalMasterModel>> resp = new JsonResponse<List<GlobalMasterModel>>();
		List<GlobalMasterModel> returnList = new ArrayList<GlobalMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewTotalGlobal", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewTotalGlobal ends");
		return returnList;
	}
}
