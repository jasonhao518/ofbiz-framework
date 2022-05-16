package org.apache.ofbiz.base.config;

import java.util.Arrays;

import org.apache.ofbiz.entity.util.EntityUtilProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import io.lettuce.core.ReadFrom;

@Configuration
@EnableCaching
public class RedisConfig {
	
	@Bean
	public LettuceConnectionFactory connectionFactory() {
//		RedisStaticMasterReplicaConfiguration configuration = new RedisStaticMasterReplicaConfiguration(
//				"redis-master.default", 6379);
//		configuration.addNode("redis-replicas.default", 6379);
		boolean standalone = EntityUtilProperties.propertyValueEquals("general", "redis.type", "standalone");
		LettuceConnectionFactory factory = null;
		if(standalone) {
			factory = new LettuceConnectionFactory();
		}else {
			RedisClusterConfiguration configuration = new RedisClusterConfiguration(Arrays.asList("redis-redis-cluster-headless:6379"));
			
			configuration.setPassword("");
			LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder().readFrom(ReadFrom.ANY).build();
            factory = new LettuceConnectionFactory(configuration,clientConfig);
		}

		return factory;
	}
	
	@Bean
    public CacheManager cacheManager(LettuceConnectionFactory connectionFactory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//		config.addCacheKeyConverter(new RequestToSessionConverter());
        return  RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
    }
	
}
