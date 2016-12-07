package br.com.fa7.biblioteca.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
	@Inject
	private FacesContext context;
	
	private Aluno aluno;
	private List<Livro> livros;
	private List<Livro> livrosSelecionados;
	
	@PostConstruct
	public void init() {
		System.out.println("[INFO] Inicio reserva");
		inicializarListaSelecionados();
		aluno = (Aluno) context.getExternalContext()
				.getSessionMap().get("alunoLogado");
		aluno = alunoService.selecionar(aluno.getId());
		carregarLivrosParaReserva();
	}

	private void inicializarListaSelecionados() {
		livrosSelecionados = new ArrayList<Livro>();
	}
	
	public void adicionarLivroParaReserva(Livro livro) {
		livrosSelecionados.add(livro);
		context.addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", 
						livro.getTitulo() + ": " + livro.getAutor() + " foi adicionado."));
	}
	
	public void finalizarReserva() {
		adicionarSelecionados();
		alunoService.reservarLivros(aluno, livrosSelecionados);
		
		adicionarMsgSucesso();
		carregarLivrosParaReserva();
		inicializarListaSelecionados();
	}

	private void carregarLivrosParaReserva() {
		livros = livroService.selecionarTodosDisponiveis();
	}
	
	private void adicionarMsgSucesso() {
		context.addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Reserva realizada."));
	}

	private void adicionarSelecionados() {
		Boolean isAlunoLogado = aluno != null;
		aluno = alunoService.selecionarComLivros(aluno.getId());
		if(isAlunoLogado) {
			aluno.adicionarLivros(livrosSelecionados);
		}
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
}
