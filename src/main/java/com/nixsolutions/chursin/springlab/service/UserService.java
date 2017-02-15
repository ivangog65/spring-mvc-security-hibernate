package com.nixsolutions.chursin.springlab.service;

import com.nixsolutions.chursin.springlab.entity.User;

import java.util.List;

/**
 * Created by chursin on 27.07.16.
 */
public interface UserService {

    void create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    void remove(Long id);

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}
