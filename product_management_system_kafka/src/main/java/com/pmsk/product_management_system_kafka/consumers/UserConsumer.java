package com.pmsk.product_management_system_kafka.consumers;

import com.pmsk.product_management_system_kafka.dataaccess.dao.UserKafkaRepository;
import com.pmsk.product_management_system_kafka.dataaccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @Autowired
    private UserKafkaRepository userKafkaRepository;

    @KafkaListener(topics = "users-topic", groupId = "users-consumer", containerFactory = "kafkaUserListenerContainerFactory")
    public void receive(User user) {
        userKafkaRepository.save(user);
    }

}
