package com.catalyst.drive.daos;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.catalyst.drive.entities.User;

/**
 * IAuth interface
 * @author kpolen
 *
 */
public interface IAuthDao extends IDao<User>{

	/**
	 * Gets a user by username
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
	User getUserbyUserName(String username);

}