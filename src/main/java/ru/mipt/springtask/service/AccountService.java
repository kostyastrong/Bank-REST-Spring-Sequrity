package ru.mipt.springtask.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mipt.springtask.DTO.AccountDTO;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.repository.AccountRepository;
import ru.mipt.springtask.repository.UserRepository;

import java.util.List;


@Service
@Slf4j
public class AccountService {
    @Autowired
    private UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper(); // TODO: never created properly
    @Autowired
    private AccountRepository accountRepository;

    public AccountEntity addAccount(Long balance, Long userId) {
        AccountEntity account = AccountEntity.builder().balance(balance).build();
        log.info(account.getBalance().toString());
        userRepository.getReferenceById(userId).addAccount(account);
        return accountRepository.save(account);
    }


    @Transactional
    public AccountEntity getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<AccountEntity> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
