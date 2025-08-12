/*
 * package nirmalya.aathithya.webmodule.his.controller; import
 * java.util.ArrayList; import java.util.Arrays; import java.util.List;
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
 * import nirmalya.aathithya.webmodule.common.utils.DropDownModel; import
 * nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles; import
 * nirmalya.aathithya.webmodule.common.utils.JsonResponse; import
 * nirmalya.aathithya.webmodule.his.model.HISPathoLabModel;
 * 
 * @Controller
 * 
 * @RequestMapping(value = "his") public class HISPathoLabController {
 * 
 * Logger logger = LoggerFactory.getLogger(HISPathoLabController.class);
 * 
 * @Autowired RestTemplate restTemplate;
 * 
 * @Autowired EnvironmentVaribles env;
 * 
 * @GetMapping("patholab")
 * 
 * public String getHomePage(Model model, HttpSession session) {
 * 
 * logger.info("Method : getHomePage starts");
 * 
 * //floorType try { DropDownModel[] floorType =
 * restTemplate.getForObject(env.getHisUrl() + "getFloorType",
 * 
 * DropDownModel[].class);
 * 
 * List<DropDownModel> floorListType= Arrays.asList(floorType);
 * System.out.println("fffffff"+floorListType); model.addAttribute("floorList",
 * floorListType);
 * 
 * } catch (RestClientException e) { e.printStackTrace(); }
 * 
 * logger.info("Method : getHomePage ends");
 * 
 * return "his/his-patholab";
 * 
 * 
 * }
 * 
 * // patholab-lab-add
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("patholab-lab-add") public @ResponseBody JsonResponse<Object>
 * addPathoLab(@RequestBody HISPathoLabModel HISPathoLabModel, Model model,
 * HttpSession session) { logger.info("Method :addPathoLab starts");
 * logger.info("@@@@@@@@@@@@@@@@" + HISPathoLabModel); JsonResponse<Object> resp
 * = new JsonResponse<Object>();
 * 
 * 
 * String organization = ""; String orgDivision = ""; try {
 * 
 * organization = (String) session.getAttribute("ORGANIZATION"); orgDivision =
 * (String) session.getAttribute("ORGANIZATION_DIVISION");
 * 
 * } catch (Exception e) { e.printStackTrace(); }
 * 
 * HISPathoLabModel.setLabOrg(organization);
 * HISPathoLabModel.setLabDiv(orgDivision); try { resp =
 * restTemplate.postForObject(env.getHisUrl() + "rest-addPathoLab",
 * HISPathoLabModel, JsonResponse.class); } catch (RestClientException e) {
 * e.printStackTrace(); } String message = resp.getMessage(); if (message !=
 * null && message != "") {
 * 
 * } else { resp.setMessage("Success"); }
 * logger.info("Method : addPathoLab ends"); return resp; }
 * 
 * //patholab-lab-view
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping("patholab-lab-view") public @ResponseBody List<HISPathoLabModel>
 * viewPathoLab(HttpSession session) {
 * logger.info("Method : viewPathoLab starts");
 * 
 * JsonResponse<List<HISPathoLabModel>> resp = new
 * JsonResponse<List<HISPathoLabModel>>(); List<HISPathoLabModel> returnList =
 * new ArrayList<HISPathoLabModel>();
 * 
 * try { resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewPathoLab",
 * JsonResponse.class); returnList = resp.getBody(); } catch
 * (RestClientException e) { e.printStackTrace(); } logger.info("viewPathoLab" +
 * returnList); logger.info("Method : viewPathoLab ends"); return returnList; }
 * 
 * 
 * //patholab-lab-edit
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("patholab-lab-edit") public @ResponseBody
 * JsonResponse<List<Object>> editPathoLab(@RequestBody String id,HttpSession
 * session) { logger.info("Method : editPathoLab starts");
 * 
 * JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();
 * 
 * try { resp = restTemplate.getForObject(env.getHisUrl() +
 * "rest-editPathoLab?id=" + id, JsonResponse.class);
 * 
 * } catch (RestClientException e) { e.printStackTrace(); }
 * 
 * String message = resp.getMessage();
 * 
 * if (message != null && message != "") {
 * 
 * } else { resp.setMessage("success"); } logger.info("editIcu" + resp);
 * logger.info("Method : editPathoLab starts"); logger.info("resp" + resp);
 * return resp; }
 * 
 * 
 * //patholab-lab-delete
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @PostMapping("patholab-lab-delete") public @ResponseBody JsonResponse<Object>
 * deletePathoLab(@RequestParam String id, Model model, HttpSession session) {
 * logger.info("Method : deletePathoLab function starts");
 * 
 * JsonResponse<Object> res = new JsonResponse<Object>();
 * 
 * try { res = restTemplate.getForObject(env.getHisUrl() +
 * "rest-deletePathoLab?id=" + id , JsonResponse.class); } catch
 * (RestClientException e) { e.printStackTrace(); }
 * 
 * String message = res.getMessage(); if (message != null && message != "") {
 * 
 * } else { res.setMessage("Success"); }
 * logger.info("Method : deletePathoLab function Ends");
 * 
 * logger.info("RESPPPPPPP"+res); return res; } }
 */