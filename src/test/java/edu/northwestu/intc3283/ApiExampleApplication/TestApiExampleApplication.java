package edu.northwestu.intc3283.ApiExampleApplication;

import org.springframework.boot.SpringApplication;

public class TestApiExampleApplication {

    public static void main(String[] args) {
        SpringApplication.from(edu.northwestu.intc3283.ApiExampleApplication.ApiExampleApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
