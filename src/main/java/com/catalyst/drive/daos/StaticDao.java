package com.catalyst.drive.daos;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.catalyst.drive.util.ConfigUtils;
import com.catalyst.drive.util.Utils;

/**
 * Dao offering static, generic access to common transactional methods. NOTE: If
 * you have more than one persistence unit, you will want to refactor this class
 * to allow pulling of entities by persistence unit. Also, do not mix use of
 * this unit with others in the same transaction. I.E. Don't grab an entity with
 * this unit and try updating it with another.
 * 
 * @author kpolen
 * 
 */
public class StaticDao {
	private static final Logger LOGGER = Logger.getLogger(StaticDao.class);
	// Uses the default persistence unit when name is null
	private static final EntityManager em = Persistence.createEntityManagerFactory(getDefaultPUName())
			.createEntityManager();
	// Path to persistence.xml relative to classes in production or run/debug
	public static final String PERSISTENCE_XML = "/META-INF/persistence.xml";
	public static final String PERISTENCE_UNIT_TAG = "persistence-unit";
	public static final String NAME_ATTRIBUTE = "name";

	/**
	 * Gets the default persistence unit name. If this defaults to null and
	 * there is only one persistence unit, it will still load correctly.
	 * 
	 * @return
	 */
	private static String getDefaultPUName() {
		List<String> names;
		InputStream is = StaticDao.class.getClassLoader().getResourceAsStream(PERSISTENCE_XML);
		try {
			if (is != null) {
				// Production or debug.
				names = ConfigUtils.getConfigAttributeValue(is, PERISTENCE_UNIT_TAG, NAME_ATTRIBUTE);
			} else {
				// Testing. Note: when null createEntityManagerFactory uses the
				// first PU it finds
				names = null;
			}
		} catch (Exception e) {
			names = null;
		}
		return !CollectionUtils.isEmpty(names) ? names.get(0) : null;
	}

	/**
	 * Gets all entities of type T
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getAll(Class<T> c) {
		Query query = getEm().createQuery("SELECT t FROM " + c.getName() + " t ORDER BY t.id");
		return query.getResultList();
	}

	/**
	 * Proxy of the em.find method with a create functionality if id does not
	 * exist. You will have to persist the entity yourself for it to show up in
	 * the database if one is created.
	 * 
	 * @param c
	 *            - the entity type
	 * @param id
	 *            - the id
	 * @return an entity of id and type passed
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOrCreate(Class<T> c, int id) {
		T t = getEm().find(c, id);
		if (t == null) {
			try {
				Class<?> clazz = Class.forName(c.getName());
				Constructor<?> ctor = clazz.getConstructor();
				t = (T) ctor.newInstance(new Object[] {});
			} catch (Exception e) {
				LOGGER.warn("No entity of type " + c.getName() + " could be made, with ex: " + Utils.getStackTrace(e));
			}
		}
		return t;
	}

	/**
	 * Create or update an entity
	 * 
	 * @param t
	 *            - the entity
	 * @return the entity in updated form
	 */
	public static <T> T createOrUpdate(T t) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		T result = getEm().merge(t);
		tx.commit();
		return result;
	}

	/**
	 * Create or update a set of entities. Use this when creating or updating
	 * many entities at once.
	 * 
	 * @param tList
	 *            - the list of entities to be updated or created
	 * @return the entities in updated form
	 */
	public static <T> List<T> createOrUpdate(List<T> tList) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		List<T> results = new ArrayList<T>();
		for (T t : tList) {
			results.add(getEm().merge(t));
		}
		tx.commit();
		return results;
	}

	/**
	 * Delete an entity. Use this when deleting many entities at once.
	 * 
	 * @param t
	 *            - the entity
	 */
	public static <T> void delete(T t) {
		// merge first because we can't remove a detached entity
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(getEm().merge(t));
		tx.commit();
	}

	/**
	 * Deletes a set of entities.
	 * 
	 * @param tList
	 *            - the list of entities
	 */
	public static <T> void delete(List<T> tList) {
		// merge first because we can't remove a detached entity
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		for (T t : tList) {
			getEm().remove(getEm().merge(t));
		}
		tx.commit();
	}

	/**
	 * Delete entity by its id
	 * 
	 * @param c
	 *            - type/class of the entity
	 * @param id
	 *            - id of the entity
	 */
	public static <T> void delete(Class<T> c, int id) {
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		T t = getEm().find(c, id);
		if (t != null) {
			getEm().remove(t);
		}
		tx.commit();
	}

	/**
	 * Executes a query with the passed map of named parameters.
	 * 
	 * @param query
	 *            - an HQL query using named parameters
	 * @param paramMap
	 *            - a map of names and values for parameters in the query
	 * @return the result list
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> query(String query, Map<String, Object> paramMap) {
		Query q = em.createQuery(query);
		if (paramMap != null) {
			for (Map.Entry<String, Object> param : paramMap.entrySet()) {
				q.setParameter(param.getKey(), param.getValue());
			}
		}
		return q.getResultList();
	}

	/**
	 * Executes a query with the passed list of named parameters.
	 * 
	 * @param query
	 *            - an HQL query using ? parameters
	 * @param paramMap
	 *            - a list of values for parameters in the query in the order by
	 *            which they appear
	 * @return the result list
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> query(String query, List<Object> params) {
		Query q = em.createQuery(query);
		if (params != null) {
			// Ordinal parameters are 1-based
			for (int i = 1; i <= params.size(); i++) {
				q.setParameter(i, params.get(i - 1));
			}
		}
		return q.getResultList();
	}

	/**
	 * Gets the entity manager
	 * 
	 * @return the entity manager
	 */
	public static EntityManager getEm() {
		return em;
	}
}
