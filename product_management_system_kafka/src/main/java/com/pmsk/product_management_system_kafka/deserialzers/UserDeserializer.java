package com.pmsk.product_management_system_kafka.deserialzers;

import com.pmsk.product_management_system_kafka.dataaccess.entities.Product;
import com.pmsk.product_management_system_kafka.dataaccess.entities.User;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class UserDeserializer extends JsonDeserializer<User> {

    public UserDeserializer() {
        super(User.class);
    }
}
