package br.com.silvaaraujo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.silvaaraujo.model.Usuario;
import br.com.silvaaraujo.repository.UsuarioRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioRepository.findByEmail(username);
		if (user == null) throw new UsernameNotFoundException(username);
		return user;
	}

	public Usuario getAuthenticatedUser() {
		Usuario user = null;
		try {
			user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {}
		
		return user;
	}
}
