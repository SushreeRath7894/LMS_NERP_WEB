package nirmalya.aathithya.webmodule.master.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.TimeSheetAllProjectDetailsModel;
import nirmalya.aathithya.webmodule.master.model.TimeSheetModel;
import nirmalya.aathithya.webmodule.master.model.TimeSheetProjectDetailsModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class TimeSheetController {
	Logger logger = LoggerFactory.getLogger(TimeSheetController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping(value = { "time-sheet" })
	public String timeSheet(Model model, HttpSession session) {
		logger.info("Method : timeSheet starts");
		/*
		 * try { DropDownModel[] year = restClient.getForObject(env.getMasterUrl() +
		 * "getYearList", DropDownModel[].class); List<DropDownModel> yearList =
		 * Arrays.asList(year);
		 * 
		 * List<Integer> yearData = new ArrayList<Integer>();
		 * 
		 * int startYear = Integer.parseInt(yearList.get(0).getKey()); int endYear =
		 * Integer.parseInt(yearList.get(0).getName());
		 * 
		 * for (int i = startYear; i <= endYear; i++) { yearData.add(i); }
		 * 
		 * model.addAttribute("yearList", yearData); } catch (RestClientException e) {
		 * e.printStackTrace(); }
		 */
		
		try {
			DropDownModel[] year = restClient.getForObject(env.getMasterUrl() + "getYearList", DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			List<String> yearData = new ArrayList<String>();

			int startYear = Integer.parseInt(yearList.get(0).getKey());
			int endYear = Integer.parseInt(yearList.get(0).getName());
			for (int i = 0; i < yearList.size(); i++) {
				yearData.add(yearList.get(i).getKey());
			}

			model.addAttribute("yearList", yearData);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		logger.info("Method : timeSheet ends");
		return "master/timesheet";
	}
	@SuppressWarnings("unchecked")
	@GetMapping("time-sheet-view-manage-employee-master-through-ajax")
	public @ResponseBody List<TimeSheetAllProjectDetailsModel> empolyeeThroughajax(Model model, HttpServletRequest request) {
		logger.info("Method : empolyeeThroughajax starts");

		JsonResponse<List<TimeSheetAllProjectDetailsModel>> jsonResponse = new JsonResponse<List<TimeSheetAllProjectDetailsModel>>();

		try {

			jsonResponse = restClient.getForObject(env.getMasterUrl() + "view-employee-details",
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<TimeSheetAllProjectDetailsModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<TimeSheetAllProjectDetailsModel>>() {
					});

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; empolyeeThroughajax ends");


		return jsonResponse.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "time-sheet-get-week-list" })
	public @ResponseBody JsonResponse<Object> getWeekDays(Model model, @RequestBody List<String> month,
			BindingResult result) {
		logger.info("Method : getWeekDays starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getWeekDays?id=" + month, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getWeekDays ends");
		return res;

	}
	/*
	@SuppressWarnings("unchecked")
	@PostMapping("time-sheet-get-project-list")
	public @ResponseBody List<TimeSheetProjectDetailsModel> getProject(Model model, HttpServletRequest request, @RequestBody String empId) {
		logger.info("Method : getProject starts");

		JsonResponse<List<TimeSheetProjectDetailsModel>> jsonResponse = new JsonResponse<List<TimeSheetProjectDetailsModel>>();

		try {

			jsonResponse = restClient.getForObject(env.getMasterUrl() + "getProjectDetails?id=" + empId,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<TimeSheetProjectDetailsModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<TimeSheetProjectDetailsModel>>() {
					});

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; vendorLocationThroughAjax ends");
		logger.info("time" + jsonResponse.getBody());

		return jsonResponse.getBody();
	}*/
	@SuppressWarnings("unchecked")
	@PostMapping("/time-sheet-get-project-list")
	public @ResponseBody JsonResponse<List<TimeSheetProjectDetailsModel>> getProject(HttpSession session,@RequestBody List<String> empId) {
		logger.info("Method : getProject starts");
		
		JsonResponse<List<TimeSheetProjectDetailsModel>> resp = new JsonResponse<List<TimeSheetProjectDetailsModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getProjectDetails?id=" + empId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getAllChartAccList starts");
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "time-sheet-save" )
	public @ResponseBody JsonResponse<Object> saveTimeSheet(@RequestBody List<TimeSheetModel> timeSheetModel,
			Model model, HttpSession session) {
		logger.info("Method : saveTimeSheet function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			for (int i = 0; i < timeSheetModel.size(); i++) {
				timeSheetModel.get(i).setCreatedBy(userId);
			}
			res = restClient.postForObject(env.getMasterUrl() + "saveTimeSheet", timeSheetModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : saveTimeSheet function Ends");
		return res;
	}
	//total Timesheet through ajax
		@SuppressWarnings("unchecked")
		@GetMapping("time-sheet-total-hour-through-ajax")
		public @ResponseBody List<TimeSheetAllProjectDetailsModel> timeSheetThroughAjax(Model model,
				HttpServletRequest request, @RequestParam String id,@RequestParam String year,HttpSession session) {
			logger.info("Method : timeSheetThroughAjax starts");
			List<TimeSheetAllProjectDetailsModel> list = new ArrayList<TimeSheetAllProjectDetailsModel>();
			JsonResponse<List<TimeSheetAllProjectDetailsModel>> jsonResponse = new JsonResponse<List<TimeSheetAllProjectDetailsModel>>();

			try {

				jsonResponse = restClient.getForObject(env.getMasterUrl() + "get-time-sheet-total-list?id=" + id +"&year=" + year ,
						JsonResponse.class);

				ObjectMapper mapper = new ObjectMapper();

				List<TimeSheetAllProjectDetailsModel> addreq = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<TimeSheetAllProjectDetailsModel>>() {
						});
		
				TimeSheetAllProjectDetailsModel timeSheetAllProjectDetailsModel = new TimeSheetAllProjectDetailsModel();
				if(addreq.size()==0) {
				
					timeSheetAllProjectDetailsModel.setJanHour("0");
					timeSheetAllProjectDetailsModel.setFebHour("0");
					timeSheetAllProjectDetailsModel.setMarchHour("0");
					timeSheetAllProjectDetailsModel.setAprilHour("0");
					timeSheetAllProjectDetailsModel.setMayHour("0");
					timeSheetAllProjectDetailsModel.setJuneHour("0");
					timeSheetAllProjectDetailsModel.setJulyHour("0");
					timeSheetAllProjectDetailsModel.setAugHour("0");
					timeSheetAllProjectDetailsModel.setSepHour("0");
					timeSheetAllProjectDetailsModel.setOctHour("0");
					timeSheetAllProjectDetailsModel.setNovHour("0");
					timeSheetAllProjectDetailsModel.setDecHour("0");
					list.add(timeSheetAllProjectDetailsModel);
					jsonResponse.setBody(list);
				}else {
					jsonResponse.setBody(addreq);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method ; timeSheetThroughAjax ends");
			
			return jsonResponse.getBody();
		}
		
		@SuppressWarnings("unchecked")
		@PostMapping("time-sheet-delete-project-details")
		public @ResponseBody JsonResponse<Object> deleteTimeSheetProDetails(@RequestParam String empId,@RequestParam String year,
				@RequestParam String monthdata,@RequestParam String week,
				Model model, HttpSession session) {
			logger.info("Method : deleteTimeSheetProDetails function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String createdBy = "";

			try {
				createdBy = (String) session.getAttribute("USER_ID");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				res = restClient.getForObject(env.getMasterUrl() + "deleteTimeSheetProDetails?empId=" + empId +"&year="+year+"&monthdata="+monthdata+"&week="+week + "&createdBy="
						+ createdBy , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteTimeSheet function Ends");
			return res;
		}
		@SuppressWarnings("unchecked")
		@PostMapping("time-sheet-delete")
		public @ResponseBody JsonResponse<Object> deleteTimeSheet(@RequestParam String id,
				Model model, HttpSession session) {
			logger.info("Method : deleteTimeSheet function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String createdBy = "";

			try {
				createdBy = (String) session.getAttribute("USER_ID");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				res = restClient.getForObject(env.getMasterUrl() + "deleteTimeSheetEmp?id=" + id + "&createdBy="
						+ createdBy , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteTimeSheet function Ends");
			return res;
		}
}
