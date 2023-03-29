package ru.mipt.springtask.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.mipt.springtask.Exceptions.InvalidAmountException;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.entity.Role;
import ru.mipt.springtask.entity.TransactionEntity;
import ru.mipt.springtask.entity.UserPrincipal;
import ru.mipt.springtask.service.AccountService;
import ru.mipt.springtask.service.TransactionService;
import ru.mipt.springtask.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Slf4j
@RequestMapping
public class Controller {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @Secured("ROLE_ADMIN")

    @PostMapping("/add_account/{money}/{id}")
    public AccountEntity addAccount(@PathVariable("money") Long money, @PathVariable Long id) {
        return accountService.addAccount(money, id);
    }

    @Secured("ROLE_ADMIN")

    @PostMapping("/add_user")
    public UserPrincipal addUser() {
        Set<Role> userSet = new HashSet<>();
        userSet.add(new Role("USER"));
        return userService.addUser(userSet);
    }

    @PostMapping("/translate/{account_from}/{account_to}/{money}")  // admin/user???
    public TransactionEntity translate(@PathVariable("account_from") Long account_from,
                                       @PathVariable("account_to") Long account_to,
                                       @PathVariable("money") Long money) throws InvalidAmountException {
        return transactionService.Transfer(account_from, account_to, money);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get_account/{id}")
    public AccountEntity getAccount(@PathVariable("id") Long id, HttpSession session, Authentication auth) {
        log.info(id.toString());
        Collection<GrantedAuthority> authorities = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(user -> (UserDetails) user)
                .map(principal -> (Collection<GrantedAuthority>) principal.getAuthorities())
                .orElse(Collections.emptyList());

        boolean ability = authorities.stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority())) ||
                userService.getUserPrincipal(accountService.getAccount(id).getUserId())
                        .equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (!ability) {
            log.info("Not enough rights");
            return null;
        }

        return accountService.getAccount(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/get_all_accounts")
    public List<AccountEntity> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}
