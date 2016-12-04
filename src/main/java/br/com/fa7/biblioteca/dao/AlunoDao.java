package br.com.fa7.biblioteca.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.fa7.biblioteca.model.Aluno;

@Stateless
public class AlunoDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	GenericDao<Aluno, Integer> dao;
	
	@PostConstruct
	public void init() {
		dao = new GenericDao<>(entityManager);
	}
	
	public Aluno selecionarPorMatricula(String matricula) {
		TypedQuery<Aluno> query = entityManager
				.createQuery("FROM Aluno a WHERE a.matricula LIKE :matricula", Aluno.class)
				.setParameter("matricula", matricula);
		List<Aluno> alunoRetorno = query.getResultList();
		return alunoRetorno == null? null : alunoRetorno.get(0);
	}

	public Aluno selecionar(Integer id) {
		return dao.selecionar(Aluno.class, id);
	}

	public Aluno salvar(Aluno aluno) {
		return dao.salvar(aluno);
	}

	public Aluno selecionarComLivros(Integer id) {
		TypedQuery<Aluno> query = entityManager
				.createQuery("FROM Aluno a JOIN FETCH a.livros WHERE a.id = :id", Aluno.class)
				.setParameter("id", id);
		List<Aluno> alunoRetorno = query.getResultList();
		return alunoRetorno == null? null : alunoRetorno.get(0);
	}
}
