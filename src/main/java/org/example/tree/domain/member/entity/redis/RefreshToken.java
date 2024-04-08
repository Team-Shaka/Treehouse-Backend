package org.example.tree.domain.member.entity.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

// 테스트를 위해 짧게 하기도 해야함
// 60 * 60 * 24 * 14 <- 2주
@RedisHash(value = "refreshToken_Treehouse", timeToLive = 60 * 8)
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private Long memberId;

    @Indexed
    private String refreshToken;
}
