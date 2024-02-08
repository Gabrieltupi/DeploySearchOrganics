package com.vemser.dbc.searchorganic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class SearchorganicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchorganicApplication.class, args);
    }

}
