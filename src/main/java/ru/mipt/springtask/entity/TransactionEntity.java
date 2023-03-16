package ru.mipt.springtask.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    @Getter
    private Long account_to;

    @Getter
    private Long account_from;

    @Getter
    private Long money;

    @Getter
    @Setter
    private Boolean cancelled;
}
