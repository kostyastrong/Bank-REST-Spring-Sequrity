package ru.mipt.springtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mipt.springtask.entity.UserPrincipal;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserPrincipal, Long> {

    Optional<UserPrincipal> findByUserName(String name);
}
