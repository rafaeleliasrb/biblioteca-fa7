package br.com.fa7.biblioteca.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.fa7.biblioteca.model.BaseModel;

/**
 * @author RafaelElias
 *
 */
public class GenericDao<T extends BaseModel, I extends Serializable> {

	private EntityManager entityManager;
	
	private GenericDao() {
	}
	
	public GenericDao(EntityManager entityManager) {
		this();
		this.entityManager = entityManager;
	}
	
	public T salvar(T entidade) {
		if(seNovaEntidade(entidade)) {
			entityManager.persist(entidade);
		}
		else {
			entidade = entityManager.merge(entidade);
		}
		
		return entidade;
	}

	private boolean seNovaEntidade(T entidade) {
		return entidade.getId() == null;
	}

	@SuppressWarnings("unchecked")
	public List<T> selecionarTodos(Class<T> entidade) {
	   return entityManager.createQuery("FROM " + entidade.getSimpleName())
                .getResultList();
    }
	
	public T selecionar(Class<T> entidade, I id) {
	   return entityManager.find(entidade, id);
    }
}
