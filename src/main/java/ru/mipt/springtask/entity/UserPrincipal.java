package ru.mipt.springtask.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class UserPrincipal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;  // final?

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    public String userName;


    @ManyToMany(mappedBy = "userPrincipal")
    private Set<Role> roles = new HashSet<>();
}
