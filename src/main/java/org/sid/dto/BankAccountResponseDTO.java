package org.sid.dto;

import java.util.Date;

import org.sid.bank_service.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data  @NoArgsConstructor @AllArgsConstructor @Builder

public class BankAccountResponseDTO {
     private Long id;
     private Date createdAt;
    private AccountType type;
    private String currency;
    private double balance;
  
    
}
