package org.sid.bank_service.web;

import java.util.Date;
import java.util.List;

import org.sid.bank_service.Repository.BankAccountRepository;
import org.sid.bank_service.entities.BankAccount;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountRestController {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountRestController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts() {
        return bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
    }

    @PostMapping("/bankAccounts")
    public BankAccount saveBankAccount(@RequestBody BankAccount bankAccount) {
        if (bankAccount.getDateCreation() == null) {
            bankAccount.setDateCreation(new Date());
        }
        return bankAccountRepository.save(bankAccount);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccount updateBankAccount(@PathVariable Long id, @RequestBody BankAccount bankAccount) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));

        if (bankAccount.getSolde() != 0.0) {
            account.setSolde(bankAccount.getSolde());
        }
        if (bankAccount.getBalance() != 0.0) {
            account.setBalance(bankAccount.getBalance());
        }
        if (bankAccount.getCurrency() != null) {
            account.setCurrency(bankAccount.getCurrency());
        }
        if (bankAccount.getDateCreation() != null) {
            account.setDateCreation(bankAccount.getDateCreation());
        }
        if (bankAccount.getType() != null) {
            account.setType(bankAccount.getType());
        }

        return bankAccountRepository.save(account);
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void deleteBankAccount(@PathVariable Long id) {
        bankAccountRepository.deleteById(id);
    }
}