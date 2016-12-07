package br.com.fa7.biblioteca.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.fa7.biblioteca.dao.UsuarioDao;
import br.com.fa7.biblioteca.model.Usuario;

@Stateless
public class UsuarioService {

	@Inject
	private UsuarioDao usuarioDao;
	
	public Usuario selecionarUsuarioPorLoginESenha(Usuario usuario) {
		return usuarioDao.selecionarUsuarioPorLoginEsenha(usuario);
	}
}
