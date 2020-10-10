package com.javatechie.spring.ws.api.config;

import com.javatechie.spring.ws.api.model.Man;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

// Configuration class to set up the redis configuration.
@Configuration
public class RedisConfig {

	// Setting up the jedis connection factory.
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName("redis-11341.c9.us-east-1-2.ec2.cloud.redislabs.com");
		jedisConnectionFactory.setPort(11341);
		jedisConnectionFactory.setPassword("TBtfevtswaVhou3bUUFU97slDAQC2Z3U");
		return jedisConnectionFactory;
	}
	 
	// Setting up the redis template object.
	@Bean
	public RedisTemplate<String, Man> redisTemplate() {
		final RedisTemplate<String, Man> redisTemplate = new RedisTemplate<String, Man>();

	//	Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Man>(Man.class));
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Man>(Man.class));
		return redisTemplate;
	}
}
