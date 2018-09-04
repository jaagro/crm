package com.jaagro.crm.biz.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author tony
 */
@Component
public class Consumer {

    private static final String QUEUE = "comp-queue";

    @RabbitListener(queues = QUEUE)
    public void receive(String message){
        System.out.println(message + ": 我是接收者");
    }
}
