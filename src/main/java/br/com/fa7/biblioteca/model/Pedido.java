package br.com.fa7.biblioteca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Pedido extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
				mappedBy = "pedido", 
				fetch = FetchType.LAZY)
	private Collection<SolicitacaoLivro> solicitacoes;

	public Collection<SolicitacaoLivro> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Collection<SolicitacaoLivro> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public void adicionarSolicitacao(SolicitacaoLivro solicitacaoLivro) {
		if(this.solicitacoes == null) {
			solicitacoes = new ArrayList<SolicitacaoLivro>();
		}
		solicitacaoLivro.setPedido(this);
		this.solicitacoes.add(solicitacaoLivro);
	}
}
