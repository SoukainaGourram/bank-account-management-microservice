package org.sid.mappers;

import org.sid.bank_service.entities.BankAccount;
import org.sid.dto.BankAccountResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {
    public BankAccountResponseDTO fromBankAccount(BankAccount bankAccount) {
        return BankAccountResponseDTO.builder()
                .id(bankAccount.getId())
                .createdAt(bankAccount.getDateCreation())
                .type(bankAccount.getType())
                .currency(bankAccount.getCurrency())
                .balance(bankAccount.getBalance())
                .build();
    }
}
