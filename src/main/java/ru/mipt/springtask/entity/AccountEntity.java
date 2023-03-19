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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @Column(name = "id", nullable = false)
    private Long id;  // final?

    @Getter
    @Setter
    private Long balance;

    @Getter
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
