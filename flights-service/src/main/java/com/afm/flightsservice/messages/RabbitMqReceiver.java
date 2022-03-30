package com.afm.flightsservice.messages;

import com.afm.flightsservice.service.AirPlaneService;
import lombok.RequiredArgsConstructor;
import model.profile.UserDetailMsg;
import model.utils.RemoveBookSeat;
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
    private final AirPlaneService airPlaneService;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {}

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(RemoveBookSeat removeBookSeat) {
        logger.info("Remove book seat received is.. " + removeBookSeat);
        airPlaneService.removeBookSeat(removeBookSeat.getFlightName(), removeBookSeat.getSeatCord());

    }
}
