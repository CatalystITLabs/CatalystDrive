package com.catalyst.drive.daos;

import org.springframework.core.GenericTypeResolver;
import java.util.List;
import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * Dao provides basic CRUD functionality for an entity. <br/>
 * <B> Usage: </B><br/>
 * For a class Foo you must create a new class: <br/>
 * <code>Class FooDao extends Dao &LTFoo&GT { }</code> <br/>
 * You can not simply write: <br/>
 * <code> @Autowired Dao &LTFoo&GT fooDao<br/> @Autowired Dao &LTBar&GT barDao<br/> </code>
 * because only one bean would be created for the Dao class and those two
 * statements would conflict.
 * 
 * @see DaoTest
 * 
 * @author slujan, edited by kpolen for use in this project
 * 
 */
public class Dao<T> implements IDao<T> {

	/**
	 * Injected database connection:
	 */
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	private Class<T> typeParameterClass = (Class<T>) GenericTypeResolver
			.resolveTypeArgument(getClass(), Dao.class);
	private String typeName = typeParameterClass.getName();

	/**
	 * @see com.catalyst.drive.daos.IDao#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Query query = this.em.createQuery("SELECT t FROM " + typeName
				+ " t ORDER BY t.id");
		return query.getResultList();
	}

	/**
	 * @see com.catalyst.drive.daos.IDao#createOrUpdate(T)
	 */
	@Transactional
	public T createOrUpdate(T t) {
		return this.em.merge(t);
	}

	/**
	 * @see com.catalyst.drive.daos.IDao#delete(T)
	 */
	@Transactional
	public void delete(T t) {
		// merge first because we can't remove a detached entity
		this.em.remove(em.merge(t));
	}

	/**
	 * @see com.catalyst.drive.daos.IDao#delete(int)
	 */
	@Transactional
	public void delete(int id) {
		T t = em.find(typeParameterClass, id);
		em.remove(t);
	}

	/**
	 * @see com.catalyst.drive.daos.IDao#delete(int)
	 */
	public T find(int id) {
		return em.find(typeParameterClass, id);
	}

	/**
	 * @see com.catalyst.drive.daos.IDao#getEm()
	 */
	public EntityManager getEm() {
		return em;
	}
}
