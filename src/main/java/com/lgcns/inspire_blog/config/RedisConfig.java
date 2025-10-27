package com.lgcns.inspire_blog.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

    @Bean
    @Profile({"local"})
    public RedisConnectionFactory localRedisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    @Profile({"prod", "docker"})
    public RedisConnectionFactory prodRedisConnectionFactory(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port) {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    /**
     * ✅ 로컬 개발환경에서만 embedded-redis 실행
     */
    @Profile("local")
    @Configuration
    static class EmbeddedRedisServer {

        private RedisServer redisServer;

        @PostConstruct
        public void startRedis() throws IOException {
            redisServer = new RedisServer(6379);
            redisServer.start();
            System.out.println("✅ Embedded Redis started on port 6379");
        }

        @PreDestroy
        public void stopRedis() {
            if (redisServer != null) {
                redisServer.stop();
                System.out.println("🛑 Embedded Redis stopped");
            }
        }
    }
}