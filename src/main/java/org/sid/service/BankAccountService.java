package org.sid.service;

import org.sid.dto.BankAccountRequestDTO;
import org.sid.dto.BankAccountResponseDTO;

public interface BankAccountService {
    BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);
}