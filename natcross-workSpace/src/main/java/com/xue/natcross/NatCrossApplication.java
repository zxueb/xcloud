package com.xue.natcross;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NatCrossApplication {
  public static void main(String[] args) {
    SpringApplication.run(NatCrossApplication.class);
  }
}
