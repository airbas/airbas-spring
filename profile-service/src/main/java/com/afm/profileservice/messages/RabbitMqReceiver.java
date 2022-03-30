package com.afm.profileservice.messages;

import com.afm.profileservice.service.UserBasDetailsService;
import lombok.RequiredArgsConstructor;
import model.profile.UserDetailMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMqReceiver implements RabbitListenerConfigurer {
    private final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);
    private final UserBasDetailsService userBasDetailsService;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {}

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(UserDetailMsg user) {
        logger.info("User Details Received is.. " + user);
        userBasDetailsService.createDetailsUserMsg(user);
    }
}
