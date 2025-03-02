package com.example.tasklist.domain.task;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;

    @Enumerated(value = EnumType.STRING)
    Status status;

    LocalDateTime expirationDate;

    @Column(name = "image")
    @CollectionTable(name = "tasks_images")
    @ElementCollection
    List<String> images;

}
