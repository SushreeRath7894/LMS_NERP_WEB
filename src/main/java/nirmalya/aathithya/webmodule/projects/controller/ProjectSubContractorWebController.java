/*
 * package nirmalya.aathithya.webmodule.projects.controller;
 * 
 * import java.util.Arrays; import java.util.List;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpSession;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.ResponseBody; import
 * org.springframework.web.client.RestClientException; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import com.fasterxml.jackson.core.type.TypeReference; import
 * com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * import nirmalya.aathithya.webmodule.common.utils.DateFormatter; import
 * nirmalya.aathithya.webmodule.common.utils.DropDownModel; import
 * nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles; import
 * nirmalya.aathithya.webmodule.common.utils.JsonResponse; import
 * nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel; import
 * nirmalya.aathithya.webmodule.projects.model.ProjectSubContractorWebModel;
 * import
 * nirmalya.aathithya.webmodule.projects.model.ProjectSubContractorWebModel;
 * 
 * @SuppressWarnings("unused")
 * 
 * @Controller
 * 
 * @RequestMapping(value = "projects/")
 * 
 * public class ProjectSubContractorWebController {
 * 
 * Logger logger =
 * LoggerFactory.getLogger(ProjectSubContractorWebController.class);
 * 
 * @Autowired RestTemplate restClient;
 * 
 * @Autowired EnvironmentVaribles env;
 * 
 * @GetMapping("/project-sub-contractor") public String projectContractor(Model
 * model, HttpSession session) {
 * 
 * logger.info("Method : projectContractor starts");
 * 
 * try { DropDownModel[] dropDownModel =
 * restClient.getForObject(env.getProjects() + "getstatelist",
 * DropDownModel[].class); List<DropDownModel> statelist =
 * Arrays.asList(dropDownModel); model.addAttribute("stateList", statelist); }
 * catch (Exception e) { e.printStackTrace(); }
 * 
 * logger.info("Method : projectContractor ends"); return
 * "projects/project-contractor";
 * 
 * }
 * 
 * // add sub contractor
 * 
 * @SuppressWarnings({ "unchecked" })
 * 
 * @PostMapping(value = { "project-sub-contractor-add" }) public @ResponseBody
 * JsonResponse<Object> addSubContractor(@RequestBody
 * ProjectSubContractorWebModel subContractor, HttpSession session) {
 * logger.info("Method: addSubContractor starts"); JsonResponse<Object> resp =
 * new JsonResponse<>(); String userId = ""; String organization = ""; String
 * orgDivision = "";
 * 
 * try { userId = (String) session.getAttribute("USER_ID"); organization =
 * (String) session.getAttribute("ORGANIZATION"); orgDivision = (String)
 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) { //
 * Handle the exception }
 * 
 * subContractor.setCreatedBy(userId);
 * subContractor.setOrganizationName(organization);
 * subContractor.setOrganizationDivision(orgDivision);
 * 
 * System.out.println("SubContractor===" + subContractor);
 * 
 * try { resp = restClient.postForObject(env.getProjects() +
 * "rest-SubContractor-add", subContractor, JsonResponse.class); } catch
 * (RestClientException e) { e.printStackTrace(); }
 * 
 * logger.info("Method: addSubContractor ends");
 * System.out.println("Final Save>>>------" + resp); return resp; }
 * 
 * // view sub contractor
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping("project-sub-contractor-view") public @ResponseBody Object
 * viewSubContractor(HttpSession session) {
 * logger.info("Method :viewSubContractor starts"); JsonResponse<Object> resp =
 * new JsonResponse<Object>(); String orgName = ""; String orgDivision = ""; try
 * { orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
 * { e.printStackTrace(); } try { resp = restClient.getForObject(
 * env.getProjects() + "rest-SubContractor-view?orgName=" + orgName +
 * "&orgDivision=" + orgDivision, JsonResponse.class); } catch (Exception e) {
 * e.printStackTrace(); }
 * 
 * if (resp.getMessage() != "" && resp.getMessage() != null) {
 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
 * resp.setMessage("Success"); } System.out.println("view===" + resp);
 * logger.info("Method :viewSubContractor ends"); return resp; }
 * 
 * // edit sub contractor
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping("project-sub-contractor-edit") public @ResponseBody Object
 * editSubContractor(@RequestParam String id, HttpSession session) {
 * logger.info("Method :editSubContractor starts"); JsonResponse<Object> resp =
 * new JsonResponse<Object>(); String orgName = ""; String orgDivision = ""; try
 * { orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
 * { e.printStackTrace(); } try {
 * 
 * resp = restClient.getForObject(env.getProjects() +
 * "rest-SubContractor-edit?id=" + id + "&orgName=" + orgName + "&orgDivision="
 * + orgDivision, JsonResponse.class); } catch (Exception e) {
 * e.printStackTrace(); } if (resp.getMessage() != "" && resp.getMessage() !=
 * null) { resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else
 * { resp.setMessage("Success"); } System.out.println("edit>>>-----" + resp);
 * logger.info("Method :editSubContractor ends"); return resp; } // delete sub
 * contractor
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("project-sub-contractor-delete") public @ResponseBody
 * JsonResponse<Object> deleteSubContractor(@RequestParam String id, Model
 * model, HttpSession session) {
 * logger.info("Method : deleteSubContractor starts");
 * 
 * JsonResponse<Object> res = new JsonResponse<Object>();
 * 
 * JsonResponse<Object> resp = new JsonResponse<Object>(); String orgName = "";
 * String orgDivision = ""; try {
 * 
 * orgName = (String) session.getAttribute("ORGANIZATION"); orgDivision =
 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
 * { logger.error(e.getMessage()); } try { res =
 * restClient.getForObject(env.getProjects() + "rest-SubContractor-delete?id=" +
 * id + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class); }
 * catch (RestClientException e) { e.printStackTrace(); }
 * 
 * String message = res.getMessage(); if (message != null && message != "") {
 * 
 * } else { res.setMessage("Success"); }
 * logger.info("Method : deleteSubContractor Ends");
 * 
 * System.out.println("RESPPPPPPP" + res); return res; }
 * 
 * // auto search project name
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping("project-sub-contractor-autosearch-projName")
 * public @ResponseBody JsonResponse<DropDownModel>
 * getProjectAutoSearchList(Model model,
 * 
 * @RequestParam String searchValue, HttpServletRequest request, HttpSession
 * session) { logger.info("Method : getProjectAutoSearchList starts");
 * JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
 * 
 * try { res = restClient.getForObject(env.getProjects() +
 * "get-rest-ProjectAutoSearchList?id=" + searchValue, JsonResponse.class); }
 * catch (Exception e) { e.printStackTrace(); }
 * 
 * if (res.getMessage() != null) {
 * 
 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
 * res.setMessage("success"); }
 * logger.info("Method : getProjectAutoSearchList ends");
 * System.out.println("AUTOSEARCHHH" + res); return res; }
 * 
 * // add sub contractor work(child)
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("project-sub-contractor-add-work") public @ResponseBody
 * JsonResponse<Object> addSubContractorWork(
 * 
 * @RequestBody ProjectSubContractorWebModel SubContractorWork, HttpSession
 * session) { logger.info("Method : addSubContractorWork starts");
 * 
 * JsonResponse<Object> resp = new JsonResponse<Object>();
 * 
 * String userId = ""; String orgName = ""; String orgDiv = "";
 * 
 * try { userId = (String) session.getAttribute("USER_ID"); orgName = (String)
 * session.getAttribute("ORGANIZATION"); orgDiv = (String)
 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
 * e.printStackTrace(); }
 * 
 * SubContractorWork.setCreatedBy(userId);
 * SubContractorWork.setOrganizationName(orgName);
 * SubContractorWork.setOrganizationDivision(orgDiv);
 * 
 * try { resp = restClient.postForObject(env.getProjects() +
 * "rest-SubContractor-add-work", SubContractorWork, JsonResponse.class); }
 * catch (Exception e) { e.printStackTrace(); }
 * 
 * if (resp.getMessage() != null && resp.getMessage() != "") {
 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
 * resp.setMessage("Success"); }
 * 
 * logger.info("Method : addSubContractorWork starts"); return resp; }
 * 
 * // view sub contractor work(child)
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping("project-sub-contractor-view-work") public @ResponseBody
 * List<ProjectSubContractorWebModel> viewSubContractorWork(HttpSession session)
 * {
 * 
 * logger.info("Method : viewPlanSchedule starts");
 * 
 * JsonResponse<List<ProjectSubContractorWebModel>> resp = new
 * JsonResponse<List<ProjectSubContractorWebModel>>();
 * 
 * try { resp = restClient.getForObject(env.getProjects() +
 * "rest-SubContractor-view-work", JsonResponse.class); } catch
 * (RestClientException e) { e.printStackTrace(); }
 * 
 * ObjectMapper mapper = new ObjectMapper();
 * 
 * List<ProjectSubContractorWebModel> projectSubContractorWebModel =
 * mapper.convertValue(resp.getBody(), new
 * TypeReference<List<ProjectSubContractorWebModel>>() { });
 * 
 * resp.setBody(projectSubContractorWebModel);
 * 
 * logger.info("Method : viewPlanSchedule ends");
 * System.out.println("viewSubContractorWork " + projectSubContractorWebModel);
 * return resp.getBody(); }
 * 
 * // edit sub contractor work
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("project-sub-contractor-work-edit") public @ResponseBody
 * JsonResponse<List<Object>> subcontractorWorkEdit(@RequestBody String id,
 * HttpSession session) { logger.info("Method : subContractortEdit starts");
 * 
 * JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();
 * 
 * try { resp = restClient.getForObject(env.getProjects() +
 * "rest-SubContractor-work-edit?id=" + id, JsonResponse.class);
 * System.out.println("IDDDDD" + id);
 * 
 * } catch (RestClientException e) { e.printStackTrace(); }
 * 
 * String message = resp.getMessage();
 * 
 * if (message != null && message != "") {
 * 
 * } else { resp.setMessage("success"); } System.out.println("edit" + resp);
 * logger.info("Method : subContractortEdit ends"); return resp; }
 * 
 * // delete sub contractor work (child)
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("project-sub-contractor-work-delete") public @ResponseBody
 * JsonResponse<Object> subcontractorWorkDelete(@RequestParam String id, Model
 * model, HttpSession session) {
 * logger.info("Method : SubContractorWorkDelete function starts");
 * 
 * JsonResponse<Object> res = new JsonResponse<Object>();
 * 
 * JsonResponse<Object> resp = new JsonResponse<Object>();
 * 
 * try { res = restClient.getForObject(env.getProjects() +
 * "rest-SubContractor-work-delete?id=" + id, JsonResponse.class); } catch
 * (RestClientException e) { e.printStackTrace(); }
 * 
 * String message = res.getMessage(); if (message != null && message != "") {
 * 
 * } else { res.setMessage("Success"); }
 * logger.info("Method : SubContractorWorkDelete function Ends");
 * 
 * System.out.println("RESPPPPPPP" + res); return res; }
 * 
 * }
 */