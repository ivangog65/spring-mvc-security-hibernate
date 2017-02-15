package com.nixsolutions.chursin.springlab.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.nixsolutions.chursin.springlab.config.DatabaseConfig;
import com.nixsolutions.chursin.springlab.entity.User;
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

import java.sql.Date;
import java.util.List;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class UserServiceTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    User user;

    @Before
    public void setUp() throws Exception {
/*        em = emf.createEntityManager();*/
        user = new User(100L, "login1", "password1", "email1"
                , "fn1", "ln1", Date.valueOf("1990-01-01"), roleService.findById(1L));
    }

    @Test
    public void testSaveUser() throws Exception {
        try {
            userService.create(null);
            fail("should be threw InvalidDataAccessApiUsageException when pass null");
        } catch (InvalidDataAccessApiUsageException e) {
        } catch (Exception e) {
            fail("should be threw InvalidDataAccessApiUsageException when pass null");
        }
        userService.create(user);
        User actualUser = userService.findByLogin(user.getLogin());
        assertEquals(user, actualUser);
        userService.remove(actualUser);
    }

    @Test
    public void testUpdateUser() {
        try {
            userService.update(null);
            fail("should be threw InvalidDataAccessApiUsageException when pass null");
        } catch (InvalidDataAccessApiUsageException e) {
        } catch (Exception e) {
            fail("should be threw InvalidDataAccessApiUsageException when pass null");
        }
        userService.create(user);
        User actualUser = userService.findByLogin(user.getLogin());
        assertEquals(user, actualUser);
        actualUser.setLogin("testUser");
        userService.update(actualUser);
        User actualTestUser = userService.findByLogin("testUser");
        assertEquals(actualUser, actualTestUser);
        userService.remove(actualTestUser);
    }

    @Test
    public void testRemoveUser() {
        try {
            userService.remove((User) null);
            fail("should be threw InvalidDataAccessApiUsageException when pass null");
        } catch (InvalidDataAccessApiUsageException e) {
        } catch (Exception e) {
            fail("should be threw InvalidDataAccessApiUsageException when pass null");
        }
        userService.create(user);
        User createdUser = userService.findByLogin(user.getLogin());
        assertEquals(user, createdUser);
        userService.remove(createdUser);
        assertNull(userService.findByLogin(createdUser.getLogin()));
    }

    @Test
    public void testRemoveById() {
        userService.create(user);
        User createdUser = userService.findByLogin(user.getLogin());
        assertEquals(user, createdUser);
        userService.remove(createdUser.getId());
        assertNull(userService.findByEmail(createdUser.getEmail()));
    }

    @Test
    public void testFindAll() {
        userService.create(user);
        List<User> users = userService.findAll();
        assertEquals(user, users.get(users.size() - 1));
        userService.remove(userService.findByLogin(user.getLogin()));
    }

    @Test
    public void testFindByLogin() {
        userService.create(user);
        User actualUser = userService.findByLogin(user.getLogin());
        assertEquals(user, actualUser);
        userService.remove(actualUser);
    }

    @Test
    public void testFindByEmail() {
        userService.create(user);
        User actualUser = userService.findByEmail(user.getEmail());
        assertEquals(user, actualUser);
        userService.remove(actualUser);
    }

    @Test
    public void testFindById() {
        userService.create(user);
        Long id = userService.findByLogin(user.getLogin()).getId();
        User actualUser = userService.findById(id);
        assertEquals(user, actualUser);
        userService.remove(actualUser);
    }


}
