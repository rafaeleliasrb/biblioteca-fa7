package br.com.fa7.biblioteca.dao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.fa7.biblioteca.model.SolicitacaoLivro;

@Stateless
public class SolicitacaoLivroDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	GenericDao<SolicitacaoLivro, Integer> dao;
	
	@PostConstruct
	public void init() {
		dao = new GenericDao<>(entityManager);
	}
	
	public SolicitacaoLivro salvar(SolicitacaoLivro solicitacao) {
		return dao.salvar(solicitacao);
	}
}
