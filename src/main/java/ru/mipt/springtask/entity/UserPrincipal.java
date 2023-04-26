package ru.mipt.springtask.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserPrincipal {
    @Getter
    @Setter
    public String userName;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;  // final?
    @Getter
    @Setter
    private String password;
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)

    private Set<AccountEntity> accounts = new HashSet<>();

    @ManyToMany(mappedBy = "userPrincipal")
    private Set<Role> roles = new HashSet<>();

    public void addAccount(AccountEntity account) {
        accounts.add(account);
        account.setUser(this);
    }

    public void removeAccount(AccountEntity account) {
        accounts.remove(account);
        account.setUser(null);
    }
}
