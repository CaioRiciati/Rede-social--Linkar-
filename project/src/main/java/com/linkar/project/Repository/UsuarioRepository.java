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
    // Aqui a gente tá dizendo que esse repositório vai cuidar da tabela de "Usuário"
    // e que o ID dessa tabela é do tipo Long (número grande).

	
	
    @Override
    Optional<Usuario> findById(Long id);
    // isso serve pra procurar um usuário pelo ID.
    // O "Optional" é tipo uma caixinha que pode vir com o usuário dentr ou não (caso não ache nada ).

    
    
    @Query(value = "SELECT * FROM usuarios WHERE email = :email AND senha = :senha", nativeQuery = true)
    // Essa é uma consulta (query) que a gente escreveu "na mão".
    // Ela procura um usuário com o email e senha que foram passados.
    // nativeQuery = true quer dizer que é SQL puro mesmo 
    
    
    Usuario login(@Param("email") String email, @Param("senha") String senha);
    // Esse método é tipo o "login" do site — ele vai no banco ver se existe um usuário com esse email e senha.
}
