package com.hyuseinlesho.contactservice.controller;

import com.hyuseinlesho.contactservice.model.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.entity.Contact;
import com.hyuseinlesho.contactservice.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/create")
    public Mono<Contact> createContact(
            @RequestBody @Valid CreateContactDto contactDto
    ) {
        return contactService.createContact(contactDto);
    }

    @GetMapping
    public Flux<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/since")
    public Flux<Contact> getNewContactsSince(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since
    ) {
        return contactService.getNewContactsSince(since);
    }
}
