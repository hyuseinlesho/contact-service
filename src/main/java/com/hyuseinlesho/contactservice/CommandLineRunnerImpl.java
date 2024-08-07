package com.hyuseinlesho.contactservice;

import com.hyuseinlesho.contactservice.model.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.service.ContactService;
import org.springframework.boot.CommandLineRunner;

//@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final ContactService contactService;

    public CommandLineRunnerImpl(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Test Reactive
        for (int i = 0; i < 100; i++) {
            CreateContactDto contactDto = CreateContactDto.builder()
                    .name("John Doe " + i)
                    .email("john.doe" + i + "@example.com")
                    .message("Test message " + i)
                    .build();
            contactService.createContact(contactDto).subscribe();
        }
    }
}
