package ru.mipt.springtask.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mipt.springtask.Exceptions.InvalidAmountException;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.entity.TransactionEntity;
import ru.mipt.springtask.service.AccountService;
import ru.mipt.springtask.service.TransactionService;

@RestController
@Slf4j
public class Controller {
    @Autowired
    private AccountService accountService;
    private TransactionService transactionService;

    @PostMapping("/add_account/{money}")
    public AccountEntity addAccount(@PathVariable("money") Long money) {
        return accountService.addAccount(money);
    }

    @PostMapping("/translate/{account_from}/{account_to}/{money}")
    public TransactionEntity translate(@PathVariable("account_from") Long account_from,
                                       @PathVariable("account_to") Long account_to,
                                       @PathVariable("money") Long money) throws InvalidAmountException {
        return transactionService.Transfer(account_from, account_to, money);
    }

    @GetMapping("/get_account/{id}")
    public AccountEntity getAccount(@PathVariable("id") Long id) {
        log.info(id.toString());
        return accountService.getAccount(id);
    }
}
