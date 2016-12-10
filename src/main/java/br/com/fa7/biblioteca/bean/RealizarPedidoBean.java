package br.com.fa7.biblioteca.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;

import br.com.fa7.biblioteca.model.Pedido;
import br.com.fa7.biblioteca.model.SolicitacaoLivro;
import br.com.fa7.biblioteca.service.SolicitacaoLivroService;

@Named
@ViewScoped
public class RealizarPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private SolicitacaoLivroService solicitacaoLivroService;
	@Inject
	private FacesContext context;
	
	private Pedido pedido = new Pedido();
	private List<SolicitacaoLivro> solicitacoes = new ArrayList<SolicitacaoLivro>();
	
	@PostConstruct
	public void init() {
		solicitacoes = solicitacaoLivroService.selecionarTodos();
	}

	public void submeterPedido() throws JMSException {
		if(verificarQuantidadesDasSolicitacoes()) {
			solicitacaoLivroService.realizarPedido(pedido, solicitacoes);
			context.addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Pedido submetido."));
		}
	}
	
	private Boolean verificarQuantidadesDasSolicitacoes() {
		Boolean retorno = true;
		for(SolicitacaoLivro solicitacao : solicitacoes) {
			if(solicitacao.getQuantidade() == null) {
				retorno = false;
				context.addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", 
								"A quantidade de " 
										+ solicitacao.getTitulo() 
										+ ": " + solicitacao.getTitulo()
										+ " é obrigatória"));
			}
		}
		
		return retorno;
	}

	public List<SolicitacaoLivro> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<SolicitacaoLivro> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
}
