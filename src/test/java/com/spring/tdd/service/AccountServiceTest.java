package com.spring.tdd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.spring.tdd.entity.Account;
import com.spring.tdd.repository.AccountRepository;

@SpringBootTest
@Transactional // Ensure rollback after each test
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

	// Test for updating an account
	@Test
	public void testUpdateAccount() {
		Account account = new Account("Old Name", "old.email@example.com", 500.0);
		account = accountRepository.save(account);

		Account updatedAccount = new Account("New Name", "new.email@example.com", 1000.0);

		Optional<Account> result = accountService.updateAccount(account.getId(), updatedAccount);
		assertTrue(result.isPresent());
		assertEquals("New Name", result.get().getName());
		assertEquals("new.email@example.com", result.get().getEmail());
	}

	// Test for updating a non-existing account
	@Test
	public void testUpdateAccount_NotFound() {
		Optional<Account> result = accountService.updateAccount(999L, new Account("Test", "test@example.com", 500.0));
		assertTrue(result.isEmpty());
	}

	// Test for deleting an account
	@Test
	public void testDeleteAccount() {
		Account account = new Account("Delete Me", "deleteme@example.com", 500.0);
		account = accountRepository.save(account);
		boolean result = accountService.deleteAccount(account.getId());
		assertTrue(result);
		assertFalse(accountRepository.existsById(account.getId()));
	}

	// Test for deleting a non-existing account
	@Test
	public void testDeleteAccount_NotFound() {
		boolean result = accountService.deleteAccount(999L);
		assertFalse(result);
	}
}
