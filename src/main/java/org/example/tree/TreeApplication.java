package org.example.tree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableRedisRepositories
public class TreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreeApplication.class, args);
    }

}
