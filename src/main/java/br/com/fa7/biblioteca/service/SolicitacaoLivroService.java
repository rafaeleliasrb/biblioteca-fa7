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

	public List<SolicitacaoLivro> selecionarTodos() {
		return solicitacaoLivroDao.selecionarTodos();
	}

	public void realizarPedido(Pedido pedido, List<SolicitacaoLivro> solicitacoes) throws JMSException {
		pedido = pedidoDao.salvar(pedido);
		
		solicitacoes = solicitacoesQueEntraramNoPedido(solicitacoes);
		pedido.setSolicitacoes(solicitacoes);

		pedido = pedidoDao.salvar(pedido);
		//TODO comunicaçao com a fila
		distribuidoraJMS.enviarPedido(pedido);
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
}
