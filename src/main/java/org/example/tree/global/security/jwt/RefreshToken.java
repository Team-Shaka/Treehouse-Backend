package org.example.tree.global.security.jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RefreshToken {
    @Id
    private String memberId;

    private String token;

    public RefreshToken updateValue(String token) {
        this.token = token;
        return this;
    }
}
