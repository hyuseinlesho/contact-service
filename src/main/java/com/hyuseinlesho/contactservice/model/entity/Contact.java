package com.hyuseinlesho.contactservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("contacts")
public class Contact {

    @Id
    private Long id;

    private String name;
    private String email;
    private String message;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
}
