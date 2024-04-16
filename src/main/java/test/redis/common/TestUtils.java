package test.redis.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.SplittableRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    public static String getRandomId() {
        SplittableRandom random = new SplittableRandom();
        return String.valueOf(random.nextInt(1, 20_001));
    }
}
