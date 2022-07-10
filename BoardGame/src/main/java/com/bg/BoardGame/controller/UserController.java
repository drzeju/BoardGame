package com.bg.BoardGame.controller;

import com.bg.BoardGame.dto.ResponseDto;
import com.bg.BoardGame.dto.user.SignupDto;
import com.bg.BoardGame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

//@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;


//===========================================    SIGNUP    ===========================================
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signup(signupDto);
    }


////===========================================    SIGNIN    ===========================================
//    @PostMapping("/signin")
//    public SigninResponseDto signIn(@RequestBody SigninDto signinDto){
//        return userService.signin(signinDto);
//    }
//
//
////===========================================    SIGNOUT    ===========================================
//    @PostMapping("/signout")
//    public SignoutResponseDto signOut(@RequestBody User user) { return userService.signout(user); }


}
