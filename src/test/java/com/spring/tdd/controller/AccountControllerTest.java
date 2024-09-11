package com.spring.tdd.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.tdd.entity.Account;
import com.spring.tdd.service.AccountService;

public class AccountControllerTest {

 private AccountService accountService;
 private AccountController accountController;

 @BeforeEach
 public void setUp() {
     accountService = mock(AccountService.class);
     accountController = new AccountController(accountService);
 }

 @Test
 public void testCreateAccount() {
     Account account = new Account("Vensi Kandula", "vensikandula@example.com", 100.0);
     when(accountService.createAccount(account)).thenReturn(account);

     ResponseEntity<Account> response = accountController.createAccount(account);
     assertEquals(HttpStatus.OK, response.getStatusCode());
     assertEquals("Vensi Kandula", response.getBody().getName());
 }

 @Test
 public void testGetAccountById() {
     // Arrange
     Account account = new Account("Vensi Kandula", "vensikandula@example.com", 100.0);
     when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

     // Act
     ResponseEntity<?> response = accountController.getAccountById(1L);

     // Assert
     assertEquals(HttpStatus.OK, response.getStatusCode());
     assertNotNull(response.getBody());
     assertTrue(response.getBody() instanceof Account);
     Account responseBody = (Account) response.getBody();
     assertEquals("Vensi Kandula", responseBody.getName());
 }


 @Test
 public void testGetAccountById_NotFound() {
     // Arrange
     when(accountService.getAccountById(1L)).thenReturn(Optional.empty());

     // Act
     ResponseEntity<?> response = accountController.getAccountById(1L);

     // Assert
     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     assertNotNull(response.getBody());
     assertTrue(response.getBody() instanceof String);
     String responseBody = (String) response.getBody();
     assertEquals("Account with ID 1 not found.", responseBody);
 }

}


