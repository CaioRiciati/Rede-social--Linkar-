package com.linkar.project.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkar.project.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByIdUsuarioAndDataOrderByPrioridadeAsc(Long idUsuario, LocalDate data);
}
