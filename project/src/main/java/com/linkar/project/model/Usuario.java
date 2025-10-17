package com.linkar.project.model;

import java.util.List;

import com.linkar.project.inum.TipoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
	@Table(name = "usuarios")
	public class Usuario {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nome;

	    @Column(unique = true)
	    private String email;

	    private String senha;

	    @Enumerated(EnumType.STRING)
	    private TipoUsuario tipo; 

	    private String fotoPerfil;

	    @OneToMany(mappedBy = "usuario")
	    private List<Post> posts;

	    @OneToMany(mappedBy = "usuario")
	    private List<Comentario> comentarios;


	}


