package br.com.fa7.biblioteca.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fa7.biblioteca.model.Livro;
import br.com.fa7.biblioteca.service.LivroService;

@Named
@RequestScoped
public class ListarLivroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private LivroService livroService;

	private List<Livro> livros;
	
	@PostConstruct
	public void init() {
		carregarLivrosParaReserva();
	}

	private void carregarLivrosParaReserva() {
		livros = livroService.selecionarTodos();
	}
	
	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
}
