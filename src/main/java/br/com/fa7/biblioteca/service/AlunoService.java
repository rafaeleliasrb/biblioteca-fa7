package br.com.fa7.biblioteca.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.Hibernate;

import br.com.fa7.biblioteca.dao.AlunoDao;
import br.com.fa7.biblioteca.model.Aluno;

@Stateless
public class AlunoService {

	@Inject
	private AlunoDao alunoDao;
	
	public Aluno selecionarPorMatricula(String matricula) {
		if(matricula == null || matricula.isEmpty()) {
			throw new RuntimeException("Matrícula obrigatória.");
		}
		return alunoDao.selecionarPorMatricula(matricula);
	}
	
	public Aluno selecionar(Integer id) {
		return alunoDao.selecionar(id);
	}

	public Aluno salvar(Aluno aluno) {
		return alunoDao.salvar(aluno);
	}

	public Aluno selecionarComLivros(Integer id) {
		Aluno aluno = alunoDao.selecionar(id);
		Hibernate.initialize(aluno.getLivros());
		return aluno;
	}
}
