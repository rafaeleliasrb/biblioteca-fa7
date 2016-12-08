package br.com.fa7.biblioteca.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Pedido extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<SolicitacaoLivro> solicitacoes;

	public List<SolicitacaoLivro> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<SolicitacaoLivro> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
}
