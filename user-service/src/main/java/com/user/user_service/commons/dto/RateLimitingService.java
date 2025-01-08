package com.user.user_service.commons.dto;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    @Value("${app.rateLimit}")
    private int rateLimit;
    @Value("${app.rateLimitDuration}")
    private int rateLimitDuration;
    @Value("${app.rateIPLimit}")
    private int rateIPLimit;
    @Value("${app.rateIPLimitDuration}")
    private int rateIPLimitDuration;

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String email) {
        return cache.computeIfAbsent(email, this::createBucket);
    }

    public Bucket createBucket(String email) {
        Bandwidth limit = Bandwidth.classic(rateLimit, Refill.intervally(rateLimit, Duration.ofMinutes(rateLimitDuration)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public Bucket resolveIPAddress(String ipAddress) {
        return cache.computeIfAbsent(ipAddress, this::createIPBucket);
    }

    public Bucket createIPBucket(String ipAddress) {
        Bandwidth limit = Bandwidth.classic(rateIPLimit, Refill.intervally(rateIPLimit, Duration.ofMinutes(rateIPLimitDuration)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
