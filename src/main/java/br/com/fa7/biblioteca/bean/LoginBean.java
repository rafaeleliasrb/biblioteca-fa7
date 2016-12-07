package br.com.fa7.biblioteca.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fa7.biblioteca.model.Aluno;
import br.com.fa7.biblioteca.model.Usuario;
import br.com.fa7.biblioteca.service.AlunoService;
import br.com.fa7.biblioteca.service.UsuarioService;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoService alunoService;
	@Inject
	private FacesContext context;
	@Inject
	private UsuarioService usuarioService;
	
	private Usuario usuario = new Usuario();
	
	public String efetuaLogin() {
		usuario = usuarioService.selecionarUsuarioPorLoginESenha(usuario);
		
		boolean existeUsuario = usuario != null;
		if(existeUsuario) {
			Aluno aluno = alunoService.selecionar(usuario.getId());
			
			boolean existeAluno = aluno != null;
			if (existeAluno) {
				context.getExternalContext().getSessionMap()
					.put("alunoLogado", aluno);
				return "listarLivros?faces-redirect=true";
			}
			else if(usuario.getAdministrador()) {
				context.getExternalContext().getSessionMap()
					.put("usuarioLogado", usuario);
				return "avaliarSolicitacoes?faces-redirect=true";
			}
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));

		return "login?faces-redirect=true";
	}

	public String deslogar() {
		context.getExternalContext().getSessionMap().remove("alunoLogado");
		return "login?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
