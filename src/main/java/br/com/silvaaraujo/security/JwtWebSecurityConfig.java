package br.com.silvaaraujo.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	//declaracao das rotas publicas
	private static final String[] PUBLIC_ROUTES = {
		"/usuarios"
	};
	
	private static final String[] PUBLIC_ROUTES_GET = {
		//"/tasks"
	};
	
	/**
	 * Metodo para configuracao de propriedades referentes a seguranca da aplicacao.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//registra o bean (CorsConfigurationSource) criado abaixo e aplica as configuracoes descritas nele.
		http.cors()
			.and().csrf().disable(); //desabilita a protecao CSRF pois a aplicação eh stateless
		
		http.authorizeRequests()
			.antMatchers(PUBLIC_ROUTES).permitAll() //permite acesso sem autenticacao
			.antMatchers(HttpMethod.GET, PUBLIC_ROUTES_GET).permitAll() //permite acesso sem autenticacao somente para verbo GET
			.anyRequest().authenticated(); //para outras rotas exige autenticacao
		
		http.addFilter(new JwtAuthenticatonFilter(authenticationManager(), this.jwtUtil));
		http.addFilter(new JwtAuthorizationFilter(authenticationManager(), this.jwtUtil, this.userDetailsService));
		
		//garante que nao serao criadas sessoes de usuario
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/**
	 * Metodo reponsavel pela configuracao de CORS que a aplicacao assumira.
	 * @return
	 */
	@Bean
	protected CorsConfigurationSource CorsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		//permite acesso de multiplas fontes a todas as rotas com as configuracoes basicas
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	 * Bean responsavel por gerar o password codificado.
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
}
