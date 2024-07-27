package com.hyuseinlesho.contactservice.service;

import com.hyuseinlesho.contactservice.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.Contact;
import com.hyuseinlesho.contactservice.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    void saveContact() {
        CreateContactDto contactDto = CreateContactDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .message("This is a test message.").build();

        Contact contact = Contact.builder()
                .name(contactDto.getName())
                .email(contactDto.getEmail())
                .message(contactDto.getMessage()).build();

        when(contactRepository.save(contact))
                .thenReturn(contact);

        contactService.saveContact(contactDto);

        verify(contactRepository, times(1)).save(contact);
    }
}
