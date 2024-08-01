package com.hyuseinlesho.contactservice.controller;

import com.hyuseinlesho.contactservice.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.Contact;
import com.hyuseinlesho.contactservice.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> saveContact(@RequestBody @Valid CreateContactDto contactDto) {
        contactService.saveContact(contactDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/since")
    public ResponseEntity<List<Contact>> getNewContactsSince(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since
    ) {
        List<Contact> contacts = contactService.getNewContactsSince(since);
        return ResponseEntity.ok(contacts);
    }
}
