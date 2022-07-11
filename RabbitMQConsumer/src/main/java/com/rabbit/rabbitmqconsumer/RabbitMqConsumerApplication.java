package com.rabbit.rabbitmqconsumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RabbitMqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqConsumerApplication.class, args);
    }

}
