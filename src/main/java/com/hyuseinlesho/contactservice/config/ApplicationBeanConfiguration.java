package com.hyuseinlesho.contactservice.config;

import com.hyuseinlesho.contactservice.mapper.ContactMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    ContactMapper contactMapper() {
        return Mappers.getMapper(ContactMapper.class);
    }
}
