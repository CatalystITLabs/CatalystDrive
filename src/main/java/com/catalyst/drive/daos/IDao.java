package com.catalyst.drive.daos;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Interface for a generic Dao
 * @author kpolen
 *
 * @param <T>
 */
public interface IDao<T> {

	/**
	 * Gets all entities of type T
	 * @return
	 */
	List<T> getAll();

	/**
	 * Create or update an entity
	 * @param t - the type
	 * @return the entity in updated form
	 */
	T createOrUpdate(T t);

	/**
	 * Delete an entity
	 * @param t - the entity
	 */
	void delete(T t);

	/**
	 * Delete an entity by id
	 * @param id - the entity's id
	 */
	void delete(int id);

	/**
	 * Gets the entity manager
	 * @return
	 */
	EntityManager getEm();

}