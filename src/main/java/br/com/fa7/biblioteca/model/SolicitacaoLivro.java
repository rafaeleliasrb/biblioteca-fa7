package br.com.fa7.biblioteca.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SolicitacaoLivro extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private String titulo;
	private String autor;
	private Integer quantidade;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID",nullable = false) 
	private Pedido pedido;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
}
