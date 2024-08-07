package com.hyuseinlesho.contactservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyuseinlesho.contactservice.model.entity.Contact;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ContactProducer {
    private static final String TOPIC = "contact-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public ContactProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Contact contact) {
        try {
            String message = objectMapper.writeValueAsString(contact);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize contact to JSON", e);
        }
    }
}
