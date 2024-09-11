package com.spring.tdd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.tdd.entity.Account;
import com.spring.tdd.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account createAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public Optional<Account> getAccountById(Long id) {
		return accountRepository.findById(id);
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}
	
	// Update account
	@Override
    public Optional<Account> updateAccount(Long id, Account accountDetails) {
        return accountRepository.findById(id).map(existingAccount -> {
            existingAccount.setName(accountDetails.getName());
            existingAccount.setEmail(accountDetails.getEmail());
            existingAccount.setBalance(accountDetails.getBalance());
            return accountRepository.save(existingAccount);
        });
    }

    // Delete account
	@Override
    public boolean deleteAccount(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
