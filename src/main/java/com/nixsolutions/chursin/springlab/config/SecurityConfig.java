package com.nixsolutions.chursin.springlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by chursin on 28.07.16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select login, password, 1 from user where login=?")
                .authoritiesByUsernameQuery(
                        "select u.login, r.name from user u, role r where u.role_id=r.id and u.login=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/add_user/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/edit_user/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/delete_user/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                .and()
                .formLogin().loginPage("/index").loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/dispatch").failureUrl("/index?error=true")
                .usernameParameter("j_username").passwordParameter("j_password").permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/index").permitAll().and().exceptionHandling().accessDeniedPage("/403");
    }

}
