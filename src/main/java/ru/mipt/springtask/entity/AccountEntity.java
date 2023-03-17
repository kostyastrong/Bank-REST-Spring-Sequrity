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
@Table(name = "accounts")

public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;  // final?

    @Getter
    @Setter
    private Long balance;
}
