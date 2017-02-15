package com.nixsolutions.chursin.springlab.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    private static final String roleUser = "ROLE_USER";
    private static final String roleAdmin = "ROLE_ADMIN";

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    public LoginControllerTest() {
    }

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setViewResolvers(viewResolver).build();
    }

    @Test
    public void testAccessDenied() throws Exception {
        String msg = "Hi name, you do not have permission to access this page!";
        Principal user = mock(Principal.class);
        when(user.getName()).thenReturn("name");
        mockMvc.perform(get("/403").principal(user))
                .andExpect(status().isOk())
                .andExpect(model().attribute("msg", msg))
                .andExpect(view().name("403"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("index"));
    }

    @Test
    public void testDispatchAdmin() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.isUserInRole(roleAdmin)).thenReturn(true);
        loginController.dispatch(request);
        verify(request, times(1)).isUserInRole(roleAdmin);
        verify(request, never()).isUserInRole(roleUser);
    }

    @Test
    public void testDispatchUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.isUserInRole(roleUser)).thenReturn(true);
        loginController.dispatch(request);
        verify(request, times(1)).isUserInRole(roleAdmin);
        verify(request, times(1)).isUserInRole(roleUser);
    }

    @Test
    public void testDispatchIndex() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.isUserInRole(anyString())).thenReturn(false);
        loginController.dispatch(request);
        verify(request, times(2)).isUserInRole(anyString());
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testUserRedirect() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"));
    }


}
