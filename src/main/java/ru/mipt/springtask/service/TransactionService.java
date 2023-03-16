package ru.mipt.springtask.service;

import lombok.Data;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.mipt.springtask.DTO.TransactionDTO;
import ru.mipt.springtask.Exceptions.InvalidAmountException;
import ru.mipt.springtask.Exceptions.InvalidIdException;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.entity.TransactionEntity;
import ru.mipt.springtask.repository.AccountRepository;
import ru.mipt.springtask.repository.TransactionRepository;

import java.util.Optional;

import org.modelmapper.ModelMapper;

@Service
public class TransactionService {
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    synchronized void Cancel(Long id) throws InvalidIdException, InvalidAmountException {
        Optional<TransactionEntity> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()){
            throw new InvalidIdException("No transactions with such id");
        }
        transaction.get().setCancelled(Boolean.TRUE);
    }

    public void Add(Long id, Long val) {
        AccountEntity referenceById = accountRepository.getReferenceById(id);
        referenceById.setBalance(referenceById.getBalance() + val);
    }
    synchronized public TransactionEntity Transfer(Long from, Long to, long money) throws InvalidAmountException {
        AccountEntity account_from = accountRepository.findById(from).orElseThrow();
        AccountEntity account_to = accountRepository.findById(from).orElseThrow();
//        if (account_from.equals(null) || account_to.equals(null)) {
//            throw new InvalidIdException("No users with such id-s");
//        }  // TODO default or custom throw method
        if (account_from.getBalance() < money){
            throw new InvalidAmountException("Not enough money to transfer");
        }
        if (money < 0) {
            throw new InvalidAmountException("Negative amount of money to transfer");
        }
        Add(from, -money);
        Add(to, -money);
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .account_from(from)
                .account_to(to)
                .money(money)
                .cancelled(false).build();
        TransactionEntity transactionEntity = modelMapper.map(transactionDTO, TransactionEntity.class);
        transactionRepository.save(transactionEntity);
        return transactionEntity;
    }


}
