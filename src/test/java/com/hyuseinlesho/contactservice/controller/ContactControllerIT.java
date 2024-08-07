package com.hyuseinlesho.contactservice.controller;

import com.hyuseinlesho.contactservice.model.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.entity.Contact;
import com.hyuseinlesho.contactservice.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = ContactController.class)
@Import(ContactService.class)
public class ContactControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ContactService contactService;

    private CreateContactDto createContactDto;
    private Contact contact;

    @BeforeEach
    public void setup() {
        createContactDto = CreateContactDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message.")
                .build();
        contact = Contact.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message.")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void createContact() {
        when(contactService.createContact(createContactDto)).thenReturn(Mono.just(contact));

        webTestClient.post()
                .uri("/api/contacts/create")
                .bodyValue(createContactDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getAllContacts() {
        when(contactService.getAllContacts()).thenReturn(Flux.just(contact));

        webTestClient.get()
                .uri("/api/contacts")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(contact.getId())
                .jsonPath("$[0].name").isEqualTo(contact.getName())
                .jsonPath("$[0].email").isEqualTo(contact.getEmail())
                .jsonPath("$[0].message").isEqualTo(contact.getMessage())
                .jsonPath("$[0].createdAt").isNotEmpty();
    }

    @Test
    public void getNewContactsSince() {
        LocalDateTime since = LocalDateTime.now().minusDays(1);
        when(contactService.getNewContactsSince(since)).thenReturn(Flux.just(contact));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/contacts/since")
                        .queryParam("since", since.toString())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(contact.getId())
                .jsonPath("$[0].name").isEqualTo(contact.getName())
                .jsonPath("$[0].email").isEqualTo(contact.getEmail())
                .jsonPath("$[0].message").isEqualTo(contact.getMessage())
                .jsonPath("$[0].createdAt").isNotEmpty();
    }
}
