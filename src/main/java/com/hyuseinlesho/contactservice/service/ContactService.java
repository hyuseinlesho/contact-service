package com.hyuseinlesho.contactservice.service;

import com.hyuseinlesho.contactservice.model.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.entity.Contact;
import com.hyuseinlesho.contactservice.producer.ContactProducer;
import com.hyuseinlesho.contactservice.repository.ContactRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactProducer contactProducer;

    public ContactService(ContactRepository contactRepository, ContactProducer contactProducer) {
        this.contactRepository = contactRepository;
        this.contactProducer = contactProducer;
    }

    public Mono<Contact> saveContact(CreateContactDto contactDto) {
        Contact contact = mapToContact(contactDto);
        contact.setCreatedAt(LocalDateTime.now());
        return contactRepository.save(contact)
                .doOnNext(contactProducer::sendMessage);
    }

    public Flux<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

    public Flux<Contact> getNewContactsSince(LocalDateTime since) {
        return contactRepository.findByCreatedAtAfter(since);
    }

    private static Contact mapToContact(CreateContactDto contactDto) {
        return Contact.builder()
                .name(contactDto.getName())
                .email(contactDto.getEmail())
                .message(contactDto.getMessage())
                .build();
    }
}
