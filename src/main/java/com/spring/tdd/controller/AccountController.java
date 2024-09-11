package com.spring.tdd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.tdd.entity.Account;
import com.spring.tdd.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
    // create Account
	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		Account createdAccount = accountService.createAccount(account);
		return ResponseEntity.ok(createdAccount);
	}

	
    // Get Account By Id	
	 @GetMapping("/{id}")
	    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
	        Optional<Account> account = accountService.getAccountById(id);

	        if (account.isPresent()) {
	            return ResponseEntity.ok(account.get());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("Account with ID " + id + " not found.");
	        }
	    }

    // Get All Accounts
	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	 // Update account
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        Optional<Account> updatedAccount = accountService.updateAccount(id, account);
        return updatedAccount.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Delete account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        boolean isDeleted = accountService.deleteAccount(id);
        if (isDeleted) {
            return ResponseEntity.ok("Account deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        }
    }
}
