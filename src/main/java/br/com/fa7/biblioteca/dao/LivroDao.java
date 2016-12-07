package br.com.fa7.biblioteca.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.fa7.biblioteca.model.Livro;

@Stateless
public class LivroDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	GenericDao<Livro, Integer> dao;
	
	@PostConstruct
	public void init() {
		dao = new GenericDao<>(entityManager);
	}
	
	public Livro salvar(Livro livro) {
		return dao.salvar(livro);
	}

	public List<Livro> selecionarTodos() {
		return dao.selecionarTodos(Livro.class);
	}

	public List<Livro> selecionarTodosDisponiveis() {
		TypedQuery<Livro> query = entityManager
				.createQuery("FROM Livro l WHERE l.quantidade > 0", Livro.class);
		return query.getResultList();
	}
}
