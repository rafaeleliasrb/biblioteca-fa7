package br.com.fa7.biblioteca.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fa7.biblioteca.model.Aluno;
import br.com.fa7.biblioteca.service.AlunoService;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoService alunoService;
	
	@Inject
	private FacesContext context;
	
	private Aluno aluno = new Aluno();
	
	public String efetuaLogin() {
		aluno = alunoService.selecionarPorMatricula(getAluno().getMatricula());
		
		boolean existe = aluno != null;
		if (existe) {
			context.getExternalContext().getSessionMap()
					.put("alunoLogado", aluno);
			return "reservarLivros?faces-redirect=true";
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Aluno não encontrado"));

		return "login?faces-redirect=true";
	}

	public String deslogar() {
		context.getExternalContext().getSessionMap().remove("alunoLogado");
		return "login?faces-redirect=true";
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
}
