package org.example.tree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreeApplication.class, args);
    }

}
