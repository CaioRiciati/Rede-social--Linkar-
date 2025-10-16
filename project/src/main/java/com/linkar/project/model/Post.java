package com.linkar.project.model;

import java.time.LocalDateTime;
import java.util.List;

import com.linkar.project.inum.Visibilidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(columnDefinition = "TEXT")
    private String conteudo;

    @Enumerated(EnumType.STRING)
    private Visibilidade visibilidade; // PUBLICO ou PRIVADO

    private LocalDateTime criadoEm = LocalDateTime.now();

    @OneToMany(mappedBy = "post")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "post")
    private List<Curtida> curtidas;

    // getters e setters
}
