package com.nixsolutions.chursin.springlab.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.nixsolutions.chursin.springlab.config.DatabaseConfig;
import com.nixsolutions.chursin.springlab.entity.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    private Role[] roles = new Role[3];

    @Before
    public void setUp() throws Exception {
        roles[0] = new Role(101L, "newRole1");
        roles[1] = new Role(102L, "newRole2");
        roles[2] = new Role(103L, "testRole");
        roleService.create(roles[0]);
        roleService.create(roles[1]);
    }

    @After
    public void cleanUp() throws Exception {
        for (Role r : roles) {
            Role role = roleService.findByName(r.getName());
            if (role != null) {
                roleService.remove(role);
            }
        }
    }

    @Test
    public void testSaveRole() throws Exception {
        try {
            roleService.create(null);
            fail("should be threw InvalidDataAccessApiUsageException when pass null");
        } catch (InvalidDataAccessApiUsageException e) {
        } catch (Exception e) {
            fail("should be threw InvalidDataAccessApiUsageException when pass null");
        }
        roleService.create(roles[2]);
        Role role = roleService.findByName(roles[2].getName());
        assertEquals(roles[2].getName(), role.getName());
    }

    @Test
    public void testUpdateRole() throws Exception {
        try {
            roleService.update(null);
            fail("should be throw InvalidDataAccessApiUsageException if role null");
        } catch (InvalidDataAccessApiUsageException e) {
        } catch (Exception e) {
            fail("should be throw InvalidDataAccessApiUsageException if role null");
        }
        roles[0] = roleService.findByName(roles[0].getName());
        roles[0].setName("nobody");
        roleService.update(roles[0]);
        Role role = roleService.findByName(roles[0].getName());
        assertEquals(roles[0], role);
    }

    @Test
    public void testFindByName() throws Exception {
        assertNull(roleService.findByName(null));
        Role expectedRole = roleService.findAll().get(0);
        Role role = roleService.findByName(expectedRole.getName());
        assertEquals(expectedRole, role);
    }

    @Test
    public void testFindById() throws Exception {
        Role expectedRole = roleService.findAll().get(0);
        Role role = roleService.findById(expectedRole.getId());
        assertEquals(expectedRole, role);
    }

    @Test
    public void testDeleteRole() throws Exception {
        try {
            roleService.remove(null);
            fail("should be throw InvalidDataAccessApiUsageException if role null");
        } catch (InvalidDataAccessApiUsageException e) {
        } catch (Exception e) {
            fail("should be throw InvalidDataAccessApiUsageException if role null");
        }
        Role expectedRole = roleService.findByName(roles[0].getName());
        assertEquals(expectedRole.getName(), roles[0].getName());
        roleService.remove(expectedRole);
        assertNull(roleService.findByName(expectedRole.getName()));
    }
}