package com.hyuseinlesho.contactservice.service;

import com.hyuseinlesho.contactservice.model.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.entity.Contact;
import com.hyuseinlesho.contactservice.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactServiceTest {
    // TODO Refactor tests

    @Mock
    private ContactRepository contactRepository;

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
                .message("Test message.").build();

        contactService.saveContact(contactDto);

        ArgumentCaptor<Contact> contactArgumentCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(contactRepository).save(contactArgumentCaptor.capture());

        Contact savedContact = contactArgumentCaptor.getValue();
        assertThat(savedContact.getName()).isEqualTo("John Doe");
        assertThat(savedContact.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(savedContact.getMessage()).isEqualTo("Test message.");
    }

    @Test
    void getNewContactsSince() {
        LocalDateTime since = LocalDateTime.now().minusDays(1);

        Contact contact1 = Contact.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("Test message")
                .createdAt(LocalDateTime.now().minusHours(12)).build();

        Contact contact2 = Contact.builder()
                .name("Test User")
                .email("test.user@example.com")
                .message("Another test message")
                .createdAt(LocalDateTime.now().minusHours(6)).build();

//        when(contactRepository.findByCreatedAtAfter(any(LocalDateTime.class)))
//                .thenReturn(List.of(contact1, contact2));
//
//        List<Contact> contacts = contactService.getNewContactsSince(since);
//
//        assertEquals(2, contacts.size());
//        assertThat(contacts.get(0).getName()).isEqualTo("John Doe");
//        assertThat(contacts.get(1).getName()).isEqualTo("Test User");
    }
}
