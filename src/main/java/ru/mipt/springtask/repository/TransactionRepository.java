package ru.mipt.springtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

}
