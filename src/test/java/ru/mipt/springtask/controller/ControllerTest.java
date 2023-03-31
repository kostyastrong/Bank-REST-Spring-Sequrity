package ru.mipt.springtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.entity.Role;
import ru.mipt.springtask.entity.UserPrincipal;
import ru.mipt.springtask.service.AccountService;
import ru.mipt.springtask.service.TransactionService;
import ru.mipt.springtask.service.UserService;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testTranslate() throws Exception {

        String user1String = mockMvc.perform(post("/add_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"user1\",\"password\":\"user1pass\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String user2String = mockMvc.perform(post("/add_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"user2\",\"password\":\"user2pass\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserPrincipal user1 = new ObjectMapper().readValue(user1String, UserPrincipal.class);
        UserPrincipal user2 = new ObjectMapper().readValue(user2String, UserPrincipal.class);

        String account1String = mockMvc.perform(post("/add_account/300/%s".formatted(user1.getId())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String account2String = mockMvc.perform(post("/add_account/300/%s".formatted(user2.getId())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AccountEntity account1 = new ObjectMapper().readValue(account1String, AccountEntity.class);
        AccountEntity account2 = new ObjectMapper().readValue(account2String, AccountEntity.class);

        Long beforeMinusing = accountService.getAccount(1L).getBalance();
        mockMvc.perform(post("/translate/%s/%s/120"
                .formatted(account1.getId(), account2.getId()))).andExpect(status().isOk());
        var result = mockMvc.perform(get("/get_account/%s"
                .formatted(account1.getId())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        AccountEntity resultEntity = new ObjectMapper().readValue(result, AccountEntity.class);
        log.info(result);
        assertEquals(resultEntity.getBalance(), 180);
    }
}