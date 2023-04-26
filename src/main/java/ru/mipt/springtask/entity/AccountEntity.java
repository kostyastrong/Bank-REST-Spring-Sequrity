package ru.mipt.springtask.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "accounts")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = AccountEntity.class)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    private Long balance;

    @ManyToOne(fetch = FetchType.EAGER)  // TODO: error with lazy init

    private UserPrincipal user;
}
