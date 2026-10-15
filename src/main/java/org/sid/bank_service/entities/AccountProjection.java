package org.sid.bank_service.entities;

import org.sid.bank_service.enums.AccountType;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = BankAccount.class, name = "ProjectionN1")
public interface AccountProjection {
   Long getId();
   AccountType getType();
}