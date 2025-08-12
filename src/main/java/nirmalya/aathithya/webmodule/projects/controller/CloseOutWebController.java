/*
 * package nirmalya.aathithya.webmodule.projects.controller;
 * 
 * import java.util.Arrays; import java.util.List; import java.util.ArrayList;
 * 
 * import java.net.URLDecoder; import java.net.URLEncoder;
 * 
 * import javax.servlet.http.HttpSession;
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
 * nirmalya.aathithya.webmodule.master.model.AdvanceManagementModelNew; import
 * nirmalya.aathithya.webmodule.projects.model.CloseOutWebModel; import
 * nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel; import
 * nirmalya.aathithya.webmodule.user.model.RolesAccessModel;
 * 
 * @SuppressWarnings("unused")
 * 
 * @Controller
 * 
 * @RequestMapping(value = "projects/")
 * 
 * public class CloseOutWebController {
 * 
 * Logger logger = LoggerFactory.getLogger(CloseOutWebController.class);
 * 
 * @Autowired RestTemplate restClient;
 * 
 * @Autowired EnvironmentVaribles env;
 * 
 * @GetMapping("/close-out") public String closeOut(Model model, HttpSession
 * session) {
 * 
 * logger.info("Method : closeOut starts");
 * 
 * try {
 * 
 * DropDownModel[] project = restClient.getForObject(env.getProjects() +
 * "get-closeOut-list", DropDownModel[].class); List<DropDownModel> projectList
 * = Arrays.asList(project);
 * 
 * model.addAttribute("projectList", projectList); } catch (Exception e) {
 * e.printStackTrace();
 * 
 * }
 * 
 * logger.info("Method : closeOut ends");
 * 
 * return "projects/close-out"; }
 * 
 * 
 * // view-projectName-getdataOnSO
 * 
 * @GetMapping(value = { "close-out-view-project-getdataOnSO" })
 * public @ResponseBody List<CloseOutWebModel> getProjectNmDetails(@RequestParam
 * String id, HttpSession session) {
 * logger.info("Method : getProjectNameDetails starts"); List<CloseOutWebModel>
 * productList = new ArrayList<CloseOutWebModel>();
 * 
 * if (id != null && id != "") {
 * 
 * try { CloseOutWebModel[] closeOutWebModel = restClient.getForObject(
 * env.getProjects() + "rest-getProjectName-Data?id=" + id,
 * CloseOutWebModel[].class); productList = Arrays.asList(closeOutWebModel);
 * productList.forEach(s -> s.setProjectplanId(s.getProjectplanId())); int count
 * = 0; for (CloseOutWebModel m : closeOutWebModel) { count++;
 * m.setProjectplanId(count);
 * 
 * }
 * 
 * } catch (Exception e) { e.printStackTrace(); } }
 * System.out.println("aaaaaaaa"+productList);
 * logger.info("Method : getProjectNameDetails ends"); return productList; }
 * 
 * 
 * 
 * // project-view
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping("close-out-view") public @ResponseBody List<CloseOutWebModel>
 * viewCloseOut(HttpSession session) {
 * 
 * logger.info("Method : viewCloseOut starts");
 * 
 * JsonResponse<List<CloseOutWebModel>> resp = new
 * JsonResponse<List<CloseOutWebModel>>();
 * 
 * try { resp = restClient.getForObject(env.getProjects() +
 * "rest-closeOut-view", JsonResponse.class); } catch (RestClientException e) {
 * e.printStackTrace(); }
 * 
 * ObjectMapper mapper = new ObjectMapper();
 * 
 * List<CloseOutWebModel> projectModel = mapper.convertValue(resp.getBody(), new
 * TypeReference<List<CloseOutWebModel>>() { });
 * 
 * resp.setBody(projectModel);
 * 
 * logger.info("Method : viewCloseOut ends"); return resp.getBody(); }
 * 
 * //CRUD OPERATIONS
 * 
 * //FOR ADD
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("close-out-add") public @ResponseBody JsonResponse<Object>
 * addCloseOut(@RequestBody CloseOutWebModel closeOut, HttpSession session) {
 * logger.info("Method : addCloseOut starts");
 * 
 * JsonResponse<Object> resp = new JsonResponse<Object>();
 * System.out.println("pppppppppppppppppppppppppppppppppppppppppppp"+closeOut);
 * 
 * String userId = ""; String orgName = ""; String orgDiv = "";
 * 
 * try { userId = (String) session.getAttribute("USER_ID"); orgName = (String)
 * session.getAttribute("ORGANIZATION"); orgDiv = (String)
 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
 * e.printStackTrace(); }
 * 
 * closeOut.setCreatedBy(userId); closeOut.setOrganizationName(orgName);
 * closeOut.setOrganizationDivision(orgDiv);
 * 
 * try { resp = restClient.postForObject(env.getProjects() + "rest-addCloseOut",
 * closeOut, JsonResponse.class); } catch (Exception e) { e.printStackTrace(); }
 * 
 * if (resp.getMessage() != null && resp.getMessage() != "") {
 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
 * resp.setMessage("success"); }
 * 
 * logger.info("Method : addCloseOut ends"); return resp; }
 * 
 * //FOR VIEW
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping("close-out-view-lesson") public @ResponseBody
 * List<CloseOutWebModel> viewCloseOutLesson(HttpSession session) {
 * 
 * logger.info("Method : viewCloseOutLesson starts");
 * 
 * JsonResponse<List<CloseOutWebModel>> resp = new
 * JsonResponse<List<CloseOutWebModel>>();
 * 
 * try { resp = restClient.getForObject(env.getProjects() +
 * "rest-closeOutLesson-view", JsonResponse.class); } catch (RestClientException
 * e) { e.printStackTrace(); }
 * 
 * ObjectMapper mapper = new ObjectMapper();
 * 
 * List<CloseOutWebModel> closeOutModel = mapper.convertValue(resp.getBody(),
 * new TypeReference<List<CloseOutWebModel>>() { });
 * 
 * resp.setBody(closeOutModel);
 * 
 * logger.info("Method : viewCloseOutLessson ends"); return resp.getBody(); }
 * 
 * //FOR EDIT
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("close-out-edit") public @ResponseBody
 * JsonResponse<List<Object>> editCloseOut(@RequestBody String closeOutId,
 * HttpSession session) { logger.info("Method : editCloseOut starts");
 * 
 * JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();
 * 
 * try { resp = restClient.getForObject(env.getProjects() +
 * "rest-closeOutLesson-edit?id="+closeOutId, JsonResponse.class);
 * System.out.println("IDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD="+closeOutId);
 * 
 * } catch (RestClientException e) { e.printStackTrace(); }
 * 
 * String message = resp.getMessage();
 * 
 * if (message != null && message != "") {
 * 
 * } else { resp.setMessage("success"); } System.out.println("edit"+resp);
 * logger.info("Method : editCloseOut ends"); return resp; }
 * 
 * //FOR DELETE
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("close-out-delete") public @ResponseBody JsonResponse<Object>
 * deleteCloseOut(@RequestParam String id, Model model, HttpSession session) {
 * logger.info("Method : deleteCloseOut function starts");
 * 
 * JsonResponse<Object> res = new JsonResponse<Object>();
 * 
 * JsonResponse<Object> resp = new JsonResponse<Object>();
 * 
 * try { res = restClient.getForObject(env.getProjects() +
 * "rest-closeOutLesson-delete?id=" + id , JsonResponse.class); } catch
 * (RestClientException e) { e.printStackTrace(); }
 * 
 * String message = res.getMessage(); if (message != null && message != "") {
 * 
 * } else { res.setMessage("Success"); }
 * logger.info("Method : deleteCloseOut function Ends");
 * 
 * System.out.println("RESPPPPPPP"+res); return res; }
 * 
 * 
 * //for copy view
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping("close-out-project-view-data") public @ResponseBody
 * List<CloseOutWebModel> closeOutProjectViewData(@RequestParam String id,
 * HttpSession session) {
 * 
 * logger.info("Method : closeOutProjectViewData starts" + id);
 * 
 * JsonResponse<List<CloseOutWebModel>> resp = new
 * JsonResponse<List<CloseOutWebModel>>();
 * 
 * try { resp = restClient.getForObject(env.getProjects() +
 * "rest-close-out-project-view-data?id=" + id, JsonResponse.class); } catch
 * (RestClientException e) { e.printStackTrace(); }
 * 
 * ObjectMapper mapper = new ObjectMapper(); List<CloseOutWebModel>
 * CloseOutWebModel = mapper.convertValue(resp.getBody(), new
 * TypeReference<List<CloseOutWebModel>>() { }); resp.setBody(CloseOutWebModel);
 * 
 * logger.info("Method : closeOutProjectViewData ends"); return resp.getBody();
 * }
 * 
 * //for copy add
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("close-out-add-project") public @ResponseBody
 * JsonResponse<Object> addProjectData(HttpSession session,
 * 
 * @RequestBody List<CloseOutWebModel> model) {
 * logger.info("Method : addProjectData starts   " + model);
 * JsonResponse<Object> resp = new JsonResponse<Object>(); try { resp =
 * restClient.postForObject(env.getProjects() + "rest-project-add-data", model,
 * JsonResponse.class);
 * 
 * } catch (Exception e) {
 * 
 * e.printStackTrace(); }
 * 
 * if (resp.getMessage() != "" && resp.getMessage() != null) {
 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
 * resp.setMessage("Success"); }
 * 
 * logger.info("Method : addProjectData ends");
 * 
 * return resp; }
 * 
 * }
 */