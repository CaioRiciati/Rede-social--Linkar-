package com.linkar.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkar.project.inum.Categoria;
import com.linkar.project.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCriadoEmDesc();

    List<Post> findByCategoriaOrderByCriadoEmDesc(Categoria categoria);
}
