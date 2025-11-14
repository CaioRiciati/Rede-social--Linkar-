package com.linkar.project.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkar.project.model.Usuario;

@Repository 

// Essa anotação aqui diz que essa interface é um "repositório".
// Ou seja, é ela que vai conversar direto com o banco de dados.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	
	
    @Override
    Optional<Usuario> findById(Long id);


    
    
    @Query(value = "SELECT * FROM usuarios WHERE email = :email AND senha = :senha", nativeQuery = true)

    
    Usuario login(@Param("email") String email, @Param("senha") String senha);
    // Esse método é tipo o "login" do site — ele vai no banco ver se existe um usuário com esse email e senha.
}
