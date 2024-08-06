package com.hyuseinlesho.contactservice.service;

import com.hyuseinlesho.contactservice.model.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.entity.Contact;
import com.hyuseinlesho.contactservice.producer.ContactProducer;
import com.hyuseinlesho.contactservice.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactProducer contactProducer;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveContact() {
        CreateContactDto contactDto = CreateContactDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message.")
                .build();

        Contact contact = Contact.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message.")
                .createdAt(LocalDateTime.now())
                .build();

        when(contactRepository.save(any(Contact.class))).thenReturn(Mono.just(contact));

        Mono<Contact> result = contactService.saveContact(contactDto);

        StepVerifier.create(result)
                .expectNextMatches(savedContact -> {
                    assertThat(savedContact.getName()).isEqualTo("John Doe");
                    assertThat(savedContact.getEmail()).isEqualTo("john.doe@example.com");
                    assertThat(savedContact.getMessage()).isEqualTo("Test message.");
                    return true;
                })
                .expectComplete()
                .verify();

        verify(contactProducer).sendMessage(contact);
    }

    @Test
    void getNewContactsSince() {
        LocalDateTime since = LocalDateTime.now().minusDays(1);

        Contact contact1 = Contact.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message")
                .createdAt(LocalDateTime.now().minusHours(12))
                .build();

        Contact contact2 = Contact.builder()
                .name("Test User")
                .email("test.user@example.com")
                .message("Another test message")
                .createdAt(LocalDateTime.now().minusHours(6))
                .build();

        when(contactRepository.findByCreatedAtAfter(since)).thenReturn(Flux.just(contact1, contact2));

        Flux<Contact> result = contactService.getNewContactsSince(since);

        StepVerifier.create(result)
                .expectNextMatches(contact -> contact.getName().equals("John Doe"))
                .expectNextMatches(contact -> contact.getName().equals("Test User"))
                .expectComplete()
                .verify();
    }
}
