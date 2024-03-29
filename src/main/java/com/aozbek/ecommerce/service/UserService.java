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

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotExist::new);

        userRepository.delete(user);
    }

    public void assignRoleToUser(Long userId, Long roleId) {
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
        userRepository.save(user);
    }

    public void revokeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotExist::new);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(RoleNotExist::new);

        List<Role> roles = user.getRoles();
        if (roles != null) {
            roles.remove(role);
            user.setRoles(roles);
            userRepository.save(user);
        } else {
            throw new RoleNotExist();
        }
    }

    public List<Role> getAllRolesOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotExist::new);

        return user.getRoles();
    }
}
