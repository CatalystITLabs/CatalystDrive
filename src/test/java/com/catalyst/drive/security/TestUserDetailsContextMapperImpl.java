package com.catalyst.drive.security;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.catalyst.drive.TestBase;
import com.catalyst.drive.daos.IAuthDao;
import com.catalyst.drive.daos.IRoleDao;
import com.catalyst.drive.entities.Role;
import com.catalyst.drive.entities.User;
import com.catalyst.drive.security.UserDetailsContextMapperImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration(transactionManager = "myTxManager", defaultRollback = true)
public class TestUserDetailsContextMapperImpl extends TestBase {
	private User user;
	@Autowired
	private UserDetailsContextMapperImpl udcmi;
	@Autowired
	private IAuthDao authDao;
	@Autowired
	private IRoleDao roleDao;

	@Before
	public void setup() {
		user = new User();
		user.setUsername("test");
		user = authDao.createOrUpdate(user);
	}

	@Test
	public void testMapUserFromContextValidUser() {
		// Without roles
		DirContextOperations ctx = Mockito.mock(DirContextOperations.class);
		UserDetails ud = udcmi.mapUserFromContext(ctx, user.getUsername(), new ArrayList<GrantedAuthority>());
		assertEquals(ud.getUsername(), user.getUsername());
		// With roles
		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRolename("other");
		role = roleDao.createOrUpdate(role);
		user.setRoles(roles);
		user = authDao.createOrUpdate(user);
		ud = udcmi.mapUserFromContext(ctx, user.getUsername(), new ArrayList<GrantedAuthority>());
		assertEquals(ud.getUsername(), user.getUsername());
		user.setRoles(new HashSet<Role>());
		user = authDao.createOrUpdate(user);
		roleDao.delete(role);
	}

	@Test
	@Transactional
	public void testMapUserFromContextNewUser() {
		List<GrantedAuthority> authorities = UserDetailsContextMapperImpl.getGrantedAuthorities(Arrays
				.asList(new String[] { UserDetailsContextMapperImpl.ROLE_MD_ASSOCIATES,
						UserDetailsContextMapperImpl.ROLE_MD_DEVELOPERS, "other" }));
		DirContextOperations ctx = Mockito.mock(DirContextOperations.class);
		udcmi.mapUserFromContext(ctx, "newtest", authorities);
	}

	@After
	public void teardown() {
		authDao.delete(user);
	}
}
