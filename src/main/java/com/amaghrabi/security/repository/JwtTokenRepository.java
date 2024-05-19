package com.amaghrabi.security.repository;

import com.amaghrabi.security.model.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Integer> {
    @Query(value = """
            select t from JwtToken t inner join User u
            on t.user.id = u.id
            where u.id = :id and (t.expired = false)
            """)
    List<JwtToken> findAllValidTokenByUser(Integer id);

    Optional<JwtToken> findByToken(String token);
}
