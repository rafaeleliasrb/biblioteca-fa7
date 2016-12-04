package br.com.fa7.biblioteca.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.fa7.biblioteca.dao.LivroDao;
import br.com.fa7.biblioteca.model.Livro;

@Stateless
public class LivroService {

	@Inject
	private LivroDao dao;
	
	public Livro salvar(Livro livro) {
		return dao.salvar(livro);
	}

	public List<Livro> selecionarTodos() {
		return dao.selecionarTodos();
	}
}
