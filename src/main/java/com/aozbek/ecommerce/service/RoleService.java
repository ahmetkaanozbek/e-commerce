package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.model.Role;
import com.aozbek.ecommerce.repository.RoleRepository;
import org.springframework.stereotype.Service;

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
        roleRepository.deleteById(roleId);
    }

    public Role updateRole(Long roleId, Role updatedName) {
        Role updatedRole = roleRepository.getReferenceById(roleId);
        updatedRole.setName(updatedName.getName());
        return roleRepository.save(updatedRole);
    }
}
