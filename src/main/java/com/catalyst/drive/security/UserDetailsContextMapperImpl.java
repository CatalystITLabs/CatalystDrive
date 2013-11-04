package com.catalyst.drive.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.catalyst.drive.daos.IAuthDao;
import com.catalyst.drive.daos.StaticDao;
import com.catalyst.drive.entities.Role;

/**
 * FOR APPLICATIONS WHICH AUTHENTICATE WITH LDAP BUT KEEP AN INDEPENDENT DB OF
 * USERS AND ROLES This class should inject the app-specific roles into the user
 * security object who has been authenticated from ldap, as well as perform
 * other post-authentication processes such as resetting login attempts, etc.
 * When the application has roles for its users in the database that do not map
 * directly to ldap roles, you should use this implementation.
 * 
 * @author kpolen
 * 
 */
@Service
public class UserDetailsContextMapperImpl implements UserDetailsContextMapper, Serializable {
	private static final long serialVersionUID = 3962976258168853954L;
	// ldap roles to be mapped
	public static final String ROLE_MD_ASSOCIATES = "MDAssociates";
	public static final String ROLE_MD_DEVELOPERS = "MDDevelopers";

	@Autowired
	private IAuthDao authDao;

	/**
	 * This method runs after the user is authenticated from ldap. Here is where
	 * the user's roles are pulled from the application db and added to the
	 * user's UserDetails object. If no user exists in the application db
	 * corresponding to this ldap user, you can create one in the catch when
	 * UsernameNotFoundException is thrown.
	 */
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities) {
		com.catalyst.drive.entities.User domainUser = authDao.getUserbyUserName(username);
		if (domainUser == null) {
			// Here is where a new user is created in the context of this
			// application if the user is not found
			domainUser = new com.catalyst.drive.entities.User();
			domainUser.setUsername(username);
			Set<Role> roles = new HashSet<Role>();
			domainUser.setRoles(roles);
			for (GrantedAuthority authority : authorities) {
				if (ROLE_MD_ASSOCIATES.equalsIgnoreCase(authority.getAuthority())) {
					roles.add(getOrCreateRoleFor(CatalystAuthority.ROLE_USER));
				} else if (ROLE_MD_DEVELOPERS.equalsIgnoreCase(authority.getAuthority())) {
					roles.add(getOrCreateRoleFor(CatalystAuthority.ROLE_ADMIN));
				} else {
					roles.add(getOrCreateRoleFor(CatalystAuthority.ROLE_OTHER));
				}
			}
			domainUser = authDao.createOrUpdate(domainUser);
		}
		return this.validUser(domainUser);

	}

	/**
	 * Gets or creates a new role for the catalystAuthority
	 * 
	 * @param catalystAuthority
	 *            - the CatalystAuthority
	 * @return a role
	 */
	@SuppressWarnings("unchecked")
	private Role getOrCreateRoleFor(CatalystAuthority catalystAuthority) {
		Role role;
		Query q = StaticDao.getEm().createQuery(
				"SELECT r FROM " + Role.class.getName() + " r WHERE r.rolename = :rolename");
		q.setParameter("rolename", catalystAuthority.getAuthority());
		List<Role> results = q.getResultList();
		if (CollectionUtils.isEmpty(results)) {
			role = new Role();
			role.setRolename(catalystAuthority.getAuthority());
			role = StaticDao.createOrUpdate(role);
		} else {
			role = results.get(0);
		}
		return role;
	}

	/**
	 * Necessary declaration to fulfill contract of implemented interface
	 */
	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
	}

	/**
	 * Returns a (security type) User to use for the UserDetails object in
	 * session In this method you can do role mapping/injection, lockout
	 * functionality, etc.
	 * 
	 * @param String
	 *            username
	 * @return org.springframework.security.core.userdetails.User user;
	 */
	public User validUser(com.catalyst.drive.entities.User domainUser) {
		Set<com.catalyst.drive.entities.Role> roles = domainUser.getRoles();

		// Create list of app-specific roles for this user
		List<String> roleNames = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(roles)) {
			for (com.catalyst.drive.entities.Role role : roles) {
				roleNames.add(role.getRolename());
			}
		}
		// Declare/set other fields for creation of Spring Security user
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		// Return org.springframework.security.core.userdetails.User user for
		// session
		return new User(domainUser.getUsername(), "password_undisclosed", enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, getGrantedAuthorities(roleNames));
	}

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * 
	 * @param roles
	 *            {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}