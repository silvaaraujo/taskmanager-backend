package br.com.silvaaraujo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.silvaaraujo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);
	
}
