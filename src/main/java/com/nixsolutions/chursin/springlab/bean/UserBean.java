package com.nixsolutions.chursin.springlab.bean;

import com.nixsolutions.chursin.springlab.entity.Role;
import com.nixsolutions.chursin.springlab.entity.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by chursin on 28.07.16.
 */
public class UserBean {

    protected Long id;

    @NotEmpty
    protected String firstName;

    @NotEmpty
    protected String lastName;

    @Size(min = 4, max = 20)
    protected String login;

    @Email
    @Size(min = 4, max = 20)
    protected String email;

    @Size(min = 4, max = 20)
    protected String password;

    @Size(min = 4, max = 20)
    private String passwordAgain;

    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date birthday;

    protected Role role;

    public static UserBean userToBean(User user) {
        UserBean userBean = new UserBean(user.getId(), user.getFirstName(),
                user.getLastName(), user.getLogin(), user.getEmail(),
                user.getPassword(), user.getBirthday(), user.getRole());
        return userBean;
    }

    public static User beanToUser(UserBean userBean) {
        User user = new User(userBean.getId(), userBean.getLogin(), userBean.getPassword()
                , userBean.getEmail(), userBean.getFirstName(), userBean.getLastName()
                , new java.sql.Date(userBean.getBirthday().getTime()), userBean.getRole());
        return user;
    }

    public UserBean() {
    }

    public UserBean(final Long id, final String firstName,
                    final String lastName, final String login, final String email,
                    final String password, final Date birthDate, final Role role) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.birthday = birthDate;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserBean [id=" + id + ", firstName=" + firstName + ", lastName="
                + lastName + ", login=" + login + ", email=" + email
                + ", password=" + password + ", birthday=" + birthday
                + ", role=" + role + "]";
    }

}
