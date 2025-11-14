package com.linkar.project.model;

import java.time.LocalDateTime;
import java.util.List;

import com.linkar.project.inum.Categoria;
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
    private Visibilidade visibilidade;

    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "foto_url")
    private String fotoUrl; //Caminho da imagem (ex: "/uploads/posts/123.jpg")

    @OneToMany(mappedBy = "post")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "post")
    private List<Curtida> curtidas;
    
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Visibilidade getVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(Visibilidade visibilidade) {
		this.visibilidade = visibilidade;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Curtida> getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(List<Curtida> curtidas) {
		this.curtidas = curtidas;
	}

	// Retorna a quantidade de curtidas do post
	public int getQtdCurtidas() {
	    return curtidas != null ? curtidas.size() : 0;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	

	}



    
    
}
