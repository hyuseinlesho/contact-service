package com.hyuseinlesho.contactservice.mapper;

import com.hyuseinlesho.contactservice.dto.CreateContactDto;
import com.hyuseinlesho.contactservice.model.Contact;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {
    Contact mapToContact(CreateContactDto contactDto);
}
