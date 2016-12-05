package br.com.fa7.biblioteca.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.Hibernate;

import br.com.fa7.biblioteca.dao.AlunoDao;
import br.com.fa7.biblioteca.dao.LivroDao;
import br.com.fa7.biblioteca.model.Aluno;
import br.com.fa7.biblioteca.model.Livro;

@Stateless
public class AlunoService {

	@Inject
	private AlunoDao alunoDao;
	@Inject
	private LivroDao livroDao;
	
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

	public void reservarLivros(Aluno aluno, List<Livro> livrosSelecionados) {
		alunoDao.salvar(aluno);
		atualizarQuantidadeLivrosDisponiveis(livrosSelecionados);
	}

	private void atualizarQuantidadeLivrosDisponiveis(List<Livro> livrosSelecionados) {
		for(Livro livro : livrosSelecionados) {
			livro.diminurDisponiveis();
			livroDao.salvar(livro);
		}
	}
}
