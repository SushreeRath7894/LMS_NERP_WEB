package nirmalya.aathithya.webmodule.user.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.user.model.Activity;
import nirmalya.aathithya.webmodule.user.model.Function;
import nirmalya.aathithya.webmodule.user.model.Menu;
import nirmalya.aathithya.webmodule.user.model.Module;
import nirmalya.aathithya.webmodule.user.model.User;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	HttpSession session;

	public static String userId = "";
	public static String org = "";
	public static String orgDiv = "";

	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		org = "";
		orgDiv = "";
		// do some logic here if you want something to be done whenever
		// the user successfully logs in.
//		HttpSession session = request.getSession();
		/*
		 * HttpSessionManager httpSessionManager = (HttpSessionManager) request
		 * .getAttribute(HttpSessionManager.class.getName());
		 */

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		User user = userDetails.getUser();
		System.out.println(user);
		
		session.setAttribute("SESSION_AGENT", request.getHeader("User-Agent"));
		session.setAttribute("SESSION_ID", session.getId());
		session.setAttribute("SESSION_HOST", request.getRemoteHost());
		session.setAttribute("SESSION_PORT", request.getRemotePort());
		session.setAttribute("USER_ID", user.getUser());
		session.setAttribute("USER_NAME", user.getUserName());
		session.setAttribute("USER_EMAIL", user.getUserEmail());
		session.setAttribute("VENDOR_ID", user.getVendorId());
		session.setAttribute("MANAGER_ID", user.getManagerId());
		session.setAttribute("ORGANIZATION", user.getOrganization());
		session.setAttribute("ORGANIZATION_DIVISION", user.getOrgDivision());
		session.setAttribute("ORGANIZATION_LOGO", user.getOrgLogo());
		session.setAttribute("PROFILE_IMAGE", user.getProfileImg());
		System.out.println("img=====" + (String) session.getAttribute("PROFILE_IMAGE"));

		List<String> role = (List<String>) user.getRoles();

		Optional<String> data = role.stream().filter(d -> d.equals("rol001")).findAny();
		Optional<String> dataSalesManger = role.stream().filter(d -> d.equals("rol003")).findAny();
		Optional<String> dataRegionalManger = role.stream().filter(d -> d.equals("rol036")).findAny();
		Optional<String> hrManger = role.stream().filter(d -> d.equals("rol026")).findAny();
		System.out.println(hrManger);
		Optional<String> dataExecutive = role.stream().filter(d -> d.equals("rol037")).findAny();
		Optional<String> transportManger = role.stream().filter(d -> d.equals("rol067")).findAny();
		String designation = "";

		if (data.isPresent()) {
			session.setAttribute("IS_ADMIN", data.get());
			designation = "Director";
		}
		
		if (hrManger.isPresent()) {
			session.setAttribute("HR_MANAGER", hrManger.get());
		}

		if (transportManger.isPresent()) {
			System.out.println(transportManger.get());
			session.setAttribute("IS_TRANSPORT_MANAGER", transportManger.get());
		}

		if (dataSalesManger.isPresent()) {
			session.setAttribute("IS_SALES_MANAGER", dataSalesManger.get());
			designation = "National Manger";
		}

		if (dataRegionalManger.isPresent()) {
			designation = "Regional Manger";
		}

		if (dataExecutive.isPresent()) {
			designation = "Sales Executive";
		}

		userId = user.getUser();
		org = user.getOrganization();
		orgDiv = user.getOrgDivision();

		session.setAttribute("USER_PASSWORD", user.getUserPassword());
		if (user.getUserType().contains("SuperAdmin")) {
			session.setAttribute("USER_ROLETYPE", "SuperAdmin");
		} else {
			session.setAttribute("USER_ROLETYPE", user.getUserType());
		}

		session.setAttribute("USER_MOBILE", user.getUserMobile());
		session.setAttribute("AUTHORITIES", authentication.getAuthorities());
		
		session.setAttribute("USER_ROLES", user.getRoles());
		session.setAttribute("DASHBOARD", user.getRoleDashboard());
		session.setAttribute("DATEFORMAT", user.getDateFormat());
		session.setAttribute("DATEFORMAT_ID", user.getDateFormatId());
		session.setAttribute("DATEFORMAT_JS", user.getDateFormatJS());
		StringBuffer roles = new StringBuffer();
		user.getRoles().stream().forEach(s -> roles.append(s));
		session.setAttribute("USER_ROLES_STRING", roles.toString());

		JsonResponse<List<Menu>> jsonResponse = new JsonResponse<List<Menu>>();

		List<Module> module = new ArrayList<Module>();
		List<Menu> mList = new ArrayList<Menu>();
		List<String> uList = new ArrayList<String>();
		List<Activity> activityList = new ArrayList<Activity>();

		try {
			user.getRoles().add(user.getOrganization());
			user.getRoles().add(user.getOrgDivision());
			jsonResponse = restTemplate.postForObject(env.getUserUrl() + "getMenu", user.getRoles(),
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			mList = mapper.convertValue(jsonResponse.getBody(), new TypeReference<List<Menu>>() {
			});
			
			List<Menu> filteredList = new ArrayList<Menu>();

			String pMod = "";
			if (mList != null && mList.size() > 0) {
				
				filteredList = mList.stream()
		                .filter(menu -> "MOD061".equals(menu.getModuleId()))
		                .collect(Collectors.toList());
				
				for (Menu m : mList) {

					String mod = m.getModule();

					if (mod.equals(pMod)) {

					} else {

						pMod = mod;

						String pFun = "";

						Module newMod = new Module();
						newMod.setName(mod);
						newMod.setModuleLogoName(m.getModuleLogo());
						newMod.setModuleId(m.getModuleId());
						List<Menu> fList = mList.stream().filter(s -> s.getModule().equals(mod))
								.collect(Collectors.toList());

						List<Function> funList = new ArrayList<Function>();

						for (Menu f : fList) {
							String fun = f.getFunction();

							if (fun.equals(pFun)) {

							} else {

								pFun = fun;
								List<Menu> aList = fList.stream().filter(s -> s.getFunction().equals(fun))
										.collect(Collectors.toList());
								List<Activity> sList = new ArrayList<Activity>();
								for (Menu a : aList) {
									Activity newAct = new Activity();
									newAct.setName(a.getActivity());
									newAct.setActivity(a.getUrl());
									newAct.setActivityId(a.getActivityId());
									newAct.setActionId(a.getActionId());
									newAct.setActionName(a.getActionName());
									newAct.setActivityLogo(a.getActivityLogo());
									activityList.add(newAct);
									uList.add(a.getUrl());
									

									if (a.getActivityStatus()) { // checking only active functions
										sList.add(newAct);
									}

								}

								Function newFun = new Function();
								newFun.setName(fun);
								newFun.setFunction(sList);
								newFun.setFunction(sList);
								newFun.setFunctionId(f.getFunctionId());
								funList.add(newFun);
								newMod.setFunctionId(fList.get(0).getFunctionId());
								newMod.setActivityId(fList.get(0).getActivityId());
							}

						}

						newMod.setModule(funList);

						module.add(newMod);
					}
				}

			}

			/*
			 * adding extra urls to be accessed by all.
			 * 
			 */
			String dashboard = (String) session.getAttribute("DASHBOARD");
			uList.add(dashboard);
			session.setAttribute("MENU", module);
			session.setAttribute("MENU_COUNT", module.size());
			if (module.size() == 1) {
				session.setAttribute("MODULE_ID", mList.get(0).getModuleId());
			}
			System.out.println(filteredList);
			session.setAttribute("URL_LIST", uList);
			session.setAttribute("SELFSERVICELIST", filteredList);
			session.setAttribute("ACTIVITY_LIST", activityList);
			session.setAttribute("loginMessage", null);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		// set our response to OK status
		response.setStatus(HttpServletResponse.SC_OK);

		// since we have created our custom success handler, its up to us to
		// where
		// we will redirect the user after successfully login
		/*
		 * Long time = new Date().getTime(); Cookie cookie = new Cookie("sessoionTime",
		 * time.toString()); response.addCookie(cookie);
		 * 
		 * 
		 */
		
		
		String dashboard = (String) session.getAttribute("redirectUrl") != null
		        ? (String) session.getAttribute("redirectUrl")
		        : (String) session.getAttribute("DASHBOARD");
		
		System.out.println("Dashboard ==> " + dashboard);
		
		System.out.println("Dashboard ==> " + session.getAttribute("DASHBOARD"));



		/*
		 * String dashboard = (String) session.getAttribute("DASHBOARD"); if (module !=
		 * null) { if (module.size() == 1) {
		 * response.sendRedirect(mList.get(0).getUrl()); String url = httpSessionManager
		 * .encodeURL(dashboard, httpSessionManager.getCurrentSessionAlias(request)); //
		 * on login success add session alias in url response.sendRedirect(url);
		 * 
		 * } else { response.sendRedirect(dashboard); String url = httpSessionManager
		 * .encodeURL(dashboard, httpSessionManager.getCurrentSessionAlias(request)); //
		 * on login success add session alias in url response.sendRedirect(url); } }
		 * else { response.sendRedirect(dashboard); String url = httpSessionManager
		 * .encodeURL(dashboard, httpSessionManager.getCurrentSessionAlias(request)); //
		 * on login success add session alias in url response.sendRedirect(url); }
		 */

		response.sendRedirect(dashboard);
	}
}
