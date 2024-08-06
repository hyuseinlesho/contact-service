package com.hyuseinlesho.contactservice.service;

import com.hyuseinlesho.contactservice.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.Contact;
import com.hyuseinlesho.contactservice.producer.ContactProducer;
import com.hyuseinlesho.contactservice.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactProducer contactProducer;

    public ContactService(ContactRepository contactRepository, ContactProducer contactProducer) {
        this.contactRepository = contactRepository;
        this.contactProducer = contactProducer;
    }

    public void saveContact(CreateContactDto contactDto) {
        Contact contact = mapToContact(contactDto);
        contactRepository.save(contact);
        contactProducer.sendMessage(contact.toString());
    }

    public List<Contact> getNewContactsSince(LocalDateTime since) {
        return contactRepository.findContactsSince(since);
    }

    private static Contact mapToContact(CreateContactDto contactDto) {
        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setMessage(contactDto.getMessage());
        return contact;
    }
}
