package com.nixsolutions.chursin.springlab.service;

import com.nixsolutions.chursin.springlab.entity.User;
import com.nixsolutions.chursin.springlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void create(User user) {
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void remove(Long id) {
        userRepository.delete(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
