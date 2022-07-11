package com.bg.BoardGame.controller;

import com.bg.BoardGame.dto.ResponseDto;
import com.bg.BoardGame.dto.user.UserDto;
import com.bg.BoardGame.dto.user.UserUpdateDto;
import com.bg.BoardGame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;


//===========================================    SIGNUP    ===========================================
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody UserDto userDto){
        return userService.signup(userDto);
    }


//===========================================    UPDATE    ===========================================

    @PatchMapping("/update/{userId}")
    public ResponseDto updateUser(@PathVariable("userId") Integer userId, @RequestBody UserUpdateDto userUpdateDto){
        try {
            userService.updateUser(userUpdateDto, userId);
        } catch (Exception e) {
            e.printStackTrace();
        };
        return new ResponseDto("success", "User details has been updated");
    }




}
