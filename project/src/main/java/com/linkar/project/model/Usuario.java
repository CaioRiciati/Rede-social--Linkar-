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

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public TipoUsuario getTipo() {
			return tipo;
		}

		public void setTipo(TipoUsuario tipo) {
			this.tipo = tipo;
		}

		public String getFotoPerfil() {
			return fotoPerfil;
		}

		public void setFotoPerfil(String fotoPerfil) {
			this.fotoPerfil = fotoPerfil;
		}

		public List<Post> getPosts() {
			return posts;
		}

		public void setPosts(List<Post> posts) {
			this.posts = posts;
		}

		public List<Comentario> getComentarios() {
			return comentarios;
		}

		public void setComentarios(List<Comentario> comentarios) {
			this.comentarios = comentarios;
		}


	    
	    
	}


