package br.com.fa7.biblioteca.dao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.fa7.biblioteca.model.Pedido;

@Stateless
public class PedidoDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	GenericDao<Pedido, Integer> dao;
	
	@PostConstruct
	public void init() {
		dao = new GenericDao<>(entityManager);
	}
	
	public Pedido salvar(Pedido pedido) {
		return dao.salvar(pedido);
	}
}
