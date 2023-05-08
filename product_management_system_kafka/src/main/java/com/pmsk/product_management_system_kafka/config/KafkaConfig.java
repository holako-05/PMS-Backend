package com.pmsk.product_management_system_kafka.config;

import com.pmsk.product_management_system_kafka.dataaccess.entities.Category;
import com.pmsk.product_management_system_kafka.dataaccess.entities.Product;
import com.pmsk.product_management_system_kafka.dataaccess.entities.User;
import com.pmsk.product_management_system_kafka.deserialzers.CategoryDeserializer;
import com.pmsk.product_management_system_kafka.deserialzers.ProductDeserializer;
import com.pmsk.product_management_system_kafka.deserialzers.UserDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerCategoryConfigs() {
        Map<String, Object> consumerConfigs = new HashMap<>();
        consumerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, "categories-consumer");
        consumerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerConfigs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return consumerConfigs;
    }

    @Bean
    public ConsumerFactory<String, Category> consumerCategoryFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerCategoryConfigs(), new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new CategoryDeserializer()));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Category>> kafkaCategoryListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Category> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerCategoryFactory());
        return factory;
    }

    @Bean
    public Map<String, Object> consumerProductConfigs() {
        Map<String, Object> consumerConfigs = new HashMap<>();
        consumerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, "products-consumer");
        consumerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerConfigs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return consumerConfigs;
    }

    @Bean
    public ConsumerFactory<String, Product> consumerProductFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerProductConfigs(), new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new ProductDeserializer()));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Product>> kafkaProductListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Product> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerProductFactory());
        return factory;
    }

    @Bean
    public Map<String, Object> consumerUserConfigs() {
        Map<String, Object> consumerConfigs = new HashMap<>();
        consumerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, "users-consumer");
        consumerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerConfigs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return consumerConfigs;
    }

    @Bean
    public ConsumerFactory<String, User> consumerUserFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerUserConfigs(), new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new UserDeserializer()));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, User>> kafkaUserListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerUserFactory());
        return factory;
    }

}
