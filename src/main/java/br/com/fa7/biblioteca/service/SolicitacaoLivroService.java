package br.com.fa7.biblioteca.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.fa7.biblioteca.dao.PedidoDao;
import br.com.fa7.biblioteca.dao.SolicitacaoLivroDao;
import br.com.fa7.biblioteca.model.Pedido;
import br.com.fa7.biblioteca.model.SolicitacaoLivro;

@Stateless
public class SolicitacaoLivroService {

	@Inject
	private SolicitacaoLivroDao solicitacaoLivroDao;
	@Inject
	private PedidoDao pedidoDao;

	public SolicitacaoLivro salvar(SolicitacaoLivro solicitacao) {
		return solicitacaoLivroDao.salvar(solicitacao);
	}

	public List<SolicitacaoLivro> selecionarTodos() {
		return solicitacaoLivroDao.selecionarTodos();
	}

	public void realizarPedido(Pedido pedido, List<SolicitacaoLivro> solicitacoes) {
		pedido = pedidoDao.salvar(pedido);
		pedido.setSolicitacoes(solicitacoes);

		pedido = pedidoDao.salvar(pedido);
		//TODO comunicaçao com a fila
	}
}
