package nirmalya.aathithya.webmodule.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestInterceptor()).excludePathPatterns("/index-assets/**",
				"/index-get-breadcrumb-data", "/assets/**", "/extend/**", "/css/**", "/datatables/**", "/FileUpload/**",
				"/download/**", "/js/**","/scorm/**", "/login", "/his-index", "/logout", "/register", "/", "/account/viewbank",
				"/document/**", "/access-denied", "/order-status", "/sales", "/restaurant/kitchen-staff-order-details",
				"/restaurant/kitchen-staff-order-details-modal", "/error", "/purchase/vendor-page-modal-adds",
				"/itemDetails-page/**", "/purchase/**", "/recruitment/offer-letter-pdf", "/master/payslip-pdf-download",
				"/employee/birthday-wish", "/mould/**", "/inventory/vendor-echallan-pdf-downloads", 
				"/purchase/vendor-page-modal-adds", "/master/**","/his/**",
				"/itemDetails-page/**","/purchase/**","/open-modal-requisition/**",
				"/inventory/vendor-invoice-pdf-downloads", "/customer/view-saleInvoice-pdf-downloads",
				"/purchase/manage-purchase-order-pdf-downloads", "/purchase/view-purchase-quotation-pdf-downloads",
				"/customer/view-deliverychallan-pdf-downloads", "/customer/view-quotation-pdf-downloads/","/javaScript/**",
				"/production/**","/qa/**","/gstreturn/**","/gstreturn/gst-3B-view-pdf-report", "/employee/**", "/weight/**",
				"/sales/customer-page-modal-adds/**","/customlogin","/budget/**","/grc/**","/account/**","/ceo-dashboard/**",
				"/production/production-logbook-of-download","/production/packing-logbook-download","/production/lmr-logbook-download",
				"/master/manage-notice-pdf-download", "/master/view-employe-paySlip-api-self","/pipeline/**","/pipeline/view-product-get-sku-listing","/pipeline/view-product-get-total-list",
				"/pipeline/view-product-save","/pipeline/view-product-save-sku-dtls","/pipeline/view-product-upload-file/","/his/his-patholab-report-pdf",
				"/pipeline/view-product-deleteSku/","/meeting","/configuration/**","/edms/**","/create-project-v1/**","/all-tasks/**","/document/image/**","/vms/**","/document/dms/**",
				"/candidates-job-apply","/get-job-details","/goal/**","/candidate-portal","/registerCandidate","/get-job-list",
				"/sendOtp","/verifyOtp","/registerCandidate","/patient/**","/feedback-form/**","/get-all-review-details/**",
				"/save-manager-response/**","/hotel/**","/sattkara-free-trail-registration","/user/save-organisation-details",
				"/user/get-stateList/**","/user/get-city-list/**","/academic-course-view","/get-all-product-details",
				"/cart/**","/checkout/**","/all-courses/**","/courses-categories/**","/aboutus/**","/contactus/**","/contactus-email-ajax/**",
				"/wishlist/**","/faqs/**","/terms-condition/**","/privacy-policy/**","/my-profile/**","/profile-course-view/**","/instructors/**",
				"/wishlist/**","/faqs/**","/terms-condition/**","/privacy-policy/**","/my-profile/**","/profile-course-view/**",
				"/refund-policy/**","/help-center/**","/carrer/**","/testimonials/**","/home/**","/course-catelog/**","/academic/get-all-course-list/**",
				"/shop","/consultancy","/atva","/itil-maturity-model","/iso-consulting","/generative-ai","/blog","/enterprise","/upgrading","/resources",













				"/course-details/**","/academic/get-all-instructor-list/**","/academic/course-details-view/**","/setRedirectAndLogin/**","/signup/**","/save-candidate-reg-details/**","/academic/get-catlog-courses/**");
	}

}
