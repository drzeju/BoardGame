package com.bg.BoardGame.service;

import com.bg.BoardGame.dto.ResponseDto;
//import com.bg.BoardGame.dto.user.SigninDto;
//import com.bg.BoardGame.dto.user.SigninResponseDto;
//import com.bg.BoardGame.dto.user.SignoutResponseDto;
import com.bg.BoardGame.dto.user.UserDto;
import com.bg.BoardGame.dto.user.UserUpdateDto;
import com.bg.BoardGame.exceptions.CustomException;
import com.bg.BoardGame.model.AuthenticationToken;
import com.bg.BoardGame.model.Role;
import com.bg.BoardGame.model.User;
import com.bg.BoardGame.repository.RoleRepository;
import com.bg.BoardGame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public ResponseDto signup(UserDto userDto) {

        //check is user already present
        if(Objects.nonNull(userRepository.findByEmail(userDto.getEmail()))){
            throw new CustomException("user already present.");
        }

        //hash the password---------------------------------------------------------------------------------------------
        String encryptedpassword = userDto.getPassword();

        try {
            encryptedpassword = hashPassword(userDto.getPassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if(!optionalRole.isPresent()){
            throw new CustomException("role does not exist.");
        }

        Role role = optionalRole.get();

        //save the user-------------------------------------------------------------------------------------------------
        User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
                encryptedpassword, userDto.getRoleName(), role);

        userRepository.save(user);

        //create the token----------------------------------------------------------------------------------------------
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "dummy response");
        return responseDto;
    }




    public void updateUser(UserUpdateDto userUpdateDto, Integer userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()){
            throw new Exception("user is not present!");
        }
        User user = optionalUser.get();

        String encryptedpassword = userUpdateDto.getPassword();

        try {
            encryptedpassword = hashPassword(userUpdateDto.getPassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Optional<Role> optionalRole = roleRepository.findById(userUpdateDto.getRoleId());
        if(!optionalRole.isPresent()){
            throw new CustomException("role does not exist.");
        }

        Role role = optionalRole.get();

        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setPassword(encryptedpassword);
        user.setRoleName(userUpdateDto.getRoleName());
        user.setRole(role);

        userRepository.save(user);
    }




    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

}
