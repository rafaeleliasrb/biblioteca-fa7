package br.com.fa7.biblioteca.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.fa7.biblioteca.model.EnumStatusSolicitacao;
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

	public List<SolicitacaoLivro> selecionarTodasCriadas() {
		String sql = new String("FROM SolicitacaoLivro s WHERE s.status = :statusCriada");
		TypedQuery<SolicitacaoLivro> query = entityManager
				.createQuery(sql, SolicitacaoLivro.class)
				.setParameter("statusCriada", EnumStatusSolicitacao.CRIADA);
		
		return query.getResultList();
	}
}
