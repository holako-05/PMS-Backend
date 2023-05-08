package com.pmsk.product_management_system_kafka.deserialzers;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.pmsk.product_management_system_kafka.dataaccess.entities.Category;

import java.io.IOException;

public class CategoryDeserializer extends JsonDeserializer<Category> {

    public CategoryDeserializer() {
        super(Category.class);
    }
}
