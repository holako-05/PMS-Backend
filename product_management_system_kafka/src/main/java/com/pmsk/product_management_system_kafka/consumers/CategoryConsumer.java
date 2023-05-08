package com.pmsk.product_management_system_kafka.consumers;

import com.pmsk.product_management_system_kafka.dataaccess.dao.CategoryKafkaRepository;
import com.pmsk.product_management_system_kafka.dataaccess.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CategoryConsumer {

    @Autowired
    private CategoryKafkaRepository categoryKafkaRepository;

    @KafkaListener(topics = "categories-topic", groupId = "categories-consumer", containerFactory = "kafkaCategoryListenerContainerFactory")
    public void receive(Category category) {
        categoryKafkaRepository.save(category);
    }

}
