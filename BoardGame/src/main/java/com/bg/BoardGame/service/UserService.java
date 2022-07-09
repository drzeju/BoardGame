package com.bg.BoardGame.service;

import com.bg.BoardGame.dto.ResponseDto;
import com.bg.BoardGame.dto.user.SigninDto;
import com.bg.BoardGame.dto.user.SigninResponseDto;
import com.bg.BoardGame.dto.user.SignupDto;
import com.bg.BoardGame.exceptions.AuthenticationFailException;
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
    public ResponseDto signup(SignupDto signupDto) {

        //check is user already present
        if(Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))){
            throw new CustomException("user already present.");
        }

        //hash the password---------------------------------------------------------------------------------------------
        String encryptedpassword = signupDto.getPassword();

        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Optional<Role> optionalRole = roleRepository.findById(signupDto.getRoleId());
        if(!optionalRole.isPresent()){
            throw new CustomException("role does not exist.");
        }

        Role role = optionalRole.get();

        //save the user-------------------------------------------------------------------------------------------------
        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(),
                encryptedpassword, signupDto.getRoleName(), role);

        userRepository.save(user);

        //create the token----------------------------------------------------------------------------------------------
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "dummy response");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }


    public SigninResponseDto signin(SigninDto signinDto) {
        //find user by email
        User user = userRepository.findByEmail(signinDto.getEmail());

        if (Objects.isNull(user)){
            throw new AuthenticationFailException("user is not valid");
        }

        //hash the password
        try {
            if (!user.getPassword().equals(hashPassword(signinDto.getPassword()))){
                throw new AuthenticationFailException("wrong password");
            }
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        //compare the password in DB

        //if password match
        AuthenticationToken token = authenticationService.getToken(user);

        //retrieve the token
        if (Objects.isNull(token)){
            throw new CustomException("token is not present.");
        }
        return new SigninResponseDto("success", token.getToken());

        //return the response
    }
}
