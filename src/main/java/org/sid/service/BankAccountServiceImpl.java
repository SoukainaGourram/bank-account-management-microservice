package org.sid.service;

import java.util.Date;

import org.sid.bank_service.entities.BankAccount;
import org.sid.bank_service.repository.BankAccountRepository;
import org.sid.dto.BankAccountRequestDTO;
import org.sid.dto.BankAccountResponseDTO;
import org.sid.mappers.BankAccountMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO) {
        BankAccount bankAccount = BankAccount.builder()
                .dateCreation(new Date())
                .type(bankAccountDTO.getType())
                .currency(bankAccountDTO.getCurrency())
                .balance(bankAccountDTO.getBalance())
                .build();

        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.fromBankAccount(savedBankAccount);
    }
}
