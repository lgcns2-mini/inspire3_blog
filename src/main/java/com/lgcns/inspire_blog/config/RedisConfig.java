package com.lgcns.inspire_blog.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.lgcns.inspire_blog.util.PortUtils;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
public class RedisConfig {

    private RedisServer redisServer;
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 외부 Redis 연결 (기본 6379)
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @PostConstruct
    public void startRedis() throws Exception {
        String embeddedEnabled = System.getenv("EMBEDDED_REDIS_ENABLED");
        if (embeddedEnabled != null && embeddedEnabled.equalsIgnoreCase("false")) {
            System.out.println("🚫 Embedded Redis disabled. Using external Redis instead.");
            return;
        }

        redisPort = PortUtils.findAvailableTcpPort();
        System.out.println("[debug] >>>> redis port " + redisPort);
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            try {
                redisServer.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
