package com.nixsolutions.chursin.springlab.service;

import com.nixsolutions.chursin.springlab.entity.Role;

import java.util.List;

/**
 * Created by chursin on 27.07.16.
 */
public interface RoleService {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    List<Role> findAll();

    Role findById(Long id);
}
