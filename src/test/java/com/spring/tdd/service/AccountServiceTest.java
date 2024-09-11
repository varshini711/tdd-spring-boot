package com.spring.tdd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spring.tdd.entity.Account;
import com.spring.tdd.repository.AccountRepository;

public class AccountServiceTest {

    private AccountService accountService;
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void testCreateAccount() {
        // Arrange
        Account account = new Account("Vensi Kandula", "vensikandula@example.com", 100.0);
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        Account createdAccount = accountService.createAccount(account);

        // Assert
        assertEquals("Vensi Kandula", createdAccount.getName());
        assertEquals("vensikandula@example.com", createdAccount.getEmail());
        assertEquals(100.0, createdAccount.getBalance());
    }

    @Test
    public void testGetAccountById() {
        Account account = new Account("Vensi Kandula", "vensikandula@example.com", 100.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<Account> foundAccount = accountService.getAccountById(1L);
        assertTrue(foundAccount.isPresent());
        assertEquals("Vensi Kandula", foundAccount.get().getName());
    }

    @Test
    public void testGetAccountById_NotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Account> foundAccount = accountService.getAccountById(1L);
        assertFalse(foundAccount.isPresent());
    }
}


