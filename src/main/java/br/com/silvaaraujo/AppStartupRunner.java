package br.com.silvaaraujo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.silvaaraujo.model.Usuario;
import br.com.silvaaraujo.repository.UsuarioRepository;

@Component
public class AppStartupRunner implements ApplicationRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		initUser();
	}

	private void initUser() {
		Usuario usuario = usuarioRepository.findByEmail("user@mail.com");
		if (usuario == null) {
			usuario = new Usuario("user@mail.com", passwordEncoder.encode("123"));
			this.usuarioRepository.save(usuario);
		}
	}

}
