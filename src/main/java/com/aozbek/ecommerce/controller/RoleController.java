package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.model.Role;
import com.aozbek.ecommerce.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createRole(@RequestBody Role role) {
        roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body("A role has been created successfully.");
    }

    @DeleteMapping(value = "/delete/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.status(HttpStatus.OK).body("A role has been deleted successfully.");
    }

    @PutMapping(value = "/update/{roleId}")
    public ResponseEntity<String> updateRole(@PathVariable Long roleId, @RequestBody Role updatedName) {
        roleService.updateRole(roleId, updatedName);
        return ResponseEntity.status(HttpStatus.OK).body("A role has been updated successfully.");
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> allRoles = roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(allRoles);
    }
}
