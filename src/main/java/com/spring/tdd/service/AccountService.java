package com.spring.tdd.service;

import java.util.List;
import java.util.Optional;

import com.spring.tdd.entity.Account;

public interface AccountService {
	Account createAccount(Account account);

	Optional<Account> getAccountById(Long id);

	List<Account> getAllAccounts();
	Optional<Account> updateAccount(Long id, Account accountDetails);
	boolean deleteAccount(Long id);
}
