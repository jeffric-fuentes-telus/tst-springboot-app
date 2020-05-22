package com.telus.iofdoor.auth.api.repositories.factories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
@Configuration
public class JedisFactory {
    @Value("${redis.server}")
    private String redisServer;
    @Value("${env}")
    private String Environment;
    @Value("${redis.port}")
    int redisPort;
    @Value("${redis.password}")
    String redisPassword;
    private Jedis jedisInstance;


    @Bean
    public Jedis getJedisInstance(){
        this.jedisInstance = new Jedis(redisServer, redisPort);
        this.jedisInstance.auth(redisPassword);
        return this.jedisInstance;
    }

}
