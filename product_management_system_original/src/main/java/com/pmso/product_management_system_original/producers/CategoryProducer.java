package com.pmso.product_management_system_original.producers;

import com.pmso.product_management_system_original.dataaccess.entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class CategoryProducer {

    private static final String TOPIC_NAME = "categories-topic";
    @Autowired
    private KafkaTemplate<String, Category> kafkaTemplate;

    public void send(Category category) {
        kafkaTemplate.send(TOPIC_NAME, category);
    }

}
