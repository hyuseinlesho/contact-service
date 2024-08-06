package com.hyuseinlesho.contactservice.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ContactProducer {
    private static final String TOPIC = "contact-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;
    public ContactProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("New contact message created: " + message);
    }
}
