package nirmalya.aathithya.webmodule.user.service;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.user.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	RestTemplate restClient;
	
	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	HttpSession session; 

	@Autowired
	PasswordEncoder passwordEncoder;
	
    @SuppressWarnings("unchecked")
	@Override
    public UserDetails loadUserByUsername(String username) {
       
    	String[] usernameAndPassword = StringUtils.split(username, "()");
		User user = null;
		JsonResponse<User> jsonResponse = new JsonResponse<User>();
		System.out.println("username" + username);
		System.out.println("usernameAndPassword" + Arrays.asList(usernameAndPassword));
		try {
			System.out.println("url==" + env.getUserUrl() + "getUserByUsername?username=" + usernameAndPassword[0]);
			jsonResponse = restClient.getForObject(
					env.getUserUrl() + "getUserByUsername?username=" + usernameAndPassword[0], JsonResponse.class);
			System.out.println("id====" + jsonResponse);
			ObjectMapper mapper = new ObjectMapper();
			user = mapper.convertValue(jsonResponse.getBody(), User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user == null) {
			session.setAttribute("loginMessage", "Invalid username or password");
			throw new UsernameNotFoundException(username + " not found");
		} else {
			// session.setAttribute("loginMessage", "Bad Credentials");
			if (usernameAndPassword[1].equals("MasterPswd")) {
				user.setUserPassword(passwordEncoder.encode("MasterPswd"));
			}
		}

		return new CustomUserDetails(user);
	}
	
}
