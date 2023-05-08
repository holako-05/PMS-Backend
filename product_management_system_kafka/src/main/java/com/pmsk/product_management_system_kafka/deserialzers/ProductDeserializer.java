package com.pmsk.product_management_system_kafka.deserialzers;

import com.pmsk.product_management_system_kafka.dataaccess.entities.Product;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class ProductDeserializer extends JsonDeserializer<Product> {

    public ProductDeserializer() {
        super(Product.class);
    }
}
