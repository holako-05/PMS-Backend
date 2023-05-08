package com.pmsk.product_management_system_kafka.consumers;

import com.pmsk.product_management_system_kafka.dataaccess.dao.ProductKafkaRepository;
import com.pmsk.product_management_system_kafka.dataaccess.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {
    @Autowired
    private ProductKafkaRepository productKafkaRepository;

    @KafkaListener(topics = "products-topic", groupId = "products-consumer", containerFactory = "kafkaProductListenerContainerFactory")
    public void receive(Product product) {
        productKafkaRepository.save(product);
    }

}
