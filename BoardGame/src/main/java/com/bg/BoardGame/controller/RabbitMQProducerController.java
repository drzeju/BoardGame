package com.bg.BoardGame.controller;

import com.bg.BoardGame.model.User;
import com.bg.BoardGame.service.RabbitMQSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/rabbitmq/")
@RestController
public class RabbitMQProducerController {
    private RabbitMQSenderService rabbitMqSender;
    @Autowired
    public RabbitMQProducerController(RabbitMQSenderService rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }
    @Value("${app.message}")
    private String message;
    @PostMapping(value = "user")
    public String publishUserDetails(@RequestBody User user) {
        rabbitMqSender.send(user);
        return message;
    }
}
