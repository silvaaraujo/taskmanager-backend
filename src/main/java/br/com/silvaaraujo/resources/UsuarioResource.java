package br.com.silvaaraujo.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.silvaaraujo.model.Usuario;
import br.com.silvaaraujo.repository.UsuarioRepository;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> incluir(@RequestBody Usuario usuario) {
		usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		Usuario savedUser = this.usuarioRepository.save(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
