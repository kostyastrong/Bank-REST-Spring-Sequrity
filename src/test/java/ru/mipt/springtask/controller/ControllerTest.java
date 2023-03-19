package ru.mipt.springtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.entity.Role;
import ru.mipt.springtask.entity.UserPrincipal;
import ru.mipt.springtask.service.AccountService;
import ru.mipt.springtask.service.TransactionService;
import ru.mipt.springtask.service.UserService;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Slf4j
class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        accountService.deleteAll();
    }

    @Test
    public void testTranslate() throws Exception {
        Set<Role> userSet = new HashSet<Role>();
        userSet.add(new Role("USER"));
        UserPrincipal user1 = userService.addUser(new HashSet<Role>(userSet));
        UserPrincipal user2 = userService.addUser(new HashSet<Role>(userSet));

        AccountEntity account1 = accountService.addAccount(300L, user1.getId());
        AccountEntity account2 = accountService.addAccount(200L, user2.getId());
        Long beforeMinusing = accountService.getAccount(1L).getBalance();
        mockMvc.perform(post("http://localhost:8080/translate/%s/%s/120"
                .formatted(account1.getId(), account2.getId()))).andExpect(status().isOk());
        var result = mockMvc.perform(get("http://localhost:8080/get_account/%s"
                .formatted(account1.getId())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        AccountEntity resultEntity = new ObjectMapper().readValue(result, AccountEntity.class);
        log.info(result);
        assert resultEntity.getBalance() == 180;
    }
}