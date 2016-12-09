package br.com.fa7.biblioteca.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.fa7.biblioteca.model.Aluno;
import br.com.fa7.biblioteca.model.Usuario;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent evento) {

		FacesContext context = evento.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();

		System.out.println(nomePagina);

		if ("/login.xhtml".equals(nomePagina)) {
			return;
		}

		Aluno alunoLogado = (Aluno) context.getExternalContext()
				.getSessionMap().get("alunoLogado");
		Usuario usuario = (Usuario) context.getExternalContext()
				.getSessionMap().get("usuarioLogado");
		
		if (alunoLogado != null || usuario != null) {
			return;
		}

		NavigationHandler handler = context.getApplication()
				.getNavigationHandler();
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
