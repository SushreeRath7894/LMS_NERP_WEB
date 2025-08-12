package nirmalya.aathithya.webmodule.sales.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectFileuploadModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectShippingModel;

@Controller
@RequestMapping(value = "sales")
public class OpenProjectModalWebController {
	Logger logger = LoggerFactory.getLogger(OpenProjectModalWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "project-modal" })
	public String createProject(Model model, HttpSession session) {
		logger.info("Method : createProject starts");

		// for project country drop down

		logger.info("Method : createProject ends");
		return "sales/open-project-modal";
	}

	// FOR PROJECT CREATION ADD STARTS

	@SuppressWarnings("unchecked")
	@PostMapping("/project-modal-add")
	public @ResponseBody JsonResponse<Object> addPrjCreation(@RequestBody List<ProjectCreationWebModel> prjCreation,
			HttpSession session) {
		logger.info("Method : addPrjcreation starts" + prjCreation);

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

		for (ProjectCreationWebModel m : prjCreation) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}

		for (ProjectFileuploadModel a : prjCreation.get(0).getDocumentList()) {

			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String[] extension = a.getFileName().split("\\.");
					int lastindex = extension.length - 1;
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, extension[lastindex]);
							a.setFileName(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				}
			}
		}

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-addPrjCreation", prjCreation, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addPrjcreation end" + resp);
		return resp;
	}

	public String saveAllMultiImages(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllMultiImages starts");
		String imageName1 = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}

	// FOR PROJECT CREATION VIEW STARTS

	@SuppressWarnings("unchecked")
	@GetMapping("project-modal-view")
	public @ResponseBody List<ProjectCreationWebModel> viewProjectCreation(HttpSession session) {
		logger.info("Method : viewProjectCreation starts");

		JsonResponse<List<ProjectCreationWebModel>> resp = new JsonResponse<List<ProjectCreationWebModel>>();
		List<ProjectCreationWebModel> returnList = new ArrayList<ProjectCreationWebModel>();

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
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-view-project-creation" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewProjectCreation ends" + returnList);
		return returnList;
	}

	// FOR PROJECT CREATION EDIT STARTS
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "project-modal-edit" })
	public @ResponseBody JsonResponse<ProjectCreationWebModel> projectEdit(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : projectEdit starts" + id);
		List<ProjectCreationWebModel> productList = new ArrayList<ProjectCreationWebModel>();
		List<ProjectFileuploadModel> documentList = new ArrayList<ProjectFileuploadModel>();
		JsonResponse<ProjectCreationWebModel> jsonResponse = new JsonResponse<ProjectCreationWebModel>();
		ProjectCreationWebModel product = new ProjectCreationWebModel();
		ObjectMapper mapper = new ObjectMapper();

		try {
			jsonResponse = restClient.getForObject(env.getProjects() + "edit-projectscreation?id=" + id,
					JsonResponse.class);
			product = mapper.convertValue(jsonResponse.getBody(), new TypeReference<ProjectCreationWebModel>() {

			});

			if (productList != null) {
				documentList = product.getDocumentList();
				if (documentList != null) {
					for (ProjectFileuploadModel m : documentList) {
						if (m.getFileName() != null && m.getFileName() != "") {

							String[] extension = m.getFileName().split("\\.");
							if (extension.length == 2) {
								if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

									String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " + m.getFileName()
											+ "></i> ";

									m.setAction(docPath);
								}
								if (extension[1].equals("pdf")) {
									String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
											+ m.getFileName() + " ;></i> ";

									m.setAction(docPath);
								}
								if (extension[1].equals("doc") || extension[1].equals("dox")
										|| extension[1].equals("docx")) {
									String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
											+ m.getFileName() + "></i> ";
									m.setAction(docPath);
								}
								if (extension[1].equals("png") || extension[1].equals("jpg")
										|| extension[1].equals("jpeg")) {
									String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
											+ m.getFileName() + "></i>  ";
									m.setAction(docPath);
								}
							} else {
								m.setAction("N/A");
							}
						} else {
							m.setAction("N/A");
						}
						m.setAction("<a href=\"/document/document/" + m.getFileName() + "\" target=\"_blank\" >"
								+ m.getAction() + "</a>");
						logger.info("m.setAction" + m);

					}
				}
				product.setDocumentList(documentList);

				if (product.getShippingList().size() > 0) {
					int c = 0;
					for (ProjectShippingModel a : product.getShippingList()) {
						c = c + 1;
						a.setSlNo(c);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonResponse.setBody(product);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editProductInfo ends");
		return jsonResponse;
	}

	// FOR PROJECT CREATION DELETE STARTS

	@SuppressWarnings("unchecked")
	@PostMapping("project-modal-delete")
	public @ResponseBody JsonResponse<Object> deleteProjectCreation(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteProjectCreation function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "deleteProjectCreation?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteProjectCreation function Ends");

		return res;
	}

	// FOR PROJECT CREATION DELETE STARTS

	/*
	 * function for project drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "project-modal-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForProject(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForProject starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-project-state-list?id=" + tCountry,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateNameForProject ends");
		return res;

	}

	/*
	 * function for billing drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "project-modal-billing-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForBilling(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForBilling starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-project-billing-state-list?id=" + tCountry,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateNameForBilling ends" + res);
		return res;

	}

	/*
	 * function for shipping drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "project-modal-shipping-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForShipping(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForShipping starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-project-shipping-state-list?id=" + tCountry,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateNameForShipping ends" + res);
		return res;

	}

	// getting project state id on edit
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "project-modal-state-list-project" })
	public @ResponseBody JsonResponse<Object> getProjectStateList(@RequestParam String id) {
		logger.info("Method : getProjectStateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-getProjectStateList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getProjectStateList ends");
		return res;
	}

	// getting billing state id on edit
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "project-modal-state-list-billing" })
	public @ResponseBody JsonResponse<Object> getBillingStateList(@RequestParam String id) {
		logger.info("Method : getBillingStateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-getBillingStateList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getBillingStateList ends");
		return res;
	}

	// getting shipping state id on edit
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "project-modal-state-list-shipping" })
	public @ResponseBody JsonResponse<Object> getShippingStateList(@RequestParam String id) {
		logger.info("Method : getShippingStateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-getShippingStateList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getShippingStateList ends");
		return res;
	}

}
