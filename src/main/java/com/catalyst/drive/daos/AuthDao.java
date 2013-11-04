package com.catalyst.drive.daos;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

import com.catalyst.drive.entities.User;


/**
 * Dao which is used for authorization.  Can easily be replaced with your own implementation.  The AuthDao and User 
 * and Role entities are included to demonstrate spring security implementation.
 * @author kpolen
 * @param <T> - entity type
 * NOTE: Given the methods contained within, it would generally be better practice to have two daos, one for role and 
 * one for user, than to have this single AuthDao. For the sake of quick demonstration, this consolidation was done.  
 */
@Component
public class AuthDao extends Dao<User> implements IAuthDao{
	
	/**
	 * @see com.catalyst.drive.daos.IAuthDao#getUserbyUserName(java.lang.String)
	 */
	public User getUserbyUserName(String username) {
		Query q = getEm().createQuery("SELECT u FROM User u WHERE " +
				"u.username =:username");
		q.setParameter("username", username);
		try {
			return (User) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}

