package com.catalyst.drive.entities;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.catalyst.drive.TestBase;
import com.catalyst.drive.entities.Role;
import com.catalyst.drive.entities.User;

public class TestUser extends TestBase {

	@Test
	@Override
	public void testBasicFunctionality() {
		User user0 = new User();
		user0.setId(0);
		User user1 = new User();
		user1.setId(1);
		assertTrue(!user0.equals(user1));
		user1.setId(0);
		assertTrue(user0.equals(user1));
		assertTrue(!user0.equals(new Object()));
		assertEquals(user0.toString(), "entities.User[ id=" + 0 + " ]");
		assertEquals(user0.hashCode(), 0);
	}
	
	@Test
	public void testGettersAndSetters() {
		User user = new User();
		Integer id = 0;
		user.setId(id);
		assertEquals(user.getId(), id);
		String username = "test";
		user.setUsername(username);
		assertEquals(user.getUsername(), username);
		String password = "pass";
		user.setPassword(password);
		assertEquals(user.getPassword(), password);
		Set<Role> roles = new HashSet<Role>();
		user.setRoles(roles);
		assertEquals(user.getRoles(), roles);
	}
}
