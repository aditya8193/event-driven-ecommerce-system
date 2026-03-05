package com.aditya.product_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic productCreatedTopic() {
        return TopicBuilder.name("product-created")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic stockReducedTopic() {
        return TopicBuilder.name("stock-reduced")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderCreatedDLQTopic() {
        return TopicBuilder.name("order-created-dlq")
                .partitions(3)
                .replicas(1)
                .build();
    }
}