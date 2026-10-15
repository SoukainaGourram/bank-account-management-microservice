package org.sid.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.sid.bank_service.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder 
public class BankAccountRequestDTO {
    
    private AccountType type;
    private String currency;

    @JsonAlias("solde")
    private double balance;
  
    
}
