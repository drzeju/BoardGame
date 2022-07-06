package com.bg.BoardGame.service;

import com.bg.BoardGame.model.Category;
import com.bg.BoardGame.model.Role;
import com.bg.BoardGame.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void createRole(Role role) {
        roleRepository.save(role);
    }

    public List<Role> listRole() {
        return roleRepository.findAll();
    }

    public boolean findById(int roleId) {
        return roleRepository.findById(roleId).isPresent();
    }

    public void editRole(int roleId, Role updateRole) {
        Role role = roleRepository.getById(roleId);
        role.setRoleName(updateRole.getRoleName());
        roleRepository.save(role);
    }

    public void removeRole(Integer roleId) {
        roleRepository.deleteById(roleId);
    }
}
