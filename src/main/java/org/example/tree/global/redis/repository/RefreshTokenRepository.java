package org.example.tree.global.redis.repository;

import org.example.tree.domain.member.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
