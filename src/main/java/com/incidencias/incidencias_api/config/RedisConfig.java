package com.incidencias.incidencias_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.incidencias.incidencias_api.dto.IncidenciaDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;
@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        CollectionType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, IncidenciaDTO.class);

        Jackson2JsonRedisSerializer<List<IncidenciaDTO>> listSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, listType);

        Jackson2JsonRedisSerializer<IncidenciaDTO> singleSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, IncidenciaDTO.class);

        RedisCacheConfiguration listConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(listSerializer)
                );

        RedisCacheConfiguration singleConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(singleSerializer)
                );

        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        cacheConfigs.put("incidencias-distrito", listConfig);
        cacheConfigs.put("incidencias", singleConfig);
        cacheConfigs.put("usuarios", singleConfig);

        return RedisCacheManager.builder(factory)
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }
}
