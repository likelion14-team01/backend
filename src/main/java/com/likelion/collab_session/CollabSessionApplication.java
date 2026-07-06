package com.likelion.collab_session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CollabSessionApplication {

  public static void main(String[] args) {
    SpringApplication.run(CollabSessionApplication.class, args);
  }

}
