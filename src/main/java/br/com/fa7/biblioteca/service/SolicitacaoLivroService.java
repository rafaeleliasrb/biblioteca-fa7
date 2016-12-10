package br.com.fa7.biblioteca.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;

import br.com.fa7.biblioteca.dao.PedidoDao;
import br.com.fa7.biblioteca.dao.SolicitacaoLivroDao;
import br.com.fa7.biblioteca.jms.DistribuidoraJMS;
import br.com.fa7.biblioteca.model.Pedido;
import br.com.fa7.biblioteca.model.SolicitacaoLivro;

@Stateless
public class SolicitacaoLivroService {

	@Inject
	private SolicitacaoLivroDao solicitacaoLivroDao;
	@Inject
	private PedidoDao pedidoDao;
	@Inject
	private DistribuidoraJMS distribuidoraJMS;

	public SolicitacaoLivro salvar(SolicitacaoLivro solicitacao) {
		return solicitacaoLivroDao.salvar(solicitacao);
	}

	public List<SolicitacaoLivro> selecionarTodasCriadas() {
		return solicitacaoLivroDao.selecionarTodasCriadas();
	}

	public void realizarPedido(List<SolicitacaoLivro> solicitacoes) throws JMSException {
		Pedido pedido = criarPedido();
		
		List<SolicitacaoLivro> solicitacoesDoPedido = solicitacoesQueEntraramNoPedido(solicitacoes);
		adicionarSolicitacoesNoPedido(pedido, solicitacoesDoPedido);

		pedido = pedidoDao.salvar(pedido);
		atualizarStatusSolicitacoes(solicitacoes);
		
		distribuidoraJMS.enviarPedido(pedido);
	}
	
	private void atualizarStatusSolicitacoes(List<SolicitacaoLivro> solicitacoes) {
		for(SolicitacaoLivro solicitacaoLivro : solicitacoes) {
			if(quantidadeLivroMaiorQueZero(solicitacaoLivro.getQuantidade())) {
				solicitacaoLivro.aceitarSolicitacao();
			}
			else {
				solicitacaoLivro.rejeitarSolicitacao();
			}
			salvar(solicitacaoLivro);
		}
		
	}
	
	private void adicionarSolicitacoesNoPedido(Pedido pedido, 
			List<SolicitacaoLivro> solicitacoesDoPedido) {
		for(SolicitacaoLivro solicitacaoLivro : solicitacoesDoPedido) {
			pedido.adicionarSolicitacao(solicitacaoLivro);
		}
	}
	
	private List<SolicitacaoLivro> solicitacoesQueEntraramNoPedido(List<SolicitacaoLivro> solicitacoes) {
		List<SolicitacaoLivro> solicitacoesDoPedido = new ArrayList<>();
		for(SolicitacaoLivro solicitacaoLivro : solicitacoes) {
			if(quantidadeLivroMaiorQueZero(solicitacaoLivro.getQuantidade())) {
				solicitacoesDoPedido.add(solicitacaoLivro);
			}
		}
		return solicitacoesDoPedido;
	}
	
	private boolean quantidadeLivroMaiorQueZero(Integer quantidade) {
		return quantidade > 0;
	}

	private Pedido criarPedido() {
		Pedido pedido = new Pedido();
		pedido = pedidoDao.salvar(pedido);
		return pedido;
	}
}
