package ru.mayorov.deal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DealApplicaton {
    public static void main(String[] args){
        SpringApplication.run(DealApplicaton.class, args);
    }
}
