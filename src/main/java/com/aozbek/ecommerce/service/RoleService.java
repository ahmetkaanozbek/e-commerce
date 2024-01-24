package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.exception.RoleNotExist;
import com.aozbek.ecommerce.model.Role;
import com.aozbek.ecommerce.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createRole(Role role) {
        roleRepository.save(role);
    }

    public void deleteRole(Long roleId) {
        roleRepository.findById(roleId).orElseThrow(RoleNotExist::new);
        roleRepository.deleteById(roleId);
    }

    public void updateRole(Long roleId, Role updatedName) {
        roleRepository.findById(roleId).orElseThrow(RoleNotExist::new);
        Role updatedRole = roleRepository.getReferenceById(roleId);
        updatedRole.setName(updatedName.getName());
        roleRepository.save(updatedRole);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
