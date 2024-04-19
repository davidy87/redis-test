package test.redis.redisrepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.redis.common.RefreshToken;
import test.redis.common.TestUtils;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/redis-repository")
@RestController
public class RedisRepositoryApiController {

    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/save")
    public String saveToRedisRepository() {
        RefreshToken refreshToken = RefreshToken.builder()
                .id(TestUtils.getRandomId())
                .token(TestUtils.getRandomUUID())
                .refreshTime(LocalDateTime.now())
                .build();

        log.info(">>>>> [save] refresh token: {}", refreshToken);
        refreshTokenRepository.save(refreshToken);
        return "success";
    }

    @GetMapping("/get")
    public String getFromRedisRepository() {
        refreshTokenRepository.findById(TestUtils.getRandomId());
        return "success";
    }
}
