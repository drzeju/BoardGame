package com.bg.BoardGame.service;

import com.bg.BoardGame.exceptions.AuthenticationFailException;
import com.bg.BoardGame.model.AuthenticationToken;
import com.bg.BoardGame.model.User;
import com.bg.BoardGame.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if (Objects.isNull(token)) {
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token) {
        //null check
        if(Objects.isNull(token)) {
            throw new AuthenticationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }
    }
}
