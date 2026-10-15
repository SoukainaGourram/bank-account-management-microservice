package org.sid.bank_service.repository;

import java.util.List;

import org.sid.bank_service.entities.AccountProjection;
import org.sid.bank_service.entities.BankAccount;
import org.sid.bank_service.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "bankAccounts", excerptProjection = AccountProjection.class)
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @RestResource(path = "byType", rel = "byType")
    List<BankAccount> findByType(@Param("type") AccountType type);
}