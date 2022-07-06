package com.bg.BoardGame.repository;

import ch.qos.logback.core.subst.Token;
import com.bg.BoardGame.model.AuthenticationToken;
import com.bg.BoardGame.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);

}
