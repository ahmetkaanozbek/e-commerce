package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    public ResponseEntity<User> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        User updatedUser = userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

}
