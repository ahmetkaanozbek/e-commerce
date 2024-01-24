package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.exception.RoleNotExist;
import com.aozbek.ecommerce.exception.UserNotExist;
import com.aozbek.ecommerce.model.Role;
import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.repository.RoleRepository;
import com.aozbek.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotExist::new);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(RoleNotExist::new);

        List<Role> roles = user.getRoles();
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
