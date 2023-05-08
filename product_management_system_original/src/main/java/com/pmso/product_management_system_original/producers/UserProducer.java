package com.pmso.product_management_system_original.producers;

import com.pmso.product_management_system_original.dataaccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    private static final String TOPIC_NAME = "users-topic";

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void send(User user) {
        kafkaTemplate.send(TOPIC_NAME, user);
    }

}
