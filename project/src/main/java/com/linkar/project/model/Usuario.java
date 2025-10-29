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

// Essa classe é tipo o "molde" dos usuários do site.
// Cada usuário vai ser salvo no banco de dados com esses dados aqui.

@Entity // Diz que essa classe vai virar uma tabela no banco de dados.
@Table(name = "usuarios") // Nome da tabela: usuarios
public class Usuario {

    @Id // Esse é o identificador único do usuário.
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // Aqui o banco gera o ID sozinho 

    private Long id; // o famoso "id" do usuário

    private String nome; // nome da pessoa 

    @Column(unique = true) 
    // o email tem que ser único, tipo ninguém pode usar o mesmo email.
    private String email;

    private String senha; // senha do usuário (óbvio rs)

    @Enumerated(EnumType.STRING)
    // tipo de usuário vai vir de um enum que a gente criou.
    private TipoUsuario tipo; 

    private String fotoPerfil; // link da foto do perfil

    @OneToMany(mappedBy = "usuario")
    // um usuário pode ter vários posts!
    private List<Post> posts;

    @OneToMany(mappedBy = "usuario")
    // e também pode ter vários comentários
    private List<Comentario> comentarios;


    // Daqui pra baixo são os gets e sets
   
    
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
