package com.github.maojx0630.upright.mahjong;

import lombok.SneakyThrows;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(annotationClass = Repository.class)
public class DemoApplication {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
