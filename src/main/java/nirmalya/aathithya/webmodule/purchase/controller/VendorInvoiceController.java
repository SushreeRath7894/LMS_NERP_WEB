package nirmalya.aathithya.webmodule.purchase.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;

@Controller
@RequestMapping(value = "purchase/")
public class VendorInvoiceController {

	Logger logger = LoggerFactory.getLogger(VendorInvoiceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("vendor-invoice")
	public String getInvoicePage(Model model, HttpSession session) {

		logger.info("Method : getInvoiceViewPage starts");
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getPaymentterm",
					DropDownModel[].class);
			List<DropDownModel> PaytermList = Arrays.asList(dropDownModel);
			model.addAttribute("PaytermList", PaytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getCarrierList",
					DropDownModel[].class);
			List<DropDownModel> CarrierLists = Arrays.asList(dropDownModel);
			model.addAttribute("CarrierLists", CarrierLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getInvoiceViewPage ends");
		return "purchase/vendor-invoice-details";
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("vendor-invoice-add")
	public @ResponseBody JsonResponse<Object> addVendorInvoicenew(HttpSession session,
			@RequestBody List<VendorInvoiceModel> VendorInvoiceModel) {
		logger.info("Method : addVendorInvoicenew starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<VendorInvoiceModel> documentList = new ArrayList<VendorInvoiceModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
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

		}
		for (VendorInvoiceModel a : VendorInvoiceModel) {

			if (a.getInvoiceDate() != null && a.getInvoiceDate() != "") {
				a.setInvoiceDate(DateFormatter.inputDateFormat(a.getInvoiceDate(), dateFormat));
			}
			if (a.getDueDate() != null && a.getDueDate() != "") {
				a.setDueDate(DateFormatter.inputDateFormat(a.getDueDate(), dateFormat));
			}
		}
		for (VendorInvoiceModel m : VendorInvoiceModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		for (InventoryVendorDocumentModel a : VendorInvoiceModel.get(0).getDocumentList()) {

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
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addVendorInvoicenew", VendorInvoiceModel,
					JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addVendorInvoicenew ends");

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

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("vendor-invoice-through-ajax")
	public @ResponseBody List<VendorInvoiceModel> viewVendorInvoice(HttpSession session) {

		logger.info("Method :viewVendorInvoice starts");
		JsonResponse<List<VendorInvoiceModel>> resp = new JsonResponse<List<VendorInvoiceModel>>();
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

		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userId);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "viewVendorInvoice", empModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<VendorInvoiceModel> VendorInvoiceModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<VendorInvoiceModel>>() {
				});

		for (VendorInvoiceModel a : VendorInvoiceModel) {
			a.setQuantitynew(a.getQuantity());
			if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
				a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
			}
		}

		resp.setBody(VendorInvoiceModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewVendorInvoice ends");
		return resp.getBody();
	}

	/*
	 * edit
	 */

	@GetMapping(value = { "vendor-invoice-edit-new" })
	public @ResponseBody List<VendorInvoiceModel> viewVendorIvoiceEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewVendorIvoiceEdit starts");
		List<VendorInvoiceModel> productList = new ArrayList<VendorInvoiceModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
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

		}

		if (id != null && id != "") {
			try {
				VendorInvoiceModel[] VendorInvoiceModel = restTemplate
						.getForObject(env.getPurchaseUrl() + "viewVendorIvoiceEdit?id=" + id + "&organization="
								+ organization + "&orgDivision=" + orgDivision, VendorInvoiceModel[].class);

				productList = Arrays.asList(VendorInvoiceModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (VendorInvoiceModel m : VendorInvoiceModel) {
					count++;
					m.setSlNo(count);
				}
				
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
												+ m.getFileName() + "></i> ";

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
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewVendorIvoiceEdit ends");
		return productList;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "vendor-invoice-delete")
	public @ResponseBody JsonResponse<Object> deleteVendorInvoice(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteVendorInvoice function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

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

		}

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteVendorInvoice?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteVendorInvoice function Ends");

		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-invoice-get-cust-address")
	public @ResponseBody JsonResponse<VendorNewModel> getcustomerAddressDtls(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : getVendorAddress starts" + id);

		JsonResponse<VendorNewModel> jsonResponse = new JsonResponse<VendorNewModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "getcustomerAddressDtlsById?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		VendorNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<VendorNewModel>() {
		});

		jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : getcustomerAddressDtls ends");

		return jsonResponse;
	}
	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-invoice-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveVendorinvoice(HttpSession session,
			@RequestParam String approveStatus, String vendorInvoice) {

		logger.info("Method : approveVendorinvoice starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate
					.getForObject(
							env.getPurchaseUrl() + "approveVendorinvoice?approveStatus=" + approveStatus + "&vendorInvoice="
									+ vendorInvoice + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		logger.info("response=====" + response);
		logger.info("Method : approveVendorinvoice ends");
		return response;
	}
	@PostMapping("/vendor-invoice-upload-fileproduct")
	public @ResponseBody JsonResponse<Object> uploadProductFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadProductFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("productPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadProductFile controller ' ends");
		return response;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 280;
				Integer width = 474;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					BufferedImage outputImage = new BufferedImage(width, height, img.getType());

					Graphics2D g2d = outputImage.createGraphics();
					g2d.drawImage(img, 0, 0, width, height, null);
					g2d.dispose();
					String outputImagePath = env.getFileUploadMaster() + "thumb/" + imageName;
					ImageIO.write(outputImage, ext, new File(outputImagePath));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}
}
