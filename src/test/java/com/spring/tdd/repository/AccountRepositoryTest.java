package com.spring.tdd.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.spring.tdd.entity.Account;

import jakarta.persistence.EntityManager;

@DataJpaTest
@Transactional

public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private EntityManager entityManager;

	// Test case for saving an account
	@Test
	public void testSaveAccount() {
		Account account = new Account();
		account.setName("John Doe");
		account.setEmail("john.doe@example.com");
		account.setBalance(500.0);

		Account savedAccount = accountRepository.save(account);

		assertThat(savedAccount).isNotNull();
		assertThat(savedAccount.getId()).isGreaterThan(0);
	}

	// Test case for account by id
    @Test
    public void testFindAccountById() {
        Account account = new Account();
        account.setName("Jane Doe");
        account.setEmail("jane.doe@example.com");
        account.setBalance(300.0);
        Account savedAccount = accountRepository.save(account);
        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());
        assertThat(foundAccount).isPresent();
        Account fetchedAccount = foundAccount.get();
        assertThat(fetchedAccount.getName()).isEqualTo("Jane Doe");
        assertThat(fetchedAccount.getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(fetchedAccount.getBalance()).isEqualTo(300.0);
    }

	// Test case for updating an account
	@Test
	public void testUpdateAccount() {
		Account account = new Account();
		account.setName("Jack Sparrow");
		account.setEmail("jack.sparrow@example.com");
		account.setBalance(1000.0);

		Account savedAccount = accountRepository.save(account);

		savedAccount.setBalance(2000.0);
		Account updatedAccount = accountRepository.save(savedAccount);

		assertThat(updatedAccount.getBalance()).isEqualTo(2000.0);
	}

	// Test case for deleting an account
	@Test
	public void testDeleteAccount() {
		Account account = new Account();
		account.setName("Davy Jones");
		account.setEmail("davy.jones@example.com");
		account.setBalance(500.0);

		Account savedAccount = accountRepository.save(account);

		accountRepository.deleteById(savedAccount.getId());

		Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());
		assertThat(foundAccount).isEmpty();
	}

	// Test case for finding all accounts
	@Test
	public void testFindAllAccounts() {
		Account account1 = new Account("Alice", "alice@example.com", 300.0);
		Account account2 = new Account("Bob", "bob@example.com", 400.0);

		accountRepository.save(account1);
		accountRepository.save(account2);

		Iterable<Account> allAccounts = accountRepository.findAll();

		assertThat(allAccounts).hasSize(2);
	}

	// Test for finding a non-existent account
	@Test
	public void testFindAccount_NotFound() {
		Optional<Account> account = accountRepository.findById(999L);
		assertTrue(account.isEmpty());
	}
}
