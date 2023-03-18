package ru.mipt.springtask.entity;

import lombok.*;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;  // final?

    @Getter
    @Setter
    private Long password;


    @Column(name = "roles", nullable=false)
    private String role;
}
