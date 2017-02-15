package com.nixsolutions.chursin.springlab.service;

import com.nixsolutions.chursin.springlab.entity.Role;
import com.nixsolutions.chursin.springlab.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void create(Role role) {
        roleRepository.saveAndFlush(role);
    }

    @Transactional
    public void update(Role role) {
        roleRepository.saveAndFlush(role);
    }

    @Transactional
    public void remove(Role role) {
        roleRepository.delete(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }
}
