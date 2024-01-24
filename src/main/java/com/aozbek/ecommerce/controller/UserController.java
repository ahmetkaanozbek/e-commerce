package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.model.Role;
import com.aozbek.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("A user has been deleted successfully.");
    }

    @PostMapping(value = "/{userId}/assign-role/{roleId}")
    public ResponseEntity<String> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.status(HttpStatus.OK).body("A role has been assigned successfully.");
    }

    @PatchMapping(value = "/{userId}/revoke-role/{roleId}")
    public ResponseEntity<String> revokeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.revokeRoleFromUser(userId, roleId);
        return ResponseEntity.status(HttpStatus.OK).body("A role has been revoked successfully.");
    }

    @GetMapping(value = "/get-roles/{userId}")
    public ResponseEntity<List<Role>> getAllRolesOfUser(@PathVariable Long userId) {
        List<Role> roles = userService.getAllRolesOfUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }
}
