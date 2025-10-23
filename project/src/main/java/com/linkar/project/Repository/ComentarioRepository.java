package com.linkar.project.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.linkar.project.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {}
