package org.sid.bank_service.web;

import java.util.List;

import org.sid.bank_service.entities.BankAccount;
import org.sid.bank_service.enums.AccountType;
import org.sid.bank_service.repository.BankAccountRepository;
import org.sid.dto.BankAccountRequestDTO;
import org.sid.dto.BankAccountResponseDTO;
import org.sid.mappers.BankAccountMapper;
import org.sid.service.BankAccountService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api")
public class BankAccountRestController {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

        public BankAccountRestController(BankAccountRepository bankAccountRepository, BankAccountService bankAccountService,
            BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountService = bankAccountService;
        this.bankAccountMapper = bankAccountMapper;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccountResponseDTO> bankAccounts() {
        return toResponses(bankAccountRepository.findAll());
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccountResponseDTO bankAccount(@PathVariable Long id) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
        return bankAccountMapper.fromBankAccount(account);
    }

    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO saveBankAccount(@RequestBody BankAccountRequestDTO requestDTO) {
        // Implementation for saving bank account
        return bankAccountService.addAccount(requestDTO);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccountResponseDTO updateBankAccount(@PathVariable Long id, @RequestBody BankAccount bankAccount) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));

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

        return bankAccountMapper.fromBankAccount(bankAccountRepository.save(account));
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void deleteBankAccount(@PathVariable Long id) {
        bankAccountRepository.deleteById(id);
    }

    @GetMapping("/bankAccounts/search/findByType")
    public List<BankAccountResponseDTO> findByType(@RequestParam String type) {
        AccountType accountType;
        try {
            accountType = AccountType.valueOf(type);
        } catch (IllegalArgumentException e) {
            if ("SAVING_ACCOUNT".equals(type)) {
                accountType = AccountType.SAVING_ACCOUNT;
            } else {
                throw e;
            }
        }
        return toResponses(bankAccountRepository.findByType(accountType));
    }

    private List<BankAccountResponseDTO> toResponses(List<BankAccount> accounts) {
        return accounts.stream().map(bankAccountMapper::fromBankAccount).toList();
    }
}