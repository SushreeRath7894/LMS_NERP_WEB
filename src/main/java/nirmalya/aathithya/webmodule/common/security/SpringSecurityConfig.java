/**
 *  Configures Web Security
 */
package nirmalya.aathithya.webmodule.common.security;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import nirmalya.aathithya.webmodule.user.service.CustomAuthenticationSuccessHandler;
import nirmalya.aathithya.webmodule.user.service.CustomUserDetailsService;

/**
 * @author Nirmalya Labs
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	HttpSession session;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Autowired
//	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
//	}

	 @Autowired
	 public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
      authenticationManagerBuilder.userDetailsService(customUserDetailsService)
      							.passwordEncoder(passwordEncoder);
  }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	public void urlCheck() {

	}

	@Autowired
	CustomAuthenticationFailureHandler CustomAuthenticationFailureHandler;
	
	public SimpleAuthenticationFilter authenticationFilter() throws Exception {
		SimpleAuthenticationFilter filter = new SimpleAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler);
		filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		return filter;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable().headers().frameOptions().disable()   // 🔥 REQUIRED FOR IFRAMES
        .and()
		.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class).authorizeRequests()
				.antMatchers("/oauth/token").permitAll()
				.antMatchers("/api-docs/**").permitAll()
				.antMatchers("/index-assets/**").permitAll()
                
				.antMatchers("/register").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/term-and-condition").permitAll()
				.antMatchers("/forgot-password").permitAll()
				.antMatchers("/get-otp").permitAll()
				.antMatchers("/save-new-password").permitAll()
				.antMatchers("/order-status").permitAll()
				.antMatchers("/restaurant/kitchen-staff-order-details").permitAll()
				.antMatchers("/restaurant/kitchen-staff-order-details-modal").permitAll()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/extend/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/nirmalyaRest/**").permitAll()
				.antMatchers("/datatables/**").permitAll()
				.antMatchers("/FileUpload/**").permitAll()
				.antMatchers("/document/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/about-us").permitAll()
				.antMatchers("/contact").permitAll()
				.antMatchers("/his-index").permitAll()
				.antMatchers("/javaScript/**").permitAll()
				.antMatchers("/recruitment/**").permitAll()
				.antMatchers("/recruitment/offer-letter-pdf/").permitAll()
				.antMatchers("/index-get-breadcrumb-data").permitAll()
				.antMatchers("/master/advanceManagement")
				.permitAll().antMatchers("/layout/**").permitAll()
				.antMatchers("/sales/**").permitAll()
				.antMatchers("/scorm/**").permitAll()

				.antMatchers("/master/**").permitAll()
				.antMatchers("/master/payslip-pdf-download").permitAll()
				.antMatchers("/employee/**").permitAll()
				.antMatchers("/employee/birthday-wish").permitAll()

				.antMatchers("/master/view-employe-paySlip-api-self").permitAll()

				.antMatchers("/master/getEmployeeListsSlip/**").permitAll()

				.antMatchers("/account/**").permitAll()
				.antMatchers("/pipeline/**").permitAll()

				.antMatchers("/purchase/**").permitAll()
				.antMatchers("/production/**").permitAll()
				.antMatchers("/production/production-logbook-of-download").permitAll()
				.antMatchers("/qa/**").permitAll()
				.antMatchers("/weight/**").permitAll()
				.antMatchers("/budget/**").permitAll()
				.antMatchers("/ceo-dashboard/**").permitAll()
				.antMatchers("/purchase/manage-purchase-order-pdf-downloads/**").permitAll()
				.antMatchers("/purchase/view-purchase-quotation-pdf-downloads/**").permitAll()
				.antMatchers("/customer/view-deliverychallan-pdf-downloads/**").permitAll()
				.antMatchers("/customer/view-quotation-pdf-downloads/**").permitAll()
				.antMatchers("/master/manage-notice-pdf-download/**").permitAll()
				.antMatchers("/employee/**").permitAll()
				.antMatchers("/pipeline/admin-executive-report").permitAll()
				.antMatchers("/pipeline/view-crm-leads-detail").permitAll()

				.antMatchers("/purchase/vendor-page-modal-adds").permitAll()
				
				.antMatchers("/gstreturn/**").permitAll()
				.antMatchers("/edms/manage-access").permitAll()
				//.antMatchers("/edms/**").permitAll()
				 
				.antMatchers("/itemDetails-page/**").permitAll()
				.antMatchers("/open-modal-requisition/**").permitAll()
				.antMatchers("/purchase/vendor-page-modal-adds").permitAll()
				.antMatchers("/itemDetails-page/**").permitAll()
				.antMatchers("/open-modal-requisition/**").permitAll()
				.antMatchers("/sales/customer-page-modal-adds/**").permitAll()
				.antMatchers("/production/packing-logbook-download/**").permitAll()
				.antMatchers("/production/lmr-logbook-download/**").permitAll()
				.antMatchers("/customlogin").permitAll()
				.antMatchers("/login-width-height").permitAll()
				.antMatchers("/meeting").permitAll()
				.antMatchers("/configuration/**").permitAll()
				.antMatchers("/his/**").permitAll()
				.antMatchers("/create-project-v1/**").permitAll()
				.antMatchers("/his/his-patholab-report-pdf/**").permitAll()
				.antMatchers("/all-tasks/**").permitAll()
				.antMatchers("/vms/**").permitAll()
				.antMatchers("/candidates-job-apply").permitAll()
				.antMatchers("/get-job-details").permitAll()
				.antMatchers("/goal/**").permitAll()
				.antMatchers("/get-job-list").permitAll()
				.antMatchers("/candidate-portal").permitAll()
				.antMatchers("/feedback-form/**").permitAll()
				.antMatchers("/registerCandidate").permitAll()
				.antMatchers("/sendOtp").permitAll()
				.antMatchers("/verifyOtp").permitAll()
				.antMatchers("/patient/**").permitAll()

				.antMatchers("/get-all-review-details").permitAll()
				.antMatchers("/save-manager-response").permitAll()
				.antMatchers("/hotel").permitAll()
				.antMatchers("/sattkara-free-trail-registration").permitAll()
				.antMatchers("/user/save-organisation-details").permitAll()
				.antMatchers("/user/get-stateList").permitAll()
				.antMatchers("/user/get-city-list").permitAll()
				.antMatchers("/academic-course-view").permitAll()
				.antMatchers("/get-all-product-details").permitAll()
				.antMatchers("/cart").permitAll()
				.antMatchers("/checkout").permitAll()
				.antMatchers("/all-courses").permitAll()
				.antMatchers("/courses-categories").permitAll()
				.antMatchers("/aboutus").permitAll()
				.antMatchers("/shop").permitAll()
				.antMatchers("/consultancy").permitAll()
				.antMatchers("/atva").permitAll()
				.antMatchers("/contactus").permitAll()
				.antMatchers("/contactus-email-ajax").permitAll()
				.antMatchers("/wishlist").permitAll()
				.antMatchers("/faqs").permitAll()
				.antMatchers("/terms-condition").permitAll()
				.antMatchers("/privacy-policy").permitAll()
				.antMatchers("/my-profile").permitAll()
				.antMatchers("/profile-course-view").permitAll()
				.antMatchers("/instructors").permitAll()
				.antMatchers("/refund-policy").permitAll()
				.antMatchers("/help-center").permitAll()
				.antMatchers("/carrer").permitAll()
				.antMatchers("/testimonials").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/course-catelog").permitAll()
				.antMatchers("/academic/get-all-course-list").permitAll()
				.antMatchers("/course-details").permitAll()
				.antMatchers("/academic/course-details-view").permitAll()
				.antMatchers("/academic/get-all-instructor-list").permitAll()
				.antMatchers("/setRedirectAndLogin").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/itil-maturity-model").permitAll()
				.antMatchers("/iso-consulting").permitAll()
				.antMatchers("/generative-ai").permitAll()
				.antMatchers("/blog").permitAll()
				.antMatchers("/resources").permitAll()
				.antMatchers("/enterprise").permitAll()
				.antMatchers("/upgrading").permitAll()
				.antMatchers("/save-candidate-reg-details").permitAll()
				.antMatchers("/rest-delete-public-batches").permitAll()
				.antMatchers("/academic/get-catlog-courses").permitAll()
				.and().formLogin().loginPage("/login").permitAll().successHandler(customAuthenticationSuccessHandler)
				.and().authorizeRequests().antMatchers("/**").authenticated()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login?expired").maximumSessions(1).expiredUrl("/login?expired");
	}

//	@Bean
//	public TokenStore tokenStore() {
//		return new InMemoryTokenStore();
//	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//		return bean;
//	}

}
