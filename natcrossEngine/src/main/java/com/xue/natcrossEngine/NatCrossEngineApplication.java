package com.xue.natcrossEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NatCrossEngineApplication {
  public static void main(String[] args) {
    SpringApplication.run(NatCrossEngineApplication.class);
  }
}
