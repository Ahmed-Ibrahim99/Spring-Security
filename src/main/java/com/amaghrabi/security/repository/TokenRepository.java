package com.amaghrabi.security.repository;

import com.amaghrabi.security.model.Token;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, JsonTypeInfo.Id> {
    Optional<Token> findByToken(String token);
}
