package com.linkar.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkar.project.model.Curtida;
import com.linkar.project.model.Post;
import com.linkar.project.model.Usuario;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    boolean existsByPostAndUsuario(Post post, Usuario usuario);
    void deleteByPostAndUsuario(Post post, Usuario usuario);
	int countByPost(Post post);
}
