package org.apache.ofbiz.base.config;

import java.time.Duration;

import org.apache.ofbiz.entity.util.EntityUtilProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;

@Configuration
@EnableCaching
public class RedisConfig {
	
	@Bean
	public LettuceConnectionFactory connectionFactory() {
//		RedisStaticMasterReplicaConfiguration configuration = new RedisStaticMasterReplicaConfiguration(
//				"redis-master.default", 6379);
//		configuration.addNode("redis-replicas.default", 6379);
		boolean standalone = EntityUtilProperties.propertyValueEquals("general", "redis.type", "standalone");

			RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
			        .master("mymaster")
			        .sentinel("redis-node-0.redis-headless.db.svc.cluster.local", 26379)
			        .sentinel("redis-node-1.redis-headless.db.svc.cluster.local", 26379)
			        .sentinel("redis-node-2.redis-headless.db.svc.cluster.local", 26379);
			sentinelConfig.setPassword(RedisPassword.of(""));
			sentinelConfig.setSentinelPassword(RedisPassword.of(""));
			sentinelConfig.setDatabase(1);

			SocketOptions socketOptions = SocketOptions.builder()
			        .connectTimeout(Duration.ofSeconds(30L))
			        .build();

			ClientOptions clientOptions = ClientOptions.builder()
			        .socketOptions(socketOptions)
			        .timeoutOptions(TimeoutOptions.enabled())
			        .build();

			LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
			        .commandTimeout(Duration.ofSeconds(30L))
			        .clientOptions(clientOptions)
			        .build();


			return new LettuceConnectionFactory(sentinelConfig, lettuceClientConfiguration);
	
	}
	
	@Bean
    public CacheManager cacheManager(LettuceConnectionFactory connectionFactory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//		config.addCacheKeyConverter(new RequestToSessionConverter());
        return  RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
    }
	
}
