package com.javacore.spring_api_app.service.limiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    private final Map<Long, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public Bucket resolveBucket(Long userId) {
        return buckets.computeIfAbsent(userId, this::newBucket);
    }

    private Bucket newBucket(Long userId) {
        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofHours(2)));

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
