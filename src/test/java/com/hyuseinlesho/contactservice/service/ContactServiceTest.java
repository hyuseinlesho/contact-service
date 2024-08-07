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
    void createContact() {
        CreateContactDto contactDto = CreateContactDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message.")
                .build();

        Contact contact = getContact1();

        when(contactRepository.save(any(Contact.class))).thenReturn(Mono.just(contact));

        Mono<Contact> result = contactService.createContact(contactDto);

        StepVerifier.create(result)
                .expectNextMatches(savedContact -> {
                    assertThat(savedContact.getName()).isEqualTo("John Doe");
                    assertThat(savedContact.getEmail()).isEqualTo("john.doe@example.com");
                    assertThat(savedContact.getMessage()).isEqualTo("Test message");
                    return true;
                })
                .expectComplete()
                .verify();

        verify(contactProducer).sendMessage(contact);
    }

    @Test
    void getAllContacts_ReturnsContactFlux() {
        Contact contact1 = getContact1();
        Contact contact2 = getContact2();

        when(contactRepository.findAll()).thenReturn(Flux.just(contact1, contact2));

        Flux<Contact> result = contactService.getAllContacts();

        StepVerifier.create(result)
                .expectNext(contact1)
                .expectNext(contact2)
                .expectComplete()
                .verify();
    }

    @Test
    void getNewContactsSince() {
        LocalDateTime since = LocalDateTime.now().minusDays(1);

        Contact contact1 = getContact1();
        Contact contact2 = getContact2();

        when(contactRepository.findAllByCreatedAtAfter(since)).thenReturn(Flux.just(contact1, contact2));

        Flux<Contact> result = contactService.getNewContactsSince(since);

        StepVerifier.create(result)
                .expectNextMatches(contact -> contact.getName().equals("John Doe"))
                .expectNextMatches(contact -> contact.getName().equals("Test User"))
                .expectComplete()
                .verify();
    }

    private static Contact getContact1() {
        return Contact.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message")
                .createdAt(LocalDateTime.now().minusHours(12))
                .build();
    }

    private static Contact getContact2() {
        return Contact.builder()
                .name("Test User")
                .email("test.user@example.com")
                .message("Another test message")
                .createdAt(LocalDateTime.now().minusHours(6))
                .build();
    }
}
