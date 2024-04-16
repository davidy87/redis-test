package test.redis.redisrepository;

import org.springframework.data.repository.CrudRepository;
import test.redis.common.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
