package br.com.fa7.biblioteca.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class SolicitacaoLivro extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private String titulo;
	private String autor;
	private Integer quantidade;
	@Enumerated(EnumType.ORDINAL)
	private EnumStatusSolicitacao status; 
	@ManyToOne(fetch=FetchType.LAZY)
	private Pedido pedido;
	
	public SolicitacaoLivro() {
		this.status = EnumStatusSolicitacao.CRIADA;
	}
	
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
	public EnumStatusSolicitacao getStatus() {
		return status;
	}
	public void setStatus(EnumStatusSolicitacao status) {
		this.status = status;
	}

	public void rejeitarSolicitacao() {
		if(this.status.equals(EnumStatusSolicitacao.CRIADA)) {
			this.status = EnumStatusSolicitacao.REJEITADA;
		}
		else {
			throw new RuntimeException("A solicitação precisa estar criada antes de ser rejeitada");
		}
	}
	
	public void aceitarSolicitacao() {
		if(this.status.equals(EnumStatusSolicitacao.CRIADA)) {
			this.status = EnumStatusSolicitacao.ACEITA;
		}
		else {
			throw new RuntimeException("A solicitação precisa estar criada antes de ser aceita");
		}
	}
}
