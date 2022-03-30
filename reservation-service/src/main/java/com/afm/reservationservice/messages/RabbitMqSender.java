package com.afm.reservationservice.messages;

import model.utils.RemoveBookSeat;
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

    public void send(RemoveBookSeat removeBookSeat){
        rabbitTemplate.convertAndSend(exchange, routingkey, removeBookSeat);
    }

    public RemoveBookSeat buildMsg(String airplaneName, String seat){
        return new RemoveBookSeat(airplaneName, seat);
    }

}
