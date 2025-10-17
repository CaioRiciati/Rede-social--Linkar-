package com.linkar.project.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.linkar.project.model.Usuario;

public interface UsuarioRepository  extends CrudRepository<Usuario, String>{

	
	Usuario findById(long id);
	
	@Query(value="select * from linkar.usuarios where email = :email and senha = :senha", nativeQuery = true)
	public Usuario login(String email, String senha);
}
