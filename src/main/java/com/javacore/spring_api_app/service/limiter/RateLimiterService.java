package com.javacore.spring_api_app.service.limiter;

import io.github.bucket4j.Bucket;

public interface RateLimiterService {
    Bucket resolveBucket(Long userId);
}
