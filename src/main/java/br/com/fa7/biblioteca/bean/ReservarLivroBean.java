package br.com.fa7.biblioteca.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fa7.biblioteca.model.Aluno;
import br.com.fa7.biblioteca.model.Livro;
import br.com.fa7.biblioteca.service.AlunoService;
import br.com.fa7.biblioteca.service.LivroService;

@Named
@SessionScoped
public class ReservarLivroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private LivroService livroService;
	@Inject
	private AlunoService alunoService;
	
	private FacesContext context;
	
	private Aluno aluno;
	private List<Livro> livros;
	private List<Livro> livrosSelecionados;
	
	@PostConstruct
	public void init() {
		context = FacesContext.getCurrentInstance();
		aluno = (Aluno) context.getExternalContext()
				.getSessionMap().get("alunoLogado");
		aluno = alunoService.selecionar(aluno.getId());
		carregarLivrosParaReserva();
	}

	private void carregarLivrosParaReserva() {
		livros = livroService.selecionarTodos();
		
	}

	public void adicionarSelecionados() {
		Boolean isAlunoLogado = aluno != null;
		aluno = alunoService.selecionarComLivros(aluno.getId());
		if(isAlunoLogado) {
			aluno.adicionarLivros(livrosSelecionados);
		}
	}
	
	public void finalizarReserva() {
		alunoService.reservarLivros(aluno, livrosSelecionados);
	}
	
	public String solicitarLivro() {
		return "solicitarLivros?faces-redirect=true";
	}
	
	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public List<Livro> getLivrosSelecionados() {
		return livrosSelecionados;
	}

	public void setLivrosSelecionados(List<Livro> livrosSelecionados) {
		this.livrosSelecionados = livrosSelecionados;
	}
}
