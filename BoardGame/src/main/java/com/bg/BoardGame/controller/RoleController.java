package com.bg.BoardGame.controller;

import com.bg.BoardGame.common.ApiResponse;
import com.bg.BoardGame.model.Category;
import com.bg.BoardGame.model.Role;
import com.bg.BoardGame.repository.CategoryRepository;
import com.bg.BoardGame.repository.RoleRepository;
import com.bg.BoardGame.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createRole(@RequestBody Role role) {
        roleService.createRole(role);
        return new ResponseEntity<>(new ApiResponse(true, "new role created"), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public List<Role> listRole() {
        return roleService.listRole();
    }

    @PostMapping("/update/{roleId}")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable("roleId") int roleId, @RequestBody Role role) {
        System.out.println("role id" + roleId);
        if (!roleService.findById(roleId)) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "role does not exist"), HttpStatus.NOT_FOUND);
        }
        roleService.editRole(roleId, role);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "role has been updated"), HttpStatus.OK);
    }


    @DeleteMapping("/remove/{roleId}")
    public ResponseEntity<ApiResponse> removeRole(@PathVariable("roleId") Integer roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (!optionalRole.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "role does not exist."), HttpStatus.BAD_REQUEST);
        }
        roleService.removeRole(roleId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "role has been removed."), HttpStatus.OK);

    }
}
