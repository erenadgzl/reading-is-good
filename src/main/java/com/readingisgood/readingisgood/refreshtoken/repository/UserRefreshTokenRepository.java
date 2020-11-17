package com.readingisgood.readingisgood.refreshtoken.repository;

import com.readingisgood.readingisgood.refreshtoken.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

    Optional<UserRefreshToken> findByToken(String token);

}