package com.catalyst.drive.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.catalyst.drive.TestBase;
import com.catalyst.drive.entities.Role;
import com.catalyst.drive.entities.User;

public class TestRole extends TestBase {

	@Test
	@Override
	public void testBasicFunctionality() {
		Role role0 = new Role();
		role0.setId(0);
		Role role1 = new Role();
		role1.setId(1);
		assertTrue(!role0.equals(role1));
		role1.setId(0);
		assertTrue(role0.equals(role1));
		assertTrue(!role0.equals(new Object()));
		assertEquals(role0.toString(), "entities.Role[ id=" + 0 + " ]");
		assertEquals(role0.hashCode(), 0);
	}
	
	@Test
	public void testGettersAndSetters() {
		Role role = new Role();
		Integer id = 0;
		role.setId(id);
		assertEquals(role.getId(), id);
		String rolename = "test";
		role.setRolename(rolename);
		assertEquals(role.getRolename(), rolename);
		Set<User> users = new HashSet<User>();
		role.setUsers(users);
		assertEquals(role.getUsers(), users);
	}
}
