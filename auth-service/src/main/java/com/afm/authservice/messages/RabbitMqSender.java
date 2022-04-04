package com.afm.authservice.messages;

import com.afm.authservice.controller.AuthenticationController;
import model.profile.UserDetailMsg;
import model.utils.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {
    private RabbitTemplate rabbitTemplate;
    private static Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void send(UserDetailMsg user){
        logger.info("RabbitMQ Message sending with data : " + user);
        rabbitTemplate.convertAndSend(exchange, routingkey, user);
    }

    public UserDetailMsg buildUserDetail(String mail, String firstName, String secondName){
        return new UserDetailMsg(mail, firstName, secondName);
    }
}
