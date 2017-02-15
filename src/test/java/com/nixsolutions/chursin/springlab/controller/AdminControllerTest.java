package com.nixsolutions.chursin.springlab.controller;

import com.google.code.kaptcha.Constants;
import com.nixsolutions.chursin.springlab.entity.Role;
import com.nixsolutions.chursin.springlab.entity.User;
import com.nixsolutions.chursin.springlab.service.RoleService;
import com.nixsolutions.chursin.springlab.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

    private static final String CAPTCHA = "ASDF";
    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private User user;
    @Mock
    private Role role;
    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    public AdminControllerTest() {
    }

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setViewResolvers(viewResolver).build();
    }

    @Test
    public void testGetUserList() throws Exception {
        List<User> userList = mock(ArrayList.class);
        when(userService.findAll()).thenReturn(userList);
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", userList))
                .andExpect(view().name("admin"));
    }

    @Test
    public void testEditUserForm() throws Exception {
        Long id = 1L;
        when(userService.findById(id)).thenReturn(user);
        when(roleService.findAll()).thenReturn(new ArrayList<Role>());

        mockMvc.perform(get("/edit_user").param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attributeExists("userBean"))
                .andExpect(view().name("edituser"));
    }

    @Test
    public void testSubmitEditUserNoErrors() throws Exception {
        doNothing().when(userService).update(user);
        mockMvc.perform(post("/edit_user")
                .param("login", "login").param("password", "password")
                .param("passwordAgain", "password")
                .param("email", "email@mail.ru")
                .param("firstName", "fName").param("lastName", "lName")
                .param("birthday", "1990-10-10"))
                .andExpect(model().size(1))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("userBean"))
                .andExpect(model().attributeDoesNotExist("roles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admin"));
    }

    @Test
    public void testSubmitEditUserErrors() throws Exception {
        doNothing().when(userService).update(user);
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(roleService.findAll()).thenReturn(new ArrayList<Role>());
        mockMvc.perform(post("/edit_user")
                .param("login", "login")
                .param("password", "password")
                .param("passwordAgain", "pass")
                .param("email", "email@mail.ru")
                .param("firstName", "fName")
                .param("lastName", "lName")
                .param("birthday", "1990-10-10"))
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attributeExists("userBean"))
                .andExpect(model().attributeErrorCount("userBean", 2))
                .andExpect(model().attributeHasFieldErrors("userBean", "password"))
                .andExpect(model().attributeHasFieldErrors("userBean", "email"))
                .andExpect(status().isOk())
                .andExpect(view().name("edituser"));
    }

    @Test
    public void testAddUserForm() throws Exception {
        when(roleService.findAll()).thenReturn(new ArrayList<Role>());
        mockMvc.perform(get("/add_user"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attributeExists("userBean"))
                .andExpect(view().name("adduser"));
    }

    @Test
    public void testSubmitAddUserNoErrors() throws Exception {
        doNothing().when(userService).create(user);
        mockMvc.perform(post("/add_user")
                .param("login", "login")
                .param("password", "password")
                .param("passwordAgain", "password")
                .param("email", "email@mail.ru")
                .param("firstName", "fName")
                .param("lastName", "lName")
                .param("birthday", "1990-10-10"))
                .andExpect(model().size(1))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("userBean"))
                .andExpect(model().attributeDoesNotExist("roles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admin"));
    }

    @Test
    public void testSubmitAddUserErrors() throws Exception {
        doNothing().when(userService).create(user);
        when(roleService.findAll()).thenReturn(new ArrayList<Role>());
        when(userService.findByLogin(anyString())).thenReturn(user);
        when(userService.findByEmail(anyString())).thenReturn(user);
        mockMvc.perform(post("/add_user")
                .param("login", "login").param("password", "password")
                .param("passwordAgain", "pass")
                .param("email", "email@mail.ru")
                .param("firstName", "fName")
                .param("lastName", "lName")
                .param("birthday", "1990-10-10"))
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attributeExists("userBean"))
                .andExpect(model().attributeErrorCount("userBean", 3))
                .andExpect(model().attributeHasFieldErrors("userBean", "login"))
                .andExpect(model().attributeHasFieldErrors("userBean", "password"))
                .andExpect(model().attributeHasFieldErrors("userBean", "email"))
                .andExpect(status().isOk())
                .andExpect(view().name("adduser"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).remove(anyLong());
        this.mockMvc.perform(get("/delete_user").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admin"));
    }


    @Test
    public void testSignUpForm() throws Exception {
        mockMvc.perform(get("/sign_up"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userBean"))
                .andExpect(model().attributeDoesNotExist("roles"))
                .andExpect(view().name("signup"));
    }

    @Test
    public void testSubmitSignUpNoErrors() throws Exception {
        doNothing().when(userService).create(user);
        when(roleService.findById(anyLong())).thenReturn(role);
        mockMvc.perform(post("/sign_up")
                .param("login", "login")
                .param("password", "password")
                .param("passwordAgain", "password")
                .param("email", "email@mail.ru")
                .param("firstName", "fName")
                .param("lastName", "lName")
                .param("birthday", "1990-10-10")
                .param("captcha", CAPTCHA)
                .sessionAttr(Constants.KAPTCHA_SESSION_KEY, CAPTCHA))
                .andExpect(model().size(1))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("userBean"))
                .andExpect(model().attributeDoesNotExist("roles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("index"));
    }

    @Test
    public void testSubmitSignUpErrors() throws Exception {
        when(roleService.findById(anyLong())).thenReturn(role);
        doNothing().when(userService).create(user);
        mockMvc.perform(post("/sign_up")
                .param("login", "login").param("password", "password")
                .param("passwordAgain", "pass")
                .param("email", "email@mail.ru")
                .param("firstName", "fName")
                .param("lastName", "lName")
                .param("birthday", "1990-10-10")
                .param("captcha", "asd"))
                .andExpect(model().attributeDoesNotExist("roles"))
                .andExpect(model().attributeExists("userBean"))
                .andExpect(model().attributeErrorCount("userBean", 2))
                .andExpect(model().attributeHasFieldErrors("userBean", "password"))
                .andExpect(model().attributeHasFieldErrors("userBean", "captcha"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }
}
