package br.com.fa7.biblioteca.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fa7.biblioteca.model.SolicitacaoLivro;
import br.com.fa7.biblioteca.service.SolicitacaoLivroService;

@Named
@ViewScoped
public class SolicitarLivroBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private SolicitacaoLivroService solicitacaoLivroService;
	@Inject
	private FacesContext context;
	
	private SolicitacaoLivro solicitacao = new SolicitacaoLivro();
	
	public void solicitarLivro() {
		solicitacaoLivroService.salvar(solicitacao);
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", 
						"Solicitação realizada."));
		solicitacao = new SolicitacaoLivro();
	}

	public SolicitacaoLivro getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoLivro solicitacao) {
		this.solicitacao = solicitacao;
	}
}
