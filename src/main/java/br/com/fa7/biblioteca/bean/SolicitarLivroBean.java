package br.com.fa7.biblioteca.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fa7.biblioteca.model.SolicitacaoLivro;
import br.com.fa7.biblioteca.service.SolicitacaoLivroService;

@Named
@ViewScoped
public class SolicitarLivroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private SolicitacaoLivro solicitacao = new SolicitacaoLivro();

	@Inject
	private SolicitacaoLivroService solicitacaoLivroService;
	
	public void solicitarLivro() {
		solicitacaoLivroService.salvar(solicitacao);
		solicitacao = new SolicitacaoLivro();
	}

	public SolicitacaoLivro getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoLivro solicitacao) {
		this.solicitacao = solicitacao;
	}
}