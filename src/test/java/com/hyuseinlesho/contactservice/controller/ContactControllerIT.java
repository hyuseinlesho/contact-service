package com.hyuseinlesho.contactservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyuseinlesho.contactservice.model.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.entity.Contact;
import com.hyuseinlesho.contactservice.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
    }

    @Test
    void saveContact() throws Exception {
        CreateContactDto contactDto = CreateContactDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message.").build();

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getNewContactsSince() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Contact contact1 = Contact.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message")
                .createdAt(LocalDateTime.now().minusHours(2)).build();

        Contact contact2 = Contact.builder()
                .name("Test User")
                .email("test.user@example.com")
                .message("Another test message")
                .createdAt(LocalDateTime.now().minusHours(1)).build();

        contactRepository.saveAll(List.of(contact1, contact2));

        String since = now.minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME);

        mockMvc.perform(get("/api/contacts/since")
                        .param("since", since)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Test User"));
    }
}
