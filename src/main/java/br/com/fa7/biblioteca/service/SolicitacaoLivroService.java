package br.com.fa7.biblioteca.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.fa7.biblioteca.dao.SolicitacaoLivroDao;
import br.com.fa7.biblioteca.model.SolicitacaoLivro;

@Stateless
public class SolicitacaoLivroService {

	@Inject
	private SolicitacaoLivroDao solicitacaoLivroDao;

	public SolicitacaoLivro salvar(SolicitacaoLivro solicitacao) {
		return solicitacaoLivroDao.salvar(solicitacao);
	}
}
