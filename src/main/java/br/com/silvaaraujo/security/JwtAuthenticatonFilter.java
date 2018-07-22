package br.com.silvaaraujo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.silvaaraujo.model.Usuario;

public class JwtAuthenticatonFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;
	
	public JwtAuthenticatonFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super();
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication auth = null;
		try {
			Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSenha(), new ArrayList<>());
			auth = this.authenticationManager.authenticate(authToken);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain, 
											Authentication auth) throws IOException, ServletException {
		
		String username = ((Usuario)auth.getPrincipal()).getEmail();
		String token = this.jwtUtil.generateToken(username);
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
	}
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, 
        									HttpServletResponse response, 
        									AuthenticationException exception) throws IOException, ServletException {
        	
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }
        
        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }

}
