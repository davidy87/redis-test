package test.redis.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@RedisHash("refresh-token")
public class RefreshToken {

    @Id
    private String id;

    private String token;

    private LocalDateTime refreshTime;

    @Builder
    public RefreshToken(String id, String token, LocalDateTime refreshTime) {
        this.id = id;
        this.token = token;
        this.refreshTime = refreshTime;
    }
}
