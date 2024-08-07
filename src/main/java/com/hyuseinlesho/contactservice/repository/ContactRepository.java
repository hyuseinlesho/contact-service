package com.hyuseinlesho.contactservice.repository;

import com.hyuseinlesho.contactservice.model.entity.Contact;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface ContactRepository extends ReactiveCrudRepository<Contact, Long> {
    Flux<Contact> findAllByCreatedAtAfter(LocalDateTime since);
}
