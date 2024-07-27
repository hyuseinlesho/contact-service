package com.hyuseinlesho.contactservice.service;

import com.hyuseinlesho.contactservice.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.mapper.ContactMapper;
import com.hyuseinlesho.contactservice.model.Contact;
import com.hyuseinlesho.contactservice.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    public void saveContact(CreateContactDto contactDto) {
        Contact contact = contactMapper.mapToContact(contactDto);
        contactRepository.save(contact);
    }

    public List<Contact> getNewContactsSince(LocalDateTime since) {
        return contactRepository.findContactsSince(since);
    }
}
