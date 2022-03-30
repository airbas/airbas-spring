package com.afm.authservice.messages;

import model.profile.UserDetailMsg;
import model.utils.LoginRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void send(UserDetailMsg user){
        System.out.println(user);
        rabbitTemplate.convertAndSend(exchange, routingkey, user);
    }

    public UserDetailMsg buildUserDetail(String mail, String firstName, String secondName){
        return new UserDetailMsg(mail, firstName, secondName);
    }
}
