package br.com.fa7.biblioteca.dao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.fa7.biblioteca.model.Usuario;

@Stateless
public class UsuarioDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	GenericDao<Usuario, Integer> dao;
	
	@PostConstruct
	public void init() {
		dao = new GenericDao<>(entityManager);
	}

	public Usuario selecionarUsuarioPorLoginEsenha(Usuario usuario) {
		TypedQuery<Usuario> query = entityManager
				.createQuery("FROM Usuario u WHERE u.login = :login AND u.senha = :senha", 
						Usuario.class)
				.setParameter("login", usuario.getLogin())
				.setParameter("senha", usuario.getSenha());
		return query.getSingleResult();
	}
}
