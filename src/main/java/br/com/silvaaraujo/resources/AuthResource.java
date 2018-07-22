package br.com.silvaaraujo.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.silvaaraujo.model.Usuario;
import br.com.silvaaraujo.security.JwtUtil;
import br.com.silvaaraujo.security.UserDetailServiceImpl;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping(value="/refresh_token", method=RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		System.out.println("REFRESH TOKEN");
		Usuario user = this.userDetailsService.getAuthenticatedUser();
		String token = this.jwtUtil.generateToken(user.getEmail());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
}
