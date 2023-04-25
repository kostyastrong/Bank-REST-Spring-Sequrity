package ru.mipt.springtask.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mipt.springtask.DTO.AccountDTO;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.repository.AccountRepository;

import java.util.List;


@Service
@Slf4j
public class AccountService {
    private final ModelMapper modelMapper = new ModelMapper(); // TODO: never created properly
    @Autowired
    private AccountRepository accountRepository;

    public AccountEntity addAccount(Long balance, Long userId) {  // TODO: remove dto
        AccountDTO dto = AccountDTO.builder()
                .balance(balance)
                .userId(userId).build();
        AccountEntity entity = modelMapper.map(dto, AccountEntity.class);
        log.info(entity.getBalance().toString());
        return accountRepository.save(entity);
    }

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
