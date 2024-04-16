package test.redis.redistemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.redis.common.RefreshToken;
import test.redis.common.TestUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/redis-template")
@RestController
public class RedisTemplateApiController {

    private final RedisTemplate<String, Object> hashRedisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    @PostMapping("/hash/save")
    public String saveToHashRedisTemplate() {
        RefreshToken refreshToken = RefreshToken.builder()
                .id(TestUtils.getRandomId())
                .token(UUID.randomUUID().toString())
                .refreshTime(LocalDateTime.now())
                .build();

        Jackson2HashMapper mapper = new Jackson2HashMapper(true);
        Map<String, Object> hash = mapper.toHash(refreshToken);
        hashRedisTemplate.opsForHash().putAll("refresh-token:" + refreshToken.getId(), hash);

        return "success";
    }

    @GetMapping("/hash/get")
    public String getFromHashRedisTemplate() {
        String id = TestUtils.getRandomId();
        hashRedisTemplate.opsForHash().get("refresh-token:" + id, id);
        return "success";
    }

    @PostMapping("/string/save")
    public String saveToStringRedisTemplate() {
        RefreshToken refreshToken = RefreshToken.builder()
                .id(TestUtils.getRandomId())
                .token(UUID.randomUUID().toString())
                .refreshTime(LocalDateTime.now())
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            stringRedisTemplate.opsForValue()
                    .set("refresh-token:" + refreshToken.getId(), mapper.writeValueAsString(refreshToken));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "success";
    }

    @GetMapping("/string/get")
    public String getFromStringRedisTemplate() {
        String id = TestUtils.getRandomId();
        String value = stringRedisTemplate.opsForValue().get("refresh-token:" + id);

        if (value == null) {
            return "Not Found";
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.readValue(value, RefreshToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "success";
    }
}
