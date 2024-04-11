package org.example.tree.domain.member.entity.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "phoneAuthTreeHouse", timeToLive = 1830)
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneAuth {
    @Id
    private String phoneNum;
    private LocalDateTime authNumTime;
    private Integer authNum;
}